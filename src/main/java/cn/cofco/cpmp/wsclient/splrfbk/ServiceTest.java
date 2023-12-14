package cn.cofco.cpmp.wsclient.splrfbk;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;


public class ServiceTest {
	
	public static void main(String[] args) {
		
		SICUSSUPPLIERSTATUSOUTService service = new SICUSSUPPLIERSTATUSOUTService();
		SICUSSUPPLIERSTATUSOUT sicussupplierstatusout = service.getHTTPPort();
		
		BindingProvider bp = (BindingProvider) sicussupplierstatusout;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,"appuser5");
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "a123456");
        
        ObjectFactory objectFactory = new ObjectFactory();
        DTCUSSUPPLIERSTATUS dtcussupplierstatus = objectFactory.createDTCUSSUPPLIERSTATUS();
        List<DTCUSSUPPLIERSTATUS.FEEDBACK> feedbacks = new ArrayList<DTCUSSUPPLIERSTATUS.FEEDBACK>();
        DTCUSSUPPLIERSTATUS.FEEDBACK feedback = objectFactory.createDTCUSSUPPLIERSTATUSFEEDBACK();
        feedback.setPARTNERNUMBER("jfkdlsjf");
    	feedback.setSYNSTATUS("41");
    	feedback.setFEEDBACKMSG("success");
    	feedback.setTARGETSYSTEM("cg");
        feedbacks.add(feedback);
        dtcussupplierstatus.feedback = feedbacks;
        
        sicussupplierstatusout.siCUSSUPPLIERSTATUSOUT(dtcussupplierstatus);
	}
}
