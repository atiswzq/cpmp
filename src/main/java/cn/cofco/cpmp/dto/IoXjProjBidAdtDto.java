package cn.cofco.cpmp.dto;


/**
 * Created by Wzq on 2018/01/13.
 * for [询价项目供应商投标审核：通过/拒绝DTO] in cpmp
 */
public class IoXjProjBidAdtDto {

    /**
     * 投标ID
     * 是否强制：是
     */
    private Long id;

    /**
     * 审核备注
     * 是否强制：否
     */
    private String adtMemo;

    /**
     * 标书状态：00-已申请；01-已接受；02-已拒绝
     * 是否强制：是
     */
    private String bidDocSts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "IoXjProjBidAdtDto{" +
                "id=" + id +
                ", adtMemo='" + adtMemo + '\'' +
                ", bidDocSts='" + bidDocSts + '\'' +
                '}';
    }
}
