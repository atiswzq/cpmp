package cn.cofco.cpmp.dto.exptgrd;

import cn.cofco.cpmp.entity.BidProjOn;

import java.util.List;

/**
 * Created by Xujy on 2017/12/21.
 * for [最低总价评标] in cpmp
 */
public class GrdInfTtlLowIoDto {

    private BidProjOn projInf;

    private List<GrdInfTtlLowDtlIoDto> list;

    private String projGrdSts;

    public BidProjOn getProjInf() {
        return projInf;
    }

    public void setProjInf(BidProjOn projInf) {
        this.projInf = projInf;
    }

    public List<GrdInfTtlLowDtlIoDto> getList() {
        return list;
    }

    public void setList(List<GrdInfTtlLowDtlIoDto> list) {
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
        return "GrdInfTtlLowIoDto{" +
                "projInf=" + projInf +
                ", list=" + list +
                ", projGrdSts=" + projGrdSts +
                '}';
    }
}
