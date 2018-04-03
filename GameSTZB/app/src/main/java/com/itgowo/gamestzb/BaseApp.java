package com.itgowo.gamestzb;

import android.app.Application;

public class BaseApp extends Application {
    public static BaseApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Utils.setupShortcuts();

    }
}
