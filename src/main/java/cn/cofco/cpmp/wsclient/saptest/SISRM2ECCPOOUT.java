package cn.cofco.cpmp.wsclient.saptest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-06-12T22:37:37.086+08:00
 * Generated source version: 3.1.11
 * 
 */
@WebService(targetNamespace = "jt:bpm:demo", name = "SI_SRM2ECC_PO_OUT")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SISRM2ECCPOOUT {

    @WebMethod(operationName = "SI_SRM2ECC_PO_OUT", action = "http://sap.com/xi/WebService/soap1.1")
    @WebResult(name = "MT_SRM2ECC_PO_RESP", targetNamespace = "jt:bpm:demo", partName = "MT_SRM2ECC_PO_RESP")
    public DTSRM2ECCPORESP siSRM2ECCPOOUT(
            @WebParam(partName = "MT_SRM2ECC_PO_REQ", name = "MT_SRM2ECC_PO_REQ", targetNamespace = "jt:bpm:demo")
                    DTSRM2ECCPOREQ mtSRM2ECCPOREQ
    );
}
