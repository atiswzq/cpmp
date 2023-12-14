package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SysParm extends SysParmKey {
    private String parmNam;

    private String valTyp;

    private String parmVal;

    private String parmFrom;

    private String parmTo;

    private String parmMemo;

    private String effFlg;

    private String mntUsr;

    private Timestamp mntTim;

    public String getParmNam() {
        return parmNam;
    }

    public void setParmNam(String parmNam) {
        this.parmNam = parmNam;
    }

    public String getValTyp() {
        return valTyp;
    }

    public void setValTyp(String valTyp) {
        this.valTyp = valTyp;
    }

    public String getParmVal() {
        return parmVal;
    }

    public void setParmVal(String parmVal) {
        this.parmVal = parmVal;
    }

    public String getParmFrom() {
        return parmFrom;
    }

    public void setParmFrom(String parmFrom) {
        this.parmFrom = parmFrom;
    }

    public String getParmTo() {
        return parmTo;
    }

    public void setParmTo(String parmTo) {
        this.parmTo = parmTo;
    }

    public String getParmMemo() {
        return parmMemo;
    }

    public void setParmMemo(String parmMemo) {
        this.parmMemo = parmMemo;
    }

    public String getEffFlg() {
        return effFlg;
    }

    public void setEffFlg(String effFlg) {
        this.effFlg = effFlg;
    }

    public String getMntUsr() {
        return mntUsr;
    }

    public void setMntUsr(String mntUsr) {
        this.mntUsr = mntUsr;
    }

    public Timestamp getMntTim() {
        return mntTim;
    }

    public void setMntTim(Timestamp mntTim) {
        this.mntTim = mntTim;
    }

    @Override
    public String toString() {
        return "SysParm{" +
                "parmTyp='" + parmTyp + '\'' +
                ", parmCod='" + parmCod + '\'' +
                ", valTyp='" + valTyp + '\'' +
                "parmNam='" + parmNam + '\'' +
                ", parmVal='" + parmVal + '\'' +
                ", parmFrom='" + parmFrom + '\'' +
                ", parmTo='" + parmTo + '\'' +
                ", parmMemo='" + parmMemo + '\'' +
                ", effFlg='" + effFlg + '\'' +
                ", mntUsr='" + mntUsr + '\'' +
                ", mntTim=" + mntTim +
                '}';
    }
}