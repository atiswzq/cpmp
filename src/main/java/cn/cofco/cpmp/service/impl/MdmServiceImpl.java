package cn.cofco.cpmp.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.SplrBnkAcntMapper;
import cn.cofco.cpmp.dao.SplrDvlpAplyMapper;
import cn.cofco.cpmp.dao.SplrMapper;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.entity.SplrBnkAcnt;
import cn.cofco.cpmp.entity.SplrDvlpAply;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.mdm.entity.MdmMateriel;
import cn.cofco.cpmp.service.IMdmService;
import cn.cofco.cpmp.support.OutputDtoUtil;

import cn.cofco.cpmp.utils.BeanUtils;
import cn.cofco.cpmp.utils.ContextTools;
import cn.cofco.cpmp.utils.CurrentUserInfo;
import cn.cofco.cpmp.utils.PageUtils;
import cn.cofco.cpmp.wsclient.splr.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.ws.BindingProvider;

/**
 * 采购平台接收MDM数据实现 Created by xsmiler on 2017/6/10.
 */
@Service
@Transactional("transactionManager")
public class MdmServiceImpl implements IMdmService {

	private Logger logger = LoggerManager.getSplrLog();

	@Resource
	private SplrMapper splrMapper;

	@Resource
	private SplrBnkAcntMapper splrBnkAcntMapper;

	@Resource
	private SplrDvlpAplyMapper splrDvlpAplyMapper;

	private String checkAuth() {
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		Integer userId = userinfo.getUserid();

		if (userId == null || userId == 0) {
			return "鉴权失败 - userId为空";
		}

		return "";
	}

	@Override
	public boolean splrQuery(Long id) throws Exception {

		SICUSSUPPLIERACCESSOUTService service = new SICUSSUPPLIERACCESSOUTService();
		SICUSSUPPLIERACCESSOUT sicussupplieraccessout = service.getHTTPPort();

		BindingProvider bp = (BindingProvider) sicussupplieraccessout;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, Constants.MDM_USER);
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, Constants.MDM_USER_PASSWD);

		ObjectFactory objectFactory = new ObjectFactory();
		GetPartner getPartner = objectFactory.createGetPartner();
		PartnerRequest partnerRequest = objectFactory.createPartnerRequest();

		Splr splr = splrMapper.selectByPrimaryKey(id);

		// 客商编码
		partnerRequest.setPARTNERNUMBER(splr.getPtnrCod());
		// 集团账户组代码
		partnerRequest.setACCOUNTGROUP(splr.getAcntGrup());
		// 客商类别代码
		partnerRequest.setBPCLASS(splr.getPtnrTyp());
		// 客商名称
		partnerRequest.setFULLNAME(splr.getFullNam());
		// 税务登记号
		partnerRequest.setTAXNUMBER(splr.getTaxCod());
		// 营业执照号
		partnerRequest.setBUSINESSLICENSE(splr.getDbusiLice());
		// 组织机构代码
		partnerRequest.setORGANIZATIONCODE(splr.getOrgCod());
		// 是否完成三证合一
		partnerRequest.setHASCREDITCODE(splr.getHasCreditCode());
		// 社会统一信用代码
		partnerRequest.setCREDITCODE(splr.getCrdtCod());
		// 所属专业公司
		partnerRequest.setCOMPANY(splr.getParentCpn());

		getPartner.setInput(partnerRequest);
		GetPartnerResponse getPartnerResponse = sicussupplieraccessout.cusSupplierQuery(getPartner);

		logger.info("客商查询返回结果：{}", getPartnerResponse.getReturn().toString());
		if (getPartnerResponse.getReturn().getISEXIST().equals("0")) { // 0-不存在；1-存在
			return false;
		} else if (getPartnerResponse.getReturn().getISEXIST().equals("1")) {

			// TODO 更新供应商信息到库
			logger.info("更新客商数据..");
			Splr splr1 = new Splr();
			splr1.setSplrId(id);

			splr1 = copy2Splr(splr1, getPartnerResponse.getReturn());
			// 置为合格供应商
			splr1.setSplrSts(Constants.SPLR_STS_QUALIFIED);
			// 全覆盖更新
			splrMapper.updateByPrimaryKey(splr1);
			logger.info("供应商数据更新成功！");

			// 判断是否存在1005的专业公司
			logger.info("判断存在的供应商是否有1005的专业公司");
			boolean isCompanyExist = false;
			for (CoINFO coINFO : getPartnerResponse.getReturn().getCompanys()) {
				if (coINFO.getCOCOMPANY().equals("1005")) {
					isCompanyExist = true;
				}
			}
			logger.info("判断存在的供应商是否有1005的专业公司,结果(存在)：{}", isCompanyExist);
			return isCompanyExist;
		}
		return false;
	}

	private Splr copy2Splr(Splr splr, MdmPARTNER mdmPartner) {
		splr.setPtnrCod(mdmPartner.getPARTNERNUMBER());
		splr.setAcntGrup(mdmPartner.getACCOUNTGROUP());
		splr.setFullNam(mdmPartner.getFULLNAME());
		splr.setShortNam(mdmPartner.getSHORTNAME());
		splr.setRgstAddr(mdmPartner.getADDRESS());
		splr.setPostCod(mdmPartner.getPOSTCODE());
		splr.setCountry(mdmPartner.getCOUNTRY());
		splr.setRegion(mdmPartner.getREGION());
		splr.setCity(mdmPartner.getCITY());
		splr.setDistrict(mdmPartner.getDISTRICT());
		splr.setTelephone(mdmPartner.getTELEPHONE());
		splr.setTelEx(mdmPartner.getTELEX());
		splr.setMob(mdmPartner.getMOBILEPHONE());
		splr.setFax(mdmPartner.getFAX());
		splr.setFaxEx(mdmPartner.getFAXEX());
		splr.setEmail(mdmPartner.getEMAIL());
		splr.setPtnrTyp(mdmPartner.getBPCLASS());
		splr.setHasCreditCode(mdmPartner.getHASCREDITCODE());
		splr.setTaxCod(mdmPartner.getTAXNUMBER());
		splr.setDbusiLice(mdmPartner.getBUSINESSLICENSE());
		splr.setOrgCod(mdmPartner.getORGANIZATIONCODE());
		splr.setCrdtCod(mdmPartner.getCREDITCODE());
		splr.setIdst1(mdmPartner.getINDUSTRY1());
		splr.setIdst2(mdmPartner.getINDUSTRY2());
		splr.setIdst(mdmPartner.getINDUSTRY());
		splr.setOprRng(mdmPartner.getBUSINESSSCOPE());
		splr.setRegCptl(mdmPartner.getREGISTRATIONCAPITAL());
//		splr.setRegTim(mdmPartner.getRegistration_date());
		splr.setCpnNtr(mdmPartner.getENTERPRISENATURE());
		splr.setLglUser(mdmPartner.getLEGALREPRESENTATIVE());
		splr.setLglIdntCad(mdmPartner.getLEGALREPRESENTATIVEID());
		splr.setTrdePtnr(mdmPartner.getTRADINGPARTNER());
		splr.setIsKeyAcnt(mdmPartner.getISKEYACCOUNT());
		splr.setSuprGrup(mdmPartner.getSUPERIORGROUP());
		splr.setParentCpn(mdmPartner.getPARENTCOMPANY());
		splr.setCtrlUsr(mdmPartner.getCONTROLLER());
		splr.setOldCod(mdmPartner.getOLDNUMBER());
		splr.setClassification(mdmPartner.getCLASSIFICATION());
//		splr.setSplrSts();

		return splr;
	}

	@Override
	public OutputDto splrAply(Long id, boolean checkAuth) throws Exception {

		CurrentUserInfo userinfo = null;
		if (checkAuth) {
			if (!"".equals(checkAuth())) {
				return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
			}
			userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		}

		SICUSSUPPLIERACCESSOUTService service = new SICUSSUPPLIERACCESSOUTService();
		SICUSSUPPLIERACCESSOUT sicussupplieraccessout = service.getHTTPPort();

		BindingProvider bp = (BindingProvider) sicussupplieraccessout;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, Constants.MDM_USER);
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, Constants.MDM_USER_PASSWD);

		ObjectFactory objectFactory = new ObjectFactory();
		SetPartner setPartner = objectFactory.createSetPartner();

		Splr splr = splrMapper.selectByPrimaryKey(id);

		MdmPARTNER mdmPARTNER = objectFactory.createMdmPARTNER();
		List<MdmPARTNER> input = new ArrayList<>();
		mdmPARTNER.setPARTNERID(String.valueOf("YZCG"+splr.getSplrId()));
		mdmPARTNER.setPARTNERNUMBER(splr.getPtnrCod());
		mdmPARTNER.setACCOUNTGROUP(splr.getAcntGrup());
		mdmPARTNER.setBPCLASS(splr.getPtnrTyp());
		mdmPARTNER.setFULLNAME(splr.getFullNam());
		mdmPARTNER.setSECNAME(splr.getSecName());
		mdmPARTNER.setSHORTNAME(splr.getShortNam());
		mdmPARTNER.setADDRESS(splr.getRgstAddr());
		mdmPARTNER.setPOSTCODE(splr.getPostCod());
		mdmPARTNER.setCOUNTRY(splr.getCountry());
		mdmPARTNER.setREGION(splr.getRegion());
		mdmPARTNER.setCITY(splr.getCity());
		mdmPARTNER.setDISTRICT(splr.getDistrict());
		mdmPARTNER.setTELEPHONE(splr.getTelephone());
		mdmPARTNER.setTELEX(splr.getTelEx());
		mdmPARTNER.setMOBILEPHONE(splr.getMob());
		mdmPARTNER.setFAX(splr.getFax());
		mdmPARTNER.setFAXEX(splr.getFaxEx());
		mdmPARTNER.setEMAIL(splr.getEmail());
		mdmPARTNER.setBPCLASS(splr.getPtnrTyp());
		mdmPARTNER.setHASCREDITCODE(splr.getHasCreditCode());
		mdmPARTNER.setTAXNUMBER(splr.getTaxCod());
		mdmPARTNER.setBUSINESSLICENSE(splr.getDbusiLice());
		mdmPARTNER.setORGANIZATIONCODE(splr.getOrgCod());
		mdmPARTNER.setCREDITCODE(splr.getCrdtCod());
		mdmPARTNER.setINDUSTRY1(splr.getIdst1());
		mdmPARTNER.setINDUSTRY2(splr.getIdst2());
		mdmPARTNER.setINDUSTRY(splr.getIdst());
		mdmPARTNER.setBUSINESSSCOPE(splr.getOprRng());
		mdmPARTNER.setREGISTRATIONCAPITAL(splr.getRegCptl());
		mdmPARTNER.setENTERPRISENATURE(splr.getCpnNtr());
		mdmPARTNER.setLEGALREPRESENTATIVE(splr.getLglUser());
		mdmPARTNER.setLEGALREPRESENTATIVEID(splr.getLglIdntCad());
		mdmPARTNER.setTRADINGPARTNER(splr.getTrdePtnr());
		mdmPARTNER.setISKEYACCOUNT(splr.getIsKeyAcnt());
//		mdmPARTNER.setSUPERIORGROUP(splr.getSuprGrup());
//		mdmPARTNER.setPARENTCOMPANY(splr.getParentCpn());
		mdmPARTNER.setCONTROLLER(splr.getCtrlUsr());
		mdmPARTNER.setOLDNUMBER(splr.getOldCod());
//		mdmPARTNER.setCLASSIFICATION(splr.getClassification());
		mdmPARTNER.setPARTNERTYPE("20");
		if (Constants.DEL_FLG_DEL.equals(splr.getDelFlg())) {
			mdmPARTNER.setISDELETE("Y");
		}
		if (Constants.DEL_FLG_NODEL.equals(splr.getDelFlg())) {
			mdmPARTNER.setISDELETE("N");
		}


		List<BankINFO> banks = new ArrayList<>();

		Map map = PageUtils.getQueryCondsMap(null, 1, 1000);
		map.put("splrId", id);
		// 查询供应商银行账号信息
		List<SplrBnkAcnt> splrBnkAcnts = splrBnkAcntMapper.selectByBnkConditions(map);
		for (SplrBnkAcnt splrBnkAcnt : splrBnkAcnts) {
			BankINFO bankINFO = objectFactory.createBankINFO();
			bankINFO.setACCOUNTHOLDER(splrBnkAcnt.getAcntNam());
			bankINFO.setBANKACCOUNT(splrBnkAcnt.getBnkAcnt());
			bankINFO.setBANKCODE(splrBnkAcnt.getBnkCod());
			bankINFO.setBANKACCOUNT(splrBnkAcnt.getBnkAcnt());
			bankINFO.setBANKCOUNTRY(splrBnkAcnt.getBnkCnty());
			bankINFO.setBANKNAME(splrBnkAcnt.getBnkNam());
			bankINFO.setDEFAULT(splrBnkAcnt.getDft());
			bankINFO.setSWIFTCODE(splrBnkAcnt.getSwiftCod());
			banks.add(bankINFO);
		}

		mdmPARTNER.setBanks(banks);

		List<CoINFO> companys = new ArrayList<>();
		CoINFO coINFO = objectFactory.createCoINFO();
		coINFO.setCOCOMPANY("1005");

		companys.add(coINFO);
		mdmPARTNER.setCompanys(companys);

		input.add(mdmPARTNER);

		String aplyUuid = UUID.randomUUID().toString();

		setPartner.setInput(input);
		setPartner.setUuid(aplyUuid);
		setPartner.setSysid("YZCG");

		SplrDvlpAply splrDvlpAply = new SplrDvlpAply();
		BeanUtils.copyProperties(splrDvlpAply, splr);
		splrDvlpAply.setMdmAplyUuid(aplyUuid);
		splrDvlpAply.setAplyTim(new Timestamp(System.currentTimeMillis()));
		if (null != userinfo) {
			splrDvlpAply.setAplyUsr(userinfo.getUsername());
		}

		splrDvlpAplyMapper.insert(splrDvlpAply);

		logger.info("发起MDM申请，报文：{}", setPartner.toString());
		SetPartnerResponse setPartnerResponse = sicussupplieraccessout.cusSupplierApply(setPartner);
		logger.info("发起MDM申请响应，报文：{}", setPartnerResponse.toString());
		List<PartnerResponse> resReturn = setPartnerResponse.getReturn();
		PartnerResponse partnerResponse = resReturn.get(0);
		if (partnerResponse.getFLAG().equals("1")) {

			logger.info("主数据申请成功，返回信息: {}", resReturn.toString());

			Splr splr1 = new Splr();
			splr1.setSplrId(id);
			splr1.setSplrSts(Constants.SPLR_STS_MDM_APLY);

			splrMapper.updateByPrimaryKeySelective(splr1);

			OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR);
			return outputDto;
		} else {
			logger.info("主数据申请失败，返回信息: {}", resReturn.toString());
			OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.SYS_ERR, partnerResponse.getMESSAGE());
			return outputDto;
		}
	}

	@Override
	public boolean splrAply(final List<Long> ids) throws Exception {

		new Thread(new Runnable() {
			@Override
			public void run() {
				// 便利供应商列表
				for (Long id : ids) {

					Splr splr = splrMapper.selectByPrimaryKey(id);
					if (null != splr && Constants.SPLR_ADMT_APLY_STS_BACK.equals(splr.getSplrSts())) {
						continue;
					}

					try {
						// 查询供应商是否存在
						boolean isExist = splrQuery(id);
						if (!isExist) {
							splrAply(id, false);
						}
					} catch (Exception e) {
						logger.error("发起mdm申请失败", e);
						Splr splr1 = new Splr();
						splr1.setSplrId(id);
						splr1.setSplrSts(Constants.SPLR_ADMT_APLY_STS_CHK);
						splrMapper.updateByPrimaryKeySelective(splr1);
					}
				}
			}
		}).start();
		return true;
	}
}
