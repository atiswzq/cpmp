package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.SysParm;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.holder.SysParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISysParmService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Xujy on 2017/5/1.
 */
@Controller
@RequestMapping("/sysParm")
public class SysParmController {

    private static Logger logger = LoggerManager.getSysLog();

    @Resource
    private ISysParmService sysParmService;

    @RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "系统参数刷新", httpMethod = "GET", response = OutputDto.class, notes = "系统参数刷新")
    public
    @ResponseBody
    JSONObject refresh(HttpServletRequest request) {
        logger.info("request for -> 系统参数刷新");
        try {
            List<SysParm> sysParmList = sysParmService.getSysParmAll();
            SysParmHolder.loadSysParms(sysParmList);
            OutputDto outputDto = OutputDtoUtil.setOutputDto(true, RtnEnum.SUC_OPR, sysParmList);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("系统参数刷新 - 异常 - Exception: " + ExceptionUtils.getFullStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

}
