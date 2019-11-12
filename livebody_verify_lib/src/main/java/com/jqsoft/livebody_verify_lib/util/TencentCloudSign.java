package com.jqsoft.livebody_verify_lib.util;


import android.util.Log;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author yedong
 * @date 2019/8/6
 * 腾讯云签名工具
 */
public class TencentCloudSign {

    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();
    private final static Charset UTF8 = Charset.forName("UTF-8"); // 格式
    private final static String SECRET_ID = "AKIDiEfy3toSb4LN6aq7IH2atY0DWgqmmvsZ"; // 腾讯云id
    private final static String SECRET_KEY = "husd8OZdS0AIBpDl11VjDRSEkJe7LK4X";  // 腾讯云唯一标识key

    private static String host = "ocr.tencentcloudapi.com";
    private static String service = "ocr"; // 服务
    private static String algorithm = "TC3-HMAC-SHA256";
    private static String signedHeaders = "content-type;host";
    private static String date;  // 时间
    private static String timestamp; // 时间戳
    private static String credentialScope;
    private static String payload;

    private static byte[] hmac256(byte[] key, String msg) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
            mac.init(secretKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return mac.doFinal(msg.getBytes(UTF8));
    }

    private static String sha256Hex(String s) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(s.getBytes(UTF8));
            return printHexBinary(d).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 拼接规范请求串
     *
     * @return 返回请求串
     */
    private static String canonicalRequest() {
        String httpRequestMethod = "POST";
        String canonicalUri = "/";
        String canonicalQueryString = "";
        String canonicalHeaders = "content-type:application/json; charset=utf-8\n" + "host:" + host + "\n";
        String hashedRequestPayload = sha256Hex(payload);
        String canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n"
                + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload;
        Log.d("TAG","canonicalRequest:"+canonicalRequest);
        return canonicalRequest;
    }

    /**
     * 拼接待签名字符串
     *
     * @return 返回字符串
     */
    private static String stringToSign() {
        String hashedCanonicalRequest = sha256Hex(canonicalRequest());
        String stringToSign = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
        Log.d("TAG","stringToSign:"+stringToSign);
        return stringToSign;
    }

    /**
     * 计算签名
     *
     * @return 返回字符串
     */
    private static String signature() {
        byte[] secretDate = hmac256(("TC3" + SECRET_KEY).getBytes(UTF8), date);
        byte[] secretService = hmac256(secretDate, service);
        byte[] secretSigning = hmac256(secretService, "tc3_request");
        String signature = printHexBinary(hmac256(secretSigning, stringToSign())).toLowerCase();
        Log.d("TAG","signature:"+signature);
        return signature;
    }

    /**
     * 拼接 Authorization
     *
     * @return 返回字符串
     */
    public static String authorization(String timestamp,String request) {
        payload = request;
        TencentCloudSign.timestamp = timestamp;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 注意时区，否则容易出错
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        date = sdf.format(new Date(Long.valueOf(timestamp + "000")));
        credentialScope = date + "/" + service + "/" + "tc3_request";
        String authorization = algorithm + " " + "Credential=" + SECRET_ID + "/" + credentialScope + ", "
                + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature();
        Log.d("TAG","authorization:"+authorization);
        return authorization;
    }

    /**
     * 字节数组转16进制字符串
     *
     * @param data 字节数组
     * @return 返回字符串
     */
    public static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        byte[] var3 = data;
        int var4 = data.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            r.append(hexCode[b >> 4 & 15]);
            r.append(hexCode[b & 15]);
        }

        return r.toString();
    }

}
