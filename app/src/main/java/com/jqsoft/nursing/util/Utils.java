package com.jqsoft.nursing.util;

import android.app.Activity;
import android.content.Context;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.util.Calendar;

//import com.amap.api.navi.model.AMapNaviPath;
//import com.amap.api.navi.model.AMapNaviStep;

public class Utils {
    private static DecimalFormat fnum = new DecimalFormat("##0.0");
    public static final int AVOID_CONGESTION = 4;  // 躲避拥堵
    public static final int AVOID_COST = 5;  // 避免收费
    public static final int AVOID_HIGHSPEED = 6; //不走高速
    public static final int PRIORITY_HIGHSPEED = 7; //高速优先

    public static final int START_ACTIVITY_REQUEST_CODE = 1;
    public static final int ACTIVITY_RESULT_CODE = 2;

    public static final String INTENT_NAME_AVOID_CONGESTION = "AVOID_CONGESTION";
    public static final String INTENT_NAME_AVOID_COST = "AVOID_COST";
    public static final String INTENT_NAME_AVOID_HIGHSPEED = "AVOID_HIGHSPEED";
    public static final String INTENT_NAME_PRIORITY_HIGHSPEED = "PRIORITY_HIGHSPEED";


    public static String getFriendlyTime(int s) {
        String timeDes = "";
        int h = s / 3600;
        if (h > 0) {
            timeDes += h + "小时";
        }
        int min = (int) (s % 3600) / 60;
        if (min > 0) {
            timeDes += min + "分";
        }
        return timeDes;
    }

    public static String getFriendlyDistance(int m) {
        if (m < 1000) {
            return m + "米";
        }
        float dis = m / 1000f;
        String disDes = fnum.format(dis) + "公里";
        return disDes;
    }

//    public static Spanned getRouteOverView(AMapNaviPath path) {
//        String routeOverView = "";
//        if (path == null) {
//            Html.fromHtml(routeOverView);
//        }
//
//        int cost = path.getTollCost();
//        if (cost > 0) {
//            routeOverView += "过路费约<font color=\"red\" >" + cost + "</font>元";
//        }
//        int trafficLightNumber = getTrafficNumber(path);
//        if (trafficLightNumber > 0) {
//            routeOverView += "红绿灯" + trafficLightNumber + "个";
//        }
//        return Html.fromHtml(routeOverView);
//    }
//
//    public static int getTrafficNumber(AMapNaviPath path) {
//        int trafficLightNumber = 0;
//        if (path == null) {
//            return trafficLightNumber;
//        }
//        List<AMapNaviStep> steps = path.getSteps();
//        for (AMapNaviStep step : steps) {
//            trafficLightNumber += step.getTrafficLightNumber();
//        }
//        return trafficLightNumber;
//    }


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
        dpd.setMaxDate(maxDate);
        dpd.show(((Activity) context).getFragmentManager(), tag);
//        dpd.show(getChildFragmentManager(), "Datepickerdialog1");
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


    public static int getIntFromString(String s) {
        s = Util.trimString(s);
        int result = 0;
        try {
            result = Integer.parseInt(s, 10);
        } catch (NumberFormatException exception) {
            result = 0;
        }
        return result;
    }
}