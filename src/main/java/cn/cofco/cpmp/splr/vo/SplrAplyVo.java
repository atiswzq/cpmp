package cn.cofco.cpmp.splr.vo;

import cn.cofco.cpmp.bpm.entity.BpmSplrMat;

import java.util.List;

/**
 * Created by xsmiler on 2017/7/20.
 */
public class SplrAplyVo {

    Long splrId;
    String prodList;
    String remark;
    List<Long> chkIds;
    List<BpmSplrMat> bpmSplrMats;

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getProdList() {
        return prodList;
    }

    public void setProdList(String prodList) {
        this.prodList = prodList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Long> getChkIds() {
        return chkIds;
    }

    public void setChkIds(List<Long> chkIds) {
        this.chkIds = chkIds;
    }

    public List<BpmSplrMat> getBpmSplrMats() {
        return bpmSplrMats;
    }

    public void setBpmSplrMats(List<BpmSplrMat> bpmSplrMats) {
        this.bpmSplrMats = bpmSplrMats;
    }
}
