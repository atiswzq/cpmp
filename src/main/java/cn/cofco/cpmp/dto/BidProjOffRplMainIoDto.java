package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/7/16.
 * for [网上竞价项目废标申请提交BPM DTO] in cpmp
 */
public class BidProjOffRplMainIoDto {

    /**
     * BPM审批流水号
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
     * 招标单位
     */
    private String bidDpt;

    /**
     * 招标单位地址
     */
    private String bidDptAddr;

    /**
     * 联系人
     */
    private String ctct;

    /**
     * 联系人电话
     */
    private String ctctTel;

    /**
     * 废标备注
     */
    private String rplMemo;

    /**
     * 申请人
     */
    private String createusername;

    /**
     * 申请时间
     */
    private String createdate;

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

    public String getRplMemo() {
        return rplMemo;
    }

    public void setRplMemo(String rplMemo) {
        this.rplMemo = rplMemo;
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

    @Override
    public String toString() {
        return "BidProjOffRplMainIoDto{" +
                "bpmSeqNo='" + bpmSeqNo + '\'' +
                ", projNbr='" + projNbr + '\'' +
                ", projNam='" + projNam + '\'' +
                ", bidOpenTim='" + bidOpenTim + '\'' +
                ", bidDpt='" + bidDpt + '\'' +
                ", bidDptAddr='" + bidDptAddr + '\'' +
                ", ctct='" + ctct + '\'' +
                ", ctctTel='" + ctctTel + '\'' +
                ", rplMemo='" + rplMemo + '\'' +
                ", createusername='" + createusername + '\'' +
                ", createdate='" + createdate + '\'' +
                '}';
    }
}
