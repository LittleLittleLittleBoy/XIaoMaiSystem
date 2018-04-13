package cn.edu.nju.candleflame.tickets.entity;

import java.io.Serializable;

public class RoomEntityPK implements Serializable {
    String theaterid;
    String roomid;

    public RoomEntityPK() {
    }

    public String getTheaterid() {
        return theaterid;
    }

    public void setTheaterid(String theaterid) {
        this.theaterid = theaterid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }
}
