package com.astery.xapplication.repository;

import com.astery.xapplication.data_source.local.database.LocalDataSource;
import com.astery.xapplication.data_source.remote.RemoteDataSource;

public class Repository {
    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;

    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }
}
