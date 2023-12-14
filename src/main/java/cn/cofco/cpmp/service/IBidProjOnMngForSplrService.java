package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.*;

/**
 * Created by Xujy on 2017/5/17.
 * for [线上招标项目管理 - 供应商] in cpmp
 */
public interface IBidProjOnMngForSplrService {

    /**
     * 线上招标项目供应商申请投标
     * @param dto
     * @return
     */
    OutputDto appBid(IoBidProjOnAppBidDto dto) throws Exception;


    /**
     * 根据条件分页查询线上招标项目信息
     * @param dto
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     * @throws Exception
     */
    OutputDto getBidInfByIoQueryBidProjOnSplrTendInfDto(IoQueryBidProjOnSplrTendInfDto dto, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;


    /**
     * 根据条件分页查询线上招标项目列表
     * @param dto
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     * @throws Exception
     */
    OutputDto getPagedResultByIoQueryBidProjOnProjInfForSplrDto(IoQueryBidProjOnProjInfForSplrDto dto, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;

    /**
     * 根据项目ID查询线上招标信息详情
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewProjDtl(Long projId) throws Exception;


    /**
     * 线上招标项目供应商报价
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto qot(IoBidProjOnQotDto dto) throws Exception;

    /**
     * 获取报价详情
     * @param id
     * @return
     * @throws Exception
     */
    OutputDto getQotInf(Long id) throws Exception;

    /**
     * 获取线上招标项目供应商报价历史
     * @param dto
     * @return
     */
    OutputDto getQotHis(IoGetQotHisDto dto, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 查询实时竞价物料报价价格排名以及最低报价
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto getQotOrder(Long projId) throws Exception;

    /**
     * 供应商查看网上竞价项目招标结果
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewProjRstDtl(Long projId) throws Exception;
}
