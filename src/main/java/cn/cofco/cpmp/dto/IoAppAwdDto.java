package cn.cofco.cpmp.dto;

import java.util.List;

/**
 * Created by Xujy on 2017/6/4.
 * for [线上招标项目申请决标DTO] in cpmp
 */
public class IoAppAwdDto {

    private Long projId;

    private String appAwdMemo;

    private List<IoAppAwdDtlDto> appAwdDtls;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getAppAwdMemo() {
        return appAwdMemo;
    }

    public void setAppAwdMemo(String appAwdMemo) {
        this.appAwdMemo = appAwdMemo;
    }

    public List<IoAppAwdDtlDto> getAppAwdDtls() {
        return appAwdDtls;
    }

    public void setAppAwdDtls(List<IoAppAwdDtlDto> appAwdDtls) {
        this.appAwdDtls = appAwdDtls;
    }

    @Override
    public String toString() {
        return "IoAppAwdDto{" +
                "projId=" + projId +
                ", appAwdMemo='" + appAwdMemo + '\'' +
                ", appAwdDtls=" + appAwdDtls +
                '}';
    }
}
