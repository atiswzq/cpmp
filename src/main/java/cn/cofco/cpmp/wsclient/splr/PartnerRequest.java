
package cn.cofco.cpmp.wsclient.splr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>partnerRequest complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="partnerRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ACCOUNT_GROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BP_CLASS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BUSINESS_LICENSE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="COMPANY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CREDIT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FULL_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HAS_CREDIT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ORGANIZATION_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PARTNER_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TAX_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "partnerRequest", propOrder = {
        "accountgroup",
        "bpclass",
        "businesslicense",
        "company",
        "creditcode",
        "fullname",
        "hascreditcode",
        "organizationcode",
        "partnernumber",
        "taxnumber"
})
public class PartnerRequest {

    @XmlElement(name = "ACCOUNT_GROUP")
    protected String accountgroup;
    @XmlElement(name = "BP_CLASS")
    protected String bpclass;
    @XmlElement(name = "BUSINESS_LICENSE")
    protected String businesslicense;
    @XmlElement(name = "COMPANY")
    protected String company;
    @XmlElement(name = "CREDIT_CODE")
    protected String creditcode;
    @XmlElement(name = "FULL_NAME")
    protected String fullname;
    @XmlElement(name = "HAS_CREDIT_CODE")
    protected String hascreditcode;
    @XmlElement(name = "ORGANIZATION_CODE")
    protected String organizationcode;
    @XmlElement(name = "PARTNER_NUMBER")
    protected String partnernumber;
    @XmlElement(name = "TAX_NUMBER")
    protected String taxnumber;

    /**
     * 获取accountgroup属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getACCOUNTGROUP() {
        return accountgroup;
    }

    /**
     * 设置accountgroup属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setACCOUNTGROUP(String value) {
        this.accountgroup = value;
    }

    /**
     * 获取bpclass属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBPCLASS() {
        return bpclass;
    }

    /**
     * 设置bpclass属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBPCLASS(String value) {
        this.bpclass = value;
    }

    /**
     * 获取businesslicense属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBUSINESSLICENSE() {
        return businesslicense;
    }

    /**
     * 设置businesslicense属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBUSINESSLICENSE(String value) {
        this.businesslicense = value;
    }

    /**
     * 获取company属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOMPANY() {
        return company;
    }

    /**
     * 设置company属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOMPANY(String value) {
        this.company = value;
    }

    /**
     * 获取creditcode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCREDITCODE() {
        return creditcode;
    }

    /**
     * 设置creditcode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCREDITCODE(String value) {
        this.creditcode = value;
    }

    /**
     * 获取fullname属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFULLNAME() {
        return fullname;
    }

    /**
     * 设置fullname属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFULLNAME(String value) {
        this.fullname = value;
    }

    /**
     * 获取hascreditcode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getHASCREDITCODE() {
        return hascreditcode;
    }

    /**
     * 设置hascreditcode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setHASCREDITCODE(String value) {
        this.hascreditcode = value;
    }

    /**
     * 获取organizationcode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getORGANIZATIONCODE() {
        return organizationcode;
    }

    /**
     * 设置organizationcode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setORGANIZATIONCODE(String value) {
        this.organizationcode = value;
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

    /**
     * 获取taxnumber属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTAXNUMBER() {
        return taxnumber;
    }

    /**
     * 设置taxnumber属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTAXNUMBER(String value) {
        this.taxnumber = value;
    }

}
