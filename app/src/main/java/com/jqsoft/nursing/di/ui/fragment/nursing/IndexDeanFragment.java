package com.jqsoft.nursing.di.ui.fragment.nursing;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.nursing.ListDropDownAdapter;
import com.jqsoft.nursing.adapter.nursing.MyAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.Identity;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.FunctionImageBean;
import com.jqsoft.nursing.bean.JsData;
import com.jqsoft.nursing.bean.YData;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.MonthTextBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.QuarterTextBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.YearTextBean;
import com.jqsoft.nursing.bean.nursing.IndexElderBean;
import com.jqsoft.nursing.bean.nursing.OrgnizationBean;
import com.jqsoft.nursing.bean.nursing.OrgnizationListAndSuccessBean;
import com.jqsoft.nursing.bean.response.SignAndHonourAgreementResultBean;
import com.jqsoft.nursing.bean.response_new.IntelligentHonourAgreementOverviewResultBean;
import com.jqsoft.nursing.bean.response_new.SignInfoOverviewResultBean;
import com.jqsoft.nursing.di.contract.IndexFragmentContract;
import com.jqsoft.nursing.di.contract.nursing.IndexElderFragmentContract;
import com.jqsoft.nursing.di.module.IndexFragmentModule;
import com.jqsoft.nursing.di.module.nursing.IndexElderFragmentModule;
import com.jqsoft.nursing.di.presenter.IndexFragmentPresenter;
import com.jqsoft.nursing.di.presenter.nursing.IndexElderFragmentPresenter;
import com.jqsoft.nursing.di.ui.activity.WorkbenchActivity;
import com.jqsoft.nursing.di.ui.fragment.AnnualSignInfoOverviewFragmentNew;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.feature.IDateRange;
import com.jqsoft.nursing.listener.NoDoubleClickListener;
import com.jqsoft.nursing.popup_window.MonthQuarterYearRangePopupWindow;
import com.jqsoft.nursing.rx.RxBus;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.LogUtil;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.jqsoft.nursing.view.PagePointView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import bakerj.backgrounddarkpopupwindow.BackgroundDarkPopupWindow;
import butterknife.BindView;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by quantan.liu on 2017/4/12.
 */

public class IndexDeanFragment extends AbstractFragment implements IndexElderFragmentContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.point)
    PagePointView ppvIndicator;
    @BindView(R.id.lay_sign_info_overview_load_failure)
    View laySignInfoOverviewLoadFailure;

    @BindView(R.id.ll_intelligent_overview)
    LinearLayout llIntelligentOverview;
    @BindView(R.id.lay_intelligent_honour_agreement_overview_load_failure)
    View layIntelligentHonourAgreementOverviewLoadFailure;
    @BindView(R.id.lith_my_sign_number)
    View lithMySignNumber;
    @BindView(R.id.lt_intelligent_honour_agreement_remind)
    View ltIntelligentHonourAgreementRemind;
    @BindView(R.id.litnh_latest_7_days_need_execute_projects_number)
    View litnhLatest7DaysNeedExecuteProjectsNumber;
    @BindView(R.id.litnh_timeout_not_execute_projects_number)
    View litnhTimeoutNotExecuteProjectsNumber;
    @BindView(R.id.litnv_service_assess)
    View litnvServiceAssess;
    @BindView(R.id.litnv_sign_application)
    View litnvSignApplication;
    @BindView(R.id.litnv_appointment_sign)
    View litnvAppointmentSign;

    @BindView(R.id.ll_institution_name)
    LinearLayout llInstitutionName;
    @BindView(R.id.tv_institution_name)
    TextView tvInstitutionName;

    @BindView(R.id.sf_refresh)
    SwipeRefreshLayout sf_refresh;


    @BindView(R.id.tv_base_xzcount)
    TextView tv_base_xzcount;

    @BindView(R.id.tv_base_cscount)
    TextView tv_base_cscount;

    @BindView(R.id.pg_base_is)
    ProgressBar pg_base_is;

    @BindView(R.id.pg_base_cs)
    ProgressBar pg_base_cs;

    @BindView(R.id.index_title)
    Spinner index_title;

    @BindView(R.id.fl_layout)
    FrameLayout fl_layout;

    @BindView(R.id.ll_layout)
    LinearLayout ll_layout;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.iv_rili)
    ImageView iv_rili;


    @BindView(R.id.textView5)
    TextView textView5;

    @BindView(R.id.toolbar4)
    Toolbar toolbar4;


    MyPagerAdapter adapter;
    AlertDialog areaDialog;
    private CompositeSubscription areaSelectSubscription;
    private CompositeSubscription functionImageSubscription;
    private CompositeSubscription arrowClickSubscription;
    private CompositeSubscription refreshIntelligentHonourAgreementRemindSubscription;

//    FunctionImageGroupContent imageGroupContent;

    List<Fragment> mFragments = new ArrayList<>();
    List<IndexElderBean> signInfoOverviewList = new ArrayList<>();
    private List<OrgnizationBean> orgnizationList = new ArrayList<>();
    boolean isGetOrgnizationListSuccess = false;
    Runnable runnable;
    String institutionId = Constants.EMPTY_STRING;
    int institutionIdIndex = 0;
    CompositeSubscription mCompositeSubscription;

    private ListDropDownAdapter listDropDownAdapter;

    private MyAdapter adapter_sp;
    // TextView tvFailureView;
    MonthQuarterYearRangePopupWindow dateRangePopupWindow;
    IDateRange selectedDateRange;

    @Inject
    IndexElderFragmentPresenter mPresenter;


    private int index = 0;

    @Override
    public void loadData() {

        runnable = new Runnable() {
            @Override
            public void run() {
                OrgnizationListAndSuccessBean bean = new OrgnizationListAndSuccessBean(getOrgnizationList(), isGetOrgnizationListSuccess);
                RxBus.getDefault().post(Constants.EVENT_TYPE_NURSING_DID_GET_ORGNIZATION_LIST, bean);
            }
        };

        if (this.arrowClickSubscription == null) {
            registerArrowClickEvent();
        }
        registerIntelligentHonourAgreementRemindEvent();

        loadSignInfoOverviewData();
        loadIntelligentHonourAgreementOverviewData();


    }

    @Override
    public void initInject() {
        DaggerApplication.get(this.getActivity())
                .getAppComponent()
                .addIndexElderFragment(new IndexElderFragmentModule(this))
                .inject(this);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        LogUtil.i("IndexDeanFragment onCreate setHasOptionsMenu(true) called");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
//        reassignToolbar();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //LogUtil.i("IndexDeanFragment onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.action_search:
                //code here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initToolbar();
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        if (this.areaSelectSubscription != null && areaSelectSubscription.hasSubscriptions()) {
//            this.areaSelectSubscription.unsubscribe();
//        }
//        if (this.functionImageSubscription != null && functionImageSubscription.hasSubscriptions()) {
//            this.functionImageSubscription.unsubscribe();
//        }
        if (arrowClickSubscription != null && arrowClickSubscription.hasSubscriptions()) {
            arrowClickSubscription.unsubscribe();
        }
        if (refreshIntelligentHonourAgreementRemindSubscription != null && refreshIntelligentHonourAgreementRemindSubscription.hasSubscriptions()) {
            refreshIntelligentHonourAgreementRemindSubscription.unsubscribe();
        }
    }


    public Toolbar initToolbar() {
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.toolbar);
        //LogUtil.i("initToolbar toolbar:" + toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        return toolbar;
    }

    @Override
    public int getLayoutId() {
//        LogUtil.i("IndexDeanFragment getLayoutId is called");
//        setHasOptionsMenu(true);
//        LogUtil.i("setHasOptionsMenu called");
        return R.layout.fragment_index_dean;
    }

    @Override
    public void initData() {
        mFragments.clear();


    }

    public void reassignToolbar() {
        WorkbenchActivity workbenchActivity = (WorkbenchActivity) getActivity();
        if (workbenchActivity != null) {
            Toolbar toolbar = (Toolbar) workbenchActivity.findViewById(R.id.toolbar);
            //LogUtil.i("IndexDeanFragment initView toolbar:" + toolbar);
            workbenchActivity.setToolBarWithNoBackButtonAndNoTitle(toolbar);
        }

    }

    @Override
    public void initView() {
        registerRxBusEvent();


        adapter = new MyPagerAdapter(getFragmentManager());
        vpContent.setAdapter(adapter);
        vpContent.setOffscreenPageLimit(Constants.MAX_OFFSCREEN_PAGE_LIMIT);

        showMySignNumber(0);

        showIntelligentHonourAgreementOverview(null);

        laySignInfoOverviewLoadFailure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
                loadSignInfoOverviewData();
            }
        });

        litnhLatest7DaysNeedExecuteProjectsNumber.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
                //  Util.gotoExecutionProjectsActivity(getActivity(), ExecutionProjectsType.ExecutionProjectsTypeEnum.Latest7Days);
            }
        });

        litnhTimeoutNotExecuteProjectsNumber.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
                //  Util.gotoExecutionProjectsActivity(getActivity(), ExecutionProjectsType.ExecutionProjectsTypeEnum.Timeout);
            }
        });

        litnvServiceAssess.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
//                ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.SIGN_SERVICE_ASSESS_ACTIVITY_NAME);
                // Util.gotoActivity(getActivity(), SignServiceAssessActivity.class);
            }
        });

        litnvSignApplication.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
//                ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.SIGN_APPLICATION_ACTIVITY_NAME);
                // Util.gotoActivity(getActivity(), SignApplicationActivity.class);
            }
        });

        litnvAppointmentSign.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
//                ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.RESERVATION_ACTIVITY_NAME);
                //  Util.gotoActivity(getActivity(), ReservationServiceActivity.class);
            }
        });

        layIntelligentHonourAgreementOverviewLoadFailure.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
                loadIntelligentHonourAgreementOverviewData();
            }
        });

        index_title.setBackgroundColor(Color.parseColor("#067eee"));


        index_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                institutionId = adapter_sp.getItem(position).getsId();
                getElderInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sf_refresh.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        sf_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // getElderInfo();
//                nursingTaskAdapter.setEnableLoadMore(false);
//                loadNursingList();
                loadSignInfoOverviewData();

            }
        });

        initDateRangePopupWindow(Constants.DATE_RANGE_TYPE_MONTH);

        RxView.clicks(iv_rili)
                .throttleFirst(Constants.RXBINDING_THROTTLE, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        dateRangePopupWindow.show();
                    }
                });


    }

    private ListView rootList;
    private ListView childList;
    private BackgroundDarkPopupWindow mPopWin;
    private LinearLayout layout;
    private FrameLayout flChild;
    private LinearLayout ll_mylayout;
    private int saveBuilding = 0;

    private void showPopupWindow(int width, int height) {

        listDropDownAdapter = new ListDropDownAdapter(getActivity(), orgnizationList, saveBuilding);

        layout = (LinearLayout) LayoutInflater.from(getActivity())
                .inflate(R.layout.popup_index, null);
        rootList = (ListView) layout.findViewById(R.id.rootcategory);
        ll_mylayout = (LinearLayout) layout.findViewById(R.id.ll_mylayout);
        rootList.setAdapter(listDropDownAdapter);
        listDropDownAdapter.notifyDataSetChanged();

        mPopWin = new BackgroundDarkPopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        mPopWin.setFocusable(true);
        mPopWin.setBackgroundDrawable(new BitmapDrawable());
        mPopWin.setAnimationStyle(android.R.style.Animation_Dialog);

        mPopWin.setDarkStyle(-1);
        mPopWin.setDarkColor(Color.parseColor("#a0000000"));
        mPopWin.resetDarkPosition();
        mPopWin.darkBelow(fl_layout);
        mPopWin.showAsDropDown(fl_layout, fl_layout.getRight() / 2, 0);

        rootList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                listDropDownAdapter = new ListDropDownAdapter(getActivity(), orgnizationList, position);
                rootList.setAdapter(listDropDownAdapter);
                adapter.notifyDataSetChanged();
                //   index_title.setText(orgnizationList.get(position).getsOrgName());
                saveBuilding = position;


                mPopWin.dismiss();
                institutionId = orgnizationList.get(position).getsId();
                getElderInfo();
            }


        });
    }

    @Override
    public void onLoadDeanCockpitElderListDataSuccess(HttpResultNurseBaseBean<IndexElderBean> bean) {
        sf_refresh.setRefreshing(false);
        if (bean != null) {
            List<IndexElderBean> list = new ArrayList<>();
            IndexElderBean indexElderBean = bean.getData();
            list.add(indexElderBean);
            if (list != null) {
/*
                for(int i=0;i<list.size();i++){
                   list.get(i).save();

                }*/

                showSignInfoOverview(list);
                textView5.setText("护理老人数:" + indexElderBean.getServerelder() + "人");

                if (TextUtils.isEmpty(indexElderBean.getServerelder())) {
                    tv_base_xzcount.setText("0/0");
                    pg_base_is.setMax(0);
                    pg_base_is.setProgress(0);
//                    pg_base_is.setMax(100);
//                    pg_base_is.setProgress(20);
                } else {
                    tv_base_xzcount.setText(indexElderBean.getServerisdo() + "/" + indexElderBean.getServerall());
                    pg_base_is.setMax(Integer.parseInt(indexElderBean.getServerall()));
                    pg_base_is.setProgress(Integer.parseInt(indexElderBean.getServerisdo()));
//                    pg_base_is.setMax(100);
//                    pg_base_is.setProgress(20);
                }

                String sShouldcsCount, sHadcsCount;
                if (TextUtils.isEmpty(indexElderBean.getServerelder())) {
                    tv_base_cscount.setText("0/0");
                    pg_base_cs.setMax(0);
                    pg_base_cs.setProgress(0);
                } else {
                    sShouldcsCount = indexElderBean.getServerundo();
                    sHadcsCount = indexElderBean.getServerall();
                    tv_base_cscount.setText(sShouldcsCount + "/" + sHadcsCount);
                    pg_base_cs.setMax(Integer.parseInt(sHadcsCount));
                    pg_base_cs.setProgress(Integer.parseInt(sShouldcsCount));
                }


                showSignInfoOverviewOrFailureView(true);
            } else {
                showSignInfoOverviewOrFailureView(false);
            }
        } else {
            showSignInfoOverviewOrFailureView(false);
        }
    }

    @Override
    public void onLoadDeanCockpitElderListDataFailure(String message) {

        showSignInfoOverviewOrFailureView(false);
    }


    private void initInstitutionType(int index) {
        String presentation = Constants.EMPTY_STRING;
        String tempInstitutionType = Constants.EMPTY_STRING;
        List<OrgnizationBean> list = getOrgnizationList();
        if (ListUtils.getSize(list) > index) {
            OrgnizationBean ob = list.get(index);
            presentation = ob.getsOrgName();
            tempInstitutionType = ob.getsId();
            tvInstitutionName.setText(presentation);

            institutionId = tempInstitutionType;
            institutionIdIndex = index;


            adapter_sp = new MyAdapter(getActivity(), list);
            index_title.setAdapter(adapter_sp);
            //  adapter.setDatas(list);
        }
    }

    @Override
    public void onLoadOrgnizationListDataSuccess(HttpResultNurseBaseBean<List<OrgnizationBean>> bean) {


        String hint = "机构列表为空";
        isGetOrgnizationListSuccess = true;
        orgnizationList.clear();
        if (bean != null) {
            List<OrgnizationBean> list = bean.getData();
            if (!ListUtils.isEmpty(list)) {
                orgnizationList.addAll(list);
                initInstitutionType(0);
            } else {
                Util.showToast(getActivity(), hint);
            }
        } else {
            Util.showToast(getActivity(), hint);
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override
    public void onLoadOrgnizationListDataFailure(String message) {
        sf_refresh.setRefreshing(false);
        Util.showToast(getActivity(), "获取机构列表失败");
        isGetOrgnizationListSuccess = false;
        orgnizationList.clear();
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override
    public void onRefresh() {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Constants.EMPTY_STRING;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


    public String getSignInfoOverviewString(SignAndHonourAgreementResultBean bean) {
        if (bean == null) {
            return Constants.HINT_YEAR_SIGN_OVERVIEW;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(bean.getClinicName());
            sb.append(bean.getYear());
            sb.append(Constants.HINT_YEAR_SIGN_OVERVIEW);
            return sb.toString();
        }
    }

    private String getOrganizationKey() {
        return IdentityManager.getOrgnizationName(getActivity());
    }

    private String getUserId() {
        return IdentityManager.getUserId(getActivity());
    }

    private String getCategory() {
        return Constants.INTELLIGENT_HONOUR_AGREEMENT_CATEGORY_OVERVIEW;
    }

    public void getOrgnizationListFromNetworkOrGetElderInfo() {
        List<OrgnizationBean> list = getOrgnizationList();
        if (ListUtils.isEmpty(list)) {
            Map<String, String> map = getOrgnizationListRequestMap();
            mPresenter.getOrgnizationList(map);
        } else {
            getElderInfo();
        }
    }

    public void loadSignInfoOverviewData() {
        getOrgnizationListFromNetworkOrGetElderInfo();
//        List<OrgnizationBean> list = getOrgnizationList();
//        if (ListUtils.isEmpty(list)){
//            Map<String, String> map = getOrgnizationListRequestMap();
//            mPresenter.getOrgnizationList(map);
//        } else {
//            getElderInfo();
//        }
//        Map<String, String> map = getDeanCockpitElderListRequestMap();
//        mPresenter.getDeanCockpitElderList(map);
//        String organizationKey = getOrganizationKey();
//        String userId = getUserId();
//        Map<String, String> map = ParametersFactory.getSignInfoOverviewMap(getActivity(), organizationKey, userId);
//        RequestBody body = Util.getBodyFromMap(map);
//        mPresenter.getSignInfoOverview(body);
    }

    private Map<String, String> getOrgnizationListRequestMap() {
        String userId = IdentityManager.getUserId(getActivity());
        Map<String, String> map = ParametersFactory.getOrgnizationListMap(getActivity(), userId);
        return map;
    }


    public List<OrgnizationBean> getOrgnizationList() {
        return orgnizationList;
    }


    private void getElderInfo() {
        Map<String, String> map = getDeanCockpitElderListRequestMap();
        mPresenter.getDeanCockpitElderList(map);

    }

    private Map<String, String> getDeanCockpitElderListRequestMap() {
        String firstDay = "", lastDay = "";
        String userId = IdentityManager.getUserId(getActivity());

        String sdate = tvDate.getText().toString();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String currentDay = year + "-" + month;
        if (sdate.equals(currentDay)) {

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String currentdate = df.format(new Date());

            firstDay = getMonthFirstDay();
            lastDay = currentdate;
        } else {
            firstDay = selectedDateRange.getStartDateString();
            lastDay = selectedDateRange.getEndDateString();


        }


        Map<String, String> map = ParametersFactory.getIndexElderListMap(getActivity(), userId,
                "NurseType", institutionId, firstDay, lastDay);
        return map;
    }

    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    //得到本月的第一天
    public String getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat firstDay = new SimpleDateFormat("yyyy-MM-dd");
        return firstDay.format(calendar.getTime());
    }

    //得到本月的最后一天
    public String getMonthLastDay(String sdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat lastDay = new SimpleDateFormat("yyyy-MM-dd");
        return lastDay.format(calendar.getTime());
    }


    //得到本月的第一天
    public String getMonthFirstDay1(String sdate) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);


        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat firstDay = new SimpleDateFormat("yyyy-MM-dd");
        return firstDay.format(calendar.getTime());
    }

    public void loadIntelligentHonourAgreementOverviewData() {
//        String organizationKey = getOrganizationKey();
//        String userId = getUserId();
//        String category = getCategory();
//        Map<String, String> map = ParametersFactory.getIntelligentHonourAgreementOverviewMap(getActivity(), organizationKey, userId, category);
//        RequestBody body = Util.getBodyFromMap(map);
//        mPresenter.getIntelligentHonourAgreementOverview(body);

    }

    public void refreshIntelligentHonourAgreementRemindInfo() {
        loadIntelligentHonourAgreementOverviewData();
    }

    private List<IndexElderBean> getOrganizationSignInfoOverviewList(List<IndexElderBean> list) {
        List<IndexElderBean> result = new ArrayList<>();
        if (!ListUtils.isEmpty(list)) {
            for (IndexElderBean item : list) {

                result.add(item);

            }
        }
        return result;
    }

    public void showSignInfoOverviewData(List<IndexElderBean> list) {
        if (list != null) {
            this.signInfoOverviewList = list;
            List<IndexElderBean> filteredList = getOrganizationSignInfoOverviewList(list);
            if (ListUtils.isEmpty(mFragments)) {
                for (int i = 0; i < filteredList.size(); ++i) {
                    IndexElderBean item = filteredList.get(i);
                    //                for (int j = 0; j < Constants.NUMBER_OF_SIGN_INFO_OVERVIEW_PER_YEAR; ++j){
                    Bundle args = new Bundle();
                    args.putString(Constants.CONTAINER_TYPE_KEY, tvDate.getText().toString());
                    args.putSerializable(Constants.SIGN_INFO_KEY, item);
                    args.putInt(Constants.TOTAL_SIGN_INFO_OVERVIEW_NUMBER_KEY, filteredList.size());
                    args.putInt(Constants.SIGN_INFO_OVERVIEW_INDEX_KEY, i);
                    //                    args.putString(Constants.CLINIC_NAME_KEY, Identity.getOrganizationName());
                    //                    args.putString(Constants.YEAR_KEY, item.getYear());
                    //                    String count = Constants.EMPTY_STRING;
                    //                    String ratio = Constants.EMPTY_STRING;
                    //                    String hint = Constants.EMPTY_STRING;
                    //                    if (j==0){
                    //                        count=item.getQyPaidCount();
                    //                        ratio=item.getQyPaidLv();
                    //                        hint=Constants.HINT_PAID_SIGN;
                    //                    } else if (j==1){
                    //                        count=item.getQyOldCount();
                    //                        ratio=item.getQyOldLv();
                    //                        hint=Constants.HINT_ELDERLY_PEOPLE;
                    //                    } else if (j==2){
                    //                        count=item.getQyGxyCount();
                    //                        ratio=item.getQyGxyLv();
                    //                        hint=Constants.HINT_HYPERTENSION;
                    //                    } else if (j==3){
                    //                        count=item.getQyTnbCount();
                    //                        ratio=item.getQyTnbLv();
                    //                        hint=Constants.HINT_DIABETES;
                    //                    }
                    //                    args.putString(Constants.TOTAL_FEE_SIGN_NUMBER, count);
                    //                    args.putString(Constants.SIGN_RATIO, ratio);
                    //                    args.putString(Constants.SIGN_INFO_HINT, hint);
                    //                AnnualSignInfoOverviewFragment fragment = new AnnualSignInfoOverviewFragment();
                    AnnualSignInfoOverviewFragmentNew fragment = new AnnualSignInfoOverviewFragmentNew();
                    fragment.setArguments(args);
                    mFragments.add(fragment);

                    //                }

                }
            } else {
                AnnualSignInfoOverviewFragmentNew fragmentNew = (AnnualSignInfoOverviewFragmentNew) mFragments.get(0);
                fragmentNew.setData(filteredList.get(0), tvDate.getText().toString());
            }
            vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    showMySignNumber(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            adapter.notifyDataSetChanged();
            ppvIndicator.setViewPager(vpContent);
        }

    }

    public void showMySignNumber(int position) {
        String signNumber = getMySignNumber(signInfoOverviewList, position);
        String mySignNumber = String.format(Locale.CHINA, getResources().getString(R.string.my_elder_number), signNumber);
        Util.setImageTextForViewHorizontal(lithMySignNumber, R.mipmap.i_index_doctor, mySignNumber);

    }

    public String getMySignNumber(List<IndexElderBean> list, int index) {
        String result = Constants.ZERO_STRING;
        if (!ListUtils.isEmpty(list)) {
            IndexElderBean bean = list.get(index);
            //  String year = bean.getYear();
            for (IndexElderBean item : list) {
                //  String type = item.getType();
                //    String someYear = Util.trimString(item.getYear());
                result = item.getElderin();
//                if (Constants.TYPE_CURRENT_DOCTOR.equals(type) && someYear.equals(year)) {
//                    result = item.getQyCount();
//                    break;
//                }
            }
        }
        return result;
    }

    public void showSignInfoOverview(List<IndexElderBean> list) {
        if (list != null) {
            this.signInfoOverviewList = list;
//            List<SignInfoOverviewResultBean> list = bean.getList();
            if (!ListUtils.isEmpty(list)) {
                showMySignNumber(0);

                showSignInfoOverviewData(list);
            }
        } else {
            this.signInfoOverviewList = new ArrayList<>();
        }
//        String signInfoOverviewString = getSignInfoOverviewString(bean);
//        tvSignInfoOverview.setText(signInfoOverviewString);
//        tvHonourAgreementHint.setText(Constants.HINT_INTELLIGENT_HONOUR_AGREEMENT_ALERT);
//        if (bean!=null){
//            List<SignNumberAndRatioBean> list = bean.getSignNumberAndRatioList();
//            if (!ListUtils.isEmpty(list)){
//                for (SignNumberAndRatioBean item : list){
//                    SignNumberAndRatioContent content = new SignNumberAndRatioContent(getActivity());
//                    content.initView(item);
//                    llSignInfoDetail.addView(content.getView());
//                }
//            }
//            String mySignInfo = Constants.HINT_NUMBER_OF_MY_SIGN+bean.getMySignedNumber()+Constants.PERSON;
//            tvSignInfoMe.setText(mySignInfo);
//
//            ImageView latest7DayImageView = getImageViewFromView(litLatest7Days);
//            TextView latest7DayTextView = getTextViewFromView(litLatest7Days);
//            latest7DayImageView.setImageResource(R.mipmap.person);
//            String latest7DayText = Constants.HINT_NUMBER_OF_PROJECTS_LATEST_7_DAYS+bean.getNumberOfProjectsNeedToExecute()+Constants.PERSON_CI;
//            latest7DayTextView.setText(latest7DayText);
//            litLatest7Days.setOnClickListener(new NoDoubleClickListener() {
//                @Override
//                public void onNoDoubleClick(View v) {
//                    super.onNoDoubleClick(v);
//                    Util.gotoExecutionProjectsActivity(getActivity(), ExecutionProjectsType.ExecutionProjectsTypeEnum.Latest7Days);
//                }
//            });
//
//            ImageView timeoutImageView = getImageViewFromView(litTimeout);
//            TextView timeoutTextView = getTextViewFromView(litTimeout);
//            timeoutImageView.setImageResource(R.mipmap.person);
//            String timeoutText = Constants.HINT_NUMBER_OF_PROJECTS_TIMEOUT+bean.getNumberOfProjectsTimeout()+Constants.PERSON_CI;
//            timeoutTextView.setText(timeoutText);
//            litTimeout.setOnClickListener(new NoDoubleClickListener() {
//                @Override
//                public void onNoDoubleClick(View v) {
//                    super.onNoDoubleClick(v);
//                    Util.gotoExecutionProjectsActivity(getActivity(), ExecutionProjectsType.ExecutionProjectsTypeEnum.Timeout);
//                }
//            });
//
//            ImageView newServiceAssessImageView = getImageViewFromView(litServiceAssess);
//            TextView newServiceAssessTextView = getTextViewFromView(litServiceAssess);
//            newServiceAssessImageView.setImageResource(R.mipmap.person);
//            String newServiceAssessText = Constants.YOU_HAVE+bean.getNumberOfNewServiceAssess()+Constants.HINT_NEW_SERVICE_ASSESS_INFO;
//            newServiceAssessTextView.setText(newServiceAssessText);
//
//            ImageView newSignApplicationImageView = getImageViewFromView(litSignApplication);
//            TextView newSignApplicationTextView = getTextViewFromView(litSignApplication);
//            newSignApplicationImageView.setImageResource(R.mipmap.person);
//            String newSignApplicationText = Constants.YOU_HAVE+bean.getNumberOfNewSignedApplication()+Constants.HINT_NEW_SIGN_APPLICATION_INFO;
//            newSignApplicationTextView.setText(newSignApplicationText);
//
//            ImageView appointmentSignImageView = getImageViewFromView(litAppointmentSign);
//            TextView appointmentSignTextView = getTextViewFromView(litAppointmentSign);
//            appointmentSignImageView.setImageResource(R.mipmap.person);
//            String appointmentSignText = Constants.YOU_HAVE+bean.getNumberOfAppointmentSignServiceService()+Constants.HINT_APPOINTMENT_SIGN_SERVICE_INFO;
//            appointmentSignTextView.setText(appointmentSignText);
//        } else {
//
//        }
    }

    public void showSignInfoOverviewOrFailureView(boolean success) {
        sf_refresh.setRefreshing(false);
        if (success) {
//            vpContent.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.VISIBLE);
            laySignInfoOverviewLoadFailure.setVisibility(View.GONE);
        } else {
            Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);
//            vpContent.setVisibility(View.GONE);
            llContent.setVisibility(View.GONE);
            laySignInfoOverviewLoadFailure.setVisibility(View.VISIBLE);
        }
    }

    public void showIntelligentHonourAgreementOverviewOrFailureView(boolean success) {
        if (success) {
            llIntelligentOverview.setVisibility(View.VISIBLE);
            layIntelligentHonourAgreementOverviewLoadFailure.setVisibility(View.GONE);
        } else {
            Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);
            llIntelligentOverview.setVisibility(View.GONE);
            layIntelligentHonourAgreementOverviewLoadFailure.setVisibility(View.VISIBLE);
        }
    }

    public void showIntelligentHonourAgreementOverview(IntelligentHonourAgreementOverviewResultBean bean) {
        if (bean == null) {
            bean = new IntelligentHonourAgreementOverviewResultBean(Constants.ZERO_STRING, Constants.ZERO_STRING,
                    Constants.ZERO_STRING, Constants.ZERO_STRING, Constants.ZERO_STRING);
        }
        if (bean != null) {
//            int imageId = R.mipmap.take_photo_blue;

            TextView tvIntelligentHonourAgreement = Util.getTextViewFromView(ltIntelligentHonourAgreementRemind);
            tvIntelligentHonourAgreement.setText(getResources().getString(R.string.intelligent_honour_agreement_remind));

            String latest7DaysNeedExecuteProjectsNumber = getResources().getString(R.string.latest_7_days_need_execute_projects_number);
            String latest7 = String.valueOf(Util.getIntFromString(Util.trimString(bean.getExecitem())));
            String latest7DaysPerPersonTimes = String.format(Locale.CHINA, getResources().getString(R.string.per_person_and_times),
                    latest7);
            Util.setImageTextNumberForViewHorizontal(litnhLatest7DaysNeedExecuteProjectsNumber, R.mipmap.i_index_latest_7_days, latest7DaysNeedExecuteProjectsNumber, latest7DaysPerPersonTimes);


            String timeoutNotExecuteProjectsNumber = getResources().getString(R.string.timeout_not_execute_projects_number);
            String timeout = String.valueOf(Util.getIntFromString(Util.trimString(bean.getTimeout())));
            String timeoutNotExecutePerPersonTimes = String.format(Locale.CHINA, getResources().getString(R.string.per_person_and_times), timeout);
            Util.setImageTextNumberForViewHorizontal(litnhTimeoutNotExecuteProjectsNumber, R.mipmap.i_index_timeout, timeoutNotExecuteProjectsNumber, timeoutNotExecutePerPersonTimes);

            String serviceAssess = getResources().getString(R.string.service_assess);
            String serviceAssessNumber = bean.getServiceEvaluation();
            Util.setImageTextNumberForViewVertical(litnvServiceAssess, R.mipmap.i_index_service_assess, serviceAssess, serviceAssessNumber);

            String signApplication = getResources().getString(R.string.sign_application);
            String signApplicationNumber = bean.getApplySignDoctor();
            Util.setImageTextNumberForViewVertical(litnvSignApplication, R.mipmap.i_index_sign_application, signApplication, signApplicationNumber);

            String appointmentSign = getResources().getString(R.string.appointment_execution);
            String appointmentSignNumber = bean.getAppointmentService();
            Util.setImageTextNumberForViewVertical(litnvAppointmentSign, R.mipmap.i_index_appointment_sign, appointmentSign, appointmentSignNumber);
        }
    }


//    @Override
//    public void onLoadSignOverviewDataSuccess(HttpResultBaseBean<List<SignInfoOverviewResultBean>> bean) {
//        if (bean != null) {
//            List<SignInfoOverviewResultBean> list = bean.getData();
//
//            if (list != null) {
//                showSignInfoOverview(list);
//                showSignInfoOverviewOrFailureView(true);
//            } else {
//                showSignInfoOverviewOrFailureView(false);
//            }
//        } else {
//            showSignInfoOverviewOrFailureView(false);
//        }
//
//    }
//
//    @Override
//    public void onLoadSignOverviewDataFailure(String message) {
//        showSignInfoOverviewOrFailureView(false);
//
//    }
//
//    @Override
//    public void onLoadIntelligentHonourAgreementOverviewDataSuccess(HttpResultBaseBean<IntelligentHonourAgreementOverviewResultBean> bean) {
//        LogUtil.i("Intelligent wrapper:"+(bean != null ? "not null" : "null"));
//        if (bean != null) {
//            IntelligentHonourAgreementOverviewResultBean b = bean.getData();
//            LogUtil.i("Intelligent data:"+(b != null ? "not null" : "null"));
//            if (b != null) {
//                showIntelligentHonourAgreementOverview(b);
//                showIntelligentHonourAgreementOverviewOrFailureView(true);
//            } else {
//                showIntelligentHonourAgreementOverview(b);
//                showIntelligentHonourAgreementOverviewOrFailureView(false);
//            }
//        } else {
//            showIntelligentHonourAgreementOverviewOrFailureView(false);
//        }
//    }
//
//    @Override
//    public void onLoadIntelligentHonourAgreementOverviewDataFailure(String message) {
//        showIntelligentHonourAgreementOverviewOrFailureView(false);
//
//    }


//    //    @OnClick(R.id.index_chart1_time)
//    public void onSelectChart1Time() {
////        Util.showDateSelectDialog(getActivity(), "2017-06-07", "dateselecttag", new DatePickerDialog.OnDateSetListener() {
////            @Override
////            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
////                String s = String.format("%d-%d-%d %d-%d-%d", year, monthOfYear, dayOfMonth,
////                        yearEnd, monthOfYearEnd, dayOfMonthEnd);
////                LogUtil.i("selected date:"+s);
////            }
////        });
////        Util.showTimeRangeSelectDialog(getActivity(), "8:30", "17:30", "timerangepicker", new TimePickerDialog.OnTimeSetListener() {
////            @Override
////            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
////                String s = String.format("%d:%d-%d:%d", hourOfDay, minute, hourOfDayEnd, minuteEnd);
////                LogUtil.i("selected time range is:"+s);
////            }
////        });
////        Util.showDateRangeSelectDialog(getActivity(), "2017-06-07", "2017-07-24", "daterangetag", new com.borax12.materialdaterangepicker.date.DatePickerDialog.OnDateSetListener() {
////            @Override
////            public void onDateSet(com.borax12.materialdaterangepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
////                String s = String.format("%d-%d-%d %d-%d-%d", year, monthOfYear, dayOfMonth,
////                        yearEnd, monthOfYearEnd, dayOfMonthEnd);
////                LogUtil.i("selected date range is:"+s);
////            }
////        });
////        Util.showTimeSelectDialog(getActivity(), "", "time_select_tag", new TimePickerDialog.OnTimeSetListener() {
////            @Override
////            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
////                String time = Util.getCanonicalHourMinuteString(hourOfDay, minute);
////                LogUtil.i("time:"+time);
////            }
////
//////            @Override
//////            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
//////                String startTime = Util.getCanonicalHourMinuteString(hourOfDay, minute);
//////                String endTime = Util.getCanonicalHourMinuteString(hourOfDayEnd, minuteEnd);
//////                LogUtil.i("startTime/endTime:"+startTime+"/"+endTime);
//////            }
////        });
//
//        String initial = indexChart1Time.getText().toString();
//        Util.showDateSelectDialog(getActivity(), initial, "index_fragment_chart1_time", new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                String s = Util.getCanonicalYearMonthDayString(year, monthOfYear + 1, dayOfMonth);
//                indexChart1Time.setText(s);
//            }
//        });
//    }
//
//    //    @OnClick(R.id.index_chart2_time)
//    public void onSelectChart2Time() {
//        String initial = indexChart2Time.getText().toString();
//        Util.showDateSelectDialog(getActivity(), initial, "index_fragment_chart2_time", new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                String s = Util.getCanonicalYearMonthDayString(year, monthOfYear + 1, dayOfMonth);
//                indexChart2Time.setText(s);
//            }
//        });
//
//    }
//
//
//    //    @OnClick(R.id.index_area_linear_layout)
//    public void selectArea() {
//        //LogUtil.i("select area is clicked");
//
//        showAreaSelectDialog();
//
////        Handler handler = new Handler();
////        handler.postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                Intent intent = new Intent(getActivity(), ChatActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                intent.putExtra(Constants.MESSAGE_FROM_USER_ID_KEY, "1");
////                intent.putExtra(Constants.MESSAGE_TO_USER_ID_KEY, "2");
////                intent.putExtra(Constants.MESSAGE_CONTENT_KEY, "消息");
////                startActivity(intent);
////
//////                Util.bringApplicationToForegroundFromChatMessageNotificationClick(getActivity(), "1", "2", "消息");
////            }
////        }, 5000);
//
//
////        List<AreaBean> areaList = SimulateData.getSimulatedAreaList();
////        AreaAdapter adapter = new AreaAdapter(areaList);
////        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                AreaBean item = (AreaBean) adapter.getItem(position);
////                LogUtil.i("has selected item name:"+item.getName());
////            }
////        });
////        new MaterialDialog.Builder(this.getActivity())
////                .title(R.string.hint_select_area)
////                // second parameter is an optional layout manager. Must be a LinearLayoutManager or GridLayoutManager.
////                .adapter(adapter, new GridLayoutManager(getActivity(), 3))
////                .show();
//    }
//
//    private void showAreaSelectDialog() {
////        List<AreaBean> areaList = SimulateData.getSimulatedAreaList();
//        List<AreaBean> areaList = Identity.area;
//        Util.showAreaSelectMaterialDialog(getActivity(), areaList);
////        test();
//    }
//
//    public void test() {
//        List<String> list = Util.getStringListFromStringArray(new String[]{"AAA", "BBB", "CCC", "DDD", "EEE",
//                "FFF", "GGG", "HHH", "III", "JJJ", "KKK", "MMM", "NNNN"});
//        int[] selectedIndexArray = new int[]{0, 5, 6};
//
//
//        List<IconTextBackgroundColorBean> beanList = new ArrayList<>();
//        for (int i = 0; i < 10; ++i) {
//            IconTextBackgroundColorBean bean = new IconTextBackgroundColorBean(R.mipmap.icon_14, "text" + i,
//                    Color.WHITE);
//            beanList.add(bean);
//        }
//        Util.showIconTextListMaterialDialog(getActivity(), beanList, R.string.activity_title_treatment, new MaterialSimpleListAdapter.Callback() {
//            @Override
//            public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
//                ToastUtil.show(getActivity(), "您已经选择了" + item.getContent());
//                dialog.dismiss();
//            }
//        });
//
////        Util.showFolderSelectMaterialDialog((WorkbenchActivity)this.getActivity(), "", "folder_select_tag");
////        Util.showFileSelectMaterialDialog((WorkbenchActivity)this.getActivity(), "/sdcard/51job", Constants.FILE_TYPE_IMAGE,
////                "file_select_tag");
////        Util.showColorSelectMaterialDialog((WorkbenchActivity)this.getActivity(), R.string.color_preset_first_level, R.string.color_preset_second_level);
////        Util.showIndeterminateHorizontalMaterialDialog(getActivity(), "title", "msg");
////        Util.showIndeterminateCircularMaterialDialog(getActivity(), "title", "msg");
//
////        List<AreaBean> areaList = SimulateData.getSimulatedAreaList();
////        AreaAdapter adapter = new AreaAdapter(areaList);
////        final MaterialDialog dialog = Util.showGridRecyclerViewMaterialDialog(getActivity(), "hint", adapter);
////        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                AreaBean item = (AreaBean) adapter.getItem(position);
////                RxBus.getDefault().post(Constants.EVENT_TYPE_DID_SELECT_AREA, item);
////                dialog.dismiss();
////            }
////        });
//
//
////        BaseQuickAdapter<TreatmentListBean.TreatmentBean, BaseViewHolder> mAdapter = new TreatmentAdapter(new ArrayList<TreatmentListBean.TreatmentBean>());
////        EasyLoadMoreView easyLoadMoreView = new EasyLoadMoreView();
////        mAdapter.setLoadMoreView(easyLoadMoreView);
////        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
////        final MaterialDialog dialog = Util.showLinearRecyclerViewMaterialDialog(getActivity(), "提示", mAdapter);
////        ((TreatmentAdapter) mAdapter).setOnItemClickListener(new TreatmentAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                Util.showAlert(getActivity(), "hint", "you selected position "+position);
////                dialog.dismiss();
////            }
////        });
////        RecyclerView recyclerView = dialog.getRecyclerView();
////        TreatmentListBean bean = SimulateData.getSimulatedTreatmentListBean();
////        List<TreatmentListBean.TreatmentBean> treatmentList = bean.getTreatmentList();
////        mAdapter.setNewData(treatmentList);
//
////        Util.showEditTextMaterialDialog(getActivity(), "提示", "请输入字符", "please input", "23", false, 2, 8, Constants.EDIT_INPUT_TYPE_PHONE, new MaterialDialog.InputCallback() {
////            @Override
////            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
////                ToastUtil.show(getActivity(), "you have input "+input);
////            }
////        });
////        MaterialDialog dialog = Util.showCustomViewMaterialDialog(getActivity(), "提示", "请选择", R.layout.fragment_mine, false
////                , new MaterialDialog.SingleButtonCallback() {
////                    @Override
////                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
////                        ToastUtil.show(getActivity(), "you clicked confirm button");
////                    }
////                });
//
////        Util.showMultipleChoiceStringListMaterialDialog(getActivity(), "提示", "请选择", list, selectedIndexArray,
////                new MaterialDialog.ListCallbackMultiChoice() {
////                    @Override
////                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
////                        String s = Util.join(which, '-');
////                        ToastUtil.show(getActivity(), "you have selected "+s);
////                        return false;
////                    }
////                });
////        Util.showSingleChoiceStringListMaterialDialog(getActivity(), "提示", "请选择一项", list, 1, new MaterialDialog.ListCallbackSingleChoice() {
////            @Override
////            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
////                ToastUtil.show(getActivity(), "选择了索引"+which);
////                return true;
////            }
////        });
////        Util.showStringListMaterialDialog(getActivity(), "提示", "请选择一项", list, new MaterialDialog.ListCallback() {
////            @Override
////            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
////                ToastUtil.show(getActivity(), "您选择了第"+position+"项，数据为:"+text);
////            }
////        });
//    }


//    private void startNBADetailActivity(String id, String imgUrl, View view) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), NBAActivity.class);
//        intent.putExtra("id", id);
//        intent.putExtra("url", imgUrl);
//        /**
//         * 用这个ActivityOptionsCompat比用ActivityOptions兼容性更好，前者是V4下的兼容到16后者到21.
//         * ActivityOptionsCompat.makeSceneTransitionAnimation(）的第三个参数则是跳转后图片显示的transitionName的值
//         *     <android.support.design.widget.AppBarLayout
//         android:transitionName="zhihu_detail_title"
//         android:fitsSystemWindows="true">
//         */
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                view, getActivity().getResources().getString(R.string.zhihu_detail_title));
//        getActivity().startActivity(intent, options.toBundle());
//    }

    public void registerAreaSelectEvent() {
//        Subscription mSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_DID_SELECT_AREA, AreaBean.class)
//                .subscribe(new Action1<AreaBean>() {
//                    @Override
//                    public void call(AreaBean ab) {
////                        mPresenter.fetchWXHotSearch(20, 1, s);
//                        Identity.setCurrentArea(ab);
//                        indexAreaName.setText(ab.getName());
//                    }
//                });
//        if (this.areaSelectSubscription == null) {
//            areaSelectSubscription = new CompositeSubscription();
//        }
//        areaSelectSubscription.add(mSubscription);
    }

    public void registerFunctionImageClickEvent() {
//        Subscription mSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_DID_SELECT_FUNCTION_IMAGE, FunctionImageBean.class)
//                .subscribe(new Action1<FunctionImageBean>() {
//                    @Override
//                    public void call(FunctionImageBean functionImageBean) {
//                        gotoFunction(functionImageBean);
////                Util.showAlert(getActivity(), "提示", "您选择了功能"+functionImageBean.getId());
//                    }
//                });
//        if (this.functionImageSubscription == null) {
//            functionImageSubscription = new CompositeSubscription();
//        }
//        functionImageSubscription.add(mSubscription);
    }

    public void registerArrowClickEvent() {
        Subscription mSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_DID_CLICK_LEFT_RIGHT_ARROW_IN_SIGN_INFO_OVERVIEW, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        vpContent.setCurrentItem(integer, true);
                    }
                });
        if (this.arrowClickSubscription == null) {
            arrowClickSubscription = new CompositeSubscription();
        }
        arrowClickSubscription.add(mSubscription);
    }

    public void registerIntelligentHonourAgreementRemindEvent() {
        Subscription mSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_REFRESH_INTELLIGENT_HONOUR_AGREEMENT_REMIND, Boolean.class)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            refreshIntelligentHonourAgreementRemindInfo();
                        }
                    }
                });
        if (refreshIntelligentHonourAgreementRemindSubscription == null) {
            refreshIntelligentHonourAgreementRemindSubscription = new CompositeSubscription();
        }
        refreshIntelligentHonourAgreementRemindSubscription.add(mSubscription);
    }

    public void registerRxBusEvent() {
        Subscription mSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_NURSING_DID_GET_ORGNIZATION_LIST, OrgnizationListAndSuccessBean.class)
                .subscribe(new Action1<OrgnizationListAndSuccessBean>() {
                    @Override
                    public void call(OrgnizationListAndSuccessBean bean) {
                        if (bean == null || ListUtils.isEmpty(bean.getList())) {
                            if (bean.isSuccess()) {
                                showSuccessViewOrFailureView(true, true, true);
                            } else {
                                showSuccessViewOrFailureView(false, true, true);
                            }
                        } else {
                            getOrgnizationListFromNetworkOrGetElderInfo();
                        }
                    }
                });
        if (this.mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(mSubscription);

        Subscription mSubscription2 = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_NURSING_REQUEST_GETTING_ORGNIZATION_LIST, Boolean.class)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean b) {
                        Map<String, String> map = getOrgnizationListRequestMap();
                        mPresenter.getOrgnizationList(map);
                    }
                });
        mCompositeSubscription.add(mSubscription2);
    }

//    private void unregisterRxBusEvent() {
//        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
//            mCompositeSubscription.unsubscribe();
//        }
//    }


    public void gotoFunction(FunctionImageBean bean) {
//        String id = bean.getId();
//        int intId = Util.getIntFromString(id);
//        if (Constants.FUNCTION_1 == intId) {
//            ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.TREATMENT_ACTIVITY_NAME);
//        } else if (Constants.FUNCTION_2 == intId) {
//            ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.TREATMENT_ACTIVITY_NAME);
//        } else if (Constants.FUNCTION_3 == intId) {
//            ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.TREATMENT_ACTIVITY_NAME);
//        } else if (Constants.FUNCTION_4 == intId) {
//            ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.MEDICAL_INSTITUTION_ACTIVITY_NAME);
//        } else if (Constants.FUNCTION_5 == intId) {
//            ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.CANHE_ACTIVITY_NAME);
//        } else if (Constants.FUNCTION_6 == intId) {
//            ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.COMPENSATE_ACTIVITY_NAME);
//        } else if (Constants.FUNCTION_7 == intId) {
//            RxBus.getDefault().post(Constants.EVENT_TYPE_WOULD_SCROLL_TO_WORKBENCH_INDEX, Constants.WORKBENCH_ONLINE_SIGN);
//        }
    }


//    @Override
//    public void refreshView(ChartDataBean mData) {
//        LogUtil.i("IndexDeanFragment refreshView");
////        List<NBAListBean.NBABean> nbaBeenList = mData.getT1348649145984();
////        if (isRefresh) {
////            srlAndroid.setRefreshing(false);
////            mAdapter.setEnableLoadMore(true);
////            isRefresh = false;
////            mAdapter.setNewData(nbaBeenList);
////        } else {
////            srlAndroid.setEnabled(true);
////            index += 20;
////            mAdapter.addData(nbaBeenList);
////            mAdapter.loadMoreComplete();
////        }
//
//    }


    //chart
    /*
    当json字符串生成时候，再调用loadUrl("javascript:refresh('" + getJsData() + "')"); 传递给网页，这样网页就能刷新表格了。

     */
//    public void initWebView1() {
////        chart1WebView = (WebView) ((Activity)getActivity()).findViewById(R.id.chart1_web_view);
//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        chart1WebView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (!chart1WebView.getSettings().getLoadsImagesAutomatically()) {
//                    chart1WebView.getSettings().setLoadsImagesAutomatically(true);
//                }
//            }
//        });
//        // 启用支持javascript
//        WebSettings settings = chart1WebView.getSettings();
//        settings.setJavaScriptEnabled(true);
////        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
////        settings.setDomStorageEnabled(true);
////        settings.setAppCacheMaxSize(8 * 1024 * 1024);
////        settings.setAllowFileAccess(true);
////        settings.setAppCacheEnabled(true);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
////            chart1WebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
////        }
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            settings.setLoadsImagesAutomatically(true);
////        } else {
////            settings.setLoadsImagesAutomatically(false);
////        }
//
//        chart1WebView.addJavascriptInterface(new JsToJava(), "signed_doctor_client");
//
//        // 加载网页文件
//        chart1LoadUrl("file:///android_asset/chart1.html");
////        chart1LoadUrl("javascript:refresh('" + getJsData1() + "')");
//
//    }
//
//    public void chart1LoadUrl(final String url) {
//        ((Activity) getActivity()).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                chart1WebView.loadUrl(url);
//            }
//        });
//    }

    public String getJsData1() {
        JsData data = new JsData();
        data.categories = new String[]{"春", "夏", "秋", "冬"};
        data.series = new YData[2];
        Random random = new Random();
        for (int i = 0; i < data.series.length; i++) {
            data.series[i] = new YData();
            data.series[i].name = "Name" + (i + 1);
            int j = i;
            data.series[i].data = new int[]{100 * j + random.nextInt(100), 100 * j + random.nextInt(100), 100 * j + random.nextInt(100), 100 * j + random.nextInt(100)};
        }
        String json = new Gson().toJson(data);

        return json;
    }

//    public void initWebView2() {
////        chart1WebView = (WebView) ((Activity)getActivity()).findViewById(R.id.chart1_web_view);
//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        chart2WebView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (!chart2WebView.getSettings().getLoadsImagesAutomatically()) {
//                    chart2WebView.getSettings().setLoadsImagesAutomatically(true);
//                }
//            }
//        });
//        // 启用支持javascript
//        WebSettings settings = chart2WebView.getSettings();
//        settings.setJavaScriptEnabled(true);
////        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
////        settings.setDomStorageEnabled(true);
////        settings.setAppCacheMaxSize(8 * 1024 * 1024);
////        settings.setAllowFileAccess(true);
////        settings.setAppCacheEnabled(true);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
////            chart1WebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
////        }
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            settings.setLoadsImagesAutomatically(true);
////        } else {
////            settings.setLoadsImagesAutomatically(false);
////        }
//
//        chart2WebView.addJavascriptInterface(new JsToJava(), "signed_doctor_client");
//
//        // 加载网页文件
//        chart2LoadUrl("file:///android_asset/chart2.html");
////        chart1LoadUrl("javascript:refresh('" + getJsData1() + "')");
//
//    }
//
//    public void chart2LoadUrl(final String url) {
//        ((Activity) getActivity()).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                chart2WebView.loadUrl(url);
//            }
//        });
//    }

    public String getJsData2() {
        JsData data = new JsData();
        data.categories = new String[]{"东", "南", "西", "北"};
        data.series = new YData[2];
        Random random = new Random();
        for (int i = 0; i < data.series.length; i++) {
            data.series[i] = new YData();
            data.series[i].name = "Name" + (i + 1);
            int j = i;
            data.series[i].data = new int[]{100 * j + random.nextInt(100), 100 * j + random.nextInt(100), 100 * j + random.nextInt(100), 100 * j + random.nextInt(100)};
        }
        String json = new Gson().toJson(data);
        //LogUtil.i("json=" + json);
        return json;
    }

    public void loadChart() {
//        chart1LoadUrl("javascript:refresh('" + getJsData1() + "')");
//        chart2LoadUrl("javascript:refresh('" + getJsData2() + "')");

    }

//    public void registerRxBusEvent() {
//        Subscription mSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_NURSING_DID_GET_ORGNIZATION_LIST, OrgnizationListAndSuccessBean.class)
//                .subscribe(new Action1<OrgnizationListAndSuccessBean>() {
//                    @Override
//                    public void call(OrgnizationListAndSuccessBean bean) {
//                        if (bean==null || ListUtils.isEmpty(bean.getList())){
//                            if (bean.isSuccess()) {
//                                showSuccessViewOrFailureView(true, true, true);
//                            } else {
//                                showSuccessViewOrFailureView(false, true, true);
//                            }
//                        } else {
//                            getOrgnizationListFromNetworkOrGetElderInfo();
//                        }
//                    }
//                });
//        if (this.mCompositeSubscription == null) {
//            mCompositeSubscription = new CompositeSubscription();
//        }
//        mCompositeSubscription.add(mSubscription);
//
//    }

    private void unregisterRxBusEvent() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterRxBusEvent();

    }

    private void showSuccessViewOrFailureView(boolean success, boolean isListEmpty, boolean isOrgnizationList) {
        if (success) {
            if (isListEmpty) {
                //   pcChart.setVisibility(View.GONE);
                //  failureView.setVisibility(View.VISIBLE);
                //   tvFailureView.setText(isOrgnizationList ? getOrgnizationListEmptyHint() : getListEmptyHint());
              /*  hilightStatisticsListIndicatorView(false);
                hilightStatisticsChartIndicatorView(true);*/
            } else {
                //  pcChart.setVisibility(View.VISIBLE);
                //  failureView.setVisibility(View.GONE);
               /* if (isChartSelected()) {
                    hilightChart();
                } else {
                    hilightList();
                }*/
            }
        } else {
            //    pcChart.setVisibility(View.GONE);
            //  failureView.setVisibility(View.VISIBLE);
            //   tvFailureView.setText(isOrgnizationList ? getOrgnizationListFailureHint() : getFailureHint());
           /* hilightStatisticsListIndicatorView(false);
            hilightStatisticsChartIndicatorView(true);*/
        }
    }


    private void initDateRangePopupWindow(int index) {
        List<IDateRange> list = new ArrayList<>();
        if (Constants.DATE_RANGE_TYPE_MONTH == index) {
            List<MonthTextBean> monthList = Util.getLatestNumberMonthTextBeanList(MonthTextBean.NUMBER);
            list = Arrays.asList(monthList.toArray(new IDateRange[0]));
        } else if (Constants.DATE_RANGE_TYPE_QUARTER == index) {
            List<QuarterTextBean> quarterList = Util.getLatestNumberQuarterTextBeanList(QuarterTextBean.NUMBER);
            list = Arrays.asList(quarterList.toArray(new IDateRange[0]));
        } else if (Constants.DATE_RANGE_TYPE_YEAR == index) {
            List<YearTextBean> yearList = Util.getLatestNumberYearTextBeanList(YearTextBean.NUMBER);
            list = Arrays.asList(yearList.toArray(new IDateRange[0]));
        }
        if (!ListUtils.isEmpty(list)) {
            selectedDateRange = list.get(0);
        } else {
            selectedDateRange = null;
        }
        setDateRangeString();
        dateRangePopupWindow = new MonthQuarterYearRangePopupWindow(getActivity(), Constants.POPUP_WINDOW_WIDTH_FOR_STATISTICS, Constants.POPUP_WINDOW_HEIGHT, toolbar4, list);
        dateRangePopupWindow.setDateRangeItemClickListener(new MonthQuarterYearRangePopupWindow.DateRangeItemClickListener() {
            @Override
            public void dateRangeItemDidClick(IDateRange iDateRange) {
                selectedDateRange = iDateRange;
                setDateRangeString();
                // onRefresh();
                getElderInfo();
            }
        });
    }

    private void setDateRangeString() {
        if (selectedDateRange != null) {
            tvDate.setText(selectedDateRange.getPresentation());
        }

    }


}
