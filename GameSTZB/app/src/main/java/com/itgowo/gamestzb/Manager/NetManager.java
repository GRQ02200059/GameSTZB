package com.itgowo.gamestzb.Manager;

import android.util.Log;

import com.itgowo.gamestzb.Entity.BaseRequest;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.Entity.UpdateVersion;
import com.itgowo.itgowolib.itgowo;
import com.itgowo.itgowolib.itgowoNetTool;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Map;

public class NetManager {
    private static final String TAG = "NetManager";
    //    public static final String ROOTURL = "http://10.0.4.44:1666/GameSTZB";
    public static final String ROOTURL = "http://itgowo.com:1666/GameSTZB";
    public static final String ROOTURL_UPDATEVERSION = "http://itgowo.com:1888/Version";
    public static final String ROOTURL_DOWNLOAD_HERO_IMAGE = "https://itgowo.oss-cn-qingdao.aliyuncs.com/game/app/hero/hero_%s.jpg";
    //    public static final String ROOTURL_DOWNLOAD_HERO_IMAGE = "https://stzb.res.netease.com/pc/qt/20170323200251/data/role/card_%s.jpg";
    public static final String ROOTURL_HERO_INFO = "https://app.gamer.163.com//game-db/g10/hero/";

    public static void getRandomHero(int num, itgowoNetTool.onReceviceDataListener listener) {
        BaseRequest request = new BaseRequest();
        request.setAction(BaseRequest.GET_RANDOM_HERO).setData(new BaseRequest.DataEntity().setRandomNum(num)).initToken();
        basePost(request, listener);
    }

    public static void getHeroGuess(itgowoNetTool.onReceviceDataListener listener) {
        basePost(new BaseRequest().setAction(BaseRequest.GET_HERO_GUESS), listener);
    }

    public static void postHeroGuess(HeroEntity heroEntity, itgowoNetTool.onReceviceDataListener listener) {
        basePost(new BaseRequest().setAction(BaseRequest.POST_HERO_GUESS).setData(heroEntity), listener);
    }

    public static void getHeroDetail(int id, itgowoNetTool.onReceviceDataListener listener) {
        BaseRequest request = new BaseRequest();
        request.setAction(BaseRequest.GET_HERO_DETAIL).setData(new BaseRequest.DataEntity().setId(id)).initToken();
        basePost(request, listener);
    }

    public static void getUpdateInfo(itgowoNetTool.onReceviceDataListener listener) {
        BaseRequest request = new BaseRequest();
        request.setAction(UpdateVersion.GET_UPDATE_VERSION).setFlag(UpdateVersion.GET_UPDATE_VERSION_FLAG).initToken();
        basePost(ROOTURL_UPDATEVERSION, request, listener);
    }

    public static void getHeroList(itgowoNetTool.onReceviceDataListener listener) {
        BaseRequest request = new BaseRequest();
        request.setAction(BaseRequest.GET_HERO_LIST).initToken();
        basePost(request, listener);
    }

    public static void getHeroListWithUser(itgowoNetTool.onReceviceDataListener listener) {
        BaseRequest request = new BaseRequest();
        request.setAction(BaseRequest.GET_HERO_LIST_WITH_USER).initToken();
        basePost(request, listener);
    }

    public static void getHeroListWithUserAndAttr(itgowoNetTool.onReceviceDataListener listener) {
        BaseRequest request = new BaseRequest();
        request.setAction(BaseRequest.GET_HERO_LIST_WITH_USER_AND_ATTR).initToken();
        basePost(request, listener);
    }

    public static void getHeroDetailList(itgowoNetTool.onReceviceDataListener listener) {
        BaseRequest request = new BaseRequest();
        request.setAction(BaseRequest.GET_HERO_DETAIL_LIST).initToken();
        basePost(request, listener);
    }

    public static void download(final File file, final String url) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(file.getAbsolutePath());
        requestParams.setMultipart(true);
        try {
            x.http().getSync(requestParams, File.class);
            LogUtil.d("download:" + file.getName() + "   " + url);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public static void basePost(Object requestObject, final itgowoNetTool.onReceviceDataListener listener) {
        basePost(ROOTURL, requestObject, listener);
    }

    public static void basePost(String rooturl, Object requestObject, final itgowoNetTool.onReceviceDataListener listener) {
        String requestJson = "";
        if (requestObject instanceof BaseRequest) {
            requestJson = ((BaseRequest) requestObject).toJson();
        } else if (requestObject instanceof String) {
            requestJson = (String) requestObject;
        } else {
            Log.e(TAG, "basePOST:requestObject is not supposed");
            return;
        }
        itgowo.netTool().Request(rooturl, null, requestJson, listener);
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
