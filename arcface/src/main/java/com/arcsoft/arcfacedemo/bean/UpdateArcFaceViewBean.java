package com.arcsoft.arcfacedemo.bean;

public class UpdateArcFaceViewBean {
    private  String viewName;
    private  String msg;
    private int textColor;

    public UpdateArcFaceViewBean(String viewName, String msg, int textColor) {
        this.viewName = viewName;
        this.msg = msg;
        this.textColor = textColor;
    }

    public UpdateArcFaceViewBean() {
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
