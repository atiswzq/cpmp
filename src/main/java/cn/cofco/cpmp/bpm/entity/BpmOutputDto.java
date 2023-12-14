package cn.cofco.cpmp.bpm.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Xujy on 2017/8/13.
 * for [文件用途] in cpmp
 */
@XmlRootElement(name = "data")
@XmlType(propOrder = { "msgCod", "msgInf"})
public class BpmOutputDto {

    private String msgCod;
    private String msgInf;

    @XmlElement(name = "msgCod")
    public String getMsgCod() {
        return msgCod;
    }

    public void setMsgCod(String msgCod) {
        this.msgCod = msgCod;
    }

    @XmlElement(name = "msgInf")
    public String getMsgInf() {
        return msgInf;
    }

    public void setMsgInf(String msgInf) {
        this.msgInf = msgInf;
    }

    @Override
    public String toString() {
        return "BpmOutputDto{" +
                "msgCod='" + msgCod + '\'' +
                ", msgInf='" + msgInf + '\'' +
                '}';
    }
}
