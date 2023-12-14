package cn.cofco.cpmp.dto;


import cn.cofco.cpmp.entity.BidProjOnWinDtl;

import java.util.List;

/**
 * Created by wzq on 2017/5/30.
 * for [查看专家审批信息 - 采购员] in cpmp
 */
public class BidProjOnExptAppResDto {

    /*
    * 评标ID
    * */
    private Long grdId;
    /*
    *专家姓名
    * */
    private String exptNam;

    /*
    * 专家审批结果
    * */
    private String isAgreed;

    /*
    * 专家审批理由
    * */
    private String projAwdRsn;

    public Long getGrdId() {
        return grdId;
    }

    public void setGrdId(Long grdId) {
        this.grdId = grdId;
    }

    public String getExptNam() {
        return exptNam;
    }

    public void setExptNam(String exptNam) {
        this.exptNam = exptNam;
    }

    public String getIsAgreed() {
        return isAgreed;
    }

    public void setIsAgreed(String isAgreed) {
        this.isAgreed = isAgreed;
    }

    public String getProjAwdRsn() {
        return projAwdRsn;
    }

    public void setProjAwdRsn(String projAwdRsn) {
        this.projAwdRsn = projAwdRsn;
    }
    @Override
    public String toString() {
        return "BidProjOnExptAppResDto{" +
                "grdId='" + grdId + '\'' +
                "exptNam='" + exptNam + '\'' +
                ", isAgreed='" + isAgreed + '\'' +
                ", projAwdRsn='" + projAwdRsn + '\'' +
                '}';
    }
}
