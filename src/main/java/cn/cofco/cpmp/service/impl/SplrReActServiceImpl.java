package cn.cofco.cpmp.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.cofco.cpmp.entity.SplrWdot;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.SplrMapper;
import cn.cofco.cpmp.dao.SplrReActMapper;
import cn.cofco.cpmp.dao.SplrWdotInfoMapper;
import cn.cofco.cpmp.dao.SplrWdotMapper;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.SplrReActAdtDto;
import cn.cofco.cpmp.dto.SplrReActSplrMngDto;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.entity.SplrReActive;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrReActService;
import cn.cofco.cpmp.splr.vo.SplrReActVo;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.ContextTools;
import cn.cofco.cpmp.utils.CurrentUserInfo;
import cn.cofco.cpmp.utils.StringUtils;

/**
 * @author 李世涛
 * @date 2018年1月10日
 * 
 */
@Service
@Transactional("transactionManager")
public class SplrReActServiceImpl implements ISplrReActService {

	private final static Logger logger = LoggerManager.getSplrLog();

	@Resource
	private SplrMapper splrMapper;

	@Resource
	private SplrReActMapper splrReActMapper;

	@Resource
	private SplrWdotInfoMapper splrWdotInfoMapper;

	@Resource
	private SplrWdotMapper splrWdotMapper;

	private String checkAuth() {
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		Integer userId = userinfo.getUserid();

		if (userId == null || userId == 0) {
			return "鉴权失败 - userId为空";
		}

		return "";
	}

	/**
	 * 供应商重启用申请
	 * 
	 * @param splrReActVo
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrReActAply(SplrReActVo splrReActVo) throws Exception {
		// 0. 权限校验
		if (!"".equals(checkAuth())) {
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

		if (userinfo.getCompany() == null) {
			logger.error("对应公司信息不存在");
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
		}
		if (userinfo.getCompany() == null || StringUtils.isEmpty(userinfo.getCompany().getDept_code())) {
			String errMsg = "供应商重启用申请 - 失败 - 所属公司为空，无法提交审批";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		// 校验参数
		if (null == splrReActVo || null == splrReActVo.getSplrId()) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, "未传参数或供应商id为空");
		}

		// 查询供应商信息
		Splr splr = splrMapper.selectByPrimaryKey(Long.valueOf(splrReActVo.getSplrId()));

		if (null == splr) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "供应商不存在！");
		}

		// 设置重启用申请(向采购经理)
		SplrReActive splrReAct = new SplrReActive();
		splrReAct.setSplrId(Long.valueOf(splrReActVo.getSplrId()));
		splrReAct.setAplyUsr(userinfo.getUserid().longValue());
		splrReAct.setAplyTim(new Timestamp(System.currentTimeMillis()));
		splrReAct.setAplyOrg(String.valueOf(userinfo.getCompany().getId()));
		splrReAct.setAplySts(Constants.SPLR_RE_ACT_APLY_STS_BUILD);
		splrReAct.setReActiveCtt(splrReActVo.getReActiveCtt());
		splrReAct.setDelFlg("0");
		splrReAct.setSplrSts(Constants.SPLR_RE_ACT_APLY_STS_APLYING);

		Map map = new HashMap();
		map.put("splrId", Long.valueOf(splrReActVo.getSplrId()));
		map.put("aplyOrg", userinfo.getCompany().getId());
		map.put("aplySts", Constants.SPLR_ADT_STS_FAIL);

		// 如果申请失败,再次申请时,将先前的有效值设为无效
		List<SplrReActive> splrReActives = splrReActMapper.selectByCondition(map);
		for (SplrReActive splrReActive : splrReActives) {
			splrReActive.setDelFlg("1");
			splrReActMapper.updateByPrimaryKeySelective(splrReActive);
		}

		splrReActMapper.insert(splrReAct);

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
	}

	/**
	 * 供应商重启用审核(采购部经理)
	 * 
	 */
	public OutputDto splrReActAdtByMng(SplrReActAdtDto sraad) throws Exception {
		if (!"".equals(checkAuth())) {
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}
		SplrReActive splrReAct = new SplrReActive();
		splrReAct.setAplyId(sraad.getAplyId());
		splrReAct.setReActDeptAdtMsg(sraad.getAdtMsg());
		splrReAct.setReActDeptAdtSts(sraad.getAdtSts());
		splrReAct.setReActiveTim(new Timestamp(System.currentTimeMillis()));

		if (Constants.SPLR_ADT_STS_FAIL.equals(sraad.getAdtSts())) {
			splrReAct.setSplrSts(Constants.SPLR_RE_ACT_APLY_STS_BACK);
			splrReAct.setAplySts(Constants.SPLR_RE_ACT_APLY_STS_BACK);
			splrReActMapper.updateByPrimaryKeySelective(splrReAct);

			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
		} else if (Constants.SPLR_ADT_STS_SUCCESS.equals(sraad.getAdtSts())) {
			splrReAct.setAplySts(Constants.SPLR_RE_ACT_APLY_STS_APLYING);
			splrReActMapper.updateByPrimaryKeySelective(splrReAct);

			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
		}

		return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR, RtnEnum.FAIL_OPR.getDesc());
	}

	/**
	 * 供应商重启用审核(供应商管理部)
	 * 
	 * @param sraad
	 * @return
	 * @throws Exception
	 */
	public OutputDto splrReActAdtBySplrMng(SplrReActAdtDto sraad) throws Exception {
		if (!"".equals(checkAuth())) {
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}
		SplrReActive splrReAct = new SplrReActive();
		splrReAct.setAplyId(sraad.getAplyId());
		splrReAct.setReActSplrMngAdtMsg(sraad.getAdtMsg());
		splrReAct.setReActSplrMngAdtSts(sraad.getAdtSts());
		splrReAct.setReActiveTim(new Timestamp(System.currentTimeMillis()));

		if (Constants.SPLR_ADT_STS_FAIL.equals(sraad.getAdtSts())) {
			splrReAct.setAplySts(Constants.SPLR_RE_ACT_APLY_STS_BACK);
			splrReAct.setSplrSts(Constants.SPLR_RE_ACT_APLY_STS_BACK);
			splrReActMapper.updateByPrimaryKeySelective(splrReAct);

			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_OPR, sraad.getAdtMsg());
		} else if (Constants.SPLR_ADT_STS_SUCCESS.equals(sraad.getAdtSts())) {
			splrReAct.setAplySts(Constants.SPLR_RE_ACT_APLY_STS_SUCCESS);
			splrReAct.setSplrSts(Constants.SPLR_STS_QUALIFIED);
			splrReActMapper.updateByPrimaryKeySelective(splrReAct);

			SplrReActive sReAct = splrReActMapper.selectByPrimaryKey(sraad.getAplyId());

			// 重新启用成功后
			Map map = new HashMap();
			map.put("splrId", sReAct.getSplrId());
			map.put("aplyOrg", sReAct.getAplyOrg());

			// 将淘汰表里面的相关信息删除
			splrWdotMapper.deleteBySplrIdAndOrg(map);

			// 将淘汰关联表里面的相关信息删除
			splrWdotInfoMapper.deleteBySplrIdAndOrg(map);

			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, splrReAct);
		}
		return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR, RtnEnum.FAIL_OPR.getDesc());
	}

	/**
	 * 供应商重启用(供应商管理员直接重启用,不需要审核)
	 * 
	 * @param srasmd
	 * @return
	 * @throws Exception
	 */
	public OutputDto splrReActBySplrMng(SplrReActSplrMngDto srasmd) throws Exception {
		// 0. 权限校验
		if (!"".equals(checkAuth())) {
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

		if (userinfo.getCompany() == null) {
			logger.error("对应公司信息不存在");
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
		}
		if (userinfo.getCompany() == null || StringUtils.isEmpty(userinfo.getCompany().getDept_code())) {
			String errMsg = "供应商重启用(管理员)- 失败 - 所属公司为空";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		// 校验参数
		if (null == srasmd.getSplrId()) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, "未传参数或供应商id为空");
		}

		// 查询供应商信息
		Splr splr = splrMapper.selectByPrimaryKey(srasmd.getSplrId());

		if (null == splr) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "供应商不存在！");
		}

		// 设置重启用
		SplrReActive splrReAct = new SplrReActive();
		splrReAct.setSplrId(srasmd.getSplrId());
		splrReAct.setSplrMngNam(userinfo.getUsername());
		splrReAct.setAplyOrg(userinfo.getOrgId());
		splrReAct.setSplrSts(Constants.SPLR_STS_QUALIFIED);
		splrReAct.setDelFlg("0");
		splrReAct.setReActiveMsg(srasmd.getReActMsg());
		splrReAct.setReActiveTim(new Timestamp(System.currentTimeMillis()));
		splrReActMapper.insert(splrReAct);

		// 供应商管理员重启用了一个供应商,所有的公司都能使用该供应商
		splr.setSplrSts(Constants.SPLR_STS_QUALIFIED);
		splrMapper.updateByPrimaryKey(splr);

		// 重新启用成功后
		Map map = new HashMap();
		map.put("splrId", srasmd.getSplrId());
		map.put("aplyOrg", splrReAct.getAplyOrg());

		// 将淘汰表里面的相关信息删除
		splrWdotMapper.deleteBySplrIdAndOrg(map);

		// 将淘汰关联表里面的相关信息删除
		splrWdotInfoMapper.deleteBySplrIdAndOrg(map);

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
	}
}
