package cn.cofco.cpmp.mdm;

import javax.xml.ws.BindingProvider;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.splr.vo.SplrVo;
import cn.cofco.cpmp.wsclient.splr.GetPartner;
import cn.cofco.cpmp.wsclient.splr.GetPartnerResponse;
import cn.cofco.cpmp.wsclient.splr.ObjectFactory;
import cn.cofco.cpmp.wsclient.splr.PartnerRequest;
import cn.cofco.cpmp.wsclient.splr.SICUSSUPPLIERACCESSOUT;
import cn.cofco.cpmp.wsclient.splr.SICUSSUPPLIERACCESSOUTService;

public class MdmService {
	
	public static void CusSupplierQuery(SplrVo splrVo) {
		
		SICUSSUPPLIERACCESSOUTService service = new SICUSSUPPLIERACCESSOUTService();
		SICUSSUPPLIERACCESSOUT sicussupplieraccessout = service.getHTTPPort();
		
		BindingProvider bp = (BindingProvider) sicussupplieraccessout;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, Constants.MDM_USER);
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, Constants.MDM_USER_PASSWD);
        
		ObjectFactory objectFactory = new ObjectFactory();
		GetPartner getPartner = objectFactory.createGetPartner();
		PartnerRequest partnerRequest = objectFactory.createPartnerRequest();
		
		// 客商编码
		partnerRequest.setPARTNERNUMBER(splrVo.getPtnrCod());
		// 集团账户组代码
		partnerRequest.setACCOUNTGROUP(splrVo.getAcntGrup());
		// 客商类别代码
		partnerRequest.setBPCLASS(splrVo.getPtnrTyp());
		// 客商名称
		partnerRequest.setFULLNAME(splrVo.getFullNam());
		// 税务登记号
		partnerRequest.setTAXNUMBER(splrVo.getTaxCod());
		// 营业执照号
		partnerRequest.setBUSINESSLICENSE(splrVo.getDbusiLice());
		// 组织机构代码
		partnerRequest.setORGANIZATIONCODE(splrVo.getOrgCod());
		// 是否完成三证合一
		partnerRequest.setHASCREDITCODE(splrVo.getHasCreditCode());
		// 社会统一信用代码
		partnerRequest.setCREDITCODE(splrVo.getCrdtCod());
		// 所属专业公司
		partnerRequest.setCOMPANY(splrVo.getParentCpn());
		
		
		
		getPartner.setInput(partnerRequest);
		
		GetPartnerResponse getPartnerResponse = sicussupplieraccessout.cusSupplierQuery(getPartner);
		
		System.out.println(getPartnerResponse);
	}
	
	public static void main(String[] args) {
		MdmService.CusSupplierQuery(new SplrVo());
	}
}
