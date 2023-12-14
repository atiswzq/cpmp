package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.BnkCodParm;
import cn.cofco.cpmp.entity.ComParm;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IComParmService;
import cn.cofco.cpmp.support.OutputDtoUtil;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by Xujy on 2017/5/1.
 */
@Controller
@RequestMapping("/comParm")
public class ComParmController {

	private static Logger logger = LoggerManager.getSysLog();

	@Resource
	private IComParmService comParmService;

	@RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "通用参数刷新", httpMethod = "GET", response = OutputDto.class, notes = "通用参数刷新")
	public @ResponseBody JSONObject refresh() {
		logger.info("request for -> 通用参数刷新");
		try {
			List<ComParm> comParmList = comParmService.getComParmAll();
			ComParmHolder.loadComParms(comParmList);
			OutputDto outputDto = OutputDtoUtil.setOutputDto(true,
					RtnEnum.SUC_OPR, comParmList);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.error("通用参数刷新 - 异常 - Exception: "
					+ ExceptionUtils.getFullStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR);
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "新增通用参数", httpMethod = "POST", response = OutputDto.class, notes = "新增通用参数")
	public @ResponseBody JSONObject add(@RequestBody ComParm comParm) {
		logger.info("request for -> 新增通用参数");
		try {
			List<ComParm> comParmList = comParmService.getComParmAll();
			ComParmHolder.loadComParms(comParmList);
			OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, comParmList);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.error("新增通用参数 - 异常 - Exception: "
					+ ExceptionUtils.getFullStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.SYS_ERR);
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/getByParmTyp/{parmTyp}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "根据参数类型获取通用参数信息", httpMethod = "GET", response = OutputDto.class, notes = "根据参数类型获取通用参数信息")
	public @ResponseBody JSONObject getByParmTyp(
			@ApiParam(value = "parmTyp", required = true) @PathVariable String parmTyp) {
		logger.info("request for -> 根据参数类型获取通用参数信息");
		try {
			List<ComParm> comParmList = ComParmHolder.getByParmTyp(parmTyp);
			OutputDto outputDto = OutputDtoUtil.setOutputDto(true, RtnEnum.SUC,
					comParmList);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.error("根据参数类型获取通用参数信息 - 异常 - Exception: "
					+ ExceptionUtils.getFullStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR);
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/getByParmTyps/{parmTyps}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "根据参数类型(多个)获取通用参数信息", httpMethod = "GET", response = OutputDto.class, notes = "根据参数类型获取通用参数信息")
	public @ResponseBody JSONObject getByParmTyps(
			@ApiParam(value = "参数类型，多个用逗号隔开", required = true) @PathVariable String parmTyps) {
		logger.info("request for -> 根据参数类型获取通用参数信息");
		try {
			Map<String, List<ComParm>> comParmList = ComParmHolder.getByParmTyps(parmTyps);
			OutputDto outputDto = OutputDtoUtil.setOutputDto(true, RtnEnum.SUC,
					comParmList);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.error("根据参数类型获取通用参数信息 - 异常 - Exception: "
					+ ExceptionUtils.getFullStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR);
			return outputDto.toJson();
		}
	}
	
	@RequestMapping(value = "/getByBnkNam/{bnkNam}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "根据银行名称模糊查询银行代码列表", httpMethod = "GET", response = OutputDto.class, notes = "根据银行名称模糊查询银行代码列表")
	public @ResponseBody JSONObject getByBnkNam(
			@ApiParam(value = "银行名称", required = true) @PathVariable String bnkNam) {
		logger.info("request for -> 根据银行名称模糊查询银行代码列表");
		try {
			OutputDto outputDto = comParmService.getByBnkNam(bnkNam);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.error("根据银行名称模糊查询银行代码列表- 异常 - Exception: "
					+ ExceptionUtils.getFullStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR);
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/addCurrTyp", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "增加币种汇率", httpMethod = "POST", response = OutputDto.class, notes = "增加币种汇率")
	public @ResponseBody JSONObject addCurrTyp(
			@RequestBody ComParm currTyp) {
		logger.info("request for -> 增加币种汇率");
		try {
			OutputDto outputDto = comParmService.addCurrTyp(currTyp);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.error("增加币种汇率- 异常 - Exception: "
					+ ExceptionUtils.getFullStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR);
			return outputDto.toJson();
		}
	}

	@RequestMapping(value = "/deleteCurrTyp/{parmCod}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "删除币种汇率", httpMethod = "GET", response = OutputDto.class, notes = "删除币种汇率")
	public @ResponseBody JSONObject deleteCurrTyp(
			@ApiParam(value = "删除币种汇率", required = true) @PathVariable String parmCod) {
		logger.info("request for -> 删除币种汇率");
		try {
			OutputDto outputDto = comParmService.deleteCurrTyp(parmCod);
			return outputDto.toJson();
		} catch (Exception e) {
			logger.error("删除币种汇率- 异常 - Exception: "
					+ ExceptionUtils.getFullStackTrace(e));
			OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
					RtnEnum.SYS_ERR);
			return outputDto.toJson();
		}
	}


}
