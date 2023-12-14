package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrDvlpAply;

import java.util.List;
import java.util.Map;

public interface SplrDvlpAplyMapper {
    int deleteByPrimaryKey(Long aplyId);

    int insert(SplrDvlpAply record);

    int insertSelective(SplrDvlpAply record);

    SplrDvlpAply selectByPrimaryKey(Long aplyId);

    int updateByPrimaryKeySelective(SplrDvlpAply record);

    int updateByPrimaryKey(SplrDvlpAply record);

    List<SplrDvlpAply> selectByCondition(Map map);
}