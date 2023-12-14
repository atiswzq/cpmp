package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.ChkTyp;

public interface ChkTypMapper {
    int deleteByPrimaryKey(Long chkTypId);

    int insert(ChkTyp record);

    int insertSelective(ChkTyp record);

    ChkTyp selectByPrimaryKey(Long chkTypId);

    int updateByPrimaryKeySelective(ChkTyp record);

    int updateByPrimaryKey(ChkTyp record);
}