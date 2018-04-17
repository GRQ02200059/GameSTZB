package com.itgowo.gamestzb;

import com.itgowo.gamestzb.Entity.HeroEntity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class STZBManager {
    //    public static final String ROOTURL = "http://itgowo.com:666/GameSTZB";
    public static final String ROOTURL = "http://10.0.4.33:666/GameSTZB";
    private List<HeroEntity> totalHeroList = new ArrayList<>();
    private List<HeroEntity> heroList5 = new ArrayList<>();
    private List<HeroEntity> heroList4 = new ArrayList<>();
    private List<HeroEntity> heroList3 = new ArrayList<>();
    private List<HeroEntity> heroList2 = new ArrayList<>();
    private List<HeroEntity> heroList1 = new ArrayList<>();

    public STZBManager setTotalHeroList(List<HeroEntity> totalHeroList) {
        this.totalHeroList = totalHeroList;
        initData();
        return this;
    }

    public List<HeroEntity> getTotalHeroList() {
        return totalHeroList;
    }

    public List<HeroEntity> getHeroList5() {
        return heroList5;
    }

    public List<HeroEntity> getHeroList4() {
        return heroList4;
    }

    public List<HeroEntity> getHeroList3() {
        return heroList3;
    }

    public List<HeroEntity> getHeroList2() {
        return heroList2;
    }

    public List<HeroEntity> getHeroList1() {
        return heroList1;
    }

    public void initData() {
        for (int i = 0; i < totalHeroList.size(); i++) {
            HeroEntity entity = totalHeroList.get(i);
            switch (entity.getQuality()) {
                case 5:
                    heroList5.add(entity);
                    break;
                case 4:
                    heroList4.add(entity);
                    break;
                case 3:
                    heroList3.add(entity);
                    break;
                case 2:
                    heroList2.add(entity);
                    break;
                case 1:
                    heroList1.add(entity);
                    break;
            }
        }
    }

    public void loadNetData(boolean isCustom) {
        BaseRequest request = new BaseRequest();
        request.setAction(isCustom ? BaseRequest.GET_CUSTOM_HERO_LIST : BaseRequest.GET_HERO_LIST);
        RequestParams requestParams = new RequestParams(ROOTURL);
        requestParams.setBodyContent(request.toJson());
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
