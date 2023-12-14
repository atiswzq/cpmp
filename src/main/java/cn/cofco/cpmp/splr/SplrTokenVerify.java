package cn.cofco.cpmp.splr;

import javax.annotation.Resource;

import cn.cofco.cpmp.dao.SplrAcntMapper;
import cn.cofco.cpmp.entity.SplrAcnt;

public class SplrTokenVerify {
	
	@Resource
	private SplrAcntMapper splrAcntMapper;
	
	public SplrAcnt tokenVerify(String accessToken) {
		
		return splrAcntMapper.selectByToken(accessToken);
	}
}

