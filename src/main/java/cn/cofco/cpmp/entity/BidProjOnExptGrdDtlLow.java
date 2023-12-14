package cn.cofco.cpmp.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BidProjOnExptGrdDtlLow {
    private Long id;

    private Long grdId;

    private Long bidAppId;

    private Long matId;

    private Long splrId;

    private Long projId;

    private String matCod;

    private String matNam;

    private String matUnt;

    private String techServ;

    private String memo;

    private Long reqId;

    private Timestamp dlvDte;

    private String dlvAdr;

    private String pchsNum;

    private String splrNam;

    private String currTyp;

    private String exRat;

    private BigDecimal untPri;

    private String splNum;

    private BigDecimal ttlPri;

    private Timestamp tendDlvDte;

    private String matBnd;

    private Integer grdOrd;

    private String isRcmd;

    private String grdRsn;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGrdId() {
        return grdId;
    }

    public void setGrdId(Long grdId) {
        this.grdId = grdId;
    }

    public Long getBidAppId() {
        return bidAppId;
    }

    public void setBidAppId(Long bidAppId) {
        this.bidAppId = bidAppId;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
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

    public Long getReqId() {
        return reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
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

    public String getPchsNum() {
        return pchsNum;
    }

    public void setPchsNum(String pchsNum) {
        this.pchsNum = pchsNum;
    }

    public String getSplrNam() {
        return splrNam;
    }

    public void setSplrNam(String splrNam) {
        this.splrNam = splrNam;
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

    public BigDecimal getUntPri() {
        return untPri;
    }

    public void setUntPri(BigDecimal untPri) {
        this.untPri = untPri;
    }

    public String getSplNum() {
        return splNum;
    }

    public void setSplNum(String splNum) {
        this.splNum = splNum;
    }

    public BigDecimal getTtlPri() {
        return ttlPri;
    }

    public void setTtlPri(BigDecimal ttlPri) {
        this.ttlPri = ttlPri;
    }

    public Timestamp getTendDlvDte() {
        return tendDlvDte;
    }

    public void setTendDlvDte(Timestamp tendDlvDte) {
        this.tendDlvDte = tendDlvDte;
    }

    public String getMatBnd() {
        return matBnd;
    }

    public void setMatBnd(String matBnd) {
        this.matBnd = matBnd;
    }

    public Integer getGrdOrd() {
        return grdOrd;
    }

    public void setGrdOrd(Integer grdOrd) {
        this.grdOrd = grdOrd;
    }

    public String getIsRcmd() {
        return isRcmd;
    }

    public void setIsRcmd(String isRcmd) {
        this.isRcmd = isRcmd;
    }

    public String getGrdRsn() {
        return grdRsn;
    }

    public void setGrdRsn(String grdRsn) {
        this.grdRsn = grdRsn;
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
}