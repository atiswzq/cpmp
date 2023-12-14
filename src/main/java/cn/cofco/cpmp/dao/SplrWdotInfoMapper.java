package cn.cofco.cpmp.dao;

import java.util.Map;

import cn.cofco.cpmp.entity.SplrWdotInfo;

public interface SplrWdotInfoMapper {
	int deleteByPrimaryKey(Long aplyId);

	int insert(SplrWdotInfo record);

	int insertSelective(SplrWdotInfo record);

	SplrWdotInfo selectByPrimaryKey(Long aplyId);

	int updateByPrimaryKeySelective(SplrWdotInfo record);

	int updateByPrimaryKey(SplrWdotInfo record);

	void deleteBySplrIdAndOrg(Map map);

}