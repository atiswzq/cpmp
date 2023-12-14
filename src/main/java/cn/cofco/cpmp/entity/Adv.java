package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class Adv {
    private Long advId;

    private String advNam;

    private String advPic;

    private String advLnk;

    private Integer disOdr;

    private String advTyp;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    public Long getAdvId() {
        return advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public String getAdvNam() {
        return advNam;
    }

    public void setAdvNam(String advNam) {
        this.advNam = advNam;
    }

    public String getAdvPic() {
        return advPic;
    }

    public void setAdvPic(String advPic) {
        this.advPic = advPic;
    }

    public String getAdvLnk() {
        return advLnk;
    }

    public void setAdvLnk(String advLnk) {
        this.advLnk = advLnk;
    }

    public Integer getDisOdr() {
        return disOdr;
    }

    public void setDisOdr(Integer disOdr) {
        this.disOdr = disOdr;
    }

    public String getAdvTyp() {
        return advTyp;
    }

    public void setAdvTyp(String advTyp) {
        this.advTyp = advTyp;
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