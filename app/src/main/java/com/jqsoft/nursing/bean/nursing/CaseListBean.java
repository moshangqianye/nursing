package com.jqsoft.nursing.bean.nursing;

/**
 * @author yedong
 * @date 2019/1/17
 * 病例记录数据model
 */
public class CaseListBean {

    private String MedicalHistoryID;
    private String FKElderInfoID;
    private String MedicalTime;  // 日期
    private String MedicalNumber;
    private String MedicalType; // 类型
    private String sMemo;  // 说明
    private String Pic;  // 图片地址

    public String getMedicalHistoryID() {
        return MedicalHistoryID;
    }

    public void setMedicalHistoryID(String medicalHistoryID) {
        MedicalHistoryID = medicalHistoryID;
    }

    public String getFKElderInfoID() {
        return FKElderInfoID;
    }

    public void setFKElderInfoID(String FKElderInfoID) {
        this.FKElderInfoID = FKElderInfoID;
    }

    public String getMedicalTime() {
        return MedicalTime;
    }

    public void setMedicalTime(String medicalTime) {
        MedicalTime = medicalTime;
    }

    public String getMedicalNumber() {
        return MedicalNumber;
    }

    public void setMedicalNumber(String medicalNumber) {
        MedicalNumber = medicalNumber;
    }

    public String getMedicalType() {
        return MedicalType;
    }

    public void setMedicalType(String medicalType) {
        MedicalType = medicalType;
    }

    public String getsMemo() {
        return sMemo;
    }

    public void setsMemo(String sMemo) {
        this.sMemo = sMemo;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }
}
