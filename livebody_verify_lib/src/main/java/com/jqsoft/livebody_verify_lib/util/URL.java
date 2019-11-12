package com.jqsoft.livebody_verify_lib.util;



import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有组拼的URL路径 包括公共服务的和交易服务的
 *
 * @author gujunqi
 *         date：2013-12-24
 */
public class URL {
    private static String tag = "BphsURL";
    public static final String ACTION_COMMON_PARAM_MOBILE_MAC = "@mobileMac";

    /**
     * 保存公共信息相关访问相对路径
     */
    public static Map<String, String> commonInfoMap = new HashMap<String, String>();

    static {
        commonInfoMap.put("SaveFaceRecognitionDay_URL", "Andrews/SaveFaceRecognitionDay.asmx");
        commonInfoMap.put("SaveFaceRecognitionDay_METHOD", "SaveRecognitionInfo");

    }

    ;

    /**
     * 取得交易全路径
     *
     * @param pathKey 要访问的URL标识
     */
    public static String getCommonPath(String pathKey,String ArcSaveBitmapUrl) {
        StringBuffer result = new StringBuffer();
        if (!pathKey.contains("_METHOD")) {


            result.append(ArcSaveBitmapUrl);
        }
        result.append(commonInfoMap.get(pathKey));

        return result.toString();
    }

}
