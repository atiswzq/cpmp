package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/7/15.
 * for [线下公开招标项目定标详情申请提交BPM 定标明细 DTO] in cpmp
 */
public class BidProjOffAwdDtlIoDto {


    /**
     * 客商编码
     */
    private String ptnrCod;

    /**
     * 客商全称
     */
    private String fullNam;

    /**
     * 中标金额
     */
    private String awdAmt;

    /**
     * 物料名称
     */
    private String matNam;

    /**
     * 采购数量
     */
    private String pchsNum;

    /**
     * 物料计量单位
     */
    private String matUnt;


    public String getPtnrCod() {
        return ptnrCod;
    }

    public void setPtnrCod(String ptnrCod) {
        this.ptnrCod = ptnrCod;
    }

    public String getFullNam() {
        return fullNam;
    }

    public void setFullNam(String fullNam) {
        this.fullNam = fullNam;
    }

    public String getAwdAmt() {
        return awdAmt;
    }

    public void setAwdAmt(String awdAmt) {
        this.awdAmt = awdAmt;
    }

    public String getMatNam() {
        return matNam;
    }

    public void setMatNam(String matNam) {
        this.matNam = matNam;
    }

    public String getPchsNum() {
        return pchsNum;
    }

    public void setPchsNum(String pchsNum) {
        this.pchsNum = pchsNum;
    }

    public String getMatUnt() {
        return matUnt;
    }

    public void setMatUnt(String matUnt) {
        this.matUnt = matUnt;
    }

    @Override
    public String toString() {
        return "BidProjOffAwdDtlIoDto{" +
                "ptnrCod='" + ptnrCod + '\'' +
                ", fullNam='" + fullNam + '\'' +
                ", awdAmt='" + awdAmt + '\'' +
                ", matNam='" + matNam + '\'' +
                ", pchsNum=" + pchsNum +
                ", matUnt='" + matUnt + '\'' +
                '}';
    }
}
