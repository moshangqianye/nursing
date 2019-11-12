package com.jqsoft.livebody_verify_lib.youtuIdentify;

import android.text.TextUtils;
import android.util.Log;

import com.jqsoft.livebody_verify_lib.bean.Constant;
import com.jqsoft.livebody_verify_lib.bean.TencentRequestBean;
import com.jqsoft.livebody_verify_lib.util.TencentCloudSign;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by qwy on 2016/10/19.
 * 网络请求工具类
 */
public class TecentHttpUtil {
    public static void uploadIdCard(String bitmap, String card_type, final SimpleCallBack callback) {
        StringBuffer mySign = new StringBuffer("");
        YoutuSign.appSign(Constant.AppID, Constant.SecretID, Constant.SecretKey,
                System.currentTimeMillis() / 1000 + Constant.EXPIRED_SECONDS,
                Constant.QQNumber, mySign);
        RequestParams params = new RequestParams("http://api.youtu.qq.com/youtu/ocrapi/idcardocr");
        params.setAsJsonContent(true);
        params.addHeader("accept", "*/*");
        params.addHeader("Host", "api.youtu.qq.com");
        params.addHeader("user-agent", "youtu-java-sdk");
        params.addHeader("Authorization", mySign.toString());
        params.addHeader("Content-Type", "text/json");
        params.addParameter("card_type", Integer.valueOf(card_type));
        params.addBodyParameter("image", bitmap);
        params.addBodyParameter("app_id", Constant.AppID);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("onSuccess", result);
                callback.Succ(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("onError", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("onCancelled", cex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 腾讯云身份证读取
     * @param bitmap 图片Base64字符串
     * @param callBack 识别请求回调
     */
    public static void readIdCard(String bitmap, final SimpleCallBack callBack) {

        final String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        Log.d("TAG","timestamp:"+timestamp);
        TencentRequestBean requestBean = new TencentRequestBean();
        requestBean.setImageBase64(replaceBlank(bitmap));
        requestBean.setCardSide("FRONT");
        final String requestBody= requestBean.toString();
        new Thread(){
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(mediaType, requestBody);
                Request request = new Request.Builder()
                        .url("https://ocr.tencentcloudapi.com/")
                        .post(body)
                        .addHeader("host", "ocr.tencentcloudapi.com")
                        .addHeader("x-tc-action", "IDCardOCR")
                        .addHeader("x-tc-timestamp", timestamp)
                        .addHeader("x-tc-version", "2018-11-19")
                        .addHeader("x-tc-region", "ap-guangzhou")
                        .addHeader("authorization", TencentCloudSign.authorization(timestamp,requestBody))
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    Log.d("TAG", result);
                    if (!TextUtils.isEmpty(result)){
                        callBack.Succ(result);
                    }else {
                        callBack.error();
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    callBack.error();
                }
            }
        }.start();
    }


    public interface SimpleCallBack {

        void Succ(String result);

        void error();
    }

    /**
     * 去除字符串中空格制表符换行
     * @param src 字符串
     * @return 返回字符串
     */
    private static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }


}
