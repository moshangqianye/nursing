package com.jqsoft.nursing.bean.nursing;

/**
 * 登录人护理老人信息
 * Created by Administrator on 2018-04-02.
 */

public class RoundsRecordCount {
    public String RoomID;	//总数据条数
    public String RoomRecordCount;	//总数据条数



    public RoundsRecordCount() {
       // super();
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getRoomRecordCount() {
        return RoomRecordCount;
    }

    public void setRoomRecordCount(String roomRecordCount) {
        RoomRecordCount = roomRecordCount;
    }
}
