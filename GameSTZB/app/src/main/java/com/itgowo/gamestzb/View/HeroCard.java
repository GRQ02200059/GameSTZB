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

import org.xutils.common.util.DensityUtil;

public class HeroCard extends RelativeLayout {
    public ImageView headimg;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;
    private ImageView star6;
    private TextView country;
    private TextView name;
    private TextView type;
    private TextView cost;

    public HeroCard(Context context) {
        super(context);
        View.inflate(context, R.layout.view_herocard, this);
        initView();
    }

    public HeroCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_herocard, this);
        initView();
        setLargerMode();
    }

    public void setLargerMode() {
        setSizeMode(13, 11, 105, 144, 10, 2, 2, 6, 2, 3);
    }

    public void setMiniMode() {
        setSizeMode(8, 6, 35, 48, 5, 1, 1, 4, 1, 1);
    }

    public void setMiddleMode() {
        setSizeMode(12, 11, 70, 96, 8, 2, 2, 5, 2, 2);
    }

    public void setSizeMode(int countryTextSize, int nameTextSize, int headWidth, int headHeight, int starWidth, int paddingLeft, int paddingTop, int otherTextSize, int typePaddingRight, int typePaddingBottom) {
        country.setTextSize(countryTextSize);
        name.setTextSize(nameTextSize);
        headimg.getLayoutParams().width = DensityUtil.dip2px(headWidth);
        headimg.getLayoutParams().height = DensityUtil.dip2px(headHeight);
        int width = DensityUtil.dip2px(starWidth);
        star1.getLayoutParams().width = width;
        star2.getLayoutParams().width = width;
        star3.getLayoutParams().width = width;
        star4.getLayoutParams().width = width;
        star5.getLayoutParams().width = width;
        star1.getLayoutParams().height = width;
        star2.getLayoutParams().height = width;
        star3.getLayoutParams().height = width;
        star4.getLayoutParams().height = width;
        star5.getLayoutParams().height = width;
        star6.getLayoutParams().height = width;
        RelativeLayout.LayoutParams mLayoutParams = (LayoutParams) type.getLayoutParams();
        mLayoutParams.setMargins(0, 0, DensityUtil.dip2px(paddingLeft), DensityUtil.dip2px(paddingTop));
        type.setLayoutParams(mLayoutParams);
        RelativeLayout.LayoutParams mLayoutParams1 = (LayoutParams) cost.getLayoutParams();
        mLayoutParams1.setMargins(0, 0, DensityUtil.dip2px(typePaddingRight), DensityUtil.dip2px(typePaddingBottom));
        cost.setLayoutParams(mLayoutParams1);
        type.setTextSize(otherTextSize);
        cost.setTextSize(otherTextSize);
        country.setPadding(DensityUtil.dip2px(paddingLeft), DensityUtil.dip2px(paddingTop), 0, 0);
        name.setPadding(DensityUtil.dip2px(paddingLeft), DensityUtil.dip2px(paddingTop), 0, 0);
    }

    private void initView() {
        headimg = (ImageView) findViewById(R.id.headimg);
        star1 = (ImageView) findViewById(R.id.star1);
        star2 = (ImageView) findViewById(R.id.star2);
        star3 = (ImageView) findViewById(R.id.star3);
        star4 = (ImageView) findViewById(R.id.star4);
        star5 = (ImageView) findViewById(R.id.star5);
        star6 = (ImageView) findViewById(R.id.star6);
        country = (TextView) findViewById(R.id.country);
        name = (TextView) findViewById(R.id.name);
        type = (TextView) findViewById(R.id.type);
        cost = (TextView) findViewById(R.id.cost);
    }

    public void setName(String mName) {
        name.setText(mName);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setData(HeroEntity entity) {
        name.setText(entity.getName());
        switch (entity.getQuality()) {
            case 6:
                star1.setVisibility(VISIBLE);
                star2.setVisibility(VISIBLE);
                star3.setVisibility(VISIBLE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(VISIBLE);
                headimg.setForeground(getResources().getDrawable(R.drawable.hero_mask_5));
                star6.setVisibility(VISIBLE);
                break;
            case 5:
                star1.setVisibility(VISIBLE);
                star2.setVisibility(VISIBLE);
                star3.setVisibility(VISIBLE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(VISIBLE);
                star6.setVisibility(GONE);
                headimg.setForeground(getResources().getDrawable(R.drawable.hero_mask_5));
                break;
            case 4:
                star1.setVisibility(GONE);
                star2.setVisibility(VISIBLE);
                star3.setVisibility(VISIBLE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(VISIBLE);
                star6.setVisibility(GONE);
                headimg.setForeground(getResources().getDrawable(R.drawable.hero_mask_4));
                break;
            case 3:
                star1.setVisibility(GONE);
                star2.setVisibility(GONE);
                star3.setVisibility(VISIBLE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(VISIBLE);
                star6.setVisibility(GONE);
                headimg.setForeground(getResources().getDrawable(R.drawable.hero_mask_3));
                break;
            case 2:
                star1.setVisibility(GONE);
                star2.setVisibility(GONE);
                star3.setVisibility(GONE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(VISIBLE);
                star6.setVisibility(GONE);
                headimg.setForeground(getResources().getDrawable(R.drawable.hero_mask_2));
                break;
            case 1:
                star1.setVisibility(GONE);
                star2.setVisibility(GONE);
                star3.setVisibility(GONE);
                star4.setVisibility(GONE);
                star5.setVisibility(VISIBLE);
                star6.setVisibility(GONE);
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
            case "神":
                country.setTextColor(getResources().getColor(R.color.country_shen));
                break;
        }
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
        type.setText(entity.getLength() == 0 ? "" : String.valueOf(entity.getLength()));
        String costStr = String.valueOf(entity.getCost()).replace(".0", "");
        cost.setText(costStr);
    }
}
