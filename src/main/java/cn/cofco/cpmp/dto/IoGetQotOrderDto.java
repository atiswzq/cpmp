package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/29.
 * for [查询实时竞价物料报价价格排名DTO] in cpmp
 */
public class IoGetQotOrderDto {

    /**
     * 项目ID
     */
    private Long projId;

    /**
     * 报价
     */
    private String qotTyp;

    /**
     * 物料编码
     */
    private String matCod;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getQotTyp() {
        return qotTyp;
    }

    public void setQotTyp(String qotTyp) {
        this.qotTyp = qotTyp;
    }

    public String getMatCod() {
        return matCod;
    }

    public void setMatCod(String matCod) {
        this.matCod = matCod;
    }

    @Override
    public String toString() {
        return "IoGetQotOrderDto{" +
                "projId=" + projId +
                ", qotTyp='" + qotTyp + '\'' +
                ", matCod='" + matCod + '\'' +
                '}';
    }
}
