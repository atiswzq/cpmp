package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.AtchMapper;
import cn.cofco.cpmp.dao.XjProjMapper;
import cn.cofco.cpmp.dao.XjProjMatDtlMapper;
import cn.cofco.cpmp.dao.XjProjWinDtlMapper;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.factory.MatFactory;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IXjProjMngForPortalService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.DateUtils;
import cn.cofco.cpmp.utils.PageUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wzq on 2018/01/13.
 * for [询价项目管理 - 门户网站 实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class XjProjMngForPortalServiceImpl implements IXjProjMngForPortalService{

    private static Logger logger = LoggerManager.getBidOnlineMngLog();
    @Resource
    private XjProjMapper xjProjMapper;
    @Resource
    private XjProjMatDtlMapper xjProjMatDtlMapper;
    @Resource
    private AtchMapper atchMapper;
    @Resource
    private MatFactory matFactory;
    @Resource
    private XjProjWinDtlMapper xjProjWinDtlMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto queryXjProjs(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {
        logger.info("分页查询询价项目列表");

        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize + 1;
        Integer to = pageNo * pageSize;

        XjProj xjProj = new XjProj();
        xjProj.setBidRngTyp(Constants.BID_RNG_TYP_UNVERCTORING);
        xjProj.setBidNtcPubFlg(Constants.BID_NTC_PUB_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(xjProj, start, to);
        map.put("desc", 1);
        map.put("IsMCompany",IsMCompany);
        List<XjProj> list = xjProjMapper.selectByMap(map);
        Integer count = xjProjMapper.countByMap(map);
        PagedResult result = new PagedResult(list, count);
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewXjProjDtl(Long projId) throws Exception {
        logger.info("根据项目ID查询询价项目信息详情");

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (projId == null) {
            String errMsg = "参数校验 - 不通过 - projId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        XjProj entity = xjProjMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg()) || Constants.BID_NTC_PUB_FLG_OFF.equals(entity.getBidNtcPubFlg())) {
            String errMsg = "根据项目ID查询线上招标信息详情 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        XjProjMatDtl matDtl = new XjProjMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<XjProjMatDtl> matDtls = xjProjMatDtlMapper.selectByMap(matDtlMap);

        ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_XJBUD);
        if (comParm == null) {
            String errMsg = "根据项目ID查询线上招标信息详情 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_XJBUD + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        String atchPrefix = comParm.getParmVal();
        String refId = atchPrefix + projId;
        Atch atch = new Atch();
        atch.setRefId(refId);

        Map map = PageUtils.getQueryCondsMap(atch, 0, 0);

        List<Atch> atches = atchMapper.selectByMap(map);

        XjProjInfViewIoDto xjProjInfViewIoDto = new XjProjInfViewIoDto();
        xjProjInfViewIoDto.setXjProj(entity);
        xjProjInfViewIoDto.setMatDtls(matDtls);
        xjProjInfViewIoDto.setAtches(atches);
        xjProjInfViewIoDto.setMatTypDesc(matFactory.getMatTypDesc(entity.getMatTyp()));

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, xjProjInfViewIoDto);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto queryXjProjRsts(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {
        logger.info("分页查询询价项目招标结果列表");

        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize + 1;
        Integer to = pageNo * pageSize;

        XjProj xjProj = new XjProj();
        xjProj.setBidRngTyp(Constants.BID_RNG_TYP_UNVERCTORING);
        xjProj.setBidRstPubFlg(Constants.BID_RST_PUB_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(xjProj, start, to);
        map.put("desc", 1);
        map.put("IsMCompany",IsMCompany);
        List<XjProj> list = xjProjMapper.selectByMap(map);
        Integer count = xjProjMapper.countByMap(map);
        PagedResult result = new PagedResult(list, count);
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewXjProjRstDtl(Long projId) throws Exception {
        logger.info("查看询价项目招标结果");

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (projId == null || projId == 0L) {
            String errMsg = "查看询价项目招标结果 - 参数校验 - 失败 - 项目ID不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        XjProj xjProj = xjProjMapper.selectByPrimaryKey(projId);
        if (xjProj == null || !Constants.EFF_FLG_ON.equals(xjProj.getEffFlg())) {
            String errMsg = "查看询价项目招标结果 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【-20:已废标】
        String projSts = xjProj.getProjSts();
        if (Constants.BID_PROJ_ON_STS_RPL.equals(projSts)) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, xjProj);
        }

        // 判断项目状态是否为【50:决标审批通过】
        if (!Constants.BID_PROJ_ON_STS_AWD_ACCEPTED.equals(projSts)) {
            String errMsg = "查看招标结果 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");
        XjProjMatDtl xjProjMatDtl = new XjProjMatDtl();
        xjProjMatDtl.setProjId(xjProj.getProjId());
        xjProjMatDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matMap = PageUtils.getQueryCondsMap(xjProjMatDtl,0,0);
        List<XjProjMatDtl> matDtlList = xjProjMatDtlMapper.selectByMap(matMap);
        Map<String,XjProjMatDtl> matDtlMap = new HashMap<>();
        for(XjProjMatDtl matDtl:matDtlList){
            matDtlMap.put(String.valueOf(matDtl.getId()),matDtl);
        }
        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 查询数据库 - 开始");
        logger.info("3.1 查询该项目下所有物料明细");
        XjProjWinDtl xjProjWinDtl = new XjProjWinDtl();
        xjProjWinDtl.setProjId(projId);
        xjProjWinDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(xjProjWinDtl, 0, 0);
        List<XjProjWinDtl> list = xjProjWinDtlMapper.selectByMap(map);
        List<XjProjAwdMatDtlDto> xjProjAwdMatDtlDtos = new ArrayList<>();

        if (list == null || list.isEmpty()) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        } else {
            for(XjProjWinDtl winDtl :list){
                XjProjMatDtl matInf = matDtlMap.get(String.valueOf(winDtl.getMatId()));
                XjProjAwdMatDtlDto xjProjAwdMatDtlDto = new XjProjAwdMatDtlDto();
                xjProjAwdMatDtlDto.setMatNam(matInf.getMatNam());
                xjProjAwdMatDtlDto.setMatId(matInf.getId());
                xjProjAwdMatDtlDto.setMatUnt(matInf.getMatUnt());
                xjProjAwdMatDtlDto.setMatCod(matInf.getMatCod());
                xjProjAwdMatDtlDto.setMatBnd(winDtl.getMatBnd());
                xjProjAwdMatDtlDto.setCurrTyp(winDtl.getCurrTyp());
                xjProjAwdMatDtlDto.setDlvAdr(matInf.getDlvAdr());
                xjProjAwdMatDtlDto.setExRat(winDtl.getExRat());
                xjProjAwdMatDtlDto.setPchsNum(winDtl.getPchsNum());
                xjProjAwdMatDtlDto.setSplrNam(winDtl.getSplrNam());
                xjProjAwdMatDtlDto.setTendDlvDte(DateUtils.date2SimpleString(winDtl.getTendDlvDte()));
                xjProjAwdMatDtlDto.setProjId(winDtl.getProjId());
                xjProjAwdMatDtlDto.setDlvDte(DateUtils.date2SimpleString(matInf.getDlvDte()));
                // 判断是否需要展示价格
                if (!Constants.PUB_PRI_FLG_ON.equals(xjProj.getPubPriFlg())) {
                    xjProjAwdMatDtlDto.setUntPri(null);
                    xjProjAwdMatDtlDto.setTtlPri(null);

                }else{
                    xjProjAwdMatDtlDto.setTtlPri(winDtl.getTtlPri());
                    xjProjAwdMatDtlDto.setUntPri(winDtl.getUntPri());
                }
                xjProjAwdMatDtlDtos.add(xjProjAwdMatDtlDto);
            }
            long durationC = DateUtils.currentTimeMillis() - timeC;
            logger.info("3. 查询数据库 - 结束，duration[" + durationC + "ms]");

            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, xjProjAwdMatDtlDtos);
        }
    }
}
