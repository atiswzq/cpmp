package cn.cofco.cpmp.bpm.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Xujy on 2017/8/13.
 * for [BPM回调PI包装接口] in cpmp
 */
@XmlRootElement(name = "BASEINFO")
public class BaseInfo {

    private String msgId;
    private String pmsgId;
    private String sendTime;
    private String systemS;
    private String routename;
    private String systemT;
    private String retry;
    private String remark1;
    private String remark2;
    private String remark3;


    @XmlElement(name = "MSGID")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @XmlElement(name = "PMSGID")
    public String getPmsgId() {
        return pmsgId;
    }

    public void setPmsgId(String pmsgId) {
        this.pmsgId = pmsgId;
    }

    @XmlElement(name = "SENDTIME")
    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @XmlElement(name = "S_SYSTEM")
    public String getSystemS() {
        return systemS;
    }

    public void setSystemS(String systemS) {
        this.systemS = systemS;
    }

    @XmlElement(name = "ROUTENAME")
    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    @XmlElement(name = "T_SYSTEM")
    public String getSystemT() {
        return systemT;
    }

    public void setSystemT(String systemT) {
        this.systemT = systemT;
    }

    @XmlElement(name = "RETRY")
    public String getRetry() {
        return retry;
    }

    public void setRetry(String retry) {
        this.retry = retry;
    }

    @XmlElement(name = "REMARK1")
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    @XmlElement(name = "REMARK2")
    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    @XmlElement(name = "REMARK3")
    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "msgId='" + msgId + '\'' +
                ", pmsgId='" + pmsgId + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", systemS='" + systemS + '\'' +
                ", routename='" + routename + '\'' +
                ", systemT='" + systemT + '\'' +
                ", retry='" + retry + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark3='" + remark3 + '\'' +
                '}';
    }
}
