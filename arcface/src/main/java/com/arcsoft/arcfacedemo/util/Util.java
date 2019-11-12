package com.arcsoft.arcfacedemo.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created by Administrator on 2018-08-09.
 */

public class Util {
    // 去掉字符串首尾的空白符
    public static String trimString(String s) {
        if (s == null) {
            return "";
        } else {
            return s.trim();
        }
    }




    public static void setDialogWidthHeight(Activity activity, Dialog dialog, float widthPercent, float heightPercent) {
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * heightPercent); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * widthPercent); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);

    }

    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    public static String getExternalStoragePath(Context context, String subpath) {
        if (context == null) {
            return null;
        } else {
            subpath = trimString(subpath);
            try {
                boolean isSdCardExist = isSDCardEnable();
                if (isSdCardExist) {
                    File file = context.getExternalFilesDir("");
                    if (file == null) {
                        return null;
                    } else {
                        String suffix = (TextUtils.isEmpty(subpath)) ? "" : (File.separator + subpath);
                        String result = file.getAbsolutePath() + suffix;
//                        LogUtil.i("getExternalStoragePath:" + result);
                        File specificFile = new File(result);
                        if (!specificFile.exists()) {
                            boolean isSuccess = specificFile.mkdirs();
                            if (isSuccess) {
                                return result;
                            } else {
                                return null;
                            }
                        } else {
                            return result;
                        }
                    }
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static String getInnerStoragePath(Context context, String subpath) {
        if (context == null) {
            return null;
        } else {
            subpath = trimString(subpath);
            try {
                File file = context.getFilesDir();
                if (file == null) {
                    return null;
                } else {
                    String suffix = (TextUtils.isEmpty(subpath)) ? "" : (File.separator + subpath);
                    String result = file.getAbsolutePath() + suffix;
//                    LogUtil.i("getInnerStoragePath:" + result);
                    File specificFile = new File(result);
                    if (!specificFile.exists()) {
                        boolean isSuccess = specificFile.mkdirs();
                        if (isSuccess) {
                            return result;
                        } else {
                            return null;
                        }
                    } else {
                        return result;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public static Bitmap getBitmapFromPath(String path) {

        if (!new File(path).exists()) {
            System.err.println("getBitmapFromPath: file not exists");
            return null;
        }
        // Bitmap bitmap = Bitmap.createBitmap(1366, 768, Config.ARGB_8888);
        // Canvas canvas = new Canvas(bitmap);
        // Movie movie = Movie.decodeFile(path);
        // movie.draw(canvas, 0, 0);
        //
        // return bitmap;

        byte[] buf = new byte[1024 * 1024];// 1M
        Bitmap bitmap = null;

        try {

            FileInputStream fis = new FileInputStream(path);
            int len = fis.read(buf, 0, buf.length);
            bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
            if (bitmap == null) {
                System.out.println("len= " + len);
                System.err
                        .println("path: " + path + "  could not be decode!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return bitmap;
    }
    public static String getPreferExternalFolderPath(Context context, String subpath) {
        String result = getExternalStoragePath(context, subpath);
        if (!TextUtils.isEmpty(result)) {
            return result;
        } else {
            String innerPath = getInnerStoragePath(context, subpath);
            return result;
        }
    }





    public static boolean saveBitmapToFile(Bitmap bitmap, String filepath) {
        boolean success = false;
        try {
            File file = new File(filepath);//将要保存图片的路径
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static String getPhotoFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String result = sdf.format(new Date());
        return result;
    }
    public static String getPhotoFileNames() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public static long getTimeStamp() {
        long acsTime = System.currentTimeMillis();
        return acsTime;
    }





    /***
     * 清理所有缓存
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void clearAppData(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        manager.clearApplicationUserData();
    }


    /***
     * 清理所有缓存
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }


    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
