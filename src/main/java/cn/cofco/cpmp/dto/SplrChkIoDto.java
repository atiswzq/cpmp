package cn.cofco.cpmp.dto;

/**
 * Created by wzq on 2018/01/31
 */
public class SplrChkIoDto {

    /*考察表名字*/
    private String chkNam;

    /*考察表路径*/
    private String chkUrl;

    public String getChkNam() {
        return chkNam;
    }

    public void setChkNam(String chkNam) {
        this.chkNam = chkNam;
    }

    public String getChkUrl() {
        return chkUrl;
    }

    public void setChkUrl(String chkUrl) {
        this.chkUrl = chkUrl;
    }

    @Override
    public String toString() {
        return "SplrChkIoDto{" +
                "chkNam='" + chkNam + '\'' +
                ", chkUrl='" + chkUrl + '\'' +
                '}';
    }
}
