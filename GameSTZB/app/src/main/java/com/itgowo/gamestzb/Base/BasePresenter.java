package com.itgowo.gamestzb.Base;

import android.content.Context;

public class BasePresenter {

    public BaseActivity context;

    public BasePresenter(BaseActivity context) {
        this.context = context;
    }
    public interface onActionListener{
        void showWaitDialog();
        void hideWaitDialog();
    }
}
