package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.AptDef;

public interface AptDefMapper {
    int deleteByPrimaryKey(Long aptDefId);

    int insert(AptDef record);

    int insertSelective(AptDef record);

    AptDef selectByPrimaryKey(Long aptDefId);

    int updateByPrimaryKeySelective(AptDef record);

    int updateByPrimaryKey(AptDef record);
}