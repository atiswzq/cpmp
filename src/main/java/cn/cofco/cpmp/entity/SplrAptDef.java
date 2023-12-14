package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrAptDef {
    private Long splrAptId;

    private String splrTyp;

    private Integer aptDefId;

    private String require;

    private String dffFlg;

    private Long crtUsr;

    private Timestamp crtTim;

    private Long modUsr;

    private Timestamp modTim;

    public Long getSplrAptId() {
        return splrAptId;
    }

    public void setSplrAptId(Long splrAptId) {
        this.splrAptId = splrAptId;
    }

    public String getSplrTyp() {
        return splrTyp;
    }

    public void setSplrTyp(String splrTyp) {
        this.splrTyp = splrTyp;
    }

    public Integer getAptDefId() {
        return aptDefId;
    }

    public void setAptDefId(Integer aptDefId) {
        this.aptDefId = aptDefId;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
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
}