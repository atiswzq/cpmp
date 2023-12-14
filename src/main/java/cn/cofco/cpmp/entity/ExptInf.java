package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class ExptInf {
    private Long exptId;

    private String exptNam;

    private String mobNbr;

    private String exptPsw;

    private String exptTtl;

    private String matTyps;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    public Long getExptId() {
        return exptId;
    }

    public void setExptId(Long exptId) {
        this.exptId = exptId;
    }

    public String getExptNam() {
        return exptNam;
    }

    public void setExptNam(String exptNam) {
        this.exptNam = exptNam;
    }

    public String getMobNbr() {
        return mobNbr;
    }

    public void setMobNbr(String mobNbr) {
        this.mobNbr = mobNbr;
    }

    public String getExptPsw() {
        return exptPsw;
    }

    public void setExptPsw(String exptPsw) {
        this.exptPsw = exptPsw;
    }

    public String getExptTtl() {
        return exptTtl;
    }

    public void setExptTtl(String exptTtl) {
        this.exptTtl = exptTtl;
    }

    public String getMatTyps() {
        return matTyps;
    }

    public void setMatTyps(String matTyps) {
        this.matTyps = matTyps;
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