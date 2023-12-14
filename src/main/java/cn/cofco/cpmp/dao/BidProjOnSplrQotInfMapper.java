package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOnSplrQotInf;

import java.util.List;
import java.util.Map;

public interface BidProjOnSplrQotInfMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOnSplrQotInf record);

    int insertSelective(BidProjOnSplrQotInf record);

    BidProjOnSplrQotInf selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOnSplrQotInf record);

    int updateByPrimaryKey(BidProjOnSplrQotInf record);

    List<BidProjOnSplrQotInf> selectByMap(Map map);

    Integer countByMap(Map map);
}