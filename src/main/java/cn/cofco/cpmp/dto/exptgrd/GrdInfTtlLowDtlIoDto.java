package cn.cofco.cpmp.dto.exptgrd;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Xujy on 2017/12/21.
 * for [最低总价评标 - 明细] in cpmp
 */
public class GrdInfTtlLowDtlIoDto {

    private Long splrId;

    private String splrNam;

    private BigDecimal ttlPri;

    private String isRcmd;

    private String grdRsn;

    private Integer grdOrd;

    private List<GrdInfTtlLowDtlQotInfIoDto> qotInfs;

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

    public BigDecimal getTtlPri() {
        return ttlPri;
    }

    public void setTtlPri(BigDecimal ttlPri) {
        this.ttlPri = ttlPri;
    }

    public String getIsRcmd() {
        return isRcmd;
    }

    public void setIsRcmd(String isRcmd) {
        this.isRcmd = isRcmd;
    }

    public String getGrdRsn() {
        return grdRsn;
    }

    public void setGrdRsn(String grdRsn) {
        this.grdRsn = grdRsn;
    }

    public Integer getGrdOrd() {
        return grdOrd;
    }

    public void setGrdOrd(Integer grdOrd) {
        this.grdOrd = grdOrd;
    }

    public List<GrdInfTtlLowDtlQotInfIoDto> getQotInfs() {
        return qotInfs;
    }

    public void setQotInfs(List<GrdInfTtlLowDtlQotInfIoDto> qotInfs) {
        this.qotInfs = qotInfs;
    }

    @Override
    public String toString() {
        return "GrdInfTtlLowDtlIoDto{" +
                "splrId=" + splrId +
                ", splrNam='" + splrNam + '\'' +
                ", ttlPri=" + ttlPri +
                ", isRcmd='" + isRcmd + '\'' +
                ", grdRsn='" + grdRsn + '\'' +
                ", grdOrd=" + grdOrd +
                ", qotInfs=" + qotInfs +
                '}';
    }
}
