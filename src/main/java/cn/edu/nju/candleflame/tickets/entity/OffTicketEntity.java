package cn.edu.nju.candleflame.tickets.entity;

import javax.persistence.*;

@Entity(name = "offticket")
public class OffTicketEntity {
    @Id
    String ticketid;
    @Column
    String position;
    @ManyToOne
    @JoinColumn(name = "showid", foreignKey = @ForeignKey(name = "none", value =ConstraintMode.NO_CONSTRAINT))
    ShowInfoEntity show;

    public OffTicketEntity() {
    }

    public OffTicketEntity(String ticketid, String position, ShowInfoEntity show) {
        this.ticketid = ticketid;
        this.position = position;
        this.show = show;
    }

    public String getTicketid() {
        return ticketid;
    }

    public void setTicketid(String ticketid) {
        this.ticketid = ticketid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ShowInfoEntity getShow() {
        return show;
    }

    public void setShow(ShowInfoEntity show) {
        this.show = show;
    }
}
