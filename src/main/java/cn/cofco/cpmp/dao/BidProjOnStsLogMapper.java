package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOnStsLog;

import java.util.List;
import java.util.Map;

public interface BidProjOnStsLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOnStsLog record);

    int insertSelective(BidProjOnStsLog record);

    BidProjOnStsLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOnStsLog record);

    int updateByPrimaryKey(BidProjOnStsLog record);

    List<BidProjOnStsLog> selectByMap(Map map);
}