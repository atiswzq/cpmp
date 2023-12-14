package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.support.OutputDtoUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Xujy on 2017/6/10.
 * for [外系统连通性测试] in cpmp
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static Logger logger =  LoggerManager.getSysLog();

    @RequestMapping(value = "", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value = "外系统连通性测试", httpMethod = "POST", response = OutputDto.class, notes = "外系统连通性测试")
    public @ResponseBody JSONObject test(HttpServletRequest request, @RequestBody Map map) {
        logger.info("request for -> 外系统连通性测试");
        try {
            OutputDto outputDto = OutputDtoUtil.setOutputDto(true, RtnEnum.SUC_OPR, map);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("外系统连通性测试 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

}
