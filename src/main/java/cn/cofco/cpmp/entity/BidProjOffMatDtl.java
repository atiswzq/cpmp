package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class BidProjOffMatDtl {
    private Long id;

    private Long projId;

    private String matCod;

    private String matNam;

    private String pchsNum;

    private String matUnit;

    private String qualServ;

    private String memo;

    private String winMemo;

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

    public String getPchsNum() {
        return pchsNum;
    }

    public void setPchsNum(String pchsNum) {
        this.pchsNum = pchsNum;
    }

    public String getMatUnit() {
        return matUnit;
    }

    public void setMatUnit(String matUnit) {
        this.matUnit = matUnit;
    }

    public String getQualServ() {
        return qualServ;
    }

    public void setQualServ(String qualServ) {
        this.qualServ = qualServ;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getWinMemo() {
        return winMemo;
    }

    public void setWinMemo(String winMemo) {
        this.winMemo = winMemo;
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