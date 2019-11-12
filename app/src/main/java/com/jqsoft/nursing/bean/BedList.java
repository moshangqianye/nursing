package com.jqsoft.nursing.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/3.
 */

public class BedList implements Serializable{
    private String FKRoomID;
    private String BedID;
    private String BedName;
    private String BedNO;

    public BedList() {
    }

    public BedList(String FKRoomID, String bedID, String bedName, String bedNO) {
        this.FKRoomID = FKRoomID;
        BedID = bedID;
        BedName = bedName;
        BedNO = bedNO;
    }

    public String getFKRoomID() {
        return FKRoomID;
    }

    public void setFKRoomID(String FKRoomID) {
        this.FKRoomID = FKRoomID;
    }

    public String getBedID() {
        return BedID;
    }

    public void setBedID(String bedID) {
        BedID = bedID;
    }

    public String getBedName() {
        return BedName;
    }

    public void setBedName(String bedName) {
        BedName = bedName;
    }

    public String getBedNO() {
        return BedNO;
    }

    public void setBedNO(String bedNO) {
        BedNO = bedNO;
    }
}
