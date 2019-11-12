package com.jqsoft.nursing.di.ui.fragment.nursing;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.CategoryFloorAdapter;
import com.jqsoft.nursing.adapter.CategoryListAdapter;

import com.jqsoft.nursing.adapter.nursing.RoundRoomFragmentmacAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.Identity;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.BuildingRoomFloorBean;
import com.jqsoft.nursing.bean.FloorList;
import com.jqsoft.nursing.bean.RoomList;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.RoundsRecordCount;
import com.jqsoft.nursing.di.contract.SocialAssistanceObjectActivityContract;
import com.jqsoft.nursing.di.module.RoundRoomFramentModule;
import com.jqsoft.nursing.di.presenter.RoundRoomFragmentPresenter;
import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.di.ui.activity.nursing.RoundRoomDetailActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleClickListener;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.util.dialog.EvaluateDialog;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import bakerj.backgrounddarkpopupwindow.BackgroundDarkPopupWindow;
import butterknife.BindView;


/**查房**/

public class RoundFragmentNew extends AbstractFragment implements  SocialAssistanceObjectActivityContract.View,SwipeRefreshLayout.OnRefreshListener {

    @Inject
    RoundRoomFragmentPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_scan)
    TextView tvScanQrcode;

    @BindView (R.id.tv_room)
    TextView tv_room;

    @BindView (R.id.ly_search)
    LinearLayout ly_search;

    @BindView (R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.lay_online_consultation_load_failure)
    View failureView;



    private ArrayList<BuildingRoomFloorBean> buildingList = new ArrayList<>();
    private List<FloorList> floorLists = new ArrayList<>();
    private CategoryListAdapter adapter;
    private CategoryFloorAdapter floorAdapter;
    private TextView tvFailureView;

    @BindView(R.id.lay_content)
    SwipeRefreshLayout srl;

    private RoundRoomFragmentmacAdapter mAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private ArrayList<RoomList> listRecycle =new ArrayList<>();
    private int mypotion=0,saveBuilding=0,saveFloor=0;

    @BindView (R.id.tv_room_code)
    TextView tv_room_code;

    private List<RoundsRecordCount> RoundsRecordCountList = new ArrayList<>();
    private final int REQUEST_CODE=100;

    @Override
    protected void initInject() {
        DaggerApplication.get(getActivity())
                .getAppComponent()
                .addRoundRoomnewFragment(new RoundRoomFramentModule(this))
                .inject(this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        AppCompatActivity activity = (AppCompatActivity)getActivity();
//        activity.setSupportActionBar(toolbar);
//        toolbar.inflateMenu(R.menu.menu_scan_qrcode);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.menu_scan_qrcode, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId()==R.id.action_scan_qrcode){
//            gotoScanQrcode();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void gotoScanQrcode(){
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

//        Intent intent = new Intent(getActivity(), ZxingActivity.class);
//        startActivityForResult(intent, IntentConstant.REQUESTCODE_SCAN_QR_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            case IntentConstant.REQUESTCODE_SCAN_QR_CODE:
//                if (data!=null){
//                    String result = data.getStringExtra(IntentConstant.EXTRANAME_QR_CODE_TEXT);
//                    getQrcodeInfo(result);
////                    gotoDetailActivityByQrcode(result);
//                }
//                break;
            case REQUEST_CODE:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        Util.showToast(getActivity(), "扫描结果为空");
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        getQrcodeInfo(result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Util.showToast(getActivity(), "解析二维码失败");
                    } else {
                        Util.showToast(getActivity(), "解析二维码失败");
                    }
                } else {
                    Util.showToast(getActivity(), "扫描结果为空");
                }
                break;
        }
    }

    private void gotoDetailActivityByQrcode(String readResult){
        readResult=Util.trimString(readResult);
        Bundle bundle = new Bundle();
        String[] readRoomResult = readResult.split(",");
//            if (readRoomResult!=null && readRoomResult.length>=3) {
////                bundle.putString(Constants.NURSING_READ_RESULT_KEY, readResult);
//                bundle.putString(Constants.NURSING_BED_ID_KEY, readRoomResult[2]);
//                bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, isFromNfc);
//                Util.gotoActivityWithBundle(this, NursingDetailActivity.class, bundle);
//            }else if(readRoomResult){
//
//            }
        if (readRoomResult[0].equals("0")) {

            bundle.putString(Constants.NURSING_BED_ID_KEY, readRoomResult[2]);
            bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, true);
            Util.gotoActivityWithBundle(getActivity(), NursingDetailActivity.class, bundle);
        } else if (readRoomResult[0].equals("1")) {
            bundle.putString("roomid", readRoomResult[1]);
            bundle.putString("roomNo", readRoomResult[2]);
            bundle.putString("roomType", readRoomResult[3]);
            bundle.putString("roomExtra", "");
            bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, true);

            Util.gotoActivityWithBundle(getActivity(), RoundRoomDetailActivity.class, bundle);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_round_new;
    }


    @Override
    protected void initData() {
        Map<String, String> map = getRequestMap();
        mPresenter.getSocialDatas(map, true);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }

    private void getQrcodeInfo(String qrcode){
        Map<String, String> map = getQrcodeRequestMap(qrcode);
        mPresenter.getQrcodeInfo(map, false);

    }

    @Override
    protected void initView() {

        Util.setViewClickListener(tvScanQrcode, new Runnable() {
            @Override
            public void run() {
                gotoScanQrcode();
            }
        });

        tv_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(buildingList.size()==0){

                }else {
                    showPopupWindow(ly_search.getWidth(), ly_search.getHeight());
                }

            }
        });


        srl.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        srl.setOnRefreshListener(this);


        mAdapter = new RoundRoomFragmentmacAdapter(new ArrayList<RoomList>(), getActivity(),RoundsRecordCountList);
        this.mAdapter = (RoundRoomFragmentmacAdapter) mAdapter;
      //  mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //  mAdapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);

//                Map<String, String> map = getmacAddressMap();
//                mPresenter.getmacInfo(map, true);
                RoomList roomList = (RoomList) adapter.getItem(position);
                final String roomid=roomList.getRoom().getRoomID();
                final String roomNo=roomList.getRoom().getRoomNO();
//                EvaluateDialog
//                final EvaluateDialog evaluateDialog = new EvaluateDialog(getActivity(), R.style.EvaluateDialog);
//                evaluateDialog.setOnCancelListener(new EvaluateDialog.OnCancelListener() {
//                    @Override
//                    public void onCancel(View v) {
//                        Toast.makeText(getActivity(),"test3",Toast.LENGTH_LONG).show();
//                        Map<String, String> map = getmacAddressMap(roomid,"3");
//                        mPresenter.getmacInfo(map, true);
//                    }
//                });
//                evaluateDialog.setOnStoreListener(new EvaluateDialog.OnStoreListener() {
//                    @Override
//                    public void onStore(View v) {
//
//                        Toast.makeText(getActivity(),"test1",Toast.LENGTH_LONG).show();
//                        Map<String, String> map = getmacAddressMap(roomid,"1");
//                mPresenter.getmacInfo(map, true);
//                    }
//                });
//                evaluateDialog.setOnFeedbackListener(new EvaluateDialog.OnFeedbackListener() {
//                    @Override
//                    public void onFeedback(View v) {
//                        Toast.makeText(getActivity(),"test2",Toast.LENGTH_LONG).show();
//
//                        Map<String, String> map = getmacAddressMap(roomid,"2");
//                        mPresenter.getmacInfo(map, true);
//                    }
//                });
//                evaluateDialog.setOnsibackListener(new EvaluateDialog.OnsiListener() {
//
//                    @Override
//                    public void onsi(View v) {
//                        Toast.makeText(getActivity(),"test4",Toast.LENGTH_LONG).show();
//                        Map<String, String> map = getmacAddressMap(roomid,"4");
//                        mPresenter.getmacInfo(map, true);
//                    }
//                });
//
//                evaluateDialog.show();
//                evaluateDialog.setTitle("请选择"+roomNo+"要绑定的第几块显示屏?");

                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.dialog_evaluate, null);
                Button   btn_quxiao = (Button) view1.findViewById(R.id.btn_quxiao);
                Button  btn_queding = (Button) view1.findViewById(R.id.btn_queding);
                final TextView   goto_store_btn = (TextView) view1.findViewById(R.id.goto_store_btn);

                final TextView   goto_feedback_btn = (TextView) view1.findViewById(R.id.goto_feedback_btn);

                final TextView   cancel_btn = (TextView) view1.findViewById(R.id.cancel_btn);

                final TextView   btn_si = (TextView) view1.findViewById(R.id.btn_si);
                final TextView   title1 = (TextView) view1.findViewById(R.id.title1);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view1);
                     dialog =builder.create();// 获取dialog
               dialog.show();// 显示对话框
                title1.setText("请选择当前"+roomNo+"房间要绑定第几块显示屏");


                goto_store_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag1){
                            goto_store_btn.setBackgroundResource(R.drawable.checked_bg);
                            goto_feedback_btn.setBackgroundResource(R.drawable.normal_bg);
                            cancel_btn.setBackgroundResource(R.drawable.normal_bg);
                            btn_si.setBackgroundResource(R.drawable.normal_bg);
                            flag1=false;
                            flag2=true;
                            flag3=true;
                            flag4=true;
                            state=1;

                        }else {
                            goto_store_btn.setBackgroundResource(R.drawable.normal_bg);
                            flag1=true;
                            flag2=true;
                            flag3=true;
                            flag4=true;
                            state=0;
                        }

                    }
                });

                goto_feedback_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag2){
                            goto_feedback_btn.setBackgroundResource(R.drawable.checked_bg);
                            goto_store_btn.setBackgroundResource(R.drawable.normal_bg);
                            cancel_btn.setBackgroundResource(R.drawable.normal_bg);
                            btn_si.setBackgroundResource(R.drawable.normal_bg);
                            flag2=false;
                            flag1=true;
                            flag3=true;
                            flag4=true;
                            state=2;
                        }else {
                            goto_feedback_btn.setBackgroundResource(R.drawable.normal_bg);
                            flag2=true;
                            flag1=true;
                            flag3=true;
                            flag4=true;
                            state=0;
                        }

                    }
                });

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag3){
                            cancel_btn.setBackgroundResource(R.drawable.checked_bg);
                            goto_store_btn.setBackgroundResource(R.drawable.normal_bg);
                            goto_feedback_btn.setBackgroundResource(R.drawable.normal_bg);
                            btn_si.setBackgroundResource(R.drawable.normal_bg);
                            flag3=false;
                            flag2=true;
                            flag1=true;
                            flag4=true;
                            state=3;
                        }else {
                            cancel_btn.setBackgroundResource(R.drawable.normal_bg);
                            flag3=true;
                            flag2=true;
                            flag1=true;
                            flag4=true;
                            state=0;
                        }

                    }
                });

                btn_si.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(flag4){
                            btn_si.setBackgroundResource(R.drawable.checked_bg);
                            goto_store_btn.setBackgroundResource(R.drawable.normal_bg);
                            goto_feedback_btn.setBackgroundResource(R.drawable.normal_bg);
                            cancel_btn.setBackgroundResource(R.drawable.normal_bg);
                            flag4=false;
                            flag2=true;
                            flag3=true;
                            flag1=true;
                            state=4;
                        }else {
                            btn_si.setBackgroundResource(R.drawable.normal_bg);
                            flag4=true;
                            flag2=true;
                            flag3=true;
                            flag1=true;
                            state=0;
                        }

                    }
                });

                btn_queding.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getActivity(),state+"",Toast.LENGTH_LONG).show();
                        if(state==0){
                            Toast.makeText(getActivity(),"请选择显示屏",Toast.LENGTH_LONG).show();
                        }else {

                            Map<String, String> map = getmacAddressMap(roomid,state+"");
                            mPresenter.getmacInfo(map, true);
                            dialog.dismiss();
                        }

                    }
                });
                btn_quxiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        tvFailureView = (TextView) failureView.findViewById(R.id.tv_load_failure_hint);
        tvFailureView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
                onRefresh();
            }
        });



    }
    boolean flag1=true,flag2=true,flag3=true,flag4=true;
    int state=0;
    private AlertDialog dialog;

    @Override
    public void onRefresh() {
        srl.setRefreshing(false);
        Util.showGifProgressDialog(getActivity());
        Map<String, String> map = getRequestMap();
        mPresenter.getSocialDatas(map, true);
    }


    @Override
    protected void loadData() {
        /*String userId = IdentityManager.getUserId(getActivity());
        Map<String, String> map = ParametersFactory.getSchedulingMap(getContext(),userId,"2018-01-01","2018-03-20"
                );
        mPresenter.main(map, false);*/


    }

    public Map<String, String> getRequestMap() {
        String userId = IdentityManager.getUserId(getActivity());
        Map<String, String> map = ParametersFactory.getBuildinginfo(getActivity(), userId);
        return map;
    }

    public Map<String, String> getQrcodeRequestMap(String qrcode) {
        String userId = IdentityManager.getUserId(getActivity());
        Map<String, String> map = ParametersFactory.getQrcodeInfo(getActivity(), userId, qrcode);
        return map;
    }

    public Map<String, String> getmacAddressMap(String roomid,String number) {
        String userId = IdentityManager.getUserId(getActivity());
//         roomid="fe2392d0-acbd-46e1-a905-c93b0a86d7e8";
//         number="2";
//        String mac="A2:B9:4D:72:18:B5";
        String mac= Identity.srcInfo.getMymac();


        Map<String, String> map = ParametersFactory.getmacinfo(getActivity(), userId,roomid,number,mac);
        return map;
    }


    @Override
    public void onLoadListSuccess(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean) {
        srl.setVisibility(View.GONE);
        srl.setRefreshing(false);
        int a = bean.getData().size();
        bean.getData().get(0).getFloor().get(0).getFloor();
        bean.getData().get(0).getFloor().get(0).getRoom().get(0).getRoom();
       // int listSize = getListSizeFromResult(bean);

        buildingList.clear();
        buildingList.addAll(bean.getData());
        listRecycle.clear();
        listRecycle=bean.getData().get(0).getFloor().get(0).getRoom();

        mAdapter.setNewData(listRecycle);

        tv_room_code.setText(bean.getData().get(0).getBuilding().getBuildingNO()+bean.getData().get(0).getFloor().get(0).getFloor().getFloorNO());


        Map<String, String> map = getRequestCountMap(buildingList.get(0).getFloor().get(0).getFloor().getFloorID());
        mPresenter.getRoundsRecordCount(map, true);

        showRecyclerViewOrFailureView(true, ListUtils.isEmpty(listRecycle));

//
    }

    public Map<String, String> getRequestCountMap(String flooid) {
        Date day=new Date();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(df.format(day));

        String userId = IdentityManager.getUserId(getActivity());

        String startday = df.format(day);
        Map<String, String> map = ParametersFactory.getRoundsRecordCount(getActivity(), userId,flooid,startday);
        return map;
    }

    @Override
    public void onLoadMoreListSuccess(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean) {
        srl.setRefreshing(false);

    }

    @Override
    public void onLoadListFailure(String message, boolean isLoadMore) {
        srl.setRefreshing(false);

    }

    @Override
    public void onLoadRoundsRecordCountSuccess(HttpResultNurseBaseBean<List<RoundsRecordCount>> bean) {

        if(bean.getData()==null){

        }else {
            RoundsRecordCountList =bean.getData();
//            mAdapter = new RoundRoomFragmentAdapter(new ArrayList<RoomList>(), getActivity(),RoundsRecordCountList);
            mAdapter.setRoundsRecordCounts(RoundsRecordCountList);
            mAdapter.setNewData(listRecycle);
            mAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onLoadMoreRoundsRecordCountSuccess(HttpResultNurseBaseBean<List<RoundsRecordCount>> bean) {

    }

    @Override
    public void onLoadRoundsRecordCountFailure(String message, boolean isLoadMore) {

    }

    @Override
    public void onLoadQrcodeSuccess(HttpResultNurseBaseBean<String> bean) {
        if (bean!=null) {
            gotoDetailActivityByQrcode(bean.getData());
        }
    }

    @Override
    public void onLoadMoreQrcodeSuccess(HttpResultNurseBaseBean<String> bean) {

    }

    @Override
    public void onLoadQrcodeFailure(String message, boolean isLoadMore) {
        Util.showToast(getActivity(), "扫码失败，请重试！");
    }

    @Override
    public void onLoadmacSuccess(HttpResultNurseBaseBean<String> bean) {
        Toast.makeText(getActivity(),bean.getMsg(),Toast.LENGTH_LONG).show();
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.hint_suggestion)
                .content(bean.getMsg())
                .positiveText(R.string.confirm)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();



                    }
                }).build();
        dialog.show();
    }

    @Override
    public void onLoadmacFailure(String message, boolean isLoadMore) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

    }

    private ListView rootList;
    private ListView childList;
    private BackgroundDarkPopupWindow mPopWin;
    private LinearLayout layout;
    private FrameLayout flChild;

    private void showPopupWindow(int width, int height) {



        adapter = new CategoryListAdapter(getActivity(), buildingList,saveBuilding);

        layout = (LinearLayout) LayoutInflater.from(getActivity())
                .inflate(R.layout.popup_category, null);
        rootList = (ListView) layout.findViewById(R.id.rootcategory);
        rootList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        flChild = (FrameLayout) layout.findViewById(R.id.child_lay);
        childList = (ListView) layout.findViewById(R.id.childcategory);
        flChild.setVisibility(View.INVISIBLE);


//        mPopWin = new PopupWindow(layout, width, height * 2 / 3, true);
//        mPopWin.setBackgroundDrawable(new BitmapDrawable());
//        mPopWin.showAsDropDown(tv_room, 5, 1);
//        mPopWin.update();
        mPopWin = new BackgroundDarkPopupWindow(layout,width, height * 2 / 3);
        mPopWin.setFocusable(true);
        mPopWin.setBackgroundDrawable(new BitmapDrawable());
        mPopWin.setAnimationStyle(android.R.style.Animation_Dialog);

        mPopWin.setDarkStyle(-1);
        mPopWin.setDarkColor(Color.parseColor("#a0000000"));
        mPopWin.resetDarkPosition();
        mPopWin.darkBelow(tv_room);
        mPopWin.showAsDropDown(tv_room, tv_room.getRight() / 2, 0);

        floorLists = buildingList.get(saveBuilding).getFloor();
        flChild.setVisibility(View.VISIBLE);
        floorAdapter = new CategoryFloorAdapter(getActivity(), floorLists,saveFloor);
        childList.setAdapter(floorAdapter);
        floorAdapter.notifyDataSetChanged();

        rootList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mypotion=position;
                adapter = new CategoryListAdapter(getActivity(), buildingList,position);
                rootList.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                floorLists = buildingList.get(position).getFloor();
                flChild.setVisibility(View.VISIBLE);
                floorAdapter = new CategoryFloorAdapter(getActivity(), floorLists,0);
                childList.setAdapter(floorAdapter);
                floorAdapter.notifyDataSetChanged();
            }


        });
        childList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                floorAdapter = new CategoryFloorAdapter(getActivity(), floorLists,position);
                childList.setAdapter(floorAdapter);
                floorAdapter.notifyDataSetChanged();

                mPopWin.dismiss();
               // listRecycle.clear();
                listRecycle=buildingList.get(mypotion).getFloor().get(position).getRoom();
                showRecyclerViewOrFailureView(true, ListUtils.isEmpty(listRecycle));

                mAdapter.setNewData(listRecycle);
                mAdapter.notifyDataSetChanged();
              //  tv_room.setText(buildingList.get(mypotion).getBuilding().getBuildingNO()+buildingList.get(mypotion).getFloor().get(position).getFloor().getFloorNO());
                saveBuilding=mypotion;
                saveFloor=position;
                tv_room_code.setText(buildingList.get(mypotion).getBuilding().getBuildingNO()+buildingList.get(mypotion).getFloor().get(position).getFloor().getFloorNO());

                Map<String, String> map = getRequestCountMap(buildingList.get(mypotion).getFloor().get(position).getFloor().getFloorID());
                mPresenter.getRoundsRecordCount(map, true);

            }
        });
    }


    public ArrayList<RoomList> getListFromResult(HttpResultNurseBaseBean<ArrayList<RoomList>> beanList) {
        if (beanList != null) {
            ArrayList<RoomList> list = beanList.getData();
            return list;
        } else {
            return null;
        }
    }

    private void showRecyclerViewOrFailureView(boolean success, boolean isListEmpty) {
        if (success) {
            if (isListEmpty) {
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

    private String getListEmptyHint() {
        return getResources().getString(R.string.hint_no_room_consultation);
    }

    private String getFailureHint() {
        return getResources().getString(R.string.hint_load_failure);
    }



}








