package cn.cofco.cpmp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cofco.cpmp.entity.Splr;

public interface SplrMapper {
    int deleteByPrimaryKey(Long splrId);

    int insert(Splr record);

    int insertSelective(Splr record);

    Splr selectByPrimaryKey(Long splrId);

    int updateByPrimaryKeySelective(Splr record);

    int updateByPrimaryKey(Splr record);

    /**
     * 根据供应商状态查找供应商列表
     * @param splrSts
     * @return
     */
    List<Splr> selectByStatus(@Param("splrSts")String splrSts, @Param("start")Integer start, @Param("end")Integer end);

    Integer selectByStatusCount(@Param("splrSts")String splrSts);

    /**
     * 根据条件查找供应商列表
     * @param map
     * @return
     */
    List<Splr> selectByConditions(Map map);

    /**
     * 根据条件查找供应商数量
     * @param map
     * @return
     */
    Integer countOfMap(Map map);

    /**
     * 批量插入供应商
     * @param splrs
     * @return
     */
    int inserts(List<Splr> splrs);

    /**
     * 验证单位名称是否存在
     * @param fullName
     * @return
     */
    List<Splr> selectByFullName(@Param("fullName")String fullName);
}