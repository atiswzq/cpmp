package cn.cofco.cpmp.dto.exptgrd;

import cn.cofco.cpmp.entity.BidProjOn;

import java.util.List;

/**
 * Created by Xujy on 2017/12/21.
 * for [最低单价评标] in cpmp
 */
public class GrdInfUniLowIoDto {

    private BidProjOn projInf;

    private List<GrdInfUniLowDtlIoDto> list;

    private String projGrdSts;

    public BidProjOn getProjInf() {
        return projInf;
    }

    public void setProjInf(BidProjOn projInf) {
        this.projInf = projInf;
    }

    public List<GrdInfUniLowDtlIoDto> getList() {
        return list;
    }

    public void setList(List<GrdInfUniLowDtlIoDto> list) {
        this.list = list;
    }

    public String getProjGrdSts() {
        return projGrdSts;
    }

    public void setProjGrdSts(String projGrdSts) {
        this.projGrdSts = projGrdSts;
    }

    @Override
    public String toString() {
        return "GrdInfUniLowIoDto{" +
                "projInf=" + projInf +
                ", list=" + list +
                ", projGrdSts=" + projGrdSts +
                '}';
    }
}
