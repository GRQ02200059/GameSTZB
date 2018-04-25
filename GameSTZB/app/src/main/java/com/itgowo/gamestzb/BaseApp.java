package com.itgowo.gamestzb;

import android.app.Application;

import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.itgowolib.itgowo;

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
        itgowo.netTool().initHttpClient(new NetManager.HttpClient());
        UserManager .init(this);
    }
}
