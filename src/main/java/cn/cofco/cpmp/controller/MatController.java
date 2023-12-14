package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.entity.Materiel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Mat;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IMatService;
import cn.cofco.cpmp.support.OutputDtoUtil;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author xsmiler
 * @date 2017/05/29
 */
@Controller
@RequestMapping("/mat")
public class MatController {
	
	private Logger logger = LoggerManager.getSplrMngLog();
	
	@Resource
	IMatService matService;
	
	@RequestMapping(value = "/addMat", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "添加物料接口", httpMethod = "POST", response = OutputDto.class, notes = "添加物料接口")
	public @ResponseBody JSONObject addMat(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader(required = true) String access_token,
			@ApiParam(value = "物料列表", required = true) @RequestBody(required = true) List<Mat> mats) {
		logger.info("request for -> 添加物料");

		try {
			OutputDto outputDto = matService.addMat(access_token, mats);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("添加物料  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/getMat", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取物料接口", httpMethod = "POST", response = OutputDto.class, notes = "获取物料接口")
	public @ResponseBody JSONObject getMat(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader(required = true) String access_token,
			@ApiParam(value = "pageNo", required = false) @RequestHeader(required = false) Integer pageNo,
            @ApiParam(value = "pageSize", required = false) @RequestHeader(required = false) Integer pageSize,
            @ApiParam(value = "物料查询参数实体", required = true) @RequestBody(required = true) Mat dto) {
		logger.info("request for -> 获取物料接口");

		try {
			OutputDto outputDto = matService.getMat(access_token, dto, pageNo, pageSize);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取物料接口  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/splrByMatType", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "根据物料类型获取供应商列表接口", httpMethod = "GET", response = OutputDto.class, notes = "根据物料类型获取供应商列表接口")
	public @ResponseBody JSONObject splrByMatType(
			HttpServletRequest request,
			@ApiParam(value = "物料类型", required = true) @RequestHeader(required = true) String matType,
			@ApiParam(value = "供应商状态", required = true) @RequestHeader(required = true) String splrSts) {
		logger.info("request for -> 根据物料类型获取供应商列表");

		try {
			OutputDto outputDto = matService.splrByMatType(matType, splrSts);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("根据物料类型获取供应商列表  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/matList/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "查询物料列表", httpMethod = "POST", response = OutputDto.class, notes = "查询物料列表")
	public @ResponseBody JSONObject matList(
			HttpServletRequest request,
			@ApiParam(value = "物料", required = true) @RequestBody(required = true) Materiel materiel,
			@ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
			@ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize) {
		logger.info("request for -> 查询物料列表");

		try {
			OutputDto outputDto = matService.matList(materiel, pageNo, pageSize);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("查询物料列表  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/matType", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "查询物料类型", httpMethod = "GET", response = OutputDto.class, notes = "查询物料类型")
	public @ResponseBody JSONObject matType(HttpServletRequest request) {
		logger.info("request for -> 查询物料类型");

		try {
			OutputDto outputDto = matService.matType();
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("查询物料类型  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/matSapType", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "查询物料类型", httpMethod = "GET", response = OutputDto.class, notes = "查询物料类型")
	public @ResponseBody JSONObject matSapType(HttpServletRequest request) {
		logger.info("request for -> 查询物料类型");

		try {
			OutputDto outputDto = matService.matSapType();
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("查询物料类型  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}
}
