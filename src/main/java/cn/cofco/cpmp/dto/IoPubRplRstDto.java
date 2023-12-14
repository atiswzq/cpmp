package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/6/18.
 * for [发布废标结果DTO] in cpmp
 */
public class IoPubRplRstDto {

    private Long projId;

    private String rplRstInf;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getRplRstInf() {
        return rplRstInf;
    }

    public void setRplRstInf(String rplRstInf) {
        this.rplRstInf = rplRstInf;
    }

    @Override
    public String toString() {
        return "IoPubRplRstDto{" +
                "projId=" + projId +
                ", rplRstInf='" + rplRstInf + '\'' +
                '}';
    }
}
