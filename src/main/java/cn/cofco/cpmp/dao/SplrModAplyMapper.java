package cn.cofco.cpmp.dao;

import java.util.List;

import cn.cofco.cpmp.entity.SplrModAply;

public interface SplrModAplyMapper {
    int deleteByPrimaryKey(Long modId);

    int insert(SplrModAply record);

    int insertSelective(SplrModAply record);

    SplrModAply selectByPrimaryKey(Long modId);

    int updateByPrimaryKeySelective(SplrModAply record);

    int updateByPrimaryKey(SplrModAply record);
    
    int insertSelectives(List<SplrModAply> splrModAplies);
}