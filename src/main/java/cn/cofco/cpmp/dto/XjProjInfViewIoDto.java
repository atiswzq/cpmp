package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.*;

import java.util.List;

/**
 * Created by Wzq on 2018/01/13.
 * for [查看询价项目返回报文] in cpmp
 */
public class XjProjInfViewIoDto {
    private XjProj xjProj;
    private String matTypDesc;
    private List<XjProjMatDtl> matDtls;
    private List<XjProjSplrInvt> splrs;
    private List<Atch> atches;

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

    public List<XjProjMatDtl> getMatDtls() {
        return matDtls;
    }

    public void setMatDtls(List<XjProjMatDtl> matDtls) {
        this.matDtls = matDtls;
    }

    public List<XjProjSplrInvt> getSplrs() {
        return splrs;
    }

    public void setSplrs(List<XjProjSplrInvt> splrs) {
        this.splrs = splrs;
    }

    public List<Atch> getAtches() {
        return atches;
    }

    public void setAtches(List<Atch> atches) {
        this.atches = atches;
    }

    @Override
    public String toString() {
        return "XjProjInfViewIoDto{" +
                "xjProj=" + xjProj +
                ", matTypDesc='" + matTypDesc + '\'' +
                ", matDtls=" + matDtls +
                ", splrs=" + splrs +
                ", atches=" + atches +
                '}';
    }
}
