package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOnSplrInvt;

import java.util.List;
import java.util.Map;

public interface BidProjOnSplrInvtMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOnSplrInvt record);

    int insertSelective(BidProjOnSplrInvt record);

    BidProjOnSplrInvt selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOnSplrInvt record);

    int updateByPrimaryKey(BidProjOnSplrInvt record);

    int deleteByProjId(Long projId);

    List<BidProjOnSplrInvt> selectByEntity(Map map);
}