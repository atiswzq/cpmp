package cn.cofco.cpmp.mdm.entity;

/**
 * 物料单位
 * @author xu
 *
 */
public class MdmMatUnit {
	private String meins1;
	private String umren;
	private String umrez;
	private String brgew;
	private String ntgew;
	private String gewei;
	private String volum;
	private String voleh;
	private String eantp;
	private String ean11;
	
	public String getMeins1() {
		return meins1;
	}
	public String getUmren() {
		return umren;
	}
	public String getUmrez() {
		return umrez;
	}
	public String getBrgew() {
		return brgew;
	}
	public String getNtgew() {
		return ntgew;
	}
	public String getGewei() {
		return gewei;
	}
	public String getVolum() {
		return volum;
	}
	public String getVoleh() {
		return voleh;
	}
	public String getEantp() {
		return eantp;
	}
	public String getEan11() {
		return ean11;
	}
	public void setMeins1(String meins1) {
		this.meins1 = meins1;
	}
	public void setUmren(String umren) {
		this.umren = umren;
	}
	public void setUmrez(String umrez) {
		this.umrez = umrez;
	}
	public void setBrgew(String brgew) {
		this.brgew = brgew;
	}
	public void setNtgew(String ntgew) {
		this.ntgew = ntgew;
	}
	public void setGewei(String gewei) {
		this.gewei = gewei;
	}
	public void setVolum(String volum) {
		this.volum = volum;
	}
	public void setVoleh(String voleh) {
		this.voleh = voleh;
	}
	public void setEantp(String eantp) {
		this.eantp = eantp;
	}
	public void setEan11(String ean11) {
		this.ean11 = ean11;
	}
	@Override
	public String toString() {
		return "MdmMatUnit [meins1=" + meins1 + ", umren=" + umren + ", umrez="
				+ umrez + ", brgew=" + brgew + ", ntgew=" + ntgew + ", gewei="
				+ gewei + ", volum=" + volum + ", voleh=" + voleh + ", eantp="
				+ eantp + ", ean11=" + ean11 + "]";
	}
	
	
}
