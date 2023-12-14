package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/21.
 * for [线上招标项目截标DTO] in cpmp
 */
public class IoBidProjOnCutDto {

    /**
     * 项目ID - 必填
     */
    private Long projId;

    /**
     * 截标备注
     */
    private String bidEndMemo;

    /**
     * 二次截标备注
     */
    private String bidEndMemo2;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getBidEndMemo() {
        return bidEndMemo;
    }

    public void setBidEndMemo(String bidEndMemo) {
        this.bidEndMemo = bidEndMemo;
    }

    public String getBidEndMemo2() {
        return bidEndMemo2;
    }

    public void setBidEndMemo2(String bidEndMemo2) {
        this.bidEndMemo2 = bidEndMemo2;
    }

    @Override
    public String toString() {
        return "IoBidProjOnCutDto{" +
                "projId=" + projId +
                ", bidEndMemo='" + bidEndMemo + '\'' +
                ", bidEndMemo2='" + bidEndMemo2 + '\'' +
                '}';
    }
}
