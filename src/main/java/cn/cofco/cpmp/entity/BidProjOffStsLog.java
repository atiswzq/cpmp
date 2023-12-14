package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class BidProjOffStsLog {
    private Long id;

    private Long projId;

    private String projNam;

    private String projSts;

    private Timestamp stsUpdTim;

    private String modUsr;

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

    public String getProjNam() {
        return projNam;
    }

    public void setProjNam(String projNam) {
        this.projNam = projNam;
    }

    public String getProjSts() {
        return projSts;
    }

    public void setProjSts(String projSts) {
        this.projSts = projSts;
    }

    public Timestamp getStsUpdTim() {
        return stsUpdTim;
    }

    public void setStsUpdTim(Timestamp stsUpdTim) {
        this.stsUpdTim = stsUpdTim;
    }

    public String getModUsr() {
        return modUsr;
    }

    public void setModUsr(String modUsr) {
        this.modUsr = modUsr;
    }
}