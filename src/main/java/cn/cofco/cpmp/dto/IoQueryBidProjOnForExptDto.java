package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/30.
 * for [查询线上招标项目 - 专家] in cpmp
 */
public class IoQueryBidProjOnForExptDto {

    /**
     * 项目编号 - 可空（模糊匹配）
     */
    private String projNbr;

    /**
     * 项目名称 - 可空（模糊匹配）
     */
    private String projNam;

    public String getProjNbr() {
        return projNbr;
    }

    public void setProjNbr(String projNbr) {
        this.projNbr = projNbr;
    }

    public String getProjNam() {
        return projNam;
    }

    public void setProjNam(String projNam) {
        this.projNam = projNam;
    }

    @Override
    public String toString() {
        return "IoQueryBidProjOnForExptDto{" +
                "projNbr='" + projNbr + '\'' +
                ", projNam='" + projNam + '\'' +
                '}';
    }
}
