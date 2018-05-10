package com.itgowo.gamestzb.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class RepeatImageView extends android.support.v7.widget.AppCompatImageView {
    private boolean orientationLandscape = true;
    private int repeatCount = 0;

    public RepeatImageView(Context context) {
        super(context);
        setRepeatCount(5);
    }

    public RepeatImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRepeatCount(5);
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public RepeatImageView setRepeatCount(int repeatCount) {
        if (repeatCount < 1) {
            this.repeatCount = 1;
        } else {
            this.repeatCount = repeatCount;
        }
        return this;
    }

    public boolean isOrientationLandscape() {
        return orientationLandscape;
    }

    public RepeatImageView setOrientationLandscape(boolean orientationLandscape) {
        this.orientationLandscape = orientationLandscape;
        return this;
    }

    public void setWidthOrHeightWithoutRepeat(int widthOrHeightWithoutRepeat) {
        if (orientationLandscape) {
            getLayoutParams().height = widthOrHeightWithoutRepeat;
        } else {
            getLayoutParams().width = widthOrHeightWithoutRepeat;
        }
    }

    public void setImage(@Nullable Drawable drawable, float zoom) {
        Bitmap zoomBitmap = zoomDrawable(drawable, zoom, zoom);
        int width, height = 0;
        if (orientationLandscape) {
            width = zoomBitmap.getWidth() * repeatCount;
            height = zoomBitmap.getHeight();
        } else {
            width = zoomBitmap.getWidth();
            height = zoomBitmap.getHeight() * repeatCount;
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);
        for (int i = 0; i < repeatCount; i++) {
            if (orientationLandscape) {
                canvas.drawBitmap(zoomBitmap, zoomBitmap.getWidth() * i, 0, null);
            } else {
                canvas.drawBitmap(zoomBitmap, 0, zoomBitmap.getHeight() * i, null);
            }
        }
        setImageBitmap(bitmap);
    }

    static Bitmap zoomDrawable(Drawable drawable, float scaleWidth, float scaleHeight) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap
        Matrix matrix = new Matrix();   // 创建操作图片用的 Matrix 对象
        matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        return newbmp;       // 把 bitmap 转换成 drawable 并返回
    }

    static Bitmap drawableToBitmap(Drawable drawable) { // drawable 转换成 bitmap
        int width = drawable.getIntrinsicWidth();   // 取 drawable 的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;         // 取 drawable 的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // 建立对应 bitmap
        Canvas canvas = new Canvas(bitmap);         // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);      // 把 drawable 内容画到画布中
        return bitmap;
    }
}
