package com.jqsoft.nursing.bean.nursing;

/**
 * Created by Administrator on 2018-04-19.
 */

public class DeanCockpitBedBean {
     private String All;
     private String DataType;
    private String HasElder;
    public DeanCockpitBedBean() {
        super();
    }

    public DeanCockpitBedBean(String all, String dataType, String hasElder) {
        All = all;
        DataType = dataType;
        HasElder = hasElder;
    }

    public String getAll() {
        return All;
    }

    public void setAll(String all) {
        All = all;
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
