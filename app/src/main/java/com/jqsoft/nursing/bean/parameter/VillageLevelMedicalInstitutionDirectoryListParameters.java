package com.jqsoft.nursing.bean.parameter;

import com.jqsoft.nursing.annotation.HttpParameter;
import com.jqsoft.nursing.bean.parameter.base.CommonParameters;

/**
 * Created by Administrator on 2017-06-22.
 */

public class VillageLevelMedicalInstitutionDirectoryListParameters extends CommonParameters {
    @HttpParameter
    private String keyword;//关键字
    @HttpParameter
    private String area;//地区
    @HttpParameter
    private int page;//页数
    @HttpParameter
    private int size;//行数

    public VillageLevelMedicalInstitutionDirectoryListParameters() {
        super();
    }

    public VillageLevelMedicalInstitutionDirectoryListParameters(String appkey, String timestamp, String token, String sig, String v) {
        super(appkey, timestamp, token, sig, v);
    }

    public VillageLevelMedicalInstitutionDirectoryListParameters(String appkey, String timestamp, String token, String sig, String v, String keyword, String area, int page, int size) {
        super(appkey, timestamp, token, sig, v);
        this.keyword = keyword;
        this.area = area;
        this.page = page;
        this.size = size;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
