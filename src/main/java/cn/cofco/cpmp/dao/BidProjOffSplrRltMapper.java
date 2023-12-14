package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOffSplrRlt;

import java.util.List;
import java.util.Map;

public interface BidProjOffSplrRltMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOffSplrRlt record);

    int insertSelective(BidProjOffSplrRlt record);

    BidProjOffSplrRlt selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOffSplrRlt record);

    int updateByPrimaryKey(BidProjOffSplrRlt record);

    int deleteByProjId(Long projId);

    List<BidProjOffSplrRlt> selectByEntity(Map map);
}