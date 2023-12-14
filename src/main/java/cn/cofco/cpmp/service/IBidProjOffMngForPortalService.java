package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.IoQueryBidProjOffForPchsDto;
import cn.cofco.cpmp.dto.OutputDto;

/**
 * Created by Tao on 2017/6/19.
 * for [线下标项目管理 - 门户网站] in cpmp
 * 首页招标发布公告列表和详情查询
 * 首页招标结果公告列表和详情查询
 */
public interface IBidProjOffMngForPortalService {

    /**
     * 获取分页信息
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     */
    OutputDto getPagedResultByEntity(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception ;


    /**
     * 根据项目ID查询线下招标信息详情
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewProjDtl(Long projId) throws Exception;

    /**
     * 分页查询招标结果
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     */
    OutputDto getPagedBidResultByEntity(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;

    /**
     * 根据项目ID查询线下招标结果详情
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewProjRstDtl(Long projId) throws Exception;
}
