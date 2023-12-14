package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrAptModAply;

public interface SplrAptModAplyMapper {
    int deleteByPrimaryKey(Long aplyId);

    int insert(SplrAptModAply record);

    int insertSelective(SplrAptModAply record);

    SplrAptModAply selectByPrimaryKey(Long aplyId);

    int updateByPrimaryKeySelective(SplrAptModAply record);

    int updateByPrimaryKey(SplrAptModAply record);
}