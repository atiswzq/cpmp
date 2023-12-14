package cn.cofco.cpmp.dto;

/**
 * Created by Tao on 2017/5/31.
 * *for [供应商线下项目投标信息上送DTO] in cpmp
 */
public class IoBidProjOffAppBidDto {

    /**
     * 项目ID - 必填
     **/
    private Long projId;
    /**
     * 供应商ID - 必填
     **/
    private Long splrId;


    /**
     * 供应商联系人
     **/
    private String cntct;

    /**
     * 供应商联系人电话
     **/

    private String cntctTel;
    /**
     * 供应商名称
     **/
    private String splrNam;
    /**
     * 保证金状态
     **/
    private String dpstSts;
    /**
     * 保证金图片
     **/
    private String dpstPic;
    /**
     * 备注
     **/
    private String memo;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }


    public String getCntct() {
        return cntct;
    }

    public void setCntct(String cntct) {
        this.cntct = cntct;
    }
    public String getCntctTel() {
        return cntctTel;
    }

    public void setCntctTel(String cntctTel) {
        this.cntctTel = cntctTel;
    }
    public String getSplrNam() {
        return splrNam;
    }

    public void setSplrNam(String splrNam) {
        this.splrNam = splrNam;
    }

    public String getDpstSts() {
        return dpstSts;
    }

    public void setDpstSts(String dpstSts) {
        this.dpstSts = dpstSts;
    }

    public String getDpstPic() {
        return dpstPic;
    }

    public void setDpstPic(String dpstPic) {
        this.dpstPic = dpstPic;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "IoBidProjOnAppBidDto{" +
                "projId=" + projId +
                ", splrId=" + splrId +
                ", splrCtct='" + cntct + '\'' +
                ", splrCtctTel='" + cntctTel + '\'' +
                ", splrNam='" + splrNam + '\'' +
                ", dpstSts='" + dpstSts + '\'' +
                ", dpstPic='" + dpstPic + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
