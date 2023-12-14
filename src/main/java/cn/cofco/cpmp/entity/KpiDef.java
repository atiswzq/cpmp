package cn.cofco.cpmp.entity;

import java.math.BigDecimal;

public class KpiDef {
    private Long kpiId;

    private Long kpiTypId;

    private String kpiNam;

    private BigDecimal kpiWgt;

    private BigDecimal dspyOrder;

    private String defFlg;

    public Long getKpiId() {
        return kpiId;
    }

    public void setKpiId(Long kpiId) {
        this.kpiId = kpiId;
    }

    public Long getKpiTypId() {
        return kpiTypId;
    }

    public void setKpiTypId(Long kpiTypId) {
        this.kpiTypId = kpiTypId;
    }

    public String getKpiNam() {
        return kpiNam;
    }

    public void setKpiNam(String kpiNam) {
        this.kpiNam = kpiNam;
    }

    public BigDecimal getKpiWgt() {
        return kpiWgt;
    }

    public void setKpiWgt(BigDecimal kpiWgt) {
        this.kpiWgt = kpiWgt;
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