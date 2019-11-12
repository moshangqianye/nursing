package com.jqsoft.nursing.bean.grassroots_civil_administration;

import com.bigkoo.pickerview.model.IPickerViewData;


import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-08-16.
 * 签约居民端应用程序登录后返回的bean
 */

public class SRCLoginDataDictionaryBean extends LitePalSupport implements Serializable, IPickerViewData {
    private String code;
    private String name;
    private String pCode;
    private String sort;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
