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
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrOnlineAnsrService;
import cn.cofco.cpmp.splr.vo.SplrOnlineAnsrVo;
import cn.cofco.cpmp.splr.vo.SplrOnlineQueVo;
import cn.cofco.cpmp.support.OutputDtoUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 供应商在线答疑
 * 
 * @author 李世涛
 * @date 2018年1月14日
 * 
 */
@Controller
@RequestMapping("/splrOnline")
public class SplrOnlineAnsrController {

	private final static Logger logger = LoggerManager.getSplrLog();

	@Resource
	ISplrOnlineAnsrService onlineAnsrService;

	@RequestMapping(value = "/que", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商提问", httpMethod = "POST", response = OutputDto.class, notes = "供应商提问")
	public @ResponseBody JSONObject que(HttpServletRequest request,
			@ApiParam(value = "提问信息", required = true) @RequestBody(required = true) SplrOnlineQueVo splrOnlineQue) {
		logger.info("request for -> 供应商提问");

		try {
			OutputDto outputDto = onlineAnsrService.splrOnlineQue(splrOnlineQue);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商提问 - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/ansr", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商提问回复", httpMethod = "POST", response = OutputDto.class, notes = "供应商提问回复")
	public @ResponseBody JSONObject ansr(HttpServletRequest request,
			@ApiParam(value = "提问回复信息", required = true) @RequestBody(required = true) SplrOnlineAnsrVo splrOnlineAnsr) {
		logger.info("request for -> 供应商提问回复");

		try {
			OutputDto outputDto = onlineAnsrService.splrOnlineAnsr(splrOnlineAnsr);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商提问回复 - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/splrAnsrList", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商在线答疑信息列表(公开内容列表,对话框列表)", httpMethod = "GET", response = OutputDto.class, notes = "供应商在线答疑信息列表(公开内容列表,对话框列表)")
	public @ResponseBody JSONObject ansrList(HttpServletRequest request,
			@ApiParam(value = "供应商访问token", required = true) @RequestHeader String access_token,
			@ApiParam(value = "项目id", required = true) @RequestHeader(required = true) Long projId) {
		logger.info("request for -> 供应商在线答疑信息列表(公开内容列表,对话框列表)");

		try {
			OutputDto outputDto = onlineAnsrService.splrAnsrList(projId);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商在线答疑信息列表(公开内容列表,对话框列表) - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/purchAnsrList", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "采购员获取供应商在线答疑内容", httpMethod = "GET", response = OutputDto.class, notes = "采购员获取供应商在线答疑内容")
	public @ResponseBody JSONObject purchList(HttpServletRequest request,
			@ApiParam(value = "项目id", required = true) @RequestHeader(required = true) Long projId) {
		logger.info("request for -> 采购员获取供应商在线答疑内容");

		try {
			OutputDto outputDto = onlineAnsrService.purchaserAnsrList(projId);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("采购员获取供应商在线答疑内容 - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/openAnsr", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "在线答疑内容公开设置", httpMethod = "POST", response = OutputDto.class, notes = "在线答疑内容公开设置")
	public @ResponseBody JSONObject openAnsr(HttpServletRequest request,
			@ApiParam(value = "消息id", required = true) @RequestHeader(required = true) Long mid,
			@ApiParam(value = "公开状态", required = true) @RequestHeader(required = true) int openFlg) {
		logger.info("request for -> 在线答疑内容公开设置");

		try {
			OutputDto outputDto = onlineAnsrService.openAnsrInfo(mid, openFlg);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("在线答疑内容公开设置 - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

}
