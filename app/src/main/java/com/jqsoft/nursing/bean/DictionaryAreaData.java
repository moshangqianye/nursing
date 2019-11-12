package com.jqsoft.nursing.bean;



import com.jqsoft.nursing.di.ui.selectadress.views.IStringRepresentationAndValue;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

/**
 * Created by Jerry on 2017/7/13.
 */

public class DictionaryAreaData extends LitePalSupport  implements IStringRepresentationAndValue {
    private String key;//地区主键
    private String ssid;//自身的id
    private String areaname;//地区名
    private String superId;//父级id
    private String arealevel;// 地区的等级(1:省、直辖市级 2:市级 3:县、区级 4:乡、镇级 5:村级)
    private String levelName;//地区等级名
    private String DTIMESTAMP;//


    public DictionaryAreaData() {
    }


    public DictionaryAreaData(String key, String ssid, String areaname, String superId, String arealevel, String levelName) {
        this.key = key;
        this.ssid = ssid;
        this.areaname = areaname;
        this.superId = superId;
        this.arealevel = arealevel;
        this.levelName = levelName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }



    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getArealevel() {
        return arealevel;
    }

    public void setArealevel(String arealevel) {
        this.arealevel = arealevel;
    }

    @Override
    public String getStringRepresentation() {
        return areaname;
    }

    @Override
    public String getStringValue() {
        return ssid;
    }


    public String getDTIMESTAMP() {
        return DTIMESTAMP;
    }

    public void setDTIMESTAMP(String DTIMESTAMP) {
        this.DTIMESTAMP = DTIMESTAMP;
    }
}
