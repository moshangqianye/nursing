package com.jqsoft.nursing.di.ui.fragment;

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
import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.di.ui.activity.nursing.RoundRoomDetailActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
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

public class NursingFragment extends AbstractFragment implements   SocialAssistanceObjectActivityContract.View {
    @Inject
    RoundRoomFragmentPresenter mPresenter;

    @BindView(R.id.tv_scan)
    TextView tvScanQrcode;


    //    @BindView(R.id.stl_nursing)
//    SlidingTabLayout stlNursing;
//    @BindView(R.id.vp_content)
//    ViewPager vpContent;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    private final int REQUEST_CODE=100;


//    private ArrayList<AbstractFragment> mFragments = new ArrayList<>();
//    private final String[] mTitles = {
//            "全部", "进行中", "已完成"
//    };
//    private int VIEW_PAGER_INITIAL_INDEX=0;
//    private MyPagerAdapter mAdapter;

    private void gotoScanQrcode(){
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

//        Intent intent = new Intent(getActivity(), ZxingActivity.class);
//        startActivityForResult(intent, IntentConstant.REQUESTCODE_SCAN_QR_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            case IntentConstant.REQUESTCODE_SCAN_QR_CODE:
//                if (data!=null){
//                    String result = data.getStringExtra(IntentConstant.EXTRANAME_QR_CODE_TEXT);
//                    getQrcodeInfo(result);
////                    gotoDetailActivityByQrcode(result);
//                }
//                break;
            case REQUEST_CODE:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        Util.showToast(getActivity(), "扫描结果为空");
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        getQrcodeInfo(result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Util.showToast(getActivity(), "解析二维码失败");
                    } else {
                        Util.showToast(getActivity(), "解析二维码失败");
                    }
                } else {
                    Util.showToast(getActivity(), "扫描结果为空");
                }
                break;
        }
    }

    private void gotoDetailActivityByQrcode(String readResult){
        readResult=Util.trimString(readResult);
        Bundle bundle = new Bundle();
        String[] readRoomResult = readResult.split(",");
//            if (readRoomResult!=null && readRoomResult.length>=3) {
////                bundle.putString(Constants.NURSING_READ_RESULT_KEY, readResult);
//                bundle.putString(Constants.NURSING_BED_ID_KEY, readRoomResult[2]);
//                bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, isFromNfc);
//                Util.gotoActivityWithBundle(this, NursingDetailActivity.class, bundle);
//            }else if(readRoomResult){
//
//            }
        if (readRoomResult[0].equals("0")) {

            bundle.putString(Constants.NURSING_BED_ID_KEY, readRoomResult[2]);
            bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, true);
            Util.gotoActivityWithBundle(getActivity(), NursingDetailActivity.class, bundle);
        } else if (readRoomResult[0].equals("1")) {
            bundle.putString("roomid", readRoomResult[1]);
            bundle.putString("roomNo", readRoomResult[2]);
            bundle.putString("roomType", readRoomResult[3]);
            bundle.putString("roomExtra", "");
            bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, true);

            Util.gotoActivityWithBundle(getActivity(), RoundRoomDetailActivity.class, bundle);
        }
    }

    private void getQrcodeInfo(String qrcode){
        Map<String, String> map = getQrcodeRequestMap(qrcode);
        mPresenter.getQrcodeInfo(map, false);

    }

    public Map<String, String> getQrcodeRequestMap(String qrcode) {
        String userId = IdentityManager.getUserId(getActivity());
        Map<String, String> map = ParametersFactory.getQrcodeInfo(getActivity(), userId, qrcode);
        return map;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nursing_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvScanQrcode.setVisibility(View.GONE);
        Util.setViewClickListener(tvScanQrcode, new Runnable() {
            @Override
            public void run() {
                gotoScanQrcode();
            }
        });


        initFragments();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initInject() {
        DaggerApplication.get(getActivity())
                .getAppComponent()
                .addRoundRoomFragment(new RoundRoomFramentModule(this))
                .inject(this);
    }

    private void initFragments(){
        Util.replaceIdWithFragment(getActivity(), R.id.fl_content, new SpecificNursingFragment());

//        for (String title : mTitles) {
//            mFragments.add(new SpecificNursingFragment());
//        }
//
//
//        mAdapter = new MyPagerAdapter(getChildFragmentManager());
//        vpContent.setAdapter(mAdapter);
//
//        stlNursing.setViewPager(vpContent);
//        vpContent.setOffscreenPageLimit(Constants.OFFSCREEN_PAGE_LIMIT);
//        vpContent.setCurrentItem(VIEW_PAGER_INITIAL_INDEX);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        if (bean!=null) {
            gotoDetailActivityByQrcode(bean.getData());
        }
    }

    @Override
    public void onLoadMoreQrcodeSuccess(HttpResultNurseBaseBean<String> bean) {

    }

    @Override
    public void onLoadQrcodeFailure(String message, boolean isLoadMore) {
        Util.showToast(getActivity(), "扫码失败，请重试！");
    }

    @Override
    public void onLoadmacSuccess(HttpResultNurseBaseBean<String> bean) {

    }

    @Override
    public void onLoadmacFailure(String message, boolean isLoadMore) {

    }


//    private class MyPagerAdapter extends FragmentPagerAdapter {
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mTitles[position];
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//    }
}
