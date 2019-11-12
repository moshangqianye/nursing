package com.jqsoft.nursing.di.ui.activity;


import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.folderselector.FileChooserDialog;
import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.Identity;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.BuildingRoomFloorBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.RoundsRecordCount;
import com.jqsoft.nursing.di.contract.SocialAssistanceObjectActivityContract;
import com.jqsoft.nursing.di.module.RoundRoomFramentModule;
import com.jqsoft.nursing.di.presenter.WorkbenchActivityPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.activity.nursing.MacFragmentActivity;
import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.di.ui.activity.nursing.RoundRoomDetailActivity;
import com.jqsoft.nursing.di.ui.fragment.HealthListFragment;
import com.jqsoft.nursing.di.ui.fragment.IndexFragment;
import com.jqsoft.nursing.di.ui.fragment.NursingFragment;
import com.jqsoft.nursing.di.ui.fragment.RoundFragment;
import com.jqsoft.nursing.di.ui.fragment.SimpleCardFragment;
import com.jqsoft.nursing.di.ui.fragment.nursing.DeanCockpitBedFragment;
import com.jqsoft.nursing.di.ui.fragment.nursing.DeanCockpitElderFragment;
import com.jqsoft.nursing.di.ui.fragment.nursing.DeanCockpitFinanceFragment;
import com.jqsoft.nursing.di.ui.fragment.nursing.IndexDeanFragment;
import com.jqsoft.nursing.di.youtuIdentify.BitMapUtils;
import com.jqsoft.nursing.di.youtuIdentify.IdentifyResult;
import com.jqsoft.nursing.di.youtuIdentify.TecentHttpUtil;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.entity.TabEntity;
import com.jqsoft.nursing.keepalive.single_pixel.ScreenBroadcastListener;
import com.jqsoft.nursing.keepalive.single_pixel.ScreenManager;
import com.jqsoft.nursing.rx.RxBus;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.LogUtil;
import com.jqsoft.nursing.utils.ToastUtil;
import com.jqsoft.nursing.utils.ViewFindUtils;
import com.jqsoft.nursing.utils2.SharedPreferencesUtil;
import com.tencent.bugly.beta.Beta;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class WorkbenchActivity extends AbstractActivity implements ColorChooserDialog.ColorCallback,
        FileChooserDialog.FileCallback, FolderChooserDialog.FolderCallback ,SocialAssistanceObjectActivityContract.View {
    private boolean isTrue = true;
    private String nfcFlag;
    private String sPostImgFlag = "1";
    private Thread mTestThread;
    private boolean isDestroyedBySystem = false;

    private Context tcontext;
    private static String DB_NAME;
    private static final int DB_VERSION = 2;
    private static WorkbenchActivity.DatabaseHelper mOpenHelper;
    private static SQLiteDatabase db2;
    private static final int SETTING_SERVER_IP = 11;
    private static final int SETTING_BT = 22;
    public static String remoteIPA = "";
    public static String remoteIPB = "";
    public static String remoteIPC = "";
    private int mode = 2;//1,OTG; 2, NFC; //3, Bluetooth;
    private int fragmenttype = 0;


    private PendingIntent pi = null;
    private IntentFilter tagDetected = null;
    private String[][] mTechLists;
    private Intent inintent = null;
    private final static int REQUEST_IMAGE = 100;
    private int readflag = 0;
    private BluetoothAdapter btAdapt;
    private static final int REQUEST_ENABLE_BT = 2;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    public static String addressmac = "";

    public static final int MESSAGE_VALID_OTGBUTTON = 15;
    public static final int MESSAGE_VALID_NFCBUTTON = 16;
    public static final int MESSAGE_VALID_BTBUTTON = 17;
    public static final int MESSAGE_VALID_PROCESS = 1001;

    public static String DB_CREATE_TABLE_IPCONFIG = "CREATE TABLE IF NOT EXISTS [setipconfig] " +
            "([ID] INTEGER PRIMARY KEY,[IP] VARCHAR)";


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db2) {
            db2.execSQL(DB_CREATE_TABLE_IPCONFIG);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    private static Button onredo;

    @Inject
//    RoundRoomFragmentPresenter mPresenter;
    WorkbenchActivityPresenter mPresenter;

    private final int REQUEST_CODE=100;


    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @BindView(R.id.iv_index)
    ImageView ivIndex;
    @BindView(R.id.iv_round_room)
    ImageView ivRoundRoom;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.iv_nursing)
    ImageView ivNursing;
    @BindView(R.id.iv_mine)
    ImageView ivMine;

    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.tv_round_room)
    TextView tvRoundRoom;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.tv_nursing)
    TextView tvNursing;
    @BindView(R.id.tv_mine)
    TextView tvMine;

    @BindView(R.id.ll_index)
    LinearLayout ll_index;
    @BindView(R.id.ll_round_room)
    LinearLayout ll_round_room;
    @BindView(R.id.ll_nursing)
    LinearLayout ll_nursing;
    @BindView(R.id.ll_mine)
    LinearLayout ll_mine;
    @BindView(R.id.ll_scan)
    LinearLayout ll_scan;

    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {Constants.TITLE_INDEX, Constants.TITLE_NURSING,
            Constants.TITLE_QUERY, Constants.TITLE_STATISTICS, Constants.TITLE_MINE};
    private int[] mIconUnselectIds = {
            R.mipmap.g_module_index_n, R.mipmap.g_module_transact_n,
            R.mipmap.g_module_query_n, R.mipmap.g_module_statistics_n, R.mipmap.g_module_mine_n};
    private int[] mIconSelectIds = {
            R.mipmap.g_module_index_h, R.mipmap.g_module_transact_h,
            R.mipmap.g_module_query_h, R.mipmap.g_module_statistics_h, R.mipmap.g_module_mine_h};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout;

    private CompositeSubscription mIndexSelectSubscription;

    private void clearImageTextStatus(){
        ivIndex.setImageResource(R.mipmap.g_module_index_n);
        tvIndex.setTextColor(Color.parseColor("#66000000"));
        ivRoundRoom.setImageResource(R.mipmap.g_module_query_n);
        tvRoundRoom.setTextColor(Color.parseColor("#66000000"));
        ivNursing.setImageResource(R.mipmap.g_module_statistics_n);
        tvNursing.setTextColor(Color.parseColor("#66000000"));
        ivMine.setImageResource(R.mipmap.g_module_mine_n);
        tvMine.setTextColor(Color.parseColor("#66000000"));
    }

    private void initImageClickListener(){
        Util.setViewClickListener(ll_index, new Runnable() {
            @Override
            public void run() {
                hilightIndex();
            }
        });
        Util.setViewClickListener(ll_round_room, new Runnable() {
            @Override
            public void run() {
                hilightRoundRoom();
            }
        });
        Util.setViewClickListener(ll_nursing, new Runnable() {
            @Override
            public void run() {
                hilightNursing();
            }
        });
        Util.setViewClickListener(ll_mine, new Runnable() {
            @Override
            public void run() {
                hilightMine();
            }
        });
    }

    private void hilightMine() {
        clearImageTextStatus();
        ivMine.setImageResource(R.mipmap.g_module_mine_h);
        tvMine.setTextColor(getResources().getColor(R.color.colorTheme));
        switchToIndex(3);
    }

    private void hilightNursing() {
        clearImageTextStatus();
        ivNursing.setImageResource(R.mipmap.g_module_statistics_h);
        tvNursing.setTextColor(getResources().getColor(R.color.colorTheme));
        switchToIndex(2);
    }

    private void hilightRoundRoom() {
        clearImageTextStatus();
        ivRoundRoom.setImageResource(R.mipmap.g_module_query_h);
        tvRoundRoom.setTextColor(getResources().getColor(R.color.colorTheme));
        switchToIndex(1);
    }

    private void hilightIndex() {
        clearImageTextStatus();
        ivIndex.setImageResource(R.mipmap.g_module_index_h);
        tvIndex.setTextColor(getResources().getColor(R.color.colorTheme));
        switchToIndex(0);
    }


    /* 扫二维码 ===========================*/
    private void gotoScanQrcode(){
        Intent intent = new Intent(WorkbenchActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

//        Intent intent = new Intent(WorkbenchActivity.this, ZxingActivity.class);
//        startActivityForResult(intent, IntentConstant.REQUESTCODE_SCAN_QR_CODE);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
////            case IntentConstant.REQUESTCODE_SCAN_QR_CODE:
////                if (data!=null){
////                    String result = data.getStringExtra(IntentConstant.EXTRANAME_QR_CODE_TEXT);
////                    getQrcodeInfo(result);
//////                    gotoDetailActivityByQrcode(result);
////                }
////                break;
//            case REQUEST_CODE:
//                if (null != data) {
//                    Bundle bundle = data.getExtras();
//                    if (bundle == null) {
//                        Util.showToast(WorkbenchActivity.this, "扫描结果为空");
//                        return;
//                    }
//                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                        String result = bundle.getString(CodeUtils.RESULT_STRING);
//                        getQrcodeInfo(result);
//                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                        Util.showToast(WorkbenchActivity.this, "解析二维码失败");
//                    } else {
//                        Util.showToast(WorkbenchActivity.this, "解析二维码失败");
//                    }
//                } else {
//                    Util.showToast(WorkbenchActivity.this, "扫描结果为空");
//                }
//                break;
//        }
//    }

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
            Util.gotoActivityWithBundle(WorkbenchActivity.this, NursingDetailActivity.class, bundle);
        } else if (readRoomResult[0].equals("1")) {
            bundle.putString("roomid", readRoomResult[1]);
            bundle.putString("roomNo", readRoomResult[2]);
            bundle.putString("roomType", readRoomResult[3]);
            bundle.putString("roomExtra", "");
            bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, true);

            Util.gotoActivityWithBundle(WorkbenchActivity.this, RoundRoomDetailActivity.class, bundle);
        }
    }

    private void getQrcodeInfo(String qrcode){
        if(qrcode==null || TextUtils.isEmpty(qrcode)){
            Util.showToast(WorkbenchActivity.this, "扫描结果为空");
        }else {
            if(qrcode.length()>4){
                String mac=qrcode.substring(0,4);

                if(("mac=").equals(mac)){
                  String   mymac=   qrcode.replaceAll(qrcode.substring(0,4),"");
                    Identity.srcInfo.setMymac(mymac);
                    Intent  intent = new Intent();
                    intent.setClass(WorkbenchActivity.this, MacFragmentActivity.class);
                    startActivity(intent);
                }else {
                    Map<String, String> map = getQrcodeRequestMap(qrcode);
                    mPresenter.getQrcodeInfo(map, false);
                }


            }else {

                Map<String, String> map = getQrcodeRequestMap(qrcode);
                mPresenter.getQrcodeInfo(map, false);
            }


        }



    }

    public Map<String, String> getQrcodeRequestMap(String qrcode) {
        String userId = IdentityManager.getUserId(WorkbenchActivity.this);
        Map<String, String> map = ParametersFactory.getQrcodeInfo(WorkbenchActivity.this, userId, qrcode);
        return map;
    }

    @Override
    public void onLoadListSuccess(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean) {

    }

    @Override
    public void onLoadMoreListSuccess(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean) {

    }

    @Override
    public void onLoadListFailure(String message, boolean isLoadMore) {

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
        if (bean!=null) {
            gotoDetailActivityByQrcode(bean.getData());
        }
    }

    @Override
    public void onLoadMoreQrcodeSuccess(HttpResultNurseBaseBean<String> bean) {

    }

    @Override
    public void onLoadQrcodeFailure(String message, boolean isLoadMore) {
        Util.showToast(WorkbenchActivity.this, "扫码失败，请重试！");
    }

    @Override
    public void onLoadmacSuccess(HttpResultNurseBaseBean<String> bean) {

    }

    @Override
    public void onLoadmacFailure(String message, boolean isLoadMore) {

    }

    /* 扫二维码 ===========================*/

    @Override
    protected void initInject() {
        DaggerApplication.get(this)
                .getAppComponent()
                .addRoundRoomFragment(new RoundRoomFramentModule(this))
                .inject(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_workbench;
    }

    public ArrayList<Fragment> getmFragments() {
        return mFragments;
    }

    private void registerIndexSelectSubscription() {
        Subscription subscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_WOULD_SCROLL_TO_WORKBENCH_INDEX, Integer.class).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
//                switchToIndex(integer);
                showWorkbenchActivityAndSwitchToIndex(integer);
            }
        });
//        Subscription initilizeOnlineSignSubscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_WOULD_SCROLL_TO_WORKBENCH_ONLINE_SIGN_AND_INITIALIZE, IndexAndOnlineSignInitialData.class)
//                .subscribe(new Action1<IndexAndOnlineSignInitialData>() {
//                    @Override
//                    public void call(IndexAndOnlineSignInitialData indexAndOnlineSignInitialData) {
//
//                    }
//                });
        if (mIndexSelectSubscription == null) {
            mIndexSelectSubscription = new CompositeSubscription();
        }
        mIndexSelectSubscription.add(subscription);
    }

    public void switchToIndex(int index) {
        mViewPager.setCurrentItem(index);
    }

    public void showWorkbenchActivityAndSwitchToIndex(int index) {
        Intent intent = new Intent(this, WorkbenchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.WORKBENCH_ON_NEW_INTENT_INITIAL_INDEX_KEY, index);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void gotoSomePageThroughPushNotification(String pi, String userUuid) {
        if (Constants.PI_ADD_SIGN_APPLICATION.equals(pi) || Constants.PI_CANCEL_SIGN_APPLICATION.equals(pi)) {
            //  Util.gotoActivity(this, OnLineSignApplication.class);
        } else if (Constants.PI_ADD_APPOINTMENT_SIGN.equals(pi) || Constants.PI_CANCEL_APPOINTMENT_SIGN.equals(pi)
                || Constants.PI_EXECUTE_APPOINTMENT_SIGN_SUCCESS.equals(pi)
                || Constants.PI_EXECUTE_APPOINTMENT_SIGN_FAILURE.equals(pi)) {
            RxBus.getDefault().post(Constants.EVENT_TYPE_GOTO_APPOINTMENT_SIGN_PAGE, true);
        } else if (Constants.PI_ADD_SIGN_SERVICE_ASSESS.equals(pi)) {
            Util.gotoActivity(this, ClientSignServiceAssessActivity.class);
        } else if (Constants.PI_NEW_ONLINE_ADVISORY.equals(pi)) {
//            RxBus.getDefault().post(Constants.EVENT_TYPE_REFRESH_CHAT_PERSON_LIST, true);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.DOCTOR_USER_ID, userUuid);
            bundle.putString("total", Constants.ONE_STRING);
            Util.gotoActivityWithBundle(this, ChatDetailActivity.class, bundle);
        }
    }


    int index = 0;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        index = Util.getWorkbenchIndexFromIntent(intent);
        if (index >= Constants.WORKBENCH_INDEX && index <= Constants.WORKBENCH_MINE) {
            switchToIndex(index);
        }

        checkPushNotificationExist(intent);

//        isDestroyedBySystem = false;
//        if (intent!=null){
//            isDestroyedBySystem = intent.getBooleanExtra(Constants.IS_DESTROYED_BY_SYSTEM_KEY, false);
//        }


    }

    private void checkPushNotificationExist(Intent intent) {
        boolean isPushNotificationExist = Util.getWorkbenchPushNotificationExistFromIntent(intent);
        String pi = Util.getWorkbenchPushNotificationIntentFromIntent(intent);
        String userUuid = Util.getWorkbenchPushNotificationUserUuidFromIntent(intent);
        if (isPushNotificationExist) {
            Identity.shouldReadIdCard = !isPushNotificationExist;
            gotoSomePageThroughPushNotification(pi, userUuid);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(Constants.IS_DESTROYED_BY_SYSTEM_KEY, true);
        LogUtil.i("onSaveInstanceState:IS_DESTROYED_BY_SYSTEM_KEY's value:" + true);
        SharedPreferencesUtil.setBoolean(this, Constants.IS_DESTROYED_BY_SYSTEM_KEY, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isDestroyedBySystem = savedInstanceState.getBoolean(Constants.IS_DESTROYED_BY_SYSTEM_KEY, false);
        LogUtil.i("onRestoreInstanceState:IS_DESTROYED_BY_SYSTEM_KEY's value:" + isDestroyedBySystem);
    }

    //    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        outPersistentState.putInt(Constants.IS_DESTROYED_BY_SYSTEM_KEY, 1);
//        super.onSaveInstanceState(outState, outPersistentState);
//    }

    @Override
    protected void initData() {

//		ReadCardAPI.setpathflag(2);
//		ReadCardAPI.setlogflag(1);
//		mAdapter = NfcAdapter.getDefaultAdapter(this);


    }

    private void test() {
        String s = null;
        int l = s.length();
    }

    private void showTestNotification() {
        Observable.timer(8, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Util.showNotificationAndClickHandlerIsResumeApplication(WorkbenchActivity.this, "hint", "There comes a message");
                    }
                });
    }


    private void gotoFrontAfter8Seconds() {
        Observable.timer(8000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        LogUtil.i("toForeground call toFront");
                        toFront();
                    }
                });
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                toFront();
//            }
//        }, 8000);
    }

    private void toFront() {
        Util.bringApplicationToForegroundAndVisibleToUser(this);
//        Util.bringApplicationToFront(this);

//        String topActivityName = Util.getCurrentApplicationTopActivityName(this);
//        Class clazz = null;
//        try {
//            clazz = Class.forName(topActivityName);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            clazz=null;
//        }
//        if (clazz!=null) {
//            LogUtil.i("top activity class is not null");
//            Identity.shouldReadIdCard = false;
//            Intent intent = new Intent(this, clazz);
//            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK
//            |Intent.FLAG_ACTIVITY_CLEAR_TOP );
//            startActivity(intent);
//        } else {
//            LogUtil.i("top activity class is null");
//        }


//        try {
//            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//            List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1000);
//            int taskId = 0;
//            for (int i = 0; i < taskList.size(); ++i){
//                ActivityManager.RunningTaskInfo rti = taskList.get(i);
//                String packageName = rti.baseActivity.getPackageName();
//                LogUtil.i("toForeground index:"+i+" packageName:"+packageName);
//                if (getPackageName().equals(packageName)){
//                    taskId = rti.id;
//                    break;
//                }
//            }
//            LogUtil.i("toForeground taskId:"+taskId);
//            int gotId = getTaskId();
//            LogUtil.i("toForeground gotTaskId:"+gotId);
//            if (taskId!=0){
////                am.moveTaskToFront(gotId, 0);
//                am.moveTaskToFront(gotId, ActivityManager.MOVE_TASK_WITH_HOME);
////                am.moveTaskToFront(gotId, ActivityManager.MOVE_TASK_WITH_HOME|ActivityManager.MOVE_TASK_NO_USER_ACTION);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtil.i("exception occurred in toFront:"+e.getLocalizedMessage());
//        }

//        moveTaskToBack(true);
    }

    private void checkUpgrade() {
        Beta.checkUpgrade();
    }


    @Override
    protected void initView() {
        checkUpgrade();

        mDecorView = getWindow().getDecorView();
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.commonTabLayout);


        initScanImageView();
        initImageClickListener();


//        showTestNotification();
//        gotoFrontAfter8Seconds();


//        test();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        LogUtil.i("in WorkbenchActivity toolbar:"+toolbar);
//        setSupportActionBar(toolbar);

//        initSinglePixelKeepAliveProcess();


//        for (String title : mTitles) {
//            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
//        }
        //  String sAreaLeavel=Util.choicArea();
       /* if(sAreaLeavel.equals("area_1")  ){
             String[] mTitles = {Constants.TITLE_INDEX,
                    Constants.TITLE_QUERY, Constants.TITLE_STATISTICS, Constants.TITLE_MINE};
             int[] mIconUnselectIds = {
                    R.mipmap.g_module_index_n,
                    R.mipmap.g_module_query_n, R.mipmap.g_module_statistics_n, R.mipmap.g_module_mine_n};
             int[] mIconSelectIds = {
                    R.mipmap.g_module_index_h,
                    R.mipmap.g_module_query_h, R.mipmap.g_module_statistics_h, R.mipmap.g_module_mine_h};


            for (int i = 0; i < mTitles.length; ++i) {
                if (i == 0) {
                    IndexDeanFragment indexFragment = new IndexDeanFragment();
                    mFragments.add(indexFragment);
//                mFragments.add(new WeChatFragment());
                }  else if (i == 1) {
//                OnlineChatingFragment  onlineChatingFragment = new OnlineChatingFragment();
//                mFragments.add(onlineChatingFragment);
                    QueryDataFragment queryDataFragment = new QueryDataFragment();
                    mFragments.add(queryDataFragment);

                } else if (i == 2) {
                    StatisticsFragment statisticsFragment = new StatisticsFragment();
                    mFragments.add(statisticsFragment);

                } else {
                    String title = mTitles[i];
                    mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
                }
            }


            for (int i = 0; i < mTitles.length; i++) {
                mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
            }

        }else if(sAreaLeavel.equals("area_3") || sAreaLeavel.equals("area_2")){
            for (int i = 0; i < mTitles.length; ++i) {
                if (i == 0) {
                    IndexDeanFragment indexFragment = new IndexDeanFragment();
                    mFragments.add(indexFragment);
//                mFragments.add(new WeChatFragment());
                } else if (i == 1) {
                    HandleBussiNewFragment inspectFragment = new HandleBussiNewFragment();
                    mFragments.add(inspectFragment);


                } else if (i == 2) {
//                OnlineChatingFragment  onlineChatingFragment = new OnlineChatingFragment();
//                mFragments.add(onlineChatingFragment);
                    QueryDataFragment queryDataFragment = new QueryDataFragment();
                    mFragments.add(queryDataFragment);

                } else if (i == 3) {
                    StatisticsFragment statisticsFragment = new StatisticsFragment();
                    mFragments.add(statisticsFragment);
//                OnlineChatingFragment onlineChatingFragment = new OnlineChatingFragment();
//                mFragments.add(onlineChatingFragment);
//                QueryDataFragment moduleListFragment = new QueryDataFragment();
//                mFragments.add(moduleListFragment);
//                MineFragment mineFragment = new MineFragment();
//                mFragments.add(mineFragment);
                } else {
                    String title = mTitles[i];
                    mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
                }
            }


            for (int i = 0; i < mTitles.length; i++) {
                mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
            }

        }else {
            String[] mTitles = {Constants.TITLE_INDEX,
                    Constants.TITLE_QUERY, Constants.TITLE_STATISTICS, Constants.TITLE_MINE};
            int[] mIconUnselectIds = {
                    R.mipmap.g_module_index_n,
                    R.mipmap.g_module_query_n, R.mipmap.g_module_statistics_n, R.mipmap.g_module_mine_n};
            int[] mIconSelectIds = {
                    R.mipmap.g_module_index_h,
                    R.mipmap.g_module_query_h, R.mipmap.g_module_statistics_h, R.mipmap.g_module_mine_h};


            for (int i = 0; i < mTitles.length; ++i) {
                if (i == 0) {
                    IndexDeanFragment indexFragment = new IndexDeanFragment();
                    mFragments.add(indexFragment);
//                mFragments.add(new WeChatFragment());
                }  else if (i == 1) {
//                OnlineChatingFragment  onlineChatingFragment = new OnlineChatingFragment();
//                mFragments.add(onlineChatingFragment);
                    QueryDataFragment queryDataFragment = new QueryDataFragment();
                    mFragments.add(queryDataFragment);

                } else if (i == 2) {
                    StatisticsFragment statisticsFragment = new StatisticsFragment();
                    mFragments.add(statisticsFragment);

                } else {
                    String title = mTitles[i];
                    mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
                }
            }






            for (int i = 0; i < mTitles.length; i++) {
                mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
            }
        }*/

       /* String[] mTitles = {Constants.TITLE_INDEX,
                Constants.TITLE_QUERY, Constants.TITLE_STATISTICS, Constants.TITLE_MINE};
        int[] mIconUnselectIds = {
                R.mipmap.g_module_index_n,
                R.mipmap.g_module_query_n, R.mipmap.g_module_statistics_n, R.mipmap.g_module_mine_n};
        int[] mIconSelectIds = {
                R.mipmap.g_module_index_h,
                R.mipmap.g_module_query_h, R.mipmap.g_module_statistics_h, R.mipmap.g_module_mine_h};
*/

        String[] mTitles = {Constants.TITLE_INDEX,
                Constants.TITLE_Scheduling, Constants.TITLE_NURSING, Constants.TITLE_MINE};
        int[] mIconUnselectIds = {
                R.mipmap.g_module_index_n,
                R.mipmap.g_module_query_n, R.mipmap.g_module_statistics_n, R.mipmap.g_module_mine_n};
        int[] mIconSelectIds = {
                R.mipmap.g_module_index_h,
                R.mipmap.g_module_query_h, R.mipmap.g_module_statistics_h, R.mipmap.g_module_mine_h};


        if (!IdentityManager.isDean(this)) {
            mTabLayout.setVisibility(View.GONE);
            flContent.setVisibility(View.VISIBLE);
            for (int i = 0; i < mTitles.length; ++i) {
                if (i == 0) {
                    IndexFragment indexFragment = new IndexFragment();
                    mFragments.add(indexFragment);
                    //                mFragments.add(new WeChatFragment());
                } else if (i == 1) {
                    //                OnlineChatingFragment  onlineChatingFragment = new OnlineChatingFragment();
                    //                mFragments.add(onlineChatingFragment);
                    //                SchedulingFragment schedulingFragment =new SchedulingFragment();
                    //                mFragments.add(schedulingFragment);

                    RoundFragment schedulingFragment = new RoundFragment();
                    //                QueryDataFragment queryDataFragment = new QueryDataFragment();
                    mFragments.add(schedulingFragment);

                } else if (i == 2) {
//                    NursingFragment fragment = new NursingFragment();
                    HealthListFragment fragment = HealthListFragment.newInstance();
                    mFragments.add(fragment);

                    //                StatisticsFragment statisticsFragment = new StatisticsFragment();
                    //                mFragments.add(statisticsFragment);

                } else {
                    SimpleCardFragment fragment = new SimpleCardFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("type","1");
                    fragment.setArguments(bundle);

                    mFragments.add(fragment);

//                    String title = mTitles[i];
//                    mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
                }
            }
        } else {
            mTabLayout.setVisibility(View.VISIBLE);
            flContent.setVisibility(View.GONE);

            mTitles = new String[]{Constants.TITLE_INDEX,
                    Constants.TITLE_ELDER, Constants.TITLE_BED, Constants.TITLE_FINANCE, Constants.TITLE_MINE};
            mIconUnselectIds = new int[]{
                    R.mipmap.g_module_index_n,
                    R.mipmap.g_module_query_n, R.mipmap.g_module_statistics_n, R.mipmap.g_module_finance_n, R.mipmap.g_module_mine_n};
            mIconSelectIds = new int[]{
                    R.mipmap.g_module_index_h,
                    R.mipmap.g_module_query_h, R.mipmap.g_module_statistics_h, R.mipmap.g_module_finance_h, R.mipmap.g_module_mine_h};
            for (int i = 0; i < mTitles.length; ++i) {
                if (i == 0) {
                    IndexDeanFragment fragment0 = new IndexDeanFragment();
                    mFragments.add(fragment0);
//                    String title = mTitles[i];
//                    mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
                } else if (i == 1) {
                    DeanCockpitElderFragment fragment = new DeanCockpitElderFragment();
                    mFragments.add(fragment);
                } else if (i == 2) {
                    DeanCockpitBedFragment fragment = new DeanCockpitBedFragment();
                    mFragments.add(fragment);
                } else if (i == 3) {
                    DeanCockpitFinanceFragment fragment = new DeanCockpitFinanceFragment();
                    mFragments.add(fragment);
                } else {
                    SimpleCardFragment fragment = new SimpleCardFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("type","0");
                    fragment.setArguments(bundle);

                    mFragments.add(fragment);

//                    String title = mTitles[i];
//                    mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
                }
            }

        }


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }


        mViewPager = ViewFindUtils.find(mDecorView, R.id.viewPager);
        mViewPager.setOffscreenPageLimit(Constants.VIEW_PAGER_OFF_SCREEN_NUMBER);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        /** with ViewPager */

        initTabData();

        if (mIndexSelectSubscription == null) {
            registerIndexSelectSubscription();
        }

        int index = Util.getWorkbenchIndexFromIntent(getIntent());
        if (index >= Constants.WORKBENCH_INDEX && index <= Constants.WORKBENCH_MINE) {
            switchToIndex(index);
        }


//        //两位数
//        mTabLayout.showMsg(0, 55);
//        mTabLayout.setMsgMargin(0, -5, 5);
//
//        //三位数
//        mTabLayout.showMsg(1, 100);
//        mTabLayout.setMsgMargin(1, -5, 5);
//
//        //设置未读消息红点
//        mTabLayout.showDot(2);
//        MsgView rtv_2_2 = mTabLayout.getMsgView(2);
//        if (rtv_2_2 != null) {
//            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
//        }
//
//        //设置未读消息背景
//        mTabLayout.showMsg(3, 5);
//        mTabLayout.setMsgMargin(3, 0, 5);
//        MsgView rtv_2_3 = mTabLayout.getMsgView(3);
//        if (rtv_2_3 != null) {
//            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
//        }

        //    mViewPager.setCurrentItem(0);

        checkPushNotificationExist(getIntent());

        checkNfcReadingInfo();

        Identity.shouldReadIdCard = false;
//        Identity.shouldReadIdCard = true;
        isDestroyedBySystem = false;
        SharedPreferencesUtil.setBoolean(this, Constants.IS_DESTROYED_BY_SYSTEM_KEY, false);

        hilightIndex();


    }

    int CAMERA_OK=998;

    private void initScanImageView(){
        Util.setViewClickListener(ll_scan, new Runnable() {
            @Override
            public void run() {

//                Intent  intent = new Intent();
//                intent.setClass(WorkbenchActivity.this, MacFragmentActivity.class);
//                startActivity(intent);

                if (Build.VERSION.SDK_INT>22){
                    if (ContextCompat.checkSelfPermission(WorkbenchActivity.this,
                            android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        //先判断有没有权限 ，没有就在这里进行权限的申请
                        ActivityCompat.requestPermissions(WorkbenchActivity.this,
                                new String[]{android.Manifest.permission.CAMERA},CAMERA_OK);

                    }else {
                        //说明已经获取到摄像头权限了 想干嘛干嘛
                        gotoScanQrcode();
                    }
                }else {
//这个说明系统版本在6.0之下，不需要动态获取权限。

                }


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 998:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
                    gotoScanQrcode();
                }else {
                    //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                    Toast.makeText(WorkbenchActivity.this,"请手动打开相机权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }


    private void checkNfcReadingInfo() {
        String readResult = getDeliveredStringByKey(Constants.NURSING_READ_RESULT_KEY);
        boolean isFromNfc = getDeliveredBooleanByKey(Constants.NURSING_IS_FROM_NFC_KEY);
        if (isFromNfc) {
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
                bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, isFromNfc);
                Util.gotoActivityWithBundle(this, NursingDetailActivity.class, bundle);
                finish();
            } else if (readRoomResult[0].equals("1")) {
                bundle.putString("roomid", readRoomResult[1]);
                bundle.putString("roomNo", readRoomResult[2]);
                bundle.putString("roomType", readRoomResult[3]);
                bundle.putString("roomExtra", "");
                bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, true);

                Util.gotoActivityWithBundle(this, RoundRoomDetailActivity.class, bundle);
                finish();
            }
        }
    }

    private void initSinglePixelKeepAliveProcess() {
        final ScreenManager screenManager = ScreenManager.getInstance(WorkbenchActivity.this);
        ScreenBroadcastListener listener = new ScreenBroadcastListener(this);
        listener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                screenManager.finishActivity();
                LogUtil.i("screen on, finish activity");
            }

            @Override
            public void onScreenOff() {
                screenManager.startActivity();
                LogUtil.i("screen off, start activity");
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mIndexSelectSubscription != null && mIndexSelectSubscription.hasSubscriptions()) {
            mIndexSelectSubscription.unsubscribe();
        }
    }

//    public void onEvent(NotificationClickEvent event) {
//        Message msg = event.getMessage();
//        UserInfo userInfo = (UserInfo) msg.getFromUser();
//        String title = Identity.getTargetDisplayTitle(userInfo);
//        String targetId = userInfo.getUserName();
//        ;
//        String appkey = msg.getFromAppKey();
//
//        if (Util.isCurrentChatActivity(this)) {
//            MessageListRefreshConfigurationBean bean = new MessageListRefreshConfigurationBean();
//            bean.setTitle(title);
//            bean.setTargetId(targetId);
//            bean.setAppkey(appkey);
//            RxBus.getDefault().post(Constants.EVENT_TYPE_SHOULD_REFRESH_CHAT_MESSAGE, bean);
//        } else if (Util.isAppVisibleToUser(this)) {
//            Intent intent = new Intent(this, MessageListActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra(Constants.CONV_TITLE, title);
//            intent.putExtra(Constants.TARGET_ID, targetId);
//            intent.putExtra(Constants.TARGET_APP_KEY, appkey);
//            startActivity(intent);
//        } else if (Util.isAppRunning(this)) {
//            Util.bringApplicationToForegroundFromChatMessageNotificationClick2(this, title, targetId, appkey);
////            Util.runApplicationFromScratch(this, Constants.PACKAGE_NAME);
//        } else {
//            Util.runApplicationFromScratch(this, Constants.PACKAGE_NAME);
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_workbench);


    }

    Random mRandom = new Random();

    private void initTabData() {
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
//                if (position == 0) {
//                    mTabLayout.showMsg(0, mRandom.nextInt(100) + 1);
////                    UnreadMsgUtils.show(mTabLayout.getMsgView(0), mRandom.nextInt(100) + 1);
//                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
                hilightPosition(position);
                reassignToolbar(mFragments.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }

    private void hilightPosition(int position){
        if (!IdentityManager.isDean(this)) {
            switch (position){
                case 0:
                    hilightIndex();
                    break;
                case 1:
                    hilightRoundRoom();
                    break;
                case 2:
                    hilightNursing();
                    break;
                case 3:
                    hilightMine();
                    break;
                default:
                    break;
            }
        }
    }

    public void reassignToolbar(Fragment fragment) {
       /* if (fragment instanceof IndexDeanFragment) {
            IndexDeanFragment indexFragment = (IndexDeanFragment) fragment;
            indexFragment.reassignToolbar();
        } else if (fragment instanceof HeyibanFragment) {
            HeyibanFragment heyibanFragment = (HeyibanFragment) fragment;
            heyibanFragment.reassignToolbar();
        } else if (fragment instanceof QueryDataFragment) {
            QueryDataFragment queryDataFragment = (QueryDataFragment) fragment;
            queryDataFragment.reassignToolbar();
        }*/
    }

    public void warnExit() {
        Util.showMaterialDialog(this, Constants.HINT, Constants.HINT_CONFIRM_EXITING, Constants.CANCEL,
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }, Constants.CONFIRM, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
//                        Util.logoutJMessageAndExitApplication();
//                        Util.exitApplication();
                    }
                }, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        warnExit();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        ToastUtil.show(this, "you selected color " + selectedColor);
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {

    }

    @Override
    public void onFileSelection(@NonNull FileChooserDialog dialog, @NonNull File file) {
        ToastUtil.show(this, "you selected file:" + file.getAbsolutePath());
    }

    @Override
    public void onFileChooserDismissed(@NonNull FileChooserDialog dialog) {

    }

    @Override
    public void onFolderSelection(@NonNull FolderChooserDialog dialog, @NonNull File folder) {
        ToastUtil.show(this, "you selected folder:" + folder.getAbsolutePath());
    }

    @Override
    public void onFolderChooserDismissed(@NonNull FolderChooserDialog dialog) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        MenuInflater inflater = getMenuInflater();
////        inflater.inflate(R.menu.option_menu, menu);
////        return true;
//        return super.onCreateOptionsMenu(menu);
//    }

    //  @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//         /*   case R.id.openlog:
//                ReadCardAPI.setlogflag(1);
//                return true;
//            case R.id.closelog:
//                ReadCardAPI.setlogflag(0);
//                return true;*/
////		case R.id.setip:
//            // Launch the DeviceListActivity to see devices and do scan
////			Intent serverIntent1 = new Intent(this, SetServerIPActivity.class);
////			startActivityForResult(serverIntent1, SETTING_SERVER_IP);
////			return true;
//          /*  case R.id.setuseotg:
//
//                return true;*/
//            case R.id.setusenfc:
//                onredo.setVisibility(View.GONE);
//                // Launch the DeviceListActivity to see devices and do scan
//                if (mAdapter != null) {
//                    //     title.setText("身份证识别NFC模式");
//                    mode = 2;
//                    if (mAdapter != null) startNFC_Listener();
//                } else {
//                    new android.app.AlertDialog.Builder(WorkbenchActivity.this)
//                            .setTitle("提示").setMessage("本机不支持功能！")
//                            .setPositiveButton("确定", null).show();
//                }
//                return true;
//            case R.id.setusebt:
//                // Launch the DeviceListActivity to see devices and do scan
//                //   title.setText("身份证识别蓝牙模式");
//                onredo.setVisibility(View.VISIBLE);
//                onredo.setEnabled(true);
//                onredo.setFocusable(true);
//                //   onredo.setBackgroundResource(R.drawable.sfz_dq);
//                mode = 3;
//                if (mAdapter != null) stopNFC_Listener();
//                if (!btAdapt.isEnabled()) {
//                    Intent enableIntent = new Intent(
//                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//                    // Otherwise, setup the chat session
//                }
//                ReadCardAPI.setmac(addressmac);
//                return true;
//            case R.id.setbtconfig:
//                // Launch the DeviceListActivity to see devices and do scan
////        	new AlertDialog.Builder(tcontext)
////			.setTitle("��ʾ" ).setMessage("�������ݳ�ʱ��" )
////			.setPositiveButton("ȷ��" ,  null ).show();
//                if (!btAdapt.isEnabled()) {
//                    Intent enableIntent = new Intent(
//                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//                    return true;
//                    // Otherwise, setup the chat session
//                }
//                Intent serverIntent2 = new Intent(this, DeviceListActivity.class);
//                startActivityForResult(serverIntent2, SETTING_BT);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Create a generic PendingIntent that will be deliver to this activity.
        // The NFC stack
        // will fill in the intent with the details of the discovered tag before
        // delivering to this activity.
/*		PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		// Setup an intent filter for all MIME based dispatches
//		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

//		try {
			ndef.addCategory(Intent.CATEGORY_DEFAULT);
//			ndef.addDataType("");
//		} catch (MalformedMimeTypeException e) {
//			throw new RuntimeException("fail", e);
//		}
		IntentFilter[] mFilters = new IntentFilter[] { ndef, };
*/        // Setup a tech list for all NfcF tags
//		String[][] mTechLists = new String[][] { new String[] { NfcB.class.getName() } };
//		mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
//		mAdapter.enableForegroundDispatch(this, pi, new IntentFilter[] { tagDetected }, mTechLists);
    }


    private String p = null;
    private Bitmap bitmap = null;

    public onClickIdCardListener onClickIdCardListener;

    public interface onClickIdCardListener {
        void sendIdCardInfo(IdentifyResult result);


    }

    public void setOnClickIdCardListener(onClickIdCardListener listener) {
        onClickIdCardListener = listener;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                if (path != null && path.size() > 0) {
                    p = path.get(0);
//                    onSelected();
                    bitmap = getImage(p);
                    //   imageView.setImageBitmap(bitmap);
                    Util.showGifProgressDialog(WorkbenchActivity.this);
                    TecentHttpUtil.uploadIdCard(BitMapUtils.bitmapToBase64(bitmap), "0", new TecentHttpUtil.SimpleCallBack() {
                        @Override
                        public void Succ(String res) {
                            IdentifyResult result = new Gson().fromJson(res, IdentifyResult.class);
                            if (result != null) {
                                if (result.getErrorcode() == 0) {
                                    // 识别成功
                                    Util.hideGifProgressDialog(WorkbenchActivity.this);

                                    //   showDialogInfo(result);

                                } else {
                                    Util.hideGifProgressDialog(WorkbenchActivity.this);
                                    Toast.makeText(WorkbenchActivity.this, "识别失败，请拍照清楚后重新识别", Toast.LENGTH_SHORT).show();
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
                        Util.showToast(WorkbenchActivity.this, "扫描结果为空");
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        getQrcodeInfo(result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Util.showToast(WorkbenchActivity.this, "解析二维码失败");
                    } else {
                        Util.showToast(WorkbenchActivity.this, "解析二维码失败");
                    }
                } else {
                    Util.showToast(WorkbenchActivity.this, "扫描结果为空");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";


    public OnSendidcardclickListner onSendidcardclickListner;

    public interface OnSendidcardclickListner {
        void sendId(String cardId, String idname, int sex);

        void failnote();
    }

    public void setOnSendidcardclickListner(OnSendidcardclickListner listner) {
        this.onSendidcardclickListner = listner;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static boolean isExit = false;

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandlers.sendEmptyMessageDelayed(0, 2000);
        } else {
            RxBus.getDefault().post(Constants.EVENT_TYPE_FINISH_ACTIVITY, true);
            finish();
            System.exit(0);
        }
    }

    Handler mHandlers = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

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
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(WorkbenchActivity.this);
        android.support.v7.app.AlertDialog dialogInfo = builder.setTitle("识别成功")
                .setMessage(sb.toString())
                .setPositiveButton("复制号码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("text", result.getId());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(WorkbenchActivity.this, "身份证号已复制到粘贴板", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        dialogInfo.show();

//        et_search.setText(result.getId());
//        et_search.setSelection(result.getId().length());

    }


}