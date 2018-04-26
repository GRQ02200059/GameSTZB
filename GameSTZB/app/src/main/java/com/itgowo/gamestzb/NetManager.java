package com.itgowo.gamestzb;

import android.util.Log;

import com.itgowo.itgowolib.itgowo;
import com.itgowo.itgowolib.itgowoNetTool;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Map;

public class NetManager {
    private static final String TAG = "NetManager";
//        public static final String ROOTURL = "http://192.168.1.119:1666/GameSTZB";
    public static final String ROOTURL = "http://itgowo.com:1666/GameSTZB";

    public static void getRandomHero(int num, itgowoNetTool.onReceviceDataListener listener) {
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

    public static void basePost(Object requestObject, final itgowoNetTool.onReceviceDataListener listener) {
        String requestJson = "";
        if (requestObject instanceof BaseRequest) {
            requestJson = ((BaseRequest) requestObject).toJson();
        } else if (requestObject instanceof String) {
            requestJson = (String) requestObject;
        } else {
            Log.e(TAG, "basePOST:requestObject is not supposed");
            return;
        }
       itgowo.netTool().Request(ROOTURL,null,requestJson,listener);
    }

     public static class HttpClient implements itgowoNetTool.onRequestDataListener {

        @Override
        public void onRequest(String url, Map head, String body, itgowoNetTool.onRequestDataListener onRequestDataListener, itgowoNetTool.onReceviceDataListener listener) {
            RequestParams requestParams = new RequestParams(url);
            requestParams.setBodyContent(body);
            requestParams.setAsJsonContent(true);
            requestParams.setConnectTimeout(5000);
            requestParams.setReadTimeout(5000);
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    itgowo.netTool().onRequestComplete(body, result, listener);
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
    }
}
