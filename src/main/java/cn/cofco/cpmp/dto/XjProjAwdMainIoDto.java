package cn.cofco.cpmp.dto;

/**
 * Created by Wzq on 2018/01/15.
 * for [询价项目定标申请提交BPM 主体DTO] in cpmp
 */
public class XjProjAwdMainIoDto {

    /**
     * BPM审批流水号, 与BPM系统交互唯一标识，
     * 2位项目类型-8位日期-13位时间戳-6位随机码，（项目类型： JJ-网上竞价项目，GK-公开招标项目，XJ-询价项目）
     * 例如：
     * JJ-20170602-1496569052235-123456
     */
    private String bpmSeqNo;

    /**
     * 项目编号
     */
    private String projNbr;

    /**
     * 定标备注
     */
    private String appAwdMemo;

    /**
     * 项目名称
     */
    private String projNam;

    /**
     * 申请人
     */
    private String createusername;

    /**
     * 申请时间
     */
    private String createdate;

  /*  *//*
    * 评标规则
    * *//*
    private String grdRul;*/

    /*
    * 备注（报价方式及其他）
    * */
    private String projMemo;

    /**
     * 合计价格描述: 例如：RMB:2000; USD:5000; EUR:1000;
     */
    private String ttlPriceMsg;

   /* *//**
     * 评标专家
     *//*
    private String grdExpt;*/


    public String getBpmSeqNo() {
        return bpmSeqNo;
    }

    public void setBpmSeqNo(String bpmSeqNo) {
        this.bpmSeqNo = bpmSeqNo;
    }

    public String getProjNbr() {
        return projNbr;
    }

    public void setProjNbr(String projNbr) {
        this.projNbr = projNbr;
    }

    public String getAppAwdMemo() {
        return appAwdMemo;
    }

    public void setAppAwdMemo(String appAwdMemo) {
        this.appAwdMemo = appAwdMemo;
    }

    public String getProjNam() {
        return projNam;
    }

    public void setProjNam(String projNam) {
        this.projNam = projNam;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getProjMemo() {
        return projMemo;
    }

    public void setProjMemo(String projMemo) {
        this.projMemo = projMemo;
    }

    public String getTtlPriceMsg() {
        return ttlPriceMsg;
    }

    public void setTtlPriceMsg(String ttlPriceMsg) {
        this.ttlPriceMsg = ttlPriceMsg;
    }


    @Override
    public String toString() {
        return "XjProjAwdMainIoDto{" +
                "bpmSeqNo='" + bpmSeqNo + '\'' +
                ", projNbr='" + projNbr + '\'' +
                ", appAwdMemo='" + appAwdMemo + '\'' +
                ", projNam='" + projNam + '\'' +
                ", createusername='" + createusername + '\'' +
                ", createdate='" + createdate + '\'' +
                ", projMemo='" + projMemo + '\'' +
                ", ttlPriceMsg='" + ttlPriceMsg + '\'' +
                '}';
    }
}
