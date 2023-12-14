package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SysParm;
import cn.cofco.cpmp.entity.SysParmKey;

import java.util.List;

public interface SysParmMapper {
    int deleteByPrimaryKey(SysParmKey key);

    int insert(SysParm record);

    int insertSelective(SysParm record);

    SysParm selectByPrimaryKey(SysParmKey key);

    List<SysParm> selectAll();

    int updateByPrimaryKeySelective(SysParm record);

    int updateByPrimaryKey(SysParm record);
}