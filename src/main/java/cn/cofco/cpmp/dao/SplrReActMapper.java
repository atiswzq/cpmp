package cn.cofco.cpmp.dao;

import java.util.List;
import java.util.Map;

import cn.cofco.cpmp.entity.SplrReActive;

public interface SplrReActMapper {
	int deleteByPrimaryKey(Long aplyId);

	int insert(SplrReActive record);

	int insertSelective(SplrReActive record);

	SplrReActive selectByPrimaryKey(Long aplyId);

	int updateByPrimaryKeySelective(SplrReActive record);

	int updateByPrimaryKey(SplrReActive record);

	List<SplrReActive> selectByCondition(Map map);

}