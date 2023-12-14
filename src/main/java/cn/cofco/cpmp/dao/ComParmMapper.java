package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.ComParm;
import cn.cofco.cpmp.entity.ComParmKey;

import java.util.List;

public interface ComParmMapper {
    int deleteByPrimaryKey(ComParmKey key);

    int insert(ComParm record);

    int insertSelective(ComParm record);

    ComParm selectByPrimaryKey(ComParmKey key);

    List<ComParm> selectAll();

    int updateByPrimaryKeySelective(ComParm record);

    int updateByPrimaryKey(ComParm record);

    /*
  * 查询币种所有参数
  * */
    List<ComParm> selectByParmTyp(String parmTyp);

}