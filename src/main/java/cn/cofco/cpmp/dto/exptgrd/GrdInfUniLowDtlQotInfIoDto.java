package cn.cofco.cpmp.dto.exptgrd;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Xujy on 2017/12/23.
 * for [最低单价评标明细 - 物料 - 各供应商报价] in cpmp
 */
public class GrdInfUniLowDtlQotInfIoDto {

    private Long id;

    private Long splrId;

    private String splrNam;

    private String isRcmd;

    private String currTyp;

    private String exRat;

    private String uniPri;

    private String splNum;

    private BigDecimal ttlPri;

    private Timestamp tendDlvDte;

    private String matBnd;

    private String grdOrd;

    private String grdRsn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIsRcmd() {
        return isRcmd;
    }

    public void setIsRcmd(String isRcmd) {
        this.isRcmd = isRcmd;
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

    public String getUniPri() {
        return uniPri;
    }

    public void setUniPri(String uniPri) {
        this.uniPri = uniPri;
    }

    public String getSplNum() {
        return splNum;
    }

    public void setSplNum(String splNum) {
        this.splNum = splNum;
    }

    public BigDecimal getTtlPri() {
        return ttlPri;
    }

    public void setTtlPri(BigDecimal ttlPri) {
        this.ttlPri = ttlPri;
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

    public String getGrdOrd() {
        return grdOrd;
    }

    public void setGrdOrd(String grdOrd) {
        this.grdOrd = grdOrd;
    }

    public String getGrdRsn() {
        return grdRsn;
    }

    public void setGrdRsn(String grdRsn) {
        this.grdRsn = grdRsn;
    }

    @Override
    public String toString() {
        return "GrdInfUniLowDtlQotInfIoDto{" +
                "id=" + id +
                ", splrId=" + splrId +
                ", splrNam='" + splrNam + '\'' +
                ", isRcmd='" + isRcmd + '\'' +
                ", currTyp='" + currTyp + '\'' +
                ", exRat='" + exRat + '\'' +
                ", uniPri='" + uniPri + '\'' +
                ", splNum='" + splNum + '\'' +
                ", ttlPri='" + ttlPri + '\'' +
                ", tendDlvDte=" + tendDlvDte +
                ", matBnd='" + matBnd + '\'' +
                ", grdOrd='" + grdOrd + '\'' +
                ", grdRsn='" + grdRsn + '\'' +
                '}';
    }
}
