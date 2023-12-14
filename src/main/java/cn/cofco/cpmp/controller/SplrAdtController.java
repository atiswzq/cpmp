package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Mat;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrAdtService;
import cn.cofco.cpmp.service.ISplrSelfService;
import cn.cofco.cpmp.service.ISplrService;
import cn.cofco.cpmp.splr.vo.SplrAdmtVo;
import cn.cofco.cpmp.splr.vo.SplrAplyVo;
import cn.cofco.cpmp.splr.vo.SplrVo;
import cn.cofco.cpmp.support.OutputDtoUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * @author xsmiler
 * @date 2017/07/09
 */
@Controller
@RequestMapping("/splrAdt")
public class SplrAdtController {
	private Logger logger = LoggerManager.getSplrLog();

	@Resource
	private ISplrAdtService splrAdtService;
	
	@RequestMapping(value = "/admt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商注册申请", httpMethod = "POST", response = OutputDto.class, notes = "供应商注册申请")
	public @ResponseBody JSONObject admt(
			HttpServletRequest request,
			@ApiParam(value = "供应商信息，matIds填写物料编码", required = true) @RequestBody(required = true) SplrAdmtVo splrAdmtVo) {
		logger.info("request for -> 供应商注册申请");

		try {
			OutputDto outputDto = splrAdtService.splrAdmt(splrAdmtVo);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商注册申请  - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/aply", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商开发申请", httpMethod = "POST", response = OutputDto.class, notes = "供应商开发申请")
	public @ResponseBody JSONObject aply(
			HttpServletRequest request,
			@ApiParam(value = "供应商ID", required = true) @RequestHeader(required = true) Long splrId) {
		logger.info("request for -> 供应商准入申请");

		try {
			OutputDto outputDto = splrAdtService.splrAply(request, splrId);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商开发申请  - 异常 - Exception: "

					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/adtTempForType", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商申请模板id", httpMethod = "GET", response = OutputDto.class, notes = "获取供应商申请模板id")
	public @ResponseBody JSONObject adtTempForType(
			HttpServletRequest request,@ApiParam(value = "申请类型：aply-开发申请", required = true) @RequestHeader String type) {
		logger.info("request for -> 获取供应商申请模板id");

		try {
			OutputDto outputDto = splrAdtService.adtTempForType(type);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取供应商申请模板id  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

}

