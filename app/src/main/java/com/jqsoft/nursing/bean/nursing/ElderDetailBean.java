package com.jqsoft.nursing.bean.nursing;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * 登录人护理老人信息
 * Created by Administrator on 2018-04-02.
 */

public class ElderDetailBean implements Serializable {
    private String ElderInfoID;	//总数据条数
    private String ElderName;	//当前数据排序
    private String BuildingNO;	//任务主键
    private String RoomNumber;	//护理员
    private String BedNO;	//老人主键
    private String BedID;	//老人名称
    private String RoomTypeName;	//楼栋号
    private String ElderSex;	//房间号
    private String ElderAge;	//床位号
    private String ElderBirthday;	//房间类型
    private String ElderIDNo;	//性别
    private String ElderHouseAddress;	//年龄
    private String NurseLevelName;	//生日
    private String Status;	//身份证号
    private String InTypeName;	//家庭地址

    private String ElderPic;	//家庭地址

    public ElderDetailBean() {
        super();
    }

    public ElderDetailBean(String elderInfoID, String elderName, String buildingNO, String roomNumber, String bedNO, String bedID, String roomTypeName, String elderSex, String elderAge, String elderBirthday, String elderIDNo, String elderHouseAddress, String nurseLevelName, String status, String inTypeName, String elderPic) {
        super();
        ElderInfoID = elderInfoID;
        ElderName = elderName;
        BuildingNO = buildingNO;
        RoomNumber = roomNumber;
        BedNO = bedNO;
        BedID = bedID;
        RoomTypeName = roomTypeName;
        ElderSex = elderSex;
        ElderAge = elderAge;
        ElderBirthday = elderBirthday;
        ElderIDNo = elderIDNo;
        ElderHouseAddress = elderHouseAddress;
        NurseLevelName = nurseLevelName;
        Status = status;
        InTypeName = inTypeName;
        ElderPic = elderPic;
    }

    public String getElderInfoID() {
        return ElderInfoID;
    }

    public void setElderInfoID(String elderInfoID) {
        ElderInfoID = elderInfoID;
    }

    public String getElderName() {
        return ElderName;
    }

    public void setElderName(String elderName) {
        ElderName = elderName;
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

    public String getBedID() {
        return BedID;
    }

    public void setBedID(String bedID) {
        BedID = bedID;
    }

    public String getRoomTypeName() {
        return RoomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        RoomTypeName = roomTypeName;
    }

    public String getElderSex() {
        return ElderSex;
    }

    public void setElderSex(String elderSex) {
        ElderSex = elderSex;
    }

    public String getElderAge() {
        return ElderAge;
    }

    public void setElderAge(String elderAge) {
        ElderAge = elderAge;
    }

    public String getElderBirthday() {
        return ElderBirthday;
    }

    public void setElderBirthday(String elderBirthday) {
        ElderBirthday = elderBirthday;
    }

    public String getElderIDNo() {
        return ElderIDNo;
    }

    public void setElderIDNo(String elderIDNo) {
        ElderIDNo = elderIDNo;
    }

    public String getElderHouseAddress() {
        return ElderHouseAddress;
    }

    public void setElderHouseAddress(String elderHouseAddress) {
        ElderHouseAddress = elderHouseAddress;
    }

    public String getNurseLevelName() {
        return NurseLevelName;
    }

    public void setNurseLevelName(String nurseLevelName) {
        NurseLevelName = nurseLevelName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getInTypeName() {
        return InTypeName;
    }

    public void setInTypeName(String inTypeName) {
        InTypeName = inTypeName;
    }

    public String getElderPic() {
        return ElderPic;
    }

    public void setElderPic(String elderPic) {
        ElderPic = elderPic;
    }
}
