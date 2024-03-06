package com.example.a24a10357_liorzalta_task1;

import android.app.Application;
import android.util.Log;

import com.example.a24a10357_liorzalta_task1.Model.Record;
import com.example.a24a10357_liorzalta_task1.Utilities.RecordsUtil;

import java.util.ArrayList;
import java.util.Collections;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RecordsUtil.init(this);
    }
}
