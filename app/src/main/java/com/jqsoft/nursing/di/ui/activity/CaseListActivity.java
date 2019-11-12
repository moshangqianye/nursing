package com.jqsoft.nursing.di.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.fragment.CaseListFragment;

/**
 * @author yedong
 * @date 2019/1/22
 * 老人病例列表页面
 */
public class CaseListActivity extends AbstractActivity {


    private String mElderID;

    /**
     * 跳转
     *
     * @param context
     * @param elderID 老人Id
     */
    public static void start(Context context, String elderID) {
        Intent intent = new Intent(context, CaseListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ElderID", elderID);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_list;
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mElderID = extras.getString("ElderID");
        }
    }

    @Override
    protected void initView() {
        CaseListFragment fragment = CaseListFragment.newInstance(CaseListFragment.TYPE_ONE,mElderID);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_show,fragment).commitAllowingStateLoss();
    }

    @Override
    protected void loadData() {

    }
}
