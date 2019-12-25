package com.jqsoft.nursing.arcface;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;


import com.jqsoft.nursing.R;
import com.jqsoft.nursing.bean.ReadIdCardBean;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.presenter.base.BasePresenter;
import com.jqsoft.nursing.util.ToastUtil;
import com.msd.ocr.idcard.LibraryInitOCR;


/**
 * @author yedong
 * @date 2019/8/28
 * 扫描身份证页面
 */
public class ScanCardActivity extends AbstractActivity implements View.OnClickListener {

    private static final String IS_SAVE_IMAGE = "isSaveImage"; // 是否保存识别成功图片标识
    private SurfaceHolder surfaceHolder;
    private ViewfinderView camera_finderView;
    private SurfaceView camera_sv;
    private ImageView iv_bg;

    private CameraManager cameraManager;
    private boolean hasSurface;
    private boolean mIsSaveImg; // 是否保存图片

    /**
     * 跳转activity
     *
     * @param context   上下文
     * @param isSaveImg 是否保存图片
     */
    public static void start(Context context, boolean isSaveImg) {
        Intent starter = new Intent(context, ScanCardActivity.class);
        starter.putExtra(IS_SAVE_IMAGE, isSaveImg);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_card;
    }

    @Override
    protected void initData() {
        mIsSaveImg = getIntent().getBooleanExtra(IS_SAVE_IMAGE, false);
        // 2. 初始化解码器
        Bundle bundle = new Bundle();
        bundle.putBoolean("saveImage", mIsSaveImg); // 是否保存识别图片
        bundle.putInt("type", 0);  // 0身份证, 1驾驶证
        LibraryInitOCR.initDecode(this, handler, bundle);
    }

    @Override
    protected void initView() {
        camera_sv = (SurfaceView)findViewById(R.id.camera_sv);
        camera_finderView =(ViewfinderView) findViewById(R.id.camera_finderView);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        surfaceHolder = camera_sv.getHolder();
        findViewById(R.id.bt_cancel).setOnClickListener(this);
    }


    @Override
    protected void loadData() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        cameraManager = new CameraManager();

        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(surfaceHolderCallback);
        }
    }

    /**
     * 初始化相机
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }

        if (cameraManager.isOpen()) {
            return;
        }

        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder, this);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            cameraManager.startPreview(previewCallback);
            Camera camera = cameraManager.getCamera();
            Camera.Size size = camera.getParameters().getPreviewSize();
            camera_finderView.initFinder(size.width, size.height);
        } catch (Exception ioe) {
            Log.d("zk", ioe.toString());
        }
    }

    /**
     * 相机预览回调
     */
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        public void onPreviewFrame(final byte[] data, final Camera camera) {
            try {
                Camera.Parameters parameters = camera.getParameters();
                //Rect rect  =  new Rect(368, 144, 1549, 936);

                Rect rect = camera_finderView.getViewfinder(camera);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_bg.getLayoutParams();
                params.width = rect.right - rect.left;
                params.height = rect.bottom - rect.top;
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                iv_bg.setLayoutParams(new RelativeLayout.LayoutParams(params));
                iv_bg.setVisibility(View.VISIBLE);
                //3. 传递相机数据,请求解码
                LibraryInitOCR.decode(rect, parameters.getPreviewSize().width, parameters.getPreviewSize().height, data);
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
                finish();
            }

        }
    };

    /**
     * 扫描解码回调处理
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //4. 接收解码回调
            switch (msg.what) {
                case LibraryInitOCR.DECODE_SUCCESS:
                    Log.i("ocr", "成功: " + msg.obj.toString());
                    Intent result = (Intent) msg.obj;
                    String ocrResult = result.getStringExtra("OCRResult");
                    ReadIdCardBean bean = new Gson().fromJson(ocrResult, ReadIdCardBean.class);
                    // 通知页面识别成功
                    if (bean != null) {
                        ScanCardUtil.doCallBackMethod(bean);
                        new Thread() { // 解决没有及时销毁activity造成闪现黑屏
                            @Override
                            public void run() {
                                finish();
//                                try {
//                                    Thread.sleep(500);
//                                    finish();
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                            }
                        }.start();
                    } else {
                        ToastUtil.show(ScanCardActivity.this, "数据解析失败,请重新扫描");
                    }
                    break;
                case LibraryInitOCR.DECODE_FAIL: // 解码失败
                    Toast.makeText(ScanCardActivity.this, "解码失败", Toast.LENGTH_LONG).show();
                    break;
                case LibraryInitOCR.DECODE_UNAUTHORIZED: // 未授权
                    Toast.makeText(ScanCardActivity.this, "未授权", Toast.LENGTH_LONG).show();
                    break;
                case LibraryInitOCR.DECODE_LINE_IN_RECT:
//                    Toast.makeText(ScanCardActivity.this, "证件不在指定区域", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        cameraManager.stopPreview();
        cameraManager.closeDriver();
        if (!hasSurface) {
            surfaceHolder.removeCallback(surfaceHolderCallback);
        }

    }

    private SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (!hasSurface) {
                hasSurface = true;
                initCamera(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            hasSurface = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

        LibraryInitOCR.closeDecode(); // 释放资源
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_cancel:
                finish();
                break;
        }
    }
}
