package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrAdmtChk;

import java.util.List;
import java.util.Map;

public interface SplrAdmtChkMapper {
    int deleteByPrimaryKey(Long admtChkId);

    int insert(SplrAdmtChk record);

    int insertSelective(SplrAdmtChk record);

    SplrAdmtChk selectByPrimaryKey(Long admtChkId);

    int updateByPrimaryKeySelective(SplrAdmtChk record);

    int updateByPrimaryKey(SplrAdmtChk record);

    int deleteBySplrId(Long splrId);

    int countByMap(Map map);

    List<SplrAdmtChk> selectByMap(Map map);

}