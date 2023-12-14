package cn.cofco.cpmp.wsclient.matfbk;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-06-15T13:13:24.291+08:00
 * Generated source version: 3.1.11
 * 
 */
@WebServiceClient(name = "SI_MaterialStatus_OutService", 
                  wsdlLocation = "classpath:wsdl/matfbk.wsdl",
                  targetNamespace = "http://www.cofco.com/segment/material") 
public class SIMaterialStatusOutService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.cofco.com/segment/material", "SI_MaterialStatus_OutService");
    public final static QName HTTPSPort = new QName("http://www.cofco.com/segment/material", "HTTPS_Port");
    public final static QName HTTPPort = new QName("http://www.cofco.com/segment/material", "HTTP_Port");
    static {
        URL url = null;
        try {
            url = new URL("classpath:wsdl/matfbk.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SIMaterialStatusOutService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/matfbk.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SIMaterialStatusOutService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SIMaterialStatusOutService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SIMaterialStatusOutService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public SIMaterialStatusOutService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SIMaterialStatusOutService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SIMaterialStatusOutService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns SIMaterialStatusOut
     */
    @WebEndpoint(name = "HTTPS_Port")
    public SIMaterialStatusOut getHTTPSPort() {
        return super.getPort(HTTPSPort, SIMaterialStatusOut.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SIMaterialStatusOut
     */
    @WebEndpoint(name = "HTTPS_Port")
    public SIMaterialStatusOut getHTTPSPort(WebServiceFeature... features) {
        return super.getPort(HTTPSPort, SIMaterialStatusOut.class, features);
    }


    /**
     *
     * @return
     *     returns SIMaterialStatusOut
     */
    @WebEndpoint(name = "HTTP_Port")
    public SIMaterialStatusOut getHTTPPort() {
        return super.getPort(HTTPPort, SIMaterialStatusOut.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SIMaterialStatusOut
     */
    @WebEndpoint(name = "HTTP_Port")
    public SIMaterialStatusOut getHTTPPort(WebServiceFeature... features) {
        return super.getPort(HTTPPort, SIMaterialStatusOut.class, features);
    }

}
