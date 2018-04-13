package cn.edu.nju.candleflame.tickets.entity;

import org.springframework.stereotype.Controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "theater")
public class TheaterEntity {
    @Id
    String theaterid;
    @Column
    String name;
    @Column
    String place;
    @Column
    double money=0;
    @Column
    String email;

    public String getTheaterid() {
        return theaterid;
    }

    public void setTheaterid(String theaterid) {
        this.theaterid = theaterid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TheaterEntity(String theaterid) {
        this.theaterid = theaterid;
    }
    public TheaterEntity() {
    }
}
