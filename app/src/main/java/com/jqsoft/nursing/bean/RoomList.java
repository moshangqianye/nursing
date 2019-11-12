package com.jqsoft.nursing.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/3.
 */

public class RoomList implements Serializable{
    private RoomOBJ Room;
    private ArrayList<BedList> Bed;

    public RoomList() {
    }

    public RoomList(RoomOBJ room, ArrayList<BedList> bed) {
        Room = room;
        Bed = bed;
    }

    public RoomOBJ getRoom() {
        return Room;
    }

    public void setRoom(RoomOBJ room) {
        Room = room;
    }

    public ArrayList<BedList> getBed() {
        return Bed;
    }

    public void setBed(ArrayList<BedList> bed) {
        Bed = bed;
    }
}
