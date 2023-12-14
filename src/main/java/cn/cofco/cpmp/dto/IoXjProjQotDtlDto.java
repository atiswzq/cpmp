package cn.cofco.cpmp.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Wzq on 2018/01/13.
 * for [询价项目报价明细DTO] in cpmp
 */
public class IoXjProjQotDtlDto {

    /**
     * 物料编码
     */
    private String matCod;

    /**
     * 物料名称
     */
    private String matNam;

    /**
     * 物料计量单位 - 单位码值
     */
    private String matUnt;

    /**
     * 供应数量
     */
    private String splNum;

    /**
     * 单价
     */
    private BigDecimal price;

    /** 应标交货日期：格式：2017-11-30 00:00:00 **/
    private String tendDlvDteStr;
    private Timestamp tendDlvDte;

    private String matBnd;

    private String currTyp;

    private String exRat;

    /*物料主键*/
    private Long matId;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    @Override
    public String toString() {
        return "IoXjProjQotDtlDto{" +
                "matCod='" + matCod + '\'' +
                ", matNam='" + matNam + '\'' +
                ", matUnt='" + matUnt + '\'' +
                ", splNum='" + splNum + '\'' +
                ", price=" + price +
                ", tendDlvDteStr='" + tendDlvDteStr + '\'' +
                ", tendDlvDte=" + tendDlvDte +
                ", matBnd='" + matBnd + '\'' +
                ", currTyp='" + currTyp + '\'' +
                ", exRat='" + exRat + '\'' +
                ", matId=" + matId +
                '}';
    }
}
