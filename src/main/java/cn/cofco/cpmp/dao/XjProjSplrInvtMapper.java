package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.XjProjSplrInvt;

import java.util.List;
import java.util.Map;

public interface XjProjSplrInvtMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XjProjSplrInvt record);

    int insertSelective(XjProjSplrInvt record);

    XjProjSplrInvt selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XjProjSplrInvt record);

    int updateByPrimaryKey(XjProjSplrInvt record);

    int deleteByProjId(Long projId);

    List<XjProjSplrInvt> selectByEntity(Map map);
}
