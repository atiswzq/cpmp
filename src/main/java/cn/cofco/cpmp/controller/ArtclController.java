package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.IoArtclDtlDto;
import cn.cofco.cpmp.dto.IoArtclQureyDto;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IArtclService;
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
 * for [门户网站 - 文章管理 控制器类] in cpmp
 */
@Controller
@RequestMapping("/artcl")
public class ArtclController {

    private static Logger logger = LoggerManager.getPortalMngLog();

    @Resource
    private IArtclService artclService;

    @RequestMapping(value = "/queryByConds/{pageNo}/{pageSize}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据条件分页查询文章信息", httpMethod = "POST", response = OutputDto.class, notes = "根据条件分页查询文章信息")
    public @ResponseBody
    JSONObject queryBidProjOns(HttpServletRequest request,
                               @ApiParam(value = "pageNo", required = false) @PathVariable Integer pageNo,
                               @ApiParam(value = "pageSize", required = false) @PathVariable Integer pageSize,
                               @RequestBody IoArtclQureyDto dto) {
        logger.info("request for -> 根据条件分页查询文章信息");
        try {
            OutputDto outputDto = artclService.queryByConds(dto, pageNo, pageSize);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据条件分页查询文章信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }



    @RequestMapping(value = "/view/{artclId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据ID查询文章详情", httpMethod = "GET", response = OutputDto.class, notes = "根据ID查询文章详情")
    public @ResponseBody
    JSONObject view(HttpServletRequest request,
                    @ApiParam(value = "artclId", required = true) @PathVariable Long artclId) {
        logger.info("request for -> 根据ID查询文章详情");
        try {
            OutputDto outputDto = artclService.view(artclId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("根据ID查询文章详情 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "新增文章信息", httpMethod = "POST", response = OutputDto.class, notes = "新增文章信息")
    public @ResponseBody
    JSONObject add(@RequestBody IoArtclDtlDto dto) {
        logger.info("request for -> 新增文章信息");
        try {
            OutputDto outputDto = artclService.add(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("新增文章信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "编辑文章信息", httpMethod = "POST", response = OutputDto.class, notes = "编辑文章信息")
    public @ResponseBody
    JSONObject edit(HttpServletRequest request,
                               @RequestBody IoArtclDtlDto dto) {
        logger.info("request for -> 编辑文章信息");
        try {
            OutputDto outputDto = artclService.edit(dto);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("编辑文章信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/del/{artclId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "删除文章信息", httpMethod = "GET", response = OutputDto.class, notes = "删除文章信息")
    public @ResponseBody
    JSONObject del(HttpServletRequest request,
                   @ApiParam(value = "artclId", required = true) @PathVariable Long artclId) {
        logger.info("request for -> 删除文章信息");
        try {
            OutputDto outputDto = artclService.del(artclId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("删除文章信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/pub/{artclId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "发布文章信息", httpMethod = "GET", response = OutputDto.class, notes = "发布文章信息")
    public @ResponseBody
    JSONObject pub(HttpServletRequest request,
                   @ApiParam(value = "artclId", required = true) @PathVariable Long artclId) {
        logger.info("request for -> 发布文章信息");
        try {
            OutputDto outputDto = artclService.pub(artclId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("发布文章信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }


    @RequestMapping(value = "/cclPub/{artclId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "取消发布文章信息", httpMethod = "GET", response = OutputDto.class, notes = "取消发布文章信息")
    public @ResponseBody
    JSONObject cclPub(HttpServletRequest request,
                   @ApiParam(value = "artclId", required = true) @PathVariable Long artclId) {
        logger.info("request for -> 取消发布文章信息");
        try {
            OutputDto outputDto = artclService.cclPub(artclId);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("取消发布文章信息 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }
}
