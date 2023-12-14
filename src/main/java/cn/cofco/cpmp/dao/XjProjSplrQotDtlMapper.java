package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.XjProjSplrQotDtl;

import java.util.List;
import java.util.Map;

public interface XjProjSplrQotDtlMapper {

    int deleteByPrimaryKey(Long id);

    int insert(XjProjSplrQotDtl record);

    int insertSelective(XjProjSplrQotDtl record);

    XjProjSplrQotDtl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XjProjSplrQotDtl record);

    int updateByPrimaryKey(XjProjSplrQotDtl record);

    List<XjProjSplrQotDtl> selectByMap(Map map);
}
