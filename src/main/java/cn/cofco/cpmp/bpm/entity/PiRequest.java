package cn.cofco.cpmp.bpm.entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Xujy on 2017/8/13.
 * for [文件用途] in cpmp
 */
@XmlRootElement(name = "I_REQUEST")
@XmlType(propOrder = {"baseInfo", "message"})
public class PiRequest {

    private BaseInfo baseInfo;
    private String message;

    @XmlElement(name = "BASEINFO")
    public BaseInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    @XmlElement(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PiRequest{" +
                "baseInfo=" + baseInfo +
                ", message='" + message + '\'' +
                '}';
    }
}
