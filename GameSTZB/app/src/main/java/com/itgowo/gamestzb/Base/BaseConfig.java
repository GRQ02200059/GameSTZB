package com.itgowo.gamestzb.Base;

import android.content.Context;
import android.os.Environment;

import com.itgowo.gamestzb.Entity.UpdateVersion;
import com.itgowo.gamestzb.Entity.UserInfo;

import java.io.File;

public class BaseConfig {
    public static final String TENCENTQQ_APP_ID = "1106861720";
    public static final String WEIXIN_APP_ID = "wxdbc93bf3e636758c";
    public static final String WEIXIN_APP_SECRET = "f8720aa2f1c9adb3ecae053aebea1e9f";
    public static final String UMENG_APPKEY = "5af037c4f43e4819780001f4";
    public static final String UMENG_MESSAGE_SECRET = "174c980990d7e8bb5b8a517b60ea99f7";
    public static final String ALIYUN_HOTFIX_APPID = "24916357";
    public static final String ALIYUN_HOTFIX_APPSECRET = "e0ed13e83351bfa0a38adc910dfd0487";
    public static final String ALIYUN_HOTFIX_APPRSA = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCSzHSAfxnXPctkgudLDBI/j3CSqobJM1IBzTP/M7u2P0vuQLpQX1J8FSB4SQPAYtOLGQage3/3AA1fMtBYsZhPqXNn0O52Zba0uDF8bNz6WoWNHKm4pGChS+TI7c25SYBwHseeTA4WmKdsWpA6ffm+7J6UfmDnjB6ZCirFbio0mQPo7DHlfkYoJpVwLioVYe2vjKSKN42c/28uCR2D2+OUIooF1wP6Z1w4Iijh0grdXSEAfhmbWNr2MxPrvsQFfzSAr2yeBLGUp70xPJDcCPeg7tWNI0xmm5ZnzIUH/goXa9HE12LBZOOzXoYVjy/lrzbFwZRa0Ldbl8n1cQ1B5qg7AgMBAAECggEAHAqwSod9AS/NtZKH6j8REVEuOOYYP0DtbmirgRl5xxOKRqXYsVe0Iv3KKRARhmOac1zBdlCBHkbFRyUmxXqPVNBSukcf9j/xyc3RrioBgFVgY5dRGHNV/hnIR1Wd0cInpcNLcIKowkpA2SLnoDTGS2FZ3ZXSnwiWI7fcecBUkZ1LH2B3XHQUrwpHw5pQd6UaKgP3KTFyyyfjkUt0S7tz0yEBFNPr7KcmhZDEn3AVfaQm7V6/mW9R3A5UF/jsBTV9RpbgeSb1E/FoR56KfZgrEQbL8F22h6ymSBj3L3YbMaoDH+CfNCgwEAQtogszY26IWshvaJgZXlM8FVqqT/wcEQKBgQDEQCuXaKddeMtU9SgICzSEJV2IB0JSqqZXohG6W9uYS9Sf4qmiuXRLbAfXs1401SbewWE2+gARqoTnvuHEy1T0/cPecOx6420PdNQABZtI8pqelG0D3WHxaVbgukPh16iaRcSlv6J8QU52zAaGUWgYW6Uvf/QWbjHd0NCGigiD+QKBgQC/fftMFpC+8j7fbrEQZKb269MwnA6GnaJ35fYrNEaQy/+2sP2OEOSaytl4QU/4Wdnw1CazjZhGMJ9iivLLsXla5BHgGAs5T9IzWUFc6kAb0BfJ9Hx59AF45A4P8CBjbtTuC87OKwRt3MKgeFMJWVh7+3dTjfapLhYPoV7fIEVy0wKBgQCvLai7qotbAGxgFcX6CCaEnuXyeqhVKkb5io3QkBZgx6+wWJ+bxXdtq60EefPprSrZRD0G2fodytyAEP9uryHS3oJsmNg+iavcVCi5LEgEwaoLS+pTvNWkrKcESsA1ZE/4BbcXTQw5ZgIoQM7CLwCAXa4BzHGRZCPXIIJC1gvr4QKBgCqCuEgC9m09cBHADBTck2hboA25h0wBMLE3Xvfmagf5EbKoBhIE1As9HxwgO7WhlM1u+4x+0aA7aRbCS/ZGVu+DlChQjiVSABMgLG+JHWRPRQizevLD5ZPF5Q1KvJX2Y9TY0Ddau2f8S0S+kYiQyiD0CWCGMo0KSyF7LImSK3i7AoGBAKy0GakIwHNpOEWhFjSm+NoOqq8IbyFPbg8mAlktZ1y6Wg4LmLjnyhpxTvMpGXZJL4O08JbjnC0f7S8vBbxBkUbO2yG7Uxv+4jrptWZtdLtvzO7i3MwTMsW3S3SNIHPzTkgSj1nkXAFybwN+x5SCSsp899z0bLpA8SSX1JunpI4G";
    public static String UMENG_DEVICE_TOKEN = "";
    public static UserInfo userInfo;
    public static final String SP_NAME_APPCONFIG = "AppConfig";
    public static final String USER_INFO = "UserInfo";
    public static final String USER_UUID = "UserUUID";
    public static final String USER_ISPLAYVIDEO = "isPlayVideo";
    public static final String USER_ISPLAYMUSIC = "isPlayMusic";
    public static final String USER_MONEY = "userMoney";
    public static final String USER_HEROLIST = "userHeroList";
    public static String SP_NAME_USERINFO = "UserInfo";
    public static UpdateVersion updateInfo  ;
    public static File getAppFile(String name){
        File file=new File( Environment.getExternalStorageDirectory(),"itgowo/Game/stzb");
        if (!file.exists()){
            file.mkdirs();
        }
        file=new File(file,name);
        return file;
    }
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
    public static void putUserData(String key, long data) {
        if (userInfo == null || userInfo.getUuid() == null || userInfo.getUuid().length() < 10) {
            return;
        }
        BaseApp.app.getSharedPreferences(SP_NAME_USERINFO + userInfo.getUuid(), Context.MODE_PRIVATE)
                .edit().putLong(key, data).apply();
    }
    public static String getUserData(String key, String defaultValue) {
        if (userInfo == null || userInfo.getUuid() == null || userInfo.getUuid().length() < 10) {
            return defaultValue;
        }
        return BaseApp.app.getSharedPreferences(SP_NAME_USERINFO + userInfo.getUuid(), Context.MODE_PRIVATE).getString(key, defaultValue);
    }
    public static Long getUserData(String key, long defaultValue) {
        if (userInfo == null || userInfo.getUuid() == null || userInfo.getUuid().length() < 10) {
            return defaultValue;
        }
        return BaseApp.app.getSharedPreferences(SP_NAME_USERINFO + userInfo.getUuid(), Context.MODE_PRIVATE).getLong(key, defaultValue);
    }
    public static void putData(String key, boolean data) {
        BaseApp.app.getSharedPreferences(SP_NAME_APPCONFIG, Context.MODE_PRIVATE).edit().putBoolean(key, data).apply();
    }

    public static boolean getData(String key, boolean defaultValue) {
        return BaseApp.app.getSharedPreferences(SP_NAME_APPCONFIG, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }
}
