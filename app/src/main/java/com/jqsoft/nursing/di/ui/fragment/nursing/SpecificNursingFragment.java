package com.jqsoft.nursing.di.ui.fragment.nursing;

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
import com.jqsoft.nursing.adapter.nursing.NursingListAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.NursingBean;
import com.jqsoft.nursing.di.contract.nursing.SpecificNursingFragmentContract;
import com.jqsoft.nursing.di.module.nursing.SpecificNursingFragmentModule;
import com.jqsoft.nursing.di.presenter.nursing.SpecificNursingFragmentPresenter;
import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.LogUtil;
import com.jqsoft.nursing.utils3.util.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 特定类型的护理fragment
 * Created by Administrator on 2018-04-03.
 */

public class SpecificNursingFragment extends AbstractFragment  implements
        SpecificNursingFragmentContract.View, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{




    @BindView(R.id.lay_content_nursing_list)
    View layContentNursingList;
    @BindView(R.id.lay_specific_nursing_load_failure)
    View nursingListFailureView;
    TextView tvNursingListFailureView;


    SwipeRefreshLayout srlNursingList;
    RecyclerView rvNursingList;


    TextView tvFailureView;

    @Inject
    SpecificNursingFragmentPresenter mPresenter;

    private boolean isRefresh = false;

    private int type;

    private NursingListAdapter nursingListAdapter;
    List<NursingBean> nursingList;
//    List<NursingBean> nursingListCopy;


    private String keywordString;

    private int currentPage = Constants.DEFAULT_INITIAL_PAGE;
    private int pageSize = Constants.DEFAULT_PAGE_SIZE;
//    private int pageSize = 1;

    public SpecificNursingFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_specific_nursing_layout;
    }

    @Override
    protected void initData() {
        nursingList=new ArrayList<>();
//        nursingListCopy=new ArrayList<>();
    }

    @Override
    protected void initView() {
        srlNursingList = (SwipeRefreshLayout) layContentNursingList;
        rvNursingList= (RecyclerView) layContentNursingList.findViewById(R.id.recyclerview);

        srlNursingList.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        srlNursingList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                nursingListAdapter.setEnableLoadMore(false);
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
                        nursingListAdapter.setEnableLoadMore(false);
                        loadNursingList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        nursingListAdapter = new NursingListAdapter(nursingList);
     //   nursingListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        nursingListAdapter.setOnLoadMoreListener(this, rvNursingList);
        nursingListAdapter.setEnableLoadMore(false);
//        rvNursingList.setNestedScrollingEnabled(false);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        rvNursingList.setLayoutManager(new FullyLinearLayoutManagerSmoothScroll(getActivity()));
//        NoScrollFullyLinearLayoutManager policyManager = new NoScrollFullyLinearLayoutManager(getActivity());
//        policyManager.setScrollEnabled(false);
//        rvNursingList.setLayoutManager(policyManager);
        rvNursingList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
//        rvNursingList.setNestedScrollingEnabled(false);
//        Util.addDividerToRecyclerView(getActivity(), rvNursingList, true);
      //  Util.addHorizontalDividerToRecyclerView(getActivity(), rvNursingList);
        rvNursingList.setAdapter(nursingListAdapter);
        nursingListAdapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);
                NursingBean nb = (NursingBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.NURSING_BED_ID_KEY, nb.getBedID());
                bundle.putParcelable(Constants.NURSING_BEAN_KEY, nb);
                Util.gotoActivityWithBundle(getActivity(), NursingDetailActivity.class, bundle);

            }
        });
//        willNotAutoLoadMore();
    }

//    private void initNursingListCopy(){
//        nursingListCopy.clear();
//        nursingListCopy.addAll(nursingList);
//    }

//    private void willNotAutoLoadMore() {
//        nursingListAdapter.disableLoadMoreIfNotFullPage();
//    }

    @Override
    public void onLoadNursingListDataSuccess(HttpResultNurseBaseBean<List<NursingBean>> wrapper) {
        int listSize = getListSizeFromResult(wrapper);
        List<NursingBean> list = getListFromResult(wrapper);


        srlNursingList.setEnabled(true);
        isRefresh = false;

        srlNursingList.setRefreshing(false);
        if (wrapper != null) {
            if (list != null) {
                if (!ListUtils.isEmpty(list)) {
                    nursingList.clear();
                    nursingList.addAll(list);
//                    initNursingListCopy();
//                    nursingListAdapter.setNewData(nursingListCopy);
//                    willNotAutoLoadMore();
                    nursingListAdapter.notifyDataSetChanged();
                    showNursingRecyclerViewOrFailureView(true, false);
                } else {
                    showNursingRecyclerViewOrFailureView(true, true);
                }
            } else {
                showNursingRecyclerViewOrFailureView(true, true);
            }
        } else {
            showNursingRecyclerViewOrFailureView(true, true);
        }

        setLoadMoreStatus(pageSize, listSize, true);


    }

    @Override
    public void onLoadNursingListMoreDataSuccess(HttpResultNurseBaseBean<List<NursingBean>> wrapper) {
        int listSize = getListSizeFromResult(wrapper);
        List<NursingBean> list = getListFromResult(wrapper);


        srlNursingList.setEnabled(true);
        isRefresh = false;

        srlNursingList.setRefreshing(false);
        if (wrapper != null) {
            if (list != null) {
                if (!ListUtils.isEmpty(list)) {
                    nursingList.addAll(list);
//                    initNursingListCopy();
//                    nursingListAdapter.setNewData(nursingListCopy);
//                    willNotAutoLoadMore();
                    nursingListAdapter.notifyDataSetChanged();
                    showNursingRecyclerViewOrFailureView(true, false);
                } else {
                    showNursingRecyclerViewOrFailureView(true, false);
                }
            } else {
                showNursingRecyclerViewOrFailureView(true, false);
            }
        } else {
            showNursingRecyclerViewOrFailureView(true, false);
        }

        setLoadMoreStatus(pageSize, listSize, true);

    }

    @Override
    public void onLoadNursingListDataFailure(String message, boolean isLoadMore) {

//        willNotAutoLoadMore();

        showNursingRecyclerViewOrFailureView(false, true);
        if (isLoadMore){
            if (currentPage>Constants.DEFAULT_INITIAL_PAGE) {
                --currentPage;
            }

        } else {

        }
        srlNursingList.setRefreshing(false);
        setLoadMoreStatus(0, 0, false);

        Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);
    }


    private void showNursingRecyclerViewOrFailureView(boolean success, boolean isListEmpty) {
        if (success) {
            if (isListEmpty) {
                layContentNursingList.setVisibility(View.GONE);
                nursingListFailureView.setVisibility(View.VISIBLE);
                tvNursingListFailureView.setText(getNursingListListEmptyHint());
            } else {
                layContentNursingList.setVisibility(View.VISIBLE);
                nursingListFailureView.setVisibility(View.GONE);
            }
        } else {
            layContentNursingList.setVisibility(View.GONE);
            nursingListFailureView.setVisibility(View.VISIBLE);
            tvNursingListFailureView.setText(getNursingListFailureHint());

        }
    }

    private String getNursingListListEmptyHint() {
        return getResources().getString(R.string.hint_no_nursing_list_info_please_click_to_reload);
    }

    private String getNursingListFailureHint() {
        return getResources().getString(R.string.hint_load_nursing_list_info_error_please_click_to_reload);
    }



    @Override
    protected void loadData() {
        loadNursingList();
    }

    @Override
    protected void initInject() {
        super.initInject();
        DaggerApplication.get(getActivity())
                .getAppComponent()
                .addSpecificNursingFragment(new SpecificNursingFragmentModule(this))
                .inject(this);

    }

    private void loadNursingList(){
        currentPage = Constants.DEFAULT_INITIAL_PAGE;
        isRefresh = true;
        nursingListAdapter.setEnableLoadMore(false);

        Map<String, String> map = getNursingListRequestMap();
        mPresenter.getNursingList(map, false);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {
        ++currentPage;
        LogUtil.i("SpecificNursingFragment onLoadMoreRequested currentPage:"+currentPage);
        Map<String, String> map = getNursingListRequestMap();
        mPresenter.getNursingList(map, true);

    }



    private Map<String, String> getNursingListRequestMap(){
        String userId = IdentityManager.getUserId(getActivity());
        String beginIndex = String.valueOf(currentPage*pageSize);
        String endIndex = String.valueOf((currentPage+1)*pageSize);
        Map<String, String> map = ParametersFactory.getNursingListMap(getActivity(), userId, beginIndex, endIndex);
        return map;
    }

    public void setLoadMoreStatus(int pageSize, int listSize, boolean isSuccessful) {
        if (isSuccessful) {
            if (listSize < pageSize) {
                nursingListAdapter.setEnableLoadMore(false);
                nursingListAdapter.loadMoreEnd(true);
            } else {
                nursingListAdapter.setEnableLoadMore(true);
                nursingListAdapter.loadMoreComplete();
            }
        } else {
            nursingListAdapter.setEnableLoadMore(true);
            nursingListAdapter.loadMoreFail();
        }
    }

    public List<NursingBean> getListFromResult(HttpResultNurseBaseBean<List<NursingBean>> beanList) {
        if (beanList != null) {
            List<NursingBean> list = beanList.getData();
            return list;
        } else {
            return null;
        }
    }


    public int getListSizeFromResult(HttpResultNurseBaseBean<List<NursingBean>> beanList) {
        if (beanList != null) {
            List<NursingBean> list = beanList.getData();
            if (list != null) {
                int size = ListUtils.getSize(list);
                return size;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
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

}
