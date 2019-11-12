package com.jqsoft.nursing.bean;

import java.io.Serializable;

/**
 * Created by Mars on 2018/1/8.
 */

public class UrbanbaseInfoSaveBean implements Serializable {
    private  String batchNo;


    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public UrbanbaseInfoSaveBean(String batchNo) {
        this.batchNo = batchNo;
    }

    public UrbanbaseInfoSaveBean() {
        super();
    }
}
