package com.astery.xapplication.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.astery.xapplication.data_source.controller.DataController;
import com.astery.xapplication.data_source.local.database.LocalDataSource;
import com.astery.xapplication.data_source.remote.RemoteDataSource;
import com.astery.xapplication.pojo.Category;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.repository.listeners.GetItemListener;
import com.astery.xapplication.repository.listeners.JobListener;

import java.util.List;

public class Repository {
    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;
    private final DataController dataController;

    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource, DataController dataController) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
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



    /** items that allow to do job with data one after one and in the end - notify parent*/
    static class Command{
        private Command next;
        private JobListener listener;
        private JobListener parentListener;
        private final CommandTask task;

        public Command(CommandTask task) {
            this.task = task;
        }

        public void work(){
            task.work(listener);
        }

        public Command getNext() {
            return next;
        }

        public void setNext(@NonNull Command next) {
            if (this.next == null) {
                this.next = next;
                this.next.setParentListener(parentListener);
            } else{
                this.next.setNext(next);
            }
        }

        public void setParentListener(JobListener parentListener) {
            this.parentListener = parentListener;

            this.listener = success -> {
                if (success) {
                    if (next == null)
                        parentListener.done(true);
                    else {
                        Log.i("main","Command task done work");
                        next.work();
                    }
                }
                else
                    parentListener.done(false);
            };
            if (next != null)
                this.next.setParentListener(parentListener);
        }
    }

    private interface CommandTask{
        void work(JobListener listener);
    }

}
