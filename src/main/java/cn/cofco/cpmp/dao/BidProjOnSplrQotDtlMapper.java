package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOnSplrQotDtl;

import java.util.List;
import java.util.Map;

public interface BidProjOnSplrQotDtlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOnSplrQotDtl record);

    int insertSelective(BidProjOnSplrQotDtl record);

    BidProjOnSplrQotDtl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOnSplrQotDtl record);

    int updateByPrimaryKey(BidProjOnSplrQotDtl record);

    List<BidProjOnSplrQotDtl> selectByMap(Map map);
}