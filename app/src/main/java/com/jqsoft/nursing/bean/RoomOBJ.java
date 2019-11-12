package com.jqsoft.nursing.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/3.
 */

public class RoomOBJ implements Serializable{
    private String FKFloorID;
    private String RoomID;
    private String RoomImage;
    private String RoomName;
    private String RoomNO;
    private String RoomTypeCode;
    private String RoomTypeName;
    private String IsNF;

    public RoomOBJ() {
    }

    public RoomOBJ(String FKFloorID, String roomID, String roomImage, String roomName, String roomNO, String roomTypeCode, String roomTypeName, String isNF) {
        this.FKFloorID = FKFloorID;
        RoomID = roomID;
        RoomImage = roomImage;
        RoomName = roomName;
        RoomNO = roomNO;
        RoomTypeCode = roomTypeCode;
        RoomTypeName = roomTypeName;
        IsNF = isNF;
    }

    public String getFKFloorID() {
        return FKFloorID;
    }

    public void setFKFloorID(String FKFloorID) {
        this.FKFloorID = FKFloorID;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getRoomImage() {
        return RoomImage;
    }

    public void setRoomImage(String roomImage) {
        RoomImage = roomImage;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getRoomNO() {
        return RoomNO;
    }

    public void setRoomNO(String roomNO) {
        RoomNO = roomNO;
    }

    public String getRoomTypeCode() {
        return RoomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        RoomTypeCode = roomTypeCode;
    }

    public String getRoomTypeName() {
        return RoomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        RoomTypeName = roomTypeName;
    }

    public String getIsNF() {
        return IsNF;
    }

    public void setIsNF(String isNF) {
        IsNF = isNF;
    }
}
