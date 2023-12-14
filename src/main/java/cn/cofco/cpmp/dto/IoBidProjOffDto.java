package cn.cofco.cpmp.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Tao on 2017/5/16.
 */
public class IoBidProjOffDto {
    // 提交标识：0-不提交; 1-提交
    private String subFlg;

    // 项目ID
    private Long projId;

    // 项目编号
    private String projNbr;

    // 项目名称
    private String projNam;

    // 组织ID
    private Long orgId;

    // 招标适用组织IDs
    private String sutOrgIds;

    // 招标负责人
    private String projRsps;

    // 物料品类
    private String matTyp;

    // 招标单位
    private String bidDpt;

    // 招标单位地址
    private String bidDptAddr;

    // 竞价保证金（元）
    private BigDecimal bidDpst;

    // 联系人
    private String ctct;

    // 联系人电话
    private String ctctTel;

    // 投标截止时间
    private String bidEndTimStr; // 前端上送
    private Timestamp bidEndTim; // 后台转换

    // 招标范围类型：0-非定向招标；1-定向招标
    private String bidRngTyp;

    // 定向邀标供应商IDs
    private String aptSplrIds;

    //中标供应商ID
    private  String splrIds;

    // 报价次数类型：0-一次报价；1-多次报价（最多三次）；2-实时报价
    private String qotCntTyp;

    // 项目备注
    private String projMemo;

    private String bidBok;

    private String bidOpenTimStr; // 前端上送
    private Timestamp bidOpenTim; // 后台转换

    // 币种
    private String currTyp;

    // 是否需要投标保证金标识
    private String dpstFlg;

    // 是否需要公开招标价格标识
    private String pubPriFlg;

    // 附件信息
    private List<IoAtchDto> atchDtos;

    // 物料需求发起部门
    private String matReqDept;

    // 招标小组成员
    private String bidTeamMmb;



    public String getBidBok() {
        return bidBok;
    }

    public void setBidBok(String bidBok) {
        this.bidBok = bidBok;
    }

    public String getSubFlg() {
        return subFlg;
    }

    public void setSubFlg(String subFlg) {
        this.subFlg = subFlg;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getSutOrgIds() {
        return sutOrgIds;
    }

    public void setSutOrgIds(String sutOrgIds) {
        this.sutOrgIds = sutOrgIds;
    }

    public String getMatTyp() {
        return matTyp;
    }

    public void setMatTyp(String matTyp) {
        this.matTyp = matTyp;
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

    public String getBidEndTimStr() {
        return bidEndTimStr;
    }

    public void setBidEndTimStr(String bidEndTimStr) {
        this.bidEndTimStr = bidEndTimStr;
    }


    public String getBidRngTyp() {
        return bidRngTyp;
    }

    public void setBidRngTyp(String bidRngTyp) {
        this.bidRngTyp = bidRngTyp;
    }

    public String getAptSplrIds() {
        return aptSplrIds;
    }

    public void setAptSplrIds(String aptSplrIds) {
        this.aptSplrIds = aptSplrIds;
    }

    public String getSplrIds() {
        return splrIds;
    }

    public void setSplrIds(String splrIds) {
        this.splrIds = splrIds;
    }

    public String getQotCntTyp() {
        return qotCntTyp;
    }

    public void setQotCntTyp(String qotCntTyp) {
        this.qotCntTyp = qotCntTyp;
    }

    public String getProjMemo() {
        return projMemo;
    }

    public void setProjMemo(String projMemo) {
        this.projMemo = projMemo;
    }


    public Timestamp getBidEndTim() {
        return bidEndTim;
    }

    public void setBidEndTim(Timestamp bidEndTim) {
        this.bidEndTim = bidEndTim;
    }

    public String getProjRsps() {
        return projRsps;
    }

    public void setProjRsps(String projRsps) {
        this.projRsps = projRsps;
    }

    public String getBidOpenTimStr() {
        return bidOpenTimStr;
    }

    public void setBidOpenTimStr(String bidOpenTimStr) {
        this.bidOpenTimStr = bidOpenTimStr;
    }

    public Timestamp getBidOpenTim() {
        return bidOpenTim;
    }

    public void setBidOpenTim(Timestamp bidOpenTim) {
        this.bidOpenTim = bidOpenTim;
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

    public List<IoAtchDto> getAtchDtos() {
        return atchDtos;
    }

    public void setAtchDtos(List<IoAtchDto> atchDtos) {
        this.atchDtos = atchDtos;
    }

    public String getMatReqDept() {
        return matReqDept;
    }

    public void setMatReqDept(String matReqDept) {
        this.matReqDept = matReqDept;
    }

    public String getBidTeamMmb() {
        return bidTeamMmb;
    }

    public void setBidTeamMmb(String bidTeamMmb) {
        this.bidTeamMmb = bidTeamMmb;
    }

    @Override
    public String toString() {
        return "IoBidProjOffDto{" +
                "subFlg='" + subFlg + '\'' +
                ", projId=" + projId +
                ", projNbr='" + projNbr + '\'' +
                ", projNam='" + projNam + '\'' +
                ", orgId=" + orgId +
                ", sutOrgIds='" + sutOrgIds + '\'' +
                ", projRsps='" + projRsps + '\'' +
                ", matTyp='" + matTyp + '\'' +
                ", bidDpt='" + bidDpt + '\'' +
                ", bidDptAddr='" + bidDptAddr + '\'' +
                ", bidDpst=" + bidDpst +
                ", ctct='" + ctct + '\'' +
                ", ctctTel='" + ctctTel + '\'' +
                ", bidEndTimStr='" + bidEndTimStr + '\'' +
                ", bidEndTim=" + bidEndTim +
                ", bidRngTyp='" + bidRngTyp + '\'' +
                ", aptSplrIds='" + aptSplrIds + '\'' +
                ", splrIds='" + splrIds + '\'' +
                ", qotCntTyp='" + qotCntTyp + '\'' +
                ", projMemo='" + projMemo + '\'' +
                ", bidBok='" + bidBok + '\'' +
                ", bidOpenTimStr='" + bidOpenTimStr + '\'' +
                ", bidOpenTim=" + bidOpenTim +
                ", currTyp='" + currTyp + '\'' +
                ", dpstFlg='" + dpstFlg + '\'' +
                ", pubPriFlg='" + pubPriFlg + '\'' +
                ", atchDtos=" + atchDtos +
                ", matReqDept='" + matReqDept + '\'' +
                ", bidTeamMmb='" + bidTeamMmb + '\'' +
                '}';
    }
}