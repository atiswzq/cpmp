package cn.cofco.cpmp.dto;


import java.sql.Timestamp;

/**
 * Created by Wzq on 2018/01/13.
 * for [询价项目上行报文DTO-物料信息DTL] in cpmp
 */
public class IoXjProjMatInfDto {
    // 自增ID
    private Long id;

    // 项目ID
    private Long projId;

    // 需求编号
    private Long reqId;

    // 物料编码
    private String matCod;

    // 物料名称
    private String matNam;

    // 采购数量
    private String pchsNum;

    // 物料计量单位
    private String matUnt;

    // 技术指标与服务要求
    private String techServ;

    // 备注
    private String memo;

    /** 交货日期：格式：2017-11-30 00:00:00 **/
    private String dlvDteStr;
    private Timestamp dlvDte;

    /**
     * 交货地址
     */
    private String dlvAdr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

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

    public String getDlvDteStr() {
        return dlvDteStr;
    }

    public void setDlvDteStr(String dlvDteStr) {
        this.dlvDteStr = dlvDteStr;
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
        return "IoXjProjMatInfDto{" +
                "id=" + id +
                ", projId=" + projId +
                ", reqId=" + reqId +
                ", matCod='" + matCod + '\'' +
                ", matNam='" + matNam + '\'' +
                ", pchsNum='" + pchsNum + '\'' +
                ", matUnt='" + matUnt + '\'' +
                ", techServ='" + techServ + '\'' +
                ", memo='" + memo + '\'' +
                ", dlvDteStr='" + dlvDteStr + '\'' +
                ", dlvDte=" + dlvDte +
                ", dlvAdr='" + dlvAdr + '\'' +
                '}';
    }
}
