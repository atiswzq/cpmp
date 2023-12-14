package cn.cofco.cpmp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class SplrModAply {
    private Long modId;

    private BigDecimal splrId;

    private String modFld;

    private String oldVal;

    private String newVal;

    private BigDecimal prpr;

    private Timestamp modTim;

    private String adtSts;

    private Date adtTim;

    private String delFlg;

    public Long getModId() {
        return modId;
    }

    public void setModId(Long modId) {
        this.modId = modId;
    }

    public BigDecimal getSplrId() {
        return splrId;
    }

    public void setSplrId(BigDecimal splrId) {
        this.splrId = splrId;
    }

    public String getModFld() {
        return modFld;
    }

    public void setModFld(String modFld) {
        this.modFld = modFld;
    }

    public String getOldVal() {
        return oldVal;
    }

    public void setOldVal(String oldVal) {
        this.oldVal = oldVal;
    }

    public String getNewVal() {
        return newVal;
    }

    public void setNewVal(String newVal) {
        this.newVal = newVal;
    }

    public BigDecimal getPrpr() {
        return prpr;
    }

    public void setPrpr(BigDecimal prpr) {
        this.prpr = prpr;
    }

    public Timestamp getModTim() {
        return modTim;
    }

    public void setModTim(Timestamp modTim) {
        this.modTim = modTim;
    }

    public String getAdtSts() {
        return adtSts;
    }

    public void setAdtSts(String adtSts) {
        this.adtSts = adtSts;
    }

    public Date getAdtTim() {
        return adtTim;
    }

    public void setAdtTim(Date adtTim) {
        this.adtTim = adtTim;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }
}