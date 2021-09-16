package com.astery.xapplication.data_source.local.database;

import com.astery.xapplication.data_source.local.database.db_utils.LocalLoadable;
import com.astery.xapplication.data_source.rx_utils.RxExecutable;
import com.astery.xapplication.data_source.rx_utils.RxTaskManager;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.pojo.Question;
import com.astery.xapplication.pojo.pojo_converters.ItemConverter;
import com.astery.xapplication.pojo.pojo_converters.QuestionConverter;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocalDataSource {

    public final AppDatabase database;

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

    /** load questions (and theirs answers) */
    public void loadQuestion(Question question, LocalLoadable loadable){
        RxTaskManager.doTask(new RxExecutable() {
            @Override
            public void doSomething() {
                database.questionDao().addQuestion(QuestionConverter.getEntity(question));
                database.questionDao().addAnswers(question.getAnswers());
            }
            @Override
            public void onCompleteListener() {
                loadable.onCompleteListener();
            }
            @Override
            public void onErrorListener() {
                loadable.onErrorListener();
            }
        });
    }


    /** load questions (and theirs answers) */
    public void loadItem(Item item, LocalLoadable loadable){
        RxTaskManager.doTask(new RxExecutable() {
            @Override
            public void doSomething() {
                database.articleDao().addItem(ItemConverter.getEntity(item));
                database.articleDao().addAdvises(item.getAdvises());
            }
            @Override
            public void onCompleteListener() {
                loadable.onCompleteListener();
            }
            @Override
            public void onErrorListener() {
                loadable.onErrorListener();
            }
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
