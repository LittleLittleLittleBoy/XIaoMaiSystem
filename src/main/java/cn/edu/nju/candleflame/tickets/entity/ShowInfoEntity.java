package cn.edu.nju.candleflame.tickets.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "showinfo")
public class ShowInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int showid;
    @ManyToOne
    @JoinColumn(name = "theaterid", foreignKey = @ForeignKey(name = "none", value =ConstraintMode.NO_CONSTRAINT))
    TheaterEntity theater;
    @Column
    String roomid;
    @Column
    String title;
    @Column
    double price1;
    @Column
    double price2;
    @Column
    double price3;
    @Column
    String type;
    @Column
    Timestamp time;
    @Column
    String description;

    public int getShowid() {
        return showid;
    }

    public void setShowid(int showid) {
        this.showid = showid;
    }

    public TheaterEntity getTheater() {
        return theater;
    }

    public void setTheater(TheaterEntity theater) {
        this.theater = theater;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice1() {
        return price1;
    }

    public void setPrice1(double price1) {
        this.price1 = price1;
    }

    public double getPrice2() {
        return price2;
    }

    public void setPrice2(double price2) {
        this.price2 = price2;
    }

    public double getPrice3() {
        return price3;
    }

    public void setPrice3(double price3) {
        this.price3 = price3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
