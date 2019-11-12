package com.jqsoft.nursing.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/3.
 */

public class FloorList implements Serializable{
    private FloorOBJ Floor;
    private ArrayList<RoomList> Room;

    public FloorList() {
    }

    public FloorList(FloorOBJ floor, ArrayList<RoomList> room) {
        Floor = floor;
        Room = room;
    }

    public FloorOBJ getFloor() {
        return Floor;
    }

    public void setFloor(FloorOBJ floor) {
        Floor = floor;
    }

    public ArrayList<RoomList> getRoom() {
        return Room;
    }

    public void setRoom(ArrayList<RoomList> room) {
        Room = room;
    }


}
