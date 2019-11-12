package com.jqsoft.nursing.di.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.nursing.HealthListAdapter;
import com.jqsoft.nursing.arcface.CardImageLiveFaceVerifyActivity;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.ArcDataBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.HealthListBean;
import com.jqsoft.nursing.bean.nursing.LoginResultNewBean;
import com.jqsoft.nursing.di.contract.ArcFaceListActivityContract;
import com.jqsoft.nursing.di.module.ArcFaceListActivityModule;
import com.jqsoft.nursing.di.module.nursing.HealthListFragmentModule;
import com.jqsoft.nursing.di.presenter.ArcFaceListActivityPresenter;
import com.jqsoft.nursing.di.presenter.HealthListPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di.view.IHealthListView;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.util.CommentUtil;
import com.jqsoft.nursing.util.SwitchUtil;
import com.jqsoft.nursing.util.ToastUtil;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils3.util.PreferencesUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**

 * 人脸识别列表 copy from老人健康列表页面
 */
public class ArcFaceListActivity extends AbstractActivity implements ArcFaceListActivityContract.View, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    @BindView(R.id.lay_content_nursing_list)
    View mLayContentNursingList;  // 有数据展示的布局
    @BindView(R.id.lay_specific_nursing_load_failure)
    View mNursingListFailureView; // 无数据时展示布局
    @BindView(R.id.nursing_title)
    TextView tv_title;   // 标题
    @BindView(R.id.ll_scan)
    LinearLayout ll_scan;  // 扫码布局
    @BindView(R.id.fl_nursing_title_menu)
    FrameLayout fl_nursing_title_menu;  // 搜索布局
    @BindView(R.id.iv_nursing_title_menu)
    ImageView iv_nursing_title_menu;  // 搜索控件
    @BindView(R.id.tv_sure)
    TextView tv_sure;   // 搜索确定
    @BindView(R.id.et_search)
    EditText et_search;  // 搜索输入框
    @BindView(R.id.ll_search)
    LinearLayout ll_search;  // 搜索布局

    private SwipeRefreshLayout mSrlHealthList; // 刷新控件
    private TextView mTvHealthListFailure;  // 获取列表无数据或网络请求失败显示提示控件
    private HealthListAdapter mAdapter; // 健康列表适配器
    private int currentPage = Constants.DEFAULT_INITIAL_PAGE; // 当前页
    private int pageSize = Constants.DEFAULT_PAGE_SIZE;  // 每页数量
    private List<HealthListBean.RowsBean> mHealthListBeanList;   // 列表数据
    private boolean isRefresh = true;    // 是否是刷新还是加载更多
    private String elderName;   // 老人姓名（搜索出入的姓名）
    private Context context;

    @Inject
    ArcFaceListActivityPresenter mPresenter;  // 健康列表Presenter



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_health_list;
    }

    @Override
    protected void initData() {
        mHealthListBeanList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        this.context= this;
        application = (DaggerApplication) ArcFaceListActivity.this.getApplication();
        fl_nursing_title_menu.setVisibility(View.VISIBLE);
        iv_nursing_title_menu.setImageResource(R.mipmap.search_white);
        iv_nursing_title_menu.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        tv_title.setText("采集信息列表");
        mSrlHealthList = (SwipeRefreshLayout) mLayContentNursingList;
        mTvHealthListFailure = (TextView) mNursingListFailureView.findViewById(R.id.tv_load_failure_hint);
        mTvHealthListFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRefresh = true;
                mAdapter.setEnableLoadMore(false);
                elderName = "";
                loadHealthList("");
            }
        });
        // 展示数据列表
        RecyclerView mRvHealthList = (RecyclerView) mLayContentNursingList.findViewById(R.id.recyclerview);
        mSrlHealthList.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        mSrlHealthList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新
                isRefresh = true;
                mAdapter.setEnableLoadMore(false);
                elderName="";
                loadHealthList("");
            }
        });
        mAdapter = new HealthListAdapter(mHealthListBeanList);
        mAdapter.setOnLoadMoreListener(this, mRvHealthList);
        mAdapter.setEnableLoadMore(false);
        mRvHealthList.setLayoutManager(new FullyLinearLayoutManager(this));
        mRvHealthList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvHealthList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                HealthListBean.RowsBean bean = (HealthListBean.RowsBean) baseQuickAdapter.getItem(i);
                if (bean != null) {
                    SwitchUtil.gotoVerifyNew1(context,"","",bean,100);

                }
            }
        });

        ll_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchUtil.gotoVerifyNew(context,"","",100);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_nursing_title_menu:
                // 搜索
                et_search.setText("");
                if (!ll_search.isShown()) {
                    ll_search.setVisibility(View.VISIBLE);
                } else {
                    ll_search.setVisibility(View.GONE);
                }

                break;
            case R.id.tv_sure:
                // 确定
                elderName = et_search.getText().toString().trim();
                if (!TextUtils.isEmpty(elderName)) {
                    loadHealthList(elderName);
                } else {
                    ToastUtil.show(this, "请输入姓名或者证件号码");
                }
                break;
            default:
                break;
        }
    }
    private DaggerApplication application;
    @Override
    public void onResume() {
        super.onResume();
        ll_search.setVisibility(View.GONE);
        int flag=application.getiFlag();
        if(flag==1){
            loadData();
            application.setiFlag(0);
        }


    }

    @Override
    protected void initInject() {
        super.initInject();
        DaggerApplication.get(this)
                .getAppComponent()
                .addArcFaceListActivity(new ArcFaceListActivityModule(this))
                .inject(this);

    }

    @Override
    protected void loadData() {
        loadHealthList("");
    }

    /**
     * 加载请求老人健康列表
     */
    private void loadHealthList(String name) {

        String sToken= PreferencesUtils.getString(ArcFaceListActivity.this,"token");
//        Map<String, String> map = ParametersFactory.getLoginMapFNew(this, getUsername(), getPassword(),sToken);
//        loginPresenter.main(map);

        currentPage = Constants.DEFAULT_INITIAL_PAGE;
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(false);
        }
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


        params.put("userid", "c9e80a74-f7ce-4803-b914-54eca951423c");
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
    private Map<String, String> getHealthListRequestMap() {
        String userId = IdentityManager.getUserId(this);
        String beginIndex = String.valueOf(currentPage * pageSize);
        String endIndex = String.valueOf((currentPage + 1) * pageSize);
        return ParametersFactory.getHealthListMap(this, userId, beginIndex, endIndex, elderName);
    }



    @Override
    public void onLoadHealthListSuccess(HttpResultNewBaseBean<String> bean) {
        ll_search.setVisibility(View.GONE);
        mSrlHealthList.setRefreshing(false);
        mAdapter.loadMoreComplete();
        if (bean != null) {
            Gson gson =new Gson();
            Type type = new TypeToken<HealthListBean>() {}.getType();
            HealthListBean healthListBean =  gson.fromJson(bean.getBackInfo().toString(),type);

            List<HealthListBean.RowsBean> data = healthListBean.getRows();
            if (!CommentUtil.isEmpty(data)) {  // 请求到数据
                mLayContentNursingList.setVisibility(View.VISIBLE);
                mNursingListFailureView.setVisibility(View.GONE);
                int size = data.size();  // 返回数据的条数
                if (size < pageSize) {  // 设置不可上拉加载
                    mAdapter.setEnableLoadMore(false);
                } else {  // 可以上拉加载
                    mAdapter.setEnableLoadMore(true);
                    currentPage++;
                }
                if (isRefresh) { // 刷新需要清空集合
                    mHealthListBeanList.clear();
                }
                mHealthListBeanList.addAll(data);
                mAdapter.notifyDataSetChanged();

            } else {   // 请求到数据但是数据为null或者size为0
                if (isRefresh) {  // 刷新未得到数据
                    mLayContentNursingList.setVisibility(View.GONE);
                    mNursingListFailureView.setVisibility(View.VISIBLE);
                    mTvHealthListFailure.setText(getResources().getString(R.string.hint_no_nursing_list_info_please_click_to_reload));
                } else {   // 上拉加载未得到数据
                    ToastUtil.show(this, "暂无更多数据了");
                }
            }
        }

//
//        ArcDataBean arcDataBean=new ArcDataBean();
//        SharedPreferences userSettings = getSharedPreferences("setting", 0);
//        SharedPreferences.Editor editor = userSettings.edit();
//        if (arcDataBean == null) {
//            arcDataBean.setNoBrushAgeRange("0-18,65-2000");
//            arcDataBean.setAction("3");
//            arcDataBean.setRange("5");
//            arcDataBean.setFrames("50");
//            arcDataBean.setSimilarity("0-8:0.5,8-50:0.6,50-2000:0.4");
//            arcDataBean.setScreenOf("0.1");
//            arcDataBean.setManCardVerify("0-8:0.4,8-50:0.7,50-2000:0.4");
//        } else if (TextUtils.isEmpty(arcDataBean.getNoBrushAgeRange())) {
//            arcDataBean.setNoBrushAgeRange("0-18,65-2000");
//        }
//        else if (TextUtils.isEmpty(arcDataBean.getAction())) {
//            arcDataBean.setAction("3");
//        } else if (TextUtils.isEmpty(arcDataBean.getRange())) {
//            arcDataBean.setRange("5");
//        } else if (TextUtils.isEmpty(arcDataBean.getFrames())) {
//            arcDataBean.setFrames("50");
//        } else if (TextUtils.isEmpty(arcDataBean.getSimilarity())) {
//            arcDataBean.setSimilarity("0-8:0.5,8-50:0.6,50-2000:0.4");
//        } else if (TextUtils.isEmpty(arcDataBean.getScreenOf())) {
//            arcDataBean.setScreenOf("0.1");
//        } else if (TextUtils.isEmpty(arcDataBean.getManCardVerify())) {
//            arcDataBean.setManCardVerify("0-8:0.4,8-50:0.6,50-2000:0.4");
//        }
//        try {
//            editor.putString("LkzWsXxlb", arcDataBean.getLkzWsXxlb());
//            editor.putString("NoBrushAgeRange", arcDataBean.getNoBrushAgeRange());
//            editor.putInt("Action", Integer.parseInt(arcDataBean.getAction()));
//            editor.putInt("Range", Integer.parseInt(arcDataBean.getRange()));
//            editor.putInt("Frames", Integer.parseInt(arcDataBean.getFrames()));
//            editor.putString("Similarity", arcDataBean.getSimilarity());
//            editor.putString("ScreenOf", arcDataBean.getScreenOf());
//            editor.putString("ManCardVerify", arcDataBean.getManCardVerify());
//
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//
//        if (arcDataBean != null) {
//            if (!TextUtils.isEmpty(arcDataBean.getsAPPShuaKa())) {
//                int shuake = Integer.parseInt(arcDataBean.getsAPPShuaKa());
////                BphsConstants.IdVISIABLE = shuake;   // 1:是，：否，默认
////                BphsConstants.saveCardID = shuake;  //< 右上角拍照是否接着刷卡  1表示刷卡>
//            }
//            if (!TextUtils.isEmpty(arcDataBean.getsAPPTanChuangShowButton())) {
////                UserLoginInfo.setChecKStyle(arcDataBean.getsAPPTanChuangShowButton()); //弹出框是否带有人脸 、身份证刷卡
//            }
//            if (!TextUtils.isEmpty(arcDataBean.getsAPPDiaoYueLIS())) {
//                editor.putString("appdiaoyuelis", arcDataBean.getsAPPDiaoYueLIS());
//            }
//            if (!TextUtils.isEmpty(arcDataBean.getsAPPDiaoYueBChao())) {
//                editor.putString("appdiaoyuebchao", arcDataBean.getsAPPDiaoYueBChao());
//            }
//            if (!TextUtils.isEmpty(arcDataBean.getiAPPFaceNoPassCount())) {
//                editor.putString("iAPPFaceNoPassCount", arcDataBean.getiAPPFaceNoPassCount());
//            }
//            if (!TextUtils.isEmpty(arcDataBean.getsAPPFaceFaZhi())) {
//                editor.putString("sAPPFaceFaZhi", arcDataBean.getsAPPFaceFaZhi());
//            }
//            if (!TextUtils.isEmpty(arcDataBean.getsCheckFacePhoto())) {//是否开启申请人工审核照片功能
//                editor.putString("sCheckFacePhoto", arcDataBean.getsCheckFacePhoto());
//            } else {
//                editor.putString("sCheckFacePhoto", "0");
//            }
//            if (!TextUtils.isEmpty(arcDataBean.getsDeletePhoto())) {///是否开启申请删除底片功能	1:开启 ,0:不开启，默认0
//                editor.putString("sDeletePhoto", arcDataBean.getsDeletePhoto());
//            } else {
//                editor.putString("sDeletePhoto", "0");
//            }
//
//        }
//        editor.apply();
    }




    /**
     * 获取列表失败回调
     *
     * @param message    失败提示信息
     * @param isLoadMore 是否上拉加载更多
     */
    @Override
    public void onLoadHealthListFail(String message, boolean isLoadMore) {
        ll_search.setVisibility(View.GONE);
        mSrlHealthList.setRefreshing(false);
        mAdapter.loadMoreComplete();
        if (isLoadMore) {  // 加载更多获取数据失败
            mLayContentNursingList.setVisibility(View.VISIBLE);
            mNursingListFailureView.setVisibility(View.GONE);
            ToastUtil.show(this, message);
        } else { // 刷新获取数据失败
            mLayContentNursingList.setVisibility(View.GONE);
            mNursingListFailureView.setVisibility(View.VISIBLE);
            mTvHealthListFailure.setText(getResources().getString(R.string.hint_load_nursing_list_info_error_please_click_to_reload));
        }

    }

    /**
     * 加载更多请求
     */
    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;

        currentPage=currentPage+1;
        String sToken= PreferencesUtils.getString(ArcFaceListActivity.this,"token");
        Map<String, String> params = new HashMap<>();
        params.put("Token", sToken);
        params.put("pageIndex", ""+currentPage);
        params.put("pageSize", ""+pageSize);
        params.put("name", "");
        params.put("idCard", "");
        params.put("userid", "c9e80a74-f7ce-4803-b914-54eca951423c");

//        mPresenter.getLoadHealthList(params, false);

//        Map<String, String> map = getHealthListRequestMap();
        mPresenter.getLoadHealthList(params, true);
    }


}
