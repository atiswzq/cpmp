package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOnSplrTendInf;

import java.util.List;
import java.util.Map;

public interface BidProjOnSplrTendInfMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOnSplrTendInf record);

    int insertSelective(BidProjOnSplrTendInf record);

    BidProjOnSplrTendInf selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOnSplrTendInf record);

    int updateByPrimaryKey(BidProjOnSplrTendInf record);

    int countByMap(Map map);

    List<BidProjOnSplrTendInf> selectByMap(Map map);
}