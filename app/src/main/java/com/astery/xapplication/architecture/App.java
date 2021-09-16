package com.astery.xapplication.architecture;

import android.app.Application;

public class App extends Application {
    public AppContainer container = new AppContainer(getApplicationContext());
}