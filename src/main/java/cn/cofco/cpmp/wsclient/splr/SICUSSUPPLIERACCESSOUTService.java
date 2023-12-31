package cn.cofco.cpmp.wsclient.splr;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-08-30T22:13:30.965+08:00
 * Generated source version: 3.1.11
 *
 */
@WebServiceClient(name = "SI_CUSSUPPLIER_ACCESS_OUTService",
        wsdlLocation = "classpath:wsdl/splr.wsdl",
        targetNamespace = "http://www.cofco.com/bpm/customer_supplier")
public class SICUSSUPPLIERACCESSOUTService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.cofco.com/bpm/customer_supplier", "SI_CUSSUPPLIER_ACCESS_OUTService");
    public final static QName HTTPSPort = new QName("http://www.cofco.com/bpm/customer_supplier", "HTTPS_Port");
    public final static QName HTTPPort = new QName("http://www.cofco.com/bpm/customer_supplier", "HTTP_Port");
    static {
        URL url = null;
        try {
            url = new URL("classpath:wsdl/splr.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SICUSSUPPLIERACCESSOUTService.class.getName())
                    .log(java.util.logging.Level.INFO,
                            "Can not initialize the default wsdl from {0}", "classpath:wsdl/splr.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SICUSSUPPLIERACCESSOUTService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SICUSSUPPLIERACCESSOUTService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SICUSSUPPLIERACCESSOUTService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public SICUSSUPPLIERACCESSOUTService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SICUSSUPPLIERACCESSOUTService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SICUSSUPPLIERACCESSOUTService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns SICUSSUPPLIERACCESSOUT
     */
    @WebEndpoint(name = "HTTPS_Port")
    public SICUSSUPPLIERACCESSOUT getHTTPSPort() {
        return super.getPort(HTTPSPort, SICUSSUPPLIERACCESSOUT.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SICUSSUPPLIERACCESSOUT
     */
    @WebEndpoint(name = "HTTPS_Port")
    public SICUSSUPPLIERACCESSOUT getHTTPSPort(WebServiceFeature... features) {
        return super.getPort(HTTPSPort, SICUSSUPPLIERACCESSOUT.class, features);
    }


    /**
     *
     * @return
     *     returns SICUSSUPPLIERACCESSOUT
     */
    @WebEndpoint(name = "HTTP_Port")
    public SICUSSUPPLIERACCESSOUT getHTTPPort() {
        return super.getPort(HTTPPort, SICUSSUPPLIERACCESSOUT.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SICUSSUPPLIERACCESSOUT
     */
    @WebEndpoint(name = "HTTP_Port")
    public SICUSSUPPLIERACCESSOUT getHTTPPort(WebServiceFeature... features) {
        return super.getPort(HTTPPort, SICUSSUPPLIERACCESSOUT.class, features);
    }

}
