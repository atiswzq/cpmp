package cn.cofco.cpmp.dto;


import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Wzq on 2018/01/15.
 * for [询价项目申请决标明细DTO] in cpmp
 */
public class IoXjAppAwdDtlDto {

    private String matCod;

    private Long splrId;

    private String splrNam;

    private String pchsNum;

    private BigDecimal untPri;

    private BigDecimal ttlPri;

    /** 应标交货日期, 格式：2017-11-30 **/
    private String tendDlvDteStr;
    private Timestamp tendDlvDte;

    private String matBnd;

    private String currTyp;

    private String exRat;

    private String memo;

    /*物料ID*/
    private Long matId;
    /*交货地址*/
    private String dlvAdr;

    public String getMatCod() {
        return matCod;
    }

    public void setMatCod(String matCod) {
        this.matCod = matCod;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getSplrNam() {
        return splrNam;
    }

    public void setSplrNam(String splrNam) {
        this.splrNam = splrNam;
    }

    public String getPchsNum() {
        return pchsNum;
    }

    public void setPchsNum(String pchsNum) {
        this.pchsNum = pchsNum;
    }

    public BigDecimal getUntPri() {
        return untPri;
    }

    public void setUntPri(BigDecimal untPri) {
        this.untPri = untPri;
    }

    public BigDecimal getTtlPri() {
        return ttlPri;
    }

    public void setTtlPri(BigDecimal ttlPri) {
        this.ttlPri = ttlPri;
    }

    public String getTendDlvDteStr() {
        return tendDlvDteStr;
    }

    public void setTendDlvDteStr(String tendDlvDteStr) {
        this.tendDlvDteStr = tendDlvDteStr;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public String getDlvAdr() {
        return dlvAdr;
    }

    public void setDlvAdr(String dlvAdr) {
        this.dlvAdr = dlvAdr;
    }

    @Override
    public String toString() {
        return "IoXjAppAwdDtlDto{" +
                "matCod='" + matCod + '\'' +
                ", splrId=" + splrId +
                ", splrNam='" + splrNam + '\'' +
                ", pchsNum='" + pchsNum + '\'' +
                ", untPri=" + untPri +
                ", ttlPri=" + ttlPri +
                ", tendDlvDteStr='" + tendDlvDteStr + '\'' +
                ", tendDlvDte=" + tendDlvDte +
                ", matBnd='" + matBnd + '\'' +
                ", currTyp='" + currTyp + '\'' +
                ", exRat='" + exRat + '\'' +
                ", memo='" + memo + '\'' +
                ", matId=" + matId +
                ", dlvAdr='" + dlvAdr + '\'' +
                '}';
    }
}
