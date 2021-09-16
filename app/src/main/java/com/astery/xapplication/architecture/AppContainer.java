package com.astery.xapplication.architecture;

import com.astery.xapplication.data_source.local.LocalDataSource;
import com.astery.xapplication.data_source.remote.RemoteDataSource;
import com.astery.xapplication.repository.Repository;

/** Container of objects shared across the whole app*/
public class AppContainer {

    private RemoteDataSource remoteDataSource = new RemoteDataSource();
    private LocalDataSource localDataSource = new LocalDataSource();

    public Repository repository = new Repository(localDataSource, remoteDataSource);
}