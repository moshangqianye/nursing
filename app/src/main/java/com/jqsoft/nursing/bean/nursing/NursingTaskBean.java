package com.jqsoft.nursing.bean.nursing;

import android.os.Parcel;
import android.os.Parcelable;

import com.jqsoft.nursing.util.Util;

/**
 * 老人护理详细信息
 * Created by Administrator on 2018-04-08.
 */

public class NursingTaskBean implements Parcelable {
    private String ServicePlanDetailsID;	//详细信息主键
    private String FKServicePlanID;	//计划外键
    private String ServiceItemName;	//项目名称
    private String ServiceItemId;	//项目主键
    private String BeginTime;	//开始时间（时分）
    private String EndTime;	//结束时间（时分）
    private String ServicePrice;	//价格
    private String sMemo;	//备注
    private String IsExcute;	//是否执行  1已执行，无或0为未执行
    private String ExcuteTime;	//执行时间
    private String SignName;	//签名（执行人）
    private String ServicePic;	//项目图片

    private String ElderName;	//项目图片
    private String ElderSex;	//项目图片
    private String ElderAge;	//项目图片
    private String NurseLevelName;	//项目图片
    private String BuildingNO;	//项目图片
    private String RoomNumber;	//项目图片
    private String BedNO;	//项目图片

    public String getExecuteStatusString(){
        if (Util.isNursingTaskDone(IsExcute)){
            return "已完成";
        } else {
            return "提交护理";
        }
    }

    public NursingTaskBean() {
        super();
    }

    public NursingTaskBean(String servicePlanDetailsID, String FKServicePlanID, String serviceItemName, String serviceItemId, String beginTime, String endTime, String servicePrice, String sMemo, String isExcute, String excuteTime, String signName, String servicePic) {
        ServicePlanDetailsID = servicePlanDetailsID;
        this.FKServicePlanID = FKServicePlanID;
        ServiceItemName = serviceItemName;
        ServiceItemId = serviceItemId;
        BeginTime = beginTime;
        EndTime = endTime;
        ServicePrice = servicePrice;
        this.sMemo = sMemo;
        IsExcute = isExcute;
        ExcuteTime = excuteTime;
        SignName = signName;
        ServicePic = servicePic;
    }

    public String getServicePlanDetailsID() {
        return ServicePlanDetailsID;
    }

    public void setServicePlanDetailsID(String servicePlanDetailsID) {
        ServicePlanDetailsID = servicePlanDetailsID;
    }

    public String getFKServicePlanID() {
        return FKServicePlanID;
    }

    public void setFKServicePlanID(String FKServicePlanID) {
        this.FKServicePlanID = FKServicePlanID;
    }

    public String getServiceItemName() {
        return ServiceItemName;
    }

    public void setServiceItemName(String serviceItemName) {
        ServiceItemName = serviceItemName;
    }

    public String getServiceItemId() {
        return ServiceItemId;
    }

    public void setServiceItemId(String serviceItemId) {
        ServiceItemId = serviceItemId;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getServicePrice() {
        return ServicePrice;
    }

    public void setServicePrice(String servicePrice) {
        ServicePrice = servicePrice;
    }

    public String getsMemo() {
        return sMemo;
    }

    public void setsMemo(String sMemo) {
        this.sMemo = sMemo;
    }

    public String getIsExcute() {
        return IsExcute;
    }

    public void setIsExcute(String isExcute) {
        IsExcute = isExcute;
    }

    public String getExcuteTime() {
        return ExcuteTime;
    }

    public void setExcuteTime(String excuteTime) {
        ExcuteTime = excuteTime;
    }

    public String getSignName() {
        return SignName;
    }

    public void setSignName(String signName) {
        SignName = signName;
    }

    public String getServicePic() {
        return ServicePic;
    }

    public void setServicePic(String servicePic) {
        ServicePic = servicePic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ServicePlanDetailsID);
        dest.writeString(this.FKServicePlanID);
        dest.writeString(this.ServiceItemName);
        dest.writeString(this.ServiceItemId);
        dest.writeString(this.BeginTime);
        dest.writeString(this.EndTime);
        dest.writeString(this.ServicePrice);
        dest.writeString(this.sMemo);
        dest.writeString(this.IsExcute);
        dest.writeString(this.ExcuteTime);
        dest.writeString(this.SignName);
        dest.writeString(this.ServicePic);
    }

    protected NursingTaskBean(Parcel in) {
        this.ServicePlanDetailsID = in.readString();
        this.FKServicePlanID = in.readString();
        this.ServiceItemName = in.readString();
        this.ServiceItemId = in.readString();
        this.BeginTime = in.readString();
        this.EndTime = in.readString();
        this.ServicePrice = in.readString();
        this.sMemo = in.readString();
        this.IsExcute = in.readString();
        this.ExcuteTime = in.readString();
        this.SignName = in.readString();
        this.ServicePic = in.readString();
    }

    public static final Parcelable.Creator<NursingTaskBean> CREATOR = new Parcelable.Creator<NursingTaskBean>() {
        @Override
        public NursingTaskBean createFromParcel(Parcel source) {
            return new NursingTaskBean(source);
        }

        @Override
        public NursingTaskBean[] newArray(int size) {
            return new NursingTaskBean[size];
        }
    };

    public String getElderName() {
        return ElderName;
    }

    public void setElderName(String elderName) {
        ElderName = elderName;
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
}
