package com.jqsoft.nursing.bean.base;

/**
 * Created by Administrator on 2017-05-18.
 */

public class HttpResultNewBaseBean<T> {
    private String Success ;
    private String ErrorMsg;
    private String Message;
    private T BackInfo;

    public HttpResultNewBaseBean() {
    }

    public HttpResultNewBaseBean(String success, String errorMsg, T backInfo) {
        Success = success;
        ErrorMsg = errorMsg;
        BackInfo = backInfo;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }

    public T getBackInfo() {
        return BackInfo;
    }

    public void setBackInfo(T backInfo) {
        BackInfo = backInfo;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
