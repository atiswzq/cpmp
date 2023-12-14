package cn.cofco.cpmp.entity;

import java.math.BigDecimal;

public class KpiTypDef {
    private Long kpiTypId;

    private String kpiTypNam;

    private BigDecimal kpiTypWgt;

    private BigDecimal dspyOrder;

    private String defFlg;

    public Long getKpiTypId() {
        return kpiTypId;
    }

    public void setKpiTypId(Long kpiTypId) {
        this.kpiTypId = kpiTypId;
    }

    public String getKpiTypNam() {
        return kpiTypNam;
    }

    public void setKpiTypNam(String kpiTypNam) {
        this.kpiTypNam = kpiTypNam;
    }

    public BigDecimal getKpiTypWgt() {
        return kpiTypWgt;
    }

    public void setKpiTypWgt(BigDecimal kpiTypWgt) {
        this.kpiTypWgt = kpiTypWgt;
    }

    public BigDecimal getDspyOrder() {
        return dspyOrder;
    }

    public void setDspyOrder(BigDecimal dspyOrder) {
        this.dspyOrder = dspyOrder;
    }

    public String getDefFlg() {
        return defFlg;
    }

    public void setDefFlg(String defFlg) {
        this.defFlg = defFlg;
    }
}