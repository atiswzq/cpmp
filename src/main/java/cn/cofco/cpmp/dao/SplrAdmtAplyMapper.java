package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrAdmtAply;

import java.util.List;
import java.util.Map;

public interface SplrAdmtAplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SplrAdmtAply record);

    int insertSelective(SplrAdmtAply record);

    SplrAdmtAply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SplrAdmtAply record);

    int updateByPrimaryKey(SplrAdmtAply record);

    /**
     * 根据条件查找列表
     * @param map
     * @return
     */
    List<SplrAdmtAply> selectByCondition(Map map);
}