package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.Adv;

public interface AdvMapper {
    int deleteByPrimaryKey(Long advId);

    int insert(Adv record);

    int insertSelective(Adv record);

    Adv selectByPrimaryKey(Long advId);

    int updateByPrimaryKeySelective(Adv record);

    int updateByPrimaryKey(Adv record);
}