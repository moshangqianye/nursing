package com.jqsoft.nursing.di.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.nursing.CaseListAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.CaseListBean;
import com.jqsoft.nursing.di.module.nursing.CaseListFragmentModule;
import com.jqsoft.nursing.di.presenter.CaseListPresenter;
import com.jqsoft.nursing.di.ui.activity.PreviewPicActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di.view.ICaseListView;
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
 * @date 2019/1/17
 * 老人病例列表页面
 */
public class CaseListFragment extends AbstractFragment implements View.OnClickListener, ICaseListView, BaseQuickAdapter.RequestLoadMoreListener {

    public static final int TYPE_ZERO = 0; // 从首页列表进入
    public static final int TYPE_ONE = 1; // 从老人列表中病例信息进入

    @BindView(R.id.view_title)
    View mViewTitle;   // 标题布局
    @BindView(R.id.lay_content_nursing_list)
    View mLayContentNursingList;  // 有数据展示的布局
    @BindView(R.id.lay_specific_nursing_load_failure)
    LinearLayout mNursingListFailureView; // 无数据时展示布局

    private SwipeRefreshLayout mSrlNursingList;
    private TextView mTvNursingListFailureView;

    private int currentPage = Constants.DEFAULT_INITIAL_PAGE; // 当前页
    private int pageSize = Constants.DEFAULT_PAGE_SIZE;  // 每页数量
    private List<CaseListBean> mCaseList;   // 列表数据
    private boolean isRefresh = true;    // 是否是刷新还是加载更多
    private CaseListAdapter mAdapter; // 健康列表适配器

    private String mElderID;   // 老人Id
    private int type = -1;  // 进入的入口

    @Inject
    CaseListPresenter mPresenter;  // 病例记录Presenter

    /**
     * @param type    进入入口 0表示从首页列表进入  1表示从老人列表中病例信息进入
     * @param elderID
     * @return
     */
    public static CaseListFragment newInstance(int type, String elderID) {

        Bundle args = new Bundle();
        CaseListFragment fragment = new CaseListFragment();
        args.putInt("TYPE", type);
        args.putString("ElderID", elderID);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 传值
     * @param elderID
     */
    public void setData(String elderID){
        mElderID = elderID;
        loadCaseList();
    }

    @Override
    protected void initInject() {
        DaggerApplication.get(getActivity())
                .getAppComponent()
                .addCaseListFragment(new CaseListFragmentModule(this))
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_case_list;
    }

    @Override
    protected void initData() {
        mCaseList = new ArrayList<>();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mElderID = arguments.getString("ElderID");
            type = arguments.getInt("TYPE");
        }
    }

    @Override
    protected void initView() {
        if (type == TYPE_ONE){ // 设置请求错误或无数据展示的布局提示信息居中，头部布局隐藏
            mNursingListFailureView.setGravity(Gravity.CENTER);
            mViewTitle.setVisibility(View.VISIBLE);
        }else if (type == TYPE_ZERO){
            mViewTitle.setVisibility(View.GONE);
        }
        TextView tv_title = (TextView) mRootView.findViewById(R.id.nursing_title);
        tv_title.setText(R.string.case_record);
        mRootView.findViewById(R.id.tv_scan).setVisibility(View.GONE);
        View ll_back = mRootView.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        ll_back.setVisibility(View.VISIBLE);

        mSrlNursingList = (SwipeRefreshLayout) mLayContentNursingList;
        mTvNursingListFailureView = (TextView) mNursingListFailureView.findViewById(R.id.tv_load_failure_hint);
        mTvNursingListFailureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRefresh = true;
                mAdapter.setEnableLoadMore(false);
                loadCaseList();
            }
        });
        RecyclerView mRvNursingList = (RecyclerView) mLayContentNursingList.findViewById(R.id.recyclerview);
        mSrlNursingList.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        mSrlNursingList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新
                isRefresh = true;
                mAdapter.setEnableLoadMore(false);
                loadCaseList();
            }
        });
        mAdapter = new CaseListAdapter(mCaseList);
        mAdapter.setOnLoadMoreListener(this, mRvNursingList);
        mAdapter.setEnableLoadMore(false);
        mRvNursingList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mRvNursingList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRvNursingList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                CaseListBean bean = (CaseListBean) baseQuickAdapter.getItem(i);
                if (bean != null) {
                    if (!TextUtils.isEmpty(bean.getPic())) {
                        PreviewPicActivity.start(getActivity(), bean.getPic());
                    } else {
                        ToastUtil.show(getActivity(), "图片地址不正确");
                    }
                }
            }
        });
    }

    @Override
    protected void loadData() {
        loadCaseList();
    }

    // 获取病例列表
    private void loadCaseList() {
        currentPage = Constants.DEFAULT_INITIAL_PAGE;
        mAdapter.setEnableLoadMore(false);
        Map<String, String> map = getCaseListRequestMap();
        mPresenter.getCaseList(map, false);
    }

    /**
     * 获取病例记录请求参数
     *
     * @return 返回map
     */
    private Map<String, String> getCaseListRequestMap() {
        String userId = IdentityManager.getUserId(getActivity());
        String beginIndex = String.valueOf(currentPage * pageSize);
        String endIndex = String.valueOf((currentPage + 1) * pageSize);
        return ParametersFactory.getCaseListMap(getActivity(), mElderID, userId, beginIndex, endIndex);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadCaseListSuccess(HttpResultNurseBaseBean<List<CaseListBean>> bean) {
        mSrlNursingList.setRefreshing(false);
        mAdapter.loadMoreComplete();
        if (bean != null) {
            List<CaseListBean> data = bean.getData();
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
                    mCaseList.clear();
                }
                mCaseList.addAll(data);
                mAdapter.notifyDataSetChanged();

            } else {   // 请求到数据但是数据为null或者size为0
                if (isRefresh) {  // 刷新未得到数据
                    mLayContentNursingList.setVisibility(View.GONE);
                    mNursingListFailureView.setVisibility(View.VISIBLE);
                    mTvNursingListFailureView.setText(getResources().getString(R.string.hint_no_case_list_info_please_click_to_reload));
                } else {   // 上拉加载未得到数据
                    ToastUtil.show(getActivity(), "暂无更多数据了");
                }
            }
        }
    }

    /**
     * 获取列表失败回调
     *
     * @param message    失败提示的信息
     * @param isLoadMore 是否刷新
     */
    @Override
    public void onLoadCaseListFail(String message, boolean isLoadMore) {
        mSrlNursingList.setRefreshing(false);
        mAdapter.loadMoreComplete();
        if (isLoadMore) {  // 加载更多获取数据失败
            mLayContentNursingList.setVisibility(View.VISIBLE);
            mNursingListFailureView.setVisibility(View.GONE);
            ToastUtil.show(getActivity(), message);
        } else { // 刷新获取数据失败
            mLayContentNursingList.setVisibility(View.GONE);
            mNursingListFailureView.setVisibility(View.VISIBLE);
            mTvNursingListFailureView.setText(getResources().getString(R.string.hint_load_case_list_info_error_please_click_to_reload));
        }
    }

    /**
     * 加载更多请求
     */
    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        Map<String, String> map = getCaseListRequestMap();
        mPresenter.getCaseList(map, true);
    }
}
