package com.astery.xapplication.data_source.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.astery.xapplication.data_source.local.database.LocalDataSource;
import com.astery.xapplication.data_source.local.database.db_utils.LocalLoadable;
import com.astery.xapplication.data_source.remote.RemoteDataSource;
import com.astery.xapplication.data_source.remote.listeners.RemoteListGettable;
import com.astery.xapplication.data_source.remote.listeners.RemoteOneGettable;
import com.astery.xapplication.pojo.EventTemplate;
import com.astery.xapplication.repository.listeners.GetItemListener;
import com.astery.xapplication.repository.listeners.JobListener;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;

@SuppressWarnings("unchecked")
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
            remoteDataSource.getValues(new RemoteListGettable<T>() {
                @Override public Class<T> getObjectClass() { return className; }
                @Override public void getResult(List<T> list) { pushDataToLocal(list, listener,className); }
                @Override public void getError(String message) { listener.done(false); }
            });
        } else{ listener.done(false); }
    }

    /** load from remote, push to local, get to the client*/
    public <T> void getValuesFromRemote(GetItemListener<T> listener, Class<T> className){
        if (connected()){
            remoteDataSource.getValues(new RemoteListGettable<T>() {
                @Override public Class<T> getObjectClass() { return className; }
                @Override public void getResult(List<T> list) { pushDataToLocal(list, success -> listener.getItem((T) list) ,className); }
                @Override public void getError(String message) { listener.error();}
            });
        } else{ listener.error();}
    }

    /** load from remote, push to local, get to the client*/
    public <T> void getValuesFromRemoteById(RemoteOneGettable<T> listener, String id){
        if (connected()) {
            remoteDataSource.getDataById(listener, listener.getClass().getSimpleName(), id);
        } else
            listener.getError(null);
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
    public <T> void pushDataToLocal(List<T> list, JobListener listener, Class<T> className){
        Log.i("main", "push to local");
        localDataSource.loadValues(list, new LocalLoadable() {
            @Override public void onCompleteListener() { listener.done(true); }

            @Override public void onErrorListener() { listener.done(false); }
        }, className.getSimpleName());
    }


    /** return true if the device connected to the internet */
    public boolean connected(){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
    }




}
