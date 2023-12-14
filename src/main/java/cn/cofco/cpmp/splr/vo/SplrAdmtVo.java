package cn.cofco.cpmp.splr.vo;

import cn.cofco.cpmp.bpm.entity.BpmSplrMat;
import cn.cofco.cpmp.dto.SplrChkIoDto;

import java.util.List;

/**
 * Created by xsmiler on 2017/10/29.
 */
public class SplrAdmtVo {

    Long splrId;
    String prodList;
    String remark;
    List<SplrChkIoDto> splrChkIoDtos;
    List<String> matIds;

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

    public List<SplrChkIoDto> getSplrChkIoDtos() {
        return splrChkIoDtos;
    }

    public void setSplrChkIoDtos(List<SplrChkIoDto> splrChkIoDtos) {
        this.splrChkIoDtos = splrChkIoDtos;
    }

    public List<String> getMatIds() {
        return matIds;
    }

    public void setMatIds(List<String> matIds) {
        this.matIds = matIds;
    }
}
