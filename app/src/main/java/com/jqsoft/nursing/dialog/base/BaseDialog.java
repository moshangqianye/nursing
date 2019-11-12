package com.jqsoft.nursing.dialog.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jqsoft.nursing.util.Util;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-03-06.
 */

public abstract class BaseDialog extends Dialog {
    protected Context context;
    protected int layout;
    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public BaseDialog(Context context, int theme, int layout){
        super(context, theme);
        this.context=context;
        this.layout=layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        ButterKnife.bind(this);

        init();
        setDialogWidthAndGravity();

        initData();
        initView();
    }

    private void init(){
        setCanceledOnTouchOutside(true);
    }

    private void setDialogWidthAndGravity(){
        Util.setDialogFillWidth((Activity) context, this);
        Util.placeDialogAtBottom(this);

    }

    @NonNull
    @Override
    public Bundle onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public abstract void initData();
    public abstract void initView();
}
