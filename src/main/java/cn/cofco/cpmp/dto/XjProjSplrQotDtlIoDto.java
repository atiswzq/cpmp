package cn.cofco.cpmp.dto;


import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Wzq on 2018/01/15.
 * for [供应商报价明细表] in cpmp
 */
public class XjProjSplrQotDtlIoDto {

    private String matCod;

    private String matNam;

    private String matUnt;

    private String splNum;

    private BigDecimal untPri;

    private Long splrId;

    private Long projId;

    private String fullNam;

    /*应标日期*/
    private Timestamp tendDlvDte;

    /*物料品牌*/
    private String matBnd;

    /*n报价币种*/
    private String currTyp;

    /*当前汇率*/
    private String exRat;

    /*物料ID*/
    private Long matId;

    /*
    * 汇率换算后价格*/
    private BigDecimal trpPrice;

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

    public String getMatUnt() {
        return matUnt;
    }

    public void setMatUnt(String matUnt) {
        this.matUnt = matUnt;
    }

    public String getSplNum() {
        return splNum;
    }

    public void setSplNum(String splNum) {
        this.splNum = splNum;
    }

    public BigDecimal getUntPri() {
        return untPri;
    }

    public void setUntPri(BigDecimal untPri) {
        this.untPri = untPri;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getFullNam() {
        return fullNam;
    }

    public void setFullNam(String fullNam) {
        this.fullNam = fullNam;
    }

    public Timestamp getTendDlvDte() {
        return tendDlvDte;
    }

    public void setTendDlvDte(Timestamp tendDlvDte) {
        this.tendDlvDte = tendDlvDte;
    }

    public String getMatBnd() {
        return matBnd;
    }

    public void setMatBnd(String matBnd) {
        this.matBnd = matBnd;
    }

    public String getCurrTyp() {
        return currTyp;
    }

    public void setCurrTyp(String currTyp) {
        this.currTyp = currTyp;
    }

    public String getExRat() {
        return exRat;
    }

    public void setExRat(String exRat) {
        this.exRat = exRat;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public BigDecimal getTrpPrice() {
        return trpPrice;
    }

    public void setTrpPrice(BigDecimal trpPrice) {
        this.trpPrice = trpPrice;
    }

    @Override
    public String toString() {
        return "XjProjSplrQotDtlIoDto{" +
                "matCod='" + matCod + '\'' +
                ", matNam='" + matNam + '\'' +
                ", matUnt='" + matUnt + '\'' +
                ", splNum='" + splNum + '\'' +
                ", untPri=" + untPri +
                ", splrId=" + splrId +
                ", projId=" + projId +
                ", fullNam='" + fullNam + '\'' +
                ", tendDlvDte=" + tendDlvDte +
                ", matBnd='" + matBnd + '\'' +
                ", currTyp='" + currTyp + '\'' +
                ", exRat='" + exRat + '\'' +
                ", matId=" + matId +
                ", trpPrice=" + trpPrice +
                '}';
    }
}
