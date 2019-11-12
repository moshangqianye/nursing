package com.jqsoft.nursing.di.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.jqsoft.livebody_verify_lib.activity.CardImageLiveFaceVerifyActivity;
import com.jqsoft.livebody_verify_lib.activity.HeadCollectActivity;
import com.jqsoft.livebody_verify_lib.bean.Version;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.GCAPolicyAdapter;
import com.jqsoft.nursing.adapter.NotificationAdapter;
import com.jqsoft.nursing.adapter.nursing.NursingListAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.Identity;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.FunctionImageBean;
import com.jqsoft.nursing.bean.JsData;
import com.jqsoft.nursing.bean.YData;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.NotificationBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.PolicyBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;
import com.jqsoft.nursing.bean.nursing.NursingBean;
import com.jqsoft.nursing.di.contract.IndexFragmentContract;
import com.jqsoft.nursing.di.module.IndexFragmentModule;
import com.jqsoft.nursing.di.presenter.IndexFragmentPresenter;
import com.jqsoft.nursing.di.ui.activity.ArcFaceListActivity;
import com.jqsoft.nursing.di.ui.activity.BuildingRoomActivity;
import com.jqsoft.nursing.di.ui.activity.GuideActivity;
import com.jqsoft.nursing.di.ui.activity.HandleProgress;
import com.jqsoft.nursing.di.ui.activity.ListGuideActivity;
import com.jqsoft.nursing.di.ui.activity.NotificationDetailActivity;
import com.jqsoft.nursing.di.ui.activity.PersonCollectionActivity;
import com.jqsoft.nursing.di.ui.activity.PolicyDetailActivity;
import com.jqsoft.nursing.di.ui.activity.ReceptionActivity;
import com.jqsoft.nursing.di.ui.activity.SchedulingActivity;
import com.jqsoft.nursing.di.ui.activity.SignServiceAssessActivity;
import com.jqsoft.nursing.di.ui.activity.SocialHistoryActivity;
import com.jqsoft.nursing.di.ui.activity.WorkbenchActivity;

import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.helper.NoScrollFullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;
import com.jqsoft.nursing.rx.RxBus;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.LogUtil;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.jqsoft.nursing.view.ZQImageViewRoundOval;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by quantan.liu on 2017/4/12.
 */

public class IndexFragment extends AbstractFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, IndexFragmentContract.View {
    @BindView(R.id.tv_welcome)
    TextView tvWelcome;
    @BindView(R.id.ll_function_image_group)
    LinearLayout llFunctionImageGroup;
    @BindView(R.id.civ_appointment)
    ZQImageViewRoundOval civAppointment;
    @BindView(R.id.lay_notification)
    LinearLayout layNotification;
    @BindView(R.id.lay_content_notification)
    View layContentNotification;
    @BindView(R.id.tv_notification_more)
    TextView tvNotificationMore;

    SwipeRefreshLayout srlNotification;
    RecyclerView rvNotification;

    @BindView(R.id.lay_notification_load_failure)
    View notificationFailureView;

    TextView tvNotificationFailureView;


    @BindView(R.id.tv_policy_family)
    TextView tvPolicyFamily;
    @BindView(R.id.lay_content_policy)
    View layContentPolicy;

    SwipeRefreshLayout srlPolicy;
    RecyclerView rvPolicy;

    @BindView(R.id.lay_policy_load_failure)
    View policyFailureView;

    TextView tvPolicyFailureView;
     private DaggerApplication application;

    @BindView(R.id.lay_content_nursing_list)
    View layContentNursingList;
    @BindView(R.id.lay_nursing_list_load_failure)
    View nursingListFailureView;
    TextView tvNursingListFailureView;
    @BindView(R.id.ll_Scheduling)
      LinearLayout ll_Scheduling;
    SwipeRefreshLayout srlNursingList;
    RecyclerView rvNursingList;
    @BindView(R.id.ll_arcface)
     LinearLayout ll_arcface;


    List<NotificationBean> notificationList;
    List<PolicyBean> policyList;
    List<NursingBean> nursingList;

    NotificationAdapter notificationAdapter;
    GCAPolicyAdapter policyAdapter;
    NursingListAdapter nursingListAdapter;

    @Inject
    IndexFragmentPresenter mPresenter;

    CompositeSubscription mCompositeSubscription;


    @Override
    public void loadData() {
    }

    @Override
    public void initInject() {
        DaggerApplication.get(this.getActivity())
                .getAppComponent()
                .addIndexFragment(new IndexFragmentModule(this))
                .inject(this);


    }


    @Override
    public int getLayoutId() {
//        LogUtil.i("IndexDeanFragment getLayoutId is called");
        return R.layout.fragment_index;
    }

    @Override
    public void initData() {
        notificationList = new ArrayList<>();
        policyList = new ArrayList<>();
        nursingList=new ArrayList<>();
        ll_Scheduling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.gotoActivity(getActivity(), SchedulingActivity.class);
            }
        });
    }

    @Override
    public void onRefresh() {
//        policyAdapter.setEnableLoadMore(false);
//        loadPolicyList();
    }

    @Override
    public void onLoadMoreRequested() {
        ++currentPage;
        LogUtil.i("SpecificNursingFragment onLoadMoreRequested currentPage:"+currentPage);
        Map<String, String> map = getNursingListRequestMap();
        mPresenter.getNursingList(map, true);
    }
    private int currentPage = Constants.DEFAULT_INITIAL_PAGE;
    private int pageSize = Constants.DEFAULT_PAGE_SIZE;
    private boolean isRefresh = false;

    private void loadNursingList(){
        currentPage = Constants.DEFAULT_INITIAL_PAGE;
        isRefresh = true;
        nursingListAdapter.setEnableLoadMore(false);

        Map<String, String> map = getNursingListRequestMap();
        mPresenter.getNursingList(map,false);
    }

    private Map<String, String> getNursingListRequestMap(){
        String userId = IdentityManager.getUserId(getActivity());
        String beginIndex = String.valueOf(currentPage*pageSize);
        String endIndex = String.valueOf((currentPage+1)*pageSize);

//        String userId = IdentityManager.getUserId(getActivity());
//        Map<String, String> map = ParametersFactory.getNursingListMap(getActivity(), userId, Constants.BEGIN_INDEX_STRING,
//                Constants.HOME_PAGE_NURSING_LIST_END_INDEX);

        Map<String, String> map = ParametersFactory.getNursingListMap(getActivity(), userId, beginIndex, endIndex);

        return map;
    }

    private void loadPolicyList() {
        Map<String, String> map = getPolicyListRequestMap();
//        RequestBody body = Util.getBodyFromMap(map);
        mPresenter.getPolicyList(map);

    }

    private Map<String, String> getPolicyListRequestMap() {
////        String cardNo = Identity.getCardNo();
//        String cardNo = IdentityManager.getCardNo(getActivity());
//        String personID = IdentityManager.getPersonID(getActivity());
        String username= IdentityManager.getLoginSuccessUsername(getContext());
        Map<String, String> map = ParametersFactory.getGCAPolicyListMap(getActivity(), Constants.CODE_POLICY, username, String.valueOf(Constants.START_PAGE_INDEX), String.valueOf(Constants.PAGE_SIZE_MINOR),
                Constants.METHOD_NAME_NOTIFICATION_OR_POLICY);
        return map;
    }

    private void loadNotificationList() {
        Map<String, String> map = getNotificationListRequestMap();
//        RequestBody body = Util.getBodyFromMap(map);
        mPresenter.getNotificationList(map);
    }

    private Map<String, String> getNotificationListRequestMap() {
//        String year = Util.getCurrentYearString();
//        Map<String, String> map = ParametersFactory.getNotificationDataMap(getActivity(), year, Constants.DEFAULT_INITIAL_PAGE,
//                Constants.DEFAULT_PAGE_SIZE);
        String username= IdentityManager.getLoginSuccessUsername(getContext());
        Map<String, String> map = ParametersFactory.getNotificationListMap(getActivity(), Constants.CODE_NOTIFICATION, username, String.valueOf(Constants.START_PAGE_INDEX), String.valueOf(Constants.PAGE_SIZE_MINOR),
                Constants.METHOD_NAME_NOTIFICATION_OR_POLICY);
        return map;
    }

    @Override
    public void onLoadNotificationDataSuccess(GCAHttpResultBaseBean<List<NotificationBean>> bean) {
        srlNotification.setRefreshing(false);
//        simulateNotification();
        if (bean != null) {
            List<NotificationBean> list = bean.getData();
            if (!ListUtils.isEmpty(list)) {
//                replaceXmlTag(list);
                notificationList.clear();
                notificationList.addAll(list);
//                testAddMoreNotificationData();
                notificationAdapter.notifyDataSetChanged();
                showNotificationRecyclerViewOrFailureView(true, false);
//                showNotificationRecyclerViewOrFailureView(true, true);
//                showNotificationRecyclerViewOrFailureView(false, true);
            } else {
                showNotificationRecyclerViewOrFailureView(true, true);
            }
        } else {
            showNotificationRecyclerViewOrFailureView(true, true);
        }
    }

    private void testAddMoreNotificationData() {
        for (int i = 0; i < 16; ++i) {
            NotificationBean nb = new NotificationBean("" + i, "20171228", "标题" + i, "作者" + i, "消息" + i, "", "", "");
            notificationList.add(nb);
        }
    }

//    private void replaceXmlTag(List<NotificationBean> list){
//        if (!ListUtils.isEmpty(list)){
//            for (int i = 0; i < list.size(); ++i){
//                NotificationBean pb = list.get(i);
//                pb.setContent(Util.getReplacedXmlTagString(pb.getContent()));
//            }
//        }
//    }

    @Override
    public void onLoadNotificationDataFailure(String message) {
        srlNotification.setRefreshing(false);
        showNotificationRecyclerViewOrFailureView(false, true);
//        simulateNotification();
        Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);
    }

    @Override
    public void onLoadPolicyDataSuccess(GCAHttpResultBaseBean<List<PolicyBean>> wrapper) {
        srlPolicy.setRefreshing(false);
        if (wrapper != null) {
            List<PolicyBean> list = wrapper.getData();
            if (list != null) {
//                List<PolicyBean> list = getPolicyListFromAddingMessageBean(ramb);
                if (!ListUtils.isEmpty(list)) {
                    policyList.clear();
                    policyList.addAll(list);
                    policyAdapter.notifyDataSetChanged();
                    showPolicyRecyclerViewOrFailureView(true, false);
                    //                showPolicyRecyclerViewOrFailureView(true, true);
                    //                showPolicyRecyclerViewOrFailureView(false, true);
                } else {
                    showPolicyRecyclerViewOrFailureView(true, true);
                }
            } else {
                showPolicyRecyclerViewOrFailureView(true, true);
            }
        } else {
            showPolicyRecyclerViewOrFailureView(true, true);
        }


//        simulatePolicy();

    }

    @Override
    public void onLoadPolicyDataFailure(String message) {
        srlPolicy.setRefreshing(false);
        showPolicyRecyclerViewOrFailureView(false, true);
//        simulatePolicy();
        Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);
    }

    @Override
    public void onLoadNursingListDataSuccess(HttpResultNurseBaseBean<List<NursingBean>> wrapper) {
//        int listSize = getListSizeFromResult(wrapper);
////        List<NursingBean> list = getListFromResult(wrapper);
//
//
//        srlNursingList.setEnabled(true);
//        isRefresh = false;
//
//        srlNursingList.setRefreshing(false);
//        if (wrapper != null) {
//            List<NursingBean> list = wrapper.getData();
//            if (list != null) {
//                if (!ListUtils.isEmpty(list)) {
//                    nursingList.clear();
//                    nursingList.addAll(list);
//                    nursingListAdapter.notifyDataSetChanged();
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

    public List<NursingBean> getListFromResult(HttpResultNurseBaseBean<List<NursingBean>> beanList) {
        if (beanList != null) {
            List<NursingBean> list = beanList.getData();
            return list;
        } else {
            return null;
        }
    }


    @Override
    public void onLoadNursingListDataFailure(String message) {
        srlNursingList.setRefreshing(false);
        showNursingRecyclerViewOrFailureView(false, true);
        Util.showToast(getActivity(), Constants.HINT_LOADING_DATA_FAILURE);
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

//    private List<PolicyBean> getPolicyListFromAddingMessageBean(PolicyAndMessageBean ramb){
//        List<PolicyBean> result = new ArrayList<>();
//        if (ramb==null){
//            return result;
//        } else {
//            String message = Util.trimString(ramb.getContent());
//            String count = Util.trimString(ramb.getTotal());
//            List<PolicyBean> policyList = ramb.getList();
//            if (policyList==null){
//                policyList=new ArrayList<>();
//            }
//            for (int i = 0; i < policyList.size(); ++i){
//                PolicyBean bean = policyList.get(i);
//                bean.setType(PolicyBean.TYPE_REMIND);
//            }
//            PolicyBean messageBean = new PolicyBean(PolicyBean.TYPE_MESSAGE, R.mipmap.r_policy_3, message, count,
//                    Constants.EMPTY_STRING);
//            policyList.add(messageBean);
//
//            result.addAll(policyList);
//        }
//        return result;
//    }

    private void showNotificationRecyclerViewOrFailureView(boolean success, boolean isListEmpty) {
        if (success) {
            if (isListEmpty) {
                layNotification.setVisibility(View.GONE);
                notificationFailureView.setVisibility(View.VISIBLE);
                tvNotificationFailureView.setText(getNotificationListEmptyHint());
            } else {
                layNotification.setVisibility(View.VISIBLE);
                notificationFailureView.setVisibility(View.GONE);
            }
        } else {
            layNotification.setVisibility(View.GONE);
            notificationFailureView.setVisibility(View.VISIBLE);
            tvNotificationFailureView.setText(getNotificationFailureHint());

        }
    }

    private String getNotificationListEmptyHint() {
        return getResources().getString(R.string.hint_no_notification_info_please_click_to_reload);
    }

    private String getNotificationFailureHint() {
        return getResources().getString(R.string.hint_load_notification_info_error_please_click_to_reload);
    }

    private void showPolicyRecyclerViewOrFailureView(boolean success, boolean isListEmpty) {
        if (success) {
            if (isListEmpty) {
                layContentPolicy.setVisibility(View.GONE);
                policyFailureView.setVisibility(View.VISIBLE);
                tvPolicyFailureView.setText(getPolicyListEmptyHint());
            } else {
                layContentPolicy.setVisibility(View.VISIBLE);
                policyFailureView.setVisibility(View.GONE);
            }
        } else {
            layContentPolicy.setVisibility(View.GONE);
            policyFailureView.setVisibility(View.VISIBLE);
            tvPolicyFailureView.setText(getPolicyFailureHint());

        }
    }

    private String getPolicyListEmptyHint() {
        return getResources().getString(R.string.hint_no_policy_info_please_click_to_reload);
    }

    private String getPolicyFailureHint() {
        return getResources().getString(R.string.hint_load_policy_info_error_please_click_to_reload);
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

//    private void simulateNotification(){
//        List<NotificationBean> simulatedList = SimulateData.getSimulatedNotificationList();
//        notificationList.clear();
//        notificationList.addAll(simulatedList);
//        notificationAdapter.notifyDataSetChanged();
//    }
//
//    private void simulatePolicy(){
//        List<PolicyBean> simulatedList = SimulateData.getSimulatedPolicyList();
//        policyList.clear();
//        policyList.addAll(simulatedList);
//        policyAdapter.notifyDataSetChanged();
//    }


    @Override
    public void initView() {
        application = (DaggerApplication) this.getActivity().getApplication();
//        FunctionImageTextGroupContent imageGroupContent = new FunctionImageTextGroupContent(getActivity());
//        imageGroupContent.initView(DataSourceFactory.getIndexPageFunctionImageTextGroupList());
//        llFunctionImageGroup.addView(imageGroupContent.getView());

        civAppointment.setType(ZQImageViewRoundOval.TYPE_ROUND);
        ll_arcface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                gotoVerify(getContext(),"","","",100);
                Intent intent = new Intent(getContext(), ArcFaceListActivity.class);
                startActivity(intent);
            }
        });

        srlNotification = (SwipeRefreshLayout) layContentNotification;
        rvNotification = (RecyclerView) layContentNotification.findViewById(R.id.recyclerview);
        srlPolicy = (SwipeRefreshLayout) layContentPolicy;
        rvPolicy = (RecyclerView) layContentPolicy.findViewById(R.id.recyclerview);
        srlNursingList = (SwipeRefreshLayout) layContentNursingList;
        rvNursingList= (RecyclerView) layContentNursingList.findViewById(R.id.recyclerview);

        srlNotification.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
//        srlNotification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                notificationAdapter.setEnableLoadMore(false);
//                loadNotificationList();
//            }
//        });

        RxView.clicks(tvNotificationMore)
                .throttleFirst(Constants.RXBINDING_THROTTLE, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
//                        Util.gotoActivity(getActivity(), NotificationActivity.class);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        tvNotificationFailureView = (TextView) notificationFailureView.findViewById(R.id.tv_load_failure_hint);
        RxView.clicks(tvNotificationFailureView)
                .throttleFirst(Constants.RXBINDING_THROTTLE, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        notificationAdapter.setEnableLoadMore(false);
                        loadNotificationList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


//        List<NotificationBean> notificationList = SimulateData.getSimulatedNotificationList();
        notificationAdapter = new NotificationAdapter(notificationList, NotificationAdapter.TYPE_SINGLE_LINE);
        //        easyLoadMoreView = new EasyLoadMoreView();
//        notificationAdapter.setLoadMoreView(easyLoadMoreView);
     //   notificationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        notificationAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        }, rvNotification);
        notificationAdapter.setEnableLoadMore(false);
//        rvNotification.setNestedScrollingEnabled(false);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        rvNotification.setLayoutManager(new FullyLinearLayoutManagerSmoothScroll(getActivity()));
//        rvNotification.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        NoScrollFullyLinearLayoutManager notificationManager = new NoScrollFullyLinearLayoutManager(getActivity());
        notificationManager.setScrollEnabled(false);
        rvNotification.setLayoutManager(notificationManager);
//        Util.addDividerToRecyclerView(getActivity(), rvNotification, true);
        rvNotification.setAdapter(notificationAdapter);
        notificationAdapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);
                NotificationBean nb = notificationAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.NOTIFICATION_DETAIL_ACTIVITY_KEY, nb);
                Util.gotoActivityWithBundle(getActivity(), NotificationDetailActivity.class, bundle);

            }
        });


//        RxView.clicks(tvPolicyFamily)
//                .throttleFirst(Constants.RXBINDING_THROTTLE, TimeUnit.SECONDS)
//                .subscribe(new Observer<Object>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Object value) {
//                        Util.gotoActivity(getActivity(), FamilyMemberActivity.class);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        srlPolicy.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
//        srlPolicy.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                policyAdapter.setEnableLoadMore(false);
//                loadPolicyList();
//
//            }
//        });

        tvPolicyFailureView = (TextView) policyFailureView.findViewById(R.id.tv_load_failure_hint);
        RxView.clicks(tvPolicyFailureView)
                .throttleFirst(Constants.RXBINDING_THROTTLE, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        policyAdapter.setEnableLoadMore(false);
                        loadPolicyList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        policyAdapter = new GCAPolicyAdapter(policyList);
//        easyLoadMoreView = new EasyLoadMoreView();
//        policyAdapter.setLoadMoreView(easyLoadMoreView);
    //    policyAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        policyAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        }, rvPolicy);
        policyAdapter.setEnableLoadMore(false);
//        rvPolicy.setNestedScrollingEnabled(false);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        rvPolicy.setLayoutManager(new FullyLinearLayoutManagerSmoothScroll(getActivity()));
        NoScrollFullyLinearLayoutManager policyManager = new NoScrollFullyLinearLayoutManager(getActivity());
        policyManager.setScrollEnabled(false);
        rvPolicy.setLayoutManager(policyManager);
//        rvPolicy.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
//        rvPolicy.setNestedScrollingEnabled(false);
        Util.addDividerToRecyclerView(getActivity(), rvPolicy, true);
        rvPolicy.setAdapter(policyAdapter);
        policyAdapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);
                PolicyBean pb = policyAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.POLICY_DETAIL_ACTIVITY_KEY, pb);
                Util.gotoActivityWithBundle(getActivity(), PolicyDetailActivity.class, bundle);

//                int type = bean.getType();
//                if (PolicyBean.TYPE_REMIND == type) {
//                    String cardNo = IdentityManager.getCardNo(getActivity());
//                    Util.gotoExecutionProjectsActivity(getActivity(), ExecutionProjectsType.ExecutionProjectsTypeEnum.Latest7Days,
//                            cardNo);
//                } else {
//                    Identity.shouldReadIdCard = false;
//                    RxBus.getDefault().post(Constants.EVENT_TYPE_WOULD_SCROLL_TO_WORKBENCH_INDEX, Constants.WORKBENCH_QUERY);
//
//                }

            }
        });

        srlNursingList.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        srlNursingList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                nursingListAdapter.setEnableLoadMore(false);
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
        nursingListAdapter.setOnLoadMoreListener(this, rvNursingList);
        nursingListAdapter.setEnableLoadMore(false);
    //    nursingListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        nursingListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//
//            }
//        }, rvNursingList);
        nursingListAdapter.setEnableLoadMore(false);

//        rvNursingList.setNestedScrollingEnabled(false);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        rvNursingList.setLayoutManager(new FullyLinearLayoutManagerSmoothScroll(getActivity()));
//        NoScrollFullyLinearLayoutManager policyManager = new NoScrollFullyLinearLayoutManager(getActivity());
//        policyManager.setScrollEnabled(false);
//        rvNursingList.setLayoutManager(policyManager);
        rvNursingList.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
//        rvNursingList.setNestedScrollingEnabled(false);
//        rvNursingList.addItemDecoration(new RecyclerViewHorizontalSeparator(getActivity()));
 //       Util.addHorizontalDividerToRecyclerView(getActivity(), rvNursingList);
//        Util.addDividerToRecyclerView(getActivity(), rvNursingList, true);
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

        registerRxBusEvent();

//        loadNotificationList();
//        loadPolicyList();
        nursingListAdapter.setEnableLoadMore(false);
        loadNursingList();
        

        //LogUtil.i("IndexDeanFragment initView");
//        String welcomeString = Util.getWelcomeString();
//        tvWelcomeText.setText(welcomeString);


//        setHasOptionsMenu(true);


//        AppCompatActivity mAppCompatActivity = (AppCompatActivity) getActivity();
//        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.toolbar);
//        LogUtil.i("IndexDeanFragment initView toolbar:"+toolbar);
//        mAppCompatActivity.setSupportActionBar(toolbar);


//        reassignToolbar();
//
//        String areaName = Identity.getCurrentAreaName();
//        indexAreaName.setText(areaName);
//
//        indexAreaLinearLayout.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            public void onNoDoubleClick(View v) {
//                super.onNoDoubleClick(v);
//                selectArea();
//            }
//        });
//
////        searchView = (MaterialSearchView) findViewById(R.id.search_view);
//        searchView.setVoiceSearch(false);
////        searchView.setCursorDrawable(R.drawable.color_cursor_white);
////        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
//
//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG)
////                        .show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //Do some magic
//                return false;
//            }
//        });
//
//        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
//                //Do some magic
////                ToastUtil.show(getActivity(), "searchview show");
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                //Do some magic
//            }
//        });
//
//
//        String currentYMD = Util.getCurrentYearMonthDayString();
//        indexChart1Time.setText(currentYMD);
//        indexChart2Time.setText(currentYMD);
//        indexChart1Time.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            public void onNoDoubleClick(View v) {
//                super.onNoDoubleClick(v);
//                onSelectChart1Time();
//            }
//        });
//        indexChart2Time.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            public void onNoDoubleClick(View v) {
//                super.onNoDoubleClick(v);
//                onSelectChart2Time();
//            }
//        });
//
//        initWebView1();
//        initWebView2();
//
////        //点击事件
////        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                Toast.makeText(ColoredActivity.this,"第"+i+"行",Toast.LENGTH_LONG).show();
////            }
////        });
//
////        srlAndroid.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
////        rvAndroid.setLayoutManager(new LinearLayoutManager(getActivity()));
////        rvAndroid.setAdapter(mAdapter);
////        srlAndroid.setOnRefreshListener(this);
////        mAdapter.setLoadMoreView(new EasyLoadMoreView());
////        mAdapter.setOnLoadMoreListener(this, rvAndroid);
////
////        ((NBAAdapter)mAdapter).setOnItemClickListener(new NBAAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClickListener(String id, String imgUrl, View view) {
////                startNBADetailActivity(id, imgUrl, view);
////            }
////        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterRxBusEvent();
    }

    public Toolbar initToolbar() {
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.toolbar);
        //LogUtil.i("initToolbar toolbar:" + toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        return toolbar;
    }


    public void reassignToolbar() {
        WorkbenchActivity workbenchActivity = (WorkbenchActivity) getActivity();
        if (workbenchActivity != null) {
            Toolbar toolbar = (Toolbar) workbenchActivity.findViewById(R.id.toolbar);
            //LogUtil.i("IndexDeanFragment initView toolbar:" + toolbar);
            workbenchActivity.setToolBarWithNoBackButtonAndNoTitle(toolbar);
        }

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        LogUtil.i("IndexDeanFragment onCreate setHasOptionsMenu(true) called");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
//        reassignToolbar();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        LogUtil.i("IndexDeanFragment onCreateOptionsMenu called");
////        reassignToolbar();
//        menu.clear();
//        inflater.inflate(R.menu.menu_search, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //LogUtil.i("IndexDeanFragment onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.action_search:
                //code here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initToolbar();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

//    private class MyPagerAdapter extends FragmentPagerAdapter {
//        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return Constants.EMPTY_STRING;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//    }


//    //    @OnClick(R.id.index_chart1_time)
//    public void onSelectChart1Time() {
////        Util.showDateSelectDialog(getActivity(), "2017-06-07", "dateselecttag", new DatePickerDialog.OnDateSetListener() {
////            @Override
////            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
////                String s = String.format("%d-%d-%d %d-%d-%d", year, monthOfYear, dayOfMonth,
////                        yearEnd, monthOfYearEnd, dayOfMonthEnd);
////                LogUtil.i("selected date:"+s);
////            }
////        });
////        Util.showTimeRangeSelectDialog(getActivity(), "8:30", "17:30", "timerangepicker", new TimePickerDialog.OnTimeSetListener() {
////            @Override
////            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
////                String s = String.format("%d:%d-%d:%d", hourOfDay, minute, hourOfDayEnd, minuteEnd);
////                LogUtil.i("selected time range is:"+s);
////            }
////        });
////        Util.showDateRangeSelectDialog(getActivity(), "2017-06-07", "2017-07-24", "daterangetag", new com.borax12.materialdaterangepicker.date.DatePickerDialog.OnDateSetListener() {
////            @Override
////            public void onDateSet(com.borax12.materialdaterangepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
////                String s = String.format("%d-%d-%d %d-%d-%d", year, monthOfYear, dayOfMonth,
////                        yearEnd, monthOfYearEnd, dayOfMonthEnd);
////                LogUtil.i("selected date range is:"+s);
////            }
////        });
////        Util.showTimeSelectDialog(getActivity(), "", "time_select_tag", new TimePickerDialog.OnTimeSetListener() {
////            @Override
////            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
////                String time = Util.getCanonicalHourMinuteString(hourOfDay, minute);
////                LogUtil.i("time:"+time);
////            }
////
//////            @Override
//////            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
//////                String startTime = Util.getCanonicalHourMinuteString(hourOfDay, minute);
//////                String endTime = Util.getCanonicalHourMinuteString(hourOfDayEnd, minuteEnd);
//////                LogUtil.i("startTime/endTime:"+startTime+"/"+endTime);
//////            }
////        });
//
//        String initial = indexChart1Time.getText().toString();
//        Util.showDateSelectDialog(getActivity(), initial, "index_fragment_chart1_time", new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                String s = Util.getCanonicalYearMonthDayString(year, monthOfYear + 1, dayOfMonth);
//                indexChart1Time.setText(s);
//            }
//        });
//    }
//
//    //    @OnClick(R.id.index_chart2_time)
//    public void onSelectChart2Time() {
//        String initial = indexChart2Time.getText().toString();
//        Util.showDateSelectDialog(getActivity(), initial, "index_fragment_chart2_time", new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                String s = Util.getCanonicalYearMonthDayString(year, monthOfYear + 1, dayOfMonth);
//                indexChart2Time.setText(s);
//            }
//        });
//
//    }
//
//
//    //    @OnClick(R.id.index_area_linear_layout)
//    public void selectArea() {
//        //LogUtil.i("select area is clicked");
//
//        showAreaSelectDialog();
//
////        Handler handler = new Handler();
////        handler.postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                Intent intent = new Intent(getActivity(), ChatActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                intent.putExtra(Constants.MESSAGE_FROM_USER_ID_KEY, "1");
////                intent.putExtra(Constants.MESSAGE_TO_USER_ID_KEY, "2");
////                intent.putExtra(Constants.MESSAGE_CONTENT_KEY, "消息");
////                startActivity(intent);
////
//////                Util.bringApplicationToForegroundFromChatMessageNotificationClick(getActivity(), "1", "2", "消息");
////            }
////        }, 5000);
//
//
////        List<AreaBean> areaList = SimulateData.getSimulatedAreaList();
////        AreaAdapter adapter = new AreaAdapter(areaList);
////        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                AreaBean item = (AreaBean) adapter.getItem(position);
////                LogUtil.i("has selected item name:"+item.getName());
////            }
////        });
////        new MaterialDialog.Builder(this.getActivity())
////                .title(R.string.hint_select_area)
////                // second parameter is an optional layout manager. Must be a LinearLayoutManager or GridLayoutManager.
////                .adapter(adapter, new GridLayoutManager(getActivity(), 3))
////                .show();
//    }
//
//    private void showAreaSelectDialog() {
////        List<AreaBean> areaList = SimulateData.getSimulatedAreaList();
//        List<AreaBean> areaList = Identity.area;
//        Util.showAreaSelectMaterialDialog(getActivity(), areaList);
////        test();
//    }
//
//    public void test() {
//        List<String> list = Util.getStringListFromStringArray(new String[]{"AAA", "BBB", "CCC", "DDD", "EEE",
//                "FFF", "GGG", "HHH", "III", "JJJ", "KKK", "MMM", "NNNN"});
//        int[] selectedIndexArray = new int[]{0, 5, 6};
//
//
//        List<IconTextBackgroundColorBean> beanList = new ArrayList<>();
//        for (int i = 0; i < 10; ++i) {
//            IconTextBackgroundColorBean bean = new IconTextBackgroundColorBean(R.mipmap.icon_14, "text" + i,
//                    Color.WHITE);
//            beanList.add(bean);
//        }
//        Util.showIconTextListMaterialDialog(getActivity(), beanList, R.string.activity_title_treatment, new MaterialSimpleListAdapter.Callback() {
//            @Override
//            public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
//                ToastUtil.show(getActivity(), "您已经选择了" + item.getContent());
//                dialog.dismiss();
//            }
//        });
//
////        Util.showFolderSelectMaterialDialog((WorkbenchActivity)this.getActivity(), "", "folder_select_tag");
////        Util.showFileSelectMaterialDialog((WorkbenchActivity)this.getActivity(), "/sdcard/51job", Constants.FILE_TYPE_IMAGE,
////                "file_select_tag");
////        Util.showColorSelectMaterialDialog((WorkbenchActivity)this.getActivity(), R.string.color_preset_first_level, R.string.color_preset_second_level);
////        Util.showIndeterminateHorizontalMaterialDialog(getActivity(), "title", "msg");
////        Util.showIndeterminateCircularMaterialDialog(getActivity(), "title", "msg");
//
////        List<AreaBean> areaList = SimulateData.getSimulatedAreaList();
////        AreaAdapter adapter = new AreaAdapter(areaList);
////        final MaterialDialog dialog = Util.showGridRecyclerViewMaterialDialog(getActivity(), "hint", adapter);
////        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                AreaBean item = (AreaBean) adapter.getItem(position);
////                RxBus.getDefault().post(Constants.EVENT_TYPE_DID_SELECT_AREA, item);
////                dialog.dismiss();
////            }
////        });
//
//
////        BaseQuickAdapter<TreatmentListBean.TreatmentBean, BaseViewHolder> mAdapter = new TreatmentAdapter(new ArrayList<TreatmentListBean.TreatmentBean>());
////        EasyLoadMoreView easyLoadMoreView = new EasyLoadMoreView();
////        mAdapter.setLoadMoreView(easyLoadMoreView);
////        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
////        final MaterialDialog dialog = Util.showLinearRecyclerViewMaterialDialog(getActivity(), "提示", mAdapter);
////        ((TreatmentAdapter) mAdapter).setOnItemClickListener(new TreatmentAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                Util.showAlert(getActivity(), "hint", "you selected position "+position);
////                dialog.dismiss();
////            }
////        });
////        RecyclerView recyclerView = dialog.getRecyclerView();
////        TreatmentListBean bean = SimulateData.getSimulatedTreatmentListBean();
////        List<TreatmentListBean.TreatmentBean> treatmentList = bean.getTreatmentList();
////        mAdapter.setNewData(treatmentList);
//
////        Util.showEditTextMaterialDialog(getActivity(), "提示", "请输入字符", "please input", "23", false, 2, 8, Constants.EDIT_INPUT_TYPE_PHONE, new MaterialDialog.InputCallback() {
////            @Override
////            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
////                ToastUtil.show(getActivity(), "you have input "+input);
////            }
////        });
////        MaterialDialog dialog = Util.showCustomViewMaterialDialog(getActivity(), "提示", "请选择", R.layout.fragment_mine, false
////                , new MaterialDialog.SingleButtonCallback() {
////                    @Override
////                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
////                        ToastUtil.show(getActivity(), "you clicked confirm button");
////                    }
////                });
//
////        Util.showMultipleChoiceStringListMaterialDialog(getActivity(), "提示", "请选择", list, selectedIndexArray,
////                new MaterialDialog.ListCallbackMultiChoice() {
////                    @Override
////                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
////                        String s = Util.join(which, '-');
////                        ToastUtil.show(getActivity(), "you have selected "+s);
////                        return false;
////                    }
////                });
////        Util.showSingleChoiceStringListMaterialDialog(getActivity(), "提示", "请选择一项", list, 1, new MaterialDialog.ListCallbackSingleChoice() {
////            @Override
////            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
////                ToastUtil.show(getActivity(), "选择了索引"+which);
////                return true;
////            }
////        });
////        Util.showStringListMaterialDialog(getActivity(), "提示", "请选择一项", list, new MaterialDialog.ListCallback() {
////            @Override
////            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
////                ToastUtil.show(getActivity(), "您选择了第"+position+"项，数据为:"+text);
////            }
////        });
//    }


//    private void startNBADetailActivity(String id, String imgUrl, View view) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), NBAActivity.class);
//        intent.putExtra("id", id);
//        intent.putExtra("url", imgUrl);
//        /**
//         * 用这个ActivityOptionsCompat比用ActivityOptions兼容性更好，前者是V4下的兼容到16后者到21.
//         * ActivityOptionsCompat.makeSceneTransitionAnimation(）的第三个参数则是跳转后图片显示的transitionName的值
//         *     <android.support.design.widget.AppBarLayout
//         android:transitionName="zhihu_detail_title"
//         android:fitsSystemWindows="true">
//         */
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                view, getActivity().getResources().getString(R.string.zhihu_detail_title));
//        getActivity().startActivity(intent, options.toBundle());
//    }

    public void registerAreaSelectEvent() {
//        Subscription mSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_DID_SELECT_AREA, AreaBean.class)
//                .subscribe(new Action1<AreaBean>() {
//                    @Override
//                    public void call(AreaBean ab) {
////                        mPresenter.fetchWXHotSearch(20, 1, s);
//                        Identity.setCurrentArea(ab);
//                        indexAreaName.setText(ab.getName());
//                    }
//                });
//        if (this.areaSelectSubscription == null) {
//            areaSelectSubscription = new CompositeSubscription();
//        }
//        areaSelectSubscription.add(mSubscription);
    }

    public void registerRxBusEvent() {
        Subscription mSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_INDEX_REFRESH_INTELLIGENT_HONOUR_AGREEMENT, Boolean.class)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean b) {
                        loadPolicyList();
//                        gotoFunction(functionImageBean);
//                Util.showAlert(getActivity(), "提示", "您选择了功能"+functionImageBean.getId());
                    }
                });
        if (this.mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(mSubscription);

        Subscription mFunctionImageSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_DID_SELECT_FUNCTION_IMAGE,
                FunctionImageBean.class)
                .subscribe(new Action1<FunctionImageBean>() {
                    @Override
                    public void call(FunctionImageBean functionImageBean) {
//                        Util.showAlert(getActivity(), "提示", "您选择了功能["+functionImageBean.getTitle()+"]");
                        gotoFunction(functionImageBean);
                    }
                });
        mCompositeSubscription.add(mFunctionImageSubscription);
    }

    private void unregisterRxBusEvent() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void gotoFunction(FunctionImageBean bean) {
        String id = bean.getId();
        int intId = Util.getIntFromString(id);


        String sAreaLeavel=Util.choicArea();
        if(sAreaLeavel.equals("area_1") ){
            if (Constants.FUNCTION_1 == intId) {
                Util.gotoActivity(getActivity(), BuildingRoomActivity.class);
            } else if (Constants.FUNCTION_2 == intId) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SocialHistoryActivity.class);
                intent.putExtra("pageType","2");
                startActivity(intent);
            } else if (Constants.FUNCTION_3 == intId) {
              //  Util.gotoActivity(getActivity(), MapServiceActivity.class);
            } else if (Constants.FUNCTION_4 == intId) {
                Util.gotoActivity(getActivity(), PersonCollectionActivity.class);
            } else if (Constants.FUNCTION_5 == intId) {
                Util.setReception("JgTypSlzx");
                Util.gotoActivity(getActivity(), ReceptionActivity.class);
            } else if (Constants.FUNCTION_6 == intId) {
                Util.setReception("JgTypJzz");
                Util.gotoActivity(getActivity(), ReceptionActivity.class);
              //  Toast.makeText(getActivity(), "即将上线,敬请期待!", Toast.LENGTH_SHORT).show();
              //  Util.gotoActivity(getActivity(), SocialAssestansActivity.class);
            } else if (Constants.FUNCTION_7 == intId) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.FROM_WHERE_ACTIVITY_KEY,  "BSZN");
                Util.gotoActivityWithBundle(getActivity(), ListGuideActivity.class, bundle);

               /* Intent intent = new Intent(getActivity().getApplicationContext(), HandleProgress.class);
                intent.putExtra("pageType","1");
                startActivity(intent);*/
            } else if (Constants.FUNCTION_8 == intId) {
                Toast.makeText(getActivity(), "即将上线,敬请期待!", Toast.LENGTH_SHORT).show();
                /*application.setTableType("2");
                Util.gotoActivity(getContext(), HouseholdSurveysActivity.class);*/
            }
        }else if(sAreaLeavel.equals("area_3")|| sAreaLeavel.equals("area_2") ){
            if (Constants.FUNCTION_1 == intId) {

                Util.setReception("JgTypSlzx");
                Util.gotoActivity(getActivity(), ReceptionActivity.class);
            } else if (Constants.FUNCTION_2 == intId) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.GUIDE_ITEM_ACTIVITY_KEY,  Identity.srcInfo.getAreaId());
                Util.gotoActivity(getActivity(), GuideActivity.class);
            } else if (Constants.FUNCTION_3 == intId) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.GUIDE_ITEM_ACTIVITY_KEY,  Identity.srcInfo.getAreaId());
                Util.gotoActivityWithBundle(getActivity(), GuideActivity.class, bundle);
            } else if (Constants.FUNCTION_4 == intId) {
                Util.gotoActivity(getActivity(), PersonCollectionActivity.class);
            } else if (Constants.FUNCTION_5 == intId) {
                Util.gotoActivity(getActivity(), SignServiceAssessActivity.class);
            } else if (Constants.FUNCTION_6 == intId) {
                RxBus.getDefault().post(Constants.EVENT_TYPE_WOULD_SCROLL_TO_WORKBENCH_INDEX, Constants.WORKBENCH_TRANSACT);

                /*RxBus.getDefault().post(intId, Constants);*/
               /* Intent intent = new Intent(getActivity().getApplicationContext(), UrbanLowInsuranceActivity.class);

                intent.putExtra("ItemId", "ITEM_DIBAO");
                intent.putExtra("titils", "城乡低保");

                startActivity(intent);*/
            } else if (Constants.FUNCTION_7 == intId) {
                Intent intent = new Intent(getActivity().getApplicationContext(), HandleProgress.class);
                intent.putExtra("pageType","1");
                startActivity(intent);
            } else if (Constants.FUNCTION_8 == intId) {
                RxBus.getDefault().post(Constants.EVENT_TYPE_WOULD_SCROLL_TO_WORKBENCH_INDEX, Constants.WORKBENCH_TRANSACT);

               /* application.setTableType("2");
                Util.gotoActivity(getContext(), HouseholdSurveysActivity.class);*/
            }
        }else {
            if (Constants.FUNCTION_1 == intId) {
                Util.gotoActivity(getActivity(), BuildingRoomActivity.class);
            } else if (Constants.FUNCTION_2 == intId) {
                Intent intent = new Intent(getActivity().getApplicationContext(), HandleProgress.class);
                intent.putExtra("pageType","2");
                startActivity(intent);
            } else if (Constants.FUNCTION_3 == intId) {
              //  Util.gotoActivity(getActivity(), MapServiceActivity.class);
            } else if (Constants.FUNCTION_4 == intId) {
                Util.gotoActivity(getActivity(), PersonCollectionActivity.class);
            } else if (Constants.FUNCTION_5 == intId) {
                Util.setReception("JgTypSlzx");
                Util.gotoActivity(getActivity(), ReceptionActivity.class);
            } else if (Constants.FUNCTION_6 == intId) {
                Toast.makeText(getActivity(), "即将上线,敬请期待!", Toast.LENGTH_SHORT).show();
                //  Util.gotoActivity(getActivity(), SocialAssestansActivity.class);
            } else if (Constants.FUNCTION_7 == intId) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.GUIDE_ITEM_ACTIVITY_KEY,  Identity.srcInfo.getAreaId());
                    Util.gotoActivity(getActivity(), GuideActivity.class);
               /* Intent intent = new Intent(getActivity().getApplicationContext(), HandleProgress.class);
                intent.putExtra("pageType","1");
                startActivity(intent);*/
            } else if (Constants.FUNCTION_8 == intId) {
                Toast.makeText(getActivity(), "即将上线,敬请期待!", Toast.LENGTH_SHORT).show();
                /*application.setTableType("2");
                Util.gotoActivity(getContext(), HouseholdSurveysActivity.class);*/
            }
        }


    }


//    @Override
//    public void refreshView(ChartDataBean mData) {
//        LogUtil.i("IndexDeanFragment refreshView");
////        List<NBAListBean.NBABean> nbaBeenList = mData.getT1348649145984();
////        if (isRefresh) {
////            srlAndroid.setRefreshing(false);
////            mAdapter.setEnableLoadMore(true);
////            isRefresh = false;
////            mAdapter.setNewData(nbaBeenList);
////        } else {
////            srlAndroid.setEnabled(true);
////            index += 20;
////            mAdapter.addData(nbaBeenList);
////            mAdapter.loadMoreComplete();
////        }
//
//    }


    //chart
    /*
    当json字符串生成时候，再调用loadUrl("javascript:refresh('" + getJsData() + "')"); 传递给网页，这样网页就能刷新表格了。

     */
//    public void initWebView1() {
////        chart1WebView = (WebView) ((Activity)getActivity()).findViewById(R.id.chart1_web_view);
//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        chart1WebView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (!chart1WebView.getSettings().getLoadsImagesAutomatically()) {
//                    chart1WebView.getSettings().setLoadsImagesAutomatically(true);
//                }
//            }
//        });
//        // 启用支持javascript
//        WebSettings settings = chart1WebView.getSettings();
//        settings.setJavaScriptEnabled(true);
////        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
////        settings.setDomStorageEnabled(true);
////        settings.setAppCacheMaxSize(8 * 1024 * 1024);
////        settings.setAllowFileAccess(true);
////        settings.setAppCacheEnabled(true);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
////            chart1WebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
////        }
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            settings.setLoadsImagesAutomatically(true);
////        } else {
////            settings.setLoadsImagesAutomatically(false);
////        }
//
//        chart1WebView.addJavascriptInterface(new JsToJava(), "nursing");
//
//        // 加载网页文件
//        chart1LoadUrl("file:///android_asset/chart1.html");
////        chart1LoadUrl("javascript:refresh('" + getJsData1() + "')");
//
//    }
//
//    public void chart1LoadUrl(final String url) {
//        ((Activity) getActivity()).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                chart1WebView.loadUrl(url);
//            }
//        });
//    }

    public String getJsData1() {
        JsData data = new JsData();
        data.categories = new String[]{"春", "夏", "秋", "冬"};
        data.series = new YData[2];
        Random random = new Random();
        for (int i = 0; i < data.series.length; i++) {
            data.series[i] = new YData();
            data.series[i].name = "Name" + (i + 1);
            int j = i;
            data.series[i].data = new int[]{100 * j + random.nextInt(100), 100 * j + random.nextInt(100), 100 * j + random.nextInt(100), 100 * j + random.nextInt(100)};
        }
        String json = new Gson().toJson(data);
        //LogUtil.i("json=" + json);
        return json;
    }

//    public void initWebView2() {
////        chart1WebView = (WebView) ((Activity)getActivity()).findViewById(R.id.chart1_web_view);
//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        chart2WebView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (!chart2WebView.getSettings().getLoadsImagesAutomatically()) {
//                    chart2WebView.getSettings().setLoadsImagesAutomatically(true);
//                }
//            }
//        });
//        // 启用支持javascript
//        WebSettings settings = chart2WebView.getSettings();
//        settings.setJavaScriptEnabled(true);
////        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
////        settings.setDomStorageEnabled(true);
////        settings.setAppCacheMaxSize(8 * 1024 * 1024);
////        settings.setAllowFileAccess(true);
////        settings.setAppCacheEnabled(true);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
////            chart1WebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
////        }
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            settings.setLoadsImagesAutomatically(true);
////        } else {
////            settings.setLoadsImagesAutomatically(false);
////        }
//
//        chart2WebView.addJavascriptInterface(new JsToJava(), "nursing");
//
//        // 加载网页文件
//        chart2LoadUrl("file:///android_asset/chart2.html");
////        chart1LoadUrl("javascript:refresh('" + getJsData1() + "')");
//
//    }
//
//    public void chart2LoadUrl(final String url) {
//        ((Activity) getActivity()).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                chart2WebView.loadUrl(url);
//            }
//        });
//    }

    public String getJsData2() {
        JsData data = new JsData();
        data.categories = new String[]{"东", "南", "西", "北"};
        data.series = new YData[2];
        Random random = new Random();
        for (int i = 0; i < data.series.length; i++) {
            data.series[i] = new YData();
            data.series[i].name = "Name" + (i + 1);
            int j = i;
            data.series[i].data = new int[]{100 * j + random.nextInt(100), 100 * j + random.nextInt(100), 100 * j + random.nextInt(100), 100 * j + random.nextInt(100)};
        }
        String json = new Gson().toJson(data);
        //LogUtil.i("json=" + json);
        return json;
    }

    public void loadChart() {
//        chart1LoadUrl("javascript:refresh('" + getJsData1() + "')");
//        chart2LoadUrl("javascript:refresh('" + getJsData2() + "')");

    }

    /**
     * 人脸识别
     *
     * @param context     上下文
     * @param cardNo      身份证号
     * @param IsMothercard  判断是否是监护人，1代表是
     * @param requestCode 跳转请求码code
     */
    public static void gotoVerify(Context context, String sPersonID, String cardNo, String IsMothercard, int requestCode) {
        SharedPreferences userSettings =context.getSharedPreferences("setting", 0);
        String NoBrushAgeRange=   userSettings.getString("NoBrushAgeRange","0-18,65-2000");
//        String isOnlyface= IsOnlyFace(cardNo,IsMothercard,NoBrushAgeRange);
//
//        // 配置服务器地址========================
////        //人脸匹配基地址
//        com.jqsoft.livebody_verify_lib.bean.Version.setBaseUrl(BphsConstants.Http_base_url);
//
//
//        //        //判断是否使用公司还是腾讯的身份证识别     属性要是用set 后面直接就get  不要用类名加 .
//        com.jqsoft.livebody_verify_lib.bean.Version.setsReadCard_Value(BphsConstants.sReadCardType_VALUE);
//
////        //通过人脸照片和身份证照片比对来添加人脸档案
//        com.jqsoft.livebody_verify_lib.bean.Version.setCreateFeatureUrl("create_feature");
////        //人脸比对
//        com.jqsoft.livebody_verify_lib.bean.Version.setCompareUrl("face_compare");
//        //推送活体验证成功后的居民信息给各个公卫系统的基地址
//        com.jqsoft.livebody_verify_lib.bean.Version.setPushFaceDataBaseUrl(BphsConstants.Http_Face_erd);
//        //推送活体验证成功后的居民信息给各个公卫系统的具体地址
//        Version.setPushFaceDataConcrete("face/faceToNet");
//        //==================================
//        UserLoginInfo info = UserLoginInfo.getInstances();
        Intent intent = new Intent(context, HeadCollectActivity.class);
//        //身份证号码
//        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_NUMBER_KEY, cardNo);
//        //是否是监护人，1代表是
//        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ISMOTHER_KEY,isOnlyface);
//        //sPersonID
//        intent.putExtra(CardImageLiveFaceVerifyActivity.SPERSON_ID,sPersonID);
//        //组织机构编码
//        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ORG_CODE_KEY, info.getsOrganizationKey());
//        //用户姓名
//        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_SUBMIT_USER_KEY, info.getsLoginName());
//        intent.putExtra(CardImageLiveFaceVerifyActivity.SUERSENAME,info.getsUserName());
        FragmentActivity activity = (FragmentActivity) context;
        if (!activity.isDestroyed() && !activity.isFinishing()) {  // 防止Activity被销毁
            activity.startActivityForResult(intent, requestCode);
        }
    }
//    private class JsToJava {
//        @JavascriptInterface
//        public void jqueryHasBeenInitialized(boolean jqueryLoadComplete) {
//            //LogUtil.i("jqueryLoadComplete:" + jqueryLoadComplete);
//            loadChart();
//
//        }
//    }

}
