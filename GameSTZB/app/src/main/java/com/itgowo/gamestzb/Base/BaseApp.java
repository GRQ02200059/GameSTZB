package com.itgowo.gamestzb.Base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.itgowo.gamestzb.BuildConfig;
import com.itgowo.gamestzb.Manager.NetManager;
import com.itgowo.gamestzb.Manager.STZBManager;
import com.itgowo.gamestzb.Manager.UserManager;
import com.itgowo.gamestzb.MusicService;
import com.itgowo.itgowolib.itgowo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.xutils.x;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseApp extends Application {
    public static BaseApp app;
    private static STZBManager stzbManager = new STZBManager();
    private int activityCount = 0;
   public static ExecutorService threadPool = Executors.newCachedThreadPool();
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
//        StrictMode.enableDefaults();
//        Utils.setupShortcuts();
        init();
        initData();
    }

    public static STZBManager getStzbManager() {
        return stzbManager;
    }

    private void initData() {
        UserManager.autoLogin();
    }

    private void init() {
        x.Ext.init(app);
        x.Ext.setDebug(BuildConfig.DEBUG);
        itgowo.netTool().initHttpClient(new NetManager.HttpClient());
        UserManager.init(this);


        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, BaseConfig.UMENG_MESSAGE_SECRET);
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        UMConfigure.setEncryptEnabled(true);
        MobclickAgent.setScenarioType(app, MobclickAgent.EScenarioType.E_UM_NORMAL);
        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                BaseConfig.UMENG_DEVICE_TOKEN = deviceToken;
            }

            @Override
            public void onFailure(String s, String s1) {
                MobclickAgent.onEvent(app, "push register errer", s + "   " + s1);
            }
        });
        app.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                PushAgent.getInstance(activity).onAppStart();
                activityCount++;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MobclickAgent.onResume(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                MobclickAgent.onPause(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityCount--;
                if (activityCount == 0) {
                    MusicService.stopMusic(app);
                    MobclickAgent.onKillProcess(app);
                }
            }
        });
        FeedbackAPI.init(app, BaseConfig.ALIYUN_HOTFIX_APPID, BaseConfig.ALIYUN_HOTFIX_APPSECRET);
    }

}
