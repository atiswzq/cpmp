package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/21.
 * for [线上招标项目申请废标DTO] in cpmp
 */
public class IoBidProjOnRepealDto {
    /**
     * 项目ID - 必填
     */
    private Long projId;

    /**
     * 申请废标备注
     */
    private String appRplMemo;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getAppRplMemo() {
        return appRplMemo;
    }

    public void setAppRplMemo(String appRplMemo) {
        this.appRplMemo = appRplMemo;
    }

    @Override
    public String toString() {
        return "IoBidProjOnRepealDto{" +
                "projId=" + projId +
                ", appRplMemo='" + appRplMemo + '\'' +
                '}';
    }
}
