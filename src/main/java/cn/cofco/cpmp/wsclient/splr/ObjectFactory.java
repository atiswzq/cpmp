
package cn.cofco.cpmp.wsclient.splr;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.cofco.cpmp.wsclient.splr package. 
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

    private final static QName _GetPartnerResponse_QNAME = new QName("http://ibm.com/service/partner/", "getPartnerResponse");
    private final static QName _SetPartner_QNAME = new QName("http://ibm.com/service/partner/", "setPartner");
    private final static QName _GetPartner_QNAME = new QName("http://ibm.com/service/partner/", "getPartner");
    private final static QName _SetPartnerResponse_QNAME = new QName("http://ibm.com/service/partner/", "setPartnerResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.cofco.cpmp.wsclient.splr
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPartnerResponse }
     *
     */
    public GetPartnerResponse createGetPartnerResponse() {
        return new GetPartnerResponse();
    }

    /**
     * Create an instance of {@link SetPartner }
     *
     */
    public SetPartner createSetPartner() {
        return new SetPartner();
    }

    /**
     * Create an instance of {@link GetPartner }
     *
     */
    public GetPartner createGetPartner() {
        return new GetPartner();
    }

    /**
     * Create an instance of {@link SetPartnerResponse }
     *
     */
    public SetPartnerResponse createSetPartnerResponse() {
        return new SetPartnerResponse();
    }

    /**
     * Create an instance of {@link CoINFO }
     *
     */
    public CoINFO createCoINFO() {
        return new CoINFO();
    }

    /**
     * Create an instance of {@link BankINFO }
     *
     */
    public BankINFO createBankINFO() {
        return new BankINFO();
    }

    /**
     * Create an instance of {@link PartnerRequest }
     *
     */
    public PartnerRequest createPartnerRequest() {
        return new PartnerRequest();
    }

    /**
     * Create an instance of {@link PartnerResponse }
     *
     */
    public PartnerResponse createPartnerResponse() {
        return new PartnerResponse();
    }

    /**
     * Create an instance of {@link MdmPARTNER }
     *
     */
    public MdmPARTNER createMdmPARTNER() {
        return new MdmPARTNER();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPartnerResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ibm.com/service/partner/", name = "getPartnerResponse")
    public JAXBElement<GetPartnerResponse> createGetPartnerResponse(GetPartnerResponse value) {
        return new JAXBElement<GetPartnerResponse>(_GetPartnerResponse_QNAME, GetPartnerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPartner }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ibm.com/service/partner/", name = "setPartner")
    public JAXBElement<SetPartner> createSetPartner(SetPartner value) {
        return new JAXBElement<SetPartner>(_SetPartner_QNAME, SetPartner.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPartner }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ibm.com/service/partner/", name = "getPartner")
    public JAXBElement<GetPartner> createGetPartner(GetPartner value) {
        return new JAXBElement<GetPartner>(_GetPartner_QNAME, GetPartner.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPartnerResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ibm.com/service/partner/", name = "setPartnerResponse")
    public JAXBElement<SetPartnerResponse> createSetPartnerResponse(SetPartnerResponse value) {
        return new JAXBElement<SetPartnerResponse>(_SetPartnerResponse_QNAME, SetPartnerResponse.class, null, value);
    }

}
