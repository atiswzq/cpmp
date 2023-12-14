package cn.cofco.cpmp.dto;

/**
 * Created by Tao on 2017/5/30.
 */
public class IoQueryBidProjOffSplrInfDto {

    /**
            * 供应商ID - 可空
     **/
    private Long splrId;

    /**
     * 项目ID - 可空
     **/
    private Long projId;

    /**
     * 项目名称 - 可空
     **/
    private String projNam;

    /**
     * 供应商联系人 - 可空
     **/
    private String splrCtct;

    /**
     * 供应商联系人电话 - 可空
     **/
    private String splrCtctTel;

    /**
     * 标书状态 - 可空
     **/
    private String bidDocSts;

    /**
     * 报价次数 - 可空
     **/
    private Integer qotCnt;

    /**
     * 投标起始时间 - 可空
     **/
    private String bidTimBgn;

    /**
     * 投标结束时间 - 可空
     **/
    private String bidTimEnd;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getProjNam() {
        return projNam;
    }

    public void setProjNam(String projNam) {
        this.projNam = projNam;
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

    public String getBidDocSts() {
        return bidDocSts;
    }

    public void setBidDocSts(String bidDocSts) {
        this.bidDocSts = bidDocSts;
    }

    public Integer getQotCnt() {
        return qotCnt;
    }

    public void setQotCnt(Integer qotCnt) {
        this.qotCnt = qotCnt;
    }

    public String getBidTimBgn() {
        return bidTimBgn;
    }

    public void setBidTimBgn(String bidTimBgn) {
        this.bidTimBgn = bidTimBgn;
    }

    public String getBidTimEnd() {
        return bidTimEnd;
    }

    public void setBidTimEnd(String bidTimEnd) {
        this.bidTimEnd = bidTimEnd;
    }

    @Override
    public String toString() {
        return "IoQuerySplrBidProjOnDto{" +
                "splrId=" + splrId +
                ", projId=" + projId +
                ", projNam='" + projNam + '\'' +
                ", splrCtct='" + splrCtct + '\'' +
                ", splrCtctTel='" + splrCtctTel + '\'' +
                ", bidDocSts='" + bidDocSts + '\'' +
                ", qotCnt=" + qotCnt +
                ", bidTimBgn='" + bidTimBgn + '\'' +
                ", bidTimEnd='" + bidTimEnd + '\'' +
                '}';
    }
}
