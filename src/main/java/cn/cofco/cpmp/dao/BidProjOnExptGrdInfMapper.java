package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BidProjOnExptGrdInf;

import java.util.List;
import java.util.Map;

public interface BidProjOnExptGrdInfMapper {
    int deleteByPrimaryKey(Long grdId);

    int insert(BidProjOnExptGrdInf record);

    int insertSelective(BidProjOnExptGrdInf record);

    BidProjOnExptGrdInf selectByPrimaryKey(Long grdId);

    int updateByPrimaryKeySelective(BidProjOnExptGrdInf record);

    int updateByPrimaryKey(BidProjOnExptGrdInf record);

    int countByMap(Map map);

    List<BidProjOnExptGrdInf> selectByMap(Map map);
}