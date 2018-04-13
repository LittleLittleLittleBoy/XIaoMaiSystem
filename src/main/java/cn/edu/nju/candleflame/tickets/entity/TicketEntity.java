package cn.edu.nju.candleflame.tickets.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name="ticket")
public class TicketEntity {
    @Id
    String ticketid;
    @ManyToOne
    @JoinColumn(name = "showid", foreignKey = @ForeignKey(name = "none", value =ConstraintMode.NO_CONSTRAINT))
    ShowInfoEntity show;
    @ManyToOne
    @JoinColumn(name = "userid", foreignKey = @ForeignKey(name = "none", value =ConstraintMode.NO_CONSTRAINT))
    UserInfoEntity user;
    @Column
    String position;
    @Column
    double price;
    @Column
    Timestamp createtime;
    @Column
    int number;
    @Column
    boolean issuccess=false;
    @Column
    boolean iscancel=false;
    @Column
    boolean ispayed=false;


    public boolean isIscancel() {
        return iscancel;
    }

    public void setIscancel(boolean iscancel) {
        this.iscancel = iscancel;
    }

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }

    public ShowInfoEntity getShow() {
        return show;
    }

    public void setShow(ShowInfoEntity show) {
        this.show = show;
    }

    public UserInfoEntity getUser() {
        return user;
    }

    public void setUser(UserInfoEntity user) {
        this.user = user;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isIssuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public boolean isIspayed() {
        return ispayed;
    }

    public void setIspayed(boolean ispayed) {
        this.ispayed = ispayed;
    }
}
