package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.BidProjOnExptAppResDto;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.exptgrd.IoGrdInfLowPostDto;
import cn.cofco.cpmp.entity.BidProjOnExptGrdDtl;
import cn.cofco.cpmp.entity.BidProjOnExptGrdInf;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOnMngForExptService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Xujy on 2017/5/30.
 * for [线上项目管理 - 专家评标 控制器类] in cpmp
 */

@Controller
@RequestMapping("/bidProjOnMngForExpt")
public class BidProjOnMngForExptController {

    private static Logger logger = LoggerManager.getExptMngLog();

    @Resource
    private IBidProjOnMngForExptService bidProjOnMngForExptService;


    @RequestMapping(value = "/queryGrdInfs/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询评标项目", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询评标项目")
    public @ResponseBody
    JSONObject queryGrdInfs(@ApiParam(value = "pageNo", required = true) @PathVariable Integer pageNo,
                            @ApiParam(value = "pageSize", required = true) @PathVariable Integer pageSize,
                            @ApiParam(value = "IsMCompany", required = false) @RequestHeader(required = false) String IsMCompany,
                            @RequestBody BidProjOnExptGrdInf entity) {
        logger.info("request for -> 根据条件分页查询评标项目");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.queryGrdInfs(entity, pageNo, pageSize,IsMCompany);
            return outputDto.toJson();

        } catch (Exception e) {
            logger.info("根据条件分页查询评标项目 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/getScrDtls/{pageNo}/{pageSize}/{grdId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据评标ID查看评分详情", httpMethod = "GET", response = OutputDto.class, notes = "根据评标ID查看评分详情")
    public @ResponseBody
    JSONObject getScrDtls(@ApiParam(value = "pageNo", required = true) @PathVariable Integer pageNo,
                          @ApiParam(value = "pageSize", required = true) @PathVariable Integer pageSize,
                          @ApiParam(value = "grdId", required = true) @PathVariable Long grdId) {
        logger.info("request for -> 根据评标ID查看评分详情");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.getScrDtls(grdId, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据评标ID查看评分详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/sndGrdKey/{grdId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发送评标密钥", httpMethod = "GET", response = OutputDto.class, notes = "发送评标密钥")
    public @ResponseBody
    JSONObject sndOpenKey(@ApiParam(value = "grdId", required = true) @PathVariable Long grdId) {
        logger.info("request for -> 发送评标密钥");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.sndGrdKey(grdId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发送评标密钥 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/viewBidDtl/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看线上招标项目评分详情", httpMethod = "GET", response = OutputDto.class, notes = "查看线上招标项目供应商投标信息详情")
    public @ResponseBody
    JSONObject viewBidDtl(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        logger.info("request for -> 查看线上招标项目供应商投标信息详情");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.viewBidDtl(id);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看线上招标项目供应商投标信息详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/bgnGrd", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "开始评标", httpMethod = "POST", response = OutputDto.class, notes = "开始评标")
    public @ResponseBody
    JSONObject bgnGrd(@RequestBody BidProjOnExptGrdInf entity) {
        logger.info("request for -> 开始评标");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.bgnGrd(entity);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("开始评标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @Deprecated()
    @RequestMapping(value = "/score", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线上招标项目打分", httpMethod = "POST", response = OutputDto.class, notes = "线上招标项目打分")
    public @ResponseBody
    JSONObject score(@RequestBody BidProjOnExptGrdDtl entity) {
        logger.info("request for -> 线上招标项目打分");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.score(entity);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线上招标项目打分 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @Deprecated()
    @RequestMapping(value = "/subScrRst/{grdId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "线上招标项目提交打分结果", httpMethod = "GET", response = OutputDto.class, notes = "线上招标项目提交打分结果")
    public @ResponseBody
    JSONObject subScrRst(@ApiParam(value = "grdId", required = true) @PathVariable Long grdId) {
        logger.info("request for -> 线上招标项目提交打分结果");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.subScrRst(grdId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("线上招标项目提交打分结果 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/showExptInf", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取专家信息", httpMethod = "GET", response = OutputDto.class, notes = "获取专家信息")
    public @ResponseBody
    JSONObject showExptInf() {
        logger.info("request for -> 获取专家信息");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.showExptInf();
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("获取专家信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }
/*
    @RequestMapping(value = "/showAwdInf/{grdId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看定标结果信息", httpMethod = "GET", response = OutputDto.class, notes = "查看定标结果信息")
    public @ResponseBody
    JSONObject showAwdInf(@ApiParam(value = "grdId", required = true) @PathVariable Long grdId) {
        logger.info("request for -> 查看定标结果信息");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.showAwdInf(grdId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看定标结果信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }*/

   /* @RequestMapping(value = "/subAppRes", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "专家审批定标结果", httpMethod = "POST", response = OutputDto.class, notes = "专家审批定标结果")
    public @ResponseBody
    JSONObject subAppRes(@RequestBody BidProjOnExptAppResDto entity) {
        logger.info("request for -> 专家审批定标结果");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.subAppRes(entity);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("专家审批定标结果 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }*/

    @RequestMapping(value = "/viewGrdInf/{grdId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "展示评标信息", httpMethod = "GET", response = OutputDto.class, notes = "展示评标信息")
    public @ResponseBody
    JSONObject viewGrdInf(@ApiParam(value = "grdId", required = true) @PathVariable Long grdId) {
        logger.info("request for -> 展示评标信息");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.viewGrdInf(grdId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("展示评标信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/postGrdInf", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "提交最低价评标信息", httpMethod = "POST", response = OutputDto.class, notes = "提交最低价评标信息")
    public @ResponseBody
    JSONObject postGrdInf(@RequestBody IoGrdInfLowPostDto dto) {
        logger.info("request for -> 提交最低价评标信息");
        try {
            OutputDto outputDto = bidProjOnMngForExptService.postGrdInf(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("提交最低价评标信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, "提交最低价评标信息 - 异常 - Exception: " + e.getMessage());
            return outputDto.toJson();
        }
    }


}
