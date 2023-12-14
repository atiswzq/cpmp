package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOnExptGrdDtlLow;

import java.util.List;
import java.util.Map;

public interface BidProjOnExptGrdDtlLowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOnExptGrdDtlLow record);

    int insertSelective(BidProjOnExptGrdDtlLow record);

    BidProjOnExptGrdDtlLow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOnExptGrdDtlLow record);

    int updateByPrimaryKey(BidProjOnExptGrdDtlLow record);

    int countByMap(Map map);

    List<BidProjOnExptGrdDtlLow> selectByMap(Map map);
}