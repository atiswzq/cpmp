package cn.cofco.cpmp.service.impl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.SplrAplyDto;
import cn.cofco.cpmp.dto.SplrIoDto;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.splr.dto.SplrAptInfo;
import cn.cofco.cpmp.splr.vo.SplrBnkAcntVo;
import cn.cofco.cpmp.splr.vo.SplrInfoVo;
import cn.cofco.cpmp.splr.vo.TokenInfo;
import cn.cofco.cpmp.utils.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.constants.ConstantsEnum;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.PagedResult;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.service.ISplrService;
import cn.cofco.cpmp.splr.dto.SplrChrmDto;
import cn.cofco.cpmp.splr.vo.SplrVo;
import cn.cofco.cpmp.support.OutputDtoUtil;

@Service
@Transactional("transactionManager")
public class SplrServiceImpl implements ISplrService {

	private Logger logger = LoggerManager.getSplrLog();

	@Resource
	private SplrMapper splrMapper;
	
	@Resource
	private SplrAdmtAplyMapper splrAdmtAplyMapper;
	
	@Resource
	private SplrOrgMapper splrOrgMapper;
	
	@Resource
	private SplrWdotMapper splrWdotMapper;
	
	@Resource
	private SplrBkltMapper splrBkltMapper;
	
	@Resource
	private SplrAdmtSplMatMapper splrAdmtSplMatMapper;
	
	@Resource
	private SplrChrmInfoMapper splrChrmInfoMapper;

	@Resource
	private SplrBnkAcntMapper splrBnkAcntMapper;

	@Resource
	private SplrAptMapper splrAptMapper;

	@Resource
	private SplrChrmMapper splrChrmMapper;

	@Resource
	private SplrAcntMapper splrAcntMapper;

	@Resource
	private SplrDvlpAplyMapper splrDvlpAplyMapper;

	@Resource
	private SplrAdmtChkMapper splrAdmtChkMapper;
	
    private String checkAuth() {
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        Integer userId = userinfo.getUserid();

        if (userId == null || userId == 0) {
            return "鉴权失败 - userId为空";
        }

        return "";
    }
	
	
	/**
	 * 供应商注册审核
	 * @param splrId
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto rgstAdt(Long splrId) throws Exception {
		
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }
		
		Splr splr = new Splr();
		splr.setSplrId(splrId);
		splr.setSplrSts(ConstantsEnum.SPLR_STS_READY_SPLR.getCode());
		splrMapper.updateByPrimaryKeySelective(splr);
		
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
	}

	/**
	 * 注册供应商列表
	 * @param splr
	 * @param pageNo
	 * @param pageSize
	 * @param splrStatus
	 * @return
	 */
	@Override
	public OutputDto splrList(SplrVo splr, Integer pageNo, Integer pageSize, String splrStatus) {

		// 检查用户
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        // 分页查询页码处理
		if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize + 1;

        Integer to = pageNo * pageSize;
        
        Map map = PageUtils.getQueryCondsMap(splr, start, to);
        if (null != splrStatus && splrStatus.length() > 0) {
			map.put("splrSts", splrStatus);
		}

        // 供应商列表查询
		List<Splr> splrs = splrMapper.selectByConditions(map);
		Integer count = splrMapper.countOfMap(map);

		Map map1 = PageUtils.getQueryCondsMap(null, 1, 1000);
		map1.put("defFlg", "0");
		List<SplrChrm> splrChrms = splrChrmMapper.selectByConditions(map1);

		List<SplrVo> splrVos = new ArrayList<>();
		for (Splr splr1 : splrs) {
			SplrVo splrVo = new SplrVo();
			BeanUtils.copyProperties(splrVo, splr1);

			// 查询主账号
			Map map2 = new HashMap();
			map2.put("splrId", splr1.getSplrId());
			map2.put("acntTyp", Constants.SPLR_ACNT_ADMIN);
			map2.put("defFlg", Constants.DEL_FLG_NODEL);
			List<SplrAcnt> splrAcnts = splrAcntMapper.selectAcntsByConditions(map2);
			if (null != splrAcnts && splrAcnts.size() > 0) {
				splrVo.setLoginAccount(splrAcnts.get(0).getUsrNam());
			}

			for (SplrChrm splrChrm : splrChrms) {
				if (splrVo.getSplrId().equals(splrChrm.getSplrId())) {
					splrVo.setChrmShow("1");
				} else {
					splrVo.setChrmShow("0");
				}
			}
			splrVos.add(splrVo);
		}
		
		PagedResult result = new PagedResult(splrVos, count);
		
		if (splrs != null && splrs.size() > 0) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, result);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA, RtnEnum.SUC_WITH_NO_DATA.getDesc());
	}

	@Override
	public OutputDto splrAdmtForBPM(String access_token, Long splrId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputDto splrAdmtForMDM(String access_token, Long splrId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputDto getWdotSplr(String access_token) throws Exception {
		
		return null;
	}

	@Override
	public OutputDto getBkltSplr(String access_token) throws Exception {
		
		return null;
	}

	/**
	 *
	 * @param splrId
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrAdmtForBuild(Long splrId)
			throws Exception {
		
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		// 建立申请  begin
		SplrAdmtAply splrAdmtAply = new SplrAdmtAply();
		Splr splr = splrMapper.selectByPrimaryKey(splrId);
		
		BeanUtils.copyProperties(splrAdmtAply, splr);
		
//		splrAdmtAply.setOrgId(Long.valueOf(userinfo.getOrgId()));
		splrAdmtAply.setSplrId(splrId);
//		splrAdmtAply.setSplrNam(splr.getFullNam());
		
		// 设置申请状态为 - 建立申请
		splrAdmtAply.setAplySts(Constants.SPLR_ADMT_APLY_STS_BUILD);
		
		int rowNum = splrAdmtAplyMapper.insertSelective(splrAdmtAply);
		
		if (rowNum == 1) {
			
			SplrOrg splrOrg = new SplrOrg();
			splrOrg.setSplrId(splrId);
			splrOrg.setOrgId(Long.valueOf(userinfo.getOrgId()));
			
			// 判断是否存在对应关系
			SplrOrgKey splrOrgKey = new SplrOrgKey();
			BeanUtils.copyProperties(splrOrgKey, splrOrg);
			SplrOrg splorg1 = splrOrgMapper.selectByPrimaryKey(splrOrgKey);
			if (splorg1 == null) {
				// 更新供应商组织关系表
				int rowNum1 = splrOrgMapper.insertSelective(splrOrg);
				
				if (rowNum1 == 1) {
					
				}
			}
			
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
		}
		
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR, RtnEnum.FAIL_OPR.getDesc());
	}

	@Override
	public OutputDto splrAdmtList(String access_token, Integer pageNo, Integer pageSize) throws Exception {
		
		if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize;

        Map map = PageUtils.getQueryCondsMap(null, start, pageSize);
		
//        List<SplrAdmtAply> splrAdmtAplies = splrAdmtAplyMapper.selectCondition(map);
//        Integer count = splrAdmtAplyMapper.selectConditionCount(map);
        
//        PagedResult result = new PagedResult(splrAdmtAplies, count);
        
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, null);
	}

	@Override
	public OutputDto splrAdmtForMat(Long headId,
			List<Mat> mats) throws Exception {
		
		List<SplrAdmtSplMat> splrAdmtSplMats = new ArrayList<SplrAdmtSplMat>();
		for (Mat mat : mats) {
			SplrAdmtSplMat splrAdmtSplMat = new SplrAdmtSplMat();
			BeanUtils.copyProperties(splrAdmtSplMat, mat);
			// 设置准入申请头表ID
			splrAdmtSplMat.setHeaderId(headId);
			splrAdmtSplMat.setMatTypCod(mat.getMatTyp());
			splrAdmtSplMat.setMatUnt(mat.getUnt());
			
			splrAdmtSplMats.add(splrAdmtSplMat);
		}
		
		int rowNum = splrAdmtSplMatMapper.inserts(splrAdmtSplMats);
		if (rowNum == splrAdmtSplMats.size()) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
		}
		
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR, RtnEnum.FAIL_OPR.getDesc());
	}

	@Override
	public OutputDto getSplrChrm() throws Exception {
		
		List<SplrChrmDto> splrChrmDtos = splrChrmInfoMapper.selectForChrm();
		Map<String, List<String>> chrms = new ConcurrentHashMap<String, List<String>>();
		
		for (SplrChrmDto splrChrmDto : splrChrmDtos) {
			if (splrChrmDto.getSplrNam() == null) {
				continue;
			}
			// 已供应商名字为主键
			if (chrms.get(splrChrmDto.getSplrNam()) == null) {
				List<String> chrmIcons = new ArrayList<String>();
				chrmIcons.add(Constants.SPLR_CHRM_FILE_PATH + "/" + splrChrmDto.getChrmIcon());
				chrms.put(splrChrmDto.getSplrNam(), chrmIcons);
			} else {
				chrms.get(splrChrmDto.getSplrNam()).add(Constants.SPLR_CHRM_FILE_PATH + "/" + splrChrmDto.getChrmIcon());
			}
		}
		
		
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR, chrms);
	}

	@Override
	public OutputDto getSplrInfo(Long id) throws Exception {

		// 查询供应商信息
		Splr splr = splrMapper.selectByPrimaryKey(id);

		Map map = PageUtils.getQueryCondsMap(null, 1, 1000);
		map.put("splrId", id);

		// 查询供应商银行账号信息
		List<SplrBnkAcnt> splrBnkAcnts = splrBnkAcntMapper.selectByBnkConditions(map);
		List<SplrBnkAcntVo> splrBnkAcntVos = new ArrayList<SplrBnkAcntVo>();
		for (SplrBnkAcnt splrBnkAcnt : splrBnkAcnts) {
			SplrBnkAcntVo splrBnkAcntVo = new SplrBnkAcntVo();
			BeanUtils.copyProperties(splrBnkAcntVo, splrBnkAcnt);
			splrBnkAcntVos.add(splrBnkAcntVo);
		}

		// 查询供应商资质文件信息
		List<SplrAptInfo> splrAptInfos = splrAptMapper.splrAptList(splr
				.getSplrId(), splr.getClassification());

		//查询供应商考察表文件信息
		SplrAdmtChk splrAdmtChk = new SplrAdmtChk();
		splrAdmtChk.setSplrId(id);
		Map chkMap = PageUtils.getQueryCondsMap(splrAdmtChk,0,0);
		List<SplrAdmtChk> splrAdmtChks = splrAdmtChkMapper.selectByMap(chkMap);

		SplrInfoVo splrInfoVo = new SplrInfoVo();
		BeanUtils.copyProperties(splrInfoVo, splr);
		splrInfoVo.setSplrBnkAcnts(splrBnkAcntVos);
		splrInfoVo.setSplrApts(splrAptInfos);
		splrInfoVo.setSplrAdmtChks(splrAdmtChks);
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR, splrInfoVo);
	}

	/**
	 * 根据状态获取供应商列表
	 * @param splrStatus
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrListByStatus(String splrStatus, Integer pageNo, Integer pageSize) throws Exception {

		// 检查用户
		if (!"".equals(checkAuth())) {
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}

		// 分页查询页码处理
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
			pageSize = Constants.PAGE_SIZE;
		}

		Integer start = (pageNo - 1) * pageSize + 1;

		Integer to = pageNo * pageSize;

		List<Splr> splrs = splrMapper.selectByStatus(splrStatus, start, to);
		List<SplrIoDto> splrIoDtos = new ArrayList<>();

		for(Splr splr :splrs){
			SplrIoDto splrIoDto = new SplrIoDto();
			splrIoDto.setFullNam(splr.getFullNam());
			splrIoDto.setShortNam(splr.getShortNam());
			splrIoDto.setSplrId(splr.getSplrId());
			splrIoDto.setSplrSts(splr.getSplrSts());
			splrIoDto.setSplrLevl(splr.getSplrLevl());
			SplrAplyDto splrAplyDto = new SplrAplyDto();
			SplrDvlpAply splrDvlpAply = new SplrDvlpAply();
			splrDvlpAply.setSplrId(splr.getSplrId());
			Map map = PageUtils.getQueryCondsMap(splrDvlpAply,0,0);
			List<SplrDvlpAply> splrDvlpAplies = splrDvlpAplyMapper.selectByCondition(map);
			if(splrDvlpAplies!=null&&!splrDvlpAplies.isEmpty()){
				splrDvlpAplies.sort(Comparator.comparing(SplrDvlpAply::getAplyId));
				if(Constants.SPLR_ADT_STS_FAIL.equals(splrDvlpAplies.get(splrDvlpAplies.size()-1).getAplySts())){
					splrIoDto.setAdtMsg(splrDvlpAplies.get(splrDvlpAplies.size()-1).getAdtMsg());
				}
			}
			splrIoDtos.add(splrIoDto);
		}
		Integer count = splrMapper.selectByStatusCount(splrStatus);

		PagedResult result = new PagedResult(splrIoDtos, count);

		if (splrs != null && splrs.size() > 0) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, result);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA, RtnEnum.SUC_WITH_NO_DATA.getDesc());
	}

	@Override
	public OutputDto changeStsForSplr(Splr splr) throws Exception {

		Splr mSplr = new Splr();
		mSplr.setSplrId(splr.getSplrId());
		mSplr.setSplrSts(splr.getSplrSts());

		splrMapper.updateByPrimaryKeySelective(mSplr);

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR);
	}

	@Override
	public boolean checkSplrForQualified(Long splrId) throws Exception {
		Splr splr = splrMapper.selectByPrimaryKey(splrId);
		if (null != splr) {
			if (Constants.SPLR_STS_QUALIFIED.equals(splr.getSplrSts()) || Constants.SPLR_ADMT_APLY_STS_CHK.equals(splr.getSplrSts())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public OutputDto resetPasswd(Long splrId) {
		// 检查用户
		if (!"".equals(checkAuth())) {
			return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
		}
		CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
		Splr splr = splrMapper.selectByPrimaryKey(splrId);

		// 查询主账号
		Map map2 = new HashMap();
		map2.put("splrId", splrId);
		map2.put("acntTyp", Constants.SPLR_ACNT_ADMIN);
		map2.put("defFlg", Constants.DEL_FLG_NODEL);
		List<SplrAcnt> splrAcnts = splrAcntMapper.selectAcntsByConditions(map2);
		if (null != splrAcnts && splrAcnts.size() > 0) {

			// 重置密码
			SplrAcnt splrAcnt = new SplrAcnt();
			splrAcnt.setAcntId(splrAcnts.get(0).getAcntId());
			splrAcnt.setAccessToken(UUID.randomUUID().toString());
			splrAcnt.setModUsr(Long.valueOf(userinfo.getUserid()));
			splrAcnt.setModTim(DateUtils.getCurrentTimeStamp());
			splrAcnt.setPasswd("888888");
			splrAcntMapper.updateByPrimaryKeySelective(splrAcnt);

			Map map = new HashMap();
			if (splrAcnts.get(0).getUsrNam() != null) {
				map.put("usrNam", splrAcnts.get(0).getUsrNam());
			}
			map.put("passwd", "888888");

			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR,
					map);
		}

		SplrAcnt splrAcnt = new SplrAcnt();
		splrAcnt.setSplrId(splrId);
		String access_token = UUID.randomUUID().toString();
		splrAcnt.setAccessToken(access_token);
		splrAcnt.setAcntTyp(Constants.SPLR_ACNT_ADMIN);
		splrAcnt.setCrtUsr(Long.valueOf(userinfo.getUserid()));
		if (splr.getPtnrCod() != null) {
			splrAcnt.setUsrNam(splr.getPtnrCod());
		} else {
			logger.info("供应商重置账号失败  - 客商编码为空！");
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.FAIL_OPR, "客商编码为空");
		}
		splrAcnt.setPasswd("888888");

		splrAcnt.setDffFlg(Constants.DEL_FLG_NODEL);
		splrAcnt.setCrtTim(DateUtils.getCurrentTimeStamp());
		int rowNum2 = splrAcntMapper.insert(splrAcnt);
		if (rowNum2 != 1) {
			logger.info("供应商重置账号失败  - 创建供应商管理员账户失败！");
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.FAIL_OPR);
		}

		Map map = new HashMap();
		if (splr.getPtnrCod() != null) {
			map.put("usrNam", splr.getPtnrCod());
		}
		map.put("passwd", "888888");

		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR,
				map);
	}

}
