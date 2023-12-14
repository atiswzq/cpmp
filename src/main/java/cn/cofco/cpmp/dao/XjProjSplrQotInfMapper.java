package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.XjProjSplrQotInf;

import java.util.List;
import java.util.Map;

public interface XjProjSplrQotInfMapper {

    int deleteByPrimaryKey(Long id);

    int insert(XjProjSplrQotInf record);

    int insertSelective(XjProjSplrQotInf record);

    XjProjSplrQotInf selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XjProjSplrQotInf record);

    int updateByPrimaryKey(XjProjSplrQotInf record);

    List<XjProjSplrQotInf> selectByMap(Map map);

    Integer countByMap(Map map);
}
