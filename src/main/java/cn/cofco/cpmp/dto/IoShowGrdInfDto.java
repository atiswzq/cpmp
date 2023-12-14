package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/6/3.
 * for [查看評標信息DTO] in cpmp
 */
public class IoShowGrdInfDto {

    /**
     * 评标类别：0-一次评标；1-二次评标
     */
    private String grdTyp;

    /**
     * 项目ID
     */
    private Long projId;

    public String getGrdTyp() {
        return grdTyp;
    }

    public void setGrdTyp(String grdTyp) {
        this.grdTyp = grdTyp;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    @Override
    public String toString() {
        return "IoShowGrdInfDto{" +
                "grdTyp='" + grdTyp + '\'' +
                ", projId=" + projId +
                '}';
    }
}
