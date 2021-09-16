package com.astery.xapplication.data_source.local.database;

import android.util.Log;

import com.astery.xapplication.data_source.local.database.db_utils.LocalLoadable;
import com.astery.xapplication.data_source.rx_utils.RxExecutable;
import com.astery.xapplication.data_source.rx_utils.RxTaskManager;
import com.astery.xapplication.pojo.Advise;
import com.astery.xapplication.pojo.Question;
import com.astery.xapplication.pojo.pojo_converters.QuestionConverter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocalDataSource {

    public final AppDatabase database;

    public LocalDataSource(AppDatabase database) {
        this.database = database;
    }


    /** get all questions for desire or consequence */
    public void getQuestions(String parentId, DisposableSingleObserver<List<Question>> observer){
        database.questionDao().getQuestions(parentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


    public void loadQuestion(Question question, LocalLoadable loadable){
        RxTaskManager.doTask(new RxExecutable() {
            @Override
            public void doSomething() {
                database.questionDao().addQuestion(QuestionConverter.getEntity(question));
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
}
