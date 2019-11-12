package com.jqsoft.livebody_verify_lib.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eidlink.eidsdk.EIDPortraitActivity;
import com.google.gson.Gson;
import com.jqsoft.livebody_verify_lib.R;
import com.jqsoft.livebody_verify_lib.bean.TencentResponseSuccessBean;
import com.jqsoft.livebody_verify_lib.bean.VerifyResultBean;
import com.jqsoft.livebody_verify_lib.bean.Version;
import com.jqsoft.livebody_verify_lib.callback.MyResultCallback;
import com.jqsoft.livebody_verify_lib.util.Base64Util;
import com.jqsoft.livebody_verify_lib.util.DeleteFileUtil;
import com.jqsoft.livebody_verify_lib.util.Util;
import com.jqsoft.livebody_verify_lib.view.CheckBoxSample;
import com.jqsoft.livebody_verify_lib.youtuIdentify.BitMapUtils;
import com.jqsoft.livebody_verify_lib.youtuIdentify.TecentHttpUtil;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.minivision.livebodylibrary.util.FaceDetector;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 身份证正面与活体人脸验证
 * （需要绑定身份证照片与活体人脸时使用此类）
 * Created by Administrator on 2018-08-09.
 */

public class CardImageLiveFaceVerifyActivity_old extends AppCompatActivity {
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

    FunctionOptions options;

    public static String VERIFY_STATUS_KEY = "verifyStatusKey";
//    public static String VERIFY_CARD_NO_KEY="verifyCardNoKey";

    public static String PASSIN_ID_NUMBER_KEY = "idNumberKey";
    public static String PASSIN_ID_ORG_CODE_KEY = "orgCodeKey";
    public static String PASSIN_ID_SUBMIT_USER_KEY = "submitUserKey";

    private boolean isInResolveIdCardNumberMode = true;

    private int SELECT_LIVE_HEAD = 600;

    public static int IMAGE_SELECT_TYPE_CARD_FRONT = 1;
    public static int IMAGE_SELECT_TYPE_CARD_BACK = 2;
    public static int IMAGE_SELECT_TYPE_FACE = 3;

    String cardFrontPath = "", cardBackPath = "", facePath = "";
    String idNumber = "";

    int imageSelectType = IMAGE_SELECT_TYPE_CARD_FRONT;

    boolean isCardFrontOnlyFromCamera = true;

    LinearLayout llBack;

    ImageView ivCardFront;
    ImageView ivCardBack;
    ImageView ivFace;
    //    CheckBox cbLicense;
    CheckBoxSample viewLicense;
    Button btnVerify;

    RequestCall call;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_image_live_face_verify_layout);

        initData();
        initView();
    }

    private void initData() {
        populateData();
        initPictureLibrary();
    }

    private void populateData() {
        Intent intent = getIntent();
        if (intent != null) {
            idNumber = intent.getStringExtra(PASSIN_ID_NUMBER_KEY);
            idNumber = Util.trimString(idNumber);
            idNumber = idNumber.replaceAll("x", "X");
        }
    }

    private void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked();
            }
        });
        ivCardFront = (ImageView) findViewById(R.id.iv_card_front);
        ivCardBack = (ImageView) findViewById(R.id.iv_card_back);
        ivFace = (ImageView) findViewById(R.id.iv_face);
//        cbLicense=(CheckBox)findViewById(R.id.cb_license);
        viewLicense = (CheckBoxSample) findViewById(R.id.cb_license);
        btnVerify = (Button) findViewById(R.id.btn_verify);

        ivCardFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCardFrontImage();
            }
        });

        ivCardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCardBackImage();
            }
        });

        ivFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectFaceImage();
                selectLiveHead();
            }
        });

//        cbLicense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                refreshVerifyButtonStatus(isChecked);
//            }
//        });
        viewLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLicense.toggle();
                refreshVerifyButtonStatus(viewLicense.isChecked());
            }
        });


        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVerifyButtonClicked();
            }
        });
        refreshVerifyButtonStatus(viewLicense.isChecked());
//        refreshVerifyButtonStatus(cbLicense.isChecked());
    }


    private void onBackClicked() {
        deleteByFilePath(cardFrontPath);
        returnFailure();
        finish();
    }

    private void selectLiveHead() {
        imageSelectType = IMAGE_SELECT_TYPE_FACE;
        FaceDetector.init(CardImageLiveFaceVerifyActivity_old.this);
        Intent intent = new Intent(CardImageLiveFaceVerifyActivity_old.this, EIDPortraitActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SELECT_LIVE_HEAD);

    }

    private void verifyIdCardNumber(Bitmap bitmap) {
        isInResolveIdCardNumberMode = true;
        Util.showGifProgressDialog(this, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isInResolveIdCardNumberMode) {
                    refreshUI("", null);
                }
                isInResolveIdCardNumberMode = false;
            }
        });
//        TecentHttpUtil.uploadIdCard(BitMapUtils.bitmapToBase64(bitmap), "0", new TecentHttpUtil.SimpleCallBack() {
//            @Override
//            public void Succ(String res) {
//                isInResolveIdCardNumberMode=false;
//                Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity_old.this);
//
//                Log.i("chenxu", "success resolved result:"+res);
//                IdentifyResult result = new Gson().fromJson(res, IdentifyResult.class);
//                if (result != null) {
//                    if (result.getErrorcode() == 0) {
//                        // 识别成功
//                        String readAPIcard = result.getId();
//                        readAPIcard=Util.trimString(readAPIcard);
//                        readAPIcard=readAPIcard.replaceAll("x", "X");
//                        Log.i("chenxu", "resolved id card number:"+readAPIcard);
////                        readAPIname = result.getName();
////                        Toast.makeText(CardImageLiveFaceVerifyActivity.this, readAPIcard, Toast.LENGTH_SHORT).show();
//                        if (!TextUtils.isEmpty(readAPIcard)) {
//                            if (readAPIcard.contains("X")) {
////                                String readapicard = readAPIcard.replace("X", "x");
//                                String readapicard = readAPIcard;
//                                if (idNumber.equals(readapicard)) {
//
//                                } else {
//                                    showNotificationDialog("选中的身份证与您刷卡的身份证不是同一人", null);
//                                    refreshUI("", null);
//                                }
//                            } else {
//                                if (idNumber.equals(readAPIcard)) {
//
//                                } else {
//                                    showNotificationDialog("选中的身份证与您刷卡的身份证不是同一人", null);
//                                    refreshUI("", null);
//                                }
//                            }
//                        } else {
//                            showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
//                            refreshUI("", null);
//                        }
//                    } else {
//                        showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
//                        refreshUI("", null);
//                    }
//                } else {
//                    showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
//                    refreshUI("", null);
//                }
//            }
//
//            @Override
//            public void error() {
//                Log.i("chenxu", "failure resolved result");
//                isInResolveIdCardNumberMode=false;
//                showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
//                refreshUI("", null);
//                Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity_old.this);
//            }
//        });

        TecentHttpUtil.readIdCard(BitMapUtils.bitmapToBase64(bitmap), new TecentHttpUtil.SimpleCallBack() {
            @Override
            public void Succ(String res) {
                isInResolveIdCardNumberMode = false;
                Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity_old.this);

                Log.i("chenxu", "success resolved result:" + res);
                TencentResponseSuccessBean tencentResponseBean = new Gson().fromJson(res, TencentResponseSuccessBean.class);
                if (tencentResponseBean != null) { // 解析成功
                    TencentResponseSuccessBean.ResponseBean bean = tencentResponseBean.getResponse();
                    if (bean != null && bean.getError() == null) { // 识别成功
                        // 识别成功
                        String readAPIcard = bean.getIdNum();
                        readAPIcard = Util.trimString(readAPIcard);
                        readAPIcard = readAPIcard.replaceAll("x", "X");
                        Log.i("chenxu", "resolved id card number:" + readAPIcard);
                        if (!TextUtils.isEmpty(readAPIcard)) {
                            if (!idNumber.equals(readAPIcard)) {
                                showNotificationDialog("选中的身份证与您刷卡的身份证不是同一人", null);
                                refreshUI("", null);
                            }
                        } else {
                            showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                            refreshUI("", null);
                        }
                    } else {
                        showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                        refreshUI("", null);
                    }
                } else {
                    showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                    refreshUI("", null);
                }
            }

            @Override
            public void error() {
                Looper.prepare();
                Log.i("chenxu", "failure resolved result");
                isInResolveIdCardNumberMode = false;
                showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                refreshUI("", null);
                Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity_old.this);
                Looper.loop();
            }
        });
    }

    private void refreshVerifyButtonStatus(boolean isChecked) {
        btnVerify.setEnabled(true);
//        btnVerify.setEnabled(isChecked);
    }

    private void verify() {
        Util.showGifProgressDialog(this, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                cancelNetworkRequest();
            }
        });

        String cardFrontBase64String = Base64Util.imageToBase64(cardFrontPath);
//        String cardBackBase64String = Base64Util.imageToBase64(cardBackPath);
        String faceBase64String = Base64Util.imageToBase64(facePath);

        long timestamp = Util.getTimeStamp();
        String timestampString = String.valueOf(timestamp);
        String token = Util.getProcessedToken(timestamp);

        Map<String, String> params = new HashMap<>();
        params.put("acs_token", token);
        params.put("acs_time", timestampString);
        params.put("idnum", idNumber);
        params.put("card_base64", cardFrontBase64String);
//        params.put("card_back", cardBackBase64String);
        params.put("head_base64", faceBase64String);

        Log.i("chenxu", "acs_token:" + token);
        Log.i("chenxu", "acs_time:" + timestampString);
        Log.i("chenxu", "idnum:" + idNumber);


//        Map<String, String> headers = new HashMap<>();
//        headers.put("signature", "");
//        headers.put("public_key", "6000000858322998-1");

        String url = Version.BASE_URL + Version.CREATE_FEATURE_URL;

        call = OkHttpUtils.post()//
//                .addFile("mFile", "agguigu-afu.jpe", file)//
                .url(url)//
                .params(params)//
//                .headers(headers)//
                .build();//
        call.execute(new MyResultCallback<VerifyResultBean>() {

            @Override
            public void onSuccess(VerifyResultBean response) {
                Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity_old.this);

                if (response != null && "1".equals(response.getRespond())) {
                    deleteByFilePath(cardFrontPath);
                    returnSuccess();
                    Log.i("chenxu", "CardImageLiveFaceVerifyActivity onSuccess");
                    showToast("验证成功");
                } else {
                    returnFailure();
                    String suffix = "";
                    if (response != null) {
                        suffix = ":" + response.getMessage();
                    }
                    Log.i("chenxu", "CardImageLiveFaceVerifyActivity failure");
                    showNotificationDialog("验证失败" + suffix, null);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity_old.this);

                returnFailure();
                Log.i("chenxu", "CardImageLiveFaceVerifyActivity onFailure");
                showNotificationDialog("验证失败", null);
            }
        });
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
            Log.i("chenxu", "CardImageLiveFaceVerifyActivity showNotificationDialog encounter exceptioin");
        }

    }


    private void returnSuccess() {
        Intent intent = new Intent();
        intent.putExtra(VERIFY_STATUS_KEY, true);
        intent.putExtra(PASSIN_ID_NUMBER_KEY, idNumber);
//        intent.putExtra(VERIFY_CARD_NO_KEY, idNumber);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void returnFailure() {
        Intent intent = new Intent();
        intent.putExtra(VERIFY_STATUS_KEY, false);
        intent.putExtra(PASSIN_ID_NUMBER_KEY, idNumber);
//        intent.putExtra(VERIFY_CARD_NO_KEY, idNumber);
        setResult(RESULT_CANCELED, intent);

    }

    private void onVerifyButtonClicked() {
        if (false/*!viewLicense.isChecked()*/) {
//        if (!cbLicense.isChecked()){
            showToast("请同意许可协议");
        } else if (!isFilePathValid(cardFrontPath)) {
            showToast("身份证正面照不能为空");
        } /*else if (!isFilePathValid(cardBackPath)){
            showToast("身份证背面照不能为空");
        } */ else if (!isFilePathValid(facePath)) {
            showToast("头像不能为空");
        } else {
            verify();
        }
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private boolean isFilePathValid(String filePath) {
        filePath = Util.trimString(filePath);
        File file = new File(filePath);
        if (!TextUtils.isEmpty(filePath) && file != null && file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    private void selectCardFrontImage() {
        imageSelectType = IMAGE_SELECT_TYPE_CARD_FRONT;
        if (isCardFrontOnlyFromCamera) {
            PictureConfig.getInstance().init(options).startOpenCamera(CardImageLiveFaceVerifyActivity_old.this);
        } else {
            PictureConfig.getInstance().init(options).openPhoto(this, resultCallback);
        }


    }

    private void selectCardBackImage() {
        imageSelectType = IMAGE_SELECT_TYPE_CARD_BACK;
        PictureConfig.getInstance().init(options).openPhoto(this, resultCallback);

    }

    private void selectFaceImage() {
        imageSelectType = IMAGE_SELECT_TYPE_FACE;
        PictureConfig.getInstance().init(options).openPhoto(this, resultCallback);

    }

    private void deleteByFilePath(String filePath) {
        filePath = Util.trimString(filePath);
        DeleteFileUtil.delete(filePath);
    }

    @Override
    public void onBackPressed() {
        onBackClicked();
//        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("chenxu", "requestCode/resultCode:" + requestCode + "/" + resultCode);
        if (resultCode == RESULT_OK) {

            if (requestCode == FunctionConfig.CAMERA_RESULT) {
                if (/*isCardFrontOnlyFromCamera && */imageSelectType == IMAGE_SELECT_TYPE_CARD_FRONT) {
                    if (data != null) {
                        List<LocalMedia> mediaList = (List<LocalMedia>) data.getSerializableExtra(FunctionConfig.EXTRA_RESULT);
                        if (mediaList != null && mediaList.size() > 0) {
                            LocalMedia lm = mediaList.get(0);
                            onSelectOneMedia(lm);
                        }

                    }

                }

            }
        }

        if (requestCode == SELECT_LIVE_HEAD) {

            try {
                String str64 = data.getStringExtra("eid_img");//照片
                String img_code = data.getStringExtra("eid_img_code");//1为真脸   0为假脸  预留
                if ("1".equals(img_code) && str64 != null && !TextUtils.isEmpty(str64)) {
                    String path = Util.getPreferExternalFolderPath(CardImageLiveFaceVerifyActivity_old.this, "live_body_face");
                    String filename = Util.getPhotoFileName();
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
                    showNotificationDialog("获取的活体人脸为假脸", null);

                }
            } catch (Exception e) {
                e.printStackTrace();
                refreshUI("", null);
                showNotificationDialog("获取活体人脸失败", null);
            }

        }
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
        } /*else {
            deleteByFilePath(media.getPath());
        }*/
        String temp[] = paths.replaceAll("\\\\", "/").split("/");
        if (temp.length > 1) {
            String aFileName = temp[temp.length - 1];
            media.setFileName(aFileName);
        }

//            String s1 = Base64Util.imageToBase64(paths);

        Bitmap bitmap = BitmapFactory.decodeFile(paths);

        refreshUI(paths, bitmap);

        verifyIdCardNumber(bitmap);

    }

    private void refreshUI(String path, Bitmap bitmap) {
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (imageSelectType == IMAGE_SELECT_TYPE_CARD_FRONT) {
            deleteByFilePath(cardFrontPath);
            cardFrontPath = path;
            if (bitmap != null) {
                ivCardFront.setImageBitmap(bitmap);
            } else {
                ivCardFront.setImageResource(R.mipmap.cf_id_card_number);
            }
        } else if (imageSelectType == IMAGE_SELECT_TYPE_CARD_BACK) {
            deleteByFilePath(cardBackPath);
            cardBackPath = path;
            if (bitmap != null) {
                ivCardBack.setImageBitmap(bitmap);
            } else {
                ivCardBack.setImageResource(R.mipmap.cf_card_back);
            }
        } else if (imageSelectType == IMAGE_SELECT_TYPE_FACE) {
            deleteByFilePath(facePath);
            facePath = path;
            if (bitmap != null) {
                ivFace.setImageBitmap(bitmap);
            } else {
                ivFace.setImageResource(R.mipmap.cf_click_to_collect);
            }
        }
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
        super.onDestroy();
        cancelNetworkRequest();
    }

    private void cancelNetworkRequest() {
        if (call != null) {
            call.cancel();
        }
        call = null;
    }

}
