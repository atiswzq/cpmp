package cn.cofco.cpmp.dto;


import cn.cofco.cpmp.entity.Atch;
import cn.cofco.cpmp.entity.XjProjSplrQotDtl;

import java.util.List;

/**
 * Created by Wzq on 2018/01/13.
 * for [查看报价详情下行报文] in cpmp
 */
public class XjProjSplrQotIoInfDto {

    private Long id;

    private String qotTyp;

    private Long projId;

    private Long bidId;

    private String servPrms;

    private String qotMemo;

    private List<XjProjSplrQotDtl> qotDtls;

    /**
     * 附件信息
     */
    private List<Atch> atches;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQotTyp() {
        return qotTyp;
    }

    public void setQotTyp(String qotTyp) {
        this.qotTyp = qotTyp;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public String getServPrms() {
        return servPrms;
    }

    public void setServPrms(String servPrms) {
        this.servPrms = servPrms;
    }

    public String getQotMemo() {
        return qotMemo;
    }

    public void setQotMemo(String qotMemo) {
        this.qotMemo = qotMemo;
    }

    public List<XjProjSplrQotDtl> getQotDtls() {
        return qotDtls;
    }

    public void setQotDtls(List<XjProjSplrQotDtl> qotDtls) {
        this.qotDtls = qotDtls;
    }

    public List<Atch> getAtches() {
        return atches;
    }

    public void setAtches(List<Atch> atches) {
        this.atches = atches;
    }

    @Override
    public String toString() {
        return "XjProjSplrQotIoInfDto{" +
                "id=" + id +
                ", qotTyp='" + qotTyp + '\'' +
                ", projId=" + projId +
                ", bidId=" + bidId +
                ", servPrms='" + servPrms + '\'' +
                ", qotMemo='" + qotMemo + '\'' +
                ", qotDtls=" + qotDtls +
                ", atches=" + atches +
                '}';
    }
}
