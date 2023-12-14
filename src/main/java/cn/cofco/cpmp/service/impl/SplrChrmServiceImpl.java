package cn.cofco.cpmp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import cn.cofco.cpmp.dao.SplrChrmMapper;
import cn.cofco.cpmp.entity.SplrChrm;
import cn.cofco.cpmp.splr.vo.SplrChrmSetVo;
import cn.cofco.cpmp.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.SplrChrmInfoMapper;
import cn.cofco.cpmp.dao.SplrMapper;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.entity.SplrChrmInfo;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.service.ISplrChrmService;
import cn.cofco.cpmp.splr.dto.SplrChrmDto;
import cn.cofco.cpmp.splr.vo.ChrmListVo;
import cn.cofco.cpmp.splr.vo.SplrChrmInfoVo;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.BeanUtils;

@Service
@Transactional("transactionManager")
public class SplrChrmServiceImpl implements ISplrChrmService {

	@Resource
	private SplrChrmInfoMapper splrChrmInfoMapper;
	
	@Resource
	private SplrMapper splrMapper;

	@Resource
	private SplrChrmMapper splrChrmMapper;
	
	@Override
	public OutputDto getSplrChrms() throws Exception {
		
		List<SplrChrmDto> splrChrmDtos = splrChrmInfoMapper.selectForChrm();
		
		Map<Long, ChrmListVo> chrmMap = new ConcurrentHashMap<Long, ChrmListVo>();
		List<ChrmListVo> chrmListVos = new ArrayList<ChrmListVo>();
		for (SplrChrmDto splrChrmDto : splrChrmDtos) {
			if (splrChrmDto == null || splrChrmDto.getSplrId() == null) {
				continue;
			}
			if (chrmMap.get(splrChrmDto.getSplrId()) == null) {
				ChrmListVo chrmListVo = new ChrmListVo();
				chrmListVo.setSplrId(splrChrmDto.getSplrId());
				chrmListVo.setSplrNam(splrChrmDto.getSplrNam());
				List<Map<String, String>> iconItems = new ArrayList<Map<String,String>>();
				chrmListVo.setIconItems(iconItems);
				chrmMap.put(splrChrmDto.getSplrId(), chrmListVo);
			}
			Map<String, String> iconItem = new ConcurrentHashMap<String, String>();
			iconItem.put("chrmIcon", splrChrmDto.getChrmIcon() == null ? "": splrChrmDto.getChrmIcon());
			iconItem.put("iconTitle", splrChrmDto.getIconTitle() == null ? "": splrChrmDto.getIconTitle());
			iconItem.put("itdc", splrChrmDto.getItdc() == null ? "": splrChrmDto.getItdc());
			chrmMap.get(splrChrmDto.getSplrId()).getIconItems().add(iconItem);
		}
		for(Map.Entry<Long, ChrmListVo> entry : chrmMap.entrySet()) {
			chrmListVos.add(entry.getValue());
		}
		
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR, chrmListVos);
	}

	@Override
	public OutputDto getSplrChrmById(Long splrId) throws Exception {
		
		List<SplrChrmInfo> splrChrmInfos = splrChrmInfoMapper.select(splrId);
		Splr splr = splrMapper.selectByPrimaryKey(splrId);
		SplrChrmInfoVo splrChrmInfoVo = new SplrChrmInfoVo();
		BeanUtils.copyProperties(splrChrmInfoVo, splr);
		for (SplrChrmInfo splrChrmInfo : splrChrmInfos) {
			if (splrChrmInfoVo.getIconItems() == null) {
				List<Map<String, String>> iconItems = new ArrayList<Map<String,String>>();
				splrChrmInfoVo.setIconItems(iconItems);
			}
			Map<String, String> iconItem = new ConcurrentHashMap<String, String>();
			iconItem.put("chrmIcon", splrChrmInfo.getChrmIcon() == null ? "": splrChrmInfo.getChrmIcon());
			iconItem.put("iconTitle", splrChrmInfo.getIconTitle() == null ? "": splrChrmInfo.getIconTitle());
			splrChrmInfoVo.getIconItems().add(iconItem);
		}
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR, splrChrmInfoVo);
	}

	@Override
	public OutputDto setSplrChrms(SplrChrmSetVo splrChrmSetVo) throws Exception {


		Map map = PageUtils.getQueryCondsMap(null, 1, 1000);
		map.put("splrId", splrChrmSetVo.getSplrId());
		map.put("defFlg", "0");
		List<SplrChrm> baseSplrChrms = splrChrmMapper.selectByConditions(map);
		if (null != baseSplrChrms && baseSplrChrms.size() > 0) {
			for (SplrChrm splrChrm : baseSplrChrms) {
				splrChrm.setDefFlg("1");
				splrChrmMapper.updateByPrimaryKeySelective(splrChrm);
			}
		}

		// 更新供应商展示状态
		if (Constants.SPLR_CHRM_SHOW.equals(splrChrmSetVo.getChrmShowSts())) {
			Map map1 = PageUtils.getQueryCondsMap(null, 1, 1000);
			map1.put("defFlg", "0");
			List<SplrChrm> splrChrms = splrChrmMapper.selectByConditions(map1);
			if (splrChrms.size() <= 7) {
				SplrChrm splrChrm = new SplrChrm();
				splrChrm.setSplrId(splrChrmSetVo.getSplrId());
				splrChrm.setDefFlg("0");
				splrChrmMapper.insert(splrChrm);
			}  else {
				// 已达最大数量
				return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
						RtnEnum.USER_NUM_ERR, "已达最大展示数量！");
			}
		} else if (Constants.SPLR_CHRM_NO_SHOW.equals(splrChrmSetVo.getChrmShowSts())) {
			SplrChrm splrChrm = new SplrChrm();
			splrChrm.setSplrId(splrChrmSetVo.getSplrId());
			splrChrm.setDefFlg("1");
			splrChrmMapper.insert(splrChrm);
		} else {
			// 未知状态处理
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.ARG_INVALID, "未知状态！");
		}
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR);
	}

}
