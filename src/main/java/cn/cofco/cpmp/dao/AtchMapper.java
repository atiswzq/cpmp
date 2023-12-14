package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.Atch;

import java.util.List;
import java.util.Map;

public interface AtchMapper {
    int deleteByPrimaryKey(Long atchId);

    int insert(Atch record);

    int insertSelective(Atch record);

    Atch selectByPrimaryKey(Long atchId);

    int updateByPrimaryKeySelective(Atch record);

    int updateByPrimaryKey(Atch record);

    int countByMap(Map map);

    List<Atch> selectByMap(Map map);

    int deleteByRefId(String refId);
}