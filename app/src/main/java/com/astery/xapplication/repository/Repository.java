package com.astery.xapplication.repository;

import android.util.Log;

import com.astery.xapplication.data_source.controller.DataController;
import com.astery.xapplication.pojo.Advise;
import com.astery.xapplication.pojo.Answer;
import com.astery.xapplication.pojo.Category;
import com.astery.xapplication.pojo.Event;
import com.astery.xapplication.pojo.EventTemplate;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.pojo.Question;
import com.astery.xapplication.repository.commands.Command;
import com.astery.xapplication.repository.commands.CommandManager;
import com.astery.xapplication.repository.commands.TogetherCommandManager;
import com.astery.xapplication.repository.listeners.GetItemListener;
import com.astery.xapplication.repository.listeners.JobListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Repository {
    public final DataController dataController;

    public Repository(DataController dataController) {
        this.dataController = dataController;
    }

    /** initially loading data*/
    public void prepareData(JobListener parentListener){
        Command preparation = new Command(listener ->
                dataController.loadValuesFromRemote(listener, Category.class));

        preparation.setParentListener(parentListener);
        preparation.work();
    }

    public void loadCategories(GetItemListener<List<Category>> listener){
        dataController.getValuesFromLocal(listener, Category.class);
    }

    public void loadItemsForCategory(GetItemListener<List<Item>> listener){
        dataController.getValuesFromLocal(listener, Item.class);
    }

    public void getItemById(String id, GetItemListener<Item> listener){
        dataController.getValuesFromLocalById(listener, id, Item.class);
    }


    public void loadEvents(Calendar calendar, GetItemListener<List<Event>> itemListener){
        Date date = calendar.getTime();
        Log.i("main", date.getTime() + "");
        dataController.getValuesFromLocalByDay(itemListener, date, Event.class);
    }

    public void addEvent(Event event, JobListener listener){
        ArrayList<Event> events = new ArrayList<>();
        events.add(event);
        Log.i("main", event.getDate().getTime() + "");
        dataController.pushDataToLocal(events, listener, Event.class);
    }


    /** load questions, tips. It doesn't load item */
    public void getEventInfoForTips(Event event, JobListener parent) {

        List<Question> questions = event.getTemplate().getQuestions();
        TogetherCommandManager qManager = new TogetherCommandManager();
        TogetherCommandManager aManager = new TogetherCommandManager();
        TogetherCommandManager uManager = new TogetherCommandManager();

        if (questions == null)
            return;

        for (Question question : questions) {
            qManager.setCommand(listener -> dataController.getValuesFromLocalById(new GetItemListener<Question>() {
                @Override
                public void getItem(Question item) {
                    question.setText(item.getText());
                    listener.done(true);
                }

                @Override public void error() { listener.done(false); }
            }, question.getId(), Question.class));


            if (question.getSelectedAnswer().getItemOb() == null)
                continue;
            if (question.getSelectedAnswer().getItemOb().getAdvises() == null) {
                aManager.setCommand(listener -> dataController.getValuesFromLocalByParent(new GetItemListener<List<Advise>>() {
                    @Override
                    public void getItem(List<Advise> item) {
                        question.getSelectedAnswer().getItemOb().setAdvises(item);
                        listener.done(true);
                    }
                    @Override public void error() { listener.done(false); }
                }, question.getSelectedAnswer().getItem(), Advise.class));
            }
        }
        uManager.setCommand(aManager.getCommand());
        uManager.setCommand(qManager.getCommand());

        Command load = uManager.getCommand();
        load.setParentListener(parent);
        load.work();
    }

    /** load eventTemplate and answers. It doesn't load questions. It's enough to provide all information to look and defer is there are any tips*/
    public void getEventInfoForLook(Event event, JobListener parent) {
        CommandManager<EventTemplate> cm = new CommandManager<>(event::setTemplate);

        Command load = cm.getCommand(listener -> dataController.getValuesFromLocalById(cm.getInnerListener(), event.getTemplateId(), EventTemplate.class));
        cm.setNextCommands(listener -> {

            if (event.getEventDescription() == null)
                return;

            event.getTemplate().setQuestions(new ArrayList<>());

            for (String question: event.getEventDescription().getProperties().keySet()){
                Question q = new Question(question, null, null);
                event.getTemplate().getQuestions().add(q);

                CommandManager<Answer> cm3 = new CommandManager<>(item -> {
                    q.setSelectedAnswer(item);
                    item.setItemOb(new Item(item.getItem(), null, null, null));
                });
                load.setNext(cm3.getCommand(listener1 ->
                        dataController.getValuesFromLocalById(cm3.getInnerListener(),event.getEventDescription().getProperties().get(question),
                                Answer.class)));
            }
        });

        load.setParentListener(parent);
        load.work();
    }

}
