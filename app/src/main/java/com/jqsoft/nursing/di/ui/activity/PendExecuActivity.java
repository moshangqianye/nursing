package com.jqsoft.nursing.di.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.ExecuedProjectAdapter;
import com.jqsoft.nursing.adapter.PendExecuAdapter;
import com.jqsoft.nursing.adapter.nursing.HealthListAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.PendExecuBeanList;
import com.jqsoft.nursing.bean.PeopleBaseInfoBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.nursing.HealthListBean;
import com.jqsoft.nursing.bean.nursing.LoginResultNewBean;
import com.jqsoft.nursing.di.contract.ArcFaceListActivityContract;
import com.jqsoft.nursing.di.contract.PendExecuContract;
import com.jqsoft.nursing.di.module.ArcFaceListActivityModule;
import com.jqsoft.nursing.di.module.PendExecuActivityModule;
import com.jqsoft.nursing.di.presenter.ArcFaceListActivityPresenter;
import com.jqsoft.nursing.di.presenter.PendExecuPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.fragment.PendExeucedFragment;
import com.jqsoft.nursing.di.ui.onlinesignadapter.FragmentAdapters;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.util.CommentUtil;
import com.jqsoft.nursing.util.ToastUtil;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils3.util.PreferencesUtils;
import com.jqsoft.nursing.utils3.util.StringUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;


public class PendExecuActivity extends AbstractActivity implements ArcFaceListActivityContract.View{


    private PendExecuAdapter mPendExecuAdapter;
    private ExecuedProjectAdapter mExecuProjectAdapter;
    private FragmentAdapters fragmentAdapters;

    @Inject
    ArcFaceListActivityPresenter mPresenter;  // 健康列表Presenter
    private DaggerApplication application;

    private String sYear,sSignKey;
    private PeopleBaseInfoBean  mpeopleBasebean;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView((R.id.viewpager))
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;


    private TextView mTvHealthListFailure;  // 获取列表无数据或网络请求失败显示提示控件
    private HealthListAdapter mAdapter; // 健康列表适配器
//    private int currentPage = Constants.DEFAULT_INITIAL_PAGE; // 当前页
//    private int pageSize = Constants.DEFAULT_PAGE_SIZE;  // 每页数量
    private List<HealthListBean.RowsBean> mHealthListBeanList;   // 列表数据
    private boolean isRefresh = true;    // 是否是刷新还是加载更多
    private String elderName;   // 老人姓名（搜索出入的姓名）
    private Context context;
    private LoginResultNewBean userinfo;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_pend_execu;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        setToolBar(toolbar, Constants.EMPTY_STRING);
        initfragment();

        application = (DaggerApplication) PendExecuActivity.this.getApplication();
        userinfo= PreferencesUtils.getUserLoginInfo(PendExecuActivity.this);

    }

    public void initfragment() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //初始化TabLayout的title

        mTabLayout.addTab(mTabLayout.newTab().setText("全部"));
        mTabLayout.addTab(mTabLayout.newTab().setText("80-90岁老人"));
        mTabLayout.addTab(mTabLayout.newTab().setText("100岁以上老人"));

        List<String> titles = new ArrayList<>();
        titles.add("全部");
        titles.add("80-90岁老人");
        titles.add("100岁以上老人");

        //初始化ViewPager的数据集
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PendExeucedFragment());//
        fragments.add(new PendExeucedFragment());//
        fragments.add(new PendExeucedFragment());//

        //创建ViewPager的adapter
        mViewPager.setOffscreenPageLimit(Constants.VIEW_PAGER_OFF_SCREEN_NUMBER);
        fragmentAdapters = new FragmentAdapters(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(fragmentAdapters);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapters);

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initInject() {
        super.initInject();
        DaggerApplication.get(this)
                .getAppComponent()
                .addArcFaceListActivity(new ArcFaceListActivityModule(this))
                .inject(this);
    }




    public void showSignInfoOverview(List<PendExecuBeanList> list) {

    }







    public String getDeliveredStringByKey(String key) {
        if (StringUtils.isBlank(key)) {
            return Constants.EMPTY_STRING;
        } else {
            key = Util.trimString(key);
            Intent intent = getIntent();
            if (intent == null) {
                return Constants.EMPTY_STRING;
            } else {
                String result = intent.getStringExtra(key);
                return result;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void update(){
    }

    @Override
    public void onLoadHealthListSuccess(HttpResultNewBaseBean<String> bean) {
        if (bean != null) {

            Fragment fragment = fragmentAdapters.getFragments().get(0);
            ((PendExeucedFragment) fragment).setPendbean(bean);


        }

    }

    @Override
    public void onLoadMoreListSuccess(HttpResultNewBaseBean<String> bean) {
        if (bean != null) {
            Gson gson =new Gson();
            Type type = new TypeToken<HealthListBean>() {}.getType();
            HealthListBean healthListBean =  gson.fromJson(bean.getBackInfo().toString(),type);

            List<HealthListBean.RowsBean> data = healthListBean.getRows();
            if (!CommentUtil.isEmpty(data)) {  // 请求到数据
                Fragment fragment = fragmentAdapters.getFragments().get(0);
                ((PendExeucedFragment) fragment).setPendMorebean(data);


            } else {   // 请求到数据但是数据为null或者size为0
                if (isRefresh) {  // 刷新未得到数据

                } else {   // 上拉加载未得到数据
                    ToastUtil.show(this, "暂无更多数据了");
                }
            }
        }
    }

    @Override
    public void onLoadHealthListFail(String message, boolean isLoadMore) {

    }

    @Override
    public void onLoadHealthEndSuccess(HttpResultNewBaseBean<String> bean) {

    }

    @Override
    public void onLoadHealtEndFail(String message) {

    }

    /**
     * 加载请求老人健康列表
     */
    public void loadHealthList(String name, int currentPage,int pageSize) {

        String sToken= PreferencesUtils.getString(PendExecuActivity.this,"token");
        Map<String, String> params = new HashMap<>();
        params.put("Token", sToken);
        params.put("pageIndex", ""+currentPage);
        params.put("pageSize", ""+pageSize);
        if(name.length()==15 || name.length()==18){
            params.put("idCard", name);
            params.put("name", "");
        }else {
            params.put("idCard","" );
            params.put("name", name);
        }


        params.put("userid", userinfo.getGKey());
        JSONObject jsonObject = new JSONObject();
        jsonObject = new JSONObject(params);
        Log.v("okhttp",jsonObject.toString());
        mPresenter.getLoadHealthList(params, false);
    }

    /**
     * 获取健康列表请求参数
     *
     * @return 返回map
     */
//    private Map<String, String> getHealthListRequestMap() {
//        String userId = IdentityManager.getUserId(this);
//        String beginIndex = String.valueOf(currentPage * pageSize);
//        String endIndex = String.valueOf((currentPage + 1) * pageSize);
//        return ParametersFactory.getHealthListMap(this, userId, beginIndex, endIndex, elderName);
//    }


    public void onLoadMoreRequested(int currentPage,int pageSize) {

        String sToken= PreferencesUtils.getString(PendExecuActivity.this,"token");
        Map<String, String> params = new HashMap<>();
        params.put("Token", sToken);
        params.put("pageIndex", ""+currentPage);
        params.put("pageSize", ""+pageSize);
        params.put("name", "");
        params.put("idCard", "");
        params.put("userid", userinfo.getGKey());
        mPresenter.getLoadHealthList(params, true);
    }
}
