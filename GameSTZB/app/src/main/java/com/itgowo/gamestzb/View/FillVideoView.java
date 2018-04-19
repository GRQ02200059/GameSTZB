package com.itgowo.gamestzb.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class FillVideoView extends VideoView {
    public FillVideoView(Context context) {
        super(context);
    }

    public FillVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
