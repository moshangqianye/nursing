package com.jqsoft.livebody_verify_lib.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dalong.rotaryballview.RotaryBallView;
import com.jqsoft.livebody_verify_lib.R;

/**
 * Created by Administrator on 2017-06-06.
 */

public class LoadingUtil {
    private RotaryBallView mRotaryBallView;
    private Context context;

    private static LoadingUtil loadingUtil = null;
//    private MaterialDialog dialog;
    public Dialog dialog;
    private View view;

    private TextView loadingTextView;

    public LoadingUtil(Context context) {
        this.context=context;
       instantiateDialog();
    }

    public void instantiateDialog(){
        if (dialog==null){
            if (view==null){
                view= LayoutInflater.from(context).inflate(R.layout.layout_loading_lib, null, false);
            }
//            dialog = new MaterialDialog.Builder(context)
//                    .backgroundColorRes(android.R.color.transparent)
//                    .title(Constants.EMPTY_STRING)
////                    .content(Constants.EMPTY_STRING)
//                    .customView(view, false)
//                    .negativeText(Constants.EMPTY_STRING)
//                    .positiveText(Constants.EMPTY_STRING)
//                    .build();
            dialog=new Dialog(context, R.style.DialogStyle);
            dialog.setContentView(view);
//            dialog.setCancelable(false);
//            dialog.setCanceledOnTouchOutside(false);
            Util.setDialogWidthHeight((Activity)context, dialog, 0.80f, 0.60f);
//            Util.setDialogWidthHeight((Activity)context, dialog, 0.36f, 0.12f);
//            Util.setDialogWidthHeight(dialog, 180, 180);
//            dialog.getTitleView().setVisibility(View.GONE);
//            dialog.getContentView().setVisibility(View.GONE);
//            Util.setDialogWidthAndHeight(dialog, Constants.LOADING_DIALOG_WIDTH, Constants.LOADING_DIALOG_HEIGHT);


            mRotaryBallView=(RotaryBallView)dialog.findViewById(R.id.mRotaryBallView);
            mRotaryBallView.setMaxRadius(30);
            mRotaryBallView.setDistance(60);

            loadingTextView=(TextView)dialog.findViewById(R.id.tv_text);

//            if (mRotaryBallView ==null){
//                mRotaryBallView =new RotaryBallView(context);
//
//            }

        }
    }

    public void setLoadingText(String s){
        s=Util.trimString(s);
        if (TextUtils.isEmpty(s)){
            loadingTextView.setVisibility(View.GONE);
        } else {
            loadingTextView.setVisibility(View.VISIBLE);
        }
        loadingTextView.setText(s);
    }


    public void setOnDismissListener(DialogInterface.OnDismissListener dismissListener){
        dialog.setOnDismissListener(dismissListener);
    }

    public static LoadingUtil getInstance(Context context) {
//        if (loadingUtil==null){
//            synchronized (LoadingUtil.class){
                if (loadingUtil==null){
                    loadingUtil=new LoadingUtil(context);
                }
//            }
//        }
        return loadingUtil;
    }

    public void show(){
        instantiateDialog();
        dialog.show();
        mRotaryBallView.startAnimator();
    }

    public void hide(){
        try {
            if(dialog!=null &&dialog.isShowing()){
                dialog.dismiss();
                mRotaryBallView.stopAnimator();
                dialog=null;
                view=null;
                mRotaryBallView=null;
                loadingUtil=null;
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            dialog = null;
            view=null;
            mRotaryBallView=null;
            loadingUtil=null;
        }
    }

    public boolean isShowing(){
        if (dialog!=null && dialog.isShowing()){
            return true;
        } else {
            return false;
        }
    }
}
