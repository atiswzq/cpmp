
package cn.cofco.cpmp.wsclient.splr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getPartnerResponse complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="getPartnerResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://ibm.com/service/partner/}mdmPARTNER" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPartnerResponse", propOrder = {
        "_return"
})
public class GetPartnerResponse {

    @XmlElement(name = "return")
    protected MdmPARTNER _return;

    /**
     * 获取return属性的值。
     *
     * @return
     *     possible object is
     *     {@link MdmPARTNER }
     *
     */
    public MdmPARTNER getReturn() {
        return _return;
    }

    /**
     * 设置return属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link MdmPARTNER }
     *
     */
    public void setReturn(MdmPARTNER value) {
        this._return = value;
    }

    @Override
    public String toString() {
        return "GetPartnerResponse{" +
                "_return=" + _return +
                '}';
    }
}
