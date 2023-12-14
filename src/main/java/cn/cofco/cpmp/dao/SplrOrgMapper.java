package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrOrg;
import cn.cofco.cpmp.entity.SplrOrgKey;

public interface SplrOrgMapper {
    int deleteByPrimaryKey(SplrOrgKey key);

    int insert(SplrOrg record);

    int insertSelective(SplrOrg record);

    SplrOrg selectByPrimaryKey(SplrOrgKey key);

    int updateByPrimaryKeySelective(SplrOrg record);

    int updateByPrimaryKey(SplrOrg record);
}