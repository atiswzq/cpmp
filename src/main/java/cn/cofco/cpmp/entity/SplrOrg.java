package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrOrg extends SplrOrgKey {
	
	private Long splrId;

    private Long orgId;
	
    private Long crtId;

    private String crtNam;

    private Timestamp crtTim;

    private Long modId;

    private Timestamp modTim;

    private String modNam;

    private String splrOrgSts;

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    
    public Long getCrtId() {
        return crtId;
    }

    public void setCrtId(Long crtId) {
        this.crtId = crtId;
    }

    public String getCrtNam() {
        return crtNam;
    }

    public void setCrtNam(String crtNam) {
        this.crtNam = crtNam;
    }

    public Timestamp getCrtTim() {
        return crtTim;
    }

    public void setCrtTim(Timestamp crtTim) {
        this.crtTim = crtTim;
    }

    public Long getModId() {
        return modId;
    }

    public void setModId(Long modId) {
        this.modId = modId;
    }

    public Timestamp getModTim() {
        return modTim;
    }

    public void setModTim(Timestamp modTim) {
        this.modTim = modTim;
    }

    public String getModNam() {
        return modNam;
    }

    public void setModNam(String modNam) {
        this.modNam = modNam;
    }

    public String getSplrOrgSts() {
        return splrOrgSts;
    }

    public void setSplrOrgSts(String splrOrgSts) {
        this.splrOrgSts = splrOrgSts;
    }
}