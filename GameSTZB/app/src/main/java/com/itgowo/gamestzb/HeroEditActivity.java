package com.itgowo.gamestzb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.itgowo.gamestzb.Base.BaseActivity;

public class HeroEditActivity extends BaseActivity {
    public static void go(Context context){
        context.startActivity(new Intent(context,HeroEditActivity.class));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_edit);
    }
}
