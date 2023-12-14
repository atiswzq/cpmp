package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOnExptGrdDtl;

import java.util.List;
import java.util.Map;

public interface BidProjOnExptGrdDtlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOnExptGrdDtl record);

    int insertSelective(BidProjOnExptGrdDtl record);

    BidProjOnExptGrdDtl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOnExptGrdDtl record);

    int updateByPrimaryKey(BidProjOnExptGrdDtl record);

    int countByMap(Map map);

    List<BidProjOnExptGrdDtl> selectByMap(Map map);
}