package cn.cofco.cpmp.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Xujy on 2017/11/20.
 * for [文件用途] in cpmp
 */
public class OpenRcdSplrQotIoDto {

    // 供应商全称
    private String splrNam;

    // 供应数量
    private String splNum;

    // 单价
    private BigDecimal price;

    // 币种
    private String currTyp;

    // 交货日期
    private Timestamp tendDlvDte;

    // 报价时汇率
    private String exRat;

    //供应商id
    private Long splrId;

    //品牌
    private String matBnd;

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

    public String getCurrTyp() {
        return currTyp;
    }

    public void setCurrTyp(String currTyp) {
        this.currTyp = currTyp;
    }

    public Timestamp getTendDlvDte() {
        return tendDlvDte;
    }

    public void setTendDlvDte(Timestamp tendDlvDte) {
        this.tendDlvDte = tendDlvDte;
    }

    public String getExRat() {
        return exRat;
    }

    public void setExRat(String exRat) {
        this.exRat = exRat;
    }

    public String getMatBnd() {
        return matBnd;
    }

    public void setMatBnd(String matBnd) {
        this.matBnd = matBnd;
    }

    @Override
    public String toString() {
        return "OpenRcdSplrQotIoDto{" +
                "splrNam='" + splrNam + '\'' +
                ", splNum=" + splNum +
                ", price=" + price +
                ", currTyp='" + currTyp + '\'' +
                ", tendDlvDte=" + tendDlvDte +
                ", exRat='" + exRat + '\'' +
                ", splrId='" + splrId + '\'' +
                ", matBnd='" + matBnd + '\'' +
                '}';
    }
}
