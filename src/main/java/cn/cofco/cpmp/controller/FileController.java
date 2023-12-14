package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.holder.SysParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IFileService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.FileUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;

/**
 * @author xsmiler
 * @date 2017/06/15
 */
@Controller
@RequestMapping("/file")
public class FileController implements ServletConfigAware, ServletContextAware {

    private ServletContext servletContext;

    private ServletConfig servletConfig;

    private Logger logger = LoggerManager.getSysLog();

    @RequestMapping(value = "/upload/{type}", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "上传文件接口", httpMethod = "POST", response = OutputDto.class, notes = "上传文件接口")
    public @ResponseBody
    JSONObject upload(@ApiParam(value = "上传文件类型：atch-附件；img-图片；apt-资质文件；chk-考察表文件", required = true) @PathVariable String type
            , @ApiParam(value = "上传文件", required = true) @RequestParam("file") MultipartFile file
    ) {
        logger.info("request for -> 上传文件");

        // 1. 栏位校验
        if (!Arrays.stream(Constants.FILE_TYPE).anyMatch(a -> a.equalsIgnoreCase(type))) {
            return OutputDtoUtil
                    .setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR, "文件类型不支持").toJson();
        }

        try {
            String rootPath = servletConfig.getServletContext().getRealPath("/");
            String filePath = new File(rootPath + "..").getCanonicalPath();
            logger.info("file path :{}", filePath);
            String fileName = FileUtils.writeFile(filePath + File.separator + type, file);
            String path = type + "/" + fileName;
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc(), path).toJson();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            logger.info("上传文件  - 异常 - Exception: " + ExceptionUtils.getStackTrace(e));
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR, e.getMessage()).toJson();
        }

    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
