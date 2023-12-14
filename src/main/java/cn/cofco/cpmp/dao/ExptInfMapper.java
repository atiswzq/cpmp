package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.ExptInf;

import java.util.List;
import java.util.Map;

public interface ExptInfMapper {
    int deleteByPrimaryKey(Long exptId);

    int insert(ExptInf record);

    int insertSelective(ExptInf record);

    ExptInf selectByPrimaryKey(Long exptId);

    int updateByPrimaryKeySelective(ExptInf record);

    int updateByPrimaryKey(ExptInf record);

    List<ExptInf> selectByMap(Map map);

    int countByMap(Map map);
}