package com.jqsoft.nursing.di.ui.fragment.nursing;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.nursing.NursingTaskAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.nursing.NursingTaskBean;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils3.util.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 当日护理老人详细信息fragment
 * Created by Administrator on 2018-04-03.
 */

public class NursingDetailTasksFragment extends AbstractFragment {


    @BindView(R.id.lay_content_nursing_list)
    View layContentNursingList;
    @BindView(R.id.lay_specific_nursing_load_failure)
    View nursingListFailureView;
    TextView tvNursingListFailureView;


    SwipeRefreshLayout srlNursingList;
    RecyclerView rvNursingList;


    TextView tvFailureView;


    private boolean isRefresh = false;

    private int type;

    private NursingTaskAdapter nursingTaskAdapter;
    List<NursingTaskBean> nursingList;


    private String keywordString;

//    private int currentPage = Constants.DEFAULT_INITIAL_PAGE;
//    private int pageSize = Constants.DEFAULT_PAGE_SIZE;
//    private int pageSize = 1;

    public NursingDetailTasksFragment() {
        super();
//        LogUtil.i("NursingDetailTasksFragment constructor");
    }



    private void checkFragmentLoadStatus(){
        if (NursingDetailActivity.FRAGMENT_COUNT<=0){
//            LogUtil.i("NursingDetailTasksFragment checkFragmentLoadStatus FRAGMENT_COUNT<=0");
            loadNursingList();
        }
    }

    private void decrementFragmentCount(){
        synchronized (NursingDetailActivity.class){
            --NursingDetailActivity.FRAGMENT_COUNT;
//            LogUtil.i("NursingDetailTasksFragment decrementFragmentCount: "+NursingDetailActivity.FRAGMENT_COUNT);
            checkFragmentLoadStatus();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nursing_detail_task_layout;
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    protected void initData() {
//        LogUtil.i("NursingDetailTasksFragment initData");
        nursingList = new ArrayList<>();

    }

    @Override
    protected void initView() {
        srlNursingList = (SwipeRefreshLayout) layContentNursingList;
//        LogUtil.i("NursingDetailTasksFragment initView srlNursingList:"+srlNursingList);
        rvNursingList = (RecyclerView) layContentNursingList.findViewById(R.id.recyclerview);

        srlNursingList.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        srlNursingList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                nursingTaskAdapter.setEnableLoadMore(false);
                loadNursingList();

            }
        });

        tvNursingListFailureView = (TextView) nursingListFailureView.findViewById(R.id.tv_load_failure_hint);
        RxView.clicks(tvNursingListFailureView)
                .throttleFirst(Constants.RXBINDING_THROTTLE, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        nursingTaskAdapter.setEnableLoadMore(false);
                        loadNursingList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        nursingTaskAdapter = new NursingTaskAdapter(nursingList, this);
     //   nursingTaskAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        nursingTaskAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        }, rvNursingList);
        nursingTaskAdapter.setEnableLoadMore(false);
//        rvNursingList.setNestedScrollingEnabled(false);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        rvNursingList.setLayoutManager(new FullyLinearLayoutManagerSmoothScroll(getActivity()));
//        NoScrollFullyLinearLayoutManager policyManager = new NoScrollFullyLinearLayoutManager(getActivity());
//        policyManager.setScrollEnabled(false);
//        rvNursingList.setLayoutManager(policyManager);
        rvNursingList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
//        rvNursingList.setNestedScrollingEnabled(false);
//        Util.addDividerToRecyclerView(getActivity(), rvNursingList, true);
        Util.addHorizontalDividerToRecyclerView(getActivity(), rvNursingList);
        rvNursingList.setAdapter(nursingTaskAdapter);
        nursingTaskAdapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);

            }
        });

        decrementFragmentCount();

    }

    public void endNursing(int position, NursingTaskBean item){
        Activity activity = getActivity();
        if (activity instanceof NursingDetailActivity){
            NursingDetailActivity nda = (NursingDetailActivity)activity;
            nda.endNursing(this, position, item);
        }

    }


    public void showNursingRecyclerViewOrFailureView(List<NursingTaskBean> list, boolean success, boolean isListEmpty) {
//        LogUtil.i("NursingDetailTasksFragment showNursingRecyclerViewOrFailureView srlNursingList:"+srlNursingList);
        srlNursingList.setRefreshing(false);
        if (success) {
            if (isListEmpty) {
                layContentNursingList.setVisibility(View.GONE);
                nursingListFailureView.setVisibility(View.VISIBLE);
                tvNursingListFailureView.setText(getNursingListEmptyHint());
            } else {
                nursingList.clear();
                if (!ListUtils.isEmpty(list)) {
                    nursingList.addAll(list);
                }
                nursingTaskAdapter.notifyDataSetChanged();

                layContentNursingList.setVisibility(View.VISIBLE);
                nursingListFailureView.setVisibility(View.GONE);
            }
        } else {
            layContentNursingList.setVisibility(View.GONE);
            nursingListFailureView.setVisibility(View.VISIBLE);
            tvNursingListFailureView.setText(getNursingListFailureHint());

        }
    }

    public void setLoadMoreStatus() {
        nursingTaskAdapter.setEnableLoadMore(false);
    }


   private String getNursingListEmptyHint() {
        return getResources().getString(R.string.hint_no_nursing_task_info_please_click_to_reload);
    }

    private String getNursingListFailureHint() {
        return getResources().getString(R.string.hint_load_nursing_task_info_error_please_click_to_reload);
    }

    public List<NursingTaskBean> getNursingList() {
        return nursingList;
    }

    public void setNursingList(List<NursingTaskBean> nursingList) {
        this.nursingList = nursingList;
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void initInject() {
        super.initInject();

    }

    private void loadNursingList(){
        AbstractActivity activity = (AbstractActivity) getActivity();
        if (activity instanceof NursingDetailActivity){
            NursingDetailActivity nda = (NursingDetailActivity)activity;
            nda.loadInfo();
        }
    }








//    @Override
//    public void onLoadNursingListDataSuccess(HttpResultNurseBaseBean<List<NursingTaskBean>> wrapper) {
//        int listSize = getListSizeFromResult(wrapper);
//        List<NursingTaskBean> list = getListFromResult(wrapper);
//
//
//        srlNursingList.setEnabled(true);
//        setLoadMoreStatus(pageSize, listSize, true);
//        isRefresh = false;
//
//        srlNursingList.setRefreshing(false);
//        if (wrapper != null) {
//            if (list != null) {
//                if (!ListUtils.isEmpty(list)) {
//                    nursingList.clear();
//                    nursingList.addAll(list);
//                    nursingTaskAdapter.notifyDataSetChanged();
//                    showNursingRecyclerViewOrFailureView(true, false);
//                } else {
//                    showNursingRecyclerViewOrFailureView(true, true);
//                }
//            } else {
//                showNursingRecyclerViewOrFailureView(true, true);
//            }
//        } else {
//            showNursingRecyclerViewOrFailureView(true, true);
//        }
//
//
//
//    }
//
//    @Override
//    public void onLoadNursingListMoreDataSuccess(HttpResultNurseBaseBean<List<NursingTaskBean>> wrapper) {
//        int listSize = getListSizeFromResult(wrapper);
//        List<NursingTaskBean> list = getListFromResult(wrapper);
//
//
//        srlNursingList.setEnabled(true);
//        setLoadMoreStatus(pageSize, listSize, true);
//        isRefresh = false;
//
//        srlNursingList.setRefreshing(false);
//        if (wrapper != null) {
//            if (list != null) {
//                if (!ListUtils.isEmpty(list)) {
//                    nursingList.addAll(list);
//                    nursingTaskAdapter.notifyDataSetChanged();
//                    showNursingRecyclerViewOrFailureView(true, false);
//                } else {
//                    showNursingRecyclerViewOrFailureView(true, true);
//                }
//            } else {
//                showNursingRecyclerViewOrFailureView(true, true);
//            }
//        } else {
//            showNursingRecyclerViewOrFailureView(true, true);
//        }
//
//
//    }
//
//    @Override
//    public void onLoadNursingListDataFailure(String message, boolean isLoadMore) {
//
//        showNursingRecyclerViewOrFailureView(false, true);
//        if (isLoadMore){
//            if (currentPage>Constants.DEFAULT_INITIAL_PAGE) {
//                --currentPage;
//            }
//
//        } else {
//
//        }
//        srlNursingList.setRefreshing(false);
//        setLoadMoreStatus(0, 0, false);
//
//        Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);
//    }


    //    public void setLoadMoreStatus(int pageSize, int listSize, boolean isSuccessful) {
//        if (isSuccessful) {
//            if (listSize < pageSize) {
////                nursingTaskAdapter.setEnableLoadMore(false);
//                nursingTaskAdapter.loadMoreEnd(true);
//            } else {
//                nursingTaskAdapter.setEnableLoadMore(true);
//                nursingTaskAdapter.loadMoreComplete();
//            }
//        } else {
//            nursingTaskAdapter.setEnableLoadMore(true);
//            nursingTaskAdapter.loadMoreFail();
//        }
//    }
//
//    public List<NursingTaskBean> getListFromResult(HttpResultNurseBaseBean<List<NursingTaskBean>> beanList) {
//        if (beanList != null) {
//            List<NursingTaskBean> list = beanList.getData();
//            return list;
//        } else {
//            return null;
//        }
//    }
//
//
//    public int getListSizeFromResult(HttpResultNurseBaseBean<List<NursingTaskBean>> beanList) {
//        if (beanList != null) {
//            List<NursingTaskBean> list = beanList.getData();
//            if (list != null) {
//                int size = ListUtils.getSize(list);
//                return size;
//            } else {
//                return 0;
//            }
//        } else {
//            return 0;
//        }
//    }

//    public void setLoadMoreStatus(int pageSize, int listSize, boolean isSuccessful) {
//        if (isSuccessful) {
//            if (listSize < pageSize) {
////                nursingTaskAdapter.setEnableLoadMore(false);
//                nursingTaskAdapter.loadMoreEnd(true);
//            } else {
//                nursingTaskAdapter.setEnableLoadMore(true);
//                nursingTaskAdapter.loadMoreComplete();
//            }
//        } else {
//            nursingTaskAdapter.setEnableLoadMore(true);
//            nursingTaskAdapter.loadMoreFail();
//        }
//    }
//



}
