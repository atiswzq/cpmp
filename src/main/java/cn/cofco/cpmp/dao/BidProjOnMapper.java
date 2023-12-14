package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOn;

import java.util.List;
import java.util.Map;

public interface BidProjOnMapper {
    int deleteByPrimaryKey(Long projId);

    int insert(BidProjOn record);

    int insertSelective(BidProjOn record);

    BidProjOn selectByPrimaryKey(Long projId);

    int updateByPrimaryKeySelective(BidProjOn record);

    int updateByPrimaryKeyWithBLOBs(BidProjOn record);

    int updateByPrimaryKey(BidProjOn record);

    int countByMap(Map map);

    List<BidProjOn> selectByMap(Map map);

    int countByMapOfVctInvt(Map map);

    List<BidProjOn> selectByMapOfVctInvt(Map map);
}