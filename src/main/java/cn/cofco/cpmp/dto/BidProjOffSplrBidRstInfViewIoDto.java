package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.BidProjOff;
import cn.cofco.cpmp.entity.BidProjOffSplrInf;
import cn.cofco.cpmp.entity.Splr;

import java.util.List;

/**
 * Created by Tao on 2017/6/29.
 */
public class BidProjOffSplrBidRstInfViewIoDto {
    private BidProjOff bidProjOff;
    //    private List<BidProjOffMatDtl> matDtls;
    private List<Splr> splrs;

    public BidProjOff getBidProjOff() {
        return bidProjOff;
    }

    public void setBidProjOff(BidProjOff bidProjOff) {
        this.bidProjOff = bidProjOff;
    }

    public List<Splr> getSplrs() {
        return splrs;
    }

    public void setSplrs(List<Splr> splrs) {
        this.splrs = splrs;
    }

    @Override
    public String toString() {
        return "BidProjOffSplrBidRstInfViewIoDto{" +
                "bidProjOff=" + bidProjOff +
                ", splrs=" + splrs +
                '}';
    }
}
