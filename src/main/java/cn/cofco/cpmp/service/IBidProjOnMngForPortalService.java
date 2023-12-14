package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.OutputDto;

/**
 * Created by Xujy on 2017/5/17.
 * for [线上招标项目管理 - 门户网站] in cpmp
 */
public interface IBidProjOnMngForPortalService {

    /**
     * 分页查询线上招标项目列表
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     * @throws Exception
     */
    OutputDto queryProjs(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;

    /**
     * 根据项目ID查询线上招标信息详情
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewProjDtl(Long projId) throws Exception;

    /**
     * 分页查询线上招标结果列表
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     * @throws Exception
     */
    OutputDto queryProjRsts(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;

    /**
     * 根据项目ID查询线上招标结果详情
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewProjRstDtl(Long projId) throws Exception;
}
