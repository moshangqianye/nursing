package com.jqsoft.nursing.di.ui.activity.nursing;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.NursingBean;
import com.jqsoft.nursing.bean.nursing.NursingElderBean;
import com.jqsoft.nursing.bean.nursing.NursingTaskBean;
import com.jqsoft.nursing.bean.nursing.NursingTaskNewBean;
import com.jqsoft.nursing.di.contract.nursing.NursingDetailActivityContract;
import com.jqsoft.nursing.di.module.nursing.NursingDetailActivityModule;
import com.jqsoft.nursing.di.presenter.nursing.NursingDetailActivityPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.fragment.CaseListFragment;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di.ui.fragment.nursing.NursingDetailTasksFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.util.tts.TTSWrapper;
import com.jqsoft.nursing.utils.GlideUtils;
import com.jqsoft.nursing.utils.LogUtil;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.jqsoft.nursing.utils3.util.StringUtils;
import com.jqsoft.nursing.view.ZQImageViewRoundOval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 护理详情页面
 * Created by Administrator on 2018-04-08.
 */

public class NursingDetailActivity extends AbstractActivity implements NursingDetailActivityContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_patient_name_gender_age)
    TextView tvPatientNameGenderAge;
    @BindView(R.id.tv_nursing_level)
    TextView tvNursingLevel;
    @BindView(R.id.tv_patient_address)
    TextView tvPatientAddress;
    @BindView(R.id.civ_nurse_head)
    ZQImageViewRoundOval civNurseHead;
    @BindView(R.id.tv_nurse_name)
    TextView tvNurseName;
    @BindView(R.id.tv_nurse_department)
    TextView tvNurseDepartment;
    @BindView(R.id.tv_nurse_address)
    TextView tvNurseAddress;
    @BindView(R.id.tv_appointment_time)
    TextView tvAppointmentTime;
    @BindView(R.id.civ_call)
    ZQImageViewRoundOval civCall;
    @BindView(R.id.tl_tab_layout)
    TabLayout tlTabLayout;
    @BindView(R.id.vp_view_pager)
    ViewPager vpViewPager;

    @BindView(R.id.tv_save_all)
    TextView tv_save_all;

    private String mElderID;

    private List<AbstractFragment> fragmentList = new ArrayList<>();
    public final static String[] mTitles = {
            "进行中", "已完成", "全部", "病例信息"
    };
    public static int FRAGMENT_COUNT = mTitles.length;

    String bedId;
    NursingBean nursingBean;

    List<NursingTaskBean> allList;

    NursingDetailTasksFragment activeFragment;
    int activePosition;

    public boolean hasAuthorizedByNfc = false;

    //    TTSController mTtsManager;
    AudioManager audioManager;

    @Inject
    NursingDetailActivityPresenter mPresenter;
    private AbstractFragment fragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nursing_detail;
    }

    @Override
    protected void initData() {
        initDataFromIntent();
    }

    private void initDataFromIntent() {
        bedId = getDeliveredStringByKey(Constants.NURSING_BED_ID_KEY);
        nursingBean = (NursingBean) getDeliveredParcelableByKey(Constants.NURSING_BEAN_KEY);
        hasAuthorizedByNfc = getDeliveredBooleanByKey(Constants.NURSING_IS_FROM_NFC_KEY);
//        if (!hasAuthorizedByNfc){
//            tv_save_all.setVisibility(View.GONE);
//        } else {
//            tv_save_all.setVisibility(View.VISIBLE);
//        }
        if (nursingBean != null) {
            mElderID = nursingBean.getElderInfoID();
        }
        allList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        initViewFromIntent();
    }

    private void initViewFromIntent() {
        setToolBar(toolbar, Constants.EMPTY_STRING);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        Util.makeApplicationShowInLockedMode(this, true);

//        initTtsController();


        initTabLayoutAndViewPager();

//        loadInfo();


        tv_save_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<NursingTaskBean> undolist = new ArrayList<NursingTaskBean>();
                undolist.clear();
                for (int i = 0; i < allList.size(); i++) {
                    if (("1").equals(allList.get(i).getIsExcute())) {

                    } else {
                        undolist.add(allList.get(i));
                    }
                }

                if (undolist.size() == 0) {
                    Toast.makeText(getApplicationContext(), "进行中的项目为空", Toast.LENGTH_LONG).show();

                } else {

                    MaterialDialog dialog = new MaterialDialog.Builder(NursingDetailActivity.this)
                            .title(R.string.hint_suggestion)
                            .content("是否确认保存全部?")
                            .positiveText(R.string.confirm)
                            .negativeText(R.string.cancel)
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();


                                }
                            })
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();

                                    Map<String, String> map = getEndNursingTasknewRequestMap(undolist);
                                    mPresenter.endNursingTask(map);
                                }
                            }).build();

                    dialog.setCancelable(false);
                    dialog.show();


                }


            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initDataFromIntent();
        initViewFromIntent();
    }

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        public void onAudioFocusChange(int focusChange) {

            switch (focusChange) {

                case AudioManager.AUDIOFOCUS_GAIN:

                    LogUtil.i("AudioFocusChange AUDIOFOCUS_GAIN");
                    abandonAudioFocus();
//                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC),
//                            0);

                    break;


                case AudioManager.AUDIOFOCUS_LOSS:

                    LogUtil.i("AudioFocusChange AUDIOFOCUS_LOSS");


                    break;

                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:

                    LogUtil.i("AudioFocusChange AUDIOFOCUS_LOSS_TRANSIENT");


                    break;

                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:

                    LogUtil.i("AudioFocusChange AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");


                    break;

                default:

                    LogUtil.i("AudioFocusChange focus = " + focusChange);

                    break;

            }

        }

    };

    public void requestAudioFocus(String s) {

        int result = audioManager.requestAudioFocus(afChangeListener,

                AudioManager.STREAM_MUSIC, // Use the music stream.

                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

            speak(s);

        } else {

            LogUtil.i("AudioManager request Audio Focus result = " + result);
            Util.showToast(this, "请求播放语音失败");
        }


    }

    private void abandonAudioFocus() {

        LogUtil.i("abandonAudioFocus ");
        audioManager.abandonAudioFocus(afChangeListener);

    }

    public void speak(String s) {
        TTSWrapper.getInstance(this).speak(s);

//        if (mTtsManager!=null) {
//            mTtsManager.startSpeaking(s);
//        }

    }

    private void initPatientAndNurseInfo(NursingElderBean nursingBean) {
        if (nursingBean != null) {
            tvPatientNameGenderAge.setText("长者姓名 : " + Util.trimString(nursingBean.getElderName()) + Constants.SPACE_STRING +
                    Util.trimString(nursingBean.getElderSex()) + Constants.SPACE_STRING + Util.trimString(nursingBean.getElderAge()) + "岁");
            tvNursingLevel.setText("护理级别 : " + Util.trimString(nursingBean.getNurseLevelName()));
            tvPatientAddress.setText("所属房间 : " + "楼栋" + Constants.SPACE_STRING + Util.trimString(nursingBean.getBuildingNO() + Constants.SPACE_STRING +
                    "房间" + Constants.SPACE_STRING + Util.trimString(nursingBean.getRoomNumber() + Constants.SPACE_STRING +
                    "床位" + Constants.SPACE_STRING + Util.trimString(nursingBean.getBedNO()))));
        } else {
            tvPatientNameGenderAge.setText("长者姓名 : ");
            tvNursingLevel.setText("护理级别 : ");
            tvPatientAddress.setText("所属房间 : ");
        }

        civNurseHead.setType(ZQImageViewRoundOval.TYPE_CIRCLE);
        String nurseHeadImg = IdentityManager.getHeadImg(this);
        GlideUtils.loadImageWithPlaceholderAndError(civNurseHead, nurseHeadImg, R.mipmap.icon_touxiang, R.mipmap.icon_touxiang);
        tvNurseName.setText("医护:" + Util.trimString(IdentityManager.getTrueName(this)));
//        tvNurseName.setText("医护:"+Util.trimString(nursingBean.getParamedic()));
        String orgName = IdentityManager.getOrgnizationName(this);
        String ultimateOrgName = Constants.EMPTY_STRING;
        if (StringUtils.isBlank(orgName)) {

        } else {
            ultimateOrgName = Constants.LEFT_PARENTHESIS + orgName +
                    Constants.RIGHT_PARENTHESIS;
        }
        tvNurseDepartment.setText(ultimateOrgName);
        tvNurseAddress.setText("地址:"/*+Util.trimString(Constants.EMPTY_STRING)*/);
        tvAppointmentTime.setText("预约上门时间:"/*+Util.trimString(Constants.EMPTY_STRING)*/);

//        civCall.setType(ZQImageViewRoundOval.TYPE_CIRCLE);
//        Util.setViewClickListener(civCall, new Runnable() {
//            @Override
//            public void run() {
////                Util.showToast(NursingDetailActivity.this, "按钮被点击");
//            }
//        });
    }

    private void initTabLayoutAndViewPager() {
        fragmentList.clear();
        for (int i = 0; i < mTitles.length; i++) {
            if (i == mTitles.length - 1) {
                fragment = CaseListFragment.newInstance(CaseListFragment.TYPE_ZERO, mElderID);
            } else {
                fragment = new NursingDetailTasksFragment();
            }
            fragmentList.add(fragment);
        }

        MyPagerAdapter viewPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vpViewPager.setAdapter(viewPagerAdapter);
        vpViewPager.setOffscreenPageLimit(Constants.OFFSCREEN_PAGE_LIMIT);

        tlTabLayout.addTab(tlTabLayout.newTab().setText(mTitles[0]));
        tlTabLayout.addTab(tlTabLayout.newTab().setText(mTitles[1]));
        tlTabLayout.addTab(tlTabLayout.newTab().setText(mTitles[2]));
        tlTabLayout.addTab(tlTabLayout.newTab().setText(mTitles[3]));

        tlTabLayout.setupWithViewPager(vpViewPager);
    }

    @Override
    protected void onStartInvoked() {
        super.onStartInvoked();
//        loadInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mTtsManager.stopSpeaking();
    }

//    private void initTtsController(){
//        mTtsManager = TTSController.getInstance(getApplicationContext());
//        mTtsManager.init();
//
//    }


    public void loadInfo() {
//        LogUtil.i("NursingDetailActivity loadInfo");
        Map<String, String> map = getNursingListRequestMap();
        mPresenter.main(map);

    }

    private Map<String, String> getNursingListRequestMap() {
        String userId = IdentityManager.getUserId(this);
        Map<String, String> map = ParametersFactory.getNursingTaskListMap(this, userId, bedId);
        return map;
    }

    private Map<String, String> getEndNursingTaskRequestMap(NursingTaskBean item) {
        String userId = IdentityManager.getUserId(this);
        String detailId = Util.trimString(item.getServicePlanDetailsID());
        Map<String, String> map = ParametersFactory.getEndNursingTaskMap(this, userId, detailId);
        return map;
    }

    private Map<String, String> getEndNursingTasknewRequestMap(List<NursingTaskBean> item) {
        String userId = IdentityManager.getUserId(this);
//        String detailId = Util.trimString(item.getServicePlanDetailsID());
        Map<String, String> map = ParametersFactory.getEndNursingTasknewMap(this, userId, item);
        return map;
    }


    public void endNursing(NursingDetailTasksFragment nursingDetailTasksFragment, int position, NursingTaskBean item) {
//        if (!hasAuthorizedByNfc){
//            Util.showToast(this, "请刷NFC卡后再提交护理任务");
//        } else {
//            activeFragment = nursingDetailTasksFragment;
//            activePosition = position;
//
//            Map<String, String> map = getEndNursingTaskRequestMap(item);
//            mPresenter.endNursingTask(map);
//        }


        activeFragment = nursingDetailTasksFragment;
        activePosition = position;

        Map<String, String> map = getEndNursingTaskRequestMap(item);
        mPresenter.endNursingTask(map);

    }


    @Override
    public void onLoadListSuccess(HttpResultNurseBaseBean<NursingTaskNewBean> bean) {
        List<NursingTaskBean> taskBeanList = new ArrayList<>();
        taskBeanList = bean.getData().getList();

        int listSize = getListSizeFromResult(taskBeanList);
        List<NursingTaskBean> list = getListFromResult(taskBeanList);

        allList.clear();
        if (list != null) {
            allList.addAll(list);
        }
        sortAllList();

        showSuccess(allList);

        NursingElderBean nursingBean = new NursingElderBean();
        nursingBean = bean.getData().getElder();
        mElderID = nursingBean.getElderInfoID();
        if (fragment instanceof CaseListFragment){
            ((CaseListFragment) fragment).setData(mElderID);
        }
        initPatientAndNurseInfo(nursingBean);


//        showFailure();

//        simulate();

    }

    @Override
    public void onLoadListFailure(String message) {

        showFailure();
        Util.showToast(this, Constants.HINT_LOADING_DATA_FAILURE);
//        simulate();
    }

    @Override
    public void onEndNursingTaskSuccess(HttpResultNurseBaseBean<List<HttpResultEmptyBean>> bean) {
        handleEndNursingTaskSuccess();
    }

    @Override
    public void onEndNursingTaskFailure(String msg) {
//        handleEndNursingTaskSuccess();

        Util.showToast(this, "护理任务完成失败");
    }

    private void handleEndNursingTaskSuccess() {
        try {
//            activePosition=0;

            NursingTaskBean activeBean = activeFragment.getNursingList().get(activePosition);
            activeBean.setIsExcute(Constants.NURSING_TASK_STATUS_DONE);

            sortAllList();
            showSuccess(allList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Util.showToast(this, "护理任务成功完成");
        loadInfo();
    }

    private void sortAllList() {
        Collections.sort(allList, new Comparator<NursingTaskBean>() {
            @Override
            public int compare(NursingTaskBean o1, NursingTaskBean o2) {
                boolean isLeftUndone = Util.isNursingTaskUndone(o1.getIsExcute());
                boolean isRightUndone = Util.isNursingTaskUndone(o2.getIsExcute());
                if (isLeftUndone && !isRightUndone) {
                    return -1;
                } else if (!isLeftUndone && isRightUndone) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    private void showSuccess(List<NursingTaskBean> list) {
        for (int i = 0; i < fragmentList.size(); ++i) {
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof NursingDetailTasksFragment) {
                NursingDetailTasksFragment ndtf = (NursingDetailTasksFragment) fragment;
                if (i == 2) {
                    ndtf.showNursingRecyclerViewOrFailureView(list, true, ListUtils.isEmpty(list));
                } else if (i == 0) {
                    List<NursingTaskBean> undoneList = getUndoneList(list);
                    ndtf.showNursingRecyclerViewOrFailureView(undoneList, true, ListUtils.isEmpty(undoneList));
                } else if (i == 1) {
                    List<NursingTaskBean> doneList = getDoneList(list);
                    ndtf.showNursingRecyclerViewOrFailureView(doneList, true, ListUtils.isEmpty(doneList));
                }
                ndtf.setLoadMoreStatus();
            }
        }

    }

    private void showFailure() {
        for (AbstractFragment fragment : fragmentList) {
            if (fragment instanceof NursingDetailTasksFragment) {
                NursingDetailTasksFragment ndtf = (NursingDetailTasksFragment) fragment;
                ndtf.showNursingRecyclerViewOrFailureView(null, false, true);
                ndtf.setLoadMoreStatus();
            }
        }

    }

    private void simulate() {
        List<NursingTaskBean> list = new ArrayList<>();
        for (int i = 0; i < 12; ++i) {
            String isExecute = (i % 2 == 0) ? "0" : "1";
            NursingTaskBean ntb = new NursingTaskBean("1", "1", "洗衣" + i, "1", "8:00", "12:00", "100.0", "这是一个很长的备注",
                    isExecute, "2018-04-10", "张三", "a.png");
            list.add(ntb);
        }
        allList.clear();
        allList.addAll(list);
        sortAllList();
        showSuccess(allList);
    }

    private List<NursingTaskBean> getUndoneList(List<NursingTaskBean> list) {
        List<NursingTaskBean> result = new ArrayList<>();
        if (!ListUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); ++i) {
                NursingTaskBean ntb = list.get(i);
                String executeStatus = ntb.getIsExcute();
                if (Util.isNursingTaskUndone(executeStatus)) {
                    result.add(ntb);
                } else {
                    continue;
                }
            }
        }
        return result;
    }

    private List<NursingTaskBean> getDoneList(List<NursingTaskBean> list) {
        List<NursingTaskBean> result = new ArrayList<>();
        if (!ListUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); ++i) {
                NursingTaskBean ntb = list.get(i);
                String executeStatus = ntb.getIsExcute();
                if (Util.isNursingTaskDone(executeStatus)) {
                    result.add(ntb);
                } else {
                    continue;
                }
            }
        }
        return result;
    }

    public List<NursingTaskBean> getListFromResult(List<NursingTaskBean> beanList) {
        if (beanList != null) {
            List<NursingTaskBean> list = beanList;
            return list;
        } else {
            return null;
        }
    }


    public int getListSizeFromResult(List<NursingTaskBean> beanList) {
        if (beanList != null) {
            List<NursingTaskBean> list = beanList;
            if (list != null) {
                int size = ListUtils.getSize(list);
                return size;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }


    @Override
    protected void loadData() {
        loadInfo();
    }

    @Override
    protected void initInject() {
        super.initInject();
        DaggerApplication.get(this)
                .getAppComponent()
                .addNursingDetailActivity(new NursingDetailActivityModule(this))
                .inject(this);
    }

    @Override
    protected void onStop() {
        abandonAudioFocus();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Util.makeApplicationShowInLockedMode(this, false);

        FRAGMENT_COUNT = mTitles.length;
//        mTtsManager.destroy();

        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        //    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    }

}
