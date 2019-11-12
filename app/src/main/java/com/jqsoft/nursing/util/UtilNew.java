package com.jqsoft.nursing.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.maps.AMap;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.jakewharton.rxbinding.view.RxView;

import com.jqsoft.livebody_verify_lib.bean.Version;
import com.jqsoft.livebody_verify_lib.util.Md5Tool;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constant;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.IStringRepresentationAndValue;
import com.jqsoft.nursing.bean.grassroots_civil_administration.NameValueBean;
import com.jqsoft.nursing.utils2.RegexUtil;
import com.jqsoft.nursing.utils3.util.FileUtils;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.jqsoft.nursing.utils3.util.PreferencesUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.jqsoft.livebody_verify_lib.util.Util.getInnerPathWithSubDirectory;

/**
 * Created by Administrator on 2017-12-06.
 */

public class UtilNew {
    public static int JPUSH_NOTIFICATION_ID = 1;

    public static int getIntFromString(String s) {
        int i = 0;
        try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        return i;
    }

    public static int[] getYearMonthDayFromCanonicalString(String s) {
        s = Util.trimString(s);
        String[] sArray = s.split(Constants.HYPHEN_STRING, 3);
        if (sArray != null && sArray.length == 3) {
            int year = getIntFromString(sArray[0]);
            int month = getIntFromString(sArray[1]);
            int day = getIntFromString(sArray[2]);
            return new int[]{year, month, day};
        } else {
            return null;
        }
    }
    public static void setLoadMoreStatus(BaseQuickAdapterEx mAdapter, int pageSize, int listSize, boolean isSuccessful) {
        if (isSuccessful) {
            if (listSize < pageSize) {
//                mAdapter.setEnableLoadMore(false);
                mAdapter.loadMoreEnd(true);
            } else {
                mAdapter.setEnableLoadMore(true);
                mAdapter.loadMoreComplete();
            }
        } else {
            mAdapter.setEnableLoadMore(true);
            mAdapter.loadMoreFail();
        }
    }

    /**
     * 获取时间
     *
     * @param Resource
     * @return
     */
    public static String getDate(String Resource) {
        String reSut = "";
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
            reSut = f.format(f.parse(Resource.replace("/", "-")));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reSut;
    }
    public static void showRecyclerViewOrFailureView(SwipeRefreshLayout srl,View failureView,TextView tvFailureView,boolean success, boolean isListEmpty) {
        if (success) {
            if (isListEmpty) {
                srl.setVisibility(View.GONE);
                failureView.setVisibility(View.VISIBLE);
                tvFailureView.setText("暂无数据,点我刷新");
            } else {
                srl.setVisibility(View.VISIBLE);
                failureView.setVisibility(View.GONE);
            }
        } else {
            srl.setVisibility(View.GONE);
            failureView.setVisibility(View.VISIBLE);
            tvFailureView.setText("加载数据失败,点我重试");

        }
    }


    public static void showTimePickerDialog(Context context, String initialString, String tag, Calendar maxDate, TimePickerDialog.OnTimeSetListener callback) {
        Calendar now = Calendar.getInstance();
        int[] ymdArray = getYearMonthDayFromCanonicalString(initialString);
        int year = now.get(Calendar.YEAR);
//        int month = now.get(Calendar.MONTH) - 1;
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        if (ymdArray != null && ymdArray.length == 3) {
            year = ymdArray[0];
            month = ymdArray[1] - 1;
            day = ymdArray[2];
        }
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                callback,
                24,
                0,
                true
        );

        dpd.dismissOnPause(false);
//        dpd.showYearPickerFirst(false);
        dpd.setVersion(TimePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(context.getResources().getColor(R.color.colorTheme));
//        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(context.getResources().getString(R.string.please_select_date));
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.WEEK_OF_MONTH, -1);
        Calendar date3 = Calendar.getInstance();
        date3.add(Calendar.WEEK_OF_MONTH, 1);
        Calendar[] days = {date1, date2, date3};
//        dpd.setHighlightedDays(days);
//        dpd.setMaxDate(maxDate);
        dpd.show(((Activity) context).getFragmentManager(), tag);
//        dpd.show(getChildFragmentManager(), "Datepickerdialog1");
    }

    public static String getEditTextString(EditText editText) {
        String s = Constant.EMPTY_STRING;
        if (editText != null) {
            s = editText.getText().toString();
        }
        s = trimString(s);
        return s;
    }


    public static void showDateNewDialogWithMaxDate1(Context context, String initialString, String tag, Calendar maxDate, DatePickerDialog.OnDateSetListener callback) {
        Calendar now = Calendar.getInstance();
        int[] ymdArray = getYearMonthDayFromCanonicalString(initialString);
        int year = now.get(Calendar.YEAR);
//        int month = now.get(Calendar.MONTH) - 1;
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        if (ymdArray != null && ymdArray.length == 3) {
            year = ymdArray[0];
            month = ymdArray[1] - 1;
            day = ymdArray[2];
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                callback,
                year,
                month,
                day
        );

        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(context.getResources().getColor(R.color.colorTheme));
//        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(context.getResources().getString(R.string.please_select_date));
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.WEEK_OF_MONTH, -1);
        Calendar date3 = Calendar.getInstance();
        date3.add(Calendar.WEEK_OF_MONTH, 1);
        Calendar[] days = {date1, date2, date3};
        dpd.setHighlightedDays(days);
        dpd.setMaxDate(maxDate);
        dpd.show(((Activity) context).getFragmentManager(), tag);
//        dpd.show(getChildFragmentManager(), "Datepickerdialog1");
    }


    public static void showDateNewDialogWithNextYearDate(Context context, String initialString, String tag, Calendar maxDate, DatePickerDialog.OnDateSetListener callback) {
        Calendar now = Calendar.getInstance();
        int[] ymdArray = getYearMonthDayFromCanonicalString(initialString);
        int year = now.get(Calendar.YEAR);
//        int month = now.get(Calendar.MONTH) - 1;
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        if (ymdArray != null && ymdArray.length == 3) {
            year = ymdArray[0];
            month = ymdArray[1] - 1;
            day = ymdArray[2];
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                callback,
                year + 1,
                month,
                day
        );

        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(context.getResources().getColor(R.color.colorTheme));
//        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(context.getResources().getString(R.string.please_select_date));
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.WEEK_OF_MONTH, -1);
        Calendar date3 = Calendar.getInstance();
        date3.add(Calendar.WEEK_OF_MONTH, 1);
        Calendar[] days = {date1, date2, date3};
        dpd.setHighlightedDays(days);
//        dpd.setMaxDate(maxDate);
        dpd.show(((Activity) context).getFragmentManager(), tag);
//        dpd.show(getChildFragmentManager(), "Datepickerdialog1");
    }

    public static void showDateNewDialogWithMinDate(Context context, String initialString, String tag, Calendar maxDate, DatePickerDialog.OnDateSetListener callback) {
        Calendar now = Calendar.getInstance();
        int[] ymdArray = getYearMonthDayFromCanonicalString(initialString);
        int year = now.get(Calendar.YEAR);
//        int month = now.get(Calendar.MONTH) - 1;
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        if (ymdArray != null && ymdArray.length == 3) {
            year = ymdArray[0];
            month = ymdArray[1] - 1;
            day = ymdArray[2];
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                callback,
                year,
                month,
                day
        );
        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(context.getResources().getColor(R.color.colorTheme));
//        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(context.getResources().getString(R.string.please_select_date));
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.WEEK_OF_MONTH, -1);
        Calendar date3 = Calendar.getInstance();
        date3.add(Calendar.WEEK_OF_MONTH, 1);
        Calendar[] days = {date1, date2, date3};
        dpd.setHighlightedDays(days);
        if (maxDate != null) {
            dpd.setMinDate(maxDate);
        }

        dpd.setTitle("");
        dpd.show(((Activity) context).getFragmentManager(), tag);
    }

    public static void showDateNewDialogWithMaxDate(Context context, String initialString, String tag, Calendar maxDate, DatePickerDialog.OnDateSetListener callback) {
        Calendar now = Calendar.getInstance();
        int[] ymdArray = getYearMonthDayFromCanonicalString(initialString);
        int year = now.get(Calendar.YEAR);
//        int month = now.get(Calendar.MONTH) - 1;
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        if (ymdArray != null && ymdArray.length == 3) {
            year = ymdArray[0];
            month = ymdArray[1] - 1;
            day = ymdArray[2];
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                callback,
                year,
                month,
                day
        );
        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(context.getResources().getColor(R.color.colorTheme));
//        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(context.getResources().getString(R.string.please_select_date));
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.WEEK_OF_MONTH, -1);
        Calendar date3 = Calendar.getInstance();
        date3.add(Calendar.WEEK_OF_MONTH, 1);
        Calendar[] days = {date1, date2, date3};
        dpd.setHighlightedDays(days);
        if (maxDate != null) {
            dpd.setMaxDate(maxDate);
        }

        dpd.setTitle("");
        dpd.show(((Activity) context).getFragmentManager(), tag);
    }

    public static void showDateNewDialogWithMaxDate2(Context context, String initialString, String tag, Calendar maxDate, DatePickerDialog.OnDateSetListener callback) {
        Calendar now = Calendar.getInstance();
        int[] ymdArray = getYearMonthDayFromCanonicalString(initialString);
        int year = now.get(Calendar.YEAR);
//        int month = now.get(Calendar.MONTH) - 1;
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        if (ymdArray != null && ymdArray.length == 3) {
            year = ymdArray[0];
            month = ymdArray[1] - 1;
            day = ymdArray[2];
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                callback,
                year,
                month,
                day
        );

        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(context.getResources().getColor(R.color.colorTheme));
//        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(context.getResources().getString(R.string.please_select_date));
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.WEEK_OF_MONTH, -1);
        Calendar date3 = Calendar.getInstance();
        date3.add(Calendar.WEEK_OF_MONTH, 1);
        Calendar[] days = {date1, date2, date3};
        dpd.setHighlightedDays(days);
        dpd.setMaxDate(maxDate);
        dpd.show(((Activity) context).getFragmentManager(), tag);
//        dpd.show(getChildFragmentManager(), "Datepickerdialog1");
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

    public static float getFloatFromString(String s) {
        float f = 0f;
        try {
            f = Float.parseFloat(s);
        } catch (Exception e) {
            e.printStackTrace();
            f = 0f;
        }
        return f;
    }

    public static String getFileNameFromDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss_SSS");
        Date date = new Date();
        String name = sdf.format(date);
        return name;
    }

    public static String checkString(String s) {
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }

    public static String checkdecimal(String s) {
        if (s == "") {
//            return "-" + 999;
            return s;
        } else {
            return s;
        }
    }

    public static String trimString(String s) {
        s = checkString(s);
        return s.trim();
    }

    public static String notNullEntity(String s) {
        if (s.equals("null") || s.isEmpty()) {
            return "";
        } else {
            return s;
        }

    }

    public static String getDBnamePath(Context context) {
        String DB_NAME;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            DB_NAME = "/sdcard/yishu/usingservice.db";
        } else {
            DB_NAME = context.getFilesDir().getPath() + "/yishu/usingservice.db";
        }

        return DB_NAME;
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


    public static void hideGifProgressDialog(final Context context) {

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

    public static String getCurrentYearMonthDayString() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        String s = getCanonicalYearMonthDayString(year, month, day);
        return s;
    }

    public static String getCurrentYearMonthDayStringthreee() {

        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, 3);  //设置为前3月
        dBefore = calendar.getTime();   //得到前3月的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd "); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
        return defaultStartDate;
    }

    public static String getCurrentYearMonthDayStringtfi(int i) {

        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, i);  //设置为前3月
        dBefore = calendar.getTime();   //得到前3月的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd "); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
        return defaultStartDate;
    }

    public static String getCurrentYearMonthDayStringone() {

        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, 1);  //设置为前3月
        dBefore = calendar.getTime();   //得到前3月的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd "); //设置时间格式
        String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
        return defaultStartDate;
    }

    public static String getCurrentYearMonthDataStringshisi() {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currdate = format.format(d);
        System.out.println("现在的日期是：" + currdate);

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, 14);// num为增加的天数，可以改变的
        d = ca.getTime();
        String enddate = format.format(d);
        return enddate;
    }

    public static String getCurrentYearString() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    static String ZERO_STRING = "0";
    static String HYPHEN_STRING = "-";

    public static String getCanonicalYearMonthDayString(int year, int month, int day) {
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append(HYPHEN_STRING);
        if (month < 10) {
            sb.append(ZERO_STRING);
        }
        sb.append(month);
        sb.append(HYPHEN_STRING);
        if (day < 10) {
            sb.append(ZERO_STRING);
        }
        sb.append(day);
        return sb.toString();
    }

    public static String getCanonicalYearString(int year) {
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append(HYPHEN_STRING);

        return sb.toString();
    }

    public static String getCanonicalYearMonthString(int year, int month) {
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append(HYPHEN_STRING);
        if (month < 10) {
            sb.append(ZERO_STRING);
        }
        sb.append(month);
//        sb.append(HYPHEN_STRING);
//        if (day < 10) {
//            sb.append(ZERO_STRING);
//        }
//        sb.append(day);
        return sb.toString();
    }

    /**
     * uri转path
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
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

    public static Bitmap File2BitmapUpload(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
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
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
//	return bitmap;
    }

    public static Bitmap compressImage(Bitmap image) {
        if (image != null) {
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
        } else {
            return null;

        }
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

    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public static void makeApplicationShowInLockedMode(Activity activity, boolean show) {
        if (activity != null) {
            int flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
            if (show) {
                activity.getWindow().addFlags(flags);
            } else {
                activity.getWindow().clearFlags(flags);
            }
        }
    }



    public static void delete(String path, Context context) {
        File file = null;
        if (!TextUtils.isEmpty(path)) {
            file = new File(path);
            if (file.exists())
                file.delete();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        }

    }

    public static void setChartDataLargeValueFormatter(ChartData chartData) {
        if (chartData != null) {
            chartData.setValueFormatter(new LargeValueFormatter());
        }
    }

    public static <T> void reverseList(List<T> list) {
        if (!ListUtils.isEmpty(list)) {
            Collections.reverse(list);
        }

    }

    public static <T> void processStatisticsChartList(List<T> list) {
        reverseList(list);
    }

    public static <T> List<T> getProcessedStatisticsChartTitleList(List<T> list) {
        List<T> result = getCopyList(list);
        reverseList(result);
        return result;
    }

    public static <T> List<T> getCopyList(List<T> list) {
        List<T> result = new ArrayList<>();
        if (!ListUtils.isEmpty(list)) {
            result.addAll(list);
            return result;
        } else {
            return result;
        }
    }

    public static String getChartLabel(String s) {
        s = trimString(s);
        if (s.length() <= Constant.CHART_LABEL_STRING_MAX_COUNT) {
            return s;
        } else {
            return s.substring(0, 5) + Constant.ELLIPSIS_STRING;
        }
    }

    public static void setYAxisLargeValueFormat(YAxis yAxis, YAxis yAxis2) {
        setYAxisLargeValueFormat(yAxis);
        setYAxisLargeValueFormat(yAxis2);
    }

    public static void setYAxisSpaceTop(YAxis yAxis, YAxis yAxis2) {
        setYAxisSpaceTop(yAxis);
        setYAxisSpaceTop(yAxis2);
    }

    public static void setYAxisLargeValueFormat(YAxis yAxis) {
        if (yAxis != null) {
            yAxis.setLabelCount(Constant.CHART_Y_LABEL_COUNT, false);
            yAxis.setValueFormatter(new LargeValueFormatter());
        }
    }

    public static void setYAxisSpaceTop(YAxis yAxis) {
        if (yAxis != null) {
            yAxis.setSpaceTop(Constant.CHART_Y_SPACE_TOP_PERCENT);
        }
    }

    public static void setHorizontalBarChartInversionPositionAxisMinimum(HorizontalBarChart hbcChart) {
        if (hbcChart != null) {
            setHorizontalBarChartInversionPositionSpecificAxisMinimum(hbcChart.getAxisLeft());
            setHorizontalBarChartInversionPositionSpecificAxisMinimum(hbcChart.getAxisRight());
        }
    }

    public static void setHorizontalBarChartInversionPositionSpecificAxisMinimum(YAxis yAxis) {
        if (yAxis != null) {
            float axisMax = yAxis.getAxisMaximum();
            float ultimateMax = Math.max(axisMax, Constant.HORIZONTAL_BAR_CHART_MINIMUM_Y_AXIS_VALUE);

            yAxis.setAxisMaximum(ultimateMax);
        }
    }

    public static float getMax(List<Float> floatList) {
        float sum = 0f;
        if (!ListUtils.isEmpty(floatList)) {
            sum = Collections.max(floatList);
        }
        return sum;
    }

    public static void resetHorizontalBarChartState(BarChart hbcChart) {
        if (hbcChart != null) {
            hbcChart.clear();
            hbcChart.getAxisLeft().resetAxisMaximum();
            hbcChart.getAxisRight().resetAxisMaximum();
        }

    }

    public static float getFloatFromPercentString(String s) {
        s = trimString(s);
        s = s.replaceAll(Constant.PERCENT_STRING, Constant.EMPTY_STRING);
        s = s.replaceAll(Constant.COMMA_STRING, Constant.EMPTY_STRING);
        float result = getFloatFromString(s);
        return result;
    }

    public static float getSum(List<Float> floatList) {
        float sum = 0f;
        if (!ListUtils.isEmpty(floatList)) {
            for (int i = 0; i < floatList.size(); ++i) {
                float f = floatList.get(i);
                sum += f;
            }
        }
        return sum;
    }

    public static boolean isNearZero(float f) {
        return Math.abs(f) < Constant.TINY_NUMBER;
    }

    public static float getPercent(float item, float sum) {
        if (isNearZero(sum)) {
            return 0f;
        }
        float percent = item / sum;
        return percent;
    }


    public static boolean isLongitudeValid(String longitude) {
        float longitudeFloat = getFloatFromString(longitude);
        if (longitudeFloat < Constant.MIN_LONGITUDE || longitudeFloat > Constant.MAX_LONGITUDE) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isLatitudeValid(String latitude) {
        float latitudeFloat = getFloatFromString(latitude);
        if (latitudeFloat < Constant.MIN_LATITUDE || latitudeFloat > Constant.MAX_LATITUDE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * marker点击时跳动一下
     */
    public static void jumpPoint(final Marker marker, AMap aMap, final LatLng latLng, final Runnable runnable) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        Point startPoint = proj.toScreenLocation(latLng);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = Constant.MAP_MARKER_JUMP_DURATION;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * latLng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * latLng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 10);
                } else {
                    marker.showInfoWindow();
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }
        });
    }

    public static double getDoubleValueFromString(String s) {
        s = Util.trimString(s);
        double result = 0;
        try {
            result = Double.parseDouble(s);
        } catch (Exception exception) {
            result = 0.00;
        }
        return result;
    }

    public static String getNumberWithSpecificFractionDigits(String numberString, int minFractionDigitsNumber, int maxFractionDigitsNumber) {
        double d = getDoubleValueFromString(numberString);
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(maxFractionDigitsNumber);
        nf.setMinimumFractionDigits(minFractionDigitsNumber);
        String s = nf.format(d);
        return s;
    }

    public static String getNumberWithSpecificFractionDigits(String numberString, int fractionDigitsNumber) {
        double d = getDoubleValueFromString(numberString);
        String s = String.format("%." + fractionDigitsNumber + "f", d);
        return s;
    }

    public static String getCanonicalLongitudeOrLatitude(String s) {
        String result = getNumberWithSpecificFractionDigits(s, Constant.LONGITUDE_LATITUDE_FRACTION_DIGITS_NUMBER, Constant.LONGITUDE_LATITUDE_FRACTION_DIGITS_NUMBER);
//        String result = getNumberWithSpecificFractionDigits(s, Constants.LONGITUDE_LATITUDE_FRACTION_DIGITS_NUMBER);
        return result;
    }

    public static void showToast(Context context, String msg) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }



    public static String getMetaDataFromManifest(Context context, String key) {
        String result = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            result = appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }


    public static String getMetaDataFromManifestnew(Context context, String key) {
        String result = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            result = appInfo.metaData.getString(key);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }


    public static String getMetaDataFromManifestInt(Context context, String key) {
        int result;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            result = appInfo.metaData.getInt(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            result = 00;
        }
        return result + "";
    }

    public static Boolean getMetaDataFromManifestBoolean(Context context, String key) {
        Boolean result;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            result = appInfo.metaData.getBoolean(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public static String getUnderlinedString(String s) {
        s = trimString(s);
        String result = "<u>" + s + "</u>";
        return result;
    }





    public static void addDividerToRecyclerView(Context context, RecyclerView rv, boolean isVertical) {
        int orientation = isVertical ? DividerItemDecoration.VERTICAL : DividerItemDecoration.HORIZONTAL;
        rv.addItemDecoration(new DividerItemDecoration(context, orientation));
    }

    /**
     * MaterialDialog   普通对话框
     *
     * @param context               设备上下文
     * @param title                 标题
     * @param content               内容
     * @param negativeText          取消按钮的文字
     * @param negtiveClickListener  取消按钮的点击方法
     * @param positiveText          确定按钮的文字
     * @param positiveClickListener 确定按钮的点击方法
     * @param cancelable            点击对话框以外是否可以使对话框消失
     */
    public static void showMaterialDialog(Context context, String title, String content,
                                          String negativeText, MaterialDialog.SingleButtonCallback negtiveClickListener,
                                          String positiveText, MaterialDialog.SingleButtonCallback positiveClickListener,
                                          boolean cancelable) {
        MaterialDialog.Builder dialog = new MaterialDialog.Builder(context);
        dialog.backgroundColorRes(R.color.white);
        if (title != null) {
            dialog.title(title);
//            dialog.title(title).titleColorRes(R.color.material_dialog_title_color);
        }
        if (content != null) {
            dialog.content(content);
//            dialog.content(content).contentColorRes(R.color.material_dialog_content_color);
        }
        if (negativeText != null) {
            dialog.negativeText(negativeText);
//            dialog.negativeText(negativeText)
//                    .negativeColorRes(R.color.material_dialog_cancel_color);
        }
        if (negtiveClickListener != null) {
            dialog.onNegative(negtiveClickListener);
        } else {
            dialog.onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.cancel();
                }
            });
        }
        if (positiveText != null) {
            dialog.positiveText(positiveText)
                    .positiveColorRes(R.color.colorPrimary);
        }
        if (positiveClickListener != null) {
            dialog.onPositive(positiveClickListener);
        } else {
            dialog.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            });
        }
        dialog.cancelable(cancelable)
                .show();
    }

    public static void showSingleChoiceStringListMaterialDialog(Context context, String title, String msg,
                                                                List<String> list, int defaultSelectedIndex,
                                                                MaterialDialog.ListCallbackSingleChoice callback) {
        ColorStateList csl = getColorStateList();
        new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .items(list)
                .choiceWidgetColor(csl)
                .autoDismiss(true)
                .negativeText(Constants.CANCEL)
                .itemsCallbackSingleChoice(defaultSelectedIndex, callback)
                .show();
    }

    public static void showSingleChoiceStringListMaterialDialogEx(Context context, String title, String msg,
                                                                  List<IStringRepresentationAndValue> list, int defaultSelectedIndex,
                                                                  MaterialDialog.ListCallbackSingleChoice callback) {
        ColorStateList csl = getColorStateList();
        List<String> stringList = new ArrayList<>();
        if (list == null) {
            list = new ArrayList<>();
        }
        for (int i = 0; i < list.size(); ++i) {
            IStringRepresentationAndValue sv = list.get(i);
            String representation = sv.getStringRepresentation();
            stringList.add(representation);
        }
        new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .items(stringList)
                .choiceWidgetColor(csl)
                .autoDismiss(true)
                .negativeText(Constants.CANCEL)
                .itemsCallbackSingleChoice(defaultSelectedIndex, callback)
                .show();
    }

    public static ColorStateList getColorStateList() {
        int[] colors = new int[]{Color.YELLOW, Color.YELLOW, Color.RED, Color.YELLOW, Color.RED, Color.RED};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public static void saveWhetherEnablePushFlag(Context context, boolean whetherEnablePush) {
        PreferencesUtils.putBoolean(context, Constants.WHETHER_ENABLE_PUSH_KEY, whetherEnablePush);
    }

    public static boolean getWhetherEnablePushFlag(Context context) {
        boolean b = PreferencesUtils.getBoolean(context, Constants.WHETHER_ENABLE_PUSH_KEY, true);
        Log.i("chenxu", "whether enable push:" + b);
        return b;
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static boolean getAliasSetSuccess(Context context) {
        boolean b = PreferencesUtils.getBoolean(context, Constants.SET_ALIAS_SUCCESS_KEY, false);
        Log.i("chenxu", "is alias set success:" + b);
        return b;
    }

    public static void setAliasStatus(Context context, boolean isSuccess) {
        PreferencesUtils.putBoolean(context, Constants.SET_ALIAS_SUCCESS_KEY, isSuccess);
    }

    public static void gotoActivity(Context context, Class targetActivityClass) {
        gotoActivityWithBundle(context, targetActivityClass, null);
    }

    public static void gotoActivityWithBundle(Context context, Class targetActivityClass, Bundle bundle) {
        if (context == null || targetActivityClass == null) {
            return;
        } else {
            try {
                Intent intent = new Intent(context, targetActivityClass);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                context.startActivity(intent);
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    activity.overridePendingTransition(R.anim.right_in, R.anim.right_out);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getWorkbenchIndexFromIntent(Intent intent) {
        if (intent != null) {
            int result = intent.getIntExtra(Constants.WORKBENCH_ON_NEW_INTENT_INITIAL_INDEX_KEY, 0);
            return result;
        } else {
            return 0;
        }
    }

    public static boolean getWorkbenchPushNotificationExistFromIntent(Intent intent) {
        if (intent != null) {
            boolean result = intent.getBooleanExtra(Constants.WORKBENCH_PUSH_NOTIFICATION_EXIST_KEY, false);
            return result;
        } else {
            return false;
        }
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
                .equals(Environment.MEDIA_MOUNTED);
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



    public static void deletePic(String path, Context context) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getContentResolver();
        String where = MediaStore.Images.Media.DATA + "='" + path + "'";
//删除图片
        mContentResolver.delete(uri, where, null);
    }

    /**
     * 通过身份证号码获取出生日期、性别、年龄
     *
     * @param certificateNo
     * @return 返回的出生日期格式：1990-01-01   性别格式：F-女，M-男
     */
    public static Map<String, String> getBirAgeSex(String certificateNo) {
        String birthday = "";
        String age = "";
        String sexCode = "";

        int year = Calendar.getInstance().get(Calendar.YEAR);
        char[] number = certificateNo.toCharArray();
        boolean flag = true;
        if (number.length == 15) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == 18) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && certificateNo.length() == 15) {
            birthday = "19" + certificateNo.substring(6, 8) + "-"
                    + certificateNo.substring(8, 10) + "-"
                    + certificateNo.substring(10, 12);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
        } else if (flag && certificateNo.length() == 18) {
            birthday = certificateNo.substring(6, 10) + "-"
                    + certificateNo.substring(10, 12) + "-"
                    + certificateNo.substring(12, 14);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("birthday", birthday);
        map.put("age", age);
        map.put("sexCode", sexCode);

        return map;
    }

    public static String compareId(String idcard) {
        if (idcard.indexOf('x') != -1) { //判断是不是有X
            idcard = idcard.replace("x", "X"); //如果有X，就改成统一的大写X
        }
        return idcard;
    }

    public static void placeDialogAtBottom(Dialog dialog) {
        if (dialog != null) {
            Window window = dialog.getWindow();
//            WindowManager.LayoutParams lp = window.getAttributes();
            if (window != null) {
                window.setGravity(Gravity.BOTTOM);
            }
        }
    }

    public static void placeDialogCenter(Dialog dialog) {
        if (dialog != null) {
            Window window = dialog.getWindow();
//            WindowManager.LayoutParams lp = window.getAttributes();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
            }
        }
    }

    public static void setDialogWidth(Activity activity, Dialog dialog, float widthPercent, float heightPercent) {
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * heightPercent); //
        p.width = (int) (d.getWidth() * widthPercent);
        dialogWindow.setAttributes(p);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
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

    public static void setDialogWidthHeight(Dialog dialog, int width, int height) {
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (height); // 高度设置为屏幕的0.6
        p.width = (int) (width); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);

    }

    public static void setDialogFillWidth(Activity activity, Dialog dialog) {
        if (activity != null && dialog != null) {
            Window window = dialog.getWindow();
            WindowManager manager = activity.getWindowManager();
            Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高用
            if (window != null) {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = d.getWidth();
                window.setAttributes(lp);
            }
        }
    }

    public static Activity scanForActivity(Context cont) {
        if (cont == null) {
            return null;
        } else if (cont instanceof Activity) {
            return (Activity) cont;
        } else if (cont instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) cont).getBaseContext());
        }

        return null;
    }

    public static void setEditTextDecimalNumber(EditText editText) {
        if (editText != null) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
    }

    public static void setEditTextMaxLength(EditText editText, int maxLength) {
        if (editText != null && maxLength > 0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
    }

    public static String getDivideValue(String numeratorString, String denominatorString) {
        numeratorString = trimString(numeratorString);
        denominatorString = trimString(denominatorString);
        boolean isDenominatorInteger = isStringInteger(denominatorString);
        if (isDenominatorInteger) {
            int denominator = getIntFromString(denominatorString);
            if (denominator == 0) {
                return "";
            }

        }
        double numratorDouble = getDoubleValueFromString(numeratorString);
        double denominatorDouble = getDoubleValueFromString(denominatorString);
        String resultString = "";
        try {
            double result = numratorDouble / denominatorDouble;
            result *= 100;
            resultString = getNumberWithSpecificFractionDigits(String.valueOf(result), 0, 2);
//            resultString=String.valueOf(result);
        } catch (Exception e) {
            e.printStackTrace();
            resultString = "";
        }
        return resultString;
    }


    public static boolean isStringInteger(String s) {
        s = trimString(s);
        boolean result = s.matches("\\d*");
        return result;
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1;
        Date dt2;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
            if (dt1.getTime() > dt2.getTime()) {
                isBigger = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBigger;
    }


    public static void showDateNewDialog(Context context, String initialString, String tag, DatePickerDialog.OnDateSetListener callback) {
        Calendar now = Calendar.getInstance();
        int[] ymdArray = getYearMonthDayFromCanonicalString(initialString);
        int year = now.get(Calendar.YEAR);
//        int month = now.get(Calendar.MONTH) - 1;
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        if (ymdArray != null && ymdArray.length == 3) {
            year = ymdArray[0];
            month = ymdArray[1] - 1;
            day = ymdArray[2];
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                callback,
                year,
                month,
                day
        );
        dpd.dismissOnPause(false);
        dpd.showYearPickerFirst(false);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(context.getResources().getColor(R.color.colorTheme));
//        dpd.setAccentColor(Color.parseColor("#9C27B0"));
        dpd.setTitle(context.getResources().getString(R.string.please_select_date));
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.WEEK_OF_MONTH, -1);
        Calendar date3 = Calendar.getInstance();
        date3.add(Calendar.WEEK_OF_MONTH, 1);
        Calendar[] days = {date1, date2, date3};
        dpd.setHighlightedDays(days);
        dpd.show(((Activity) context).getFragmentManager(), tag);
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


    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
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

    public static long getTimeStamp() {
        long acsTime = System.currentTimeMillis();
        return acsTime;
    }

    public static String getProcessedToken(long timestamp) {
        String raw = getOriginalToken(timestamp);
        String result = Md5Tool.hashKey(raw);
        return result;
    }


    public static String getOriginalToken(long timestamp) {
//        long timestamp = getTimeStamp();
        String result = Version.APP_KEY + timestamp;
        return result;
    }
    public static void showNotificationDialogTitle(Context context, String msg, DialogInterface.OnClickListener positiveListener) {
        try {

            new AlertDialog.Builder(context)
                    .setTitle("个人档案不合格原因")
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("确定", positiveListener)
                    .show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void showNotificationDialog(Context context, String msg, DialogInterface.OnClickListener positiveListener) {
        try {

            new AlertDialog.Builder(context)
                    .setTitle("")
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("确定", positiveListener)
                    .show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void showNotificationDaDialog(Context context, String msg, DialogInterface.OnClickListener positiveListener,DialogInterface.OnClickListener onCancelListener ) {
        try {

            new AlertDialog.Builder(context)
                    .setTitle("")
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("确定", positiveListener)
                    .setNegativeButton("取消",onCancelListener)
                    .show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String isEmty(String str) {
        if (str == null || TextUtils.isEmpty(str)) {
            return "";
        } else {
            return str;
        }

    }

    static List<NameValueBean> raceList = new ArrayList<>();

    public static List<NameValueBean> getNation() {
        raceList.add(new NameValueBean("汉族", "1"));
        raceList.add(new NameValueBean("蒙古族", "02"));
        raceList.add(new NameValueBean("回族", "03"));
        raceList.add(new NameValueBean("藏族", "04"));
        raceList.add(new NameValueBean("维吾尔族", "05"));
        raceList.add(new NameValueBean("苗族", "06"));
        raceList.add(new NameValueBean("彝族", "07"));
        raceList.add(new NameValueBean("壮族", "08"));
        raceList.add(new NameValueBean("布依族", "09"));
        raceList.add(new NameValueBean("朝鲜族", "10"));
        raceList.add(new NameValueBean("满族", "11"));
        raceList.add(new NameValueBean("侗族", "12"));
        raceList.add(new NameValueBean("瑶族", "13"));
        raceList.add(new NameValueBean("白族", "14"));
        raceList.add(new NameValueBean("土家族", "15"));
        raceList.add(new NameValueBean("哈尼族", "16"));
        raceList.add(new NameValueBean("哈萨克族", "17"));
        raceList.add(new NameValueBean("傣族", "18"));
        raceList.add(new NameValueBean("黎族", "19"));
        raceList.add(new NameValueBean("傈僳族", "20"));
        raceList.add(new NameValueBean("佤族", "21"));
        raceList.add(new NameValueBean("畲族", "22"));
        raceList.add(new NameValueBean("高山族", "23"));
        raceList.add(new NameValueBean("拉祜族", "24"));
        raceList.add(new NameValueBean("水族", "25"));
        raceList.add(new NameValueBean("东乡族", "26"));
        raceList.add(new NameValueBean("纳西族", "27"));
        raceList.add(new NameValueBean("景颇族", "28"));
        raceList.add(new NameValueBean("柯尔克孜族", "29"));
        raceList.add(new NameValueBean("土族", "30"));
        raceList.add(new NameValueBean("达斡尔族", "31"));
        raceList.add(new NameValueBean("仫佬族", "32"));
        raceList.add(new NameValueBean("羌族", "33"));
        raceList.add(new NameValueBean("布朗族", "34"));
        raceList.add(new NameValueBean("撒拉族", "35"));
        raceList.add(new NameValueBean("毛南族", "36"));
        raceList.add(new NameValueBean("仡佬族", "37"));
        raceList.add(new NameValueBean("锡伯族", "38"));
        raceList.add(new NameValueBean("阿昌族", "39"));
        raceList.add(new NameValueBean("普米族", "40"));
        raceList.add(new NameValueBean("塔吉克族", "41"));
        raceList.add(new NameValueBean("怒族", "42"));
        raceList.add(new NameValueBean("乌孜别克族", "43"));
        raceList.add(new NameValueBean("俄罗斯族", "44"));
        raceList.add(new NameValueBean("鄂温克族", "45"));
        raceList.add(new NameValueBean("崩龙族", "46"));
        raceList.add(new NameValueBean("保安族", "47"));
        raceList.add(new NameValueBean("裕固族", "48"));
        raceList.add(new NameValueBean("京族", "49"));
        raceList.add(new NameValueBean("塔塔尔族", "50"));
        raceList.add(new NameValueBean("独龙族", "51"));
        raceList.add(new NameValueBean("鄂伦春族", "52"));
        raceList.add(new NameValueBean("赫哲族", "53"));
        raceList.add(new NameValueBean("门巴族", "54"));
        raceList.add(new NameValueBean("珞巴族", "55"));
        raceList.add(new NameValueBean("基诺族", "56"));
        raceList.add(new NameValueBean("其他", "97"));
        raceList.add(new NameValueBean("外国血统中国籍人士", "99"));
        return raceList;

    }

    public static List<IStringRepresentationAndValue> reorderRaceList(List<IStringRepresentationAndValue> list, String HANZU_RACE_REPRESENTATION) {
        List<IStringRepresentationAndValue> result = new ArrayList<>();
        if (!ListUtils.isEmpty(list)) {
            int hanZuIndex = -1;
            for (int i = 0; i < list.size(); ++i) {
                IStringRepresentationAndValue v = list.get(i);
                String s = v.getStringRepresentation();
                if (HANZU_RACE_REPRESENTATION.equals(s)) {
                    hanZuIndex = i;
                    result.add(0, v);
                } else {
                    result.add(v);
                }
            }
//            if (hanZuIndex!=-1){
//                IStringRepresentationAndValue v = list.get(hanZuIndex);
//                list.remove(hanZuIndex);
//                list.add(0, v);
//            }
        }
        return result;
    }

    public static int getRaceSpecificTypeIndex(String aRace, List<IStringRepresentationAndValue> realRaceList) {
        for (int i = 0; i < realRaceList.size(); ++i) {
            NameValueBean nvb = (NameValueBean) realRaceList.get(i);
            String raceName = nvb.getName();
            if (aRace.equals(raceName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param alph
     * @return
     * @Title : filterAlphabet
     * @Type : FilterStr
     * @date : 2014年3月12日 下午7:28:54
     * @Description : 过滤出字母
     */
    public static String filterAlphabet(String alph) {
        alph = alph.replaceAll("m", "");
        return alph;
    }

    public static String removeCh(String str) {
        String reg = "[\u2E80-\u9FFF]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(str);
        String repickStr = mat.replaceAll("");
        return repickStr;
    }


    public static void onclicks(final EditText editText, final double minNum, final double maxNum, final TextView textView, final String tex) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    try {
                        double num = Double.parseDouble(s.toString());
                        if (num < minNum || num > maxNum) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(tex);
                        } else {
                            textView.setVisibility(View.GONE);

                        }
                    }catch (Exception e){
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(tex);
                    }

                }
            }
        });
    }

    public static void HeditorListener(final EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {   // 按下完成按钮，这里和上面imeOptions对应
                    String strheight = editText.getText().toString();
                    try {
                        double dht = Double.parseDouble(strheight);
                        DecimalFormat df = new DecimalFormat("0.0");
                        editText.setText(df.format(dht));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
                return false;
            }
        });
    }

    public static void WeditorListener(final EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {   // 按下完成按钮，这里和上面imeOptions对应
                    String strheight = editText.getText().toString();
                    try {
                        double dht = Double.parseDouble(strheight);
                        DecimalFormat df = new DecimalFormat("0.00");
                        editText.setText(df.format(dht));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
                return false;
            }
        });
    }



}
