package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.IoBidProjOffAppBidDto;
import cn.cofco.cpmp.dto.IoQueryBidProjOffProjInfForSplrDto;
import cn.cofco.cpmp.dto.IoQueryBidProjOffSplrInfDto;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOffMngForSplrService;
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
/**
 * Created by Tao on 2017/5/31.
 * for [线下项目管理 - 供应商 控制器类] in cpmp
 */
@Controller
@RequestMapping("/bidProjOffMngForSplr")
public class BidProjOffForSplrController {
    private static Logger logger = LoggerManager.getBidOfflineMngLog();

    @Resource
    private IBidProjOffMngForSplrService iBidProjOffMngForSplrService;

    @RequestMapping(value = "/bidApp", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线下招标项目供应商申请投标", httpMethod = "POST", response = OutputDto.class, notes = "线下招标项目供应商申请投标")
    public @ResponseBody
    JSONObject bidApp(@RequestBody IoBidProjOffAppBidDto dto) {
        logger.info("request for -> 线下招标项目供应商申请投标");
        try {
            OutputDto outputDto = iBidProjOffMngForSplrService.appBid(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线下招标项目供应商申请投标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }
    @RequestMapping(value = "/queryBidProjs/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询已投标的线下招标项目", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询已投标的线下招标项目")
    public @ResponseBody
    JSONObject queryBidProjs(@ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                               @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
                             @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany,
                               @RequestBody IoQueryBidProjOffSplrInfDto dto) {
        logger.info("request for -> 根据条件分页查询已投标的线下招标项目");
        try {
            OutputDto outputDto = iBidProjOffMngForSplrService.getBidInfByIoQueryBidProjOffSplrInfDto(dto, pageNo, pageSize,IsMCompany);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询已投标的线下招标项目 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/queryProjs/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询线下招标项目列表", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询线下招标项目列表")
    public @ResponseBody
    JSONObject queryByConds(@ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                            @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
                            @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany,
                            @RequestBody IoQueryBidProjOffProjInfForSplrDto dto) {
        logger.info("request for -> 根据条件分页查询线下招标项目列表 - args: [pageNo:" + pageNo + "], [pageSize:" + pageSize + "], " + dto.toString());
        try {
            OutputDto outputDto = iBidProjOffMngForSplrService.getPagedResultByIoQueryBidProjOffProjInfForSplrDto(dto, pageNo, pageSize,IsMCompany);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询线下招标项目列表 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewProjDtl/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据项目ID查询线下招标信息详情", httpMethod = "GET", response = OutputDto.class, notes = "根据项目ID查询綫下招标信息详情")
    public @ResponseBody
    JSONObject view(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看綫下招标项目详情");
        try {
            OutputDto outputDto = iBidProjOffMngForSplrService.viewProjDtl(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据项目ID查询綫下招标信息详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }
}
