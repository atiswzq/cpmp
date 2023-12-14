package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.Atch;
import cn.cofco.cpmp.entity.BidProjOn;
import cn.cofco.cpmp.entity.BidProjOnMatDtl;
import cn.cofco.cpmp.entity.BidProjOnSplrInvt;

import java.util.List;

/**
 * Created by Xujy on 2017/5/9.
 * for [查看线上招标项目返回报文] in cpmp
 */
public class BidProjOnInfViewIoDto {

    private BidProjOn bidProjOn;
    private String matTypDesc;
    private List<BidProjOnMatDtl> matDtls;
    private List<BidProjOnSplrInvt> splrs;
    private List<Atch> atches;

    public BidProjOn getBidProjOn() {
        return bidProjOn;
    }

    public void setBidProjOn(BidProjOn bidProjOn) {
        this.bidProjOn = bidProjOn;
    }

    public List<BidProjOnMatDtl> getMatDtls() {
        return matDtls;
    }

    public void setMatDtls(List<BidProjOnMatDtl> matDtls) {
        this.matDtls = matDtls;
    }

    public List<BidProjOnSplrInvt> getSplrs() {
        return splrs;
    }

    public void setSplrs(List<BidProjOnSplrInvt> splrs) {
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
        return "BidProjOnInfViewIoDto{" +
                "bidProjOn=" + bidProjOn +
                ", matTypDesc='" + matTypDesc + '\'' +
                ", matDtls=" + matDtls +
                ", splrs=" + splrs +
                ", atches=" + atches +
                '}';
    }
}
