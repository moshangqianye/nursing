package com.jqsoft.nursing.bean.base;

/**
 * Created by Administrator on 2017-05-18.
 */

public class HttpResultNurseBaseBean<T> {
    private String Success;
    private String Msg;
    private T Data;

    public HttpResultNurseBaseBean() {
    }

    public HttpResultNurseBaseBean(String result, String msg, T data) {
        this.Success = result;
        this.Msg = msg;
        this.Data = data;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
