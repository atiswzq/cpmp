package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.Atch;
import cn.cofco.cpmp.entity.BidProjOff;
import cn.cofco.cpmp.entity.BidProjOffSplrRlt;

import java.util.List;

/**
 * Created by Tao on 2017/5/17.
 */
public class BidProjOffInfViewIoDto {

    private BidProjOff bidProjOff;
//    private List<BidProjOffMatDtl> matDtls;
    private List<BidProjOffSplrRlt> splrs;

    private List<Atch> atches;

    private String matTypDesc;

    public BidProjOff getBidProjOff() {
        return bidProjOff;
    }

    public void setBidProjOff(BidProjOff bidProjOff) {
        this.bidProjOff = bidProjOff;
    }

    public List<BidProjOffSplrRlt> getSplrs() {
        return splrs;
    }

    public void setSplrs(List<BidProjOffSplrRlt> splrs) {
        this.splrs = splrs;
    }

    public List<Atch> getAtches() {
        return atches;
    }

    public void setAtches(List<Atch> atches) {
        this.atches = atches;
    }

    public String getMatTypDesc() {
        return matTypDesc;
    }

    public void setMatTypDesc(String matTypDesc) {
        this.matTypDesc = matTypDesc;
    }

    @Override
    public String toString() {
        return "BidProjOffInfViewIoDto{" +
                "bidProjOff=" + bidProjOff +
                ", splrs=" + splrs +
                ", atches=" + atches +
                ", matTypDesc='" + matTypDesc + '\'' +
                '}';
    }
}
