package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrBklt {
    private Long aplyId;

    private Long splrId;

    private String aplyCtt;

    private Long aplyUsr;

    private Timestamp aplyTim;

    private String aplySts;

    private String delFlg;

    public Long getAplyId() {
        return aplyId;
    }

    public void setAplyId(Long aplyId) {
        this.aplyId = aplyId;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getAplyCtt() {
        return aplyCtt;
    }

    public void setAplyCtt(String aplyCtt) {
        this.aplyCtt = aplyCtt;
    }

    public Long getAplyUsr() {
        return aplyUsr;
    }

    public void setAplyUsr(Long aplyUsr) {
        this.aplyUsr = aplyUsr;
    }

    public Timestamp getAplyTim() {
        return aplyTim;
    }

    public void setAplyTim(Timestamp aplyTim) {
        this.aplyTim = aplyTim;
    }

    public String getAplySts() {
        return aplySts;
    }

    public void setAplySts(String aplySts) {
        this.aplySts = aplySts;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }
}