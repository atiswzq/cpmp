package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOnWinDtl;

import java.util.List;
import java.util.Map;

public interface BidProjOnWinDtlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOnWinDtl record);

    int insertSelective(BidProjOnWinDtl record);

    BidProjOnWinDtl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOnWinDtl record);

    int updateByPrimaryKey(BidProjOnWinDtl record);

    List<BidProjOnWinDtl> selectByMap(Map map);

    List<BidProjOnWinDtl> selectAwdSplrsByProjId(Long projId);

    int deleteByProjId(Long projId);
}