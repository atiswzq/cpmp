package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/7/19.
 * for [发送开标密钥短信DTO] in cpmp
 */
public class OpenKeySmsDto {

    private String pcode;

    private String vcode;

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    @Override
    public String toString() {
        return "OpenKeySmsDto{" +
                "pcode='" + pcode + '\'' +
                ", vcode='" + vcode + '\'' +
                '}';
    }
}
