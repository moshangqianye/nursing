package com.jqsoft.nursing.base;


import android.graphics.Color;

import com.amap.api.maps.CoordinateConverter;

public class Constant {
    /**
     * 显示版本更新
     */

    public static final int EMPTY_INT = 245;
    public static final int SHOW_UPDATE = 500;
    public static final int SHOW_UPDATEWS = 600;

    public static final int SHOW_BANK_NOTICE = 100;
    public static String EMPTY_STRING = "";
    public static final int UPDATE_DOWNLOAD_COMPLETED = 8;
    public static final int MSG_LOAD_OVER = 1;
    public static final int UPDATAAREA = 99;
    public static final int MSG_LOAD_ERROR = 2;
    public static final  int MSG_LOAD_ZERO = 0;
    public static final  int RESULT_NULL = 02300;
    public static final  int MSG_LOAD_ORGPERSONZERO=585;
    public static final int MSG_sSuccess = 100;
    public static final int MSG_sMessage = 20;
    public static final int MSG_LOAD_CHILDREN_OVER = 1000;
    public static final int MSG_LOAD_CHILDREN_ERROR = 1001;
    public static final int MSG_NETWORK_ERROR = 1002;

    public static final int MSG_LOAD_Phone = 42;
    public static final int MSG_LOAD_Phone_ERROR = 52;
    public static final int MSG_LOAD_ERROR_null =23;
    public static final int MSG_LOAD_JIEXI= 32;
    public static final int MSG_LOAD_SCC = 8;
    public static final int MSG_LOAD_ZARO = 0;
    public static final int MSG_LOAD_EMPTY = 3;
    public static final int MSG_LOAD_EXC=0;
    public static final int MSG_LOAD_INIT = 4;
    public static final int MSG_LOAD_DISMISS = -1;
    public static final int UPDATE_NORMOL = 12;
    public static final  int BIRTHDAY = 15;
    public static final int GET_LOCATION = 13;
    public static final int INITDATA = 133;
    public static final int MSG_NO_COLLECT = 14;
    public static final int MSG_DISMISS_PROGRESSBAR=15;
    public static   float HORIZONTAL_BAR_CHART_MINIMUM_Y_AXIS_VALUE =50f;
    public static final int UPDATE_DOWNLOADING = 6;
    public static final int UPDATE_DOWNLOAD_ERROR = 7;
    public static final int UPDATE_DOWNLOAD_CANCELED = 9;
    public static final int WLANCONNECT = 10;
    public static final int LOADTHREAD = 11;
    public static final int IMAGEREVERT = 18;
    public static final int TIJIAO_OVER = 21;
    public static final int TIJIAO_ERROR = 22;
    public static final int TIJIAO_SCCUSE = 23;
    public static final int FARISR_VIDEO = 9090;
    public static final int BAOCUN_OVER = 25;
    public static final int BAOCUN_ERROR = 26;
    public static final  int PICERROR = 100;
    public static final  int LOADAREADATA = 10120;
    public static final  int ERROR01= 1111;
    public static final int PICSCCUSE =101;
    public static final int DIAPICSCCUSE = 10002;
    public static final int INTENT_THEARD = 27;
    public static final int ShowDialog = 29;
    public static final int DELETOK = 101;
    public static final int PAGE_SIZE = 8;
    public static final int JIAOYANCODE = 100;
    public static final int JIAOYANOVER = 101;
    public static final  int GETISREADCARD = 102;
    public static final  int SENDWEBQEQUEST = 103;
    public static float BAR_CHART_ONE_COLUMN_GROUP_SPACE=0.5f;
    public static float BAR_CHART_ONE_COLUMN_BAR_SPACE=0.0f;
    public static final String NOT_BIND = "1";// 未绑定
    public static final String HAS_BIND = "0";// 绑定
    public static   int CHART_Y_LABEL_COUNT = 5;
    public static final String DEVICE_BIND_URL = "DEVICE_BIND_URL";
    public static final String LOGIN_URL = "LOGIN_URL";
    public static String PERCENT_STRING = "%";
    public static String COMMA_STRING = ",";
    public static final String WEB_ROOT = "WEB_ROOT";
    public static final String CMNET_ROOT = "CMNET_ROOT";
    public static boolean IS_EXIT = true;
    public static final int ENENT_BW = 1012;
    public static final int ENENT_BUS_PIC = 10010;
    public static final int EVENTLOCATE = 1111001;
    public static final int ENENT_BUS_TIME = 1010;
    public static final int ENENT_BUS_INDIVIDUA = 100000;
    public static final int ENENT_BUS_MJTIME = 1011;
    public static final  int  WORKBENCH_ONLINE_SIGN = 1;
    public static final long INTRODUCTION_DISPLAY_DURATION = 4000;
    public static final int ENENT_BUS_POLICY = 1129;

    /**
     * 标记是否是activity点击返回按钮,跳转到的activity
     */
    public static final int GO_BACK = -111;
    public static final String AREA_LEVEL_PROVINCE = "1";
    public static final String AREA_LEVEL_CITY="2";
    public static final String AREA_LEVEL_COUNTY="3";
    public static final   String AREA_LEVEL_STREET="4";
    public static final  String AREA_LEVEL_VILLAGE="5";
    public static final  String AREA_LEVEL_ZU="6";
    public static final String ECG_DATA_DIR="/jqsoftGareaEcg/ecg";
    int EVENT_TYPE_ONLINE_SIGN_PER_FRAGMENT_INITIALIZE = 10101;
    public static int CHART_FIRST_COLOR = Color.parseColor("#3DA1FF");
    public static int CHART_SECOND_COLOR = Color.parseColor("#82CE26");
    public static int CHART_THIRD_COLOR = Color.parseColor("#2578D4");
    public static int CHART_FOURTH_COLOR = Color.parseColor("#FFAD2D");
    public static  int CHART_FIFTH_COLOR = Color.parseColor("#FA6868");
    public static   int[] CHART_COLOR_LIST = {CHART_FIRST_COLOR, CHART_SECOND_COLOR, CHART_THIRD_COLOR,
            CHART_FOURTH_COLOR, CHART_FIFTH_COLOR};
    public static final String ECG_FILE_NAME="ecg_data_file.dat";
    public static final String ECG_TEST_FILE_NAME_SHORT_ARRAY ="ecg_data_test_file_short_array.dat";
    public static final String ECG_TEST_FILE_NAME_BYTE_ARRAY ="ecg_data_test_file_byte_array.dat";
    public static   float TINY_NUMBER = 1e-5f;
    /**
     * 检查更新
     */
    public static final String UPDATE_NOW = "立即更新";
    public static final String UPDATE_DELAY = "以后再说";

    public static final String CONNECTED = "CONNECTED";

    public static final String UPDATE_STATUS_FORCE = "3";// 强制更新
    public static final String UPDATE_STATUS_RECOMMEND = "2";// 推荐更新
    public static final String UPDATE_STATUS_NO = "1";// 无更新

    /**
     * http client 超时时间
     */
    public static final int Http_Connection_Timeout = 30 * 1000;// 8000
    public static final int Http_Connection_SO_TIMEOUT = 32 * 1000;// 32000
    /**
     * 强制关闭等待层的时间120秒
     */
    public static final int CLOSE_WAIT_PANEL_MILLISECOND = 2*5000;// 32000   90 * 1000
    /**
     * 用于主界面关闭等待层...
     */
    public static final int closePanel = 5 * 1000;// 32000

    public static final String NO_NET = "无法链接互联网，请检查您的网络设置！";


    public static final String PRINT_HINT_BITMAP_NULL = "所要打印的图片为空";
    public static final String PRINT_HINT_PRINT_SUCCESS = "打印图片成功";
    public static final String PRINT_HINT_NOT_SUPPORT_PRINT = "本设备不支持打印";
    public static final String PRINT_HINT_HTML_DOCUMENT_EMPTY = "打印内容为空";

//    public static String QQNumber = "1141771375";// 腾讯优图开发者账号（QQ号）
//    public static String AppID = "10086430";
//    public static String SecretID = "AKIDCXu690wASYUEm8IEzra2Toz2tlbSulgP";
//    public static String SecretKey = "jIfqrphOsAGAzb1EJtMelgP0PehYDFCx";
//    public static int EXPIRED_SECONDS = 2592000;// 过期时间戳

    public static String QQNumber = "675204267";// 腾讯优图开发者账号（QQ号）
    public static String AppID = "10174925";
    public static String SecretID = "AKIDJRJvoUG72tVO5BjQDm5D7lBMJ1Tf6tTR";
    public static String SecretKey = "FuGRTFl1695iBUXMzNVR8a7OQFpBJDrM";
    public static int EXPIRED_SECONDS = 2592000;// 过期时间戳

//    public static String QQNumber = "1069339190";// 腾讯优图开发者账号（QQ号）
//    public static String AppID = "10127858";
//    public static String SecretID = "AKID6OVRBovbkhdR9ghCByvnU05Vnzpaofz9";
//    public static String SecretKey = "770mOq8PV6ayAB7x8EAvUq59D8rNn8IP";
//    public static int EXPIRED_SECONDS = 2592000;// 过期时间戳

    static  String HYPHEN_STRING = "-";

    public static float  CHART_VALUE_TEXT_SIZE=13;
    public static float BAR_CHART_ONE_COLUMN_BAR_WIDTH=0.5f;
    public static  int CHART_LABEL_STRING_MAX_COUNT = 8;
    public static String ELLIPSIS_STRING="..";
    public static float CHART_Y_SPACE_TOP_PERCENT = 20;
    public static  int BAR_NUMBER_PER_SCREEN_FOR_ONE_COLUMN = 16;
    public static float CHART_Y_AXIS_VALUE_FACTOR=1.4f;
    public static String NONSCIENTIFIC_NUMBER_FORMATTER_STRING = ",##0.00";
    public static String INT_NUMBER_FORMATTER_STRING="##";

    public static  int BAR_NUMBER_PER_SCREEN_SMALL = 12;


    public static int MAP_ZOOM_LEVEL = 14;
    public static int MAP_ZOOM_LEVEL_FOR_ONE_POINT = 14;
    public static int MAP_MARKER_JUMP_DURATION=1000;
    public static CoordinateConverter.CoordType SERVER_MAP_COORDINATE_TYPE=CoordinateConverter.CoordType.BAIDU;
    public static String MAP_AMBIENT_SEARCH_INITIAL_RADIUS = "1000";
//    public static int MAP_BOUND_SPACE=300;
    public static int MAP_BOUND_SPACE=10;

    public static String MAP_LOCATION_TYPE_TOTAL = "total";
    public static String MAP_LOCATION_TYPE_POINT = "point";

    public static  int RXBINDING_THROTTLE = 1;

    public static  int ANIMATION_DURATION=400;

    public static int AMAP_LOCATE_INTERVAL = 10000;
    public static float MIN_LONGITUDE = -180f;
    public static float MAX_LONGITUDE = 180f;
    public static float MIN_LATITUDE = -90f;
    public static float MAX_LATITUDE = 90f;

    public static  int LONGITUDE_LATITUDE_FRACTION_DIGITS_NUMBER=6;

    public static String HINT = "提示";
    public static String HINT_LONGITUDE_INVALID = "经度不合法";
    public static String HINT_LATITUDE_INVALID = "纬度不合法";
    public static String HINT_CONFIRM_TO_NAVIGATE = "确定导航到目的地吗?";

    public static int PHONE_NUMBER_MIN_LENGTH=7;
    public static int PHONE_NUMBER_MAX_LENGTH=12;
    public static int PHONE_NUMBER_MAX_LENGTHa=13;
    public  static int ID_CARD_NUMBER_MIN_LENGTH=15;
    public static int ID_CARD_NUMBER_MAX_LENGTH=18;

    public static final int NET_ERROR = 0;  // 网络请求失败标识
    public static final int NET_SUCCESS = 1;  // 网络请求成功标识
    public static final int MATERNAL_INFO_SAVE_NET_SUCCESS = 3;  // 孕产妇信息登记网络成功（成功不是保存成功是网络请求通过）
    public static final int MATERNAL_INFO_SAVE_NET_FAIL = 4;   // 孕产妇信息登记网络失败
    public static final int LAST_HEALTH_CHECK_NET_SUCCESS = 5; // 最近一次健康体检查询网络请求通过
    public static final int LAST_HEALTH_CHECK_NET_FAIL = 6;  // 最近一次健康体检查询网络请求不通过
    public static final int ORGANIZATION_LIST_NET_SUCCESS = 7;  // 获取机构列表成功状态
    public static final int ORGANIZATION_INFO_NET_SUCCESS = 8;  // 获取机构信息成功状态
    public static final int HEALTH_EDUCATION_LIST_NET_SUCCESS = 9;  // 健康教育活动统计列表成功状态
    public static final int HEALTH_SUPERVISION_LIST_NET_SUCCESS = 10;  // 卫生监督实施进度列表成功状态
    public static final int COMMENT_BARCHART_LIST_NET_SUCCESS = 11;  // 通用的柱状图列表获取成功状态

    public static final int PARSING_ERROR = 10001;   // 数据解析错误

    public static final String LOGIN_QQ_OPEN_ID = "qq_openid";  // QQ登录openid
    public static final String LOGIN_WX_OPEN_ID = "wx_openid";  // 微信登录openid

    public static final int LOGIN_BIND_SUCCESS = 1;  // 第三方登录绑定成功标识
    public static final int LOGIN_UNBIND_SUCCESS = 2;  // 第三方登录解绑成功标识
    public static final int LOGIN_BIND_FAIL = 0;  // 第三方登录绑定或解绑失败标识

    public static final int NET_REQUEST_SUCCESS = 1;  // 网络请求成功标识
    public static final int NET_REQUEST_FAIL = 0;  // 网络请求失败标识
    public static final int NET_REQUEST_SUCCESS_EMPTY = 2; // 网络请求成功但是没数据
}
