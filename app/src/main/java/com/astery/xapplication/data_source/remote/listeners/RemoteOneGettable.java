package com.astery.xapplication.data_source.remote.listeners;

import java.util.List;

public interface RemoteOneGettable<T> {
        Class<T> getObjectClass();
        void getResult(T item);
        void getError(String message);
}
