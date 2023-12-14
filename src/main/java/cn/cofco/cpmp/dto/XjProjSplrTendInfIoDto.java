package cn.cofco.cpmp.dto;


import java.sql.Timestamp;

/**
 * Created by Wzq on 2018/01/13.
 * for [文件用途] in cpmp
 */
public class XjProjSplrTendInfIoDto {

    /**
     * 投标ID
     */
    private Long id;

    /**
     * 供应商ID
     */
    private Long splrId;

    /**
     * 供应商联系人
     */
    private String splrCtct;

    /**
     * 供应商联系人联系电话
     */
    private String splrCtctTel;

    /**
     * 供应商名称
     */
    private String splrNam;
    /**
     * 报价ID
     */
    private Long qotId;
    /**
     * 报价次数
     */
    private Integer qotCnt;
    /**
     * 投标备注
     */
    private String memo;

    /**
     * 投标审核备注
     */
    private String adtMemo;
    /**
     * 标书状态
     */
    private String bidDocSts;

    /**
     * 项目ID
     */
    private Long projId;
    /**
     * 项目编号
     */
    private String projNbr;

    /**
     * 项目名称
     */
    private String projNam;

    /**
     * 项目负责人
     */
    private String projRsps;
    /**
     * 组织ID: 招标工厂ID
     */
    private Long orgId;

    /**
     * 招标单位名称
     */
    private String bidDptNam;
    /**
     * 招标单位地址
     */
    private String bidDptAddr;
    /**
     * 招标联系人
     */
    private String ctct;

    /**
     * 招标联系人电话
     */
    private String ctctTel;

    /**
     * 投标截止时间
     */
    private Timestamp bidEndTim;

    /**
     * 开标时间
     */
    private Timestamp bidOpenTim;
    /**
     * 项目备注
     */
    private String projMemo;
    /**
     * 报价次数类型：0-一次报价；1-多次报价（最多三次）；2-实时报价
     */
    private String qotCntTyp;
    /**
     * 招标范围类型：0-非定向招标；1-定向招标
     */
    private String bidRngTyp;

    /**
     * 物料品类
     */
    private String matTyp;

    /**
     * 项目状态
     */
    private String projSts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
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

    public Long getQotId() {
        return qotId;
    }

    public void setQotId(Long qotId) {
        this.qotId = qotId;
    }

    public Integer getQotCnt() {
        return qotCnt;
    }

    public void setQotCnt(Integer qotCnt) {
        this.qotCnt = qotCnt;
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

    public Timestamp getBidEndTim() {
        return bidEndTim;
    }

    public void setBidEndTim(Timestamp bidEndTim) {
        this.bidEndTim = bidEndTim;
    }

    public Timestamp getBidOpenTim() {
        return bidOpenTim;
    }

    public void setBidOpenTim(Timestamp bidOpenTim) {
        this.bidOpenTim = bidOpenTim;
    }

    public String getProjMemo() {
        return projMemo;
    }

    public void setProjMemo(String projMemo) {
        this.projMemo = projMemo;
    }

    public String getQotCntTyp() {
        return qotCntTyp;
    }

    public void setQotCntTyp(String qotCntTyp) {
        this.qotCntTyp = qotCntTyp;
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

    @Override
    public String toString() {
        return "XjProjSplrTendInfIoDto{" +
                "id=" + id +
                ", splrId=" + splrId +
                ", splrCtct='" + splrCtct + '\'' +
                ", splrCtctTel='" + splrCtctTel + '\'' +
                ", splrNam='" + splrNam + '\'' +
                ", qotId=" + qotId +
                ", qotCnt=" + qotCnt +
                ", memo='" + memo + '\'' +
                ", adtMemo='" + adtMemo + '\'' +
                ", bidDocSts='" + bidDocSts + '\'' +
                ", projId=" + projId +
                ", projNbr='" + projNbr + '\'' +
                ", projNam='" + projNam + '\'' +
                ", projRsps='" + projRsps + '\'' +
                ", orgId=" + orgId +
                ", bidDptNam='" + bidDptNam + '\'' +
                ", bidDptAddr='" + bidDptAddr + '\'' +
                ", ctct='" + ctct + '\'' +
                ", ctctTel='" + ctctTel + '\'' +
                ", bidEndTim=" + bidEndTim +
                ", bidOpenTim=" + bidOpenTim +
                ", projMemo='" + projMemo + '\'' +
                ", qotCntTyp='" + qotCntTyp + '\'' +
                ", bidRngTyp='" + bidRngTyp + '\'' +
                ", matTyp='" + matTyp + '\'' +
                ", projSts='" + projSts + '\'' +
                '}';
    }
}
