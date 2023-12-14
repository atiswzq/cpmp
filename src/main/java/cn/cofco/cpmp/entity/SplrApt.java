package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrApt {
    private Long aptId;

    private Long splrId;

    private Long aptDefId;

    private String aptNam;

    private Timestamp aptInTim;

    private String crfcOgnz;

    private Integer adtUsr;

    private Timestamp adtTim;

    private String adtSts;

    private String dffFlg;

    private Long crtUsr;

    private Timestamp crtTim;

    private Long modUsr;

    private Timestamp modTim;

    private Long splrAptId;

    public Long getAptId() {
        return aptId;
    }

    public void setAptId(Long aptId) {
        this.aptId = aptId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public Long getAptDefId() {
        return aptDefId;
    }

    public void setAptDefId(Long aptDefId) {
        this.aptDefId = aptDefId;
    }

    public String getAptNam() {
        return aptNam;
    }

    public void setAptNam(String aptNam) {
        this.aptNam = aptNam;
    }

    public Timestamp getAptInTim() {
        return aptInTim;
    }

    public void setAptInTim(Timestamp aptInTim) {
        this.aptInTim = aptInTim;
    }

    public String getCrfcOgnz() {
        return crfcOgnz;
    }

    public void setCrfcOgnz(String crfcOgnz) {
        this.crfcOgnz = crfcOgnz;
    }

    public Integer getAdtUsr() {
        return adtUsr;
    }

    public void setAdtUsr(Integer adtUsr) {
        this.adtUsr = adtUsr;
    }

    public Timestamp getAdtTim() {
        return adtTim;
    }

    public void setAdtTim(Timestamp adtTim) {
        this.adtTim = adtTim;
    }

    public String getAdtSts() {
        return adtSts;
    }

    public void setAdtSts(String adtSts) {
        this.adtSts = adtSts;
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

    public Long getSplrAptId() {
        return splrAptId;
    }

    public void setSplrAptId(Long splrAptId) {
        this.splrAptId = splrAptId;
    }
}