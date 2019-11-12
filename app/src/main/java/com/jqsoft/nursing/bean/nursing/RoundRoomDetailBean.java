package com.jqsoft.nursing.bean.nursing;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录人护理老人信息
 * Created by Administrator on 2018-04-02.
 */

public class RoundRoomDetailBean implements Serializable {


    private List<ElderDetailBean> Elders =new ArrayList<>();
    private List<RoundDataBean> RoundDatas=new ArrayList<>();

    public List<ElderDetailBean> getElders() {
        return Elders;
    }

    public void setElders(ArrayList<ElderDetailBean> elders) {
        Elders = elders;
    }

    public List<RoundDataBean> getRoundDatas() {
        return RoundDatas;
    }

    public void setRoundDatas(ArrayList<RoundDataBean> roundDatas) {
        RoundDatas = roundDatas;
    }

    public RoundRoomDetailBean(ArrayList<ElderDetailBean> elders, ArrayList<RoundDataBean> roundDatas) {

        super();
        Elders = elders;
        RoundDatas = roundDatas;
    }

    public RoundRoomDetailBean() {
        super();
    }
}
