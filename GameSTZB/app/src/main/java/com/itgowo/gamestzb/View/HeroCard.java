package com.itgowo.gamestzb.View;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.R;

public class HeroCard extends RelativeLayout {
    public ImageView headimg;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private TextView country;
    private TextView name;

    public HeroCard(Context context) {
        super(context);
        View.inflate(context, R.layout.view_herocard, this);
        initView();
    }

    public HeroCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_herocard, this);
        initView();
    }

    public void setLargerMode() {

    }

    public void setMiniMode() {

    }

    public void setMiddleMode() {

    }

    private void initView() {
        headimg = (ImageView) findViewById(R.id.headimg);
        star1 = (ImageView) findViewById(R.id.star1);
        star2 = (ImageView) findViewById(R.id.star2);
        star3 = (ImageView) findViewById(R.id.star3);
        star4 = (ImageView) findViewById(R.id.star4);
        star5 = (ImageView) findViewById(R.id.star5);
        country = (TextView) findViewById(R.id.country);
        name = (TextView) findViewById(R.id.name);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setData(HeroEntity entity) {
        name.setText(entity.getName());
        switch (entity.getQuality()) {
            case 5:
                star1.setVisibility(VISIBLE);
                star2.setVisibility(VISIBLE);
                star3.setVisibility(VISIBLE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(VISIBLE);
                headimg.setForeground(getResources().getDrawable(R.drawable.hero_mask_5));
                break;
            case 4:
                star1.setVisibility(GONE);
                star2.setVisibility(VISIBLE);
                star3.setVisibility(VISIBLE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(VISIBLE);
                headimg.setForeground(getResources().getDrawable(R.drawable.hero_mask_4));
                break;
            case 3:
                star1.setVisibility(GONE);
                star2.setVisibility(GONE);
                star3.setVisibility(VISIBLE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(VISIBLE);
                headimg.setForeground(getResources().getDrawable(R.drawable.hero_mask_3));
                break;
            case 2:
                star1.setVisibility(GONE);
                star2.setVisibility(GONE);
                star3.setVisibility(GONE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(VISIBLE);
                headimg.setForeground(getResources().getDrawable(R.drawable.hero_mask_2));
                break;
            case 1:
                star1.setVisibility(GONE);
                star2.setVisibility(GONE);
                star3.setVisibility(GONE);
                star4.setVisibility(GONE);
                star5.setVisibility(VISIBLE);
                headimg.setForeground(getResources().getDrawable(R.drawable.hero_mask_1));
        }
        country.setText(entity.getContory());
        switch (entity.getContory()) {
            case "蜀":
                country.setTextColor(getResources().getColor(R.color.country_shu));
                break;
            case "汉":
                country.setTextColor(getResources().getColor(R.color.country_han));
                break;
            case "群":
                country.setTextColor(getResources().getColor(R.color.country_qun));
                break;
            case "魏":
                country.setTextColor(getResources().getColor(R.color.country_wei));
                break;
            case "吴":
                country.setTextColor(getResources().getColor(R.color.country_wu));
                break;
        }
    }
}
