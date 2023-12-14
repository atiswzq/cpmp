package cn.cofco.cpmp.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RequisitionMapper {

    List<Map> selectByMap(Map map);

    Integer countByMap(Map map);

    int updateToInzb(@Param("inzb") String inzb, @Param("reqIds") List<Long> reqIds);
    //根据物料名称搜索
    List<Map> selectByName(Map map);
    Integer countByName(Map map);
}