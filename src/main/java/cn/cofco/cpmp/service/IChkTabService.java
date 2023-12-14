package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.OutputDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xsmiler on 2017/7/19.
 */
public interface IChkTabService {

    /**
     * 根据考核表id下载考核表
     * @param response
     * @param id
     * @throws Exception
     */
    void downloadTempById(HttpServletResponse response, Long id) throws Exception;

    /**
     * 上传考核表
     *
     * @param request
     * @param chkTyp
     * @param id
     * @param splrId
     * @param file  @return
     * @throws Exception
    OutputDto uploadChkTab(HttpServletRequest request, String chkTyp, Long id, Long splrId, MultipartFile file) throws Exception;*/
}
