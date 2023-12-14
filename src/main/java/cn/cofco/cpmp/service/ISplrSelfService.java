package cn.cofco.cpmp.service;

import cn.cofco.cpmp.splr.vo.*;
import org.springframework.web.multipart.MultipartFile;

import cn.cofco.cpmp.dto.OutputDto;

public interface ISplrSelfService {
	
	/**
	 * 供应商注册
	 * @param quickRgstVo
	 * @return
	 * @throws Exception
	 */
	OutputDto quickRgstSplr(QuickRgstVo quickRgstVo) throws Exception;
	
	/**
	 * 供应商登录
	 * @param splrLoginVo
	 * @return
	 * @throws Exception
	 */
	OutputDto splrLogin(SplrLoginVo splrLoginVo) throws Exception;
	
	/**
	 * 供应商信息修改
	 * @param splrVo
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	OutputDto splrInfo(SplrVo splrVo, String access_token) throws Exception;
	
	/**
	 * 供应商账户修改密码
	 * @return
	 * @throws Exception
	 */
	OutputDto splrModPasswd(SplrModPswdVo splrModPswdVo) throws Exception;
	
	/**
	 * 创建供应商账户
	 * @param splrAcntVo 
	 * @param access_token 
	 * @return
	 * @throws Exception
	 */
	OutputDto splrCrtAcnt(SplrAcntVo splrAcntVo, String access_token) throws Exception;
	
	/**
	 * 删除供应商账户
	 * @param acntId 
	 * @param access_token 
	 * @return
	 * @throws Exception
	 */
	OutputDto splrDelAcnt(Long acntId, String access_token) throws Exception;
	
	/**
	 * 获取供应商账户列表
	 * @param access_token
	 * @return
	 */
	OutputDto splrAcnts(String access_token) throws Exception;

	/**
	 * 添加供应商银行账号信息
	 * @param splrBnkAcntVo
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	OutputDto splrBnkAcnt(SplrBnkAcntVo splrBnkAcntVo, String access_token) throws Exception;

	/**
	 * 供应商上传资质文件
	 * @return
	 * @throws Exception 
	 */
	OutputDto uploadApt(SplrAptVo splrAptVo) throws Exception;

	/**
	 * 资质文件列表
	 * @param access_token
	 * @return
	 */
	OutputDto aptList(String access_token);

	/**
	 * 供应商添加自荐产品
	 * @param splrRcmdOnsfVo
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	OutputDto splrAddRcmdOnsf(SplrRcmdOnsfVo splrRcmdOnsfVo, String access_token) throws Exception;

	/**
	 * 获取自荐产品列表
	 * @param access_token
	 * @param pageSize 
	 * @param pageNo 
	 * @return
	 * @throws Exception
	 */
	OutputDto splrRcmdOnsfs(String access_token, Integer pageNo, Integer pageSize) throws Exception;

	/**
	 * 供应商信息修改
	 * @param splrVo
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	OutputDto splrUpdateInfo(SplrVo splrVo, String access_token) throws Exception;

	/**
	 * 供应商信息获取
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	OutputDto getSplrInfo(String access_token) throws Exception;

	/**
	 * 获取银行账号信息接口
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	OutputDto getSplrBnkAcnts(Integer pageNo, Integer pageSize) throws Exception;

	/**
	 * 修改银行账号信息
	 * @param access_token
	 * @param splrBnkAcntVo
	 * @return
	 * @throws Exception
	 */
	OutputDto modSplrBnkAcnt(String access_token, SplrBnkAcntVo splrBnkAcntVo) throws Exception;

	/**
	 * 供应商自荐产品查询
	 * @param id
	 * @param access_token
	 * @return
	 */
	OutputDto selectSplrRcmdOnsf(Long id, String access_token) throws Exception;

	/**
	 * 供应商自荐产品修改
	 * @param id
	 * @param splrRcmdOnsfVo
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	OutputDto updateSplrRcmdOnsf(Long id, SplrRcmdOnsfVo splrRcmdOnsfVo,
			String access_token) throws Exception;

	/**
	 * 根据bnkId获取银行信息
	 * @param access_token
	 * @param bnkId
	 * @return
	 * @throws Exception
	 */
	OutputDto getSplrBnkAcnt(String access_token, Long bnkId) throws Exception;
	
	/**
	 * 删除银行信息
	 * @param access_token
	 * @param bnkId
	 * @return
	 * @throws Exception
	 */
	OutputDto delSplrBnkAcnt(String access_token, Long bnkId) throws Exception;

	/**
	 * 自荐产品删除
	 * @param id
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	OutputDto delSplrRcmdOnsf(Long id, String access_token) throws Exception;

	/**
	 * 获取供应商某个资质文件
	 * @param access_token
	 * @param aptId
	 * @return
	 * @throws Exception
	 */
	OutputDto getApt(String access_token, Long aptId) throws Exception;


	/**
	 * 供应商上传风采照片
	 * @param splrChrmVo
	 * @return
	 * @throws Exception
	 */
    OutputDto uploadChrm(SplrChrmVo splrChrmVo) throws Exception;

	/**
	 * 修改供应商风采信息
	 * @param splrChrmVo
	 * @return
	 * @throws Exception
	 */
	OutputDto updateChrm(SplrChrmVo splrChrmVo) throws Exception;

	/**
	 * 获取供应商风采列表
	 * @return
	 * @throws Exception
	 */
	OutputDto getChrm() throws Exception;

	// TODO 将会删除
	/**
	 * 上传文件
	 * @param type 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	OutputDto uploadFile(String type, MultipartFile file) throws Exception;

	/**
	 * 根据id获取供应商信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	OutputDto getSplrInfo(Long id) throws Exception;

	/**
	 * 根据id获取风采信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	OutputDto getChrmById(Long id) throws Exception;

	/**
	 * 根据id删除风采信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	OutputDto delChrmById(Long id) throws Exception;

	/**
	 * 校验供应商名字是否存在
	 * @param rgstCheckNameVo
	 * @return
	 */
    OutputDto checkName(RgstCheckNameVo rgstCheckNameVo);
}
