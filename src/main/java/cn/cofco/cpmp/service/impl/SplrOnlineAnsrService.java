package cn.cofco.cpmp.service.impl;

import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;

import cn.cofco.cpmp.dao.BidProjOnMapper;
import cn.cofco.cpmp.dao.BidProjOnSplrTendInfMapper;
import cn.cofco.cpmp.entity.BidProjOn;
import cn.cofco.cpmp.entity.BidProjOnSplrTendInf;
import cn.cofco.cpmp.utils.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.SplrMapper;
import cn.cofco.cpmp.dao.SplrOnlineAnsrMapper;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.entity.SplrOnlineAnsr;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrOnlineAnsrService;
import cn.cofco.cpmp.splr.dto.SplrAnsrListDto;
import cn.cofco.cpmp.splr.dto.SplrCountDto;
import cn.cofco.cpmp.splr.dto.SplrOnlineDto;
import cn.cofco.cpmp.splr.vo.SplrOnlineAnsrVo;
import cn.cofco.cpmp.splr.vo.SplrOnlineQueVo;
import cn.cofco.cpmp.support.OutputDtoUtil;

@Service
@Transactional("transactionManager")
public class SplrOnlineAnsrService implements ISplrOnlineAnsrService {
	private final static Logger logger = LoggerManager.getSplrLog();

	@Resource
	private SplrMapper splrMapper;

	@Resource
	private SplrOnlineAnsrMapper splrOnlineAnsrMapper;

	@Resource
	private BidProjOnSplrTendInfMapper bidProjOnSplrTendInfMapper;
	@Resource
	private BidProjOnMapper bidProjOnMapper;

	private String checkAuth() {
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		Integer userId = userinfo.getUserid();
		if (userId == null || userId == 0) {
			return "鉴权失败 - userId为空";
		}
		return "";
	}

	@Override
	public OutputDto splrOnlineQue(SplrOnlineQueVo splrOnlineQue) throws Exception {
		// 校验参数
		if (null == splrOnlineQue || null == splrOnlineQue.getSplrId()) {
			logger.error("供应商未传参数或供应商id为空");
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, "未传参数或供应商id为空");
		}

		// 查询供应商信息
		Splr splr = splrMapper.selectByPrimaryKey(Long.valueOf(splrOnlineQue.getSplrId()));
		if (null == splr) {
			logger.error("供应商不存在, 无提问权限！");
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "供应商不存在, 无提问权限！");
		}
		//只有参与投标并且通过的供应商才有资格咨询该项目信息
		BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
		bidProjOnSplrTendInf.setProjId(splrOnlineQue.getProjId());
		bidProjOnSplrTendInf.setSplrId(splrOnlineQue.getSplrId());
		Map map = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf,0,0);
		List<String> bidDocStses = Arrays.asList(new String[]{Constants.BID_DOC_STS_ACCEPTED, Constants.BID_DOC_STS_AWD, Constants.BID_DOC_STS_UNAWD});
		map.put("bidDocStses",bidDocStses);
		List<BidProjOnSplrTendInf> bidProjOnSplrTendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);
		if(bidProjOnSplrTendInfs==null||bidProjOnSplrTendInfs.isEmpty()){
			String errMsg = "只有投标申请通过的供应商才能提问";
			logger.error(errMsg);
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA,errMsg);
		}
		SplrOnlineAnsr sQue = new SplrOnlineAnsr();
		sQue.setSplrId(splrOnlineQue.getSplrId());
		sQue.setProjId(splrOnlineQue.getProjId());
		sQue.setSplrNam(splr.getFullNam());
		if (splr.getShortNam() != null) {
			sQue.setSplrNam(splr.getShortNam());
		}
		sQue.setQueContent(splrOnlineQue.getContent());
		sQue.setQueTime(new Timestamp(System.currentTimeMillis()));

		splrOnlineAnsrMapper.insert(sQue);
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
	}

	@Override
	public OutputDto splrOnlineAnsr(SplrOnlineAnsrVo splrOnlineAnsr) throws Exception {

		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		if (!"".equals(checkAuth())) {
			logger.error("答疑者无此操作权限");
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}

		// 校验参数
		if (null == splrOnlineAnsr || null == splrOnlineAnsr.getSplrId()) {
			logger.error("供应商未传参数或供应商id为空");
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, "未传参数或供应商id为空");
		}
		// 查询供应商信息
		Splr splr = splrMapper.selectByPrimaryKey(Long.valueOf(splrOnlineAnsr.getSplrId()));
		if (null == splr) {
			logger.error("供应商不存在");
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "供应商不存在!");
		}

		// 判断答疑者的权限,需要知道满足什么条件
		// if (userinfo.getCompany().getId() != ) {
		// logger.error("答疑者没有答疑权限. userId: "+userinfo.getCompany().getId());
		// return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
		// RtnEnum.SYS_ERR, "答疑者没有答疑权限!");
		// }
		SplrOnlineAnsr sAnsr = new SplrOnlineAnsr();
		sAnsr.setSplrId(splrOnlineAnsr.getSplrId());
		if (splr.getShortNam() != null) {
			sAnsr.setSplrNam(splr.getShortNam());
		} else {
			sAnsr.setSplrNam(splr.getFullNam());
		}
		sAnsr.setProjId(splrOnlineAnsr.getProjId());
		sAnsr.setAnsrContent(splrOnlineAnsr.getAnsrContent());
		sAnsr.setAnsrTime(new Timestamp(System.currentTimeMillis()));
		sAnsr.setProjLeader(userinfo.getUsername());
		splrOnlineAnsrMapper.insert(sAnsr);

		Map map = new HashMap<>();
		map.put("mid", sAnsr.getMid());
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, map);
	}

	@Override
	public OutputDto splrAnsrList(Long projId) throws Exception {
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		Long splrId = currentSplrUserInfo.getSplrId();
		if (splrId == null || splrId == 0) {
			logger.error("鉴权失败 - splrId为空, 提问供应商没有注册或登录");
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}

		Map map = new HashMap<>();
		map.put("projId", projId);
		List<SplrOnlineDto> openContList = splrOnlineAnsrMapper.findOpenContList(map);

		map.put("splrId", splrId);
		List<SplrOnlineDto> dialogList = splrOnlineAnsrMapper.selectByCondition(map);

		SplrAnsrListDto splrAnsrList = new SplrAnsrListDto();
		splrAnsrList.setDialogList(dialogList);
		splrAnsrList.setOpenContList(openContList);

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, splrAnsrList);
	}

	@Override
	public OutputDto purchaserAnsrList(Long projId) throws Exception {
		if (!"".equals(checkAuth())) {
			logger.error("采购员无此操作权限");
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}
		/*CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		String realname = userinfo.getRealname();
		BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
		if(bidProjOn==null||!realname.equals(bidProjOn.getProjRsps())){
			String errMsg = "只有项目负责人有权限";
			logger.error(errMsg);
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
		}*/

		//CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		// 判断采购者阅读答疑提问的权限,需要知道满足什么条件
		// if (userinfo.getCompany().getId() != ) {
		// logger.error("答疑者没有答疑权限. userId: "+userinfo.getCompany().getId());
		// return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
		// RtnEnum.SYS_ERR, "答疑者没有答疑权限!");
		// }
		Map map = new HashMap<>();
		map.put("projId", projId);

		List<SplrCountDto> cDtos = splrOnlineAnsrMapper.selectSplrCountByProjId(projId);
		List purchaserAnsrList = new ArrayList();
		for (SplrCountDto cd : cDtos) {
			map.put("splrId", cd.getSplrId());
			List<SplrOnlineDto> splrOnlineDtos = splrOnlineAnsrMapper.selectByCondition(map);
			purchaserAnsrList.add(splrOnlineDtos);
		}

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, purchaserAnsrList);
	}

	@Override
	public OutputDto openAnsrInfo(Long mid, int openFlg) throws Exception {

		if (!"".equals(checkAuth())) {
			logger.error("登录用户(答疑员)无此操作权限");
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}
		SplrOnlineAnsr sAnsr = splrOnlineAnsrMapper.findAnsrByMid(mid);
		sAnsr.setOpenFlg(openFlg);

		// 根据点击按钮(可以公开,也可以撤回公开)来改变是否公开的状态值
		splrOnlineAnsrMapper.update(sAnsr);

	/*	// 修改问题的状态
		Map map = new HashMap<>();
		map.put("splrId", sAnsr.getSplrId());
		map.put("projId", sAnsr.getProjId());
		SplrOnlineAnsr sQue = null;
		while (sQue == null) {
			map.put("mid", mid--);
			sQue = splrOnlineAnsrMapper.findQueByMidSplrIdProjId(map);
		}
		sQue.setOpenFlg(openFlg);
		splrOnlineAnsrMapper.update(sQue);*/

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getCod());
	}

}
