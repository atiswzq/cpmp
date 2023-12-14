package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/9/24.
 * for [附件信息 DTO] in cpmp
 */
public class AtchIoDto {

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
