package com.itgowo.gamestzb.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itgowo.gamestzb.Entity.HeroDetailEntity;
import com.itgowo.gamestzb.Manager.STZBManager;
import com.itgowo.gamestzb.R;

public class HeroDetailView extends RelativeLayout {
    private ImageView heroHead;
    private TextView name;
    private TextView birthday;
    private TextView cost;
    private TextView type;
    private TextView quality;
    private ImageView country;
    private TextView distance;
    private TextView skill;
    private TextView desc;

    public HeroDetailEntity getHeroDetailEntity() {
        return heroDetailEntity;
    }

    private HeroDetailEntity heroDetailEntity;

    public HeroDetailView(Context context) {
        super(context);
        View.inflate(context, R.layout.view_hero_detail, this);
        initView();
    }

    public HeroDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_hero_detail, this);
        initView();
    }

    public void setData(HeroDetailEntity DetailEntity) {
        heroDetailEntity = DetailEntity;
        STZBManager.bindView(heroDetailEntity.getId(), heroHead);
        name.setText(heroDetailEntity.getName());
        cost.setText(String.valueOf(heroDetailEntity.getCost()));
        type.setText(heroDetailEntity.getType());
        quality.setText(String.valueOf(heroDetailEntity.getQuality()));
        if (!TextUtils.isEmpty(heroDetailEntity.getContory())) {
            switch (heroDetailEntity.getContory()) {
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
                    country.setImageResource(R.drawable.icon_country_shen);
            }
        }
        distance.setText(String.valueOf(heroDetailEntity.getDistance()));
        skill.setText(heroDetailEntity.getMethodName());
        desc.setText(heroDetailEntity.getDesc());
    }

    private void initView() {
        heroHead = (ImageView) findViewById(R.id.hero_head);
        name = (TextView) findViewById(R.id.name);
        birthday = (TextView) findViewById(R.id.birthday);
        cost = (TextView) findViewById(R.id.cost);
        type = (TextView) findViewById(R.id.type);
        quality = (TextView) findViewById(R.id.quality);
        country = (ImageView) findViewById(R.id.country);
        distance = (TextView) findViewById(R.id.distance);
        skill = (TextView) findViewById(R.id.skill);
        desc = (TextView) findViewById(R.id.desc);
        desc.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
