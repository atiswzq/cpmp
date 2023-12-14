
package cn.cofco.cpmp.wsclient.saptest;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.cofco.cpmp.wsclient.saptest package. 
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

    private final static QName _MTSRM2ECCPOREQ_QNAME = new QName("jt:bpm:demo", "MT_SRM2ECC_PO_REQ");
    private final static QName _MTSRM2ECCPORESP_QNAME = new QName("jt:bpm:demo", "MT_SRM2ECC_PO_RESP");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.cofco.cpmp.wsclient.saptest
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DTSRM2ECCPOREQ }
     * 
     */
    public DTSRM2ECCPOREQ createDTSRM2ECCPOREQ() {
        return new DTSRM2ECCPOREQ();
    }

    /**
     * Create an instance of {@link DTSRM2ECCPORESP }
     * 
     */
    public DTSRM2ECCPORESP createDTSRM2ECCPORESP() {
        return new DTSRM2ECCPORESP();
    }

    /**
     * Create an instance of {@link DTSRM2ECCPOREQ.ITEM }
     * 
     */
    public DTSRM2ECCPOREQ.ITEM createDTSRM2ECCPOREQITEM() {
        return new DTSRM2ECCPOREQ.ITEM();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTSRM2ECCPOREQ }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jt:bpm:demo", name = "MT_SRM2ECC_PO_REQ")
    public JAXBElement<DTSRM2ECCPOREQ> createMTSRM2ECCPOREQ(DTSRM2ECCPOREQ value) {
        return new JAXBElement<DTSRM2ECCPOREQ>(_MTSRM2ECCPOREQ_QNAME, DTSRM2ECCPOREQ.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DTSRM2ECCPORESP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "jt:bpm:demo", name = "MT_SRM2ECC_PO_RESP")
    public JAXBElement<DTSRM2ECCPORESP> createMTSRM2ECCPORESP(DTSRM2ECCPORESP value) {
        return new JAXBElement<DTSRM2ECCPORESP>(_MTSRM2ECCPORESP_QNAME, DTSRM2ECCPORESP.class, null, value);
    }

}
