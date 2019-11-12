package com.jqsoft.nursing.util;

import android.util.Base64;

import com.jqsoft.nursing.base.Constants;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesEcbUtil {

    public static final String ALGORITHM_DES = "DES/ECB/PKCS5Padding";

    /**
     * 加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public static String encode(String key, String data)  {
        return encode(key, data.getBytes());
    }

    /**
     * 加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public static String encode(String key, byte[] data)  {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
// key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] bytes = cipher.doFinal(data);

            return Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (Exception e) {
//            throw new Exception(e);
            return Constants.EMPTY_STRING;
        }
    }

    /**
     * 解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static byte[] decode(String key, byte[] data) throws Exception {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
// key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取编码后的值
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String decodeValue(String key, String data) {
        byte[] datas;
        String value = null;
        try {
            if (System.getProperty("os.name") != null
                    && (System.getProperty("os.name").equalsIgnoreCase("sunos") || System
                    .getProperty("os.name").equalsIgnoreCase("linux"))) {
                datas = decode(key, Base64.decode(data, 3));
            } else {
                datas = decode(key, Base64.decode(data, 3));
            }

            value = new String(datas);
        } catch (Exception e) {
            value = "";
        }
        return value;
    }
}