package cn.cofco.cpmp.service;

import java.util.List;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.mdm.entity.MdmMateriel;

/**
 * Created by xsmiler on 2017/6/10.
 */
public interface IMdmService {

	/**
	 * MDM供应商查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean splrQuery(Long id) throws Exception;

	/**
	 * MDM供应商申请接口
	 * @param id
	 * @throws Exception
	 */
	OutputDto splrAply(Long id, boolean checkAuth) throws Exception;

	/**
	 * 批量申请
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	boolean splrAply(List<Long> ids) throws Exception;

}
