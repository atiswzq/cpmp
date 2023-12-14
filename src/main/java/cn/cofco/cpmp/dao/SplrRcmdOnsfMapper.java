package cn.cofco.cpmp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cofco.cpmp.entity.SplrRcmdOnsf;

public interface SplrRcmdOnsfMapper {
    int deleteByPrimaryKey(Long rcmdOnsfId);

    int insert(SplrRcmdOnsf record);

    int insertSelective(SplrRcmdOnsf record);

    SplrRcmdOnsf selectByPrimaryKey(Long rcmdOnsfId);

    int updateByPrimaryKeySelective(SplrRcmdOnsf record);

    int updateByPrimaryKey(SplrRcmdOnsf record);
    
    List<SplrRcmdOnsf> selectByCondition(@Param("splrId")Long splrId, @Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);
    
    int selectByConditionCount(@Param("splrId")Long splrId);
}