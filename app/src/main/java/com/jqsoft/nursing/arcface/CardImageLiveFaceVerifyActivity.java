package com.jqsoft.nursing.arcface;


import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arcsoft.arcfacedemo.activity.RegisterAndRecognizeActivity;
import com.arcsoft.arcfacedemo.common.Constants;
import com.arcsoft.arcfacedemo.faceserver.FaceServer;
import com.arcsoft.arcfacedemo.util.BitmapUtil;
import com.arcsoft.arcfacedemo.util.ConfigUtil;
import com.arcsoft.arcfacedemo.util.IDCard;
import com.arcsoft.arcfacedemo.util.ImageUtil;

import com.arcsoft.arcfacedemo.util.ToastUtils;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.google.gson.Gson;
import com.jqsoft.livebody_verify_lib.R;
import com.jqsoft.livebody_verify_lib.bean.CardBean;
import com.jqsoft.livebody_verify_lib.bean.Constant;
import com.jqsoft.livebody_verify_lib.bean.TencentResponseSuccessBean;
import com.jqsoft.livebody_verify_lib.bean.VerifyResultBean;
import com.jqsoft.livebody_verify_lib.bean.Version;
import com.jqsoft.livebody_verify_lib.callback.MyResultCallback;
import com.jqsoft.livebody_verify_lib.certificateCamera.CameraActivity;
import com.jqsoft.livebody_verify_lib.util.Base64Util;
import com.jqsoft.livebody_verify_lib.util.CommonDialog;
import com.jqsoft.livebody_verify_lib.util.DeleteFileUtil;
import com.jqsoft.livebody_verify_lib.util.PhotoDialog;
import com.jqsoft.livebody_verify_lib.util.ResponseInterceptor;
import com.jqsoft.livebody_verify_lib.util.StringUtils;
import com.jqsoft.livebody_verify_lib.util.Util;
import com.jqsoft.livebody_verify_lib.util.WaterMarkBitmapUtil;
import com.jqsoft.livebody_verify_lib.util.WebServiceUtils;
import com.jqsoft.livebody_verify_lib.view.CheckBoxSample;
import com.jqsoft.livebody_verify_lib.view.tool.ArrayListUtil;
import com.jqsoft.livebody_verify_lib.view.tool.HealTestWithEidTextOptionsLayout;
import com.jqsoft.livebody_verify_lib.view.tool.NameValueBeanWithNo;
import com.jqsoft.livebody_verify_lib.youtuIdentify.BitMapUtils;
import com.jqsoft.livebody_verify_lib.youtuIdentify.IdentifyResult;
import com.jqsoft.livebody_verify_lib.youtuIdentify.TecentHttpUtil;
import com.jqsoft.nursing.bean.AreaDictionaryUtil;
import com.jqsoft.nursing.bean.DictionaryAreaData;
import com.jqsoft.nursing.bean.IStringRepresentationAndValue;
import com.jqsoft.nursing.bean.NameValueBean;
import com.jqsoft.nursing.bean.ReadIdCardBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;

import com.jqsoft.nursing.bean.nursing.HealthListBean;
import com.jqsoft.nursing.bean.nursing.LoginResultNewBean;
import com.jqsoft.nursing.di.contract.SaveFaceInfoContract;
import com.jqsoft.nursing.di.module.SaveFaceInfoModule;
import com.jqsoft.nursing.di.presenter.SaveFaceInfoPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.selectadress.manager.AddressManager;
import com.jqsoft.nursing.di.ui.selectadress.views.SelectAddressPop;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.util.ToastUtil;
import com.jqsoft.nursing.util.UtilNew;
import com.jqsoft.nursing.utils.GlideUtils;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.jqsoft.nursing.utils3.util.PreferencesUtils;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.jqsoft.livebody_verify_lib.bean.Constant.GETBASEPHOTO_LOAD_OVER;
import static com.jqsoft.livebody_verify_lib.bean.Constant.GETBASEPHOTO_LOAD_SUCESS;
import static com.jqsoft.livebody_verify_lib.util.Util.bitmapToBase64;

/**
 * 身份证正面与活体人脸验证
 * （需要绑定身份证照片与活体人脸时使用此类）
 * Created by Administrator on 2018-08-09.
 */

public class CardImageLiveFaceVerifyActivity extends AbstractActivity implements IScanCardView, SaveFaceInfoContract.View{
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
    public static final int ERROR01 = 1111;
    public static final int PICSCCUSE = 101;
    public static final int PICERROR = 100;
    FunctionOptions options;

    public static String VERIFY_STATUS_KEY = "verifyStatusKey";
    //    public static String VERIFY_CARD_NO_KEY="verifyCardNoKey";
    public static String SPERSON_ID = "sPersonID";
    public static String SUERSENAME = "sUserName";
    public static String PASSIN_ID_NUMBER_KEY = "idNumberKey";
    //是否开启人证比对
    public static String PASSIN_ID_ISMOTHER_KEY = "IsOnlyFace";
    public static String PASSIN_ID_ORG_CODE_KEY = "orgCodeKey";
    public static String PASSIN_ID_SUBMIT_USER_KEY = "submitUserKey";

    public static String PASSOUT_HEAD_IMAGE_PATH_KEY = "headImagePathKey";

    private boolean isInResolveIdCardNumberMode = true;

    private int SELECT_LIVE_HEAD = 600;

    public static int IMAGE_SELECT_TYPE_CARD_FRONT = 1;
    public static int IMAGE_SELECT_TYPE_CARD_BACK = 2;
    public static int IMAGE_SELECT_TYPE_FACE = 3;

    public static String VERIFY_STATUS_NORMAL = "1";
    public static String VERIFY_STATUS_FORCE = "1";

    private boolean isForcePass = false;


    String cardFrontPath = "", cardBackPath = "", facePath = "";
    String idNumber = "";
    String isOnlyFace = "";
    String sPersonID = "";
    String sUserName = "";
    int imageSelectType = IMAGE_SELECT_TYPE_CARD_FRONT;

    boolean isCardFrontOnlyFromCamera = true;
    boolean isFaceOnlyFromCamera = true;

    LinearLayout llBack;

    ImageView ivCardFront;
    ImageView ivCardBack;
    ImageView ivFace,iv_face1;
    //    CheckBox cbLicense;
    CheckBoxSample viewLicense;
    Button btnVerify;

    RequestCall call;
    private Bitmap bitmap = null;
    private int AFFACE_HEAD = 800;
    private LinearLayout ll_card;
    LinearLayout ll_SwBtn;
    private SwitchButton btn_changeCamera;
    int faceType = 0; // 0第一次(或者激活失败)  1虹软   2 eid（出现崩溃）
    private String IdCardPath;
    private TextView apply_btn;
    String sRemark = "";
    private Bitmap takePhotoBitmap;
    private String mPhotoPath, Applymes;
    private File mPhotoFile;
    private int TAKE_PHOTO = 100;
    private Uri photoPath;
    private String sCheckFacePhoto, sDeletePhoto;
    private ImageView iv_location,iv_idcard;
    private EditText et_familyadress,et_name,et_idcard,et_hujiadress_new;
    private TextView et_hujiadress,tv_nation,tv_sex,tv_birth;

    @Inject
    SaveFaceInfoPresenter saveFaceInfoPresenter;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_card_image_live_face_verify_layout);
//
//
//    }


    protected void initInject() {
        DaggerApplication.get(this)
                .getAppComponent()
                .addSaveFaceInfo(new SaveFaceInfoModule(this))
                .inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_image_live_face_verify_layout;
    }


    private HealthListBean.RowsBean mpeopleBasebean;
    private String flag="";

    @Override
    protected void initData() {

        application = (DaggerApplication) CardImageLiveFaceVerifyActivity.this.getApplication();


    }
    //民族
    String ssNationName, ssNationCode;
    int selectedNationIndex = -1;
    List<NameValueBean> raceList = new ArrayList<>();
    List<IStringRepresentationAndValue> realRaceList = new ArrayList<>();
    String HANZU_RACE_REPRESENTATION = "汉族";
    private LoginResultNewBean userinfo;
    @Override
    protected void initView() {
        initData1();
        initView1();

         userinfo= PreferencesUtils.getUserLoginInfo(CardImageLiveFaceVerifyActivity.this);
         int level =userinfo.getUserLevel();
         String sAreaCode="";
         if(level==1){
             sAreaCode=userinfo.getProvince();
         }else if(level==2){
             sAreaCode=userinfo.getCity();
         }else if(level==3){
             sAreaCode=userinfo.getCounty();
         }else if(level==4){
             sAreaCode=userinfo.getStreet();
         }else if(level==5){
             sAreaCode=userinfo.getCommunity();
         }else {
             sAreaCode="";
         }

        intSelectAdress(sAreaCode);
        et_hujiadress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("1")){

                }else {



                pop.setSelectAddresFinish(new SelectAddresFinish() {
                    @Override
                    public void finish(String pCode, String cCode, String CountyCode, String StreetCode, String VillageCode, String aCode) {
                        //选中地址完成

                        provinceCode = pCode;
                        cityCode = cCode;
                        countyCode = CountyCode;
                        streetCode = StreetCode;
                        villageCode = VillageCode;
                        areaCode = aCode;

                        sAddress="";
                        listAreaName.clear();
                        listAreaName= AddressManager.newInstance().getAddress(pCode, cCode, CountyCode, StreetCode, VillageCode, aCode);
                       for(int i=0;i<listAreaName.size();i++){
                           sAddress+=listAreaName.get(i);
                       }

                        et_hujiadress.setText(sAddress);
                    }
                });
                pop.setAddress(provinceCode, cityCode, countyCode, streetCode, villageCode, areaCode);
                pop.show(CardImageLiveFaceVerifyActivity.this.getFragmentManager()
                        , "address");
                }
            }
        });

        setNation();
        //raceList = DataDictionaryUtil.getNameValueBeanListByCode("Nation");
        realRaceList = Arrays.asList(raceList.toArray(new IStringRepresentationAndValue[0]));
        realRaceList = reorderRaceList(realRaceList);
        selectedNationIndex = getRaceSpecificTypeIndex(HANZU_RACE_REPRESENTATION);
        initRace(selectedNationIndex);

        tv_nation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("1")){

                }else {
                    UtilNew.showSingleChoiceStringListMaterialDialogEx(CardImageLiveFaceVerifyActivity.this, "请选择民族", null,
                            realRaceList, selectedNationIndex, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                    initRace(which);
                                    return false;
                                }
                            });
                }
            }
        });


        final LayoutInflater mInflater = LayoutInflater.from(CardImageLiveFaceVerifyActivity.this);
        tagAdapter= new TagAdapter<String>(mVals)
        {
            @Override
            public View getView(FlowLayout parent, int position, String o)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        parent, false);
                tv.setText(o);
                //can use viewholder
                // 控制是否置灰
                if (!isAllItemEnable) {
                    tv.setEnabled(false);
                    tv.setEnabled(false);
                    tv.setFocusable(false);
                    tv.setClickable(true);
                } else {
                    tv.setEnabled(true);
                    tv.setEnabled(true);
                }



                return tv;
            }
        };
        mFlowLayout.setAdapter(tagAdapter);

//        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
//            @Override
//            public View getView(FlowLayout parent, int position, String s) {
//                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
//                        mFlowLayout, false);
//                tv.setText(s);
//                return tv;
//            }
//
//        });

        list.clear();
        listCode.clear();
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {

                int s1= mFlowLayout.getSelectedList().size();
                if(s1==0){
                    list.clear();
                    listCode.clear();
                }else {
                    if(mVals[position].equals("其他")){
                        list.add(mVals[position]);
                        listCode.add("15");
                    }else if(mVals[position].equals("重病大病")){
                        list.add(mVals[position]);
                        listCode.add("13");
                    }else if(mVals[position].equals("失地农民")){
                        list.add(mVals[position]);
                        listCode.add("12");
                    }else if(mVals[position].equals("留守老人")){
                        list.add(mVals[position]);
                        listCode.add("11");
                    }else if(mVals[position].equals("空巢(有子女)")){
                        list.add(mVals[position]);
                        listCode.add("10");
                    }else if(mVals[position].equals("空巢(无子女)")){
                        list.add(mVals[position]);
                        listCode.add("09");
                    }else if(mVals[position].equals("低保")){
                        list.add(mVals[position]);
                        listCode.add("08");
                    }else if(mVals[position].equals("五保")){
                        list.add(mVals[position]);
                        listCode.add("07");
                    }else if(mVals[position].equals("优抚对象")){
                        list.add(mVals[position]);
                        listCode.add("06");
                    }else if(mVals[position].equals("三无")){
                        list.add(mVals[position]);
                        listCode.add("05");
                    }



                }

                return true;
            }
        });



        tv_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("1")){

                }else {
                    Toast.makeText(CardImageLiveFaceVerifyActivity.this, "请输入正确的身份证号码,会自动填充性别!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("1")){

                }else {
                    Toast.makeText(CardImageLiveFaceVerifyActivity.this, "请输入正确的身份证号码,会自动填充出生日期!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        et_idcard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                String Idcard=et_idcard.getText().toString();
                if ((et_idcard.length()==15) ){
                    //   Toast.makeText(AddFindActivity.this,IDCard.IDCardValidate(Idcard),Toast.LENGTH_SHORT).show();

                    String info= IDCard.IDCardValidate(Idcard);
                    if (info.equals("")){
                        tv_birth.setText( IDCard.getbirthdayNew(Idcard));
                        tv_sex.setText(IDCard.getSex(Idcard));
                    }else{
                        tv_birth.setText("");
                        tv_sex.setText("");
//                        Toast.makeText(PersonalAocumentActivity.this,info,Toast.LENGTH_SHORT).show();
                    }
                }else if(et_idcard.length()==18){
                    String info= IDCard.IDCardValidate(Idcard);
                    if (info.equals("")){
                        tv_birth.setText( IDCard.getbirthdayNew(Idcard));
                        tv_sex.setText(IDCard.getSex(Idcard));
                    }else{
                        tv_birth.setText("");
                        tv_sex.setText("");
                    }
                }

            }
        });

        huixian();
    }
    TagAdapter<String> tagAdapter ;
    private void huixian(){
        flag= (String) this.getIntent().getStringExtra("flag");
        mpeopleBasebean = (HealthListBean.RowsBean) this.getIntent().getSerializableExtra("mpeopleBasebean");
        if(flag.equals("1")){


            iv_face1.setVisibility(View.VISIBLE);
            ivFace.setVisibility(View.GONE);
            String pictureUrl = mpeopleBasebean.getSElderPhoto();
            GlideUtils.loadImageWithPlaceholderAndError(iv_face1, pictureUrl, com.jqsoft.nursing.R.mipmap.cf_click_to_collect, com.jqsoft.nursing.R.mipmap.cf_click_to_collect);

//            final float scale = CardImageLiveFaceVerifyActivity.this.getResources().getDisplayMetrics().density;
//           int px=(int) (190 * scale + 0.5f);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(px,
//                    px);//两个400分别为添加图片的大小
//            ivFace.setLayoutParams(params);

            tv_birth.setText(mpeopleBasebean.getDBirthTime());
            tv_sex.setText(mpeopleBasebean.getSSexName());
            tv_nation.setText(mpeopleBasebean.getSNationName());

            iv_idcard.setVisibility(View.GONE);
            iv_location.setVisibility(View.GONE);
            btnVerify.setVisibility(View.GONE);
            et_name.setFocusable(false);
            et_name.setClickable(true);
            et_idcard.setFocusable(false);
            et_idcard.setClickable(true);
            et_hujiadress.setFocusable(false);
            et_hujiadress.setClickable(true);
            et_familyadress.setFocusable(false);
            et_familyadress.setClickable(true);

            et_hujiadress_new.setFocusable(false);
            et_hujiadress_new.setClickable(true);

            et_name.setText(mpeopleBasebean.getSName());
            et_idcard.setText(mpeopleBasebean.getSIdCard());
            et_hujiadress_new.setText(mpeopleBasebean.getSRegisterAddress());
            et_familyadress.setText(mpeopleBasebean.getSAddress());
//            tv_nation.setText(mpeopleBasebean.getSNationName());
//            tv_nation.setText(mpeopleBasebean.getSNationName());
            String sPeopleleibie=mpeopleBasebean.getSPersonTypeName();
            if(sPeopleleibie!=null){
                String[] mVals =sPeopleleibie.split(",");
                for(int position=0;position<mVals.length;position++){
                    if(mVals[position].equals("其他")){
                        tagAdapter.setSelectedList(9);
                    }else if(mVals[position].equals("重病大病")){
                        tagAdapter.setSelectedList(8);
                    }else if(mVals[position].equals("失地农民")){
                        tagAdapter.setSelectedList(7);
                    }else if(mVals[position].equals("留守老人")){
                        tagAdapter.setSelectedList(6);
                    }else if(mVals[position].equals("空巢(有子女)")){
                        tagAdapter.setSelectedList(5);
                    }else if(mVals[position].equals("空巢(无子女)")){
                        tagAdapter.setSelectedList(4);
                    }else if(mVals[position].equals("低保")){
                        tagAdapter.setSelectedList(3);
                    }else if(mVals[position].equals("五保")){
                        tagAdapter.setSelectedList(2);
                    }else if(mVals[position].equals("优抚对象")){
                        tagAdapter.setSelectedList(1);
                    }else if(mVals[position].equals("三无")){
                        tagAdapter.setSelectedList(0);
                    }

                }

            }

            tagAdapter.disableAllItemChoser();
        }
    }


    final List<String> list = new ArrayList<>();
    final List<String> listCode = new ArrayList<>();
    List<String> listAreaName = new ArrayList<>();
    private String sAddress="";

    private String[] mVals = new String[]
            { "三无", "优抚对象", "五保",
                    "低保", "空巢(无子女)", "空巢(有子女)", "留守老人", "失地农民", "重病大病","其他"};

    private TagFlowLayout mFlowLayout;

    public void setNation() {
        raceList.add(new NameValueBean("汉族", "01"));
        raceList.add(new NameValueBean("蒙古族", "02"));
        raceList.add(new NameValueBean("回族", "03"));
        raceList.add(new NameValueBean("藏族", "04"));
        raceList.add(new NameValueBean("维吾尔族", "05"));
        raceList.add(new NameValueBean("苗族", "06"));
        raceList.add(new NameValueBean("彝族", "07"));
        raceList.add(new NameValueBean("壮族", "08"));
        raceList.add(new NameValueBean("布依族", "09"));
        raceList.add(new NameValueBean("朝鲜族", "10"));
        raceList.add(new NameValueBean("满族", "11"));
        raceList.add(new NameValueBean("侗族", "12"));
        raceList.add(new NameValueBean("瑶族", "13"));
        raceList.add(new NameValueBean("白族", "14"));
        raceList.add(new NameValueBean("土家族", "15"));
        raceList.add(new NameValueBean("哈尼族", "16"));
        raceList.add(new NameValueBean("哈萨克族", "17"));
        raceList.add(new NameValueBean("傣族", "18"));
        raceList.add(new NameValueBean("黎族", "19"));
        raceList.add(new NameValueBean("傈僳族", "20"));
        raceList.add(new NameValueBean("佤族", "21"));
        raceList.add(new NameValueBean("畲族", "22"));
        raceList.add(new NameValueBean("高山族", "23"));
        raceList.add(new NameValueBean("拉祜族", "24"));
        raceList.add(new NameValueBean("水族", "25"));
        raceList.add(new NameValueBean("东乡族", "26"));
        raceList.add(new NameValueBean("纳西族", "27"));
        raceList.add(new NameValueBean("景颇族", "28"));
        raceList.add(new NameValueBean("柯尔克孜族", "29"));
        raceList.add(new NameValueBean("土族", "30"));
        raceList.add(new NameValueBean("达斡尔族", "31"));
        raceList.add(new NameValueBean("仫佬族", "32"));
        raceList.add(new NameValueBean("羌族", "33"));
        raceList.add(new NameValueBean("布朗族", "34"));
        raceList.add(new NameValueBean("撒拉族", "35"));
        raceList.add(new NameValueBean("毛南族", "36"));
        raceList.add(new NameValueBean("仡佬族", "37"));
        raceList.add(new NameValueBean("锡伯族", "38"));
        raceList.add(new NameValueBean("阿昌族", "39"));
        raceList.add(new NameValueBean("普米族", "40"));
        raceList.add(new NameValueBean("塔吉克族", "41"));
        raceList.add(new NameValueBean("怒族", "42"));
        raceList.add(new NameValueBean("乌孜别克族", "43"));
        raceList.add(new NameValueBean("俄罗斯族", "44"));
        raceList.add(new NameValueBean("鄂温克族", "45"));
        raceList.add(new NameValueBean("崩龙族", "46"));
        raceList.add(new NameValueBean("保安族", "47"));
        raceList.add(new NameValueBean("裕固族", "48"));
        raceList.add(new NameValueBean("京族", "49"));
        raceList.add(new NameValueBean("塔塔尔族", "50"));
        raceList.add(new NameValueBean("独龙族", "51"));
        raceList.add(new NameValueBean("鄂伦春族", "52"));
        raceList.add(new NameValueBean("赫哲族", "53"));
        raceList.add(new NameValueBean("门巴族", "54"));
        raceList.add(new NameValueBean("珞巴族", "55"));
        raceList.add(new NameValueBean("基诺族", "56"));
        raceList.add(new NameValueBean("其他", "97"));
        raceList.add(new NameValueBean("外国血统中国籍人士", "99"));

    }


    private void initData1() {
        initOkHttpUtils();
        populateData();
        initPictureLibrary();
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

    private void populateData() {
        Intent intent = getIntent();
        if (intent != null) {
            idNumber = intent.getStringExtra(PASSIN_ID_NUMBER_KEY);
            isOnlyFace = intent.getStringExtra(PASSIN_ID_ISMOTHER_KEY);
            sPersonID = intent.getStringExtra(SPERSON_ID);
            sUserName = intent.getStringExtra(SUERSENAME);
            idNumber = Util.trimString(idNumber);
            idNumber = idNumber.replaceAll("x", "X");
        }
    }

    private Bitmap headbitmap = null;

    private void initView1() {
        iv_face1=(ImageView)findViewById(R.id.iv_face1);
        tv_sex=(TextView)findViewById(R.id.tv_sex);
        tv_birth=(TextView)findViewById(R.id.tv_birth);
        et_name=(EditText)findViewById(R.id.et_name);
        et_idcard=(EditText)findViewById(R.id.et_idcard);
        et_hujiadress_new=(EditText)findViewById(R.id.et_hujiadress_new);

        mFlowLayout =(TagFlowLayout)findViewById(R.id.ll_people);
        tv_nation=(TextView)findViewById(R.id.tv_nation);
        et_hujiadress=(TextView)findViewById(R.id.et_hujiadress);
        iv_idcard=(ImageView)findViewById(R.id.iv_idcard);
        et_familyadress=(EditText)findViewById(R.id.et_familyadress);
        iv_location=(ImageView)findViewById(R.id.iv_location);

        btn_changeCamera = (SwitchButton) findViewById(R.id.btn_changeCamera);
        ll_SwBtn = (LinearLayout) findViewById(R.id.ll_SwBtn);
        ll_card = (LinearLayout) findViewById(R.id.ll_card);
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
        apply_btn = (TextView) findViewById(R.id.apply_btn);
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
        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        faceType = userSettings.getInt("FaceType", 0);
        sCheckFacePhoto = userSettings.getString("sCheckFacePhoto", "");
        if (sCheckFacePhoto.equals("1")) {
            apply_btn.setVisibility(View.VISIBLE);
        } else {
            apply_btn.setVisibility(View.GONE);
        }
        //强制使用eid把下面代码打开
//        faceType=2;
//        final SharedPreferences.Editor editor2 = userSettings.edit();
//        editor2.putInt("FaceType", 2);
//        editor2.apply();


        if (2 != faceType) {
            ll_SwBtn.setVisibility(View.VISIBLE);

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
                if(flag.equals("1")){

                }else {
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
                        editor.commit();
//                    changeCamera.setText("前置");

                    }
                }

            }
        });
        ivFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag.equals("1")){

                }else {
                    final SharedPreferences userSettings = getSharedPreferences("setting", 0);
                    int faceType = userSettings.getInt("FaceType", 0);
                    if (faceType == 1) {
                        selectLiveHead();
                    } else {
                        selectFaceImage();
                    }
                }
//

            }
        });
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("1")){

                }else {
                    PhotoDialog inputDialog = new PhotoDialog(1, CardImageLiveFaceVerifyActivity.this).builder().setTitle("申请人工审核")
                            .setShowReason(true)
                            .setPositiveBtn("确定", new PhotoDialog.OnPositiveListener() {
                                @Override
                                public void onPositive(View view, String inputMsg) {
                                    if (TextUtils.isEmpty(inputMsg)) {
                                        Toast.makeText(CardImageLiveFaceVerifyActivity.this, "申请人工审核原因不能为空", Toast.LENGTH_SHORT).show();
                                    } else {
                                        sRemark = inputMsg;
                                        if (takePhotoBitmap != null) {
                                            new Thread(applySend).start();
                                        } else {
                                            Toast.makeText(CardImageLiveFaceVerifyActivity.this, "请拍照后再申请", Toast.LENGTH_SHORT).show();
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
                if(flag.equals("1")){

                }else {
                    viewLicense.toggle();
                    refreshVerifyButtonStatus(viewLicense.isChecked());
                }
            }
        });


        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btnVerify.setClickable(false);
                //sOnlyFace开启首次人脸比对，隐藏人证比对

                Bitmap bm = ((BitmapDrawable) ((ImageView) ivFace).getDrawable()).getBitmap();
                if (bm == null) {
                    bm = headbitmap;
                }




                registerFace(bm);
//
//                if (faceType == 1) {
//                    Bitmap bm = ((BitmapDrawable) ((ImageView) ivFace).getDrawable()).getBitmap();
//                    if (bm == null) {
//                        bm = headbitmap;
//                    }
//
//
//
//
//                    registerFace(bm);
//                } else {
//                    if (isOnlyFace.equals("1")) {
//                        ToastUtil.show(CardImageLiveFaceVerifyActivity.this,"t");
////                        onVerifyButtonOnlyFace();
//                    } else {
//                        ToastUtil.show(CardImageLiveFaceVerifyActivity.this,"t");
////                        onVerifyButtonClicked();
//                    }
//                }


            }
        });
        refreshVerifyButtonStatus(viewLicense.isChecked());
//        refreshVerifyButtonStatus(cbLicense.isChecked());

        //判断是否只开启人脸比对，不开启人证比对
        if (isOnlyFace.equals("1")) {
            ll_card.setVisibility(View.GONE);
        } else {
            ll_card.setVisibility(View.VISIBLE);
        }
        iv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("1")){

                }else {
                    Intent intent = new Intent();
                    intent.putExtra("SocialDeatailLocatLat", "null");
                    intent.putExtra("SocialDeatailLocatLng", "null");
                    intent.setClass(getApplicationContext(), MainMapActivity.class);
                    startActivityForResult(intent, REQUEST_A);
                }
            }
        });
        ScanCardUtil.setCallBack(this);
        iv_idcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("1")){

                }else {
                    selectImage();
                }
//                ScanCardActivity.start(CardImageLiveFaceVerifyActivity.this, true);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    public static final int REQUEST_A = 10;

    private void onBackClicked() {
        deleteByFilePath(cardFrontPath);
        returnFailure();
        finish();
    }

    private void selectLiveHead() {
        imageSelectType = IMAGE_SELECT_TYPE_FACE;
        FaceDetector.init(CardImageLiveFaceVerifyActivity.this);
        Intent intent = new Intent(CardImageLiveFaceVerifyActivity.this, RegisterAndRecognizeActivity.class);

        intent.putExtra("isOnlyFace", isOnlyFace);
        intent.putExtra("IdCardPath", IdCardPath);
        intent.putExtra("formWay", "CardImageLiveFaceVerifyActivity");
        intent.putExtra("path", cardFrontPath);
        intent.putExtra("idNumber", idNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, AFFACE_HEAD);

    }

    private void verifyIdCardNumber(final Bitmap bitmap) {
        isInResolveIdCardNumberMode = true;
        Util.showGifProgressDialog(this, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isInResolveIdCardNumberMode) {
                    refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
                }
                isInResolveIdCardNumberMode = false;
            }
        });
        strPic = BitMapUtils.bitmapToBase64(bitmap);


        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        readcardType_VALUE = userSettings.getString("sReadCardType_VALUE", "");

        if (TextUtils.isEmpty(Version.getsReadCard_Value())) {
            Version.setsReadCard_Value(readcardType_VALUE);
        }
        new Thread(netReadCard).start();
//        if (Version.getsReadCard_Value().equals("1")) {
//
//        }else {
//            TecentHttpUtil.readIdCard(BitMapUtils.bitmapToBase64(bitmap), new TecentHttpUtil.SimpleCallBack() {
//                @Override
//                public void Succ(String res) {
//
//                    SuccResult = res;
//                    handler.sendEmptyMessage(1);
//
//                }
//
//                @Override
//                public void error() {
//                    handler.sendEmptyMessage(2);
//
//                }
//            });
//        }
    }

    private String SuccResult = "";

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
        AlertDialog.Builder builder = new AlertDialog.Builder(CardImageLiveFaceVerifyActivity.this);
        AlertDialog dialogInfo = builder.setTitle("识别成功")
                .setMessage(sb.toString())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })

                .create();
        dialogInfo.show();


    }

    private String strPic;
    private CardBean cardBean;
    private Runnable netReadCard = new Runnable() {
        @Override
        public void run() {
            cardBean = WebServiceUtils.redCardId(strPic, CardImageLiveFaceVerifyActivity.this);
            if (cardBean != null) {
                if (!TextUtils.isEmpty(cardBean.getsCardNo())) {
                    // 识别成功
                    myhandler.sendEmptyMessage(PICSCCUSE);

                } else {
                    myhandler.sendEmptyMessage(PICERROR);

                }
            } else {
                myhandler.sendEmptyMessage(ERROR01);
            }

        }
    };

    private void refreshVerifyButtonStatus(boolean isChecked) {
        btnVerify.setEnabled(true);
//        btnVerify.setEnabled(isChecked);
    }

    private void verifyNormal() {
        isForcePass = false;
        verify(VERIFY_STATUS_NORMAL);
    }

    private void verifyForce() {
        isForcePass = true;
        verify(VERIFY_STATUS_FORCE);
    }


    private String facebaseurl = "", readcardType_VALUE = "";

    private void verify(String status) {
        Util.showGifProgressDialog(this, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                cancelNetworkRequest();
            }
        });

        String cardFrontBase64String = Base64Util.imageToBase64(cardFrontPath);
        String faceBase64String = Base64Util.imageToBase64(facePath);

        long timestamp = Util.getTimeStamp();
        String timestampString = String.valueOf(timestamp);
        String token = Util.getProcessedToken(timestamp);

        Map<String, String> params = new HashMap<>();
        params.put("acs_token", token);
        params.put("acs_time", timestampString);
        params.put("idnum", idNumber);
        params.put("status", status);
        params.put("card_base64", cardFrontBase64String);
        params.put("head_base64", faceBase64String);


        String feature_base64 = FaceServer.getInstance().sFeature;
        params.put("feature_base64", feature_base64);//人类特征数据  base64字符串（可为空）
        params.put("arcsoft_version", "2.1");//虹软引起版本号码（可为空）


        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        facebaseurl = userSettings.getString("face_base_url", "");

        if (TextUtils.isEmpty(Version.BASE_URL)) {
            Version.BASE_URL = facebaseurl;
        }


        String url = Version.BASE_URL + Version.CREATE_FEATURE_URL;
        Log.i("chenxu", "url" + url);
        call = OkHttpUtils.post()//
//                .addFile("mFile", "agguigu-afu.jpe", file)//
                .url(url)//
                .params(params)//
//                .headers(headers)//
                .build();//
        call.execute(new MyResultCallback<VerifyResultBean>() {

            @Override
            public void onSuccess(VerifyResultBean response) {
                Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                btnVerify.setClickable(true);
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
                    showNotificationDialogForVerifyFailure("智能提示" + suffix, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
                        }
                    });
//                            showNotificationDialog("智能提示" + suffix+"。是否强制通过？", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    verifyForce();
//                                }
//                            });
                }
            }

            @Override
            public void onFailure(Exception e) {
                Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                btnVerify.setClickable(true);
                returnFailure();
                Log.i("chenxu", e.getMessage() + "CardImageLiveFaceVerifyActivity onFailure");
                if (e.getMessage() != null) {
                    showNotificationDialogForVerifyFailure(e.getMessage(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
                        }
                    });
                } else {
                    showNotificationDialogForVerifyFailure("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
                        }
                    });
                }
//                        showNotificationDialog("智能提示"+"。是否强制通过？", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                verifyForce();
//                            }
//                        });
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
                        .setNegativeButton("取消", null)
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("chenxu", "CardImageLiveFaceVerifyActivity showNotificationDialog encounter exceptioin");
        }

    }

    private void showNotificationDialogForVerifyFailure(String msg, DialogInterface.OnClickListener positiveListener) {
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
        intent.putExtra(SPERSON_ID, sPersonID);
        intent.putExtra(PASSOUT_HEAD_IMAGE_PATH_KEY, facePath);
        intent.putExtra(SUERSENAME, sUserName);
//        intent.putExtra(VERIFY_CARD_NO_KEY, idNumber);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void returnFailure() {
        Intent intent = new Intent();
        intent.putExtra(VERIFY_STATUS_KEY, false);
        intent.putExtra(PASSIN_ID_NUMBER_KEY, idNumber);
        intent.putExtra(SPERSON_ID, sPersonID);
        intent.putExtra(SUERSENAME, sUserName);
        intent.putExtra(PASSOUT_HEAD_IMAGE_PATH_KEY, "");
//        intent.putExtra(VERIFY_CARD_NO_KEY, idNumber);
        setResult(RESULT_CANCELED, intent);

    }

    private void onVerifyButtonClicked() {
        if (false/*!viewLicense.isChecked()*/) {
//        if (!cbLicense.isChecked()){
            showToast("请同意许可协议");
            btnVerify.setClickable(true);
        } else if (!isFilePathValid(cardFrontPath)) {
            ivCardFront.setImageResource(R.mipmap.cf_id_card_number);
            showToast("身份证正面照不能为空,请重新点击身份证拍照");
            btnVerify.setClickable(true);

        } /*else if (!isFilePathValid(cardBackPath)){
            showToast("身份证背面照不能为空");
        } */ else if (!isFilePathValid(facePath)) {
            showToast("头像不能为空");
            btnVerify.setClickable(true);
        } else {
            compressBitmap();
//            new Thread(SaveBasePhotoRunnable).start();
//
            verifyNormal();
        }
    }

    private void compressBitmap() {
        if (cardFrontPath != null) {
            List<File> mFileList = new ArrayList<>();
            File file1 = new File(cardFrontPath);
            mFileList.clear();
            mFileList.add(file1);
            Luban.compress(CardImageLiveFaceVerifyActivity.this, mFileList)
                    .setMaxSize(100)                // limit the final image size（unit：Kb）
                    // limit image width
                    .putGear(Luban.CUSTOM_GEAR)     // use CUSTOM GEAR compression mode
                    .asListObservable()
                    .subscribe(new Consumer<List<File>>() {
                        @Override
                        public void accept(List<File> files) throws Exception {
                            cardFrontPath = files.get(0).getAbsolutePath();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                        }
                    });
        }


    }

    private void onVerifyButtonOnlyFace() {
        if (!isFilePathValid(facePath)) {
            showToast("头像不能为空");
        } else {
            isForcePass = false;
//            Util.showGifProgressDialog(this, new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    cancelNetworkRequest();
//                }
//            });
//            compressBitmap();
////            new Thread(SaveBasePhotoRunnable).start();
            verifyOnlyFace(VERIFY_STATUS_NORMAL);
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
            // PictureConfig.getInstance().init(options).startOpenCamera(CardImageLiveFaceVerifyActivity.this);

                selectImage();


        } else {
            PictureConfig.getInstance().init(options).openPhoto(this, resultCallback);
        }


    }

    private void selectCardBackImage() {

        imageSelectType = IMAGE_SELECT_TYPE_CARD_BACK;
//        PictureConfig.getInstance().init(options).openPhoto(this, resultCallback);

    }

    private void selectFaceImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            //Toast.makeText(MainActivity.this,"您申请了动态权限",Toast.LENGTH_SHORT).show();
            //如果有了相机的权限有调用相机
            imageSelectType = IMAGE_SELECT_TYPE_FACE;
            if (isFaceOnlyFromCamera) {
                PictureConfig.getInstance().init(options).startOpenCamera(CardImageLiveFaceVerifyActivity.this);
                // selectImage();
            } else {
                PictureConfig.getInstance().init(options).openPhoto(this, resultCallback);
            }
        } else {
            //否则去请求相机权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);

        }

    }

    private void deleteByFilePath(String filePath) {
        ivCardFront.setImageResource(R.mipmap.cf_id_card_number);
        filePath = Util.trimString(filePath);
        DeleteFileUtil.delete(filePath);
    }

    @Override
    public void onBackPressed() {
        onBackClicked();
//        super.onBackPressed();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

                } else if (/*isFaceOnlyFromCamera && */imageSelectType == IMAGE_SELECT_TYPE_FACE) {
                    if (data != null) {
                        List<LocalMedia> mediaList = (List<LocalMedia>) data.getSerializableExtra(FunctionConfig.EXTRA_RESULT);
                        if (mediaList != null && mediaList.size() > 0) {
                            LocalMedia lm = mediaList.get(0);
                            onSelectOneMediaForFace(lm);
                        }

                    }

                }


            }
            if (requestCode == TAKE_PHOTO) {
                Uri uri = photoPath;
                Log.i("jie01", uri + "uri-------");
                String path = Util.getRealFilePath(CardImageLiveFaceVerifyActivity.this, uri);
                if (path != null) {
                    takePhotoBitmap = getImage(path);

                }


            }
        }
        if (requestCode == IDCARD_REQUEST) {
            if (resultCode == RESULT_OK) {
                final String path = CameraActivity.getResult(data);
//                Bundle extras = data.getExtras();
//                String path = extras.getString("path");

//                BitmapUtil.setPath(path);
                Log.e("jerry", "path:==" + path);
                IdCardPath = path;
                bitmap = getImage(path);
//                refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, path, bitmap);
                verifyIdCardNumber(bitmap);

//                if (faceType == 1) {
////                    registerFace1(bitmap);
//                    verifyIdCardNumber(bitmap);
//                } else {
//                    verifyIdCardNumber(bitmap);
//                }


            }
        }
        if (requestCode == SELECT_LIVE_HEAD) {

            try {
                assert data != null;
                String str64 = data.getStringExtra("eid_img");//照片
                String img_code = data.getStringExtra("eid_img_code");//1为真脸   0为假脸  预留
                if ("1".equals(img_code) && str64 != null && !TextUtils.isEmpty(str64)) {
                    String path = Util.getPreferExternalFolderPath(CardImageLiveFaceVerifyActivity.this, "live_body_face");
                    String filename = Util.getPhotoFileName();
                    path += File.separator + filename;
                    Bitmap bitmap = Base64Util.base64ToBmp(str64);
                    boolean success = Util.saveBitmapToFile(bitmap, path);
                    if (success) {
                        refreshUI(IMAGE_SELECT_TYPE_FACE, path, bitmap);

                    } else {
                        refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
                    }

                } else {
                    refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
                    showNotificationDialog("获取活体人脸失败，请重新重新采集", null);

                }
            } catch (Exception e) {
                e.printStackTrace();
                refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
                showNotificationDialog("获取活体人脸失败，请重新重新采集", null);
            }

        }
        if (requestCode == AFFACE_HEAD) {

            //虹软识别人脸采集返回
            Log.d("90119", String.valueOf(resultCode));
            if (resultCode == 90119 || resultCode == 90115) {
                Util.clearAppData(CardImageLiveFaceVerifyActivity.this);
                Util.clearAllCache(CardImageLiveFaceVerifyActivity.this);

            } else if (resultCode == 1001) {
                showDialog();

            } else if (resultCode == 65539) {//身份证人脸不清晰
                String mErrCode="";
                try {
                     mErrCode=data.getExtras().getString("ErrCode");
                }catch (Exception e){
                    mErrCode="";
                }

                showIdCardFailDialog(mErrCode);

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
                    String bitmappath = userSettings.getString("bitmappath", "");
                    int mOrientation = userSettings.getInt("mOrientation", 0);

                    Bitmap bitmap = BitmapFactory.decodeFile(bitmappath);

                    int CameraInfo = userSettings.getInt("CameraInfo", Camera.CameraInfo.CAMERA_FACING_BACK);
                    if (bitmap != null) {
                        if (Camera.CameraInfo.CAMERA_FACING_BACK == CameraInfo) {

                            bitmap = rotateBitmap(bitmap, 90 + mOrientation);
                            saveBitmapToFile(bitmap, bitmappath);
                        } else {
                            bitmap = rotateBitmap(bitmap, -90 - mOrientation);
                            saveBitmapToFile(bitmap, bitmappath);
                        }
                    }
                    SharedPreferences.Editor editor = userSettings.edit();
                    editor.putInt("mOrientation", 0);
                    List<File> mFileList = new ArrayList<>();
                    File file1 = new File(bitmappath);
                    mFileList.clear();
                    mFileList.add(file1);
                    Luban.compress(CardImageLiveFaceVerifyActivity.this, mFileList)
                            .setMaxSize(100)                // limit the final image size（unit：Kb）
                            .setMaxHeight(480)             // limit image height
                            .setMaxWidth(640)              // limit image width
                            .putGear(Luban.CUSTOM_GEAR)     // use CUSTOM GEAR compression mode
                            .asListObservable()
                            .subscribe(new Consumer<List<File>>() {
                                @Override
                                public void accept(List<File> files) throws Exception {

                                    Bitmap bitmap = BitmapFactory.decodeFile(files.get(0).getAbsolutePath());

                                    refreshUI(IMAGE_SELECT_TYPE_FACE, files.get(0).getAbsolutePath(), bitmap);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    throwable.printStackTrace();
                                    SharedPreferences userSettings = getSharedPreferences("setting", 0);
                                    String bitmappath = userSettings.getString("bitmappath", "");
                                    Bitmap bitmap = BitmapFactory.decodeFile(bitmappath);
                                    int CameraInfo = userSettings.getInt("CameraInfo", Camera.CameraInfo.CAMERA_FACING_BACK);
                                    if (bitmap != null) {
                                        if (Camera.CameraInfo.CAMERA_FACING_BACK == CameraInfo) {
                                            bitmap = rotateBitmap(bitmap, 90);
                                            saveBitmapToFile(bitmap, bitmappath);
                                        } else {
                                            bitmap = rotateBitmap(bitmap, -90);
                                            saveBitmapToFile(bitmap, bitmappath);
                                        }
                                    }
                                    refreshUI(IMAGE_SELECT_TYPE_FACE, bitmappath, bitmap);
                                }
                            });


//                        } else {
//                            refreshUI("", null);
//                        }

                } else {
                    refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);

                }

            }


        }

        if (requestCode == 10 && resultCode == 10) {

            String change01 = data.getStringExtra("location");
            slongitude = data.getStringExtra("slongitude");
            slongitude = slongitude;
            slocation = change01;
            if (slongitude == null || "null".equals(slongitude) || TextUtils.isEmpty(slongitude)) {
                slongitude = "";

                et_familyadress.setText(change01);

            } else {

                String[] as = slongitude.split(",");
                if (as.length > 1) {
                    slongitude = as[1] + "," + as[0];
                    et_familyadress.setText(change01);
                } else {
                    slongitude = "";
                    et_familyadress.setText(change01);
                }

            }


        }
    }
    private String slongitude = "", slocation = "";
    private void showDialog() {

        new CommonDialog(CardImageLiveFaceVerifyActivity.this).builder().setTitle("人脸识别超时提示")
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
                        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
                        int faceType = userSettings.getInt("FaceType", 0);
                        if (faceType == 1) {
                            selectLiveHead();
                        } else {
                            selectFaceImage();
                        }
                    }
                }).show();
    }

    private void showIdCardFailDialog(String mErrCode) {

        new CommonDialog(CardImageLiveFaceVerifyActivity.this).builder().setTitle("身份证识别超时提示")
                .setContentMsg("请确保身份证拍摄人像清晰！\n"+"错误码:"+mErrCode)
                .setNegativeBtn("退出", new CommonDialog.OnNegativeListener() {
                    @Override
                    public void onNegative(View view) {
                        finish();
                    }
                })
                .setPositiveBtn("确定", new CommonDialog.OnPositiveListener() {
                    @Override
                    public void onPositive(View view) {
                        selectCardFrontImage();
                    }
                }).show();
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
        } else {
            deleteByFilePath(media.getPath());
        }
        String temp[] = paths.replaceAll("\\\\", "/").split("/");
        if (temp.length > 1) {
            String aFileName = temp[temp.length - 1];
            media.setFileName(aFileName);
        }

//            String s1 = Base64Util.imageToBase64(paths);

        Bitmap bitmap = BitmapFactory.decodeFile(paths);

        refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, paths, bitmap);

        verifyIdCardNumber(bitmap);

    }

    private void onSelectOneTake(String paths) {
        //   String paths = Util.trimString(media.getCompressPath());
        if (TextUtils.isEmpty(paths)) {
            paths = Util.trimString(paths);
        } else {
            deleteByFilePath(paths);
        }
        // String temp[] = paths.replaceAll("\\\\", "/").split("/");
//        if (temp.length > 1) {
//            String aFileName = temp[temp.length - 1];
//            media.setFileName(aFileName);
//        }

//            String s1 = Base64Util.imageToBase64(paths);

        bitmap = getImage(paths);

        refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, paths, bitmap);

        verifyIdCardNumber(bitmap);

    }

    private void onSelectOneMediaForFace(LocalMedia media) {
        String paths = Util.trimString(media.getCompressPath());
        if (TextUtils.isEmpty(paths)) {
            paths = Util.trimString(media.getPath());
        } else {
//            deleteByFilePath(media.getPath());
        }
        String temp[] = paths.replaceAll("\\\\", "/").split("/");
        if (temp.length > 1) {
            String aFileName = temp[temp.length - 1];
            media.setFileName(aFileName);
        }

//            String s1 = Base64Util.imageToBase64(paths);

        Bitmap bitmap = BitmapFactory.decodeFile(paths);

        refreshUI(IMAGE_SELECT_TYPE_FACE, paths, bitmap);


    }

    private void refreshUI(int imageSelectType, String path, Bitmap bitmap) {
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (imageSelectType == IMAGE_SELECT_TYPE_CARD_FRONT) {
//            deleteByFilePath(cardFrontPath);
            cardFrontPath = path;
            if (bitmap != null) {
                ivCardFront.setImageBitmap(WaterMarkBitmapUtil.createWaterMaskBitmap(this, bitmap, "两卡制专用"));
            } else {
                ivCardFront.setImageResource(R.mipmap.cf_id_card_number);
            }
        } else if (imageSelectType == IMAGE_SELECT_TYPE_CARD_BACK) {
//            deleteByFilePath(cardBackPath);
            cardBackPath = path;
            if (bitmap != null) {
                ivCardBack.setImageBitmap(bitmap);
            } else {
                ivCardBack.setImageResource(R.mipmap.cf_card_back);
            }
        } else if (imageSelectType == IMAGE_SELECT_TYPE_FACE) {
//            deleteByFilePath(facePath);
            facePath = path;
            if (bitmap != null) {
                headbitmap = bitmap;
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
        FaceServer.getInstance().unInit();
        super.onDestroy();
        cancelNetworkRequest();
        if (myhandler != null) {
            myhandler.removeCallbacksAndMessages(null);
        }
    }

    private void cancelNetworkRequest() {
        if (call != null) {
            call.cancel();
        }
        call = null;
    }

    private int IDCARD_REQUEST = 900;

    private void selectImage() {
//        MultiImageSelector.create(getActivity()).showCamera(true) // 是否显示相机. 默认为显示
////                .count(1) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
//                .single() // 单选模式
////                .multi() // 多选模式, 默认模式;
////                .origin(ArrayList<String>) // 默认已选择图片. 只有在选择模式为多选时有效
//                .start(getActivity(), REQUEST_IMAGE);


        //去寻找是否已经有了相机的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            //Toast.makeText(MainActivity.this,"您申请了动态权限",Toast.LENGTH_SHORT).show();
            //如果有了相机的权限有调用相机
            startRetrievingIdCardNumberFromTakingPicture(IDCARD_REQUEST);

        } else {
            //否则去请求相机权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);

        }


    }


    public void startRetrievingIdCardNumberFromTakingPicture(int requestCode) {
        Intent intent = new Intent(CardImageLiveFaceVerifyActivity.this, CameraActivity.class);//CameraActivity

//        Intent intent = new Intent(CardImageLiveFaceVerifyActivity.this, CameraActivity.class);//CameraActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        String pathStr = Util.getImageTakenPreferSDPath(CardImageLiveFaceVerifyActivity.this);
        String nameStr = Util.getPhotoFileNames();
        if (!TextUtils.isEmpty(pathStr)) {
            intent.putExtra("path", pathStr);
        }
        if (!TextUtils.isEmpty(nameStr)) {
            intent.putExtra("name", nameStr);
        }
        CardImageLiveFaceVerifyActivity.this.startActivityForResult(intent, requestCode);

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


    private void verifyOnlyFace(String status) {
        Util.showGifProgressDialog(this, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                cancelNetworkRequest();
            }
        });

        String sName =et_name.getText().toString().trim();
        String sIdcard =et_idcard.getText().toString().trim();
        String sSex =tv_sex.getText().toString().trim();
        String sNation =tv_nation.getText().toString().trim();
        String sBirth =tv_birth.getText().toString().trim();
        String sFamilyAdress=et_familyadress.getText().toString().trim();
        String shujiAdress=et_hujiadress_new.getText().toString().trim();

        if(TextUtils.isEmpty(sName)){
                Toast.makeText(CardImageLiveFaceVerifyActivity.this,"请输入姓名!",Toast.LENGTH_LONG).show();
                return;
        }else if(TextUtils.isEmpty(sIdcard)){
            Toast.makeText(CardImageLiveFaceVerifyActivity.this,"请输入身份证号码!",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(sSex)){
            Toast.makeText(CardImageLiveFaceVerifyActivity.this,"请输入正确的身份证号码，自动填充性别!",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(sNation)){
            Toast.makeText(CardImageLiveFaceVerifyActivity.this,"请输入民族!",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(sBirth)){
            Toast.makeText(CardImageLiveFaceVerifyActivity.this,"请输入正确的身份证号码，自动填充出生日期!",Toast.LENGTH_LONG).show();
            return;
        }else if(listCode.size()==0){
            Toast.makeText(CardImageLiveFaceVerifyActivity.this,"请选择人员类别!",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(shujiAdress)){
            Toast.makeText(CardImageLiveFaceVerifyActivity.this,"请选择户籍地址!",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(sFamilyAdress)){
            Toast.makeText(CardImageLiveFaceVerifyActivity.this,"请输入常住住址!",Toast.LENGTH_LONG).show();
            return;
        }else if(TextUtils.isEmpty(facePath)){
            Toast.makeText(CardImageLiveFaceVerifyActivity.this,"请采集头像!",Toast.LENGTH_LONG).show();
            return;
        }else {

            String faceBase64String = Base64Util.imageToBase64(facePath);
            String sToken= PreferencesUtils.getString(CardImageLiveFaceVerifyActivity.this,"token");
            Map<String, String> params = new HashMap<>();
            params.put("Token", sToken);
            params.put("sName", sName);

            params.put("sSexName", sSex);
            if(sSex.equals("男")){
                params.put("sSexCode", "1");
            }else if(sSex.equals("女")){
                params.put("sSexCode", "2");
            }

            params.put("sIdCard", sIdcard);
            String sAge="0";
            try{
                 sAge=""+IDCard.getAge(sIdcard);
            }catch (Exception e){
                 sAge="0";
            }

            params.put("iAge", sAge);
            params.put("dBirthTime", sBirth);
            params.put("sNationCode", ssNationCode);
            params.put("sNationName", ssNationName);
            params.put("sRegisterAddress", shujiAdress);
            params.put("sAddress", sFamilyAdress);

            params.put("sRegisterProvince", "");
            params.put("sRegisterProvinceName", "");
            params.put("sRegisterCity", "");
            params.put("sRegisterCityName","");
            params.put("sRegisterCounty", "");
            params.put("sRegisterCountyName", "");
            params.put("sRegisterStreet", "");
            params.put("sRegisterStreetName", "");
            params.put("sRegisterCommunity", "");
            params.put("sRegisterCommunityName","");

            params.put("sProvince", "");
            params.put("sProvinceName", "");
            params.put("sCity", "");
            params.put("sCityName", "");
            params.put("sCounty", "");
            params.put("sCountyName",  "");
            params.put("sStreet", "");
            params.put("sStreetName", "");
            params.put("sCommunity", "");
            params.put("sCommunityName", "");

//            params.put("sRegisterProvince", provinceCode);
//            params.put("sRegisterProvinceName", listAreaName.get(0));
//            params.put("sRegisterCity", cityCode);
//            params.put("sRegisterCityName", listAreaName.get(1));
//            params.put("sRegisterCounty", countyCode);
//            params.put("sRegisterCountyName", listAreaName.get(2));
//            params.put("sRegisterStreet", streetCode);
//            params.put("sRegisterStreetName", listAreaName.get(3));
//            params.put("sRegisterCommunity", villageCode);
//            params.put("sRegisterCommunityName", listAreaName.get(4));
//
//            params.put("sProvince", provinceCode);
//            params.put("sProvinceName",  listAreaName.get(0));
//            params.put("sCity", cityCode);
//            params.put("sCityName", listAreaName.get(1));
//            params.put("sCounty", countyCode);
//            params.put("sCountyName",  listAreaName.get(2));
//            params.put("sStreet", streetCode);
//            params.put("sStreetName", listAreaName.get(3));
//            params.put("sCommunity", villageCode);
//            params.put("sCommunityName", listAreaName.get(4));

            String feature_base64 = PreferencesUtils.getString(CardImageLiveFaceVerifyActivity.this, "faceFeature", "");
            params.put("sFaceFeatures", feature_base64);
            params.put("sFaceEdition", "2.1");

            if(slongitude!=null){
                try{
                    String[] strArr = slongitude.split(",");
                    if(strArr.length!=0){
                        params.put("sLongitude", strArr[1]);
                        params.put("sLatitude", strArr[0]);
                    }else {
                        params.put("sLongitude","");
                        params.put("sLatitude","");
                    }
                }catch (Exception e){
                    params.put("sLongitude","");
                    params.put("sLatitude","");
                }
            }else {
                params.put("sLongitude","");
                params.put("sLatitude","");
            }

            String sPeople="",sPeopleCode="";
            for(int i=0;i<list.size();i++){
                if(TextUtils.isEmpty(sPeople)){
                    sPeople=list.get(i);
                }else {
                    sPeople=sPeople+","+list.get(i);
                }
            }
            for(int i=0;i<listCode.size();i++){
                if(TextUtils.isEmpty(sPeopleCode)){
                    sPeopleCode=listCode.get(i);
                }else {
                    sPeopleCode=sPeopleCode+","+listCode.get(i);
                }
            }
            params.put("sPersonTypeCode", sPeopleCode);
            params.put("sPersonTypeName", sPeople);



            params.put("sAddUserId", userinfo.getSAddUserId());
            params.put("sElderPhoto", faceBase64String);

        Util.showGifProgressDialog(getApplicationContext());
        saveFaceInfoPresenter.main(params);
        }



/*



        final SharedPreferences userSettings = getSharedPreferences("setting", 0);
        facebaseurl = userSettings.getString("face_base_url", "");

        if (TextUtils.isEmpty(Version.BASE_URL)) {
            Version.BASE_URL = facebaseurl;
        }

        //String url = Version.BASE_URL + Version.CREATE_FEATURE_ONLYFACE_URL;
        String url = "http://192.168.45.20:5444/api/DistinguishAPP/SaveElder";
        Log.i("chenxu", "url" + url);
        call = OkHttpUtils.post()//
//                .addFile("mFile", "agguigu-afu.jpe", file)//
                .url(url)//
                .params(params)//
//                .headers(headers)//
                .build();//
        call.execute(new MyResultCallback<HttpResultNewBaseBean<String>>() {

            @Override
            public void onSuccess(HttpResultNewBaseBean<String> response) {
                Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                String s="1";
//                btnVerify.setClickable(true);
//                if (response != null && "1".equals(response.getRespond())) {
//                    deleteByFilePath(cardFrontPath);
//                    returnSuccess();
//                    Log.i("chenxu", "CardImageLiveFaceVerifyActivity onSuccess");
//                    showToast("验证成功");
//                } else {
//                    returnFailure();
//                    String suffix = "";
//                    if (response != null) {
//                        suffix = ":" + response.getMessage();
//                    }
//                    Log.i("chenxu", "CardImageLiveFaceVerifyActivity failure");
//                    showNotificationDialogForVerifyFailure("智能提示" + suffix, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
//                        }
//                    });
//
//                }
            }

            @Override
            public void onFailure(Exception e) {
                Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                btnVerify.setClickable(true);
                returnFailure();
                Log.i("chenxu", e.getMessage() + "CardImageLiveFaceVerifyActivity onFailure");
                if (e.getMessage() != null) {
                    showNotificationDialogForVerifyFailure(e.getMessage(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
                        }
                    });
                } else {
                    showNotificationDialogForVerifyFailure("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
                        }
                    });
                }
//                        showNotificationDialog("智能提示"+"。是否强制通过？", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                verifyForce();
//                            }
//                        });
            }
        });
        */
    }

    @SuppressLint("HandlerLeak")
    private Handler myhandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PICSCCUSE:
                    isInResolveIdCardNumberMode = false;
                    Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                    String readAPIcard = cardBean.getsCardNo();
                    String name = cardBean.getsPersonName();
                    String nation = cardBean.getsNation();
                    String address = cardBean.getsAddress();
                    readAPIcard = Util.trimString(readAPIcard);

//                        readAPIname = result.getName();
//                        Toast.makeText(CardImageLiveFaceVerifyActivity.this, readAPIcard, Toast.LENGTH_SHORT).show();
                    if (!TextUtils.isEmpty(readAPIcard)) {
                        if(readAPIcard.length()==15 || readAPIcard.length()==18){
                            if(!TextUtils.isEmpty(name)){
                                et_name.setText(name);
                            }
                            et_idcard.setText(readAPIcard);
                            et_hujiadress_new.setText(cardBean.getsAddress());

                        }else {
                            showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                            Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                        }
//                        showNotificationDialog(name+readAPIcard+nation+address, null);

                    } else {
                        showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                        Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                    }

                    break;
                case Constant.APPLy_SCUSSE:

                    Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                    Toast.makeText(CardImageLiveFaceVerifyActivity.this, Applymes, Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case Constant.APPLY_ERAR:
                    Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                    Toast.makeText(CardImageLiveFaceVerifyActivity.this, "接口访问失败", Toast.LENGTH_LONG).show();
                    break;
                case Constant.APPLY_FAIL:
                    Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                    Toast.makeText(CardImageLiveFaceVerifyActivity.this, Applymes, Toast.LENGTH_LONG).show();
                    break;
                case PICERROR:
                    showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                    refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
                    Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                    break;
                case ERROR01:
                    isInResolveIdCardNumberMode = false;
                    showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                    refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
                    Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                case GETBASEPHOTO_LOAD_OVER:
                    showNotificationDialog("保存失败！" + GetBasePhotoMessage, null);
                    break;
                case GETBASEPHOTO_LOAD_SUCESS:
                    deleteByFilePath(cardFrontPath);
                    returnSuccess();
                    showToast(GetBasePhotoMessage);
                    ToastUtils.show(CardImageLiveFaceVerifyActivity.this, GetBasePhotoMessage);
                    break;

            }

        }
    };


    private void registerFace(Bitmap bmp) {
        //本地人脸库初始化
        clearFaces();
        FaceServer.getInstance().init(this);
        ConfigUtil.setFtOrient(CardImageLiveFaceVerifyActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);

        bmp = ImageUtil.alignBitmapForNv21(bmp);
        if (bmp == null) {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CardImageLiveFaceVerifyActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
            builder.setTitle("提示");
            builder.setMessage("获取的图片为空,请点击刚刚拍摄的底片，可以重新采集!");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
                }
            });

            android.app.AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {

            int width = bmp.getWidth();
            int height = bmp.getHeight();
            //bitmap转NV21
            final byte[] nv21 = ImageUtil.bitmapToNv21(bmp, width, height);
            if (nv21 != null) {
                boolean success = FaceServer.getInstance().registenew(CardImageLiveFaceVerifyActivity.this, nv21, bmp.getWidth(), bmp.getHeight(),
                        "registered");
                if (success) {
                    Toast.makeText(this, "已获取比对人脸!", Toast.LENGTH_SHORT).show();

                    if (isOnlyFace.equals("1")) {
                        onVerifyButtonOnlyFace();
                    } else {
                        onVerifyButtonClicked();
                    }


                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CardImageLiveFaceVerifyActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                    builder.setTitle("提示");
                    builder.setMessage("未获取到图片信息,请点击刚刚拍摄的底片，可以重新采集!");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            refreshUI(IMAGE_SELECT_TYPE_FACE, "", null);
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


    public void clearFaces() {
        int faceNum = FaceServer.getInstance().getFaceNumber(this);
        if (faceNum == 0) {
//            Toast.makeText(this, com.arcsoft.arcfacedemo.R.string.no_face_need_to_delete, Toast.LENGTH_SHORT).show();
        } else {
            int deleteCount = FaceServer.getInstance().clearAllFaces(CardImageLiveFaceVerifyActivity.this);
//            Toast.makeText(HeadCollectActivity.this, deleteCount + " faces cleared!", Toast.LENGTH_SHORT).show();

        }
    }

    public static String getBase64String(String s) {
        if (StringUtils.isBlank(s)) {
            return "";
        } else {
            return com.jqsoft.livebody_verify_lib.util.Base64.encode(s);
        }
    }

    String GetBasePhotoMessage, GetBasePhotoErro;
    /**
     * 保存底片
     */
    Runnable SaveBasePhotoRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                SharedPreferences userSettings = getSharedPreferences("setting", 0);
                String finalArcSaveBitmapUrl = userSettings.getString("ArcSaveBitmapUrl", "");
                String methodName = "SaveRecognitionBasePhoto";
                String sLoginName = userSettings.getString("sLoginName", "");
                String sInputOrgCode = userSettings.getString("sInputOrgCode", "");
                String cardFrontBase64String = Base64Util.imageToBase64(cardFrontPath);
                String IdCardCompareResult = userSettings.getString("IdCardCompareResult", "");
                JSONObject data = new JSONObject();
                data.put("iCollectionType", getBase64String("2"));//类型(1:人脸识别 2: 人证核验 3:身份证) (Base64加密)
                data.put("iResultCode", getBase64String("1"));//识别结果编码(1 成功 0 失败) (Base64加密)
                data.put("sResultName", getBase64String("成功"));// 识别结果名称(1 成功 0 失败) (Base64加密)
                data.put("sCardNo", getBase64String(idNumber));//身份证号码 (Base64加密)
                data.put("sLoginName", getBase64String(sLoginName));//用户登录名 (Base64加密)
                data.put("sInputOrgCode", getBase64String(sInputOrgCode));//录入机构编码 (Base64加密)
                String faceBase64String = Base64Util.imageToBase64(facePath);
                data.put("dCoefficient", getBase64String(IdCardCompareResult));//对比度 (Base64加密)
                data.put("Photo_Base", faceBase64String);//本底照片(图片Base64值)
                data.put("Photo_Recognition", cardFrontBase64String);// (max)身份证照片(图片Base64值)
                data.put("sPersonID", getBase64String(sPersonID));//  健康档案唯一标识 (Base64加密)


                String content = WebServiceUtils.requestByParams(methodName,
                        data.toString(), finalArcSaveBitmapUrl);
                if (!TextUtils.isEmpty(content)) {
                    try {

                        JSONObject json = new JSONObject(content);
                        int isSuccess = json.getInt("sSuccess");
                        GetBasePhotoMessage = json.getString("sMessage");
                        if (isSuccess == 1) {
                            myhandler.sendEmptyMessage(GETBASEPHOTO_LOAD_SUCESS);
                        } else {
                            myhandler.sendEmptyMessage(GETBASEPHOTO_LOAD_OVER);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        GetBasePhotoErro = e.getMessage();
                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
//                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_ERROR);
                    }
                } else {
                    myhandler.sendEmptyMessage(Constant.MSG_LOAD_EMPTY);
                }
            } catch (Exception e) {
                e.printStackTrace();
                GetBasePhotoErro = e.getMessage();
                myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
            }

        }
    };


    private String sCardFeature = "";

    private void registerFace1(Bitmap bmp) {
        //本地人脸库初始化
        clearFaces();
        FaceServer.getInstance().init(this);
        ConfigUtil.setFtOrient(CardImageLiveFaceVerifyActivity.this, FaceEngine.ASF_OP_0_HIGHER_EXT);

        bmp = ImageUtil.alignBitmapForNv21(bmp);
        if (bmp == null) {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CardImageLiveFaceVerifyActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
            builder.setTitle("提示");
            builder.setMessage("获取的图片为空,请点击刚刚拍摄的底片，可以重新采集!");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
                }
            });

            android.app.AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {

            int width = bmp.getWidth();
            int height = bmp.getHeight();
            //bitmap转NV21
            final byte[] nv21 = ImageUtil.bitmapToNv21(bmp, width, height);
            if (nv21 != null) {
//                int code=FaceServer.getInstance().extractFaceFeature(this,nv21, bmp.getWidth(), bmp.getHeight());
//                if (code== ErrorInfo.MOK){
//                    Toast.makeText(this, "已获取比对人脸!", Toast.LENGTH_SHORT).show();
//                    verifyIdCardNumber(bitmap);
//                }else {
//
//                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CardImageLiveFaceVerifyActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
//                    builder.setTitle("提示");
//                    builder.setMessage("获取特征值失败:"+code+"请重新采集身份证!");
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
//                        }
//                    });
//
//                    android.app.AlertDialog dialog = builder.create();
//                    dialog.setCancelable(false);
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.show();
//                }


                boolean success = FaceServer.getInstance().register(CardImageLiveFaceVerifyActivity.this, nv21, bmp.getWidth(), bmp.getHeight(),
                        "registered");
                if (success) {
                    Toast.makeText(this, "已获取比对人脸!", Toast.LENGTH_SHORT).show();
                    verifyIdCardNumber(bitmap);

                } else {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CardImageLiveFaceVerifyActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                    builder.setTitle("提示");
                    builder.setMessage("身份证不清晰，请重新采集身份证!");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
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

    public void IntetActivity() {
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

    /**
     * 申请人工审核
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
                    }
                } else {
                    myhandler.sendEmptyMessage(Constant.APPLY_ERAR);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void getSuc(String res) {
        isInResolveIdCardNumberMode = false;
        Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);

        TencentResponseSuccessBean tencentResponseBean = new Gson().fromJson(res, TencentResponseSuccessBean.class);
        if (tencentResponseBean != null) { // 解析成功
            TencentResponseSuccessBean.ResponseBean bean = tencentResponseBean.getResponse();
            if (bean != null && bean.getError() == null) { // 识别成功
                String readAPIcard = bean.getIdNum();
                readAPIcard = Util.trimString(readAPIcard);
                readAPIcard = readAPIcard.replaceAll("x", "X");
                if (!TextUtils.isEmpty(readAPIcard)) {
                    if (!idNumber.equals(readAPIcard)) {
                        showNotificationDialog("选中的身份证与您刷卡的身份证不是同一人", null);
                        refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
                    }
                } else {
                    showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                    refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
                }
            } else {
                showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
            }
        } else {
            showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
            refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getSuc(SuccResult);
                    break;
                case 2:
                    isInResolveIdCardNumberMode = false;
                    showNotificationDialog("识别失败，请拍照清楚后重新识别", null);
                    refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, "", null);
                    Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
                    Looper.loop();
                    break;
            }
        }
    };


    @Override
    public void onBackListener(ReadIdCardBean bean) {
        ToastUtils.show(CardImageLiveFaceVerifyActivity.this,bean.getNum());

    }
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
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
                        int activeCode = faceEngine.active(CardImageLiveFaceVerifyActivity.this, Constants.APP_ID, Constants.SDK_KEY);
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
                            Toast.makeText(CardImageLiveFaceVerifyActivity.this, "活体引擎激活成功", Toast.LENGTH_SHORT).show();
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

    private DaggerApplication application;

    @Override
    public void onSaveFaceInfoSuccess(HttpResultNewBaseBean<String> bean) {

        com.jqsoft.nursing.util.Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
        Toast.makeText(CardImageLiveFaceVerifyActivity.this,"保存成功",Toast.LENGTH_LONG).show();

        application.setiFlag(1);
        finish();

    }

    @Override
    public void onSaveFaceInfoFailure(String message) {
        com.jqsoft.nursing.util.Util.hideGifProgressDialog(CardImageLiveFaceVerifyActivity.this);
        Toast.makeText(CardImageLiveFaceVerifyActivity.this,message,Toast.LENGTH_LONG).show();
    }

    private String provinceCode;
    private String cityCode;
    private String areaCode;
    private String countyCode;
    private String streetCode;
    private String villageCode;
    private SelectAddressPop pop;
    private void intSelectAdress(String AreaCode) {
        pop = new SelectAddressPop();
        villageCode = "";
        areaCode = "";
        streetCode = "";
        countyCode = "";
        cityCode = "";
        provinceCode = "";
        et_hujiadress.setText("");
        String AreaLevel = AreaDictionaryUtil.getAreaLevelFromAreaCode(AreaCode);
        DictionaryAreaData dictionaryAreaData = AreaDictionaryUtil.getAreaDataBeanFromAreaCodeAndAreaLevel(AreaCode, AreaLevel);
        assert dictionaryAreaData != null;
        intLevelCode(dictionaryAreaData, AreaLevel);
        sethousleAddressCode(AreaLevel, dictionaryAreaData);

    }


    public interface SelectAddresFinish {
        void finish(String provinceCode, String cityCode, String countyCode, String streetCode, String villageCode, String areaCode
        );
    }

    private void intLevelCode(DictionaryAreaData bean, String level) {
        if (bean != null) {
            String code = bean.getSsid();
            if (Constant.AREA_LEVEL_PROVINCE.equals(level)) {
                provinceCode = code;
            } else if (Constant.AREA_LEVEL_CITY.equals(level)) {
                cityCode = code;
            } else if (Constant.AREA_LEVEL_COUNTY.equals(level)) {
                countyCode = code;
            } else if (Constant.AREA_LEVEL_STREET.equals(level)) {
                streetCode = code;
            } else if (Constant.AREA_LEVEL_VILLAGE.equals(level)) {
                villageCode = code;
            } else if (Constant.AREA_LEVEL_ZU.equals(level)) {
                areaCode = code;
            }
        }

    }


    private void sethousleAddressCode(String level, DictionaryAreaData bean) {
        if (!TextUtils.isEmpty(level)) {
            int loopNumber = Integer.parseInt(level);
            int levelNumber = loopNumber;
            String SuperId = bean.getSuperId();
            DictionaryAreaData dictionaryAreaData;


            //向上查到顶
            for (int i = 0; i < loopNumber + 1; i++) {
                if (levelNumber > 1) {
                    levelNumber = levelNumber - 1;
                    dictionaryAreaData = AreaDictionaryUtil.getAreaDataBeanFromAreaCodeAndAreaLevel(SuperId, String.valueOf(levelNumber));
                    SuperId = dictionaryAreaData.getSuperId();
                    intLevelCode(dictionaryAreaData, String.valueOf(levelNumber));
                }

            }


        }

        sAddress="";

        listAreaName.clear();
        listAreaName= AddressManager.newInstance().getAddress(provinceCode, cityCode, countyCode, streetCode, villageCode, areaCode);
        for(int i=0;i<listAreaName.size();i++){
            sAddress+=listAreaName.get(i);
        }

        et_hujiadress.setText(sAddress);
        pop.setAddress(provinceCode, cityCode, countyCode, streetCode, villageCode, areaCode);

    }


    private List<IStringRepresentationAndValue> reorderRaceList(List<IStringRepresentationAndValue> list) {
        List<IStringRepresentationAndValue> result = new ArrayList<>();
        if (!ListUtils.isEmpty(list)) {
            int hanZuIndex = -1;
            for (int i = 0; i < list.size(); ++i) {
                IStringRepresentationAndValue v = list.get(i);
                String s = v.getStringRepresentation();
                if (HANZU_RACE_REPRESENTATION.equals(s)) {
                    hanZuIndex = i;
                    result.add(0, v);
                } else {
                    result.add(v);
                }
            }
//            if (hanZuIndex!=-1){
//                IStringRepresentationAndValue v = list.get(hanZuIndex);
//                list.remove(hanZuIndex);
//                list.add(0, v);
//            }
        }
        return result;
    }


    private int getRaceSpecificTypeIndex(String aRace) {
        for (int i = 0; i < realRaceList.size(); ++i) {
            NameValueBean nvb = (NameValueBean) realRaceList.get(i);
            String raceName = nvb.getName();
            if (aRace.equals(raceName)) {
                return i;
            }
        }
        return -1;
    }

    private void initRace(int index) {
        if (index >= 0 && index < realRaceList.size()) {
            selectedNationIndex = index;
            NameValueBean nvb = (NameValueBean) realRaceList.get(index);
            ssNationName = nvb.getName();
            ssNationCode = nvb.getValue();
            tv_nation.setText(ssNationName);
        }

    }
}
