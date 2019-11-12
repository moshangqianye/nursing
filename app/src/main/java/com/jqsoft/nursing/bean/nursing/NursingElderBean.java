package com.jqsoft.nursing.bean.nursing;

/**
 * 老人护理详细信息
 * Created by Administrator on 2018-04-08.
 */

public class NursingElderBean {

    private String ElderName;	//项目图片
    private String ElderAge;	//项目图片
    private String ElderSex;	//项目图片

    private String NurseLevelName;	//项目图片
    private String BuildingNO;	//项目图片
    private String RoomNumber;	//项目图片
    private String BedNO;	//项目图片
    private String ElderInfoID;

    public NursingElderBean(String elderName, String elderAge, String elderSex, String nurseLevelName, String buildingNO, String roomNumber, String bedNO) {
        ElderName = elderName;
        ElderAge = elderAge;
        ElderSex = elderSex;
        NurseLevelName = nurseLevelName;
        BuildingNO = buildingNO;
        RoomNumber = roomNumber;
        BedNO = bedNO;
    }

    public NursingElderBean() {
        super();
    }

    public String getElderName() {
        return ElderName;
    }

    public void setElderName(String elderName) {
        ElderName = elderName;
    }

    public String getElderAge() {
        return ElderAge;
    }

    public void setElderAge(String elderAge) {
        ElderAge = elderAge;
    }

    public String getElderSex() {
        return ElderSex;
    }

    public void setElderSex(String elderSex) {
        ElderSex = elderSex;
    }

    public String getNurseLevelName() {
        return NurseLevelName;
    }

    public void setNurseLevelName(String nurseLevelName) {
        NurseLevelName = nurseLevelName;
    }

    public String getBuildingNO() {
        return BuildingNO;
    }

    public void setBuildingNO(String buildingNO) {
        BuildingNO = buildingNO;
    }

    public String getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        RoomNumber = roomNumber;
    }

    public String getBedNO() {
        return BedNO;
    }

    public void setBedNO(String bedNO) {
        BedNO = bedNO;
    }

    public String getElderInfoID() {
        return ElderInfoID;
    }

    public void setElderInfoID(String elderInfoID) {
        ElderInfoID = elderInfoID;
    }
}
