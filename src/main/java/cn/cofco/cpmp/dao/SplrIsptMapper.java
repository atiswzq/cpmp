package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrIspt;

public interface SplrIsptMapper {
    int deleteByPrimaryKey(Long isptId);

    int insert(SplrIspt record);

    int insertSelective(SplrIspt record);

    SplrIspt selectByPrimaryKey(Long isptId);

    int updateByPrimaryKeySelective(SplrIspt record);

    int updateByPrimaryKey(SplrIspt record);
}