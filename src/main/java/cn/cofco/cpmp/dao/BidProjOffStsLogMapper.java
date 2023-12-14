package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOffStsLog;

import java.util.List;
import java.util.Map;

public interface BidProjOffStsLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOffStsLog record);

    int insertSelective(BidProjOffStsLog record);

    BidProjOffStsLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOffStsLog record);

    int updateByPrimaryKey(BidProjOffStsLog record);

    List<BidProjOffStsLog> selectByMap(Map map);
}