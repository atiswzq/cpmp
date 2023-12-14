package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/7/15.
 * for [网上竞价项目立项申请提交BPM 定向邀请供应商 DTO] in cpmp
 */
public class BidProjOnBudSplrIoDto {

    /**
     * 客商ID
     */
    private String ptnrId;

    /**
     * 客商编码
     */
    private String ptnrCod;

    /**
     * 客商账户组
     */
    private String acntGrup;

    /**
     * 客商全称
     */
    private String fullNam;

    /**
     * 客商简称
     */
    private String shortNam;

    public String getPtnrId() {
        return ptnrId;
    }

    public void setPtnrId(String ptnrId) {
        this.ptnrId = ptnrId;
    }

    public String getPtnrCod() {
        return ptnrCod;
    }

    public void setPtnrCod(String ptnrCod) {
        this.ptnrCod = ptnrCod;
    }

    public String getAcntGrup() {
        return acntGrup;
    }

    public void setAcntGrup(String acntGrup) {
        this.acntGrup = acntGrup;
    }

    public String getFullNam() {
        return fullNam;
    }

    public void setFullNam(String fullNam) {
        this.fullNam = fullNam;
    }

    public String getShortNam() {
        return shortNam;
    }

    public void setShortNam(String shortNam) {
        this.shortNam = shortNam;
    }
}
