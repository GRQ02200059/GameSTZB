package com.itgowo.gamestzb;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static void setupShortcuts() {
        ShortcutManager mShortcutManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            mShortcutManager = BaseApp.app.getSystemService(ShortcutManager.class);
            List<ShortcutInfo> infos = new ArrayList<>();
            for (int i = 0; i < mShortcutManager.getMaxShortcutCountPerActivity(); i++) {
                Intent intent = new Intent(BaseApp.app, MainActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra("msg", "我和" + i + "的对话");

                ShortcutInfo info = new ShortcutInfo.Builder(BaseApp.app, "id" + i)
                        .setShortLabel(i + "aaa")
                        .setLongLabel("联系人:" + i)
                        .setIcon(Icon.createWithResource(BaseApp.app, R.mipmap.ic_launcher))
                        .setIntent(intent)
                        .build();
                infos.add(info);
//            manager.addDynamicShortcuts(Arrays.asList(info));
            }

            mShortcutManager.setDynamicShortcuts(infos);
        }
    }

    public static String ReadFile2String(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String temp = reader.readLine();
        return temp;
    }

    public static void download(final File file, final String url) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(file.getAbsolutePath());
        requestParams.setMultipart(true);
        x.http().get(requestParams, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                System.out.println("download:" + file.getName() + "   " + url);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                System.out.println("downloaderror:" + file.getName() + "   " + url);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    public static boolean hasNotificationPermission(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        boolean isOpened = manager.areNotificationsEnabled();
        return isOpened;
    }
    public static void intentDetailsSettingActivity(Context context){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
