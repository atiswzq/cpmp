package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOnMatDtl;

import java.util.List;
import java.util.Map;

public interface BidProjOnMatDtlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BidProjOnMatDtl record);

    int insertSelective(BidProjOnMatDtl record);

    BidProjOnMatDtl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidProjOnMatDtl record);

    int updateByPrimaryKey(BidProjOnMatDtl record);

    List<BidProjOnMatDtl> selectByMap(Map map);
}