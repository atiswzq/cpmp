package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/30.
 * for [专家登录DTO] in cpmp
 */
public class IoExptLoginDto {

    /**
     * 专家手机号
     */
    private String mobNbr;

    /**
     * 专家密码
     */
    private String exptPsw;


    public String getMobNbr() {
        return mobNbr;
    }

    public void setMobNbr(String mobNbr) {
        this.mobNbr = mobNbr;
    }

    public String getExptPsw() {
        return exptPsw;
    }

    public void setExptPsw(String exptPsw) {
        this.exptPsw = exptPsw;
    }

    @Override
    public String toString() {
        return "IoExptLoginDto{" +
                "mobNbr='" + mobNbr + '\'' +
                ", exptPsw='" + exptPsw + '\'' +
                '}';
    }
}
