package cn.cofco.cpmp.mdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.xml.ws.BindingProvider;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.holder.RegionHolder;
import cn.cofco.cpmp.mdm.entity.*;
import cn.cofco.cpmp.utils.DateUtils;
import org.slf4j.Logger;

import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.wsclient.matfbk.DTMaterialStatus;
import cn.cofco.cpmp.wsclient.matfbk.SIMaterialStatusOut;
import cn.cofco.cpmp.wsclient.matfbk.SIMaterialStatusOutService;
import cn.cofco.cpmp.wsclient.splrfbk.DTCUSSUPPLIERSTATUS;
import cn.cofco.cpmp.wsclient.splrfbk.ObjectFactory;
import cn.cofco.cpmp.wsclient.splrfbk.SICUSSUPPLIERSTATUSOUT;
import cn.cofco.cpmp.wsclient.splrfbk.SICUSSUPPLIERSTATUSOUTService;

public class MdmInterfaceImpl implements MdmInterface {

	private Logger logger = LoggerManager.getSplrSelfMngLog();
	
	@Resource
	private SplrMapper splrMapper;
	
	@Resource
	private SplrBnkAcntMapper splrBnkAcntMapper;
	
	@Resource
	private MaterielMapper materielMapper;

	@Resource
	private SplrDvlpAplyMapper splrDvlpAplyMapper;

	@Resource
	private SplrAcntMapper splrAcntMapper;
	
	/**
	 * 接收物料信息发布接口
	 * @param mdmMat
	 */
	@Override
	public void mats(MdmMat mdmMat) {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				logger.info("接收到物料发布信息:[{}]", mdmMat.toString());
				try {
					List<MdmMateriel> mdmMateriels = mdmMat.getMdm_materiels();
					List<DTMaterialStatus.FEEDBACK> feedbacks = new ArrayList<DTMaterialStatus.FEEDBACK>();
					cn.cofco.cpmp.wsclient.matfbk.ObjectFactory objectFactory = new cn.cofco.cpmp.wsclient.matfbk.ObjectFactory();
					for (MdmMateriel mdmMateriel : mdmMateriels) {
						DTMaterialStatus.FEEDBACK feedback = objectFactory.createDTMaterialStatusFEEDBACK();
						try {
							Materiel materiel = materielMapper.selectByMatcod(mdmMateriel.getMatcode());

							// 获取物料描述：优先获取中文，其次英文
							List<MaterielDesc> materielDescs = mdmMateriel.getMateriel_desc();
							String cDesc = null;
							String eDesc = null;
							for (MaterielDesc materielDesc : materielDescs) {
								if (materielDesc.getLangu().equalsIgnoreCase(Constants.LANGU_CHINESE)) {
									cDesc = materielDesc.getMatdesc();
								} else if (materielDesc.getLangu().equalsIgnoreCase(Constants.LANGU_ENGLISH)) {
									eDesc = materielDesc.getMatdesc();
								}
							}
//							materiel.setMatDesc(cDesc == null ? eDesc : cDesc);

							// 获取专业公司级物料分组
							List<CoInfo> coInfos = mdmMateriel.getCo_info();
							String coMatgroup = null;
							for (CoInfo coInfo: coInfos) {
								if ("1005".equals(coInfo.getCo_procomp())) {
									coMatgroup = coInfo.getCo_matgroup();
								}
							}

							// 新增
							if (materiel == null) {
								Materiel materiel2 = copy2Materiel(mdmMateriel);
								materiel2.setMatDesc(cDesc == null ? eDesc : cDesc);
								materiel2.setCoMatgroup(coMatgroup);
								int rowNum = materielMapper.insertSelective(materiel2);
							}
							// 修改
							if (materiel != null) {
								Materiel materiel2 = copy2Materiel(materiel, mdmMateriel);
								materiel2.setMatDesc(cDesc == null ? eDesc : cDesc);
								materiel2.setCoMatgroup(coMatgroup);
								int rowNum = materielMapper.updateByPrimaryKey(materiel2);
							}

							feedback.setFEEDBACKMSG("mat data receive success!");
							feedback.setMATCODE(mdmMateriel.getMatcode());
							feedback.setSYNSTATUS("41");
							feedback.setTARGETSYSTEM("YZCG");
						} catch(Exception e) {
							e.printStackTrace();
							logger.error("采购平台接收物料数据发布异常：[{}]", e.getMessage());

							feedback.setFEEDBACKMSG("mat data receive failed!");
							feedback.setMATCODE(mdmMateriel.getMatcode());
							feedback.setSYNSTATUS("42");
							feedback.setTARGETSYSTEM("YZCG");
						}
						feedbacks.add(feedback);
					}
					
					// mdm 回调
					SIMaterialStatusOutService service = new SIMaterialStatusOutService();
					SIMaterialStatusOut siMaterialStatusOut = service.getHTTPPort();
					
					BindingProvider bp = (BindingProvider) siMaterialStatusOut;
					bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,"appuser5");
					bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "a123456");
					
					DTMaterialStatus dtMaterialStatus = objectFactory.createDTMaterialStatus();
					dtMaterialStatus.feedback = feedbacks;
					siMaterialStatusOut.siMaterialStatusOut(dtMaterialStatus);
				} catch(Exception e) {
					logger.error("采购平台接收物料数据发布异常：[{}]", e.getMessage());
				}
			}
			
		}).start();
	}

	/**
	 * 接收客商信息发布接口
	 * @param mdmSplr
	 */
	@Override
	public void splrs(MdmSplr mdmSplr) {
		// 新开一个线程执行业务逻辑
		new Thread(new Runnable() {

			@Override
			public void run() {

				logger.info("接收到客商发布信息:[{}]", mdmSplr.toString());
				try {
					List<MdmPartner> mdmPartners = mdmSplr.getMdmPartners();
					List<DTCUSSUPPLIERSTATUS.FEEDBACK> feedbacks = new ArrayList<DTCUSSUPPLIERSTATUS.FEEDBACK>();
					ObjectFactory objectFactory = new ObjectFactory();

					for (MdmPartner mdmPartner : mdmPartners) {
						DTCUSSUPPLIERSTATUS.FEEDBACK feedback = objectFactory.createDTCUSSUPPLIERSTATUSFEEDBACK();

						if (!(Constants.CO_PARTNER_TYPE_SPLR.equals(mdmPartner.getCo_partner_type()) || Constants.CO_PARTNER_TYPE_PARTNER.equals(mdmPartner.getCo_partner_type()))) {
							feedback.setPARTNERNUMBER(mdmPartner.getPartner_number());
							feedback.setSYNSTATUS("41");
							feedback.setTARGETSYSTEM("YZCG");
							feedback.setFEEDBACKMSG("receive success - 该数据不是客商或者供应商数据，采购平台不做保存！");
							feedbacks.add(feedback);
							continue;
						}

						try {

							// 处理partnerId

							String partnerId = "0";
							if (null != mdmPartner.getPartner_id() && "null" != mdmPartner.getPartner_id() && mdmPartner.getPartner_id().indexOf("YZCG") != -1) {
								partnerId = mdmPartner.getPartner_id().substring(4);
							}
							Splr splrForPartnerId = splrMapper.selectByPrimaryKey(Long.valueOf(partnerId));

							String partnerCod = null;
							if (null != mdmPartner.getPartner_number()) {
								partnerCod = mdmPartner.getPartner_number();
							}
							List<Splr> temSplr = null;
							Map map = new ConcurrentHashMap();
							map.put("start", 1);
							map.put("limit", 1000);
							map.put("ptnrCod", partnerCod);
							temSplr = splrMapper.selectByConditions(map);

							// 判断该客商数据是否存在采购平台
							if (null == splrForPartnerId && (temSplr == null || temSplr.size() == 0)) {
								Splr splr = copy2Splr(mdmPartner);
								// 推送过来的数据默认为05状态
								splr.setSplrSts(Constants.SPLR_STS_QUALIFIED);

								int rowNum = splrMapper.insertSelective(splr);
								Long splrId = splr.getSplrId();
								List<MdmBnkInfo> mdmBnkInfos = mdmPartner.getBank_info();
								List<SplrBnkAcnt> splrBnkAcnts = new ArrayList<SplrBnkAcnt>();
								if(mdmBnkInfos != null && mdmBnkInfos.size() > 0) {

									for (MdmBnkInfo mdmBnkInfo : mdmBnkInfos) {
										SplrBnkAcnt splrBnkAcnt = copy2SplrBnk(mdmBnkInfo);
										splrBnkAcnt.setSplrId(splrId);
										splrBnkAcnts.add(splrBnkAcnt);
									}
									// 批量插入银行账户
									splrBnkAcntMapper.inserts(splrBnkAcnts);
								}
							} else if (null != splrForPartnerId) {
								Splr splr = copy2Splr(splrForPartnerId, mdmPartner);
								// 推送过来的数据默认为05状态
								splr.setSplrSts(Constants.SPLR_STS_QUALIFIED);

								splrMapper.updateByPrimaryKey(splr);
								List<MdmBnkInfo> mdmBnkInfos = mdmPartner.getBank_info();
								// 删除以前的银行账号
								splrBnkAcntMapper.deleteBySplrId(splr.getSplrId());
								List<SplrBnkAcnt> splrBnkAcnts = new ArrayList<SplrBnkAcnt>();
								if(mdmBnkInfos != null && mdmBnkInfos.size() > 0) {

									for (MdmBnkInfo mdmBnkInfo : mdmBnkInfos) {
										SplrBnkAcnt splrBnkAcnt = copy2SplrBnk(mdmBnkInfo);
										splrBnkAcnt.setSplrId(splr.getSplrId());
										splrBnkAcnts.add(splrBnkAcnt);
									}
									// 批量插入银行账户
									splrBnkAcntMapper.inserts(splrBnkAcnts);
								}
							} else {
								Splr splr = copy2Splr(temSplr.get(0), mdmPartner);
								// 推送过来的数据默认为05状态
								splr.setSplrSts(Constants.SPLR_STS_QUALIFIED);

								splrMapper.updateByPrimaryKey(splr);
								List<MdmBnkInfo> mdmBnkInfos = mdmPartner.getBank_info();
								// 删除以前的银行账号
								splrBnkAcntMapper.deleteBySplrId(splr.getSplrId());
								List<SplrBnkAcnt> splrBnkAcnts = new ArrayList<SplrBnkAcnt>();
								if(mdmBnkInfos != null && mdmBnkInfos.size() > 0) {

									for (MdmBnkInfo mdmBnkInfo : mdmBnkInfos) {
										SplrBnkAcnt splrBnkAcnt = copy2SplrBnk(mdmBnkInfo);
										splrBnkAcnt.setSplrId(splr.getSplrId());
										splrBnkAcnts.add(splrBnkAcnt);
									}
									// 批量插入银行账户
									splrBnkAcntMapper.inserts(splrBnkAcnts);
								}
							}
							feedback.setPARTNERNUMBER(mdmPartner.getPartner_number());
							feedback.setSYNSTATUS("41");
							feedback.setTARGETSYSTEM("YZCG");
							feedback.setFEEDBACKMSG("receive success!");
						} catch(Exception e) {
							e.printStackTrace();
							logger.info("客商信息接收失败，客商编码：[{}],错误信息为：[{}]", mdmPartner.getPartner_number(), e.getMessage());
							feedback.setPARTNERNUMBER(mdmPartner.getPartner_number());
							feedback.setSYNSTATUS("42");
							feedback.setTARGETSYSTEM("YZCG");
							feedback.setFEEDBACKMSG("receive failure!" + e.getMessage());
						}
						feedbacks.add(feedback);
					}

					// 回调mdm接口
					try {
						SICUSSUPPLIERSTATUSOUTService service = new SICUSSUPPLIERSTATUSOUTService();
						SICUSSUPPLIERSTATUSOUT sicussupplierstatusout = service.getHTTPPort();

						BindingProvider bp = (BindingProvider) sicussupplierstatusout;
						bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,"appuser5");
						bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "a123456");

						DTCUSSUPPLIERSTATUS dtcussupplierstatus = objectFactory.createDTCUSSUPPLIERSTATUS();
						dtcussupplierstatus.feedback = feedbacks;
						sicussupplierstatusout.siCUSSUPPLIERSTATUSOUT(dtcussupplierstatus);
						logger.info("采购平台接收客商数据发布反馈成功：[{}]", dtcussupplierstatus.toString());
					} catch(Exception e) {
						logger.error("采购平台接收客商数据发布反馈异常：[{}]", e.getMessage());
					}
				} catch(Exception e) {
					logger.error("采购平台接收客商数据发布异常：[{}]", e.getMessage());
				}
			}
		}).start();
	}
	
	private Materiel copy2Materiel(MdmMateriel mdmMateriel) {
		
		Materiel materiel = new Materiel();
		return copy2Materiel(materiel, mdmMateriel);
	}
	
	private Materiel copy2Materiel(Materiel materiel, MdmMateriel mdmMateriel) {
		
		materiel.setMatCod(mdmMateriel.getMatcode());
		materiel.setMatInd(mdmMateriel.getMatind());
		materiel.setMatTyp(mdmMateriel.getMattype());
		materiel.setMatGrup(mdmMateriel.getMatgroup());
		materiel.setUnt(mdmMateriel.getUnit());
		materiel.setBrgew1(mdmMateriel.getBrgew1());
		materiel.setNtgew1(mdmMateriel.getNtgew1());
		materiel.setGewei1(mdmMateriel.getGewei1());
		materiel.setVolum1(mdmMateriel.getVolum1());
		materiel.setVoleh1(mdmMateriel.getVoleh1());
		materiel.setEantp1(mdmMateriel.getEantp1());
		materiel.setEan111(mdmMateriel.getEan111());
		materiel.setProdGrup(mdmMateriel.getProdgroup());
		materiel.setProcessid(mdmMateriel.getProcessid());
		materiel.setProcomp(mdmMateriel.getProcomp());
		materiel.setAplyUsr(mdmMateriel.getApplicant());
		materiel.setCrtTim(mdmMateriel.getCreatetime());
		materiel.setDelFlg(mdmMateriel.getLvoma());
		
		return materiel;
	}
	
	
	private Splr copy2Splr(MdmPartner mdmPartner) {
		Splr splr = new Splr();
		return copy2Splr(splr, mdmPartner);
	}

	private Splr copy2Splr(Splr splr, MdmPartner mdmPartner) {
		splr.setPtnrCod(mdmPartner.getPartner_number());
		splr.setAcntGrup(mdmPartner.getAccount_group());
		splr.setFullNam(mdmPartner.getFull_name());
		splr.setSecName(mdmPartner.getSec_name());
		splr.setShortNam(mdmPartner.getShort_name());
		splr.setRgstAddr(mdmPartner.getAddress());
		splr.setPostCod(mdmPartner.getPost_code());
		splr.setCountry(mdmPartner.getCountry());
		splr.setRegion(mdmPartner.getRegion_gb());
		splr.setCity(mdmPartner.getCity());
		splr.setDistrict(mdmPartner.getDistrict());
		splr.setTelephone(mdmPartner.getTelephone());
		splr.setTelEx(mdmPartner.getTel_ex());
		splr.setMob(mdmPartner.getMobile_phone());
		splr.setFax(mdmPartner.getFax());
		splr.setFaxEx(mdmPartner.getFax_ex());
		splr.setEmail(mdmPartner.getEmail());
		splr.setPtnrTyp(mdmPartner.getBp_class());
		splr.setHasCreditCode(mdmPartner.getHas_credit_code());
		splr.setTaxCod(mdmPartner.getTax_number());
		splr.setDbusiLice(mdmPartner.getBusiness_license());
		splr.setOrgCod(mdmPartner.getOrganization_code());
		splr.setCrdtCod(mdmPartner.getCredit_code());
		splr.setIdst1(mdmPartner.getIndustry1());
		splr.setIdst2(mdmPartner.getIndustry2());
		splr.setIdst(mdmPartner.getIndustry());
		splr.setOprRng(mdmPartner.getBusiness_scope());
		splr.setRegCptl(mdmPartner.getRegistration_capital());
		splr.setRegTim(DateUtils.string2timestamp(mdmPartner.getRegistration_date()));
		splr.setCpnNtr(mdmPartner.getEnterprise_nature());
		splr.setLglUser(mdmPartner.getLegal_representative());
		splr.setLglIdntCad(mdmPartner.getLegal_representative_id());
		splr.setTrdePtnr(mdmPartner.getTrading_partner());
		splr.setIsKeyAcnt(mdmPartner.getIs_key_account());
		splr.setSuprGrup(mdmPartner.getSuperior_group());
		splr.setParentCpn(mdmPartner.getParent_company());
		splr.setCtrlUsr(mdmPartner.getController());
		splr.setOldCod(mdmPartner.getOld_number());
		splr.setClassification(mdmPartner.getClassification());
		splr.setCoPartnerType(mdmPartner.getCo_partner_type());
//		splr.setSplrSts();
		if ("Y".equals(mdmPartner.getDelete())) {
			splr.setDelFlg(Constants.DEL_FLG_DEL);
		} else if("N".equals(mdmPartner.getDelete())) {
			splr.setDelFlg(Constants.DEL_FLG_NODEL);
		}


		
		return splr;
	}
	
	private SplrBnkAcnt copy2SplrBnk(MdmBnkInfo mdmBnkInfo) {
		SplrBnkAcnt splrBnkAcnt = new SplrBnkAcnt();
		
		return copy2SplrBnk(splrBnkAcnt, mdmBnkInfo);
	}
	
	private SplrBnkAcnt copy2SplrBnk(SplrBnkAcnt splrBnkAcnt, MdmBnkInfo mdmBnkInfo) {
		splrBnkAcnt.setBnkCnty(mdmBnkInfo.getBank_country());
		splrBnkAcnt.setBnkCod(mdmBnkInfo.getBank_code());
		splrBnkAcnt.setBnkNam(mdmBnkInfo.getBank_name());
		splrBnkAcnt.setBnkAcnt(mdmBnkInfo.getBank_account());
		splrBnkAcnt.setAcntNam(mdmBnkInfo.getAccount_holder());
		splrBnkAcnt.setSwiftCod(mdmBnkInfo.getSwift_code());
		return splrBnkAcnt;
	}

	@Override
	public MdmApvlRes apvl(MdmApvlReq mdmApvlReq) {
		
		logger.info("客商主数据审批状态通知, 唯一识别码：[{}], 客商唯一识别码：[{}]，审批状态[{}]，审批意见[{}]", mdmApvlReq.getUuid(), mdmApvlReq.getPartner_id(), mdmApvlReq.getApprove_status(), mdmApvlReq.getApprove_opinion());

		try {
			Map<String, String> map = new ConcurrentHashMap<>();
			map.put("mdmAplyUuid", mdmApvlReq.getUuid());
			List<SplrDvlpAply> splrDvlpAplies = splrDvlpAplyMapper.selectByCondition(map);
			SplrDvlpAply splrDvlpAply = splrDvlpAplies.get(0);

			SplrDvlpAply splrDvlpAply1 = new SplrDvlpAply();
			splrDvlpAply1.setAplyId(splrDvlpAply.getAplyId());
			splrDvlpAply1.setMdmAplyMsg(mdmApvlReq.getApprove_opinion());

			splrDvlpAplyMapper.updateByPrimaryKeySelective(splrDvlpAply1);

			if (mdmApvlReq.getApprove_status().equalsIgnoreCase("1")) {
				Splr splr = new Splr();
				splr.setSplrId(splrDvlpAply.getSplrId());
				splr.setSplrSts(Constants.SPLR_STS_QUALIFIED);
				splrMapper.updateByPrimaryKeySelective(splr);

				MdmApvlRes mdmApvlRes = new MdmApvlRes();
				mdmApvlRes.setStatus("1");
				mdmApvlRes.setMessage("客商主数据审批状态通知成功");
				return mdmApvlRes;
			} else {
				Splr splr = new Splr();
				splr.setSplrId(splrDvlpAply.getSplrId());
				splr.setSplrSts(Constants.SPLR_STS_MDM_FAIL);
				splrMapper.updateByPrimaryKeySelective(splr);

				MdmApvlRes mdmApvlRes = new MdmApvlRes();
				mdmApvlRes.setStatus("0");
				mdmApvlRes.setMessage("客商主数据审批状态通知失败");
				return mdmApvlRes;
			}
		} catch (Exception e) {
			MdmApvlRes mdmApvlRes = new MdmApvlRes();
			mdmApvlRes.setStatus("0");
			mdmApvlRes.setMessage(e.getMessage());
			return mdmApvlRes;
		}

	}
	
}
