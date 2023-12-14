package cn.cofco.cpmp.dao;


import cn.cofco.cpmp.entity.XjProjWinDtl;

import java.util.List;
import java.util.Map;

public interface XjProjWinDtlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XjProjWinDtl record);

    int insertSelective(XjProjWinDtl record);

    XjProjWinDtl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XjProjWinDtl record);

    int updateByPrimaryKey(XjProjWinDtl record);

    List<XjProjWinDtl> selectByMap(Map map);

    List<XjProjWinDtl> selectAwdSplrsByProjId(Long projId);

    int deleteByProjId(Long projId);
}
