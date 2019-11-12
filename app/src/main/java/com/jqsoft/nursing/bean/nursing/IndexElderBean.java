package com.jqsoft.nursing.bean.nursing;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-04-18.
 */

public class IndexElderBean implements Serializable{
    private String elderin;//数据分类
    private String elderout;//当前分类老人数
    private String elderqj;//数据分类
    private String laifang;//当前分类老人数
    private String jiedai;//数据分类
    private String serverelder;//当前分类老人数
    private String serverisdo;//数据分类
    private String serverundo;//当前分类老人数
    private String serverall;//数据分类

    public IndexElderBean() {
        super();
    }


    public String getElderin() {
        return elderin;
    }

    public void setElderin(String elderin) {
        this.elderin = elderin;
    }

    public String getElderout() {
        return elderout;
    }

    public void setElderout(String elderout) {
        this.elderout = elderout;
    }

    public String getElderqj() {
        return elderqj;
    }

    public void setElderqj(String elderqj) {
        this.elderqj = elderqj;
    }

    public String getLaifang() {
        return laifang;
    }

    public void setLaifang(String laifang) {
        this.laifang = laifang;
    }

    public String getJiedai() {
        return jiedai;
    }

    public void setJiedai(String jiedai) {
        this.jiedai = jiedai;
    }

    public String getServerelder() {
        return serverelder;
    }

    public void setServerelder(String serverelder) {
        this.serverelder = serverelder;
    }

    public String getServerisdo() {
        return serverisdo;
    }

    public void setServerisdo(String serverisdo) {
        this.serverisdo = serverisdo;
    }

    public String getServerundo() {
        return serverundo;
    }

    public void setServerundo(String serverundo) {
        this.serverundo = serverundo;
    }

    public String getServerall() {
        return serverall;
    }

    public void setServerall(String serverall) {
        this.serverall = serverall;
    }
}
