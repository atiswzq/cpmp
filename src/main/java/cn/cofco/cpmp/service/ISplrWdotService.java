package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.SplrWdotAdtDto;
import cn.cofco.cpmp.dto.SplrWdotSplrMngDto;
import cn.cofco.cpmp.splr.dto.SplrWdotDto;
import cn.cofco.cpmp.splr.vo.SplrWdotVo;

public interface ISplrWdotService {

	/**
	 * 供应商淘汰申请
	 * 
	 * @param splrId
	 * @param aplyCtt
	 * @return
	 * @throws Exception
	 */
	OutputDto splrWdotAply(SplrWdotVo splrWdotVo) throws Exception;

	/**
	 * 供应商淘汰列表
	 * 
	 * @return
	 * @throws Exception
	 */
	OutputDto getSplrWdotList(Integer pageNo, Integer pageSize, SplrWdotDto splrWdotDto,String orgId) throws Exception;

	/**
	 * 供应商淘汰审核(采购部经理)
	 * 
	 * @return
	 * @throws Exception
	 */
	OutputDto splrWdotAdtByMng(SplrWdotAdtDto splrWdotAdtDto) throws Exception;

	/**
	 * 供应商淘汰审核(供应商管理部)
	 * 
	 * @return
	 * @throws Exception
	 */
	OutputDto splrWdotAdtBySplrMng(SplrWdotAdtDto splrWdotAdtDto) throws Exception;

	/**
	 * 供应商淘汰(供应商管理员直接淘汰)
	 * 
	 * @return
	 * @throws Exception
	 */
	OutputDto splrWdotBySplrMng(SplrWdotSplrMngDto splrMngDto) throws Exception;

	/**
	 * 获取供应商列表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	OutputDto getSplrList(Integer pageNo, Integer pageSize, SplrWdotDto splrWdotDto) throws Exception;
}
