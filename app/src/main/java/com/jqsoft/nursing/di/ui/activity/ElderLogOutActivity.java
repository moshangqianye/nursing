package com.jqsoft.nursing.di.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jqsoft.livebody_verify_lib.certificateCamera.CameraActivity;
import com.jqsoft.livebody_verify_lib.util.Base64Util;
import com.jqsoft.livebody_verify_lib.util.DeleteFileUtil;
import com.jqsoft.livebody_verify_lib.util.Util;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.nursing.HealthListAdapter;
import com.jqsoft.nursing.arcface.CardImageLiveFaceVerifyActivity;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.IStringRepresentationAndValue;
import com.jqsoft.nursing.bean.NameValueBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.nursing.HealthListBean;
import com.jqsoft.nursing.di.contract.ArcFaceListActivityContract;
import com.jqsoft.nursing.di.module.ArcFaceListActivityModule;
import com.jqsoft.nursing.di.presenter.ArcFaceListActivityPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleClickListener;
import com.jqsoft.nursing.util.CommentUtil;
import com.jqsoft.nursing.util.SwitchUtil;
import com.jqsoft.nursing.util.ToastUtil;
import com.jqsoft.nursing.util.UtilNew;
import com.jqsoft.nursing.util.Utils;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.jqsoft.nursing.utils3.util.PreferencesUtils;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.tencent.bugly.beta.Beta;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**

 * 老人注销页面
 */
public class ElderLogOutActivity extends AbstractActivity implements ArcFaceListActivityContract.View, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    private int maxSelectNum = 1;// 图片最大可选数量
    private boolean isShow = true;
    private int selectType = FunctionConfig.TYPE_IMAGE;
    private boolean enablePreview = true;
    private boolean isPreviewVideo = true;
    private boolean theme = false;
    private boolean selectImageType = false;
    private int maxB = 200;
    private int compressW = 0;
    private int compressH = 0;
    private boolean isCompress = true;
    private boolean isCheckNumMode = false;
    private int compressFlag = 2;// 1 系统自带压缩 2 luban压缩
    private int themeStyle;
    private int previewColor, completeColor, previewBottomBgColor, previewTopBgColor, bottomBgColor, checkedBoxDrawable;
    private boolean mode = false;// 启动相册模式
    private int selectMode = FunctionConfig.MODE_SINGLE;
    public static final int ERROR01 = 1111;
    public static final int PICSCCUSE = 101;
    public static final int PICERROR = 100;
    FunctionOptions options;

    String cardFrontPath = "", cardBackPath = "", facePath = "";
    int imageSelectType = IMAGE_SELECT_TYPE_CARD_FRONT;
    public static int IMAGE_SELECT_TYPE_CARD_FRONT = 1;
    public static int IMAGE_SELECT_TYPE_CARD_BACK = 2;
    public static int IMAGE_SELECT_TYPE_FACE = 3;

    @BindView(R.id.sms_interceptor_title_fragment)
    TextView tv_title;   // 标题

    @BindView(R.id.iv_card_front)
    ImageView ivCardFront;   // 标题

    @BindView(R.id.iv_card_back)
    ImageView ivCardBack;   // 标题

    @BindView(R.id.iv_face)
    ImageView ivFace;   // 标题
    private int TAKE_PHOTO = 100;
    private HealthListBean.RowsBean nursingBean;

    @BindView(R.id.et_elder_name)
    TextView et_elder_name;

    @BindView(R.id.tv_reason)
    TextView tv_reason;

    @BindView(R.id.tv_death)
    TextView tv_death;

    @BindView(R.id.btn_verify)
    Button btn_verify;

    @BindView(R.id.view7)
    View view7;

    @BindView(R.id.ll_chusheng)
    LinearLayout ll_chusheng;

    @BindView(R.id.et_idcard)
    TextView et_idcard;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Inject
    ArcFaceListActivityPresenter mPresenter;  // 健康列表Presenter



    @Override
    protected int getLayoutId() {
        return R.layout.activity_elder_logout_layout;
    }

    @Override
    protected void initData() {
        nursingBean = (HealthListBean.RowsBean) getDeliveredSerializableByKey("item");
    }

    //民族
    String ssNationName, ssNationCode;
    int selectedNationIndex = -1;
    List<NameValueBean> raceList = new ArrayList<>();
    List<IStringRepresentationAndValue> realRaceList = new ArrayList<>();
    String HANZU_RACE_REPRESENTATION = "死亡";

    @Override
    protected void initView() {

        et_elder_name.setText(nursingBean.getSName());
        et_idcard.setText(nursingBean.getSIdCard());
        tv_address.setText(nursingBean.getSAddress());


        application = (DaggerApplication) ElderLogOutActivity.this.getApplication();

        tv_title.setText("老人注销");


        ivCardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCardBackImage();
            }
        });

        ivCardFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCardFrontImage();
            }
        });
        ivFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCardFaceImage();
            }
        });


        tv_death.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
                String initial = getSignTimeString();
                Calendar c2=Calendar.getInstance();
                Utils.showDateNewDialogWithMaxDate(ElderLogOutActivity.this, initial, "a_party_fragment_sign_time", c2,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String s = com.jqsoft.nursing.util.Util.getCanonicalYearMonthDayString(year, monthOfYear + 1, dayOfMonth);
                        tv_death.setText(s);

                    }
                });
            }
        });

        setNation();
        //raceList = DataDictionaryUtil.getNameValueBeanListByCode("Nation");
        realRaceList = Arrays.asList(raceList.toArray(new IStringRepresentationAndValue[0]));
        realRaceList = reorderRaceList(realRaceList);
        selectedNationIndex = getRaceSpecificTypeIndex(HANZU_RACE_REPRESENTATION);
        initRace(selectedNationIndex);
        tv_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilNew.showSingleChoiceStringListMaterialDialogEx(ElderLogOutActivity.this, "请选择注销原因", null,
                        realRaceList, selectedNationIndex, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                initRace(which);
                                return false;
                            }
                        });
            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sReason =tv_reason.getText().toString().trim();
                if(sReason.equals("死亡")){
                    String sDeathTime =tv_death.getText().toString().trim();
                    if(TextUtils.isEmpty(sDeathTime)){
                        Toast.makeText(ElderLogOutActivity.this,"请选择死亡时间!",Toast.LENGTH_LONG).show();
                        return;
                    }else if(TextUtils.isEmpty(facePath)){
                        Toast.makeText(ElderLogOutActivity.this,"请选择注销材料!",Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        ElderLogout(nursingBean);
                    }

                }else {
                    if(TextUtils.isEmpty(facePath)){
                        Toast.makeText(ElderLogOutActivity.this,"请选择注销材料!",Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        ElderLogout(nursingBean);
                    }
                }





            }
        });
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClicked();
            }
        });
    }
    private void onBackClicked() {
        deleteByFilePath(facePath);

        finish();
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
            tv_reason.setText(ssNationName);

            if(ssNationName.equals("死亡")){

                ll_chusheng.setVisibility(View.VISIBLE);
                view7.setVisibility(View.VISIBLE);
            }else {
                ll_chusheng.setVisibility(View.GONE);
                view7.setVisibility(View.GONE);
            }

        }

    }
    public void setNation() {
        raceList.add(new NameValueBean("死亡", "0"));
        raceList.add(new NameValueBean("迁出", "2"));
        raceList.add(new NameValueBean("其他", "3"));


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


    private String getSignTimeString() {
        String s = com.jqsoft.nursing.util.Util.trimString(tv_death.getText().toString());
        return s;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.iv_nursing_title_menu:
//
//
//                break;
//            case R.id.tv_sure:
//
//                break;
            default:
                break;
        }
    }
    private DaggerApplication application;
    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    protected void initInject() {
        super.initInject();
        DaggerApplication.get(this)
                .getAppComponent()
                .addArcFacenewListActivity(new ArcFaceListActivityModule(this))
                .inject(this);

    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onLoadHealthListSuccess(HttpResultNewBaseBean<String> bean) {

    }




    /**
     * 获取列表失败回调
     *
     * @param message    失败提示信息
     * @param isLoadMore 是否上拉加载更多
     */
    @Override
    public void onLoadHealthListFail(String message, boolean isLoadMore) {

    }

    @Override
    public void onLoadHealthEndSuccess(HttpResultNewBaseBean<String> bean) {
        Util.hideGifProgressDialog(ElderLogOutActivity.this);
        Toast.makeText(ElderLogOutActivity.this,"注销成功",Toast.LENGTH_LONG).show();

        application.setiFlag(1);
        finish();
    }

    @Override
    public void onLoadHealtEndFail(String message) {
        Util.hideGifProgressDialog(ElderLogOutActivity.this);
        Toast.makeText(ElderLogOutActivity.this,message,Toast.LENGTH_LONG).show();
    }

    /**
     * 加载更多请求
     */
    @Override
    public void onLoadMoreRequested() {

    }

    private void selectCardBackImage() {
        imageSelectType = IMAGE_SELECT_TYPE_CARD_BACK;
        PictureConfig.getInstance().init(options).startOpenCamera(this);
    }

    private void selectCardFrontImage() {
        imageSelectType = IMAGE_SELECT_TYPE_CARD_FRONT;
        PictureConfig.getInstance().init(options).startOpenCamera(this);
    }
    private void selectCardFaceImage() {
        imageSelectType = IMAGE_SELECT_TYPE_FACE;
//        PictureConfig.getInstance().init(options).startOpenCamera(this);
        PictureConfig.getInstance().init(options).openPhoto(this, resultCallback);

    }
    private List<LocalMedia> selectMedia = new ArrayList<>();
    List<LocalMedia> selectMedianew = new ArrayList<>();
    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            Log.i("callBack_result", selectMedia.size() + "");
            LocalMedia media = resultList.get(0);
            if (media.isCompressed()) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                String path = media.getCompressPath();
            } else {
                // 原图地址
                String path = media.getPath();
            }
            if (selectMedia != null) {
//                adapternew.setList(selectMedia);
//                adapternew.notifyDataSetChanged();
//                LocalMedia localMedia = new LocalMedia();
//                localMedia = selectMedia.get(0);
                selectMedianew = selectMedia;



                    onSelectOneFaceMedia(selectMedianew.get(0));


            }
        }

        @Override
        public void onSelectSuccess(LocalMedia media) {
            selectMedia.add(media);

//            onSelectOneMedia(media);
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

        Bitmap bitmap = BitmapFactory.decodeFile(paths);

        refreshUI(IMAGE_SELECT_TYPE_CARD_BACK, paths, bitmap);



    }


    private void deleteByFilePath(String filePath) {
        ivCardFront.setImageResource(com.jqsoft.livebody_verify_lib.R.mipmap.cf_id_card_number);
        filePath = Util.trimString(filePath);
        DeleteFileUtil.delete(filePath);
    }


    private void refreshUI(int imageSelectType, String path, Bitmap bitmap) {
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (imageSelectType == IMAGE_SELECT_TYPE_CARD_FRONT) {
//            deleteByFilePath(cardFrontPath);
            cardFrontPath = path;
            if (bitmap != null) {
                ivCardFront.setImageBitmap(bitmap);
//                ivCardFront.setImageBitmap(WaterMarkBitmapUtil.createWaterMaskBitmap(this, bitmap, "两卡制专用"));
            } else {
                ivCardFront.setImageResource(com.jqsoft.livebody_verify_lib.R.mipmap.cf_id_card_number);
            }
        } else if (imageSelectType == IMAGE_SELECT_TYPE_CARD_BACK) {
//            deleteByFilePath(cardBackPath);
            cardBackPath = path;
            if (bitmap != null) {
                ivCardBack.setImageBitmap(bitmap);
            } else {
                ivCardBack.setImageResource(com.jqsoft.livebody_verify_lib.R.mipmap.cf_card_back);
            }
        } else if (imageSelectType == IMAGE_SELECT_TYPE_FACE) {
//            deleteByFilePath(cardBackPath);
            facePath = path;
            if (bitmap != null) {
                ivFace.setImageBitmap(bitmap);
            } else {
                ivFace.setImageResource(com.jqsoft.livebody_verify_lib.R.mipmap.cf_card_back);
            }
        }
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
                            onSelectOneFrontMedia(lm);
                        }

                    }

                } else if (imageSelectType == IMAGE_SELECT_TYPE_CARD_BACK) {
                    if (data != null) {
                        List<LocalMedia> mediaList = (List<LocalMedia>) data.getSerializableExtra(FunctionConfig.EXTRA_RESULT);
                        if (mediaList != null && mediaList.size() > 0) {
                            LocalMedia lm = mediaList.get(0);
                            onSelectOneBackMedia(lm);
                        }

                    }

                } else if (imageSelectType == IMAGE_SELECT_TYPE_FACE) {
                    if (data != null) {
                        List<LocalMedia> mediaList = (List<LocalMedia>) data.getSerializableExtra(FunctionConfig.EXTRA_RESULT);
                        if (mediaList != null && mediaList.size() > 0) {
                            LocalMedia lm = mediaList.get(0);
                            onSelectOneFaceMedia(lm);
                        }

                    }

                }


            }else if(requestCode == FunctionConfig.CAMERA_RESULT){

            }

        }






    }


    private void onSelectOneBackMedia(LocalMedia media) {
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

        Bitmap bitmap = BitmapFactory.decodeFile(paths);

        refreshUI(IMAGE_SELECT_TYPE_CARD_BACK, paths, bitmap);

    }
    private void onSelectOneFaceMedia(LocalMedia media) {
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

        Bitmap bitmap = BitmapFactory.decodeFile(paths);

        refreshUI(IMAGE_SELECT_TYPE_FACE, paths, bitmap);

    }

    private void onSelectOneFrontMedia(LocalMedia media) {
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

        Bitmap bitmap = BitmapFactory.decodeFile(paths);

        refreshUI(IMAGE_SELECT_TYPE_CARD_FRONT, paths, bitmap);

    }
    public void ElderLogout(HealthListBean.RowsBean bean){

        if (bean != null) {
            String faceBase64String = Base64Util.imageToBase64(facePath);
//            String cardfrontBase64String = Base64Util.imageToBase64(cardFrontPath);
//            String cardBackBase64String = Base64Util.imageToBase64(cardBackPath);
            String sToken= PreferencesUtils.getString(ElderLogOutActivity.this,"token");
            Map<String, String> params = new HashMap<>();
            params.put("Token", sToken);
            params.put("DeadFile", faceBase64String);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowTime = dateFormat.format(date);
            params.put("dCancellationTime", nowTime);
            params.put("dDeathTime", tv_death.getText().toString().trim());
            params.put("iState", ssNationCode);
            params.put("gKey", bean.getGKey());
            params.put("sName",  bean.getSName());
            params.put("sIdCard", bean.getSIdCard());

           String  s1= params.toString();
            mPresenter.getLoadEndList(params);
        }
    }
}
