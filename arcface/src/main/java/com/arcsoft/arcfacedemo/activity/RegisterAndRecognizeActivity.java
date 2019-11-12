package com.arcsoft.arcfacedemo.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceView;
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
import com.arcsoft.arcfacedemo.faceserver.CompareResult;
import com.arcsoft.arcfacedemo.faceserver.FaceServer;
import com.arcsoft.arcfacedemo.idcard.DetectFaceResult;
import com.arcsoft.arcfacedemo.idcard.IdCardVerifyError;
import com.arcsoft.arcfacedemo.idcard.IdCardVerifyListener;
import com.arcsoft.arcfacedemo.idcard.IdCardVerifyManager;
import com.arcsoft.arcfacedemo.idcard.MainActivity;
import com.arcsoft.arcfacedemo.model.DrawInfo;
import com.arcsoft.arcfacedemo.model.FacePreviewInfo;
import com.arcsoft.arcfacedemo.util.BitmapUtil;
import com.arcsoft.arcfacedemo.util.ConfigUtil;
import com.arcsoft.arcfacedemo.util.DrawHelper;
import com.arcsoft.arcfacedemo.util.IDCard;
import com.arcsoft.arcfacedemo.util.ImageUtil;
import com.arcsoft.arcfacedemo.util.NV21ToBitmap;
import com.arcsoft.arcfacedemo.util.Util;
import com.arcsoft.arcfacedemo.util.camera.CameraHelper;
import com.arcsoft.arcfacedemo.util.camera.CameraListener;
import com.arcsoft.arcfacedemo.util.face.FaceHelper;
import com.arcsoft.arcfacedemo.util.face.FaceListener;
import com.arcsoft.arcfacedemo.util.face.RequestFeatureStatus;
import com.arcsoft.arcfacedemo.widget.FaceRectView;
import com.arcsoft.arcfacedemo.widget.ShowFaceInfoAdapter;
import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.Face3DAngle;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.VersionInfo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.arcsoft.arcfacedemo.util.ImageUtil.bitmapToNv21;

/**
 *
 */
public class RegisterAndRecognizeActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {
    /**
     * 人证核对参数
     */
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean isInit = false;
    //比对阈值，建议为0.82
    private static final double THRESHOLD = 0.82d;
    private static double ManCardVerify = 0.82d;
    private SurfaceView surfaceView;
    private SurfaceView surfaceRect;
    private Camera camera;
    private int camereId = 0;
    private int displayOrientation = 0;
    //视频或图片人脸数据是否检测到
    private boolean isCurrentReady = false;
    //身份证人脸数据是否检测到
    private boolean isIdCardReady = false;
    //重试次数
    private static final int MAX_RETRY_TIME = 100;
    private int tryTime = 0;
    private ExecutorService activeService = Executors.newSingleThreadExecutor();
    private long startTime = 0;
    // 比对结果
    private double IdCardCompareResult = 0;
    /**
     * 人证核对监听比对
     */
    private IdCardVerifyListener idCardVerifyListener = new IdCardVerifyListener() {
        @Override
        public void onPreviewResult(DetectFaceResult detectFaceResult, byte[] bytes, int i, int i1) {
            if (detectFaceResult.getErrCode() == IdCardVerifyError.OK) {
                isCurrentReady = true;
                //需要在主线程进行比对
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        compare();
                    }
                });
            }
        }

        @Override
        public void onIdCardResult(DetectFaceResult detectFaceResult, byte[] bytes, int i, int i1) {
            if (detectFaceResult.getErrCode() == IdCardVerifyError.OK) {
                isIdCardReady = true;

                //需要在主线程进行比对
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        compare();
                    }
                });
            }
        }
    };


    private static final int MAX_DETECT_NUM = 10;
    /**
     * 当FR成功，活体未成功时，FR等待活体的时间
     */
    private static final int FACE_REGISTER_FAIL = 404;
    private static final int FACE_REGISTER_NO_BITMAP_FAIL = 405;
    private static final int WAIT_LIVENESS_INTERVAL = 50;
    private CameraHelper cameraHelper;
    private DrawHelper drawHelper;
    private Camera.Size previewSize;
    /**
     * 优先打开的摄像头
     */
    private Integer cameraID = BitmapUtil.getCameraId();
    private FaceEngine faceEngine;
    private FaceHelper faceHelper;
    private List<CompareResult> compareResultList;
    private ShowFaceInfoAdapter adapter;
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

    private int afCode = -1;
    private ConcurrentHashMap<Integer, Integer> requestFeatureStatusMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Integer> livenessMap = new ConcurrentHashMap<>();
    private CompositeDisposable getFeatureDelayedDisposables = new CompositeDisposable();
    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private View previewView;
    /**
     * 绘制人脸框的控件
     */
    private FaceRectView faceRectView;

    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final float SIMILAR_THRESHOLD = 0.8F;
    /**
     * 0 上下 1左右 2摆头  3满足其一  4无动作
     */
    private int ACTION_SELECTION = ACTION_UP_DOWN;
    private static final int ACTION_UP_DOWN = 0;
    private static final int ACTION_LEFT_RIGHT = 1;
    private static final int ACTION_SWING = 2;
    private static final int ACTION_ALL = 3;
    private static final int ACTION_NONE = 4;
    /**
     * 所需的所有权限信息
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private List<Face3DAngle> face3DList = new CopyOnWriteArrayList<>();
    //上下
    private List<Float> PitchList = new CopyOnWriteArrayList<>();
    //左右
    private List<Float> YawList = new CopyOnWriteArrayList<>();
    //印度式摆头
    private List<Float> RollList = new CopyOnWriteArrayList<>();
    private int CheckrequestId = 0;
    private int mOrientation;
    /**
     * 对多少帧进行动作统计
     */
    private int FRAME_NUMBER = 10;
    /**
     * 相似度
     */
    private double SIMILAR_NUMBER = 0.75;

    /**
     * 动作幅度差值
     */

    private int DIFFERENCE_VALUE = 8;
    private static String bitmappath;
    private int savebitmap = 0;  // 0  未存活体图片 //1 已存  取

    /**
     * 动作选择
     */

    Bitmap bmp;
    private TextView txt_score, txt_rate, txt_tips;
    private String path;
    private Boolean isSaveBitmap = false;
    private String idNumber;
    private int age = 0;
    private String formWay = "HeadCollectActivity";
    private double RESOLVING = 0.2;
    private String IdCardPath;
    private String isOnlyFace;
    private String myfaceFeature = "";
    private float Similar;
    private LinearLayout ll_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
        } else {
            formWay = intent.getStringExtra("formWay");
//            path=intent.getStringExtra("path");
            idNumber = intent.getStringExtra("idNumber");
            myfaceFeature = intent.getStringExtra("faceFeatrue");

        }

        setContentView(R.layout.activity_register_and_recognize);
        //保持亮屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        txt_score = (TextView) findViewById(R.id.txt_score);
        txt_rate = (TextView) findViewById(R.id.txt_rate);
        txt_tips = (TextView) findViewById(R.id.txt_tips);
        if (formWay.equals("CardImageLiveFaceVerifyActivity")) {
            if (intent != null) {
                IdCardPath = intent.getStringExtra("IdCardPath");
                isOnlyFace = intent.getStringExtra("isOnlyFace");
            }
            ActiveIdCard();
        }

        initInfoFromLogin();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            getWindow().setAttributes(attributes);
        }
//        activeEngine();
        // Activity启动后就锁定为启动时的方向


        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                break;
            default:
                break;
        }
//        }

        //本地人脸库初始化
        if (TextUtils.isEmpty(myfaceFeature)) {
            FaceServer.getInstance().init(this);
        } else {
            FaceServer.getInstance().initnew(this, myfaceFeature);
        }

        ConfigUtil.setFtOrient(RegisterAndRecognizeActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);
//        if (formWay.equals("CardImageLiveFaceVerifyActivity")){
//            ConfigUtil.setFtOrient(RegisterAndRecognizeActivity.this, FaceEngine.ASF_OP_90_ONLY);
//        }else {
//
//        }


        previewView = findViewById(R.id.texture_preview);
        //在布局结束后才做初始化操作
        previewView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        faceRectView = (FaceRectView) findViewById(R.id.face_rect_view);
        Switch switchLivenessDetect = (Switch) findViewById(R.id.switch_liveness_detect);
        switchLivenessDetect.setChecked(livenessDetect);

        switchLivenessDetect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                livenessDetect = isChecked;
            }
        });
        RecyclerView recyclerShowFaceInfo = (RecyclerView) findViewById(R.id.recycler_view_person);
        compareResultList = new CopyOnWriteArrayList<>();
        adapter = new ShowFaceInfoAdapter(compareResultList, this);
        recyclerShowFaceInfo.setAdapter(adapter);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int spanCount = (int) (dm.widthPixels / (getResources().getDisplayMetrics().density * 100 + 0.5f));
        recyclerShowFaceInfo.setLayoutManager(new GridLayoutManager(this, spanCount));
        recyclerShowFaceInfo.setItemAnimator(new DefaultItemAnimator());

        initTimer();
//        registerFace();
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //计时时间
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
    public Handler handler = new Handler();
    //创建一个Runnable对象
    Runnable runnable = new Runnable() {

        @Override
        public void run() {

            if (timeing < 25) {
                timeing++;
//(任务内延时)
//每隔1s实现定时操作更改ui页面的数字
                handler.postDelayed(this, 1000);
            } else {
//计时到10秒后关闭此定时器，重置标志位，重置计时0
                handler.removeCallbacks(this);
                flag = false;
                timeing = 0;


                Intent intent = new Intent();
                // 获取用户计算后的结果
                intent.putExtra("iComparisonCount", iComparisonCount); //将计算的值回传回去
                //通过intent对象返回结果，必须要调用一个setResult方法，
                //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
                setResult(1001, intent);
                finish();

//                setResult(1001);
//                finish();
            }
        }
    };


    private double dAPPFaceFaZhi = 0.3d;

    private void initInfoFromLogin() {

        startOrientationChangeListener();

        SharedPreferences userSettings = getSharedPreferences("setting", 0);
        ACTION_SELECTION = userSettings.getInt("Action", 0);
        FRAME_NUMBER = userSettings.getInt("Frames", 10);
        DIFFERENCE_VALUE = userSettings.getInt("Range", 10);
        if (!TextUtils.isEmpty(userSettings.getString("ScreenOf", "0.1"))) {
            RESOLVING = Double.parseDouble(userSettings.getString("ScreenOf", "0.2"));
        }
        SIMILAR_NUMBER = 0.6;
        if (formWay.equals("FaceFindActivity")) {

        } else if (formWay.equals("CardImageLiveFaceVerifyActivity")) {
            if ("".equals(IDCard.IDCardValidate(idNumber))) {
                age = IDCard.getAge(idNumber);
            }
            String ManCardVerifyStr = userSettings.getString("ManCardVerify", "0-8:0.4,8-50:0.5,50-2000:0.3");
            ManCardVerify = parseSimilarString(ManCardVerifyStr);
        } else if (formWay.equals("HeadCollectActivity")) {
            if ("".equals(IDCard.IDCardValidate(idNumber))) {
                age = IDCard.getAge(idNumber);
            }
            String Similarity = userSettings.getString("Similarity", "0-8:0.1,8-50:0.2,50-2000:0.3");
            SIMILAR_NUMBER = parseSimilarString(Similarity);
//            if (!TextUtils.isEmpty(path)){
//                bmp = getBitmapFromPath(path);
//            }
        }

        String sAPPFaceFaZhi = userSettings.getString("sAPPFaceFaZhi", "");
        if (sAPPFaceFaZhi == null || TextUtils.isEmpty(sAPPFaceFaZhi)) {

        } else {
            try {
                dAPPFaceFaZhi = parseSimilarString(sAPPFaceFaZhi);
            } catch (Exception e) {
                dAPPFaceFaZhi = 0.3d;
            }

        }


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

    private Bitmap getBitmapFromPath(String path) {

        if (!new File(path).exists()) {
            System.err.println("getBitmapFromPath: file not exists");
            return null;
        }
        // Bitmap bitmap = Bitmap.createBitmap(1366, 768, Config.ARGB_8888);
        // Canvas canvas = new Canvas(bitmap);
        // Movie movie = Movie.decodeFile(path);
        // movie.draw(canvas, 0, 0);
        //
        // return bitmap;

        byte[] buf = new byte[1024 * 1024];// 1M
        Bitmap bitmap = null;

        try {

            FileInputStream fis = new FileInputStream(path);
            int len = fis.read(buf, 0, buf.length);
            bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
            if (bitmap == null) {
                System.out.println("len= " + len);
                System.err
                        .println("path: " + path + "  could not be decode!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return bitmap;
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


    /**
     * 初始化引擎
     */
    private void initEngine() {
        faceEngine = new FaceEngine();
//        afCode = faceEngine.init(this, FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
//                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_LIVENESS);
        afCode = faceEngine.init(this, FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_LIVENESS | FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_AGE | FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_GENDER | FaceEngine.ASF_FACE3DANGLE);


        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);
        Log.i(TAG, "initEngine:  init: " + afCode + "  version:" + versionInfo);

        if (afCode != ErrorInfo.MOK) {
            Toast.makeText(this, getString(R.string.init_failed, afCode), Toast.LENGTH_SHORT).show();
            if (afCode == 90119 || afCode == 90115 || afCode == 4) {
                Util.clearAppData(RegisterAndRecognizeActivity.this);
                Util.clearAllCache(RegisterAndRecognizeActivity.this);
            }
            finish();
        }
    }

    /**
     * 销毁引擎
     */
    private void unInitEngine() {

        if (afCode == ErrorInfo.MOK) {
            afCode = faceEngine.unInit();
            Log.i(TAG, "unInitEngine: " + afCode);
        }
    }


    @Override
    protected void onDestroy() {
        //反初始化  人证核验
        if (activeService != null) {
            activeService.shutdown();
        }
        IdCardVerifyManager.getInstance().unInit();


        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }

        //faceHelper中可能会有FR耗时操作仍在执行，加锁防止crash
        if (faceHelper != null) {
            synchronized (faceHelper) {
                unInitEngine();
            }
            ConfigUtil.setTrackId(this, faceHelper.getCurrentTrackId());
            faceHelper.release();
        } else {
            unInitEngine();
        }
        if (getFeatureDelayedDisposables != null) {
            getFeatureDelayedDisposables.dispose();
            getFeatureDelayedDisposables.clear();
        }
        FaceServer.getInstance().unInit();
        super.onDestroy();
    }

    public void clearFaces() {
        int faceNum = FaceServer.getInstance().getFaceNumber(this);
        if (faceNum == 0) {
//            Toast.makeText(this, R.string.no_face_need_to_delete, Toast.LENGTH_SHORT).show();
        } else {
            int deleteCount = FaceServer.getInstance().clearAllFaces(RegisterAndRecognizeActivity.this);
//            Toast.makeText(RegisterAndRecognizeActivity.this, deleteCount + " faces cleared!", Toast.LENGTH_SHORT).show();

        }
    }

    private void registerFace() {

        bmp = ImageUtil.alignBitmapForNv21(bmp);
        if (bmp == null) {
            if (formWay.equals("HeadCollectActivity")) {
//                    Toast.makeText(this,"未获取到对比图片！请重新进入页面!",Toast.LENGTH_SHORT).show();

                setResult(FACE_REGISTER_NO_BITMAP_FAIL);
                finish();

            }

            return;
        }
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        //bitmap转NV21
        final byte[] nv21 = ImageUtil.bitmapToNv21(bmp, width, height);
        if (nv21 != null) {
            boolean success = FaceServer.getInstance().register(RegisterAndRecognizeActivity.this, nv21, bmp.getWidth(), bmp.getHeight(),
                    "registered ");
            if (success) {
                Toast.makeText(this, "已获取比对人脸!", Toast.LENGTH_SHORT).show();

            } else {
                if (formWay.equals("HeadCollectActivity")) {
//                        Toast.makeText(this,"未获取到比对人脸特征,请联系管理员删除本底照片!",Toast.LENGTH_SHORT).show();
                    setResult(FACE_REGISTER_FAIL);
                    finish();
                }

            }
            Log.d(TAG, "registerFace: " + success);

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

    private int iComparisonCount = 0;
    private int faceflag = 0;
    private byte[] nv22 = null;
    private int isAliveFace=0;

    private void initCamera() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        final FaceListener faceListener = new FaceListener() {
            @Override
            public void onFail(Exception e) {
                Log.e(TAG, "onFail: " + e.getMessage());
            }

            //请求FR的回调
            @Override
            public void onFaceFeatureInfoGet(@Nullable final FaceFeature faceFeature, final Integer requestId) {


                //FR成功
                if (faceFeature != null) {

                    //不做活体检测的情况，直接搜索
                    if (!livenessDetect) {
                        searchFace(faceFeature, requestId);
                    }
                    //活体检测通过，搜索特征
                    else if (livenessMap.get(requestId) != null && livenessMap.get(requestId) == LivenessInfo.ALIVE) {
                        isAliveFace=1;
                        if (CheckrequestId == 0) {
                            CheckrequestId = requestId;
                        } else if (CheckrequestId != requestId) {
                            face3DList.clear();
                            CheckrequestId = requestId;
                        }
                        //活体比对
                        searchFace(faceFeature, requestId);

                        Log.d("face3D", "requestId: " + requestId);
                    }
                    //活体检测未出结果，延迟100ms再执行该函数
                    else if (livenessMap.get(requestId) != null && livenessMap.get(requestId) == LivenessInfo.UNKNOWN) {
                        isAliveFace=-1;
                        getFeatureDelayedDisposables.add(Observable.timer(WAIT_LIVENESS_INTERVAL, TimeUnit.MILLISECONDS)
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) {
                                        onFaceFeatureInfoGet(faceFeature, requestId);
                                    }
                                }));
                    }
                    //活体检测失败
                    else {
                        isAliveFace=0;
                        Log.d("livenessMap", "livenessMap.size:" + livenessMap.size() + " " + requestId);
                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.NOT_ALIVE);
                    }

                }
                //FR 失败
                else {
                    isAliveFace=0;
                    requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                }
            }

        };

//        NV21ToBitmap nv21ToBitmap1;
        final NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);


        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                previewSize = camera.getParameters().getPreviewSize();
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation
                        , cameraId, isMirror);
                if (faceEngine == null) {
                    if (TextUtils.isEmpty(myfaceFeature)) {
                        FaceServer.getInstance().init(RegisterAndRecognizeActivity.this);
                    } else {
                        FaceServer.getInstance().initnew(RegisterAndRecognizeActivity.this, myfaceFeature);
                    }
                    ConfigUtil.setFtOrient(RegisterAndRecognizeActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);
                }

                faceHelper = new FaceHelper.Builder()
                        .faceEngine(faceEngine)
                        .frThreadNum(MAX_DETECT_NUM)
                        .previewSize(previewSize)
                        .faceListener(faceListener)
                        .currentTrackId(ConfigUtil.getTrackId(RegisterAndRecognizeActivity.this.getApplicationContext()))
                        .build();
            }

            @Override
            public void onPreview(final byte[] nv21, Camera camera) {


                if (faceRectView != null) {
                    faceRectView.clearFaceInfo();
                }
                final List<FacePreviewInfo> facePreviewInfoList = faceHelper.onPreviewFrame(nv21);

                /**--------------------------------------------------------------人证核对数据------------------------------------------------------------------*/
                if (formWay.equals("CardImageLiveFaceVerifyActivity")) {
                    //视频数据
                    if (isInit) {
                        final int mWidth = previewSize.width;
                        final int mHeight = previewSize.height;
                        DetectFaceResult result = IdCardVerifyManager.getInstance().onPreviewData(nv21, mWidth, mHeight, true);
                        if (result.getErrCode() != IdCardVerifyError.OK) {
                            Log.i(TAG, "onPreviewData video result: " + result.getErrCode());
                        }

                    }
                }

                /**--------------------------------------------------------------------------------------------------------------------------------------------*/
                if (facePreviewInfoList != null && faceRectView != null && drawHelper != null) {
                    List<DrawInfo> drawInfoList = new CopyOnWriteArrayList<>();
                    for (int i = 0; i < facePreviewInfoList.size(); i++) {
//                        int i1 = facePreviewInfoList.get(i).getLivenessInfo().getLiveness();
                        int i1 = isAliveFace;

                        Rect rect = facePreviewInfoList.get(i).getFaceInfo().getRect();
                        double resolving = 0;
                        double area = Math.abs((rect.bottom - rect.top) * (rect.right - rect.left));
                        double creamarea = previewSize.width * previewSize.height;
                        resolving = area / creamarea;

                        if (resolving > RESOLVING || resolving == 0 || creamarea <= 0) {

                            if (i1 == -1) {
                                face3DList.clear();
                                Log.d("face3DList", "非活体");
                                txt_tips.setText("请前后移动下手机");
                            } else if (i1 == 0) {
                                face3DList.clear();
                                txt_score.setText("未识别");
                                Log.d("face3DList", "未识别");
                            } else if (i1 == 1) {
                                final List<FaceInfo> faceInfoList = new CopyOnWriteArrayList<>();
                                faceInfoList.clear();
                                faceInfoList.add(facePreviewInfoList.get(i).getFaceInfo());
                                NV21ToBitmap nv21ToBitmap;

                                Bitmap bitmap = null;
                                txt_tips.setText("请前后移动下手机...");
                                txt_score.setText("识别中...");
/**-------------------------------------------------相似度检测（人证 : IdCardCompareResult，人人 : name）-------------------------------------*/
                                if (!formWay.equals("HeadCollectActivity")) {
                                    Similar = 1;
                                }
//                                if (!TextUtils.isEmpty(name)) {
//                                    double similar = Double.parseDouble(name);

                                boolean checksimilar = true;
                                if (formWay.equals("CardImageLiveFaceVerifyActivity")) {

                                    txt_rate.setText(String.valueOf(nf.format(IdCardCompareResult)));//转为百分数
                                    checksimilar = IdCardCompareResult >= ManCardVerify;
                                    if (!TextUtils.isEmpty(isOnlyFace) && ("1").equals(isOnlyFace)) {
                                        checksimilar = true;
                                    }
                                } else if (formWay.equals("HeadCollectActivity")) {

                                    txt_rate.setText(String.valueOf(nf.format(Similar)));
                                    checksimilar = Similar > SIMILAR_NUMBER;

                                    //如果识别次数在0.3和标准值之间，累加次数
                                    if (Similar > dAPPFaceFaZhi && Similar < SIMILAR_NUMBER) {
                                        iComparisonCount++;
                                    }

                                }
                                if (checksimilar) {
/**----------------------------------------------------------------------------------------------------------------------------------------------------*/

                                    //若摇头或者点头时保存第一帧照片
                                    if ((faceflag == 0) && (ACTION_SELECTION != ACTION_NONE)) {
                                        nv22 = nv21;
                                        faceflag = 1;
                                    }
                                    //识别次数赋值为0
                                    iComparisonCount = 0;
                                    Log.d("face3DList", "face3DList: " + face3DList.size());
                                    if (face3DList.size() > FRAME_NUMBER) {

                                        //帧数
                                        float range = 0;
                                        if (ACTION_SELECTION != ACTION_NONE) {
                                            range = countActiveRange();
                                        }
                                        if (ACTION_SELECTION == ACTION_NONE || range >= DIFFERENCE_VALUE) { //动作幅度
//
                                            txt_score.setText("识别成功!!");
                                            if (!isSaveBitmap) {//防止多次存图
                                                if (savebitmap == 0) {
                                                    faceflag = 0;

                                                    nv21ToBitmap = new NV21ToBitmap(RegisterAndRecognizeActivity.this);
                                                    if (ACTION_SELECTION != ACTION_NONE) {
                                                        if (nv22 == null) {
                                                            bitmap = nv21ToBitmap.nv21ToBitmap(nv21, previewSize.width, previewSize.height);
                                                        } else {
                                                            bitmap = nv21ToBitmap.nv21ToBitmap(nv22, previewSize.width, previewSize.height);

                                                        }
                                                    } else {
                                                        bitmap = nv21ToBitmap.nv21ToBitmap(nv21, previewSize.width, previewSize.height);
                                                    }
//                                                        bitmap = nv21ToBitmap.nv21ToBitmap(nv21, previewSize.width, previewSize.height);
//                                                    saveBitmap(RegisterAndRecognizeActivity.this,bitmap);
                                                    String path = Util.getPreferExternalFolderPath(RegisterAndRecognizeActivity.this, "live_body_face");
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
                                                        editor.putString("IdCardCompareResult", String.valueOf(IdCardCompareResult));
                                                    } else {
                                                        editor.putString("dCoefficient", Similar + "");
                                                    }
                                                    editor.putInt("mOrientation", mOrientation);
                                                    editor.apply();

                                                    Intent intent = new Intent();
                                                    intent.putExtra("mOrientation", mOrientation);
                                                    setResult(1, intent);
                                                    finish();
                                                }

                                            }

                                        } else {
                                            if (ACTION_SELECTION == 0) {  //动作选择
                                                txt_tips.setText("请上下摇头!");
                                            } else if (ACTION_SELECTION == 1) {
                                                txt_tips.setText("请左右摇头!");
                                            } else if (ACTION_SELECTION == 2) {
//
                                                txt_tips.setText("请摇头!");
                                            } else if (ACTION_SELECTION == 3) {
//
                                                txt_tips.setText("请摇头或点头!");
                                            } else if (ACTION_SELECTION == 4) {
//                                                    txtScore.setText("正在检测!");
                                            }
                                        }
                                    } else {

                                        if (ACTION_SELECTION == 0) {  //动作选择
//
                                            txt_tips.setText("请上下摇头!");
                                        } else if (ACTION_SELECTION == 1) {
//
                                            txt_tips.setText("请左右摇头!");

                                        } else if (ACTION_SELECTION == 2) {
                                            txt_tips.setText("请摇头!");
                                        } else if (ACTION_SELECTION == 3) {
//
                                            txt_tips.setText("请摇头或点头!");
                                        } else if (ACTION_SELECTION == 4) {
//                                                txtScore.setText("正在检测!");
                                        }
                                    }

                                    Observable.create(new ObservableOnSubscribe<Object>() {
                                        @Override
                                        public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                                            processImage(nv21, faceInfoList);
                                            emitter.onComplete();
                                        }
                                    })
                                            .subscribeOn(Schedulers.computation())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<Object>() {
                                                @Override
                                                public void onSubscribe(Disposable d) {

                                                }

                                                @Override
                                                public void onNext(Object o) {

                                                }

                                                @Override
                                                public void onError(Throwable e) {

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });

                                } else {  //相似度低

                                    if ("CardImageLiveFaceVerifyActivity".equals(formWay)) {
                                        if (IdCardCompareResult <= 0) {
                                            txt_tips.setText("相似度低!，请确保身份证照片清晰！");

                                        } else {

                                            txt_rate.setText(String.valueOf(nf.format(IdCardCompareResult)));
                                        }
                                    } else if ("HeadCollectActivity".equals(formWay)) {
//                                                if (face3DList.size()>20){ //帧数
//                                                    setResult(65585);
//                                                    finish();
//                                                }
//                                                txt_rate.setText(String.valueOf(nf.format(similar)));
                                        txt_tips.setText("相似度低!，请确保当前光线不要太暗或者反光！");

                                    }


//                                        txtScore.setText("请尝试换个环境拍摄!确保当前光线不要太暗或者反光！");
                                }

//
//                                }


                            } else if (i1 == 2) {
                                txt_tips.setText("人脸数超过1!");
                            }
                            drawInfoList.add(new DrawInfo(facePreviewInfoList.get(i).getFaceInfo().getRect(), GenderInfo.UNKNOWN, AgeInfo.UNKNOWN_AGE, LivenessInfo.UNKNOWN,
                                    String.valueOf(Similar)));
                        }
//                        else if (resolving==0){
//                            txtScore.setText("未识别!");
//                        }
                        else {
                            txt_tips.setText("人距离太远了,请靠近一点!");
                        }

                    }
                    drawHelper.draw(faceRectView, drawInfoList);
                }
                if (registerStatus == REGISTER_STATUS_READY && facePreviewInfoList != null && facePreviewInfoList.size() > 0) {
                    registerStatus = REGISTER_STATUS_PROCESSING;
                    Observable.create(new ObservableOnSubscribe<Boolean>() {
                        @Override
                        public void subscribe(ObservableEmitter<Boolean> emitter) {
                            boolean success = FaceServer.getInstance().register(RegisterAndRecognizeActivity.this, nv21.clone(), previewSize.width, previewSize.height, "registered " + faceHelper.getCurrentTrackId());
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
                                    Toast.makeText(RegisterAndRecognizeActivity.this, result, Toast.LENGTH_SHORT).show();
                                    registerStatus = REGISTER_STATUS_DONE;
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(RegisterAndRecognizeActivity.this, "register failed!", Toast.LENGTH_SHORT).show();
                                    registerStatus = REGISTER_STATUS_DONE;
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                clearLeftFace(facePreviewInfoList);

                if (facePreviewInfoList != null && facePreviewInfoList.size() > 0 && previewSize != null) {

                    for (int i = 0; i < facePreviewInfoList.size(); i++) {
                        if (livenessDetect) {
                            livenessMap.put(facePreviewInfoList.get(i).getTrackId(), facePreviewInfoList.get(i).getLivenessInfo().getLiveness());
                            Log.d("livenessMap", "livenessMap.size:" + livenessMap.size() + "TrackId" + facePreviewInfoList.get(i).getTrackId());
                        }
                        /**
                         * 对于每个人脸，若状态为空或者为失败，则请求FR（可根据需要添加其他判断以限制FR次数），
                         * FR回传的人脸特征结果在{@link FaceListener#onFaceFeatureInfoGet(FaceFeature, Integer)}中回传
                         */
                        if (requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId()) == null
                                || requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId()) == RequestFeatureStatus.FAILED
                                || requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId()) == RequestFeatureStatus.NOT_ALIVE
                        ) {
                            requestFeatureStatusMap.put(facePreviewInfoList.get(i).getTrackId(), RequestFeatureStatus.SEARCHING);
                            faceHelper.requestFaceFeature(nv21, facePreviewInfoList.get(i).getFaceInfo(), previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, facePreviewInfoList.get(i).getTrackId());
//                            Log.i(TAG, "onPreview: fr start = " + System.currentTimeMillis() + " trackId = " + facePreviewInfoList.get(i).getTrackId());
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
                .specificCameraId(cameraID != null ? cameraID : Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
    }

    /**
     * @return 动作幅度 计算
     */
    private float countActiveRange() {
        List<Float> floats = new CopyOnWriteArrayList<>();
        if (ACTION_SELECTION == 0) {  //动作选择
            floats = PitchList;
        } else if (ACTION_SELECTION == 1) {
            floats = YawList;
        } else if (ACTION_SELECTION == 2
        ) {
            floats = RollList;
        } else {
            floats = PitchList;
        }
        float min = Collections.min(floats);
        float max = Collections.max(floats);
        float leng = max - min;
        leng = Math.abs(leng);
        if (ACTION_SELECTION == 3) {  //满足其一
            float Pitchmin = Collections.min(PitchList);
            float Pitchmax = Collections.max(PitchList);
            float Pitchleng = Pitchmax - Pitchmin;
            Pitchleng = Math.abs(Pitchleng);

            float Yawmin = Collections.min(YawList);
            float Yawmax = Collections.max(YawList);
            float Yawleng = Yawmax - Yawmin;
            Yawleng = Math.abs(Yawleng);

            float Rollmin = Collections.min(RollList);
            float Rollmax = Collections.max(RollList);
            float Rollleng = Rollmax - Rollmin;
            Rollleng = Math.abs(Rollleng);

            List<Float> floatlist = new CopyOnWriteArrayList<>();
            floatlist.add(Pitchleng);
            floatlist.add(Yawleng);
            floatlist.add(Rollleng);


            leng = Collections.max(floatlist);
            return leng;
        }
        return leng;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                initEngine();
                initCamera();
                if (cameraHelper != null) {
                    cameraHelper.start();
                }
            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 删除已经离开的人脸
     *
     * @param facePreviewInfoList 人脸和trackId列表
     */
    private void clearLeftFace(List<FacePreviewInfo> facePreviewInfoList) {
        Set<Integer> keySet = requestFeatureStatusMap.keySet();
        if (compareResultList != null) {
            for (int i = compareResultList.size() - 1; i >= 0; i--) {
                if (!keySet.contains(compareResultList.get(i).getTrackId())) {
                    compareResultList.remove(i);
                    adapter.notifyItemRemoved(i);
                }
            }
        }
        if (facePreviewInfoList == null || facePreviewInfoList.size() == 0) {
            requestFeatureStatusMap.clear();

            livenessMap.clear();
            return;
        }

        for (Integer integer : keySet) {
            boolean contained = false;
            for (FacePreviewInfo facePreviewInfo : facePreviewInfoList) {
                if (facePreviewInfo.getTrackId() == integer) {
                    contained = true;
                    break;
                }
            }
            if (!contained) {
                requestFeatureStatusMap.remove(integer);
                livenessMap.remove(integer);
                Log.d("livenessMap", "remove");
            }
        }

    }

    private void searchFace(final FaceFeature frFace, final Integer requestId) {
        Observable
                .create(new ObservableOnSubscribe<CompareResult>() {
                    @Override
                    public void subscribe(ObservableEmitter<CompareResult> emitter) {
                        //如果存在特征值，直接拿特征值和当前人脸界面去比
                        if (TextUtils.isEmpty(myfaceFeature)) {
                            CompareResult compareResult = FaceServer.getInstance().getTopOfFaceLib(RegisterAndRecognizeActivity.this, frFace);
                            if (compareResult == null) {
                                emitter.onError(null);
                            } else {
                                emitter.onNext(compareResult);
                            }
                        } else {
                            CompareResult compareResult = FaceServer.getInstance().getTopOfFaceLibNew(RegisterAndRecognizeActivity.this, frFace, myfaceFeature);
                            if (compareResult == null) {
                                emitter.onError(null);
                            } else {
                                emitter.onNext(compareResult);
                            }
                        }


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
//                        if (compareResult == null || compareResult.getUserName() == null) {
//                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
////                            faceHelper.addName(requestId, "VISITOR " + requestId);
//                            return;
//                        }
                        if (compareResult == null) {
                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                            return;
                        }
                        Similar = compareResult.getSimilar();
//
////                        Log.i(TAG, "onNext: fr search get result  = " + System.currentTimeMillis() + " trackId = " + requestId + "  similar = " + compareResult.getSimilar());
//                        if (compareResult.getSimilar() > SIMILAR_NUMBER) {
//                            boolean isAdded = false;
////                            if (compareResultList == null) {
//                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.SUCCEED);
////                            faceHelper.addName(requestId, "" + compareResult.getSimilar());
//                            Similar = compareResult.getSimilar();
////                                return;
////                            }
////                            for (CompareResult compareResult1 : compareResultList) {
////                                if (compareResult1.getTrackId() == requestId) {
////                                    isAdded = true;
////                                    break;
////                                }
////                            }
////                            if (!isAdded) {
////                                //对于多人脸搜索，假如最大显示数量为 MAX_DETECT_NUM 且有新的人脸进入，则以队列的形式移除
////                                if (compareResultList.size() >= MAX_DETECT_NUM) {
////                                    compareResultList.remove(0);
////                                    adapter.notifyItemRemoved(0);
////                                }
////                                //添加显示人员时，保存其trackId
////                                compareResult.setTrackId(requestId);
////                                compareResultList.add(compareResult);
////                                adapter.notifyItemInserted(compareResultList.size() - 1);
////                            }
////                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.SUCCEED);
////                            faceHelper.addName(requestId, compareResult.getUserName());
//
//                        } else {
//                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
////                            faceHelper.addName(requestId, "" + compareResult.getSimilar());
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    /**
     * 在{@link #previewView}第一次布局完成后，去除该监听，并且进行引擎和相机的初始化
     */
    @Override
    public void onGlobalLayout() {
        previewView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
        } else {
            initEngine();
            initCamera();

        }
    }

    /**
     * 退出
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            //具体的操作代码

            setResult(2);
//                saveBitmapForSdCard("img" + time, bitmap);
            finish();
        }
        return super.dispatchKeyEvent(event);

    }


    /**
     * 三位角度检测
     */
    public void processImage(final byte[] nv21, List<FaceInfo> faceInfoList) {

        /**
         * 传入给process函数，进行年龄、性别、三维角度检测
         */

        long processStartTime = System.currentTimeMillis();
        int faceProcessCode = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, FaceEngine.ASF_AGE | FaceEngine.ASF_GENDER | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_LIVENESS);

        if (faceProcessCode != ErrorInfo.MOK) {
//            addNotificationInfo(notificationSpannableStringBuilder, new ForegroundColorSpan(Color.RED), "process failed! code is ", String.valueOf(faceProcessCode), "\n");
        } else {
//            Log.i(TAG, "processImage: process costTime = " + (System.currentTimeMillis() - processStartTime));
        }
        //年龄信息结果
//        List<AgeInfo> ageInfoList = new ArrayList<>();
        //性别信息结果
//        List<GenderInfo> genderInfoList = new ArrayList<>();
        //人脸三维角度结果
        List<Face3DAngle> face3DAngleList = new CopyOnWriteArrayList<>();
        //活体检测结果
//        List<LivenessInfo> livenessInfoList = new ArrayList<>();
        //获取年龄、性别、三维角度、活体结果
//        int ageCode = faceEngine.getAge(ageInfoList);
//        int genderCode = faceEngine.getGender(genderInfoList);
        int face3DAngleCode = faceEngine.getFace3DAngle(face3DAngleList);
//        int livenessCode = faceEngine.getLiveness(livenessInfoList);
        Log.d("face3D", "Pitch: " + face3DAngleList.get(0).getPitch() + " Roll:" +
                face3DAngleList.get(0).getRoll() + " Status:" + face3DAngleList.get(0).getStatus() + " Yaw:"
                + face3DAngleList.get(0).getYaw());
        if (face3DAngleList.size() > 0) {

            face3DList.add(face3DAngleList.get(0));
            Log.d("face3DList", "+1" + "  :" + face3DList.size());
            PitchList.add(face3DAngleList.get(0).getPitch());
            YawList.add(face3DAngleList.get(0).getYaw());
            RollList.add(face3DAngleList.get(0).getRoll());
        }

    }
/**-------------------------------------————————————人证核对——————————————————————————-------------------------------------------------*/

    /**
     * 比 对
     */
    private void compare() {
        if (!isCurrentReady || !isIdCardReady) {
            return;
        }
        //人证比对
        com.arcsoft.arcfacedemo.idcard.CompareResult compareResult = IdCardVerifyManager.getInstance().compareFeature(ManCardVerify);
        Log.i(TAG, "compareFeature: result " + compareResult.getResult() + ", isSuccess "
                + compareResult.isSuccess() + ", errCode " + compareResult.getErrCode());
        IdCardCompareResult = compareResult.getResult();
//    txtScore.setText("compareFeature: result " + compareResult.getResult());
//    Toast.makeText(this, "compareFeature: result " + compareResult.getResult()
//            + ", costTime " + (System.currentTimeMillis() - startTime) + ", isSuccess "
//            + compareResult.isSuccess() + ", errCode " + compareResult.getErrCode(), Toast.LENGTH_LONG).show();
        isIdCardReady = false;
        isCurrentReady = false;

        if (!compareResult.isSuccess() && tryTime < MAX_RETRY_TIME) {
            tryTime++;
            inputIdCard();
        } else {
            tryTime = 0;
        }
    }

    /**
     * 传入身份证数据,并校验身份证是否符合要求
     */
    private void inputIdCard() {
        if (!TextUtils.isEmpty(IdCardPath)) {
            if (isInit) {
                DetectFaceResult result = IdCardVerifyManager.getInstance().inputIdCardData(nv21Data, idCardwidth, idCardheight);
                if (0 != result.getErrCode()) {
                    Intent intent=new Intent();
                    intent.putExtra("ErrCode",result.getErrCode()+"");
                    setResult(65539,intent);
                    finish();
                }
                Log.i(TAG, "inputIdCardData result: " + result.getErrCode());
            }
        }


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
        return bitmap;// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 激活 初始化
     */
    byte[] nv21Data;
    int idCardheight;
    int idCardwidth;

    private void ActiveIdCard() {
        boolean allGranted = true;


        if (!TextUtils.isEmpty(IdCardPath)) {
            Bitmap bitmap = getImage(IdCardPath);
            idCardwidth = bitmap.getWidth() - (bitmap.getWidth() % 4);
            //身份证数据高（高度需为2的倍数）
            idCardheight = bitmap.getHeight() - (bitmap.getHeight() % 2);
            nv21Data = bitmapToNv21(bitmap, idCardwidth, idCardheight);

        }


        for (String needPermission : NEEDED_PERMISSIONS) {
            allGranted &= ContextCompat.checkSelfPermission(this, needPermission) == PackageManager.PERMISSION_GRANTED;
        }
        if (!allGranted) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, 2);
        }
        //初始化
        int initResult = IdCardVerifyManager.getInstance().init(this, idCardVerifyListener);
        isInit = initResult == IdCardVerifyError.OK;
        inputIdCard();
        Log.i(TAG, "init result: " + initResult);
        if (!isInit) {
            Toast.makeText(RegisterAndRecognizeActivity.this, "init result: "
                    + initResult, Toast.LENGTH_LONG).show();
        }

/////////////////////////////////////////////////////////////////////////////////////
//        final int activeResult = IdCardVerifyManager.getInstance().active(
//                RegisterAndRecognizeActivity.this, Constants.APP_ID, Constants.SDK_KEY);
//        Log.i(TAG, "active result: " + activeResult);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(RegisterAndRecognizeActivity.this, "active result: "
//                        + activeResult, Toast.LENGTH_LONG).show();
//                if(activeResult == IdCardVerifyError.OK) {
//                    //初始化
//                    int initResult = IdCardVerifyManager.getInstance().init(
//                            RegisterAndRecognizeActivity.this, idCardVerifyListener);
//                    isInit = initResult == IdCardVerifyError.OK;
//                    Log.i(TAG, "init result: " + initResult);
//                    if(!isInit) {
//                        Toast.makeText(RegisterAndRecognizeActivity.this, "init result: "
//                                + initResult, Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });

    }


}
