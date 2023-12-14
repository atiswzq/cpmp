package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class Atch {
    private Long atchId;

    private String refId;

    private String atchNam;

    private String atchUrl;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    public Long getAtchId() {
        return atchId;
    }

    public void setAtchId(Long atchId) {
        this.atchId = atchId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getAtchNam() {
        return atchNam;
    }

    public void setAtchNam(String atchNam) {
        this.atchNam = atchNam;
    }

    public String getAtchUrl() {
        return atchUrl;
    }

    public void setAtchUrl(String atchUrl) {
        this.atchUrl = atchUrl;
    }

    public String getEffFlg() {
        return effFlg;
    }

    public void setEffFlg(String effFlg) {
        this.effFlg = effFlg;
    }

    public String getCrtUsr() {
        return crtUsr;
    }

    public void setCrtUsr(String crtUsr) {
        this.crtUsr = crtUsr;
    }

    public Timestamp getCrtTim() {
        return crtTim;
    }

    public void setCrtTim(Timestamp crtTim) {
        this.crtTim = crtTim;
    }

    public String getModUsr() {
        return modUsr;
    }

    public void setModUsr(String modUsr) {
        this.modUsr = modUsr;
    }

    public Timestamp getModTim() {
        return modTim;
    }

    public void setModTim(Timestamp modTim) {
        this.modTim = modTim;
    }
}