package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.entity.SplrChrm;

import java.util.List;
import java.util.Map;

public interface SplrChrmMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SplrChrm record);

    int insertSelective(SplrChrm record);

    SplrChrm selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SplrChrm record);

    int updateByPrimaryKey(SplrChrm record);

    /**
     * 根据条件查找风采展示供应商列表
     * @param map
     * @return
     */
    List<SplrChrm> selectByConditions(Map map);

    /**
     * 批量插入供应商风采展示列表
     * @param splrChrms
     * @return
     */
    int inserts(List<SplrChrm> splrChrms);
}