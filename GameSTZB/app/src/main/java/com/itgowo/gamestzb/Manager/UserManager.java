package com.itgowo.gamestzb.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.itgowo.gamestzb.Base.BaseApp;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.Entity.BaseRequest;
import com.itgowo.gamestzb.Entity.BaseResponse;
import com.itgowo.gamestzb.Entity.QQLoginEntity;
import com.itgowo.gamestzb.Entity.UserInfo;
import com.itgowo.itgowolib.itgowoNetTool;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.List;

import static com.itgowo.gamestzb.Base.BaseConfig.TENCENTQQ_APP_ID;
import static com.itgowo.gamestzb.Base.BaseConfig.WEIXIN_APP_ID;

public class UserManager {
    public static boolean isLogin = false;

    public static final String SCOPE = "all";
    public static Tencent mTencent;
    private static IUiListener mIUiListener;
    private static IWXAPI mWeixinAPI;
    private static List<onUserStatusListener> onUserStatusListeners = new ArrayList<>();

    public static void init(Context context) {
        mTencent = Tencent.createInstance(TENCENTQQ_APP_ID, context);
        mWeixinAPI = WXAPIFactory.createWXAPI(context, WEIXIN_APP_ID, true);
        mWeixinAPI.registerApp(WEIXIN_APP_ID);
    }

    public static void addUserStatusListener(onUserStatusListener listener) {
        onUserStatusListeners.add(listener);
    }

    public static void removeUserStatusListener(onUserStatusListener listener) {
        onUserStatusListeners.remove(listener);
    }

    public static void refreshUserStatus(boolean isLogin1, UserInfo userInfo) {
        String uuid = "";
        if (BaseConfig.userInfo != null) {
            uuid = BaseConfig.userInfo.getUuid();
        }
        BaseConfig.userInfo = userInfo;
        isLogin = isLogin1;
        if (isLogin1) {
            BaseConfig.putData(BaseConfig.USER_INFO, userInfo.toJson());
            MobclickAgent.onProfileSignIn(String.valueOf(BaseConfig.userInfo.getLogintype()), BaseConfig.userInfo.getUuid());
            PushAgent.getInstance(BaseApp.app).deleteAlias(uuid, "自有id", (isSuccess, message) -> {

            });
            PushAgent.getInstance(BaseApp.app).addAlias(userInfo.getUuid(), "自有id", (isSuccess, message) -> {

            });
        } else {
            PushAgent.getInstance(BaseApp.app).deleteAlias(uuid, "自有id", (isSuccess, message) -> {

            });
            MobclickAgent.onProfileSignOff();
            BaseConfig.putData(BaseConfig.USER_INFO, "");
        }
        for (onUserStatusListener onUserStatusListener : onUserStatusListeners) {
            if (onUserStatusListener == null) {
                onUserStatusListeners.remove(onUserStatusListener);
            } else {
                onUserStatusListener.onChanged();
            }
        }
    }

    public static void login4Server(UserInfo userInfo, onUserLoginListener listener) {
        BaseRequest<UserInfo> request = new BaseRequest<>();
        request.setData(userInfo).setAction(BaseRequest.REG_USER).setToken(userInfo.getUuid());
        NetManager.basePost(request, new itgowoNetTool.onReceviceDataListener<BaseResponse<UserInfo>>() {

            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<UserInfo> result) {
                refreshUserStatus(true, result.getData());
                if (listener != null) {
                    listener.onSuccess(result.getData());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (listener != null) {
                    listener.onError(throwable);
                }
                logout();
            }
        });
    }

    public static void login4QQ(Activity context, onUserLoginListener listener) {
        if (!mTencent.isSessionValid()) {
            mIUiListener = new IUiListener() {
                @Override
                public void onComplete(Object mO) {
                    QQLoginEntity mQQLoginEntity = JSON.parseObject(mO.toString(), QQLoginEntity.class);
                    mTencent.setAccessToken(mQQLoginEntity.getAccess_token(), String.valueOf(mQQLoginEntity.getExpires_in()));
                    mTencent.setOpenId(mQQLoginEntity.getOpenid());
                    com.tencent.connect.UserInfo info = new com.tencent.connect.UserInfo(context, mTencent.getQQToken());
                    mIUiListener = new IUiListener() {
                        @Override
                        public void onComplete(Object mO) {
                            UserInfo userInfo = UserInfo.covertUserInfoFormQQ(mO.toString());
                            login4Server(userInfo, listener);
                        }

                        @Override
                        public void onError(UiError mError) {
                            if (listener != null) {
                                listener.onError(new Throwable(mError.errorCode + " " + mError.errorMessage + "\r\n" + mError.errorDetail));
                            }
                        }

                        @Override
                        public void onCancel() {
                            if (listener != null) {
                                listener.onCancel();
                            }
                        }
                    };
                    info.getUserInfo(mIUiListener);
                }

                @Override
                public void onError(UiError mError) {
                    if (listener != null) {
                        listener.onError(new Throwable(mError.errorCode + " " + mError.errorMessage + "\r\n" + mError.errorDetail));
                    }
                }

                @Override
                public void onCancel() {
                    if (listener != null) {
                        listener.onCancel();
                    }
                }
            };
            mTencent.login(context, SCOPE, mIUiListener);
        }
    }


    public static void logout() {
        mTencent.logout(null);
        refreshUserStatus(false, null);

    }

    public static void autoLogin() {
        try {
            UserInfo info = JSON.parseObject(BaseConfig.getData(BaseConfig.USER_INFO, ""), UserInfo.class);
            if (info != null) {
                login4Server(info, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface onUserLoginListener {
        void onSuccess(UserInfo userInfo);

        void onCancel();

        void onError(Throwable e);
    }

    public interface onUserStatusListener {
        void onChanged();
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
    }


}
