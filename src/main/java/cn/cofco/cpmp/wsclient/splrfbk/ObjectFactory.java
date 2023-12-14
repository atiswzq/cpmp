
package cn.cofco.cpmp.wsclient.splrfbk;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.cofco.cpmp.wsclient.splrfbk package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MTCUSSUPPLIERSTATUS_QNAME = new QName("http://www.cofco.com/segment/customer_supplier", "MT_CUSSUPPLIER_STATUS");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.cofco.cpmp.wsclient.splrfbk
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTCUSSUPPLIERSTATUS }
     * 
     */
    public DTCUSSUPPLIERSTATUS createDTCUSSUPPLIERSTATUS() {
        return new DTCUSSUPPLIERSTATUS();
    }

    /**
     * Create an instance of {@link DTCUSSUPPLIERSTATUS.FEEDBACK }
     * 
     */
    public DTCUSSUPPLIERSTATUS.FEEDBACK createDTCUSSUPPLIERSTATUSFEEDBACK() {
        return new DTCUSSUPPLIERSTATUS.FEEDBACK();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTCUSSUPPLIERSTATUS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cofco.com/segment/customer_supplier", name = "MT_CUSSUPPLIER_STATUS")
    public JAXBElement<DTCUSSUPPLIERSTATUS> createMTCUSSUPPLIERSTATUS(DTCUSSUPPLIERSTATUS value) {
        return new JAXBElement<DTCUSSUPPLIERSTATUS>(_MTCUSSUPPLIERSTATUS_QNAME, DTCUSSUPPLIERSTATUS.class, null, value);
    }

}
