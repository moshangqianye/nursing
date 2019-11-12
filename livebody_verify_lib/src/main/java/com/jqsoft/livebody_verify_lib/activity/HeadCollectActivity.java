package com.jqsoft.livebody_verify_lib.activity;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.activity.RegisterAndRecognizeActivity;
import com.arcsoft.arcfacedemo.common.Constants;
import com.arcsoft.arcfacedemo.faceserver.FaceServer;
import com.arcsoft.arcfacedemo.util.BitmapUtil;
import com.arcsoft.arcfacedemo.util.ConfigUtil;
import com.arcsoft.arcfacedemo.util.ImageUtil;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.eidlink.eidsdk.EIDPortraitActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jqsoft.livebody_verify_lib.R;
import com.jqsoft.livebody_verify_lib.bean.Constant;
import com.jqsoft.livebody_verify_lib.bean.GetRecognitionBasePhotoBean;
import com.jqsoft.livebody_verify_lib.bean.IDCardNumberVerificationBean;
import com.jqsoft.livebody_verify_lib.bean.PushFaceDataRequestBean;
import com.jqsoft.livebody_verify_lib.bean.PushFaceDataResultBean;
import com.jqsoft.livebody_verify_lib.bean.VerifyResultBean;
import com.jqsoft.livebody_verify_lib.bean.Version;
import com.jqsoft.livebody_verify_lib.callback.MyResultCallback;
import com.jqsoft.livebody_verify_lib.util.Base64Util;
import com.jqsoft.livebody_verify_lib.util.CommonDialog;
import com.jqsoft.livebody_verify_lib.util.DeleteFileUtil;
import com.jqsoft.livebody_verify_lib.util.PhotoDialog;
import com.jqsoft.livebody_verify_lib.util.ResponseInterceptor;
import com.jqsoft.livebody_verify_lib.util.StringUtils;
import com.jqsoft.livebody_verify_lib.util.Util;
import com.jqsoft.livebody_verify_lib.util.WebServiceUtils;
import com.kyleduo.switchbutton.SwitchButton;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.minivision.livebodylibrary.util.FaceDetector;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.jqsoft.livebody_verify_lib.util.Util.bitmapToBase64;
import static com.jqsoft.livebody_verify_lib.util.Util.getPhotoFileName;

/**
 * 头像采集
 * Created by Administrator on 2018-08-09.
 */

public class HeadCollectActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final int FACE_REGISTER_FAIL = 404;
    private int maxSelectNum = 1;// 图片最大可选数量
    private boolean isShow = true;
    private int selectType = FunctionConfig.TYPE_IMAGE;
    private boolean enablePreview = true;
    private boolean isPreviewVideo = true;
    private boolean theme = false;
    private boolean selectImageType = false;
    private int maxB = 0;
    private int compressW = 0;
    private int compressH = 0;
    private boolean isCompress = false;
    private boolean isCheckNumMode = false;
    private int compressFlag = 1;// 1 系统自带压缩 2 luban压缩
    private int themeStyle;
    private int previewColor, completeColor, previewBottomBgColor, previewTopBgColor, bottomBgColor, checkedBoxDrawable;
    private boolean mode = false;// 启动相册模式
    private int selectMode = FunctionConfig.MODE_SINGLE;
    private SwitchButton btn_changeCamera;
    FunctionOptions options;
    private String sCheckFacePhoto;
    private String sDeletePhoto;
    public static String COLLECT_HEAD_STATUS_KEY = "collectHeadStatusKey";
//    public static String VERIFY_CARD_NO_KEY="verifyCardNoKey";

    //    public static int IMAGE_SELECT_TYPE_CARD_FRONT=1;
//    public static int IMAGE_SELECT_TYPE_CARD_BACK=2;
    public static int IMAGE_SELECT_TYPE_FACE = 3;

    String cardFrontPath = "", /*cardBackPath, */
            facePath = "";
    String idNumber = "";
    String isOnlyFace = "";
    String orgCode = "";
    String submitUser = "";
    String sPersonID = "";
    String sUserName = "";
    String passinHeadBase64String = "";
    String sRemark = "";
    private static final int FACE_REGISTER_NO_BITMAP_FAIL = 405;
    int imageSelectType = IMAGE_SELECT_TYPE_FACE;
    private int SELECT_LIVE_HEAD = 600;
    private int REQUEST_FACE_ARCHIVE_CREATION_CODE = 700;
    private int AFFACE_HEAD = 800;
    private int TAKE_PHOTO = 100;
    private String mPhotoPath;
    private File mPhotoFile;
    private Uri photoPath;
    private Bitmap takePhotoBitmap;
    boolean isHeadOnlyFromCamera = false;
    private TextView delete_btn,apply_btn;
    LinearLayout llBack;
    LinearLayout llPassinHead;
    ImageView ivPassinHead;
    LinearLayout ll_SwBtn;
    //    ImageView ivCardFront;
//    ImageView ivCardBack;
    ImageView ivHead;
    ////    CheckBox cbLicense;
//    CheckBoxSample viewLicense;
    Button btnCollect;

    boolean isLoadHeadImageSuccess = false;

    RequestCall loadHeadImageCall;
    RequestCall call;
    RequestCall pushLiveBodyCall;
    int faceType = 0; // 0第一次(或者激活失败)  1虹软   2 eid（出现崩溃）
    private int iClearDate = 0;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    private Bitmap mainbitmap;
    /**
     * 虹软人脸参数
     */
    private int ACTION_SELECTION = 0;
    private double DIFFERENCE_VALUE = 10;
    private double SIMILAR_NUMBER = 0.1;
    private int FRAME_NUMBER = 50;
    static String bitmappath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_collect_layout);
        initData();
        initView();
        Log.d("faceType", String.valueOf(faceType));
    }

    private void populateData() {
        Intent intent = getIntent();
        if (intent != null) {
            idNumber = intent.getStringExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_NUMBER_KEY);
            sPersonID = intent.getStringExtra(CardImageLiveFaceVerifyActivity.SPERSON_ID);
            sUserName = intent.getStringExtra(CardImageLiveFaceVerifyActivity.SUERSENAME);
            isOnlyFace = intent.getStringExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ISMOTHER_KEY);
            idNumber = Util.trimString(idNumber);
            orgCode = intent.getStringExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ORG_CODE_KEY);
            orgCode = Util.trimString(orgCode);
            submitUser = intent.getStringExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_SUBMIT_USER_KEY);
            submitUser = Util.trimString(submitUser);
        }
    }

    private void initData() {
        initOkHttpUtils();
        populateData();
        initPictureLibrary();
//        deleteArchive();
    }

    String sMessage, Applymes;
    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }

        try {
            bitmappath = savePath + Util.getPhotoFileName() + ".jpg";
            filePic = new File(bitmappath);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    private void initView() {
        ll_SwBtn = (LinearLayout) findViewById(R.id.ll_SwBtn);

        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked();
            }
        });

        llPassinHead = (LinearLayout) findViewById(R.id.ll_passin_head);
        ivPassinHead = (ImageView) findViewById(R.id.iv_passin_head);
        btn_changeCamera = (SwitchButton) findViewById(R.id.btn_changeCamera);
        delete_btn = (TextView) findViewById(R.id.delete_btn);
        apply_btn = (TextView)findViewById(R.id.apply_btn);
        ivHead = (ImageView) findViewById(R.id.iv_head);
        btnCollect = (Button) findViewById(R.id.btn_collect);
        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        faceType = userSettings.getInt("FaceType", 0);
        sCheckFacePhoto = userSettings.getString("sCheckFacePhoto", "");
        sDeletePhoto = userSettings.getString("sDeletePhoto", "");

        if(sDeletePhoto.equals("1")){
            delete_btn.setVisibility(View.VISIBLE);
        }else{
            delete_btn.setVisibility(View.GONE);
        }
        if(sCheckFacePhoto.equals("1")){
            apply_btn.setVisibility(View.VISIBLE);
        }else{
            apply_btn.setVisibility(View.GONE);
        }


        //强制使用eid把下面代码打开
//        faceType=2;
//        final SharedPreferences.Editor editor2 = userSettings.edit();
//        editor2.putInt("FaceType", 2);
//        editor2.apply();

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoDialog inputDialog = new PhotoDialog(0, HeadCollectActivity.this).builder().setTitle("申请删除底片")
                        .setShowReason(true)
                        .setPositiveBtn("确定", new PhotoDialog.OnPositiveListener() {
                            @Override
                            public void onPositive(View view, String inputMsg) {
                                if (TextUtils.isEmpty(inputMsg)) {
                                    Toast.makeText(HeadCollectActivity.this, "删除底片原因不能为空", Toast.LENGTH_SHORT).show();
                                } else {
                                    sRemark = inputMsg;
                                    new Thread(applyDelete).start();
                                }
                            }
                        })

                        .setNegativeBtn("取消", null)
                        .setContentMsg("");

                inputDialog.getContentView().setHint("请输入原因(必填)");
                inputDialog.getContentView().setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
                inputDialog.show();


            }
        });
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoDialog inputDialog = new PhotoDialog(1, HeadCollectActivity.this).builder().setTitle("申请人工审核")
                        .setShowReason(true)
                        .setPositiveBtn("确定", new PhotoDialog.OnPositiveListener() {
                            @Override
                            public void onPositive(View view, String inputMsg) {
                                if (TextUtils.isEmpty(inputMsg)) {
                                    Toast.makeText(HeadCollectActivity.this, "申请人工审核原因不能为空", Toast.LENGTH_SHORT).show();
                                } else {
                                    sRemark = inputMsg;
                                    if(takePhotoBitmap!=null){
                                        new Thread(applySend).start();
                                    }else{
                                        Toast.makeText(HeadCollectActivity.this, "请拍照后再申请", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        })
                        .setTakePhotoBtn(new PhotoDialog.OnTakePhotoListener() {
                            @Override
                            public void onTakePhoto(View view) {
                                IntetActivity();
                            }
                        }
                        )
                        .setNegativeBtn("取消", null)
                        .setContentMsg("");

                inputDialog.getContentView().setHint("请输入原因(必填)");
                inputDialog.getContentView().setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
                inputDialog.show();


            }
        });

        if (1 == faceType) {
            ll_SwBtn.setVisibility(View.VISIBLE);
            FaceServer.getInstance().init(this);
            ConfigUtil.setFtOrient(HeadCollectActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);

        } else {
            ll_SwBtn.setVisibility(View.INVISIBLE);
        }
        BitmapUtil.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        int CameraInfo = userSettings.getInt("CameraInfo", Camera.CameraInfo.CAMERA_FACING_BACK);
        if (Camera.CameraInfo.CAMERA_FACING_FRONT == CameraInfo) {
            btn_changeCamera.setChecked(false);
        } else {
            BitmapUtil.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);
            btn_changeCamera.setChecked(true);
        }
        btn_changeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences userSettings = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = userSettings.edit();
                if (BitmapUtil.getCameraId() == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    BitmapUtil.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);

                    editor.putInt("CameraInfo", Camera.CameraInfo.CAMERA_FACING_BACK);
                    editor.apply();
//                    changeCamera.setText("后置");
                } else {
                    BitmapUtil.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    editor.putInt("CameraInfo", Camera.CameraInfo.CAMERA_FACING_FRONT);
                    editor.apply();
//                    changeCamera.setText("前置");

                }

            }
        });
//        ivPassinHead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadHeadImageAccordingly();
//            }
//        });

        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                selectFaceImage();
                faceType = userSettings.getInt("FaceType", 0);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (1 == faceType) {
//                        if (cameraIsCanUse() ){
//                             selectLiveEidHead();
                        selectLiveHead();
//                        }else {
//                            String perms[] = new String[1];
//                            perms[0]=Manifest.permission.CAMERA;
//                            new AppSettingsDialog.Builder(HeadCollectActivity.this)
//                                    .build().show();
////                             EasyPermissions.requestPermissions(HeadCollectActivity.this,
////                                     "暂无相机权限！请手动添加权限后重试！",
////                                     0x0001, perms);
////                             EasyPermissions.requestPermissions(HeadCollectActivity.this,"需提供手机相机权限",1, Manifest.permission.CAMERA);
//                            Toast.makeText(HeadCollectActivity.this,"暂无相机权限！请手动添加权限后重试！",Toast.LENGTH_SHORT).show();
//
//                        }


                    } else {
                        selectLiveEidHead();
                    }


                } else {

                    selectLiveEidHead();
                }

            }
        });
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCollectButtonClicked();
            }
        });
        activeEngine();
        if (faceType != 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activeEngine();
//                clearFaces();
            } else {
                faceType = 2;
                final SharedPreferences userSettings1 = getSharedPreferences("setting", 0);
                final SharedPreferences.Editor editor1 = userSettings1.edit();
                editor1.putInt("FaceType", 2);
                editor1.apply();
            }
        }

//        new Thread(GetBasePhotoRunnable).start();
        loadIDCardNumberHeadImageInfo(idNumber);
        Log.i("jie01",idNumber+"idNumber----------------1");
    }

    public void clearFaces() {
        int faceNum = FaceServer.getInstance().getFaceNumber(this);
        if (faceNum == 0) {
//            Toast.makeText(this, com.arcsoft.arcfacedemo.R.string.no_face_need_to_delete, Toast.LENGTH_SHORT).show();
        } else {
            int deleteCount = FaceServer.getInstance().clearAllFaces(HeadCollectActivity.this);
//            Toast.makeText(HeadCollectActivity.this, deleteCount + " faces cleared!", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    /**
     * 激活引擎
     *
     * @param
     */
    public void activeEngine() {
        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        final SharedPreferences.Editor editor = userSettings.edit();
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if (faceType != 9) {
                    try {
                        FaceEngine faceEngine = new FaceEngine();
                        int activeCode = faceEngine.active(HeadCollectActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                        emitter.onNext(activeCode);

                    } catch (UnsatisfiedLinkError e) {
                        Log.d("subscribe: ", "subscribe: "+e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final SharedPreferences userSettings1 = getSharedPreferences("setting", 0);
                                final SharedPreferences.Editor editor1 = userSettings1.edit();
                                editor1.putInt("FaceType", 2);
                                editor1.apply();
//                        Toast.makeText(HeadCollectActivity.this, "活体引擎激活失败,请重新识别", Toast.LENGTH_SHORT).show();
//                        finish();

                            }
                        });
                    }
                }


            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
//                            showToast(getString(R.string.active_success));
                            Toast.makeText(HeadCollectActivity.this, "活体引擎激活成功", Toast.LENGTH_SHORT).show();
                            editor.putInt("FaceType", 1);
                            editor.apply();
                            ll_SwBtn.setVisibility(View.VISIBLE);

                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            editor.putInt("FaceType", 1);
                            editor.commit();
                            ll_SwBtn.setVisibility(View.VISIBLE);

//                            showToast(getString(R.string.already_activated));
                        } else {

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        editor.putInt("FaceType", 2);
                        editor.commit();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 返回true 表示可以使用 返回false表示不可以使用
     */
    public boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }
        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }

    private void onBackClicked() {
        returnFailure();
        deleteByFilePath(cardFrontPath);
        deleteByFilePath(facePath);
        finish();
    }

    private void refreshVerifyButtonStatus(boolean isChecked) {
        btnCollect.setEnabled(true);
//        btnCollect.setEnabled(isChecked);
    }

    private void initOkHttpUtils() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        long timeout = 35;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ResponseInterceptor())
                .addNetworkInterceptor(interceptor)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);

    }

    private String facebaseurl = "";
    private Bitmap headeidbitmap = null;
    private int flagShowImg = 0;

    private String faceFerture="";
    private void loadIDCardNumberHeadImageInfo(final String idNumber) {
//        clearFaces();
        Util.showGifProgressDialog(this, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                cancelLoadHeadImageNetworkRequest();
            }
        });


        long timestamp = Util.getTimeStamp();
        String timestampString = String.valueOf(timestamp);
        String token = Util.getProcessedToken(timestamp);


        Map<String, String> params = new HashMap<>();
//        params.put("card_front", cardFrontBase64String);
////        params.put("card_back", cardBackBase64String);
        params.put("acs_token", token);
        params.put("acs_time", timestampString);
        params.put("idnum", idNumber);

        Log.i("chenxu", "acs_token:" + token);
        Log.i("chenxu", "acs_time:" + timestampString);
        Log.i("chenxu", "idnum:" + idNumber);

//        Map<String, String> headers = new HashMap<>();
//        headers.put("signature", "");
//        headers.put("public_key", "6000000858322998-1");
        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        facebaseurl = userSettings.getString("face_base_url", "");
        Log.i("jie01",facebaseurl+"facebaseurl----------------1");
        if (TextUtils.isEmpty(Version.BASE_URL)) {
            Version.BASE_URL = facebaseurl;
        }

        String url = Version.BASE_URL + Version.VERIFICATION_URL;
        Log.i("jie01",url+"url----------------1");
        loadHeadImageCall = OkHttpUtils.post()//
//                .addFile("mFile", "agguigu-afu.jpe", file)//
                .url(url)//
                .params(params)//
//                .headers(headers)//
                .build();//
        loadHeadImageCall.execute(new MyResultCallback<IDCardNumberVerificationBean>() {

            @Override
            public void onSuccess(IDCardNumberVerificationBean response) {
                Util.hideGifProgressDialog(HeadCollectActivity.this);

                if (response != null && "1".equals(response.getRespond())) {
                    if(sCheckFacePhoto.equals("1")){
                        apply_btn.setVisibility(View.VISIBLE);
                    }else{
                        apply_btn.setVisibility(View.GONE);
                    }
//                    delete_btn.setVisibility(View.VISIBLE);
                    isLoadHeadImageSuccess = true;
                    Log.i("chenxu", "HeadCollectActivity onSuccess");
                    String idNumExist = response.getIdnumExist();
                    if ("true".equals(idNumExist)) {
                        faceFerture=response.getHeadFeature();


                        String headBase64String = response.getHeadImg();
                        mainbitmap = Base64Util.base64ToBmp(headBase64String);
//                        saveBitmap(HeadCollectActivity.this,mainbitmap);
                        headeidbitmap = mainbitmap;
                        takePhotoBitmap=mainbitmap;
                        if (faceType == 2) {

                        } else {
                            if(TextUtils.isEmpty(faceFerture)){
                                registerFaceSixian(mainbitmap);
                            }else {
                                registerFaceFeature();
                            }

                        }
                        ivPassinHead.setImageBitmap(mainbitmap);

                    } else {
                        requestFaceArchiveCreation();

//                        showNotificationDialog("不存在对应身份证号码的人脸档案",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        requestFaceArchiveCreation();
//                                    }
//                                });

                    }
                } else {
                    isLoadHeadImageSuccess = false;
                    String suffix = "";
                    if (response != null) {
                        String message = response.getMessage();
                        message = Util.trimString(message);
                        suffix = ":" + message;
                    }
                    Log.i("jie01", suffix+"suffix------------------1");
                    String ultimateString = "加载头像失败" + suffix;
                    Log.i("chenxu", "HeadCollectActivity failure");
                    showNotificationDialog(ultimateString, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                              returnFailure();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                isLoadHeadImageSuccess = false;

                Log.i("chenxu", "HeadCollectActivity onFailure:" + e.getMessage());
                Util.hideGifProgressDialog(HeadCollectActivity.this);
                Log.i("jie01", e.getMessage()+"message------------------1");
                String suffix = "";
                String ultimateString = "加载头像失败" + suffix;
                showNotificationDialog(ultimateString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        returnFailure();
                    }
                });
            }
        });
    }


    private void collect() {
        Util.showGifProgressDialog(this, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                cancelNetworkRequest();
            }
        });

//        String cardFrontBase64String = Base64Util.imageToBase64(cardFrontPath);
////        String cardBackBase64String = Base64Util.imageToBase64(cardBackPath);
        String faceBase64String = Base64Util.imageToBase64(facePath);

        long timestamp = Util.getTimeStamp();
        String timestampString = String.valueOf(timestamp);
        String token = Util.getProcessedToken(timestamp);


        Map<String, String> params = new HashMap<>();
//        params.put("card_front", cardFrontBase64String);
////        params.put("card_back", cardBackBase64String);
        params.put("acs_token", token);
        params.put("acs_time", timestampString);
        params.put("idnum", idNumber);
        params.put("head_base64", faceBase64String);

        Log.i("chenxu", "acs_token:" + token);
        Log.i("chenxu", "acs_time:" + timestampString);
        Log.i("chenxu", "idnum:" + idNumber);

        final String hint = "。请拍摄更清晰的照片再提交。";

//        Map<String, String> headers = new HashMap<>();
//        headers.put("signature", "");
//        headers.put("public_key", "6000000858322998-1");
        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        facebaseurl = userSettings.getString("face_base_url", "");

        if (TextUtils.isEmpty(Version.BASE_URL)) {
            Version.BASE_URL = facebaseurl;
        }

        String url = Version.BASE_URL + Version.COMPARE_URL;

        call = OkHttpUtils.post()//
//                .addFile("mFile", "agguigu-afu.jpe", file)//
                .url(url)//
                .params(params)//
//                .headers(headers)//
                .build();//
        call.execute(new MyResultCallback<VerifyResultBean>() {

            @Override
            public void onSuccess(VerifyResultBean response) {
                Util.hideGifProgressDialog(HeadCollectActivity.this);

                if (response != null && "1".equals(response.getRespond())) {
                    returnSuccess();
                    Log.i("chenxu", "HeadCollectActivity onSuccess");
                    showToast("保存成功");
                } else {
                    returnFailure();
                    String suffix = "";
                    if (response != null) {
                        String message = response.getMessage();
                        message = Util.trimString(message);
                        suffix = message;

                        if (message.contains("人脸档案不存在")) {
                            requestFaceArchiveCreation();
                            return;
                        }
                    }
                    Log.i("chenxu", "HeadCollectActivity failure");
                    showNotificationDialog("保存失败:" + suffix, null);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("chenxu", "HeadCollectActivity onFailure:" + e.getMessage());
                Util.hideGifProgressDialog(HeadCollectActivity.this);

                returnFailure();
//                        Log.i("chenxu", "HeadCollectActivity onFailure");
                String s = "保存失败:" + e.getMessage();
                showNotificationDialog(s, null);
            }
        });
    }


    private void requestFaceArchiveCreation() {
        Intent intent = new Intent(this, CardImageLiveFaceVerifyActivity.class);
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_NUMBER_KEY, idNumber);
        intent.putExtra(CardImageLiveFaceVerifyActivity.SPERSON_ID, sPersonID);
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ISMOTHER_KEY, isOnlyFace);
        intent.putExtra(CardImageLiveFaceVerifyActivity.SUERSENAME, sUserName);
//        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
//        startActivity(intent);
        startActivityForResult(intent, REQUEST_FACE_ARCHIVE_CREATION_CODE);
//        finish();
    }

    private boolean flageid = false;

    private void returnSuccess() {
//        pushFaceVerificationData(idNumber, orgCode, submitUser );
        flageid = true;
        new Thread(runnable).start();

    }

    private void returnFailure() {
        Intent intent = new Intent();
        intent.putExtra(COLLECT_HEAD_STATUS_KEY, false);
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_NUMBER_KEY, idNumber);
        intent.putExtra(CardImageLiveFaceVerifyActivity.SPERSON_ID, sPersonID);
        intent.putExtra(CardImageLiveFaceVerifyActivity.SUERSENAME, sUserName);
//        intent.putExtra(CardImageLiveFaceVerifyActivity.VERIFY_CARD_NO_KEY, idNumber);
        setResult(RESULT_CANCELED, intent);

    }

    private void onCollectButtonClicked() {
       /* if (!viewLicense.isChecked()){
//        if (!cbLicense.isChecked()){
            showToast("请同意许可协议");
        } else
        if (!isFilePathValid(cardFrontPath)){
            showToast("身份证正面照不能为空");
        } else if (!isFilePathValid(cardBackPath)){
            showToast("身份证背面照不能为空");
        } else*/
        if (!isFilePathValid(facePath)) {
            showToast("头像不能为空");
        } else {

            if (faceType != 1) {
                collect();
            } else {
                Util.showGifProgressDialog(HeadCollectActivity.this);
                flageid = false;
                //new Thread(runnable).start();
                verify();
//                returnSuccess();
            }
//
//            returnSuccess
//
        }
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void showNotificationDialog(String msg, DialogInterface.OnClickListener positiveListener) {
        try {
            if (!isFinishing()) {
                new AlertDialog.Builder(this)
                        .setTitle("")
                        .setMessage(msg)
                        .setPositiveButton("确定", positiveListener)
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isFilePathValid(String filePath) {
        filePath = Util.trimString(filePath);
        File file = new File(filePath);
        if (!TextUtils.isEmpty(filePath) && file.exists()) {
            return true;
        } else {
            return false;
        }
    }


    private void selectLiveHead() {
//        clearFaces();
        imageSelectType = IMAGE_SELECT_TYPE_FACE;

        Intent intent = new Intent(HeadCollectActivity.this, RegisterAndRecognizeActivity.class);

//        bitmappath="/sdcard/dskqxt/pi/2019-03-27_16-24-49.jpg";
//        if (!new File(bitmappath).exists()) {
////            saveBitmapToFile(mainbitmap,bitmappath);
//            saveBitmap(HeadCollectActivity.this,mainbitmap);
//            /*saveBitmap()*/
//        }
        intent.putExtra("path", bitmappath);
        intent.putExtra("idNumber", idNumber);
        intent.putExtra("formWay", "HeadCollectActivity");
        intent.putExtra("faceFeatrue", faceFerture);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, AFFACE_HEAD);

    }

    private void selectLiveEidHead() {
        imageSelectType = IMAGE_SELECT_TYPE_FACE;
        FaceDetector.init(HeadCollectActivity.this);
        Intent intent = new Intent(HeadCollectActivity.this, EIDPortraitActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SELECT_LIVE_HEAD);

    }

    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
//        origin.recycle();
        return newBM;
    }

    private void selectFaceImage() {
        imageSelectType = IMAGE_SELECT_TYPE_FACE;
        if (isHeadOnlyFromCamera) {
            PictureConfig.getInstance().init(options).startOpenCamera(HeadCollectActivity.this);
        } else {
            PictureConfig.getInstance().init(options).openPhoto(this, resultCallback);
        }

//        PictureConfig.getInstance().init(options).openPhoto(this, resultCallback);

    }

    public static boolean saveBitmapToFile(Bitmap bitmap, String filepath) {
        boolean success = false;
        try {
            File file = new File(filepath);//将要保存图片的路径
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    private int iAPPFaceNoPassCount = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == FunctionConfig.CAMERA_RESULT) {
                if (/*isHeadOnlyFromCamera &&*/ imageSelectType == IMAGE_SELECT_TYPE_FACE) {
                    if (data != null) {
                        List<LocalMedia> mediaList = (List<LocalMedia>) data.getSerializableExtra(FunctionConfig.EXTRA_RESULT);
                        if (mediaList != null && mediaList.size() > 0) {
                            LocalMedia lm = mediaList.get(0);
                            onSelectOneMedia(lm);
                        }

                    }

                }

            }
            if(requestCode ==  TAKE_PHOTO){
                 Uri uri = photoPath;
                 Log.i("jie01",uri+"uri-------");
                 String path = Util.getRealFilePath(HeadCollectActivity.this, uri);
                if (path != null) {
                    takePhotoBitmap = Util.getImage(path);

               }


            }
        }


        if (requestCode == AFFACE_HEAD) {

            //虹软识别人脸采集返回
            Log.d("90119", String.valueOf(resultCode));
            if (resultCode == 90119 || resultCode == 90115) {
                Util.clearAppData(HeadCollectActivity.this);
                Util.clearAllCache(HeadCollectActivity.this);
            } else if (resultCode == 1001) {
                SharedPreferences userSettings = getSharedPreferences("setting", 0);
                String sAPPFaceNoPassCount = userSettings.getString("iAPPFaceNoPassCount", "");

                if (TextUtils.isEmpty(sAPPFaceNoPassCount)) {
                    try {
                        iAPPFaceNoPassCount = Integer.parseInt(sAPPFaceNoPassCount);
                    } catch (Exception e) {
                        iAPPFaceNoPassCount = 100;
                    }

                } else {
                    iAPPFaceNoPassCount = 100;
                }


                int iComparisonCount = data.getIntExtra("iComparisonCount", 0);
                //设置结果显示框的显示数值
                if (iComparisonCount > iAPPFaceNoPassCount) {
//                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HeadCollectActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
//                    builder.setTitle("提示");
//                    builder.setMessage("系统智能检测，首次采集底片无法识别人脸特征，必须重新采集后才可以开展后续工作!");
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface dialog, int which) {
//                            deleteArchive();
//                        }
//                    });
//
//                    android.app.AlertDialog dialog = builder.create();
//                    dialog.setCancelable(false);
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.show();

                    showDialog();
//                    Toast.makeText(getApplicationContext(),"test"+iComparisonCount,Toast.LENGTH_LONG).show();
                } else {
                    showDialog();
                }


//                Toast.makeText(getApplicationContext(),"超市啦",Toast.LENGTH_LONG).show();

            } else {
                String img_code = String.valueOf(resultCode);//1为真脸   0为假脸  预留
                Log.d("tag", "img_code: " + img_code);
//                if ("1".equals(img_code) && str64!=null && !TextUtils.isEmpty(str64)){
                if ("1".equals(img_code)) {

//                     String   path = Util.getPreferExternalFolderPath(HeadCollectActivity.this, "live_body_face");
//                        String filename = Util.getPhotoFileName();
//                        path+=File.separator+filename;
//                    Bitmap bitmap = Base64Util.base64ToBmp(str64);
                    SharedPreferences userSettings = getSharedPreferences("setting", 0);
                    bitmappath = userSettings.getString("bitmappath", "");
                    int mOrientation = userSettings.getInt("mOrientation", 0);
                    Bitmap bitmap = BitmapFactory.decodeFile(bitmappath);
                    int CameraInfo = userSettings.getInt("CameraInfo", Camera.CameraInfo.CAMERA_FACING_BACK);
//                    refreshUI(bitmappath, bitmap);

                    if (bitmap != null) {
                        if (Camera.CameraInfo.CAMERA_FACING_BACK == CameraInfo) {
                            bitmap = rotateBitmap(bitmap, 90 + mOrientation);
                            saveBitmapToFile(bitmap, bitmappath);
                        } else {
                            bitmap = rotateBitmap(bitmap, -90 - mOrientation);
                            saveBitmapToFile(bitmap, bitmappath);
                        }
                    }
                    isLoadHeadImageSuccess = false;
                    compressBitmap();
                    refreshUI(bitmappath, bitmap);
                    SharedPreferences.Editor editor = userSettings.edit();
                    editor.putInt("mOrientation", 0);

                } else {
                    refreshUI("", null);

                }

            }


        }
        if (requestCode == SELECT_LIVE_HEAD) {    //eid识别人脸采集返回

            try {
                assert data != null;
                String str64 = data.getStringExtra("eid_img");//照片
                String img_code = data.getStringExtra("eid_img_code");//1为真脸   0为假脸  预留
                if ("1".equals(img_code) && str64 != null && !TextUtils.isEmpty(str64)) {
                    String path = Util.getPreferExternalFolderPath(HeadCollectActivity.this, "live_body_face");
                    String filename = getPhotoFileName();
                    path += File.separator + filename;
                    Bitmap bitmap = Base64Util.base64ToBmp(str64);
                    boolean success = Util.saveBitmapToFile(bitmap, path);
                    if (success) {
                        refreshUI(path, bitmap);
                    } else {
                        refreshUI("", null);
                    }

                } else {
                    refreshUI("", null);
//                    showNotificationDialog("获取活体人脸失败，请重新采集。", null);
//                    SharedPreferences userSettings = getSharedPreferences("setting", 0);
//                    SharedPreferences.Editor editor = userSettings.edit();
//                    editor.putInt("FaceType", 2);
//                    editor.commit();
//                    ll_SwBtn.setVisibility(View.INVISIBLE);
//                    selectLiveEidHead();
                }
            } catch (Exception e) {
                e.printStackTrace();
                refreshUI("", null);
                showNotificationDialog(e.getMessage(), null);
                SharedPreferences userSettings = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = userSettings.edit();
                editor.putInt("FaceType", 2);
                editor.apply();
                ll_SwBtn.setVisibility(View.INVISIBLE);
//                selectLiveEidHead();
            }

        }
        if (requestCode == REQUEST_FACE_ARCHIVE_CREATION_CODE) {
            try {
                assert data != null;
                boolean isCreationSuccess = data.getBooleanExtra(CardImageLiveFaceVerifyActivity.VERIFY_STATUS_KEY, false);
                String headImagePath = data.getStringExtra(CardImageLiveFaceVerifyActivity.PASSOUT_HEAD_IMAGE_PATH_KEY);
                headImagePath = Util.trimString(headImagePath);
                cardFrontPath = headImagePath;
                if (isCreationSuccess) {
                    showToast("人脸档案创建成功");
//                    clearFaces();

                    Bitmap bitmap = BitmapFactory.decodeFile(headImagePath);
//                    saveBitmap(HeadCollectActivity.this,bitmap);
                    refreshCapturedImage(headImagePath, bitmap);
//                    deleteByFilePath(cardFrontPath);
//                    pushFaceVerificationData(idNumber, orgCode, submitUser );
                } else {
                    showToast("人脸档案创建失败");
                    finish();
//                    showNotificationDialog("人脸档案创建失败", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("人脸档案创建失败");
                finish();
//                showNotificationDialog("人脸档案创建失败", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
            }
        }

    }


    private void showDialog() {

        new CommonDialog(HeadCollectActivity.this).builder().setTitle("人脸识别超时提示")
                .setContentMsg("请避免反光或者太暗的环境拍摄人脸")
                .setNegativeBtn("退出", new CommonDialog.OnNegativeListener() {
                    @Override
                    public void onNegative(View view) {
                        finish();
                    }
                })
                .setPositiveBtn("重试一下", new CommonDialog.OnPositiveListener() {
                    @Override
                    public void onPositive(View view) {
//                        Bitmap bm =((BitmapDrawable) ((ImageView) ivPassinHead).getDrawable()).getBitmap();
//                        if(bm==null){
//                            bm=  headbitmap;
//                        }
//
//
//                        registerFace(bm);
                        selectLiveHead();
                    }
                }).show();
    }


    @Override
    public void onBackPressed() {
        onBackClicked();
//        super.onBackPressed();
    }


    private String baseurl = "";

    //张恒回调接口
    private void pushFaceVerificationData(String idNumber, String orgCode, String submitUser) {
        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        baseurl = userSettings.getString("face_erd_baseurl", "");

        if (TextUtils.isEmpty(Version.PUSH_FACE_DATA_BASE_URL)) {
            Version.PUSH_FACE_DATA_BASE_URL = baseurl;
        }

        String url = Version.PUSH_FACE_DATA_BASE_URL + Version.PUSH_FACE_DATA_CONCRETE;
//        String cardNo = Identity.getCardNo();
        PushFaceDataRequestBean requestBean = new PushFaceDataRequestBean();
        requestBean.setIdNo(idNumber);
        requestBean.setOrgCode(orgCode);
        requestBean.setSubmitUser(submitUser);
        requestBean.setCollectionType("1");
        requestBean.setMsg("验证成功");
        requestBean.setResult("1");

        String json = new Gson().toJson(requestBean);


//        Util.showGifProgressDialog(this);
        Util.showGifProgressDialog(this, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                cancelPushLiveBodyDataRequest();
            }
        });
        pushLiveBodyCall = OkHttpUtils.postString().url(url).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build();
        pushLiveBodyCall.execute(new MyResultCallback<PushFaceDataResultBean>() {

            @Override
            public void onSuccess(PushFaceDataResultBean bean) {
                String result = new Gson().toJson(bean);
                String message = "";
                try {
                    JSONObject ss = new JSONObject(result);
                    message = "回调失败:" + ss.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("chenxu", "pushFaceVerificationData onSuccess result:" + result);
//                Util.decodeBase64Bean(bean);

                if (bean != null && "0".equals(bean.getCode())) {
                    showToast("上传活体验证数据成功");
                    succeedAndReturnResult();

                    if (faceType != 1) {
                        finish();
                    } else {
                        Util.hideGifProgressDialog(HeadCollectActivity.this);
                        succeedAndReturnResult();
                        btnCollect.setClickable(true);
                        Toast.makeText(HeadCollectActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                        finish();
                    }

//
                } else {
                    showNotificationDialog(message, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                String result = "回调失败:" + e.getMessage();
                Log.i("chenxu", "pushFaceVerificationData onFailure result:" + result);
                Util.hideGifProgressDialog(HeadCollectActivity.this);
                showNotificationDialog(result, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }
                });
            }
        });
    }

    private void succeedAndReturnResult() {
        Intent intent = new Intent();
        intent.putExtra(COLLECT_HEAD_STATUS_KEY, true);
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_NUMBER_KEY, idNumber);
        intent.putExtra(CardImageLiveFaceVerifyActivity.SPERSON_ID, sPersonID);
        intent.putExtra(CardImageLiveFaceVerifyActivity.SUERSENAME, sUserName);
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ORG_CODE_KEY, orgCode);
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_SUBMIT_USER_KEY, submitUser);
//        intent.putExtra(CardImageLiveFaceVerifyActivity.VERIFY_CARD_NO_KEY, idNumber);
        setResult(RESULT_OK, intent);
//        finish();

    }

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
        }

        @Override
        public void onSelectSuccess(LocalMedia media) {


            onSelectOneMedia(media);
        }
    };

    private void onSelectOneMedia(LocalMedia media) {
        String paths = Util.trimString(media.getCompressPath());
        if (TextUtils.isEmpty(paths)) {
            paths = Util.trimString(media.getPath());
        }
        String temp[] = paths.replaceAll("\\\\", "/").split("/");
        if (temp.length > 1) {
            String aFileName = temp[temp.length - 1];
            media.setFileName(aFileName);
        }

//            String s1 = Base64Util.imageToBase64(paths);
//        clearFaces();

        Bitmap bitmap = BitmapFactory.decodeFile(paths);
        saveBitmap(HeadCollectActivity.this, bitmap);
        refreshUI(paths, bitmap);
    }

    private Bitmap eidbitmap = null;

    private void refreshUI(String path, Bitmap bitmap) {
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
       /* if (imageSelectType==IMAGE_SELECT_TYPE_CARD_FRONT){
            cardFrontPath=path;
            ivCardFront.setImageBitmap(bitmap);
        } else if (imageSelectType==IMAGE_SELECT_TYPE_CARD_BACK){
            cardBackPath=path;
            ivCardBack.setImageBitmap(bitmap);
        } else*/
//       if (imageSelectType==IMAGE_SELECT_TYPE_FACE){
//            facePath=path;
//            ivHead.setImageBitmap(bitmap);

        deleteByFilePath(facePath);
        facePath = path;
        if (bitmap != null) {
            eidbitmap = bitmap;
            ivHead.setImageBitmap(bitmap);
        } else {
            eidbitmap = null;
            ivHead.setImageResource(R.mipmap.cf_click_to_collect);
        }

//        }
    }

    private void refreshCapturedImage(String path, Bitmap bitmap) {
        if (bitmap != null) {

            if (faceType == 2) {

            } else {
                if(TextUtils.isEmpty(faceFerture)){
                    registerFace(bitmap);
                }else {
                    registerFaceFeature();
                }

            }

            headeidbitmap = bitmap;
            ivPassinHead.setImageBitmap(bitmap);
        } else {
            ivPassinHead.setImageResource(R.mipmap.cf_face);
        }

    }

    private void deleteByFilePath(String filePath) {
        filePath = Util.trimString(filePath);
        DeleteFileUtil.delete(filePath);
    }


    private void initPictureLibrary() {
        // 进入相册
        /**
         * type --> 1图片 or 2视频
         * copyMode -->裁剪比例，默认、1:1、3:4、3:2、16:9
         * maxSelectNum --> 可选择图片的数量
         * selectMode         --> 单选 or 多选
         * isShow       --> 是否显示拍照选项 这里自动根据type 启动拍照或录视频
         * isPreview    --> 是否打开预览选项
         * isCrop       --> 是否打开剪切选项
         * isPreviewVideo -->是否预览视频(播放) mode or 多选有效
         * ThemeStyle -->主题颜色
         * CheckedBoxDrawable -->图片勾选样式
         * cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
         * cropH-->裁剪高度 值不能小于100
         * isCompress -->是否压缩图片
         * setEnablePixelCompress 是否启用像素压缩
         * setEnableQualityCompress 是否启用质量压缩
         * setRecordVideoSecond 录视频的秒数，默认不限制
         * setRecordVideoDefinition 视频清晰度  Constants.HIGH 清晰  Constants.ORDINARY 低质量
         * setImageSpanCount -->每行显示个数
         * setCheckNumMode 是否显示QQ选择风格(带数字效果)
         * setPreviewColor 预览文字颜色
         * setCompleteColor 完成文字颜色
         * setPreviewBottomBgColor 预览界面底部背景色
         * setBottomBgColor 选择图片页面底部背景色
         * setCompressQuality 设置裁剪质量，默认无损裁剪
         * setSelectMedia 已选择的图片
         * setCompressFlag 1为系统自带压缩  2为第三方luban压缩
         * 注意-->type为2时 设置isPreview or isCrop 无效
         * 注意：Options可以为空，默认标准模式
         */
        String b = "50";// 压缩最大大小 单位是b

        if (!isNull(b)) {
            maxB = Integer.parseInt(b);
        }

        if (!isNull("200") && !isNull("200")) {
            compressW = Integer.parseInt("200");
            compressH = Integer.parseInt("200");
        }

        if (theme) {
            // 设置主题样式
            themeStyle = ContextCompat.getColor(this, R.color.blue);
        } else {
            themeStyle = ContextCompat.getColor(this, R.color.bar_grey);
        }

        if (selectImageType) {
            checkedBoxDrawable = R.drawable.select_cb;
        } else {
            checkedBoxDrawable = 0;
        }

        if (isCheckNumMode) {
            // QQ 风格模式下 这里自己搭配颜色
            previewColor = ContextCompat.getColor(this, R.color.blue);
            completeColor = ContextCompat.getColor(this, R.color.blue);
        } else {
            previewColor = ContextCompat.getColor(this, R.color.tab_color_true);
            completeColor = ContextCompat.getColor(this, R.color.tab_color_true);
        }
//                    selectMedia.clear();
//                    adapter.notifyDataSetChanged();
        options = new FunctionOptions.Builder()
                .setType(selectType) // 图片or视频 FunctionConfig.TYPE_IMAGE  TYPE_VIDEO
                .setCompress(true) //是否压缩
                .setEnablePixelCompress(true) //是否启用像素压缩
                .setEnableQualityCompress(true) //是否启质量压缩
                .setMaxSelectNum(maxSelectNum) // 可选择图片的数量
                .setMinSelectNum(0)// 图片或视频最低选择数量，默认代表无限制
                .setSelectMode(selectMode) // 单选 or 多选
                .setShowCamera(isShow) //是否显示拍照选项 这里自动根据type 启动拍照或录视频
                .setEnablePreview(enablePreview) // 是否打开预览选项
                .setPreviewVideo(isPreviewVideo) // 是否预览视频(播放) mode or 多选有效
                .setCheckedBoxDrawable(checkedBoxDrawable)
                .setRecordVideoDefinition(FunctionConfig.HIGH) // 视频清晰度
                .setRecordVideoSecond(10) // 视频秒数
                .setVideoS(0)// 查询多少秒内的视频 单位:秒
                .setCustomQQ_theme(0)// 可自定义QQ数字风格，不传就默认是蓝色风格
                .setGif(false)// 是否显示gif图片，默认不显示
                .setMaxB(50) // 压缩最大值 例如:200kb  就设置202400，202400 / 1024 = 200kb
                .setPreviewColor(previewColor) //预览字体颜色
                .setCompleteColor(completeColor) //已完成字体颜色
                .setPreviewBottomBgColor(previewBottomBgColor) //预览图片底部背景色
                .setPreviewTopBgColor(previewTopBgColor)//预览图片标题背景色
                .setBottomBgColor(bottomBgColor) //图片列表底部背景色
                .setGrade(Luban.THIRD_GEAR) // 压缩档次 默认三档
                .setCheckNumMode(isCheckNumMode)
                .setCompressQuality(80) // 图片裁剪质量,默认无损
                .setImageSpanCount(4) // 每行个数
                .setSelectMedia(new ArrayList<LocalMedia>()) // 已选图片，传入在次进去可选中，不能传入网络图片
//                            .setSelectMedia(selectMedia) // 已选图片，传入在次进去可选中，不能传入网络图片
                .setCompressFlag(2) // 1 系统自带压缩 2 luban压缩
                .setCompressW(0) // 压缩宽 如果值大于图片原始宽高无效
                .setCompressH(0) // 压缩高 如果值大于图片原始宽高无效
                .setThemeStyle(themeStyle) // 设置主题样式
                .setNumComplete(false) // 0/9 完成  样式
                .setClickVideo(false)// 开启点击声音
//                            .setPicture_title_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题字体颜色
//                            .setPicture_right_color(ContextCompat.getColor(MainActivity.this, R.color.black)) // 设置标题右边字体颜色
//                            .setLeftBackDrawable(R.mipmap.back2) // 设置返回键图标
//                            .setStatusBar(ContextCompat.getColor(MainActivity.this, R.color.white)) // 设置状态栏颜色，默认是和标题栏一致
//                            .setImmersive(true)// 是否改变状态栏字体颜色(黑色)
                .create();

//        if (mode) {
//            // 只拍照
//            PictureConfig.getInstance().init(options).startOpenCamera(this);
//        } else {
        // 先初始化参数配置，在启动相册
//            PictureConfig.getInstance().init(options).openPhoto(this, resultCallback);
//        }
    }

    /**
     * 判断 一个字段的值否为空
     *
     * @param s
     * @return
     * @author Michael.Zhang 2013-9-7 下午4:39:00
     */

    public boolean isNull(String s) {
        if (null == s || s.equals("") || s.equalsIgnoreCase("null")) {
            return true;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        FaceServer.getInstance().unInit();
        super.onDestroy();
        cancelLoadHeadImageNetworkRequest();
        cancelNetworkRequest();
        cancelPushLiveBodyDataRequest();
    }

    private void cancelNetworkRequest() {
        if (call != null) {
            call.cancel();
        }
        call = null;
    }

    private void cancelLoadHeadImageNetworkRequest() {
        if (loadHeadImageCall != null) {
            loadHeadImageCall.cancel();
        }
        loadHeadImageCall = null;
    }

    private void cancelPushLiveBodyDataRequest() {
        if (pushLiveBodyCall != null) {
            pushLiveBodyCall.cancel();
        }
        pushLiveBodyCall = null;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        ToastUtils.showToast(getApplicationContext(), "用户授权成功");

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }

    }

    public static String getBase64String(String s) {
        if (StringUtils.isBlank(s)) {
            return "";
        } else {
            return com.jqsoft.livebody_verify_lib.util.Base64.encode(s);
        }
    }

    String bitmapBase64, baseBimapBase64;

    private void compressBitmap() {
        if (bitmappath == null) {

        } else {
            SharedPreferences userSettings = getSharedPreferences("setting", 0);
            String path = userSettings.getString("bitmappath", "");
            List<File> mFileList = new ArrayList<>();
            File file1 = new File(path);
            File file2 = new File(bitmappath);
            mFileList.clear();
            mFileList.add(file1);
            mFileList.add(file2);

            Luban.compress(HeadCollectActivity.this, mFileList)
                    .setMaxSize(50)                // limit the final image size（unit：Kb）
                    // limit image width
                    .putGear(Luban.CUSTOM_GEAR)     // use CUSTOM GEAR compression mode
                    .asListObservable()
                    .subscribe(new Consumer<List<File>>() {
                        @Override
                        public void accept(List<File> files) throws Exception {

                            bitmapBase64 = Util.imageToBase64(files.get(1).getAbsolutePath());
//                        bitmapBase64= getBase64String(bitmapBase64);
//                        baseBimapBase64= getBase64String(baseBimapBase64);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                        }
                    });
        }


    }



    //    private String jsonexc;
    private Handler myhandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SENDLOG:
                    new Thread(runNote).start();
                    break;
                case Constant.MSG_LOAD_OVER:
                    Util.hideGifProgressDialog(HeadCollectActivity.this);
                    succeedAndReturnResult();
                    btnCollect.setClickable(true);
                    Toast.makeText(HeadCollectActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                    finish();

//                    Util.showNotificationDialog(getActivity(), sMessage, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            getActivity().finish();
//                        }
//                    });
//                    WeiboDialogUtils.closeDialog(mDialog);
//                    returnSuccess();
                    break;
                case Constant.MSG_LOAD_EXC:
                    Util.hideGifProgressDialog(HeadCollectActivity.this);
                    btnCollect.setClickable(true);

                    if (!TextUtils.isEmpty(sMessage)) {
                        showNotificationDialog(sMessage, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                              returnFailure();
                            }
                        });
                    } else {
                        Toast.makeText(HeadCollectActivity.this, "sMessage为空", Toast.LENGTH_LONG).show();
//                        WeiboDialogUtils.closeDialog(mDialog);
                    }
                    break;
                case Constant.APPLy_SCUSSE:

                    Util.hideGifProgressDialog(HeadCollectActivity.this);
                    Toast.makeText(HeadCollectActivity.this, Applymes, Toast.LENGTH_LONG).show();
                   // finish();
                    break;
                case Constant.APPLY_ERAR:
                    Util.hideGifProgressDialog(HeadCollectActivity.this);
                    Toast.makeText(HeadCollectActivity.this, "接口访问失败", Toast.LENGTH_LONG).show();
                    break;
                case Constant.APPLY_FAIL:
                    Util.hideGifProgressDialog(HeadCollectActivity.this);
                    Toast.makeText(HeadCollectActivity.this, Applymes, Toast.LENGTH_LONG).show();
                    break;
                case Constant.MSG_LOAD_EMPTY:
                    Util.hideGifProgressDialog(HeadCollectActivity.this);
                    btnCollect.setClickable(true);
                    if (!TextUtils.isEmpty(sMessage)) {
                        showNotificationDialog(sMessage, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                              returnFailure();
                            }
                        });
                    } else {
                        Toast.makeText(HeadCollectActivity.this, "sMessage为空", Toast.LENGTH_LONG).show();
//                        WeiboDialogUtils.closeDialog(mDialog);
                    }
//                    Util.showNotificationDialog(getActivity(), "未访问服务,请检查服务或网络是否有问题",null);
//                    WeiboDialogUtils.closeDialog(mDialog);
                    break;
                case Constant.MSG_LOAD_ERROR:
                    Util.hideGifProgressDialog(HeadCollectActivity.this);

                    Toast.makeText(HeadCollectActivity.this, sMessage, Toast.LENGTH_LONG).show();

                    btnCollect.setClickable(true);
                    if (!TextUtils.isEmpty(sMessage)) {
                        showNotificationDialog(sMessage, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                              returnFailure();
                            }
                        });
                    } else {
                        Toast.makeText(HeadCollectActivity.this, "sMessage为空", Toast.LENGTH_LONG).show();
//                        WeiboDialogUtils.closeDialog(mDialog);
                    }

//                    Util.showNotificationDialog(getActivity(), sMessage, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            getActivity().finish();
//                        }
//                    });
//                    WeiboDialogUtils.closeDialog(mDialog);
                    break;
                case Constant.GETBASEPHOTO_LOAD_OVER:
                    isLoadHeadImageSuccess = false;
                    String suffix = "";
                    GetBasePhotoMessage = Util.trimString(GetBasePhotoMessage);
                    suffix = ":" + GetBasePhotoMessage;
                    String ultimateString = "加载头像失败" + suffix;
                    Log.i("chenxu", "HeadCollectActivity failure");
                    showNotificationDialog(ultimateString, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                              returnFailure();
                        }
                    });
                    break;
                case Constant.GETBASEPHOTO_LOAD_SUCESS:
                    if (!TextUtils.isEmpty(recognitionBasePhotoBean.getIsExist())) {
                        if ("1".equals(recognitionBasePhotoBean.getIsExist())) {
                            isLoadHeadImageSuccess = true;
                            String headBase64String = recognitionBasePhotoBean.getPhoto_Base();
                            if (!TextUtils.isEmpty(headBase64String)) {
                                mainbitmap = Base64Util.base64ToBmp(headBase64String);
                                saveBitmap(HeadCollectActivity.this, mainbitmap);
                                headeidbitmap = mainbitmap;
                                if (faceType != 2) {

                                    if(TextUtils.isEmpty(faceFerture)){
                                        registerFace(mainbitmap);
                                    }else {
                                        registerFaceFeature();
                                    }

                                }
                                ivPassinHead.setImageBitmap(mainbitmap);
                            }

                        } else if ("0".equals(recognitionBasePhotoBean.getIsExist())) {
                            requestFaceArchiveCreation();
                        }
                    }
                    break;
                    default:

            }

        }
    };

    private Bitmap headbitmap = null;
    private Bitmap Photo_Base = null;

    private void registerFaceFeature() {
        clearFaces();
        FaceServer.getInstance().registenew1(
                "registered ",faceFerture);
    }

    private void registerFace(Bitmap bmp) {
        if (FaceServer.getInstance() == null) {
            FaceServer.getInstance().init(HeadCollectActivity.this);
            ConfigUtil.setFtOrient(HeadCollectActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);
        }
        Photo_Base = bmp;
        Log.e("jieo1", Photo_Base + "");
        //本地人脸库初始化
        clearFaces();
        headbitmap = bmp;
        bmp = ImageUtil.alignBitmapForNv21(bmp);
        if (bmp == null) {
            deleteArchive();
            requestFaceArchiveCreation();
        } else {

            int width = bmp.getWidth();
            int height = bmp.getHeight();
            //bitmap转NV21
            final byte[] nv21 = ImageUtil.bitmapToNv21(bmp, width, height);
            if (nv21 != null) {

                boolean success = FaceServer.getInstance().register(HeadCollectActivity.this, nv21, bmp.getWidth(), bmp.getHeight(),
                        "registered ");
                if (success) {
                    Toast.makeText(this, "已获取比对人脸!", Toast.LENGTH_SHORT).show();

                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HeadCollectActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                    builder.setTitle("提示");
                    builder.setMessage("系统智能检测，首次采集底片无法识别人脸特征，必须重新采集后才可以开展后续工作!");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            deleteArchive();
                        }
                    });

                    android.app.AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                }


            }
        }
    }


    private void registerFaceSixian(Bitmap bmp) {
        if (FaceServer.getInstance() == null) {
            FaceServer.getInstance().init(HeadCollectActivity.this);
            ConfigUtil.setFtOrient(HeadCollectActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);
        }
        Photo_Base = bmp;
        Log.e("jieo1", Photo_Base + "");
        //本地人脸库初始化
        clearFaces();
        headbitmap = bmp;
        bmp = ImageUtil.alignBitmapForNv21(bmp);
        if (bmp == null) {
            deleteArchive();
            requestFaceArchiveCreation();

        } else {

            int width = bmp.getWidth();
            int height = bmp.getHeight();
            //bitmap转NV21
            final byte[] nv21 = ImageUtil.bitmapToNv21(bmp, width, height);
            if (nv21 != null) {

                boolean success = FaceServer.getInstance().register(HeadCollectActivity.this, nv21, bmp.getWidth(), bmp.getHeight(),
                        "registered ");
                if (success) {
                    Toast.makeText(this, "已获取比对人脸!", Toast.LENGTH_SHORT).show();

                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HeadCollectActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                    builder.setTitle("提示");
                    builder.setMessage("系统智能检测，首次采集底片无法识别人脸特征，必须重新采集后才可以开展后续工作!");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            deleteArchive();
                        }
                    });

                    android.app.AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                }


            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (isLoadHeadImageSuccess) {
            final SharedPreferences userSettings = getSharedPreferences("setting", 0);
            faceType = userSettings.getInt("FaceType", 0);

            if (faceType != 2) {
                Bitmap bm = ((BitmapDrawable) ((ImageView) ivPassinHead).getDrawable()).getBitmap();
                if (bm == null) {
                    bm = headbitmap;
                }


                if(TextUtils.isEmpty(faceFerture)){
                    registerFace(bm);
                }else {
                    registerFaceFeature();
                }

            }


        }


    }

    private String GetBasePhotoMessage = "";
    GetRecognitionBasePhotoBean recognitionBasePhotoBean;
    /**
     * 获取底片
     */
    Runnable GetBasePhotoRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                SharedPreferences userSettings = getSharedPreferences("setting", 0);
                String finalArcSaveBitmapUrl = userSettings.getString("ArcSaveBitmapUrl", "");
                String methodName = "GetRecognitionBasePhoto";

                JSONObject data = new JSONObject();
                data.put("sPersonID", sPersonID);

                String content = WebServiceUtils.requestByParams(methodName,
                        data.toString(), finalArcSaveBitmapUrl);
                if (!TextUtils.isEmpty(content)) {
                    try {

                        JSONObject json = new JSONObject(content);
                        int isSuccess = json.getInt("sSuccess");
                        GetBasePhotoMessage = json.getString("sMessage");
                        if (isSuccess == 1) {
                            JSONObject jo = new JSONObject(json.getString("Data"));
                            recognitionBasePhotoBean = new Gson().fromJson(jo.toString(), new TypeToken<GetRecognitionBasePhotoBean>() {
                            }.getType());
                            if (recognitionBasePhotoBean != null) {
                                myhandler.sendEmptyMessage(Constant.GETBASEPHOTO_LOAD_SUCESS);
                            } else {
                                sMessage = GetBasePhotoMessage;
                                myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
                            }
                        } else {
                            myhandler.sendEmptyMessage(Constant.GETBASEPHOTO_LOAD_OVER);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        sMessage = e.getMessage();
                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
//                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_ERROR);
                    }
                } else {
                    myhandler.sendEmptyMessage(Constant.MSG_LOAD_EMPTY);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sMessage = e.getMessage();
                myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
            }

        }
    };

    /**
     * 删除人脸档案
     */

    private void deleteArchive() {

        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        facebaseurl = userSettings.getString("face_base_url", "");
        if (TextUtils.isEmpty(Version.BASE_URL)) {
            Version.BASE_URL = facebaseurl;
        }
        String url = Version.BASE_URL + "delete";
        Log.i("jie01", "url" + url);
        if (TextUtils.isEmpty(idNumber) || TextUtils.isEmpty(url)) {
            Toast.makeText(this, "身份证号和地址不能为空!", Toast.LENGTH_SHORT).show();
        } else {
            long timestamp = Util.getTimeStamp();
            String timestampString = String.valueOf(timestamp);
            String token = Util.getProcessedToken(timestamp);
            Map<String, String> params = new HashMap<>();
            params.put("acs_token", token);
            params.put("acs_time", timestampString);
            params.put("idnum", idNumber);


            call = OkHttpUtils.post()//
//                .addFile("mFile", "agguigu-afu.jpe", file)//
                    .url(url)//
                    .params(params)//
//                .headers(headers)//
                    .build();//
            call.execute(new MyResultCallback<VerifyResultBean>() {

                @Override
                public void onSuccess(VerifyResultBean response) {
                    if (response != null && "1".equals(response.getRespond())) {
                        Log.i("chenxu", "CardImageLiveFaceVerifyActivity onSuccess");
//                        Toast.makeText(HeadCollectActivity.this, "删除人脸档案成功", Toast.LENGTH_SHORT).show();
                        myhandler.sendEmptyMessage(Constant.SENDLOG);

                    } else {
                        String suffix = "";
                        if (response != null) {
                            suffix = ":" + response.getMessage();
                        }

                        Toast.makeText(HeadCollectActivity.this, "删除人脸档案失败", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Log.i("jie01", "e.getMessage()" + e.getMessage());
                    Toast.makeText(HeadCollectActivity.this, "删除人脸档案失败" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    /**
     * 删除人脸档案 底片日志记录
     */
    Runnable runNote = new Runnable() {
        @Override
        public void run() {
            SharedPreferences userSettings = getSharedPreferences("setting", 0);
            String finalArcSaveBitmapUrl = userSettings.getString("ArcSaveBitmapUrl", "");
            Log.e("jieo1", finalArcSaveBitmapUrl + "finalArcSaveBitmapUrl");
            String sLoginName = userSettings.getString("sLoginName", "");
            String sInputOrgCode = userSettings.getString("sInputOrgCode", "");
            String methodName = "DeleteRecognitionInfoLog";
            JSONObject data = new JSONObject();
            try {

//                Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.ic_camera);
                data.put("iCollectionType", getBase64String("4"));//类型(1:人脸识别 2: 人证核验 3:身份证 4:删除人脸记录 5:删除有效期记录) (Base64加密)
                data.put("sCardNo", getBase64String(idNumber));  /// 身份证号码 (Base64加密)
                data.put("sPersonID", getBase64String(sPersonID));//健康档案唯一标识 (Base64加密)
                data.put("sLoginName", getBase64String(sLoginName));//用户登录名 (Base64加密)
                data.put("sInputOrgCode", getBase64String(sInputOrgCode));// 录入机构主键 (Base64加密)
                data.put("Photo_Base", bitmapToBase64(Photo_Base));// 删除的底片(图片Base64值)
                data.put("sRemark", getBase64String("自动删除:底片获取不清楚"));// 删除的原因

                String content = WebServiceUtils.requestByParams(methodName,
                        data.toString(), finalArcSaveBitmapUrl);
                if (!TextUtils.isEmpty(content)) {
                    try {
                        JSONObject json = new JSONObject(content);
                        int isSuccess = json.getInt("sSuccess");
                        sMessage = json.getString("sMessage");
                        Log.e("jieo1", isSuccess + "finalArcSaveBitmapUrl");
                        if (isSuccess == 1) {
                            //myhandler.sendEmptyMessage(Constant.MSG_LOAD_OVER);


                        } else {
                            //myhandler.sendEmptyMessage(Constant.MSG_LOAD_ERROR);
                        }
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
//                        sMessage= e.getMessage();
//                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
//                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_ERROR);
                    }
                } else {
                    finish();
                    // myhandler.sendEmptyMessage(Constant.MSG_LOAD_EMPTY);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    /**
     * 删除人脸档案 底片日志记
     */
    Runnable applyDelete = new Runnable() {
        @Override
        public void run() {
            SharedPreferences userSettings = getSharedPreferences("setting", 0);
            String finalArcSaveBitmapUrl = userSettings.getString("ArcSaveBitmapUrl", "");
            Log.e("jieo1", finalArcSaveBitmapUrl + "finalArcSaveBitmapUrl");
            String sLoginName = userSettings.getString("sLoginName", "");
            String sInputOrgCode = userSettings.getString("sInputOrgCode", "");
            String methodName = "AppleDeleteRecognitionBasePhoto";
            JSONObject data = new JSONObject();
            try {

//                Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.ic_camera);
                data.put("iCollectionType", getBase64String("6"));//类型(1:人脸识别 2: 人证核验 3:身份证 4:删除人脸记录 5:删除有效期记录) (Base64加密)
                data.put("sCardNo", getBase64String(idNumber));  /// 身份证号码 (Base64加密)
                data.put("sPersonID", getBase64String(sPersonID));//健康档案唯一标识 (Base64加密)
                data.put("sLoginName", getBase64String(sLoginName));//用户登录名 (Base64加密)
                data.put("sInputOrgCode", getBase64String(sInputOrgCode));// 录入机构主键 (Base64加密)
                data.put("Photo_Base", bitmapToBase64(Photo_Base));// 删除的底片(图片Base64值)
                data.put("sRemark", getBase64String(sRemark));// 删除的原因
                data.put("sUserName", getBase64String(sUserName));
                data.put("sVerifyStatusCode", getBase64String("0"));
                Log.e("jieo1", data.toString() + "data.toString()");
                String content = WebServiceUtils.requestByParams(methodName,
                        data.toString(), finalArcSaveBitmapUrl);
                if (!TextUtils.isEmpty(content)) {
                    try {
                        JSONObject json = new JSONObject(content);
                        int isSuccess = json.getInt("sSuccess");
                        Applymes = json.getString("sMessage");
                        Log.e("jieo1", isSuccess + "finalArcSaveBitmapUrl");
                        if (isSuccess == 1) {
                            myhandler.sendEmptyMessage(Constant.APPLy_SCUSSE);
                        } else {
                            myhandler.sendEmptyMessage(Constant.APPLY_FAIL);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    myhandler.sendEmptyMessage(Constant.APPLY_ERAR);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     *   申请人工审核
     */
    Runnable applySend = new Runnable() {
        @Override
        public void run() {
            SharedPreferences userSettings = getSharedPreferences("setting", 0);
            String finalArcSaveBitmapUrl = userSettings.getString("ArcSaveBitmapUrl", "");
            Log.e("jieo1", finalArcSaveBitmapUrl + "finalArcSaveBitmapUrl");
            String sLoginName = userSettings.getString("sLoginName", "");
            String sInputOrgCode = userSettings.getString("sInputOrgCode", "");
            String methodName = "CheckFacePhoto";
            JSONObject data = new JSONObject();
            try {
                data.put("sCardNo", getBase64String(idNumber));  /// 身份证号码 (Base64加密)
                data.put("sPersonID", getBase64String(sPersonID));//健康档案唯一标识 (Base64加密)
                data.put("sLoginName", getBase64String(sLoginName));//用户登录名 (Base64加密)
                data.put("sInputOrgCode", getBase64String(sInputOrgCode));// 录入机构主键 (Base64加密)
                data.put("Photo_Base", bitmapToBase64(takePhotoBitmap));// 删除的底片(图片Base64值)
                data.put("sRemark", getBase64String(sRemark));// 删除的原因
                data.put("sUserName", getBase64String(sUserName));

                Log.e("jieo1", data.toString() + "data.toString()");
                String content = WebServiceUtils.requestByParams(methodName,
                        data.toString(), finalArcSaveBitmapUrl);
                if (!TextUtils.isEmpty(content)) {
                    try {
                        JSONObject json = new JSONObject(content);
                        int isSuccess = json.getInt("sSuccess");
                        Applymes = json.getString("sMessage");
                        Log.e("jieo1", isSuccess + "finalArcSaveBitmapUrl");
                        if (isSuccess == 1) {
                            myhandler.sendEmptyMessage(Constant.APPLy_SCUSSE);
                        } else {
                            myhandler.sendEmptyMessage(Constant.APPLY_FAIL);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
//                        sMessage= e.getMessage();
//                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
//                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_ERROR);
                    }
                } else {
                    myhandler.sendEmptyMessage(Constant.APPLY_ERAR);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
   public  void IntetActivity(){
       Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
       mPhotoPath = Util.getSDPath() + "/" + Util.getPhotoFileName();//设置图片文件路径，getSDPath()和getPhotoFileName()具体实现在下面
       mPhotoFile = new File(mPhotoPath);
       if (!mPhotoFile.exists()) {
           try {
               mPhotoFile.createNewFile();//创建新文件
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));//Intent有了图片的信息
       photoPath = Uri.fromFile(mPhotoFile);
       startActivityForResult(intent, TAKE_PHOTO);//跳转界面传回拍照所得数据
   }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long startTime= System.nanoTime();   //获取开始时间
            btnCollect.setClickable(false);
            try {
                //
                SharedPreferences userSettings = getSharedPreferences("setting", 0);
                String finalArcSaveBitmapUrl = userSettings.getString("ArcSaveBitmapUrl", "");
                String methodName = "SaveRecognitionInfo";
                String sLoginName = userSettings.getString("sLoginName", "");
                String sInputOrgCode = userSettings.getString("sInputOrgCode", "");
                Bitmap bm = ((BitmapDrawable) ((ImageView) ivPassinHead).getDrawable()).getBitmap();
                if (bm == null) {
                    bm = headbitmap;
                }

                baseBimapBase64 = Util.bitmapToBase64(bm);
//                Log.e("zyz",sLoginName);
//                Log.e("zyz",sInputOrgCode);

                String dCoefficient = userSettings.getString("dCoefficient", "");
//                Log.e("zyz",dCoefficient);
                if (dCoefficient == null || TextUtils.isEmpty(dCoefficient)) {
                    dCoefficient = "0.0000";
                } else {
                    if (dCoefficient.length() > 5) {
                        dCoefficient = dCoefficient.substring(0, 5);
                    }
                }
                if (TextUtils.isEmpty(sInputOrgCode)) {
                    Toast.makeText(HeadCollectActivity.this, "未获取到登陆用户信息，请重新登陆！", Toast.LENGTH_LONG).show();
                }

//
                JSONObject data = new JSONObject();

                data.put("iCollectionType", getBase64String("1"));  /// 类型(1,人脸识别 2：身份证) (Base64加密)
                data.put("iResultCode", getBase64String("1"));  /// )  识别结果编码(1 成功 0 失败) (Base64加密)
                data.put("sResultName", getBase64String("成功"));  /// )  识别结果名称(1 成功 0 失败) (Base64加密)
                data.put("sCardNo", getBase64String(idNumber));  /// )  身份证号码 (Base64加密)
                data.put("sLoginName", getBase64String(sLoginName));  /// )  用户登录名 (Base64加密)
                data.put("sInputOrgCode", getBase64String(sInputOrgCode));  /// )  录入机构编码 (Base64加密)
                data.put("dCoefficient", getBase64String(dCoefficient));  /// )  识别系数 (Base64加密)
                data.put("sPersonID", getBase64String(sPersonID));  /// )  sPersonID (Base64加密)


                if (flageid) {
                    Bitmap bitmap = ((BitmapDrawable) ((ImageView) ivPassinHead).getDrawable()).getBitmap();
                    if (bitmap == null) {
                        bitmap = headeidbitmap;
                    }
                    baseBimapBase64 = bitmapToBase64(bitmap);

                    Bitmap bitmap1 = ((BitmapDrawable) ((ImageView) ivHead).getDrawable()).getBitmap();
                    if (bitmap1 == null) {
                        bitmap1 = eidbitmap;
                    }
                    bitmapBase64 = bitmapToBase64(bitmap1);

                }

                data.put("Photo_Base", baseBimapBase64);  /// )  本地照片 (Base64加密)
                data.put("Photo_Recognition", bitmapBase64);  /// )  本次识别照片 (Base64加密)


                String content = WebServiceUtils.requestByParams(methodName,
                        data.toString(), finalArcSaveBitmapUrl);
                if (!TextUtils.isEmpty(content)) {
                    try {
                        JSONObject json = new JSONObject(content);
                        int isSuccess = json.getInt("sSuccess");
                        sMessage = json.getString("sMessage");

                        if (isSuccess == 1) {
                            long endTime= System.nanoTime(); //获取结束时间
                            myhandler.sendEmptyMessage(Constant.MSG_LOAD_OVER);
                            Log.i("程序运行时间",(endTime-startTime)+"ns");
                        } else {
                            myhandler.sendEmptyMessage(Constant.MSG_LOAD_ERROR);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        sMessage = e.getMessage();
                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
//                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_ERROR);
                    }
                } else {
                    myhandler.sendEmptyMessage(Constant.MSG_LOAD_EMPTY);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sMessage = e.getMessage();
                myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
            }

        }
    };
    private void verify() {
        long timestamp = Util.getTimeStamp();
        String timestampString = String.valueOf(timestamp);
        String token = Util.getProcessedToken(timestamp);
        SharedPreferences userSettings = getSharedPreferences("setting", 0);
        String dCoefficient = userSettings.getString("dCoefficient", "");
//                Log.e("zyz",dCoefficient);
        if (dCoefficient == null || TextUtils.isEmpty(dCoefficient)) {
            dCoefficient = "0.0000";
        } else {
            if (dCoefficient.length() > 5) {
                dCoefficient = dCoefficient.substring(0, 5);
            }
        }
        Bitmap bm = ((BitmapDrawable) ((ImageView) ivPassinHead).getDrawable()).getBitmap();
        if (bm == null) {
            bm = headbitmap;
        }

        baseBimapBase64 = Util.bitmapToBase64(bm);
        if (flageid) {
            Bitmap bitmap = ((BitmapDrawable) ((ImageView) ivPassinHead).getDrawable()).getBitmap();
            if (bitmap == null) {
                bitmap = headeidbitmap;
            }
            baseBimapBase64 = bitmapToBase64(bitmap);

            Bitmap bitmap1 = ((BitmapDrawable) ((ImageView) ivHead).getDrawable()).getBitmap();
            if (bitmap1 == null) {
                bitmap1 = eidbitmap;
            }
            bitmapBase64 = bitmapToBase64(bitmap1);

        }
        Map<String, String> params = new HashMap<>();
        params.put("ElderInfoID", getBase64String("2509493e-9fe7-4c04-b7cb-b8a507b6f510"));
        params.put("ElderName", getBase64String("朱馨玉"));
        params.put("iResultCode", getBase64String("1"));
        params.put("sResultName", getBase64String("成功"));
        params.put("sCardNo", getBase64String(idNumber));
        params.put("sLoginName",  getBase64String("gajly"));
        params.put("dCoefficient",  getBase64String(dCoefficient));
        params.put("Photo_Base", baseBimapBase64);//
        params.put("Photo_Recognition",bitmapBase64 );//

        Log.i("chenxu", "acs_token:" + token);
        Log.i("chenxu", "acs_time:" + timestampString);
        Log.i("chenxu", "idnum:" + idNumber);



        String url = "http://192.168.44.140:6868/api.phone/SaveFaceRecognitionMsg";
        Log.i("jie01", "url" + url);
        call = OkHttpUtils.post()//
//                .addFile("mFile", "agguigu-afu.jpe", file)//
                .url(url)//
                .params(params)//
//                .headers(headers)//
                .build();//
        call.execute(new MyResultCallback<VerifyResultBean>() {

            @Override
            public void onSuccess(VerifyResultBean response) {
               String sf =  response.getRespond();
                 Log.e("jie01",sf);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("jie01", e.getMessage());
            }
        });
    }
}
