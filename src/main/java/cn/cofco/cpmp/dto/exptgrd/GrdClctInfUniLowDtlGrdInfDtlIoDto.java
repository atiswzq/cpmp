package cn.cofco.cpmp.dto.exptgrd;

public class GrdClctInfUniLowDtlGrdInfDtlIoDto {

    private Integer grdOrd;

    private String grdRsn;

    public Integer getGrdOrd() {
        return grdOrd;
    }

    public void setGrdOrd(Integer grdOrd) {
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
        return "GrdClctInfUniLowDtlGrdInfDtlIoDto{" +
                "grdOrd=" + grdOrd +
                ", grdRsn='" + grdRsn + '\'' +
                '}';
    }
}
