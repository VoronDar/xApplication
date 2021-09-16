package com.astery.xapplication.architecture

import android.app.Application
import com.astery.xapplication.data_source.local.database.AppDatabase

class App: Application() {

    val container:AppContainer by lazy {
        AppContainer(this)
    };




}