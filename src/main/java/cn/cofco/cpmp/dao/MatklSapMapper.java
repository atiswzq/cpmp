package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.MatklSap;

import java.util.List;
import java.util.Map;

public interface MatklSapMapper {
    int deleteByPrimaryKey(String matkl4);

    int insert(MatklSap record);

    int insertSelective(MatklSap record);

    MatklSap selectByPrimaryKey(String matkl4);

    int updateByPrimaryKeySelective(MatklSap record);

    int updateByPrimaryKey(MatklSap record);

    /**
     * 查询物料分类列表
     * @return
     */
    List<MatklSap> selectAll();


    List<MatklSap> selectByMap(Map map);
}