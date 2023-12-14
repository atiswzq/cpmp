package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/7/19.
 * for [发送评标密钥短信DTO] in cpmp
 */
public class GrdKeySmsDto {

    private String name;

    private String pcode;

    private String vcode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return "GrdKeySmsDto{" +
                "name='" + name + '\'' +
                ", pcode='" + pcode + '\'' +
                ", vcode='" + vcode + '\'' +
                '}';
    }
}
