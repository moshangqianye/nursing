package com.jqsoft.nursing.bean.nursing;

/**
 * Created by Administrator on 2018-04-18.
 */

public class DeanCockpitElderBean {
    private String DataType;//数据分类
    private String HasElder;//当前分类老人数
    public DeanCockpitElderBean() {
        super();
    }

    public DeanCockpitElderBean(String dataType, String hasElder) {
        DataType = dataType;
        HasElder = hasElder;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

    public String getHasElder() {
        return HasElder;
    }

    public void setHasElder(String hasElder) {
        HasElder = hasElder;
    }
}
