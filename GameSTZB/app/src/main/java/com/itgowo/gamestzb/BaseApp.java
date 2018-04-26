package com.itgowo.gamestzb;

import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.Entity.UserInfo;
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
        initData();
    }

    private void initData() {
        try {
            BaseConfig.userInfo= JSON.parseObject(BaseConfig.getData(BaseConfig.USER_INFO,""), UserInfo.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init() {
        x.Ext.init(app);
        x.Ext.setDebug(BuildConfig.DEBUG);
        itgowo.netTool().initHttpClient(new NetManager.HttpClient());
        UserManager .init(this);
    }
}
