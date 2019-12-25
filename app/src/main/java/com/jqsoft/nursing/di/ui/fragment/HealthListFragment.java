package com.jqsoft.nursing.di.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.nursing.HealthListAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.HealthListBean;
import com.jqsoft.nursing.di.module.nursing.HealthListFragmentModule;
import com.jqsoft.nursing.di.presenter.HealthListPresenter;
import com.jqsoft.nursing.di.ui.activity.OldPersonInfoActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di.view.IHealthListView;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.util.CommentUtil;
import com.jqsoft.nursing.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author yedong
 * @date 2019/1/16
 * 老人健康列表页面
 */
public class HealthListFragment extends AbstractFragment implements IHealthListView, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

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
    private List<HealthListBean> mHealthListBeanList;   // 列表数据
    private boolean isRefresh = true;    // 是否是刷新还是加载更多
    private String elderName;   // 老人姓名（搜索出入的姓名）

    @Inject
    HealthListPresenter mPresenter;  // 健康列表Presenter

    public static HealthListFragment newInstance() {

        Bundle args = new Bundle();

        HealthListFragment fragment = new HealthListFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        ll_scan.setVisibility(View.GONE);
        fl_nursing_title_menu.setVisibility(View.VISIBLE);
        iv_nursing_title_menu.setImageResource(R.mipmap.search_white);
        iv_nursing_title_menu.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        tv_title.setText("老人列表");
        mSrlHealthList = (SwipeRefreshLayout) mLayContentNursingList;
        mTvHealthListFailure = (TextView) mNursingListFailureView.findViewById(R.id.tv_load_failure_hint);
        mTvHealthListFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRefresh = true;
                mAdapter.setEnableLoadMore(false);
                elderName = "";
                loadHealthList();
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
                loadHealthList();
            }
        });
//        mAdapter = new HealthListAdapter(getActivity(),mHealthListBeanList);
        mAdapter.setOnLoadMoreListener(this, mRvHealthList);
        mAdapter.setEnableLoadMore(false);
        mRvHealthList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mRvHealthList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRvHealthList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                HealthListBean bean = (HealthListBean) baseQuickAdapter.getItem(i);
                if (bean != null) {
//                    OldPersonInfoActivity.start(getActivity(), bean.getElderName(), bean.getElderInfoID());
                }
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
                    loadHealthList();
                } else {
                    ToastUtil.show(getActivity(), "请输入姓名");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ll_search.setVisibility(View.GONE);
    }

    @Override
    protected void initInject() {
        super.initInject();
        DaggerApplication.get(getActivity())
                .getAppComponent()
                .addHealthListFragment(new HealthListFragmentModule(this))
                .inject(this);

    }

    @Override
    protected void loadData() {
        loadHealthList();
    }

    /**
     * 加载请求老人健康列表
     */
    private void loadHealthList() {

        currentPage = Constants.DEFAULT_INITIAL_PAGE;
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(false);
        }
        Map<String, String> map = getHealthListRequestMap();
        mPresenter.getLoadHealthList(map, false);
    }

    /**
     * 获取健康列表请求参数
     *
     * @return 返回map
     */
    private Map<String, String> getHealthListRequestMap() {
        String userId = IdentityManager.getUserId(getActivity());
        String beginIndex = String.valueOf(currentPage * pageSize);
        String endIndex = String.valueOf((currentPage + 1) * pageSize);
        return ParametersFactory.getHealthListMap(getActivity(), userId, beginIndex, endIndex, elderName);
    }

    /**
     * 获取列表成功回调
     */
    @Override
    public void onLoadHealthListSuccess(HttpResultNurseBaseBean<List<HealthListBean>> bean) {
        ll_search.setVisibility(View.GONE);
        mSrlHealthList.setRefreshing(false);
        mAdapter.loadMoreComplete();
        if (bean != null) {
            List<HealthListBean> data = bean.getData();
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
                    ToastUtil.show(getActivity(), "暂无更多数据了");
                }
            }
        }
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
            ToastUtil.show(getActivity(), message);
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
        Map<String, String> map = getHealthListRequestMap();
        mPresenter.getLoadHealthList(map, true);
    }

}
