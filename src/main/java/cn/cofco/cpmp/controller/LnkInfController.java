package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.LnkInf;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ILnkInfService;
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
 * for [门户网站 - 链接管理 控制器类] in cpmp
 */
@Controller
@RequestMapping("/lnkInf")
public class LnkInfController {

    private static Logger logger = LoggerManager.getPortalMngLog();

    @Resource
    private ILnkInfService lnkInfService;

    @RequestMapping(value = "/queryByConds/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询链接信息", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询链接信息")
    public @ResponseBody
    JSONObject queryBidProjOns(HttpServletRequest request,
                               @ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                               @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
                               @RequestBody LnkInf entity) {
        logger.info("request for -> 根据条件分页查询链接信息");
        try {
            OutputDto outputDto = lnkInfService.queryByConds(entity, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询链接信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }



    @RequestMapping(value = "/view/{lnkId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据ID查询链接详情", httpMethod = "GET", response = OutputDto.class, notes = "根据ID查询链接详情")
    public @ResponseBody
    JSONObject view(HttpServletRequest request,
                    @ApiParam(value = "lnkId", required = true) @PathVariable Long lnkId) {
        logger.info("request for -> 根据ID查询链接详情");
        try {
            OutputDto outputDto = lnkInfService.view(lnkId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据ID查询链接详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "新增链接信息", httpMethod = "POST", response = OutputDto.class, notes = "新增链接信息")
    public @ResponseBody
    JSONObject add(HttpServletRequest request, @RequestBody LnkInf entity) {
        logger.info("request for -> 新增链接信息");
        try {
            OutputDto outputDto = lnkInfService.add(entity);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("新增链接信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "编辑链接信息", httpMethod = "POST", response = OutputDto.class, notes = "编辑链接信息")
    public @ResponseBody
    JSONObject edit(HttpServletRequest request, @RequestBody LnkInf entity) {
        logger.info("request for -> 编辑链接信息");
        try {
            OutputDto outputDto = lnkInfService.edit(entity);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("编辑链接信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/del/{lnkId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除链接信息", httpMethod = "GET", response = OutputDto.class, notes = "删除链接信息")
    public @ResponseBody
    JSONObject del(HttpServletRequest request,
                   @ApiParam(value = "lnkId", required = true) @PathVariable Long lnkId) {
        logger.info("request for -> 删除链接信息");
        try {
            OutputDto outputDto = lnkInfService.del(lnkId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("删除链接信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/pub/{lnkId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发布链接信息", httpMethod = "GET", response = OutputDto.class, notes = "发布链接信息")
    public @ResponseBody
    JSONObject pub(HttpServletRequest request,
                   @ApiParam(value = "lnkId", required = true) @PathVariable Long lnkId) {
        logger.info("request for -> 发布链接信息");
        try {
            OutputDto outputDto = lnkInfService.pub(lnkId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发布链接信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/cclPub/{lnkId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "取消发布链接信息", httpMethod = "GET", response = OutputDto.class, notes = "取消发布链接信息")
    public @ResponseBody
    JSONObject cclPub(HttpServletRequest request,
                   @ApiParam(value = "lnkId", required = true) @PathVariable Long lnkId) {
        logger.info("request for -> 取消发布链接信息");
        try {
            OutputDto outputDto = lnkInfService.cclPub(lnkId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("取消发布链接信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }
}
