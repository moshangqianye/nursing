package com.jqsoft.nursing.di.ui.activity.nursing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.nursing.ElderListAdapter;
import com.jqsoft.nursing.adapter.nursing.NursingListAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.ElderDetailBean;
import com.jqsoft.nursing.bean.nursing.NursingBean;
import com.jqsoft.nursing.bean.nursing.NursingTaskBean;
import com.jqsoft.nursing.bean.nursing.RoundDataBean;
import com.jqsoft.nursing.bean.nursing.RoundRoomDetailBean;
import com.jqsoft.nursing.bean.nursing.SaveRoundDataBean;
import com.jqsoft.nursing.di.contract.nursing.NursingDetailActivityContract;
import com.jqsoft.nursing.di.contract.nursing.RoundRoomDetailActivityContract;
import com.jqsoft.nursing.di.module.nursing.NursingDetailActivityModule;
import com.jqsoft.nursing.di.module.nursing.RoundDetailActivityModule;
import com.jqsoft.nursing.di.presenter.nursing.NursingDetailActivityPresenter;
import com.jqsoft.nursing.di.presenter.nursing.RoundDetailActivityPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di.ui.fragment.nursing.NursingDetailTasksFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils3.util.ListUtils;


import org.litepal.LitePal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 护理详情页面
 * Created by Administrator on 2018-04-08.
 */

public class RoundRoomDetailActivity extends AbstractActivity implements RoundRoomDetailActivityContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tl_tab_layout)
    TextView tlTabLayout;

    @BindView(R.id.tv_room_no)
    TextView tv_room_no;

    @BindView(R.id.tv_room_type)
    TextView tv_room_type;

    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.tv_room_select)
    AppCompatTextView tv_room_select;

    private String roomid,roomNo,roomType,roomExtra;

    private List<ElderDetailBean> list = new ArrayList<>();

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

    private ElderListAdapter nursingListAdapter;
    List<ElderDetailBean> nursingList=new ArrayList<>();

    private List<RoundDataBean> roundDataBeen =new ArrayList<>();
    private List<RoundDataBean> elderDataBeen=new ArrayList<>();
    private List<RoundDataBean> allDataBeen=new ArrayList<>();
    public boolean hasAuthorizedByNfc = false;

    @Inject
    RoundDetailActivityPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_round_room_detail;
    }

    @Override
    protected void initData() {
        roomid=getDeliveredStringByKey("roomid");
        roomNo=getDeliveredStringByKey("roomNo");
        roomType=getDeliveredStringByKey("roomType");
        roomExtra=getDeliveredStringByKey("roomExtra");
        hasAuthorizedByNfc = getDeliveredBooleanByKey(Constants.NURSING_IS_FROM_NFC_KEY);

        tv_room_no.setText(roomNo);
        tv_room_type.setText(roomType);


        LitePal.deleteAll(SaveRoundDataBean.class);
    }

    @Override
    protected void initView() {
        setToolBar(toolbar, Constants.EMPTY_STRING);


        loadInfo();



        srlNursingList = (SwipeRefreshLayout) layContentNursingList;
        rvNursingList= (RecyclerView) layContentNursingList.findViewById(R.id.recyclerview);

        srlNursingList.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        srlNursingList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                nursingListAdapter.setEnableLoadMore(false);
                loadInfo();

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
                        loadInfo();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        nursingListAdapter = new ElderListAdapter(nursingList,RoundRoomDetailActivity.this);
        nursingListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        nursingListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        }, rvNursingList);
        nursingListAdapter.setEnableLoadMore(false);
//        rvNursingList.setNestedScrollingEnabled(false);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        rvNursingList.setLayoutManager(new FullyLinearLayoutManagerSmoothScroll(getActivity()));
//        NoScrollFullyLinearLayoutManager policyManager = new NoScrollFullyLinearLayoutManager(getActivity());
//        policyManager.setScrollEnabled(false);
//        rvNursingList.setLayoutManager(policyManager);
        rvNursingList.setLayoutManager(new FullyLinearLayoutManager(RoundRoomDetailActivity.this));
//        rvNursingList.setNestedScrollingEnabled(false);
//        Util.addDividerToRecyclerView(getActivity(), rvNursingList, true);
        Util.addHorizontalDividerToRecyclerView(RoundRoomDetailActivity.this, rvNursingList);
        rvNursingList.setAdapter(nursingListAdapter);
        nursingListAdapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);
             /*   NursingBean nb = (NursingBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.NURSING_ELDER_ID_KEY, nb.getElderInfoID());
                Util.gotoActivityWithBundle(RoundRoomDetailActivity.this, NursingDetailActivity.class, bundle);*/

            }
        });

        tv_room_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRoundItem();
            }
        });



        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if(hasAuthorizedByNfc){

                    List<SaveRoundDataBean>  qusId1 = LitePal.findAll(SaveRoundDataBean.class);
                    List<String> containList =new ArrayList<String>();
                    List<String> containList1 =new ArrayList<String>();
                    List<String> containelderList1 =new ArrayList<String>();
                    containList.clear();
                    containList1.clear();
                    containelderList1.clear();
                    for(int i=0;i<roundDataBeen.size();i++){
                            if(roundDataBeen.get(i).getIsFillout().equals("1")){
                                containList.add(roundDataBeen.get(i).getWardRoundID());
                            }

                    }

                    for(int j=0;j<list.size();j++){
                        containelderList1.add(list.get(j).getElderInfoID());
                        for(int i=0;i<elderDataBeen.size();i++){
                            if(elderDataBeen.get(i).getIsFillout().equals("1")){
                                containList.add(elderDataBeen.get(i).getWardRoundID()+"|"+list.get(j).getElderInfoID());
                            }

                        }
                    }



                    for(int i=0;i<qusId1.size();i++){
                        if(qusId1.get(i).getIsFillout().equals("1")){
                            if(TextUtils.isEmpty(qusId1.get(i).getfKElderInfoID())){
                                containList1.add(qusId1.get(i).getWardItemID());
                            }else {
                                containList1.add(qusId1.get(i).getWardItemID()+"|"+qusId1.get(i).getfKElderInfoID());
                            }


                        }
                    }
                    if(qusId1.size()==0){
                        Toast.makeText(getApplicationContext(),"请至少选择一个查房项目!",Toast.LENGTH_SHORT).show();
                    }else {

                        Map<String, String> map = getEndNursingTaskRequestMap(qusId1);
                        mPresenter.endNursingTask(map);

//                        if(containList.size()==containList1.size()){
//
//                            Map<String, String> map = getEndNursingTaskRequestMap(qusId1);
//                            mPresenter.endNursingTask(map);
//
//                        }else {
//
//                            containList.removeAll(containList1);
//
//                            String s1 = "";
//                            String s2 = "";
//                            for(int i=0;i<roundDataBeen.size();i++){
//                                if(roundDataBeen.get(i).getWardRoundID().equals(containList.get(0))){
//                                    s1 =roundDataBeen.get(i).getWardRoundType();
//                                }
//                            }
//
//                            for(int j=0;j<list.size();j++){
//                                if(containList.get(0).contains(list.get(j).getElderInfoID())){
//                                    s2 =list.get(j).getElderName();
//                                }
//                            }
//
//                            if(!TextUtils.isEmpty(s1)){
//                                Toast.makeText(getApplicationContext(),s1+"房间必选项不能为空!",Toast.LENGTH_SHORT).show();
//                            }else if(!TextUtils.isEmpty(s2)){
//                                Toast.makeText(getApplicationContext(),s2+"老人的"+"必选项不能为空!",Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        }
//
//

                    }


//                }else {
//                    Toast.makeText(getApplicationContext(),"请刷NFC卡后再提交查房项目!",Toast.LENGTH_SHORT).show();
//
//                }

            }
        });
    }

    private Map<String, String> getEndNursingTaskRequestMap( List<SaveRoundDataBean> item){
        String userId = IdentityManager.getUserId(this);
       // String detailId = Util.trimString(item.getServicePlanDetailsID());
        Map<String, String> map = ParametersFactory.getEndRoundTaskMap(this, userId, item,roomid);
        return map;
    }

    public void loadInfo(){
        Map<String, String> map = getNursingListRequestMap();
        mPresenter.main(map);

    }

    private Map<String, String> getNursingListRequestMap(){
        String userId = IdentityManager.getUserId(this);
        Map<String, String> map = ParametersFactory.getRoundInfoListMap(this, userId, roomid);
        return map;
    }




    @Override
    public void onLoadListSuccess(HttpResultNurseBaseBean<RoundRoomDetailBean> bean) {
        List<ElderDetailBean> elderDetailBeen = bean.getData().getElders();
   //     roundDataBeen =bean.getData().getRoundDatas();
        allDataBeen.clear();
        allDataBeen =bean.getData().getRoundDatas();
        roundDataBeen.clear();
        elderDataBeen.clear();
        for(int i=0;i<allDataBeen.size();i++){
            //0是老人1是房间
            if(allDataBeen.get(i).getWardRoundTypeCode().equals("0")){
                elderDataBeen.add(allDataBeen.get(i));

            }else {
                roundDataBeen.add(allDataBeen.get(i));
            }
        }



        int listSize = getListSizeFromResult(elderDetailBeen);
        list.clear();
        list = getListFromResult(elderDetailBeen);


        srlNursingList.setEnabled(true);

        isRefresh = false;

        srlNursingList.setRefreshing(false);
        if (elderDetailBeen != null) {
            if (list != null) {
                if (!ListUtils.isEmpty(list)) {
                    nursingList.clear();
                    nursingList.addAll(list);
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

    }

    @Override
    public void onLoadListFailure(String message) {


        Util.showToast(this, Constants.HINT_LOADING_DATA_FAILURE);
    }

    @Override
    public void onEndNursingTaskSuccess(HttpResultNurseBaseBean<List<HttpResultEmptyBean>> bean) {
        LitePal.deleteAll(SaveRoundDataBean.class);
        if(bean.getMsg().equals("请求成功")){
            Toast.makeText(getApplicationContext(),"提交成功!",Toast.LENGTH_SHORT).show();
            hadCheck();
        }else {
            Toast.makeText(getApplicationContext(),bean.getMsg(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onEndNursingTaskFailure(String msg) {
        LitePal.deleteAll(SaveRoundDataBean.class);
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void initInject() {
        super.initInject();
        DaggerApplication.get(this)
                .getAppComponent()
                .addRoundDetailActivity(new RoundDetailActivityModule(this))
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public int getListSizeFromResult(List<ElderDetailBean> beanList) {
        if (beanList != null) {
            List<ElderDetailBean> list = beanList;
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

    public List<ElderDetailBean> getListFromResult(List<ElderDetailBean> beanList) {
        if (beanList != null) {
            List<ElderDetailBean> list = beanList;
            return list;
        } else {
            return null;
        }
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
        return getResources().getString(R.string.hint_no_round_list_info_please_click_to_reload);
    }

    private String getNursingListFailureHint() {
        return getResources().getString(R.string.hint_load_round_list_info_error_please_click_to_reload);
    }

    public void myElderItem(String fKElderInfoID){
        Intent intent = new Intent();
        intent.putExtra("roundDataBeen",(Serializable) elderDataBeen);
        intent.putExtra("title","老人检查项");
        intent.putExtra("fKElderInfoID",fKElderInfoID);
        intent.putExtra(Constants.NURSING_IS_FROM_NFC_KEY,hasAuthorizedByNfc);
        intent.setClass(getApplicationContext(),ElderItemActivity.class);
        startActivity(intent);
    }

    public void myRoundItem(){
        Intent intent = new Intent();
        intent.putExtra("roundDataBeen",(Serializable) roundDataBeen);
        intent.putExtra("title","房间检查项");
        intent.putExtra("fKElderInfoID","");
        intent.putExtra(Constants.NURSING_IS_FROM_NFC_KEY,hasAuthorizedByNfc);
        intent.setClass(getApplicationContext(),ElderItemActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hadCheck();
    }

    public void hadCheck(){
        List<SaveRoundDataBean>  qusId1 = LitePal.where("fKElderInfoID=?","").find(SaveRoundDataBean.class);
        if(qusId1.size()==0){
            tv_room_select.setText("检查项");
            tv_room_select.setTextColor(Color.parseColor("#3688ff"));
            tv_room_select.setBackgroundDrawable(getResources().getDrawable(R.drawable.positive_background_positive_round_corner_border));

        }else {
            tv_room_select.setText("已检查");
            tv_room_select.setTextColor(Color.parseColor("#019782"));
            tv_room_select.setBackgroundDrawable(getResources().getDrawable(R.drawable.positive_background_positive_round_corner_border_had));

        }

       // nursingListAdapter.
        nursingListAdapter.notifyDataSetChanged();//刷新

    }

}
