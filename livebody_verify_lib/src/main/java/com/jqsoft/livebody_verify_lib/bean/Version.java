package com.jqsoft.livebody_verify_lib.bean;

import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018-08-09.
 */

public class Version {
    //只创建人脸，不经过人证比对
//    public static String sOnlyFace="1";
    //正式人脸匹配地址
    public static String BASE_URL = "http://220.179.65.153:8801/";
    public static String sReadCard_Value="";

//    public static String BASE_URL = "http://218.22.56.86:8801/";
//    public static String BASE_URL = "http://192.168.44.19:8801/";
//    public static String BASE_URL = "http://192.168.44.113:8801/";
    public static String CREATE_FEATURE_URL = "create_feature";

    //首次采集创建人脸，而不是人证
    public static String CREATE_FEATURE_ONLYFACE_URL = "direct_create_feature";

    public static String COMPARE_URL="face_compare";
    public static String VERIFICATION_URL="verification";
    public static String IMAGE_TAKEN_DIRECTORY_NAME ="images_taken";
    public static String PUSH_FACE_DATA_BASE_URL = "";
    public static String PUSH_FACE_DATA_CONCRETE = "face/faceToNet";

    public static String APP_KEY="jqsoft892155892138";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static String getCreateFeatureUrl() {
        return CREATE_FEATURE_URL;
    }

    public static void setCreateFeatureUrl(String createFeatureUrl) {
        CREATE_FEATURE_URL = createFeatureUrl;
    }

    public static String getCompareUrl() {
        return COMPARE_URL;
    }

    public static void setCompareUrl(String compareUrl) {
        COMPARE_URL = compareUrl;
    }

    public static String getPushFaceDataBaseUrl() {
        return PUSH_FACE_DATA_BASE_URL;
    }

    public static void setPushFaceDataBaseUrl(String pushFaceDataBaseUrl) {
        PUSH_FACE_DATA_BASE_URL = pushFaceDataBaseUrl;
    }

    public static String getPushFaceDataConcrete() {
        return PUSH_FACE_DATA_CONCRETE;
    }

    public static void setPushFaceDataConcrete(String pushFaceDataConcrete) {
        PUSH_FACE_DATA_CONCRETE = pushFaceDataConcrete;
    }

    public static String getAppKey() {
        return APP_KEY;
    }

    public static void setAppKey(String appKey) {
        APP_KEY = appKey;
    }


    public static String getsReadCard_Value() {
        return sReadCard_Value;
    }

    public static void setsReadCard_Value(String sReadCard_Value) {
        Version.sReadCard_Value = sReadCard_Value;
    }
}
