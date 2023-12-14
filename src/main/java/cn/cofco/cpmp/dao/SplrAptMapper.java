package cn.cofco.cpmp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cofco.cpmp.entity.SplrApt;
import cn.cofco.cpmp.splr.dto.SplrAptInfo;

public interface SplrAptMapper {
    int deleteByPrimaryKey(Long aptId);

    int insert(SplrApt record);

    int insertSelective(SplrApt record);

    SplrApt selectByPrimaryKey(Long aptId);

    int updateByPrimaryKeySelective(SplrApt record);

    int updateByPrimaryKey(SplrApt record);
    
	List<SplrAptInfo> splrAptList(@Param("splrId")Long splrId, @Param("splrTyp")String splrTyp);
	
	List<SplrApt> selectAptDefByConditions(@Param("splrTyp")String splrTyp);
	
	int inserts(List<SplrApt> splrApts);
}