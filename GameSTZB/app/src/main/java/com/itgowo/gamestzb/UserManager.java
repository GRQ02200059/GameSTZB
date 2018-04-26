package com.itgowo.gamestzb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.Entity.QQLoginEntity;
import com.itgowo.gamestzb.Entity.UserInfo;
import com.itgowo.itgowolib.itgowoNetTool;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class UserManager {
    public static final String TENCENT_APP_ID = "1106861720";
    public static final String SCOPE = "all";
    public static Tencent mTencent;
    private static IUiListener mIUiListener;

    public static void init(Context context) {
        mTencent = Tencent.createInstance(TENCENT_APP_ID, context);
    }

    public static void login4Server(UserInfo userInfo, onUserLoginListener listener) {
        BaseRequest<UserInfo> request = new BaseRequest<>();
        request.setData(userInfo).setAction(BaseRequest.REG_USER).setToken(userInfo.getUuid());
        NetManager.basePost(request, new itgowoNetTool.onReceviceDataListener<BaseResponse<UserInfo>>() {

            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<UserInfo> result) {
                BaseConfig.putData(BaseConfig.USER_INFO, result.getData().toJson());
                if (listener != null) {
                    listener.onSuccess(result.getData());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (listener != null) {
                    listener.onError(throwable);
                }
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
                                listener.onError(new Throwable(mError.errorCode+" "+mError.errorMessage+"\r\n"+mError.errorDetail));
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
                        listener.onError(new Throwable(mError.errorCode+" "+mError.errorMessage+"\r\n"+mError.errorDetail));
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


    public static void logout(Activity context) {
        mTencent.logout(context);
    }

    public interface onUserLoginListener {
        void onSuccess(UserInfo userInfo);

        void onCancel();

        void onError(Throwable e);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
    }
}
