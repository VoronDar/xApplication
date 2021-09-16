package com.astery.xapplication.architecture

import android.content.Context
import com.astery.xapplication.data_source.local.database.AppDatabase
import com.astery.xapplication.data_source.local.database.LocalDataSource
import com.astery.xapplication.data_source.remote.RemoteDataSource
import com.astery.xapplication.repository.Repository

class AppContainer(context: Context) {

    private var database:AppDatabase = AppDatabase.getDatabase(context);
    private var remoteDataSource: RemoteDataSource = RemoteDataSource()
    var localDataSource: LocalDataSource = LocalDataSource(database)
    var repository: Repository = Repository(localDataSource, remoteDataSource)

}