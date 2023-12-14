package cn.cofco.cpmp.dto.exptgrd;

import java.util.List;

/**
 * Created by Xujy on 2017/12/23.
 * for [文件用途] in cpmp
 */
public class GrdClctInfTtlLowDtlIoDto {

    private Long splrId;

    private String splrNam;

    private List<GrdClctInfTtlLowDtlGrdInfIoDto> grdInfs;

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

    public List<GrdClctInfTtlLowDtlGrdInfIoDto> getGrdInfs() {
        return grdInfs;
    }

    public void setGrdInfs(List<GrdClctInfTtlLowDtlGrdInfIoDto> grdInfs) {
        this.grdInfs = grdInfs;
    }

    @Override
    public String toString() {
        return "GrdClctInfTtlLowDtlIoDto{" +
                "splrId=" + splrId +
                ", splrNam='" + splrNam + '\'' +
                ", grdInfs=" + grdInfs +
                '}';
    }
}
