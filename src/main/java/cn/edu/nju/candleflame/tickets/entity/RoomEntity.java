package cn.edu.nju.candleflame.tickets.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity(name = "room")
@IdClass(RoomEntityPK.class)
public class RoomEntity{
    @Id
    String theaterid;
    @Id
    String roomid;
    @Column
    String roominfo;
    @Column
    int row;
    @Column
    int col;
    @Column
    boolean isdelete=false;

    public RoomEntity() {
    }

    public RoomEntity(String theaterid, String roomid, String roominfo, int row, int col) {
        this.theaterid = theaterid;
        this.roomid = roomid;
        this.roominfo = roominfo;
        this.row = row;
        this.col = col;
    }

    public boolean isIsdelete() {
        return isdelete;
    }

    public void setIsdelete(boolean isdelete) {
        this.isdelete = isdelete;
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

    public String getRoominfo() {
        return roominfo;
    }

    public void setRoominfo(String roominfo) {
        this.roominfo = roominfo;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
