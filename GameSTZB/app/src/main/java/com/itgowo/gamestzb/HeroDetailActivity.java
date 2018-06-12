package com.itgowo.gamestzb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.itgowo.gamestzb.Base.BaseActivity;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.Entity.BaseResponse;
import com.itgowo.gamestzb.Entity.HeroDetailEntity;
import com.itgowo.gamestzb.Manager.NetManager;
import com.itgowo.gamestzb.Manager.STZBManager;
import com.itgowo.gamestzb.Manager.UserManager;
import com.itgowo.gamestzb.View.HeroDetailView;
import com.itgowo.itgowolib.itgowoNetTool;

import java.io.File;

public class HeroDetailActivity extends BaseActivity {

    private int heroId;
    private HeroDetailView heroDetailView;
    private TextView shareWeiXinBtn, shareQQBtn, shareTimelineBtn;
    private View goBackBtn;

    public static void go(Context context, int heroId) {
        Intent intent = new Intent(context, HeroDetailActivity.class);
        intent.putExtra(HERO_ID, heroId);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        heroId = getIntent().getIntExtra(HERO_ID, 0);
        if (heroId == 0) {
            showToastLong(getString(R.string.data_error));
            finish();
        }
        initView();
        shareQQBtn.setEnabled(false);
        shareWeiXinBtn.setEnabled(false);
        STZBManager.showWaitDialog(this, null, "正在获取武将信息");
        NetManager.getHeroDetail(heroId, new itgowoNetTool.onReceviceDataListener<BaseResponse<HeroDetailEntity>>() {

            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<HeroDetailEntity> result) {
                if (result != null) {
                    if (result.isSuccess()) {
                        if (result.getData() != null) {
                            heroDetailView.setData(result.getData());
                            shareQQBtn.setEnabled(true);
                            shareWeiXinBtn.setEnabled(true);
                        } else {
                            showToastShort(getString(R.string.data_error2));
                        }
                    } else {
                        showToastShort(result.getMsg());
                    }
                } else {
                    showToastShort(getString(R.string.data_error));
                }
                STZBManager.hideWaitDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                STZBManager.hideWaitDialog();
                showToastLong(getString(R.string.net_error_retry));
            }
        });
    }


    private void initView() {
        heroDetailView = findViewById(R.id.hero_detail_view);
        shareWeiXinBtn = findViewById(R.id.share_weixin);
        shareQQBtn = findViewById(R.id.share_QQ);
        goBackBtn = findViewById(R.id.goback);
        shareTimelineBtn = findViewById(R.id.share_Timeline);
        shareQQBtn.setOnClickListener(v -> {
            File file = BaseConfig.getAppFile(heroDetailView.getHeroDetailEntity().getName() + "detail.jpg");
            if (file.exists()) {
                file.delete();
            }
            Bitmap bitmap = Utils.bitmap_getViewBackground(heroDetailView);
            Utils.bitmap_CompressBmpToFile(bitmap, file, 10000);
            shareQQImage(file);
        });
        shareWeiXinBtn.setOnClickListener(v -> {
            File file = BaseConfig.getAppFile(heroDetailView.getHeroDetailEntity().getName() + "detail.jpg");
            if (file.exists()) {
                file.delete();
            }
            Bitmap bitmap = Utils.bitmap_getViewBackground(heroDetailView);
            Utils.bitmap_CompressBmpToFile(bitmap, file, 10000);
            shareWeixinImage(file, false);
        });
        shareTimelineBtn.setOnClickListener(v -> {
            File file = BaseConfig.getAppFile(heroDetailView.getHeroDetailEntity().getName() + "detail.jpg");
            if (file.exists()) {
                file.delete();
            }
            Bitmap bitmap = Utils.bitmap_getViewBackground(heroDetailView);
            Utils.bitmap_CompressBmpToFile(bitmap, file, 10000);
            shareWeixinImage(file, true);
        });
        goBackBtn.setOnClickListener(v -> finish());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UserManager.mTencent.onActivityResult(requestCode, resultCode, data);
    }
}
