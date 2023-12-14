package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.splr.vo.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrSelfService;
import cn.cofco.cpmp.support.OutputDtoUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author xsmiler
 * @date 2017/05
 */
@Controller
@RequestMapping("/splrSlfMng")
public class SplrSelfMngController {

	private Logger logger = LoggerManager.getSplrSelfMngLog();

	@Resource
	private ISplrSelfService splrSelfService;

	@RequestMapping(value = "/checkName", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商单位名称校验", httpMethod = "POST", response = OutputDto.class, notes = "供应商单位名称校验")
	public @ResponseBody JSONObject checkName(
			HttpServletRequest request,
			@ApiParam(value = "注册信息", required = true) @RequestBody(required = true) RgstCheckNameVo rgstCheckNameVo) {
		logger.info("request for -> 供应商单位名称校验");

		try {
			OutputDto outputDto = splrSelfService.checkName(rgstCheckNameVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商单位名称校验  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/rgst", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商注册", httpMethod = "POST", response = OutputDto.class, notes = "供应商注册")
	public @ResponseBody JSONObject rgstSplr(
			HttpServletRequest request,
			@ApiParam(value = "注册信息:itdcCompy-推介公司", required = true) @RequestBody(required = true) QuickRgstVo quickRgstVo) {
		logger.info("request for -> 供应商快速注册");

		try {
			OutputDto outputDto = splrSelfService.quickRgstSplr(quickRgstVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商快速注册  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商登录", httpMethod = "POST", response = OutputDto.class, notes = "供应商登录")
	public @ResponseBody JSONObject splrLogin(
			HttpServletRequest request,
			@ApiParam(value = "登录信息", required = true) @RequestBody(required = true) SplrLoginVo splrLoginVo) {
		logger.info("request for -> 供应商登录");

		try {
			OutputDto outputDto = splrSelfService.splrLogin(splrLoginVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商登录  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}

	}
	
	@RequestMapping(value = "/info", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商信息上传", httpMethod = "POST", response = OutputDto.class, notes = "供应商信息上传")
	public @ResponseBody JSONObject splrInfo(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "供应商信息", required = true) @RequestBody(required = true) SplrVo splrVo) {
		logger.info("request for -> 供应商信息上传");

		try {
			OutputDto outputDto = splrSelfService.splrInfo(splrVo, access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商信息上传  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}

	}
	
	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商信息", httpMethod = "GET", response = OutputDto.class, notes = "获取供应商信息")
	public @ResponseBody JSONObject getSplrInfo(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token) {
		logger.info("request for -> 获取供应商信息");

		try {
			OutputDto outputDto = splrSelfService.getSplrInfo(access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取供应商信息  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商信息修改申请", httpMethod = "POST", response = OutputDto.class, notes = "供应商信息修改申请")
	public @ResponseBody JSONObject splrUpdateInfo(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "供应商信息", required = true) @RequestBody(required = true) SplrVo splrVo) {
		logger.info("request for -> 供应商信息修改申请");

		try {
			OutputDto outputDto = splrSelfService.splrUpdateInfo(splrVo, access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商信息修改申请  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}

	}

	@RequestMapping(value = "/mim", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商修改密码", httpMethod = "POST", response = OutputDto.class, notes = "供应商修改密码")
	public @ResponseBody JSONObject modPasswd(
			HttpServletRequest request,
			@ApiParam(value = "密码信息", required = true) @RequestBody(required = true) SplrModPswdVo splrModPswdVo) {
		logger.info("request for -> 供应商修改密码");

		try {
			OutputDto outputDto = splrSelfService.splrModPasswd(splrModPswdVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商修改密码  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/acnt", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商获取账号列表", httpMethod = "GET", response = OutputDto.class, notes = "供应商获取账号列表")
	public @ResponseBody JSONObject getAcnts(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token) {
		logger.info("request for -> 供应商获取账号列表");

		try {
			OutputDto outputDto = splrSelfService.splrAcnts(access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商获取账号列表  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/crtAcnt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商创建账号", httpMethod = "POST", response = OutputDto.class, notes = "供应商创建账号")
	public @ResponseBody JSONObject splrCrtAcnt(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "账号信息", required = true) @RequestBody(required = true) SplrAcntVo splrAcntVo) {
		logger.info("request for -> 供应商创建账号");

		try {
			OutputDto outputDto = splrSelfService.splrCrtAcnt(splrAcntVo,
					access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商创建账号  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/delAcnt/{acntId}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商删除账号", httpMethod = "POST", response = OutputDto.class, notes = "供应商删除账号")
	public @ResponseBody JSONObject splrDelAcnt(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "账号Id", required = true) @PathVariable Long acntId) {
		logger.info("request for -> 供应商删除账号");

		try {
			OutputDto outputDto = splrSelfService.splrDelAcnt(acntId,
					access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商删除账号  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/apts", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商资质文件列表", httpMethod = "GET", response = OutputDto.class, notes = "供应商资质文件列表")
	public @ResponseBody JSONObject aptList(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true)@RequestHeader String access_token) {
		logger.info("request for -> 供应商资质文件列表");

		try {
			OutputDto outputDto = splrSelfService.aptList(access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商资质文件列表  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
//	@RequestMapping(value = "/update/apt", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
//	@ApiOperation(value = "供应商资质文件修改申请", httpMethod = "GET", response = OutputDto.class, notes = "供应商资质文件修改申请")
//	public @ResponseBody JSONObject updateApts(
//			HttpServletRequest request,
//			@ApiParam(value = "访问token", required = true)@RequestHeader String access_token,
//			@ApiParam(value = "资质文件名ID", required = true)@RequestHeader Long aptDefId,
//			@ApiParam(value = "资质有效期", required = true)@RequestHeader String aptInTim,
//			@ApiParam(value = "发证机构", required = true)@RequestHeader String crfcOgnz,
//			@ApiParam(value = "资质文件", required = true)@RequestParam("uploadFile") MultipartFile aptFile) {
//		logger.info("request for -> 供应商资质文件修改申请");
//
//		try {
//			OutputDto outputDto = splrSelfService.updateApt(access_token);
//			return outputDto.toJson();
//		} catch (Exception e) {
//			logger.debug(e.getMessage());
//			logger.info("供应商资质文件修改申请  - 异常 - Exception: "
//					+ ExceptionUtils.getStackTrace(e));
//			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
//					RtnEnum.SYS_ERR);
//			return outputDto.toJson();
//		}
//	}
	
	@RequestMapping(value = "/upload/apt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商上传资质文件", httpMethod = "POST", response = OutputDto.class, notes = "供应商上传资质文件")
	public @ResponseBody JSONObject uploadApt(
			HttpServletRequest request,
			@ApiParam(value = "资质文件信息", required = true) @RequestBody(required = true) SplrAptVo splrAptVo) {
		logger.info("request for -> 供应商上传资质文件");

		try {
			OutputDto outputDto = splrSelfService.uploadApt(splrAptVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商上传资质文件  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/apt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商某个资质文件", httpMethod = "POST", response = OutputDto.class, notes = "获取供应商某个资质文件")
	public @ResponseBody JSONObject getApt(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true)@RequestHeader(required=true) String access_token,
			@ApiParam(value = "资质文件ID", required = true)@RequestHeader(required=false) Long aptId) {
		logger.info("request for -> 获取供应商某个资质文件");

		try {
			OutputDto outputDto = splrSelfService.getApt(access_token, aptId);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取供应商某个资质文件  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/bnkAcnt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "添加银行账号", httpMethod = "POST", response = OutputDto.class, notes = "添加银行账号")
	public @ResponseBody JSONObject splrBnkAcnt(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "银行账号信息", required = true) @RequestBody(required = true) SplrBnkAcntVo splrBnkAcntVo) {
		logger.info("request for -> 添加银行账号");

		try {
			OutputDto outputDto = splrSelfService.splrBnkAcnt(splrBnkAcntVo,
					access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("添加银行账号  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/bnkAcnt/{pageNo}/{pageSize}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取银行账号信息列表", httpMethod = "GET", response = OutputDto.class, notes = "获取银行账号信息列表")
	public @ResponseBody JSONObject getBnkAcnts(
			HttpServletRequest request,
            @ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
            @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize) {
		logger.info("request for -> 获取银行账号信息列表");

		try {
			OutputDto outputDto = splrSelfService.getSplrBnkAcnts(pageNo, pageSize);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取银行账号信息列表 - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/bnkAcnt/{bnkId}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取银行账号信息", httpMethod = "GET", response = OutputDto.class, notes = "获取银行账号信息列表")
	public @ResponseBody JSONObject getBnkAcnt(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "银行账号Id", required = true) @PathVariable Long bnkId) {
		logger.info("request for -> 获取银行账号信息");

		try {
			OutputDto outputDto = splrSelfService.getSplrBnkAcnt(access_token, bnkId);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取银行账号信息 - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/delBnkAcnt/{bnkId}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "删除银行账户", httpMethod = "POST", response = OutputDto.class, notes = "删除银行账户")
	public @ResponseBody JSONObject delBnkAcnt(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "银行账号Id", required = true) @PathVariable Long bnkId) {
		logger.info("request for -> 删除银行账户");

		try {
			OutputDto outputDto = splrSelfService.delSplrBnkAcnt(access_token, bnkId);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("删除银行账户 - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/modBnkAcnt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "修改银行账号信息", httpMethod = "POST", response = OutputDto.class, notes = "修改银行账号信息")
	public @ResponseBody JSONObject modBnkAcnt(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "银行账号信息", required = true) @RequestBody(required = true) SplrBnkAcntVo splrBnkAcntVo) {
		logger.info("request for -> 修改银行账号信息");

		try {
			OutputDto outputDto = splrSelfService.modSplrBnkAcnt(access_token, splrBnkAcntVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("修改银行账号信息 - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/addSelfProd", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "产品自荐-添加产品", httpMethod = "POST", response = OutputDto.class, notes = "产品自荐-添加产品")
	public @ResponseBody JSONObject addSelfProd(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "产品信息", required = true) @RequestBody(required = true) SplrRcmdOnsfVo splrRcmdOnsfVo) {
		logger.info("request for -> 产品自荐-添加产品");

		try {
			OutputDto outputDto = splrSelfService.splrAddRcmdOnsf(splrRcmdOnsfVo,
					access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("产品自荐-添加产品  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/selfProd/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "产品自荐-自荐产品查询", httpMethod = "GET", response = OutputDto.class, notes = "产品自荐-自荐产品查询")
	public @ResponseBody JSONObject selectSelfProd(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "自荐产品ID", required = true) @PathVariable Long id) {
		logger.info("request for -> 产品自荐-自荐产品查询");

		try {
			
			OutputDto outputDto = splrSelfService.selectSplrRcmdOnsf(id,
					access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("产品自荐-自荐产品查询  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/selfProd/{id}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "产品自荐-自荐产品修改", httpMethod = "POST", response = OutputDto.class, notes = "产品自荐-自荐产品修改")
	public @ResponseBody JSONObject updateSelfProd(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "自荐产品ID", required = true) @PathVariable Long id,
			@ApiParam(value = "产品信息", required = true) @RequestBody(required = true) SplrRcmdOnsfVo splrRcmdOnsfVo
			) {
		logger.info("request for -> 产品自荐-自荐产品修改");

		try {
			OutputDto outputDto = splrSelfService.updateSplrRcmdOnsf(id, splrRcmdOnsfVo, 
					access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("产品自荐-自荐产品修改  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/delSelfProd/{id}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "产品自荐-自荐产品删除", httpMethod = "POST", response = OutputDto.class, notes = "产品自荐-自荐产品修改")
	public @ResponseBody JSONObject delSelfProd(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "自荐产品ID", required = true) @PathVariable Long id) {
		logger.info("request for -> 产品自荐-自荐产品删除");

		try {
			OutputDto outputDto = splrSelfService.delSplrRcmdOnsf(id, access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("产品自荐-自荐产品删除  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/selfProd/{pageNo}/{pageSize}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取自荐产品列表", httpMethod = "GET", response = OutputDto.class, notes = "获取自荐产品列表")
	public @ResponseBody JSONObject selfProd(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader String access_token,
            @ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
            @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize) {

		logger.info("request for -> 获取自荐产品列表");
		try {
			OutputDto outputDto = splrSelfService.splrRcmdOnsfs(access_token, pageNo, pageSize);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取自荐产品列表  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/chrm", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商上传风采信息", httpMethod = "POST", response = OutputDto.class, notes = "供应商上传风采信息")
	public @ResponseBody JSONObject uploadChrm(
			HttpServletRequest request,
			@ApiParam(value = "风采信息", required = true)@RequestBody(required = true) SplrChrmVo splrChrmVo) {
		logger.info("request for -> 供应商上传风采信息");

		try {
			OutputDto outputDto = splrSelfService.uploadChrm(splrChrmVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商上传风采信息  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/update/chrm", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "修改供应商风采信息", httpMethod = "POST", response = OutputDto.class, notes = "修改供应商风采照片")
	public @ResponseBody JSONObject updateChrm(
			HttpServletRequest request,
			@ApiParam(value = "风采信息", required = true)@RequestBody(required = true) SplrChrmVo splrChrmVo) {
		logger.info("request for -> 修改供应商风采照片");

		try {
			OutputDto outputDto = splrSelfService.updateChrm(splrChrmVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("修改供应商风采照片  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/chrm", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商风采信息", httpMethod = "GET", response = OutputDto.class, notes = "获取供应商风采信息")
	public @ResponseBody JSONObject getChrm(
			HttpServletRequest request) {
		logger.info("request for -> 获取供应商风采信息");

		try {
			OutputDto outputDto = splrSelfService.getChrm();
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取供应商风采信息  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/chrm/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商风采信息", httpMethod = "GET", response = OutputDto.class, notes = "获取供应商风采信息")
	public @ResponseBody JSONObject getChrmById(
			HttpServletRequest request, @ApiParam(value = "风采id", required = false) @PathVariable Long id) {
		logger.info("request for -> 通过id获取供应商风采信息");

		try {
			OutputDto outputDto = splrSelfService.getChrmById(id);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("通过id获取供应商风采信息  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/delChrm/{id}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商风采信息", httpMethod = "POST", response = OutputDto.class, notes = "获取供应商风采信息")
	public @ResponseBody JSONObject delChrmById(
			HttpServletRequest request, @ApiParam(value = "风采id", required = false) @PathVariable Long id) {
		logger.info("request for -> 通过id获取供应商风采信息");

		try {
			OutputDto outputDto = splrSelfService.delChrmById(id);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("通过id获取供应商风采信息  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

}
