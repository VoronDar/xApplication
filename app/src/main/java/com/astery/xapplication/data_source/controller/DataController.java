package com.astery.xapplication.data_source.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.astery.xapplication.R;
import com.astery.xapplication.data_source.local.database.LocalDataSource;
import com.astery.xapplication.data_source.local.database.db_utils.LocalLoadable;
import com.astery.xapplication.data_source.remote.RemoteDataSource;
import com.astery.xapplication.repository.listeners.GetItemListener;
import com.astery.xapplication.repository.listeners.JobListener;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;

public class DataController {
    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;
    private final Context context;

    public DataController(LocalDataSource localDataSource, RemoteDataSource remoteDataSource, Context context) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        this.context = context;
    }

    /** load from remote and push to local */
    public <T> void loadValuesFromRemote(JobListener listener, Class<T> className){
        if (connected()){
            Log.i("main", "get from remote");
            remoteDataSource.getValues(new GetItemListener<List<T>>() {
                @Override public void getItem(List<T> list) { pushDataToLocal(list, listener,className); }
                @Override public void error() { listener.done(false); }
            }, className);
        } else{ listener.done(false); }
    }

    /** load from remote, push to local, if it is not in remote - get from local, than get to the client*/
    public <T> void getValuesFromRemoteById(GetItemListener<T> listener, String id, Class<T> className) {
        if (connected()) {
            remoteDataSource.getDataById(new GetItemListener<T>() {
                @Override public void getItem(T item) {
                    if (item != null) {
                        localDataSource.loadValue(item, null, className.getSimpleName());
                        listener.getItem(item);
                    } else { getValuesFromLocalById(listener, id, className); }
                }
                @Override public void error() { getValuesFromLocalById(listener, id, className); }
                }, className.getSimpleName(), id, className);
        } else {
            listener.error();
            getValuesFromLocalById(listener, id, className);
        }
    }


    /** load from local, if it is not in local - get from remote, then push to local... */
    public <T> void getValuesFromLocalById(GetItemListener<T> listener, String id, Class<T> className) {
        localDataSource.getValuesById(new DisposableSingleObserver<T>() {
            @Override public void onSuccess(@NotNull T t) { listener.getItem(t); }
            @Override public void onError(@NotNull Throwable e) {
                if (connected()) getValuesFromRemoteById(listener, id, className);
                else listener.error(); }
        }, id, className.getSimpleName());
    }



    /** load from local, if it is not in local - get from remote, then push to local... */
    public <M> void getValuesFromLocalByParent(GetItemListener<List<M>> listener, String parentId, Class<M> className) {
        localDataSource.getValuesWithParent(parentId, new DisposableSingleObserver<List<M>>() {
            @Override public void onSuccess(@NotNull List<M> t) { listener.getItem(t); }
            @Override public void onError(@NotNull Throwable e) {
                if (connected()) getValuesFromRemoteByParent(listener, parentId, className);
                else listener.error(); }
        }, className.getSimpleName());
    }



    /** load from remote, push to local, if it is not in remote - get from local, than get to the client*/
    public <M> void getValuesFromRemoteByParent(GetItemListener<List<M>> listener, String parentId, Class<M> className) {
        if (connected()) {
            remoteDataSource.getValuesWithParent(parentId, new GetItemListener<List<M>>() {
                @Override public void getItem(List<M> list) {
                    localDataSource.loadValues(list, null, className.getSimpleName());
                    listener.getItem(list); }
                @Override public void error() { getValuesFromLocalByParent(listener, parentId, className); }
            }, className);
        }
    }




    /** load from local and get to the client */
    public <T> void getValuesFromLocal(GetItemListener<List<T>> listener, Class<T> className){
        Log.i("main", "get from local");
        localDataSource.getValues(new DisposableSingleObserver<List<T>>() {
            @Override public void onSuccess(@NotNull List<T> things) { listener.getItem(things); }
            @Override public void onError(@NotNull Throwable e) { Log.i("main", e.getMessage());listener.error(); }
        }, className.getSimpleName());

    }


    /** load from local and get to the client */
    public <T> void getValuesFromLocalByDay(GetItemListener<List<T>> listener, Date date, Class<T> className){
        Log.i("main", "get from local");
        localDataSource.getValuesByDate(new DisposableSingleObserver<List<T>>() {
            @Override public void onSuccess(@NotNull List<T> things) { listener.getItem(things); }
            @Override public void onError(@NotNull Throwable e) { Log.i("main", e.getMessage());listener.error(); }
        }, date, className.getSimpleName());

    }

    /** put data in local */
    public <T, M> void pushDataToLocal(T list, JobListener listener, Class<M> className){
        localDataSource.loadValues(list, new LocalLoadable() {
            @Override public void onCompleteListener() {
                if (listener != null)
                listener.done(true); }

            @Override public void onErrorListener() {
                if (listener != null) listener.done(false); }
        }, className.getSimpleName());
    }


    /** return true if the device connected to the internet */
    public boolean connected(){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
    }




}
