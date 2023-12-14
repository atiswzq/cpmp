package cn.cofco.cpmp.dao;

import java.util.List;
import java.util.Map;

import cn.cofco.cpmp.entity.SplrWdot;
import cn.cofco.cpmp.splr.dto.SplrListDto;
import cn.cofco.cpmp.splr.dto.SplrWdotDto;

public interface SplrWdotMapper {
	int deleteByPrimaryKey(Long aplyId);

	int insert(SplrWdot record);

	int insertSelective(SplrWdot record);

	SplrWdot selectByPrimaryKey(Long aplyId);

	int updateByPrimaryKeySelective(SplrWdot record);

	int updateByPrimaryKey(SplrWdot record);

	int wdotListCountByMap(Map map);

	int splrListCountByMap(Map map);

	List<SplrWdot> selectByCondition(Map map);

	/**
	 * 供应商淘汰列表
	 * 
	 * @param orgId
	 *            不同的登录用户(营业员)显示的淘汰列表信息不一样,属于同一公司的营业员登录显示相同的淘汰信息
	 * 
	 * @return
	 */
	List<SplrWdotDto> selectWdotsList(Map map);

	void deleteBySplrIdAndOrg(Map map);

	/**
	 * 获取供应商信息列表,包含供应商淘汰申请理由
	 * 
	 * @param map
	 * @return
	 */
	List<SplrListDto> selectSplrList(Map map);
}