package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrMat {
    private Long splrMatId;

    private Long splrId;

    private Long matId;

    private String delFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private Long modUsr;

    private Timestamp modTim;

    public Long getSplrMatId() {
        return splrMatId;
    }

    public void setSplrMatId(Long splrMatId) {
        this.splrMatId = splrMatId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getCrtUsr() {
        return crtUsr;
    }

    public void setCrtUsr(String crtUsr) {
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
}