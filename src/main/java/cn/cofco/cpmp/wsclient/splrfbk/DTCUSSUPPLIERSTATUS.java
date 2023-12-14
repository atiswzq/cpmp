
package cn.cofco.cpmp.wsclient.splrfbk;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DT_CUSSUPPLIER_STATUS complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DT_CUSSUPPLIER_STATUS"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FEEDBACK" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="PARTNER_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="TARGET_SYSTEM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="SYN_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="FEEDBACK_MSG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DT_CUSSUPPLIER_STATUS", propOrder = {
    "feedback"
})
public class DTCUSSUPPLIERSTATUS {

    @XmlElement(name = "FEEDBACK", required = true)
	public List<DTCUSSUPPLIERSTATUS.FEEDBACK> feedback;

    /**
     * Gets the value of the feedback property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the feedback property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFEEDBACK().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DTCUSSUPPLIERSTATUS.FEEDBACK }
     * 
     * 
     */
    public List<DTCUSSUPPLIERSTATUS.FEEDBACK> getFEEDBACK() {
        if (feedback == null) {
            feedback = new ArrayList<DTCUSSUPPLIERSTATUS.FEEDBACK>();
        }
        return this.feedback;
    }


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
     *         &lt;element name="PARTNER_NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="TARGET_SYSTEM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="SYN_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="FEEDBACK_MSG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "partnernumber",
        "targetsystem",
        "synstatus",
        "feedbackmsg"
    })
    public static class FEEDBACK {

        @XmlElement(name = "PARTNER_NUMBER", required = true)
        protected String partnernumber;
        @XmlElement(name = "TARGET_SYSTEM", required = true)
        protected String targetsystem;
        @XmlElement(name = "SYN_STATUS", required = true)
        protected String synstatus;
        @XmlElement(name = "FEEDBACK_MSG", required = true)
        protected String feedbackmsg;

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
         * 获取targetsystem属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTARGETSYSTEM() {
            return targetsystem;
        }

        /**
         * 设置targetsystem属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTARGETSYSTEM(String value) {
            this.targetsystem = value;
        }

        /**
         * 获取synstatus属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSYNSTATUS() {
            return synstatus;
        }

        /**
         * 设置synstatus属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSYNSTATUS(String value) {
            this.synstatus = value;
        }

        /**
         * 获取feedbackmsg属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFEEDBACKMSG() {
            return feedbackmsg;
        }

        /**
         * 设置feedbackmsg属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFEEDBACKMSG(String value) {
            this.feedbackmsg = value;
        }

        @Override
        public String toString() {
            return "FEEDBACK{" +
                    "partnernumber='" + partnernumber + '\'' +
                    ", targetsystem='" + targetsystem + '\'' +
                    ", synstatus='" + synstatus + '\'' +
                    ", feedbackmsg='" + feedbackmsg + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DTCUSSUPPLIERSTATUS{" +
                "feedback=" + feedback +
                '}';
    }
}
