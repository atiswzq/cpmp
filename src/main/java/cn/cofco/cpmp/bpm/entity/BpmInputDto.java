package cn.cofco.cpmp.bpm.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Xujy on 2017/8/13.
 * for [文件用途] in cpmp
 */
//@XmlType(propOrder = {"bpmSeqNo"})
@XmlRootElement(name = "data")
public class BpmInputDto {

    private String bpmSeqNo;
    private String tempNum;
    private String sucFlg;
    private String memo;
    private String uid;
    private String signature;

    @XmlElement(name = "bpmSeqNo")
    public String getBpmSeqNo() {
        return bpmSeqNo;
    }

    public void setBpmSeqNo(String bpmSeqNo) {
        this.bpmSeqNo = bpmSeqNo;
    }

    @XmlElement(name = "tempNum")
    public String getTempNum() {
        return tempNum;
    }

    public void setTempNum(String tempNum) {
        this.tempNum = tempNum;
    }

    @XmlElement(name = "sucFlg")
    public String getSucFlg() {
        return sucFlg;
    }

    public void setSucFlg(String sucFlg) {
        this.sucFlg = sucFlg;
    }

    @XmlElement(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @XmlElement(name = "uid")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @XmlElement(name = "signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "BpmInputDto{" +
                "bpmSeqNo='" + bpmSeqNo + '\'' +
                ", tempNum='" + tempNum + '\'' +
                ", sucFlg='" + sucFlg + '\'' +
                ", memo='" + memo + '\'' +
                ", uid='" + uid + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
