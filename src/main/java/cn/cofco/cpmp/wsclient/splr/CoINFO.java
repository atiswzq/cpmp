
package cn.cofco.cpmp.wsclient.splr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>coINFO complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="coINFO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CO_ACCOUNT_GROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_COMPANY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_KEY_ACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_KEY_ACCOUNT_NO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_MY_COMPANYCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_REMARK1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_REMARK10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_REMARK2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_REMARK3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_REMARK4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_REMARK5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_REMARK6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_REMARK7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_REMARK8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CO_REMARK9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "coINFO", propOrder = {
        "coaccountgroup",
        "cocompany",
        "cokeyaccount",
        "cokeyaccountno",
        "comycompanycode",
        "coremark1",
        "coremark10",
        "coremark2",
        "coremark3",
        "coremark4",
        "coremark5",
        "coremark6",
        "coremark7",
        "coremark8",
        "coremark9"
})
public class CoINFO {

    @XmlElement(name = "CO_ACCOUNT_GROUP")
    protected String coaccountgroup;
    @XmlElement(name = "CO_COMPANY")
    protected String cocompany;
    @XmlElement(name = "CO_KEY_ACCOUNT")
    protected String cokeyaccount;
    @XmlElement(name = "CO_KEY_ACCOUNT_NO")
    protected String cokeyaccountno;
    @XmlElement(name = "CO_MY_COMPANYCODE")
    protected String comycompanycode;
    @XmlElement(name = "CO_REMARK1")
    protected String coremark1;
    @XmlElement(name = "CO_REMARK10")
    protected String coremark10;
    @XmlElement(name = "CO_REMARK2")
    protected String coremark2;
    @XmlElement(name = "CO_REMARK3")
    protected String coremark3;
    @XmlElement(name = "CO_REMARK4")
    protected String coremark4;
    @XmlElement(name = "CO_REMARK5")
    protected String coremark5;
    @XmlElement(name = "CO_REMARK6")
    protected String coremark6;
    @XmlElement(name = "CO_REMARK7")
    protected String coremark7;
    @XmlElement(name = "CO_REMARK8")
    protected String coremark8;
    @XmlElement(name = "CO_REMARK9")
    protected String coremark9;

    /**
     * 获取coaccountgroup属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOACCOUNTGROUP() {
        return coaccountgroup;
    }

    /**
     * 设置coaccountgroup属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOACCOUNTGROUP(String value) {
        this.coaccountgroup = value;
    }

    /**
     * 获取cocompany属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOCOMPANY() {
        return cocompany;
    }

    /**
     * 设置cocompany属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOCOMPANY(String value) {
        this.cocompany = value;
    }

    /**
     * 获取cokeyaccount属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOKEYACCOUNT() {
        return cokeyaccount;
    }

    /**
     * 设置cokeyaccount属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOKEYACCOUNT(String value) {
        this.cokeyaccount = value;
    }

    /**
     * 获取cokeyaccountno属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOKEYACCOUNTNO() {
        return cokeyaccountno;
    }

    /**
     * 设置cokeyaccountno属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOKEYACCOUNTNO(String value) {
        this.cokeyaccountno = value;
    }

    /**
     * 获取comycompanycode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOMYCOMPANYCODE() {
        return comycompanycode;
    }

    /**
     * 设置comycompanycode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOMYCOMPANYCODE(String value) {
        this.comycompanycode = value;
    }

    /**
     * 获取coremark1属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOREMARK1() {
        return coremark1;
    }

    /**
     * 设置coremark1属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOREMARK1(String value) {
        this.coremark1 = value;
    }

    /**
     * 获取coremark10属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOREMARK10() {
        return coremark10;
    }

    /**
     * 设置coremark10属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOREMARK10(String value) {
        this.coremark10 = value;
    }

    /**
     * 获取coremark2属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOREMARK2() {
        return coremark2;
    }

    /**
     * 设置coremark2属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOREMARK2(String value) {
        this.coremark2 = value;
    }

    /**
     * 获取coremark3属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOREMARK3() {
        return coremark3;
    }

    /**
     * 设置coremark3属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOREMARK3(String value) {
        this.coremark3 = value;
    }

    /**
     * 获取coremark4属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOREMARK4() {
        return coremark4;
    }

    /**
     * 设置coremark4属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOREMARK4(String value) {
        this.coremark4 = value;
    }

    /**
     * 获取coremark5属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOREMARK5() {
        return coremark5;
    }

    /**
     * 设置coremark5属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOREMARK5(String value) {
        this.coremark5 = value;
    }

    /**
     * 获取coremark6属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOREMARK6() {
        return coremark6;
    }

    /**
     * 设置coremark6属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOREMARK6(String value) {
        this.coremark6 = value;
    }

    /**
     * 获取coremark7属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOREMARK7() {
        return coremark7;
    }

    /**
     * 设置coremark7属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOREMARK7(String value) {
        this.coremark7 = value;
    }

    /**
     * 获取coremark8属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOREMARK8() {
        return coremark8;
    }

    /**
     * 设置coremark8属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOREMARK8(String value) {
        this.coremark8 = value;
    }

    /**
     * 获取coremark9属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOREMARK9() {
        return coremark9;
    }

    /**
     * 设置coremark9属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOREMARK9(String value) {
        this.coremark9 = value;
    }

    @Override
    public String toString() {
        return "CoINFO{" +
                "coaccountgroup='" + coaccountgroup + '\'' +
                ", cocompany='" + cocompany + '\'' +
                ", cokeyaccount='" + cokeyaccount + '\'' +
                ", cokeyaccountno='" + cokeyaccountno + '\'' +
                ", comycompanycode='" + comycompanycode + '\'' +
                ", coremark1='" + coremark1 + '\'' +
                ", coremark10='" + coremark10 + '\'' +
                ", coremark2='" + coremark2 + '\'' +
                ", coremark3='" + coremark3 + '\'' +
                ", coremark4='" + coremark4 + '\'' +
                ", coremark5='" + coremark5 + '\'' +
                ", coremark6='" + coremark6 + '\'' +
                ", coremark7='" + coremark7 + '\'' +
                ", coremark8='" + coremark8 + '\'' +
                ", coremark9='" + coremark9 + '\'' +
                '}';
    }
}
