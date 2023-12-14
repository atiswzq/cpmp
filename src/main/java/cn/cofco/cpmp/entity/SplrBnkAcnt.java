package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrBnkAcnt {
    private Long bnkAcntId;
    
    private String bnkAcntTyp;

    private Long splrId;

    private String bnkCnty;

    private String bnkCod;

    private String bnkNam;

    private String bnkAcnt;

    private String acntNam;

    private String swiftCod;

    private String dft;

    private String bnkAcntSts;

    private String dffFlg;

    private Long crtUsr;

    private Timestamp crtTim;

    private Long modUsr;

    private Timestamp modTim;

    public Long getBnkAcntId() {
        return bnkAcntId;
    }

    public void setBnkAcntId(Long bnkAcntId) {
        this.bnkAcntId = bnkAcntId;
    }

    
    public String getBnkAcntTyp() {
		return bnkAcntTyp;
	}

	public void setBnkAcntTyp(String bnkAcntTyp) {
		this.bnkAcntTyp = bnkAcntTyp;
	}

	public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getBnkCnty() {
        return bnkCnty;
    }

    public void setBnkCnty(String bnkCnty) {
        this.bnkCnty = bnkCnty;
    }

    public String getBnkCod() {
        return bnkCod;
    }

    public void setBnkCod(String bnkCod) {
        this.bnkCod = bnkCod;
    }

    public String getBnkNam() {
        return bnkNam;
    }

    public void setBnkNam(String bnkNam) {
        this.bnkNam = bnkNam;
    }

    public String getBnkAcnt() {
        return bnkAcnt;
    }

    public void setBnkAcnt(String bnkAcnt) {
        this.bnkAcnt = bnkAcnt;
    }

    public String getAcntNam() {
        return acntNam;
    }

    public void setAcntNam(String acntNam) {
        this.acntNam = acntNam;
    }

    public String getSwiftCod() {
        return swiftCod;
    }

    public void setSwiftCod(String swiftCod) {
        this.swiftCod = swiftCod;
    }

    public String getDft() {
        return dft;
    }

    public void setDft(String dft) {
        this.dft = dft;
    }

    public String getBnkAcntSts() {
        return bnkAcntSts;
    }

    public void setBnkAcntSts(String bnkAcntSts) {
        this.bnkAcntSts = bnkAcntSts;
    }

    public String getDffFlg() {
        return dffFlg;
    }

    public void setDffFlg(String dffFlg) {
        this.dffFlg = dffFlg;
    }

    public Long getCrtUsr() {
        return crtUsr;
    }

    public void setCrtUsr(Long crtUsr) {
        this.crtUsr = crtUsr;
    }

    public Timestamp getCrtTim() {
        return crtTim;
    }

    public void setCrtTim(Timestamp crtTim) {
        this.crtTim = crtTim;
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

	@Override
	public String toString() {
		return "SplrBnkAcnt [bnkAcntId=" + bnkAcntId + ", bnkAcntTyp="
				+ bnkAcntTyp + ", splrId=" + splrId + ", bnkCnty=" + bnkCnty
				+ ", bnkCod=" + bnkCod + ", bnkNam=" + bnkNam + ", bnkAcnt="
				+ bnkAcnt + ", acntNam=" + acntNam + ", swiftCod=" + swiftCod
				+ ", dft=" + dft + ", bnkAcntSts=" + bnkAcntSts + ", dffFlg="
				+ dffFlg + ", crtUsr=" + crtUsr + ", crtTim=" + crtTim
				+ ", modUsr=" + modUsr + ", modTim=" + modTim + "]";
	}
    
    
}