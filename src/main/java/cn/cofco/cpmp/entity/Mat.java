package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class Mat {
    private Long matId;

    private String matNam;
    
    private String matCod;

    private String matInd;

    private String matTyp;

    private String matGrup;

    private String unt;

    private String prodGrup;

    private String prcsNbr;

    private String matDscb;

    private String delFlg;

    private String matSpft;

    private Long modUsr;

    private Timestamp modTim;

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public String getMatNam() {
		return matNam;
	}

	public void setMatNam(String matNam) {
		this.matNam = matNam;
	}

	public String getMatCod() {
        return matCod;
    }

    public void setMatCod(String matCod) {
        this.matCod = matCod;
    }

    public String getMatInd() {
        return matInd;
    }

    public void setMatInd(String matInd) {
        this.matInd = matInd;
    }

    public String getMatTyp() {
        return matTyp;
    }

    public void setMatTyp(String matTyp) {
        this.matTyp = matTyp;
    }

    public String getMatGrup() {
        return matGrup;
    }

    public void setMatGrup(String matGrup) {
        this.matGrup = matGrup;
    }

    public String getUnt() {
        return unt;
    }

    public void setUnt(String unt) {
        this.unt = unt;
    }

    public String getProdGrup() {
        return prodGrup;
    }

    public void setProdGrup(String prodGrup) {
        this.prodGrup = prodGrup;
    }

    public String getPrcsNbr() {
        return prcsNbr;
    }

    public void setPrcsNbr(String prcsNbr) {
        this.prcsNbr = prcsNbr;
    }

    public String getMatDscb() {
        return matDscb;
    }

    public void setMatDscb(String matDscb) {
        this.matDscb = matDscb;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getMatSpft() {
        return matSpft;
    }

    public void setMatSpft(String matSpft) {
        this.matSpft = matSpft;
    }

    public Long getModUsr() {
        return modUsr;
    }

    public void setModUsr(Long modUsr) {
        this.modUsr = modUsr;
    }

    public Timestamp getModTim() {
        return modTim;
    }

    public void setModTim(Timestamp modTim) {
        this.modTim = modTim;
    }
}