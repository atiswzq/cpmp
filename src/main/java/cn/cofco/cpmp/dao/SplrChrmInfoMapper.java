package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.SplrChrmInfo;
import cn.cofco.cpmp.splr.dto.SplrChrmDto;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SplrChrmInfoMapper {
    int insert(SplrChrmInfo record);

    int insertSelective(SplrChrmInfo record);

    int updateSelective(SplrChrmInfo splrChrmInfo);

    List<SplrChrmInfo> select(@Param("splrId")Long splrId);
    
    /**
     * 供应商风采列表
     * @return
     */
    List<SplrChrmDto> selectForChrm();
    
    int delete(@Param("id")Long id);
}