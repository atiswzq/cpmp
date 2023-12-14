package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOffSplrInf;

import java.util.List;
import java.util.Map;

public interface BidProjOffSplrInfMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOffSplrInf record);

    int insertSelective(BidProjOffSplrInf record);

    BidProjOffSplrInf selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOffSplrInf record);

    int updateByPrimaryKey(BidProjOffSplrInf record);

    int countByMap(Map map);

    List<BidProjOffSplrInf> selectByMap(Map map);
}