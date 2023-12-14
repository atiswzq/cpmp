package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrAptDef;

public interface SplrAptDefMapper {
    int deleteByPrimaryKey(Long splrAptId);

    int insert(SplrAptDef record);

    int insertSelective(SplrAptDef record);

    SplrAptDef selectByPrimaryKey(Long splrAptId);

    int updateByPrimaryKeySelective(SplrAptDef record);

    int updateByPrimaryKey(SplrAptDef record);
    
    
}