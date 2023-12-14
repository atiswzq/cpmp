package cn.cofco.cpmp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.cofco.cpmp.entity.SplrAcnt;
import cn.cofco.cpmp.splr.dto.SplrAptInfo;

public interface SplrAcntMapper {
    int deleteByPrimaryKey(Long acntId);

    int insert(SplrAcnt record);

    int insertSelective(SplrAcnt record);

    SplrAcnt selectByPrimaryKey(Long acntId);

    int updateByPrimaryKeySelective(SplrAcnt record);

    int updateByPrimaryKey(SplrAcnt record);

	SplrAcnt selectByLogin(@Param("usrNam")String usrNam, @Param("passwd")String passwd, @Param("phone")String phone);

	SplrAcnt selectByToken(@Param("accessToken")String access_token);

	List<SplrAcnt> selectAcnts(@Param("splrId")Long splrId);
	
	/**
	 * 按条件搜索
	 * @param conditions
	 * @return
	 */
	List<SplrAcnt> selectAcntsByConditions(Map conditions);
}