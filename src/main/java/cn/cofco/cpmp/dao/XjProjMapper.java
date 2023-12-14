package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.XjProj;

import java.util.List;
import java.util.Map;

public interface XjProjMapper {
    int deleteByPrimaryKey(Long projId);

    int insert(XjProj record);

    int insertSelective(XjProj record);

    XjProj selectByPrimaryKey(Long projId);

    int updateByPrimaryKeySelective(XjProj record);

    int updateByPrimaryKeyWithBLOBs(XjProj record);

    int updateByPrimaryKey(XjProj record);

    int countByMap(Map map);

    List<XjProj> selectByMap(Map map);

    int countByMapOfVctInvt(Map map);

    List<XjProj> selectByMapOfVctInvt(Map map);
}
