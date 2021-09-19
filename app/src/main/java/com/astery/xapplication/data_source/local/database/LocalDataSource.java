package com.astery.xapplication.data_source.local.database;

import android.util.Log;

import com.astery.xapplication.data_source.local.database.db_utils.LocalLoadable;
import com.astery.xapplication.data_source.remote.utils.FbUsable;
import com.astery.xapplication.data_source.rx_utils.RxExecutable;
import com.astery.xapplication.data_source.rx_utils.RxTaskManager;
import com.astery.xapplication.pojo.Answer;
import com.astery.xapplication.pojo.Category;
import com.astery.xapplication.pojo.Event;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.pojo.Question;
import com.astery.xapplication.pojo.only_for_db.ItemEntity;
import com.astery.xapplication.pojo.pojo_converters.ItemConverter;
import com.astery.xapplication.pojo.pojo_converters.QuestionConverter;

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


    /** get an item by id (and their advises) - it's may be used after answering the question */
    public void getItem(String id, DisposableSingleObserver<Item> observer){
        subscribe(database.articleDao().getItemById(id), observer);
    }

    /** get all questions (and their answers) for desire or consequence */
    public void getQuestions(String parentId, DisposableSingleObserver<List<Question>> observer){
        subscribe(database.questionDao().getQuestions(parentId), observer);
    }

    public <T> void getValuesWithParent(String parentId, DisposableSingleObserver<T> observer, String className){
        switch (className){
            case "Question":
                subscribe((Single<T>)database.questionDao().getQuestions(parentId), observer);
                break;
            case "Item":
                subscribe((Single<T>)database.articleDao().getItemsByParentId(parentId), observer);
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


    /** load all values */
    public <T> void loadValues(List<T> list, LocalLoadable loadable, String className){
        RxTaskManager.doTask(new RxExecutable() {
            @Override public void doSomething() {
                switch (className){
                    case "Category":
                        database.faqDao().addCategories((List<Category>)list);
                        break;
                    case "Advise":
                        database.questionDao().addAnswers((List<Answer>)list);
                        break;
                    case "Item":
                        database.articleDao().addItems(ItemConverter.getEntities((List<Item>)list));
                        break;
                    case "Event":
                        database.eventDao().addEvents((List<Event>)list);
                        break;
                    default:
                        throw new RuntimeException("loadValues got a new class " + className);
                }}

            @Override
            public void onCompleteListener() { loadable.onCompleteListener(); }

            @Override
            public void onErrorListener() { loadable.onErrorListener(); }
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
