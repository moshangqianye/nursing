package com.arcsoft.arcfacedemo.util;

import android.graphics.Bitmap;

/**
 * Created by Mars on 2018/10/24.
 */

public class BitmapUtil {
    static Bitmap bitmap;
    static Bitmap bitmapmian;
    static int cameraId;
    private static Boolean isSave;
    private static  String path;
    public static Boolean getSave() {
        return isSave;
    }

    public static void setSave(Boolean save) {
        isSave = save;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        BitmapUtil.path = path;
    }

    public static Bitmap getBitmapmian() {
        return bitmapmian;
    }

    public static void setBitmapmian(Bitmap bitmapmian) {
        BitmapUtil.bitmapmian = bitmapmian;
    }

    public static int getCameraId() {
        return cameraId;
    }

    public static void setCameraId(int cameraId) {
        BitmapUtil.cameraId = cameraId;
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static void setBitmap(Bitmap bitmap) {
        BitmapUtil.bitmap = bitmap;
    }


}
