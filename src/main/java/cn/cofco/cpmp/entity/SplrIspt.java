package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrIspt {
    private Long isptId;

    private String isptCtt;

    private String isptNam;

    private Long upldUsr;

    private Timestamp upldTim;

    private String isptTpy;

    private String dffFlg;

    private Long modUsr;

    private Timestamp modTim;

    public Long getIsptId() {
        return isptId;
    }

    public void setIsptId(Long isptId) {
        this.isptId = isptId;
    }

    public String getIsptCtt() {
        return isptCtt;
    }

    public void setIsptCtt(String isptCtt) {
        this.isptCtt = isptCtt;
    }

    public String getIsptNam() {
        return isptNam;
    }

    public void setIsptNam(String isptNam) {
        this.isptNam = isptNam;
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

    public String getIsptTpy() {
        return isptTpy;
    }

    public void setIsptTpy(String isptTpy) {
        this.isptTpy = isptTpy;
    }

    public String getDffFlg() {
        return dffFlg;
    }

    public void setDffFlg(String dffFlg) {
        this.dffFlg = dffFlg;
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