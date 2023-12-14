package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.IoQueryBidProjOffForPchsDto;
import cn.cofco.cpmp.service.IBidProjOffMngForPortalService;
import cn.cofco.cpmp.service.IBidProjOffService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.support.OutputDtoUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
/**
 * Created by Tao on 2017/6/19.
 */
@Controller
@RequestMapping("/bidProjOffMngForPortal")
public class BidProjOffMngForPortalController {
    private static Logger logger = LoggerManager.getBidOfflineMngLog();

    @Resource
    private IBidProjOffMngForPortalService bidProjOffMngForPortalService;

    @RequestMapping(value="/queryByConds/{pageNo}/{pageSize}", method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value = "根据条件分页查询线下招标项目信息", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询线下招标项目信息")
    public @ResponseBody
    JSONObject queryByConds(HttpServletRequest request,
                            @ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                            @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
                            @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany) {
        logger.info("request for -> 根据条件分页查询线下招标项目信息");
        try {
            OutputDto outputDto = bidProjOffMngForPortalService.getPagedResultByEntity(pageNo, pageSize,IsMCompany);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询线下招标项目信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewProjDtl/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据项目ID查询线下招标信息详情", httpMethod = "GET", response = OutputDto.class, notes = "根据项目ID查询綫下招标信息详情")
    public @ResponseBody
    JSONObject view(HttpServletRequest request,
                    @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看綫下招标项目详情");
        try {
            OutputDto outputDto = bidProjOffMngForPortalService.viewProjDtl(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据项目ID查询綫下招标信息详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value="/queryByRlt/{pageNo}/{pageSize}", method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value = "根据条件分页查询线下招标项目招标结果公告", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询线下招标项目招标结果公告")
    public @ResponseBody
    JSONObject queryByRlt(HttpServletRequest request,
                          @ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                          @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
                          @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany)
                           {
        logger.info("request for -> 根据条件分页查询线下招标项目招标结果公告");
        try {
            OutputDto outputDto = bidProjOffMngForPortalService.getPagedBidResultByEntity(pageNo, pageSize,IsMCompany);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询线下招标项目招标结果公告 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewProjRstDtl/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看线下招标结果详情", httpMethod = "GET", response = OutputDto.class, notes = "查看线下招标结果详情")
    public @ResponseBody
    JSONObject viewProjRstDtl(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看线下招标结果详情");
        try {
            OutputDto outputDto = bidProjOffMngForPortalService.viewProjRstDtl(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看线下招标结果详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "查看线下招标结果详情 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }




}
