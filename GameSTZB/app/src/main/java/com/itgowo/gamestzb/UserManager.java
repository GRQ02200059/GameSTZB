package com.itgowo.gamestzb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.itgowo.gamestzb.Entity.QQLoginEntity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class UserManager {
    public static com.itgowo.gamestzb.Entity.QQLoginEntity QQLoginEntity;
    public static final String TENCENT_APP_ID = "1106861720";
    public static final String SCOPE = "all";
    public static Tencent mTencent;
    private static IUiListener mIUiListener;

    public static void init(Context context) {
        mTencent = Tencent.createInstance(TENCENT_APP_ID, context);
    }

    public static void login(Activity context, onUserLoginListener listener) {
        if (!mTencent.isSessionValid()) {
            mIUiListener = new IUiListener() {
                @Override
                public void onComplete(Object mO) {
                    QQLoginEntity mQQLoginEntity = JSON.parseObject(mO.toString(), QQLoginEntity.class);
                    mTencent.setAccessToken(mQQLoginEntity.getAccess_token(), String.valueOf(mQQLoginEntity.getExpires_in()));
                    mTencent.setOpenId(mQQLoginEntity.getOpenid());
                    UserInfo info = new UserInfo(context, mTencent.getQQToken());
                    mIUiListener = new IUiListener() {
                        @Override
                        public void onComplete(Object mO) {
                            QQLoginEntity = JSON.parseObject(mO.toString(), QQLoginEntity.class);
                            if (listener != null) {
                                listener.onSuccess(QQLoginEntity);
                            }
                        }

                        @Override
                        public void onError(UiError mError) {
                            if (listener != null) {
                                listener.onError(mError);
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
                        listener.onError(mError);
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
        void onSuccess(QQLoginEntity mQQLoginEntity);

        void onCancel();

        void onError(Object e);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
    }
}
