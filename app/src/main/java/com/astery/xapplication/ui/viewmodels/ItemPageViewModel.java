package com.astery.xapplication.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.astery.xapplication.pojo.Item;
import com.astery.xapplication.repository.Repository;
import com.astery.xapplication.repository.listeners.GetItemListener;

import java.util.Objects;

public class ItemPageViewModel extends ViewModel {
    private final MutableLiveData<Item> item;


    public ItemPageViewModel() {
        item = new MutableLiveData<>();
    }

    public MutableLiveData<Item> getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item.setValue(item);
    }

    public Repository getRepository() {
        return repository;
    }

    private Repository repository;

    public void setRepository(Repository repository) {
        this.repository = repository;
    }




    public void loadItem(){
        repository.getItemById(Objects.requireNonNull(item.getValue()).getId(), new GetItemListener<Item>() {
            @Override
            public void getItem(Item item) {
                Log.i("main", item.toString());
                Log.i("main", ItemPageViewModel.this.item.toString());
                ItemPageViewModel.this.item.setValue(item);
            }
            @Override
            public void error() {

            }
        });
    }
}
