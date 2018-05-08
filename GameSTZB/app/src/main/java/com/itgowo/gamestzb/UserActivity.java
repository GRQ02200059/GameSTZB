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
import com.itgowo.gamestzb.Manager.UserManager;
import com.itgowo.gamestzb.View.HeroCard;

public class UserActivity extends BaseActivity {
    private HeroCard heroCard;
    private CheckBox isPlayVideo, isPlayMusic;
    private Button btnLoginQQ;

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
        heroCard.refreshInfo();
        refreshLogonBtnStatus();
    }

    private void refreshLogonBtnStatus() {
        btnLoginQQ.setText(UserManager.isLogin ? R.string.logout : R.string.loginqq);
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
                v.setEnabled(false);
                if (UserManager.isLogin) {
                    UserManager.logout();
                    refreshLogonBtnStatus();
                    heroCard.refreshInfo();
                    v.setEnabled(true);
                } else {
                    UserManager.login4QQ(UserActivity.this, new UserManager.onUserLoginListener() {
                        @Override
                        public void onSuccess(UserInfo userInfo) {
                            heroCard.refreshInfo();
                            setResult(Activity.RESULT_OK);
                            refreshLogonBtnStatus();
                            v.setEnabled(true);
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            v.setEnabled(true);
                            Toast.makeText(context, "登录失败，请重试！", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        heroCard = (HeroCard) findViewById(R.id.hero);
        isPlayVideo = findViewById(R.id.isPlayVideo);
        isPlayMusic = findViewById(R.id.isPlayMusic);
        btnLoginQQ = findViewById(R.id.btn_login_qq);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UserManager.onActivityResult(requestCode, resultCode, data);
    }
}
