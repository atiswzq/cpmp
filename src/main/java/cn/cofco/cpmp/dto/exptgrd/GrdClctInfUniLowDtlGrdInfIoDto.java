package cn.cofco.cpmp.dto.exptgrd;

import java.util.List;

/**
 * Created by Xujy on 2017/12/23.
 * for [文件用途] in cpmp
 */
public class GrdClctInfUniLowDtlGrdInfIoDto {


    private Long splrId;

    private String splrNam;

    private List<GrdClctInfUniLowDtlGrdInfDtlIoDto> grdList;

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

    public List<GrdClctInfUniLowDtlGrdInfDtlIoDto> getGrdList() {
        return grdList;
    }

    public void setGrdList(List<GrdClctInfUniLowDtlGrdInfDtlIoDto> grdList) {
        this.grdList = grdList;
    }

    @Override
    public String toString() {
        return "GrdClctInfUniLowDtlGrdInfIoDto{" +
                "splrId=" + splrId +
                ", splrNam='" + splrNam + '\'' +
                ", grdList=" + grdList +
                '}';
    }
}
