package cn.cofco.cpmp.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Xujy on 2017/9/24.
 * for [物料信息] in cpmp
 */
public class MatInfIoDto {

    private Long  reqId;

    private String matCod;

    private String matNam;

    // 物料组
    private String matTyp;

    // 物料组描述
    private String matTypDsc;

    // 计量单位
    private String matUnt;

    // 计量单位名称
    private String matSpft;

    // 采购数量
    private String pchsNum;

    /** 交货日期：格式：2017-11-30 00:00:00 **/
    private Timestamp dlvDte;

    /**
     * 交货地址
     */
    private String dlvAdr;

    public Long getReqId() {
        return reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
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

    public String getMatTyp() {
        return matTyp;
    }

    public void setMatTyp(String matTyp) {
        this.matTyp = matTyp;
    }

    public String getMatTypDsc() {
        return matTypDsc;
    }

    public void setMatTypDsc(String matTypDsc) {
        this.matTypDsc = matTypDsc;
    }

    public String getMatUnt() {
        return matUnt;
    }

    public void setMatUnt(String matUnt) {
        this.matUnt = matUnt;
    }

    public String getMatSpft() {
        return matSpft;
    }

    public void setMatSpft(String matSpft) {
        this.matSpft = matSpft;
    }

    public String getPchsNum() {
        return pchsNum;
    }

    public void setPchsNum(String pchsNum) {
        this.pchsNum = pchsNum;
    }

    public Timestamp getDlvDte() {
        return dlvDte;
    }

    public void setDlvDte(Timestamp dlvDte) {
        this.dlvDte = dlvDte;
    }

    public String getDlvAdr() {
        return dlvAdr;
    }

    public void setDlvAdr(String dlvAdr) {
        this.dlvAdr = dlvAdr;
    }

    @Override
    public String toString() {
        return "MatInfIoDto{" +
                "reqId=" + reqId +
                ", matCod='" + matCod + '\'' +
                ", matNam='" + matNam + '\'' +
                ", matTyp='" + matTyp + '\'' +
                ", matTypDsc='" + matTypDsc + '\'' +
                ", matUnt='" + matUnt + '\'' +
                ", matSpft='" + matSpft + '\'' +
                ", pchsNum=" + pchsNum +
                ", dlvDte=" + dlvDte +
                ", dlvAdr='" + dlvAdr + '\'' +
                '}';
    }
}
