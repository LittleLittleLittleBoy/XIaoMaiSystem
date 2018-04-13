package cn.edu.nju.candleflame.tickets.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "distributionticket")
public class DistributionTicketEntity {

    @Id
    private String ticketid;
    @ManyToOne
    @JoinColumn(name = "showid", foreignKey = @ForeignKey(name = "none", value =ConstraintMode.NO_CONSTRAINT))
    private ShowInfoEntity show;
    @ManyToOne
    @JoinColumn(name = "userid", foreignKey = @ForeignKey(name = "none", value =ConstraintMode.NO_CONSTRAINT))
    private UserInfoEntity user;
    @Column
    private int seat1;
    @Column
    private int seat2;
    @Column
    private int seat3;
    @Column
    private Timestamp createtime;
    @Column
    private double price;
    @Column
    private boolean issuccess;
    @Column
    private boolean ispayed;
    @Column
    private boolean iscancel;

    public boolean isIscancel() {
        return iscancel;
    }

    public void setIscancel(boolean iscancel) {
        this.iscancel = iscancel;
    }

    public DistributionTicketEntity(){}

    public DistributionTicketEntity(String ticketid) {
        this.ticketid = ticketid;
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

    public int getSeat1() {
        return seat1;
    }

    public void setSeat1(int seat1) {
        this.seat1 = seat1;
    }

    public int getSeat2() {
        return seat2;
    }

    public void setSeat2(int seat2) {
        this.seat2 = seat2;
    }

    public int getSeat3() {
        return seat3;
    }

    public void setSeat3(int seat3) {
        this.seat3 = seat3;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
