package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOnMngForSplrService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Xujy on 2017/5/12.
 * for [线上项目管理 - 供应商 控制器类] in cpmp
 */
@Controller
@RequestMapping("/bidProjOnMngForSplr")
public class BidProjOnMngForSplrController {

    private static Logger logger = LoggerManager.getBidOnlineMngLog();

    @Resource
    private IBidProjOnMngForSplrService bidProjOnMngForSplrService;


    @RequestMapping(value = "/bidApp", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线上招标项目供应商申请投标", httpMethod = "POST", response = OutputDto.class, notes = "线上招标项目供应商申请投标")
    public @ResponseBody
    JSONObject bidApp(@RequestBody IoBidProjOnAppBidDto dto) {
        logger.info("request for -> 线上招标项目供应商申请投标");
        try {
            OutputDto outputDto = bidProjOnMngForSplrService.appBid(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线上招标项目供应商申请投标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "线上招标项目供应商申请投标 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/queryBidProjs/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询已投标的线上招标项目", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询已投标的线上招标项目")
    public @ResponseBody
    JSONObject queryBidProjOns(@ApiParam(value = "pageNo", defaultValue = "1") @PathVariable Integer pageNo,
                               @ApiParam(value = "pageSize", defaultValue = "20") @PathVariable Integer pageSize,
                               @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany,
                               @RequestBody IoQueryBidProjOnSplrTendInfDto dto) {
        logger.info("request for -> 根据条件分页查询已投标的线上招标项目");
        try {
            OutputDto outputDto = bidProjOnMngForSplrService.getBidInfByIoQueryBidProjOnSplrTendInfDto(dto, pageNo, pageSize,IsMCompany);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询已投标的线上招标项目 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "根据条件分页查询已投标的线上招标项目 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/queryProjs/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询线上招标项目列表", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询线上招标项目列表")
    public @ResponseBody
    JSONObject queryByConds(@ApiParam(value = "pageNo", required = true, defaultValue = "1") @PathVariable Integer pageNo,
                            @ApiParam(value = "pageSize", required = true, defaultValue = "20") @PathVariable Integer pageSize,
                            @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany,
                            @RequestBody IoQueryBidProjOnProjInfForSplrDto dto) {
        logger.info("request for -> 根据条件分页查询线上招标项目列表 - args: [pageNo:" + pageNo + "], [pageSize:" + pageSize + "], " + dto.toString());
        try {
            OutputDto outputDto = bidProjOnMngForSplrService.getPagedResultByIoQueryBidProjOnProjInfForSplrDto(dto, pageNo, pageSize,IsMCompany);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询线上招标项目列表 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "根据条件分页查询线上招标项目列表 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewProjDtl/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据项目ID查询线上招标信息详情", httpMethod = "GET", response = OutputDto.class, notes = "根据项目ID查询线上招标信息详情")
    public @ResponseBody
    JSONObject view(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看线上招标项目详情");
        try {
            OutputDto outputDto = bidProjOnMngForSplrService.viewProjDtl(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据项目ID查询线上招标信息详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "根据项目ID查询线上招标信息详情 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }



    @RequestMapping(value = "/qot", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线上招标项目供应商报价", httpMethod = "POST", response = OutputDto.class, notes = "线上招标项目供应商报价")
    public @ResponseBody
    JSONObject qot(@RequestBody IoBidProjOnQotDto dto) {
        logger.info("request for -> 线上招标项目供应商报价");
        try {
            OutputDto outputDto = bidProjOnMngForSplrService.qot(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线上招标项目供应商报价 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "线上招标项目供应商报价 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/getQotOrder/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查询实时竞价物料报价价格排名以及最低报价", httpMethod = "GET", response = OutputDto.class, notes = "查询实时竞价物料报价价格排名以及最低报价")
    public @ResponseBody
    JSONObject getQotOrder(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        logger.info("request for -> 查询实时竞价物料报价价格排名以及最低报价");
        try {
            OutputDto outputDto = bidProjOnMngForSplrService.getQotOrder(id);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查询实时竞价物料报价价格排名以及最低报价 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "查询实时竞价物料报价价格排名以及最低报价 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/getQotInf/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取线上招标项目供应商报价详情", httpMethod = "GET", response = OutputDto.class, notes = "获取线上招标项目供应商报价详情")
    public @ResponseBody
    JSONObject getQotHis(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        logger.info("request for -> 获取线上招标项目供应商报价详情");
        try {
            OutputDto outputDto = bidProjOnMngForSplrService.getQotInf(id);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("获取线上招标项目供应商报价详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "获取线上招标项目供应商报价详情 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }

    @Deprecated
    @RequestMapping(value = "/getQotHis/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取线上招标项目供应商报价历史", httpMethod = "POST", response = OutputDto.class, notes = "获取线上招标项目供应商报价历史")
    public @ResponseBody JSONObject getQotHis(@ApiParam(value = "pageNo", defaultValue = "1") @PathVariable(required = false) Integer pageNo,
                         @ApiParam(value = "pageSize", defaultValue = "20") @PathVariable(required = false) Integer pageSize,
                   @RequestBody IoGetQotHisDto dto) {
        logger.info("request for -> 获取线上招标项目供应商报价历史");
        try {
            OutputDto outputDto = bidProjOnMngForSplrService.getQotHis(dto, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("获取线上招标项目供应商报价历史 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "获取线上招标项目供应商报价历史 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/viewProjRstDtl/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "供应商查看线上招标结果详情", httpMethod = "GET", response = OutputDto.class, notes = "供应商查看线上招标结果详情")
    public @ResponseBody
    JSONObject viewProjRstDtl(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 供应商查看线上招标结果详情");
        try {
            OutputDto outputDto = bidProjOnMngForSplrService.viewProjRstDtl(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("供应商查看线上招标结果详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "供应商查看线上招标结果详情 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }

}
