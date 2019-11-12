package com.jqsoft.nursing.bean.resident;

import com.bigkoo.pickerview.model.IPickerViewData;


import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-08-16.
 * 签约居民端应用程序登录后返回的bean
 */

public class SRCLoginAreaBean extends LitePalSupport implements Serializable, IPickerViewData {
    private String alias;
    private String areaCode;
    private String areaLevel;
    private String areaName;
    private String areaPid;


    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaPid() {
        return areaPid;
    }

    public void setAreaPid(String areaPid) {
        this.areaPid = areaPid;
    }

    @Override
    public String getPickerViewText() {
        return areaName;
    }



}
