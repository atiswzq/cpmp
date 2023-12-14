package cn.cofco.cpmp.controller;

import java.util.List;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.mdm.entity.MdmMateriel;
import cn.cofco.cpmp.service.IMdmService;
import cn.cofco.cpmp.splr.vo.SplrVo;
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
 *
 * @author xsmiler
 * @date 2017/06/10
 */
@Controller
@RequestMapping("/cpmp")
public class MdmController {

    private Logger logger = LoggerManager.getSplrMngLog();

    @Resource
    private IMdmService iMdmService;

//    @RequestMapping(value = "/splr", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
//    @ApiOperation(value = "采购平台供应商信息接收接口", httpMethod = "POST", response = OutputDto.class, notes = "采购平台供应商信息接收接口")
//    public @ResponseBody
//    JSONObject splrList(
//            HttpServletRequest request,
//            @ApiParam(value = "供应商信息列表", required = true) @RequestBody(required = true) MdmMateriel mdmMateriel) {
//        logger.info("request for -> 采购平台供应商信息接收接口");
//
//        
//        System.out.println(mdmMateriel);
//        try {
//            OutputDto outputDto = null;
//            return outputDto.toJson();
//        } catch (Exception e) {
//            logger.debug(e.getMessage());
//            logger.info("采购平台供应商信息接收接口  - 异常 - Exception: "
//                    + ExceptionUtils.getStackTrace(e));
//            OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
//                    RtnEnum.SYS_ERR, e.getMessage());
//            return outputDto.toJson();
//        }
//    }
    
    @RequestMapping(value = "/splrQuery/{id}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value = "测试供应商验证接口", httpMethod = "POST", response = OutputDto.class, notes = "测试供应商验证接口")
    public @ResponseBody
    JSONObject splrQuery(
            HttpServletRequest request,
            @ApiParam(value = "供应商ID", required = true) @PathVariable(required = true) Long id) {
        logger.info("request for -> 测试供应商验证接口");

        try {
            boolean outputDto = iMdmService.splrQuery(id);
            return null;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            logger.info("测试供应商验证接口  - 异常 - Exception: "
                    + ExceptionUtils.getStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
                    RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }

    @RequestMapping(value = "/splrAply/{id}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value = "测试供应商申请接口", httpMethod = "POST", response = OutputDto.class, notes = "测试供应商申请接口")
    public @ResponseBody
    JSONObject splrAply(
            HttpServletRequest request,
            @ApiParam(value = "供应商ID", required = true) @PathVariable(required = true) Long id) {
        logger.info("request for -> 测试供应商申请接口");
        OutputDto outputDto = null;
        try {
            boolean isExist = iMdmService.splrQuery(id);

            if (!isExist) {
                logger.info("该供应商：{}不存在", id);
                try {
                    outputDto = iMdmService.splrAply(id, true);
                } catch (Exception e) {
                    logger.error("发起mdm申请失败", e);
                    outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
                            RtnEnum.SYS_ERR, e.getMessage());
                    return outputDto.toJson();
                }
                return outputDto.toJson();
            } else {
                outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
                        RtnEnum.SYS_ERR, "客商数据已存在！");
                return outputDto.toJson();
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            logger.info("测试供应商申请接口  - 异常 - Exception: "
                    + ExceptionUtils.getStackTrace(e));
            outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
                    RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }
}
