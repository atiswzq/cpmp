package cn.cofco.cpmp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.entity.SplrMat;

public interface SplrMatMapper {
    int deleteByPrimaryKey(Long splrMatId);

    int insert(SplrMat record);

    int insertSelective(SplrMat record);

    SplrMat selectByPrimaryKey(Long splrMatId);

    int updateByPrimaryKeySelective(SplrMat record);

    int updateByPrimaryKey(SplrMat record);
    
    List<Splr> getSplrByMatTyp(@Param("matTyp")String matTyp, @Param("splrSts")String splrSts);

    /**
     * 批量插入供应商物料关系
     * @param splrMats
     * @return
     */
    int inserts(List<SplrMat> splrMats);

    /**
     * 查询所有物料类型
     * @return
     */
    List<String> matType();

    /**
     * 根据条件查找列表
     * @param map
     * @return
     */
    List<SplrMat> selectByCondition(Map map);
}