package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/7/15.
 * for [线下公开招标项目立项申请提交BPM 附件信息 DTO] in cpmp
 */
public class BidProjOffBudAtchIoDto {

    /**
     * 附件名称
     */
    private String atchNam;

    /**
     * 附件URL
     */
    private String atchUrl;

    public String getAtchNam() {
        return atchNam;
    }

    public void setAtchNam(String atchNam) {
        this.atchNam = atchNam;
    }

    public String getAtchUrl() {
        return atchUrl;
    }

    public void setAtchUrl(String atchUrl) {
        this.atchUrl = atchUrl;
    }

    @Override
    public String toString() {
        return "BidProjOffBudAtchIoDto{" +
                "atchNam='" + atchNam + '\'' +
                ", atchUrl='" + atchUrl + '\'' +
                '}';
    }
}
