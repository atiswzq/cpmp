package cn.cofco.cpmp.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.cofco.cpmp.dao.SplrReActMapper;
import cn.cofco.cpmp.entity.SplrReActive;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.SplrMapper;
import cn.cofco.cpmp.dao.SplrWdotInfoMapper;
import cn.cofco.cpmp.dao.SplrWdotMapper;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.PagedResult;
import cn.cofco.cpmp.dto.SplrWdotAdtDto;
import cn.cofco.cpmp.dto.SplrWdotSplrMngDto;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.entity.SplrWdot;
import cn.cofco.cpmp.entity.SplrWdotInfo;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrWdotService;
import cn.cofco.cpmp.splr.dto.SplrListDto;
import cn.cofco.cpmp.splr.dto.SplrWdotDto;
import cn.cofco.cpmp.splr.vo.SplrWdotVo;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.ContextTools;
import cn.cofco.cpmp.utils.CurrentUserInfo;
import cn.cofco.cpmp.utils.StringUtils;

/**
 * @author 李世涛
 * @date 2018年1月6日
 * 
 */
@Service
@Transactional("transactionManager")
public class SplrWdotServiceImpl implements ISplrWdotService {

	private final static Logger logger = LoggerManager.getSplrLog();

	@Resource
	private SplrMapper splrMapper;

	@Resource
	private SplrWdotMapper splrWdotMapper;

	@Resource
	private SplrWdotInfoMapper splrWdotInfoMapper;

	@Resource
	private SplrReActMapper splrReActMapper;

	private String checkAuth() {
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		Integer userId = userinfo.getUserid();

		if (userId == null || userId == 0) {
			return "鉴权失败 - userId为空";
		}

		return "";
	}

	/**
	 * 供应商淘汰申请
	 * 
	 * @param splrWdotVo
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrWdotAply(SplrWdotVo splrWdotVo) throws Exception {
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
			String errMsg = "供应商淘汰申请 - 失败 - 所属公司为空，无法提交审批";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		// 校验参数
		if (null == splrWdotVo || null == splrWdotVo.getSplrId()) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, "未传参数或供应商id为空");
		}

		// 查询供应商信息
		Splr splr = splrMapper.selectByPrimaryKey(Long.valueOf(splrWdotVo.getSplrId()));

		if (null == splr) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "供应商不存在！");
		}

		// 合格供应商才能申请淘汰
		if (!Constants.SPLR_STS_QUALIFIED.equals(splr.getSplrSts())) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "非合格供应商,不存在淘汰业务！");
		}

		// 设置淘汰申请(向采购经理)
		SplrWdot splrWdot = new SplrWdot();
		splrWdot.setSplrId(Long.valueOf(splrWdotVo.getSplrId()));
		splrWdot.setAplyUsr(userinfo.getUserid().longValue());
		// splrWdot.setAplyUsr(Long.valueOf(splrWdotVo.getAplyUsr()));
		splrWdot.setAplyTim(new Timestamp(System.currentTimeMillis()));
		splrWdot.setWdotOrg(String.valueOf(userinfo.getCompany().getId()));
		// splrWdot.setWdotOrg(splrWdotVo.getWdotOrg());
		splrWdot.setAplySts(Constants.SPLR_WDOT_APLY_STS_BUILD);
		splrWdot.setAplyCtt(splrWdotVo.getAplyCtt());
		splrWdot.setDelFlg("0");
		splrWdot.setSplrSts(Constants.SPLR_WDOT_APLY_STS_APLYING);
		splrWdot.setDeptNam(userinfo.getCompany().getDept_name());

		Map map = new HashMap();
		map.put("splrId", Long.valueOf(splrWdotVo.getSplrId()));
		map.put("wdotOrg", userinfo.getCompany().getId());
		map.put("aplySts", Constants.SPLR_ADT_STS_FAIL);

		// 如果申请失败,再次申请时,将先前的有效值设为无效
		List<SplrWdot> splrWdots = splrWdotMapper.selectByCondition(map);
		for (SplrWdot sw : splrWdots) {
			sw.setDelFlg("1");
			splrWdotMapper.updateByPrimaryKeySelective(sw);
		}
		splrWdotMapper.insert(splrWdot);

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
	}

	/**
	 * 供应商淘汰审核(采购部经理)
	 * 
	 */
	public OutputDto splrWdotAdtByMng(SplrWdotAdtDto splrWdotAdtDto) throws Exception {
		if (!"".equals(checkAuth())) {
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}

		SplrWdot splrWdot = new SplrWdot();
		splrWdot.setAplyId(splrWdotAdtDto.getAplyId());
		splrWdot.setWdotDeptAdtMsg(splrWdotAdtDto.getAdtMsg());
		splrWdot.setWdotDeptAdtSts(splrWdotAdtDto.getAdtSts());
		splrWdot.setWdotTim(new Timestamp(System.currentTimeMillis()));

		if (Constants.SPLR_ADT_STS_FAIL.equals(splrWdotAdtDto.getAdtSts())) {
			splrWdot.setAplySts(Constants.SPLR_WDOT_APLY_STS_BACK);
			splrWdot.setSplrSts(Constants.SPLR_WDOT_APLY_STS_BACK);
			splrWdotMapper.updateByPrimaryKeySelective(splrWdot);

			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
		} else if (Constants.SPLR_ADT_STS_SUCCESS.equals(splrWdotAdtDto.getAdtSts())) {
			splrWdot.setAplySts(Constants.SPLR_WDOT_APLY_STS_APLYING);
			splrWdotMapper.updateByPrimaryKeySelective(splrWdot);

			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
		}

		return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR, RtnEnum.FAIL_OPR.getDesc());
	}

	/**
	 * 供应商淘汰审核(供应商管理部)
	 */
	public OutputDto splrWdotAdtBySplrMng(SplrWdotAdtDto splrWdotAdtDto) throws Exception {
		if (!"".equals(checkAuth())) {
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}
		SplrWdot splrWdot = new SplrWdot();
		splrWdot.setAplyId(splrWdotAdtDto.getAplyId());
		splrWdot.setWdotSplrMngAdtMsg(splrWdotAdtDto.getAdtMsg());
		splrWdot.setWdotSplrMngAdtSts(splrWdotAdtDto.getAdtSts());
		splrWdot.setWdotTim(new Timestamp(System.currentTimeMillis()));

		if (Constants.SPLR_ADT_STS_FAIL.equals(splrWdotAdtDto.getAdtSts())) {
			splrWdot.setAplySts(Constants.SPLR_WDOT_APLY_STS_BACK);
			splrWdot.setSplrSts(Constants.SPLR_WDOT_APLY_STS_BACK);
			splrWdotMapper.updateByPrimaryKeySelective(splrWdot);
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
		} else if (Constants.SPLR_ADT_STS_SUCCESS.equals(splrWdotAdtDto.getAdtSts())) {
			splrWdot.setAplySts(Constants.SPLR_WDOT_APLY_STS_SUCCESS);
			splrWdot.setSplrSts(Constants.SPLR_STS_WEED_OUT);
			splrWdotMapper.updateByPrimaryKeySelective(splrWdot);

			SplrWdot sWdot = splrWdotMapper.selectByPrimaryKey(splrWdotAdtDto.getAplyId());

			// 向关联表插入淘汰结果
			SplrWdotInfo splrWdotInfo = new SplrWdotInfo();
			splrWdotInfo.setAplyId(splrWdotAdtDto.getAplyId());
			splrWdotInfo.setSplrId(sWdot.getSplrId());
			splrWdotInfo.setWdotOrg(sWdot.getWdotOrg());
			splrWdotInfoMapper.insert(splrWdotInfo);
			//判断一下是否有重新启用记录，有的话设置成无效
			Map map = new HashMap();
			map.put("splrId", Long.valueOf(sWdot.getSplrId()));
			map.put("aplyOrg", sWdot.getWdotOrg());
			map.put("splrSts",Constants.SPLR_STS_QUALIFIED);
			List<SplrReActive> splrReActives = splrReActMapper.selectByCondition(map);
			for(SplrReActive splrReActive:splrReActives){
				splrReActive.setDelFlg("1");
				splrReActMapper.updateByPrimaryKeySelective(splrReActive);
			}
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
		}
		return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR, RtnEnum.FAIL_OPR.getDesc());
	}

	/**
	 * 供应商淘汰(供应商管理员直接淘汰,不需要审核)
	 * 
	 * @param splrMngDto
	 * @return
	 * @throws Exception
	 */
	public OutputDto splrWdotBySplrMng(SplrWdotSplrMngDto splrMngDto) throws Exception {
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
			String errMsg = "供应商淘汰(管理员)- 失败 - 所属公司为空";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		// 校验参数
		if (null == splrMngDto.getSplrId()) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, "未传参数或供应商id为空");
		}

		// 查询供应商信息
		Splr splr = splrMapper.selectByPrimaryKey(splrMngDto.getSplrId());

		if (null == splr) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "供应商不存在！");
		}

		// 合格供应商才能申请淘汰
		if (!Constants.SPLR_STS_QUALIFIED.equals(splr.getSplrSts())) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "非合格供应商,不存在淘汰业务！");
		}

	/*	// 设置淘汰
		SplrWdot splrWdot = new SplrWdot();
		splrWdot.setSplrId(splrMngDto.getSplrId());
		splrWdot.setSplrMngNam(userinfo.getRealname());
		splrWdot.setWdotOrg("1");// 总部淘汰
		splrWdot.setSplrSts(Constants.SPLR_STS_WEED_OUT);
		splrWdot.setDelFlg("0");
		splrWdot.setWdotMsg(splrMngDto.getWdotMsg());
		splrWdot.setWdotTim(new Timestamp(System.currentTimeMillis()));
		splrWdotMapper.insert(splrWdot);
*/
		// 供应商管理员淘汰了一个供应商,所有的公司都不能使用该供应商
		splr.setSplrSts(Constants.SPLR_STS_WEED_OUT);
		splrMapper.updateByPrimaryKey(splr);

	/*	// 向关联表插入淘汰结果
		Map map = new HashMap();
		map.put("splrId", splrMngDto.getSplrId());
		map.put("wdotOrg", 1);
		List<SplrWdot> splrWdots = splrWdotMapper.selectByCondition(map);
		SplrWdot sWdot = null;
		if (!splrWdots.isEmpty()) {
			sWdot = splrWdots.get(0);
		}

		SplrWdotInfo splrWdotInfo = new SplrWdotInfo();
		splrWdotInfo.setAplyId(sWdot.getAplyId());
		splrWdotInfo.setSplrId(sWdot.getSplrId());
		splrWdotInfo.setWdotOrg(sWdot.getWdotOrg());
		splrWdotInfoMapper.insert(splrWdotInfo);*/

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
	}

	/**
	 * 供应商淘汰列表获取
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto getSplrWdotList(Integer pageNo, Integer pageSize, SplrWdotDto splrWdotDto,String orgId) throws Exception {

		Map map = pageSet(pageNo, pageSize, splrWdotDto,orgId);
		List<SplrWdotDto> splrWdotDtos = splrWdotMapper.selectWdotsList(map);
		Integer count = splrWdotMapper.wdotListCountByMap(map);

		PagedResult result = new PagedResult(splrWdotDtos, count);

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, result);
	}

	/**
	 * 供应商列表获取
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto getSplrList(Integer pageNo, Integer pageSize, SplrWdotDto splrWdotDto) throws Exception {
		Map map = pageSet2(pageNo, pageSize, splrWdotDto);
		List<SplrListDto> splrListDtos = splrWdotMapper.selectSplrList(map);
		Integer count = splrWdotMapper.splrListCountByMap(map);

		PagedResult result = new PagedResult(splrListDtos, count);

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, result);
	}

	private Map pageSet(Integer pageNo, Integer pageSize, SplrWdotDto splrWdotDto,String orgId) {
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

		// 分页查询页码处理
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
			pageSize = Constants.PAGE_SIZE;
		}

		Integer start = (pageNo - 1) * pageSize + 1;

		Integer end = pageNo * pageSize;

		Map map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		map.put("orgId", orgId);
		map.put("splrName", splrWdotDto.getSplrFullName());
		return map;
	}

	private Map pageSet2(Integer pageNo, Integer pageSize, SplrWdotDto splrWdotDto) {
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

		// 分页查询页码处理
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
			pageSize = Constants.PAGE_SIZE;
		}

		Integer start = (pageNo - 1) * pageSize + 1;

		Integer end = pageNo * pageSize;
		Integer orgId = userinfo.getCompany().getId();
		Map map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		map.put("orgId", String.valueOf(orgId));
		map.put("splrName", splrWdotDto.getSplrFullName());
		return map;
	}

}
