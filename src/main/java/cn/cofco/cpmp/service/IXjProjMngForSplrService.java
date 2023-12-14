package cn.cofco.cpmp.service;


import cn.cofco.cpmp.dto.*;

/**
 * Created by Wzq on 2018/01/13.
 * for [询价项目管理 - 供应商] in cpmp
 */
public interface IXjProjMngForSplrService {

    /**
     * 询价项目供应商申请投标
     * @param dto
     * @return
     */
    OutputDto appBid(IoXjProjAppBidDto dto) throws Exception;

    /**
     * 根据条件分页查询已投标的询价项目
     * @param dto
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     * @throws Exception
     */
    OutputDto getBidInfByIoQueryXjProjSplrTendInfDto(IoQueryXjProjSplrTendInfDto dto, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;

    /**
     * 根据条件分页查询询价项目列表
     * @param dto
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     * @throws Exception
     */
    OutputDto getPagedResultByIoQueryXjProjInfForSplrDto(IoQueryXjProjInfForSplrDto dto, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;

    /**
     * 根据项目ID查询询价项目信息详情
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewXjProjDtl(Long projId) throws Exception;

    /**
     * 询价项目供应商报价
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto qot(IoXjProjQotDto dto) throws Exception;

    /**
     * 查询实时竞价物料报价价格排名以及最低报价
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto getQotOrder(Long projId) throws Exception;

    /**
     * 获取报价详情
     * @param id
     * @return
     * @throws Exception
     */
    OutputDto getQotInf(Long id) throws Exception;

    /**
     * 供应商查看询价项目招标结果
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewXjProjRstDtl(Long projId) throws Exception;
}
