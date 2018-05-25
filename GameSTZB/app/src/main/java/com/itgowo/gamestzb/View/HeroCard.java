package com.itgowo.gamestzb.View;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.R;

import org.xutils.common.util.DensityUtil;

public class HeroCard extends RelativeLayout {
    public android.support.v7.widget.AppCompatImageView headimg;
    private RepeatImageView heroStar;

    private ImageView headForeground;

    private ImageView country;
    private TextView name;
    private TextView type;
    private TextView cost;
    private float zoomStar = 2f;

    public HeroCard(Context context) {
        super(context);
        View.inflate(context, R.layout.view_herocard, this);
        initView();
        setDefaultInfo();
    }

    public HeroCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_herocard, this);
        initView();
        setDefaultInfo();
    }

    public void setLargerMode() {
        setSizeMode(8, 105, 144, 0.8f, 2, 2, 7);
    }

    public void setMiniMode() {
        setSizeMode(6, 35, 48, 0.8f, 1, 1, 5);
    }


    public void setSizeMode(int nameTextSize, int headWidth, int headHeight, float zoomStar, int paddingLeft, int paddingTop, int otherTextSize) {
        this.zoomStar = zoomStar;
        name.setTextSize(nameTextSize);
//        headimg.getLayoutParams().width = DensityUtil.dip2px(headWidth);
//        headimg.getLayoutParams().height = DensityUtil.dip2px(headHeight);
        type.setTextSize(otherTextSize);
        cost.setTextSize(otherTextSize);
        name.setPadding(DensityUtil.dip2px(paddingLeft), DensityUtil.dip2px(paddingTop), 0, 0);
    }

    public void setDefaultInfo() {
        HeroEntity entity = new HeroEntity();
        entity.setId(100000);
        entity.setIcon("hero_" + entity.getId());
        entity.setName(getResources().getString(R.string.pleaseLogin));
        entity.setQuality(6);
//        entity.setContory("神");
//        entity.setCost(5.5);
//        entity.setType("骑");
        setData(entity);
    }

    public void setDefaultHead() {
        HeroEntity entity = new HeroEntity();
        entity.setId(1000);
        entity.setIcon("hero_" + entity.getId());
//        entity.setName(getResources().getString(R.string.pleaseLogin));
        entity.setQuality(6);
//        entity.setContory("神");
//        entity.setCost(5.5);
//        entity.setType("骑");
        setData(entity);
    }

    private void initView() {
        headimg = findViewById(R.id.headimg);
        heroStar = findViewById(R.id.hero_star);
        country = (ImageView) findViewById(R.id.country);
        name = (TextView) findViewById(R.id.name);
        type = (TextView) findViewById(R.id.type);
        cost = (TextView) findViewById(R.id.cost);
        headForeground = findViewById(R.id.headForeground);
        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (right != 0 && bottom != 0) {
                    removeOnLayoutChangeListener(this);


                    LayoutParams countryLayoutParams = (LayoutParams) country.getLayoutParams();
                    countryLayoutParams.leftMargin = v.getWidth() / 90;
                    countryLayoutParams.topMargin = v.getWidth() / 60;
                    countryLayoutParams.width = v.getWidth() / 6;
                    countryLayoutParams.height = v.getWidth() / 6;
                    country.setLayoutParams(countryLayoutParams);

                    LayoutParams nameLayoutParams = (LayoutParams) name.getLayoutParams();
                    nameLayoutParams.leftMargin = v.getWidth() / 45;
                    name.setLayoutParams(nameLayoutParams);

                    LayoutParams typeLayoutParams = (LayoutParams) type.getLayoutParams();
                    typeLayoutParams.rightMargin = v.getWidth() / 30;
                    typeLayoutParams.bottomMargin = v.getHeight() / 40;
                    type.setLayoutParams(typeLayoutParams);

                    LayoutParams costLayoutParams = (LayoutParams) cost.getLayoutParams();
                    costLayoutParams.bottomMargin = v.getHeight() / 40;
                    cost.setLayoutParams(costLayoutParams);

                    LayoutParams starLayoutParams = (LayoutParams) heroStar.getLayoutParams();
                    starLayoutParams.height = v.getHeight() / 15;
                    heroStar.setLayoutParams(starLayoutParams);
                }
            }
        });
    }

    public void setName(String mName) {
        name.setText(mName);
    }

    public void refreshInfo() {
        if (BaseConfig.userInfo != null) {
            Glide.with(this).load(BaseConfig.userInfo.getHead()).into(this.headimg);
            this.setName(BaseConfig.userInfo.getNickname());
        } else {
            headimg.setImageResource(R.drawable.hero_000000);
            setName(getContext().getString(R.string.pleaseLogin));
        }
    }

    public void setData(HeroEntity entity) {
        name.setText(entity.getName());
        switch (entity.getQuality()) {
            case 6:
                headForeground.setImageResource(R.drawable.hero_mask_5);
                break;
            case 5:
                headForeground.setImageResource(R.drawable.hero_mask_5);
                break;
            case 4:
                headForeground.setImageResource(R.drawable.hero_mask_4);
                break;
            case 3:
                headForeground.setImageResource(R.drawable.hero_mask_3);
                break;
            case 2:
                headForeground.setImageResource(R.drawable.hero_mask_2);
                break;
            case 1:
                headForeground.setImageResource(R.drawable.hero_mask_1);
        }
        heroStar.setRepeatCount(entity.getQuality());
        heroStar.setImage(getResources().getDrawable(R.drawable.star1), zoomStar);
        if (!TextUtils.isEmpty(entity.getContory())) {
            country.setVisibility(VISIBLE);
            switch (entity.getContory()) {
                case "蜀":
                    country.setImageResource(R.drawable.icon_country_shu);
                    break;
                case "汉":
                    country.setImageResource(R.drawable.icon_country_han);
                    break;
                case "群":
                    country.setImageResource(R.drawable.icon_country_qun);
                    break;
                case "魏":
                    country.setImageResource(R.drawable.icon_country_wei);
                    break;
                case "吴":
                    country.setImageResource(R.drawable.icon_country_wu);
                    break;
                case "神":
                    country.setImageResource(R.drawable.icon_country_shen);
                    break;
                default:
                    country.setVisibility(INVISIBLE);
            }
        }
        if (!TextUtils.isEmpty(entity.getType())) {
            switch (entity.getType()) {
                case "弓":
                    type.setBackgroundResource(R.drawable.type_gong);
                    break;
                case "骑":
                    type.setBackgroundResource(R.drawable.type_qi);
                    break;
                case "步":
                    type.setBackgroundResource(R.drawable.type_bu);
                    break;
            }
        }
        type.setText(entity.getDistance() == 0 ? "" : String.valueOf(entity.getDistance()));
        if (entity.getCost() > 0) {
            String costStr = String.valueOf(entity.getCost()).replace(".0", "");
            cost.setText(costStr);
        } else {
            cost.setText(null);
        }

    }


}
