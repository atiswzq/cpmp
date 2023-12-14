package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrAcntLgin {
    private Long lginId;

    private String usrNam;

    private Integer acntId;

    private Timestamp lginTim;

    private String lginIp;

    private String lginCity;

    private String lginSts;

    public Long getLginId() {
        return lginId;
    }

    public void setLginId(Long lginId) {
        this.lginId = lginId;
    }

    public String getUsrNam() {
        return usrNam;
    }

    public void setUsrNam(String usrNam) {
        this.usrNam = usrNam;
    }

    public Integer getAcntId() {
        return acntId;
    }

    public void setAcntId(Integer acntId) {
        this.acntId = acntId;
    }

    public Timestamp getLginTim() {
        return lginTim;
    }

    public void setLginTim(Timestamp lginTim) {
        this.lginTim = lginTim;
    }

    public String getLginIp() {
        return lginIp;
    }

    public void setLginIp(String lginIp) {
        this.lginIp = lginIp;
    }

    public String getLginCity() {
        return lginCity;
    }

    public void setLginCity(String lginCity) {
        this.lginCity = lginCity;
    }

    public String getLginSts() {
        return lginSts;
    }

    public void setLginSts(String lginSts) {
        this.lginSts = lginSts;
    }
}