package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/22.
 * for [线上招标项目选择评标专家接口上行报文DTO] in cpmp
 */
public class IoBidProjOnChsExptsDto {

    /**
     * 项目ID
     */
    private Long projId;

    /**
     * 专家IDs，用','隔开，不允许有空格，例如：1,2,3
     */
    private String exptIds;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getExptIds() {
        return exptIds;
    }

    public void setExptIds(String exptIds) {
        this.exptIds = exptIds;
    }

    @Override
    public String toString() {
        return "IoBidProjOnOpenDto{" +
                "projId=" + projId +
                ", exptIds='" + exptIds + '\'' +
                '}';
    }
}
