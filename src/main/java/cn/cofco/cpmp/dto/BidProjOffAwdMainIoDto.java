package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/7/15.
 * for [线下公开招标项目定标申请提交BPM 主体DTO] in cpmp
 */
public class BidProjOffAwdMainIoDto {

    /**
     * BPM审批流水号, 与BPM系统交互唯一标识，
     * 2位项目类型-8位日期-13位时间戳-6位随机码，（项目类型： JJ-网上竞价项目，GK-公开招标项目，XJ-询价项目）
     * 例如：
     * JJ-20170602-1496569052235-123456
     */
    private String bpmSeqNo;

    /**
     * 项目编号
     */
    private String projNbr;

    /**
     * 项目名称
     */
    private String projNam;

    /**
     * 开标时间
     */
    private String bidOpenTim;

    /**
     * 决标申请备注
     */
    private String awdMemo;

    /**
     * 申请人
     */
    private String createusername;

    /**
     * 申请时间
     */
    private String createdate;

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
     * 物料需求发起部门
     */
    private String matReqDept;

    /**
     * 物料采购预算
     */
    private String matPchsbgt;

    /**
     * 本次招标中标总额
     */
    private String bidTolAmt;

    /**
     * 补充简要说明
     */
    private String brfDesc;

    /**
     * 物料类型
     */
    private String matTyp;

    public String getBpmSeqNo() {
        return bpmSeqNo;
    }

    public void setBpmSeqNo(String bpmSeqNo) {
        this.bpmSeqNo = bpmSeqNo;
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

    public String getBidOpenTim() {
        return bidOpenTim;
    }

    public void setBidOpenTim(String bidOpenTim) {
        this.bidOpenTim = bidOpenTim;
    }

    public String getAwdMemo() {
        return awdMemo;
    }

    public void setAwdMemo(String awdMemo) {
        this.awdMemo = awdMemo;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
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

    public String getMatReqDept() {
        return matReqDept;
    }

    public void setMatReqDept(String matReqDept) {
        this.matReqDept = matReqDept;
    }

    public String getMatPchsbgt() {
        return matPchsbgt;
    }

    public void setMatPchsbgt(String matPchsbgt) {
        this.matPchsbgt = matPchsbgt;
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

    public String getMatTyp() {
        return matTyp;
    }

    public void setMatTyp(String matTyp) {
        this.matTyp = matTyp;
    }

    @Override
    public String toString() {
        return "BidProjOffAwdMainIoDto{" +
                "bpmSeqNo='" + bpmSeqNo + '\'' +
                ", projNbr='" + projNbr + '\'' +
                ", projNam='" + projNam + '\'' +
                ", bidOpenTim='" + bidOpenTim + '\'' +
                ", awdMemo='" + awdMemo + '\'' +
                ", createusername='" + createusername + '\'' +
                ", createdate='" + createdate + '\'' +
                ", matReqBpmAppSeq='" + matReqBpmAppSeq + '\'' +
                ", caseRept='" + caseRept + '\'' +
                ", dept='" + dept + '\'' +
                ", matReqDept='" + matReqDept + '\'' +
                ", matPchsbgt='" + matPchsbgt + '\'' +
                ", bidTolAmt='" + bidTolAmt + '\'' +
                ", brfDesc='" + brfDesc + '\'' +
                ", matTyp='" + matTyp + '\'' +
                '}';
    }
}
