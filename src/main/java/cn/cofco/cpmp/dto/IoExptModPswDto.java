package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/30.
 * for [修改密码DTO] in cpmp
 */
public class IoExptModPswDto {

    /**
     * 原密码
     */
    private String psw;

    /**
     * 新密码
     */
    private String pswNew;


    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getPswNew() {
        return pswNew;
    }

    public void setPswNew(String pswNew) {
        this.pswNew = pswNew;
    }

    @Override
    public String toString() {
        return "IoExptModPswDto{" +
                "psw='" + psw + '\'' +
                ", pswNew='" + pswNew + '\'' +
                '}';
    }
}
