package cn.cofco.cpmp.controller;


import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IXjProjMngForPortalService;
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
 * Created by Wzq on 2018/01/13.
 * for [询价项目管理 - 门户网站 控制器类] in cpmp
 */
@Controller
@RequestMapping("/xjProjMngForPortal")
public class XjProjMngForPortalController {
    private static Logger logger = LoggerManager.getBidOnlineMngLog();
    @Resource
    private IXjProjMngForPortalService xjProjMngForPortalService;

    @RequestMapping(value = "/queryXjProjs/{pageNo}/{pageSize}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "分页查询询价项目列表", httpMethod = "GET", response = OutputDto.class, notes = "分页查询询价项目列表")
    public @ResponseBody
    JSONObject queryXjProjs(@ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                            @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
                            @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany) {
        logger.info("request for -> 分页查询询价项目列表 - args: [pageNo:" + pageNo + "], [pageSize:" + pageSize + "]");
        try {
            OutputDto outputDto = xjProjMngForPortalService.queryXjProjs(pageNo, pageSize,IsMCompany);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("分页查询询价项目列表 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewXjProjDtl/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看询价项目详情", httpMethod = "GET", response = OutputDto.class, notes = "查看询价项目详情")
    public @ResponseBody
    JSONObject viewXjProjDtl(HttpServletRequest request,
                           @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看询价项目详情");
        try {
            OutputDto outputDto = xjProjMngForPortalService.viewXjProjDtl(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看询价项目详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/queryXjProjRsts/{pageNo}/{pageSize}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "分页查询询价项目招标结果列表", httpMethod = "GET", response = OutputDto.class, notes = "分页查询询价项目招标结果列表")
    public @ResponseBody
    JSONObject queryXjProjRsts(HttpServletRequest request,
                             @ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                             @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
                             @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany) {
        logger.info("request for -> 分页查询询价项目招标结果列表 - args: [pageNo:" + pageNo + "], [pageSize:" + pageSize + "]");
        try {
            OutputDto outputDto = xjProjMngForPortalService.queryXjProjRsts(pageNo, pageSize,IsMCompany);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("分页查询询价项目招标结果列表 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewXjProjRstDtl/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看询价项目招标结果详情", httpMethod = "GET", response = OutputDto.class, notes = "查看询价项目招标结果详情")
    public @ResponseBody
    JSONObject viewXjProjRstDtl(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看询价项目招标结果详情");
        try {
            OutputDto outputDto = xjProjMngForPortalService.viewXjProjRstDtl(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看询价项目招标结果详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }
}
