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
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.bean.SimilarityBean;
import com.arcsoft.arcfacedemo.faceserver.CompareResult;
import com.arcsoft.arcfacedemo.faceserver.FaceServer;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

public class ArcFaceForFindingActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "RegisterAndRecognize";
    private static final int MAX_DETECT_NUM = 10;
    /**
     * 当FR成功，活体未成功时，FR等待活体的时间
     */
    private static  final  int FACE_REGISTER_FAIL=404;
    private static  final  int FACE_REGISTER_NO_BITMAP_FAIL=405;
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

    private Switch switchLivenessDetect;

    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final float SIMILAR_THRESHOLD = 0.8F;
    /**
     * 所需的所有权限信息
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };
    int face3Dtimes=0;
    private  List<Face3DAngle> face3DList =new ArrayList<>();
    private  List<Float> PitchList =new ArrayList<>();//上下
    private  List<Float> YawList =new ArrayList<>();//左右
    private  List<Float> RollList =new ArrayList<>();//印度式摆头
    private int CheckrequestId=0;
    /**
     * 对多少帧进行动作统计
     */
    private int FRAME_NUMBER =50;
    /**
     * 相似度
     */
    private double  SIMILAR_NUMBER=0.75;

    /**
     * 动作幅度差值
     */

    private int DIFFERENCE_VALUE=8;
    private static String bitmappath;
    private int  savebitmap=0;  // 0  未存活体图片 //1 已存  取

    /**
     * 动作选择
     */

    Bitmap bmp;
    private int ACTION_SELECTION =0;  //0 上下 1左右 2摆头  3满足其一  4无动作
    private TextView txtScore;
    private String path;
    private Boolean isSaveBitmap=false;
    private String idNumber;
    private int age=0;
    private  String formWay="HeadCollectActivity"; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
        } else {
            formWay=intent.getStringExtra("formWay");
            path=intent.getStringExtra("path");
            idNumber=intent.getStringExtra("idNumber");
        }

        setContentView(R.layout.activity_register_and_recognize);
        //保持亮屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        txtScore = (TextView)findViewById(R.id.txt_score);
        
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
        FaceServer.getInstance().init(this);
        ConfigUtil.setFtOrient(ArcFaceForFindingActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);

        previewView = findViewById(R.id.texture_preview);
        //在布局结束后才做初始化操作
        previewView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        faceRectView = (FaceRectView) findViewById(R.id.face_rect_view);
        switchLivenessDetect =(Switch) findViewById(R.id.switch_liveness_detect);
        switchLivenessDetect.setChecked(livenessDetect);

        switchLivenessDetect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                livenessDetect = isChecked;
            }
        });
        RecyclerView recyclerShowFaceInfo =(RecyclerView) findViewById(R.id.recycler_view_person);
        compareResultList = new ArrayList<>();
        adapter = new ShowFaceInfoAdapter(compareResultList, this);
        recyclerShowFaceInfo.setAdapter(adapter);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int spanCount = (int) (dm.widthPixels / (getResources().getDisplayMetrics().density * 100 + 0.5f));
        recyclerShowFaceInfo.setLayoutManager(new GridLayoutManager(this, spanCount));
        recyclerShowFaceInfo.setItemAnimator(new DefaultItemAnimator());


        registerFace();
    }

    private  void  initInfoFromLogin(){

        if (formWay.equals("FaceFindActivity")){
            SharedPreferences userSettings = getSharedPreferences("setting", 0);
            ACTION_SELECTION= userSettings.getInt("Action",0);
            FRAME_NUMBER =userSettings.getInt("Frames",50) ;
            DIFFERENCE_VALUE  = userSettings.getInt("Range",10);
            SIMILAR_NUMBER=0;
        }else  if (formWay.equals("CardImageLiveFaceVerifyActivity")){
            SharedPreferences userSettings = getSharedPreferences("setting", 0);
            ACTION_SELECTION= userSettings.getInt("Action",0);
            FRAME_NUMBER =userSettings.getInt("Frames",50) ;
            DIFFERENCE_VALUE  = userSettings.getInt("Range",10);
            SIMILAR_NUMBER=0;
        }else if (formWay.equals("HeadCollectActivity")){
            if ("".equals(IDCard.IDCardValidate(idNumber))) {
                age= IDCard.getAge(idNumber);
            }
            SharedPreferences userSettings = getSharedPreferences("setting", 0);
            ACTION_SELECTION= userSettings.getInt("Action",0);
            FRAME_NUMBER =userSettings.getInt("Frames",50) ;
            DIFFERENCE_VALUE  = userSettings.getInt("Range",10);
            String  Similarity =userSettings.getString("Similarity","0-8:0.5,8-50:0.6,50-2000:0.4") ;
            List<String> Similarityresult =  Arrays.asList(Similarity.split(","));
            List<SimilarityBean> similarityBeanList=new ArrayList<>();
            for (int i=0;i<Similarityresult.size();i++){
                String result= Similarityresult.get(i);
                List<String>  resultlist=  Arrays.asList(result.split(":"));
                if (resultlist.size()==2){
                    List<String>  agelist=  Arrays.asList(resultlist.get(0).split("-"));
                    String similar=resultlist.get(1);
                    if (agelist.size()==2){
                        if (!TextUtils.isEmpty(agelist.get(0))&&!TextUtils.isEmpty(agelist.get(1))&&!TextUtils.isEmpty(similar)){
                            int minage= Integer.parseInt(agelist.get(0));
                            int maxage=Integer.parseInt(agelist.get(1));
                            double similarf=Double.parseDouble(similar);
                            similarityBeanList.add(new SimilarityBean(minage,maxage,similarf));
                        }
                    }
                }
            }
            if (similarityBeanList.size()>0&&age!=0){
                for (int i=0;i<similarityBeanList.size();i++){
                    int maxAge= similarityBeanList.get(i).getMaxAge();
                    int minAge=similarityBeanList.get(i).getMinAge();
                    if (age>minAge&&age<=maxAge){
                        SIMILAR_NUMBER=similarityBeanList.get(i).getSimilarity();
                    }
                }
            }
            if (!TextUtils.isEmpty(path)){
                bmp = getBitmapFromPath(path);
            }
        }
    }
    private   Bitmap getBitmapFromPath(String path) {

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
     * 初始化引擎
     */
    private void initEngine() {
        faceEngine = new FaceEngine();
//        afCode = faceEngine.init(this, FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
//                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_LIVENESS);
        afCode = faceEngine.init(this, FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_LIVENESS|  FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_AGE | FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_GENDER | FaceEngine.ASF_FACE3DANGLE);



        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);
        Log.i(TAG, "initEngine:  init: " + afCode + "  version:" + versionInfo);

        if (afCode != ErrorInfo.MOK) {
            Toast.makeText(this, getString(R.string.init_failed, afCode), Toast.LENGTH_SHORT).show();
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
        if (faceNum == 0){
//            Toast.makeText(this, R.string.no_face_need_to_delete, Toast.LENGTH_SHORT).show();
        }else {
            int deleteCount = FaceServer.getInstance().clearAllFaces(ArcFaceForFindingActivity.this);
//            Toast.makeText(RegisterAndRecognizeActivity.this, deleteCount + " faces cleared!", Toast.LENGTH_SHORT).show();

        }
    }
        private void registerFace(){

            bmp = ImageUtil.alignBitmapForNv21(bmp);
            if (bmp == null) {
                if (formWay.equals("HeadCollectActivity")){
//                    Toast.makeText(this,"未获取到对比图片！请重新进入页面!",Toast.LENGTH_SHORT).show();
                    setResult(FACE_REGISTER_FAIL);
                    finish();

                }

                return;
            }
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            //bitmap转NV21
            final byte[] nv21 = ImageUtil.bitmapToNv21(bmp, width, height);
            if (nv21 != null) {
                boolean success = FaceServer.getInstance().register(ArcFaceForFindingActivity.this, nv21, bmp.getWidth(), bmp.getHeight(),
                        "registered " );
                if (success){
                    Toast.makeText(this,"已获取比对人脸!",Toast.LENGTH_SHORT).show();

                }else {
                    if (formWay.equals("HeadCollectActivity")){
//                        Toast.makeText(this,"未获取到比对人脸特征,请联系管理员删除本底照片!",Toast.LENGTH_SHORT).show();
                        setResult(FACE_REGISTER_FAIL);
                        finish();
                    }

                }
                Log.d(TAG, "registerFace: "+success);

        }}

    int checkId =0;
    int checktimes=0;
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
            bitmappath=savePath + Util.getPhotoFileName()+ ".jpg";
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
//                    Log.i(TAG, "onPreview: fr end = " + System.currentTimeMillis() + " trackId = " + requestId);

                    //不做活体检测的情况，直接搜索
                    if (!livenessDetect) {
                        searchFace(faceFeature, requestId);
                    }
                    //活体检测通过，搜索特征
                    else if (livenessMap.get(requestId) != null && livenessMap.get(requestId) == LivenessInfo.ALIVE) {

                        if (CheckrequestId==0){
                            CheckrequestId=requestId;
                        }else if (CheckrequestId!=requestId){
                            face3DList.clear();
                            CheckrequestId=requestId;
                        }
                        searchFace(faceFeature, requestId);  //活体比对

                        Log.d("face3D", "requestId: "+requestId);
                    }
                    //活体检测未出结果，延迟100ms再执行该函数
                    else if (livenessMap.get(requestId) != null && livenessMap.get(requestId) == LivenessInfo.UNKNOWN) {
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
                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.NOT_ALIVE);
                    }

                }
                //FR 失败
                else {
                    requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                }
            }

        };



        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                previewSize = camera.getParameters().getPreviewSize();
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation
                        , cameraId, isMirror);

                faceHelper = new FaceHelper.Builder()
                        .faceEngine(faceEngine)
                        .frThreadNum(MAX_DETECT_NUM)
                        .previewSize(previewSize)
                        .faceListener(faceListener)
                        .currentTrackId(ConfigUtil.getTrackId(ArcFaceForFindingActivity.this.getApplicationContext()))
                        .build();
            }


            @Override
            public void onPreview(final byte[] nv21, Camera camera) {
                if (faceRectView != null) {
                    faceRectView.clearFaceInfo();
                }
                final List<FacePreviewInfo> facePreviewInfoList = faceHelper.onPreviewFrame(nv21);
                if (facePreviewInfoList != null && faceRectView != null && drawHelper != null) {
                    List<DrawInfo> drawInfoList = new ArrayList<>();
                    for (int i = 0; i < facePreviewInfoList.size(); i++) {
                        String name = faceHelper.getName(facePreviewInfoList.get(i).getTrackId());
                        String tips="";
                        int i1 = facePreviewInfoList.get(i).getLivenessInfo().getLiveness();
                        Rect rect= facePreviewInfoList.get(i).getFaceInfo().getRect();

                        double resolving=0;
                        double area= Math.abs((rect.bottom- rect.top)*(rect.right-rect.left)) ;
                        double creamarea= previewSize.width* previewSize.height;
                        resolving= area/creamarea;
                        if (resolving>0.2){
                            if (i1 == -1) {
                                face3DList.clear();
//                            tips = "未识别";
                                txtScore.setText("未识别");
                                checktimes=0;
//                            savebitmap=0;
                            } else if (i1 == 0) {
                                face3DList.clear();
//                            tips = "非活体";
                                txtScore.setText("未识别");
                                checktimes=0;
//                            savebitmap=0;
                            } else if (i1 == 1) {
                                final List<FaceInfo> faceInfoList=new ArrayList<>();
                                faceInfoList.add(facePreviewInfoList.get(i).getFaceInfo());
                                NV21ToBitmap nv21ToBitmap;
                                Bitmap bitmap = null;
//                                if (savebitmap==0&&formWay.equals("CardImageLiveFaceVerifyActivity")){
//                                    nv21ToBitmap = new NV21ToBitmap(ArcFaceForFindingActivity.this);
//                                    bitmap = nv21ToBitmap.nv21ToBitmap(nv21, previewSize.width, previewSize.height);
//                                    saveBitmap(ArcFaceForFindingActivity.this,bitmap);
//                                    String  path = Util.getPreferExternalFolderPath(ArcFaceForFindingActivity.this, "live_body_face");
//                                    String filename = Util.getPhotoFileName();
//                                    path+=File.separator+filename;
//                                    boolean success = saveBitmapToFile(bitmap, path);
//                                    final SharedPreferences userSettings= getSharedPreferences("setting", 0);
//                                    final SharedPreferences.Editor editor = userSettings.edit();
//                                    editor.putString("bitmappath",path);
//                                    editor.apply();
//                                    setResult(1);
//                                    txtScore.setText("照片截取成功!");
//                                    savebitmap=1;
//                                }
//                            tips = "正在检测" + name;
                                tips = "" ;
                                txtScore.setText("正在检测...");
                                /**除HeadCollectActivity 其余不检测相似度*/
                                if (!formWay.equals("HeadCollectActivity")){
                                    name="1";
                                }
                                if (!TextUtils.isEmpty(name)) {
                                    double similar = Double.parseDouble(name);
                                    if (similar > SIMILAR_NUMBER) {
//                                        if (face3DList.size()>0){ //帧数
//
                                        if (face3DList.size()>FRAME_NUMBER){ //帧数
                                            List<Float> floats=new ArrayList<>();
                                            if (ACTION_SELECTION==0){  //动作选择
                                                floats=PitchList;
                                            }else if (ACTION_SELECTION==1){
                                                floats=YawList;
                                            }else if (ACTION_SELECTION==2){
                                                floats=  RollList;
                                            }else {
                                                floats=PitchList;
                                            }
                                            float min =Collections.min(floats);
                                            float max= Collections.max(floats);
                                            float leng=max-min;
                                            leng= Math.abs(leng);
                                            if (ACTION_SELECTION==3) {  //满足其一
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

                                                List<Float> floatlist=new ArrayList<>();
                                                floatlist.add(Pitchleng);
                                                floatlist.add(Yawleng);
                                                floatlist.add(Rollleng);
                                                leng=Collections.max(floatlist);
                                            }
//                                            if (leng>=0){ //动作幅度
//
                                            if (leng>=DIFFERENCE_VALUE||ACTION_SELECTION==4){ //动作幅度
//                                            tips="验证成功"+name;
//                                                tips="";
                                                txtScore.setText("验证成功!!");
                                                if (isSaveBitmap){

                                                }else {
                                                    if (savebitmap==0){
                                                        nv21ToBitmap = new NV21ToBitmap(ArcFaceForFindingActivity.this);
                                                        bitmap = nv21ToBitmap.nv21ToBitmap(nv21, previewSize.width, previewSize.height);
//                                                    saveBitmap(RegisterAndRecognizeActivity.this,bitmap);
                                                        String  path = Util.getPreferExternalFolderPath(ArcFaceForFindingActivity.this, "live_body_face");
                                                        String filename = Util.getPhotoFileName();
                                                        path+=File.separator+filename;

                                                        boolean success = saveBitmapToFile(bitmap, path);
                                                        if (success){
                                                            isSaveBitmap=true;
                                                        }
                                                        final SharedPreferences userSettings= getSharedPreferences("setting", 0);
                                                        final SharedPreferences.Editor editor = userSettings.edit();
                                                        editor.putString("bitmappath",path);
                                                        editor.putString("dCoefficient", String.valueOf(similar));
                                                        editor.apply();
                                                        setResult(1);
                                                        finish();
                                                    }else if (savebitmap==1){
                                                        finish();
                                                    }
                                                }
                                            }else {
                                                if (ACTION_SELECTION==0){  //动作选择
//                                                tips="请上下摇头！"+name;
                                                    txtScore.setText("请上下摇头!");
                                                }else if (ACTION_SELECTION==1){
//                                                tips="请左右摇头！"+name;
                                                    txtScore.setText("请左右摇头!");
                                                }else if (ACTION_SELECTION==2){
//                                                tips="请摇头！"+name;
                                                    txtScore.setText("请摇头!");
                                                }else if (ACTION_SELECTION==3){
//                                                    tips="" ;
                                                    txtScore.setText("请摇头或点头!");
                                                }else if (ACTION_SELECTION==4){
//                                                    txtScore.setText("正在检测!");
                                                }
                                            }
                                        }else {
                                            if (ACTION_SELECTION==0){  //动作选择
//                                            tips="请上下摇头！"+name;
                                                txtScore.setText("请上下摇头!");
                                            }else if (ACTION_SELECTION==1){
//                                            tips="请左右摇头！"+name;
                                                txtScore.setText("请左右摇头!");
                                            }else if (ACTION_SELECTION==2){
//                                            tips="请摇头！"+name;
                                                txtScore.setText("请摇头!");
                                            }else if (ACTION_SELECTION==3){
                                                tips="" ;
                                                txtScore.setText("请摇头或点头!");
                                            }else if (ACTION_SELECTION==4){
//                                                txtScore.setText("正在检测!");
                                            }
                                        }
//
                                        Observable.create(new ObservableOnSubscribe<Object>() {
                                            @Override
                                            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                                                processImage(nv21,faceInfoList);
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

                                    }



//                                    ;
//
                                }


                            } else if (i1 == 2) {
//                            tips = "人脸数超过1";
                                txtScore.setText("人脸数超过1");
                                checktimes=0;
                            }
                            drawInfoList.add(new DrawInfo(facePreviewInfoList.get(i).getFaceInfo().getRect(), GenderInfo.UNKNOWN, AgeInfo.UNKNOWN_AGE, LivenessInfo.UNKNOWN,
                                    tips));

                        }else if (resolving==0){
                            txtScore.setText("未识别!");
                        }else {
                            txtScore.setText("人距离太远了,请靠近一点!");
                        }


                    }
                    drawHelper.draw(faceRectView, drawInfoList);
                }
                if (registerStatus == REGISTER_STATUS_READY && facePreviewInfoList != null && facePreviewInfoList.size() > 0) {
                    registerStatus = REGISTER_STATUS_PROCESSING;
                    Observable.create(new ObservableOnSubscribe<Boolean>() {
                        @Override
                        public void subscribe(ObservableEmitter<Boolean> emitter) {
                            boolean success = FaceServer.getInstance().register(ArcFaceForFindingActivity.this, nv21.clone(), previewSize.width, previewSize.height, "registered " + faceHelper.getCurrentTrackId());
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
                                    Toast.makeText(ArcFaceForFindingActivity.this, result, Toast.LENGTH_SHORT).show();
                                    registerStatus = REGISTER_STATUS_DONE;
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(ArcFaceForFindingActivity.this, "register failed!", Toast.LENGTH_SHORT).show();
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
                        }
                        /**
                         * 对于每个人脸，若状态为空或者为失败，则请求FR（可根据需要添加其他判断以限制FR次数），
                         * FR回传的人脸特征结果在{@link FaceListener#onFaceFeatureInfoGet(FaceFeature, Integer)}中回传
                         */
                        if (requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId()) == null
                                || requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId()) == RequestFeatureStatus.FAILED) {
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
                .previewViewSize(new Point(previewView.getMeasuredWidth(),previewView.getMeasuredHeight()))
                .rotation(getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(cameraID != null ? cameraID : Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
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
            }
        }

    }

    private void searchFace(final FaceFeature frFace, final Integer requestId) {
        Observable
                .create(new ObservableOnSubscribe<CompareResult>() {
                    @Override
                    public void subscribe(ObservableEmitter<CompareResult> emitter) {
//                        Log.i(TAG, "subscribe: fr search start = " + System.currentTimeMillis() + " trackId = " + requestId);
                        CompareResult compareResult = FaceServer.getInstance().getTopOfFaceLib(ArcFaceForFindingActivity.this,frFace);
//                        Log.i(TAG, "subscribe: fr search end = " + System.currentTimeMillis() + " trackId = " + requestId);
                        if (compareResult == null) {
                            emitter.onError(null);
                        } else {
                            emitter.onNext(compareResult);
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
                        if (compareResult == null || compareResult.getUserName() == null) {
                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
                            faceHelper.addName(requestId, "VISITOR " + requestId);
                            return;
                        }

//                        Log.i(TAG, "onNext: fr search get result  = " + System.currentTimeMillis() + " trackId = " + requestId + "  similar = " + compareResult.getSimilar());
                        if (compareResult.getSimilar() > SIMILAR_THRESHOLD) {
//                            boolean isAdded = false;
//                            if (compareResultList == null) {
                                requestFeatureStatusMap.put(requestId, RequestFeatureStatus.SUCCEED);
                                faceHelper.addName(requestId, ""+compareResult.getSimilar());

//                                return;
//                            }
//                            for (CompareResult compareResult1 : compareResultList) {
//                                if (compareResult1.getTrackId() == requestId) {
//                                    isAdded = true;
//                                    break;
//                                }
//                            }
//                            if (!isAdded) {
//                                //对于多人脸搜索，假如最大显示数量为 MAX_DETECT_NUM 且有新的人脸进入，则以队列的形式移除
//                                if (compareResultList.size() >= MAX_DETECT_NUM) {
//                                    compareResultList.remove(0);
//                                    adapter.notifyItemRemoved(0);
//                                }
//                                //添加显示人员时，保存其trackId
//                                compareResult.setTrackId(requestId);
//                                compareResultList.add(compareResult);
//                                adapter.notifyItemInserted(compareResultList.size() - 1);
//                            }
//                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.SUCCEED);
//                            faceHelper.addName(requestId, compareResult.getUserName());

                        } else {
                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.SUCCEED);
                            faceHelper.addName(requestId, ""+compareResult.getSimilar());
                        }
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
        previewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
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



    /** 三位角度检测
     *
     *
     */
    public void processImage(final byte[] nv21,List<FaceInfo> faceInfoList) {

        /**
         * 传入给process函数，进行年龄、性别、三维角度检测
         */

        long processStartTime = System.currentTimeMillis();
        int faceProcessCode = faceEngine.process(nv21,previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, FaceEngine.ASF_AGE | FaceEngine.ASF_GENDER | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_LIVENESS);

        if (faceProcessCode != ErrorInfo.MOK) {
//            addNotificationInfo(notificationSpannableStringBuilder, new ForegroundColorSpan(Color.RED), "process failed! code is ", String.valueOf(faceProcessCode), "\n");
        } else {
//            Log.i(TAG, "processImage: process costTime = " + (System.currentTimeMillis() - processStartTime));
        }
        //年龄信息结果
        List<AgeInfo> ageInfoList = new ArrayList<>();
        //性别信息结果
        List<GenderInfo> genderInfoList = new ArrayList<>();
        //人脸三维角度结果
        List<Face3DAngle> face3DAngleList = new ArrayList<>();
        //活体检测结果
        List<LivenessInfo> livenessInfoList = new ArrayList<>();
        //获取年龄、性别、三维角度、活体结果
//        int ageCode = faceEngine.getAge(ageInfoList);
//        int genderCode = faceEngine.getGender(genderInfoList);
        int face3DAngleCode = faceEngine.getFace3DAngle(face3DAngleList);
//        int livenessCode = faceEngine.getLiveness(livenessInfoList);



        Log.d("face3D", "Pitch: "+face3DAngleList.get(0).getPitch()+" Roll:"+
                face3DAngleList.get(0).getRoll()+" Status:"+face3DAngleList.get(0).getStatus()+" Yaw:"
        +face3DAngleList.get(0).getYaw());
//        String sex="未知";
//        if (genderInfoList.get(0).getGender()==1){
//            sex="女";
//        }else if (genderInfoList.get(0).getGender()==0){
//            sex="男";
//        }
//        String alive="未知";
//        if (livenessInfoList.get(0).getLiveness()==1){
//            alive="活体";
//        }else if (livenessInfoList.get(0).getLiveness()==0){
//            alive="非活体";
//        }else if (livenessInfoList.get(0).getLiveness()==-1){
//            alive="未知";
//        }
//        Log.d("Mars","  性别:"+sex+"  年龄:"+ ageInfoList.get(0).getAge()+"  alive"+alive);
        if (face3DAngleList.size() > 0) {
                face3DList.add(face3DAngleList.get(0));
                PitchList.add(face3DAngleList.get(0).getPitch());
                YawList.add(face3DAngleList.get(0).getYaw());
                RollList.add(face3DAngleList.get(0).getRoll());
        }

    }


}
