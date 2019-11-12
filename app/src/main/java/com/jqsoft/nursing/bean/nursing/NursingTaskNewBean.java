package com.jqsoft.nursing.bean.nursing;

import android.os.Parcel;
import android.os.Parcelable;

import com.jqsoft.nursing.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 老人护理详细信息
 * Created by Administrator on 2018-04-08.
 */

public class NursingTaskNewBean  {

    private NursingElderBean Elder;

    private List<NursingTaskBean> List=new ArrayList<>();

    public NursingTaskNewBean() {
        super();
    }

    public NursingElderBean getElder() {
        return Elder;
    }

    public void setElder(NursingElderBean elder) {
        Elder = elder;
    }

    public java.util.List<NursingTaskBean> getList() {
        return List;
    }

    public void setList(java.util.List<NursingTaskBean> list) {
        List = list;
    }

    public NursingTaskNewBean(NursingElderBean elder, java.util.List<NursingTaskBean> list) {
        Elder = elder;
        List = list;
    }
}
