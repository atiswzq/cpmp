package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.XjProjSplrTendInf;

import java.util.List;
import java.util.Map;

public interface XjProjSplrTendInfMapper {

    int deleteByPrimaryKey(Long id);

    int insert(XjProjSplrTendInf record);

    int insertSelective(XjProjSplrTendInf record);

    XjProjSplrTendInf selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XjProjSplrTendInf record);

    int updateByPrimaryKey(XjProjSplrTendInf record);

    int countByMap(Map map);

    List<XjProjSplrTendInf> selectByMap(Map map);
}
