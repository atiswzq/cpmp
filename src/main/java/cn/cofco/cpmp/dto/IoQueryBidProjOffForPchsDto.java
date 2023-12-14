package cn.cofco.cpmp.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Tao on 2017/6/11.
 */
public class IoQueryBidProjOffForPchsDto {

    /**
     * 项目ID - 可空
     */
    private Long projId;

    /**
     * 项目编号 - 可空（模糊匹配）
     */
    private String projNbr;

    /**
     * 项目名称 - 可空（模糊匹配）
     */
    private String projNam;

    /**
     * 项目负责人 - 可空
     */
    private String projRsps;

    /**
     * 工厂ID - 可空
     */
    private Long orgId;

    /**
     * 招标单位名称 - 可空
     */
    private String bidDpt;

    /**
     * 招标单位地址 - 可空
     */
    private String bidDptAddr;

    /**
     * 投标保证金 从 - 可空
     */
    private BigDecimal bidDpstFrom;

    /**
     * 投标保证金 到 - 可空
     */
    private BigDecimal bidDpstTo;

    /**
     * 联系人 - 可空
     */
    private String ctct;

    /**
     * 联系人电话 - 可空
     */
    private String ctctTel;

    /**
     * 投标截止时间 从 - 可空
     */
    private Timestamp bidEndTimFrom;

    /**
     * 投标截止时间 到 - 可空
     */
    private Timestamp bidEndTimTo;

    /**
     * 招标范围类型：0-非定向招标；1-定向招标 - 可空
     */
    private String bidRngTyp;

    /**
     * 物料品类 - 可空
     */
    private String matTyp;

    /**
     * 项目状态 - 可空
     */
    private String projSts;

    /**
     * 招标公告发布标识 - 可空
     */
    private String bidNtcPubFlg;

    /**
     * 招标结果发布标识 - 可空
     */
    private String bidRstPubFlg;

    /**
     * 创建时间起始
     */
    private String crtTimBgnStr;

    /**
     * 创建时间结束
     */
    private String crtTimEndStr;




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

    public String getBidDpt() {
        return bidDpt;
    }

    public void setBidDpt(String bidDpt) {
        this.bidDpt = bidDpt;
    }

    public String getBidDptAddr() {
        return bidDptAddr;
    }

    public void setBidDptAddr(String bidDptAddr) {
        this.bidDptAddr = bidDptAddr;
    }

    public BigDecimal getBidDpstFrom() {
        return bidDpstFrom;
    }

    public void setBidDpstFrom(BigDecimal bidDpstFrom) {
        this.bidDpstFrom = bidDpstFrom;
    }

    public BigDecimal getBidDpstTo() {
        return bidDpstTo;
    }

    public void setBidDpstTo(BigDecimal bidDpstTo) {
        this.bidDpstTo = bidDpstTo;
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

    public Timestamp getBidEndTimFrom() {
        return bidEndTimFrom;
    }

    public void setBidEndTimFrom(Timestamp bidEndTimFrom) {
        this.bidEndTimFrom = bidEndTimFrom;
    }

    public Timestamp getBidEndTimTo() {
        return bidEndTimTo;
    }

    public void setBidEndTimTo(Timestamp bidEndTimTo) {
        this.bidEndTimTo = bidEndTimTo;
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

    public String getBidNtcPubFlg() {
        return bidNtcPubFlg;
    }

    public void setBidNtcPubFlg(String bidNtcPubFlg) {
        this.bidNtcPubFlg = bidNtcPubFlg;
    }

    public String getBidRstPubFlg() {
        return bidRstPubFlg;
    }

    public void setBidRstPubFlg(String bidRstPubFlg) {
        this.bidRstPubFlg = bidRstPubFlg;
    }


    public String getCrtTimBgnStr() {
        return crtTimBgnStr;
    }

    public void setCrtTimBgnStr(String crtTimBgnStr) {
        this.crtTimBgnStr = crtTimBgnStr;
    }

    public String getCrtTimEndStr() {
        return crtTimEndStr;
    }

    public void setCrtTimEndStr(String crtTimEndStr) {
        this.crtTimEndStr = crtTimEndStr;
    }

    @Override
    public String toString() {
        return "IoQueryBidProjOffForPchsDto{" +
                "projId=" + projId +
                ", projNbr='" + projNbr + '\'' +
                ", projNam='" + projNam + '\'' +
                ", projRsps='" + projRsps + '\'' +
                ", orgId=" + orgId +
                ", bidDpt='" + bidDpt + '\'' +
                ", bidDptAddr='" + bidDptAddr + '\'' +
                ", bidDpstFrom=" + bidDpstFrom +
                ", bidDpstTo=" + bidDpstTo +
                ", ctct='" + ctct + '\'' +
                ", ctctTel='" + ctctTel + '\'' +
                ", bidEndTimFrom=" + bidEndTimFrom +
                ", bidEndTimTo=" + bidEndTimTo +
                ", bidRngTyp='" + bidRngTyp + '\'' +
                ", matTyp='" + matTyp + '\'' +
                ", projSts='" + projSts + '\'' +
                ", bidNtcPubFlg='" + bidNtcPubFlg + '\'' +
                ", bidRstPubFlg='" + bidRstPubFlg + '\'' +
                ", crtTimBgnStr='" + crtTimBgnStr + '\'' +
                ", crtTimEndStr='" + crtTimEndStr + '\'' +
                '}';
    }
}
