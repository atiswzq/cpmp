package cn.cofco.cpmp.dto;

import java.util.List;

public class IoBidProjOffAppAwdListDto {

    private Long projId;
    private String awdMemo;
    private List<IoBidProjOffAppAwdDto> ioBidProjOffAppAwdDtos;

    /**
     * 物料需求BPM申请流水号
     */
    private String matReqBpmAppSeq;

    /**
     * 结案报告人	caseRept
     */
    private String caseRept;

    /**
     * 部门
     */
    private String dept;


    /**
     * 物料采购预算
     */
    private String matPchsBgt;

    /**
     * 本次招标中标总额
     */
    private String bidTolAmt;

    /**
     * 补充简要说明
     */
    private String brfDesc;


    /**
     * 附件信息
     */
    private List<IoAtchDto> atchDtos;


    public List<IoBidProjOffAppAwdDto> getIoBidProjOffAppAwdDtos() {
        return ioBidProjOffAppAwdDtos;
    }

    public void setIoBidProjOffAppAwdDtos(List<IoBidProjOffAppAwdDto> ioBidProjOffAppAwdDtos) {
        this.ioBidProjOffAppAwdDtos = ioBidProjOffAppAwdDtos;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getAwdMemo() {
        return awdMemo;
    }

    public void setAwdMemo(String awdMemo) {
        this.awdMemo = awdMemo;
    }

    public String getMatReqBpmAppSeq() {
        return matReqBpmAppSeq;
    }

    public void setMatReqBpmAppSeq(String matReqBpmAppSeq) {
        this.matReqBpmAppSeq = matReqBpmAppSeq;
    }

    public String getCaseRept() {
        return caseRept;
    }

    public void setCaseRept(String caseRept) {
        this.caseRept = caseRept;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }


    public String getMatPchsBgt() {
        return matPchsBgt;
    }

    public void setMatPchsBgt(String matPchsBgt) {
        this.matPchsBgt = matPchsBgt;
    }

    public String getBidTolAmt() {
        return bidTolAmt;
    }

    public void setBidTolAmt(String bidTolAmt) {
        this.bidTolAmt = bidTolAmt;
    }

    public String getBrfDesc() {
        return brfDesc;
    }

    public void setBrfDesc(String brfDesc) {
        this.brfDesc = brfDesc;
    }

    public List<IoAtchDto> getAtchDtos() {
        return atchDtos;
    }

    public void setAtchDtos(List<IoAtchDto> atchDtos) {
        this.atchDtos = atchDtos;
    }

    @Override
    public String toString() {
        return "IoBidProjOffAppAwdListDto{" +
                "projId=" + projId +
                ", awdMemo='" + awdMemo + '\'' +
                ", ioBidProjOffAppAwdDtos=" + ioBidProjOffAppAwdDtos +
                ", matReqBpmAppSeq='" + matReqBpmAppSeq + '\'' +
                ", caseRept='" + caseRept + '\'' +
                ", dept='" + dept + '\'' +
                ", matPchsBgt='" + matPchsBgt + '\'' +
                ", bidTolAmt='" + bidTolAmt + '\'' +
                ", brfDesc='" + brfDesc + '\'' +
                ", atchDtos=" + atchDtos +
                '}';
    }
}
