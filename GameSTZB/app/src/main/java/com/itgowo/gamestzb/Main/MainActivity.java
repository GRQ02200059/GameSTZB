package com.itgowo.gamestzb.Main;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.alibaba.sdk.android.feedback.util.IUnreadCountCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.itgowo.gamestzb.Base.BaseActivity;
import com.itgowo.gamestzb.Base.BaseApp;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.BuildConfig;
import com.itgowo.gamestzb.Entity.BaseResponse;
import com.itgowo.gamestzb.Entity.GetRandomHeroEntity;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.Entity.UpdateVersion;
import com.itgowo.gamestzb.Guess.GameGuessActivity;
import com.itgowo.gamestzb.HeroEditActivity;
import com.itgowo.gamestzb.HeroListActivity;
import com.itgowo.gamestzb.Manager.NetManager;
import com.itgowo.gamestzb.Manager.STZBManager;
import com.itgowo.gamestzb.Manager.UserManager;
import com.itgowo.gamestzb.Manager.ViewCacheManager;
import com.itgowo.gamestzb.MusicService;
import com.itgowo.gamestzb.R;
import com.itgowo.gamestzb.UserActivity;
import com.itgowo.gamestzb.Utils;
import com.itgowo.gamestzb.View.FillVideoView;
import com.itgowo.gamestzb.View.HeroCard;
import com.itgowo.itgowolib.itgowoNetTool;
import com.itgowo.views.SuperDialog;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.taobao.sophix.SophixManager;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.PermissionCallback;

public class MainActivity extends BaseActivity implements UserManager.onUserStatusListener, MainPresenter.onMainActivityActionListener {
    private MainPresenter presenter;
    private View layoutRootLayout;
    private LinearLayout countLayout, cardLayout;
    private TextView fabNotice;
    private List<HeroEntity> randomHeroEntities = new ArrayList<>();
    private TextView msg6;
    private TextView msg5;
    private TextView msg4;
    private TextView msg3;
    private TextView msg2;
    private TextView msg1;
    private FrameLayout videoRoot;
    private Button goodLuckBtn1, goodLuckBtn3, goodLuckBtn5;
    private int count5, count4, count3, count2, count1;
    private FloatingActionButton rightLowerButton;
    private VideoView viewVideoPlayView;
    private ImageView viewUserHeadImg;
    private Handler handler = new Handler() {
        @SuppressLint("NewApi")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final int M = 1024 * 1024;
            final Runtime runtime = Runtime.getRuntime();

            Log.i("Memory", "最大可用内存：" + runtime.maxMemory() / M + "M");
            Log.i("Memory", "当前可用内存：" + runtime.totalMemory() / M + "M");
            Log.i("Memory", "当前空闲内存：" + runtime.freeMemory() / M + "M");
            Log.i("Memory", "当前已使用内存：" + (runtime.totalMemory() - runtime.freeMemory()) / M + "M");
            handler.sendEmptyMessageDelayed(1, 2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this, this);
        Utils.checkPermission(this, new PermissionCallback() {
            @Override
            public void onClose() {

            }

            @Override
            public void onFinish() {
                nextBoot();
            }

            @Override
            public void onDeny(String permission, int position) {

            }

            @Override
            public void onGuarantee(String permission, int position) {

            }
        });

//        handler.sendEmptyMessageDelayed(1, 2000);

    }

    private void nextBoot() {
        initView();
        initLstener();
        startFirst();
        BaseApp.threadPool.execute(new Runnable() {
            @Override
            public void run() {
                SophixManager.getInstance().queryAndLoadNewPatch();
                presenter.CheckAndInitHeroListData();
                checkVersion();
            }
        });
    }


    private void refreshUserInfo() {
        if (viewUserHeadImg != null && BaseConfig.userInfo != null) {
            RequestOptions options = new RequestOptions().transform(new RoundedCorners(DensityUtil.dip2px(40)));
            Glide.with(viewUserHeadImg).load(BaseConfig.userInfo.getHead()).apply(options).into(viewUserHeadImg);
            msg6.setText(String.valueOf(UserManager.getMoney()));
        }
    }


    private void initLstener() {
        viewUserHeadImg.setOnClickListener(mView -> UserActivity.go(MainActivity.this, INTENT_UserActivity));
        findViewById(R.id.helpDev).setOnClickListener(v -> {
            SuperDialog dialog = new SuperDialog(context).setShowImage().setImageListener(imageView -> Glide.with(imageView).load("http://file.itgowo.com/game/pay/allpay.png").into(imageView)).setShowButtonLayout(false);
            dialog.setAspectRatio(0.8f).show();
        });
        fabNotice.setOnClickListener(v -> BaseApp.getStzbManager().goUpdateVersion(context));
        goodLuckBtn1.setOnClickListener(v -> goodluck(1));
        goodLuckBtn3.setOnClickListener(v -> goodluck(3));
        goodLuckBtn5.setOnClickListener(v -> goodluck(5));
    }

    private void checkVersion() {
        NetManager.getUpdateInfo(new itgowoNetTool.onReceviceDataListener<BaseResponse<UpdateVersion>>() {

            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<UpdateVersion> result) {
                if (result.isSuccess()) {
                    if (result.getData().getVersioncode() > BuildConfig.VERSION_CODE) {
                        BaseConfig.updateInfo = result.getData();
                        fabNotice.setText("升级");
                    } else {
                        FeedbackAPI.getFeedbackUnreadCount(new IUnreadCountCallback() {
                            @Override
                            public void onSuccess(int i) {
                                runOnUiThread(() -> {
                                    if (i > 0) {
                                        fabNotice.setText("新消息");
                                    } else {
                                        fabNotice.setText("");
                                    }
                                });
                            }

                            @Override
                            public void onError(int i, String s) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

    }

    @Override
    protected void onPause() {
        if (viewVideoPlayView != null) {
            viewVideoPlayView.pause();
        }
        super.onPause();
        UserManager.removeUserStatusListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (viewVideoPlayView != null) {
            viewVideoPlayView.start();
        }
        UserManager.addUserStatusListener(this);
        refreshUserInfo();
    }

    private void reSetStyle() {
        if (!BaseConfig.getData(BaseConfig.USER_ISPLAYVIDEO, true)) {
            layoutRootLayout.setBackgroundResource(R.drawable.background2);
            if (viewVideoPlayView != null) {
                if (viewVideoPlayView.isPlaying()) {
                    viewVideoPlayView.stopPlayback();
                }
                viewVideoPlayView.setVisibility(View.GONE);
                videoRoot.removeAllViews();
                viewVideoPlayView = null;
            }
        } else {
            layoutRootLayout.setBackground(null);
            viewVideoPlayView = new FillVideoView(this);
            videoRoot.addView(viewVideoPlayView);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cg_1);
            viewVideoPlayView.setVideoURI(uri);
            viewVideoPlayView.setClickable(false);
            viewVideoPlayView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mPlayer) {
                    mPlayer.start();
                    mPlayer.setLooping(true);
                }
            });
        }
        if (BaseConfig.getData(BaseConfig.USER_ISPLAYMUSIC, true)) {
            MusicService.playMusic(this, null);
        } else {
            MusicService.stopMusic(this);
        }
    }

    private void startFirst() {
        if (!BaseConfig.getData(BaseConfig.USER_ISPLAYVIDEO, true)) {
            layoutRootLayout.setVisibility(View.VISIBLE);
            layoutRootLayout.setBackgroundResource(R.drawable.background2);
            ValueAnimator anim = ValueAnimator.ofFloat(0f, 0.4f, 0.7f, 1f);
            anim.addUpdateListener(animation -> {
                layoutRootLayout.setAlpha((Float) animation.getAnimatedValue());
                rightLowerButton.setAlpha((Float) animation.getAnimatedValue());
            });
            anim.setDuration(1200);// 动画持续时间
            anim.start();
        } else {
            viewVideoPlayView = new FillVideoView(this);
            videoRoot.addView(viewVideoPlayView);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cg_1);
            viewVideoPlayView.setVideoURI(uri);
            viewVideoPlayView.setClickable(false);
            viewVideoPlayView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mPlayer) {
                    mPlayer.start();
                    mPlayer.setLooping(true);
                    viewVideoPlayView.setClickable(false);
                    layoutRootLayout.setVisibility(View.VISIBLE);
                    ValueAnimator anim = ValueAnimator.ofFloat(0f, 0.4f, 0.7f, 1f);
                    anim.addUpdateListener(animation -> {
                        layoutRootLayout.setAlpha((Float) animation.getAnimatedValue());
                        rightLowerButton.setAlpha((Float) animation.getAnimatedValue());
                    });
                    anim.setDuration(1200);// 动画持续时间
                    anim.start();
                }
            });
            viewVideoPlayView.start();
        }
        if (BaseConfig.getData(BaseConfig.USER_ISPLAYMUSIC, true)) {
            MusicService.playMusic(this, null);
        } else {
            MusicService.stopMusic(this);
        }
    }

    private void initView() {
        viewUserHeadImg = findViewById(R.id.User_Head_Img);
        msg6 = (TextView) findViewById(R.id.msg6);
        msg5 = (TextView) findViewById(R.id.msg5);
        msg4 = (TextView) findViewById(R.id.msg4);
        msg3 = (TextView) findViewById(R.id.msg3);
        msg2 = (TextView) findViewById(R.id.msg2);
        msg1 = (TextView) findViewById(R.id.msg1);
        viewVideoPlayView = findViewById(R.id.videoview);
        layoutRootLayout = findViewById(R.id.rootlayout);
        videoRoot = findViewById(R.id.videoRoot);
        fabNotice = findViewById(R.id.fabNotice);
        goodLuckBtn1 = findViewById(R.id.goodBt1);
        goodLuckBtn3 = findViewById(R.id.goodBt3);
        goodLuckBtn5 = findViewById(R.id.goodBt5);
        countLayout = findViewById(R.id.countLayout);
        cardLayout = findViewById(R.id.cardLayout);
        initFloatingActionButton();
    }

    private void initFloatingActionButton() {
        final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_dialer));
        rightLowerButton = new FloatingActionButton.Builder(this).setContentView(fabIconNew).build();
        rightLowerButton.setAlpha(0.0f);
        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rLSubBuilder.setContentView(getAction("图鉴")).build())
                .addSubActionView(rLSubBuilder.setContentView(getAction("猜将")).build())
                .addSubActionView(rLSubBuilder.setContentView(getAction("制作")).build())
                .addSubActionView(rLSubBuilder.setContentView(getAction("反馈")).build())
                .attachTo(rightLowerButton)
                .build();
        for (int i = 0; i < rightLowerMenu.getSubActionItems().size(); i++) {
            int finalI = i;
            rightLowerMenu.getSubActionItems().get(i).view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightLowerMenu.close(true);
                    switch (finalI) {
                        case 0:
                            HeroListActivity.go(context);
                            break;
                        case 1:
                            GameGuessActivity.go(context);
                            break;
                        case 2:
                            HeroEditActivity.go(context);
                            break;
                        case 3:
                            BaseApp.getStzbManager().goUpdateVersion(context);
                            break;
                        default:
                    }

                }
            });
        }
    }

    private TextView getAction(String text) {
        TextView textView = new TextView(this);
//        textView.setBackgroundResource(R.drawable.shape_btn_blue);
        textView.setText(text);
        textView.setTextSize(10);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("getAction.onClick" + text);
            }
        });
        return textView;
    }

    private void onRandomResult() {
        countLayout.setVisibility(View.VISIBLE);
        msg6.setText(String.valueOf(UserManager.getMoney()));
        msg5.setText("已收集：" + count5);
        msg4.setText("已收集：" + count4);
        msg3.setText("已收集：" + count3);
        msg2.setText("已收集：" + count2);
        msg1.setText("已收集：" + count1);
    }

    /**
     * 0-45  5星
     * 46-134  4星
     * 135-219 3星
     * <p>
     */
    private void goodluck(int num) {
//        if (UserManager.isLogin()&&UserManager.getMoney() < num * 200 - (num == 5 ? 50 : 0)) {
//            showToastShort("玉不够，去做任务领取玉符吧");
//            return;
//        }
        NetManager.getRandomHero(num, new itgowoNetTool.onReceviceDataListener<BaseResponse<GetRandomHeroEntity>>() {

            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<GetRandomHeroEntity> result) {
                if (result.isSuccess()) {
                    if (result.getData() == null) {
                        return;
                    }
                    randomHeroEntities = result.getData().getHerolist();
                    UserManager.setMoney(result.getData().getGame_money());
                    showHeros();
                } else {
                    Toast.makeText(context, result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });


    }

    private void showHeros() {
        if (randomHeroEntities == null) {
            return;
        }
        msg1.setVisibility(View.INVISIBLE);
        msg2.setVisibility(View.INVISIBLE);
        msg3.setVisibility(View.INVISIBLE);
        msg4.setVisibility(View.INVISIBLE);
        msg5.setVisibility(View.INVISIBLE);
        if (randomHeroEntities.size() == 1) {
            msg3.setVisibility(View.VISIBLE);
            count3 = randomHeroEntities.get(0).getUserCount();
        } else if (randomHeroEntities.size() == 3) {
            msg2.setVisibility(View.VISIBLE);
            msg3.setVisibility(View.VISIBLE);
            msg4.setVisibility(View.VISIBLE);
            count2 = randomHeroEntities.get(0).getUserCount();
            count3 = randomHeroEntities.get(1).getUserCount();
            count4 = randomHeroEntities.get(2).getUserCount();
        }
        if (randomHeroEntities.size() == 5) {
            msg1.setVisibility(View.VISIBLE);
            msg2.setVisibility(View.VISIBLE);
            msg3.setVisibility(View.VISIBLE);
            msg4.setVisibility(View.VISIBLE);
            msg5.setVisibility(View.VISIBLE);
            count1 = randomHeroEntities.get(0).getUserCount();
            count2 = randomHeroEntities.get(1).getUserCount();
            count3 = randomHeroEntities.get(2).getUserCount();
            count4 = randomHeroEntities.get(3).getUserCount();
            count5 = randomHeroEntities.get(4).getUserCount();
        }

        ViewCacheManager<LinearLayout> cacheManager = new ViewCacheManager<>();
        cacheManager.setOnCacheListener(new ViewCacheManager.onCacheListener<HeroCard>() {
            @Override
            public View onAddView(int position) {
                HeroCard card = new HeroCard(context);
                return card;
            }

            @Override
            public void onRemoveView(int position) {

            }

            @Override
            public void onBindView(int position, HeroCard mView) {
                mView.setData(randomHeroEntities.get(position));
                mView.clearAnimation();
                mView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_in));
                STZBManager.bindView(randomHeroEntities.get(position).getId(), mView.headimg);
            }
        });
        cacheManager.onRefresh(cardLayout, randomHeroEntities.size());
        cardLayout.startLayoutAnimation();
        onRandomResult();
    }

    @Override
    public void onChanged() {
        refreshUserInfo();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UserManager.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_UserActivity) {
                reSetStyle();
            }
        }
        System.gc();
    }


    @Override
    public void showWaitDialog() {

    }

    @Override
    public void hideWaitDialog() {

    }
}
