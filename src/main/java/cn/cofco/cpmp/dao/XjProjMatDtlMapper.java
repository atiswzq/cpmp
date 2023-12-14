package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.XjProjMatDtl;

import java.util.List;
import java.util.Map;

public interface XjProjMatDtlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XjProjMatDtl record);

    int insertSelective(XjProjMatDtl record);

    XjProjMatDtl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XjProjMatDtl record);

    int updateByPrimaryKey(XjProjMatDtl record);

    List<XjProjMatDtl> selectByMap(Map map);
}
