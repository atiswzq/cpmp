package cn.cofco.cpmp.dto;

/**
 * Created by Wzq on 2017/5/15.
 * for [供应商询价项目投标信息上送DTO] in cpmp
 */
public class IoXjProjAppBidDto {

    /**
     * 项目ID - 必填
     **/
    private Long projId;
    /**
     * 供应商ID - 必填
     **/
    private Long splrId;
    /**
     * 供应商联系人
     **/
    private String splrCtct;
    /**
     * 供应商联系人电话
     **/
    private String splrCtctTel;
    /**
     * 供应商名称
     **/
    private String splrNam;
    /**
     * 备注
     **/
    private String memo;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getSplrCtct() {
        return splrCtct;
    }

    public void setSplrCtct(String splrCtct) {
        this.splrCtct = splrCtct;
    }

    public String getSplrCtctTel() {
        return splrCtctTel;
    }

    public void setSplrCtctTel(String splrCtctTel) {
        this.splrCtctTel = splrCtctTel;
    }

    public String getSplrNam() {
        return splrNam;
    }

    public void setSplrNam(String splrNam) {
        this.splrNam = splrNam;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "IoXjProjAppBidDto{" +
                "projId=" + projId +
                ", splrId=" + splrId +
                ", splrCtct='" + splrCtct + '\'' +
                ", splrCtctTel='" + splrCtctTel + '\'' +
                ", splrNam='" + splrNam + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
