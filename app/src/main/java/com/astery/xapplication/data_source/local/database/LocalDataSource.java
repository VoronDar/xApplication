package com.astery.xapplication.data_source.local.database;

import com.astery.xapplication.data_source.local.database.db_utils.LocalLoadable;
import com.astery.xapplication.data_source.rx_utils.RxExecutable;
import com.astery.xapplication.data_source.rx_utils.RxTaskManager;
import com.astery.xapplication.pojo.Advise;
import com.astery.xapplication.pojo.Answer;
import com.astery.xapplication.pojo.Category;
import com.astery.xapplication.pojo.Event;
import com.astery.xapplication.pojo.EventTemplate;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.pojo.Question;
import com.astery.xapplication.pojo.pojo_converters.ItemConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unchecked")
public class LocalDataSource {

    private final AppDatabase database;

    public LocalDataSource(AppDatabase database) {
        this.database = database;
    }


    public <T> void getValuesWithParent(String parentId, DisposableSingleObserver<T> observer, String className){
        switch (className){
            case "Question":
                subscribe((Single<T>)database.questionDao().getQuestionsByParents(parentId), observer);
                break;
            case "Item":
                subscribe((Single<T>)database.articleDao().getItemsByParentId(parentId), observer);
                break;
            case "Advise":
                subscribe((Single<T>)database.articleDao().getAdvisesForItem(parentId), observer);
                break;
            default:
                throw new RuntimeException("getValuesWithParent got a new class " + className);
        }
    }

    public <T> void getValuesByDate(DisposableSingleObserver<T> observer, Date date, String className){
        switch (className){
            case "Event":
                subscribe((Single<T>)database.eventDao().getEventsByTime(date.getTime()), observer);
                break;
            default:
                throw new RuntimeException("getValuesWithParent got a new class " + className);
        }
    }


    public <T> void getValuesById(DisposableSingleObserver<T> observer, String id, String className){
        switch (className){
            case "EventTemplate":
                subscribe((Single<T>)database.eventDao().getEventTemplate(id), observer);
                break;
            case "Event":
                subscribe((Single<T>)database.eventDao().getEvent(id), observer);
                break;
            case "Answer":
                subscribe((Single<T>)database.questionDao().getAnswer(id), observer);
                break;
            default:
                throw new RuntimeException("getValuesWithParent got a new class " + className);
        }
    }


    public <T> void getValues(DisposableSingleObserver<T> observer, String className){
        switch (className){
            case "Category":
                subscribe((Single<T>)database.faqDao().getCategories(), observer);
                break;
            case "Item":
                subscribe((Single<T>)database.articleDao().getItems(), observer);
                break;
            default:
                throw new RuntimeException("getValues got a new class " + className);
        }
    }



    public <T> void loadValue(T item, LocalLoadable loadable, String className){
        List<T> list = new ArrayList<>();
        list.add(item);
        loadValues(list, loadable, className);
    }

    /** load all values */
    public <T> void loadValues(T list, LocalLoadable loadable, String className){
        RxTaskManager.doTask(new RxExecutable() {
            @Override public void doSomething() {
                switch (className){
                    case "Category":
                        database.faqDao().addCategories((List<Category>)list);
                        break;
                    case "Advise":
                        database.articleDao().addAdvises((List<Advise>)list);
                        break;
                    case "Item":
                        database.articleDao().addItems(ItemConverter.getEntities((List<Item>)list));
                        break;
                    case "Event":
                        database.eventDao().addEvents((List<Event>)list);
                        break;
                    case "EventTemplate":
                        database.eventDao().addEventTemplates((List<EventTemplate>)list);
                        break;
                    case "Answer":
                        database.questionDao().addAnswers((List<Answer>)list);
                        break;
                    default:
                        throw new RuntimeException("loadValues got a new class " + className);
                }}

            @Override
            public void onCompleteListener() { if (loadable != null) loadable.onCompleteListener(); }

            @Override
            public void onErrorListener() { if (loadable != null) loadable.onErrorListener(); }
        });
    }



    /** helping method that subscribes an observer on single */
    private <T> void subscribe(Single<T> item, DisposableSingleObserver<T> observer){
        item
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

}
