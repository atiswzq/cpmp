package cn.cofco.cpmp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.entity.SplrBnkAcnt;

public interface SplrBnkAcntMapper {
    int deleteByPrimaryKey(Long bnkAcntId);

    int insert(SplrBnkAcnt record);

    int insertSelective(SplrBnkAcnt record);

    SplrBnkAcnt selectByPrimaryKey(Long bnkAcntId);

    int updateByPrimaryKeySelective(SplrBnkAcnt record);

    int updateByPrimaryKey(SplrBnkAcnt record);
    
    List<SplrBnkAcnt> selectByConditions(@Param("splrId")Long splrId);
    
    /**
     * 按条件搜索
     * @param map
     * @return
     */
    List<SplrBnkAcnt> selectByBnkConditions(Map map);
    
    Integer countOfMap(Map map);
    
    int inserts(List<SplrBnkAcnt> splrBnkAcnts);
    
    int deleteBySplrId(@Param("splrId")Long splrId);
}