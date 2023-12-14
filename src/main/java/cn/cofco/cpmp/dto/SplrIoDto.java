package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.Splr;
/**
 * Created by wzq on 2017/9/23.
 * 供应商开发申请失败回调信息
 */
public class SplrIoDto {
    /*供应商基本信息*/
    private Long splrId;

    private String fullNam;

    private String shortNam;

    private String splrLevl;

    private String splrSts;
    /*
    * 开发申请失败回调信息
    * */
    private String adtMsg;

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getFullNam() {
        return fullNam;
    }

    public void setFullNam(String fullNam) {
        this.fullNam = fullNam;
    }

    public String getShortNam() {
        return shortNam;
    }

    public void setShortNam(String shortNam) {
        this.shortNam = shortNam;
    }

    public String getSplrLevl() {
        return splrLevl;
    }

    public void setSplrLevl(String splrLevl) {
        this.splrLevl = splrLevl;
    }

    public String getSplrSts() {
        return splrSts;
    }

    public void setSplrSts(String splrSts) {
        this.splrSts = splrSts;
    }

    public String getAdtMsg() {
        return adtMsg;
    }

    public void setAdtMsg(String adtMsg) {
        this.adtMsg = adtMsg;
    }

    @Override
    public String toString() {
        return "SplrIoDto{" +
                "splrId=" + splrId +
                ", fullNam='" + fullNam + '\'' +
                ", shortNam='" + shortNam + '\'' +
                ", splrLevl='" + splrLevl + '\'' +
                ", splrSts='" + splrSts + '\'' +
                ", adtMsg='" + adtMsg + '\'' +
                '}';
    }
}
