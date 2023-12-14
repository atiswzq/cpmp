package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Adv;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.service.IAdvService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Xujy on 2017/4/29.
 */
@Controller
@RequestMapping("/adv")
public class AdvController {

    private static Logger logger = LoggerFactory.getLogger("portal_mng");

    @Resource
    private IAdvService advService;

    @RequestMapping(value="/add", method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value = "新增广告信息", httpMethod = "POST", response = OutputDto.class, notes = "新增广告信息")
    public @ResponseBody JSONObject add(HttpServletRequest request,
                   @ApiParam(value = "用户信息", required = true) @RequestBody(required = true) Adv entity) {
        logger.info("request for -> 新增广告信息");
        try {
            OutputDto outputDto = advService.add(entity);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("新增广告信息 - 异常 - Exception: " + ExceptionUtils.getStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

}
