package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.BidProjOff;
import cn.cofco.cpmp.entity.BidProjOffSplrRst;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Tao on 2017/6/4.
 */
public class IoBidProjOffAppAwdDtlDto {

    private BidProjOff bidProjOff;

    private String matTypDesc;

    private List<BidProjOffSplrRst> bidProjOffSplrRstLists;

    public BidProjOff getBidProjOff() {
        return bidProjOff;
    }

    public void setBidProjOff(BidProjOff bidProjOff) {
        this.bidProjOff = bidProjOff;
    }

    public List<BidProjOffSplrRst> getBidProjOffSplrRstLists() {
        return bidProjOffSplrRstLists;
    }

    public void setBidProjOffSplrRstLists(List<BidProjOffSplrRst> bidProjOffSplrRstLists) {
        this.bidProjOffSplrRstLists = bidProjOffSplrRstLists;
    }

    public String getMatTypDesc() {
        return matTypDesc;
    }

    public void setMatTypDesc(String matTypDesc) {
        this.matTypDesc = matTypDesc;
    }

    @Override
    public String toString() {
        return "IoBidProjOffAppAwdDtlDto{" +
                "bidProjOff=" + bidProjOff +
                ", matTypDesc='" + matTypDesc + '\'' +
                ", bidProjOffSplrRstLists=" + bidProjOffSplrRstLists +
                '}';
    }
}
