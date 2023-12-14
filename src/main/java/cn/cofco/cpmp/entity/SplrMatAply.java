package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrMatAply {
    private Long aplyId;

    private Long splrId;

    private Long matId;

    private Integer aplyUsr;

    private Timestamp aplyTim;

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

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public Integer getAplyUsr() {
        return aplyUsr;
    }

    public void setAplyUsr(Integer aplyUsr) {
        this.aplyUsr = aplyUsr;
    }

    public Timestamp getAplyTim() {
        return aplyTim;
    }

    public void setAplyTim(Timestamp aplyTim) {
        this.aplyTim = aplyTim;
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