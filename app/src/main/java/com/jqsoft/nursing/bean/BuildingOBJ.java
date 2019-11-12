package com.jqsoft.nursing.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/3.
 */

public class BuildingOBJ implements Serializable{
    private String BuildingID;
    private String BuildingName;
    private String BuildingNO;

    public BuildingOBJ() {
    }

    public BuildingOBJ(String buildingID, String buildingName, String buildingNO) {
        BuildingID = buildingID;
        BuildingName = buildingName;
        BuildingNO = buildingNO;
    }

    public String getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(String buildingID) {
        BuildingID = buildingID;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getBuildingNO() {
        return BuildingNO;
    }

    public void setBuildingNO(String buildingNO) {
        BuildingNO = buildingNO;
    }
}
