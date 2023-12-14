package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.splr.vo.SplrAdmtVo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xsmiler on 2017/7/9.
 */
public interface ISplrAdtService {


    /**
     * 供应商准入审核
     * @param splrAdmtVo
     * @return
     * @throws Exception
     */
    OutputDto splrAdmt(SplrAdmtVo splrAdmtVo) throws Exception;

    /**
     * 供应商开发申请
     * @param splrId
     * @return
     * @throws Exception
     */
    OutputDto splrAply(HttpServletRequest request, Long splrId) throws Exception;

    /**
     * 根据申请类型获取考核模板
     * @param type
     * @return
     * @throws Exception
     */
    OutputDto adtTempForType(String type) throws Exception;
}
