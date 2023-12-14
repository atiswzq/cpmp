package cn.cofco.cpmp.dto.exptgrd;

import cn.cofco.cpmp.entity.BidProjOn;
import cn.cofco.cpmp.entity.BidProjOnExptGrdInf;
import cn.cofco.cpmp.entity.BidProjOnMatDtl;

import java.util.List;

/**
 * Created by Xujy on 2017/12/23.
 * for [文件用途] in cpmp
 */
public class GrdClctInfUniLowIoDto {

    private BidProjOn projInf;

    private List<String> exptNams;

    private List<BidProjOnMatDtl> matDtls;

    private List<GrdClctInfUniLowDtlIoDto> list;

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

    public List<BidProjOnMatDtl> getMatDtls() {
        return matDtls;
    }

    public void setMatDtls(List<BidProjOnMatDtl> matDtls) {
        this.matDtls = matDtls;
    }

    public List<GrdClctInfUniLowDtlIoDto> getList() {
        return list;
    }

    public void setList(List<GrdClctInfUniLowDtlIoDto> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GrdClctInfUniLowIoDto{" +
                "projInf=" + projInf +
                ", exptNams=" + exptNams +
                ", matDtls=" + matDtls +
                ", list=" + list +
                '}';
    }
}
