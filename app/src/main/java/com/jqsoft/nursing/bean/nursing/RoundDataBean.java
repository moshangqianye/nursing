package com.jqsoft.nursing.bean.nursing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录人护理老人信息
 * Created by Administrator on 2018-04-02.
 */

public class RoundDataBean implements Serializable {
    private String WardRoundID;	//总数据条数
    private String ParentID;	//当前数据排序
    private String WardRoundName;	//任务主键
    private String WardRoundType;	//护理员
    private String WardRoundTypeCode;	//老人主键
    private String Status;	//老人名称
    private String IsFillout;	//楼栋号
    private String Precedence;	//房间号
    private String MultipleChoice;	//床位号


    private List<ChildDataBean> Children =new ArrayList<>();

    public RoundDataBean() {
        super();
    }

    public RoundDataBean(String wardRoundID, String parentID, String wardRoundName, String wardRoundType, String wardRoundTypeCode, String status, String isFillout, String precedence, String multipleChoice, ArrayList<ChildDataBean> children) {
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
        Children = children;
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

    public List<ChildDataBean> getChildren() {
        return Children;
    }

    public void setChildren(ArrayList<ChildDataBean> children) {
        Children = children;
    }
}
