package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.BidProjOnExptAppResDto;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.exptgrd.IoGrdInfLowPostDto;
import cn.cofco.cpmp.entity.BidProjOnExptGrdDtl;
import cn.cofco.cpmp.entity.BidProjOnExptGrdInf;

import java.util.List;

/**
 * Created by Xujy on 2017/5/30.
 * for [线上招标项目管理服务接口 - 专家] in cpmp
 */
public interface IBidProjOnMngForExptService {


    /**
     * 根据条件分页查询评标项目
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @param IsMCompany
     * @return
     */
    OutputDto queryGrdInfs(BidProjOnExptGrdInf entity, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception;


    /**
     * 根据项目ID查询线上招标信息详情
     *
     * @param projId
     * @return
     * @throws Exception
     */
    OutputDto viewProjDtl(Long projId) throws Exception;

    /**
     * 查看线上招标项目供应商投标信息详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    OutputDto viewBidDtl(Long id) throws Exception;

    /**
     * 开始评标
     *
     * @param entity
     * @return
     * @throws Exception
     */
    OutputDto bgnGrd(BidProjOnExptGrdInf entity) throws Exception;

    /**
     * 根据评标ID查看评分详情
     *
     * @param gradId
     * @param pageNo
     * @param pageSize
     * @return
     */
    OutputDto getScrDtls(Long gradId, Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 打分
     *
     * @param entity
     * @return
     * @throws Exception
     */
    OutputDto score(BidProjOnExptGrdDtl entity) throws Exception;

    /**
     * 发送评标密钥
     *
     * @param gradId
     * @return
     * @throws Exception
     */
    OutputDto sndGrdKey(Long gradId) throws Exception;

    /**
     * 提交打分结果
     *
     * @param gradId
     * @return
     * @throws Exception
     */
    OutputDto subScrRst(Long gradId) throws Exception;

    /**
     * 获取专家信息
     *
     * @return
     * @throws Exception
     */
    OutputDto showExptInf() throws Exception;
/*

    */
/**
     * 专家查看定标结果信息
     * @param  grdId
     * @return
     * @throws Exception
     *//*

    OutputDto showAwdInf(Long grdId) throws Exception;
*/

   /* *//**
     * 专家开始审批
     *
     * @param exptAppResDto
     * @return
     * @throws Exception
     *//*
    OutputDto subAppRes(BidProjOnExptAppResDto exptAppResDto) throws Exception;*/

    /**
     * 展示评标信息
     *
     * @param grdId
     * @return
     * @throws Exception
     */
    OutputDto viewGrdInf(Long grdId) throws Exception;

    /**
     * 提交最低价评标信息
     *
     * @param dto
     * @return
     * @throws Exception
     */
    OutputDto postGrdInf(IoGrdInfLowPostDto dto) throws Exception;
}
