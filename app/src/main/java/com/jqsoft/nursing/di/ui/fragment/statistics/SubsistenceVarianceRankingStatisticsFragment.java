package com.jqsoft.nursing.di.ui.fragment.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.jqsoft.nursing.adapter.SubsistenceVarianceRankingStatisticsAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SubsistenceVarianceRankingNaturalBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SubsistenceVarianceRankingSpecificCategoryBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SubsistenceVarianceRankingStatisticsBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.MonthTextBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.QuarterTextBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.YearTextBean;
import com.jqsoft.nursing.di.contract.SubsistenceVarianceRankingStatisticsFragmentContract;
import com.jqsoft.nursing.di.module.SubsistenceVarianceRankingStatisticsFragmentModule;
import com.jqsoft.nursing.di.presenter.SubsistenceVarianceRankingStatisticsFragmentPresenter;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.feature.IDateRange;
import com.jqsoft.nursing.helper.BarDataEx;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;
import com.jqsoft.nursing.popup_window.MonthQuarterYearRangePopupWindow;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.sevenheaven.segmentcontrol.SegmentControl;

import net.qiujuer.genius.ui.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observer;

/**
 * 柱状图
 * 低保变化情况/特困人员变化情况/低收入家庭情况排名统计
 * Created by Administrator on 2018-01-02.
 */

public class SubsistenceVarianceRankingStatisticsFragment extends AbstractFragment implements SubsistenceVarianceRankingStatisticsFragmentContract.View {
    @BindView(R.id.sc_date_range)
    SegmentControl scDateRange;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.ll_subsistence_type)
    LinearLayout llSubsistenceType;
    @BindView(R.id.tv_subsistence_type)
    TextView tvSubsistenceType;
    @BindView(R.id.ll_result_type)
    LinearLayout llResultType;
    @BindView(R.id.tv_result_type)
    TextView tvResultType;
    @BindView(R.id.ll_support_type)
    LinearLayout llSupportType;
    @BindView(R.id.tv_support_type)
    TextView tvSupportType;
    @BindView(R.id.lay_content)
    LinearLayout rlContent;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.tv_review)
    TextView tvReview;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.hbc_chart)
    HorizontalBarChart hbcChart;
    @BindView(R.id.lay_subsistence_variance_ranking_load_failure)
    View failureView;

    @BindView(R.id.ll_statistics_list)
    LinearLayout llStatisticsList;
    @BindView(R.id.iv_statistics_list)
    ImageView ivStatisticsList;
    @BindView(R.id.tv_statistics_list)
    TextView tvStatisticsList;

    @BindView(R.id.ll_statistics_chart)
    LinearLayout llStatisticsChart;
    @BindView(R.id.iv_statistics_chart)
    ImageView ivStatisticsChart;
    @BindView(R.id.tv_statistics_chart)
    TextView tvStatisticsChart;

    TextView tvFailureView;

    boolean isRequestSuccess = false;

    String isAreaOwner=Constants.IS_NOT_AREA_OWNER;

//    String rescueType;//(城乡低保:99	全部  01	农村低保    02	城市低保)   (特困人员供养：03） （低收入家庭：04）

    String methodName;

    @Inject
    SubsistenceVarianceRankingStatisticsFragmentPresenter mPresenter;

    MonthQuarterYearRangePopupWindow dateRangePopupWindow;
    IDateRange selectedDateRange;

    int pageType;

    int subsistenceTypeIndex = Constants.SUBSISTENCE_TYPE_ALL;
    String subsistenceType;
    int resultTypeIndex = Constants.RESCUE_RESULT_TYPE_HOUSEHOLD;
    String resultType;
    int supportTypeIndex = Constants.VERY_POOR_SUPPORT_TYPE_ALL;
    String supportType;

    SubsistenceVarianceRankingStatisticsAdapter adapter;

    public SubsistenceVarianceRankingStatisticsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
    protected int getLayoutId() {
        return R.layout.fragment_subsistence_variance_ranking_statistics_layout;
    }

    @Override
    protected void initData() {
        populateData();
    }

    @Override
    protected void initView() {
        hilightChart();

//        scDateRange.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
//            @Override
//            public void onSegmentControlClick(int i) {
//                LogUtil.i("selected index:" + i);
//                initDateRangePopupWindow(i);
//                onRefresh();
//            }
//        });

        initDateRangePopupWindow(Constants.DATE_RANGE_TYPE_MONTH);

        RxView.clicks(tvDate)
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

        RxView.clicks(btQuery)
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

        if (Constants.PAGE_TYPE_SUBSISTENCE==pageType){
            initSubsistenceType(subsistenceTypeIndex);
        } else if (Constants.PAGE_TYPE_VERY_POOR==pageType){
            initSupportType(supportTypeIndex);
        } else {
        }
        RxView.clicks(tvSubsistenceType)
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
                        String[] typeArray = new String[]{"全部", "农村低保", "城市低保"};
                        List<String> typeList = Arrays.asList(typeArray);
                        Util.showSingleChoiceStringListMaterialDialog(getActivity(), "请选择救助类别", null, typeList,
                                subsistenceTypeIndex, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                        initSubsistenceType(which);
                                        onRefresh();
                                        return false;
                                    }
                                });
                    }
                });

        if (Constants.PAGE_TYPE_SUBSISTENCE==(pageType)){
            llSubsistenceType.setVisibility(View.VISIBLE);
            llSupportType.setVisibility(View.GONE);
        } else if (Constants.PAGE_TYPE_VERY_POOR==(pageType)){
            llSubsistenceType.setVisibility(View.GONE);
            llSupportType.setVisibility(View.VISIBLE);
        } else if (Constants.PAGE_TYPE_LOW_SALARY_FAMILY==(pageType)){
            llSubsistenceType.setVisibility(View.GONE);
            llSupportType.setVisibility(View.GONE);
        }else {
            llSubsistenceType.setVisibility(View.VISIBLE);
            llSupportType.setVisibility(View.GONE);
        }
        initResultType(resultTypeIndex);
        RxView.clicks(tvResultType)
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
                        String[] typeArray = new String[]{"户数", "人数"};
                        List<String> typeList = Arrays.asList(typeArray);
                        Util.showSingleChoiceStringListMaterialDialog(getActivity(), "请选择统计范围", null, typeList,
                                resultTypeIndex, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                        initResultType(which);
                                        onRefresh();
                                        return false;
                                    }
                                });

                    }
                });

//        initSupportType(supportTypeIndex);
        RxView.clicks(tvSupportType)
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
                        String[] typeArray = new String[]{"全部", "农村特困集中供养", "农村特困分散供养", "城市特困集中供养", "城市特困分散供养"};
                        List<String> typeList = Arrays.asList(typeArray);
                        Util.showSingleChoiceStringListMaterialDialog(getActivity(), "请选择特困供养类型", null, typeList,
                                supportTypeIndex, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                        initSupportType(which);
                                        onRefresh();
                                        return false;
                                    }
                                });

                    }
                });

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

        adapter = new SubsistenceVarianceRankingStatisticsAdapter(new ArrayList<SubsistenceVarianceRankingNaturalBean>());
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        adapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
//        Util.addDividerToRecyclerView(getActivity(), recyclerView, true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);
            }
        });

        RxView.clicks(llStatisticsList)
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
                        if (isRequestSuccess) {
                            if (isResultEmpty()){
                                rlContent.setVisibility(View.GONE);
                                hbcChart.setVisibility(View.GONE);
                                failureView.setVisibility(View.VISIBLE);
                                tvFailureView.setText(getListEmptyHint());
                                hilightStatisticsListIndicatorView(true);
                                hilightStatisticsChartIndicatorView(false);

                            } else {
                                rlContent.setVisibility(View.VISIBLE);
                                hbcChart.setVisibility(View.GONE);
                                failureView.setVisibility(View.GONE);
                                hilightStatisticsListIndicatorView(true);
                                hilightStatisticsChartIndicatorView(false);
                            }
                        } else {
                            rlContent.setVisibility(View.GONE);
                            hbcChart.setVisibility(View.GONE);
                            failureView.setVisibility(View.VISIBLE);
                            tvFailureView.setText(getFailureHint());
                            hilightStatisticsListIndicatorView(true);
                            hilightStatisticsChartIndicatorView(false);

                        }
                    }
                });

        RxView.clicks(llStatisticsChart)
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
                        if (isRequestSuccess) {
                            if (isResultEmpty()){
                                rlContent.setVisibility(View.GONE);
                                hbcChart.setVisibility(View.GONE);
                                failureView.setVisibility(View.VISIBLE);
                                tvFailureView.setText(getListEmptyHint());
                                hilightStatisticsListIndicatorView(false);
                                hilightStatisticsChartIndicatorView(true);

                            } else {
                                rlContent.setVisibility(View.GONE);
                                hbcChart.setVisibility(View.VISIBLE);
                                failureView.setVisibility(View.GONE);
                                hilightStatisticsListIndicatorView(false);
                                hilightStatisticsChartIndicatorView(true);
                            }
                        } else {
                            rlContent.setVisibility(View.GONE);
                            hbcChart.setVisibility(View.GONE);
                            failureView.setVisibility(View.VISIBLE);
                            tvFailureView.setText(getFailureHint());
                            hilightStatisticsListIndicatorView(false);
                            hilightStatisticsChartIndicatorView(true);

                        }

                    }
                });
    }

    private boolean isChartSelected(){
        boolean b = tvStatisticsList.getCurrentTextColor()==getResources().getColor(R.color.colorTheme);
        return !b;
    }

    private boolean isResultEmpty(){
        return ListUtils.isEmpty(adapter.getData());
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

//    private void setData(SubsistenceVarianceRankingStatisticsBean bean) {
//        float barWidth = Constants.CHART_DEFAULT_BAR_WIDTH;
//
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
//        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();
//
////        float randomMultiplier = 3* 100000f;
//        final List<String> titleList = bean.getxTitle();
//        List<SubsistenceVarianceRankingSpecificCategoryBean> categoryList = bean.getList();
//        List<SubsistenceVarianceRankingNaturalBean> naturalList = getNaturalBeanListFromRawBeanList(titleList, categoryList);
//        //显示列表
////        adapter.setTitleList(titleList);
//        adapter.setNewData(naturalList);
//        showSuccessViewOrFailureView(true, ListUtils.isEmpty(adapter.getData()));
//
//        if (ListUtils.isEmpty(titleList) || ListUtils.isEmpty(categoryList)) {
//            return;
//        }
//        int entityNumber = ListUtils.getSize(titleList);
//
//        for (int j = 0; j < categoryList.size(); ++j) {
//            SubsistenceVarianceRankingSpecificCategoryBean cb = categoryList.get(j);
//            List<Float> cbItem = cb.getList();
//            for (int i = 0; i < entityNumber; i++) {
//                Float f = cbItem.get(i);
//                if (j == 0) {
//                    yVals1.add(new BarEntry(i, (float) (f)));
//                } else if (j == 1) {
//                    yVals2.add(new BarEntry(i, (float) (f)));
//
//                } else if (j == 2) {
//                    yVals3.add(new BarEntry(i, (float) (f)));
//
//                }
//            }
//        }
//
//        BarDataSet set1, set2, set3;
//
//        if (hbcChart.getData() != null && hbcChart.getData().getDataSetCount() > 0) {
//
//            set1 = (BarDataSet) hbcChart.getData().getDataSetByIndex(0);
//            set2 = (BarDataSet) hbcChart.getData().getDataSetByIndex(1);
//            set3 = (BarDataSet) hbcChart.getData().getDataSetByIndex(2);
//            set1.setValues(yVals1);
//            set2.setValues(yVals2);
//            set3.setValues(yVals3);
//            hbcChart.getData().notifyDataChanged();
//            hbcChart.notifyDataSetChanged();
//
//        } else {
//            // create 4 DataSets
//            set1 = new BarDataSet(yVals1, "新增");
//            set1.setColor(Constants.CHART_FIRST_COLOR);
//            set2 = new BarDataSet(yVals2, "复查");
//            set2.setColor(Constants.CHART_SECOND_COLOR);
//            set3 = new BarDataSet(yVals3, "注销");
//            set3.setColor(Constants.CHART_THIRD_COLOR);
//
//            BarData data = new BarDataEx(set1, set2, set3);
//            data.setValueFormatter(new LargeValueFormatter());
////            data.setValueTypeface(mTfLight);
//
//            hbcChart.setData(data);
//        }
//
//        // specify the width each bar should have
//        hbcChart.getBarData().setBarWidth(barWidth);
//
//        XAxis xAxis = hbcChart.getXAxis();
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float v, AxisBase axisBase) {
//                float calculatedV = v / Constants.CHART_DEFAULT_BAR_WIDTH / Constants.NUMBER_PER_DISTRICT;
//                int index = (int) (calculatedV);
////                LogUtil.i("v:"+v+" calculatedV:"+calculatedV+" index:"+index);
//                if (index >= 0 && index < titleList.size()) {
//                    return titleList.get(index);
//                } else {
//                    return Constants.EMPTY_STRING;
//                }
//            }
//        });
//        // restrict the x-axis range
//        hbcChart.getXAxis().setAxisMinimum(0);
//
//        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided
//        float axisMaximum = barWidth * Constants.NUMBER_PER_DISTRICT * yVals1.size();
//        axisMaximum+=(yVals1.size()-1)*Constants.CHART_GROUP_SPACE_WIDTH;
//        hbcChart.getXAxis().setAxisMaximum(axisMaximum);
////        hbcChart.getXAxis().setAxisMaximum(2000);
////        hbcChart.groupBars(0, 0.5f, 0f);
//        hbcChart.groupBars(0, Constants.CHART_GROUP_SPACE_WIDTH, 0f);
//
//
//
////        hbcChart.setVisibleXRangeMaximum(Constants.NUMBER_PER_DISTRICT * yVals1.size() * barWidth);
//        hbcChart.setVisibleXRangeMaximum(Constants.BAR_NUMBER_PER_SCREEN*barWidth);
//        hbcChart.invalidate();
//
//
//    }

    private void initRecyclerViewHeaderVisibilityAndValue(String resultType) {
        String newBase = "新增数量";
        String reviewBase = "复查数量";
        String deleteBase = "注销数量";
        String suffix = Constants.EMPTY_STRING;
        if (Constants.RESCUE_RESULT_TYPE_VALUE_HOUSEHOLD.equals(resultType)){
            suffix="(户)";
        } else if (Constants.RESCUE_RESULT_TYPE_VALUE_PERSON.equals(resultType)){
            suffix="(人)";
        }
        tvNew.setText(newBase+suffix);
        tvReview.setText(reviewBase+suffix);
        tvDelete.setText(deleteBase+suffix);
    }
    private void setData(SubsistenceVarianceRankingStatisticsBean bean) {

        initRecyclerViewHeaderVisibilityAndValue(resultType);

        float barWidth = Constants.BAR_CHART_THREE_COLUMN_BAR_WIDTH;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();

//        float randomMultiplier = 3* 100000f;
        final List<String> titleList = bean.getxTitle();
        List<SubsistenceVarianceRankingSpecificCategoryBean> categoryList = bean.getList();
        List<SubsistenceVarianceRankingNaturalBean> naturalList = getNaturalBeanListFromRawBeanList(titleList, categoryList);
        //显示列表
//        adapter.setTitleList(titleList);
        adapter.setNewData(naturalList);
        showSuccessViewOrFailureView(true, ListUtils.isEmpty(adapter.getData()));

        if (ListUtils.isEmpty(titleList) || ListUtils.isEmpty(categoryList)) {
            return;
        }

        List<Float> fList = new ArrayList<>();
        int entityNumber = ListUtils.getSize(titleList);

        for (int j = 0; j < categoryList.size(); ++j) {
            SubsistenceVarianceRankingSpecificCategoryBean cb = categoryList.get(j);
            List<String> cbItem = cb.getList();
//            fList.addAll(cbItem);
            for (int i = 0; i < entityNumber; i++) {
                String str = cbItem.get(i);
                float f = Util.getFloatFromString(str);
                fList.add(f);
                if (j == 0) {
                    yVals1.add(new BarEntry(i, (float) (f)));
                } else if (j == 1) {
                    yVals2.add(new BarEntry(i, (float) (f)));

                } else if (j == 2) {
                    yVals3.add(new BarEntry(i, (float) (f)));

                }
            }
        }

        BarDataSet set1, set2, set3;

//        if (hbcChart.getData() != null && hbcChart.getData().getDataSetCount() > 0) {
//
//            set1 = (BarDataSet) hbcChart.getData().getDataSetByIndex(0);
//            set2 = (BarDataSet) hbcChart.getData().getDataSetByIndex(1);
//            set3 = (BarDataSet) hbcChart.getData().getDataSetByIndex(2);
//            set1.setValues(yVals1);
//            set2.setValues(yVals2);
//            set3.setValues(yVals3);
//            hbcChart.getData().notifyDataChanged();
//            hbcChart.notifyDataSetChanged();
//
//        } else {
            // create 4 DataSets
            set1 = new BarDataSet(yVals1, "新增");
            set1.setColor(Constants.CHART_FIRST_COLOR);
            set1.setValueTextSize(Constants.CHART_VALUE_TEXT_SIZE);
            set2 = new BarDataSet(yVals2, "复查");
            set2.setColor(Constants.CHART_SECOND_COLOR);
            set2.setValueTextSize(Constants.CHART_VALUE_TEXT_SIZE);
            set3 = new BarDataSet(yVals3, "注销");
            set3.setColor(Constants.CHART_THIRD_COLOR);
            set3.setValueTextSize(Constants.CHART_VALUE_TEXT_SIZE);

            BarData data = new BarDataEx(set1, set2, set3);
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
                if (index >= 0 && index < titleList.size()) {
                    String s = titleList.get(index);
                    String chartLabel = Util.getChartLabel(s);
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
        float groupSpace = Constants.BAR_CHART_THREE_COLUMN_GROUP_SPACE;
        float barSpace = Constants.BAR_CHART_THREE_COLUMN_BAR_SPACE; // x4 DataSet
//        float barWidth = 0.22f; // x4 DataSet
        // (0.22 + 0.04) * 3 + 0.22 = 1.00 -> interval per "group"
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        float xMaximum = hbcChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount;
        hbcChart.getXAxis().setAxisMaximum(xMaximum);
        hbcChart.groupBars(0, groupSpace, barSpace);

//        hbcChart.groupBars(xMaximum, -groupSpace, -barSpace);

        Util.setYAxisLargeValueFormat(hbcChart.getAxisLeft(), hbcChart.getAxisRight());
        Util.setYAxisSpaceTop(hbcChart.getAxisLeft(), hbcChart.getAxisRight());



//        hbcChart.setVisibleXRangeMaximum(Constants.NUMBER_PER_DISTRICT * yVals1.size() * barWidth);
        hbcChart.setVisibleXRangeMaximum(Constants.BAR_NUMBER_PER_SCREEN*barWidth);

//        hbcChart.getXAxis().setAxisMinimum(Constants.BAR_NUMBER_PER_SCREEN*barWidth);
//        hbcChart.getXAxis().setAxisMaximum(0);

//        hbcChart.setVisibleXRangeMaximum(0);
//        hbcChart.setVisibleXRangeMinimum(Constants.BAR_NUMBER_PER_SCREEN*barWidth);

        hbcChart.invalidate();

//        hbcChart.getRendererXAxis().computeAxis(0, xMaximum, true);
    }

    private List<SubsistenceVarianceRankingNaturalBean> getNaturalBeanListFromRawBeanList(
            List<String> titleList,
            List<SubsistenceVarianceRankingSpecificCategoryBean> rawList){
        List<SubsistenceVarianceRankingNaturalBean> resultList = new ArrayList<>();
        try {
            if (ListUtils.isEmpty(rawList) ||
                    ListUtils.isEmpty(titleList)){
                return resultList;
            } else {
//                for (int i = 0; i < ListUtils.getSize(rawList); ++i){
                    SubsistenceVarianceRankingSpecificCategoryBean bean0 = rawList.get(0);
                    SubsistenceVarianceRankingSpecificCategoryBean bean1 = rawList.get(1);
                    SubsistenceVarianceRankingSpecificCategoryBean bean2 = rawList.get(2);
                    for (int j = 0; bean0!=null&&bean0.getList()!=null&&j<ListUtils.getSize(bean0.getList()); ++j) {
                        SubsistenceVarianceRankingNaturalBean nb = new SubsistenceVarianceRankingNaturalBean();
                        nb.setDistrictName(titleList.get(j));
                        nb.setNewQuantity(Util.getForcedIntStringFromDoubleString(bean0.getList().get(j)));
                        nb.setReviewQuantity(Util.getForcedIntStringFromDoubleString(bean1.getList().get(j)));
                        nb.setDeleteQuantity(Util.getForcedIntStringFromDoubleString(bean2.getList().get(j)));
                        resultList.add(nb);
                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return resultList;
    }

    private void hilightChart(){
        hilightStatisticsListIndicatorView(false);
        hilightStatisticsChartIndicatorView(true);

    }

    private void hilightList(){
        hilightStatisticsListIndicatorView(true);
        hilightStatisticsChartIndicatorView(false);

    }

    private void showSuccessViewOrFailureView(boolean success, boolean isListEmpty) {
        if (isChartSelected()) {
            hilightChart();
        } else {
            hilightList();
        }
        if (success) {
            if (isListEmpty) {
                adapter.getData().clear();
                rlContent.setVisibility(View.GONE);
                hbcChart.setVisibility(View.GONE);
                failureView.setVisibility(View.VISIBLE);
                tvFailureView.setText(getListEmptyHint());
              /*  hilightStatisticsListIndicatorView(false);
                hilightStatisticsChartIndicatorView(true);*/
            } else {
                if (isChartSelected()){
                    rlContent.setVisibility(View.GONE);
                    hbcChart.setVisibility(View.VISIBLE);
                } else {
                    rlContent.setVisibility(View.VISIBLE);
                    hbcChart.setVisibility(View.GONE);
                }
                failureView.setVisibility(View.GONE);
               /* if (isChartSelected()) {
                    hilightChart();
                } else {
                    hilightList();
                }*/
            }
        } else {
            adapter.getData().clear();
            rlContent.setVisibility(View.GONE);
            hbcChart.setVisibility(View.GONE);
            failureView.setVisibility(View.VISIBLE);
            tvFailureView.setText(getFailureHint());
          /*  hilightStatisticsListIndicatorView(false);
            hilightStatisticsChartIndicatorView(true);*/
        }
    }


    private void hilightStatisticsListIndicatorView(boolean hilight) {
        if (!hilight) {
            ivStatisticsList.setImageResource(R.mipmap.g_statistics_list_n);
            tvStatisticsList.setTextColor(getResources().getColor(R.color.gray));
        } else {
            ivStatisticsList.setImageResource(R.mipmap.g_statistics_list_h);
            tvStatisticsList.setTextColor(getResources().getColor(R.color.colorTheme));
        }

    }

    private void hilightStatisticsChartIndicatorView(boolean hilight) {
        if (!hilight) {
            ivStatisticsChart.setImageResource(R.mipmap.g_statistics_chart_n);
            tvStatisticsChart.setTextColor(getResources().getColor(R.color.gray));
        } else {
            ivStatisticsChart.setImageResource(R.mipmap.g_statistics_chart_h);
            tvStatisticsChart.setTextColor(getResources().getColor(R.color.colorTheme));
        }
    }

    private String getListEmptyHint() {
        return getResources().getString(R.string.hint_list_empty_please_reload);
    }

    private String getFailureHint() {
        return getResources().getString(R.string.hint_load_failure);
    }

    private void populateData(){
                pageType=getDeliveredInt(Constants.PAGE_TYPE_KEY);
                subsistenceType=getDeliveredString(Constants.ITEM_TYPE_KEY);
                isAreaOwner=getDeliveredString(Constants.IS_AREA_OWNER_KEY);
                methodName=getDeliveredString(Constants.METHOD_NAME_KEY);
    }

    private void initResultType(int index) {
        String presentation = Constants.EMPTY_STRING;
        String tempResultType = Constants.EMPTY_STRING;
        if (Constants.RESCUE_RESULT_TYPE_HOUSEHOLD == index) {
            presentation = "户数";
            tempResultType = Constants.RESCUE_RESULT_TYPE_VALUE_HOUSEHOLD;
        } else if (Constants.RESCUE_RESULT_TYPE_PERSON == index) {
            presentation = "人数";
            tempResultType = Constants.RESCUE_RESULT_TYPE_VALUE_PERSON;
        }
        tvResultType.setText(presentation);
        resultType = tempResultType;
        resultTypeIndex = index;
    }

    private void initSupportType(int index) {
        String presentation = Constants.EMPTY_STRING;
        String tempResultType = Constants.EMPTY_STRING;
        if (Constants.VERY_POOR_SUPPORT_TYPE_ALL==index){
            presentation = "全部";
            tempResultType = Constants.VERY_POOR_SUPPORT_TYPE_ALL_VALUE;
        } else if (Constants.VERY_POOR_SUPPORT_TYPE_RURAL_FOCUS == index) {
            presentation = "农村特困集中供养";
            tempResultType = Constants.VERY_POOR_SUPPORT_TYPE_RURAL_FOCUS_VALUE;
        } else if (Constants.VERY_POOR_SUPPORT_TYPE_RURAL_DISPERSE == index) {
            presentation = "农村特困分散供养";
            tempResultType = Constants.VERY_POOR_SUPPORT_TYPE_RURAL_DISPERSE_VALUE;
        } else if (Constants.VERY_POOR_SUPPORT_TYPE_URBAN_FOCUS == index){
            presentation = "城市特困集中供养";
            tempResultType = Constants.VERY_POOR_SUPPORT_TYPE_URBAN_FOCUS_VALUE;
        } else if (Constants.VERY_POOR_SUPPORT_TYPE_URBAN_DISPERSE == index){
            presentation = "城市特困分散供养";
            tempResultType = Constants.VERY_POOR_SUPPORT_TYPE_URBAN_DISPERSE_VALUE;
        }
        tvSupportType.setText(presentation);
        supportType = tempResultType;
        supportTypeIndex = index;
    }

    private void initSubsistenceType(int index) {
        String presentation = Constants.EMPTY_STRING;
        String tempHouseholdType = Constants.EMPTY_STRING;
        if (Constants.SUBSISTENCE_TYPE_ALL == index) {
            presentation = "全部";
            tempHouseholdType = Constants.SUBSISTENCE_TYPE_VALUE_ALL;
        } else if (Constants.SUBSISTENCE_TYPE_RURAL == index) {
            presentation = "农村低保";
            tempHouseholdType = Constants.SUBSISTENCE_TYPE_VALUE_RURAL;
        } else if (Constants.SUBSISTENCE_TYPE_URBAN == index) {
            presentation = "城市低保";
            tempHouseholdType = Constants.SUBSISTENCE_TYPE_VALUE_URBAN;
        }
        tvSubsistenceType.setText(presentation);
        subsistenceType = tempHouseholdType;
        subsistenceTypeIndex = index;
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
        dateRangePopupWindow = new MonthQuarterYearRangePopupWindow(getActivity(), Constants.POPUP_WINDOW_WIDTH_FOR_STATISTICS, Constants.POPUP_WINDOW_HEIGHT, tvDate, list);
        dateRangePopupWindow.setDateRangeItemClickListener(new MonthQuarterYearRangePopupWindow.DateRangeItemClickListener() {
            @Override
            public void dateRangeItemDidClick(IDateRange iDateRange) {
                selectedDateRange = iDateRange;
                setDateRangeString();
                onRefresh();
            }
        });
    }

    private void setDateRangeString() {
        if (selectedDateRange != null) {
            tvDate.setText(selectedDateRange.getPresentation());
        }

    }

    private String getItemType(){
        return subsistenceType;
    }

    private String getIsAreaOwner(){
        return isAreaOwner;
    }

    private String getMonth(){
        if (selectedDateRange != null) {
            return selectedDateRange.getMonth();
        } else {
            return Constants.EMPTY_STRING;
        }
    }

    private String getStartDateString() {
        if (selectedDateRange != null) {
            return selectedDateRange.getStartDateString();
        } else {
            return Constants.EMPTY_STRING;
        }
    }

    private String getEndDateString() {
        if (selectedDateRange != null) {
            return selectedDateRange.getEndDateString();
        } else {
            return Constants.EMPTY_STRING;
        }
    }

    private String getAreaCodeString() {
        String areaCode = IdentityManager.getSrcLoginResultBean(getActivity()).getAreaId();
        return areaCode;
//        return Constants.EMPTY_STRING;
    }

    @Override
    protected void loadData() {
        onRefresh();
    }

    @Override
    protected void initInject() {
        DaggerApplication.get(getActivity())
                .getAppComponent()
                .addSubsistenceVarianceRankingStatisticsFragment(new SubsistenceVarianceRankingStatisticsFragmentModule(this))
                .inject(this);
    }

    private void onRefresh() {
        loadInfo();
    }

    private void loadInfo() {
        Map<String, String> map = getRankingListRequestMap();
        mPresenter.main(map);
    }

    private Map<String, String> getRankingListRequestMap() {
        Map<String, String> map = ParametersFactory.getSubsistenceVarianceRankingStatisticsListMap(getActivity(), subsistenceType,
                resultType, supportType, getMonth(), getIsAreaOwner(), getAreaCodeString(), methodName);
        return map;
    }

    private SubsistenceVarianceRankingStatisticsBean getStatisticsBean(GCAHttpResultBaseBean<List<SubsistenceVarianceRankingStatisticsBean>> bean) {
        if (bean != null) {
            List<SubsistenceVarianceRankingStatisticsBean> list = bean.getData();
            if (!ListUtils.isEmpty(list)) {
                return list.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void onLoadListSuccess(GCAHttpResultBaseBean<List<SubsistenceVarianceRankingStatisticsBean>> bean) {
        SubsistenceVarianceRankingStatisticsBean b = getStatisticsBean(bean);
        isRequestSuccess = true;
        if (b != null) {
            setData(b);
        } else {
            showSuccessViewOrFailureView(true, true);
        }
    }

    @Override
    public void onLoadListFailure(String message) {
        isRequestSuccess=false;
        showSuccessViewOrFailureView(false, true);
        Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);


        //模拟加载数据成功
//        simulate();

    }

    private void addRecyclerViewTitle(){

    }

    private void simulate() {
//        isRequestSuccess = true;
//        showSuccessViewOrFailureView(true, false);
//
//        String[] titleArray = {"蜀山区", "庐阳区", "区3", "区4", "区5", "区6", "区7", "区8", "区9", "区10"
//                , "区11", "区12", "区13", "区14", "区15", "区16", "区17"/*, "区18"*/};
//        List<String> titleList = Arrays.asList(titleArray);
//        List<SubsistenceVarianceRankingSpecificCategoryBean> dataList = new ArrayList<>();
//        for (int i = 0; i < 3; ++i) {
//            List<Float> intList = new ArrayList<>();
//            for (int j = 0; j < titleArray.length; ++j) {
//                intList.add(120.f + j);
//            }
//            SubsistenceVarianceRankingSpecificCategoryBean b = new SubsistenceVarianceRankingSpecificCategoryBean("新增" + i, intList);
//            dataList.add(b);
//        }
//        SubsistenceVarianceRankingStatisticsBean bean = new SubsistenceVarianceRankingStatisticsBean(titleList, dataList);
//        setData(bean);
    }
}
