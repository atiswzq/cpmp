
package cn.cofco.cpmp.wsclient.saptest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DT_SRM2ECC_PO_RESP complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="DT_SRM2ECC_PO_RESP"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EV_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EV_MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DT_SRM2ECC_PO_RESP", propOrder = {
    "evtype",
    "evmessage"
})
public class DTSRM2ECCPORESP {

    @XmlElement(name = "EV_TYPE")
    protected String evtype;
    @XmlElement(name = "EV_MESSAGE")
    protected String evmessage;

    /**
     * ��ȡevtype���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEVTYPE() {
        return evtype;
    }

    /**
     * ����evtype���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEVTYPE(String value) {
        this.evtype = value;
    }

    /**
     * ��ȡevmessage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEVMESSAGE() {
        return evmessage;
    }

    /**
     * ����evmessage���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEVMESSAGE(String value) {
        this.evmessage = value;
    }


    @Override
    public String toString() {
        return "DTSRM2ECCPORESP{" +
                "evtype='" + evtype + '\'' +
                ", evmessage='" + evmessage + '\'' +
                '}';
    }
}
