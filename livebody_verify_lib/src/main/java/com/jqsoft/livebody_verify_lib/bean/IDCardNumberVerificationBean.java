package com.jqsoft.livebody_verify_lib.bean;

/**
 * Created by Administrator on 2018-08-23.
 */

public class IDCardNumberVerificationBean {
    private String idnumExist;
    private String respond;
    private String message;
    private String headImg;

    private String headFeature;
    private String arcsoftVersion;

    public IDCardNumberVerificationBean() {
        super();
    }

    public IDCardNumberVerificationBean(String idnumExist, String respond, String message, String headImg) {
        this.idnumExist = idnumExist;
        this.respond = respond;
        this.message = message;
        this.headImg = headImg;
    }

    public String getIdnumExist() {
        return idnumExist;
    }

    public void setIdnumExist(String idnumExist) {
        this.idnumExist = idnumExist;
    }

    public String getRespond() {
        return respond;
    }

    public void setRespond(String respond) {
        this.respond = respond;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getHeadFeature() {
        return headFeature;
    }

    public void setHeadFeature(String headFeature) {
        this.headFeature = headFeature;
    }

    public String getArcsoftVersion() {
        return arcsoftVersion;
    }

    public void setArcsoftVersion(String arcsoftVersion) {
        this.arcsoftVersion = arcsoftVersion;
    }
}
