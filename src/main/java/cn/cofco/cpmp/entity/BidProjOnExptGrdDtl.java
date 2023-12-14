package cn.cofco.cpmp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BidProjOnExptGrdDtl {
    private Long id;

    private Long exptId;

    private Long bidAppId;

    private Long grdId;

    private Long projId;

    private String projNam;

    private Long splrId;

    private String splrNam;

    private BigDecimal scr;

    private String scrMemo;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExptId() {
        return exptId;
    }

    public void setExptId(Long exptId) {
        this.exptId = exptId;
    }

    public Long getBidAppId() {
        return bidAppId;
    }

    public void setBidAppId(Long bidAppId) {
        this.bidAppId = bidAppId;
    }

    public Long getGrdId() {
        return grdId;
    }

    public void setGrdId(Long grdId) {
        this.grdId = grdId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProjNam() {
        return projNam;
    }

    public void setProjNam(String projNam) {
        this.projNam = projNam;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getSplrNam() {
        return splrNam;
    }

    public void setSplrNam(String splrNam) {
        this.splrNam = splrNam;
    }

    public BigDecimal getScr() {
        return scr;
    }

    public void setScr(BigDecimal scr) {
        this.scr = scr;
    }

    public String getScrMemo() {
        return scrMemo;
    }

    public void setScrMemo(String scrMemo) {
        this.scrMemo = scrMemo;
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