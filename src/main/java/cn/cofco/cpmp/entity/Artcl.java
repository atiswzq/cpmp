package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class Artcl {
    private Long artclId;

    private String artclTypCod;

    private String artclTtl;

    private String pubFlg;

    private Long clkCnt;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    private String pubUsr;

    private Timestamp pubTim;

    private String artclCtt;

    public Long getArtclId() {
        return artclId;
    }

    public void setArtclId(Long artclId) {
        this.artclId = artclId;
    }

    public String getArtclTypCod() {
        return artclTypCod;
    }

    public void setArtclTypCod(String artclTypCod) {
        this.artclTypCod = artclTypCod;
    }

    public String getArtclTtl() {
        return artclTtl;
    }

    public void setArtclTtl(String artclTtl) {
        this.artclTtl = artclTtl;
    }

    public String getPubFlg() {
        return pubFlg;
    }

    public void setPubFlg(String pubFlg) {
        this.pubFlg = pubFlg;
    }

    public Long getClkCnt() {
        return clkCnt;
    }

    public void setClkCnt(Long clkCnt) {
        this.clkCnt = clkCnt;
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

    public String getPubUsr() {
        return pubUsr;
    }

    public void setPubUsr(String pubUsr) {
        this.pubUsr = pubUsr;
    }

    public Timestamp getPubTim() {
        return pubTim;
    }

    public void setPubTim(Timestamp pubTim) {
        this.pubTim = pubTim;
    }

    public String getArtclCtt() {
        return artclCtt;
    }

    public void setArtclCtt(String artclCtt) {
        this.artclCtt = artclCtt;
    }
}