package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOnMngForPortalService;
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
 * Created by Xujy on 2017/5/12.
 * for [线上项目管理 - 门户网站 控制器类] in cpmp
 */
@Controller
@RequestMapping("/bidProjOnMngForPortal")
public class BidProjOnMngForPortalController {

    private static Logger logger = LoggerManager.getBidOnlineMngLog();

    @Resource
    private IBidProjOnMngForPortalService bidProjOnMngForPortalService;


    @RequestMapping(value = "/queryProjs/{pageNo}/{pageSize}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "分页查询线上招标项目列表", httpMethod = "GET", response = OutputDto.class, notes = "分页查询线上招标项目列表")
    public @ResponseBody
    JSONObject queryProjs(@ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                            @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
    @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany){
        logger.info("request for -> 分页查询线上招标项目列表 - args: [pageNo:" + pageNo + "], [pageSize:" + pageSize + "]");
        try {
            OutputDto outputDto = bidProjOnMngForPortalService.queryProjs(pageNo, pageSize,IsMCompany);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("分页查询线上招标项目列表 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewProjDtl/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看线上招标项目详情", httpMethod = "GET", response = OutputDto.class, notes = "查看线上招标项目详情")
    public @ResponseBody
    JSONObject viewProjDtl(HttpServletRequest request,
                    @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看线上招标项目详情");
        try {
            OutputDto outputDto = bidProjOnMngForPortalService.viewProjDtl(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据项目ID查询线上招标信息详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/queryProjRsts/{pageNo}/{pageSize}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "分页查询线上招标结果列表", httpMethod = "GET", response = OutputDto.class, notes = "分页查询线上招标结果列表")
    public @ResponseBody
    JSONObject queryProjRsts(HttpServletRequest request,
                            @ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                            @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
    @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany){
        logger.info("request for -> 分页查询线上招标结果列表 - args: [pageNo:" + pageNo + "], [pageSize:" + pageSize + "]");
        try {
            OutputDto outputDto = bidProjOnMngForPortalService.queryProjRsts(pageNo, pageSize,IsMCompany);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("分页查询线上招标结果列表 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewProjRstDtl/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看线上招标结果详情", httpMethod = "GET", response = OutputDto.class, notes = "查看线上招标结果详情")
    public @ResponseBody
    JSONObject viewProjRstDtl(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看线上招标结果详情");
        try {
            OutputDto outputDto = bidProjOnMngForPortalService.viewProjRstDtl(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看线上招标结果详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }
}
