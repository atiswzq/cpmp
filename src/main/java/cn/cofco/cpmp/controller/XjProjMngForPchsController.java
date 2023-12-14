package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IXjProjMngForPchsService;
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
 * Created by wzq on 2018/1/12.
 * for [询价项目管理 - 工厂采购员 控制器类] in cpmp
 */
@Controller
@RequestMapping("/xjProjMngForPchs")
public class XjProjMngForPchsController {
    private static Logger logger = LoggerManager.getBidOnlineMngLog();

    @Resource
    private IXjProjMngForPchsService xjProjMngForPchsService;

    @RequestMapping(value = "/queryXjProjs/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询询价项目信息", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询询价项目信息")
    public @ResponseBody
    JSONObject queryProjs(
            @ApiParam(value = "pageNo", defaultValue = "1") @PathVariable Integer pageNo,
            @ApiParam(value = "pageSize", defaultValue = "20") @PathVariable Integer pageSize,
            @RequestBody IoQueryXjProjForPchsDto dto) {
        logger.info("request for -> 根据条件分页查询询价项目信息");
        try {
            OutputDto outputDto = xjProjMngForPchsService.getPagedResultByIoQueryXjProjForPchsDto(dto, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询询价项目信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "保存询价项目信息", httpMethod = "POST", response = OutputDto.class, notes = "保存询价项目信息")
    public @ResponseBody
    JSONObject saveOrSub(HttpServletRequest request,
                         @RequestBody IoXjProjDto dto) {
        logger.info("request for -> 保存询价项目信息");
        try {
            OutputDto outputDto = xjProjMngForPchsService.save(request, dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("保存询价项目信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/del/{projId}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除询价项目信息", httpMethod = "POST", response = OutputDto.class, notes = "删除询价项目信息")
    public @ResponseBody
    JSONObject del(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 删除询价项目信息");
        try {
            OutputDto outputDto = xjProjMngForPchsService.del(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("删除询价项目信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/view/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看询价项目详情", httpMethod = "GET", response = OutputDto.class, notes = "查看询价项目详情")
    public @ResponseBody
    JSONObject view(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 查看询价项目详情");
        try {
            OutputDto outputDto = xjProjMngForPchsService.view(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看询价项目详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/pub/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发布询价项目", httpMethod = "GET", response = OutputDto.class, notes = "发布询价项目")
    public @ResponseBody
    JSONObject pub(
            @ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 发布询价项目");
        try {
            OutputDto outputDto = xjProjMngForPchsService.publish(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发布询价项目 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/cut", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "询价项目截标", httpMethod = "POST", response = OutputDto.class, notes = "询价项目截标")
    public @ResponseBody
    JSONObject cut(
            @RequestBody IoXjProjCutDto dto) {
        logger.info("request for -> 询价项目截标");
        try {
            OutputDto outputDto = xjProjMngForPchsService.cut(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("询价项目截标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/repeal", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "询价项目申请废标", httpMethod = "POST", response = OutputDto.class, notes = "询价项目申请废标")
    public @ResponseBody
    JSONObject repeal(
            @RequestBody IoXjProjRepealDto dto) {
        logger.info("request for -> 询价项目申请废标");
        try {
            OutputDto outputDto = xjProjMngForPchsService.repeal(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("询价项目申请废标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/queryBidXjProjs/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询询价项目投标信息", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询询价项目投标信息")
    public @ResponseBody
    JSONObject queryBidXjProjs(
            @ApiParam(value = "pageNo", required = true) @PathVariable Integer pageNo,
            @ApiParam(value = "pageSize", required = true) @PathVariable Integer pageSize,
            @RequestBody IoQueryXjProjSplrTendInfDto dto) {
        logger.info("request for -> 根据条件分页查询询价项目投标信息");
        try {
            OutputDto outputDto = xjProjMngForPchsService.getBidInfByIoQueryXjProjSplrTendInfDto(dto, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询询价项目投标信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/viewBidDtl/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "查看询价项目供应商投标信息详情", httpMethod = "GET", response = OutputDto.class, notes = "查看询价项目供应商投标信息详情")
    public @ResponseBody
    JSONObject viewBidDtl(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        logger.info("request for -> 查看询价项目供应商投标信息详情");
        try {
            OutputDto outputDto = xjProjMngForPchsService.viewBidDtl(id);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看询价项目供应商投标信息详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/adtBidInf", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "询价项目供应商投标审核：通过/拒绝", httpMethod = "POST", response = OutputDto.class, notes = "询价项目供应商投标审核：通过/拒绝")
    public @ResponseBody
    JSONObject adtBidInf(
            @RequestBody IoXjProjBidAdtDto dto) {
        logger.info("request for -> 询价项目供应商投标审核：通过/拒绝");
        try {
            OutputDto outputDto = xjProjMngForPchsService.adtBidInf(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("询价项目供应商投标审核：通过/拒绝 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
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
            OutputDto outputDto = xjProjMngForPchsService.sndOpenKey(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发送开标密钥 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/openProj", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "询价项目开标", httpMethod = "POST", response = OutputDto.class, notes = "询价项目开标")
    public @ResponseBody
    JSONObject openProj(
            @RequestBody IoXjProjOpenDto dto) {
        logger.info("request for -> 询价项目开标");
        try {
            OutputDto outputDto = xjProjMngForPchsService.openProj(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("询价项目开标 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
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
            OutputDto outputDto = xjProjMngForPchsService.getQotInf(id);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据投标ID查看报价信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
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
            OutputDto outputDto = xjProjMngForPchsService.getTendSplrs(dto);
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
            OutputDto outputDto = xjProjMngForPchsService.getOpenRcds(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("获取开标记录表 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/getAppAwdInf/{projId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取申请决标相关信息", httpMethod = "GET", response = OutputDto.class, notes = "申请决标")
    public @ResponseBody
    JSONObject getAppAwdInf(@ApiParam(value = "projId", required = true) @PathVariable Long projId) {
        logger.info("request for -> 申请决标");
        try {
            OutputDto outputDto = xjProjMngForPchsService.getAppAwdInf(projId);
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
    JSONObject appAwd(HttpServletRequest request, @RequestBody IoXjAppAwdDto dto) {
        logger.info("request for -> 申请决标");
        try {
            OutputDto outputDto = xjProjMngForPchsService.appAwd(request, dto);
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
            OutputDto outputDto = xjProjMngForPchsService.viewAwdInf(projId);
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
            OutputDto outputDto = xjProjMngForPchsService.pubRst(projId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("查看招标结果 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/pubRplRst", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发布废标结果", httpMethod = "POST", response = OutputDto.class, notes = "发布废标结果")
    public @ResponseBody
    JSONObject pubRplRst(@RequestBody IoPubRplRstDto dto) {
        logger.info("request for -> 发布废标结果");
        try {
            OutputDto outputDto = xjProjMngForPchsService.pubRplRst(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发布废标结果 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/getReqMatInfs/{pageNo}/{pageSize}/{orgIdsStr}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取需求中的物料信息", httpMethod = "GET", response = OutputDto.class, notes = "获取需求中的物料信息")
    public @ResponseBody
    JSONObject getReqMatInfs(@ApiParam(value = "pageNo", required = true) @PathVariable Integer pageNo,
                             @ApiParam(value = "pageSize", required = true) @PathVariable Integer pageSize,
                             @ApiParam(value = "orgIdsStr") @PathVariable String orgIdsStr) {
        logger.info("request for -> 获取需求中的物料信息");
        try {
            OutputDto outputDto = xjProjMngForPchsService.getReqMatInfs(orgIdsStr, pageNo, pageSize);
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
            List list = PoiUtils.importObjFromExcel(file, Arrays.asList(fieldNames), IoXjProjMatInfDto.class);
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
            return xjProjMngForPchsService.exportOpenInf(request, projId);
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

}
