
package cn.cofco.cpmp.wsclient.splr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>partnerResponse complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="partnerResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PARTNER_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PARTNER_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "partnerResponse", propOrder = {
        "flag",
        "message",
        "partnerid",
        "partnernumber"
})
public class PartnerResponse {

    @XmlElement(name = "FLAG")
    protected String flag;
    @XmlElement(name = "MESSAGE")
    protected String message;
    @XmlElement(name = "PARTNER_ID")
    protected String partnerid;
    @XmlElement(name = "PARTNER_NUMBER")
    protected String partnernumber;

    /**
     * 获取flag属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFLAG() {
        return flag;
    }

    /**
     * 设置flag属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFLAG(String value) {
        this.flag = value;
    }

    /**
     * 获取message属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMESSAGE() {
        return message;
    }

    /**
     * 设置message属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMESSAGE(String value) {
        this.message = value;
    }

    /**
     * 获取partnerid属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPARTNERID() {
        return partnerid;
    }

    /**
     * 设置partnerid属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPARTNERID(String value) {
        this.partnerid = value;
    }

    /**
     * 获取partnernumber属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPARTNERNUMBER() {
        return partnernumber;
    }

    /**
     * 设置partnernumber属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPARTNERNUMBER(String value) {
        this.partnernumber = value;
    }

    @Override
    public String toString() {
        return "PartnerResponse{" +
                "flag='" + flag + '\'' +
                ", message='" + message + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", partnernumber='" + partnernumber + '\'' +
                '}';
    }
}
