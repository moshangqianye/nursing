package com.jqsoft.nursing.bean.nursing;

import java.io.Serializable;

/**
 * 登录人护理老人信息
 * Created by Administrator on 2018-04-02.
 */

public class ChildDataBean implements Serializable {
    private String WardRoundID;	//总数据条数
    private String ParentID;	//当前数据排序
    private String WardRoundName;	//任务主键
    private String WardRoundType;	//护理员
    private String WardRoundTypeCode;	//老人主键
    private String Status;	//老人名称
    private String IsFillout;	//楼栋号
    private String Precedence;	//房间号
    private String MultipleChoice;	//床位号

    public boolean isChecked1;
    public boolean isChecked2;
    public boolean isChecked3;
    public boolean isChecked4;
    public boolean isChecked5;
    public boolean isChecked6;
    public boolean isChecked7;
    public boolean isChecked8;
    public boolean isChecked9;
    public boolean isChecked10;

    public boolean flag;

    public String selectedRb;


    public ChildDataBean() {
        super();
    }

    public ChildDataBean(String wardRoundID, String parentID, String wardRoundName, String wardRoundType, String wardRoundTypeCode, String status, String isFillout, String precedence, String multipleChoice, String children) {
        super();
        WardRoundID = wardRoundID;
        ParentID = parentID;
        WardRoundName = wardRoundName;
        WardRoundType = wardRoundType;
        WardRoundTypeCode = wardRoundTypeCode;
        Status = status;
        IsFillout = isFillout;
        Precedence = precedence;
        MultipleChoice = multipleChoice;

    }

    public String getWardRoundID() {
        return WardRoundID;
    }

    public void setWardRoundID(String wardRoundID) {
        WardRoundID = wardRoundID;
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String parentID) {
        ParentID = parentID;
    }

    public String getWardRoundName() {
        return WardRoundName;
    }

    public void setWardRoundName(String wardRoundName) {
        WardRoundName = wardRoundName;
    }

    public String getWardRoundType() {
        return WardRoundType;
    }

    public void setWardRoundType(String wardRoundType) {
        WardRoundType = wardRoundType;
    }

    public String getWardRoundTypeCode() {
        return WardRoundTypeCode;
    }

    public void setWardRoundTypeCode(String wardRoundTypeCode) {
        WardRoundTypeCode = wardRoundTypeCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getIsFillout() {
        return IsFillout;
    }

    public void setIsFillout(String isFillout) {
        IsFillout = isFillout;
    }

    public String getPrecedence() {
        return Precedence;
    }

    public void setPrecedence(String precedence) {
        Precedence = precedence;
    }

    public String getMultipleChoice() {
        return MultipleChoice;
    }

    public void setMultipleChoice(String multipleChoice) {
        MultipleChoice = multipleChoice;
    }

    public boolean isChecked1() {
        return isChecked1;
    }

    public void setChecked1(boolean checked1) {
        isChecked1 = checked1;
    }

    public boolean isChecked2() {
        return isChecked2;
    }

    public void setChecked2(boolean checked2) {
        isChecked2 = checked2;
    }

    public boolean isChecked3() {
        return isChecked3;
    }

    public void setChecked3(boolean checked3) {
        isChecked3 = checked3;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getSelectedRb() {
        return selectedRb;
    }

    public void setSelectedRb(String selectedRb) {
        this.selectedRb = selectedRb;
    }

    public boolean isChecked4() {
        return isChecked4;
    }

    public void setChecked4(boolean checked4) {
        isChecked4 = checked4;
    }

    public boolean isChecked5() {
        return isChecked5;
    }

    public void setChecked5(boolean checked5) {
        isChecked5 = checked5;
    }

    public boolean isChecked6() {
        return isChecked6;
    }

    public void setChecked6(boolean checked6) {
        isChecked6 = checked6;
    }

    public boolean isChecked7() {
        return isChecked7;
    }

    public void setChecked7(boolean checked7) {
        isChecked7 = checked7;
    }

    public boolean isChecked8() {
        return isChecked8;
    }

    public void setChecked8(boolean checked8) {
        isChecked8 = checked8;
    }

    public boolean isChecked9() {
        return isChecked9;
    }

    public void setChecked9(boolean checked9) {
        isChecked9 = checked9;
    }

    public boolean isChecked10() {
        return isChecked10;
    }

    public void setChecked10(boolean checked10) {
        isChecked10 = checked10;
    }
}
