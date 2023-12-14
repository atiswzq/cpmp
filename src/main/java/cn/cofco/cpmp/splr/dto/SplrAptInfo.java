package cn.cofco.cpmp.splr.dto;

import java.sql.Timestamp;

public class SplrAptInfo {

	private Long aptId;
	private Long splrId;
	private Long splrAptId;
	private String require;
	private String aptNam;
	private String aptVal;
	private Timestamp aptInTim;
	private String crfcOgnz;
	
	public Long getAptId() {
		return aptId;
	}
	public void setAptId(Long aptId) {
		this.aptId = aptId;
	}
	public Long getSplrId() {
		return splrId;
	}
	public void setSplrId(Long splrId) {
		this.splrId = splrId;
	}
	public Long getSplrAptId() {
		return splrAptId;
	}
	public void setSplrAptId(Long splrAptId) {
		this.splrAptId = splrAptId;
	}
	public String getRequire() {
		return require;
	}
	public void setRequire(String require) {
		this.require = require;
	}
	public String getAptNam() {
		return aptNam;
	}
	public void setAptNam(String aptNam) {
		this.aptNam = aptNam;
	}
	public String getAptVal() {
		return aptVal;
	}
	public void setAptVal(String aptVal) {
		this.aptVal = aptVal;
	}
	public Timestamp getAptInTim() {
		return aptInTim;
	}
	public void setAptInTim(Timestamp aptInTim) {
		this.aptInTim = aptInTim;
	}

	public String getCrfcOgnz() {
		return crfcOgnz;
	}

	public void setCrfcOgnz(String crfcOgnz) {
		this.crfcOgnz = crfcOgnz;
	}
}
