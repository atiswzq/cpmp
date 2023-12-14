package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.BnkCodParm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BnkCodParmMapper {
    int deleteByPrimaryKey(String bnkCod);

    int insert(BnkCodParm record);

    int insertSelective(BnkCodParm record);

    BnkCodParm selectByPrimaryKey(String bnkCod);

    int updateByPrimaryKeySelective(BnkCodParm record);

    int updateByPrimaryKey(BnkCodParm record);

    List<BnkCodParm> selectByBnkNam(@Param("bnkNam")String bnkNam);
}