package com.itgowo.gamestzb;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Toast;

import com.itgowo.gamestzb.Base.BaseApp;
import com.itgowo.gamestzb.Main.MainActivity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static android.content.Context.CONNECTIVITY_SERVICE;

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
                        .setIcon(Icon.createWithResource(BaseApp.app, R.mipmap.zhugeliang))
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


    public static void checkPermission(Context context, PermissionCallback callback) {
        HiPermission hiPermission = HiPermission.create(context);
        List<PermissionItem> permissionItems = new ArrayList<>();
        permissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "外部存储", R.drawable.permission_ic_storage));
        permissionItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_location));
        permissionItems.add(new PermissionItem(Manifest.permission.READ_PHONE_STATE, "手机IMEI", R.drawable.permission_ic_phone));
        permissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "相机", R.drawable.permission_ic_camera));
        hiPermission.permissions(permissionItems).msg("为了APP能提供更好的服务，需要以下权限").title("枫林提示")
                .style(R.style.PermissionDefaultNormalStyle).checkMutiPermission(callback);
    }

    public static boolean isWifiConnect() {
        ConnectivityManager connManager = (ConnectivityManager) BaseApp.app.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

    public static boolean hasNotificationPermission(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        boolean isOpened = manager.areNotificationsEnabled();
        return isOpened;
    }

    public static void intentDetailsSettingActivity(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void showToastShort(String msg) {
        if (Looper.myLooper() == null) {
            Looper.prepare();
            Toast.makeText(BaseApp.app, msg, Toast.LENGTH_SHORT);
            Looper.loop();
        } else {
            Toast.makeText(BaseApp.app, msg, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 按指定大小缩放图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap bitmap_ResizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    /**
     * 获取view截图
     *
     * @param v
     * @return
     */
    public static Bitmap bitmap_getViewBackground(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(), (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return b;
    }

    /**
     * 将bitmap压缩保存到文件中,整型compress表示压缩率，如果填100，表示不压缩，填80，表示压缩20%
     *
     * @param bmp
     * @param file
     * @param mFileMaxSize 最大文件大小,建议300,单位KB
     */
    public static void bitmap_CompressBmpToFile(Bitmap bmp, File file, int mFileMaxSize) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;// 个人喜欢从80开始,此处的80表示压缩率，表示压缩20%，如果不压缩，就是100，表示压缩率是0
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
            while (baos.toByteArray().length / 1024 > mFileMaxSize) {
                baos.reset();
                options -= 10;
                bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
//    public static void bitmap_CompressBmpToFile(final Bitmap bmp, final File file, final int mFileMaxSize, final onCompleteListener mListener) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                bitmap_CompressBmpToFile(bmp,file,mFileMaxSize);
//                app_runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mListener.onComplete("");
//                    }
//                });
//            }
//        }).start();
//    }
}
