package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.ChkItem;

public interface ChkItemMapper {
    int deleteByPrimaryKey(Long chkItemId);

    int insert(ChkItem record);

    int insertSelective(ChkItem record);

    ChkItem selectByPrimaryKey(Long chkItemId);

    int updateByPrimaryKeySelective(ChkItem record);

    int updateByPrimaryKey(ChkItem record);
}