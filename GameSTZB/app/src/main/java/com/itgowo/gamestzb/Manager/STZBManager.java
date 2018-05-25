package com.itgowo.gamestzb.Manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.itgowo.gamestzb.Base.BaseApp;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.BuildConfig;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.R;
import com.itgowo.views.SuperDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class STZBManager {
    private static ExecutorService poolExecutor = Executors.newSingleThreadExecutor();
    private static Handler handler = new Handler(Looper.getMainLooper());
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

    public void goUpdateVersion(Context context) {
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

    public static void deleteHeroImage(List<HeroEntity> heroEntities) {
        File file = BaseApp.app.getDir("hero", Context.MODE_PRIVATE);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            boolean shouldDelete = true;
            File file1 = files[i];
            for (int i1 = 0; i1 < heroEntities.size(); i1++) {
                if (file1.getName().equals(heroEntities.get(i1).getFileName())) {
                    shouldDelete = false;
                    break;
                }
            }
            if (shouldDelete) {
                if (!file1.delete()) {
                    file1.deleteOnExit();
                }
            }
        }
    }

    public static void downHeroImage(List<HeroEntity> heroEntities, int count) {
        File rootFile = BaseApp.app.getDir("hero", Context.MODE_PRIVATE);
        rootFile.mkdirs();
        final int[] num = {0};
        for (int i = 0; i < heroEntities.size(); i++) {
            HeroEntity entity = heroEntities.get(i);
            File file = new File(entity.getHeroFilePath());
            if (!file.exists()) {
                poolExecutor.execute(() -> {
                    num[0]++;
                    handler.post(() -> STZBManager.setWaitDialogMessage("正在下载：" + entity.getContory() + " " + entity.getType() + " " + entity.getName() + "\r\n" + num[0] * 100 / count + "%"));
                    NetManager.download(file, String.format(NetManager.ROOTURL_DOWNLOAD_HERO_IMAGE, entity.getId()));
                    if (num[0] == count) {
                        handler.post(() -> STZBManager.hideWaitDialog());
                    }
                });
            }
        }
    }

    private static ProgressDialog waitDialog;

    public static void showWaitDialog(Context context, String title, String content) {
        if (waitDialog == null) {
            waitDialog = new ProgressDialog(context, ProgressDialog.STYLE_HORIZONTAL);
            waitDialog.setTitle(title);
            waitDialog.setCancelable(false);
            waitDialog.setMessage(content);
            waitDialog.show();
        }
    }

    public static void hideWaitDialog() {
        if (waitDialog == null) {
            return;
        }
        if (waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
        waitDialog = null;
    }

    public static void setWaitDialogMessage(String content) {
        if (waitDialog == null) {
            return;
        }
        if (waitDialog.isShowing()) {
            if (!TextUtils.isEmpty(content)) {
                waitDialog.setMessage(content);
            }
        }
    }
}
