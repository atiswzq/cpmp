package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrAdmtChk {
    private Long admtChkId;

    private Long splrId;

    private String chkNam;

    private String chkUrl;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    private String effFlg;

    public Long getAdmtChkId() {
        return admtChkId;
    }

    public void setAdmtChkId(Long admtChkId) {
        this.admtChkId = admtChkId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getChkNam() {
        return chkNam;
    }

    public void setChkNam(String chkNam) {
        this.chkNam = chkNam;
    }

    public String getChkUrl() {
        return chkUrl;
    }

    public void setChkUrl(String chkUrl) {
        this.chkUrl = chkUrl;
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

    public String getEffFlg() {
        return effFlg;
    }

    public void setEffFlg(String effFlg) {
        this.effFlg = effFlg;
    }
}


