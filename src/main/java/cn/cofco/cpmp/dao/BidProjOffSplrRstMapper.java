package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOffSplrRst;

import java.util.List;
import java.util.Map;

public interface BidProjOffSplrRstMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOffSplrRst record);

    int insertSelective(BidProjOffSplrRst record);

    BidProjOffSplrRst selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOffSplrRst record);

    int updateByPrimaryKey(BidProjOffSplrRst record);

    List<BidProjOffSplrRst> selectByMap(Map map);


}