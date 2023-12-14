package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/6/4.
 * for [获取投标供应商DTO] in cpmp
 */
public class IoGetTendSplrsDto {

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
        return "IoGetTendSplrsDto{" +
                "projId=" + projId +
                ", bidDocSts='" + bidDocSts + '\'' +
                '}';
    }
}
