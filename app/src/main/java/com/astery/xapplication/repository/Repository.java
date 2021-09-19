package com.astery.xapplication.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.astery.xapplication.data_source.controller.DataController;
import com.astery.xapplication.data_source.local.database.LocalDataSource;
import com.astery.xapplication.data_source.remote.RemoteDataSource;
import com.astery.xapplication.pojo.Category;
import com.astery.xapplication.pojo.Event;
import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.repository.listeners.GetItemListener;
import com.astery.xapplication.repository.listeners.JobListener;
import com.google.firebase.Timestamp;

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
