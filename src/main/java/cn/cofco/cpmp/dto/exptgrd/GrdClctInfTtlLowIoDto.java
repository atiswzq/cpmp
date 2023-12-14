package cn.cofco.cpmp.dto.exptgrd;

import cn.cofco.cpmp.entity.BidProjOn;
import cn.cofco.cpmp.entity.BidProjOnExptGrdInf;

import java.util.List;

/**
 * Created by Xujy on 2017/12/23.
 * for [文件用途] in cpmp
 */
public class GrdClctInfTtlLowIoDto {

    private BidProjOn projInf;

    private List<String> exptNams;

    private List<GrdClctInfTtlLowDtlIoDto> list;

    public BidProjOn getProjInf() {
        return projInf;
    }

    public void setProjInf(BidProjOn projInf) {
        this.projInf = projInf;
    }

    public List<String> getExptNams() {
        return exptNams;
    }

    public void setExptNams(List<String> exptNams) {
        this.exptNams = exptNams;
    }

    public List<GrdClctInfTtlLowDtlIoDto> getList() {
        return list;
    }

    public void setList(List<GrdClctInfTtlLowDtlIoDto> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GrdClctInfUniLowIoDto{" +
                "projInf=" + projInf +
                ", exptNams=" + exptNams +
                ", list=" + list +
                '}';
    }
}
