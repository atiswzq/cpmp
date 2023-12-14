package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrRcmdOnsf {
    private Long rcmdOnsfId;

    private Long splrId;

    private String prodNum;

    private String prodNam;

    private String prodModel;

    private String prodBrand;

    private String prodPrik;

    private String prodItdc;

    private Long upldUsr;

    private Timestamp upldTim;

    private Long modUsr;

    private Timestamp modTim;

    private String delFlg;

    public Long getRcmdOnsfId() {
        return rcmdOnsfId;
    }

    public void setRcmdOnsfId(Long rcmdOnsfId) {
        this.rcmdOnsfId = rcmdOnsfId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getProdNum() {
        return prodNum;
    }

    public void setProdNum(String prodNum) {
        this.prodNum = prodNum;
    }

    public String getProdNam() {
        return prodNam;
    }

    public void setProdNam(String prodNam) {
        this.prodNam = prodNam;
    }

    public String getProdModel() {
        return prodModel;
    }

    public void setProdModel(String prodModel) {
        this.prodModel = prodModel;
    }

    public String getProdBrand() {
        return prodBrand;
    }

    public void setProdBrand(String prodBrand) {
        this.prodBrand = prodBrand;
    }

    public String getProdPrik() {
        return prodPrik;
    }

    public void setProdPrik(String prodPrik) {
        this.prodPrik = prodPrik;
    }

    public String getProdItdc() {
        return prodItdc;
    }

    public void setProdItdc(String prodItdc) {
        this.prodItdc = prodItdc;
    }

    public Long getUpldUsr() {
        return upldUsr;
    }

    public void setUpldUsr(Long upldUsr) {
        this.upldUsr = upldUsr;
    }

    public Timestamp getUpldTim() {
        return upldTim;
    }

    public void setUpldTim(Timestamp upldTim) {
        this.upldTim = upldTim;
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

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

	@Override
	public String toString() {
		return "SplrRcmdOnsf [rcmdOnsfId=" + rcmdOnsfId + ", splrId=" + splrId
				+ ", prodNum=" + prodNum + ", prodNam=" + prodNam
				+ ", prodModel=" + prodModel + ", prodBrand=" + prodBrand
				+ ", prodPrik=" + prodPrik + ", prodItdc=" + prodItdc
				+ ", upldUsr=" + upldUsr + ", upldTim=" + upldTim + ", modUsr="
				+ modUsr + ", modTim=" + modTim + ", delFlg=" + delFlg + "]";
	}
    
}