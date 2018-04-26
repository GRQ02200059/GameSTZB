package com.itgowo.gamestzb.Base;

import android.content.Context;

import com.itgowo.gamestzb.BaseApp;
import com.itgowo.gamestzb.Entity.UserInfo;

public class BaseConfig {
    public static UserInfo userInfo;
    public static final String SP_NAME_APPCONFIG = "AppConfig";
    public static final String USER_INFO = "UserInfo";
    public static final String USER_ISPLAYVIDEO = "isPlayVideo";
    public static final String USER_ISPLAYMUSIC = "isPlayMusic";
    public static String SP_NAME_USERINFO = "UserInfo";

    public static void putData(String key, String data) {
        BaseApp.app.getSharedPreferences(SP_NAME_APPCONFIG, Context.MODE_PRIVATE).edit().putString(key, data).apply();
    }

    public static void RefreshUserInfo() {
        if (userInfo == null || userInfo.getUuid() == null || userInfo.getUuid().length() < 10) {
            return;
        }
        SP_NAME_USERINFO = "UserInfo_" + userInfo.getUuid();
    }

    public static String getData(String key, String defaultValue) {
        return BaseApp.app.getSharedPreferences(SP_NAME_APPCONFIG, Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public static void putUserData(String key, String data) {
        if (userInfo == null || userInfo.getUuid() == null || userInfo.getUuid().length() < 10) {
            return;
        }
        BaseApp.app.getSharedPreferences(SP_NAME_USERINFO + userInfo.getUuid(), Context.MODE_PRIVATE)
                .edit().putString(key, data).apply();
    }

    public static String getUserData(String key, String defaultValue) {
        if (userInfo == null || userInfo.getUuid() == null || userInfo.getUuid().length() < 10) {
            return defaultValue;
        }
        return BaseApp.app.getSharedPreferences(SP_NAME_USERINFO + userInfo.getUuid(), Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public static void putData(String key, boolean data) {
        BaseApp.app.getSharedPreferences(SP_NAME_APPCONFIG, Context.MODE_PRIVATE).edit().putBoolean(key, data).apply();
    }

    public static boolean getData(String key, boolean defaultValue) {
        return BaseApp.app.getSharedPreferences(SP_NAME_APPCONFIG, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }
}
