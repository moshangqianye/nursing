package com.jqsoft.nursing.bean.nursing;



import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录人护理老人信息
 * Created by Administrator on 2018-04-02.
 */

public class SaveRoundDataBean extends LitePalSupport implements Serializable {
    public String wardItemID;	//总数据条数
    public String wardItemName;	//任务主键
    public String wardOptionID;	//护理员
    public String fKElderInfoID;	//老人主键
    public String wardOptionName;	//老人名称

    public String isFillout;	//老人名称


    public SaveRoundDataBean() {
        super();
    }

    public String getWardItemID() {
        return wardItemID;
    }

    public void setWardItemID(String wardItemID) {
        this.wardItemID = wardItemID;
    }

    public String getWardItemName() {
        return wardItemName;
    }

    public void setWardItemName(String wardItemName) {
        this.wardItemName = wardItemName;
    }

    public String getWardOptionID() {
        return wardOptionID;
    }

    public void setWardOptionID(String wardOptionID) {
        this.wardOptionID = wardOptionID;
    }

    public String getfKElderInfoID() {
        return fKElderInfoID;
    }

    public void setfKElderInfoID(String fKElderInfoID) {
        this.fKElderInfoID = fKElderInfoID;
    }

    public String getWardOptionName() {
        return wardOptionName;
    }

    public void setWardOptionName(String wardOptionName) {
        this.wardOptionName = wardOptionName;
    }

    public String getIsFillout() {
        return isFillout;
    }

    public void setIsFillout(String isFillout) {
        this.isFillout = isFillout;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaveRoundDataBean person = (SaveRoundDataBean) o;

        if (!wardOptionID.equals(person.wardOptionID)) return false;
        return wardOptionName.equals(person.wardOptionName);

    }

    @Override
    public int hashCode() {
        int result = wardOptionID.hashCode();
        result = 31 * result + wardOptionName.hashCode();
        return result;
    }
}
