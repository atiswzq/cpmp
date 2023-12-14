package cn.cofco.cpmp.dto;
import java.math.BigDecimal;
/**
 * Created by Tao on 2017/6/1.
 */
public class IoQueryBidProjOffProjInfForSplrDto {
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
     * 投标保证金 从 - 可空
     */
    private BigDecimal bidDpstFrom;

    /**
     * 投标保证金 到 - 可空
     */
    private BigDecimal bidDpstTo;

    /**
     * 招标联系人 - 可空
     */
    private String ctct;

    /**
     * 招标联系人电话 - 可空
     */
    private String ctctTel;

    /**
     * 投标截止时间 从 - 可空
     */
    private String bidEndTimFrom;

    /**
     * 投标截止时间 到 - 可空
     */
    private String bidEndTimTo;

    /**
     * 招标范围类型：0-非定向招标；1-定向招标 - 必填
     */
    private String bidRngTyp;

    /**
     * 物料品类 - 可空
     */
    private String matTyp;

    /**
     * 项目状态 - 可空
     * 20  招标中
     * 21  已截标
     * 22  已开标
     * 23  评标中
     * 24  评标结束
     * 30  二次报价中
     * 31  二次报价结束
     * 32  二次开标
     * 33  二次评标中
     * 34  二次评标结束
     * 40  决标审批中
     * 50  决标审批通过
     * -21 申请废标审批中
     * -20 已废标
     */
    private String projSts;

    /**
     * 项目状态列表 - 前端不送
     */
    private String[] projStses;

    /**
     * 招标公告发布标识：0-待发布；1-已发布 - 前端不送
     */
    private final String bidNtcPubFlg = "1";

    /**
     * 招标结果发布标识 - 可空
     */
    private String bidRstPubFlg;

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

    public String getBidEndTimFrom() {
        return bidEndTimFrom;
    }

    public void setBidEndTimFrom(String bidEndTimFrom) {
        this.bidEndTimFrom = bidEndTimFrom;
    }

    public String getBidEndTimTo() {
        return bidEndTimTo;
    }

    public void setBidEndTimTo(String bidEndTimTo) {
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

    public String[] getProjStses() {
        return projStses;
    }

    public void setProjStses(String[] projStses) {
        this.projStses = projStses;
    }

    public String getBidNtcPubFlg() {
        return bidNtcPubFlg;
    }

    public void setBidNtcPubFlg(String bidNtcPubFlg) {
        return;
    }

    public String getBidRstPubFlg() {
        return bidRstPubFlg;
    }

    public void setBidRstPubFlg(String bidRstPubFlg) {
        this.bidRstPubFlg = bidRstPubFlg;
    }

    @Override
    public String toString() {
        return "IoQueryBidProjOffProjInfForSplrDto{" +
                "projId=" + projId +
                ", projNbr='" + projNbr + '\'' +
                ", projNam='" + projNam + '\'' +
                ", projRsps='" + projRsps + '\'' +
                ", orgId=" + orgId +
                ", bidDptN='" + bidDpt + '\'' +
                ", bidDpstFrom=" + bidDpstFrom +
                ", bidDpstTo=" + bidDpstTo +
                ", ctct='" + ctct + '\'' +
                ", ctctTel='" + ctctTel + '\'' +
                ", bidEndTimFrom='" + bidEndTimFrom + '\'' +
                ", bidEndTimTo='" + bidEndTimTo + '\'' +
                ", bidRngTyp='" + bidRngTyp + '\'' +
                ", matTyp='" + matTyp + '\'' +
                ", projSts='" + projSts + '\'' +
                ", projStses=" + projStses +
                ", bidNtcPubFlg='" + bidNtcPubFlg + '\'' +
                ", bidRstPubFlg='" + bidRstPubFlg + '\'' +
                '}';
    }
}
