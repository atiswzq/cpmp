package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class LnkInf {
    private Long lnkId;

    private String lnkNam;

    private String lnkUrl;

    private String lnkPic;

    private String pubFlg;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    public Long getLnkId() {
        return lnkId;
    }

    public void setLnkId(Long lnkId) {
        this.lnkId = lnkId;
    }

    public String getLnkNam() {
        return lnkNam;
    }

    public void setLnkNam(String lnkNam) {
        this.lnkNam = lnkNam;
    }

    public String getLnkUrl() {
        return lnkUrl;
    }

    public void setLnkUrl(String lnkUrl) {
        this.lnkUrl = lnkUrl;
    }

    public String getLnkPic() {
        return lnkPic;
    }

    public void setLnkPic(String lnkPic) {
        this.lnkPic = lnkPic;
    }

    public String getPubFlg() {
        return pubFlg;
    }

    public void setPubFlg(String pubFlg) {
        this.pubFlg = pubFlg;
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

    @Override
    public String toString() {
        return "LnkInf{" +
                "lnkId=" + lnkId +
                ", lnkNam='" + lnkNam + '\'' +
                ", lnkUrl='" + lnkUrl + '\'' +
                ", lnkPic='" + lnkPic + '\'' +
                ", pubFlg='" + pubFlg + '\'' +
                ", effFlg='" + effFlg + '\'' +
                ", crtUsr='" + crtUsr + '\'' +
                ", crtTim=" + crtTim +
                ", modUsr='" + modUsr + '\'' +
                ", modTim=" + modTim +
                '}';
    }
}