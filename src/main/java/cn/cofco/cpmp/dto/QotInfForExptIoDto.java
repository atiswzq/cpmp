package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.BidProjOnExptGrdDtl;
import cn.cofco.cpmp.entity.BidProjOnSplrQotDtl;
import cn.cofco.cpmp.entity.BidProjOnSplrQotInf;
import cn.cofco.cpmp.entity.BidProjOnSplrTendInf;

import java.util.List;

/**
 * Created by Xujy on 2017/5/30.
 * for [采购员查询报价信息DTO] in cpmp
 */
public class QotInfForExptIoDto {

    /**
     * 专家评分信息
     */
    private BidProjOnExptGrdDtl bidProjOnExptGrdDtl;

    /**
     * 供应商投标信息
     */
    private BidProjOnSplrTendInf bidProjOnSplrTendInf;

    /**
     * 一次报价
     */
    private BidProjOnSplrQotInf qotInf;

    /**
     * 一次报价明细
     */
    private List<BidProjOnSplrQotDtl> qotDtls;

    /**
     * 二次报价
     */
    private BidProjOnSplrQotInf qotInf2;

    /**
     * 二次报价明细
     */
    private List<BidProjOnSplrQotDtl> qotDtls2;

    public BidProjOnExptGrdDtl getBidProjOnExptGrdDtl() {
        return bidProjOnExptGrdDtl;
    }

    public void setBidProjOnExptGrdDtl(BidProjOnExptGrdDtl bidProjOnExptGrdDtl) {
        this.bidProjOnExptGrdDtl = bidProjOnExptGrdDtl;
    }

    public BidProjOnSplrTendInf getBidProjOnSplrTendInf() {
        return bidProjOnSplrTendInf;
    }

    public void setBidProjOnSplrTendInf(BidProjOnSplrTendInf bidProjOnSplrTendInf) {
        this.bidProjOnSplrTendInf = bidProjOnSplrTendInf;
    }

    public BidProjOnSplrQotInf getQotInf() {
        return qotInf;
    }

    public void setQotInf(BidProjOnSplrQotInf qotInf) {
        this.qotInf = qotInf;
    }

    public List<BidProjOnSplrQotDtl> getQotDtls() {
        return qotDtls;
    }

    public void setQotDtls(List<BidProjOnSplrQotDtl> qotDtls) {
        this.qotDtls = qotDtls;
    }

    public BidProjOnSplrQotInf getQotInf2() {
        return qotInf2;
    }

    public void setQotInf2(BidProjOnSplrQotInf qotInf2) {
        this.qotInf2 = qotInf2;
    }

    public List<BidProjOnSplrQotDtl> getQotDtls2() {
        return qotDtls2;
    }

    public void setQotDtls2(List<BidProjOnSplrQotDtl> qotDtls2) {
        this.qotDtls2 = qotDtls2;
    }

    @Override
    public String toString() {
        return "QotInfForExptIoDto{" +
                "bidProjOnExptGrdDtl=" + bidProjOnExptGrdDtl +
                ", bidProjOnSplrTendInf=" + bidProjOnSplrTendInf +
                ", qotInf=" + qotInf +
                ", qotDtls=" + qotDtls +
                ", qotInf2=" + qotInf2 +
                ", qotDtls2=" + qotDtls2 +
                '}';
    }
}
