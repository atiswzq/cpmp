package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Xujy on 2017/5/1.
 * for [线上招标项目管理服务接口 - 工厂采购员] in cpmp
 */
public interface IBidProjOnMngForPchsService {

    /**
     * 分页查询线上招标项目信息
     *
     * @param dto      IoQueryBidProjOnForPchsDto
     * @param pageNo   页码
     * @param pageSize 每页显示行数
     * @return
     */
    OutputDto getPagedResultByIoQueryBidProjOnForPchsDto(IoQueryBidProjOnForPchsDto dto, Integer pageNo, Integer pageSize);


    /**
     * 线上招标项目新增、编辑、提交BPM审批
     *
     * @param request
     * @param dto
     * @return
     */
    OutputDto saveOrSub(HttpServletRequest request, IoBidProjOnDto dto) throws Exception;


    /**
     * 删除线上招标项目
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto del(Long projId) throws Exception;


    /**
     * 查看线上招标项目详情
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto view(Long projId) throws Exception;

    /**
     * 发布或取消发布线上招标项目
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto publish(Long projId) throws Exception;


    /**
     * 线上招标项目截标
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto cut(IoBidProjOnCutDto dto) throws Exception;


    /**
     * 线上招标项目申请废标
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto repeal(IoBidProjOnRepealDto dto) throws Exception;

    /**
     * 查看线上招标项目供应商投标信息详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    OutputDto viewBidDtl(Long id) throws Exception;

    /**
     * 线上招标项目供应商投标审核：通过/拒绝
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto adtBidInf(IoBidProjOnBidAdtDto dto) throws Exception;

    /**
     * 根据条件分页查询供应商投标信息
     *
     * @param dto
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    OutputDto getBidInfByIoQueryBidProjOnSplrTendInfDto(IoQueryBidProjOnSplrTendInfDto dto, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 线上招标项目开标
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto openProj(IoBidProjOnOpenDto dto) throws Exception;

    /**
     * 获取专家列表
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto getExptInfs(IoGetExptInfForPchsDto dto) throws Exception;

    /**
     * 发送开标密钥
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto sndOpenKey(Long projId) throws Exception;

    /**
     * 线上招标项目选择评标专家
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto chsExpts(IoBidProjOnChsExptsDto dto) throws Exception;

    /**
     * 根据投标ID查看报价信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    OutputDto getQotInf(Long id) throws Exception;

    /**
     * 结束评标
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto endGrd(Long projId) throws Exception;

    /**
     * 获取投标供应商
     *
     * @param dto
     * @return
     */
    OutputDto getTendSplrs(IoGetTendSplrsDto dto) throws Exception;

    /**
     * 发起二次竞价
     *
     * @param dto
     * @return
     */
    OutputDto startQot2(IoStartQot2Dto dto) throws Exception;

    /**
     * 获取評標信息
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto getGrdInf(IoShowGrdInfDto dto, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 根据评标ID查看评标详情
     *
     * @param grdId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    OutputDto getGrdDtls(Long grdId, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 申请决标
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto appAwd(HttpServletRequest request, IoAppAwdDto dto) throws Exception;

    /**
     * 查看招标结果
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewAwdInf(Long projId) throws Exception;

    /**
     * 发布招标结果
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto pubRst(Long projId) throws Exception;

    /**
     * 发布废标结果
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto pubRplRst(IoPubRplRstDto dto) throws Exception;

    /**
     * 根据项目ID获取申请决标相关信息
     *
     * @param projId 项目ID
     * @return
     * @throws Exception
     */
    OutputDto getAppAwdInf(Long projId) throws Exception;

    /**
     * 获取需求中的物料信息
     *
     * @param orgIdsStr 公司IDs
     * @return
     * @throws Exception
     */
    OutputDto getReqMatInfs(String orgIdsStr, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 获取开标记录表
     *
     * @param projId 项目ID
     * @return
     * @throws Exception
     */
    OutputDto getOpenRcds(Long projId) throws Exception;


    /**
     * 导出开标记录表
     *
     * @param projId
     * @return
     * @throws Exception
     */
    ResponseEntity<byte[]> exportOpenInf(HttpServletRequest request, Long projId) throws Exception;


    /**
     * 根据物料编码绑定物料信息
     * @param matName
     * @return
     * @throws Exception
     * */
    OutputDto getReqMatInfsByName(  Integer pageNo, Integer pageSize,String matName) throws Exception;

    /**
     * 根据物料名称搜索需求中的物料信息
     * @param matCod
     * @return
     * @throws Exception
     * */
    OutputDto getMatInfsByMatCod(  String matCod) throws Exception;
  /*  *//**
     * 采购员结束评标前允许专家修改评标结果
     *
     * @param grdId
     * @return
     * @throws Exception
     *//*
    OutputDto updateGrdRes(Long grdId) throws Exception;*/

   /* *//**
     * 采购员定标结果发给专家审批
     *
     * @param appAwdDto
     * @return
     * @throws Exception
     *//*
    OutputDto sendAwdToExpt(IoAppAwdDto appAwdDto) throws Exception;
*/
  /*  *//**
     * 采购员查看专家审批结果
     *
     * @param projId
     * @return
     * @throws Exception
     *//*
    OutputDto getExptAwdRes(Long projId) throws Exception;
*/
  /*  *//**
     * 采购员允许专家修改定标审批结果
     *
     * @param grdId
     * @return
     * @throws Exception
     *//*
    OutputDto updateExptAppRes(Long grdId) throws Exception;
*/
    /**
     * 查看评标汇总
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto collectGrdInf(Long projId) throws Exception;
}
