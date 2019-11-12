package com.jqsoft.nursing.bean.nursing;

import org.litepal.LitePal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录人护理老人信息
 * Created by Administrator on 2018-04-02.
 */

public class DataOutBean     {
    public RoomIdBean mainInfo;	//总数据条数
    public List<SaveRoundDataBean> detailInfo =new ArrayList<>();	//总数据条数

    public RoomIdBean getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(RoomIdBean mainInfo) {
        this.mainInfo = mainInfo;
    }

    public List<SaveRoundDataBean> getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(List<SaveRoundDataBean> detailInfo) {
        this.detailInfo = detailInfo;
    }
}
