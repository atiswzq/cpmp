package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.BidProjOnExptGrdInf;
import cn.cofco.cpmp.entity.BidProjOnMatDtl;
import cn.cofco.cpmp.entity.BidProjOnWinDtl;

import java.util.List;

/**
 * Created by wzq on 2017/5/30.
 * for [查看定标结果信息 - 专家] in cpmp
 */
public class BidProjOnExptAwdResIoDto {
    /*
    * 专家评标信息
    * */
    private BidProjOnExptGrdInf bidProjOnExptGrdInf;

    /*
    * 定标结果信息
    * */
    private List<BidProjOnWinDtl> bidProjOnWinDtls;
    /*
    * 项目物料信息
    * */
    private List<BidProjOnMatDtl> projOnMatDtls;

    public BidProjOnExptGrdInf getBidProjOnExptGrdInf() {
        return bidProjOnExptGrdInf;
    }

    public void setBidProjOnExptGrdInf(BidProjOnExptGrdInf bidProjOnExptGrdInf) {
        this.bidProjOnExptGrdInf = bidProjOnExptGrdInf;
    }

    public List<BidProjOnWinDtl> getBidProjOnWinDtls() {
        return bidProjOnWinDtls;
    }

    public void setBidProjOnWinDtls(List<BidProjOnWinDtl> bidProjOnWinDtls) {
        this.bidProjOnWinDtls = bidProjOnWinDtls;
    }

    public List<BidProjOnMatDtl> getProjOnMatDtls() {
        return projOnMatDtls;
    }

    public void setProjOnMatDtls(List<BidProjOnMatDtl> projOnMatDtls) {
        this.projOnMatDtls = projOnMatDtls;
    }

    @Override
    public String toString() {
        return "BidProjOnExptAwdResIoDto{" +
                "bidProjOnExptGrdInf=" + bidProjOnExptGrdInf +
                ", bidProjOnWinDtls=" + bidProjOnWinDtls +
                ", projOnMatDtls=" + projOnMatDtls +
                '}';
    }
}
