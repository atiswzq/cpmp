package cn.cofco.cpmp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cofco.cpmp.dao.SplrAcntMapper;
import cn.cofco.cpmp.dao.SplrMapper;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.entity.SplrAcnt;
import cn.cofco.cpmp.service.ISplrLoginService;
import cn.cofco.cpmp.utils.BeanUtils;
import cn.cofco.cpmp.utils.CurrentSplrUserInfo;
import cn.cofco.cpmp.utils.SplrContextTools;

@Service
public class SplrLoginServiceImpl implements ISplrLoginService {

	@Resource
	private SplrAcntMapper splrAcntMapper;
	
	@Resource
	private SplrMapper splrMapper;
	
	@Override
	public CurrentSplrUserInfo currentUserInfo(String access_token) {
		
		SplrAcnt splrAcnt = splrAcntMapper.selectByToken(access_token);
		
		Splr splr = splrMapper.selectByPrimaryKey(splrAcnt.getSplrId());
		
		CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
		if (splrAcnt != null) {
			BeanUtils.copyProperties(currentSplrUserInfo, splrAcnt);
			currentSplrUserInfo.setFullNam(splr.getFullNam());
		}
		return currentSplrUserInfo;
	}

}
