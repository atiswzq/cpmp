package cn.cofco.cpmp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BidProjOn {
    private Long projId;

    private String projNbr;

    private String projNam;

    private String projRsps;

    private Long orgId;

    private String bidDptNam;

    private String bidDptAddr;

    private BigDecimal bidDpst;

    private String ctct;

    private String ctctTel;
    /*项目备注改成付款方式及其他*/
    private String projMemo;

    private String bidRngTyp;

    private String matTyp;

    private String projSts;

    private Timestamp stsUpdTim;

    private String bidTwcFlg;

    private String bpmBudSeq;

    private String bpmFinSeq;

    private String bpmRplSeq;

    private String openKey;

    private String qotCntTyp;

    private Timestamp bidOpenTim;

    private Timestamp bidEndTim;

    private String bidEndMemo;

    private Timestamp grdEndTim;

    private String grdRstMemo;

    private String bpmBudAdtMemo;

    private String appRplMemo;

    private String bpmRplAdtMemo;

    private String appAwdMemo;

    private String bpmAwdAdtMemo;

    private String qot2CntTyp;

    private Timestamp bidOpen2Tim;

    private Timestamp qot2EndTim;

    private String bidEndMemo2;

    private Timestamp grd2EndTim;

    private String grdRstMemo2;

    private String bidNtcPubFlg;

    private Timestamp ntcPubTim;

    private String bidRstPubFlg;

    private Timestamp rstPubTim;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    private String currTyp;

    private String dpstFlg;

    private String pubPriFlg;

    private String sutOrgIds;

    private String grdRul;

    private String rplNtcInf;

    /*监标人*/
    private String projSupv;

    /*监标人联系方式*/
    private String projSupvTel;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProjNbr() {
        return projNbr;
    }

    public void setProjNbr(String projNbr) {
        this.projNbr = projNbr;
    }

    public String getProjNam() {
        return projNam;
    }

    public void setProjNam(String projNam) {
        this.projNam = projNam;
    }

    public String getProjRsps() {
        return projRsps;
    }

    public void setProjRsps(String projRsps) {
        this.projRsps = projRsps;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getBidDptNam() {
        return bidDptNam;
    }

    public void setBidDptNam(String bidDptNam) {
        this.bidDptNam = bidDptNam;
    }

    public String getBidDptAddr() {
        return bidDptAddr;
    }

    public void setBidDptAddr(String bidDptAddr) {
        this.bidDptAddr = bidDptAddr;
    }

    public BigDecimal getBidDpst() {
        return bidDpst;
    }

    public void setBidDpst(BigDecimal bidDpst) {
        this.bidDpst = bidDpst;
    }

    public String getCtct() {
        return ctct;
    }

    public void setCtct(String ctct) {
        this.ctct = ctct;
    }

    public String getCtctTel() {
        return ctctTel;
    }

    public void setCtctTel(String ctctTel) {
        this.ctctTel = ctctTel;
    }

    public String getProjMemo() {
        return projMemo;
    }

    public void setProjMemo(String projMemo) {
        this.projMemo = projMemo;
    }

    public String getBidRngTyp() {
        return bidRngTyp;
    }

    public void setBidRngTyp(String bidRngTyp) {
        this.bidRngTyp = bidRngTyp;
    }

    public String getMatTyp() {
        return matTyp;
    }

    public void setMatTyp(String matTyp) {
        this.matTyp = matTyp;
    }

    public String getProjSts() {
        return projSts;
    }

    public void setProjSts(String projSts) {
        this.projSts = projSts;
    }

    public Timestamp getStsUpdTim() {
        return stsUpdTim;
    }

    public void setStsUpdTim(Timestamp stsUpdTim) {
        this.stsUpdTim = stsUpdTim;
    }

    public String getBidTwcFlg() {
        return bidTwcFlg;
    }

    public void setBidTwcFlg(String bidTwcFlg) {
        this.bidTwcFlg = bidTwcFlg;
    }

    public String getBpmBudSeq() {
        return bpmBudSeq;
    }

    public void setBpmBudSeq(String bpmBudSeq) {
        this.bpmBudSeq = bpmBudSeq;
    }

    public String getBpmFinSeq() {
        return bpmFinSeq;
    }

    public void setBpmFinSeq(String bpmFinSeq) {
        this.bpmFinSeq = bpmFinSeq;
    }

    public String getBpmRplSeq() {
        return bpmRplSeq;
    }

    public void setBpmRplSeq(String bpmRplSeq) {
        this.bpmRplSeq = bpmRplSeq;
    }

    public String getOpenKey() {
        return openKey;
    }

    public void setOpenKey(String openKey) {
        this.openKey = openKey;
    }

    public String getQotCntTyp() {
        return qotCntTyp;
    }

    public void setQotCntTyp(String qotCntTyp) {
        this.qotCntTyp = qotCntTyp;
    }

    public Timestamp getBidOpenTim() {
        return bidOpenTim;
    }

    public void setBidOpenTim(Timestamp bidOpenTim) {
        this.bidOpenTim = bidOpenTim;
    }

    public Timestamp getBidEndTim() {
        return bidEndTim;
    }

    public void setBidEndTim(Timestamp bidEndTim) {
        this.bidEndTim = bidEndTim;
    }

    public String getBidEndMemo() {
        return bidEndMemo;
    }

    public void setBidEndMemo(String bidEndMemo) {
        this.bidEndMemo = bidEndMemo;
    }

    public Timestamp getGrdEndTim() {
        return grdEndTim;
    }

    public void setGrdEndTim(Timestamp grdEndTim) {
        this.grdEndTim = grdEndTim;
    }

    public String getGrdRstMemo() {
        return grdRstMemo;
    }

    public void setGrdRstMemo(String grdRstMemo) {
        this.grdRstMemo = grdRstMemo;
    }

    public String getBpmBudAdtMemo() {
        return bpmBudAdtMemo;
    }

    public void setBpmBudAdtMemo(String bpmBudAdtMemo) {
        this.bpmBudAdtMemo = bpmBudAdtMemo;
    }

    public String getAppRplMemo() {
        return appRplMemo;
    }

    public void setAppRplMemo(String appRplMemo) {
        this.appRplMemo = appRplMemo;
    }

    public String getBpmRplAdtMemo() {
        return bpmRplAdtMemo;
    }

    public void setBpmRplAdtMemo(String bpmRplAdtMemo) {
        this.bpmRplAdtMemo = bpmRplAdtMemo;
    }

    public String getAppAwdMemo() {
        return appAwdMemo;
    }

    public void setAppAwdMemo(String appAwdMemo) {
        this.appAwdMemo = appAwdMemo;
    }

    public String getBpmAwdAdtMemo() {
        return bpmAwdAdtMemo;
    }

    public void setBpmAwdAdtMemo(String bpmAwdAdtMemo) {
        this.bpmAwdAdtMemo = bpmAwdAdtMemo;
    }

    public String getQot2CntTyp() {
        return qot2CntTyp;
    }

    public void setQot2CntTyp(String qot2CntTyp) {
        this.qot2CntTyp = qot2CntTyp;
    }

    public Timestamp getBidOpen2Tim() {
        return bidOpen2Tim;
    }

    public void setBidOpen2Tim(Timestamp bidOpen2Tim) {
        this.bidOpen2Tim = bidOpen2Tim;
    }

    public Timestamp getQot2EndTim() {
        return qot2EndTim;
    }

    public void setQot2EndTim(Timestamp qot2EndTim) {
        this.qot2EndTim = qot2EndTim;
    }

    public String getBidEndMemo2() {
        return bidEndMemo2;
    }

    public void setBidEndMemo2(String bidEndMemo2) {
        this.bidEndMemo2 = bidEndMemo2;
    }

    public Timestamp getGrd2EndTim() {
        return grd2EndTim;
    }

    public void setGrd2EndTim(Timestamp grd2EndTim) {
        this.grd2EndTim = grd2EndTim;
    }

    public String getGrdRstMemo2() {
        return grdRstMemo2;
    }

    public void setGrdRstMemo2(String grdRstMemo2) {
        this.grdRstMemo2 = grdRstMemo2;
    }

    public String getBidNtcPubFlg() {
        return bidNtcPubFlg;
    }

    public void setBidNtcPubFlg(String bidNtcPubFlg) {
        this.bidNtcPubFlg = bidNtcPubFlg;
    }

    public Timestamp getNtcPubTim() {
        return ntcPubTim;
    }

    public void setNtcPubTim(Timestamp ntcPubTim) {
        this.ntcPubTim = ntcPubTim;
    }

    public String getBidRstPubFlg() {
        return bidRstPubFlg;
    }

    public void setBidRstPubFlg(String bidRstPubFlg) {
        this.bidRstPubFlg = bidRstPubFlg;
    }

    public Timestamp getRstPubTim() {
        return rstPubTim;
    }

    public void setRstPubTim(Timestamp rstPubTim) {
        this.rstPubTim = rstPubTim;
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

    public String getCurrTyp() {
        return currTyp;
    }

    public void setCurrTyp(String currTyp) {
        this.currTyp = currTyp;
    }

    public String getDpstFlg() {
        return dpstFlg;
    }

    public void setDpstFlg(String dpstFlg) {
        this.dpstFlg = dpstFlg;
    }

    public String getPubPriFlg() {
        return pubPriFlg;
    }

    public void setPubPriFlg(String pubPriFlg) {
        this.pubPriFlg = pubPriFlg;
    }

    public String getSutOrgIds() {
        return sutOrgIds;
    }

    public void setSutOrgIds(String sutOrgIds) {
        this.sutOrgIds = sutOrgIds;
    }

    public String getGrdRul() {
        return grdRul;
    }

    public void setGrdRul(String grdRul) {
        this.grdRul = grdRul;
    }

    public String getRplNtcInf() {
        return rplNtcInf;
    }

    public void setRplNtcInf(String rplNtcInf) {
        this.rplNtcInf = rplNtcInf;
    }

    public String getProjSupv() {
        return projSupv;
    }

    public void setProjSupv(String projSupv) {
        this.projSupv = projSupv;
    }

    public String getProjSupvTel() {
        return projSupvTel;
    }

    public void setProjSupvTel(String projSupvTel) {
        this.projSupvTel = projSupvTel;
    }
}