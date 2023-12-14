package cn.cofco.cpmp.service;


import cn.cofco.cpmp.dto.OutputDto;

/**
 * Created by Wzq on 2018/01/13.
 * for [询价项目管理 - 门户网站] in cpmp
 */
public interface IXjProjMngForPortalService {

    /**
     * 分页查询线上招标项目列表
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     * @throws Exception
     */
    OutputDto queryXjProjs(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;

    /**
     * 根据项目ID查询询价项目信息详情
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewXjProjDtl(Long projId) throws Exception;

    /**
     * 分页查询询价项目招标结果列表
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     * @throws Exception
     */
    OutputDto queryXjProjRsts(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;

    /**
     * 根据项目ID查询询价项目招标结果详情
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewXjProjRstDtl(Long projId) throws Exception;
}
