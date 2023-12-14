package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Wzq on 2018/1/12.
 * for [询价项目管理服务接口 - 工厂采购员] in cpmp
 */
public interface IXjProjMngForPchsService {
    /**
     * 分页查询询价项目信息
     *
     * @param dto      IoQueryXjProjForPchsDto
     * @param pageNo   页码
     * @param pageSize 每页显示行数
     * @return
     */
    OutputDto getPagedResultByIoQueryXjProjForPchsDto(IoQueryXjProjForPchsDto dto, Integer pageNo, Integer pageSize);

    /**
     * 询价项目新增、编辑、保存
     *
     * @param request
     * @param dto
     * @return
     */
    OutputDto save(HttpServletRequest request, IoXjProjDto dto) throws Exception;

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
     * 询价项目截标
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto cut(IoXjProjCutDto dto) throws Exception;

    /**
     * 询价项目申请废标
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto repeal(IoXjProjRepealDto dto) throws Exception;

    /**
     * 根据条件分页查询供应商投标信息
     *
     * @param dto
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    OutputDto getBidInfByIoQueryXjProjSplrTendInfDto(IoQueryXjProjSplrTendInfDto dto, Integer pageNo, Integer pageSize) throws Exception;


    /**
     * 查看询价项目供应商投标信息详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    OutputDto viewBidDtl(Long id) throws Exception;

    /**
     * 询价项目供应商投标审核：通过/拒绝
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto adtBidInf(IoXjProjBidAdtDto dto) throws Exception;

    /**
     * 询价项目开标
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto openProj(IoXjProjOpenDto dto) throws Exception;

    /**
     * 发送开标密钥
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto sndOpenKey(Long projId) throws Exception;

    /**
     * 根据投标ID查看报价信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    OutputDto getQotInf(Long id) throws Exception;

    /**
     * 获取投标供应商
     *
     * @param dto
     * @return
     */
    OutputDto getTendSplrs(IoGetTendSplrsDto dto) throws Exception;

    /**
     * 获取开标记录表
     *
     * @param projId 项目ID
     * @return
     * @throws Exception
     */
    OutputDto getOpenRcds(Long projId) throws Exception;

    /**
     * 根据项目ID获取申请决标相关信息
     *
     * @param projId 项目ID
     * @return
     * @throws Exception
     */
    OutputDto getAppAwdInf(Long projId) throws Exception;

    /**
     * 申请决标
     * @param request
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto appAwd(HttpServletRequest request, IoXjAppAwdDto dto) throws Exception;

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
     * 获取需求中的物料信息
     *
     * @param orgIdsStr 公司IDs
     * @return
     * @throws Exception
     */
    OutputDto getReqMatInfs(String orgIdsStr, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 导出开标记录表
     *
     * @param projId
     * @return
     * @throws Exception
     */
    ResponseEntity<byte[]> exportOpenInf(HttpServletRequest request, Long projId) throws Exception;

}
