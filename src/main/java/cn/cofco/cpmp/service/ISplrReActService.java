package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.SplrReActAdtDto;
import cn.cofco.cpmp.dto.SplrReActSplrMngDto;
import cn.cofco.cpmp.splr.vo.SplrReActVo;

public interface ISplrReActService {

	/**
	 * 供应商重启用申请
	 * 
	 * @param splrId
	 * @param aplyCtt
	 * @return
	 * @throws Exception
	 */
	OutputDto splrReActAply(SplrReActVo splrReActVo) throws Exception;

	/**
	 * 供应商重启用审核(采购部经理)
	 * 
	 * @return
	 * @throws Exception
	 */
	OutputDto splrReActAdtByMng(SplrReActAdtDto sraad) throws Exception;

	/**
	 * 供应商重启用审核(供应商管理部)
	 * 
	 * @return
	 * @throws Exception
	 */
	OutputDto splrReActAdtBySplrMng(SplrReActAdtDto sraad) throws Exception;

	/**
	 * 供应商重启用(供应商管理员直接重启用)
	 * 
	 * @param splrId
	 *            供应商id
	 * @param ReActMsg
	 *            重启用理由
	 * @return
	 * @throws Exception
	 */
	OutputDto splrReActBySplrMng(SplrReActSplrMngDto srasmd) throws Exception;
}
