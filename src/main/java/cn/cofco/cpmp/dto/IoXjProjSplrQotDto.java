package cn.cofco.cpmp.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by wzq on 2017/9/24.
 * for [供应商报价明细表2] in cpmp
 */
public class IoXjProjSplrQotDto {

    private Long id;

    private Long qotId;

    private String matCod;

    private String matNam;

    private String matUnt;

    private String splNum;

    private String untPriEcrp;

    private BigDecimal untPri;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    private Long projId;

    private Long splrId;

    private String tendDlvDte;

    private String matBnd;

    private String currTyp;

    private String exRat;

    private Long matId;

    private String dlvAdr;

    private String dlvDte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQotId() {
        return qotId;
    }

    public void setQotId(Long qotId) {
        this.qotId = qotId;
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

    public String getMatUnt() {
        return matUnt;
    }

    public void setMatUnt(String matUnt) {
        this.matUnt = matUnt;
    }

    public String getSplNum() {
        return splNum;
    }

    public void setSplNum(String splNum) {
        this.splNum = splNum;
    }

    public String getUntPriEcrp() {
        return untPriEcrp;
    }

    public void setUntPriEcrp(String untPriEcrp) {
        this.untPriEcrp = untPriEcrp;
    }

    public BigDecimal getUntPri() {
        return untPri;
    }

    public void setUntPri(BigDecimal untPri) {
        this.untPri = untPri;
    }

    public String getEffFlg() {
        return effFlg;
    }

    public void setEffFlg(String effFlg) {
        this.effFlg = effFlg;
    }

    public String getCrtUsr() {
        return crtUsr;
    }

    public void setCrtUsr(String crtUsr) {
        this.crtUsr = crtUsr;
    }

    public Timestamp getCrtTim() {
        return crtTim;
    }

    public void setCrtTim(Timestamp crtTim) {
        this.crtTim = crtTim;
    }

    public String getModUsr() {
        return modUsr;
    }

    public void setModUsr(String modUsr) {
        this.modUsr = modUsr;
    }

    public Timestamp getModTim() {
        return modTim;
    }

    public void setModTim(Timestamp modTim) {
        this.modTim = modTim;
    }

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

    public String getTendDlvDte() {
        return tendDlvDte;
    }

    public void setTendDlvDte(String tendDlvDte) {
        this.tendDlvDte = tendDlvDte;
    }

    public String getMatBnd() {
        return matBnd;
    }

    public void setMatBnd(String matBnd) {
        this.matBnd = matBnd;
    }

    public String getCurrTyp() {
        return currTyp;
    }

    public void setCurrTyp(String currTyp) {
        this.currTyp = currTyp;
    }

    public String getExRat() {
        return exRat;
    }

    public void setExRat(String exRat) {
        this.exRat = exRat;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

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

    @Override
    public String toString() {
        return "IoXjProjSplrQotDto{" +
                "id=" + id +
                ", qotId=" + qotId +
                ", matCod='" + matCod + '\'' +
                ", matNam='" + matNam + '\'' +
                ", matUnt='" + matUnt + '\'' +
                ", splNum='" + splNum + '\'' +
                ", untPriEcrp='" + untPriEcrp + '\'' +
                ", untPri=" + untPri +
                ", effFlg='" + effFlg + '\'' +
                ", crtUsr='" + crtUsr + '\'' +
                ", crtTim=" + crtTim +
                ", modUsr='" + modUsr + '\'' +
                ", modTim=" + modTim +
                ", projId=" + projId +
                ", splrId=" + splrId +
                ", tendDlvDte='" + tendDlvDte + '\'' +
                ", matBnd='" + matBnd + '\'' +
                ", currTyp='" + currTyp + '\'' +
                ", exRat='" + exRat + '\'' +
                ", matId=" + matId +
                ", dlvAdr='" + dlvAdr + '\'' +
                ", dlvDte='" + dlvDte + '\'' +
                '}';
    }
}
