
package cn.cofco.cpmp.wsclient.splr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>bankINFO complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="bankINFO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ACCOUNT_HOLDER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BANK_ACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BANK_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BANK_COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BANK_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DEFAULT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SWIFT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bankINFO", propOrder = {
        "accountholder",
        "bankaccount",
        "bankcode",
        "bankcountry",
        "bankname",
        "_default",
        "swiftcode"
})
public class BankINFO {

    @XmlElement(name = "ACCOUNT_HOLDER")
    protected String accountholder;
    @XmlElement(name = "BANK_ACCOUNT")
    protected String bankaccount;
    @XmlElement(name = "BANK_CODE")
    protected String bankcode;
    @XmlElement(name = "BANK_COUNTRY")
    protected String bankcountry;
    @XmlElement(name = "BANK_NAME")
    protected String bankname;
    @XmlElement(name = "DEFAULT")
    protected String _default;
    @XmlElement(name = "SWIFT_CODE")
    protected String swiftcode;

    /**
     * 获取accountholder属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getACCOUNTHOLDER() {
        return accountholder;
    }

    /**
     * 设置accountholder属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setACCOUNTHOLDER(String value) {
        this.accountholder = value;
    }

    /**
     * 获取bankaccount属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBANKACCOUNT() {
        return bankaccount;
    }

    /**
     * 设置bankaccount属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBANKACCOUNT(String value) {
        this.bankaccount = value;
    }

    /**
     * 获取bankcode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBANKCODE() {
        return bankcode;
    }

    /**
     * 设置bankcode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBANKCODE(String value) {
        this.bankcode = value;
    }

    /**
     * 获取bankcountry属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBANKCOUNTRY() {
        return bankcountry;
    }

    /**
     * 设置bankcountry属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBANKCOUNTRY(String value) {
        this.bankcountry = value;
    }

    /**
     * 获取bankname属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBANKNAME() {
        return bankname;
    }

    /**
     * 设置bankname属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBANKNAME(String value) {
        this.bankname = value;
    }

    /**
     * 获取default属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDEFAULT() {
        return _default;
    }

    /**
     * 设置default属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDEFAULT(String value) {
        this._default = value;
    }

    /**
     * 获取swiftcode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSWIFTCODE() {
        return swiftcode;
    }

    /**
     * 设置swiftcode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSWIFTCODE(String value) {
        this.swiftcode = value;
    }

    @Override
    public String toString() {
        return "BankINFO{" +
                "accountholder='" + accountholder + '\'' +
                ", bankaccount='" + bankaccount + '\'' +
                ", bankcode='" + bankcode + '\'' +
                ", bankcountry='" + bankcountry + '\'' +
                ", bankname='" + bankname + '\'' +
                ", _default='" + _default + '\'' +
                ", swiftcode='" + swiftcode + '\'' +
                '}';
    }
}
