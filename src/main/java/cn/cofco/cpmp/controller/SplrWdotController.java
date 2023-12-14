package cn.cofco.cpmp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.cofco.cpmp.splr.dto.SplrWdotDto;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.SplrWdotAdtDto;
import cn.cofco.cpmp.dto.SplrWdotSplrMngDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrWdotService;
import cn.cofco.cpmp.splr.vo.SplrWdotVo;
import cn.cofco.cpmp.support.OutputDtoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 供应商淘汰
 * 
 * @author 李世涛
 * @date 2018年1月6日
 * 
 */
@Controller
@RequestMapping("/splrWdot")
public class SplrWdotController {
	private final static Logger logger = LoggerManager.getSplrLog();

	@Resource
	private ISplrWdotService splrWdotService;

	@RequestMapping(value = "/aply", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商淘汰申请", httpMethod = "POST", response = OutputDto.class, notes = "供应商淘汰申请")
	public @ResponseBody JSONObject aply(HttpServletRequest request,
			@ApiParam(value = "淘汰信息", required = true) @RequestBody(required = true) SplrWdotVo splrWdotVo) {
		logger.info("request for -> 供应商淘汰申请");

		try {
			OutputDto outputDto = splrWdotService.splrWdotAply(splrWdotVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商淘汰申请  - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/deptAdt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商淘汰审核(采购部经理)", httpMethod = "POST", response = OutputDto.class, notes = "供应商淘汰审核")
	public @ResponseBody JSONObject deptAdt(HttpServletRequest request,
			@ApiParam(value = "供应商淘汰审核状态,意见", required = true) @RequestBody(required = true) SplrWdotAdtDto splrWdotAdtDto) {
		logger.info("request for -> 供应商淘汰申请审核(采购部经理)");

		try {
			OutputDto outputDto = splrWdotService.splrWdotAdtByMng(splrWdotAdtDto);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商淘汰申请审核(采购部经理)  - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/splrMngAdt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商淘汰审核(供应商管理部)", httpMethod = "POST", response = OutputDto.class, notes = "供应商淘汰审核")
	public @ResponseBody JSONObject splrMngAdt(HttpServletRequest request,
			@ApiParam(value = "供应商淘汰审核状态,意见", required = true) @RequestBody(required = true) SplrWdotAdtDto splrWdotAdtDto) {
		logger.info("request for -> 供应商淘汰申请审核(供应商管理部)");

		try {
			OutputDto outputDto = splrWdotService.splrWdotAdtBySplrMng(splrWdotAdtDto);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商淘汰申请审核(供应商管理部)  - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/splrMngWdot", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商淘汰(供应商管理员)", httpMethod = "POST", response = OutputDto.class, notes = "供应商淘汰")
	public @ResponseBody JSONObject splrMngMdot(HttpServletRequest request,
			@ApiParam(value = "供应商淘汰意见", required = true) @RequestBody(required = true) SplrWdotSplrMngDto splrMngDto) {
		logger.info("request for -> 供应商淘汰(供应商管理员)");

		try {
			OutputDto outputDto = splrWdotService.splrWdotBySplrMng(splrMngDto);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商淘汰(供应商管理员)  - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/getSplrWdotList", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商淘汰列表", httpMethod = "POST", response = OutputDto.class, notes = "获取供应商淘汰列表")
	public @ResponseBody JSONObject getSplrWdot(HttpServletRequest request,
			@ApiParam(value = "pageNo", required = false) @RequestHeader(required = false) Integer pageNo,
			@ApiParam(value = "pageSize", required = false) @RequestHeader(required = false) Integer pageSize,
			@ApiParam(value = "组织Id", required = false) @RequestHeader(required = false) String orgId,
			@ApiParam(value = "供应商名称", required = false) @RequestBody(required = false) SplrWdotDto splrWdotDto) {
		logger.info("request for -> 获取供应商淘汰列表");

		try {
			OutputDto outputDto = splrWdotService.getSplrWdotList(pageNo, pageSize, splrWdotDto,orgId);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取供应商淘汰列表  - 异常 - Exception: " + ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/getSplrList", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商列表", httpMethod = "POST", response = OutputDto.class, notes = "供应商列表")
	public @ResponseBody JSONObject splrList(HttpServletRequest request,
			@ApiParam(value = "pageNo", required = false) @RequestHeader(required = false) Integer pageNo,
			@ApiParam(value = "pageSize", required = false) @RequestHeader(required = false) Integer pageSize,
			@ApiParam(value = "供应商名称", required = false) @RequestBody(required = false) SplrWdotDto splrWdotDto) {
		logger.info("request for -> 供应商列表");

		try {
			OutputDto outputDto = splrWdotService.getSplrList(pageNo, pageSize, splrWdotDto);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商列表  - 异常 - Exception: " + ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
}