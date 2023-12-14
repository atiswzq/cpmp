package cn.cofco.cpmp.dto.exptgrd;

import java.math.BigDecimal;

/**
 * Created by Xujy on 2017/12/21.
 * for [最低总价评标 - 明细 - 报价明细] in cpmp
 */
public class GrdInfTtlLowDtlQotInfIoDto {

    private Long id;

    private Long matId;

    private String matNam;

    private String currTyp;

    private BigDecimal uniPri;

    private String exRat;

    private String splNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public String getMatNam() {
        return matNam;
    }

    public void setMatNam(String matNam) {
        this.matNam = matNam;
    }

    public String getCurrTyp() {
        return currTyp;
    }

    public void setCurrTyp(String currTyp) {
        this.currTyp = currTyp;
    }

    public BigDecimal getUniPri() {
        return uniPri;
    }

    public void setUniPri(BigDecimal uniPri) {
        this.uniPri = uniPri;
    }

    public String getExRat() {
        return exRat;
    }

    public void setExRat(String exRat) {
        this.exRat = exRat;
    }

    public String getSplNum() {
        return splNum;
    }

    public void setSplNum(String splNum) {
        this.splNum = splNum;
    }

    @Override
    public String toString() {
        return "GrdInfTtlLowDtlQotInfIoDto{" +
                "id=" + id +
                ", matId=" + matId +
                ", matNam='" + matNam + '\'' +
                ", currTyp='" + currTyp + '\'' +
                ", uniPri=" + uniPri +
                ", exRat='" + exRat + '\'' +
                ", splNum='" + splNum + '\'' +
                '}';
    }
}
