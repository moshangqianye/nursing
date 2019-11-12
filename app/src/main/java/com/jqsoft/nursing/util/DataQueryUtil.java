package com.jqsoft.nursing.util;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginDataDictionaryBean;
import com.jqsoft.nursing.bean.resident.SRCLoginAreaBean;
import com.jqsoft.nursing.utils3.util.ListUtils;
import com.jqsoft.nursing.utils3.util.StringUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-01-25.
 */

public class DataQueryUtil {

    public static String getAreaLevelFromAreaCode(String areaCode){
        if (StringUtils.isBlank(areaCode)){
            return Constants.EMPTY_STRING;
        } else {
            List<SRCLoginAreaBean>  list = LitePal.where("areaCode=? ",areaCode ).find(SRCLoginAreaBean.class);
            if (!ListUtils.isEmpty(list)){
                SRCLoginAreaBean area = list.get(0);
                return area.getAreaLevel();
            } else {
                return Constants.EMPTY_STRING;
            }
        }
    }

    public static String getSelectedAreaNameFromSelectedAreaCodeAndAreaLevel(String selectedAreaCode, String areaLevel) {
        String result = getDefaultAreaSelectTextFromAreaLevel(areaLevel);
        if (Constants.AREA_CODE_ALL.equals(selectedAreaCode)){
            return "全部";
        } else if (StringUtils.isBlank(selectedAreaCode) || StringUtils.isBlank(areaLevel)) {
            return result;
        } else {
            areaLevel=Util.trimString(areaLevel);
            selectedAreaCode=Util.trimString(selectedAreaCode);
            List<SRCLoginAreaBean> list = new ArrayList<>();
            list = LitePal.where(" areaLevel=? and areaCode=?", areaLevel, selectedAreaCode).find(SRCLoginAreaBean.class);
            if (ListUtils.isEmpty(list)) {
                return result;
            } else {
                SRCLoginAreaBean bean = list.get(0);
                return bean.getAreaName();
            }
        }
    }

    public static SRCLoginAreaBean getSelectedAreaBeanFromSelectedAreaCodeAndAreaLevel(String selectedAreaCode, String areaLevel) {
        SRCLoginAreaBean result = null;
        if (StringUtils.isBlank(selectedAreaCode) || StringUtils.isBlank(areaLevel)) {
            return result;
        } else {
            areaLevel=Util.trimString(areaLevel);
            selectedAreaCode=Util.trimString(selectedAreaCode);
            List<SRCLoginAreaBean> list = new ArrayList<>();
            list = LitePal.where(" areaLevel=? and areaCode=?", areaLevel, selectedAreaCode).find(SRCLoginAreaBean.class);
            if (ListUtils.isEmpty(list)) {
                return result;
            } else {
                SRCLoginAreaBean bean = list.get(0);
                return bean;
            }
        }
    }

    public static String getDefaultAreaSelectTextFromAreaLevel(String areaLevel){
        String result = Constants.EMPTY_STRING;
        if (Constants.AREA_LEVEL_NATION.equals(areaLevel)){
            result = "请选择国家";
        } else if (Constants.AREA_LEVEL_PROVINCE.equals(areaLevel)){
            result = "请选择省(直辖市)";
        } else if (Constants.AREA_LEVEL_CITY.equals(areaLevel)){
            result = "请选择市";
        } else if (Constants.AREA_LEVEL_COUNTY.equals(areaLevel)){
            result = "请选择区(县)";
        } else if (Constants.AREA_LEVEL_STREET.equals(areaLevel)){
            result = "请选择街道(乡镇)";
        } else if (Constants.AREA_LEVEL_VILLAGE.equals(areaLevel)){
            result = "请选择社区(村)";
        }
        return result;
    }

     public static int getSelectedPositionFromSelectedAreaCodeAndBeanList(String selectedAreaCode, List<SRCLoginAreaBean> areaList){
        if (Constants.AREA_CODE_ALL.equals(selectedAreaCode) || StringUtils.isBlank(selectedAreaCode) || ListUtils.isEmpty(areaList)){
            return 0;
        } else {
            int pos = 0;
            for (int i = 0; i < areaList.size(); ++i){
                SRCLoginAreaBean bean = areaList.get(i);
                if (selectedAreaCode.equals(bean.getAreaCode())){
                    pos=i;
                    break;
                }
            }
            return pos;
        }
    }

    public static String getAreaCodeFromAreaLevel(String areaLevel){
         areaLevel=Util.trimString(areaLevel);
         List<SRCLoginAreaBean> result = new ArrayList<>();
         result = LitePal.where("areaLevel=?", areaLevel).find(SRCLoginAreaBean.class);
         if (ListUtils.getSize(result)==1){
             SRCLoginAreaBean first = result.get(0);
             return first.getAreaCode();
         } else {
             return Constants.EMPTY_STRING;
         }
    }

    //获取区划数据
    public static List<SRCLoginAreaBean> getAreaListFromAreaLevelAndNonNullableAreaPid(String areaLevel, String areaPid) {
        areaLevel=Util.trimString(areaLevel);
        areaPid=Util.trimString(areaPid);
        List<SRCLoginAreaBean> result = new ArrayList<>();
        List<SRCLoginAreaBean> list = new ArrayList<>();
        list = LitePal.where(" areaLevel=? and areaPid=?", areaLevel, areaPid).find(SRCLoginAreaBean.class);
        if (list == null) {
            return result;
        } else {
            return list;
        }

    }

    public static List<SRCLoginAreaBean> getAreaListFromAreaLevelAndNullableAreaPid(String areaLevel, String areaPid) {
        List<SRCLoginAreaBean> result = new ArrayList<>();
        List<SRCLoginAreaBean> list = new ArrayList<>();
        if (StringUtils.isBlank(areaLevel) && StringUtils.isBlank(areaPid)) {
            list = null;
        } else if (!StringUtils.isBlank(areaLevel) && StringUtils.isBlank(areaPid)) {
            list = LitePal.where(" areaLevel=?", areaLevel).find(SRCLoginAreaBean.class);
        } else if (StringUtils.isBlank(areaLevel) && !StringUtils.isBlank(areaPid)) {
            list = LitePal.where(" areaPid=?", areaPid).find(SRCLoginAreaBean.class);
        } else {
            list = LitePal.where(" areaLevel=? and areaPid=?", areaLevel, areaPid).find(SRCLoginAreaBean.class);
        }
        if (list == null) {
            return result;
        } else {
            return list;
        }

    }

    //从数据字典取pcode为传入参数值的列表
    public static List<SRCLoginDataDictionaryBean> getDataDictionaryFromPCode(String pcode) {
        List<SRCLoginDataDictionaryBean> result = new ArrayList<>();
        if (StringUtils.isBlank(pcode)) {
            return result;
        } else {
            List<SRCLoginDataDictionaryBean> list = LitePal.where(" pcode=? ", pcode).find(SRCLoginDataDictionaryBean.class);
            if (list == null) {
                return result;
            } else {
                return list;
            }
        }
    }
}
