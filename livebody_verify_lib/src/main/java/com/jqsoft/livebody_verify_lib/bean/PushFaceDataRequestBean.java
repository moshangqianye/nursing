package com.jqsoft.livebody_verify_lib.bean;

/**
 * Created by Administrator on 2018-08-21.
 */

public class PushFaceDataRequestBean {
    private String result;//: "1",
    private String msg;//: "验证成功",
    private String orgCode;//: "1",
    private String collectionType;//: "1",
    private String submitUser;//: "xiang01",
    private String idNo;//: "36232119950615801X"

    public PushFaceDataRequestBean() {
        super();
    }

    public PushFaceDataRequestBean(String result, String msg, String orgCode, String collectionType, String submitUser, String idNo) {
        this.result = result;
        this.msg = msg;
        this.orgCode = orgCode;
        this.collectionType = collectionType;
        this.submitUser = submitUser;
        this.idNo = idNo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
}
