package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.LnkInf;

import java.util.List;
import java.util.Map;

public interface LnkInfMapper {
    int deleteByPrimaryKey(Long lnkId);

    int insert(LnkInf record);

    int insertSelective(LnkInf record);

    LnkInf selectByPrimaryKey(Long lnkId);

    int updateByPrimaryKeySelective(LnkInf record);

    int updateByPrimaryKey(LnkInf record);

    int countByMap(Map map);

    List<LnkInf> selectByMap(Map map);
}