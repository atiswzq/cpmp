package cn.cofco.cpmp.service;

import java.util.List;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Mat;
import cn.cofco.cpmp.entity.Materiel;
import cn.cofco.cpmp.entity.MatklSap;

public interface IMatService {

	/**
	 * 添加物料列表接口
	 * @param access_token
	 * @param mats
	 * @return
	 * @throws Exception
	 */
	OutputDto addMat(String access_token, List<Mat> mats) throws Exception;
	
	/**
	 * 根据条件获取物料
	 * @param access_token
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	OutputDto getMat(String access_token, Mat dto, Integer pageNo,
			Integer pageSize) throws Exception;

	/**
	 * 根据物料类型获取供应商列表
	 *
     * @param matType
     * @param splrSts
     * @return
	 * @throws Exception
	 */
	OutputDto splrByMatType(String matType, String splrSts) throws Exception;

	/**
	 * 查询物料列表
	 * @param materiel
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
    OutputDto matList(Materiel materiel, Integer pageNo, Integer pageSize) throws Exception;

	/**
	 * 查询物料类型接口
	 * @return
	 */
	OutputDto matType() throws Exception;

	/**
	 * 查询物料类型接口
	 * @return
	 */
    OutputDto matSapType();

    List<MatklSap> matSaps();
}
