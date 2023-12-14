
package cn.cofco.cpmp.wsclient.splr;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>setPartner complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="setPartner"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="input" type="{http://ibm.com/service/partner/}mdmPARTNER" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sysid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setPartner", propOrder = {
        "input",
        "uuid",
        "sysid"
})
public class SetPartner {

    protected List<MdmPARTNER> input;
    protected String uuid;
    protected String sysid;

    /**
     * Gets the value of the input property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the input property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInput().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MdmPARTNER }
     *
     *
     */
    public List<MdmPARTNER> getInput() {
        if (input == null) {
            input = new ArrayList<MdmPARTNER>();
        }
        return this.input;
    }

    /**
     * 获取uuid属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置uuid属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * 获取sysid属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSysid() {
        return sysid;
    }

    /**
     * 设置sysid属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSysid(String value) {
        this.sysid = value;
    }

    public void setInput(List<MdmPARTNER> input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "SetPartner{" +
                "input=" + input +
                ", uuid='" + uuid + '\'' +
                ", sysid='" + sysid + '\'' +
                '}';
    }
}
