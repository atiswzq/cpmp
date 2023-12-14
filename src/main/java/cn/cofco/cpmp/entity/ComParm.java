package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class ComParm extends ComParmKey {
    private String valTyp;

    private String parmVal;

    private String parmFrom;

    private String parmTo;

    private String parmMemo;

    private String visiFlg;

    private String edtFlg;

    private String effFlg;

    private String mntUsr;

    private Timestamp mntTim;

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

    public String getVisiFlg() {
        return visiFlg;
    }

    public void setVisiFlg(String visiFlg) {
        this.visiFlg = visiFlg;
    }

    public String getEdtFlg() {
        return edtFlg;
    }

    public void setEdtFlg(String edtFlg) {
        this.edtFlg = edtFlg;
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
        return "ComParm{" +
                "parmTyp='" + parmTyp + '\'' +
                ", parmCod='" + parmCod + '\'' +
                ", valTyp='" + valTyp + '\'' +
                ", parmVal='" + parmVal + '\'' +
                ", parmFrom='" + parmFrom + '\'' +
                ", parmTo='" + parmTo + '\'' +
                ", parmMemo='" + parmMemo + '\'' +
                ", visiFlg='" + visiFlg + '\'' +
                ", edtFlg='" + edtFlg + '\'' +
                ", effFlg='" + effFlg + '\'' +
                ", mntUsr='" + mntUsr + '\'' +
                ", mntTim=" + mntTim +
                '}';
    }
}