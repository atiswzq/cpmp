package cn.cofco.cpmp.dao;

import cn.cofco.cpmp.entity.Artcl;

import java.util.List;
import java.util.Map;

public interface ArtclMapper {
    int deleteByPrimaryKey(Long artclId);

    int insert(Artcl record);

    int insertSelective(Artcl record);

    Artcl selectByPrimaryKey(Long artclId);

    int updateByPrimaryKeySelective(Artcl record);

    int updateByPrimaryKeyWithBLOBs(Artcl record);

    int updateByPrimaryKey(Artcl record);

    int countByMap(Map map);

    List<Artcl> selectByMap(Map map);
}