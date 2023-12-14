
package cn.cofco.cpmp.wsclient.splr;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>mdmPARTNER complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="mdmPARTNER"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ACCOUNT_GROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ACCOUNT_GROUP_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BLACK_LIST" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BLACK_LIST_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BP_CLASS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BP_CLASS_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BUSINESS_LICENSE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BUSINESS_SCOPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="banks" type="{http://ibm.com/service/partner/}bankINFO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="CITY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CITY_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CLASSIFICATION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CLASSIFICATION_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="COMPANY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CONTROLLER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="COUNTRY_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CREATE_USER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CREATE_USER_CO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CREDIT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companys" type="{http://ibm.com/service/partner/}coINFO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="DISTRICT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DISTRICT_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EMAIL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ENTERPRISE_NATURE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ENTERPRISE_NATURE_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EX_GROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FAX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FAX_EX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FULL_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HAS_CREDIT_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INDUSTRY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INDUSTRY1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INDUSTRY1_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INDUSTRY2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="INDUSTRY2_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_GROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IS_CV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IS_DELETE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IS_EXIST" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IS_KEY_ACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LEGAL_REPRESENTATIVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LEGAL_REPRESENTATIVE_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MOBILE_PHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OLD_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ORGANIZATION_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PARENT_COMPANY" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PARENT_COMPANY_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PARTNER_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PARTNER_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PARTNER_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="POST_CODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="REGION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="REGION_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="REGION_SAP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="REGISTRATION_CAPITAL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="REGISTRATION_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SEC_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SHORT_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SUPERIOR_GROUP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SUPERIOR_GROUP_DESC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TAX_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TELEPHONE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TEL_EX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TRADING_PARTNER" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mdmPARTNER", propOrder = {
        "accountgroup",
        "accountgroupdesc",
        "address",
        "blacklist",
        "blacklistdesc",
        "bpclass",
        "bpclassdesc",
        "businesslicense",
        "businessscope",
        "banks",
        "city",
        "citydesc",
        "classification",
        "classificationdesc",
        "company",
        "controller",
        "country",
        "countrydesc",
        "createuser",
        "createuserco",
        "creditcode",
        "companys",
        "district",
        "districtdesc",
        "email",
        "enterprisenature",
        "enterprisenaturedesc",
        "exgroup",
        "fax",
        "faxex",
        "fullname",
        "hascreditcode",
        "industry",
        "industry1",
        "industry1DESC",
        "industry2",
        "industry2DESC",
        "ingroup",
    "iscv",
    "isdelete",
    "isexist",
    "iskeyaccount",
    "legalrepresentative",
    "legalrepresentativeid",
    "mobilephone",
    "oldnumber",
    "organizationcode",
    "parentcompany",
    "parentcompanydesc",
    "partnerid",
    "partnernumber",
    "partnertype",
    "postcode",
    "region",
    "regiondesc",
    "regionsap",
    "registrationcapital",
    "registrationdate",
    "secname",
    "shortname",
    "superiorgroup",
    "superiorgroupdesc",
    "taxnumber",
    "telephone",
    "telex",
    "tradingpartner"
})
public class MdmPARTNER {

    @XmlElement(name = "ACCOUNT_GROUP")
    protected String accountgroup;
    @XmlElement(name = "ACCOUNT_GROUP_DESC")
    protected String accountgroupdesc;
    @XmlElement(name = "ADDRESS")
    protected String address;
    @XmlElement(name = "BLACK_LIST")
    protected String blacklist;
    @XmlElement(name = "BLACK_LIST_DESC")
    protected String blacklistdesc;
    @XmlElement(name = "BP_CLASS")
    protected String bpclass;
    @XmlElement(name = "BP_CLASS_DESC")
    protected String bpclassdesc;
    @XmlElement(name = "BUSINESS_LICENSE")
    protected String businesslicense;
    @XmlElement(name = "BUSINESS_SCOPE")
    protected String businessscope;
    @XmlElement(nillable = true)
    protected List<BankINFO> banks;
    @XmlElement(name = "CITY")
    protected String city;
    @XmlElement(name = "CITY_DESC")
    protected String citydesc;
    @XmlElement(name = "CLASSIFICATION")
    protected String classification;
    @XmlElement(name = "CLASSIFICATION_DESC")
    protected String classificationdesc;
    @XmlElement(name = "COMPANY")
    protected String company;
    @XmlElement(name = "CONTROLLER")
    protected String controller;
    @XmlElement(name = "COUNTRY")
    protected String country;
    @XmlElement(name = "COUNTRY_DESC")
    protected String countrydesc;
    @XmlElement(name = "CREATE_USER")
    protected String createuser;
    @XmlElement(name = "CREATE_USER_CO")
    protected String createuserco;
    @XmlElement(name = "CREDIT_CODE")
    protected String creditcode;
    @XmlElement(nillable = true)
    protected List<CoINFO> companys;
    @XmlElement(name = "DISTRICT")
    protected String district;
    @XmlElement(name = "DISTRICT_DESC")
    protected String districtdesc;
    @XmlElement(name = "EMAIL")
    protected String email;
    @XmlElement(name = "ENTERPRISE_NATURE")
    protected String enterprisenature;
    @XmlElement(name = "ENTERPRISE_NATURE_DESC")
    protected String enterprisenaturedesc;
    @XmlElement(name = "EX_GROUP")
    protected String exgroup;
    @XmlElement(name = "FAX")
    protected String fax;
    @XmlElement(name = "FAX_EX")
    protected String faxex;
    @XmlElement(name = "FULL_NAME")
    protected String fullname;
    @XmlElement(name = "HAS_CREDIT_CODE")
    protected String hascreditcode;
    @XmlElement(name = "INDUSTRY")
    protected String industry;
    @XmlElement(name = "INDUSTRY1")
    protected String industry1;
    @XmlElement(name = "INDUSTRY1_DESC")
    protected String industry1DESC;
    @XmlElement(name = "INDUSTRY2")
    protected String industry2;
    @XmlElement(name = "INDUSTRY2_DESC")
    protected String industry2DESC;
    @XmlElement(name = "IN_GROUP")
    protected String ingroup;
    @XmlElement(name = "IS_CV")
    protected String iscv;
    @XmlElement(name = "IS_DELETE")
    protected String isdelete;
    @XmlElement(name = "IS_EXIST")
    protected String isexist;
    @XmlElement(name = "IS_KEY_ACCOUNT")
    protected String iskeyaccount;
    @XmlElement(name = "LEGAL_REPRESENTATIVE")
    protected String legalrepresentative;
    @XmlElement(name = "LEGAL_REPRESENTATIVE_ID")
    protected String legalrepresentativeid;
    @XmlElement(name = "MOBILE_PHONE")
    protected String mobilephone;
    @XmlElement(name = "OLD_NUMBER")
    protected String oldnumber;
    @XmlElement(name = "ORGANIZATION_CODE")
    protected String organizationcode;
    @XmlElement(name = "PARENT_COMPANY")
    protected String parentcompany;
    @XmlElement(name = "PARENT_COMPANY_DESC")
    protected String parentcompanydesc;
    @XmlElement(name = "PARTNER_ID")
    protected String partnerid;
    @XmlElement(name = "PARTNER_NUMBER")
    protected String partnernumber;
    @XmlElement(name = "PARTNER_TYPE")
    protected String partnertype;
    @XmlElement(name = "POST_CODE")
    protected String postcode;
    @XmlElement(name = "REGION")
    protected String region;
    @XmlElement(name = "REGION_DESC")
    protected String regiondesc;
    @XmlElement(name = "REGION_SAP")
    protected String regionsap;
    @XmlElement(name = "REGISTRATION_CAPITAL")
    protected String registrationcapital;
    @XmlElement(name = "REGISTRATION_DATE")
    protected String registrationdate;
    @XmlElement(name = "SEC_NAME")
    protected String secname;
    @XmlElement(name = "SHORT_NAME")
    protected String shortname;
    @XmlElement(name = "SUPERIOR_GROUP")
    protected String superiorgroup;
    @XmlElement(name = "SUPERIOR_GROUP_DESC")
    protected String superiorgroupdesc;
    @XmlElement(name = "TAX_NUMBER")
    protected String taxnumber;
    @XmlElement(name = "TELEPHONE")
    protected String telephone;
    @XmlElement(name = "TEL_EX")
    protected String telex;
    @XmlElement(name = "TRADING_PARTNER")
    protected String tradingpartner;

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
     * 获取accountgroupdesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getACCOUNTGROUPDESC() {
        return accountgroupdesc;
    }

    /**
     * 设置accountgroupdesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setACCOUNTGROUPDESC(String value) {
        this.accountgroupdesc = value;
    }

    /**
     * 获取address属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getADDRESS() {
        return address;
    }

    /**
     * 设置address属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setADDRESS(String value) {
        this.address = value;
    }

    /**
     * 获取blacklist属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBLACKLIST() {
        return blacklist;
    }

    /**
     * 设置blacklist属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBLACKLIST(String value) {
        this.blacklist = value;
    }

    /**
     * 获取blacklistdesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBLACKLISTDESC() {
        return blacklistdesc;
    }

    /**
     * 设置blacklistdesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBLACKLISTDESC(String value) {
        this.blacklistdesc = value;
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
     * 获取bpclassdesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBPCLASSDESC() {
        return bpclassdesc;
    }

    /**
     * 设置bpclassdesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBPCLASSDESC(String value) {
        this.bpclassdesc = value;
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
     * 获取businessscope属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBUSINESSSCOPE() {
        return businessscope;
    }

    /**
     * 设置businessscope属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBUSINESSSCOPE(String value) {
        this.businessscope = value;
    }

    /**
     * Gets the value of the banks property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the banks property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBanks().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankINFO }
     *
     *
     */
    public List<BankINFO> getBanks() {
        if (banks == null) {
            banks = new ArrayList<BankINFO>();
        }
        return this.banks;
    }

    /**
     * 获取city属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCITY() {
        return city;
    }

    /**
     * 设置city属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCITY(String value) {
        this.city = value;
    }

    /**
     * 获取citydesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCITYDESC() {
        return citydesc;
    }

    /**
     * 设置citydesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCITYDESC(String value) {
        this.citydesc = value;
    }

    /**
     * 获取classification属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCLASSIFICATION() {
        return classification;
    }

    /**
     * 设置classification属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCLASSIFICATION(String value) {
        this.classification = value;
    }

    /**
     * 获取classificationdesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCLASSIFICATIONDESC() {
        return classificationdesc;
    }

    /**
     * 设置classificationdesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCLASSIFICATIONDESC(String value) {
        this.classificationdesc = value;
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
     * 获取controller属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCONTROLLER() {
        return controller;
    }

    /**
     * 设置controller属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCONTROLLER(String value) {
        this.controller = value;
    }

    /**
     * 获取country属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOUNTRY() {
        return country;
    }

    /**
     * 设置country属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOUNTRY(String value) {
        this.country = value;
    }

    /**
     * 获取countrydesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCOUNTRYDESC() {
        return countrydesc;
    }

    /**
     * 设置countrydesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCOUNTRYDESC(String value) {
        this.countrydesc = value;
    }

    /**
     * 获取createuser属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCREATEUSER() {
        return createuser;
    }

    /**
     * 设置createuser属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCREATEUSER(String value) {
        this.createuser = value;
    }

    /**
     * 获取createuserco属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCREATEUSERCO() {
        return createuserco;
    }

    /**
     * 设置createuserco属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCREATEUSERCO(String value) {
        this.createuserco = value;
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
     * Gets the value of the companys property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the companys property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompanys().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CoINFO }
     *
     *
     */
    public List<CoINFO> getCompanys() {
        if (companys == null) {
            companys = new ArrayList<CoINFO>();
        }
        return this.companys;
    }

    /**
     * 获取district属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDISTRICT() {
        return district;
    }

    /**
     * 设置district属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDISTRICT(String value) {
        this.district = value;
    }

    /**
     * 获取districtdesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDISTRICTDESC() {
        return districtdesc;
    }

    /**
     * 设置districtdesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDISTRICTDESC(String value) {
        this.districtdesc = value;
    }

    /**
     * 获取email属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEMAIL() {
        return email;
    }

    /**
     * 设置email属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEMAIL(String value) {
        this.email = value;
    }

    /**
     * 获取enterprisenature属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getENTERPRISENATURE() {
        return enterprisenature;
    }

    /**
     * 设置enterprisenature属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setENTERPRISENATURE(String value) {
        this.enterprisenature = value;
    }

    /**
     * 获取enterprisenaturedesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getENTERPRISENATUREDESC() {
        return enterprisenaturedesc;
    }

    /**
     * 设置enterprisenaturedesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setENTERPRISENATUREDESC(String value) {
        this.enterprisenaturedesc = value;
    }

    /**
     * 获取exgroup属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEXGROUP() {
        return exgroup;
    }

    /**
     * 设置exgroup属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEXGROUP(String value) {
        this.exgroup = value;
    }

    /**
     * 获取fax属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFAX() {
        return fax;
    }

    /**
     * 设置fax属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFAX(String value) {
        this.fax = value;
    }

    /**
     * 获取faxex属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFAXEX() {
        return faxex;
    }

    /**
     * 设置faxex属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFAXEX(String value) {
        this.faxex = value;
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
     * 获取industry属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINDUSTRY() {
        return industry;
    }

    /**
     * 设置industry属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINDUSTRY(String value) {
        this.industry = value;
    }

    /**
     * 获取industry1属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINDUSTRY1() {
        return industry1;
    }

    /**
     * 设置industry1属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINDUSTRY1(String value) {
        this.industry1 = value;
    }

    /**
     * 获取industry1DESC属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINDUSTRY1DESC() {
        return industry1DESC;
    }

    /**
     * 设置industry1DESC属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINDUSTRY1DESC(String value) {
        this.industry1DESC = value;
    }

    /**
     * 获取industry2属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINDUSTRY2() {
        return industry2;
    }

    /**
     * 设置industry2属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINDUSTRY2(String value) {
        this.industry2 = value;
    }

    /**
     * 获取industry2DESC属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINDUSTRY2DESC() {
        return industry2DESC;
    }

    /**
     * 设置industry2DESC属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setINDUSTRY2DESC(String value) {
        this.industry2DESC = value;
    }

    /**
     * 获取ingroup属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getINGROUP() {
        return ingroup;
    }

    /**
     * 设置ingroup属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINGROUP(String value) {
        this.ingroup = value;
    }

    /**
     * »ñÈ¡iscvÊôÐÔµÄÖµ¡£
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISCV() {
        return iscv;
    }

    /**
     * ÉèÖÃiscvÊôÐÔµÄÖµ¡£
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISCV(String value) {
        this.iscv = value;
    }

    /**
     * »ñÈ¡isdeleteÊôÐÔµÄÖµ¡£
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISDELETE() {
        return isdelete;
    }

    /**
     * ÉèÖÃisdeleteÊôÐÔµÄÖµ¡£
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISDELETE(String value) {
        this.isdelete = value;
    }

    /**
     * »ñÈ¡isexistÊôÐÔµÄÖµ¡£
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISEXIST() {
        return isexist;
    }

    /**
     * 设置isexist属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setISEXIST(String value) {
        this.isexist = value;
    }

    /**
     * 获取iskeyaccount属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getISKEYACCOUNT() {
        return iskeyaccount;
    }

    /**
     * 设置iskeyaccount属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setISKEYACCOUNT(String value) {
        this.iskeyaccount = value;
    }

    /**
     * 获取legalrepresentative属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLEGALREPRESENTATIVE() {
        return legalrepresentative;
    }

    /**
     * 设置legalrepresentative属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLEGALREPRESENTATIVE(String value) {
        this.legalrepresentative = value;
    }

    /**
     * 获取legalrepresentativeid属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLEGALREPRESENTATIVEID() {
        return legalrepresentativeid;
    }

    /**
     * 设置legalrepresentativeid属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLEGALREPRESENTATIVEID(String value) {
        this.legalrepresentativeid = value;
    }

    /**
     * 获取mobilephone属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMOBILEPHONE() {
        return mobilephone;
    }

    /**
     * 设置mobilephone属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMOBILEPHONE(String value) {
        this.mobilephone = value;
    }

    /**
     * 获取oldnumber属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOLDNUMBER() {
        return oldnumber;
    }

    /**
     * 设置oldnumber属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOLDNUMBER(String value) {
        this.oldnumber = value;
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
     * 获取parentcompany属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPARENTCOMPANY() {
        return parentcompany;
    }

    /**
     * 设置parentcompany属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPARENTCOMPANY(String value) {
        this.parentcompany = value;
    }

    /**
     * 获取parentcompanydesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPARENTCOMPANYDESC() {
        return parentcompanydesc;
    }

    /**
     * 设置parentcompanydesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPARENTCOMPANYDESC(String value) {
        this.parentcompanydesc = value;
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

    /**
     * 获取partnertype属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPARTNERTYPE() {
        return partnertype;
    }

    /**
     * 设置partnertype属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPARTNERTYPE(String value) {
        this.partnertype = value;
    }

    /**
     * 获取postcode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPOSTCODE() {
        return postcode;
    }

    /**
     * 设置postcode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPOSTCODE(String value) {
        this.postcode = value;
    }

    /**
     * 获取region属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getREGION() {
        return region;
    }

    /**
     * 设置region属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setREGION(String value) {
        this.region = value;
    }

    /**
     * 获取regiondesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getREGIONDESC() {
        return regiondesc;
    }

    /**
     * 设置regiondesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setREGIONDESC(String value) {
        this.regiondesc = value;
    }

    /**
     * »ñÈ¡regionsapÊôÐÔµÄÖµ¡£
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getREGIONSAP() {
        return regionsap;
    }

    /**
     * ÉèÖÃregionsapÊôÐÔµÄÖµ¡£
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setREGIONSAP(String value) {
        this.regionsap = value;
    }

    /**
     * »ñÈ¡registrationcapitalÊôÐÔµÄÖµ¡£
     * 
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getREGISTRATIONCAPITAL() {
        return registrationcapital;
    }

    /**
     * 设置registrationcapital属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setREGISTRATIONCAPITAL(String value) {
        this.registrationcapital = value;
    }

    /**
     * 获取registrationdate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getREGISTRATIONDATE() {
        return registrationdate;
    }

    /**
     * 设置registrationdate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setREGISTRATIONDATE(String value) {
        this.registrationdate = value;
    }

    /**
     * »ñÈ¡secnameÊôÐÔµÄÖµ¡£
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSECNAME() {
        return secname;
    }

    /**
     * ÉèÖÃsecnameÊôÐÔµÄÖµ¡£
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSECNAME(String value) {
        this.secname = value;
    }

    /**
     * »ñÈ¡shortnameÊôÐÔµÄÖµ¡£
     * 
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSHORTNAME() {
        return shortname;
    }

    /**
     * 设置shortname属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSHORTNAME(String value) {
        this.shortname = value;
    }

    /**
     * 获取superiorgroup属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSUPERIORGROUP() {
        return superiorgroup;
    }

    /**
     * 设置superiorgroup属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSUPERIORGROUP(String value) {
        this.superiorgroup = value;
    }

    /**
     * 获取superiorgroupdesc属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSUPERIORGROUPDESC() {
        return superiorgroupdesc;
    }

    /**
     * 设置superiorgroupdesc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSUPERIORGROUPDESC(String value) {
        this.superiorgroupdesc = value;
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

    /**
     * 获取telephone属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTELEPHONE() {
        return telephone;
    }

    /**
     * 设置telephone属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTELEPHONE(String value) {
        this.telephone = value;
    }

    /**
     * 获取telex属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTELEX() {
        return telex;
    }

    /**
     * 设置telex属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTELEX(String value) {
        this.telex = value;
    }

    /**
     * 获取tradingpartner属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTRADINGPARTNER() {
        return tradingpartner;
    }

    /**
     * 设置tradingpartner属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTRADINGPARTNER(String value) {
        this.tradingpartner = value;
    }

    public void setBanks(List<BankINFO> banks) {
        this.banks = banks;
    }

    public void setCompanys(List<CoINFO> companys) {
        this.companys = companys;
    }

    @Override
    public String toString() {
        return "MdmPARTNER{" +
                "accountgroup='" + accountgroup + '\'' +
                ", accountgroupdesc='" + accountgroupdesc + '\'' +
                ", address='" + address + '\'' +
                ", blacklist='" + blacklist + '\'' +
                ", blacklistdesc='" + blacklistdesc + '\'' +
                ", bpclass='" + bpclass + '\'' +
                ", bpclassdesc='" + bpclassdesc + '\'' +
                ", businesslicense='" + businesslicense + '\'' +
                ", businessscope='" + businessscope + '\'' +
                ", banks=" + banks +
                ", city='" + city + '\'' +
                ", citydesc='" + citydesc + '\'' +
                ", classification='" + classification + '\'' +
                ", classificationdesc='" + classificationdesc + '\'' +
                ", company='" + company + '\'' +
                ", controller='" + controller + '\'' +
                ", country='" + country + '\'' +
                ", countrydesc='" + countrydesc + '\'' +
                ", createuser='" + createuser + '\'' +
                ", createuserco='" + createuserco + '\'' +
                ", creditcode='" + creditcode + '\'' +
                ", companys=" + companys +
                ", district='" + district + '\'' +
                ", districtdesc='" + districtdesc + '\'' +
                ", email='" + email + '\'' +
                ", enterprisenature='" + enterprisenature + '\'' +
                ", enterprisenaturedesc='" + enterprisenaturedesc + '\'' +
                ", exgroup='" + exgroup + '\'' +
                ", fax='" + fax + '\'' +
                ", faxex='" + faxex + '\'' +
                ", fullname='" + fullname + '\'' +
                ", hascreditcode='" + hascreditcode + '\'' +
                ", industry='" + industry + '\'' +
                ", industry1='" + industry1 + '\'' +
                ", industry1DESC='" + industry1DESC + '\'' +
                ", industry2='" + industry2 + '\'' +
                ", industry2DESC='" + industry2DESC + '\'' +
                ", ingroup='" + ingroup + '\'' +
                ", iscv='" + iscv + '\'' +
                ", isdelete='" + isdelete + '\'' +
                ", isexist='" + isexist + '\'' +
                ", iskeyaccount='" + iskeyaccount + '\'' +
                ", legalrepresentative='" + legalrepresentative + '\'' +
                ", legalrepresentativeid='" + legalrepresentativeid + '\'' +
                ", mobilephone='" + mobilephone + '\'' +
                ", oldnumber='" + oldnumber + '\'' +
                ", organizationcode='" + organizationcode + '\'' +
                ", parentcompany='" + parentcompany + '\'' +
                ", parentcompanydesc='" + parentcompanydesc + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", partnernumber='" + partnernumber + '\'' +
                ", partnertype='" + partnertype + '\'' +
                ", postcode='" + postcode + '\'' +
                ", region='" + region + '\'' +
                ", regiondesc='" + regiondesc + '\'' +
                ", regionsap='" + regionsap + '\'' +
                ", registrationcapital='" + registrationcapital + '\'' +
                ", registrationdate='" + registrationdate + '\'' +
                ", secname='" + secname + '\'' +
                ", shortname='" + shortname + '\'' +
                ", superiorgroup='" + superiorgroup + '\'' +
                ", superiorgroupdesc='" + superiorgroupdesc + '\'' +
                ", taxnumber='" + taxnumber + '\'' +
                ", telephone='" + telephone + '\'' +
                ", telex='" + telex + '\'' +
                ", tradingpartner='" + tradingpartner + '\'' +
                '}';
    }
}
