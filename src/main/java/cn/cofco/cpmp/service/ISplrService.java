package cn.cofco.cpmp.service;

import java.util.List;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Mat;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.splr.vo.SplrVo;

public interface ISplrService {

	/**
	 * 供应商注册审核
	 * @param splrId
	 * @return
	 * @throws Exception
	 */
	OutputDto rgstAdt(Long splrId) throws Exception;

	/**
	 * 获取供应商列表
	 * @param splr
	 * @param pageNo
	 * @param pageSize
	 * @param splrStatus
     * @return
	 * @throws Exception
	 */
	OutputDto splrList(SplrVo splr, Integer pageNo, Integer pageSize, String splrStatus) throws Exception;

	/**
	 * 供应商准入 - 提交BPM审核
	 * @param access_token
	 * @param splrId
	 * @return
	 * @throws Exception
	 */
	OutputDto splrAdmtForBPM(String access_token, Long splrId) throws Exception;

	/**
	 * 供应商准入 - MDM数据确认
	 * @param access_token
	 * @param splrId
	 * @return
	 * @throws Exception
	 */
	OutputDto splrAdmtForMDM(String access_token, Long splrId) throws Exception;

	/**
	 * 获取冻结供应商
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	OutputDto getWdotSplr(String access_token) throws Exception;

	/**
	 * 获取黑名单供应商
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	OutputDto getBkltSplr(String access_token) throws Exception;

	/**
	 * 供应商开发申请
	 * @param splrId
	 * @return
	 * @throws Exception
	 */
	OutputDto splrAdmtForBuild(Long splrId) throws Exception;

	/**
	 * 获取供应商开发申请列表
	 * @param access_token
	 * @param pageSize 
	 * @param pageNo 
	 * @return
	 * @throws Exception
	 */
	OutputDto splrAdmtList(String access_token, Integer pageNo, Integer pageSize) throws Exception;

	/**
	 * 准入审核 - 提交物料列表
	 * @param headId
	 * @param mats
	 * @return
	 * @throws Exception
	 */
	OutputDto splrAdmtForMat(Long headId, List<Mat> mats) throws Exception;

	/**
	 * 供应商风采列表展示
	 * @return
	 * @throws Exception
	 */
	OutputDto getSplrChrm() throws Exception;

	/**
	 * 获取供应商信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
    OutputDto getSplrInfo(Long id) throws Exception;

	/**
	 * 根据状态获取供应商列表
	 * @param splrStatus
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
    OutputDto splrListByStatus(String splrStatus, Integer pageNo, Integer pageSize) throws Exception;

	/**
	 * 修改供应商信息
	 * @param splr
	 * @return
	 * @throws Exception
	 */
    OutputDto changeStsForSplr(Splr splr) throws Exception;

	/**
	 * 检查供应商是否为合格供应商
	 * @param splrId
	 * @return
	 * @throws Exception
	 */
    boolean checkSplrForQualified(Long splrId) throws Exception;

	/**
	 * 重置供应商密码
	 * @param splrId
	 * @return
	 */
	OutputDto resetPasswd(Long splrId);
}
