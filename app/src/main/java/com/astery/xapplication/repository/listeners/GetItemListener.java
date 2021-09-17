package com.astery.xapplication.repository.listeners;

public interface GetItemListener <T>{
    void getItem(T item);
    void error();
}
