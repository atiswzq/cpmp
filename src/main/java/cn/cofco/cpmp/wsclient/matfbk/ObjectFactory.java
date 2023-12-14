
package cn.cofco.cpmp.wsclient.matfbk;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.cofco.cpmp.wsclient.matfbk package. 
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

    private final static QName _MTMaterialStatus_QNAME = new QName("http://www.cofco.com/segment/material", "MT_MaterialStatus");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.cofco.cpmp.wsclient.matfbk
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTMaterialStatus }
     * 
     */
    public DTMaterialStatus createDTMaterialStatus() {
        return new DTMaterialStatus();
    }

    /**
     * Create an instance of {@link DTMaterialStatus.FEEDBACK }
     * 
     */
    public DTMaterialStatus.FEEDBACK createDTMaterialStatusFEEDBACK() {
        return new DTMaterialStatus.FEEDBACK();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTMaterialStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cofco.com/segment/material", name = "MT_MaterialStatus")
    public JAXBElement<DTMaterialStatus> createMTMaterialStatus(DTMaterialStatus value) {
        return new JAXBElement<DTMaterialStatus>(_MTMaterialStatus_QNAME, DTMaterialStatus.class, null, value);
    }

}
