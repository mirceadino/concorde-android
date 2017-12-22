package com.ubb.mirko.concorde;

import android.app.Application;
import android.content.Context;

/**
 * Created by mirko on 22/12/2017.
 */

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
