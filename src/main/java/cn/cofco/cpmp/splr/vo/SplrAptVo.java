package cn.cofco.cpmp.splr.vo;

import java.sql.Timestamp;

public class SplrAptVo {
	private Long aptId;

    private Long splrId;

    private Long aptDefId;

    private String aptNam;

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

	public Long getAptDefId() {
		return aptDefId;
	}

	public void setAptDefId(Long aptDefId) {
		this.aptDefId = aptDefId;
	}

	public String getAptNam() {
		return aptNam;
	}

	public void setAptNam(String aptNam) {
		this.aptNam = aptNam;
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
