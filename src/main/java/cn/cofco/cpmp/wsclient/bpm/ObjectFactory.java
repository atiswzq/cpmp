
package cn.cofco.cpmp.wsclient.bpm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.cofco.cpmp.wsclient.bpm package. 
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

    private final static QName _LaunchFormCollaboration4SapToken_QNAME = new QName("http://impl.flow.services.v3x.seeyon.com", "token");
    private final static QName _LaunchFormCollaboration4SapSenderLoginName_QNAME = new QName("http://impl.flow.services.v3x.seeyon.com", "senderLoginName");
    private final static QName _LaunchFormCollaboration4SapTemplateCode_QNAME = new QName("http://impl.flow.services.v3x.seeyon.com", "templateCode");
    private final static QName _LaunchFormCollaboration4SapSubject_QNAME = new QName("http://impl.flow.services.v3x.seeyon.com", "subject");
    private final static QName _LaunchFormCollaboration4SapData_QNAME = new QName("http://impl.flow.services.v3x.seeyon.com", "data");
    private final static QName _LaunchFormCollaboration4SapParam_QNAME = new QName("http://impl.flow.services.v3x.seeyon.com", "param");
    private final static QName _LaunchFormCollaboration4SapRelateDoc_QNAME = new QName("http://impl.flow.services.v3x.seeyon.com", "relateDoc");
    private final static QName _LaunchFormCollaboration4SapResponseReturn_QNAME = new QName("http://impl.flow.services.v3x.seeyon.com", "return");
    private final static QName _ServiceResponseErrorMessage_QNAME = new QName("http://services.v3x.seeyon.com/xsd", "errorMessage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.cofco.cpmp.wsclient.bpm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServiceResponse }
     * 
     */
    public ServiceResponse createServiceResponse() {
        return new ServiceResponse();
    }

    /**
     * Create an instance of {@link LaunchFormCollaboration4Sap }
     * 
     */
    public LaunchFormCollaboration4Sap createLaunchFormCollaboration4Sap() {
        return new LaunchFormCollaboration4Sap();
    }

    /**
     * Create an instance of {@link LaunchFormCollaboration4SapResponse }
     * 
     */
    public LaunchFormCollaboration4SapResponse createLaunchFormCollaboration4SapResponse() {
        return new LaunchFormCollaboration4SapResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.flow.services.v3x.seeyon.com", name = "token", scope = LaunchFormCollaboration4Sap.class)
    public JAXBElement<String> createLaunchFormCollaboration4SapToken(String value) {
        return new JAXBElement<String>(_LaunchFormCollaboration4SapToken_QNAME, String.class, LaunchFormCollaboration4Sap.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.flow.services.v3x.seeyon.com", name = "senderLoginName", scope = LaunchFormCollaboration4Sap.class)
    public JAXBElement<String> createLaunchFormCollaboration4SapSenderLoginName(String value) {
        return new JAXBElement<String>(_LaunchFormCollaboration4SapSenderLoginName_QNAME, String.class, LaunchFormCollaboration4Sap.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.flow.services.v3x.seeyon.com", name = "templateCode", scope = LaunchFormCollaboration4Sap.class)
    public JAXBElement<String> createLaunchFormCollaboration4SapTemplateCode(String value) {
        return new JAXBElement<String>(_LaunchFormCollaboration4SapTemplateCode_QNAME, String.class, LaunchFormCollaboration4Sap.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.flow.services.v3x.seeyon.com", name = "subject", scope = LaunchFormCollaboration4Sap.class)
    public JAXBElement<String> createLaunchFormCollaboration4SapSubject(String value) {
        return new JAXBElement<String>(_LaunchFormCollaboration4SapSubject_QNAME, String.class, LaunchFormCollaboration4Sap.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.flow.services.v3x.seeyon.com", name = "data", scope = LaunchFormCollaboration4Sap.class)
    public JAXBElement<String> createLaunchFormCollaboration4SapData(String value) {
        return new JAXBElement<String>(_LaunchFormCollaboration4SapData_QNAME, String.class, LaunchFormCollaboration4Sap.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.flow.services.v3x.seeyon.com", name = "param", scope = LaunchFormCollaboration4Sap.class)
    public JAXBElement<String> createLaunchFormCollaboration4SapParam(String value) {
        return new JAXBElement<String>(_LaunchFormCollaboration4SapParam_QNAME, String.class, LaunchFormCollaboration4Sap.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.flow.services.v3x.seeyon.com", name = "relateDoc", scope = LaunchFormCollaboration4Sap.class)
    public JAXBElement<String> createLaunchFormCollaboration4SapRelateDoc(String value) {
        return new JAXBElement<String>(_LaunchFormCollaboration4SapRelateDoc_QNAME, String.class, LaunchFormCollaboration4Sap.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.flow.services.v3x.seeyon.com", name = "return", scope = LaunchFormCollaboration4SapResponse.class)
    public JAXBElement<ServiceResponse> createLaunchFormCollaboration4SapResponseReturn(ServiceResponse value) {
        return new JAXBElement<ServiceResponse>(_LaunchFormCollaboration4SapResponseReturn_QNAME, ServiceResponse.class, LaunchFormCollaboration4SapResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.v3x.seeyon.com/xsd", name = "errorMessage", scope = ServiceResponse.class)
    public JAXBElement<String> createServiceResponseErrorMessage(String value) {
        return new JAXBElement<String>(_ServiceResponseErrorMessage_QNAME, String.class, ServiceResponse.class, value);
    }

}
