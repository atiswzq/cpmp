package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/29.
 * for [线上招标项目开标上行报文] in cpmp
 */
public class IoBidProjOnOpenDto {

    /**
     * 项目ID
     */
    private Long projId;

    /**
     * 开标密钥
     */
    private String openKey;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getOpenKey() {
        return openKey;
    }

    public void setOpenKey(String openKey) {
        this.openKey = openKey;
    }

    @Override
    public String toString() {
        return "IoBidProjOnOpenDto{" +
                "projId=" + projId +
                ", openKey='" + openKey + '\'' +
                '}';
    }
}
