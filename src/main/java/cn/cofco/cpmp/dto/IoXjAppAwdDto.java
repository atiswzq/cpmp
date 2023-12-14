package cn.cofco.cpmp.dto;


import java.util.List;

/**
 * Created by Wzq on 2018/01/15.
 * for [询价项目申请决标DTO] in cpmp
 */
public class IoXjAppAwdDto {

    private Long projId;

    private String appAwdMemo;

    private List<IoXjAppAwdDtlDto> appAwdDtls;

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

    public List<IoXjAppAwdDtlDto> getAppAwdDtls() {
        return appAwdDtls;
    }

    public void setAppAwdDtls(List<IoXjAppAwdDtlDto> appAwdDtls) {
        this.appAwdDtls = appAwdDtls;
    }

    @Override
    public String toString() {
        return "IoXjAppAwdDto{" +
                "projId=" + projId +
                ", appAwdMemo='" + appAwdMemo + '\'' +
                ", appAwdDtls=" + appAwdDtls +
                '}';
    }
}
