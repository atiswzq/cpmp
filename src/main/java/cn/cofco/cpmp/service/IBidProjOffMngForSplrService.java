package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.IoBidProjOffAppBidDto;
import cn.cofco.cpmp.dto.IoQueryBidProjOffProjInfForSplrDto;
import cn.cofco.cpmp.dto.IoQueryBidProjOffSplrInfDto;
import cn.cofco.cpmp.dto.OutputDto;
/**
 * Created by Tao on 2017/5/31.
 */
/**
 *
 * for [线下招标项目管理 - 供应商] in cpmp
 */
public interface IBidProjOffMngForSplrService {

    /**
     * 线下招标项目供应商申请投标
     *
     * @param dto
     * @return
     */
    OutputDto appBid(IoBidProjOffAppBidDto dto) throws Exception;


    /**
     * 根据条件分页查询已投标的线下招标项目
     * @param dto
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     * @throws Exception
     */
    OutputDto getBidInfByIoQueryBidProjOffSplrInfDto(IoQueryBidProjOffSplrInfDto dto, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;


    /**
     * 根据条件分页查询线下招标项目列表
     * @param dto
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     * @throws Exception
     */
    OutputDto getPagedResultByIoQueryBidProjOffProjInfForSplrDto(IoQueryBidProjOffProjInfForSplrDto dto, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;

    /**
     * 根据项目ID查询线下招标信息详情
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewProjDtl(Long projId) throws Exception;

}