package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.ChkDef;
import cn.cofco.cpmp.entity.ChkTempItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChkDefMapper {
    int deleteByPrimaryKey(Long chkDefId);

    int insert(ChkDef record);

    int insertSelective(ChkDef record);

    ChkDef selectByPrimaryKey(Long chkDefId);

    int updateByPrimaryKeySelective(ChkDef record);

    int updateByPrimaryKey(ChkDef record);

    /**
     * 根据模板定义id查找考察表模板
     * @param chkDefId
     * @return
     */
    List<ChkTempItem> selectChkTempById(@Param("chkDefId")Long chkDefId);
}