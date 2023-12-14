
package cn.cofco.cpmp.wsclient.bpm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="senderLoginName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="templateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="attachments" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="param" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *         &lt;element name="relateDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "token",
    "senderLoginName",
    "templateCode",
    "subject",
    "data",
    "attachments",
    "param",
    "relateDoc"
})
@XmlRootElement(name = "launchFormCollaboration4sap")
public class LaunchFormCollaboration4Sap {

    @XmlElementRef(name = "token", namespace = "http://impl.flow.services.v3x.seeyon.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> token;
    @XmlElementRef(name = "senderLoginName", namespace = "http://impl.flow.services.v3x.seeyon.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> senderLoginName;
    @XmlElementRef(name = "templateCode", namespace = "http://impl.flow.services.v3x.seeyon.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> templateCode;
    @XmlElementRef(name = "subject", namespace = "http://impl.flow.services.v3x.seeyon.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> subject;
    @XmlElementRef(name = "data", namespace = "http://impl.flow.services.v3x.seeyon.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> data;
    @XmlElement(type = Long.class)
    protected List<Long> attachments;
    @XmlElementRef(name = "param", namespace = "http://impl.flow.services.v3x.seeyon.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> param;
    @XmlElementRef(name = "relateDoc", namespace = "http://impl.flow.services.v3x.seeyon.com", type = JAXBElement.class, required = false)
    protected JAXBElement<String> relateDoc;

    /**
     * 获取token属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getToken() {
        return token;
    }

    /**
     * 设置token属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setToken(JAXBElement<String> value) {
        this.token = value;
    }

    /**
     * 获取senderLoginName属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getSenderLoginName() {
        return senderLoginName;
    }

    /**
     * 设置senderLoginName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setSenderLoginName(JAXBElement<String> value) {
        this.senderLoginName = value;
    }

    /**
     * 获取templateCode属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getTemplateCode() {
        return templateCode;
    }

    /**
     * 设置templateCode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setTemplateCode(JAXBElement<String> value) {
        this.templateCode = value;
    }

    /**
     * 获取subject属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getSubject() {
        return subject;
    }

    /**
     * 设置subject属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setSubject(JAXBElement<String> value) {
        this.subject = value;
    }

    /**
     * 获取data属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getData() {
        return data;
    }

    /**
     * 设置data属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setData(JAXBElement<String> value) {
        this.data = value;
    }

    /**
     * Gets the value of the attachments property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachments property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     *
     *
     */
    public List<Long> getAttachments() {
        if (attachments == null) {
            attachments = new ArrayList<Long>();
        }
        return this.attachments;
    }

    public void setAttachments(List<Long> attachments) {
        this.attachments = attachments;
    }

    /**
     * 获取param属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getParam() {
        return param;
    }

    /**
     * 设置param属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setParam(JAXBElement<String> value) {
        this.param = value;
    }

    /**
     * 获取relateDoc属性的值。
     *
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public JAXBElement<String> getRelateDoc() {
        return relateDoc;
    }

    /**
     * 设置relateDoc属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *
     */
    public void setRelateDoc(JAXBElement<String> value) {
        this.relateDoc = value;
    }

    @Override
    public String toString() {
        return "LaunchFormCollaboration4Sap{" +
                "token=" + token +
                ", senderLoginName=" + senderLoginName +
                ", templateCode=" + templateCode +
                ", subject=" + subject +
                ", data=" + data +
                ", attachments=" + attachments +
                ", param=" + param +
                ", relateDoc=" + relateDoc +
                '}';
    }
}
