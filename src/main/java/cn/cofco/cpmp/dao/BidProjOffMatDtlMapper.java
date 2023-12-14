package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOffMatDtl;

import java.util.List;
import java.util.Map;

public interface BidProjOffMatDtlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOffMatDtl record);

    int insertSelective(BidProjOffMatDtl record);

    BidProjOffMatDtl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOffMatDtl record);

    int updateByPrimaryKey(BidProjOffMatDtl record);

    List<BidProjOffMatDtl> selectByEntity(Map map);
}