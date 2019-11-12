package com.jqsoft.nursing.bean.resident;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-08-16.
 * 签约居民端应用程序登录后返回的bean
 */

public class SRCLoginBean implements Serializable {
    private static final long serialVersionUID = 2421263553592651152L;

    private String UserName;
    private String TrueName;
    private String UserID;
    private String AllowWriteNFC;
    private String sOrgName;
    private String EmployeeName;
    private String EmployeeSexCode;
    private String EmployeeSex;
    private String EmployeeFloor;
    private String HeadImg;
    private String IsDean;

    public String mymac;

    public String getMymac() {
        return mymac;
    }

    public void setMymac(String mymac) {
        this.mymac = mymac;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String trueName) {
        TrueName = trueName;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getAllowWriteNFC() {
        return AllowWriteNFC;
    }

    public void setAllowWriteNFC(String allowWriteNFC) {
        AllowWriteNFC = allowWriteNFC;
    }

    public String getsOrgName() {
        return sOrgName;
    }

    public void setsOrgName(String sOrgName) {
        this.sOrgName = sOrgName;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmployeeSexCode() {
        return EmployeeSexCode;
    }

    public void setEmployeeSexCode(String employeeSexCode) {
        EmployeeSexCode = employeeSexCode;
    }

    public String getEmployeeSex() {
        return EmployeeSex;
    }

    public void setEmployeeSex(String employeeSex) {
        EmployeeSex = employeeSex;
    }

    public String getEmployeeFloor() {
        return EmployeeFloor;
    }

    public void setEmployeeFloor(String employeeFloor) {
        EmployeeFloor = employeeFloor;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public void setHeadImg(String headImg) {
        HeadImg = headImg;
    }

    public String getIsDean() {
        return IsDean;
    }

    public void setIsDean(String isDean) {
        IsDean = isDean;
    }

/*  private String personName;//                     用户名
    private String sexName;//                      性别
    private String cardNo;//                        身份证号
    private String phone;//                         签约人联系电话
    private String personID;//                      个人健康档案唯一标识符
    private String no;//                            健康档案号
    private String isHypertensionState;//              是否高血压患者
    private String isType2DiabetesState;//             是否糖尿病患者
    private String isPsychosisState;//                  是否精神病患者
    private String isOldPeopleInfoState;//             是否老年人
    private String isPoorState;//                      是否贫困人口
    private String isChildrenInfoState;
    private String isPregnantwomenInfoState;
    private String areaCode;//                       地区编码
    private String costName;//                       医保名称
    private String address;
    private String id;
    private String photoUrl;
    private String age;

    private List<FamilyMemberListBean> familyMemberList;    //家庭成员列表*/

    private String area;
    private String areaId;
    private String gpyType;
    private String id;
    private String loginFlageEnum;
    private String mobiePhone;
    private String openid;
    private String passWord;
    private String rcType;
    private String realName;
    private String role;
    private String roleId;
    private String sort;

    private String status;
    private String telePhone;
    private String unit;
    private String unitCode;
    private String updateOr;
    private String userName;
    private String workPosition;



    //下面这两个变量不是服务端返回的----------------------
    public String loginSuccessUsername;
    public String loginSuccessPassword;
    private String areaLevel;
    //上面这两个变量不是服务端返回的----------------------

    public SRCLoginBean() {
        super();
    }



    public String getLoginSuccessUsername() {
        return loginSuccessUsername;
    }

    public void setLoginSuccessUsername(String loginSuccessUsername) {
        this.loginSuccessUsername = loginSuccessUsername;
    }

    public String getLoginSuccessPassword() {
        return loginSuccessPassword;
    }

    public void setLoginSuccessPassword(String loginSuccessPassword) {
        this.loginSuccessPassword = loginSuccessPassword;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getGpyType() {
        return gpyType;
    }

    public void setGpyType(String gpyType) {
        this.gpyType = gpyType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginFlageEnum() {
        return loginFlageEnum;
    }

    public void setLoginFlageEnum(String loginFlageEnum) {
        this.loginFlageEnum = loginFlageEnum;
    }

    public String getMobiePhone() {
        return mobiePhone;
    }

    public void setMobiePhone(String mobiePhone) {
        this.mobiePhone = mobiePhone;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRcType() {
        return rcType;
    }

    public void setRcType(String rcType) {
        this.rcType = rcType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUpdateOr() {
        return updateOr;
    }

    public void setUpdateOr(String updateOr) {
        this.updateOr = updateOr;
    }

//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }


    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }
}
