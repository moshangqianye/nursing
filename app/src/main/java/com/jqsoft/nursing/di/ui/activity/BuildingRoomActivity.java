package com.jqsoft.nursing.di.ui.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.CategoryFloorAdapter;
import com.jqsoft.nursing.adapter.CategoryListAdapter;
import com.jqsoft.nursing.adapter.SocialAssistanceObjectAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.BedList;
import com.jqsoft.nursing.bean.BuildingRoomFloorBean;
import com.jqsoft.nursing.bean.FloorList;
import com.jqsoft.nursing.bean.RoomList;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.RoundsRecordCount;
import com.jqsoft.nursing.di.contract.SocialAssistanceObjectActivityContract;
import com.jqsoft.nursing.di.module.SocialAssistanceObjectActivityModule;
import com.jqsoft.nursing.di.presenter.SocialAssistanceObjectPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.youtuIdentify.BitMapUtils;
import com.jqsoft.nursing.di.youtuIdentify.IdentifyResult;
import com.jqsoft.nursing.di.youtuIdentify.TecentHttpUtil;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleClickListener;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.LogUtil;
import com.jqsoft.nursing.utils3.util.ListUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import bakerj.backgrounddarkpopupwindow.BackgroundDarkPopupWindow;
import butterknife.BindView;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Jerry on 2017/12/27.
 */
//房间写卡列表搜索
public class BuildingRoomActivity extends AbstractActivity implements SocialAssistanceObjectActivityContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private int currentPage = Constants.DEFAULT_INITIAL_PAGE;
    private int pageSize = Constants.DEFAULT_PAGE_SIZE;
    private EditText et_search;
    private String tempString, cardNo, name;
    private SocialAssistanceObjectAdapter mAdapter;
    RecyclerView recyclerView;
    @BindView(R.id.lay_content)
    SwipeRefreshLayout srl;
    @BindView(R.id.check_socaildetail)
    LinearLayout check_socaildetail;
    @BindView(R.id.btn_reset)
    LinearLayout btn_reset;
    @BindView(R.id.nursing_title)
    TextView online_consultation_title;
    @BindView(R.id.iv_photo)
    ImageView iv_photo;
    @BindView(R.id.lay_online_consultation_load_failure)
    View failureView;
    @BindView(R.id.query_btn)
    ImageView query_btn;
    @BindView(R.id.tv_room)
    TextView tv_room;
    @BindView(R.id.bt_username_clear)
    Button bt_username_clear;

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @BindView(R.id.tv_room_code)
    TextView tv_room_code;


    private TextView tvFailureView;
    private TextView tv_search_send;

    private Boolean isRefresh = false;
    private final static int REQUEST_IMAGE = 100;
    private String picture = null;
    private Bitmap bitmap = null;
    private ArrayList<BuildingRoomFloorBean> buildingList = new ArrayList<>();
    private List<FloorList> floorLists = new ArrayList<>();
    private ArrayList<RoomList> roomLists = new ArrayList<>();
    private CategoryListAdapter adapter;
    private CategoryFloorAdapter floorAdapter;
    @Inject
    SocialAssistanceObjectPresenter mPresenter;
    private String SelectLogo;
    private int mypotion = 0, saveBuilding = 0, saveFloor = 0;
    private ArrayList<RoomList> listRecycle =new ArrayList<>();

    @Override
    protected void loadData() {
        onRefresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_socialassistanceobject;
    }

    @Override
    protected void initData() {
        SelectLogo = getIntent().getStringExtra("SelectLogo");
        Log.i("", SelectLogo + "");

    }

    @Override
    protected void initView() {
        online_consultation_title.setText("房间信息");
        et_search = (EditText) findViewById(R.id.et_search);
        //   tv_search_send = (TextView) findViewById(R.id.tv_search_send);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        recyclerView = (RecyclerView) srl.findViewById(R.id.recyclerview);
        tvFailureView = (TextView) failureView.findViewById(R.id.tv_load_failure_hint);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvFailureView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
                onRefresh();
            }
        });
        srl.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        srl.setOnRefreshListener(this);


        final BaseQuickAdapter<RoomList, BaseViewHolder> mAdapter = new SocialAssistanceObjectAdapter(new ArrayList<RoomList>(), getApplicationContext());
        this.mAdapter = (SocialAssistanceObjectAdapter) mAdapter;
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //  mAdapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Log.i(TAG, "输入文字中的状态，count是输入字符数");

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Log.i(TAG, "输入文本之前的状态");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Log.i(TAG, "输入文字后的状态");
                if (!TextUtils.isEmpty(s.toString())) {
                    bt_username_clear.setVisibility(View.VISIBLE);
                }

            }
        });
        bt_username_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });
        query_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempString = et_search.getText().toString();
                if (tempString.length() < 18) {
                    name = tempString;
                } else {
                    cardNo = tempString;
                }
                if (!TextUtils.isEmpty(tempString)) {
                    onRefresh();
                } else {
                    Util.showToast(BuildingRoomActivity.this, Constants.CHECK_DATA);
                }
            }
        });
        check_socaildetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempString = et_search.getText().toString();
                if (tempString.length() < 18) {
                    name = tempString;
                } else {
                    cardNo = tempString;
                }
                if (!TextUtils.isEmpty(tempString)) {
                    onRefresh();
                } else {
                    Util.showToast(BuildingRoomActivity.this, Constants.CHECK_DATA);
                }
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
                onRefresh();

            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArrayList<BedList> bedLists = mAdapter.getData().get(position).getBed();
                if (SelectLogo.equals("1")) {
                    Intent intent = new Intent(BuildingRoomActivity.this, WriteNFC.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("SelectLogo", SelectLogo);
                    bundle.putSerializable("roomInfo",mAdapter.getData().get(position).getRoom());
                    //Util.gotoActivityWithBundle(getApplicationContext(), SocialDetailActivity.class, bundle);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    if (bedLists.size() > 0) {
                        Intent intent = new Intent(BuildingRoomActivity.this, RoomBedDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bedLists", bedLists);
                        bundle.putString("SelectLogo", SelectLogo);
                        bundle.putString("buildinfo", tv_room_code.getText().toString());
                        bundle.putString("roomNO", mAdapter.getData().get(position).getRoom().getRoomNO());
                        //Util.gotoActivityWithBundle(getApplicationContext(), SocialDetailActivity.class, bundle);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Util.showToast(getApplicationContext(), "此间房没有床位");
                    }
                }


            }
        });
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(getApplicationContext(),"拍照",Toast.LENGTH_SHORT).show();
                if (ActivityCompat.checkSelfPermission(BuildingRoomActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 应用没有读取手机外部存储的权限
                    // 申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(BuildingRoomActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            100);
                } else {
                    selectImage();
                }
            }
        });
        ly_search = (LinearLayout) findViewById(R.id.ly_search);
        tv_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow(ly_search.getWidth(), ly_search.getHeight());
            }
        });
    }

    private ListView rootList;
    private ListView childList;
    private BackgroundDarkPopupWindow mPopWin;
    private LinearLayout layout, ly_search;
    private FrameLayout flChild;

    private void showPopupWindow(int width, int height) {


        adapter = new CategoryListAdapter(BuildingRoomActivity.this, buildingList,saveBuilding);

        layout = (LinearLayout) LayoutInflater.from(BuildingRoomActivity.this)
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
        floorAdapter = new CategoryFloorAdapter(BuildingRoomActivity.this, floorLists,saveFloor);
        childList.setAdapter(floorAdapter);
        floorAdapter.notifyDataSetChanged();

        rootList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mypotion=position;
                adapter = new CategoryListAdapter(BuildingRoomActivity.this, buildingList,position);
                rootList.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                floorLists = buildingList.get(position).getFloor();
                flChild.setVisibility(View.VISIBLE);
                floorAdapter = new CategoryFloorAdapter(BuildingRoomActivity.this, floorLists,0);
                childList.setAdapter(floorAdapter);
                floorAdapter.notifyDataSetChanged();
            }


        });
        childList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                floorAdapter = new CategoryFloorAdapter(BuildingRoomActivity.this, floorLists,position);
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
            }
        });

//        adapter = new CategoryListAdapter(this, buildingList, 0);
//        layout = (LinearLayout) LayoutInflater.from(BuildingRoomActivity.this)
//                .inflate(R.layout.popup_category, null);
//        rootList = (ListView) layout.findViewById(R.id.rootcategory);
//        rootList.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        flChild = (FrameLayout) layout.findViewById(R.id.child_lay);
//        childList = (ListView) layout.findViewById(R.id.childcategory);
//        flChild.setVisibility(View.INVISIBLE);
//
//
//        mPopWin = new PopupWindow(layout, width, height * 2 / 3, true);
//        mPopWin.setBackgroundDrawable(new BitmapDrawable());
//        mPopWin.showAsDropDown(tv_room, 5, 1);
//        //  mPopWin.setAnimationStyle(R.style.showPopupAnimation);
//        mPopWin.update();
//
//        floorLists = buildingList.get(saveBuilding).getFloor();
//        flChild.setVisibility(View.VISIBLE);
//        floorAdapter = new CategoryFloorAdapter(BuildingRoomActivity.this, floorLists, saveFloor);
//        childList.setAdapter(floorAdapter);
//        floorAdapter.notifyDataSetChanged();
//        rootList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                mypotion = position;
//                adapter = new CategoryListAdapter(BuildingRoomActivity.this, buildingList, position);
//                rootList.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//
//                floorLists = buildingList.get(position).getFloor();
//                flChild.setVisibility(View.VISIBLE);
//                floorAdapter = new CategoryFloorAdapter(BuildingRoomActivity.this, floorLists, 0);
//                childList.setAdapter(floorAdapter);
//                floorAdapter.notifyDataSetChanged();
//                tv_room_code.setText(buildingList.get(position).getBuilding().getBuildingNO() + "1F");
//            }
//
//
//        });
//        childList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                roomLists.clear();
//                roomLists.addAll(floorLists.get(position).getRoom());
//                mAdapter.setNewData(roomLists);
//                if (mPopWin != null) {
//                    mPopWin.dismiss();
//                }
//                saveBuilding = mypotion;
//                saveFloor = position;
//                tv_room_code.setText(buildingList.get(mypotion).getBuilding().getBuildingNO() + buildingList.get(mypotion).getFloor().get(position).getFloor().getFloorNO());
//            }
//        });
    }

    public Map<String, String> getRequestMap() {
        String userId = IdentityManager.getUserId(this);
        Map<String, String> map = ParametersFactory.getBuildinginfo(this, userId);
        return map;
    }

    @Override
    protected void initInject() {
        DaggerApplication.get(this)
                .getAppComponent()
                .addSocialAssistanceObjectActivity(new SocialAssistanceObjectActivityModule(this))
                .inject(this);
    }

    @Override
    public void onRefresh() {
        currentPage = Constants.DEFAULT_INITIAL_PAGE;
        isRefresh = true;
        mAdapter.setEnableLoadMore(false);
        Map<String, String> map = getRequestMap();
        mPresenter.getSocialDatas(map, false);
    }

    @Override
    public void onLoadMoreRequested() {
        ++currentPage;
        Map<String, String> map = getRequestMap();
        mPresenter.getSocialDatas(map, true);
        LogUtil.i("OnlineConsultationActivity onLoadMoreRequested:" + "currentPage/pageSize:" + currentPage + "/" + pageSize);
//        srl.setEnabled(false);
    }

    @Override
    public void onLoadListSuccess(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean) {
        int a = bean.getData().size();
        bean.getData().get(0).getFloor().get(0).getFloor();
        int listSize = getListSizeFromResult(bean);
        buildingList.addAll(bean.getData());
        listRecycle.clear();
        listRecycle=bean.getData().get(0).getFloor().get(0).getRoom();
        List<RoomList> list = getListFromResult(buildingList.get(0).getFloor().get(0).getRoom());
        mAdapter.setNewData(list);
        tv_room_code.setText(bean.getData().get(0).getBuilding().getBuildingNO() + bean.getData().get(0).getFloor().get(0).getFloor().getFloorNO());
        showRecyclerViewOrFailureView(true, ListUtils.isEmpty(list));
//
        srl.setRefreshing(false);
//        setLoadMoreStatus(pageSize, listSize, true);
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

    @Override
    public void onLoadMoreListSuccess(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean) {
//        int listSize = getListSizeFromResult(bean);
//        List<BuildingRoomFloorBean> list = getListFromResult(bean);
//        mAdapter.addData(list);
//
//        showRecyclerViewOrFailureView(true, ListUtils.isEmpty(mAdapter.getData()));
        srl.setEnabled(true);
        srl.setRefreshing(false);
//        setLoadMoreStatus(this.pageSize, listSize, true);
    }

    @Override
    public void onLoadListFailure(String message, boolean isLoadMore) {
        showRecyclerViewOrFailureView(false, true);
        if (isLoadMore) {
            if (currentPage > Constants.DEFAULT_INITIAL_PAGE) {
                --currentPage;
            }

        } else {

        }
        srl.setRefreshing(false);
        setLoadMoreStatus(0, 0, false);
////        Util.showToast(this, message);
        Util.showToast(this, message);

    }

    @Override
    public void onLoadRoundsRecordCountSuccess(HttpResultNurseBaseBean<List<RoundsRecordCount>> bean) {

    }

    @Override
    public void onLoadMoreRoundsRecordCountSuccess(HttpResultNurseBaseBean<List<RoundsRecordCount>> bean) {

    }

    @Override
    public void onLoadRoundsRecordCountFailure(String message, boolean isLoadMore) {

    }

    @Override
    public void onLoadQrcodeSuccess(HttpResultNurseBaseBean<String> bean) {

    }

    @Override
    public void onLoadMoreQrcodeSuccess(HttpResultNurseBaseBean<String> bean) {

    }

    @Override
    public void onLoadQrcodeFailure(String message, boolean isLoadMore) {

    }

    @Override
    public void onLoadmacSuccess(HttpResultNurseBaseBean<String> bean) {

    }

    @Override
    public void onLoadmacFailure(String message, boolean isLoadMore) {

    }

    public int getListSizeFromResult(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> beanList) {
        if (beanList != null) {
            List<BuildingRoomFloorBean> list = beanList.getData();
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

    public List<RoomList> getListFromResult(List<RoomList> beanList) {
        if (beanList != null) {
            List<RoomList> list = beanList;
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

    /**
     * select picture
     */
    private void selectImage() {
        MultiImageSelector.create(BuildingRoomActivity.this)
                .showCamera(true) // 是否显示相机. 默认为显示
//                .count(1) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                .single() // 单选模式
//                .multi() // 多选模式, 默认模式;
//                .origin(ArrayList<String>) // 默认已选择图片. 只有在选择模式为多选时有效
                .start(BuildingRoomActivity.this, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                Util.showGifProgressDialog(BuildingRoomActivity.this);
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                if (path != null && path.size() > 0) {
                    picture = path.get(0);
//                    onSelected();
                    bitmap = getImage(picture);
                    //   imageView.setImageBitmap(bitmap);

                    TecentHttpUtil.uploadIdCard(BitMapUtils.bitmapToBase64(bitmap), "0", new TecentHttpUtil.SimpleCallBack() {
                        @Override
                        public void Succ(String res) {
                            IdentifyResult result = new Gson().fromJson(res, IdentifyResult.class);
                            if (result != null) {
                                if (result.getErrorcode() == 0) {
                                    // 识别成功
                                    Util.hideGifProgressDialog(BuildingRoomActivity.this);
                                    showDialogInfo(result);

                                } else {
                                    Util.hideGifProgressDialog(BuildingRoomActivity.this);
                                    Toast.makeText(BuildingRoomActivity.this, "识别失败，请拍照清楚后重新识别", Toast.LENGTH_SHORT).show();
                                /*switch (result.getErrorcode()){
                                    case -7001:
                                        Toast.makeText(MainActivity.this, "未检测到身份证，请对准边框(请避免拍摄时倾角和旋转角过大、摄像头)", Toast.LENGTH_SHORT).show();
                                        break;
                                    case -7002:
                                        Toast.makeText(MainActivity.this, "请使用第二代身份证件进行扫描", Toast.LENGTH_SHORT).show();
                                        break;
                                    case -7003:
                                        Toast.makeText(MainActivity.this, "不是身份证正面照片(请使用带证件照的一面进行扫描)", Toast.LENGTH_SHORT).show();
                                        break;
                                    case -7004:
                                        Toast.makeText(MainActivity.this, "不是身份证反面照片(请使用身份证反面进行扫描)", Toast.LENGTH_SHORT).show();
                                        break;
                                    case -7005:
                                        Toast.makeText(MainActivity.this, "确保扫描证件图像清晰", Toast.LENGTH_SHORT).show();
                                        break;
                                    case -7006:
                                        Toast.makeText(MainActivity.this, "请避开灯光直射在证件表面", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(MainActivity.this, "识别失败，请稍后重试", Toast.LENGTH_SHORT).show();
                                        break;
                                }*/
                                }
                            }
                        }

                        @Override
                        public void error() {

                        }
                    });


                }
            }
        }
    }

    /**
     * 显示对话框
     *
     * @param result
     */
    private void showDialogInfo(final IdentifyResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("姓名：" + result.getName() + "\n");
        sb.append("性别：" + result.getSex() + "\t" + "民族：" + result.getNation() + "\n");
        sb.append("出生：" + result.getBirth() + "\n");
        sb.append("住址：" + result.getAddress() + "\n" + "\n");
        sb.append("公民身份号码：" + result.getId() + "\n");
        AlertDialog.Builder builder = new AlertDialog.Builder(BuildingRoomActivity.this);
        AlertDialog dialogInfo = builder.setTitle("识别成功")
                .setMessage(sb.toString())
                .setPositiveButton("复制号码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("text", result.getId());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(BuildingRoomActivity.this, "身份证号已复制到粘贴板", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        dialogInfo.show();

    }

    /**
     * 获取压缩后的图片
     *
     * @param srcPath
     * @return
     */
    private Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            if (options < 0) {
                image.compress(Bitmap.CompressFormat.JPEG, 10, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                break;
            } else {
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            }

            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


}
