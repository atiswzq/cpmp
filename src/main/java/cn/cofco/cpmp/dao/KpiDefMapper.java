package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.KpiDef;

public interface KpiDefMapper {
    int deleteByPrimaryKey(Long kpiId);

    int insert(KpiDef record);

    int insertSelective(KpiDef record);

    KpiDef selectByPrimaryKey(Long kpiId);

    int updateByPrimaryKeySelective(KpiDef record);

    int updateByPrimaryKey(KpiDef record);
}