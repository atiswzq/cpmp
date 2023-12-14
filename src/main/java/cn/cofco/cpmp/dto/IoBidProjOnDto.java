package cn.cofco.cpmp.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Xujy on 2017/5/6.
 * Updated by Xujy on 2017/11/18. for add grdRul[评标规则]
 * for [线上招标项目上行报文DTO] in cpmp
 */
public class IoBidProjOnDto {

    // 提交标识：0-不提交; 1-提交
    private String subFlg;

    // 项目ID
    private Long projId;

    // 项目编号
    private String projNbr;

    // 组织ID
    private Long orgId;

    // 招标适用组织IDs
    private String sutOrgIds;

    // 项目名称
    private String projNam;

    // 物料品类
    private String matTyp;

    // 招标单位
    private String bidDptNam;

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

    // 开标时间
    private String bidOpenTimStr;
    private Timestamp bidOpenTim;

    // 招标范围类型：0-非定向招标；1-定向招标
    private String bidRngTyp;

    // 定向邀标供应商IDs
    private String splrIds;

    // 报价次数类型：0-一次报价；1-多次报价（最多三次）；2-实时报价
    private String qotCntTyp;

    // 项目备注
    private String projMemo;

    // 物料明细列表
    private List<IoBidProjOnMatInfDto> matList;

    // 币种
    private String currTyp;

    // 是否需要投标保证金标识
    private String dpstFlg;

    // 是否需要公开招标价格标识
    private String pubPriFlg;

    // 评标规则
    private String grdRul;

    //监标人
    private String projSupv;

    //监标人联系方式
    private String projSupvTel;

    // 附件信息
    private List<IoAtchDto> atchDtos;

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

    public String getBidEndTimStr() {
        return bidEndTimStr;
    }

    public void setBidEndTimStr(String bidEndTimStr) {
        this.bidEndTimStr = bidEndTimStr;
    }

    public String getBidOpenTimStr() {
        return bidOpenTimStr;
    }

    public void setBidOpenTimStr(String bidOpenTimStr) {
        this.bidOpenTimStr = bidOpenTimStr;
    }

    public String getBidRngTyp() {
        return bidRngTyp;
    }

    public void setBidRngTyp(String bidRngTyp) {
        this.bidRngTyp = bidRngTyp;
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

    public List<IoBidProjOnMatInfDto> getMatList() {
        return matList;
    }

    public void setMatList(List<IoBidProjOnMatInfDto> matList) {
        this.matList = matList;
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

    public String getGrdRul() {
        return grdRul;
    }

    public void setGrdRul(String grdRul) {
        this.grdRul = grdRul;
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

    @Override
    public String toString() {
        return "IoBidProjOnDto{" +
                "subFlg='" + subFlg + '\'' +
                ", projId=" + projId +
                ", projNbr='" + projNbr + '\'' +
                ", orgId=" + orgId +
                ", sutOrgIds='" + sutOrgIds + '\'' +
                ", projNam='" + projNam + '\'' +
                ", matTyp='" + matTyp + '\'' +
                ", bidDptNam='" + bidDptNam + '\'' +
                ", bidDptAddr='" + bidDptAddr + '\'' +
                ", bidDpst=" + bidDpst +
                ", ctct='" + ctct + '\'' +
                ", ctctTel='" + ctctTel + '\'' +
                ", bidEndTimStr='" + bidEndTimStr + '\'' +
                ", bidEndTim=" + bidEndTim +
                ", bidOpenTimStr='" + bidOpenTimStr + '\'' +
                ", bidOpenTim=" + bidOpenTim +
                ", bidRngTyp='" + bidRngTyp + '\'' +
                ", splrIds='" + splrIds + '\'' +
                ", qotCntTyp='" + qotCntTyp + '\'' +
                ", projMemo='" + projMemo + '\'' +
                ", matList=" + matList +
                ", currTyp='" + currTyp + '\'' +
                ", dpstFlg='" + dpstFlg + '\'' +
                ", pubPriFlg='" + pubPriFlg + '\'' +
                ", grdRul='" + grdRul + '\'' +
                ", projSupv='" + projSupv + '\'' +
                ", projSupvTel='" + projSupvTel + '\'' +
                ", atchDtos=" + atchDtos +
                '}';
    }
}
