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

        preparation.setNext(new Command(listener -> dataController.getValuesFromLocal(
                new GetItemListener<List<Category>>() {
            @Override public void getItem(List<Category> item) {
                listener.done(true);
            }
            @Override public void error() { listener.done(false); }
        }, Category.class)));

        preparation.setParentListener(parentListener);
        preparation.work();
    }

    public void loadCategories(GetItemListener<List<Category>> listener){
        dataController.getValuesFromLocal(
                new GetItemListener<List<Category>>() {
                    @Override public void getItem(List<Category> item) {
                        listener.getItem(item);
                    }
                    @Override public void error() { listener.error(); }
                }, Category.class);
    }

    public void loadItemsForCategory(GetItemListener<List<Item>> listener){
        dataController.getValuesFromLocal(
                new GetItemListener<List<Item>>() {
                    @Override public void getItem(List<Item> item) {
                        listener.getItem(item);
                    }
                    @Override public void error() { listener.error(); }
                }, Item.class);
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

    public void getEventInfo(Event event, JobListener parent) {
        CommandManager<EventTemplate> cm = new CommandManager<>(event::setTemplate);
        Command load = cm.getCommand(listener -> dataController.getValuesFromLocalById(cm.getInnerListener(), event.getTemplateId(), EventTemplate.class));
        cm.setNextCommands(listener -> {

            if (event.getEventDescription() == null)
                return;

            event.getTemplate().setQuestions(new ArrayList<>());

            for (String question: event.getEventDescription().getProperties().keySet()){
                Question q = new Question(question, null, null);
                event.getTemplate().getQuestions().add(q);

                CommandManager<Answer> cm3 = new CommandManager<>(q::setSelectedAnswer);
                load.setNext(cm3.getCommand(listener1 ->
                        dataController.getValuesFromLocalById(cm3.getInnerListener(),event.getEventDescription().getProperties().get(question),
                                Answer.class)));

                cm3.setNextCommands(listener13 -> {
                    if (q.getSelectedAnswer().getItem() != null){
                        q.getSelectedAnswer().setItemOb(new Item(q.getSelectedAnswer().getItem(), null, null));
                        CommandManager<List<Advise>> cm4 = new CommandManager<>(item -> q.getSelectedAnswer().getItemOb().setAdvises(item));
                        load.setNext(cm4.getCommand(listener12 ->
                                dataController.getValuesFromLocalByParent(cm4.getInnerListener(), q.getSelectedAnswer().getItem(), Advise.class)));

                    }
                });
            }
        });

        load.setParentListener(parent);
        load.work();

        }

}
