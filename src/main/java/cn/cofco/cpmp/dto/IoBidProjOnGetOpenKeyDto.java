package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/28.
 * for [获取开标密钥DTO] in cpmp
 */
public class IoBidProjOnGetOpenKeyDto {

    // 项目ID
    private Long projId;

    // 手机号
    private String mobNbr;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getMobNbr() {
        return mobNbr;
    }

    public void setMobNbr(String mobNbr) {
        this.mobNbr = mobNbr;
    }

    @Override
    public String toString() {
        return "IoBidProjOnGetOpenKeyDto{" +
                "projId=" + projId +
                ", mobNbr='" + mobNbr + '\'' +
                '}';
    }
}
