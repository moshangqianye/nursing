package com.jqsoft.nursing.bean.nursing;

import org.litepal.LitePal;

import java.io.Serializable;

/**
 * 登录人护理老人信息
 * Created by Administrator on 2018-04-02.
 */

public class MainInfoBean extends LitePal implements Serializable {
    private RoomIdBean roomIdBean;	//总数据条数


    public RoomIdBean getRoomIdBean() {
        return roomIdBean;
    }

    public void setRoomIdBean(RoomIdBean roomIdBean) {
        this.roomIdBean = roomIdBean;
    }
}
