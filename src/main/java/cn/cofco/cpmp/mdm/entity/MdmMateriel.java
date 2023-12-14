package cn.cofco.cpmp.mdm.entity;

import java.util.List;

/**
 * 物料基本信息
 * 
 * @author xsmiler
 *
 */
public class MdmMateriel {

	private String matcode;
	private String matind;
	private String mattype;
	private String matgroup;
	private String unit;
	private String brgew1;
	private String ntgew1;
	private String gewei1;
	private String volum1;
	private String voleh1;
	private String eantp1;
	private String ean111;
	private String prodgroup;
	private String processid;
	private String procomp;
	private String applicant;
	private String createtime;
	private String lvoma;
	private String bstme;
	private String vabme;
	private List<MaterielDesc> materiel_desc;
	private List<CoInfo> co_info;
	private List<SystemInfo> system_info;
	private List<MdmMatUnit> mat_unit;
	
	public String getMatcode() {
		return matcode;
	}
	public void setMatcode(String matcode) {
		this.matcode = matcode;
	}
	public String getMatind() {
		return matind;
	}
	public void setMatind(String matind) {
		this.matind = matind;
	}
	public String getMattype() {
		return mattype;
	}
	public void setMattype(String mattype) {
		this.mattype = mattype;
	}
	public String getMatgroup() {
		return matgroup;
	}
	public void setMatgroup(String matgroup) {
		this.matgroup = matgroup;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBrgew1() {
		return brgew1;
	}
	public void setBrgew1(String brgew1) {
		this.brgew1 = brgew1;
	}
	public String getNtgew1() {
		return ntgew1;
	}
	public void setNtgew1(String ntgew1) {
		this.ntgew1 = ntgew1;
	}
	public String getGewei1() {
		return gewei1;
	}
	public void setGewei1(String gewei1) {
		this.gewei1 = gewei1;
	}
	public String getVolum1() {
		return volum1;
	}
	public void setVolum1(String volum1) {
		this.volum1 = volum1;
	}
	public String getVoleh1() {
		return voleh1;
	}
	public void setVoleh1(String voleh1) {
		this.voleh1 = voleh1;
	}
	public String getEantp1() {
		return eantp1;
	}
	public void setEantp1(String eantp1) {
		this.eantp1 = eantp1;
	}
	public String getEan111() {
		return ean111;
	}
	public void setEan111(String ean111) {
		this.ean111 = ean111;
	}
	public String getProdgroup() {
		return prodgroup;
	}
	public void setProdgroup(String prodgroup) {
		this.prodgroup = prodgroup;
	}
	public String getProcessid() {
		return processid;
	}
	public void setProcessid(String processid) {
		this.processid = processid;
	}
	public String getProcomp() {
		return procomp;
	}
	public void setProcomp(String procomp) {
		this.procomp = procomp;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLvoma() {
		return lvoma;
	}
	public void setLvoma(String lvoma) {
		this.lvoma = lvoma;
	}
	public List<MaterielDesc> getMateriel_desc() {
		return materiel_desc;
	}
	public void setMateriel_desc(List<MaterielDesc> materiel_desc) {
		this.materiel_desc = materiel_desc;
	}
	public List<CoInfo> getCo_info() {
		return co_info;
	}
	public void setCo_info(List<CoInfo> co_info) {
		this.co_info = co_info;
	}
	public List<SystemInfo> getSystem_info() {
		return system_info;
	}
	public void setSystem_info(List<SystemInfo> system_info) {
		this.system_info = system_info;
	}
	public List<MdmMatUnit> getMat_unit() {
		return mat_unit;
	}
	public void setMat_unit(List<MdmMatUnit> mat_unit) {
		this.mat_unit = mat_unit;
	}

	public String getBstme() {
		return bstme;
	}

	public void setBstme(String bstme) {
		this.bstme = bstme;
	}

	public String getVabme() {
		return vabme;
	}

	public void setVabme(String vabme) {
		this.vabme = vabme;
	}

	@Override
	public String toString() {
		return "MdmMateriel [matcode=" + matcode + ", matind=" + matind
				+ ", mattype=" + mattype + ", matgroup=" + matgroup + ", unit="
				+ unit + ", brgew1=" + brgew1 + ", ntgew1=" + ntgew1
				+ ", gewei1=" + gewei1 + ", volum1=" + volum1 + ", voleh1="
				+ voleh1 + ", eantp1=" + eantp1 + ", ean111=" + ean111
				+ ", prodgroup=" + prodgroup + ", processid=" + processid
				+ ", procomp=" + procomp + ", applicant=" + applicant
				+ ", createtime=" + createtime + ", lvoma=" + lvoma
				+ ", materiel_desc=" + materiel_desc + ", co_info=" + co_info
				+ ", system_info=" + system_info + ", mat_unit=" + mat_unit
				+ ", bstme=" + bstme + ", vabme=" + vabme
				+ "]";
	}
	
	
}
