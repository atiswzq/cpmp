package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrAcntLgin;

public interface SplrAcntLginMapper {
    int deleteByPrimaryKey(Long lginId);

    int insert(SplrAcntLgin record);

    int insertSelective(SplrAcntLgin record);

    SplrAcntLgin selectByPrimaryKey(Long lginId);

    int updateByPrimaryKeySelective(SplrAcntLgin record);

    int updateByPrimaryKey(SplrAcntLgin record);
}