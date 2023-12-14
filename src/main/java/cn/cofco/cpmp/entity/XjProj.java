package cn.cofco.cpmp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class XjProj {
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

    private String bpmFinSeq;

    private String bpmRplSeq;

    private String openKey;

    private String qotCntTyp;

    private Timestamp bidOpenTim;

    private Timestamp bidEndTim;

    private String bidEndMemo;

    private String appRplMemo;

    private String bpmRplAdtMemo;

    private String appAwdMemo;

    private String bpmAwdAdtMemo;

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

    private String pubPriFlg;

    private String sutOrgIds;

    private String rplNtcInf;

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

    public String getRplNtcInf() {
        return rplNtcInf;
    }

    public void setRplNtcInf(String rplNtcInf) {
        this.rplNtcInf = rplNtcInf;
    }
}
