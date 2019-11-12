package com.jqsoft.nursing.listener;

import android.view.View;

import com.jqsoft.nursing.utils.LogUtil;

public abstract class NoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 3000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
//        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            LogUtil.i("last/current click time:"+lastClickTime+"/"+currentTime);
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    public void onNoDoubleClick(View v) {

    }
}