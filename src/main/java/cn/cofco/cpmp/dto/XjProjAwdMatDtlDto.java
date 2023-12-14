package cn.cofco.cpmp.dto;

import java.math.BigDecimal;

/**
 * Created by Wzq on 2018/01/13.
 * for [询价项目定标结果主体] in cpmp
 */
public class XjProjAwdMatDtlDto {
    //物料ID
    private Long matId;

    //物料名字
    private String matNam;

    //物料编码
    private String matCod;

    //物料单位
    private String matUnt;

    //物料品牌
    private String matBnd;

    //物料交货地址
    private String dlvAdr;

    //物料交货日期
    private String dlvDte;

    //物料采购数量
    private String pchsNum;

    //物料应标日期
    private String tendDlvDte;

    //单价
    private BigDecimal untPri;

    //总价
    private BigDecimal ttlPri;

    //供应商名字
    private String splrNam;

    //币种
    private String currTyp;

    //汇率
    private String exRat;

    //项目id
    private Long projId;

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

    public String getMatCod() {
        return matCod;
    }

    public void setMatCod(String matCod) {
        this.matCod = matCod;
    }

    public String getMatUnt() {
        return matUnt;
    }

    public void setMatUnt(String matUnt) {
        this.matUnt = matUnt;
    }

    public String getMatBnd() {
        return matBnd;
    }

    public void setMatBnd(String matBnd) {
        this.matBnd = matBnd;
    }

    public String getDlvAdr() {
        return dlvAdr;
    }

    public void setDlvAdr(String dlvAdr) {
        this.dlvAdr = dlvAdr;
    }

    public String getDlvDte() {
        return dlvDte;
    }

    public void setDlvDte(String dlvDte) {
        this.dlvDte = dlvDte;
    }

    public String getPchsNum() {
        return pchsNum;
    }

    public void setPchsNum(String pchsNum) {
        this.pchsNum = pchsNum;
    }

    public String getTendDlvDte() {
        return tendDlvDte;
    }

    public void setTendDlvDte(String tendDlvDte) {
        this.tendDlvDte = tendDlvDte;
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

    public String getSplrNam() {
        return splrNam;
    }

    public void setSplrNam(String splrNam) {
        this.splrNam = splrNam;
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

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    @Override
    public String toString() {
        return "XjProjAwdMatDtlDto{" +
                "matId=" + matId +
                ", matNam='" + matNam + '\'' +
                ", matCod='" + matCod + '\'' +
                ", matUnt='" + matUnt + '\'' +
                ", matBnd='" + matBnd + '\'' +
                ", dlvAdr='" + dlvAdr + '\'' +
                ", dlvDte='" + dlvDte + '\'' +
                ", pchsNum='" + pchsNum + '\'' +
                ", tendDlvDte='" + tendDlvDte + '\'' +
                ", untPri=" + untPri +
                ", ttlPri=" + ttlPri +
                ", splrNam='" + splrNam + '\'' +
                ", currTyp='" + currTyp + '\'' +
                ", exRat='" + exRat + '\'' +
                ", projId=" + projId +
                '}';
    }
}
