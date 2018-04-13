package cn.edu.nju.candleflame.tickets.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "login")
public class LoginEntity {
    @Id
    String email;

    @Column
    String pass;

    @Column
    int type=0;

    @Column
    boolean isok=false;

    @Column
    String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isIsok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LoginEntity(String email,String pass, int type, boolean isok, String code) {
        this.email=email;
        this.pass = pass;
        this.type = type;
        this.isok = isok;
        this.code = code;
    }

    public LoginEntity() {
    }
}
