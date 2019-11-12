package com.arcsoft.arcfacedemo.idcard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.common.Constants;

import com.arcsoft.arcfacedemo.util.BitmapUtil;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean isInit = false;
    //比对阈值，建议为0.82
    private static final double THRESHOLD = 0.82d;
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
    private static final int MAX_RETRY_TIME = 50;
    private int tryTime = 0;
    private ExecutorService activeService = Executors.newSingleThreadExecutor();
    private long startTime = 0;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private IdCardVerifyListener idCardVerifyListener = new IdCardVerifyListener() {
        @Override
        public void onPreviewResult(DetectFaceResult detectFaceResult, byte[] bytes, int i, int i1) {
            if(detectFaceResult.getErrCode() == IdCardVerifyError.OK) {
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
            if(detectFaceResult.getErrCode() == IdCardVerifyError.OK) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_copy);
        boolean allGranted = true;
        for(String needPermission: NEEDED_PERMISSIONS) {
            allGranted &= ContextCompat.checkSelfPermission(this, needPermission) == PackageManager.PERMISSION_GRANTED;
        }
        if(!allGranted) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, 2);
        }
        //初始化
        int initResult = IdCardVerifyManager.getInstance().init(this, idCardVerifyListener);
        isInit = initResult == IdCardVerifyError.OK;
        inputIdCard();
        Log.i(TAG, "init result: " + initResult);
        if(!isInit) {
            Toast.makeText(MainActivity.this, "init result: "
                    + initResult, Toast.LENGTH_LONG).show();
        }

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView_copy);
        SurfaceHolder surfaceholder = surfaceView.getHolder();
        surfaceholder.addCallback(this);
        surfaceRect = (SurfaceView) findViewById(R.id.surfaceview_rect);
        surfaceRect.setZOrderMediaOverlay(true);
        surfaceRect.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        Button btnActive = (Button) findViewById(R.id.btn_active);
        btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeService.execute(new Runnable() {
                    @Override
                    public void run() {
                        final int activeResult = IdCardVerifyManager.getInstance().active(
                                MainActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                        Log.i(TAG, "active result: " + activeResult);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "active result: "
                                        + activeResult, Toast.LENGTH_LONG).show();
                                if(activeResult == IdCardVerifyError.OK) {
                                    //初始化
                                    int initResult = IdCardVerifyManager.getInstance().init(
                                            MainActivity.this, idCardVerifyListener);
                                    isInit = initResult == IdCardVerifyError.OK;
                                    Log.i(TAG, "init result: " + initResult);
                                    if(!isInit) {
                                        Toast.makeText(MainActivity.this, "init result: "
                                                + initResult, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });
        Button btnIdCard = (Button) findViewById(R.id.btn_idcard);
        btnIdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //输入身份证数据
                startTime = System.currentTimeMillis();
                inputIdCard();
            }
        });

    }

    @Override
    protected void onDestroy() {
        //反初始化
        if(activeService != null) {
            activeService.shutdown();
        }
        IdCardVerifyManager.getInstance().unInit();
        if (camera != null) {
            camera.release();
        }
        super.onDestroy();
    }
    /**
     * ARGB数据转化为NV21数据
     *
     * @param argb   argb数据
     * @param width  宽度
     * @param height 高度
     * @return nv21数据
     */
    private static byte[] argbToNv21(int[] argb, int width, int height) {
        int frameSize = width * height;
        int yIndex = 0;
        int uvIndex = frameSize;
        int index = 0;
        byte[] nv21 = new byte[width * height * 3 / 2];
        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                int R = (argb[index] & 0xFF0000) >> 16;
                int G = (argb[index] & 0x00FF00) >> 8;
                int B = argb[index] & 0x0000FF;
                int Y = (66 * R + 129 * G + 25 * B + 128 >> 8) + 16;
                int U = (-38 * R - 74 * G + 112 * B + 128 >> 8) + 128;
                int V = (112 * R - 94 * G - 18 * B + 128 >> 8) + 128;
                nv21[yIndex++] = (byte) (Y < 0 ? 0 : (Y > 255 ? 255 : Y));
                if (j % 2 == 0 && index % 2 == 0 && uvIndex < nv21.length - 2) {
                    nv21[uvIndex++] = (byte) (V < 0 ? 0 : (V > 255 ? 255 : V));
                    nv21[uvIndex++] = (byte) (U < 0 ? 0 : (U > 255 ? 255 : U));
                }

                ++index;
            }
        }
        return nv21;
    }
    /**
     * Bitmap转化为ARGB数据，再转化为NV21数据
     *
     * @param src    传入的Bitmap，格式为{@link Bitmap.Config#ARGB_8888}
     * @param width  NV21图像的宽度
     * @param height NV21图像的高度
     * @return nv21数据
     */
    public static byte[] bitmapToNv21(Bitmap src, int width, int height) {
        if (src != null && src.getWidth() >= width && src.getHeight() >= height) {
            int[] argb = new int[width * height];
            src.getPixels(argb, 0, width, 0, 0, width, height);
            return argbToNv21(argb, width, height);
        } else {
            return null;
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

    private void inputIdCard() {
       Bitmap bitmap= getImage(BitmapUtil.getPath());
        //身份证数据 根据实际数据输入
//        byte[] nv21Data = new byte[1024];
        //身份证数据宽（宽度需为4的倍数）
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
        int width = bitmap.getWidth()-(bitmap.getWidth()%4);
        //        //身份证数据高（高度需为2的倍数）
        int height = bitmap.getHeight()-(bitmap.getHeight()%2);
        byte[] nv21Data=   bitmapToNv21(bitmap,width,height);
        if(isInit) {
            DetectFaceResult result = IdCardVerifyManager.getInstance().inputIdCardData(nv21Data, width,height);
            Log.i(TAG, "inputIdCardData result: " + result.getErrCode());
        }
    }
    private void compare() {
        if(!isCurrentReady || !isIdCardReady) {
            return;
        }
        //人证比对
        CompareResult compareResult = IdCardVerifyManager.getInstance().compareFeature(THRESHOLD);
        Log.i(TAG, "compareFeature: result " + compareResult.getResult() + ", isSuccess "
                + compareResult.isSuccess() + ", errCode " + compareResult.getErrCode());
        Toast.makeText(this, "compareFeature: result " + compareResult.getResult()
                + ", costTime " + (System.currentTimeMillis() - startTime) + ", isSuccess "
                + compareResult.isSuccess() + ", errCode " + compareResult.getErrCode(), Toast.LENGTH_LONG).show();
        isIdCardReady = false;
        isCurrentReady = false;

        if(!compareResult.isSuccess() && tryTime < MAX_RETRY_TIME) {
            tryTime++;
            inputIdCard();
        } else {
            tryTime = 0;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camereId = Camera.getNumberOfCameras() - 1;
        camera = Camera.open(camereId);
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size previewSize = getBestSupportedSize(parameters.getSupportedPreviewSizes(), metrics);
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            camera.setParameters(parameters);
            final int mWidth = previewSize.width;
            final int mHeight = previewSize.height;
            displayOrientation = getCameraOri(getWindowManager().getDefaultDisplay().getRotation());
            camera.setDisplayOrientation(displayOrientation);
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback( new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    //视频数据
                    if(isInit) {
                        DetectFaceResult result = IdCardVerifyManager.getInstance().onPreviewData(data, mWidth, mHeight, true);
                        if (result.getErrCode() != IdCardVerifyError.OK) {
                            Log.i(TAG, "onPreviewData video result: " + result.getErrCode());
                        }
                        if (surfaceRect != null) {
                            Canvas canvas = surfaceRect.getHolder().lockCanvas();
                            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                            Rect rect = result.getFaceRect();
                            if (rect != null) {
                                Rect adjustedRect = DrawUtils.adjustRect(rect, mWidth, mHeight,
                                        canvas.getWidth(), canvas.getHeight(), displayOrientation, camereId);
                                //画人脸框
                                DrawUtils.drawFaceRect(canvas, adjustedRect, Color.YELLOW, 5);
                            }
                            surfaceRect.getHolder().unlockCanvasAndPost(canvas);
                        }
                    }
                }
            });
            camera.startPreview();
        } catch (Exception e) {
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, DisplayMetrics metrics) {
        Camera.Size bestSize = sizes.get(0);
        float screenRatio = (float) metrics.widthPixels / (float) metrics.heightPixels;
        if (screenRatio > 1) {
            screenRatio = 1 / screenRatio;
        }

        for (Camera.Size s : sizes) {
            if (Math.abs((s.height / (float) s.width) - screenRatio) < Math.abs(bestSize.height /
                    (float) bestSize.width - screenRatio)) {
                bestSize = s;
            }
        }
        return bestSize;
    }

    private int getCameraOri(int rotation) {
        int degrees = rotation * 90;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
            default:
                break;
        }
        int result;
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(camereId, info);
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }
}
