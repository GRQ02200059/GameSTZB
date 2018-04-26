package com.itgowo.gamestzb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.itgowo.gamestzb.Base.BaseActivity;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.Entity.UserInfo;
import com.itgowo.gamestzb.View.HeroCard;

public class UserActivity extends BaseActivity {
    private HeroCard heroCard;
    private CheckBox isPlayVideo, isPlayMusic;
    private Button btnLoginQQ, btnLoginWeixin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toast.makeText(this, "开发测试版，账户信息未完善，很多功能还只是摆设，请留意后续更新", Toast.LENGTH_LONG).show();
        initView();
        initListener();
        initData();
    }

    private void initData() {
        isPlayVideo.setChecked(BaseConfig.getData(BaseConfig.USER_ISPLAYVIDEO, true));
        isPlayMusic.setChecked(BaseConfig.getData(BaseConfig.USER_ISPLAYMUSIC, true));
    }

    private void initListener() {
        isPlayVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BaseConfig.putData(BaseConfig.USER_ISPLAYVIDEO, isChecked);
            }
        });
        isPlayMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BaseConfig.putData(BaseConfig.USER_ISPLAYMUSIC, isChecked);
            }
        });
        btnLoginQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.login4QQ(UserActivity.this, new UserManager.onUserLoginListener() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        BaseConfig.userInfo = userInfo;
                        heroCard.refreshInfo();
                        setResult(Activity.RESULT_OK);
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(context, "登录失败，请重试！", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void initView() {
        heroCard = (HeroCard) findViewById(R.id.hero);
        isPlayVideo = findViewById(R.id.isPlayVideo);
        isPlayMusic = findViewById(R.id.isPlayMusic);
        btnLoginQQ = findViewById(R.id.btn_login_qq);
        btnLoginWeixin = findViewById(R.id.btn_login_weixin);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UserManager.onActivityResult(requestCode, resultCode, data);
    }
}
