
package cn.cofco.cpmp.wsclient.bpm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceResponse", namespace = "http://services.v3x.seeyon.com/xsd", propOrder = {
    "errorMessage",
    "errorNumber",
    "result"
})
public class ServiceResponse {

    @XmlElementRef(name = "errorMessage", namespace = "http://services.v3x.seeyon.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> errorMessage;
    protected Long errorNumber;
    protected Long result;

    /**
     * 获取errorMessage信息
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * 设置errorMessage信息
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setErrorMessage(JAXBElement<String> value) {
        this.errorMessage = value;
    }

    /**
     * 获取errorNumber
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getErrorNumber() {
        return errorNumber;
    }

    /**
     * 设置errorNumber
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setErrorNumber(Long value) {
        this.errorNumber = value;
    }

    /**
     * 获取result
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getResult() {
        return result;
    }

    /**
     * 设置result
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setResult(Long value) {
        this.result = value;
    }

}
