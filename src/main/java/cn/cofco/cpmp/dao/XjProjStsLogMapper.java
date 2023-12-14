package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.XjProjStsLog;

import java.util.List;
import java.util.Map;

public interface XjProjStsLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XjProjStsLog record);

    int insertSelective(XjProjStsLog record);

    XjProjStsLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XjProjStsLog record);

    int updateByPrimaryKey(XjProjStsLog record);

    List<XjProjStsLog> selectByMap(Map map);
}
