package com.jqsoft.nursing.bean.nursing;

/**
 * Created by Administrator on 2018-04-18.
 */

public class OrgnizationBean {
    private String sId;//机构主键
    private String sOrgName;//机构名称
    public OrgnizationBean() {
        super();
    }

    public OrgnizationBean(String sId, String sOrgName) {
        this.sId = sId;
        this.sOrgName = sOrgName;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsOrgName() {
        return sOrgName;
    }

    public void setsOrgName(String sOrgName) {
        this.sOrgName = sOrgName;
    }
}
