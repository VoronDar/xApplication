package com.astery.xapplication.data_source.remote.listeners;

import java.util.List;

public interface RemoteListGettable<T> {
        Class<T> getObjectClass();
        void getResult(List<T> list);
        void getError(String message);
}
