package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrAptModAply {
    private Long aplyId;

    private Long splrId;

    private Long aptId;

    private String aptFld;

    private String fldVal;

    private Long aplyUsr;

    private Timestamp aplyTim;

    private Long adtUsr;

    private Timestamp adtTim;

    private String adtSts;

    private String delFlg;

    public Long getAplyId() {
        return aplyId;
    }

    public void setAplyId(Long aplyId) {
        this.aplyId = aplyId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public Long getAptId() {
        return aptId;
    }

    public void setAptId(Long aptId) {
        this.aptId = aptId;
    }

    public String getAptFld() {
        return aptFld;
    }

    public void setAptFld(String aptFld) {
        this.aptFld = aptFld;
    }

    public String getFldVal() {
        return fldVal;
    }

    public void setFldVal(String fldVal) {
        this.fldVal = fldVal;
    }

    public Long getAplyUsr() {
        return aplyUsr;
    }

    public void setAplyUsr(Long aplyUsr) {
        this.aplyUsr = aplyUsr;
    }

    public Timestamp getAplyTim() {
        return aplyTim;
    }

    public void setAplyTim(Timestamp aplyTim) {
        this.aplyTim = aplyTim;
    }

    public Long getAdtUsr() {
        return adtUsr;
    }

    public void setAdtUsr(Long adtUsr) {
        this.adtUsr = adtUsr;
    }

    public Timestamp getAdtTim() {
        return adtTim;
    }

    public void setAdtTim(Timestamp adtTim) {
        this.adtTim = adtTim;
    }

    public String getAdtSts() {
        return adtSts;
    }

    public void setAdtSts(String adtSts) {
        this.adtSts = adtSts;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }
}