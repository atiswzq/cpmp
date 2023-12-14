package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.BidProjOn;
import cn.cofco.cpmp.entity.BidProjOnWinDtl;
import cn.cofco.cpmp.entity.XjProj;
import cn.cofco.cpmp.entity.XjProjWinDtl;

import java.util.List;

/**
 * Created by Wzq on 2018/01/15.
 * for [获取申请决标相关信息DTO] in cpmp
 */
public class QueryXjAppAwdInfIoDto {

    private XjProj xjProj;

    private String matTypDesc;

    private List<QueryXjAppAwdInfDtlIoDto> xjAppAwdInfDtlIoDtos;

    private List<XjProjWinDtl> winDtls;

    public XjProj getXjProj() {
        return xjProj;
    }

    public void setXjProj(XjProj xjProj) {
        this.xjProj = xjProj;
    }

    public String getMatTypDesc() {
        return matTypDesc;
    }

    public void setMatTypDesc(String matTypDesc) {
        this.matTypDesc = matTypDesc;
    }

    public List<QueryXjAppAwdInfDtlIoDto> getXjAppAwdInfDtlIoDtos() {
        return xjAppAwdInfDtlIoDtos;
    }

    public void setXjAppAwdInfDtlIoDtos(List<QueryXjAppAwdInfDtlIoDto> xjAppAwdInfDtlIoDtos) {
        this.xjAppAwdInfDtlIoDtos = xjAppAwdInfDtlIoDtos;
    }

    public List<XjProjWinDtl> getWinDtls() {
        return winDtls;
    }

    public void setWinDtls(List<XjProjWinDtl> winDtls) {
        this.winDtls = winDtls;
    }

    @Override
    public String toString() {
        return "QueryXjAppAwdInfIoDto{" +
                "xjProj=" + xjProj +
                ", matTypDesc='" + matTypDesc + '\'' +
                ", xjAppAwdInfDtlIoDtos=" + xjAppAwdInfDtlIoDtos +
                ", winDtls=" + winDtls +
                '}';
    }
}
