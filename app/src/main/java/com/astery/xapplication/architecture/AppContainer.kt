package com.astery.xapplication.architecture

import android.content.Context
import com.astery.xapplication.data_source.local.database.AppDatabase
import com.astery.xapplication.data_source.local.database.LocalDataSource
import com.astery.xapplication.data_source.remote.RemoteDataSource
import com.astery.xapplication.repository.Repository
import com.google.firebase.firestore.FirebaseFirestore

class AppContainer(context: Context) {

    private val lDatabase:AppDatabase = AppDatabase.getDatabase(context);
    private val rDatabase:FirebaseFirestore = FirebaseFirestore.getInstance();

    public var remoteDataSource = RemoteDataSource(rDatabase)
    private var localDataSource = LocalDataSource(lDatabase)

    var repository = Repository(localDataSource, remoteDataSource)

}