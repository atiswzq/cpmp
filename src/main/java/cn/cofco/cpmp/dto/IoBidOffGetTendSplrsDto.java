package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/6/5.
 * for [文件用途] in cpmp
 */
public class IoBidOffGetTendSplrsDto {
    private Long projId;

    private String bidDocSts;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getBidDocSts() {
        return bidDocSts;
    }

    public void setBidDocSts(String bidDocSts) {
        this.bidDocSts = bidDocSts;
    }

    @Override
    public String toString() {
        return "IoBidOffGetTendSplrsDto{" +
                "projId=" + projId +
                ", bidDocSts='" + bidDocSts + '\'' +
                '}';
    }
}
