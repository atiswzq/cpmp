package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrBklt;

public interface SplrBkltMapper {
    int deleteByPrimaryKey(Long aplyId);

    int insert(SplrBklt record);

    int insertSelective(SplrBklt record);

    SplrBklt selectByPrimaryKey(Long aplyId);

    int updateByPrimaryKeySelective(SplrBklt record);

    int updateByPrimaryKey(SplrBklt record);
}