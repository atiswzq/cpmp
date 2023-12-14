package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class BidProjOnSplrTendInf {
    private Long id;

    private Long projId;

    private Long splrId;

    private String projNam;

    private String splrCtct;

    private String splrCtctTel;

    private String splrNam;

    private String dpstPic;

    private String dpstSts;

    private Long qotId;

    private Long qot2Id;

    private Integer qotCnt;

    private Integer qot2Cnt;

    private String memo;

    private String adtMemo;

    private String bidDocSts;

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

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getProjNam() {
        return projNam;
    }

    public void setProjNam(String projNam) {
        this.projNam = projNam;
    }

    public String getSplrCtct() {
        return splrCtct;
    }

    public void setSplrCtct(String splrCtct) {
        this.splrCtct = splrCtct;
    }

    public String getSplrCtctTel() {
        return splrCtctTel;
    }

    public void setSplrCtctTel(String splrCtctTel) {
        this.splrCtctTel = splrCtctTel;
    }

    public String getSplrNam() {
        return splrNam;
    }

    public void setSplrNam(String splrNam) {
        this.splrNam = splrNam;
    }

    public String getDpstPic() {
        return dpstPic;
    }

    public void setDpstPic(String dpstPic) {
        this.dpstPic = dpstPic;
    }

    public String getDpstSts() {
        return dpstSts;
    }

    public void setDpstSts(String dpstSts) {
        this.dpstSts = dpstSts;
    }

    public Long getQotId() {
        return qotId;
    }

    public void setQotId(Long qotId) {
        this.qotId = qotId;
    }

    public Long getQot2Id() {
        return qot2Id;
    }

    public void setQot2Id(Long qot2Id) {
        this.qot2Id = qot2Id;
    }

    public Integer getQotCnt() {
        return qotCnt;
    }

    public void setQotCnt(Integer qotCnt) {
        this.qotCnt = qotCnt;
    }

    public Integer getQot2Cnt() {
        return qot2Cnt;
    }

    public void setQot2Cnt(Integer qot2Cnt) {
        this.qot2Cnt = qot2Cnt;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAdtMemo() {
        return adtMemo;
    }

    public void setAdtMemo(String adtMemo) {
        this.adtMemo = adtMemo;
    }

    public String getBidDocSts() {
        return bidDocSts;
    }

    public void setBidDocSts(String bidDocSts) {
        this.bidDocSts = bidDocSts;
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