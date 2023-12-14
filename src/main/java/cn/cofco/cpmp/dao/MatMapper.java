package cn.cofco.cpmp.dao;

import java.util.List;
import java.util.Map;

import cn.cofco.cpmp.entity.Mat;

public interface MatMapper {
    int deleteByPrimaryKey(Long matId);

    int insert(Mat record);

    int insertSelective(Mat record);

    Mat selectByPrimaryKey(Long matId);

    int updateByPrimaryKeySelective(Mat record);

    int updateByPrimaryKey(Mat record);
    
    /**
     * 物料批量插入
     * @param mats
     * @return
     */
	int inserts(List<Mat> mats);
	
	/**
	 * 根据条件查找物料
	 * @return
	 */
	List<Mat> selectCondition(Map map);


}