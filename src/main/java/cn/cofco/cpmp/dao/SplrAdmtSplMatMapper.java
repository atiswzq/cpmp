package cn.cofco.cpmp.dao;

import java.util.List;

import cn.cofco.cpmp.entity.SplrAdmtSplMat;

public interface SplrAdmtSplMatMapper {
    int insert(SplrAdmtSplMat record);

    int insertSelective(SplrAdmtSplMat record);
    
    // 批量插入
    int inserts(List<SplrAdmtSplMat> splrAdmtSplMat);
}