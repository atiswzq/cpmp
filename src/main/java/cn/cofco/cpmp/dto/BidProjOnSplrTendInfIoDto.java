package cn.cofco.cpmp.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Xujy on 2017/5/22.
 * for [文件用途] in cpmp
 */
public class BidProjOnSplrTendInfIoDto {

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
     * 保证金图片
     */
    private String dpstPic;

    /**
     * 保证金状态：0-未缴纳；1-已缴纳
     */
    private String dpstSts;

    /**
     * 报价ID
     */
    private Long qotId;

    /**
     * 二次竞价报价ID
     */
    private Long qot2Id;

    /**
     * 报价次数
     */
    private Integer qotCnt;

    /**
     * 二次竞价报价次数
     */
    private Integer qot2Cnt;

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
     * 招标保证金
     */
    private BigDecimal bidDpst;

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
     * 评标截止时间
     */
    private Timestamp grdEndTim;

    /**
     * 二次竞价报价截止时间
     */
    private Timestamp qot2EndTim;

    /**
     * 二次竞价评标截止时间
     */
    private Timestamp grd2EndTim;

    /**
     * 项目备注
     */
    private String projMemo;

    /**
     * 二次竞价报价次数类型：0-一次报价；1-多次报价（最多三次）；2-实时报价
     */
    private String qot2CntTyp;

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

    public Timestamp getGrdEndTim() {
        return grdEndTim;
    }

    public void setGrdEndTim(Timestamp grdEndTim) {
        this.grdEndTim = grdEndTim;
    }

    public Timestamp getQot2EndTim() {
        return qot2EndTim;
    }

    public void setQot2EndTim(Timestamp qot2EndTim) {
        this.qot2EndTim = qot2EndTim;
    }

    public Timestamp getGrd2EndTim() {
        return grd2EndTim;
    }

    public void setGrd2EndTim(Timestamp grd2EndTim) {
        this.grd2EndTim = grd2EndTim;
    }

    public String getProjMemo() {
        return projMemo;
    }

    public void setProjMemo(String projMemo) {
        this.projMemo = projMemo;
    }

    public String getQot2CntTyp() {
        return qot2CntTyp;
    }

    public void setQot2CntTyp(String qot2CntTyp) {
        this.qot2CntTyp = qot2CntTyp;
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
        return "BidProjOnSplrTendInfIoDto{" +
                "id=" + id +
                ", splrId=" + splrId +
                ", splrCtct='" + splrCtct + '\'' +
                ", splrCtctTel='" + splrCtctTel + '\'' +
                ", splrNam='" + splrNam + '\'' +
                ", dpstPic='" + dpstPic + '\'' +
                ", dpstSts='" + dpstSts + '\'' +
                ", qotId=" + qotId +
                ", qot2Id=" + qot2Id +
                ", qotCnt=" + qotCnt +
                ", qot2Cnt=" + qot2Cnt +
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
                ", bidDpst=" + bidDpst +
                ", ctct='" + ctct + '\'' +
                ", ctctTel='" + ctctTel + '\'' +
                ", bidEndTim=" + bidEndTim +
                ", bidOpenTim=" + bidOpenTim +
                ", grdEndTim=" + grdEndTim +
                ", qot2EndTim=" + qot2EndTim +
                ", grd2EndTim=" + grd2EndTim +
                ", projMemo='" + projMemo + '\'' +
                ", qot2CntTyp='" + qot2CntTyp + '\'' +
                ", qotCntTyp='" + qotCntTyp + '\'' +
                ", bidRngTyp='" + bidRngTyp + '\'' +
                ", matTyp='" + matTyp + '\'' +
                ", projSts='" + projSts + '\'' +
                '}';
    }
}
