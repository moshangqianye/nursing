package com.jqsoft.nursing.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jqsoft.nursing.R;

public final class EvaluateDialog extends Dialog {
    public EvaluateDialog(@NonNull Context context) {
        super(context);
    }

    public EvaluateDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected EvaluateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

//    private Button mStoreBtn;
//    private Button mFeedbackBtn;
//    private Button mCancelBtn;
//
//    private Button btn_si;
//
//
//    private TextView mTitle;
//    private boolean mIsShowTitle = false;
//
//    private OnStoreListener mOnStoreListener;
//    private OnFeedbackListener mOnFeedbackListener;
//    private OnCancelListener mOnCancelListener;
//    private OnsiListener mOnsiListener;
//
//
//    public EvaluateDialog(Context context) {
//        super(context);
//    }
//
//    public EvaluateDialog(Context context, int theme) {
//        super(context, theme);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_evaluate);
//
//        initData();
//        initView();
//    }
//
//    private void initData() {
//        setCancelable(true);  // 设置当返回键按下是否关闭对话框
//        setCanceledOnTouchOutside(true);  // 设置当点击对话框以外区域是否关闭对话框
//    }
//
//    private void initView() {
//        mStoreBtn = (Button) findViewById(R.id.goto_store_btn);
//        mFeedbackBtn = (Button) findViewById(R.id.goto_feedback_btn);
//        mCancelBtn = (Button) findViewById(R.id.cancel_btn);
//        btn_si = (Button) findViewById(R.id.btn_si);
//        mTitle=(TextView)findViewById(R.id.title1);
//
//        mStoreBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnStoreListener != null) {
//                    mOnStoreListener.onStore(v);
//                    dismiss();
//                }
//            }
//        });
//        mFeedbackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ( mOnFeedbackListener != null) {
//                    mOnFeedbackListener.onFeedback(v);
//                    dismiss();
//                }
//            }
//        });
//        mCancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnCancelListener != null) {
//                    mOnCancelListener.onCancel(v);
//                    dismiss();
//                }
//            }
//        });
//
//        btn_si.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnsiListener != null) {
//                    mOnsiListener.onsi(v);
//                    dismiss();
//                }
//            }
//        });
//
//
//    }
//
//    /**
//     * 设置标题
//     * @param title
//     * @return
//     */
//    public EvaluateDialog setTitle(String title) {
//        if (TextUtils.isEmpty(title)) {
//            mTitle.setText("提示");
//        } else {
//            mTitle.setText(title);
//        }
//        return this;
//    }
//
//    public OnStoreListener getOnStoreListener() {
//        return mOnStoreListener;
//    }
//
//    public void setOnStoreListener(OnStoreListener onStoreListener) {
//        this.mOnStoreListener = onStoreListener;
//    }
//
//    public OnFeedbackListener getOnFeedbackListener() {
//        return mOnFeedbackListener;
//    }
//
//    public void setOnFeedbackListener(OnFeedbackListener onFeedbackListener) {
//        this.mOnFeedbackListener = onFeedbackListener;
//    }
//
//    public OnCancelListener getOnCancelListener() {
//        return mOnCancelListener;
//    }
//
//    public void setOnCancelListener(OnCancelListener onCancelListener) {
//        this.mOnCancelListener = onCancelListener;
//    }
//
//    public void setOnsibackListener(OnsiListener onsibackListener) {
//        this.mOnsiListener = onsibackListener;
//    }
//
//    public interface OnStoreListener {
//        public void onStore(View v);
//    }
//
//    public interface OnFeedbackListener {
//        public void onFeedback(View v);
//    }
//
//    public interface OnCancelListener {
//        public void onCancel(View v);
//    }
//
//    public interface OnsiListener {
//        public void onsi(View v);
//    }
//
//

}
