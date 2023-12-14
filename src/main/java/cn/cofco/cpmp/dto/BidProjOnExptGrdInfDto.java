package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.BidProjOnExptGrdInf;

/**
 * Created by wzq on 2017/5/30.
 * for [查看项目评标信息 - 专家] in cpmp
 */
public class BidProjOnExptGrdInfDto {

    private String projSts;

    private BidProjOnExptGrdInf bidProjOnExptGrdInf;

    public String getProjSts() {
        return projSts;
    }

    public void setProjSts(String projSts) {
        this.projSts = projSts;
    }

    public BidProjOnExptGrdInf getBidProjOnExptGrdInf() {
        return bidProjOnExptGrdInf;
    }

    public void setBidProjOnExptGrdInf(BidProjOnExptGrdInf bidProjOnExptGrdInf) {
        this.bidProjOnExptGrdInf = bidProjOnExptGrdInf;
    }

    @Override
    public String toString() {
        return "BidProjOnExptGrdInfDto{" +
                "projSts='" + projSts + '\'' +
                ", bidProjOnExptGrdInf=" + bidProjOnExptGrdInf +
                '}';
    }
}
