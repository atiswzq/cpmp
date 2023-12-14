package cn.cofco.cpmp.entity;

import java.sql.Timestamp;
import java.util.Date;

public class SplrAcnt {
    private Long acntId;

    private Long splrId;

    private String acntTyp;

    private String usrNam;

    private String passwd;

    private String nam;

    private String mob;

    private String dffFlg;

    private Long crtUsr;

    private Timestamp crtTim;

    private Long modUsr;

    private Timestamp modTim;

    private String accessToken;

    private Date tokenTim;

    public Long getAcntId() {
        return acntId;
    }

    public void setAcntId(Long acntId) {
        this.acntId = acntId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getAcntTyp() {
        return acntTyp;
    }

    public void setAcntTyp(String acntTyp) {
        this.acntTyp = acntTyp;
    }

    public String getUsrNam() {
        return usrNam;
    }

    public void setUsrNam(String usrNam) {
        this.usrNam = usrNam;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getDffFlg() {
        return dffFlg;
    }

    public void setDffFlg(String dffFlg) {
        this.dffFlg = dffFlg;
    }

    public Long getCrtUsr() {
        return crtUsr;
    }

    public void setCrtUsr(Long crtUsr) {
        this.crtUsr = crtUsr;
    }

    public Timestamp getCrtTim() {
        return crtTim;
    }

    public void setCrtTim(Timestamp crtTim) {
        this.crtTim = crtTim;
    }

    public Long getModUsr() {
        return modUsr;
    }

    public void setModUsr(Long modUsr) {
        this.modUsr = modUsr;
    }

    public Timestamp getModTim() {
        return modTim;
    }

    public void setModTim(Timestamp modTim) {
        this.modTim = modTim;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getTokenTim() {
        return tokenTim;
    }

    public void setTokenTim(Date tokenTim) {
        this.tokenTim = tokenTim;
    }
}