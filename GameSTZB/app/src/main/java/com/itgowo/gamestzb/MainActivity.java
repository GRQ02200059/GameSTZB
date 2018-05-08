package com.itgowo.gamestzb;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.itgowo.gamestzb.Base.BaseActivity;
import com.itgowo.gamestzb.Base.BaseConfig;
import com.itgowo.gamestzb.Entity.BaseResponse;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.Entity.UpdateVersion;
import com.itgowo.gamestzb.Manager.NetManager;
import com.itgowo.gamestzb.Manager.STZBManager;
import com.itgowo.gamestzb.Manager.UserManager;
import com.itgowo.gamestzb.View.FillVideoView;
import com.itgowo.gamestzb.View.HeroCard;
import com.itgowo.itgowolib.itgowoNetTool;
import com.itgowo.views.SuperDialog;

import java.util.ArrayList;
import java.util.List;

import library.Info;
import library.PhotoView;

public class MainActivity extends BaseActivity implements UserManager.onUserStatusListener {
    private STZBManager manager = new STZBManager();
    private View rootLayout;
    private RecyclerView recyclerView;
    private ImageButton fab;
    private FloatingActionButton fabNotice;
    private List<HeroEntity> randomHeroEntities = new ArrayList<>();
    private TextView msg5;
    private TextView msg4;
    private TextView msg3;
    private TextView msg2;
    private TextView msg1;
    private FrameLayout videoRoot;
    private int count5, count4, count3, count2, count1;
    private View mParent;
    private View mBg;
    private PhotoView mPhotoView;
    private Info mInfo;
    private int spanCount = 8;
    private AlphaAnimation in = new AlphaAnimation(0, 1);
    private AlphaAnimation out = new AlphaAnimation(1, 0);
    private VideoView videoView1;
    private HeroCard view_UserInfo;
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
        initView();
        initLstener();
        initRecyclerView();
        start();
        checkVersion();
//        handler.sendEmptyMessageDelayed(1, 2000);

    }

    private void initLstener() {
        in.setDuration(200);
        out.setDuration(200);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view_UserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivityForResult(intent, INTENT_UserActivity);
            }
        });
        findViewById(R.id.helpDev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperDialog dialog = new SuperDialog(context).setShowImage().setImageListener(new SuperDialog.onDialogImageListener() {
                    @Override
                    public void onInitImageView(ImageView imageView) {
                        Glide.with(imageView).load("http://file.itgowo.com/game/pay/allpay.png").into(imageView);
                    }
                }).setShowButtonLayout(false);
                dialog.setAspectRatio(0.8f).show();
            }
        });
        fabNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseConfig.updateInfo == null) {
                    return;
                }
               String string= "是是是\r\n   \r\n sss";
                String tip = String.format(getResources().getString(R.string.versionTip), BuildConfig.VERSION_NAME, BaseConfig.updateInfo.getVersionname(), BaseConfig.updateInfo.getVersioninfo());
                SuperDialog dialog = new SuperDialog(context).setTitle("发现新版本").setContent(tip).setListener(new SuperDialog.onDialogClickListener() {
                    @Override
                    public void click(boolean isButtonClick, int position) {
                        try {
                            Uri uri = Uri.parse(BaseConfig.updateInfo.getDownloadurl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mParent.getVisibility() == View.VISIBLE) {
                    mBg.startAnimation(out);
                    mPhotoView.animaTo(mInfo, new Runnable() {
                        @Override
                        public void run() {
                            mParent.setVisibility(View.GONE);
                        }
                    });
                }
                SuperDialog dialog = new SuperDialog(MainActivity.this);
                List<SuperDialog.DialogMenuItem> menuItems = new ArrayList<>();
                menuItems.add(new SuperDialog.DialogMenuItem("小试牛刀(1)", R.mipmap.caocao));
                menuItems.add(new SuperDialog.DialogMenuItem("大胆尝试(5)", R.mipmap.liubei));
                menuItems.add(new SuperDialog.DialogMenuItem("疯狂剁手(15)", R.mipmap.sunquan));
                dialog.setTitle("选择抽取次数").setDialogMenuItemList(menuItems).setListener(new SuperDialog.onDialogClickListener() {
                    @Override
                    public void click(boolean isButtonClick, int position) {
                        if (position == 0) {
                            goodluck(1);
                        } else if (position == 1) {
                            goodluck(5);
                        } else {
                            goodluck(15);
                        }
                    }
                }).setAspectRatio(0.3f).show();

            }
        });
    }

    private void checkVersion() {
        NetManager.getUpdateInfo(new itgowoNetTool.onReceviceDataListener<BaseResponse<UpdateVersion>>() {

            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<UpdateVersion> result) {
                if (result.getCode() == 1) {
                    if (result.getData().getVersioncode() > BuildConfig.VERSION_CODE) {
                        fabNotice.show();
                        BaseConfig.updateInfo = result.getData();
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
        if (videoView1 != null) {
            videoView1.pause();
        }
        super.onPause();
        UserManager.removeUserStatusListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView1 != null) {
            videoView1.start();
        }
        view_UserInfo.refreshInfo();
        UserManager.addUserStatusListener(this);
    }

    private void reSetStyle() {
        if (!BaseConfig.getData(BaseConfig.USER_ISPLAYVIDEO, true)) {
            rootLayout.setBackgroundResource(R.drawable.background2);
            if (videoView1 != null) {
                if (videoView1.isPlaying()) {
                    videoView1.stopPlayback();
                }
                videoView1.setVisibility(View.GONE);
                videoRoot.removeAllViews();
                videoView1 = null;
            }
        } else {
            rootLayout.setBackground(null);
            videoView1 = new FillVideoView(this);
            videoRoot.addView(videoView1);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cg_1);
            videoView1.setVideoURI(uri);
            videoView1.setClickable(false);
            videoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

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

    private void start() {
        if (!BaseConfig.getData(BaseConfig.USER_ISPLAYVIDEO, true)) {
            rootLayout.setVisibility(View.VISIBLE);
            rootLayout.setBackgroundResource(R.drawable.background2);
            ObjectAnimator anim = ObjectAnimator.ofFloat(rootLayout, "alpha", 0f, 0.2f, 0.3f, 0.5f, 1f);
            anim.setDuration(1200);// 动画持续时间
            anim.start();
            return;
        }
        videoView1 = new FillVideoView(this);
        videoRoot.addView(videoView1);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cg_1);
        videoView1.setVideoURI(uri);
        videoView1.setClickable(false);
        videoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                mPlayer.start();
                mPlayer.setLooping(true);
                videoView1.setClickable(false);
                rootLayout.setVisibility(View.VISIBLE);
                ObjectAnimator anim = ObjectAnimator.ofFloat(rootLayout, "alpha", 0f, 0.2f, 0.3f, 0.5f, 1f);
                anim.setDuration(1200);// 动画持续时间
                anim.start();
            }
        });
        videoView1.start();
        if (BaseConfig.getData(BaseConfig.USER_ISPLAYMUSIC, true)) {
            MusicService.playMusic(this, null);
        } else {
            MusicService.stopMusic(this);
        }
    }

    private void initView() {
        view_UserInfo = findViewById(R.id.userInfo);
        view_UserInfo.setLargerMode();
        recyclerView = findViewById(R.id.recyclerview);
        msg5 = (TextView) findViewById(R.id.msg5);
        msg4 = (TextView) findViewById(R.id.msg4);
        msg3 = (TextView) findViewById(R.id.msg3);
        msg2 = (TextView) findViewById(R.id.msg2);
        msg1 = (TextView) findViewById(R.id.msg1);
        mParent = findViewById(R.id.parent);
        videoView1 = findViewById(R.id.videoview);
        rootLayout = findViewById(R.id.rootlayout);
        mBg = findViewById(R.id.bg);
        mPhotoView = (PhotoView) findViewById(R.id.img);
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        videoRoot = findViewById(R.id.videoRoot);
        fab = findViewById(R.id.fab);
        fabNotice = findViewById(R.id.fabNotice);
    }

    private void initRecyclerView() {
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.anim_layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        recyclerView.setAdapter(new Myadapter());
        mPhotoView.enable();
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBg.startAnimation(out);
                mPhotoView.animaTo(mInfo, new Runnable() {
                    @Override
                    public void run() {
                        mParent.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public boolean isWifiConnect() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    private void onRandomResult() {
        msg5.setText("5 星：" + count5);
        msg4.setText("4 星：" + count4);
        msg3.setText("3 星：" + count3);
        msg2.setText("2 星：" + count2);
        msg1.setText("1 星：" + count1);
    }

    /**
     * 0-45  5星
     * 46-134  4星
     * 135-219 3星
     * <p>
     */
    private void goodluck(int num) {
        NetManager.getRandomHero(num, new itgowoNetTool.onReceviceDataListener<BaseResponse<List<HeroEntity>>>() {

            @Override
            public void onResult(String requestStr, String responseStr, BaseResponse<List<HeroEntity>> result) {
                randomHeroEntities = result.getData();
                recyclerView.getAdapter().notifyDataSetChanged();
                for (int i = 0; i < randomHeroEntities.size(); i++) {
                    switch (randomHeroEntities.get(i).getQuality()) {
                        case 1:
                            count1++;
                            break;
                        case 2:
                            count2++;
                            break;
                        case 3:
                            count3++;
                            break;
                        case 4:
                            count4++;
                            break;
                        case 5:
                            count5++;
                            break;
                    }
                }
                onRandomResult();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });


    }

    public void showHeroDialog(Context context) {
        if (randomHeroEntities == null || randomHeroEntities.size() == 0) {
            return;
        }
        final HeroEntity entity = randomHeroEntities.remove(0);
        SuperDialog dialog = new SuperDialog(MainActivity.this).setShowButtonLayout(false);
        dialog.setImageListener(new SuperDialog.onDialogImageListener() {
            @Override
            public void onInitImageView(ImageView imageView) {
                RequestOptions options = new RequestOptions().dontAnimate().dontTransform();
                if (entity.getId() < 600000) {
                    final int res = getResources().getIdentifier("hero_" + entity.getId(), "drawable", getPackageName());
                    Glide.with(imageView).load(res).apply(options).into(imageView);
                } else {
                    Glide.with(imageView).load(entity.getIcon()).apply(options).into(imageView);
                }
            }
        }).setTitleTextSize(17)
                .setContent("恭喜主公喜获 " + entity.getQuality() + "星 " + entity.getContory() + " " + entity.getType() + " " + entity.getName())
                .setTitle("枫林制作，仅供娱乐");
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (entity.getQuality() == 3) {
                    count3++;
                    return;
                }
                if (entity.getQuality() == 4) {
                    count4++;
                }
                if (entity.getQuality() == 5) {
                    count5++;
                }
                if (entity.getQuality() == 2) {
                    count2++;
                }
                if (entity.getQuality() == 1) {
                    count1++;
                }
                onRandomResult();
                showHeroDialog(context);
            }
        });
        dialog.show();
    }

    @Override
    public void onChanged() {
        view_UserInfo.refreshInfo();
    }

    class Myadapter extends RecyclerView.Adapter<viewHolder> {


        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            HeroCard heroCard = new HeroCard(parent.getContext());
            heroCard.setMiniMode();
            return new viewHolder(heroCard);
        }

        @SuppressLint("NewApi")
        @Override
        public void onBindViewHolder(final viewHolder holder, final int position) {
            final HeroEntity entity = randomHeroEntities.get(position);
            final HeroCard heroCard = (HeroCard) holder.itemView;
            heroCard.setData(entity);
            int width = recyclerView.getWidth() / spanCount;
            int height = width * 410 / 300;
            PhotoView p = (PhotoView) heroCard.headimg;
            p.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            p.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // 把PhotoView当普通的控件把触摸功能关掉
            p.disenable();
            final RequestOptions options = new RequestOptions().dontTransform().dontAnimate().format(DecodeFormat.PREFER_RGB_565);
//            Glide.with(holder.itemView).load(entity.getSrc()).apply(options).into(p);
            final int res = getResources().getIdentifier(entity.getIconName(), "drawable", getPackageName());
            Glide.with(heroCard.headimg).load(res).apply(options).into(p);
            heroCard.headimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PhotoView p = (PhotoView) v;
                    mInfo = p.getInfo();
                    Glide.with(heroCard.headimg).load(res).apply(options).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            mPhotoView.setImageDrawable(resource);
                            mPhotoView.setScaleType(ImageView.ScaleType.CENTER);
                            mBg.startAnimation(in);
                            mBg.setVisibility(View.VISIBLE);
                            mParent.setVisibility(View.VISIBLE);
                            mPhotoView.animaFrom(mInfo);
                        }
                    });
                }
            });


        }

        @Override
        public int getItemCount() {
            return randomHeroEntities == null ? 0 : randomHeroEntities.size();
        }
    }

    class viewHolder extends RecyclerView.ViewHolder {
        public viewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBackPressed() {
        if (mParent.getVisibility() == View.VISIBLE) {
            mBg.startAnimation(out);
            mPhotoView.animaTo(mInfo, new Runnable() {
                @Override
                public void run() {
                    mParent.setVisibility(View.GONE);
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UserManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_UserActivity) {
            view_UserInfo.refreshInfo();
            reSetStyle();
        }
        System.gc();
    }

}
