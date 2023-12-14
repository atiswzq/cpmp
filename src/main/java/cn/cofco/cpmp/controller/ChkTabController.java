package cn.cofco.cpmp.controller;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IChkTabService;
import cn.cofco.cpmp.service.IFileService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xsmiler
 * @date 2017/07/19
 */
@Controller
@RequestMapping("/chkTab")
public class ChkTabController {

    private Logger logger = LoggerManager.getSplrLog();

    @Resource
    private IChkTabService chkTabService;

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "下载考核表模板", httpMethod = "GET", response = OutputDto.class, notes = "下载考核表模板")
    public void downloadTempById(
            HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "考核表模板ID", required = true) @PathVariable Long id) {
        logger.info("下载考核表模板");
        try {
            chkTabService.downloadTempById(response, id);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

   /* @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "上传考核表", httpMethod = "POST", response = OutputDto.class, notes = "上传考核表")
    public @ResponseBody JSONObject uploadChkTab(
            HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "考核表类型：aply-开发申请", required = true) @RequestHeader String chkTyp,
            @ApiParam(value = "考核表模板ID", required = true) @RequestHeader Long id,
            @ApiParam(value = "供应商ID", required = true) @RequestHeader Long splrId,
            @ApiParam(value = "考核表文件（.xlsx文件）", required = true)@RequestParam("file") MultipartFile file) {
        logger.info("request for -> 上传考核表");
        try {
            OutputDto outputDto = chkTabService.uploadChkTab(request, chkTyp, id, splrId, file);
            return outputDto.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            logger.info("上传考核表  - 异常 - Exception: "
                    + ExceptionUtils.getStackTrace(e));
            OutputDto outputDto = OutputDtoUtil.setOutputDto(false,
                    RtnEnum.SYS_ERR, e.getMessage());
            return outputDto.toJson();
        }
    }*/
}
