package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.splr.vo.SplrOnlineAnsrVo;
import cn.cofco.cpmp.splr.vo.SplrOnlineQueVo;

public interface ISplrOnlineAnsrService {

	/**
	 * 供应商在线答疑---提问
	 * 
	 * @param splrOnlineQue
	 * @return
	 * @throws Exception
	 */
	OutputDto splrOnlineQue(SplrOnlineQueVo splrOnlineQue) throws Exception;

	/**
	 * 供应商在线答疑---回复
	 * 
	 * @param splrOnlineAnsr
	 * @return
	 * @throws Exception
	 */
	OutputDto splrOnlineAnsr(SplrOnlineAnsrVo splrOnlineAnsr) throws Exception;

	/**
	 * 供应商获取在线答疑内容列表(1,公开内容列表 2,对话框列表)
	 * 
	 * @param projId
	 * @return
	 * @throws Exception
	 */
	OutputDto splrAnsrList(Long projId) throws Exception;

	/**
	 * 答疑者获取供应商答疑的列表
	 * 
	 * @return
	 * @throws Exception
	 */
	OutputDto purchaserAnsrList(Long projId) throws Exception;

	/**
	 * 是否公开答疑内容
	 * 
	 * @param openFlg
	 * @return
	 * @throws Exception
	 */
	OutputDto openAnsrInfo(Long mid, int openFlg) throws Exception;

}
