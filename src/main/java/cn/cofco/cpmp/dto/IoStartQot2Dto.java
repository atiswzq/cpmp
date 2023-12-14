package cn.cofco.cpmp.dto;

import java.sql.Timestamp;

/**
 * Created by Xujy on 2017/5/30.
 * for [发起二次竞标TDO] in cpmp
 */
public class IoStartQot2Dto {

    // 项目ID
    private Long projId;

    // 二次报价截止时间
    private String qot2EndTimStr; // 前端上送
    private Timestamp qot2EndTim; // 后台转换

    // 二次竞价报价次数类型：0-一次报价；1-多次报价（最多三次）；2-实时报价
    private String qot2CntTyp;

    // 邀请二次竞标供应商IDs
    private String splrIds;

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getQot2EndTimStr() {
        return qot2EndTimStr;
    }

    public void setQot2EndTimStr(String qot2EndTimStr) {
        this.qot2EndTimStr = qot2EndTimStr;
    }

    public Timestamp getQot2EndTim() {
        return qot2EndTim;
    }

    public void setQot2EndTim(Timestamp qot2EndTim) {
        this.qot2EndTim = qot2EndTim;
    }

    public String getQot2CntTyp() {
        return qot2CntTyp;
    }

    public void setQot2CntTyp(String qot2CntTyp) {
        this.qot2CntTyp = qot2CntTyp;
    }

    public String getSplrIds() {
        return splrIds;
    }

    public void setSplrIds(String splrIds) {
        this.splrIds = splrIds;
    }

    @Override
    public String toString() {
        return "IoStartQot2Dto{" +
                "projId=" + projId +
                ", qot2EndTimStr='" + qot2EndTimStr + '\'' +
                ", qot2EndTim=" + qot2EndTim +
                ", qot2CntTyp='" + qot2CntTyp + '\'' +
                ", splrIds='" + splrIds + '\'' +
                '}';
    }
}
