package com.jqsoft.nursing.bean.nursing;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 登录人护理老人信息
 * Created by Administrator on 2018-04-02.
 */

public class NursingBean implements Parcelable {
    private String iTotalCount;	//总数据条数
    private String iCount;	//当前数据排序
    private String ServicePlanDetailsID;	//任务主键
    private String Paramedic;	//护理员
    private String ElderInfoID;	//老人主键
    private String ElderName;	//老人名称
    private String BuildingNO;	//楼栋号
    private String RoomNumber;	//房间号
    private String BedNO;	//床位号
    private String BedID; //床位ID
    private String RoomTypeName;	//房间类型
    private String ElderSex;	//性别
    private String ElderAge;	//年龄
    private String ElderBirthday;	//生日
    private String ElderIDNo;	//身份证号
    private String ElderHouseAddress;	//家庭地址
    private String NurseLevelName;	//护理级别
    private String Status;	//状态（在院）
    private String InTypeName;	//入住类型
    private String ServicePic;	//长者图片
    private String IsDo;	//项目已做数
    private String UnDo;	//项目未做数

//    private String iTotalCount;	//总数据条数
//    private String iCount;	//	当前数据排序
//    private String ServicePlanDetailsID;	//	任务主键
//    private String BeginTime;	//	开始时间（时分）
//    private String EndTime;	//	结束时间（时分）
//    private String ServicePrice;	//	服务价格
//    private String IsExcute	;	//是否已执行
//    private String SignName;	//	签名
//    private String FKServiceTypeName;	//	服务项目类型
//    private String ServiceFor;	//	项目所属（护士/护工）
//    private String ServiceItemId;	//	项目id
//    private String ServiceItemName;	//	项目名称
//    private String Precedence;	//	优先级
//    private String Paramedic;	//	护理员
//    private String ElderInfoID;	//	老人主键
//    private String ElderName;	//	老人名称
//    private String BuildingNO;	//	楼栋号
//    private String RoomNumber;	//	房间号
//    private String BedNO;	//	床位号
//    private String RoomTypeName;	//	房间类型
//    private String ElderSex;	//	性别
//    private String ElderAge;	//	年龄
//    private String ElderBirthday;	//	生日
//    private String ElderIDNo;	//	身份证号
//    private String ElderHouseAddress;	//	家庭地址
//    private String NurseLevelName;	//	护理级别
//    private String Status;	//	状态（在院）
//    private String InTypeName;	//	入住类型
//    private String ServicePic;	//	服务项目图片

    public NursingBean() {
        super();
    }

    public String getiTotalCount() {
        return iTotalCount;
    }

    public void setiTotalCount(String iTotalCount) {
        this.iTotalCount = iTotalCount;
    }

    public String getiCount() {
        return iCount;
    }

    public void setiCount(String iCount) {
        this.iCount = iCount;
    }

    public String getServicePlanDetailsID() {
        return ServicePlanDetailsID;
    }

    public void setServicePlanDetailsID(String servicePlanDetailsID) {
        ServicePlanDetailsID = servicePlanDetailsID;
    }

    public String getParamedic() {
        return Paramedic;
    }

    public void setParamedic(String paramedic) {
        Paramedic = paramedic;
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

    public String getServicePic() {
        return ServicePic;
    }

    public void setServicePic(String servicePic) {
        ServicePic = servicePic;
    }

    public String getIsDo() {
        return IsDo;
    }

    public void setIsDo(String isDo) {
        IsDo = isDo;
    }

    public String getUnDo() {
        return UnDo;
    }

    public void setUnDo(String unDo) {
        UnDo = unDo;
    }
    //    public String getiTotalCount() {
//        return iTotalCount;
//    }
//
//    public void setiTotalCount(String iTotalCount) {
//        this.iTotalCount = iTotalCount;
//    }
//
//    public String getiCount() {
//        return iCount;
//    }
//
//    public void setiCount(String iCount) {
//        this.iCount = iCount;
//    }
//
//    public String getServicePlanDetailsID() {
//        return ServicePlanDetailsID;
//    }
//
//    public void setServicePlanDetailsID(String servicePlanDetailsID) {
//        ServicePlanDetailsID = servicePlanDetailsID;
//    }
//
//    public String getBeginTime() {
//        return BeginTime;
//    }
//
//    public void setBeginTime(String beginTime) {
//        BeginTime = beginTime;
//    }
//
//    public String getEndTime() {
//        return EndTime;
//    }
//
//    public void setEndTime(String endTime) {
//        EndTime = endTime;
//    }
//
//    public String getServicePrice() {
//        return ServicePrice;
//    }
//
//    public void setServicePrice(String servicePrice) {
//        ServicePrice = servicePrice;
//    }
//
//    public String getIsExcute() {
//        return IsExcute;
//    }
//
//    public void setIsExcute(String isExcute) {
//        IsExcute = isExcute;
//    }
//
//    public String getSignName() {
//        return SignName;
//    }
//
//    public void setSignName(String signName) {
//        SignName = signName;
//    }
//
//    public String getFKServiceTypeName() {
//        return FKServiceTypeName;
//    }
//
//    public void setFKServiceTypeName(String FKServiceTypeName) {
//        this.FKServiceTypeName = FKServiceTypeName;
//    }
//
//    public String getServiceFor() {
//        return ServiceFor;
//    }
//
//    public void setServiceFor(String serviceFor) {
//        ServiceFor = serviceFor;
//    }
//
//    public String getServiceItemId() {
//        return ServiceItemId;
//    }
//
//    public void setServiceItemId(String serviceItemId) {
//        ServiceItemId = serviceItemId;
//    }
//
//    public String getServiceItemName() {
//        return ServiceItemName;
//    }
//
//    public void setServiceItemName(String serviceItemName) {
//        ServiceItemName = serviceItemName;
//    }
//
//    public String getPrecedence() {
//        return Precedence;
//    }
//
//    public void setPrecedence(String precedence) {
//        Precedence = precedence;
//    }
//
//    public String getParamedic() {
//        return Paramedic;
//    }
//
//    public void setParamedic(String paramedic) {
//        Paramedic = paramedic;
//    }
//
//    public String getElderInfoID() {
//        return ElderInfoID;
//    }
//
//    public void setElderInfoID(String elderInfoID) {
//        ElderInfoID = elderInfoID;
//    }
//
//    public String getElderName() {
//        return ElderName;
//    }
//
//    public void setElderName(String elderName) {
//        ElderName = elderName;
//    }
//
//    public String getBuildingNO() {
//        return BuildingNO;
//    }
//
//    public void setBuildingNO(String buildingNO) {
//        BuildingNO = buildingNO;
//    }
//
//    public String getRoomNumber() {
//        return RoomNumber;
//    }
//
//    public void setRoomNumber(String roomNumber) {
//        RoomNumber = roomNumber;
//    }
//
//    public String getBedNO() {
//        return BedNO;
//    }
//
//    public void setBedNO(String bedNO) {
//        BedNO = bedNO;
//    }
//
//    public String getRoomTypeName() {
//        return RoomTypeName;
//    }
//
//    public void setRoomTypeName(String roomTypeName) {
//        RoomTypeName = roomTypeName;
//    }
//
//    public String getElderSex() {
//        return ElderSex;
//    }
//
//    public void setElderSex(String elderSex) {
//        ElderSex = elderSex;
//    }
//
//    public String getElderAge() {
//        return ElderAge;
//    }
//
//    public void setElderAge(String elderAge) {
//        ElderAge = elderAge;
//    }
//
//    public String getElderBirthday() {
//        return ElderBirthday;
//    }
//
//    public void setElderBirthday(String elderBirthday) {
//        ElderBirthday = elderBirthday;
//    }
//
//    public String getElderIDNo() {
//        return ElderIDNo;
//    }
//
//    public void setElderIDNo(String elderIDNo) {
//        ElderIDNo = elderIDNo;
//    }
//
//    public String getElderHouseAddress() {
//        return ElderHouseAddress;
//    }
//
//    public void setElderHouseAddress(String elderHouseAddress) {
//        ElderHouseAddress = elderHouseAddress;
//    }
//
//    public String getNurseLevelName() {
//        return NurseLevelName;
//    }
//
//    public void setNurseLevelName(String nurseLevelName) {
//        NurseLevelName = nurseLevelName;
//    }
//
//    public String getStatus() {
//        return Status;
//    }
//
//    public void setStatus(String status) {
//        Status = status;
//    }
//
//    public String getInTypeName() {
//        return InTypeName;
//    }
//
//    public void setInTypeName(String inTypeName) {
//        InTypeName = inTypeName;
//    }
//
//    public String getServicePic() {
//        return ServicePic;
//    }
//
//    public void setServicePic(String servicePic) {
//        ServicePic = servicePic;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iTotalCount);
        dest.writeString(this.iCount);
        dest.writeString(this.ServicePlanDetailsID);
        dest.writeString(this.Paramedic);
        dest.writeString(this.ElderInfoID);
        dest.writeString(this.ElderName);
        dest.writeString(this.BuildingNO);
        dest.writeString(this.RoomNumber);
        dest.writeString(this.BedNO);
        dest.writeString(this.BedID);
        dest.writeString(this.RoomTypeName);
        dest.writeString(this.ElderSex);
        dest.writeString(this.ElderAge);
        dest.writeString(this.ElderBirthday);
        dest.writeString(this.ElderIDNo);
        dest.writeString(this.ElderHouseAddress);
        dest.writeString(this.NurseLevelName);
        dest.writeString(this.Status);
        dest.writeString(this.InTypeName);
        dest.writeString(this.ServicePic);
        dest.writeString(this.IsDo);
        dest.writeString(this.UnDo);
    }

    protected NursingBean(Parcel in) {
        this.iTotalCount = in.readString();
        this.iCount = in.readString();
        this.ServicePlanDetailsID = in.readString();
        this.Paramedic = in.readString();
        this.ElderInfoID = in.readString();
        this.ElderName = in.readString();
        this.BuildingNO = in.readString();
        this.RoomNumber = in.readString();
        this.BedNO = in.readString();
        this.BedID = in.readString();
        this.RoomTypeName = in.readString();
        this.ElderSex = in.readString();
        this.ElderAge = in.readString();
        this.ElderBirthday = in.readString();
        this.ElderIDNo = in.readString();
        this.ElderHouseAddress = in.readString();
        this.NurseLevelName = in.readString();
        this.Status = in.readString();
        this.InTypeName = in.readString();
        this.ServicePic = in.readString();
        this.IsDo = in.readString();
        this.UnDo = in.readString();
    }

    public static final Creator<NursingBean> CREATOR = new Creator<NursingBean>() {
        @Override
        public NursingBean createFromParcel(Parcel source) {
            return new NursingBean(source);
        }

        @Override
        public NursingBean[] newArray(int size) {
            return new NursingBean[size];
        }
    };
}
