package com.itgowo.gamestzb.Manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.BuildConfig;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.R;
import com.itgowo.views.SuperDialog;

import java.util.ArrayList;
import java.util.List;

public class STZBManager {
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
    public void goUpdateVersion(Context context){
        if (BaseConfig.updateInfo == null) {
            return;
        }
        String tip = String.format(context.getResources().getString(R.string.versionTip), BuildConfig.VERSION_NAME, BaseConfig.updateInfo.getVersionname(), BaseConfig.updateInfo.getVersioninfo());
        SuperDialog dialog = new SuperDialog(context).setTitle("发现新版本").setContent(tip).setListener(new SuperDialog.onDialogClickListener() {
            @Override
            public void click(boolean isButtonClick, int position) {
                try {
                    Uri uri = Uri.parse(BaseConfig.updateInfo.getDownloadurl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }
   public static void AutoLogin(){

   }

}
