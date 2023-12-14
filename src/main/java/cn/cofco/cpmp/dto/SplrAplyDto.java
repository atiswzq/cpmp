package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.Splr;

/**
 * Created by wzq on 2017/9/23.
 * for [供应商开发申请明细] in cpmp
 */
public class SplrAplyDto {
    /*供应商信息*/
    private Splr splr;

    /*开发申请回调信息*/
    private String adtMsg;

    public Splr getSplr() {
        return splr;
    }

    public void setSplr(Splr splr) {
        this.splr = splr;
    }

    public String getAdtMsg() {
        return adtMsg;
    }

    public void setAdtMsg(String adtMsg) {
        this.adtMsg = adtMsg;
    }

    @Override
    public String toString() {
        return "SplrAplyDto{" +
                "splr=" + splr +
                ", adtMsg='" + adtMsg + '\'' +
                '}';
    }
}
