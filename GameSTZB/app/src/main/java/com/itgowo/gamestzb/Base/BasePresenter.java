package com.itgowo.gamestzb.Base;

import android.content.Context;

public class BasePresenter {

    public Context context;

    public BasePresenter(Context context) {
        this.context = context;
    }
    public interface onActionListener{
        void showWaitDialog();
        void hideWaitDialog();
    }
}
