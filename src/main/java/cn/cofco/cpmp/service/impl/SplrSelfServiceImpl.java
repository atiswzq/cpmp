package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.PagedResult;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.holder.SysParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrSelfService;
import cn.cofco.cpmp.splr.dto.SplrAptInfo;
import cn.cofco.cpmp.splr.vo.*;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional("transactionManager")
public class SplrSelfServiceImpl implements ISplrSelfService {

	private Logger logger = LoggerManager.getSplrSelfMngLog();

	@Resource
	private SplrAcntMapper splrAcntMapper;

	@Resource
	private SplrMapper splrMapper;

	@Resource
	private SplrBnkAcntMapper splrBnkAcntMapper;

	@Resource
	private SplrAptMapper splrAptMapper;

	@Resource
	private SplrRcmdOnsfMapper splrRcmdOnsfMapper;

	@Resource
	private SplrModAplyMapper splrModAplyMapper;

	@Resource
	private SplrChrmInfoMapper splrChrmInfoMapper;

	/**
	 * 供应商注册
	 * 
	 * @param quickRgstVo
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { java.lang.Throwable.class })
	public OutputDto quickRgstSplr(QuickRgstVo quickRgstVo) {

		Splr splr = new Splr();
		
		// 判断该用户名是否注册过
		Map<String, String> conditions = new ConcurrentHashMap<String, String>();
		conditions.put("usrNam", quickRgstVo.getUsrNam());
		List<SplrAcnt> splrAcnts = splrAcntMapper.selectAcntsByConditions(conditions);
		if (splrAcnts.size() != 0) {
			logger.info("供应商注册失败  - 已存在该用户名！");
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.FAIL_OPR, "已存在该用户名");
		}
		
		SplrAcnt splrAcnt = new SplrAcnt();
		// 处理供应商快速注册信息
		if (quickRgstVo.getCompangNam() != null) {
			splr.setFullNam(quickRgstVo.getCompangNam());
		}
		if (quickRgstVo.getEmail() != null) {
			splr.setEmail(quickRgstVo.getEmail());
		}
		if (null != quickRgstVo.getItdcCompy()) {
			splr.setItdcCompy(quickRgstVo.getItdcCompy());
		}
		splr.setCrtTim(DateUtils.getCurrentTimeStamp());
		// 设置供应商状态为 -- 注册
		splr.setSplrSts(Constants.SPLR_STS_REGISTER);
		logger.info("供应商用户注册信息：" + splr.toString());

		int rowNum1 = splrMapper.insert(splr);

		if (rowNum1 != 1) {
			logger.info("供应商注册失败  - 数据库异常！");
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.FAIL_OPR);
		}
		logger.info("供应商 id : " + splr.getSplrId());

		// 创建供应商基础账户
		splrAcnt.setSplrId(splr.getSplrId());
		String access_token = UUID.randomUUID().toString();
		splrAcnt.setAccessToken(access_token);
		splrAcnt.setAcntTyp(Constants.SPLR_ACNT_ADMIN);
		if (quickRgstVo.getUsrNam() != null) {
			splrAcnt.setUsrNam(quickRgstVo.getUsrNam());
		}
		if (quickRgstVo.getMim() != null) {
			splrAcnt.setPasswd(quickRgstVo.getMim());
		}
		if (quickRgstVo.getPhone() != null) {
			splrAcnt.setMob(quickRgstVo.getPhone());
		}
		splrAcnt.setDffFlg("0");
		splrAcnt.setCrtTim(DateUtils.getCurrentTimeStamp());
		int rowNum2 = splrAcntMapper.insert(splrAcnt);
		if (rowNum2 != 1) {
			logger.info("供应商注册失败  - 创建供应商管理员账户失败！");
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.FAIL_OPR);
		}

		// 更新token
		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setAcntId(splrAcnt.getAcntId());
		tokenInfo.setSplrId(splr.getSplrId());
		tokenInfo.setAccess_token(access_token);
		tokenInfo.setAcntTyp(Constants.SPLR_ACNT_ADMIN);
		if (quickRgstVo.getUsrNam() != null) {
			tokenInfo.setUsrNam(quickRgstVo.getUsrNam());
		}

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR,
				tokenInfo);
	}

	/**
	 * 供应商登录
	 * 
	 * @param splrLoginVo
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrLogin(SplrLoginVo splrLoginVo) throws Exception {

		SplrAcnt splrAcnt = splrAcntMapper.selectByLogin(
				splrLoginVo.getUsrNam(), splrLoginVo.getMim(),
				null);
		if (splrAcnt != null) {
			TokenInfo tokenInfo = new TokenInfo();
			if (splrAcnt.getAccessToken() == null) {
				String token = UUID.randomUUID().toString();
				splrAcnt.setAccessToken(token);
				SplrAcnt splrAcnt2 = new SplrAcnt();
				splrAcnt2.setAcntId(splrAcnt.getAcntId());
				splrAcnt2.setAccessToken(token);
				splrAcntMapper.updateByPrimaryKeySelective(splrAcnt2);
			}

			tokenInfo.setAccess_token(splrAcnt.getAccessToken());
			tokenInfo.setAcntId(splrAcnt.getAcntId());
			tokenInfo.setSplrId(splrAcnt.getSplrId());
			tokenInfo.setAcntTyp(splrAcnt.getAcntTyp());
			Splr splr = splrMapper.selectByPrimaryKey(splrAcnt.getSplrId());
			tokenInfo.setFullNam(splr.getFullNam());
			
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, tokenInfo);
		}
		return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
				RtnEnum.FAIL_OPR, "用户名或密码错误！");
	}

	/**
	 * 供应商信息上传
	 * 
	 * @param splrVo
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrInfo(SplrVo splrVo, String access_token)
			throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		
		if (currentSplrUserInfo != null) {

			Splr oldSplr = splrMapper.selectByPrimaryKey(currentSplrUserInfo
					.getSplrId());
			Splr splr = new Splr();
			// 供应商资质文件更新
			if (splrVo.getPtnrTyp() != null) {
				List<SplrAptInfo> splrAptInfos = splrAptMapper.splrAptList(currentSplrUserInfo
						.getSplrId(), splrVo.getClassification());
				Map<Long, SplrAptInfo> splrAptMap = new ConcurrentHashMap<Long, SplrAptInfo>();
				for (SplrAptInfo splrAptInfo : splrAptInfos) {
					splrAptMap.put(splrAptInfo.getSplrAptId(), splrAptInfo);
				}
				// 获取当前供应商的资质文件
				List<SplrApt> splrApts = splrAptMapper
						.selectAptDefByConditions(splrVo.getClassification());
				
				List<SplrApt> insertSplrApts = new ArrayList<SplrApt>();
				for (SplrApt splrApt : splrApts) {
					// 判断该资质文件是否存在
					if (splrAptMap.get(splrApt.getSplrAptId()) == null) {
						splrApt.setSplrId(currentSplrUserInfo.getSplrId());
						insertSplrApts.add(splrApt);
					}
				}
				// 批量插入资质文件定义记录
				if (insertSplrApts.size() > 0) {
					splrAptMapper.inserts(insertSplrApts);
				}
			}

			// 供应商信息保存
			BeanUtils.copyProperties(splr, splrVo);
			splr.setSplrId(currentSplrUserInfo.getSplrId());

			// 设置默认国家为中国
			if (null == splr.getCountry()) {
				splr.setCountry("CN");
			}
			int rowNum = splrMapper.updateByPrimaryKeySelective(splr);

			if (rowNum == 1) {

				if (needUpdateSts(oldSplr, splr)) {
					// 更新供应商状态为注册状态
					Splr splr1 = new Splr();
					splr1.setSplrId(currentSplrUserInfo.getSplrId());
					splr1.setSplrSts(Constants.SPLR_STS_REGISTER);
					splrMapper.updateByPrimaryKeySelective(splr1);
				}

				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
			}
		} else {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.LOGIN_ERR, RtnEnum.LOGIN_ERR.getDesc());
		}

		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	private boolean isValueSame(Object obj1, Object obj2) {
		if (null == obj1 && null == obj2) {
			return true;
		}
		if (null == obj1 ^ null == obj2) {
			return false;
		}
		if (obj1.equals(obj2)) {
			return true;
		}
		return false;
	}

	private boolean needUpdateSts(Splr oldSplr, Splr newSplr) {

		if (null == oldSplr) {
			return true;
		}
		if (!isValueSame(oldSplr.getTelephone(), newSplr.getTelephone())) {
			return true;
		}
		if (!isValueSame(oldSplr.getAcntGrup(), newSplr.getAcntGrup())) {
			return true;
		}
		if (!isValueSame(oldSplr.getFullNam(), newSplr.getFullNam())  ) {
			return true;
		}
		if (!isValueSame(oldSplr.getShortNam(), newSplr.getShortNam())) {
			return true;
		}
		if (!isValueSame(oldSplr.getRgstAddr(), newSplr.getRgstAddr())) {
			return true;
		}
		if (!isValueSame(oldSplr.getPtnrTyp(), newSplr.getPtnrTyp())) {
			return true;
		}
		if (!isValueSame(oldSplr.getHasCreditCode(), newSplr.getHasCreditCode())) {
			return true;
		}
		if (!isValueSame(oldSplr.getTaxCod(), newSplr.getTaxCod())) {
			return true;
		}
		if (!isValueSame(oldSplr.getDbusiLice(), newSplr.getDbusiLice())) {
			return true;
		}
		if (!isValueSame(oldSplr.getOrgCod(), newSplr.getOrgCod())) {
			return true;
		}
		if (!isValueSame(oldSplr.getCrdtCod(), newSplr.getCrdtCod())) {
			return true;
		}
		if (!isValueSame(oldSplr.getIdst1(), newSplr.getIdst1())) {
			return true;
		}
		if (!isValueSame(oldSplr.getIdst2(), newSplr.getIdst2())) {
			return true;
		}
		if (!isValueSame(oldSplr.getIdst(), newSplr.getIdst())) {
			return true;
		}
		if (!isValueSame(oldSplr.getOprRng(), newSplr.getOprRng())) {
			return true;
		}
		if (!isValueSame(oldSplr.getRegCptl(), newSplr.getRegCptl())) {
			return true;
		}
		if (!isValueSame(oldSplr.getRegTim(), newSplr.getRegTim())) {
			return true;
		}
		if (!isValueSame(oldSplr.getCpnNtr(), newSplr.getCpnNtr())) {
			return true;
		}
		if (!isValueSame(oldSplr.getLglUser(), newSplr.getLglUser())) {
			return true;
		}
		if (!isValueSame(oldSplr.getLglIdntCad(), newSplr.getLglIdntCad())) {
			return true;
		}
		if (!isValueSame(oldSplr.getTrdePtnr(), newSplr.getTrdePtnr())) {
			return true;
		}
		if (!isValueSame(oldSplr.getSuprGrup(), newSplr.getSuprGrup())) {
			return true;
		}
		if (!isValueSame(oldSplr.getParentCpn(), newSplr.getParentCpn())) {
			return true;
		}
		if (!isValueSame(oldSplr.getCtrlUsr(), newSplr.getCtrlUsr())) {
			return true;
		}
		if (!isValueSame(oldSplr.getOldCod(), newSplr.getOldCod())) {
			return true;
		}
		if (!isValueSame(oldSplr.getClassification(), newSplr.getClassification())) {
			return true;
		}
		if (!isValueSame(oldSplr.getProdList(), newSplr.getProdList())) {
			return true;
		}
		if (!isValueSame(oldSplr.getCoPartnerType(), newSplr.getCoPartnerType())) {
			return true;
		}
		if (!isValueSame(oldSplr.getItdcCompy(), newSplr.getItdcCompy())) {
			return true;
		}
		return false;
	}

	/**
	 * 供应商账户修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrModPasswd(SplrModPswdVo splrModPswdVo)
			throws Exception {
		SplrAcnt splrAcnt = splrAcntMapper.selectByLogin(
				splrModPswdVo.getUsrNam(), splrModPswdVo.getMim(), null);
		if (splrAcnt != null) {
			SplrAcnt mSplrAcnt = new SplrAcnt();
			mSplrAcnt.setAcntId(splrAcnt.getAcntId());
			mSplrAcnt.setPasswd(splrModPswdVo.getNpasswd());
			mSplrAcnt.setModTim(DateUtils.getCurrentTimeStamp());
			splrAcntMapper.updateByPrimaryKeySelective(mSplrAcnt);
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
		} else {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.LOGIN_ERR, "原密码错误！");
		}
	}

	/**
	 * 创建供应商账户
	 * 
	 * @param splrAcntVo
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrCrtAcnt(SplrAcntVo splrAcntVo, String access_token)
			throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			if (!currentSplrUserInfo.getAcntTyp().trim()
					.equalsIgnoreCase(Constants.SPLR_ACNT_ADMIN)) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.NO_OPRT_AUTH, RtnEnum.NO_OPRT_AUTH.getDesc());
			}
			
			Map<String, String> conditions = new ConcurrentHashMap<String, String>();
			conditions.put("usrNam", splrAcntVo.getUsrNam());
			List<SplrAcnt> splrAcnts = splrAcntMapper.selectAcntsByConditions(conditions);
			if (splrAcnts.size() != 0) {
				logger.info("供应商账户添加失败  - 已存在该用户名！");
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.ACNT_IS_EXIST, RtnEnum.ACNT_IS_EXIST.getDesc());
			}
			
			List<SplrAcnt> acnts = splrAcntMapper.selectAcnts(currentSplrUserInfo
					.getSplrId());
			if (acnts.size() >= 3) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.USER_NUM_ERR, RtnEnum.USER_NUM_ERR.getDesc());
			}
			
			SplrAcnt nSplrAcnt = new SplrAcnt();
			nSplrAcnt.setSplrId(currentSplrUserInfo.getSplrId());
			nSplrAcnt.setAcntTyp(Constants.SPLR_ACNT_USER);
			nSplrAcnt.setUsrNam(splrAcntVo.getUsrNam());
			nSplrAcnt.setNam(splrAcntVo.getNam());
			nSplrAcnt.setPasswd(splrAcntVo.getMim());
			nSplrAcnt.setDffFlg("0");
			nSplrAcnt.setCrtTim(DateUtils.getCurrentTimeStamp());

			splrAcntMapper.insertSelective(nSplrAcnt);
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, nSplrAcnt.getAcntId());
		} else {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.LOGIN_ERR, RtnEnum.LOGIN_ERR.getDesc());
		}
	}

	/**
	 * 删除供应商账户
	 * 
	 * @param acntId
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrDelAcnt(Long acntId, String access_token)
			throws Exception {

		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			if (!currentSplrUserInfo.getAcntTyp().trim()
					.equalsIgnoreCase(Constants.SPLR_ACNT_ADMIN)) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.NO_OPRT_AUTH, RtnEnum.NO_OPRT_AUTH.getDesc());
			}
			
			if (currentSplrUserInfo.getAcntId().equals(acntId)) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.NO_OPRT_AUTH, "不能删除自己！");
			}

			SplrAcnt nSplrAcnt = new SplrAcnt();
			nSplrAcnt.setAcntId(acntId);
			nSplrAcnt.setDffFlg("1");
			int rowNum = splrAcntMapper.updateByPrimaryKeySelective(nSplrAcnt);
			if (rowNum == 1) {

				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
			} else {
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.FAIL_OPR, "账号不存在");
			}
		} else {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.LOGIN_ERR, RtnEnum.LOGIN_ERR.getDesc());
		}
	}

	/**
	 * 获取供应商账户列表
	 * 
	 * @param access_token
	 * @return
	 */
	@Override
	public OutputDto splrAcnts(String access_token) {

		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {

			List<SplrAcnt> acnts = splrAcntMapper.selectAcnts(currentSplrUserInfo
					.getSplrId());
			List<SplrAcntOutVo> splrAcntVos = new ArrayList<SplrAcntOutVo>();
			for (SplrAcnt splrAcnt2 : acnts) {
				SplrAcntOutVo splrAcntOutVo = new SplrAcntOutVo();
				BeanUtils.copyProperties(splrAcntOutVo, splrAcnt2);
				splrAcntVos.add(splrAcntOutVo);

			}

			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, splrAcntVos);
		} else {
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.LOGIN_ERR, RtnEnum.LOGIN_ERR.getDesc());
			}
	}

	/**
	 * 添加供应商银行账号信息
	 * 
	 * @param splrBnkAcntVo
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrBnkAcnt(SplrBnkAcntVo splrBnkAcntVo,
			String access_token) throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			
			if (!currentSplrUserInfo.getAcntTyp().trim()
					.equalsIgnoreCase(Constants.SPLR_ACNT_ADMIN)) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.NO_OPRT_AUTH, RtnEnum.NO_OPRT_AUTH.getDesc());
			}
			
			Map<String, Object> conditions = new ConcurrentHashMap<String, Object>();
			conditions.put("bnkAcnt", splrBnkAcntVo.getBnkAcnt());
			conditions.put("start", 1);
			conditions.put("limit", 1000);
			List<SplrBnkAcnt> splrBnkAcnts = splrBnkAcntMapper.selectByBnkConditions(conditions);
			if (splrBnkAcnts.size() != 0) {
				logger.info("该银行账户已存在！");
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.ACNT_IS_EXIST, RtnEnum.ACNT_IS_EXIST.getDesc());
			}
			
			
			SplrBnkAcnt splrBnkAcnt = new SplrBnkAcnt();
			BeanUtils.copyProperties(splrBnkAcnt, splrBnkAcntVo);
			splrBnkAcnt.setSplrId(currentSplrUserInfo.getSplrId());
			int rowNum = splrBnkAcntMapper.insert(splrBnkAcnt);
			if (rowNum == 1) {

				// 更新供应商状态为注册状态
				Splr splr = new Splr();
				splr.setSplrId(currentSplrUserInfo.getSplrId());
				splr.setSplrSts(Constants.SPLR_STS_REGISTER);
				splrMapper.updateByPrimaryKeySelective(splr);

				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, splrBnkAcnt.getBnkAcntId());
			}
		}

		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 供应商上传资质文件
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto uploadApt(SplrAptVo splrAptVo)
			throws Exception {

		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {

			SplrApt splrApt = new SplrApt();
			if (null != splrAptVo.getAptInTim()) {
				splrApt.setAptInTim(splrAptVo.getAptInTim());
			}
			splrApt.setCrfcOgnz(splrAptVo.getCrfcOgnz());
			splrApt.setSplrId(currentSplrUserInfo.getSplrId());
			splrApt.setAptId(splrAptVo.getAptId());
			splrApt.setAptNam(splrAptVo.getAptNam());
			int rowNum = splrAptMapper.updateByPrimaryKeySelective(splrApt);
			if (rowNum == 1) {

				// 更新供应商状态为注册状态
				Splr splr = new Splr();
				splr.setSplrId(currentSplrUserInfo.getSplrId());
				splr.setSplrSts(Constants.SPLR_STS_REGISTER);
				splrMapper.updateByPrimaryKeySelective(splr);

				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, splrApt.getAptId());
			}
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}
	

	@Override
	public OutputDto getApt(String access_token, Long aptId) throws Exception {
		
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			SplrApt splrApt = splrAptMapper.selectByPrimaryKey(aptId);
			SplrAptVo splrAptVo = new SplrAptVo();
			BeanUtils.copyProperties(splrAptVo, splrApt);
			if (splrApt == null) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_WITH_NO_DATA, RtnEnum.SUC_WITH_NO_DATA.getDesc());
			}
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, splrAptVo);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 供应商上传风采信息
	 * @param splrChrmVo
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto uploadChrm(SplrChrmVo splrChrmVo) throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {

			List<SplrChrmInfo> splrChrmInfos = splrChrmInfoMapper.select(currentSplrUserInfo.getSplrId());
			if (splrChrmInfos.size() >= 3) {
				return OutputDtoUtil
						.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR, "风采照片数量已达上限！");
			}
			SplrChrmInfo splrChrmInfo = new SplrChrmInfo();
			splrChrmInfo.setSplrId(currentSplrUserInfo.getSplrId());
			// 文件写入
			BeanUtils.copyProperties(splrChrmInfo, splrChrmVo);

			splrChrmInfo.setUpldUsr(currentSplrUserInfo.getAcntId());
			splrChrmInfo.setModUsr(currentSplrUserInfo.getAcntId());
			splrChrmInfo.setUpldTim(new Timestamp(System.currentTimeMillis()));
			splrChrmInfo.setModTim(new Timestamp(System.currentTimeMillis()));
			splrChrmInfo.setDelFlg(Constants.EFF_FLG_OFF);
			int rowNum = splrChrmInfoMapper.insertSelective(splrChrmInfo);

			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
		}

		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	@Override
	public OutputDto updateChrm(SplrChrmVo splrChrmVo) throws Exception {

		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {

			SplrChrmInfo splrChrmInfo = new SplrChrmInfo();
			BeanUtils.copyProperties(splrChrmInfo, splrChrmVo);
			
			splrChrmInfo.setModUsr(currentSplrUserInfo.getAcntId());
			splrChrmInfo.setModTim(new Timestamp(System.currentTimeMillis()));

			int rowNum = splrChrmInfoMapper.updateSelective(splrChrmInfo);
			if (rowNum == 1) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
			}

		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 获取供应商风采列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto getChrm() throws Exception {

		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			List<SplrChrmInfo> splrChrmInfos = splrChrmInfoMapper.select(currentSplrUserInfo.getSplrId());

			PagedResult result = new PagedResult(splrChrmInfos, splrChrmInfos.size());
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, result);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 资质文件列表
	 * 
	 * @param access_token
	 * @return
	 */
	@Override
	public OutputDto aptList(String access_token) {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			
			Splr splr = splrMapper.selectByPrimaryKey(currentSplrUserInfo.getSplrId());
			List<SplrAptInfo> splrAptInfos = splrAptMapper.splrAptList(currentSplrUserInfo
					.getSplrId(), splr.getClassification());
			for(SplrAptInfo splrAptInfo : splrAptInfos) {
				splrAptInfo.setAptVal(splrAptInfo.getAptVal());
			}

			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, splrAptInfos);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 供应商添加自荐产品
	 * 
	 * @param splrRcmdOnsfVo
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrAddRcmdOnsf(SplrRcmdOnsfVo splrRcmdOnsfVo,
			String access_token) throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo.getSplrId() != null) {
			SplrRcmdOnsf splrRcmdOnsf = new SplrRcmdOnsf();
			BeanUtils.copyProperties(splrRcmdOnsf, splrRcmdOnsfVo);
			splrRcmdOnsf.setSplrId(currentSplrUserInfo.getSplrId());
			int rowNum = splrRcmdOnsfMapper.insertSelective(splrRcmdOnsf);
			if (rowNum == 1) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
			}
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 获取自荐产品列表
	 * 
	 * @param access_token
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrRcmdOnsfs(String access_token, Integer pageNo,
			Integer pageSize) throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		Long splrId = null;
		if (null == currentSplrUserInfo.getSplrId() && null == userinfo.getUserid()) {
			return OutputDtoUtil
					.setOutputDto(Constants.SUC_FALSE, RtnEnum.NO_OPRT_AUTH);
		}

		List<SplrRcmdOnsf> splrRcmdOnsfs = splrRcmdOnsfMapper
				.selectByCondition(currentSplrUserInfo.getSplrId(), pageNo, pageSize);
		int count = splrRcmdOnsfMapper.selectByConditionCount(currentSplrUserInfo
				.getSplrId());
		List<SplrRcmdOnsfVo> splrRcmdOnsfVos = new ArrayList<SplrRcmdOnsfVo>();
		for (SplrRcmdOnsf splrRcmdOnsf : splrRcmdOnsfs) {
			SplrRcmdOnsfVo splrRcmdOnsfVo = new SplrRcmdOnsfVo();
			BeanUtils.copyProperties(splrRcmdOnsfVo, splrRcmdOnsf);
			splrRcmdOnsfVos.add(splrRcmdOnsfVo);
		}

		PagedResult result = new PagedResult(splrRcmdOnsfVos, count);
		OutputDto outputDto = OutputDtoUtil.setOutputDto(
				Constants.SUC_TRUE, RtnEnum.SUC_OPR, result);
		return outputDto;
	}

	/**
	 * 供应商信息修改
	 * 
	 * @param splrVo
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrUpdateInfo(SplrVo splrVo, String access_token)
			throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			Splr splr = splrMapper.selectByPrimaryKey(currentSplrUserInfo.getSplrId());
			List<SplrModAply> splrModAplies = new ArrayList<SplrModAply>();

			Field[] fields = splrVo.getClass().getDeclaredFields();
			Field[] splrFields = splr.getClass().getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {

				for (int i = 0; i < splrFields.length; i++) {
					fields[j].setAccessible(true);
					splrFields[i].setAccessible(true);
					if (fields[j].getName().equals(splrFields[i].getName())) {

						if (fields[j].get(splrVo) != null
								&& splrFields[i].get(splr) != null) {
							if (!fields[j].get(splrVo).equals(
									splrFields[i].get(splr))) {

								SplrModAply splrModAply = new SplrModAply();
								splrModAply.setModFld(fields[j].getName());
								splrModAply.setOldVal((String) splrFields[i]
										.get(splr));
								splrModAply.setNewVal((String) fields[j]
										.get(splrVo));
								splrModAplies.add(splrModAply);
							}
						}
						if (fields[j].get(splrVo) != null
								&& splrFields[i].get(splr) == null) {
							SplrModAply splrModAply = new SplrModAply();
							splrModAply.setModFld(fields[j].getName());
							splrModAply.setOldVal(null);
							splrModAply.setNewVal((String) fields[j]
									.get(splrVo));
							splrModAplies.add(splrModAply);
						}
						if (fields[j].get(splrVo) == null
								&& splrFields[i].get(splr) != null) {
							SplrModAply splrModAply = new SplrModAply();
							splrModAply.setModFld(fields[j].getName());
							splrModAply.setOldVal((String) splrFields[i]
									.get(splr));
							splrModAply.setNewVal(null);
							splrModAplies.add(splrModAply);
						}
					}
				}
			}
			int rowNum = splrModAplyMapper.insertSelectives(splrModAplies);
			if (rowNum == splrModAplies.size()) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
			}
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 供应商信息获取
	 * 
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto getSplrInfo(String access_token) throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			Splr splr = splrMapper.selectByPrimaryKey(currentSplrUserInfo.getSplrId());
			if (splr != null) {
				SplrVo splrVo = new SplrVo();
				BeanUtils.copyProperties(splrVo, splr);
				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, splrVo);
			}
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 获取银行账号信息接口
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto getSplrBnkAcnts(Integer pageNo, Integer pageSize) throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			
			if (pageNo == null || pageNo <= 0) {
	            pageNo = 1;
	        }

	        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
	            pageSize = Constants.PAGE_SIZE;
	        }

	        Integer start = (pageNo - 1) * pageSize + 1;

	        Integer to = pageNo * pageSize;
	        
	        Map map = PageUtils.getQueryCondsMap(null, start, to);
	        map.put("splrId", currentSplrUserInfo.getSplrId());
	        List<SplrBnkAcnt> splrBnkAcnts = splrBnkAcntMapper.selectByBnkConditions(map);
	        Integer count = splrBnkAcntMapper.countOfMap(map);
	        
			List<SplrBnkAcntVo> splrBnkAcntVos = new ArrayList<SplrBnkAcntVo>();
			for (SplrBnkAcnt splrBnkAcnt : splrBnkAcnts) {
				SplrBnkAcntVo splrBnkAcntVo = new SplrBnkAcntVo();
				BeanUtils.copyProperties(splrBnkAcntVo, splrBnkAcnt);
				splrBnkAcntVos.add(splrBnkAcntVo);
			}
			
			PagedResult result = new PagedResult(splrBnkAcntVos, count);
			OutputDto outputDto = OutputDtoUtil.setOutputDto(
					Constants.SUC_TRUE, RtnEnum.SUC_OPR, result);
			return outputDto;

		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 修改银行账号信息
	 * 
	 * @param access_token
	 * @param splrBnkAcntVo
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto modSplrBnkAcnt(String access_token,
			SplrBnkAcntVo splrBnkAcntVo) throws Exception {

		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			if (splrBnkAcntVo.getBnkAcntId() == null) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.UID_SGN_NIL, RtnEnum.UID_SGN_NIL.getDesc());
			}

			SplrBnkAcnt splrBnkAcnt = new SplrBnkAcnt();
			BeanUtils.copyProperties(splrBnkAcnt, splrBnkAcntVo);
			int rowNum = splrBnkAcntMapper
					.updateByPrimaryKeySelective(splrBnkAcnt);
			if (rowNum == 1) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
			}
		}

		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}
	
	/**
	 * 根据bnkId获取银行信息
	 * @param access_token
	 * @param bnkId
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto getSplrBnkAcnt(String access_token, Long bnkId)
			throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			if (!currentSplrUserInfo.getAcntTyp().trim()
					.equalsIgnoreCase(Constants.SPLR_ACNT_ADMIN)) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.NO_OPRT_AUTH, RtnEnum.NO_OPRT_AUTH.getDesc());
			}
			SplrBnkAcnt splrBnkAcnt = splrBnkAcntMapper.selectByPrimaryKey(bnkId);
			if (splrBnkAcnt != null) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, splrBnkAcnt);
			}
			
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 删除银行信息
	 * @param access_token
	 * @param bnkId
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto delSplrBnkAcnt(String access_token, Long bnkId)
			throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			if (!currentSplrUserInfo.getAcntTyp().trim()
					.equalsIgnoreCase(Constants.SPLR_ACNT_ADMIN)) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.NO_OPRT_AUTH, RtnEnum.NO_OPRT_AUTH.getDesc());
			}
			int rowNum = splrBnkAcntMapper.deleteByPrimaryKey(bnkId);
			if (rowNum == 1) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR);
			}
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 供应商自荐产品查询
	 * 
	 * @param id
	 * @param access_token
	 * @return
	 */
	@Override
	public OutputDto selectSplrRcmdOnsf(Long id, String access_token)
			throws Exception {

		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		logger.info("供应商自荐产品查询，id:" + id);
		if (id == null) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.ARG_INVALID, RtnEnum.ARG_INVALID.getDesc());
		}

		if (currentSplrUserInfo != null) {
			SplrRcmdOnsf splrRcmdOnsf = splrRcmdOnsfMapper
					.selectByPrimaryKey(id);
			logger.info("供应商自荐产品查询成功，产品信息：" + splrRcmdOnsf.toString());
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, splrRcmdOnsf);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	@Override
	public OutputDto updateSplrRcmdOnsf(Long id, SplrRcmdOnsfVo splrRcmdOnsfVo,
			String access_token) throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		logger.info("供应商自荐产品修改，id:" + id);

		if (id == null) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.ARG_INVALID, RtnEnum.ARG_INVALID.getDesc());
		}
		if (currentSplrUserInfo != null) {

			SplrRcmdOnsf splrRcmdOnsf = new SplrRcmdOnsf();
			BeanUtils.copyProperties(splrRcmdOnsf, splrRcmdOnsfVo);
			splrRcmdOnsf.setRcmdOnsfId(id);
			int rowNum = splrRcmdOnsfMapper
					.updateByPrimaryKeySelective(splrRcmdOnsf);
			if (rowNum == 1) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
			}
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	@Override
	public OutputDto delSplrRcmdOnsf(Long id, String access_token)
			throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		logger.info("供应商自荐产品删除，id:" + id);
		if (id == null) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.ARG_INVALID, RtnEnum.ARG_INVALID.getDesc());
		}
		if (currentSplrUserInfo != null) {

			int rowNum = splrRcmdOnsfMapper.deleteByPrimaryKey(id);
			if (rowNum == 1) {
				return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
						RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
			}
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 写文件
	 * @param path
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private String writeFile(String path, MultipartFile file) throws IOException {

		String fileName = System.currentTimeMillis() + "" + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		System.out.println(folder.getAbsolutePath());

		String filePath = path+ File.separator
				+ fileName;
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.createNewFile();
		}
		BufferedOutputStream buffStream = new BufferedOutputStream(
				new FileOutputStream(targetFile));
		buffStream.write(file.getBytes());
		buffStream.close();
		return fileName;
	}

	// TODO 将会删除
	@Override
	public OutputDto uploadFile(String type, MultipartFile file) throws Exception {
		
		String[] fileTypes = Constants.FILE_TYPE;
		boolean chk = false;
		for (String fileType : fileTypes) {
			if (type.equalsIgnoreCase(fileType)) {
				chk = true;
			}
		}
		if (!chk) {
			return OutputDtoUtil
					.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR, "文件类型不支持");
		}
		
		String fileName = writeFile(SysParmHolder.FILE_DIR + type, file);
		String path = type + "/" + fileName;
		logger.info("保存文件成功！路径为：[{}]", path);
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc(), path);
	}

	@Override
	public OutputDto getSplrInfo(Long id) throws Exception {
		
		Splr splr = splrMapper.selectByPrimaryKey(id);
		if (splr != null) {
			SplrVo splrVo = new SplrVo();
			BeanUtils.copyProperties(splrVo, splr);
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, splrVo);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	@Override
	public OutputDto getChrmById(Long id) throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			List<SplrChrmInfo> splrChrmInfos = splrChrmInfoMapper.select(currentSplrUserInfo.getSplrId());
			for (SplrChrmInfo splrChrmInfo : splrChrmInfos) {
				if (splrChrmInfo.getId() == id) {
					return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
							RtnEnum.SUC_OPR, splrChrmInfo);
				}
			}
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.NO_OPRT_AUTH);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.LOGIN_ERR);
	}

	@Override
	public OutputDto delChrmById(Long id) throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (currentSplrUserInfo != null) {
			List<SplrChrmInfo> splrChrmInfos = splrChrmInfoMapper.select(currentSplrUserInfo.getSplrId());
			for (SplrChrmInfo splrChrmInfo : splrChrmInfos) {
				if (splrChrmInfo.getId() == id) {
					int rowNum = splrChrmInfoMapper.delete(id);
					if (rowNum == 1) {
						return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
								RtnEnum.SUC_OPR);
					}
				}
			}
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.NO_OPRT_AUTH);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.LOGIN_ERR);
	}

	@Override
	public OutputDto checkName(RgstCheckNameVo rgstCheckNameVo) {
		// 没传数据
		if (null == rgstCheckNameVo || null == rgstCheckNameVo.getName()) {
			return OutputDtoUtil
					.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_OPR_NIL);
		}

		List<Splr> splrs = splrMapper.selectByFullName(rgstCheckNameVo.getName());
		if (null == splrs || splrs.size() == 0) {
			// 校验通过
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC);
		}

		// 判断该用户名是否注册过
		Map<String, String> conditions = new ConcurrentHashMap<String, String>();
		conditions.put("splrId", String.valueOf(splrs.get(0).getSplrId()));
		conditions.put("defFlg", "0");
		List<SplrAcnt> splrAcnts = splrAcntMapper.selectAcntsByConditions(conditions);

		if (null != splrAcnts && splrAcnts.size() > 0) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ACNT_IS_EXIST, "供应商已注册");
		}

		// 校验失败
		ComParm comParm = ComParmHolder.getByParmTypAndParmCod("COFCO_EMAIL", "1");
		String email = "";
		if (null != comParm) {
			email = comParm.getParmVal();
		}
		return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ACNT_IS_EXIST, "供应商数据存在，请联系管理员获取账号", email);
	}
}
