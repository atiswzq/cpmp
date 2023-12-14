package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class AptDef {
    private Long aptDefId;

    private String aptTyp;

    private String aptNam;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    private String defFlg;

    public Long getAptDefId() {
        return aptDefId;
    }

    public void setAptDefId(Long aptDefId) {
        this.aptDefId = aptDefId;
    }

    public String getAptTyp() {
        return aptTyp;
    }

    public void setAptTyp(String aptTyp) {
        this.aptTyp = aptTyp;
    }

    public String getAptNam() {
        return aptNam;
    }

    public void setAptNam(String aptNam) {
        this.aptNam = aptNam;
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

    public String getDefFlg() {
        return defFlg;
    }

    public void setDefFlg(String defFlg) {
        this.defFlg = defFlg;
    }
}