package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/7/15.
 * for [网上竞价项目定标详情申请提交BPM 定标明细 DTO] in cpmp
 */
public class BidProjOnAwdDtlIoDto {

    /**
     * 物料编码
     */
    private String matCod;

    /**
     * 物料名称
     */
    private String matNam;

    /**
     * 采购数量
     */
    private String pchsNum;

    /**
     * 物料计量单位
     */
    private String matUnt;

    /**
     * 客商编码
     */
    private String ptnrCod;

    /**
     * 客商全称
     */
    private String fullNam;

    /**
     * 单价
     */
    private String untPri;

    /**
     * 总价
     */
    private String ttlPri;

    /*
    * 交货地址
    * */
    private String dlvAdr;

    /*
    * 供应商应标日期
    * */
    private String tendDlvDte;

    /*
    * 供应商报价币种
    * */
    private String currTyp;

    /*
    * 定标申请备注
    * */
    private String awdMemo;

    /*
    * 供应商报价品牌
    * */
    private String matBnd;

    public String getDlvAdr() {
        return dlvAdr;
    }

    public void setDlvAdr(String dlvAdr) {
        this.dlvAdr = dlvAdr;
    }

    public String getTendDlvDte() {
        return tendDlvDte;
    }

    public void setTendDlvDte(String tendDlvDte) {
        this.tendDlvDte = tendDlvDte;
    }

    public String getCurrTyp() {
        return currTyp;
    }

    public void setCurrTyp(String currTyp) {
        this.currTyp = currTyp;
    }

    public String getAwdMemo() {
        return awdMemo;
    }

    public void setAwdMemo(String awdMemo) {
        this.awdMemo = awdMemo;
    }

    public String getMatBnd() {
        return matBnd;
    }

    public void setMatBnd(String matBnd) {
        this.matBnd = matBnd;
    }

    public String getMatCod() {
        return matCod;
    }

    public void setMatCod(String matCod) {
        this.matCod = matCod;
    }

    public String getMatNam() {
        return matNam;
    }

    public void setMatNam(String matNam) {
        this.matNam = matNam;
    }

    public String getPchsNum() {
        return pchsNum;
    }

    public void setPchsNum(String pchsNum) {
        this.pchsNum = pchsNum;
    }

    public String getMatUnt() {
        return matUnt;
    }

    public void setMatUnt(String matUnt) {
        this.matUnt = matUnt;
    }

    public String getPtnrCod() {
        return ptnrCod;
    }

    public void setPtnrCod(String ptnrCod) {
        this.ptnrCod = ptnrCod;
    }

    public String getFullNam() {
        return fullNam;
    }

    public void setFullNam(String fullNam) {
        this.fullNam = fullNam;
    }

    public String getUntPri() {
        return untPri;
    }

    public void setUntPri(String untPri) {
        this.untPri = untPri;
    }

    public String getTtlPri() {
        return ttlPri;
    }

    public void setTtlPri(String ttlPri) {
        this.ttlPri = ttlPri;
    }
}
