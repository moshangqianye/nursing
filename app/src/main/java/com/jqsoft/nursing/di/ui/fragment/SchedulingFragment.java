package com.jqsoft.nursing.di.ui.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.flyco.roundview.RoundTextView;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.ArticleAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.Article;
import com.jqsoft.nursing.bean.SchedulingBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.CenterListBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.HelpListBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.PersonCollectionBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;
import com.jqsoft.nursing.di.contract.SchedulingFragmentContract;
import com.jqsoft.nursing.di.contract.UseCollectionFragmentContract;
import com.jqsoft.nursing.di.module.SchedulingFragmentModule;
import com.jqsoft.nursing.di.module.UseCollectionFragmentModule;
import com.jqsoft.nursing.di.presenter.SchedulingFragmentPresenter;

import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.view.GroupItemDecoration;
import com.jqsoft.nursing.view.GroupRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;


/**排班**/
@SuppressLint("ValidFragment")
public class SchedulingFragment extends AbstractFragment implements View.OnClickListener,
        SchedulingFragmentContract.View,  CalendarView.OnDateSelectedListener,
        CalendarView.OnYearChangeListener{
    @BindView(R.id.tv_month_day)
    TextView mTextMonthDay;
    @BindView(R.id.tv_year)
    TextView mTextYear;
    @BindView(R.id.tv_lunar)
    TextView mTextLunar;
    @BindView(R.id.tv_current_day)
    TextView mTextCurrentDay;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.rl_tool)
    RelativeLayout mRelativeTool;
    @BindView(R.id.calendarLayout)
    CalendarLayout mCalendarLayout;
    private int mYear;
    List<Calendar> schemes;
    private  View rootView;
    @BindView(R.id.recyclerView)
    GroupRecyclerView mRecyclerView;
    ArticleAdapter articleAdapter;
    int year;

    @Inject
    SchedulingFragmentPresenter mPresenter;
    public static SchedulingFragment getInstance(String title) {
        SchedulingFragment sf = new SchedulingFragment();
        return sf;
    }

    @Override
    protected void initInject() {
        DaggerApplication.get(getContext())
                .getAppComponent()
                .addSchedulingFragment(new SchedulingFragmentModule(this))
                .inject(this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }





    @Override
    protected void initData() {
    schemes = new ArrayList<>();
        year = mCalendarView.getCurYear();

        int month = mCalendarView.getCurMonth();




        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new GroupItemDecoration<String,Article>());
             articleAdapter=    new ArticleAdapter(getContext());
        mRecyclerView.setAdapter(articleAdapter);
        mRecyclerView.notifyDataSetChanged();

    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {

        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarView.showYearSelectLayout(mYear);
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        rootView. findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout = (CalendarLayout) rootView.findViewById(R.id.calendarLayout);
        mCalendarView.setOnDateSelectedListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));


    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color,String text,String cotent) {
        Calendar calendar = new Calendar();

        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        Calendar.Scheme scheme= new Calendar.Scheme();
        scheme.setOther(cotent);
        calendar.addScheme(scheme);
        calendar.addScheme(0xFF008800,"班");
        return calendar;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();




        if (isClick) {
            if (null==calendar.getSchemes()){
                articleAdapter.setContent("今天没有排班","");
            }else {

               String str= calendar.getSchemes().get(0).getOther();
                int index=str.indexOf("_");

                articleAdapter.setContent(str.substring(0,index),str.substring(index,str.length()));
                articleAdapter.notifyDataSetChanged();
                mRecyclerView.notifyDataSetChanged();
//            Toast.makeText(getActivity(), getCalendarText(calendar), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), calendar.getSchemes().get(0).getOther(), Toast.LENGTH_SHORT).show();

            }

        }
    }
//    private static String getCalendarText(Calendar calendar) {
//        return String.format("新历%s \n 农历%s \n 公历节日：%s \n 农历节日：%s \n 节气：%s \n 是否闰月：%s",
//                calendar.getMonth() + "月" + calendar.getDay() + "日",
//                calendar.getLunarCakendar().getMonth() + "月" + calendar.getLunarCakendar().getDay() + "日",
//                TextUtils.isEmpty(calendar.getGregorianFestival()) ? "无" : calendar.getGregorianFestival(),
//                TextUtils.isEmpty(calendar.getTraditionFestival()) ? "无" : calendar.getTraditionFestival(),
//                TextUtils.isEmpty(calendar.getSolarTerm()) ? "无" : calendar.getSolarTerm(),
//                calendar.getLeapMonth() == 0 ? "否" : String.format("闰%s月", calendar.getLeapMonth()));
//    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }



    @Override
    protected void loadData() {
        String userId = IdentityManager.getUserId(getActivity());
        Map<String, String> map = ParametersFactory.getSchedulingMap(getContext(),userId,"2018-01-01","2018-03-20"
                );
        mPresenter.main(map, false);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }


    @Override
    public void onLoadListSuccess(HttpResultNurseBaseBean<List<SchedulingBean>> beanList) {
        List<SchedulingBean> list = getListFromResult(beanList);
        year = mCalendarView.getCurYear();
        for (int i=0;i<list.size();i++){

           int  month= Integer.parseInt(list.get(i).getSchedulDate().substring(6,7));
           int day= Integer.parseInt(list.get(i).getSchedulDate().substring(9,10));

            schemes.add(getSchemeCalendar(year, month, day, 0xFF40db25,"班",list.get(i).getSchedulName()+"_"+list.get(i).getSchedulElders()));

        }

        mRecyclerView.notifyDataSetChanged();
        mCalendarView.setSchemeDate(schemes);


//        PersonCollectionBean personCollectionBeanlist=list.get(0);
//        CenterList =personCollectionBeanlist.getCenterList();
//        HelpList =personCollectionBeanlist.getHelpList();

//        collection_size.setText(String.valueOf(CenterList.size()+HelpList.size()));

    }

    @Override
    public void onLoadMoreListSuccess(HttpResultNurseBaseBean<List<SchedulingBean> > beanList) {


    }

    @Override
    public void onLoadListFailure(String message, boolean isLoadMore) {

    }
    public List<SchedulingBean> getListFromResult(HttpResultNurseBaseBean<List<SchedulingBean>> beanList) {
        if (beanList != null) {
            List<SchedulingBean> list = beanList.getData();
            return list;
        } else {
            return null;
        }
    }





}









