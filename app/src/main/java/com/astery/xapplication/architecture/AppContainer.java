package com.astery.xapplication.architecture;

import android.content.Context;

import androidx.room.Room;

import com.astery.xapplication.data_source.local.LocalDataSource;
import com.astery.xapplication.data_source.local.database.AppDatabase;
import com.astery.xapplication.data_source.remote.RemoteDataSource;
import com.astery.xapplication.repository.Repository;

/** Container of objects shared across the whole app*/
public class AppContainer {

    private final AppDatabase database;

    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;

    public final Repository repository;

    public AppContainer(Context context){

         database = Room.databaseBuilder(context, AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();

         remoteDataSource = new RemoteDataSource();
         localDataSource = new LocalDataSource(database);

         repository = new Repository(localDataSource, remoteDataSource);
    }
}