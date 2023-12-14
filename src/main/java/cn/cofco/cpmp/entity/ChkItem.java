package cn.cofco.cpmp.entity;

import java.math.BigDecimal;

public class ChkItem {
    private Long chkItemId;

    private Long chkDefId;

    private BigDecimal dspyOrder;

    private Long kpiId;

    public Long getChkItemId() {
        return chkItemId;
    }

    public void setChkItemId(Long chkItemId) {
        this.chkItemId = chkItemId;
    }

    public Long getChkDefId() {
        return chkDefId;
    }

    public void setChkDefId(Long chkDefId) {
        this.chkDefId = chkDefId;
    }

    public BigDecimal getDspyOrder() {
        return dspyOrder;
    }

    public void setDspyOrder(BigDecimal dspyOrder) {
        this.dspyOrder = dspyOrder;
    }

    public Long getKpiId() {
        return kpiId;
    }

    public void setKpiId(Long kpiId) {
        this.kpiId = kpiId;
    }
}