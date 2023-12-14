package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOnMngForPchsService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.poi.PoiUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Xujy on 2017/5/1.
 * for [线上项目管理 - 工厂采购员 控制器类] in cpmp
 */
@Controller
@RequestMapping("/bidProjOnMngForPchs")
public class BidProjOnMngForPchsController {

    private static Logger logger = LoggerManager.getBidOnlineMngLog();

    @Resource
    private IBidProjOnMngForPchsService bidProjOnMngForPchsService;

    @RequestMapping(value = "/queryProjs/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询线上招标项目信息", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询线上招标项目信息")
    public @ResponseBody
    JSONObject queryProjs(
            @ApiParam(value = "pageNo", defaultValue = "1") @PathVariable Integer pageNo,
            @ApiParam(value = "pageSize", defaultValue = "20") @PathVariable Integer pageSize,
            @RequestBody IoQueryBidProjOnForPchsDto dto) {
        logger.info("request for -> 根据条件分页查询线上招标项目信息");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getPagedResultByIoQueryBidProjOnForPchsDto(dto, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询线上招标项目信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/saveOrSub", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "保存线上招标项目信息", httpMethod = "POST", response = OutputDto.class, notes = "保存线上招标项目信息")
    public @ResponseBody
    JSONObject saveOrSub(HttpServletRequest request,
                         @RequestBody IoBidProjOnDto dto) {
        logger.info("request for -> 保存线上招标项目信息");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.saveOrSub(request, dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("保存线上招标项目信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/del/{projId}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除线上招标项目信息", httpMethod = "POST", response = OutputDto.class, notes = "删除线上招标项目信息")
    public @ResponseBody
    JSONObject del(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 删除线上招标项目信息");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.del(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("删除线上招标项目信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/view/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看线上招标项目详情", httpMethod = "GET", response = OutputDto.class, notes = "查看线上招标项目详情")
    public @ResponseBody
    JSONObject view(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看线上招标项目详情");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.view(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看线上招标项目详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/pub/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发布线上招标项目", httpMethod = "GET", response = OutputDto.class, notes = "发布线上招标项目")
    public @ResponseBody
    JSONObject pub(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 发布线上招标项目");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.publish(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发布线上招标项目 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/cut", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线上招标项目截标", httpMethod = "POST", response = OutputDto.class, notes = "线上招标项目截标")
    public @ResponseBody
    JSONObject cut(
            @RequestBody IoBidProjOnCutDto dto) {
        logger.info("request for -> 线上招标项目截标");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.cut(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线上招标项目截标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/repeal", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线上招标项目申请废标", httpMethod = "POST", response = OutputDto.class, notes = "线上招标项目申请废标")
    public @ResponseBody
    JSONObject repeal(
            @RequestBody IoBidProjOnRepealDto dto) {
        logger.info("request for -> 线上招标项目申请废标");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.repeal(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线上招标项目申请废标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/queryBidProjs/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询线上招标项目投标信息", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询线上招标项目投标信息")
    public @ResponseBody
    JSONObject queryBidProjOns(
            @ApiParam(value = "pageNo", required = true) @PathVariable Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @PathVariable Integer pageSize,
            @RequestBody IoQueryBidProjOnSplrTendInfDto dto) {
        logger.info("request for -> 根据条件分页查询线上招标项目投标信息");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getBidInfByIoQueryBidProjOnSplrTendInfDto(dto, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询线上招标项目投标信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewBidDtl/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看线上招标项目供应商投标信息详情", httpMethod = "GET", response = OutputDto.class, notes = "查看线上招标项目供应商投标信息详情")
    public @ResponseBody
    JSONObject viewBidDtl(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        logger.info("request for -> 查看线上招标项目供应商投标信息详情");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.viewBidDtl(id);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看线上招标项目供应商投标信息详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/adtBidInf", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线上招标项目供应商投标审核：通过/拒绝", httpMethod = "POST", response = OutputDto.class, notes = "线上招标项目供应商投标审核：通过/拒绝")
    public @ResponseBody
    JSONObject adtBidInf(
            @RequestBody IoBidProjOnBidAdtDto dto) {
        logger.info("request for -> 线上招标项目供应商投标审核：通过/拒绝");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.adtBidInf(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线上招标项目供应商投标审核：通过/拒绝 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/sndOpenKey/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发送开标密钥", httpMethod = "GET", response = OutputDto.class, notes = "发送开标密钥")
    public @ResponseBody
    JSONObject sndOpenKey(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 发送开标密钥");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.sndOpenKey(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发送开标密钥 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/openProj", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线上招标项目开标", httpMethod = "POST", response = OutputDto.class, notes = "线上招标项目开标")
    public @ResponseBody
    JSONObject openProj(
            @RequestBody IoBidProjOnOpenDto dto) {
        logger.info("request for -> 线上招标项目开标");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.openProj(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线上招标项目开标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/getExptInfs", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取专家信息列表", httpMethod = "POST", response = OutputDto.class, notes = "获取专家信息列表")
    public @ResponseBody
    JSONObject getExptInfs(@RequestBody IoGetExptInfForPchsDto dto) {
        logger.info("request for -> 获取专家信息列表");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getExptInfs(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("获取专家信息列表 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/chsExpts", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线上招标项目选择评标专家", httpMethod = "POST", response = OutputDto.class, notes = "线上招标项目选择评标专家")
    public @ResponseBody
    JSONObject chsExpts(@RequestBody IoBidProjOnChsExptsDto dto) {
        logger.info("request for -> 线上招标项目选择评标专家");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.chsExpts(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线上招标项目选择评标专家 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/getQotInf/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据投标ID查看报价信息", httpMethod = "GET", response = OutputDto.class, notes = "根据投标ID查看报价信息")
    public @ResponseBody
    JSONObject getQotInf(
            @ApiParam(value = "id", required = true) @PathVariable Long id) {
        logger.info("request for -> 根据投标ID查看报价信息");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getQotInf(id);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据投标ID查看报价信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/endGrd/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "结束评标", httpMethod = "GET", response = OutputDto.class, notes = "结束评标")
    public @ResponseBody
    JSONObject endGrd(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 结束评标");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.endGrd(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("结束评标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/getTendSplrs", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取投标供应商", httpMethod = "POST", response = OutputDto.class, notes = "获取投标供应商")
    public @ResponseBody
    JSONObject getTendSplrs(
            @RequestBody IoGetTendSplrsDto dto) {
        logger.info("request for -> 获取投标供应商");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getTendSplrs(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("获取投标供应商 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/getOpenRcds/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取开标记录表", httpMethod = "GET", response = OutputDto.class, notes = "获取开标记录表")
    public @ResponseBody
    JSONObject getOpenRcds(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 获取开标记录表");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getOpenRcds(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("获取开标记录表 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/startQot2", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发起二次竞价", httpMethod = "POST", response = OutputDto.class, notes = "发起二次竞价")
    public @ResponseBody
    JSONObject startQot2(
            @RequestBody IoStartQot2Dto dto) {
        logger.info("request for -> 发起二次竞价");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.startQot2(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发起二次竞价 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/getGrdInf/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取評標信息", httpMethod = "POST", response = OutputDto.class, notes = "获取評標信息")
    public @ResponseBody
    JSONObject showGrdInf(
            @ApiParam(value = "pageNo", required = true) @PathVariable Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @PathVariable Integer pageSize,
            @RequestBody IoShowGrdInfDto dto) {
        logger.info("request for -> 获取評標信息");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getGrdInf(dto, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("获取評標信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/getGrdDtls/{pageNo}/{pageSize}/{grdId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据评标ID查看评标详情", httpMethod = "GET", response = OutputDto.class, notes = "根据评标ID查看评标详情")
    public @ResponseBody
    JSONObject getScrDtls(
            @ApiParam(value = "pageNo", required = true) @PathVariable Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @PathVariable Integer pageSize,
            @ApiParam(value = "grdId", required = true) @PathVariable Long grdId) {
        logger.info("request for -> 根据评标ID查看评标详情");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getGrdDtls(grdId, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据评标ID查看评标详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/getAppAwdInf/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取申请决标相关信息", httpMethod = "POST", response = OutputDto.class, notes = "申请决标")
    public @ResponseBody
    JSONObject getAppAwdInf(@ApiParam(value = "grdId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 申请决标");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getAppAwdInf(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("获取申请决标相关信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/appAwd", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "申请决标", httpMethod = "POST", response = OutputDto.class, notes = "申请决标")
    public @ResponseBody
    JSONObject appAwd(HttpServletRequest request, @RequestBody IoAppAwdDto dto) {
        logger.info("request for -> 申请决标");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.appAwd(request, dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("申请决标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
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
            OutputDto outputDto = bidProjOnMngForPchsService.viewAwdInf(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看招标结果 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/pubRst/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发布招标结果", httpMethod = "GET", response = OutputDto.class, notes = "查看招标结果")
    public @ResponseBody
    JSONObject pubRst(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看招标结果");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.pubRst(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看招标结果 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/pubRplRst", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发布废标结果", httpMethod = "POST", response = OutputDto.class, notes = "发布废标结果")
    public @ResponseBody JSONObject pubRplRst(@RequestBody IoPubRplRstDto dto) {
        logger.info("request for -> 发布废标结果");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.pubRplRst(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发布废标结果 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/getReqMatInfs/{pageNo}/{pageSize}/{orgIdsStr}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取需求中的物料信息", httpMethod = "GET", response = OutputDto.class, notes = "获取需求中的物料信息")
    public @ResponseBody JSONObject getReqMatInfs(@ApiParam(value = "pageNo", required = true) @PathVariable Integer pageNo,
                                                  @ApiParam(value = "pageSize", required = true) @PathVariable Integer pageSize,
                                                  @ApiParam(value = "orgIdsStr") @PathVariable String orgIdsStr) {
        logger.info("request for -> 获取需求中的物料信息");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getReqMatInfs(orgIdsStr, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.error("获取需求中的物料信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/getMatInfsFromExcel", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "从Excel中导入物料信息", httpMethod = "POST", response = OutputDto.class, notes = "从Excel中导入物料信息")
    public @ResponseBody
    JSONObject getMatInfsFromExcel(@ApiParam(value = "从Excel中导入物料信息", required = true) @RequestParam("file") MultipartFile file) {
        logger.info("request for -> 从Excel中导入物料信息");
        try {
            String[] fieldNames = {
                    "matCod",   // 物料编码
                    "matNam",   // 物料名称
                    "matUnt",   // 物料单位
                    "pchsNum",  // 采购数量
                    "techServ", // 技术与服务指标
                    "dlvDteStr",   // 交货日期
                    "dlvAdr",   // 交货地址
                    "memo"      // 备注
            };
            List list = PoiUtils.importObjFromExcel(file, Arrays.asList(fieldNames), IoBidProjOnMatInfDto.class);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc(), list).toJson();
        } catch (Exception e) {
            logger.info("从Excel中导入物料信息  - 异常 - Exception: " + ExceptionUtils.getStackTrace(e));
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "从Excel中导入物料信息  - 异常 - Exception: " + e.getMessage()).toJson();
        }
    }


    @RequestMapping(value = "/expertOpenInf/{projId}", method = RequestMethod.GET)
    @ApiOperation(value = "导出开标记录表pdf", httpMethod = "GET", response = ResponseEntity.class, notes = "导出开标记录表pdf")
    public ResponseEntity<byte[]> expertOpenInf(@ApiParam(value = "projId", required = true) @PathVariable Long projId,
                                                HttpServletRequest request) {
        logger.info("request for -> 导出开标记录表pdf");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            return bidProjOnMngForPchsService.exportOpenInf(request, projId);
        } catch (Exception e) {
            logger.error("导出开标记录表pdf  - 异常 - Exception: " + ExceptionUtils.getStackTrace(e));
            String errMsg = "导出开标记录表pdf  - 异常 - Exception: " + e.getMessage();
            String downFileName = null;
            byte[] errMsgBytes = null;
            try {
                downFileName = new String(("导出开标记录表pdf失败").getBytes("UTF-8"),"iso-8859-1");
                errMsgBytes = errMsg.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e1) {
                logger.error("导出开标记录表pdf  - 异常 - Exception: " + ExceptionUtils.getStackTrace(e1));
            }
            headers.setContentDispositionFormData("attachment", downFileName);
            return new ResponseEntity<byte[]>(errMsgBytes,
                    headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    根据物料名称搜索需求中的物料信息
    */
    @RequestMapping(value = "/getReqMatInfsByName/{pageNo}/{pageSize}/{matName}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据物料名称搜索需求中的物料信息", httpMethod = "GET", response = OutputDto.class, notes = "根据物料名称搜索需求中的物料信息")
    public @ResponseBody JSONObject getReqMatInfsByName(@ApiParam(value = "pageNo", required = true) @PathVariable Integer pageNo,
                                                        @ApiParam(value = "pageSize", required = true) @PathVariable Integer pageSize,
                                                        @ApiParam(value = "matName") @PathVariable String matName) {
        logger.info("request for -> 根据物料名称搜索需求中的物料信息");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getReqMatInfsByName(pageNo, pageSize,matName);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.error("根据物料名称搜索需求中的物料信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    /*
    * 根据物料编码自动绑定物料信息
    * */
    @RequestMapping(value = "/getMatInfsByMatCod/{matCod}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据物料编码绑定物料信息", httpMethod = "GET", response = OutputDto.class, notes = "根据物料编码绑定物料信息")
    public @ResponseBody JSONObject getMatInfsByMatCod(@ApiParam(value = "matCod") @PathVariable String matCod) {
        logger.info("request for -> 根据物料编码绑定物料信息");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getMatInfsByMatCod(matCod);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.error("根据物料编码绑定物料信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }
   /* @RequestMapping(value = "/updateGrdRes/{grdId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "修改专家评标状态", httpMethod = "GET", response = OutputDto.class, notes = "修改专家评标状态")
    public @ResponseBody
    JSONObject updateGrdResult(
            @ApiParam(value = "grdId", required = true) @PathVariable Long grdId) {
        logger.info("request for -> 修改专家评标状态");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.updateGrdRes(grdId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info(" 修改专家评标状态- 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }*/

   /* @RequestMapping(value = "/sendAwdToExpt", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发送定标结果给专家", httpMethod = "POST", response = OutputDto.class, notes = "发送定标结果给专家")
    public @ResponseBody JSONObject sendAwdToExpt(@RequestBody IoAppAwdDto appAwdDto) {
        logger.info("request for -> 发送定标结果给专家");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.sendAwdToExpt(appAwdDto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发送定标结果给专家 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }*/


   /* @RequestMapping(value = "/getExptAwdRes/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看专家审批结果信息", httpMethod = "GET", response = OutputDto.class, notes = "查看专家审批结果信息")
    public @ResponseBody
    JSONObject getExptAwdRes(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看专家审批结果信息");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.getExptAwdRes(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看专家审批结果信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }*/

   /* @RequestMapping(value = "/updateExptAppRes/{grdId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "修改专家审批状态", httpMethod = "GET", response = OutputDto.class, notes = "修改专家审批状态")
    public @ResponseBody
    JSONObject updateExptAppRes(
            @ApiParam(value = "grdId", required = true) @PathVariable Long grdId) {
        logger.info("request for -> 修改专家审批状态");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.updateExptAppRes(grdId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info(" 修改专家审批状态- 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }*/


    @RequestMapping(value = "/collectGrdInf/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看评标汇总", httpMethod = "GET", response = OutputDto.class, notes = "查看评标汇总")
    public @ResponseBody
    JSONObject collectGrdInf(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看评标汇总");
        try {
            OutputDto outputDto = bidProjOnMngForPchsService.collectGrdInf(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看评标汇总 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

}
