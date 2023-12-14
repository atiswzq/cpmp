package cn.cofco.cpmp.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Tao on 2017/6/4.
 */
public class IoBidProjOffAppAwdDto {

    private Long splrId;
    private String matCod;
    private String matNam;
    private String pchsNum;
    private String matUnt;
    private BigDecimal awdAmt;
    private String memo;


    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getMatCod() {
        return matCod;
    }

    public void setMatCod(String matCod) {
        this.matCod = matCod;
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

    public BigDecimal getAwdAmt() {
        return awdAmt;
    }

    public void setAwdAmt(BigDecimal awdAmt) {
        this.awdAmt = awdAmt;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "IoBidProjOffAppAwdDto{" +
                "splrId=" + splrId +
                ", matCod='" + matCod + '\'' +
                ", matNam='" + matNam + '\'' +
                ", pchsNum=" + pchsNum +
                ", matUnt='" + matUnt + '\'' +
                ", awdAmt=" + awdAmt +
                ", memo='" + memo + '\'' +
                '}';
    }
}
