package com.jqsoft.nursing.di.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.CheckingAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.HouseCheckingBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.di.contract.DeleteFindContract;
import com.jqsoft.nursing.di.presenter.DeleteFindPresenter;
import com.jqsoft.nursing.di.ui.activity.HouseholdDetailActivity;
import com.jqsoft.nursing.di.ui.activity.HouseholdSurveysActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleClickListener;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;
import com.jqsoft.nursing.utils3.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Administrator on 2017-07-07.
 */
//签约服务评价fragment
public class CheckingFragment extends AbstractFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener,DeleteFindContract.View{
//    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.lay_content)
    SwipeRefreshLayout srl;

    @BindView(R.id.lay_sign_service_assess_new_or_already_read_load_failure)
    View failureView;

    TextView tvFailureView;


    @Inject
    DeleteFindPresenter mPresenter;

    private String type;
    public CheckingAdapter mAdapter;



//    private int currentPage = Constants.DEFAULT_INITIAL_PAGE;
//
//    private int pageSize = Constants.DEFAULT_PAGE_SIZE;


    public CheckingFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_find_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        type = getDeliveredString(Constants.SIGN_SERVICE_ASSESS_TYPE_KEY);

        recyclerView=(RecyclerView)srl.findViewById(R.id.recyclerview);

        tvFailureView=(TextView)failureView.findViewById(R.id.tv_load_failure_hint);
        tvFailureView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
                onRefresh();
            }
        });


        srl.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        srl.setOnRefreshListener(this);


        BaseQuickAdapter<HouseCheckingBean, BaseViewHolder> mAdapter = new CheckingAdapter(new ArrayList<HouseCheckingBean>(),getActivity());
        this.mAdapter = (CheckingAdapter) mAdapter;
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setOnLoadMoreListener(this, recyclerView);
//        mAdapter.disableLoadMoreIfNotFullPage();
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
//        Util.addDividerToRecyclerView(getActivity(), recyclerView, true);
        recyclerView.setAdapter(mAdapter);




    }

    @Override
    protected void loadData() {
//        onRefresh();
//        mAdapter.setOnDeleteClickListener (new MyFindAdapter.DeleteListener(){
//
//            @Override
//            public void onDeleteClick(final String batchNo){
//                MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
//                        .title(R.string.hint_suggestion)
//                        .content("确定要删除吗?")
//                        .negativeText(R.string.cancel)
//                        .positiveText(R.string.confirm)
//                        .onPositive(new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                dialog.dismiss();
//
//                         Map<String, String> map = ParametersFactory.getDeleteFind(getActivity(),batchNo);
//                         mPresenter.main(map);
//                         SignServiceAssessActivity activity = (SignServiceAssessActivity) getActivity();
//                         activity.onRefresh();
//
//                            }
//                        }).build();
//                        dialog.show();
//
//
//            }
//        });

        mAdapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);
                Intent intent = new Intent(getActivity(), HouseholdDetailActivity.class);
                intent.putExtra("gid", mAdapter.getData().get(position).getGID());//5C5AAD6E-5EBC-4B5F-B0D7-3147C8D5D17A
                getActivity().startActivity(intent);
             //   Bundle bundle = new Bundle();

//                bundle.putString("batchNo", mAdapter.getData().get(position).getBatchNo());
//                bundle.putString("isMine", "0");
//
               //  Util.gotoActivity(getActivity(), HouseholdDetailActivity.class);
            }
        });

    }

//    @Override
//    protected void initInject() {
//        super.initInject();
//        DaggerApplication.get(getActivity())
//                .getAppComponent()
//                .addDeleteFindFragment(new DeleteFindFragmentModule(this))
//                .inject(this);
//
//    }


    private String getListEmptyHint(){
        String s = getResources().getString(R.string.hint_no_service_assess_info_please_click_to_reload);
        return s;
//        return getResources().getString(R.string.hint_list_empty_please_reload);
    }

    private String getFailureHint(){
        return getResources().getString(R.string.hint_load_failure);
    }


    public void showRecyclerViewOrFailureView(boolean success, boolean isListEmpty){
        if (success){
            if (isListEmpty){
                srl.setVisibility(View.GONE);
                failureView.setVisibility(View.VISIBLE);
                tvFailureView.setText(getListEmptyHint());
            } else {
                srl.setVisibility(View.VISIBLE);
                failureView.setVisibility(View.GONE);
            }
        } else {
            srl.setVisibility(View.GONE);
            failureView.setVisibility(View.VISIBLE);
            tvFailureView.setText(getFailureHint());

        }
    }


    public void endRefreshing(){
        if (srl!=null) {
            srl.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
//        currentPage = Constants.DEFAULT_INITIAL_PAGE;
//        mAdapter.setEnableLoadMore(false);
//        LogUtil.i("SignServiceAssessFragment onRefresh:currentPage/pageSize:" + currentPage + "/" + pageSize);

//        Map<String, String> map = getRequestMap();
//        mPresenter.main(map, false);

        HouseholdSurveysActivity activity = (HouseholdSurveysActivity) getActivity();
        activity.onRefresh();
    }


    @Override
    public void onLoadMoreRequested() {
//        ++currentPage;
//        Map<String, String> map = getRequestMap();
//        mPresenter.main(map, true);
//        LogUtil.i("SignServiceAssessFragment onLoadMoreRequested:" + "currentPage/pageSize:" + currentPage + "/" + pageSize);
//        srl.setEnabled(false);

        HouseholdSurveysActivity activity = (HouseholdSurveysActivity) getActivity();
        activity.onLoadMore();
    }


    public List<HouseCheckingBean> getListFromResult(HttpResultBaseBean<List<HouseCheckingBean>> beanList) {
        if (beanList != null) {
            List<HouseCheckingBean> list = beanList.getData();
            return list;
        } else {
            return null;
        }
    }

    public int getListSizeFromResult(HttpResultBaseBean<List<HouseCheckingBean>> beanList) {
        if (beanList != null) {
            List<HouseCheckingBean> list = beanList.getData();
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

    public void setLoadMoreStatusNew(int pageSize, int listSize, boolean isSuccessful) {
        if (mAdapter!=null) {
            if(listSize==15){
                mAdapter.setEnableLoadMore(true);
                mAdapter.loadMoreComplete();
           //     mAdapter.loadMoreComplete();
            }else {
                mAdapter.loadMoreEnd(true);
                /*mAdapter.setEnableLoadMore(true);
                mAdapter.loadMoreComplete();*/
            }


           // setLoadMoreStatus(pageSize, listSize, true);


        }
    }

    public void setLoadMoreStatus(int pageSize, int listSize, boolean isSuccessful) {
        if (mAdapter!=null) {
            mAdapter.setEnableLoadMore(true);
            mAdapter.loadMoreComplete();
       /* if (isSuccessful) {
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

            mAdapter.loadMoreComplete();
            mAdapter.setEnableLoadMore(false);*/
        }
    }

    @Override
    public void onDeleteFindSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean) {

    }

    @Override
    public void onDeleteFindFailure(String message) {

    }

//    @Override
//    public void onLoadListSuccess(HttpResultBaseBean<List<SignServiceAssessResultBean>> beanList) {
////        List<SignServiceAssessResultBean> simulatedList = SimulateData.getSimulatedTreatmentResultList();
////        ExecutionProjectsResultWrapperBean wrapperBean = new ExecutionProjectsResultWrapperBean(1, 20, simulatedList);
////        beanList.setData(wrapperBean);
//
////        int  page = getPageFromResult(beanList);
////        int pageSize = getPageSizeFromResult(beanList);
//        int listSize = getListSizeFromResult(beanList);
////        currentPage=page;
////        this.pageSize =pageSize;
////        LogUtil.i("SignServiceAssessFragment onLoadListSuccess,returned list size:"+pageSize+" currentPage/pageSize/listSize:"+currentPage+"/"+ this.pageSize+"/"+listSize);
//
//        List<SignServiceAssessResultBean> list = getListFromResult(beanList);
//        mAdapter.setNewData(list);
//
//        srl.setRefreshing(false);
//        setLoadMoreStatus(pageSize, listSize, true);
////        mAdapter.setEnableLoadMore(true);
//
//
////        mAdapter.disableLoadMoreIfNotFullPage();
//    }
//
//    @Override
//    public void onLoadMoreListSuccess(HttpResultBaseBean<List<SignServiceAssessResultBean>> beanList) {
////        TreatmentListBean bean = SimulateData.getSimulatedTreatmentListBean();
////
////        treatmentList = bean.getTreatmentList();
////        int  page = getPageFromResult(beanList);
////        int pageSize = getPageSizeFromResult(beanList);
//        int listSize = getListSizeFromResult(beanList);
////        currentPage=page;
////        this.pageSize =pageSize;
////        LogUtil.i("SignServiceAssessFragment onLoadMoreListSuccess,returned list size:"+pageSize+" currentPage/pageSize"+currentPage+"/"+ this.pageSize);
//
//        List<SignServiceAssessResultBean> list = getListFromResult(beanList);
//        mAdapter.addData(list);
////        mAdapter.disableLoadMoreIfNotFullPage();
//
////        mAdapter.loadMoreComplete();
////        mAdapter.disableLoadMoreIfNotFullPage();
//
//        srl.setEnabled(true);
//        srl.setRefreshing(false);
//        setLoadMoreStatus(this.pageSize, listSize, true);
//
//    }
//
//    @Override
//    public void onLoadListFailure(String message, boolean isLoadMore) {
////        LogUtil.i("SignServiceAssessFragment onLoadListFailure,message:"+message);
////        simulate();
//
////        if (isLoadMore){
////            if (currentPage>Constants.DEFAULT_INITIAL_PAGE) {
////                --currentPage;
////            }
////
////        } else {
////
////        }
//        srl.setRefreshing(false);
//        setLoadMoreStatus(0, 0, false);
//////        Util.showToast(this, message);
//        Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);
//    }

//    private void simulate() {
//        List<SignServiceAssessResultBean> list = SimulateData.getSignServiceAssessList();
//        HttpResultBaseBean<List<SignServiceAssessResultBean>> wrapper = new HttpResultBaseBean<>("0", "success", list);
//        onLoadMoreListSuccess(wrapper);
//
//    }


}
