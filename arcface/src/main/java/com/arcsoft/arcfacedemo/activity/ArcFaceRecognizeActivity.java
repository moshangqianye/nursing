package com.arcsoft.arcfacedemo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.bean.SimilarityBean;
import com.arcsoft.arcfacedemo.bean.UpdateArcFaceViewBean;
import com.arcsoft.arcfacedemo.faceserver.CompareResult;
import com.arcsoft.arcfacedemo.faceserver.FaceServer;
import com.arcsoft.arcfacedemo.model.DrawInfo;
import com.arcsoft.arcfacedemo.model.FacePreviewInfo;
import com.arcsoft.arcfacedemo.util.ConfigUtil;
import com.arcsoft.arcfacedemo.util.DrawHelper;
import com.arcsoft.arcfacedemo.util.HexUtils;
import com.arcsoft.arcfacedemo.util.IDCard;
import com.arcsoft.arcfacedemo.util.NV21ToBitmap;
import com.arcsoft.arcfacedemo.util.Util;
import com.arcsoft.arcfacedemo.util.camera.CameraHelper;
import com.arcsoft.arcfacedemo.util.camera.CameraListener;
import com.arcsoft.arcfacedemo.util.face.FaceHelper;
import com.arcsoft.arcfacedemo.util.face.FaceListener;
import com.arcsoft.arcfacedemo.util.face.LivenessType;
import com.arcsoft.arcfacedemo.util.face.RecognizeColor;
import com.arcsoft.arcfacedemo.util.face.RequestFeatureStatus;
import com.arcsoft.arcfacedemo.util.face.RequestLivenessStatus;
import com.arcsoft.arcfacedemo.widget.FaceRectView;
import com.arcsoft.arcfacedemo.widget.FaceSearchResultAdapter;
import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class ArcFaceRecognizeActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "RegisterAndRecognize";
    private static final int MAX_DETECT_NUM = 10;
    /**
     * 当FR成功，活体未成功时，FR等待活体的时间
     */
    private static final int WAIT_LIVENESS_INTERVAL = 100;
    /**
     * 失败重试间隔时间（ms）
     */
    private static final long FAIL_RETRY_INTERVAL = 1000;
    /**
     * 出错重试最大次数
     */
    private static final int MAX_RETRY_TIME = 3;

    private CameraHelper cameraHelper;
    private DrawHelper drawHelper;
    private Camera.Size previewSize;
    /**
     * 优先打开的摄像头，本界面主要用于单目RGB摄像头设备，因此默认打开前置
     */

    private Integer rgbCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;

    /**
     * VIDEO模式人脸检测引擎，用于预览帧人脸追踪
     */
    private FaceEngine ftEngine;
    /**
     * 用于特征提取的引擎
     */
    private FaceEngine frEngine;
    /**
     * IMAGE模式活体检测引擎，用于预览帧人脸活体检测
     */
    private FaceEngine flEngine;

    private int ftInitCode = -1;
    private int frInitCode = -1;
    private int flInitCode = -1;
    private FaceHelper faceHelper;
    private List<CompareResult> compareResultList;
    private FaceSearchResultAdapter adapter;
    /**
     * 活体检测的开关
     */
    private boolean livenessDetect = true;
    /**
     * 注册人脸状态码，准备注册
     */
    private static final int REGISTER_STATUS_READY = 0;
    /**
     * 注册人脸状态码，注册中
     */
    private static final int REGISTER_STATUS_PROCESSING = 1;
    /**
     * 注册人脸状态码，注册结束（无论成功失败）
     */
    private static final int REGISTER_STATUS_DONE = 2;

    private int registerStatus = REGISTER_STATUS_DONE;
    /**
     * 用于记录人脸识别相关状态
     */
    private ConcurrentHashMap<Integer, Integer> requestFeatureStatusMap = new ConcurrentHashMap<>();
    /**
     * 用于记录人脸特征提取出错重试次数
     */
    private ConcurrentHashMap<Integer, Integer> extractErrorRetryMap = new ConcurrentHashMap<>();
    /**
     * 用于存储活体值
     */
    private ConcurrentHashMap<Integer, Integer> livenessMap = new ConcurrentHashMap<>();
    /**
     * 用于存储活体检测出错重试次数
     */
    private ConcurrentHashMap<Integer, Integer> livenessErrorRetryMap = new ConcurrentHashMap<>();

    private CompositeDisposable getFeatureDelayedDisposables = new CompositeDisposable();
    private CompositeDisposable delayFaceTaskCompositeDisposable = new CompositeDisposable();
    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private View previewView;
    /**
     * 绘制人脸框的控件
     */
    private FaceRectView faceRectView;

    private Switch switchLivenessDetect;

    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    /**
     * 识别阈值
     */
    private double SIMILAR_THRESHOLD = 0.7F;
    /**
     * 所需的所有权限信息
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE

    };

    private ExecutorService activeService = Executors.newSingleThreadExecutor();
    private double dAPPFaceFaZhi = 0.3d;
    private int age = 0;
    private int mOrientation;
    private String formWay = "HeadCollectActivity";

    private String isOnlyFace = "1";
    private String idNumber;
    private TextView txt_rate;
    private double RESOLVING = 0.2;
    private TextView txt_score;
    //防止多次存图
    private boolean isSaveBitmap = false;
    private LinearLayout ll_back;
    private TextView txt_tips;
    //更新提示语句
    /**
     * 更新提示信息
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                UpdateArcFaceViewBean bean = (UpdateArcFaceViewBean) msg.obj;
                if ("txt_rate".equals(bean.getViewName())) {
                    txt_rate.setText(bean.getMsg() + "");
                    txt_rate.setTextColor(bean.getTextColor());
                } else if ("txt_score".equals(bean.getViewName())) {
                    txt_score.setText(bean.getMsg() + "");
                    txt_score.setTextColor(bean.getTextColor());
                } else if ("txt_tips".equals(bean.getViewName())) {
                    txt_tips.setText(bean.getMsg() + "");
                    txt_tips.setTextColor(bean.getTextColor());
                }
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_and_recognize);
        txt_score = (TextView) findViewById(R.id.txt_score);
        txt_rate = (TextView) findViewById(R.id.txt_rate);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        txt_tips = (TextView) findViewById(R.id.txt_tips);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        formWay = "HeadCollectActivity";
        if (intent == null) {
        } else {
            formWay = intent.getStringExtra("formWay");
            idNumber = intent.getStringExtra("idNumber");
//            getfaceFeature = intent.getStringExtra("faceFeatrue");
            isOnlyFace = intent.getStringExtra("isOnlyFace");
        }
        initInfoFromLogin();
        //保持亮屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            getWindow().setAttributes(attributes);
        }

        // Activity启动后就锁定为启动时的方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        //本地人脸库初始化
        FaceServer.getInstance().init(this);
        //加载图片 获取到特征值 直接注册
//        if (!TextUtils.isEmpty(getfaceFeature)) {
//            FaceServer.initFaceList(ArcFaceRecognizeActivity.this, getfaceFeature);
//        }
        initView();
        initTimer();
    }

    private void initView() {
        previewView = findViewById(R.id.texture_preview);
        //在布局结束后才做初始化操作
        previewView.getViewTreeObserver().addOnGlobalLayoutListener(this);

        faceRectView = (FaceRectView) findViewById(R.id.face_rect_view);
        switchLivenessDetect = (Switch) findViewById(R.id.switch_liveness_detect);
        switchLivenessDetect.setChecked(livenessDetect);
        switchLivenessDetect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                livenessDetect = isChecked;
            }
        });
        RecyclerView recyclerShowFaceInfo = (RecyclerView) findViewById(R.id.recycler_view_person);
        compareResultList = new ArrayList<>();
        adapter = new FaceSearchResultAdapter(compareResultList, this);
        recyclerShowFaceInfo.setAdapter(adapter);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int spanCount = (int) (dm.widthPixels / (getResources().getDisplayMetrics().density * 100 + 0.5f));
        recyclerShowFaceInfo.setLayoutManager(new GridLayoutManager(this, spanCount));
        recyclerShowFaceInfo.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 初始化引擎
     */
    private void initEngine() {
        ftEngine = new FaceEngine();
        ftInitCode = ftEngine.init(this, DetectMode.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_DETECT);

        frEngine = new FaceEngine();
        frInitCode = frEngine.init(this, DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_0_ONLY,
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION);

        flEngine = new FaceEngine();
        flInitCode = flEngine.init(this, DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_0_ONLY,
                16, MAX_DETECT_NUM, FaceEngine.ASF_LIVENESS);

        Log.i(TAG, "initEngine:  init: " + ftInitCode);

        if (ftInitCode != ErrorInfo.MOK) {
            String error = "失败" + ftInitCode;
            Log.i(TAG, "initEngine: " + error);
            showToast(error);

        }
        if (frInitCode != ErrorInfo.MOK) {
            String error = "失败" + ftInitCode;
            Log.i(TAG, "initEngine: " + error);
            showToast(error);
        }
        if (flInitCode != ErrorInfo.MOK) {
            String error = "失败" + ftInitCode;
            Log.i(TAG, "initEngine: " + error);
            showToast(error);
        }
    }

    private void showError(String error){
        Intent intent=new Intent();
        intent.putExtra("ErrCode",error);
        setResult(65539,intent);
        finish();
    }

    private void initInfoFromLogin() {

        startOrientationChangeListener();
        SharedPreferences userSettings = getSharedPreferences("setting", 0);
//        ACTION_SELECTION = userSettings.getInt("Action", 0);
//        FRAME_NUMBER = userSettings.getInt("Frames", 10);
//        DIFFERENCE_VALUE = userSettings.getInt("Range", 10);
        if (!TextUtils.isEmpty(userSettings.getString("ScreenOf", "0.1"))) {
            RESOLVING = Double.parseDouble(userSettings.getString("ScreenOf", "0.2"));
        }
//        SIMILAR_NUMBER = 0.6;
        if (formWay.equals("FaceFindActivity")) {

        } else if (formWay.equals("CardImageLiveFaceVerifyActivity")) {
            if (("1").equals(isOnlyFace)) {
                SIMILAR_THRESHOLD = 0.0F;
            } else {
                if ("".equals(IDCard.IDCardValidate(idNumber))) {
                    age = IDCard.getAge(idNumber);
                }
                String ManCardVerifyStr = userSettings.getString("ManCardVerify", "0-8:0.4,8-50:0.5,50-2000:0.3");
                SIMILAR_THRESHOLD = parseSimilarString(ManCardVerifyStr);
            }

        } else if (formWay.equals("HeadCollectActivity")) {
            if ("".equals(IDCard.IDCardValidate(idNumber))) {
                age = IDCard.getAge(idNumber);
            }
            String Similarity = userSettings.getString("Similarity", "0-8:0.1,8-50:0.2,50-2000:0.3");
            SIMILAR_THRESHOLD = parseSimilarString(Similarity);
//            if (!TextUtils.isEmpty(path)){
//                bmp = getBitmapFromPath(path);
//            }
        }

//        String sAPPFaceFaZhi = userSettings.getString("sAPPFaceFaZhi", "");
//        if (sAPPFaceFaZhi == null || TextUtils.isEmpty(sAPPFaceFaZhi)) {
//
//        } else {
//            try {
//                dAPPFaceFaZhi = parseSimilarString(sAPPFaceFaZhi);
//            } catch (Exception e) {
//                dAPPFaceFaZhi = 0.3d;
//            }
//
//        }
    }

    /**
     * 启动屏幕朝向改变监听函数 用于在屏幕横竖屏切换时改变保存的图片的方向
     */
    private void startOrientationChangeListener() {
        OrientationEventListener mOrEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {
                Log.i(TAG, "当前屏幕手持角度方法:" + rotation + "°");
                if (((rotation >= 0) && (rotation <= 45)) || (rotation > 315)) {
                    rotation = 0;
                } else if ((rotation > 45) && (rotation <= 135)) {
                    rotation = 90;
                } else if ((rotation > 135) && (rotation <= 225)) {
                    rotation = 180;
                } else if ((rotation > 225) && (rotation <= 315)) {
                    rotation = 270;
                } else {
                    rotation = 0;
                }
                if (rotation == mOrientation) {
                    return;
                }
                mOrientation = rotation;

            }
        };
        mOrEventListener.enable();
    }

    protected void showToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    /**
     * 销毁引擎，faceHelper中可能会有特征提取耗时操作仍在执行，加锁防止crash
     */
    private void unInitEngine() {
        if (ftInitCode == ErrorInfo.MOK && ftEngine != null) {
            synchronized (ftEngine) {
                int ftUnInitCode = ftEngine.unInit();
                Log.i(TAG, "unInitEngine: " + ftUnInitCode);
            }
        }
        if (frInitCode == ErrorInfo.MOK && frEngine != null) {
            synchronized (frEngine) {
                int frUnInitCode = frEngine.unInit();
                Log.i(TAG, "unInitEngine: " + frUnInitCode);
            }
        }
        if (flInitCode == ErrorInfo.MOK && flEngine != null) {
            synchronized (flEngine) {
                int flUnInitCode = flEngine.unInit();
                Log.i(TAG, "unInitEngine: " + flUnInitCode);
            }
        }
    }


    @Override
    protected void onDestroy() {
        //反初始化  人证核验
        if (activeService != null) {
            activeService.shutdown();
        }
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }

        unInitEngine();
        if (getFeatureDelayedDisposables != null) {
            getFeatureDelayedDisposables.clear();
        }
        if (delayFaceTaskCompositeDisposable != null) {
            delayFaceTaskCompositeDisposable.clear();
        }
        if (faceHelper != null) {
            ConfigUtil.setTrackedFaceCount(this, faceHelper.getTrackedFaceCount());
            faceHelper.release();
            faceHelper = null;
        }

        FaceServer.getInstance().unInit();
        super.onDestroy();
    }

    private void initCamera() {
        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        int CameraInfo = userSettings.getInt("CameraInfo", Camera.CameraInfo.CAMERA_FACING_BACK);
        rgbCameraID = CameraInfo;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        final FaceListener faceListener = new FaceListener() {
            @Override
            public void onFail(Exception e) {
                Log.e(TAG, "onFail: " + e.getMessage());
            }

            //请求FR的回调
            @Override
            public void onFaceFeatureInfoGet(@Nullable final FaceFeature faceFeature, final byte[] nv21, final Integer requestId, final Integer errorCode) {
                //FR成功
                if (faceFeature != null) {
                    Log.i(TAG, "onPreview: fr end = " + "onFaceFeatureInfoGet" + " trackId = " + requestId);
                    Integer liveness = livenessMap.get(requestId);
                    //不做活体检测的情况，直接搜索
                    if (!livenessDetect) {
                        searchFace(faceFeature, nv21, requestId);
                    }
                    //活体检测通过，搜索特征
                    else if (liveness != null && liveness == LivenessInfo.ALIVE) {
                        searchFace(faceFeature, nv21, requestId);
                    }
                    //活体检测未出结果，或者非活体，延迟执行该函数
                    else {
                        if (requestFeatureStatusMap.containsKey(requestId)) {
                            Observable.timer(WAIT_LIVENESS_INTERVAL, TimeUnit.MILLISECONDS)
                                    .subscribe(new Observer<Long>() {
                                        Disposable disposable;

                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            disposable = d;
                                            getFeatureDelayedDisposables.add(disposable);
                                        }

                                        @Override
                                        public void onNext(Long aLong) {
                                            onFaceFeatureInfoGet(faceFeature, nv21, requestId, errorCode);
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {
                                            getFeatureDelayedDisposables.remove(disposable);
                                        }
                                    });
                        }
                    }

                }
                //特征提取失败
                else {
                    if (increaseAndGetValue(extractErrorRetryMap, requestId) > MAX_RETRY_TIME) {
                        extractErrorRetryMap.put(requestId, 0);

                        String msg;
                        // 传入的FaceInfo在指定的图像上无法解析人脸，此处使用的是RGB人脸数据，一般是人脸模糊
                        if (errorCode != null && errorCode == ErrorInfo.MERR_FSDK_FACEFEATURE_LOW_CONFIDENCE_LEVEL) {
                            msg = getString(R.string.low_confidence_level);
                        } else {
                            msg = "ExtractCode:" + errorCode;
                        }


                        UpdateView(msg, "txt_score", RecognizeColor.COLOR_FAILED);
                        UpdateView("识别未通过", "txt_tips", RecognizeColor.COLOR_FAILED);
                        if (faceHelper!=null){
                            faceHelper.setName(requestId, getString(R.string.recognize_failed_notice, msg));
                        }

                        // 在尝试最大次数后，特征提取仍然失败，则认为识别未通过
                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                        retryRecognizeDelayed(requestId);
                    } else {
                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.TO_RETRY);
                    }
                }
            }

            @Override
            public void onFaceLivenessInfoGet(@Nullable LivenessInfo livenessInfo, final Integer requestId, Integer errorCode) {
                if (livenessInfo != null) {
                    int liveness = livenessInfo.getLiveness();
                    livenessMap.put(requestId, liveness);
                    // 非活体，重试
                    if (liveness == LivenessInfo.NOT_ALIVE) {
                        UpdateView("非活体", "txt_tips", RecognizeColor.COLOR_UNKNOWN);
                        UpdateView("未通过", "txt_score", RecognizeColor.COLOR_UNKNOWN);
                        if (faceHelper!=null){               faceHelper.setName(requestId, getString(R.string.recognize_failed_notice, "非活体"));}

                        // 延迟 FAIL_RETRY_INTERVAL 后，将该人脸状态置为UNKNOWN，帧回调处理时会重新进行活体检测
                        retryLivenessDetectDelayed(requestId);
                    }
                } else {
                    if (increaseAndGetValue(livenessErrorRetryMap, requestId) > MAX_RETRY_TIME) {
                        livenessErrorRetryMap.put(requestId, 0);
                        String msg;
                        // 传入的FaceInfo在指定的图像上无法解析人脸，此处使用的是RGB人脸数据，一般是人脸模糊
                        if (errorCode != null && errorCode == ErrorInfo.MERR_FSDK_FACEFEATURE_LOW_CONFIDENCE_LEVEL) {
                            msg = getString(R.string.low_confidence_level);
                        } else {
                            msg = "ProcessCode:" + errorCode;
                        }
                        UpdateView(msg, "txt_score", RecognizeColor.COLOR_FAILED);
                        if (faceHelper!=null){  faceHelper.setName(requestId, getString(R.string.recognize_failed_notice, msg));}

                        retryLivenessDetectDelayed(requestId);
                    } else {
                        livenessMap.put(requestId, LivenessInfo.UNKNOWN);
                    }
                }
            }


        };


        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Camera.Size lastPreviewSize = previewSize;
                previewSize = camera.getParameters().getPreviewSize();
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation
                        , cameraId, isMirror, false, false);
                Log.i(TAG, "onCameraOpened: " + drawHelper.toString());
                // 切换相机的时候可能会导致预览尺寸发生变化
                if (faceHelper == null ||
                        lastPreviewSize == null ||
                        lastPreviewSize.width != previewSize.width || lastPreviewSize.height != previewSize.height) {
                    Integer trackedFaceCount = null;
                    // 记录切换时的人脸序号
                    if (faceHelper != null) {
                        trackedFaceCount = faceHelper.getTrackedFaceCount();
                        faceHelper.release();
                    }
                    faceHelper = new FaceHelper.Builder()
                            .ftEngine(ftEngine)
                            .frEngine(frEngine)
                            .flEngine(flEngine)
                            .frQueueSize(MAX_DETECT_NUM)
                            .flQueueSize(MAX_DETECT_NUM)
                            .previewSize(previewSize)
                            .faceListener(faceListener)
                            .trackedFaceCount(trackedFaceCount == null ? ConfigUtil.getTrackedFaceCount(ArcFaceRecognizeActivity.this.getApplicationContext()) : trackedFaceCount)
                            .build();
                }
            }


            @Override
            public void onPreview(final byte[] nv21, Camera camera) {
                if (faceRectView != null) {
                    faceRectView.clearFaceInfo();
                }
                List<FacePreviewInfo> facePreviewInfoList = faceHelper.onPreviewFrame(nv21);
                if (facePreviewInfoList != null && faceRectView != null && drawHelper != null) {
                    drawPreviewInfo(facePreviewInfoList);
                }
                registerFace(nv21, facePreviewInfoList);
                clearLeftFace(facePreviewInfoList);

                if (facePreviewInfoList != null && facePreviewInfoList.size() > 0 && previewSize != null) {
                    for (int i = 0; i < facePreviewInfoList.size(); i++) {
                        Integer status = requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId());

                        Rect rect = facePreviewInfoList.get(i).getFaceInfo().getRect();
                        double resolving = 0;
                        double area = Math.abs((rect.bottom - rect.top) * (rect.right - rect.left));
                        double creamarea = previewSize.width * previewSize.height;
                        resolving = area / creamarea;
                        if (resolving > RESOLVING || resolving == 0 || creamarea <= 0) {
                            UpdateView("正在识别", "txt_tips", RecognizeColor.COLOR_SUCCESS);


                            /**
                             * 在活体检测开启，在人脸识别状态不为成功或人脸活体状态不为处理中（ANALYZING）且不为处理完成（ALIVE、NOT_ALIVE）时重新进行活体检测
                             */
                            if (livenessDetect && (status == null || status != RequestFeatureStatus.SUCCEED)) {
                                Integer liveness = livenessMap.get(facePreviewInfoList.get(i).getTrackId());
                                if (liveness == null
                                        || (liveness != LivenessInfo.ALIVE && liveness != LivenessInfo.NOT_ALIVE && liveness != RequestLivenessStatus.ANALYZING)) {
                                    livenessMap.put(facePreviewInfoList.get(i).getTrackId(), RequestLivenessStatus.ANALYZING);
                                    faceHelper.requestFaceLiveness(nv21, facePreviewInfoList.get(i).getFaceInfo(), previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, facePreviewInfoList.get(i).getTrackId(), LivenessType.RGB);
                                }
                            }
                        } else {
                            UpdateView("距离太远了,请靠近一点!", "txt_tips", RecognizeColor.COLOR_UNKNOWN);

                        }

                        /**
                         * 对于每个人脸，若状态为空或者为失败，则请求特征提取（可根据需要添加其他判断以限制特征提取次数），
                         * 特征提取回传的人脸特征结果在{@link FaceListener#onFaceFeatureInfoGet(FaceFeature, Integer, Integer)}中回传
                         */
                        if (status == null
                                || status == RequestFeatureStatus.TO_RETRY) {
                            requestFeatureStatusMap.put(facePreviewInfoList.get(i).getTrackId(), RequestFeatureStatus.SEARCHING);
                            faceHelper.requestFaceFeature(nv21, facePreviewInfoList.get(i).getFaceInfo(), previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, facePreviewInfoList.get(i).getTrackId());
//                            Log.i(TAG, "onPreview: fr start = " + System.currentTimeMillis() + " trackId = " + facePreviewInfoList.get(i).getTrackedFaceCount());
                        }
                    }
                }
            }

            @Override
            public void onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ");
            }

            @Override
            public void onCameraError(Exception e) {
                Log.i(TAG, "onCameraError: " + e.getMessage());
            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
                if (drawHelper != null) {
                    drawHelper.setCameraDisplayOrientation(displayOrientation);
                }
                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
            }
        };

        cameraHelper = new CameraHelper.Builder()
                .previewViewSize(new Point(previewView.getMeasuredWidth(), previewView.getMeasuredHeight()))
                .rotation(getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(rgbCameraID != null ? rgbCameraID : Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
        cameraHelper.start();
    }


    /**
     * @param Msg       提示信息
     * @param viewname  提示控件名
     * @param textColor 字体颜色
     */
    private void UpdateView(String Msg, String viewname, int textColor) {
        UpdateArcFaceViewBean updateArcFaceViewBean = new UpdateArcFaceViewBean(viewname, Msg, textColor);
        Message msg = new Message();
        msg.obj = updateArcFaceViewBean;
        handler.sendMessage(msg);
    }

    private void registerFace(final byte[] nv21, final List<FacePreviewInfo> facePreviewInfoList) {
        if (registerStatus == REGISTER_STATUS_READY && facePreviewInfoList != null && facePreviewInfoList.size() > 0) {
            registerStatus = REGISTER_STATUS_PROCESSING;
            Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(ObservableEmitter<Boolean> emitter) {

                    boolean success = FaceServer.getInstance().registerNv21(ArcFaceRecognizeActivity.this, nv21.clone(), previewSize.width, previewSize.height,
                            facePreviewInfoList.get(0).getFaceInfo(), "registered " + faceHelper.getTrackedFaceCount());
                    emitter.onNext(success);
                }
            })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Boolean success) {
                            String result = success ? "register success!" : "register failed!";
                            showToast(result);
                            registerStatus = REGISTER_STATUS_DONE;
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            showToast("注册失败!");
                            registerStatus = REGISTER_STATUS_DONE;
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }



    private void drawPreviewInfo(List<FacePreviewInfo> facePreviewInfoList) {
        List<DrawInfo> drawInfoList = new ArrayList<>();
        for (int i = 0; i < facePreviewInfoList.size(); i++) {
            String name = faceHelper.getName(facePreviewInfoList.get(i).getTrackId());
            Integer liveness = livenessMap.get(facePreviewInfoList.get(i).getTrackId());
            Integer recognizeStatus = requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId());

            // 根据识别结果和活体结果设置颜色
            int color = RecognizeColor.COLOR_UNKNOWN;

            if (recognizeStatus != null) {
                if (recognizeStatus == RequestFeatureStatus.FAILED) {
                    color = RecognizeColor.COLOR_FAILED;

                }
                if (recognizeStatus == RequestFeatureStatus.SUCCEED) {
                    color = RecognizeColor.COLOR_SUCCESS;

                }
            }
            if (liveness != null && liveness == LivenessInfo.NOT_ALIVE) {
                color = RecognizeColor.COLOR_FAILED;

            }

            drawInfoList.add(new DrawInfo(drawHelper.adjustRect(facePreviewInfoList.get(i).getFaceInfo().getRect()),
                    GenderInfo.UNKNOWN, AgeInfo.UNKNOWN_AGE, liveness == null ? LivenessInfo.UNKNOWN : liveness, color,
                    name == null ? " " : name));
            if (color != RecognizeColor.COLOR_UNKNOWN) {
                UpdateView(name, "txt_score", color);
            }


        }

        drawHelper.draw(faceRectView, drawInfoList);
    }


    /**
     * 删除已经离开的人脸
     *
     * @param facePreviewInfoList 人脸和trackId列表
     */
    private void clearLeftFace(List<FacePreviewInfo> facePreviewInfoList) {
        if (compareResultList != null) {
            for (int i = compareResultList.size() - 1; i >= 0; i--) {
                if (!requestFeatureStatusMap.containsKey(compareResultList.get(i).getTrackId())) {
                    compareResultList.remove(i);
                    adapter.notifyItemRemoved(i);
                }
            }
        }
        if (facePreviewInfoList == null || facePreviewInfoList.size() == 0) {
            requestFeatureStatusMap.clear();
            livenessMap.clear();
            livenessErrorRetryMap.clear();
            extractErrorRetryMap.clear();
            if (getFeatureDelayedDisposables != null) {
                getFeatureDelayedDisposables.clear();
            }
            return;
        }
        Enumeration<Integer> keys = requestFeatureStatusMap.keys();
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            boolean contained = false;
            for (FacePreviewInfo facePreviewInfo : facePreviewInfoList) {
                if (facePreviewInfo.getTrackId() == key) {
                    contained = true;
                    break;
                }
            }
            if (!contained) {
                requestFeatureStatusMap.remove(key);
                livenessMap.remove(key);
                livenessErrorRetryMap.remove(key);
                extractErrorRetryMap.remove(key);
            }
        }


    }

    private void searchFace(final FaceFeature frFace, final byte[] nv21, final Integer requestId) {
        Observable
                .create(new ObservableOnSubscribe<CompareResult>() {
                    @Override
                    public void subscribe(ObservableEmitter<CompareResult> emitter) {
                        Log.i(TAG, "searchFace " + " trackId = " + requestId);
                        CompareResult compareResult = FaceServer.getInstance().getTopOfFaceLib(frFace);
                        Log.i(TAG, "subscribe: fr search end = " + compareResult.getSimilar() + " trackId = " + requestId);
                        emitter.onNext(compareResult);

                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CompareResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CompareResult compareResult) {
                        if (compareResult == null || compareResult.getUserName() == null) {
                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                            if (faceHelper!=null){
                                faceHelper.setName(requestId, "VISITOR " + requestId);
                            }
                            return;
                        }
                        UpdateView(String.valueOf(compareResult.getSimilar()), "txt_rate", RecognizeColor.COLOR_UNKNOWN);
                        Log.i(TAG, "onNext: fr search get result  = " + System.currentTimeMillis() + " trackId = " + requestId + "  similar = " + compareResult.getSimilar());
                        if (compareResult.getSimilar() > SIMILAR_THRESHOLD) {
                            UpdateView(String.valueOf(compareResult.getSimilar()), "txt_rate", RecognizeColor.COLOR_SUCCESS);
                            boolean isAdded = false;
                            if (compareResultList == null) {
                                requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                                if (faceHelper!=null){
                                    faceHelper.setName(requestId, "VISITOR " + requestId);
                                }

                                return;
                            }
                            for (CompareResult compareResult1 : compareResultList) {
                                if (compareResult1.getTrackId() == requestId) {
                                    isAdded = true;
                                    break;
                                }
                            }

                            if (!isAdded) {
                                //对于多人脸搜索，假如最大显示数量为 MAX_DETECT_NUM 且有新的人脸进入，则以队列的形式移除
                                if (compareResultList.size() >= MAX_DETECT_NUM) {
                                    compareResultList.remove(0);
                                    adapter.notifyItemRemoved(0);
                                }
                                //添加显示人员时，保存其trackId
                                compareResult.setTrackId(requestId);
                                compareResultList.add(compareResult);
                                adapter.notifyItemInserted(compareResultList.size() - 1);
                            }
                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.SUCCEED);
                            if (faceHelper!=null){
                                faceHelper.setName(requestId, getString(R.string.recognize_success_notice, compareResult.getUserName()));
                            }

                            //保存图片至路径
                            saveBitMapToPath(compareResult, nv21, frFace);

                        } else {
                            UpdateView(String.valueOf(compareResult.getSimilar()), "txt_rate", RecognizeColor.COLOR_FAILED);

                            try{
                                if (faceHelper!=null){  faceHelper.setName(requestId, getString(R.string.recognize_failed_notice, "相似度低"));}
                                UpdateView("相似度低", "txt_tips", RecognizeColor.COLOR_FAILED);
                                retryRecognizeDelayed(requestId);
                            }catch (Exception e){

                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //如果不进行身份证比对，直接保存活体图片不要注册
                        if (("1").equals(isOnlyFace)) {
                            saveBitMapToPathNoCard(nv21);
                        } else {
                            UpdateView("对比失败", "txt_tips", RecognizeColor.COLOR_FAILED);
                            if (faceHelper!=null){
                                faceHelper.setName(requestId, getString(R.string.recognize_failed_notice, "比对失败"));
                            }

                            retryRecognizeDelayed(requestId);
                        }


                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveBitMapToPath(CompareResult compareResult, byte[] nv21, FaceFeature facefeature) {
        if (!isSaveBitmap) {//防止多次存图
            NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(ArcFaceRecognizeActivity.this);
            Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(nv21, previewSize.width, previewSize.height);
            String path = Util.getPreferExternalFolderPath(ArcFaceRecognizeActivity.this, "live_body_face");
            String filename = Util.getPhotoFileName();
            path += File.separator + filename;

            boolean success = saveBitmapToFile(bitmap, path);
            if (success) {
                isSaveBitmap = true;
            }
            final SharedPreferences userSettings = getSharedPreferences("setting", 0);
            final SharedPreferences.Editor editor = userSettings.edit();
            editor.putString("bitmappath", path);
            if ("CardImageLiveFaceVerifyActivity".equals(formWay)) {
                editor.putString("IdCardCompareResult", String.valueOf(compareResult.getSimilar()));
            } else {
                editor.putString("dCoefficient", compareResult.getSimilar() + "");
            }

            editor.putString("FaceFeature", HexUtils.bytes2Hex(facefeature.getFeatureData()));
            editor.putInt("mOrientation", mOrientation);
            editor.apply();

            Intent intent = new Intent();
            intent.putExtra("mOrientation", mOrientation);
            setResult(1, intent);
            finish();
        }
    }

    /**
     * 不进行身份证比对
     */
    private void saveBitMapToPathNoCard(byte[] nv21) {
        if (!isSaveBitmap) {//防止多次存图
            NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(ArcFaceRecognizeActivity.this);
            Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(nv21, previewSize.width, previewSize.height);
            String path = Util.getPreferExternalFolderPath(ArcFaceRecognizeActivity.this, "live_body_face");
            String filename = Util.getPhotoFileName();
            path += File.separator + filename;

            boolean success = saveBitmapToFile(bitmap, path);
            if (success) {
                isSaveBitmap = true;
            }
            final SharedPreferences userSettings = getSharedPreferences("setting", 0);
            final SharedPreferences.Editor editor = userSettings.edit();
            editor.putString("bitmappath", path);
            if ("CardImageLiveFaceVerifyActivity".equals(formWay)) {
                editor.putString("IdCardCompareResult", "0");
            } else {
                editor.putString("dCoefficient", "0");
            }
            editor.putInt("mOrientation", mOrientation);
            editor.apply();

            Intent intent = new Intent();
            intent.putExtra("mOrientation", mOrientation);
            setResult(1, intent);
            finish();
        }
    }

    /**
     * 将准备注册的状态置为{@link #REGISTER_STATUS_READY}
     *
     * @param view 注册按钮
     */
    public void register(View view) {
        if (registerStatus == REGISTER_STATUS_DONE) {
            registerStatus = REGISTER_STATUS_READY;
        }
    }

    protected void showLongToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    /**
     * 切换相机。注意：若切换相机发现检测不到人脸，则极有可能是检测角度导致的，需要销毁引擎重新创建或者在设置界面修改配置的检测角度
     *
     * @param view
     */
    public void switchCamera(View view) {
        if (cameraHelper != null) {
            boolean success = cameraHelper.switchCamera();
            if (!success) {
                showToast(getString(R.string.switch_camera_failed));
            } else {
                showLongToast(getString(R.string.notice_change_detect_degree));
            }
        }
    }

    protected boolean checkPermissions(String[] neededPermissions) {
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
     * 在{@link #previewView}第一次布局完成后，去除该监听，并且进行引擎和相机的初始化
     */
    @Override
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            previewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
        } else {
            initEngine();
            initCamera();
        }
    }

    /**
     * 将map中key对应的value增1回传
     *
     * @param countMap map
     * @param key      key
     * @return 增1后的value
     */
    public int increaseAndGetValue(Map<Integer, Integer> countMap, int key) {
        if (countMap == null) {
            return 0;
        }
        Integer value = countMap.get(key);
        if (value == null) {
            value = 0;
        }
        countMap.put(key, ++value);
        return value;
    }

    /**
     * 延迟 FAIL_RETRY_INTERVAL 重新进行活体检测
     *
     * @param requestId 人脸ID
     */
    private void retryLivenessDetectDelayed(final Integer requestId) {
        Observable.timer(FAIL_RETRY_INTERVAL, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        delayFaceTaskCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        // 将该人脸状态置为UNKNOWN，帧回调处理时会重新进行活体检测
                        if (livenessDetect) {
                            if (faceHelper!=null){faceHelper.setName(requestId, Integer.toString(requestId));}

                        }
                        livenessMap.put(requestId, LivenessInfo.UNKNOWN);
                        delayFaceTaskCompositeDisposable.remove(disposable);
                    }
                });
    }

    /**
     * 延迟 FAIL_RETRY_INTERVAL 重新进行人脸识别
     *
     * @param requestId 人脸ID
     */
    private void retryRecognizeDelayed(final Integer requestId) {
        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
        Observable.timer(FAIL_RETRY_INTERVAL, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        delayFaceTaskCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        // 将该人脸特征提取状态置为FAILED，帧回调处理时会重新进行活体检测
                        if (faceHelper!=null){        faceHelper.setName(requestId, Integer.toString(requestId));}

                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.TO_RETRY);
                        delayFaceTaskCompositeDisposable.remove(disposable);
                    }
                });
    }


    private double parseSimilarString(String Similarity) {
        List<String> Similarityresult = Arrays.asList(Similarity.split(","));
        List<SimilarityBean> similarityBeanList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < Similarityresult.size(); i++) {
            String result = Similarityresult.get(i);
            List<String> resultlist = Arrays.asList(result.split(":"));
            if (resultlist.size() == 2) {
                List<String> agelist = Arrays.asList(resultlist.get(0).split("-"));
                String similar = resultlist.get(1);
                if (agelist.size() == 2) {
                    if (!TextUtils.isEmpty(agelist.get(0)) && !TextUtils.isEmpty(agelist.get(1)) && !TextUtils.isEmpty(similar)) {
                        int minage = Integer.parseInt(agelist.get(0));
                        int maxage = Integer.parseInt(agelist.get(1));
                        double similarf = Double.parseDouble(similar);
                        similarityBeanList.add(new SimilarityBean(minage, maxage, similarf));
                    }
                }
            }
        }
        if (similarityBeanList.size() > 0 && age != 0) {
            for (int i = 0; i < similarityBeanList.size(); i++) {
                int maxAge = similarityBeanList.get(i).getMaxAge();
                int minAge = similarityBeanList.get(i).getMinAge();
                if (age >= minAge && age <= maxAge) {
                    return similarityBeanList.get(i).getSimilarity();
                }
            }
        }
        return 0.65;
    }

    private boolean saveBitmapToFile(Bitmap bitmap, String filepath) {
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


    //    //计时时间
    public int timeing;
    //点击按钮的标志
    public boolean flag;

    private void initTimer() {
        if (flag == false) {
//(执行任务的时间间隔)
//执行任务的间隔时间为1s,当没有flag==false此处的判断,
//每当点击按钮的时候都会触发这个方法,就会导致计时变快
            handler.postDelayed(runnable, 1000);
        }
//避免在计时开始后再次按下按钮触发线程执行
        flag = true;


    }

    //创建一个Handler对象
    public Handler handlerTime = new Handler();
    //创建一个Runnable对象
    Runnable runnable = new Runnable() {

        @Override
        public void run() {

            if (timeing < 25) {
                timeing++;
//(任务内延时)
//每隔1s实现定时操作更改ui页面的数字
                handlerTime.postDelayed(this, 1000);
            } else {
//计时到10秒后关闭此定时器，重置标志位，重置计时0
                handlerTime.removeCallbacks(this);
                flag = false;
                timeing = 0;


                Intent intent = new Intent();
                // 获取用户计算后的结果
//                intent.putExtra("iComparisonCount", iComparisonCount); //将计算的值回传回去
                //通过intent对象返回结果，必须要调用一个setResult方法，
                //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
                setResult(1001, intent);
                finish();

//                setResult(1001);
//                finish();
            }
        }
    };


}
