package com.itgowo.gamestzb;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.IOException;

public class MusicService extends Service {
    public static final String MUSIC_URL = "music url";
    public static final String MUSIC_ACTION = "music action";
    public static final String MUSIC_ACTION_PLAY = "music action play";
    public static final String MUSIC_ACTION_STOP = "music action stop";
    public String musicUrl;
    private MediaPlayer musicPlayer;

    public MusicService() {
    }

    public static void playMusic(Context context, @Nullable String url) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MUSIC_ACTION, MUSIC_ACTION_PLAY);
        intent.putExtra(MUSIC_URL, url);
        context.startService(intent);
    }

    public static void stopMusic(Context context) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MUSIC_ACTION, MUSIC_ACTION_STOP);
        context.startService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void doAction(Intent intent) {
        if (intent == null) {
            return;
        }
        String temp = intent.getStringExtra(MUSIC_URL);
        if (!TextUtils.isEmpty(temp)) {
            musicUrl = temp;
        }
        if (musicPlayer != null) {
            if (musicPlayer.isPlaying()) {
                musicPlayer.stop();
            }
            musicPlayer.stop();
            musicPlayer.release();
        }
        if (MUSIC_ACTION_PLAY.equals(intent.getStringExtra(MUSIC_ACTION))) {
            musicPlayer = getDefaultMediaPlayer(temp, new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.start();
                }
            });
        } else {
            stopSelf();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        doAction(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private MediaPlayer getDefaultMediaPlayer(String url, MediaPlayer.OnPreparedListener listener) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            if (TextUtils.isEmpty(url)) {
                mediaPlayer.setDataSource(getApplication(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.wangchaodansheng));
                mediaPlayer.setOnPreparedListener(listener);
                mediaPlayer.prepare();
            } else {
                mediaPlayer.setDataSource(url);
                mediaPlayer.setOnPreparedListener(listener);
                mediaPlayer.prepareAsync();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }
}
