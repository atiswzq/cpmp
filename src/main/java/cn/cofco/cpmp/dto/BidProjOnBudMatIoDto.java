package cn.cofco.cpmp.dto;

import java.sql.Timestamp;

/**
 * Created by Xujy on 2017/7/15.
 * for [网上竞价项目立项申请提交BPM 物料信息 DTO] in cpmp
 */
public class BidProjOnBudMatIoDto {

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
     * 技术指标与服务要求
     */
    private String techServ;

    /**
     * 备注
     */
    private String memo;
    /*
    * 交货地址
    * */
    private String dlvAdr;

    /*
    * 交货日期
    * */
    private String dlvDte;

    public String getDlvAdr() {
        return dlvAdr;
    }

    public void setDlvAdr(String dlvAdr) {
        this.dlvAdr = dlvAdr;
    }

    public String getDlvDte() {
        return dlvDte;
    }

    public void setDlvDte(String dlvDte) {
        this.dlvDte = dlvDte;
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

    public String getTechServ() {
        return techServ;
    }

    public void setTechServ(String techServ) {
        this.techServ = techServ;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
