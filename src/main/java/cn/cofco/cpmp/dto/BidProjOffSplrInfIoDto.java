package cn.cofco.cpmp.dto;
import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * Created by Tao on 2017/5/31.
 * for [线下项目]已投标项目查询
 */
public class BidProjOffSplrInfIoDto {

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
    private String cntct;

    /**
     * 供应商联系人联系电话
     */
    private String cntctTel;

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
    private String bidDpt;

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
     * 项目备注
     */
    private String projMemo;


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

    public String getCntct() {
        return cntct;
    }

    public void setCntct(String cntct) {
        this.cntct = cntct;
    }

    public String getCntctTel() {
        return cntctTel;
    }

    public void setCntctTel(String cntctTel) {
        this.cntctTel = cntctTel;
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

    @Override
    public String toString() {
        return "BidProjOnSplrTendInfIoDto{" +
                "id=" + id +
                ", splrId=" + splrId +
                ", splrCtct='" + cntct + '\'' +
                ", splrCtctTel='" + cntctTel + '\'' +
                ", splrNam='" + splrNam + '\'' +
                ", dpstPic='" + dpstPic + '\'' +
                ", dpstSts='" + dpstSts + '\'' +
                ", memo='" + memo + '\'' +
                ", adtMemo='" + adtMemo + '\'' +
                ", bidDocSts='" + bidDocSts + '\'' +
                ", projId=" + projId +
                ", projNbr='" + projNbr + '\'' +
                ", projNam='" + projNam + '\'' +
                ", projRsps='" + projRsps + '\'' +
                ", orgId=" + orgId +
                ", bidDpt='" + bidDpt + '\'' +
                ", bidDptAddr='" + bidDptAddr + '\'' +
                ", bidDpst=" + bidDpst +
                ", ctct='" + ctct + '\'' +
                ", ctctTel='" + ctctTel + '\'' +
                ", bidEndTim=" + bidEndTim +
                ", grdEndTim=" + grdEndTim +
                ", projMemo='" + projMemo + '\'' +
                ", bidRngTyp='" + bidRngTyp + '\'' +
                ", matTyp='" + matTyp + '\'' +
                ", projSts='" + projSts + '\'' +
                '}';
    }

}
