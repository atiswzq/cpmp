package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/7/15.
 * for [网上竞价项目立项申请提交BPM 主体DTO] in cpmp
 */
public class BidProjOnBudMainIoDto {

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
    private String bidDptNam;

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
     * 招标联系人电话
     */
    private String ctctTel;

    /**
     * 投标截止时间-时间戳
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
     * 报价次数类型: 0-一次报价；1-多次报价（最多三次）；2-实时报价
     */
    private String qotCntTyp;

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

    // 模板编号
    private String TempNum;

    // 状态
    private String Status;

    // 评标规则：0-最低价中标; 1-专家评标
    private String grdRul;

    //监标人
    private String projSupv;

    //监标人联系方式
    private String projSupvTel;

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

    public String getTempNum() {
        return TempNum;
    }

    public void setTempNum(String tempNum) {
        this.TempNum = tempNum;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
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
        return "BidProjOnBudMainIoDto{" +
                "bpmSeqNo='" + bpmSeqNo + '\'' +
                ", projNbr='" + projNbr + '\'' +
                ", projNam='" + projNam + '\'' +
                ", matTyp='" + matTyp + '\'' +
                ", bidDptNam='" + bidDptNam + '\'' +
                ", bidDptAddr='" + bidDptAddr + '\'' +
                ", bidDpst='" + bidDpst + '\'' +
                ", ctct='" + ctct + '\'' +
                ", ctctTel='" + ctctTel + '\'' +
                ", bidEndTim='" + bidEndTim + '\'' +
                ", bidOpenTim='" + bidOpenTim + '\'' +
                ", bidRngTyp='" + bidRngTyp + '\'' +
                ", qotCntTyp='" + qotCntTyp + '\'' +
                ", projMemo='" + projMemo + '\'' +
                ", createusername='" + createusername + '\'' +
                ", createdate='" + createdate + '\'' +
                ", currTyp='" + currTyp + '\'' +
                ", dpstFlg='" + dpstFlg + '\'' +
                ", pubPriFlg='" + pubPriFlg + '\'' +
                ", TempNum='" + TempNum + '\'' +
                ", Status='" + Status + '\'' +
                ", grdRul='" + grdRul + '\'' +
                ", projSupv='" + projSupv + '\'' +
                ", projSupvTel='" + projSupvTel + '\'' +
                '}';
    }
}
