package com.jqsoft.nursing.bean;

import android.content.Context;


import com.jqsoft.nursing.base.Constant;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.jqsoft.nursing.utils3.util.StringUtils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-05-30.
 */

public class AreaDictionaryUtil {
    public static String getLoginPersonAreaCode(Context context){
        if (context==null){
            return Constant.EMPTY_STRING;
        } else {
//            String result = IdentityManager.getManagementDivisionCode(context);
            String result ="";
            return result;
        }
    }

    public static String getLoginPersonAreaLevel(Context context){
        String areaCode = getLoginPersonAreaCode(context);
        String result = getAreaLevelFromAreaCode(areaCode);
        return result;
    }

    public static List<DictionaryAreaData> getAreaDataBeanListFromAreaLevelAndParentAreaCode(String areaLevel, String parentAreaCode){
        areaLevel= Util.trimString(areaLevel);
        parentAreaCode=Util.trimString(parentAreaCode);
        if (StringUtils.isBlank(areaLevel) || StringUtils.isBlank(parentAreaCode)){
            List<DictionaryAreaData> result = new ArrayList<>();
            return result;
        } else {
            List<DictionaryAreaData> list = LitePal.where("arealevel = ? and superId = ?", areaLevel, parentAreaCode)
                    .find(DictionaryAreaData.class);
            if (list==null){
                list=new ArrayList<>();
            }
            return list;
        }
    }

    public static List<DictionaryAreaData> getAreaDataBeanListFromAreaLevel(String areaLevel){

        areaLevel=Util.trimString(areaLevel);
        if (StringUtils.isBlank(areaLevel)){
            List<DictionaryAreaData> result = null;
            try {
                result = new ArrayList<>();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        } else {
            List<DictionaryAreaData> list = null;
            try {
                list = LitePal.where("arealevel = ?", areaLevel).find(DictionaryAreaData.class);
                if (list==null){
                    list=new ArrayList<>();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
    }

    public static DictionaryAreaData getParentAreaDataBeanFromAreaCode(String areaCode){
        String parentAreaCode = getParentAreaCodeFromAreaCode(areaCode);
        DictionaryAreaData parentBean = getAreaDataBeanFromAreaCode(parentAreaCode);
        return parentBean;
    }

    public static String getParentAreaCodeFromAreaCode(String areaCode){
        DictionaryAreaData areaData = getAreaDataBeanFromAreaCode(areaCode);
        if (areaData!=null){
            String result = Util.trimString(areaData.getSuperId());
            return result;
        } else {
            return Constant.EMPTY_STRING;
        }
    }

    public static String getAreaNameFromAreaCode(String areaCode){
        DictionaryAreaData areaData = getAreaDataBeanFromAreaCode(areaCode);
        if (areaData!=null){
            String result = Util.trimString(areaData.getAreaname());
            return result;
        } else {
            return Constants.EMPTY_STRING;
        }
    }

    public static String getAreaLevelFromAreaCode(String areaCode){
        DictionaryAreaData areaData = getAreaDataBeanFromAreaCode(areaCode);
        if (areaData!=null){
            String result = Util.trimString(areaData.getArealevel());
            return result;
        } else {
            return Constants.EMPTY_STRING;
        }
    }

    public static String getAreaLevelNameFromAreaCode(String areaCode){
        DictionaryAreaData areaData = getAreaDataBeanFromAreaCode(areaCode);
        if (areaData!=null){
            String result = Util.trimString(areaData.getLevelName());
            return result;
        } else {
            return Constants.EMPTY_STRING;
        }
    }

    public static DictionaryAreaData getAreaDataBeanFromAreaCode(String areaCode){
        areaCode= Util.trimString(areaCode);
        if (StringUtils.isBlank(areaCode)){
            return null;
        } else {
            List<DictionaryAreaData> list = LitePal.where("ssid = ?",areaCode).find(DictionaryAreaData.class);
            if(!ListUtils.isEmpty(list)){
                DictionaryAreaData areaData = list.get(0);
                return areaData;
            } else {
                return null;
            }
        }
    }
    public static DictionaryAreaData getAreaDataBeanFromAreaCodeAndAreaLevel(String areaCode, String areaLevel){
        areaCode= Util.trimString(areaCode);
        areaLevel = Util.trimString(areaLevel);
        if (StringUtils.isBlank(areaCode) || StringUtils.isBlank(areaLevel)){
            return null;
        } else {
            List<DictionaryAreaData> list = LitePal.where("ssid = ? and arealevel = ?",areaCode, areaLevel)
                    .find(DictionaryAreaData.class);
            if(!ListUtils.isEmpty(list)){
                DictionaryAreaData areaData = list.get(0);
                return areaData;
            } else {
                return null;
            }
        }
    }
}
