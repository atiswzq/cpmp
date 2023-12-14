package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.KpiTypDef;

public interface KpiTypDefMapper {
    int deleteByPrimaryKey(Long kpiTypId);

    int insert(KpiTypDef record);

    int insertSelective(KpiTypDef record);

    KpiTypDef selectByPrimaryKey(Long kpiTypId);

    int updateByPrimaryKeySelective(KpiTypDef record);

    int updateByPrimaryKey(KpiTypDef record);
}