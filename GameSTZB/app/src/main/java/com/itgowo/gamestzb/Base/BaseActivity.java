package com.itgowo.gamestzb.Base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.itgowo.gamestzb.Utils;

import me.weyye.hipermission.PermissionCallback;

public class BaseActivity extends AppCompatActivity {
    public static final int INTENT_UserActivity = 1001;
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        context = this;
        super.onCreate(savedInstanceState);
    }
    public void showToastShort(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
    public void showToastLong(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }
}
