package cn.cofco.cpmp.dto;


import cn.cofco.cpmp.entity.XjProjMatDtl;

import java.util.List;

/**
 * Created by Wzq on 2018/01/15.
 * for [获取申请决标相关信息明细DTO] in cpmp
 */
public class QueryXjAppAwdInfDtlIoDto {

    private XjProjMatDtl matDtl;

    private List<XjProjSplrQotDtlIoDto> QotDtls;

    public XjProjMatDtl getMatDtl() {
        return matDtl;
    }

    public void setMatDtl(XjProjMatDtl matDtl) {
        this.matDtl = matDtl;
    }

    public List<XjProjSplrQotDtlIoDto> getQotDtls() {
        return QotDtls;
    }

    public void setQotDtls(List<XjProjSplrQotDtlIoDto> qotDtls) {
        QotDtls = qotDtls;
    }

    @Override
    public String toString() {
        return "QueryXjAppAwdInfDtoIoDto{" +
                "matDtl=" + matDtl +
                ", QotDtls=" + QotDtls +
                '}';
    }
}
