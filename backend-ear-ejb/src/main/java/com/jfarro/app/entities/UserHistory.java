package com.jfarro.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDate;

@Embeddable
public class UserHistory {

    @Column(name = "estado")
    private int state;

    @Column(name = "fecha_reg")
    private LocalDate dateReg;

    @Column(name = "user_reg")
    private String userReg;

    @Column(name = "fecha_mod")
    private LocalDate dateMod;

    @Column(name = "user_mod")
    private String userMod;

    @PrePersist
    public void prePersist() {
        this.dateReg = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dateMod = LocalDate.now();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LocalDate getDateReg() {
        return dateReg;
    }

    public void setDateReg(LocalDate dateReg) {
        this.dateReg = dateReg;
    }

    public String getUserReg() {
        return userReg;
    }

    public void setUserReg(String userReg) {
        this.userReg = userReg;
    }

    public LocalDate getDateMod() {
        return dateMod;
    }

    public void setDateMod(LocalDate dateMod) {
        this.dateMod = dateMod;
    }

    public String getUserMod() {
        return userMod;
    }

    public void setUserMod(String userMod) {
        this.userMod = userMod;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserHistory{");
        sb.append("state=").append(state);
        sb.append(", dateReg=").append(dateReg);
        sb.append(", userReg='").append(userReg).append('\'');
        sb.append(", dateMod=").append(dateMod);
        sb.append(", userMod='").append(userMod).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
