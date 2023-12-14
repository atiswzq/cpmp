package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.BidProjOn;
import cn.cofco.cpmp.entity.BidProjOnWinDtl;

import java.util.List;

/**
 * Created by Xujy on 2017/9/23.
 * for [获取申请决标相关信息DTO] in cpmp
 */
public class QueryAppAwdInfIoDto {

    private BidProjOn bidProjOn;

    private String matTypDesc;

    private List<QueryAppAwdInfDtlIoDto> appAwdInfDtls;

    private List<BidProjOnWinDtl> winDtls;

    public BidProjOn getBidProjOn() {
        return bidProjOn;
    }

    public void setBidProjOn(BidProjOn bidProjOn) {
        this.bidProjOn = bidProjOn;
    }

    public List<QueryAppAwdInfDtlIoDto> getAppAwdInfDtls() {
        return appAwdInfDtls;
    }

    public void setAppAwdInfDtls(List<QueryAppAwdInfDtlIoDto> appAwdInfDtls) {
        this.appAwdInfDtls = appAwdInfDtls;
    }

    public String getMatTypDesc() {
        return matTypDesc;
    }

    public void setMatTypDesc(String matTypDesc) {
        this.matTypDesc = matTypDesc;
    }

    public List<BidProjOnWinDtl> getWinDtls() {
        return winDtls;
    }

    public void setWinDtls(List<BidProjOnWinDtl> winDtls) {
        this.winDtls = winDtls;
    }

    @Override
    public String toString() {
        return "QueryAppAwdInfIoDto{" +
                "bidProjOn=" + bidProjOn +
                ", matTypDesc='" + matTypDesc + '\'' +
                ", appAwdInfDtls=" + appAwdInfDtls +
                ", winDtls=" + winDtls +
                '}';
    }
}
