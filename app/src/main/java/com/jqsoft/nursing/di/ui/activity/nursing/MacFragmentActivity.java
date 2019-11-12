package com.jqsoft.nursing.di.ui.activity.nursing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.BuildingRoomFloorBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.RoundsRecordCount;
import com.jqsoft.nursing.di.contract.SocialAssistanceObjectActivityContract;
import com.jqsoft.nursing.di.module.RoundRoomFramentModule;
import com.jqsoft.nursing.di.presenter.RoundRoomFragmentPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di.ui.fragment.nursing.RoundFragmentNew;
import com.jqsoft.nursing.di.ui.fragment.nursing.SpecificNursingFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.util.Util;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 护理fragment
 * Created by Administrator on 2018-04-03.
 */

public class MacFragmentActivity extends AbstractActivity implements   SocialAssistanceObjectActivityContract.View {
    @Inject
    RoundRoomFragmentPresenter mPresenter;

    @BindView(R.id.tv_scan)
    TextView tvScanQrcode;

    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    private final int REQUEST_CODE=100;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mac_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Util.setViewClickListener(tvScanQrcode, new Runnable() {
            @Override
            public void run() {

            }
        });


        initFragments();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initInject() {
//        DaggerApplication.get(getActivity())
//                .getAppComponent()
//                .addRoundRoomFragment(new RoundRoomFramentModule(this))
//                .inject(this);
    }

    private void initFragments(){
        Util.replaceIdWithFragment(MacFragmentActivity.this, R.id.fl_content, new RoundFragmentNew());

    }




    @Override
    public void onLoadListSuccess(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean) {

    }

    @Override
    public void onLoadMoreListSuccess(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean) {

    }

    @Override
    public void onLoadListFailure(String message, boolean isLoadMore) {

    }

    @Override
    public void onLoadRoundsRecordCountSuccess(HttpResultNurseBaseBean<List<RoundsRecordCount>> bean) {

    }

    @Override
    public void onLoadMoreRoundsRecordCountSuccess(HttpResultNurseBaseBean<List<RoundsRecordCount>> bean) {

    }

    @Override
    public void onLoadRoundsRecordCountFailure(String message, boolean isLoadMore) {

    }

    @Override
    public void onLoadQrcodeSuccess(HttpResultNurseBaseBean<String> bean) {

    }


    @Override
    public void onLoadMoreQrcodeSuccess(HttpResultNurseBaseBean<String> bean) {

    }

    @Override
    public void onLoadQrcodeFailure(String message, boolean isLoadMore) {

    }

    @Override
    public void onLoadmacSuccess(HttpResultNurseBaseBean<String> bean) {

    }

    @Override
    public void onLoadmacFailure(String message, boolean isLoadMore) {

    }


}
