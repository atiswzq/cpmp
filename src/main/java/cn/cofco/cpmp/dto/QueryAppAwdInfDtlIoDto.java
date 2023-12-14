package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.BidProjOnMatDtl;
import cn.cofco.cpmp.entity.BidProjOnSplrQotDtl;

import java.util.List;

/**
 * Created by Xujy on 2017/9/23.
 * for [获取申请决标相关信息明细DTO] in cpmp
 */
public class QueryAppAwdInfDtlIoDto {

    private BidProjOnMatDtl matDtl;

    private List<BidProjOnSplrQotDtlIoDto> QotDtls;

    public BidProjOnMatDtl getMatDtl() {
        return matDtl;
    }

    public void setMatDtl(BidProjOnMatDtl matDtl) {
        this.matDtl = matDtl;
    }

    public List<BidProjOnSplrQotDtlIoDto> getQotDtls() {
        return QotDtls;
    }

    public void setQotDtls(List<BidProjOnSplrQotDtlIoDto> qotDtls) {
        QotDtls = qotDtls;
    }

    @Override
    public String toString() {
        return "QueryAppAwdInfDtlIoDto{" +
                "matDtl=" + matDtl +
                ", QotDtls=" + QotDtls +
                '}';
    }
}
