package cn.cofco.cpmp.dao;

import org.apache.ibatis.annotations.Param;

import cn.cofco.cpmp.entity.Materiel;

import java.util.List;
import java.util.Map;

public interface MaterielMapper {
    int deleteByPrimaryKey(Short matId);

    int insert(Materiel record);

    int insertSelective(Materiel record);

    Materiel selectByPrimaryKey(Long matId);

    int updateByPrimaryKeySelective(Materiel record);

    int updateByPrimaryKey(Materiel record);
    
    Materiel selectByMatcod(@Param("matCod")String matCod);

    /**
     * 查询物料列表
     * @param map
     * @return
     */
    List<Materiel> selectByConditions(Map map);

    /**
     * 查询物料列表
     * @param map
     * @return
     */
    Integer countOfMap(Map map);

}