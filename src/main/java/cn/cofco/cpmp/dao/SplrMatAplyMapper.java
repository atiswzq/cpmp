package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrMatAply;

public interface SplrMatAplyMapper {
    int deleteByPrimaryKey(Long aplyId);

    int insert(SplrMatAply record);

    int insertSelective(SplrMatAply record);

    SplrMatAply selectByPrimaryKey(Long aplyId);

    int updateByPrimaryKeySelective(SplrMatAply record);

    int updateByPrimaryKey(SplrMatAply record);
}