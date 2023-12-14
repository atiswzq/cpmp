package cn.cofco.cpmp.dto;

/**
 * Created by Wzq on 2018/01/14.
 * for [询价项目截标DTO] in cpmp
 */
public class IoXjProjCutDto {

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
        return "IoXjProjCutDto{" +
                "projId=" + projId +
                ", bidEndMemo='" + bidEndMemo + '\'' +
                '}';
    }
}
