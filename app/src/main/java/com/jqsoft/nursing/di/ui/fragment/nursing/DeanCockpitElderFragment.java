package com.jqsoft.nursing.di.ui.fragment.nursing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.jakewharton.rxbinding.view.RxView;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.nursing.MyAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.DeanCockpitElderBean;
import com.jqsoft.nursing.bean.nursing.OrgnizationBean;
import com.jqsoft.nursing.bean.nursing.OrgnizationListAndSuccessBean;
import com.jqsoft.nursing.di.contract.nursing.DeanCockpitElderFragmentContract;
import com.jqsoft.nursing.di.module.nursing.DeanCockpitElderFragmentModule;
import com.jqsoft.nursing.di.presenter.nursing.DeanCockpitElderFragmentPresenter;
import com.jqsoft.nursing.di.ui.activity.WorkbenchActivity;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.PercentAndLabelFormatter;
import com.jqsoft.nursing.rx.RxBus;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils3.util.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 院长驾驶舱-老人
 * Created by Administrator on 2018-04-18.
 */

public class DeanCockpitElderFragment extends AbstractFragment implements DeanCockpitElderFragmentContract.View {
    @BindView(R.id.ll_institution_name)
    LinearLayout llInstitutionName;
    @BindView(R.id.tv_institution_name)
    TextView tvInstitutionName;

    @BindView(R.id.pc_chart)
    PieChart pcChart;
    @BindView(R.id.lay_dean_cockpit_elder_load_failure)
    View failureView;

    TextView tvFailureView;

    boolean isRequestSuccess = false;
    boolean isGetOrgnizationListSuccess = false;

    int institutionIdIndex = 0;
    String institutionId=Constants.EMPTY_STRING;

    @BindView(R.id.nursing_title)
    Spinner index_title;

    private MyAdapter adapter_sp;

    private int myposition=0;


    @Inject
    DeanCockpitElderFragmentPresenter mPresenter;

//    private List<OrgnizationBean> orgnizationList = new ArrayList<>();
//    Runnable runnable;

    CompositeSubscription mCompositeSubscription;


    public DeanCockpitElderFragment() {
    }

    public List<OrgnizationBean> getOrgnizationList() {
        List<OrgnizationBean> result = new ArrayList<>();
        AbstractActivity abstractActivity = (AbstractActivity)getActivity();
        if (abstractActivity instanceof WorkbenchActivity) {
            WorkbenchActivity workbenchActivity = (WorkbenchActivity)abstractActivity;
            List<Fragment> list = workbenchActivity.getmFragments();
            if (ListUtils.getSize(list)>1){
                AbstractFragment af = (AbstractFragment)list.get(Constants.NURSING_DEAN_WORKBENCH_INDEX_GET_ORGANIZATION_LIST);
                if (af instanceof IndexDeanFragment){
                    IndexDeanFragment dcef = (IndexDeanFragment)af;
                    return dcef.getOrgnizationList();
                } else {
                    return result;
                }
            } else {
                return result;
            }
        } else {
            return result;
        }
    }



//    public List<OrgnizationBean> getOrgnizationList() {
//        return orgnizationList;
//    }
//
//    public void setOrgnizationList(List<OrgnizationBean> orgnizationList) {
//        this.orgnizationList = orgnizationList == null ? new ArrayList<OrgnizationBean>() : orgnizationList;
//    }

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
        unregisterRxBusEvent();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dean_cockpit_elder_statistics_layout;
    }

    @Override
    protected void initData() {
//        runnable=new Runnable() {
//            @Override
//            public void run() {
//                OrgnizationListAndSuccessBean bean = new OrgnizationListAndSuccessBean(getOrgnizationList(), isGetOrgnizationListSuccess);
//                RxBus.getDefault().post(Constants.EVENT_TYPE_NURSING_DID_GET_ORGNIZATION_LIST, bean);
//            }
//        };
    }

    @Override
    protected void initView() {
        registerRxBusEvent();

        initInstitutionSelection();

        initPieChart();

        tvFailureView = (TextView) failureView.findViewById(R.id.tv_load_failure_hint);

        RxView.clicks(tvFailureView)
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
                     //   onRefresh();
                        getElderInfo(myposition);
                    }
                });

        index_title.setBackgroundColor(Color.parseColor("#067eee"));
        index_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                initInstitutionType(position);
//                institutionId=adapter_sp.getItem(position).getsId();
                myposition =position;
                getElderInfo(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void registerRxBusEvent() {
        Subscription mSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_NURSING_DID_GET_ORGNIZATION_LIST, OrgnizationListAndSuccessBean.class)
                .subscribe(new Action1<OrgnizationListAndSuccessBean>() {
                    @Override
                    public void call(OrgnizationListAndSuccessBean bean) {
                        if (bean==null || ListUtils.isEmpty(bean.getList())){
                            if (bean.isSuccess()) {
                                showSuccessViewOrFailureView(true, true, true, false);
                            } else {
                                showSuccessViewOrFailureView(false, true, true, false);
                            }
                        } else {
                            initInstitutionType(institutionIdIndex);
                            setSpinnerAdapter();
//                            getElderInfo();
                        }
                    }
                });
        if (this.mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(mSubscription);

//        Subscription mSubscription2 = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_NURSING_REQUEST_GETTING_ORGNIZATION_LIST, Boolean.class)
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean b) {
//                        Map<String, String> map = getOrgnizationListRequestMap();
//                        mPresenter.getOrgnizationList(map);
//                    }
//                });
//        mCompositeSubscription.add(mSubscription2);
    }

    private void unregisterRxBusEvent() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    @Override
    protected void loadData() {
//        onRefresh();

    }

    @Override
    protected void initInject() {
        super.initInject();
        DaggerApplication.get(getActivity())
                .getAppComponent()
                .addDeanCockpitElderFragment(new DeanCockpitElderFragmentModule(this))
                .inject(this);

    }

    private void onRefresh() {
        loadInfo();
    }


    private void loadInfo() {
        getOrgnizationListFromNetworkOrGetElderInfo();

    }

    private void getElderInfo(){
        getElderInfo(0);
    }


    private void getElderInfo(int index){
        if (index>=0 && index<getOrgnizationList().size()) {
            institutionIdIndex = index;
            institutionId = getOrgnizationList().get(index).getsId();

            Map<String, String> map = getDeanCockpitElderListRequestMap();
            mPresenter.getDeanCockpitElderList(map);
        }
    }



    private Map<String, String> getDeanCockpitElderListRequestMap() {
        String userId = IdentityManager.getUserId(getActivity());
        Map<String, String> map = ParametersFactory.getDeanCockpitElderListMap(getActivity(), userId,
                "NurseType", institutionId);
        return map;
    }

    private void requestGettingOrganizationList(){
        RxBus.getDefault().post(Constants.EVENT_TYPE_NURSING_REQUEST_GETTING_ORGNIZATION_LIST, true);
    }



    public void getOrgnizationListFromNetworkOrGetElderInfo() {
        List<OrgnizationBean> list = getOrgnizationList();
        if (ListUtils.isEmpty(list)){
            requestGettingOrganizationList();
//            Map<String, String> map = getOrgnizationListRequestMap();
//            mPresenter.getOrgnizationList(map);
        } else {
            getElderInfo();
        }
    }

    private Map<String, String> getOrgnizationListRequestMap() {
        String userId = IdentityManager.getUserId(getActivity());
        Map<String, String> map = ParametersFactory.getOrgnizationListMap(getActivity(), userId);
        return map;
    }

    private List<DeanCockpitElderBean> getStatisticsBean(HttpResultNurseBaseBean<List<DeanCockpitElderBean>> bean) {
        if (bean != null) {
            List<DeanCockpitElderBean> list = bean.getData();
            return list;
        } else {
            return null;
        }
    }


    private void initInstitutionSelection() {
//        initInstitutionType(institutionIdIndex);

        RxView.clicks(tvInstitutionName)
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
//                        String[] typeArray = new String[orgnizationList.size()];
//                        orgnizationList.toArray(typeArray);
                        List<String> typeList = getStringList();
                        Util.showSingleChoiceStringListMaterialDialog(getActivity(), "请选择机构", null, typeList,
                                institutionIdIndex, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                        initInstitutionType(which);
                                        onRefresh();
                                        return false;
                                    }
                                });
                    }
                });

    }

    private List<String> getStringList() {
        List<String> result = new ArrayList<>();
        List<OrgnizationBean> orgList = getOrgnizationList();
        for (int i = 0; i < orgList.size(); ++i) {
            OrgnizationBean ob = orgList.get(i);
            result.add(ob.getsOrgName());
        }
        return result;
    }

    private void initPieChart() {

        pcChart.setNoDataText(Constants.CHART_NO_DATA_TEXT);
        pcChart.setNoDataTextColor(getResources().getColor(R.color.colorTheme));

        pcChart.getDescription().setEnabled(false);

        pcChart.setUsePercentValues(true);
        pcChart.getDescription().setEnabled(false);
        pcChart.setExtraOffsets(20, 0, 20, 0);

        pcChart.setDragDecelerationFrictionCoef(0.95f);

        pcChart.setCenterText(Constants.EMPTY_STRING);

//        pcChart.setDrawEntryLabels(false);

        pcChart.setDrawHoleEnabled(true);
        pcChart.setHoleColor(Color.WHITE);

        pcChart.setTransparentCircleColor(Color.WHITE);
        pcChart.setTransparentCircleAlpha(110);

        pcChart.setHoleRadius(58f);
        pcChart.setTransparentCircleRadius(61f);

        pcChart.setDrawCenterText(true);
        pcChart.setDrawEntryLabels(false);

        pcChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pcChart.setRotationEnabled(true);
        pcChart.setHighlightPerTapEnabled(true);

        // pcChart.setUnit(" €");
        // pcChart.setDrawUnitsInChart(true);

        Legend l = pcChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
        l.setWordWrapEnabled(true);
    }

    private void setData(List<DeanCockpitElderBean> beanList) {
        List<DeanCockpitElderBean> naturalList = getNaturalBeanListFromRawBeanList(beanList);

        showSuccessViewOrFailureView(true, ListUtils.isEmpty(naturalList), false, false);

        if (ListUtils.isEmpty(beanList)) {
            return;
        }

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        boolean b = Util.isItemPercentVerySmallCountLessThanFixedNumberFromDeanCockpitElderBeanList(beanList);

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        if (b) {
            for (int i = 0; i < beanList.size(); i++) {
                DeanCockpitElderBean bean = beanList.get(i);
//            entries.add(new PieEntry((float) Util.getFloatFromPercentString(String.valueOf(bean.getValue())),
//                    Constants.EMPTY_STRING));
                float value = (float) Util.getFloatFromPercentString(String.valueOf(bean.getHasElder()));
                entries.add(new PieEntry(value,
                        bean.getDataType()));
//            entries.add(new PieEntry((float) Util.getFloatFromPercentString(String.valueOf(bean.getValue())),
//                    bean.getName()+Constants.SPACE_STRING+bean.getValue()));
            }
        } else {
            for (int i = 0; i < beanList.size(); i++) {
                DeanCockpitElderBean bean = beanList.get(i);
                float value = (float) Util.getFloatFromPercentString(String.valueOf(bean.getHasElder()));
                boolean isSmall = Util.isItemPercentVerySmallInDeanCockpitElderBeanList(value, beanList);
                if (!isSmall) {
                    entries.add(new PieEntry(value,
                            bean.getDataType()));
                }
//                else {
//                    entries.add(new PieEntry(value,
//                            Constants.EMPTY_STRING));
//
//                }
            }
        }


//        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//        // the chart.
//        for (int i = 0; i < beanList.size() ; i++) {
//            DeanCockpitElderBean bean = naturalList.get(i);
//            entries.add(new PieEntry((float) Util.getFloatFromPercentString(String.valueOf(bean.getValue())),
//                    bean.getName()));
////            entries.add(new PieEntry((float) Util.getFloatFromPercentString(String.valueOf(bean.getValue())),
////                    bean.getName()+Constants.SPACE_STRING+bean.getValue()+Constants.SPACE_STRING+bean.getPercent()));
//        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : Constants.CHART_COLOR_LIST) {
            colors.add(c);
        }

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
//        dataSet.setValueLinePart2Length(2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


        pcChart.setEntryLabelColor(Color.BLACK);

        List<Float> fl = Util.getFloatListFromDeanCockpitElderBeanList(beanList);

        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
        data.setValueFormatter(new PercentAndLabelFormatter(b, fl));
        data.setValueTextSize(Constants.CHART_VALUE_TEXT_SIZE);
        data.setValueTextColor(Color.BLACK);
        pcChart.setData(data);

//        for (IDataSet<?> set : pcChart.getData().getDataSets())
//            set.setDrawValues(true);


        // undo all highlights
        pcChart.highlightValues(null);
        pcChart.invalidate();

        pcChart.animateY(Constants.CHART_ANIMATION_DURATION, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


    }

    private List<DeanCockpitElderBean> getNaturalBeanListFromRawBeanList(
            List<DeanCockpitElderBean> rawList) {
        List<DeanCockpitElderBean> resultList = new ArrayList<>();
        try {
            if (ListUtils.isEmpty(rawList)) {
                return resultList;
            } else {
                resultList = rawList;
//                    for (int j = 0; j < rawList.size(); ++j) {
//                        SubsistenceApprovePovertyReasonBean b = rawList.get(j);
//                        DeanCockpitElderBean nb = new DeanCockpitElderBean();
//                        nb.setTitle(b.getName());
//                        nb.setHouseholdNumber(b.getValue());
//                        nb.setPercent(b.getPercentval());
//                        resultList.add(nb);
//                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return resultList;
    }

    private void showSuccessViewOrFailureView(boolean success, boolean isListEmpty, boolean isOrgnizationList, boolean isAllZero) {
        if (success) {
            if (isListEmpty) {
                pcChart.setVisibility(View.GONE);
                failureView.setVisibility(View.VISIBLE);
                tvFailureView.setText(isOrgnizationList ? getOrgnizationListEmptyHint() : getListEmptyHint());
              /*  hilightStatisticsListIndicatorView(false);
                hilightStatisticsChartIndicatorView(true);*/
            } else {
                if (isAllZero) {
                    pcChart.setVisibility(View.GONE);
                    failureView.setVisibility(View.VISIBLE);
                    tvFailureView.setText(getAllIsZeroHint());
                } else {
                    pcChart.setVisibility(View.VISIBLE);
                    failureView.setVisibility(View.GONE);
                }
               /* if (isChartSelected()) {
                    hilightChart();
                } else {
                    hilightList();
                }*/
            }
        } else {
            pcChart.setVisibility(View.GONE);
            failureView.setVisibility(View.VISIBLE);
            tvFailureView.setText(isOrgnizationList ? getOrgnizationListFailureHint() : getFailureHint());
           /* hilightStatisticsListIndicatorView(false);
            hilightStatisticsChartIndicatorView(true);*/
        }
    }

    private String getAllIsZeroHint(){
        return getResources().getString(R.string.hint_list_not_empty_and_all_is_zero_please_reload);
    }

    private String getListEmptyHint() {
        return getResources().getString(R.string.hint_list_empty_please_reload);
    }

    private String getFailureHint() {
        return getResources().getString(R.string.hint_load_failure);
    }

    private String getOrgnizationListEmptyHint() {
        return getResources().getString(R.string.hint_orgnization_list_empty_please_reload);
    }

    private String getOrgnizationListFailureHint() {
        return getResources().getString(R.string.hint_load_orgnization_list_failure);
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


        }
    }

    private void setSpinnerAdapter(){
        adapter_sp = new MyAdapter(getActivity(),getOrgnizationList());
        index_title.setAdapter(adapter_sp);

    }

    private boolean isAllZero(List<DeanCockpitElderBean> list){
        if (ListUtils.isEmpty(list)){
            return false;
        } else {
            boolean isAllZero = true;
            for (int i = 0; i < list.size(); ++i){
                DeanCockpitElderBean bean = list.get(i);
                String value = bean.getHasElder();
                int intValue = Util.getIntFromString(value);
                if (intValue != 0){
                    isAllZero=false;
                    break;
                }
            }
            return isAllZero;
        }
    }


    @Override
    public void onLoadDeanCockpitElderListDataSuccess(HttpResultNurseBaseBean<List<DeanCockpitElderBean>> bean) {
        List<DeanCockpitElderBean> b = getStatisticsBean(bean);
        isRequestSuccess = true;
        if (isAllZero(b)){
            showSuccessViewOrFailureView(true, false, false, true);
        } else if (!ListUtils.isEmpty(b)) {
            setData(b);
        } else {
            showSuccessViewOrFailureView(true, true, false, false);
        }

    }

    @Override
    public void onLoadDeanCockpitElderListDataFailure(String message) {
        isRequestSuccess = false;
        showSuccessViewOrFailureView(false, true, false, false);
        Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);

    }

//    @Override
//    public void onLoadOrgnizationListDataSuccess(HttpResultNurseBaseBean<List<OrgnizationBean>> bean) {
//        String hint = "机构列表为空";
//        isGetOrgnizationListSuccess=true;
//        orgnizationList.clear();
//        if (bean != null) {
//            List<OrgnizationBean> list = bean.getData();
//            if (!ListUtils.isEmpty(list)) {
//                orgnizationList.addAll(list);
//                initInstitutionType(0);
//            } else {
//                Util.showToast(getActivity(), hint);
//            }
//        } else {
//            Util.showToast(getActivity(), hint);
//        }
////        if (runnable != null) {
////            runnable.run();
////        }
//
////        onLoadOrgnizationListDataFailure(Constants.EMPTY_STRING);
//    }
//
//    @Override
//    public void onLoadOrgnizationListDataFailure(String message) {
//        Util.showToast(getActivity(), "获取机构列表失败");
//        isGetOrgnizationListSuccess=false;
////        orgnizationList.clear();
////        if (runnable != null) {
////            runnable.run();
////        }
//
//    }
}
