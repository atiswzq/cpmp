package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.service.ISplrSelfService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

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
import cn.cofco.cpmp.entity.Mat;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrService;
import cn.cofco.cpmp.splr.vo.SplrVo;
import cn.cofco.cpmp.support.OutputDtoUtil;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author xsmiler
 * @date 2017/05
 */
@Controller
@RequestMapping("/splrMng")
public class SplrMngController {
	private Logger logger = LoggerManager.getSplrMngLog();

	@Resource ISplrService splrService;

	@Resource ISplrSelfService splrSelfService;

//	@RequestMapping(value = "/rgstAdt", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
//	@ApiOperation(value = "供应商注册审核", httpMethod = "POST", response = OutputDto.class, notes = "供应商注册审核")
//	public @ResponseBody JSONObject rgstSplr(
//			HttpServletRequest request,
//			@ApiParam(value = "供应商ID", required = true) @RequestHeader(required = true) Long splrId) {
//		logger.info("request for -> 供应商注册审核");
//
//		try {
//			OutputDto outputDto = splrService.rgstAdt(splrId);
//			return outputDto.toJson();
//		} catch (Exception e) {
//			logger.debug(e.getMessage());
//			logger.info("供应商注册审核  - 异常 - Exception: "
//
//					+ ExceptionUtils.getStackTrace(e));
//			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
//					RtnEnum.SYS_ERR, e.getMessage());
//			return outputDto.toJson();
//		}
//	}

	@RequestMapping(value = "/splrList/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "供应商列表", httpMethod = "POST", response = OutputDto.class, notes = "供应商列表")
	public @ResponseBody JSONObject splrList(
			HttpServletRequest request,
			@ApiParam(value = "供应商信息", required = true) @RequestBody(required = true) SplrVo splr,
            @ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
            @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
			@ApiParam(value = "供应商状态，多个以逗号隔开", required = false) @RequestHeader(required = false) String splrStatus) {
		logger.info("request for -> 供应商列表");

		try {
			OutputDto outputDto = splrService.splrList(splr, pageNo, pageSize, splrStatus);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("供应商列表  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/splrListByStatus", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "根据状态获取供应商列表", httpMethod = "GET", response = OutputDto.class, notes = "根据状态获取供应商列表")
	public @ResponseBody JSONObject splrListByStatus(
			HttpServletRequest request,
			@ApiParam(value = "pageNo", required = false) @RequestHeader(required = false) Integer pageNo,
			@ApiParam(value = "pageSize", required = false) @RequestHeader(required = false) Integer pageSize,
			@ApiParam(value = "供应商状态，多个以逗号隔开", required = false) @RequestHeader(required = false) String splrStatus) {
		logger.info("request for -> 根据状态获取供应商列表");

		try {
			OutputDto outputDto = splrService.splrListByStatus(splrStatus, pageNo, pageSize);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("根据状态获取供应商列表  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/splr/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商信息", httpMethod = "GET", response = OutputDto.class, notes = "获取供应商信息")
	public @ResponseBody JSONObject getSplrInfo(
			HttpServletRequest request,@ApiParam(value = "id", required = true) @PathVariable Long id) {
		logger.info("request for -> 获取供应商信息");

		try {
			OutputDto outputDto = splrService.getSplrInfo(id);
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

//	@RequestMapping(value = "/splrAdmtForBuild", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
//	@ApiOperation(value = "供应商准入-建立申请", httpMethod = "POST", response = OutputDto.class, notes = "供应商准入-建立申请")
//	public @ResponseBody JSONObject splrAdmtForBuild(
//			HttpServletRequest request,
//			@ApiParam(value = "供应商ID", required = false) @RequestHeader(required = false) Long splrId) {
//		logger.info("request for -> 供应商准入-建立申请");
//
//		try {
//			OutputDto outputDto = splrService.splrAdmtForBuild(splrId);
//			return outputDto.toJson();
//		} catch (Exception e) {
//			logger.debug(e.getMessage());
//			logger.info("供应商准入-建立申请  - 异常 - Exception: "
//					+ ExceptionUtils.getStackTrace(e));
//			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
//					RtnEnum.SYS_ERR, e.getMessage());
//			return outputDto.toJson();
//		}
//	}
//
//	@RequestMapping(value = "/splrAdmtForMat", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
//	@ApiOperation(value = "供应商准入-提交物料列表", httpMethod = "POST", response = OutputDto.class, notes = "供应商准入-提交物料列表")
//	public @ResponseBody JSONObject splrAdmtForMat(
//			HttpServletRequest request,
//			@ApiParam(value = "建立申请表ID", required = false) @RequestHeader(required = false) Long headId,
//			@ApiParam(value = "物料列表", required = true) @RequestBody(required = true) List<Mat> mats) {
//		logger.info("request for -> 供应商准入-提交物料列表");
//
//		try {
//			OutputDto outputDto = splrService.splrAdmtForMat(headId, mats);
//			return outputDto.toJson();
//		} catch (Exception e) {
//			logger.debug(e.getMessage());
//			logger.info("供应商准入-提交物料列表  - 异常 - Exception: "
//					+ ExceptionUtils.getStackTrace(e));
//			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
//					RtnEnum.SYS_ERR, e.getMessage());
//			return outputDto.toJson();
//		}
//	}

//	@RequestMapping(value = "/splrAdmtList", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
//	@ApiOperation(value = "供应商准入-开发申请列表", httpMethod = "POST", response = OutputDto.class, notes = "供应商准入-开发申请")
//	public @ResponseBody JSONObject splrAdmtList(
//			HttpServletRequest request,
//			@ApiParam(value = "访问token", required = true) @RequestHeader(required = true) String access_token,
//			@ApiParam(value = "pageNo", required = false) @RequestHeader(required = false) Integer pageNo,
//            @ApiParam(value = "pageSize", required = false) @RequestHeader(required = false) Integer pageSize) {
//		logger.info("request for -> 供应商准入-开发申请列表");
//
//		try {
//			OutputDto outputDto = splrService.splrAdmtList(access_token, pageNo, pageSize);
//			return outputDto.toJson();
//		} catch (Exception e) {
//			logger.debug(e.getMessage());
//			logger.info("供应商准入-开发申请列表  - 异常 - Exception: "
//					+ ExceptionUtils.getStackTrace(e));
//			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
//					RtnEnum.SYS_ERR, e.getMessage());
//			return outputDto.toJson();
//		}
//	}

//	@RequestMapping(value = "/splrAdmtForMDM", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
//	@ApiOperation(value = "供应商准入-MDM信息确认", httpMethod = "POST", response = OutputDto.class, notes = "供应商准入-MDM信息确认")
//	public @ResponseBody JSONObject splrAdmtForMDM(
//			HttpServletRequest request,
//			@ApiParam(value = "访问token", required = true) @RequestHeader(required = true) String access_token,
//			@ApiParam(value = "供应商ID", required = false) @RequestHeader(required = false) Long splrId) {
//		logger.info("request for -> 供应商准入-MDM信息确认");
//
//		try {
//			OutputDto outputDto = splrService.splrAdmtForMDM(access_token, splrId);
//			return outputDto.toJson();
//		} catch (Exception e) {
//			logger.debug(e.getMessage());
//			logger.info("供应商准入-MDM信息确认  - 异常 - Exception: "
//					+ ExceptionUtils.getStackTrace(e));
//			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
//					RtnEnum.SYS_ERR);
//			return outputDto.toJson();
//		}
//	}

	@RequestMapping(value = "/getWdotSplr", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取淘汰供应商", httpMethod = "GET", response = OutputDto.class, notes = "获取淘汰供应商")
	public @ResponseBody JSONObject addWdotSplr(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader(required = true) String access_token) {
		logger.info("request for -> 获取淘汰供应商");

		try {
			OutputDto outputDto = splrService.getWdotSplr(access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取淘汰供应商  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/getBkltSplr", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取黑名单供应商", httpMethod = "GET", response = OutputDto.class, notes = "获取黑名单供应商")
	public @ResponseBody JSONObject getBkltSplr(
			HttpServletRequest request,
			@ApiParam(value = "访问token", required = true) @RequestHeader(required = true) String access_token) {
		logger.info("request for -> 获取黑名单供应商");

		try {
			OutputDto outputDto = splrService.getBkltSplr(access_token);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("获取黑名单供应商  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/getSplrChrm", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "获取供应商风采列表", httpMethod = "GET", response = OutputDto.class, notes = "获取供应商风采列表")
	public @ResponseBody JSONObject getSplrChrm(
			HttpServletRequest request) {
		logger.info("request for -> 获取供应商风采列表");

		try {
			OutputDto outputDto = splrService.getSplrChrm();
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

	@RequestMapping(value = "/changeStsForSplr", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "改变供应商状态", httpMethod = "POST", response = OutputDto.class, notes = "改变供应商状态")
	public @ResponseBody JSONObject changeStsForSplr(
			HttpServletRequest request,
			@ApiParam(value = "供应商信息", required = true) @RequestBody(required = true) Splr splr) {
		logger.info("request for -> 改变供应商状态");

		try {
			OutputDto outputDto = splrService.changeStsForSplr(splr);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("改变供应商状态  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/resetPasswd", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "重置供应商密码", httpMethod = "POST", response = OutputDto.class, notes = "重置供应商密码")
	public @ResponseBody JSONObject resetPasswd(
			HttpServletRequest request,
			@ApiParam(value = "供应商ID", required = true) @RequestHeader(required = true) Long splrId) {
		logger.info("request for -> 重置供应商密码");

		try {
			OutputDto outputDto = splrService.resetPasswd(splrId);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.info("重置供应商密码  - 异常 - Exception: "
					+ ExceptionUtils.getStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR, e.getMessage());
			return outputDto.toJson();
		}
	}

//
//	@RequestMapping(value = "/upload/{type}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
//	@ApiOperation(value = "上传文件接口", httpMethod = "POST", response = OutputDto.class, notes = "上传文件接口")
//	public @ResponseBody JSONObject upload(
//			HttpServletRequest request,
//			@ApiParam(value = "上传文件类型：atch-附件；img-图片；apt-资质文件", required = true) @PathVariable String type,
//			@ApiParam(value = "上传文件", required = true)@RequestParam("file") MultipartFile file) {
//		logger.info("request for -> 上传文件");
//
//		try {
//			OutputDto outputDto = splrSelfService.uploadFile(type, file);
//			return outputDto.toJson();
//		} catch (Exception e) {
//			logger.debug(e.getMessage());
//			logger.info("上传文件  - 异常 - Exception: "
//					+ ExceptionUtils.getStackTrace(e));
//			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
//					RtnEnum.SYS_ERR, e.getMessage());
//			return outputDto.toJson();
//		}
//	}
}

