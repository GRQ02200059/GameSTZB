package com.itgowo.gamestzb.Guess;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.itgowo.gamestzb.Base.BaseActivity;
import com.itgowo.gamestzb.Entity.BaseResponse;
import com.itgowo.gamestzb.Entity.GuessEntity;
import com.itgowo.gamestzb.Entity.HeroDetailEntity;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.Manager.NetManager;
import com.itgowo.gamestzb.Manager.STZBManager;
import com.itgowo.gamestzb.Manager.UserManager;
import com.itgowo.gamestzb.R;
import com.itgowo.itgowolib.itgowoNetTool;

import java.util.List;

public class GameGuessActivity extends BaseActivity {

    private TextView gameMontyTv, gameHeroDesc;
    private ImageView gameGuessImg, goBack;
    private Button gameGuessBtn1, gameGuessBtn2, gameGuessBtn3, gameGuessBtn4, gameGuessNextBtn;
    private List<HeroEntity> heroEntities;
    private int heroId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_guess);

        initView();
        initListener();

        gameMontyTv.setText(String.valueOf(UserManager.getMoney()));
        getGameGuessData();
    }

    private void initListener() {
        goBack.setOnClickListener(v -> finish());
        gameGuessBtn1.setOnClickListener(v -> postGameGuessData(0));
        gameGuessBtn2.setOnClickListener(v -> postGameGuessData(1));
        gameGuessBtn3.setOnClickListener(v -> postGameGuessData(2));
        gameGuessBtn4.setOnClickListener(v -> postGameGuessData(3));
        gameGuessNextBtn.setOnClickListener(v -> getGameGuessData());
    }

    private void initView() {
        gameMontyTv = findViewById(R.id.game_monty_tv);
        gameGuessImg = findViewById(R.id.game_guess_img);
        gameGuessBtn1 = findViewById(R.id.game_guess_bt1);
        gameGuessBtn2 = findViewById(R.id.game_guess_bt2);
        gameGuessBtn3 = findViewById(R.id.game_guess_bt3);
        gameGuessBtn4 = findViewById(R.id.game_guess_bt4);
        gameGuessNextBtn = findViewById(R.id.game_guess_next_btn);
        gameHeroDesc = findViewById(R.id.game_hero_desc);
        goBack = findViewById(R.id.goback);
        gameHeroDesc.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void postGameGuessData(int position) {
        if (heroEntities == null || heroEntities.size() <= position) {
            return;
        }
        gameGuessBtn1.setEnabled(false);
        gameGuessBtn2.setEnabled(false);
        gameGuessBtn3.setEnabled(false);
        gameGuessBtn4.setEnabled(false);
        NetManager.postHeroGuess(heroEntities.get(position), new itgowoNetTool.onReceviceDataListener<BaseResponse<JSONObject>>() {
            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<JSONObject> result) {
                if (result != null && result.isSuccess()) {
                    long money = result.getData().getLong("game_money");
                    gameMontyTv.setText(String.valueOf(money));
                    UserManager.setMoney(money);
                    showToastShort("恭喜您答对了");
                    getGameGuessData();
                } else {
                    showToastShort(result.getMsg());
                    NetManager.getHeroDetail(heroId, new itgowoNetTool.onReceviceDataListener<BaseResponse<HeroDetailEntity>>() {

                        @Override
                        public void onResult(String requestStr, String responseStr, BaseResponse<HeroDetailEntity> result) {
                            if (result != null && result.isSuccess()) {
                                gameHeroDesc.setText(result.getData().getDesc());

                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {

                        }
                    });
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    private void getGameGuessData() {
        gameGuessNextBtn.setEnabled(false);
        NetManager.getHeroGuess(new itgowoNetTool.onReceviceDataListener<BaseResponse<GuessEntity>>() {
            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<GuessEntity> result) {
                gameGuessNextBtn.setEnabled(true);
                if (result != null && result.isSuccess() && result.getData().getOption().size() == 4) {
                    heroId = result.getData().getId();
                    gameHeroDesc.setText("");
                    heroEntities = result.getData().getOption();
                    gameGuessBtn1.setVisibility(View.VISIBLE);
                    gameGuessBtn2.setVisibility(View.VISIBLE);
                    gameGuessBtn3.setVisibility(View.VISIBLE);
                    gameGuessBtn4.setVisibility(View.VISIBLE);
                    gameGuessBtn1.setEnabled(true);
                    gameGuessBtn2.setEnabled(true);
                    gameGuessBtn3.setEnabled(true);
                    gameGuessBtn4.setEnabled(true);
                    gameGuessBtn1.setText(result.getData().getOption().get(0).getContory() + " " + result.getData().getOption().get(0).getName());
                    gameGuessBtn2.setText(result.getData().getOption().get(1).getContory() + " " + result.getData().getOption().get(1).getName());
                    gameGuessBtn3.setText(result.getData().getOption().get(2).getContory() + " " + result.getData().getOption().get(2).getName());
                    gameGuessBtn4.setText(result.getData().getOption().get(3).getContory() + " " + result.getData().getOption().get(3).getName());
                    gameGuessImg.setBackgroundResource(R.drawable.light);
                    STZBManager.bindView(result.getData().getId(), gameGuessImg);

                } else {
                    if (result != null) {
                        showToastShort(result.getMsg());
                    } else {
                        showToastShort("返回数据错误");
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                showToastShort(throwable.getMessage());
                gameGuessNextBtn.setEnabled(true);
            }
        });

    }

    public static void go(Context context) {
        context.startActivity(new Intent(context, GameGuessActivity.class));
    }
}
