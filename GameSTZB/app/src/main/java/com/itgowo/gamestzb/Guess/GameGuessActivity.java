package com.itgowo.gamestzb.Guess;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.itgowo.gamestzb.Base.BaseActivity;
import com.itgowo.gamestzb.Entity.BaseResponse;
import com.itgowo.gamestzb.Entity.GuessEntity;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.Manager.NetManager;
import com.itgowo.gamestzb.Manager.STZBManager;
import com.itgowo.gamestzb.Manager.UserManager;
import com.itgowo.gamestzb.R;
import com.itgowo.itgowolib.itgowoNetTool;

import java.util.List;

public class GameGuessActivity extends BaseActivity {

    private TextView gameMontyTv;
    private ImageView gameGuessImg;
    private Button gameGuessBtn1, gameGuessBtn2, gameGuessBtn3, gameGuessBtn4, gameGuessNextBtn;
    private List<HeroEntity> heroEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_guess);

        gameMontyTv = (TextView) findViewById(R.id.game_monty_tv);
        gameGuessImg = (ImageView) findViewById(R.id.game_guess_img);
        gameGuessBtn1 = findViewById(R.id.game_guess_bt1);
        gameGuessBtn2 = findViewById(R.id.game_guess_bt2);
        gameGuessBtn3 = findViewById(R.id.game_guess_bt3);
        gameGuessBtn4 = findViewById(R.id.game_guess_bt4);
        gameGuessNextBtn = findViewById(R.id.game_guess_next_btn);
        gameGuessBtn1.setOnClickListener(v -> postGameGuessData(0));
        gameGuessBtn2.setOnClickListener(v -> postGameGuessData(1));
        gameGuessBtn3.setOnClickListener(v -> postGameGuessData(2));
        gameGuessBtn4.setOnClickListener(v -> postGameGuessData(3));
        gameGuessNextBtn.setOnClickListener(v -> getGameGuessData());
        gameMontyTv.setText(String.valueOf(UserManager.getMoney()));
    }

    private void postGameGuessData(int position) {
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
                } else {
                    showToastShort(result.getMsg());
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    private void getGameGuessData() {
        NetManager.getHeroGuess(new itgowoNetTool.onReceviceDataListener<BaseResponse<GuessEntity>>() {
            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<GuessEntity> result) {
                if (result != null && result.isSuccess() && result.getData().getOption().size() == 4) {
                    heroEntities = result.getData().getOption();
                    gameGuessBtn1.setEnabled(true);
                    gameGuessBtn2.setEnabled(true);
                    gameGuessBtn3.setEnabled(true);
                    gameGuessBtn4.setEnabled(true);
                    gameGuessBtn1.setText(result.getData().getOption().get(0).getContory() + " " + result.getData().getOption().get(0).getName());
                    gameGuessBtn2.setText(result.getData().getOption().get(1).getContory() + " " + result.getData().getOption().get(1).getName());
                    gameGuessBtn3.setText(result.getData().getOption().get(2).getContory() + " " + result.getData().getOption().get(2).getName());
                    gameGuessBtn4.setText(result.getData().getOption().get(3).getContory() + " " + result.getData().getOption().get(3).getName());
                    STZBManager.bindView(new HeroEntity().setId(result.getData().getId()), gameGuessImg);
                } else {
                    showToastShort("返回数据错误");
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                showToastShort(throwable.getMessage());
            }
        });

    }

    public static void go(Context context) {
        context.startActivity(new Intent(context, GameGuessActivity.class));
    }
}
