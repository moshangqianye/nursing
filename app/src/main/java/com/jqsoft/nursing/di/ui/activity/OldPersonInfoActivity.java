package com.jqsoft.nursing.di.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;

/**
 * @author yedong
 * @date 2019/1/17
 * 老人信息
 */
public class OldPersonInfoActivity extends AbstractActivity implements View.OnClickListener {

    private String mOldName;  // 老人姓名
    private String mElderID;   // 老人Id
    private View ll_back;  // 返回

    /**
     * 跳转
     *
     * @param context
     * @param oldName 老人姓名
     * @param elderID 老人Id
     */
    public static void start(Context context, String oldName, String elderID) {
        Intent intent = new Intent(context, OldPersonInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("oldName", oldName);
        bundle.putString("elderID", elderID);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_person_info;
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mOldName = extras.getString("oldName");
            mElderID = extras.getString("elderID");
        }
    }

    @Override
    protected void initView() {
        TextView tv_title = (TextView) findViewById(R.id.nursing_title);
        findViewById(R.id.tv_scan).setVisibility(View.GONE);
        ll_back = findViewById(R.id.ll_back);
        ll_back.setVisibility(View.VISIBLE);
        ll_back.setOnClickListener(this);
        if (!TextUtils.isEmpty(mOldName)) {
            tv_title.setText(mOldName);
        }

        findViewById(R.id.ll_base_info).setOnClickListener(this);
        findViewById(R.id.ll_case_info).setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_base_info:
                // 基本信息

                break;
            case R.id.ll_case_info:
                // 病例信息
                CaseListActivity.start(this, mElderID);
                break;
        }
    }

}
