package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class BidProjOnSplrQotInf {
    private Long id;

    private String qotTyp;

    private Long projId;

    private Long bidId;

    private String servPrms;

    private String qotMemo;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    private Long splrId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQotTyp() {
        return qotTyp;
    }

    public void setQotTyp(String qotTyp) {
        this.qotTyp = qotTyp;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public String getServPrms() {
        return servPrms;
    }

    public void setServPrms(String servPrms) {
        this.servPrms = servPrms;
    }

    public String getQotMemo() {
        return qotMemo;
    }

    public void setQotMemo(String qotMemo) {
        this.qotMemo = qotMemo;
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

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }
}