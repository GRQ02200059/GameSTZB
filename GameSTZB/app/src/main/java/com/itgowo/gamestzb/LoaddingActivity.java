package com.itgowo.gamestzb;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class LoaddingActivity extends AppCompatActivity {
    private VideoView videoView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        step1();

    }

    private void step1() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cg_1);
        videoView1.setVideoURI(uri);
        videoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mPlayer) {
                mPlayer.start();
                mPlayer.setLooping(true);
                videoView1.setClickable(true);
            }
        });
        videoView1.start();
        videoView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step2();
            }
        });
    }

    private void step2() {
        videoView1.setClickable(false);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cg_2);
        videoView1.setVideoURI(uri);
        videoView1.setVisibility(View.VISIBLE);
        videoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
                Intent intent = new Intent(LoaddingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        videoView1.start();
    }


    private void initView() {
        videoView1 = new VideoView(this);
        getWindow().addContentView(videoView1, new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
