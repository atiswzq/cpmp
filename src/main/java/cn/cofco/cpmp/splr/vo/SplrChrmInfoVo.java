package cn.cofco.cpmp.splr.vo;

import java.util.List;
import java.util.Map;

public class SplrChrmInfoVo {
	
	private String fullNam;
	
    private String hasCreditCode;

    private String taxCod;

    private String dbusiLice;

    private String orgCod;

    private String crdtCod;
    
    private String lglUser;

    private String lglIdntCad;
    
    private String regCptl;
    
    private String rgstAddr;
    
    private List<Map<String, String>> iconItems;

	public String getFullNam() {
		return fullNam;
	}

	public void setFullNam(String fullNam) {
		this.fullNam = fullNam;
	}

	public String getHasCreditCode() {
		return hasCreditCode;
	}

	public void setHasCreditCode(String hasCreditCode) {
		this.hasCreditCode = hasCreditCode;
	}

	public String getTaxCod() {
		return taxCod;
	}

	public void setTaxCod(String taxCod) {
		this.taxCod = taxCod;
	}

	public String getDbusiLice() {
		return dbusiLice;
	}

	public void setDbusiLice(String dbusiLice) {
		this.dbusiLice = dbusiLice;
	}

	public String getOrgCod() {
		return orgCod;
	}

	public void setOrgCod(String orgCod) {
		this.orgCod = orgCod;
	}

	public String getCrdtCod() {
		return crdtCod;
	}

	public void setCrdtCod(String crdtCod) {
		this.crdtCod = crdtCod;
	}

	public String getLglUser() {
		return lglUser;
	}

	public void setLglUser(String lglUser) {
		this.lglUser = lglUser;
	}

	public String getLglIdntCad() {
		return lglIdntCad;
	}

	public void setLglIdntCad(String lglIdntCad) {
		this.lglIdntCad = lglIdntCad;
	}

	public String getRegCptl() {
		return regCptl;
	}

	public void setRegCptl(String regCptl) {
		this.regCptl = regCptl;
	}

	public String getRgstAddr() {
		return rgstAddr;
	}

	public void setRgstAddr(String rgstAddr) {
		this.rgstAddr = rgstAddr;
	}

	public List<Map<String, String>> getIconItems() {
		return iconItems;
	}

	public void setIconItems(List<Map<String, String>> iconItems) {
		this.iconItems = iconItems;
	}
    
    
}
