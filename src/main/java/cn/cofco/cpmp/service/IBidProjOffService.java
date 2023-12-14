package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Tao on 2017/5/7.
 * for [线下项目服务接口] in cpmp
 */
public interface IBidProjOffService {
    /**
     * 获取分页信息
     * @param dto
     * @param pageNo
     * @param pageSize
     * @return
     */
    OutputDto getPagedResultByEntity(IoQueryBidProjOffForPchsDto dto, Integer pageNo, Integer pageSize) throws Exception ;

    /**
     * 新增线下项目/更新线下项目/提交BPM审批
     * @param request
     * @param dto
     * @return
     */
    OutputDto saveOrSub(HttpServletRequest request, IoBidProjOffDto dto) throws Exception;
    /**
     * 删除线下项目
     * @param projId
     * @return
     */
    OutputDto del(Long projId) throws Exception;
    /**
     * 查询线下项目详情
     * @param projId
     * @return
     */
    OutputDto view(Long projId) throws Exception;
    /**
     * 发布线下项目
     * @param projId
     * @return
     */
    OutputDto publish(Long projId) throws Exception;
    /**
     * 线下项目截标
     * @param dto
     * @return
     */
    OutputDto cut(IoBidProjOffCutDto dto) throws Exception;
    /**
     * 线下项目废标
     * @param dto
     * @return
     */
    OutputDto repeal(IoBidProjOffRepealDto dto) throws Exception;
    /**
     * 根据条件分页查询线下招标项目投标信息
     * @param  dto
     * @param  pageNo
     * @param  pageSize
     * @return
     */
    OutputDto getBidInfByIoQueryBidProjOffSplrInfDto(IoQueryBidProjOffSplrInfDto dto, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 线下招标项目供应商投标审核
     * @param dto
     * @return
     */
    OutputDto adtBidInf(IoBidProjOffBidAdtDto dto) throws Exception;


    /**
     * 申请决标
     *
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto appAwd(HttpServletRequest request, IoBidProjOffAppAwdListDto dto) throws Exception;

    /**
     * 查看招标结果
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewAwdInf(Long projId) throws Exception;

    /**
     *
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto pubRst(Long projId) throws Exception;

    /**
     * 获取投标供应商
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto getTendSplrs(IoBidOffGetTendSplrsDto dto) throws Exception;

    /**
     * 分页查询招标结果
     * @param dto
     * @param pageNo
     * @param pageSize
     * @return
     */
    OutputDto getPagedBidResultByEntity(IoQueryBidProjOffForPchsDto dto, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 根据项目ID查询线下招标结果详情
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewProjRstDtl(Long projId) throws Exception;





}
