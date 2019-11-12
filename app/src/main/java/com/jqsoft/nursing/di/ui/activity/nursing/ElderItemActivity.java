package com.jqsoft.nursing.di.ui.activity.nursing;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jqsoft.nursing.R;


import com.jqsoft.nursing.adapter.nursing.MyPrimaryBaseAdapter;
import com.jqsoft.nursing.adapter.nursing.RoundListAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.ElderDetailBean;
import com.jqsoft.nursing.bean.nursing.RoundDataBean;
import com.jqsoft.nursing.bean.nursing.RoundRoomDetailBean;
import com.jqsoft.nursing.bean.nursing.SaveRoundDataBean;
import com.jqsoft.nursing.di.contract.nursing.RoundRoomDetailActivityContract;
import com.jqsoft.nursing.di.module.nursing.RoundDetailActivityModule;
import com.jqsoft.nursing.di.presenter.nursing.RoundDetailActivityPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils3.util.ListUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 护理详情页面
 * Created by Administrator on 2018-04-08.
 */

public class ElderItemActivity extends AbstractActivity implements RoundRoomDetailActivityContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private String roomid,roomNo,roomType;

    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.lay_content_nursing_list)
    View layContentNursingList;

    @BindView(R.id.id_lv)
    ListView id_lv;


    @BindView(R.id.nursing_detail_title)
    TextView nursing_detail_title;

    @BindView(R.id.lay_specific_nursing_load_failure)
    View nursingListFailureView;

    TextView tvNursingListFailureView;


   // SwipeRefreshLayout srlNursingList;
  //  RecyclerView rvNursingList;


    TextView tvFailureView;
    private boolean isRefresh = false;

    private int type;

    //private RoundListAdapter nursingListAdapter;
    List<RoundDataBean> nursingList=new ArrayList<>();

    private List<RoundDataBean> roundDataBeen;


    @Inject
    RoundDetailActivityPresenter mPresenter;

    private String title,myfKElderInfoID;
    public boolean hasAuthorizedByNfc ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_elder_item_detail;
    }

    @Override
    protected void initData() {
//        roomid=getDeliveredStringByKey("roomid");
        hasAuthorizedByNfc = getDeliveredBooleanByKey(Constants.NURSING_IS_FROM_NFC_KEY);
        roundDataBeen =(List<RoundDataBean>)this.getIntent().getSerializableExtra("roundDataBeen");
        title =(String)this.getIntent().getStringExtra("title");
        myfKElderInfoID =(String)this.getIntent().getStringExtra("fKElderInfoID");
        nursing_detail_title.setText(title);
    }

    @Override
    protected void initView() {
        setToolBar(toolbar, Constants.EMPTY_STRING);

        MyPrimaryBaseAdapter myBaseAdapter = new MyPrimaryBaseAdapter(roundDataBeen,ElderItemActivity.this,myfKElderInfoID);
        id_lv.setAdapter(myBaseAdapter);
      //  loadInfo();



       // srlNursingList = (SwipeRefreshLayout) layContentNursingList;
    //    rvNursingList= (RecyclerView) layContentNursingList.findViewById(R.id.recyclerview);

//        srlNursingList.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
//        srlNursingList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                nursingListAdapter.setEnableLoadMore(false);
//                loadInfo();
//
//            }
//        });

        tvNursingListFailureView = (TextView) nursingListFailureView.findViewById(R.id.tv_load_failure_hint);
        RxView.clicks(tvNursingListFailureView)
                .throttleFirst(Constants.RXBINDING_THROTTLE, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                       // nursingListAdapter.setEnableLoadMore(false);
                        loadInfo();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


     //   nursingListAdapter = new RoundListAdapter(roundDataBeen,0,ElderItemActivity.this);
//        nursingListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        nursingListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//
//            }
//        }, rvNursingList);
//        nursingListAdapter.setEnableLoadMore(false);
//        rvNursingList.setNestedScrollingEnabled(false);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        rvNursingList.setLayoutManager(new FullyLinearLayoutManagerSmoothScroll(getActivity()));
//        NoScrollFullyLinearLayoutManager policyManager = new NoScrollFullyLinearLayoutManager(getActivity());
//        policyManager.setScrollEnabled(false);
//        rvNursingList.setLayoutManager(policyManager);
     //   rvNursingList.setLayoutManager(new FullyLinearLayoutManager(ElderItemActivity.this));
//        rvNursingList.setNestedScrollingEnabled(false);
//        Util.addDividerToRecyclerView(getActivity(), rvNursingList, true);
      //  Util.addHorizontalDividerToRecyclerView(ElderItemActivity.this, rvNursingList);
      //  rvNursingList.setAdapter(nursingListAdapter);
//        nursingListAdapter.setOnItemClickListener(new NoDoubleItemClickListener() {
//            @Override
//            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                super.onNoDoubleItemClick(adapter, view, position);
//             /*   NursingBean nb = (NursingBean) adapter.getItem(position);
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.NURSING_ELDER_ID_KEY, nb.getElderInfoID());
//                Util.gotoActivityWithBundle(RoundRoomDetailActivity.this, NursingDetailActivity.class, bundle);*/
//
//            }
//        });

       // rvNursingList.setItemViewCacheSize(20000);
//        rvNursingList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            int mScrollThreshold;
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
//                if (isSignificantDelta) {
//                    if (dy > 0) {
//                      //  onScrollUp();
//                    } else {
//                       // onScrollDown();
//                    }
//                }
//            }
//            public void setScrollThreshold(int scrollThreshold) {
//                mScrollThreshold = scrollThreshold;
//            }
//        });


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(hasAuthorizedByNfc){

                    List<String> containList =new ArrayList<String>();
                    List<String> containList1 =new ArrayList<String>();
                    containList.clear();
                    containList1.clear();

                    if(TextUtils.isEmpty(myfKElderInfoID)){
                        for(int i=0;i<roundDataBeen.size();i++){
                            if(roundDataBeen.get(i).getWardRoundTypeCode().equals("1")){
                                if(roundDataBeen.get(i).getIsFillout().equals("1")){
                                    containList.add(roundDataBeen.get(i).getWardRoundID());

                                }
                            }
                        }
                    }else {
                        for(int i=0;i<roundDataBeen.size();i++){
                            if(roundDataBeen.get(i).getWardRoundTypeCode().equals("0")){
                                if(roundDataBeen.get(i).getIsFillout().equals("1")){
                                    containList.add(roundDataBeen.get(i).getWardRoundID());

                                }
                            }
                        }
                    }


                    removeDuplicate(qusId1);

                    List<SaveRoundDataBean> repeatList = new ArrayList<SaveRoundDataBean>();//用于存放重复的元素的list
                    for (int i = 0; i < qusId1.size() - 1; i++) {
                        for (int j = qusId1.size() - 1; j > i; j--) {
                            if (qusId1.get(j).getWardItemID().equals(qusId1.get(i).getWardItemID())) {
                                repeatList.add(qusId1.get(j));//把相同元素加入list(找出相同的)
                                qusId1.remove(j);//删除重复元素

                            }
                        }
                    }

                    for(int i = 0; i < qusId1.size() ; i++){
                        for(int j=0;j<repeatList.size();j++){
                            if(qusId1.get(i).getWardItemID().equals(repeatList.get(j).getWardItemID())){
                                qusId1.get(i).setWardOptionID(qusId1.get(i).getWardOptionID()+"|"+repeatList.get(j).getWardOptionID());
                                qusId1.get(i).setWardOptionName(qusId1.get(i).getWardOptionName()+"|"+repeatList.get(j).getWardOptionName());
                            }
                        }
                    }

                    for(int i=0;i<qusId1.size();i++){
                        if(qusId1.get(i).getIsFillout().equals("1")){
                            containList1.add(roundDataBeen.get(i).getWardRoundID());
                        }
                    }


                    if(containList.size()==containList1.size()){
                        if(qusId1.size()==0){
                            Toast.makeText(getApplicationContext(),"请至少选择一个检查项目!",Toast.LENGTH_SHORT).show();
                        }else {

                            if(TextUtils.isEmpty(myfKElderInfoID)){
                                LitePal.deleteAll(SaveRoundDataBean.class,"fKElderInfoID=?","");
                                LitePal.saveAll(qusId1);
                                qusId1.clear();
                                Toast.makeText(getApplicationContext(),"暂存成功!",Toast.LENGTH_SHORT).show();
                                //qusId1.clear();
                                finish();
                            }else {
                                LitePal.deleteAll(SaveRoundDataBean.class,"fKElderInfoID=?",myfKElderInfoID);
                                LitePal.saveAll(qusId1);
                                qusId1.clear();
                                Toast.makeText(getApplicationContext(),"暂存成功!",Toast.LENGTH_SHORT).show();
                                finish();
                            }


                        }


                    }else {


                        Toast.makeText(getApplicationContext(),"必选不能为空!",Toast.LENGTH_SHORT).show();

                    }

//                }else {
//                    Toast.makeText(getApplicationContext(),"请刷NFC卡后再保存查房项目!",Toast.LENGTH_SHORT).show();
//                }





            }
        });
    }

    public static void removeDuplicate(List<SaveRoundDataBean> list) {
//        LinkedHashSet<SaveRoundDataBean> set = new LinkedHashSet<SaveRoundDataBean>(list.size());
//        set.addAll(list);
//        list.clear();
//        list.addAll(set);
//        List<SaveRoundDataBean> personList = new ArrayList<>();

        Set set  =   new  HashSet();

        List newList  =   new  ArrayList();

        for  (Iterator iter = list.iterator(); iter.hasNext();)   {

            Object element  =  iter.next();

            if  (set.add(element))

                newList.add(element);

        }

        list.clear();

        list.addAll(newList);

        //System.out.println( " remove duplicate "   +  list);
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


    }

    @Override
    public void onLoadListFailure(String message) {


        Util.showToast(this, Constants.HINT_LOADING_DATA_FAILURE);
    }

    @Override
    public void onEndNursingTaskSuccess(HttpResultNurseBaseBean<List<HttpResultEmptyBean>> bean) {

    }

    @Override
    public void onEndNursingTaskFailure(String msg) {

    }


    /**
     * 下滑监听
     */
    private void onScrollDown() {
//下滑时要执行的代码
      //  nursingListAdapter.notifyDataSetChanged();
    }
    private void onScrollUp() {
//下滑时要执行的代码
       // nursingListAdapter.notifyDataSetChanged();
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void initInject() {
        super.initInject();
      /*  DaggerApplication.get(this)
                .getAppComponent()
                .addRoundDetailActivity(new RoundDetailActivityModule(this))
                .inject(this);*/
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

    List<SaveRoundDataBean> qusId1=new ArrayList<>();
    List<SaveRoundDataBean> PosQusList1=new ArrayList<>();
//    HashMap<String, String> mSingData = new HashMap<String, String>();
//    HashMap<String, List<String>> MuData = new HashMap<String, List<String>>();



    public void saveMUData(SaveRoundDataBean saveRoundDataBean) {

        qusId1.add(saveRoundDataBean);

        int ss=qusId1.size();
//        List<String> AnswerIdList= MuData.get(qusid);
//        if (AnswerIdList==null){
//            AnswerIdList=new ArrayList<>();
//        }
//        AnswerIdList.add(str);
//
//        MuData.put(qusid,AnswerIdList);
//        qusId.add(qusid);

    }
    public void removeMuData(SaveRoundDataBean saveRoundDataBean ){

      //  qusId1.remove(saveRoundDataBean);

        for (int i = qusId1.size()-1; i >=0; i--) {
            if (qusId1.get(i).getWardOptionID().equals(saveRoundDataBean.getWardOptionID())) {
                qusId1.remove(i);
            }
        }

        int ss=qusId1.size();

     //   qusId1.remove(saveRoundDataBean);
//        List<String> AnswerIdList= MuData.get(qusid);
//        if (str==null){  }else {
//            AnswerIdList.remove(str);}

    }
    public void removeSingleChooseData(String qusid,String str){
       // mSingData.remove(qusid);
    }

    public void saveData(String qusid, String str) {
//        Toast.makeText(this,str+"----"+qusid,Toast.LENGTH_LONG).show();
//        qusId.add(qusid);
//        mSingData.put(qusid,str);
    }




    List<RoundDataBean> qusId=new ArrayList<>();
    List<RoundDataBean> PosQusList=new ArrayList<>();
    HashMap<String, String> mSingData = new HashMap<String, String>();
    HashMap<String, List<String>> MuData = new HashMap<String, List<String>>();

    public void MixChooseData(){
        String qusStr=null;
        String ansStr=null;
        String DataStr=null;
        List<String> dataList=new ArrayList<>();

//        //去重复
        HashSet h = new HashSet(qusId);
        qusId.clear();
        qusId.addAll(h);
        if (qusId.equals(null)){

            Toast.makeText(this,"提交内容不能为空！",Toast.LENGTH_SHORT).show();
        }else {


            for (int i=0;i<qusId.size();i++){
                qusStr = qusId.get(i).getWardRoundID();

                if (foundSingleChooseList(qusStr)==null){

                    if (foundMUChooseList(qusStr)==null){
                    }else {
                        //是多选答案添加到答案list
//                  DataStr=qusStr+"_"+foundMUChooseList(qusStr);
                        RoundDataBean postQusBean=new RoundDataBean();

                    //    postQusBean.getWardRoundID(qusStr);
                     //   postQusBean.setQuestionsContentKey(foundMUChooseList(qusStr));
                        PosQusList.add(postQusBean);
                        String A ="";
//                  dataList.add(DataStr);

                    }

                } else {
                    //是单选答案添加到答案list
//              DataStr=qusStr+"_"+ foundSingleChooseList(qusStr);
                    RoundDataBean postQusBean=new RoundDataBean();
//              postQusBean.setQuestionsKey(qusStr);
//              postQusBean.setQuestionsContentKey((foundSingleChooseList(qusStr)));
                 //   postQusBean.setQuestionsKey(qusStr);
                  //  postQusBean.setQuestionsContentKey((foundSingleChooseList(qusStr)));
                    PosQusList.add(postQusBean);

                    String A ="";
//              dataList.add(DataStr);
                }




            }

        }}

    private String foundSingleChooseList(String qusid){
        if ( mSingData.get(qusid)==null){
            return null;
        }else {
            return   mSingData.get(qusid);
        }

    }

    private String foundMUChooseList(String qusid){
        if ( MuData.get(qusid)==null){
            return null;
        }else {
            List<String> AnswerIdList= MuData.get(qusid);
            HashSet h = new HashSet(AnswerIdList);
            AnswerIdList.clear();
            AnswerIdList.addAll(h);
            String ansStr=null;
            for (int i=0;i<AnswerIdList.size();i++){
                if (ansStr==null){
                    ansStr=AnswerIdList.get(i);
                }else {
                    ansStr=ansStr+"_"+AnswerIdList.get(i);
                }

            }
            return   ansStr;
        }

    }


}
