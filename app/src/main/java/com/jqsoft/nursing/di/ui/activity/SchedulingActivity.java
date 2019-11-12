package com.jqsoft.nursing.di.ui.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.jqsoft.nursing.bean.grassroots_civil_administration.AboutInfoBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;
import com.jqsoft.nursing.di.contract.AboutInfoContract;
import com.jqsoft.nursing.di.contract.SchedulingActivityContract;
import com.jqsoft.nursing.di.contract.SchedulingFragmentContract;
import com.jqsoft.nursing.di.module.AboutInfoActivityModule;
import com.jqsoft.nursing.di.module.SchedulingActivityModule;
import com.jqsoft.nursing.di.presenter.AboutInfoPresenter;
import com.jqsoft.nursing.di.presenter.SchedulingActivityPresenter;
import com.jqsoft.nursing.di.presenter.SchedulingFragmentPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.utils.DateUtil;
import com.jqsoft.nursing.view.GroupItemDecoration;
import com.jqsoft.nursing.view.GroupRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;


/**排班**/
public class SchedulingActivity extends AbstractActivity implements View.OnClickListener,
        SchedulingActivityContract.View,  CalendarView.OnDateSelectedListener,CalendarView.OnMonthChangeListener,
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
    @BindView(R.id.fl_current)
    View rootView;
    @BindView(R.id.recyclerView)
    GroupRecyclerView mRecyclerView;
    ArticleAdapter articleAdapter;
    @BindView(R.id.setting_title)
    TextView setting_title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    int year;
    List<SchedulingBean> list;
    int flag=0;
    @Inject
    SchedulingActivityPresenter mPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initData() {
        schemes = new ArrayList<>();
        year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new GroupItemDecoration<String,Article>());
        articleAdapter=    new ArticleAdapter(this);
        mRecyclerView.setAdapter(articleAdapter);
        mRecyclerView.notifyDataSetChanged();

    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setToolBar(toolbar, Constants.EMPTY_STRING);
        setting_title.setText("工作日程");

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
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();


            }
        });


        mCalendarLayout = (CalendarLayout)findViewById(R.id.calendarLayout);
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
                articleAdapter.setContent("今天没有排班"," ");
            }else {

                String str= calendar.getSchemes().get(0).getOther();
                int index=str.indexOf("_");

                articleAdapter.setContent(str.substring(0,index),str.substring(index,str.length()));
                articleAdapter.notifyDataSetChanged();
                mRecyclerView.notifyDataSetChanged();

            }

        }else {
            if (flag!=0){

                if (mCalendarView.getCurMonth()!=calendar.getMonth()){
                    articleAdapter.setContent("今天没有排班"," ");
                    for (int i=0;i<list.size();i++){

                        int day1= Integer.parseInt(list.get(i).getSchedulDate().substring(8,10));
                        int mouth1=Integer.parseInt(list.get(i).getSchedulDate().substring(5,7));
                        if (day1==calendar.getDay()&&mouth1==calendar.getMonth()){
                            articleAdapter.setContent(list.get(i).getSchedulName(),list.get(i).getSchedulElders());
                        }

                    }
                }else {
                    articleAdapter.setContent("今天没有排班"," ");
                    for (int i=0;i<list.size();i++) {

                        int day1 = Integer.parseInt(list.get(i).getSchedulDate().substring(8, 10));
                        int mouth1= Integer.parseInt(list.get(i).getSchedulDate().substring(5, 7));
                        int year1= Integer.parseInt(list.get(i).getSchedulDate().substring(0, 4));
                        if ( mCalendarView.getCurDay() == day1 && mCalendarView.getCurMonth() == mouth1&& mCalendarView.getCurYear() == year1 ) {
                            articleAdapter.setContent(list.get(i).getSchedulName(), list.get(i).getSchedulElders());
                        }

                    }
                }}
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
        String userId = IdentityManager.getUserId(this);
        Map<String, String> map = ParametersFactory.getSchedulingMap(this,userId,"2018-01-01","2018-03-20"
        );
        Map<String, String> map1 = ParametersFactory.getSchedulingMap1(this,userId
        );
        mPresenter.main(map1, false);
    }





    @Override
    protected void initInject() {
        DaggerApplication.get(this)
                .getAppComponent()
                .addSchedulingActivity(new SchedulingActivityModule(this))
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_my_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    public static long Date2ms(String _data){
        SimpleDateFormat format =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(_data);
            return date.getTime();
        }catch(Exception e){
            return 0;
        }
    }
    @Override
    public void onLoadListSuccess(HttpResultNurseBaseBean<List<SchedulingBean>> beanList) {
        list = getListFromResult(beanList);
        year = mCalendarView.getCurYear();

        java.util.Calendar c = java.util.Calendar.getInstance();//可以对每个时间域单独修改
        articleAdapter.setContent("今天没有排班","  ");
        int monthNow = c.get(java.util.Calendar.MONTH) + 1;
        int dateNow = c.get(java.util.Calendar.DATE);
//
        for (int i=0;i<list.size();i++){
            if (!"休息".equals( list.get(i).getSchedulName())){

                SchedulingBean list2=list.get(i);
//            int  month= Integer.parseInt(list.get(i).getSchedulDate().substring(6,7));
//            int day= Integer.parseInt(list.get(i).getSchedulDate().substring(8,10));

                String data=   DateUtil.subStandardTime(list.get(i).getSchedulDate());
                java.util.Calendar data1= DateUtil.formatDate(data);
                int  month=data1.get(java.util.Calendar.MONTH)+1;
                int day= data1.get(java.util.Calendar.DAY_OF_MONTH);
              String SchedulElders=  list.get(i).getSchedulElders();
              if ("null".equals(SchedulElders)){
                  SchedulElders="排班信息为空！";
              }
                schemes.add(getSchemeCalendar(year, month, day, 0xFF40db25,"班",list.get(i).getSchedulName()+"_"+SchedulElders));
                if (month==monthNow&&dateNow==day){
                    articleAdapter.setContent(list.get(i).getSchedulName(),list.get(i).getSchedulElders());
                }
            }



        }

        flag=1;

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


    @Override
    public void onMonthChange(int year, int month) {
//        if (mCalendarView.getCurMonth()!=month){
//        for (int i=0;i<list.size();i++){
//            int  month1= Integer.parseInt(list.get(i).getSchedulDate().substring(6,7));
//            int day1= Integer.parseInt(list.get(i).getSchedulDate().substring(8,10));
//            if (month==month1||day1==1){
//                articleAdapter.setContent(list.get(i).getSchedulName(),list.get(i).getSchedulElders());
//            }
//
//        }
//    }else {
//            for (int i=0;i<list.size();i++) {
//                int month1 = Integer.parseInt(list.get(i).getSchedulDate().substring(6, 7));
//                int day1 = Integer.parseInt(list.get(i).getSchedulDate().substring(8, 10));
//                if (month == month1 || mCalendarView.getCurDay() == day1) {
//                    articleAdapter.setContent(list.get(i).getSchedulName(), list.get(i).getSchedulElders());
//                }
//
//            }
//}

    }}