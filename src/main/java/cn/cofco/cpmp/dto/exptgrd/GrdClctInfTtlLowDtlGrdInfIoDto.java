package cn.cofco.cpmp.dto.exptgrd;

/**
 * Created by Xujy on 2017/12/23.
 * for [文件用途] in cpmp
 */
public class GrdClctInfTtlLowDtlGrdInfIoDto {

    private Long exptId;

    private String exptNam;

    private Integer grdOrd;

    private String grdRsn;

    public Long getExptId() {
        return exptId;
    }

    public void setExptId(Long exptId) {
        this.exptId = exptId;
    }

    public String getExptNam() {
        return exptNam;
    }

    public void setExptNam(String exptNam) {
        this.exptNam = exptNam;
    }

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
        return "GrdClctInfUniLowDtlGrdInfIoDto{" +
                "exptId=" + exptId +
                ", exptNam='" + exptNam + '\'' +
                ", grdOrd=" + grdOrd +
                ", grdRsn='" + grdRsn + '\'' +
                '}';
    }
}
