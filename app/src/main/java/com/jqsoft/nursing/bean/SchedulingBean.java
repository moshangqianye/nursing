package com.jqsoft.nursing.bean;

/**
 * Created by Mars on 2018/4/3.
 */

public class SchedulingBean {

    private String SchedulDetailID;
    private String FkStaffName;
    private String FkStaffID;
    private String SchedulDate;
    private String JoinSchedulTimeID;
    private String JoinSchedulTimeName;
    private String SchedulFrom;
    private String SchedulTo;
    private String SchedulTimeID;
    private String SchedulName;
    private String sMemo;
    private  String SchedulElders;

    public SchedulingBean() {
    }

    public SchedulingBean(String schedulDetailID, String fkStaffName, String fkStaffID, String schedulDate, String joinSchedulTimeID, String joinSchedulTimeName, String schedulFrom, String schedulTo, String schedulTimeID, String schedulName, String sMemo, String schedulElders) {
        SchedulDetailID = schedulDetailID;
        FkStaffName = fkStaffName;
        FkStaffID = fkStaffID;
        SchedulDate = schedulDate;
        JoinSchedulTimeID = joinSchedulTimeID;
        JoinSchedulTimeName = joinSchedulTimeName;
        SchedulFrom = schedulFrom;
        SchedulTo = schedulTo;
        SchedulTimeID = schedulTimeID;
        SchedulName = schedulName;
        this.sMemo = sMemo;
        SchedulElders = schedulElders;
    }

    public String getSchedulDetailID() {
        return SchedulDetailID;
    }

    public void setSchedulDetailID(String schedulDetailID) {
        SchedulDetailID = schedulDetailID;
    }

    public String getFkStaffName() {
        return FkStaffName;
    }

    public void setFkStaffName(String fkStaffName) {
        FkStaffName = fkStaffName;
    }

    public String getFkStaffID() {
        return FkStaffID;
    }

    public void setFkStaffID(String fkStaffID) {
        FkStaffID = fkStaffID;
    }

    public String getSchedulDate() {
        return SchedulDate;
    }

    public void setSchedulDate(String schedulDate) {
        SchedulDate = schedulDate;
    }

    public String getJoinSchedulTimeID() {
        return JoinSchedulTimeID;
    }

    public void setJoinSchedulTimeID(String joinSchedulTimeID) {
        JoinSchedulTimeID = joinSchedulTimeID;
    }

    public String getJoinSchedulTimeName() {
        return JoinSchedulTimeName;
    }

    public void setJoinSchedulTimeName(String joinSchedulTimeName) {
        JoinSchedulTimeName = joinSchedulTimeName;
    }

    public String getSchedulFrom() {
        return SchedulFrom;
    }

    public void setSchedulFrom(String schedulFrom) {
        SchedulFrom = schedulFrom;
    }

    public String getSchedulTo() {
        return SchedulTo;
    }

    public void setSchedulTo(String schedulTo) {
        SchedulTo = schedulTo;
    }

    public String getSchedulTimeID() {
        return SchedulTimeID;
    }

    public void setSchedulTimeID(String schedulTimeID) {
        SchedulTimeID = schedulTimeID;
    }

    public String getSchedulName() {
        return SchedulName;
    }

    public void setSchedulName(String schedulName) {
        SchedulName = schedulName;
    }

    public String getsMemo() {
        return sMemo;
    }

    public void setsMemo(String sMemo) {
        this.sMemo = sMemo;
    }

    public String getSchedulElders() {
        return SchedulElders;
    }

    public void setSchedulElders(String schedulElders) {
        SchedulElders = schedulElders;
    }
}
