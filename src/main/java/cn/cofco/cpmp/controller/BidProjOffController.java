package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOffService;
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
import java.util.Map;

/**
 * Created by Tao on 2017/5/7.
 */
@Controller
@RequestMapping("/bidProjOff")
public class BidProjOffController {
    private static Logger logger = LoggerManager.getBidOfflineMngLog();

    @Resource
    private IBidProjOffService bidProjOffService;

    @RequestMapping(value = "/queryByConds/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询线下招标项目信息", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询线下招标项目信息")
    public @ResponseBody
    JSONObject queryByConds(@ApiParam(value = "pageNo", defaultValue = "1") @PathVariable Integer pageNo,
                            @ApiParam(value = "pageSize", defaultValue = "20") @PathVariable Integer pageSize,
                            @RequestBody IoQueryBidProjOffForPchsDto dto) {
        logger.info("request for -> 根据条件分页查询线下招标项目信息");
        try {
            OutputDto outputDto = bidProjOffService.getPagedResultByEntity(dto, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询线下招标项目信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/saveOrSub", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "保存线下招标项目信息", httpMethod = "POST", response = OutputDto.class, notes = "保存线下招标项目信息")
    public @ResponseBody
    JSONObject saveOrSub(HttpServletRequest request, @RequestBody IoBidProjOffDto dto) {
        logger.info("request for -> 保存线下招标项目信息");
        try {
            OutputDto outputDto = bidProjOffService.saveOrSub(request, dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("保存线下招标项目信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/view/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看线下招标项目详情", httpMethod = "GET", response = OutputDto.class, notes = "查看线下招标项目详情")
    public @ResponseBody
    JSONObject view(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看线下招标项目详情");
        try {
            OutputDto outputDto = bidProjOffService.view(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看线下招标项目详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/del/{projId}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除线下招标项目信息", httpMethod = "POST", response = OutputDto.class, notes = "删除线下招标项目信息")
    public @ResponseBody
    JSONObject del(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 删除线下招标项目信息");
        try {
            OutputDto outputDto = bidProjOffService.del(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("删除线下招标项目信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/pub/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发布线下招标项目", httpMethod = "GET", response = OutputDto.class, notes = "发布线下招标项目")
    public @ResponseBody
    JSONObject pub(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 发布线下招标项目");
        try {
            OutputDto outputDto = bidProjOffService.publish(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发布线下招标项目 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/cut", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线下招标项目截标", httpMethod = "POST", response = OutputDto.class, notes = "线下招标项目截标")
    public @ResponseBody
    JSONObject cut(@RequestBody IoBidProjOffCutDto dto) {
        logger.info("request for -> 线下招标项目截标");
        try {
            OutputDto outputDto = bidProjOffService.cut(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线下招标项目截标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/repeal", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线下招标项目申请废标", httpMethod = "POST", response = OutputDto.class, notes = "线下招标项目申请废标")
    public @ResponseBody
    JSONObject repeal(@RequestBody IoBidProjOffRepealDto dto) {
        logger.info("request for -> 线下招标项目申请废标");
        try {
            OutputDto outputDto = bidProjOffService.repeal(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线下招标项目申请废标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/queryBidProjs/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询线下招标项目投标信息", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询线下招标项目投标信息")
    public @ResponseBody
    JSONObject queryBidProjs(@ApiParam(value = "pageNo") @PathVariable Integer pageNo,
                             @ApiParam(value = "pageSize") @PathVariable Integer pageSize,
                             @RequestBody IoQueryBidProjOffSplrInfDto dto) {
        logger.info("request for -> 根据条件分页查询线下招标项目投标信息");
        try {
            OutputDto outputDto = bidProjOffService.getBidInfByIoQueryBidProjOffSplrInfDto(dto, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询线下招标项目投标信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/adtBidInf", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线下招标项目供应商投标审核：通过/拒绝", httpMethod = "POST", response = OutputDto.class, notes = "线下招标项目供应商投标审核：通过/拒绝")
    public @ResponseBody
    JSONObject adtBidInf(@RequestBody IoBidProjOffBidAdtDto dto) {
        logger.info("request for -> 线下招标项目供应商投标审核：通过/拒绝");
        try {
            OutputDto outputDto = bidProjOffService.adtBidInf(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线下招标项目供应商投标审核：通过/拒绝 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/appAwd", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "申请决标", httpMethod = "POST", response = OutputDto.class, notes = "申请决标")
    public @ResponseBody
    JSONObject appAwd(HttpServletRequest request,
                      @RequestBody IoBidProjOffAppAwdListDto dto) {
        logger.info("request for -> 申请决标");
        try {
            OutputDto outputDto = bidProjOffService.appAwd(request, dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("申请决标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/pubRst/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发布招标结果", httpMethod = "GET", response = OutputDto.class, notes = "发布招标结果")
    public @ResponseBody
    JSONObject pubRst(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 发布招标结果");
        try {
            OutputDto outputDto = bidProjOffService.pubRst(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发布招标结果 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewAwdInf/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看招标结果", httpMethod = "GET", response = OutputDto.class, notes = "查看招标结果")
    public @ResponseBody
    JSONObject viewAwdInf(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看招标结果");
        try {
            OutputDto outputDto = bidProjOffService.viewAwdInf(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看招标结果 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/getTendSplrs", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取投标供应商", httpMethod = "POST", response = OutputDto.class, notes = "获取投标供应商")
    public @ResponseBody
    JSONObject getTendSplrs(
            @RequestBody IoBidOffGetTendSplrsDto dto) {
        logger.info("request for -> 获取投标供应商");
        try {
            OutputDto outputDto = bidProjOffService.getTendSplrs(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("获取投标供应商 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    /*首页分页显示,查询招标结果公告*/
    @RequestMapping(value = "/queryByRlt/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询线下招标项目招标结果公告", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询线下招标项目招标结果公告")
    public @ResponseBody
    JSONObject queryByRlt(@ApiParam(value = "pageNo") @PathVariable Integer pageNo,
                          @ApiParam(value = "pageSize") @PathVariable Integer pageSize,
                          @RequestBody IoQueryBidProjOffForPchsDto dto) {
        logger.info("request for -> 根据条件分页查询线下招标项目招标结果公告");
        try {
            OutputDto outputDto = bidProjOffService.getPagedBidResultByEntity(dto, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询线下招标项目招标结果公告 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewProjRstDtl/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看线下招标结果详情", httpMethod = "GET", response = OutputDto.class, notes = "查看线下招标结果详情")
    public @ResponseBody
    JSONObject viewProjRstDtl(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看线下招标结果详情");
        try {
            OutputDto outputDto = bidProjOffService.viewProjRstDtl(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看线下招标结果详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

}
