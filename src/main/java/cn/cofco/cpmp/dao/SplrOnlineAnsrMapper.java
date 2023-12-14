package cn.cofco.cpmp.dao;

import java.util.List;
import java.util.Map;

import cn.cofco.cpmp.entity.SplrOnlineAnsr;
import cn.cofco.cpmp.splr.dto.SplrCountDto;
import cn.cofco.cpmp.splr.dto.SplrOnlineDto;

public interface SplrOnlineAnsrMapper {

	int insert(SplrOnlineAnsr record);

	List<SplrOnlineAnsr> findBySplrIdAndProjId(Long splrId, Long projId);

	List<SplrOnlineDto> findOpenContList(Map map);

	SplrOnlineAnsr findAnsrByMid(Long miId);

	SplrOnlineAnsr findQueByMidSplrIdProjId(Map map);

	void update(SplrOnlineAnsr record);

	List<SplrOnlineDto> selectByCondition(Map map);

	List<SplrCountDto> selectSplrCountByProjId(Long projId);

}