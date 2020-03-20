package com.jqsoft.nursing.di.ui.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.PendExecuAdapter;
import com.jqsoft.nursing.adapter.nursing.HealthListAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.PendExecuBeanList;
import com.jqsoft.nursing.bean.PeopleBaseInfoBean;
import com.jqsoft.nursing.bean.nursing.HealthListBean;
import com.jqsoft.nursing.di.ui.activity.ArcFaceListActivity;
import com.jqsoft.nursing.di.ui.activity.PendExecuActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleClickListener;
import com.jqsoft.nursing.util.SwitchUtil;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.jqsoft.nursing.utils3.util.PreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class  PendExeucedFragment extends AbstractFragment implements BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.lay_content_nursing_list)
    View mLayContentNursingList;  // 有数据展示的布局

    private HealthListAdapter mPendExecuAdapter;

    @BindView(R.id.lay_pend_load_failure)
    View failureView;
    private boolean isRefresh = true;    // 是否是刷新还是加载更多
    TextView tvFailureView;
    private HealthListAdapter mAdapter; // 健康列表适配器
    private List<HealthListBean.RowsBean> mHealthListBeanList;   // 列表数据
    private SwipeRefreshLayout mSrlHealthList; // 刷新控件
    @Override
    protected void loadData() {

    }

    private int currentPage = Constants.DEFAULT_INITIAL_PAGE; // 当前页
    private int pageSize = Constants.DEFAULT_PAGE_SIZE;  // 每页数量

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pendexecu;
    }
    @Override
    protected void initView() {

        tvFailureView=(TextView)failureView.findViewById(R.id.tv_load_failure_hint);
        tvFailureView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);

            }
        });

        mHealthListBeanList = new ArrayList<>();
        // 展示数据列表
        RecyclerView mRvHealthList = (RecyclerView) mLayContentNursingList.findViewById(R.id.recyclerview);

        mAdapter = new HealthListAdapter(getActivity(),mHealthListBeanList);
        mAdapter.setOnLoadMoreListener(this, mRvHealthList);
        mAdapter.setEnableLoadMore(false);
        mRvHealthList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        mRvHealthList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRvHealthList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                HealthListBean.RowsBean bean = (HealthListBean.RowsBean) baseQuickAdapter.getItem(i);
                if (bean != null) {
                    SwitchUtil.gotoVerifyNew1(getActivity(),"","",bean,100);

                }
            }
        });


        mSrlHealthList = (SwipeRefreshLayout) mLayContentNursingList;
        mSrlHealthList.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        mSrlHealthList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新
                isRefresh = true;
                mAdapter.setEnableLoadMore(false);
                currentPage = Constants.DEFAULT_INITIAL_PAGE;
                PendExecuActivity parentActivity = (PendExecuActivity) getActivity();
                parentActivity.loadHealthList("",currentPage,pageSize);

            }
        });

        PendExecuActivity parentActivity = (PendExecuActivity) getActivity();
        currentPage = Constants.DEFAULT_INITIAL_PAGE;
        parentActivity.loadHealthList("",currentPage,pageSize);
        mSrlHealthList.setRefreshing(false);
        mAdapter.loadMoreComplete();
    }

    @Override
    protected void initData() {

    }


    public void setPendbean(List<HealthListBean.RowsBean> data) {


        int listSize = getListSizeFromResult(data);

        mAdapter.setNewData(data);

        mSrlHealthList.setRefreshing(false);
        setLoadMoreStatus(pageSize, listSize, true);
        isRefresh = false;

        showRecyclerViewOrFailureView(true, ListUtils.isEmpty(mAdapter.getData()));
    }


    public void setPendMorebean(List<HealthListBean.RowsBean> data) {
        int listSize = getListSizeFromResult(data);

        mAdapter.addData(data);

        mSrlHealthList.setEnabled(true);
        mSrlHealthList.setRefreshing(false);
        setLoadMoreStatus(this.pageSize, listSize, true);
    }

    private void showRecyclerViewOrFailureView(boolean success, boolean isListEmpty) {
        if (success) {
            if (isListEmpty) {
                //   srl.setVisibility(View.GONE);
                failureView.setVisibility(View.VISIBLE);
                tvFailureView.setText(getListEmptyHint());
            } else {
                //   srl.setVisibility(View.VISIBLE);
                failureView.setVisibility(View.GONE);
            }
        } else {
            //     srl.setVisibility(View.GONE);
            failureView.setVisibility(View.VISIBLE);
            tvFailureView.setText(getFailureHint());

        }
    }
    public void setLoadMoreStatus(int pageSize, int listSize, boolean isSuccessful) {
        if (isSuccessful) {
            if (listSize < pageSize) {
//                mAdapter.setEnableLoadMore(false);
                mAdapter.loadMoreEnd(true);
            } else {
                mAdapter.setEnableLoadMore(true);
                mAdapter.loadMoreComplete();
            }
        } else {
            mAdapter.setEnableLoadMore(true);
            mAdapter.loadMoreFail();
        }
    }

    public int getListSizeFromResult(List<HealthListBean.RowsBean> beanList) {
        if (beanList != null) {
            if (beanList != null) {
                int size = beanList.size();
                return size;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }


    private String getListEmptyHint(){
        return getResources().getString(R.string.hint_list_empty_pend_reserva);
    }

    private String getFailureHint(){
        return getResources().getString(R.string.hint_load_failure);
    }




    @Override
    protected void initInject() {

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onLoadMoreRequested() {
        ++currentPage;
        mSrlHealthList.setEnabled(false);
        PendExecuActivity parentActivity = (PendExecuActivity) getActivity();
        parentActivity.onLoadMoreRequested(currentPage,pageSize);


    }
}
