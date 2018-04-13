package cn.edu.nju.candleflame.tickets.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "moneyinfo")
public class MoneyInfoEntity {

    /**
     * type 0 顾客 1 商店 2 管理员
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "fromuser")
    String from;
    @Column
    int fromtype;
    @Column(name = "touser")
    String to;
    @Column
    int totype;
    @Column
    double money;
    @Column(insertable = false)
    Timestamp time=new Timestamp(new Date().getTime());

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getFromtype() {
        return fromtype;
    }

    public void setFromtype(int fromtype) {
        this.fromtype = fromtype;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getTotype() {
        return totype;
    }

    public void setTotype(int totype) {
        this.totype = totype;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public MoneyInfoEntity() {
    }

    public MoneyInfoEntity(String from, int fromtype, String to, int totype, double money) {
        this.from = from;
        this.fromtype = fromtype;
        this.to = to;
        this.totype = totype;
        this.money = money;
    }
}
