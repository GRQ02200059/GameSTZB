package com.itgowo.gamestzb;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.WindowManager;

import org.xutils.x;

public class BaseApp extends Application {
    public static BaseApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Utils.setupShortcuts();
        init();
    }

    private void init() {
        x.Ext.init(app);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
