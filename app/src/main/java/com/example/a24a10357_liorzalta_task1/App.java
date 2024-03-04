package com.example.a24a10357_liorzalta_task1;

import android.app.Application;

import com.example.a24a10357_liorzalta_task1.Utilities.RecordsManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RecordsManager.init(this);

    }
}
