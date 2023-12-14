package cn.cofco.cpmp.dto;

/**
 * Created by Tao on 2017/5/29.
 */
public class IoBidProjOffCutDto {
    /**
     * 项目ID - 必填
     */
    private Long projId;

    /**
     * 截标备注
     */
    private String bidEndMemo;

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


    @Override
    public String toString() {
        return "IoBidProjOnCutDto{" +
                "projId=" + projId +
                ", bidEndMemo='" + bidEndMemo + '\'' +
                '}';
    }
}
