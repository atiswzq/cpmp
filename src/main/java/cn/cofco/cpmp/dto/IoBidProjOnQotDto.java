package cn.cofco.cpmp.dto;

import java.util.List;

/**
 * Created by Xujy on 2017/5/21.
 * for [线上招标项目报价信息DTO] in cpmp
 */
public class IoBidProjOnQotDto {

    /**
     * 供应商投标ID
     */
    private Long bidId;

    /**
     * 服务承诺
     */
    private String servPrms;

    /**
     * 报价备注
     */
    private String qotMemo;

    /**
     * 报价明细
     */
    private List<IoBidProjOnQotDtlDto> dtls;

    /**
     * 附件信息
     */
    private List<IoAtchDto> atchDtos;

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

    public List<IoBidProjOnQotDtlDto> getDtls() {
        return dtls;
    }

    public void setDtls(List<IoBidProjOnQotDtlDto> dtls) {
        this.dtls = dtls;
    }

    public List<IoAtchDto> getAtchDtos() {
        return atchDtos;
    }

    public void setAtchDtos(List<IoAtchDto> atchDtos) {
        this.atchDtos = atchDtos;
    }

    @Override
    public String toString() {
        return "IoBidProjOnQotDto{" +
                "bidId=" + bidId +
                ", servPrms='" + servPrms + '\'' +
                ", qotMemo='" + qotMemo + '\'' +
                ", dtls=" + dtls +
                ", atchDtos=" + atchDtos +
                '}';
    }
}
