package com.arcsoft.arcfacedemo.bean;



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
        // 客户端版本验证


        commonInfoMap.put("ImageRecognitionWebService_URL", "Andrews/ImageRecognitionWebService.asmx");
        commonInfoMap.put("ImageRecognition_METHOD", "ImageRecognition");

    }

    ;

//    /**
//     * 取得交易全路径
//     *
//     * @param pathKey 要访问的URL标识
//     */
//    public static String getCommonPath(String pathKey) {
//        StringBuffer result = new StringBuffer();
//        if (!pathKey.contains("_METHOD")) {
//                // 生成环境
//                BphsConstants.WEB_ROOT = BphsConstants.HTTP_PROTOCOL
//                        + BphsConstants.HTTP_SERVER_IP + ":"
//                        + BphsConstants.HTTP_SERVER_PORT + "/"
//                        + BphsConstants.PROJECT_PATH + "/";
//
//
//            result.append(BphsConstants.WEB_ROOT);
//        }
//        result.append(commonInfoMap.get(pathKey));
//
//        return result.toString();
//    }

}
