package com.itgowo.gamestzb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itgowo.gamestzb.Base.BaseActivity;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.Entity.UserInfo;
import com.itgowo.gamestzb.Manager.UserManager;
import com.itgowo.views.SuperDialog;
import com.taobao.sophix.SophixManager;

public class UserActivity extends BaseActivity {
    private ImageView userHeadImg;
    private CheckBox isPlayVideo, isPlayMusic;
    private Button btnLoginQQ;
    private View goBackBtn, rebootBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toast.makeText(this, "开发测试版，账户信息未完善，很多功能还只是摆设，请留意后续更新", Toast.LENGTH_LONG).show();
        initView();
        initListener();
        initData();
    }

    public static void go(Activity context, Integer result) {
        Intent intent = new Intent(context, UserActivity.class);
        if (result == null) {
            context.startActivity(intent);
        } else {
            context.startActivityForResult(intent, result);
        }
    }

    private void initData() {
        isPlayVideo.setChecked(BaseConfig.getData(BaseConfig.USER_ISPLAYVIDEO, true));
        isPlayMusic.setChecked(BaseConfig.getData(BaseConfig.USER_ISPLAYMUSIC, true));
        if (BaseConfig.userInfo==null){
            userHeadImg.setImageDrawable(null);
        }else {
            Glide.with(userHeadImg).load(BaseConfig.userInfo.getHead()).into(userHeadImg);}
        refreshLogonBtnStatus();
    }

    private void refreshLogonBtnStatus() {
        btnLoginQQ.setText(UserManager.isLogin ? R.string.logout : R.string.loginqq);
    }

    private void initListener() {
        isPlayVideo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (BaseConfig.getData(BaseConfig.USER_ISPLAYVIDEO, true) != isChecked) {
                BaseConfig.putData(BaseConfig.USER_ISPLAYVIDEO, isChecked);
                setResult(Activity.RESULT_OK);
            }
        });
        isPlayMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (BaseConfig.getData(BaseConfig.USER_ISPLAYMUSIC, true) != isChecked) {
                    BaseConfig.putData(BaseConfig.USER_ISPLAYMUSIC, isChecked);
                    setResult(Activity.RESULT_OK);
                }
            }
        });
        btnLoginQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                if (UserManager.isLogin) {
                    UserManager.logout();
                    refreshLogonBtnStatus();
//                    heroCard.refreshInfo();
                    v.setEnabled(true);
                } else {
                    UserManager.login4QQ(UserActivity.this, new UserManager.onUserLoginListener() {
                        @Override
                        public void onSuccess(UserInfo userInfo) {
//                            heroCard.refreshInfo();
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
                            showToastShort("登录失败：" + e.getMessage());
                        }
                    });
                }
            }
        });
        goBackBtn.setOnClickListener(v -> finish());
        rebootBtn.setOnClickListener(v -> {
            SuperDialog dialog = new SuperDialog(context);
            dialog.setContent("如果App出现问题或者提示重启，请选择确定按钮").setTitle("App退出提示").setButtonTexts("确定", "取消").setListener((isButtonClick, position) -> {
                if (position == 0) {
                    SophixManager.getInstance().killProcessSafely();
                }
            }).show();
        });
    }

    private void initView() {
        userHeadImg = findViewById(R.id.User_Head_Img);
        isPlayVideo = findViewById(R.id.isPlayVideo);
        isPlayMusic = findViewById(R.id.isPlayMusic);
        btnLoginQQ = findViewById(R.id.btn_login_qq);
        goBackBtn = findViewById(R.id.goback);
        rebootBtn = findViewById(R.id.reboot);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UserManager.onActivityResult(requestCode, resultCode, data);
    }
}
