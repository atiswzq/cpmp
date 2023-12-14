package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.splr.vo.SplrChrmSetVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrChrmService;
import cn.cofco.cpmp.support.OutputDtoUtil;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 
 * @author xsmiler
 * @date 2017/06/28
 */
@Controller
@RequestMapping("/splrChrm")
public class SplrChrmController {
	private Logger logger = LoggerManager.getSplrMngLog();
	
	@Resource
	private ISplrChrmService splrChrmService;

	@RequestMapping(value = "/getSplrChrm", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商风采列表", httpMethod = "GET", response = OutputDto.class, notes = "获取供应商风采列表")
	public @ResponseBody JSONObject getSplrChrm(
			HttpServletRequest request) {
		logger.info("request for -> 获取供应商风采列表");

		try {
			OutputDto outputDto = splrChrmService.getSplrChrms();
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取供应商风采列表  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/setSplrChrm", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "设置门户网站供应商风采展示", httpMethod = "POST", response = OutputDto.class, notes = "设置门户网站供应商风采展示")
	public @ResponseBody JSONObject setSplrChrm(
			HttpServletRequest request, @ApiParam(value = "splrId: 供应商id；chrmShowSts: 01-展示、02-不展示", required = true) @RequestBody(required = true) SplrChrmSetVo splrChrmSetVo) {
		logger.info("request for -> 更新供应商风采展示列表");

		try {
			OutputDto outputDto = splrChrmService.setSplrChrms(splrChrmSetVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("更新供应商风采展示列表  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/getSplrChrm/{splrId}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商风采信息", httpMethod = "GET", response = OutputDto.class, notes = "获取供应商风采信息")
	public @ResponseBody JSONObject getSplrChrmById(
			HttpServletRequest request, @ApiParam(value = "供应商id", required = true) @PathVariable Long splrId) {
		logger.info("request for -> 获取供应商风采信息");

		try {
			OutputDto outputDto = splrChrmService.getSplrChrmById(splrId);
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
	
}

