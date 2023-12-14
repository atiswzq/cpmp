package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.IoExptLoginDto;
import cn.cofco.cpmp.dto.IoExptModPswDto;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.ExptInf;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IExptService;
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
 * Created by Xujy on 2017/5/30.
 * for [专家访问控制器类] in cpmp
 */

@Controller
@RequestMapping("/expt")
public class ExptController {

    private static Logger logger = LoggerManager.getExptMngLog();

    @Resource
    private IExptService exptService;

    @RequestMapping(value = "/queryByConds/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件查询专家信息", httpMethod = "POST", response = OutputDto.class, notes = "根据条件查询专家信息")
    public @ResponseBody
    JSONObject queryByConds(HttpServletRequest request,
                            @ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                            @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
                            @RequestBody ExptInf entity) {
        logger.info("request for -> 根据条件查询专家信息");
        try {
            OutputDto outputDto = exptService.getExptInfsByConds(entity, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件查询专家信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "新增专家信息", httpMethod = "POST", response = OutputDto.class, notes = "新增专家信息")
    public @ResponseBody
    JSONObject add(HttpServletRequest request,
                         @RequestBody(required = true) ExptInf entity) {
        logger.info("request for -> 新增专家信息");
        try {
            OutputDto outputDto = exptService.add(entity);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("新增专家信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/del/{exptId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除专家信息", httpMethod = "GET", response = OutputDto.class, notes = "删除专家信息")
    public @ResponseBody
    JSONObject del(HttpServletRequest request,
                    @ApiParam(value = "exptId", required = false) @PathVariable(required = true) Long exptId) {
        logger.info("request for -> 删除专家信息");
        try {
            OutputDto outputDto = exptService.del(exptId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("删除专家信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/mod", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "修改专家信息", httpMethod = "POST", response = OutputDto.class, notes = "修改专家信息")
    public @ResponseBody
    JSONObject mod(HttpServletRequest request,
                   @RequestBody(required = true) ExptInf entity) {
        logger.info("request for -> 修改专家信息");
        try {
            OutputDto outputDto = exptService.mod(entity);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("修改专家信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/modPsw", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "修改密码", httpMethod = "POST", response = OutputDto.class, notes = "修改密码")
    public @ResponseBody
    JSONObject modPsw(HttpServletRequest request,
                   @RequestBody(required = true) IoExptModPswDto dto) {
        logger.info("request for -> 修改密码");
        try {
            OutputDto outputDto = exptService.modPsw(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("修改密码 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/resetPsw/{exptId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "重置密码", httpMethod = "GET", response = OutputDto.class, notes = "重置密码")
    public @ResponseBody
    JSONObject resetPsw(HttpServletRequest request,
                        @ApiParam(value = "exptId", required = false) @PathVariable(required = true) Long exptId) {
        logger.info("request for -> 重置密码");
        try {
            OutputDto outputDto = exptService.resetPsw(exptId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("重置密码 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "专家登录", httpMethod = "POST", response = OutputDto.class, notes = "专家登录")
    public @ResponseBody
    JSONObject login(HttpServletRequest request,
                   @RequestBody(required = true) IoExptLoginDto dto) {
        logger.info("request for -> 专家登录");
        try {
            OutputDto outputDto = exptService.login(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("专家登录 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/view/{exptId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据专家ID查询专家信息", httpMethod = "GET", response = OutputDto.class, notes = "根据专家ID查询专家信息")
    public @ResponseBody
    JSONObject view(HttpServletRequest request,
                    @ApiParam(value = "exptId", required = false) @PathVariable(required = true) Long exptId) {
        logger.info("request for -> 根据专家ID查询专家信息");
        try {
            OutputDto outputDto = exptService.view(exptId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据专家ID查询专家信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }
}
