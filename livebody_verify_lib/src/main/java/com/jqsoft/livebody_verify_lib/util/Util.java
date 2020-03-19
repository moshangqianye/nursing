package com.jqsoft.livebody_verify_lib.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.jakewharton.rxbinding.view.RxView;
import com.jqsoft.livebody_verify_lib.bean.Constant;
import com.jqsoft.livebody_verify_lib.bean.Version;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.functions.Action1;
import rx.android.schedulers.AndroidSchedulers;
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
    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
    public static void hideGifProgressDialog(Context context) {
        try{
            final Context context1 = ((ContextWrapper) context).getBaseContext();
            if (context1 instanceof Activity) {
                if (!((Activity) context1).isFinishing() && !((Activity) context1).isDestroyed()){
                    ((Activity) context1).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LoadingUtil.getInstance(context1).hide();
                        }
                    });
                }

            } else {//if the Context used wasnt an Activity, then dismiss it too
                LoadingUtil.getInstance(context).hide();
            }
        }catch (Exception e){

        }
    }

    public static void showGifProgressDialog(Context context) {
        try {
            LoadingUtil.getInstance(context).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showGifProgressDialog(Context context, DialogInterface.OnDismissListener dismissListener) {
        try {
            LoadingUtil loadingUtil = LoadingUtil.getInstance(context);
            if (dismissListener != null) {
                loadingUtil.setOnDismissListener(dismissListener);
            }
            loadingUtil.show();
        } catch (Exception e) {
            e.printStackTrace();
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

    public static String getOriginalToken(long timestamp) {
//        long timestamp = getTimeStamp();
        String result = Version.APP_KEY + timestamp;
        return result;
    }

    public static String getProcessedToken(long timestamp) {
        String raw = getOriginalToken(timestamp);
        String result = Md5Tool.hashKey(raw);
        return result;
    }

    public static String getImageTakenPreferSDPath(Context context) {
        String s = Util.getPreferExternalCalculatedPathWithSubDirectory(context, Version.IMAGE_TAKEN_DIRECTORY_NAME);
        return s;

    }

    public static String getPreferExternalCalculatedPathWithSubDirectory(Context context, String subDirectory) {
        String sdPath = getExternalFilePathWithSubDirectory(context, subDirectory);
//        String sdPath = getSDPathWithSubDirectory(subDirectory);
        if (TextUtils.isEmpty(sdPath)) {
            String innerPath = getInnerPathWithSubDirectory(context, subDirectory);
            return innerPath;
        } else {
            return sdPath;
        }
    }

    public static String getExternalFilePathWithSubDirectory(Context context, String subDirectory) {
        if (context == null) {
            return null;
        }
        subDirectory = trimString(subDirectory);
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            String externalRoot = context.getExternalFilesDir(null).toString();
            externalRoot += File.separator + subDirectory;
            boolean createSuccess = FileUtils.makeDirs(externalRoot);
            if (createSuccess) {
                return externalRoot;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public static String getInnerPathWithSubDirectory(Context context, String subDirectory) {
        if (context == null) {
            return null;
        }
        subDirectory = trimString(subDirectory);
        String innerRoot = context.getFilesDir().toString();
        innerRoot += File.separator + subDirectory;
        boolean createSuccess = FileUtils.makeDirs(innerRoot);
        if (createSuccess) {
            return innerRoot;
        } else {
            return null;
        }
    }

    public static void setViewListener(View view, final Runnable runnable) {
        if (view != null && runnable != null) {
            RxView.clicks(view)
                    .throttleFirst(Constant.RXBINDING_THROTTLE, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            runnable.run();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
        }
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

    /**
     *
     * @Title : filterAlphabet
     * @Type : FilterStr
     * @date : 2014年3月12日 下午7:28:54
     * @Description : 过滤出字母
     * @param alph
     * @return
     */
    public static String filterAlphabet(String alph)
    {
        alph = alph.replaceAll("m", "");
        return alph;
    }
    public  static  String removeCh(String str){
        String reg = "[\u2E80-\u9FFF]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(str);
        String repickStr = mat.replaceAll("");
        return  repickStr;
    }

    /**
     * uri转path
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();

    }
    /**
     * 获取压缩后的图片
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        File file = new File(srcPath);
        if (bitmap == null) {
            //如果图片为null, 图片不完整则删除掉图片
            byte[] bytes = new byte[(int) file.length() + 1];
            try {
                FileInputStream inputStream = new FileInputStream(srcPath);
                inputStream.read(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            if (bitmap == null) {
                file.delete();
            }
        }


        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }
    public static Bitmap compressImage(Bitmap image) {
        if (image!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > 100) {  // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                if (options < 0) {
                    image.compress(Bitmap.CompressFormat.JPEG, 10, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                    break;
                } else {
                    image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                }

                options -= 10;// 每次都减少10
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
            return bitmap;
        }else{
            return null;

        }
    }


}
