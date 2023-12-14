package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Adv;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.wsclient.saptest.*;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.BindingProvider;

/**
 * Created by Xujy on 2017/4/29.
 */
@Controller
@RequestMapping("/sap")
public class SapController {

    private static Logger logger = LoggerFactory.getLogger("portal_mng");

    @RequestMapping(value="/test", method= RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ApiOperation(value = "sap测试", httpMethod = "POST", response = OutputDto.class, notes = "sap测试")
    public @ResponseBody JSONObject test(HttpServletRequest request,
                   @ApiParam(value = "用户信息", required = true) @RequestBody(required = true) Adv entity) {
        logger.info("request for -> sap测试");
        try {

            SISRM2ECCPOOUTService service = new SISRM2ECCPOOUTService();
            SISRM2ECCPOOUT sisrm2ECCPOOUT = service.getHTTPPort();

            BindingProvider bp = (BindingProvider) sisrm2ECCPOOUT;
            bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,"appuser5");
            bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "a123456");

            ObjectFactory objectFactory = new ObjectFactory();
            DTSRM2ECCPOREQ dtsrm2ECCPOREQ = objectFactory.createDTSRM2ECCPOREQ();

            dtsrm2ECCPOREQ.setBUKRS("BUKRS1");
            dtsrm2ECCPOREQ.setEBELN("EBELN1");

            DTSRM2ECCPORESP dtsrm2ECCPORESP = sisrm2ECCPOOUT.siSRM2ECCPOOUT(dtsrm2ECCPOREQ);
//            System.out.println(dtsrm2ECCPORESP.toString());

            OutputDto outputDto = OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, dtsrm2ECCPORESP);
            return outputDto.toJson();
        } catch (Exception e) {
            logger.info("sap测试 - 异常 - Exception: " + ExceptionUtils.getStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false, RtnEnum.SYS_ERR);
            return outputDto.toJson();
        }
    }

}
