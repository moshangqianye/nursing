package com.jqsoft.nursing.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/3.
 */

public class FloorOBJ implements Serializable {
    private String FloorID;
    private String FKBuildingID;
    private String FloorNO;

    public FloorOBJ() {
    }

    public FloorOBJ(String floorID, String FKBuildingID, String floorNO) {
        FloorID = floorID;
        this.FKBuildingID = FKBuildingID;
        FloorNO = floorNO;
    }

    public String getFloorID() {
        return FloorID;
    }

    public void setFloorID(String floorID) {
        FloorID = floorID;
    }

    public String getFKBuildingID() {
        return FKBuildingID;
    }

    public void setFKBuildingID(String FKBuildingID) {
        this.FKBuildingID = FKBuildingID;
    }

    public String getFloorNO() {
        return FloorNO;
    }

    public void setFloorNO(String floorNO) {
        FloorNO = floorNO;
    }
}
