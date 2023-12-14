package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.CodRulInf;

import java.util.List;
import java.util.Map;

public interface CodRulInfMapper {
    int deleteByPrimaryKey(Long codId);

    int insert(CodRulInf record);

    int insertSelective(CodRulInf record);

    CodRulInf selectByPrimaryKey(Long codId);

    int updateByPrimaryKeySelective(CodRulInf record);

    int updateByPrimaryKey(CodRulInf record);

    List<CodRulInf> selectByMap(Map map);
}