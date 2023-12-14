package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/29.
 * for [获取线上招标项目供应商报价历史DTO] in cpmp
 */
public class IoGetQotHisDto {

    /**
     * 项目ID
     */
    private Long projId;

    /**
     * 报价类别：0-一次竞价报价；1-二次竞价报价
     */
    private String qotTyp;

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

    @Override
    public String toString() {
        return "IoGetQotHisDto{" +
                "projId=" + projId +
                ", qotTyp='" + qotTyp + '\'' +
                '}';
    }
}
