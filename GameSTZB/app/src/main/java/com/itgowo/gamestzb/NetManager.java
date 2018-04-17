package com.itgowo.gamestzb;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class NetManager {
    private static final String TAG = "NetManager";
    public static final String ROOTURL = "http://10.0.4.34:1666/GameSTZB";
//    public static final String ROOTURL = "http://itgowo.com:1666/GameSTZB";

    public static void getRandomHero(int num, onNetResultListener listener) {
        BaseRequest request = new BaseRequest();
        request.setAction(BaseRequest.GET_RANDOM_HERO).setData(new BaseRequest.getRandomHeroEntity().setRandomNum(num)).initToken();
        basePost(request, listener);
    }

    public static void download(final File file, final String url) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(file.getAbsolutePath());
        requestParams.setMultipart(true);
        x.http().get(requestParams, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                System.out.println("download:" + file.getName() + "   " + url);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                System.out.println("downloaderror:" + file.getName() + "   " + url);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static void basePost(Object requestObject, final onNetResultListener listener) {
        String requestJson = "";
        if (requestObject instanceof BaseRequest) {
            requestJson = ((BaseRequest) requestObject).toJson();
        } else if (requestObject instanceof String) {
            requestJson = (String) requestObject;
        } else {
            Log.e(TAG, "basePOST:requestObject is not supposed");
            return;
        }
        RequestParams requestParams = new RequestParams(ROOTURL);
        requestParams.setBodyContent(requestJson);
        requestParams.setAsJsonContent(true);
        requestParams.setConnectTimeout(5000);
        requestParams.setReadTimeout(5000);
        final String finalRequestJson = requestJson;
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (listener != null) {
                    Type[] types = listener.getClass().getGenericInterfaces();
                    if (types == null || types.length == 0) {
                        listener.onResult(finalRequestJson, result, null);
                        return;
                    }
                    ParameterizedType type = (ParameterizedType) types[0];
                    types = type.getActualTypeArguments();
                    if (types == null || types.length == 0) {
                        listener.onResult(finalRequestJson, result, null);
                        return;
                    }
                    type = (ParameterizedType) types[0];
                    listener.onResult(finalRequestJson, result, JSON.parseObject(result, type));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (x.isDebug()) {
                    ex.printStackTrace();
                }
                if (listener != null) {
                    listener.onError(ex);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public interface onNetResultListener<resultType> {
        void onResult(String requestStr, String responseStr, resultType result);

        void onError(Throwable throwable);
    }
}
