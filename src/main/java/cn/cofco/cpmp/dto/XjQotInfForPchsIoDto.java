package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.*;

import java.util.List;

/**
 * Created by Wzq on 2018/01/14.
 * for [采购员查询询价项目报价信息DTO] in cpmp
 */
public class XjQotInfForPchsIoDto {

    /**
     * 供应商投标信息
     */
    private XjProjSplrTendInf xjProjSplrTendInf;

    /**
     * 一次报价
     */
    private XjProjSplrQotInf qotInf;

    /**
     * 一次报价明细
     */
    private List<IoXjProjSplrQotDto> qotDtls;

    /**
     * 附件信息
     */
    private List<Atch> atches;

    public XjProjSplrTendInf getXjProjSplrTendInf() {
        return xjProjSplrTendInf;
    }

    public void setXjProjSplrTendInf(XjProjSplrTendInf xjProjSplrTendInf) {
        this.xjProjSplrTendInf = xjProjSplrTendInf;
    }

    public XjProjSplrQotInf getQotInf() {
        return qotInf;
    }

    public void setQotInf(XjProjSplrQotInf qotInf) {
        this.qotInf = qotInf;
    }

    public List<IoXjProjSplrQotDto> getQotDtls() {
        return qotDtls;
    }

    public void setQotDtls(List<IoXjProjSplrQotDto> qotDtls) {
        this.qotDtls = qotDtls;
    }

    public List<Atch> getAtches() {
        return atches;
    }

    public void setAtches(List<Atch> atches) {
        this.atches = atches;
    }

    @Override
    public String toString() {
        return "XjQotInfForPchsIoDto{" +
                "bidProjOnSplrTendInf=" + xjProjSplrTendInf +
                ", qotInf=" + qotInf +
                ", qotDtls=" + qotDtls +
                ", atches=" + atches +
                '}';
    }
}
