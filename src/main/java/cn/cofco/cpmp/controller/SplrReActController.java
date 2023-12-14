package cn.cofco.cpmp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.SplrReActAdtDto;
import cn.cofco.cpmp.dto.SplrReActSplrMngDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrReActService;
import cn.cofco.cpmp.splr.vo.SplrReActVo;
import cn.cofco.cpmp.support.OutputDtoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 供应商重启用
 * 
 * @author 李世涛
 * @date 2018年1月11日
 * 
 */
@Controller
@RequestMapping("/splrReAct")
public class SplrReActController {
	private final static Logger logger = LoggerManager.getSplrLog();

	@Resource
	private ISplrReActService splrReActService;

	@RequestMapping(value = "/aply", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商重启用申请", httpMethod = "POST", response = OutputDto.class, notes = "供应商重启用申请")
	public @ResponseBody JSONObject aply(HttpServletRequest request,
			@ApiParam(value = "重启用信息", required = true) @RequestBody(required = true) SplrReActVo splrReActVo) {
		logger.info("request for -> 供应商重启用申请");

		try {
			OutputDto outputDto = splrReActService.splrReActAply(splrReActVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商重启用申请  - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/deptAdt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商重启用审核(采购部经理)", httpMethod = "POST", response = OutputDto.class, notes = "供应商重启用审核")
	public @ResponseBody JSONObject deptAdt(HttpServletRequest request,
			@ApiParam(value = "供应商重启用审核状态,意见", required = true) @RequestBody(required = true) SplrReActAdtDto sraad) {
		logger.info("request for -> 供应商重启用申请审核(采购部经理)");

		try {
			OutputDto outputDto = splrReActService.splrReActAdtByMng(sraad);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商重启用申请审核(采购部经理)  - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/splrMngAdt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商重启用审核(供应商管理部)", httpMethod = "POST", response = OutputDto.class, notes = "供应商重启用审核")
	public @ResponseBody JSONObject splrMngAdt(HttpServletRequest request,
			@ApiParam(value = "供应商重启用审核状态,意见", required = true) @RequestBody(required = true) SplrReActAdtDto sraad) {
		logger.info("request for -> 供应商重启用申请审核(供应商管理部)");

		try {
			OutputDto outputDto = splrReActService.splrReActAdtBySplrMng(sraad);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商重启用申请审核(供应商管理部)  - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/splrMngReAct", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商重启用(供应商管理员)", httpMethod = "POST", response = OutputDto.class, notes = "供应商重启用")
	public @ResponseBody JSONObject splrMngReAct(HttpServletRequest request,
			@ApiParam(value = "供应商重启用意见", required = true) @RequestHeader(required = true) SplrReActSplrMngDto srasmd) {
		logger.info("request for -> 供应商重启用申请(供应商管理员)");

		try {
			OutputDto outputDto = splrReActService.splrReActBySplrMng(srasmd);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商重启用(供应商管理员)  - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

}
