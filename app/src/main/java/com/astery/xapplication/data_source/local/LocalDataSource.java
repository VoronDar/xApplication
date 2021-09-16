package com.astery.xapplication.data_source.local;

import com.astery.xapplication.data_source.local.database.AppDatabase;

public class LocalDataSource {

    private AppDatabase database;

    public LocalDataSource(AppDatabase database) {
        this.database = database;
    }
}
