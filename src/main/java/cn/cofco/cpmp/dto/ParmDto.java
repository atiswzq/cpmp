package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.utils.DateUtils;

import java.sql.Timestamp;

/**
 * Created by Xujy on 2017/5/4.
 * for [文件用途] in cpmp
 */
public class ParmDto {


    private String parmTyp = "010";
    private String parmCod = "c22";
    private Timestamp mntTim = null;
    private String mntUsr = "XJY TEST";

    public String getParmTyp() {
        return parmTyp;
    }

    public void setParmTyp(String parmTyp) {
        this.parmTyp = parmTyp;
    }

    public String getParmCod() {
        return parmCod;
    }

    public void setParmCod(String parmCod) {
        this.parmCod = parmCod;
    }


    public Timestamp getMntTim() {
        return mntTim;
    }

    public void setMntTim(Timestamp mntTim) {
        this.mntTim = mntTim;
    }



    public String getMntUsr() {
        return mntUsr;
    }

    public void setMntUsr(String mntUsr) {
        this.mntUsr = mntUsr;
    }

    @Override
    public String toString() {
        return "ParmDto{" +
                "parmTyp='" + parmTyp + '\'' +
                ", parmCod='" + parmCod + '\'' +
                ", mntTim=" + mntTim +
                ", mntUsr='" + mntUsr + '\'' +
                '}';
    }
}
