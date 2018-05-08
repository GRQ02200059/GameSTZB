package com.itgowo.gamestzb.Manager;

import com.itgowo.gamestzb.Entity.HeroEntity;

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

   public static void AutoLogin(){

   }

}
