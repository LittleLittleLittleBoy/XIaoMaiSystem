package cn.edu.nju.candleflame.tickets.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userInfo")
public class UserInfoEntity {
    @Id
    String email;
    @Column
    String name="默认用户";
    @Column
    int score=0;
    @Column
    double money=0;
    @Column(name = "ismember")
    boolean isMember=true;
    @Column
    int total=0;

    public UserInfoEntity() {
    }

    public UserInfoEntity(String email, String name, int score, double money, boolean isMember) {
        this.email = email;
        this.name = name;
        this.score = score;
        this.money = money;
        this.isMember = isMember;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }
}
