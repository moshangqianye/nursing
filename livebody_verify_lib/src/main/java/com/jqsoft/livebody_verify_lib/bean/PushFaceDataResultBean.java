package com.jqsoft.livebody_verify_lib.bean;

/**
 * Created by Administrator on 2018-08-21.
 */

public class PushFaceDataResultBean {
    private String code;
    private String message;
    public PushFaceDataResultBean() {
        super();
    }

    public PushFaceDataResultBean(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
