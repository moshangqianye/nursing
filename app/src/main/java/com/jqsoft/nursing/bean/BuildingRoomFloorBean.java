package com.jqsoft.nursing.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/27.
 */

public class BuildingRoomFloorBean implements Serializable{
    private BuildingOBJ Building;
    private ArrayList<FloorList> floor;

    public BuildingRoomFloorBean() {
    }

    public BuildingRoomFloorBean(BuildingOBJ building, ArrayList<FloorList> floor) {
        Building = building;
        this.floor = floor;
    }

    public BuildingOBJ getBuilding() {
        return Building;
    }

    public void setBuilding(BuildingOBJ building) {
        Building = building;
    }

    public ArrayList<FloorList> getFloor() {
        return floor;
    }

    public void setFloor(ArrayList<FloorList> floor) {
        this.floor = floor;
    }
}


