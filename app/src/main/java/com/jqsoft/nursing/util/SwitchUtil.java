package com.jqsoft.nursing.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

//import com.jqsoft.livebody_verify_lib.activity.CardImageLiveFaceVerifyActivity;
//import com.jqsoft.livebody_verify_lib.activity.HeadCollectActivity;
import com.jqsoft.livebody_verify_lib.bean.Version;
import com.jqsoft.nursing.arcface.CardImageLiveFaceVerifyActivity;
import com.jqsoft.nursing.arcface.HeadCollectActivity;
import com.jqsoft.nursing.bean.nursing.HealthListBean;

import java.io.Serializable;

public class SwitchUtil {

    /**
     * 人脸识别
     *
     * @param context     上下文
     * @param cardNo      身份证号
     * @param   ，1代表是
     * @param requestCode 跳转请求码code
     */
    public static void gotoVerify(Context context, String cardNo,String personName ,int requestCode) {
        SharedPreferences userSettings =context.getSharedPreferences("setting", 0);
        String NoBrushAgeRange=   userSettings.getString("NoBrushAgeRange","0-18,65-2000");
        com.jqsoft.livebody_verify_lib.bean.Version.setBaseUrl("http://192.168.44.170:8801/");
        //        //判断是否使用公司还是腾讯的身份证识别     属性要是用set 后面直接就get  不要用类名加 .
        com.jqsoft.livebody_verify_lib.bean.Version.setsReadCard_Value("1");

//        //通过人脸照片和身份证照片比对来添加人脸档案
        com.jqsoft.livebody_verify_lib.bean.Version.setCreateFeatureUrl("create_feature");
//        //人脸比对
        com.jqsoft.livebody_verify_lib.bean.Version.setCompareUrl("face_compare");
        //推送活体验证成功后的居民信息给各个公卫系统的基地址
        com.jqsoft.livebody_verify_lib.bean.Version.setPushFaceDataBaseUrl("");
        //推送活体验证成功后的居民信息给各个公卫系统的具体地址
        Version.setPushFaceDataConcrete("face/faceToNet");
        //==================================
      //   UserLoginInfo info = UserLoginInfo.getInstances();
        Intent intent = new Intent(context, HeadCollectActivity.class);
        //身份证号码
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_NUMBER_KEY, cardNo);
        //是否是监护人，1代表是
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ISMOTHER_KEY,"1");
        //sPersonID
          intent.putExtra(CardImageLiveFaceVerifyActivity.SPERSON_ID,"");
        //组织机构编码
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ORG_CODE_KEY, "");
        //用户姓名
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_SUBMIT_USER_KEY, "");
        intent.putExtra(CardImageLiveFaceVerifyActivity.SUERSENAME,personName);
        FragmentActivity activity = (FragmentActivity) context;
        if (!activity.isDestroyed() && !activity.isFinishing()) {  // 防止Activity被销毁
            activity.startActivityForResult(intent, requestCode);
        }
    }
    /**
     * 人脸识别
     *
     * @param context     上下文
     * @param cardNo      身份证号
     * @param   ，1代表是
     * @param requestCode 跳转请求码code
     */
    public static void gotoVerifyNew(Context context, String cardNo,String personName ,int requestCode) {
        SharedPreferences userSettings =context.getSharedPreferences("setting", 0);
        String NoBrushAgeRange=   userSettings.getString("NoBrushAgeRange","0-18,65-2000");
        com.jqsoft.livebody_verify_lib.bean.Version.setBaseUrl("http://192.168.44.170:8801/");
        //        //判断是否使用公司还是腾讯的身份证识别     属性要是用set 后面直接就get  不要用类名加 .
        com.jqsoft.livebody_verify_lib.bean.Version.setsReadCard_Value("");

//        //通过人脸照片和身份证照片比对来添加人脸档案
        com.jqsoft.livebody_verify_lib.bean.Version.setCreateFeatureUrl("create_feature");
//        //人脸比对
        com.jqsoft.livebody_verify_lib.bean.Version.setCompareUrl("face_compare");
        //推送活体验证成功后的居民信息给各个公卫系统的基地址
        com.jqsoft.livebody_verify_lib.bean.Version.setPushFaceDataBaseUrl("");
        //推送活体验证成功后的居民信息给各个公卫系统的具体地址
        Version.setPushFaceDataConcrete("face/faceToNet");
        //==================================
        //   UserLoginInfo info = UserLoginInfo.getInstances();
        Intent intent = new Intent(context, CardImageLiveFaceVerifyActivity.class);
        //身份证号码
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_NUMBER_KEY, cardNo);
        //是否是监护人，1代表是
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ISMOTHER_KEY,"1");
        //sPersonID
        intent.putExtra(CardImageLiveFaceVerifyActivity.SPERSON_ID,"");
        //组织机构编码
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ORG_CODE_KEY, "");
        //用户姓名
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_SUBMIT_USER_KEY, "");
        intent.putExtra(CardImageLiveFaceVerifyActivity.SUERSENAME,personName);
        intent.putExtra("flag", "2");
        FragmentActivity activity = (FragmentActivity) context;
        if (!activity.isDestroyed() && !activity.isFinishing()) {  // 防止Activity被销毁
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 人脸识别
     *
     * @param context     上下文
     * @param cardNo      身份证号
     * @param   ，1代表是
     * @param requestCode 跳转请求码code
     */
    public static void gotoVerifyNew1(Context context, String cardNo, String personName , HealthListBean.RowsBean bean,int requestCode) {
        SharedPreferences userSettings =context.getSharedPreferences("setting", 0);
        String NoBrushAgeRange=   userSettings.getString("NoBrushAgeRange","0-18,65-2000");
        com.jqsoft.livebody_verify_lib.bean.Version.setBaseUrl("http://192.168.44.170:8801/");
        //        //判断是否使用公司还是腾讯的身份证识别     属性要是用set 后面直接就get  不要用类名加 .
        com.jqsoft.livebody_verify_lib.bean.Version.setsReadCard_Value("");

//        //通过人脸照片和身份证照片比对来添加人脸档案
        com.jqsoft.livebody_verify_lib.bean.Version.setCreateFeatureUrl("create_feature");
//        //人脸比对
        com.jqsoft.livebody_verify_lib.bean.Version.setCompareUrl("face_compare");
        //推送活体验证成功后的居民信息给各个公卫系统的基地址
        com.jqsoft.livebody_verify_lib.bean.Version.setPushFaceDataBaseUrl("");
        //推送活体验证成功后的居民信息给各个公卫系统的具体地址
        Version.setPushFaceDataConcrete("face/faceToNet");
        //==================================
        //   UserLoginInfo info = UserLoginInfo.getInstances();
        Intent intent = new Intent(context, CardImageLiveFaceVerifyActivity.class);
        //身份证号码
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_NUMBER_KEY, cardNo);
        //是否是监护人，1代表是
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ISMOTHER_KEY,"1");
        //sPersonID
        intent.putExtra(CardImageLiveFaceVerifyActivity.SPERSON_ID,"");
        //组织机构编码
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_ORG_CODE_KEY, "");
        //用户姓名
        intent.putExtra(CardImageLiveFaceVerifyActivity.PASSIN_ID_SUBMIT_USER_KEY, "");
        intent.putExtra("mpeopleBasebean", (Serializable)bean);
        intent.putExtra(CardImageLiveFaceVerifyActivity.SUERSENAME,personName);
        intent.putExtra("flag", "1");
        FragmentActivity activity = (FragmentActivity) context;
        if (!activity.isDestroyed() && !activity.isFinishing()) {  // 防止Activity被销毁
            activity.startActivityForResult(intent, requestCode);
        }
    }

}
