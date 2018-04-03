package com.itgowo.gamestzb;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.itgowo.gamestzb.Entity.HeroEntity;
import com.itgowo.gamestzb.Entity.SimpleEntity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import library.Info;
import library.PhotoView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SimpleEntity> simpleEntities = new ArrayList<>();
    private List<HeroEntity> heroEntities = new ArrayList<>();
    private Random random = new Random(System.currentTimeMillis());
    private int height, width;
    private TextView msg5;
    private TextView msg4;
    private TextView msg3;
    private int count5, count4, count3, num, seed = 6;
    private View mParent;
    private View mBg;
    private PhotoView mPhotoView;
    private Info mInfo;

    private AlphaAnimation in = new AlphaAnimation(0, 1);
    private AlphaAnimation out = new AlphaAnimation(1, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.Ext.init(getApplication());
        setContentView(R.layout.activity_main);
        initView();
        init();
        if (isWifiConnect()) {
            initRecyclerView();
        } else {
            SuperDialog superDialog = new SuperDialog(this).setTitle("枫林提醒")
                    .setContent("当前使用的不是wifi网络，为了防止流量过多浪费，默认禁止使用数据网络，是否允许使用数据网络下载图片？")
                    .setButtonTexts("允许使用", "我要省流量").setListener(new SuperDialog.onDialogClickListener() {
                        @Override
                        public void click(boolean isButtonClick, int position) {
                            if (isButtonClick && position == 0) {
                                initRecyclerView();
                            }
                        }
                    });
            superDialog.show();
        }

        try {
            String temp = Utils.ReadFile2String(getResources().openRawResource(R.raw.simpledata));
            simpleEntities = JSON.parseArray(temp, SimpleEntity.class);
            for (int i = 0; i < heroEntities.size(); i++) {
                SimpleEntity entity = simpleEntities.get(i);
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Simple/" + entity.getEncodeName() + ".jpg");
                Utils.download(file, entity.getSrc());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        msg5 = (TextView) findViewById(R.id.msg5);
        msg4 = (TextView) findViewById(R.id.msg4);
        msg3 = (TextView) findViewById(R.id.msg3);
        mParent = findViewById(R.id.parent);
        mBg = findViewById(R.id.bg);
        mPhotoView = (PhotoView) findViewById(R.id.img);
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        width = getResources().getDisplayMetrics().widthPixels / 5;
        height = width * 410 / 300;
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

    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
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

    private void init() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
                menuItems.add(new SuperDialog.DialogMenuItem("小试牛刀(1)", R.mipmap.ic_launcher_round));
                menuItems.add(new SuperDialog.DialogMenuItem("大胆尝试(5)", R.mipmap.liubei_round));
                menuItems.add(new SuperDialog.DialogMenuItem("疯狂剁手(15)", R.mipmap.sunquan_round));
                dialog.setTitle("选择抽取次数").setDialogMenuItemList(menuItems).setListener(new SuperDialog.onDialogClickListener() {
                    @Override
                    public void click(boolean isButtonClick, int position) {
                        if (position == 0) {
                            num = 1;
                            goodluck();
                        } else if (position == 1) {
                            num = 5;
                            goodluck();
                        } else {
                            num = 15;
                            goodluck();
                        }
                        RequestParams params = new RequestParams("http://itgowo.com/game/stzb");
                        x.http().get(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                JSONObject object = JSON.parseObject(result);
                                seed = object.getIntValue("seed");
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                ex.printStackTrace();
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }
                }).show();

            }
        });
        try {
            String data;
            InputStream inputStream = getResources().openRawResource(R.raw.simpledata);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            data = new String(bytes);
            simpleEntities = JSON.parseArray(data, SimpleEntity.class);
            Collections.sort(simpleEntities);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void onResult() {
        msg5.setText("5 星：" + count5);
        msg4.setText("4 星：" + count4);
        msg3.setText("3 星：" + count3);
    }

    /**
     * 0-45  5星
     * 46-134  4星
     * 135-219 3星
     * <p>
     */
    private void goodluck() {
        if (num <= 0) {
            onResult();
            return;
        }
        num--;
        SimpleEntity entity;
        int temp = random.nextInt(30);
        if (temp < seed) {
            entity = simpleEntities.get(random.nextInt(221));
        } else if (temp < seed * 2) {
            entity = simpleEntities.get(48 + random.nextInt(173));
        } else {
            entity = simpleEntities.get(137 + random.nextInt(84));
        }
        if (entity.getLevel() == 3) {
            count3++;
            goodluck();
            return;
        }
        if (entity.getLevel() == 4) {
            count4++;
        }
        if (entity.getLevel() == 5) {
            count5++;
        }
        final String src = entity.getSrc();
        SuperDialog dialog = new SuperDialog(MainActivity.this).setShowButtonLayout(false);
        dialog.setImageListener(new SuperDialog.onDialogImageListener() {
            @Override
            public void onInitImageView(ImageView imageView) {
                RequestOptions options = new RequestOptions().dontAnimate().dontTransform();
                Glide.with(imageView).load(src).apply(options).into(imageView);
            }
        }).setTitleTextSize(17).setContent("恭喜主公喜获 " + entity.getLevel() + "星 " + entity.getCountry() + " " + entity.getCn_name()).setTitle("枫林制作，仅供娱乐").show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                goodluck();
            }
        });
    }

    class Myadapter extends RecyclerView.Adapter<viewHolder> {


        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            PhotoView p = new PhotoView(parent.getContext());
            return new viewHolder(p);
        }

        @Override
        public void onBindViewHolder(final viewHolder holder, final int position) {
            final PhotoView p = (PhotoView) holder.itemView;
            p.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            p.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // 把PhotoView当普通的控件把触摸功能关掉
            p.disenable();
            final HeroEntity entity = heroEntities.get(position);
            final RequestOptions options = new RequestOptions().dontTransform().dontAnimate().format(DecodeFormat.PREFER_RGB_565);
//            Glide.with(holder.itemView).load(entity.getSrc()).apply(options).into(p);
            final int res = getResources().getIdentifier("hero_" + entity.getId(), "drawable", getPackageName());
            Glide.with(holder.itemView).load(res).apply(options).into(p);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PhotoView p = (PhotoView) v;
                    mInfo = p.getInfo();
                    Glide.with(holder.itemView).load(res).apply(options).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            mPhotoView.setImageDrawable(resource);
                            mPhotoView.setScaleType(ImageView.ScaleType.FIT_XY);
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
            return heroEntities.size();
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
}
