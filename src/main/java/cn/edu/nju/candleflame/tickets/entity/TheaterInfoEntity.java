package cn.edu.nju.candleflame.tickets.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity(name = "theaterinfo")
public class TheaterInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column
    boolean isread=false;
    @Column
    boolean isok=false;
    /**
     * 0 表示注册
     * 1 表示修改信息
     * 2 添加房间信息
     * 3 删除房间信息
     */
    @Column
    int type;
    @Column
    String theaterid;
    @Column
    String message;
    @Column
    Timestamp time=new Timestamp(new java.util.Date().getTime());

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsread() {
        return isread;
    }

    public void setIsread(boolean isread) {
        this.isread = isread;
    }

    public boolean isIsok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }

    public String getTheaterid() {
        return theaterid;
    }

    public void setTheaterid(String theaterid) {
        this.theaterid = theaterid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
