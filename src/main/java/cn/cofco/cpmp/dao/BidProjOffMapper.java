package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOff;
import cn.cofco.cpmp.entity.BidProjOffWithBLOBs;

import java.util.List;
import java.util.Map;

public interface BidProjOffMapper {
    int deleteByPrimaryKey(Long projId);

    int insert(BidProjOffWithBLOBs record);

    int insertSelective(BidProjOffWithBLOBs record);

    BidProjOffWithBLOBs selectByPrimaryKey(Long projId);

    int updateByPrimaryKeySelective(BidProjOffWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BidProjOffWithBLOBs record);

    int updateByPrimaryKey(BidProjOff record);

    int countByEntity(Map map);

    List<BidProjOff> selectByEntity(Map map);

    int countByMapOfVctInvt(Map map);

    List<BidProjOff> selectByMapOfVctInvt(Map map);
}