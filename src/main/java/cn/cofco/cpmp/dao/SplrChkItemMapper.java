package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrChkItem;

import java.util.List;

public interface SplrChkItemMapper {
    int deleteByPrimaryKey(Long chkItemId);

    int insert(SplrChkItem record);

    int insertSelective(SplrChkItem record);

    SplrChkItem selectByPrimaryKey(Long chkItemId);

    int updateByPrimaryKeySelective(SplrChkItem record);

    int updateByPrimaryKey(SplrChkItem record);

    /**
     * 批量插入数据
     * @param splrChkItems
     * @return
     */
    int inserts(List<SplrChkItem> splrChkItems);
}