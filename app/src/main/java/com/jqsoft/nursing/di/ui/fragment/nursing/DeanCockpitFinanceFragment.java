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
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.jakewharton.rxbinding.view.RxView;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.nursing.MyAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.DeanCockpitFinanceBean;
import com.jqsoft.nursing.bean.nursing.HasYearDataAndIndex;
import com.jqsoft.nursing.bean.nursing.OrgnizationBean;
import com.jqsoft.nursing.bean.nursing.OrgnizationListAndSuccessBean;
import com.jqsoft.nursing.di.contract.nursing.DeanCockpitFinanceFragmentContract;
import com.jqsoft.nursing.di.module.nursing.DeanCockpitFinanceFragmentModule;
import com.jqsoft.nursing.di.presenter.nursing.DeanCockpitFinanceFragmentPresenter;
import com.jqsoft.nursing.di.ui.activity.WorkbenchActivity;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.BarDataEx;
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
 * 院长驾驶舱-财务
 * Created by Administrator on 2018-04-18.
 */

public class DeanCockpitFinanceFragment extends AbstractFragment implements DeanCockpitFinanceFragmentContract.View {
    @BindView(R.id.ll_institution_name)
    LinearLayout llInstitutionName;
    @BindView(R.id.tv_institution_name)
    TextView tvInstitutionName;

    @BindView(R.id.hbc_chart)
    HorizontalBarChart hbcChart;
    @BindView(R.id.lay_dean_cockpit_finance_load_failure)
    View failureView;

    TextView tvFailureView;

    boolean isRequestSuccess = false;

    int institutionIdIndex = 0;
    String institutionId=Constants.EMPTY_STRING;

    int YEAR_NUMBER = 5;

    @BindView(R.id.nursing_title)
    Spinner index_title;

    private MyAdapter adapter_sp;

    @Inject
    DeanCockpitFinanceFragmentPresenter mPresenter;


    CompositeSubscription mCompositeSubscription;


    public DeanCockpitFinanceFragment() {
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
        return R.layout.fragment_dean_cockpit_finance_statistics_layout;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        registerRxBusEvent();

        initInstitutionSelection();

        initHorizontalBarChart();

        tvFailureView = (TextView) failureView.findViewById(R.id.tv_load_failure_hint);
//        tvFailureView.setText(failureString);
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
                        onRefresh();
                    }
                });

        index_title.setBackgroundColor(Color.parseColor("#067eee"));

        index_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                institutionId=adapter_sp.getItem(position).getsId();
                getFinanceInfo(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void requestGettingOrganizationList(){
        RxBus.getDefault().post(Constants.EVENT_TYPE_NURSING_REQUEST_GETTING_ORGNIZATION_LIST, true);
    }

    public void registerRxBusEvent() {
        Subscription mSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_NURSING_DID_GET_ORGNIZATION_LIST, OrgnizationListAndSuccessBean.class)
                .subscribe(new Action1<OrgnizationListAndSuccessBean>() {
                    @Override
                    public void call(OrgnizationListAndSuccessBean bean) {
                        if (bean==null || ListUtils.isEmpty(bean.getList())){
                            if (bean.isSuccess()) {
                                showSuccessViewOrFailureView(true, true, true);
                            } else {
                                showSuccessViewOrFailureView(false, true, true);
                            }
                        } else {
                            initInstitutionType(institutionIdIndex);
                            getFinanceInfo();
                        }
                    }
                });
        if (this.mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(mSubscription);

    }

    private void unregisterRxBusEvent() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void initInject() {
        super.initInject();
        DaggerApplication.get(getActivity())
                .getAppComponent()
                .addDeanCockpitFinanceFragment(new DeanCockpitFinanceFragmentModule(this))
                .inject(this);

    }

    private void onRefresh() {
        loadInfo();
    }


    private void loadInfo() {
        List<OrgnizationBean> orgList = getOrgnizationList();
        if (!ListUtils.isEmpty(orgList)){
            getFinanceInfo(institutionIdIndex);
        } else {
            requestGettingOrganizationList();
        }
    }


    private void getFinanceInfo(){
        getFinanceInfo(0);
    }

    public void getFinanceInfo(int index) {
        if (index>=0 && index<getOrgnizationList().size()){
            institutionIdIndex = index;
            institutionId = getOrgnizationList().get(index).getsId();
            Map<String, String> map = getDeanCockpitFinanceListRequestMap();
            mPresenter.getDeanCockpitFinanceList(map);

        }

    }

    private Map<String, String> getDeanCockpitFinanceListRequestMap() {
        String userId = IdentityManager.getUserId(getActivity());
        String dateFrom = getDateFrom();
        String dateTo = getDateTo();
        Map<String, String> map = ParametersFactory.getDeanCockpitFinanceListMap(getActivity(), userId,
                "Year", institutionId, dateFrom, dateTo);
        return map;
    }

    private String getDateFrom(){
        return Util.getSpecificNumberYearBeforeDateString(YEAR_NUMBER);
    }

    private String getDateTo(){
        return Util.getCurrentYearDateString();
    }

    private List<DeanCockpitFinanceBean> getStatisticsBean(HttpResultNurseBaseBean<List<DeanCockpitFinanceBean>> bean) {
        if (bean != null) {
            List<DeanCockpitFinanceBean> list = bean.getData();
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

    private void initHorizontalBarChart() {
//        hbcChart.setOnChartValueSelectedListener(this);
        // hbcChart.setHighlightEnabled(false);

        hbcChart.setNoDataText(Constants.CHART_NO_DATA_TEXT);
        hbcChart.setNoDataTextColor(getResources().getColor(R.color.colorTheme));
        hbcChart.setDrawBarShadow(false);

        hbcChart.setDrawValueAboveBar(true);

        hbcChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        hbcChart.setMaxVisibleValueCount(Constants.CHART_MAX_NO_VALUES_ENTRY_NUMBER);

        // scaling can now only be done on x- and y-axis separately
        hbcChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // hbcChart.setDrawBarShadow(true);

        hbcChart.setDrawGridBackground(false);

        XAxis xl = hbcChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
//        xl.setGranularity(Constants.CHART_DEFAULT_BAR_WIDTH);
        xl.setGranularity(1f);
        xl.setDrawLabels(true);
        xl.setCenterAxisLabels(true);
//        xl.setLabelCount(8, true);

        YAxis yl = hbcChart.getAxisLeft();
//        yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = hbcChart.getAxisRight();
//        yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

//        hbcChart.setFitBars(false);
        hbcChart.setFitBars(true);
        hbcChart.animateY(Constants.CHART_ANIMATION_DURATION);


        Legend l = hbcChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
        l.setWordWrapEnabled(true);
    }

    private void setData(List<DeanCockpitFinanceBean> beanList) {
        final List<DeanCockpitFinanceBean> naturalList = getNaturalBeanListFromRawBeanList(beanList);

        float barWidth = Constants.BAR_CHART_ONE_COLUMN_BAR_WIDTH;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
//        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();

//        float randomMultiplier = 3* 100000f;
        showSuccessViewOrFailureView(true, ListUtils.isEmpty(naturalList), false);

        if (ListUtils.isEmpty(naturalList)) {
            return;
        }

        for (int j = 0; j < naturalList.size(); ++j) {
            DeanCockpitFinanceBean cb = naturalList.get(j);
            float f1 = Util.getFloatFromString(cb.getCount());
//            float f2 = Util.getFloatFromString(cb.getHasElder());
            yVals1.add(new BarEntry(0, (float) (f1)));
//            yVals2.add(new BarEntry(1, f2));
        }

        BarDataSet set1/*, set2, set3*/;

//        if (hbcChart.getData() != null && hbcChart.getData().getDataSetCount() > 0) {
//
//            set1 = (BarDataSet) hbcChart.getData().getDataSetByIndex(0);
////            set2 = (BarDataSet) hbcChart.getData().getDataSetByIndex(1);
////            set3 = (BarDataSet) hbcChart.getData().getDataSetByIndex(2);
//            set1.setValues(yVals1);
////            set2.setValues(yVals2);
////            set3.setValues(yVals3);
//            hbcChart.getData().notifyDataChanged();
//            hbcChart.notifyDataSetChanged();
//
//        } else {
        // create 4 DataSets
        set1 = new BarDataSet(yVals1, "产生金额(元)");
        set1.setColor(Constants.CHART_FIRST_COLOR);
//            set2 = new BarDataSet(yVals2, "入住老人数");
//            set2.setColor(Constants.CHART_SECOND_COLOR);
//            set3 = new BarDataSet(yVals3, "注销");
//            set3.setColor(Constants.CHART_THIRD_COLOR);

        BarData data = new BarDataEx(set1);
        Util.setChartDataLargeValueFormatter(data);
//            data.setValueFormatter(new LargeValueFormatter());
//            data.setValueTypeface(mTfLight);
        data.setValueTextSize(Constants.CHART_VALUE_TEXT_SIZE);

        hbcChart.setData(data);
//        }

        // specify the width each bar should have
        hbcChart.getBarData().setBarWidth(barWidth);

        XAxis xAxis = hbcChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
//                return  String.valueOf((int)v);
//                float calculatedV = v / Constants.CHART_DEFAULT_BAR_WIDTH / Constants.NUMBER_PER_DISTRICT;
                int index = (int) (v);
//                LogUtil.i("v:"+v+" calculatedV:"+calculatedV+" index:"+index);

                if (index >= 0 && index < naturalList.size()) {
                    DeanCockpitFinanceBean bean = naturalList.get(index);
                    String chartLabel = Util.getChartLabel(bean.getYear());
                    return chartLabel;
                } else {
                    return Constants.EMPTY_STRING;
                }
            }
        });
        // restrict the x-axis range
        hbcChart.getXAxis().setAxisMinimum(0);

//        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided
//        float axisMaximum = barWidth * Constants.NUMBER_PER_DISTRICT * yVals1.size();
//        axisMaximum+=(yVals1.size()-1)*Constants.CHART_GROUP_SPACE_WIDTH;
//        hbcChart.getXAxis().setAxisMaximum(axisMaximum);
////        hbcChart.getXAxis().setAxisMaximum(2000);
////        hbcChart.groupBars(0, 0.5f, 0f);
//        hbcChart.groupBars(0, Constants.CHART_GROUP_SPACE_WIDTH, 0f);

        int groupCount = yVals1.size();
        float groupSpace = Constants.BAR_CHART_ONE_COLUMN_GROUP_SPACE;
        float barSpace = Constants.BAR_CHART_ONE_COLUMN_BAR_SPACE; // x4 DataSet
//        float barWidth = 0.22f; // x4 DataSet
        // (0.22 + 0.04) * 3 + 0.22 = 1.00 -> interval per "group"
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        float xMaximum = hbcChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount;
        hbcChart.getXAxis().setAxisMaximum(xMaximum);
        hbcChart.groupBars(0, groupSpace, barSpace);

        Util.setYAxisLargeValueFormat(hbcChart.getAxisLeft(), hbcChart.getAxisRight());
        Util.setYAxisSpaceTop(hbcChart.getAxisLeft(), hbcChart.getAxisRight());



//        hbcChart.setVisibleXRangeMaximum(Constants.NUMBER_PER_DISTRICT * yVals1.size() * barWidth);
//        hbcChart.setVisibleXRangeMaximum(Constants.BAR_NUMBER_PER_SCREEN*barWidth);
//        hbcChart.setVisibleXRangeMaximum(Constants.BAR_NUMBER_PER_SCREEN_SMALL*barWidth);
        hbcChart.setVisibleXRangeMinimum(Constants.BAR_NUMBER_PER_SCREEN_FOR_ONE_COLUMN*barWidth);
        hbcChart.setVisibleXRangeMaximum(Constants.BAR_NUMBER_PER_SCREEN_FOR_ONE_COLUMN * barWidth);
        hbcChart.invalidate();


    }

    private List<DeanCockpitFinanceBean> getNaturalBeanListFromRawBeanList(
            List<DeanCockpitFinanceBean> rawList) {
        List<DeanCockpitFinanceBean> resultList = new ArrayList<>();
        try {
            if (ListUtils.isEmpty(rawList)) {
                return resultList;
            } else {
                resultList = rawList;
//                    for (int j = 0; j < rawList.size(); ++j) {
//                        SubsistenceApprovePovertyReasonBean b = rawList.get(j);
//                        DeanCockpitFinanceBean nb = new DeanCockpitFinanceBean();
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

    private void showSuccessViewOrFailureView(boolean success, boolean isListEmpty, boolean isOrgnizationList) {
        if (success) {
            if (isListEmpty) {
                hbcChart.setVisibility(View.GONE);
                failureView.setVisibility(View.VISIBLE);
                tvFailureView.setText(isOrgnizationList ? getOrgnizationListEmptyHint() : getListEmptyHint());
              /*  hilightStatisticsListIndicatorView(false);
                hilightStatisticsChartIndicatorView(true);*/
            } else {
                hbcChart.setVisibility(View.VISIBLE);
                failureView.setVisibility(View.GONE);
               /* if (isChartSelected()) {
                    hilightChart();
                } else {
                    hilightList();
                }*/
            }
        } else {
            hbcChart.setVisibility(View.GONE);
            failureView.setVisibility(View.VISIBLE);
            tvFailureView.setText(isOrgnizationList ? getOrgnizationListFailureHint() : getFailureHint());
           /* hilightStatisticsListIndicatorView(false);
            hilightStatisticsChartIndicatorView(true);*/
        }
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

            adapter_sp = new MyAdapter(getActivity(),list);
            index_title.setAdapter(adapter_sp);
        }
    }

    private List<DeanCockpitFinanceBean> getFilledList(List<DeanCockpitFinanceBean> originalList){
        originalList=(originalList==null) ? new ArrayList<DeanCockpitFinanceBean>() : originalList;
        List<DeanCockpitFinanceBean> result = new ArrayList<>();
        int currentYear = Util.getCurrentYearInt();
        int beginYear = currentYear-YEAR_NUMBER;
        for (int i = 0; i <= YEAR_NUMBER; ++i){
            HasYearDataAndIndex hasYearDataAndIndex = hasYearData(originalList, i+beginYear);
            if (hasYearDataAndIndex.isHasYearData()){
                DeanCockpitFinanceBean originalBean = originalList.get(hasYearDataAndIndex.getIndex());
                DeanCockpitFinanceBean bean = new DeanCockpitFinanceBean(originalBean.getCount(), originalBean.getYear());
                result.add(bean);
            } else {
                DeanCockpitFinanceBean bean = new DeanCockpitFinanceBean(String.valueOf(0), String.valueOf(i+beginYear));
                result.add(bean);
            }
        }
        return result;
    }

    private HasYearDataAndIndex hasYearData(List<DeanCockpitFinanceBean> originalList, int year){
        String yearString = String.valueOf(year);
        if (ListUtils.isEmpty(originalList)){
            return new HasYearDataAndIndex(false, 0);
        } else {
            boolean ret = false;
            int index = 0;
            for (int i = 0; i < originalList.size(); ++i){
                DeanCockpitFinanceBean bean = originalList.get(i);
                if (yearString.equals(bean.getYear())){
                    ret = true;
                    index = i;
                    break;
                }
            }
            HasYearDataAndIndex result = new HasYearDataAndIndex(ret, index);
            return result;
        }
    }

    @Override
    public void onLoadDeanCockpitFinanceListDataSuccess(HttpResultNurseBaseBean<List<DeanCockpitFinanceBean>> bean) {
        List<DeanCockpitFinanceBean> b = getStatisticsBean(bean);
        b=getFilledList(b);
        isRequestSuccess = true;
        if (b != null) {
            setData(b);
        } else {
            showSuccessViewOrFailureView(true, true, false);
        }

    }

    @Override
    public void onLoadDeanCockpitFinanceListDataFailure(String message) {
        isRequestSuccess = false;
        showSuccessViewOrFailureView(false, true, false);
        Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);

    }

}
