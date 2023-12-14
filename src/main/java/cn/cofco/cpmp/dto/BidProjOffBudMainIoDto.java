package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/7/15.
 * for [线下公开招标项目立项申请提交BPM 主体DTO] in cpmp
 */
public class BidProjOffBudMainIoDto {

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
     * 该项目所属物料品类
     */
    private String matTyp;

    /**
     * 招标单位
     */
    private String bidDpt;

    /**
     * 招标单位地址
     */
    private String bidDptAddr;

    /**
     * 投标保证金
     */
    private String bidDpst;

    /**
     * 招标联系人
     */
    private String ctct;

    /**
     * 招标联系人电话 YYYY-MM-dd HH:mm:ss
     */
    private String ctctTel;

    /**
     * 投标截止时间 YYYY-MM-dd HH:mm:ss
     */
    private String bidEndTim;

    /**
     * 开标时间
     */
    private String bidOpenTim;


    /**
     * 招标范围类型：0-非定向招标；1-定向招标
     */
    private String bidRngTyp;


    /**
     * 项目备注
     */
    private String projMemo;


    /**
     * 申请人
     */
    private String createusername;

    /**
     * 申请时间
     */
    private String createdate;

    // 币种
    private String currTyp;

    // 是否需要投标保证金标识
    private String dpstFlg;

    // 是否需要公开招标价格标识
    private String pubPriFlg;

    // 物料需求发起部门
    private String matReqDept;

    // 招标小组成员
    private String bidTeamMmb;

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

    public String getBidDpst() {
        return bidDpst;
    }

    public void setBidDpst(String bidDpst) {
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

    public String getBidEndTim() {
        return bidEndTim;
    }

    public void setBidEndTim(String bidEndTim) {
        this.bidEndTim = bidEndTim;
    }

    public String getBidOpenTim() {
        return bidOpenTim;
    }

    public void setBidOpenTim(String bidOpenTim) {
        this.bidOpenTim = bidOpenTim;
    }

    public String getBidRngTyp() {
        return bidRngTyp;
    }

    public void setBidRngTyp(String bidRngTyp) {
        this.bidRngTyp = bidRngTyp;
    }

    public String getProjMemo() {
        return projMemo;
    }

    public void setProjMemo(String projMemo) {
        this.projMemo = projMemo;
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
        return "BidProjOffBudMainIoDto{" +
                "bpmSeqNo='" + bpmSeqNo + '\'' +
                ", projNbr='" + projNbr + '\'' +
                ", projNam='" + projNam + '\'' +
                ", matTyp='" + matTyp + '\'' +
                ", bidDpt='" + bidDpt + '\'' +
                ", bidDptAddr='" + bidDptAddr + '\'' +
                ", bidDpst='" + bidDpst + '\'' +
                ", ctct='" + ctct + '\'' +
                ", ctctTel='" + ctctTel + '\'' +
                ", bidEndTim='" + bidEndTim + '\'' +
                ", bidOpenTim='" + bidOpenTim + '\'' +
                ", bidRngTyp='" + bidRngTyp + '\'' +
                ", projMemo='" + projMemo + '\'' +
                ", createusername='" + createusername + '\'' +
                ", createdate='" + createdate + '\'' +
                ", currTyp='" + currTyp + '\'' +
                ", dpstFlg='" + dpstFlg + '\'' +
                ", pubPriFlg='" + pubPriFlg + '\'' +
                ", matReqDept='" + matReqDept + '\'' +
                ", bidTeamMmb='" + bidTeamMmb + '\'' +
                '}';
    }
}
