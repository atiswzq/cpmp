package cn.cofco.cpmp.entity;

import java.math.BigDecimal;

public class SplrChkItem {
    private Long chkItemId;

    private Long admtChkId;

    private Long chkDefId;

    private Long order;

    private Long kpiId;

    private String kpiNam;

    private Long score;

    public Long getChkItemId() {
        return chkItemId;
    }

    public void setChkItemId(Long chkItemId) {
        this.chkItemId = chkItemId;
    }

    public Long getAdmtChkId() {
        return admtChkId;
    }

    public void setAdmtChkId(Long admtChkId) {
        this.admtChkId = admtChkId;
    }

    public Long getChkDefId() {
        return chkDefId;
    }

    public void setChkDefId(Long chkDefId) {
        this.chkDefId = chkDefId;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Long getKpiId() {
        return kpiId;
    }

    public void setKpiId(Long kpiId) {
        this.kpiId = kpiId;
    }

    public String getKpiNam() {
        return kpiNam;
    }

    public void setKpiNam(String kpiNam) {
        this.kpiNam = kpiNam;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}