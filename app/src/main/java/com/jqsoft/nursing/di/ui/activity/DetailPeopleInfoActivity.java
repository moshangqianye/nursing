//package com.jqsoft.grassroots_civil_administration_platform.di.ui.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.RelativeLayout;
//
//import com.jqsoft.nursing.R;
//import com.jqsoft.nursing.base.Constants;
//import com.jqsoft.nursing.base.ParametersFactory;
//import com.jqsoft.nursing.bean.DoctorTeamInfo;
//import com.jqsoft.nursing.bean.PeopleBaseInfoBean;
//import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
//import com.jqsoft.nursing.di.contract.PeopleBaseFragmentContract;
//import com.jqsoft.nursing.di.module.PeopleBaseInfoFragmentModule;
//import com.jqsoft.nursing.di.presenter.PeopleBaseFragmentPresenter;
//import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
//
//import com.jqsoft.nursing.di.ui.fragment.PeopleSignFragment;
//import com.jqsoft.nursing.di_app.DaggerApplication;
//import com.jqsoft.nursing.util.Util;
//import com.jqsoft.nursing.utils3.util.StringUtils;
//import com.jqsoft.nursing.view.PagePointView;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.inject.Inject;
//
//import butterknife.BindView;
//import okhttp3.RequestBody;
//
//public class DetailPeopleInfoActivity extends AbstractActivity implements
//        PeopleBaseFragmentContract.View{
//
//
//    private RelativeLayout rl_execu;
//    private Context mContext = this;
//    private ArrayList<Fragment> mFragments = new ArrayList<>();
//
//
//    private ViewPager viewPager;
//    private PagePointView point;
//
//    private List<Integer> colors;
//    private ArrayList<Fragment> fragmentList;
//
//    private String sYear,sSignKey,sPersonModel,sPersonId;
//    private Bundle bundle = new Bundle();
//    private Bundle bundleSign = new Bundle();
//    private   PeopleBaseInfoBean mpeopleBasebean,mpeopleBasebean1;
//    private   Fragment secondFragment;
//    private  Fragment thirdFragment;
//
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//
//    @BindView(R.id.rl_accessfile)
//    RelativeLayout rl_accessfile;
//
//    @BindView(R.id.rl_updateinfo)
//    RelativeLayout rl_updateinfo;
//
//    @Inject
//    PeopleBaseFragmentPresenter mPresenter;
//
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_detail_people_info;
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//    @Override
//    protected void initView() {
//        setToolBar(toolbar, Constants.EMPTY_STRING);
//
//        rl_execu =(RelativeLayout)findViewById(R.id.rl_execu);
//
//        rl_execu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mpeopleBasebean==null || TextUtils.isEmpty(sYear) || TextUtils.isEmpty(sSignKey)){
//
//                }else{
//                    Intent intent = new Intent(DetailPeopleInfoActivity.this,PendExecuActivity.class);
//                    intent.putExtra("mpeopleBasebean", (Serializable)mpeopleBasebean);
//                    intent.putExtra("sYear", sYear);
//                    intent.putExtra("sSignKey", sSignKey);
//                    startActivity(intent);
//                }
//
//
//            }
//        });
//
//        rl_accessfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(TextUtils.isEmpty( mpeopleBasebean.getPersonId())){
//
//                }else{
//                    Intent intent = new Intent(DetailPeopleInfoActivity.this,AccessFileActivity.class);
//                    intent.putExtra("personId", mpeopleBasebean.getPersonId());
//                    intent.putExtra("flag","1");
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        rl_updateinfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(TextUtils.isEmpty( mpeopleBasebean.getCardNo()) || TextUtils.isEmpty( mpeopleBasebean.getYear()) ||TextUtils.isEmpty(mpeopleBasebean.getPersonId())){
//
//                }else{
//                 /*   Intent intent = new Intent(DetailPeopleInfoActivity.this,UpdatePeopleInfoActivity.class);
//                    intent.putExtra("cardNo", mpeopleBasebean.getCardNo());
//                    intent.putExtra("year", mpeopleBasebean.getYear());
//                    intent.putExtra("personId", mpeopleBasebean.getPersonId());
//                    startActivity(intent);*/
//                }
//
//            }
//        });
//
//
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        point = (PagePointView) findViewById(R.id.point);
//
//        sYear=getDeliveredStringByKey("year");
//        sSignKey=getDeliveredStringByKey("signKey");
//        sPersonModel=getDeliveredStringByKey("personModel");
//        sPersonId=getDeliveredStringByKey("personId");
//
//        InitViewPager();
//        point.setViewPager(viewPager);
//        setToolBar(toolbar, Constants.EMPTY_STRING);
//    }
//
//    @Override
//    protected void loadData() {
//       /* sYear=getDeliveredStringByKey("year");
//        sSignKey=getDeliveredStringByKey("signKey");
//        sPersonModel=getDeliveredStringByKey("personModel");
//        sPersonId=getDeliveredStringByKey("personId");
//
//        Map<String, String> map = ParametersFactory.getPeopleBaseInfo(sYear,sSignKey,sPersonModel,sPersonId);
//        RequestBody body = Util.getBodyFromMap(map);
//
//        mPresenter.main(body, false);*/
//    }
//
//
//
//    /*
//   * 初始化ViewPager
//   */
//    public void InitViewPager(){
//
//
//
//
//        fragmentList = new ArrayList<Fragment>();
//        //   Fragment btFragment= new ButtonFragment();
//        secondFragment =new PeopleBaseFragment();
//        thirdFragment = new PeopleSignFragment();
//       /* Bundle bundle = new Bundle();
//        bundle.putBundle();*/
//
//        //     fragmentList.add(btFragment);
//
//     //   dayWorkFragment = new DayWorkFragment();
//      /*  bundle.clear();
//        bundle.putString("personId", sPersonId);
//        secondFragment.setArguments(bundle);*/
//     //   mFragments.add(dayWorkFragment);
//
//        fragmentList.add(secondFragment);
//
//
//        bundleSign.clear();
//        bundleSign.putString("year", sYear);
//        bundleSign.putString("signKey", sSignKey);
//        thirdFragment.setArguments(bundleSign);
//
//        fragmentList.add(thirdFragment);
//
//
//        //给ViewPager设置适配器
//        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
//        viewPager.setCurrentItem(0);//设置当前显示标签页为第一页
//        //  viewPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
//    }
//
//    private SetpeopleBaseListter setPeopleBaseListter;
//
//    public interface SetpeopleBaseListter {
//        void setResultdata(PeopleBaseInfoBean itemlist);
//    }
//
//
//
//    @Override
//    public void onLoadListSuccess(HttpResultBaseBean<PeopleBaseInfoBean> bean) {
//        Util.hideGifProgressDialog(this);
//     //   Util.showToast(this, "获取成功");
//        if(bean!=null) {
//            mpeopleBasebean = bean.getData();
//            secondFragment = DetailPeopleInfoActivity.this.getSupportFragmentManager().getFragments().get(0);
//            ((PeopleBaseFragment) secondFragment).setPeopleBasebean(mpeopleBasebean);
//
//            thirdFragment = DetailPeopleInfoActivity.this.getSupportFragmentManager().getFragments().get(1);
//            ((PeopleSignFragment) thirdFragment).setPeopleSignbean(mpeopleBasebean);
//
//          //  setPeopleBaseListter.setResultdata(mpeopleBasebean);
//
//
//        }
//    }
//
//    @Override
//    public void onLoadMoreListSuccess(HttpResultBaseBean<PeopleBaseInfoBean> bean) {
//
//    }
//
//    @Override
//    public void onLoadListFailure(String message, boolean isLoadMore) {
//
//    }
//
//    @Override
//    public void onLoadDoctorListSuccess(HttpResultBaseBean<List<DoctorTeamInfo>> bean) {
//
//    }
//
//    @Override
//    public void onLoadDoctorListFailure(String message) {
//
//    }
//
//
//    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
//        ArrayList<Fragment> list;
//        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
//            super(fm);
//            this.list = list;
//
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Fragment getItem(int arg0) {
//            return list.get(arg0);
//        }
//
//
//
//
//    }
//
//    public String getDeliveredStringByKey(String key) {
//        if (StringUtils.isBlank(key)) {
//            return Constants.EMPTY_STRING;
//        } else {
//            key = Util.trimString(key);
//            Intent intent = getIntent();
//            if (intent == null) {
//                return Constants.EMPTY_STRING;
//            } else {
//                String result = intent.getStringExtra(key);
//                return result;
//            }
//        }
//    }
//
//    @Override
//    protected void initInject() {
//        DaggerApplication.get(this)
//                .getAppComponent()
//                .addPeopleBaseInfoFragment(new PeopleBaseInfoFragmentModule(this))
//                .inject(this);
//    }
//
//
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
////        warnExit();
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        sYear=getDeliveredStringByKey("year");
//        sSignKey=getDeliveredStringByKey("signKey");
//        sPersonModel=getDeliveredStringByKey("personModel");
//        sPersonId=getDeliveredStringByKey("personId");
//
//        Map<String, String> map = ParametersFactory.getPeopleBaseInfo(this, sYear,sSignKey,sPersonModel,sPersonId);
//        RequestBody body = Util.getBodyFromMap1(map);
//
//        mPresenter.main(body, false);
//    }
//}
