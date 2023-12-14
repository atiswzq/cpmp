package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/6/11.
 * for [上送附件DTO] in cpmp
 */
public class IoAtchDto {

    private String atchNam;

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
        return "IoAtchDto{" +
                "atchNam='" + atchNam + '\'' +
                ", atchUrl='" + atchUrl + '\'' +
                '}';
    }
}
