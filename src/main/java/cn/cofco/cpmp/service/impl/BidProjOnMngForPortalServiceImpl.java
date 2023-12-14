package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.AtchMapper;
import cn.cofco.cpmp.dao.BidProjOnMapper;
import cn.cofco.cpmp.dao.BidProjOnMatDtlMapper;
import cn.cofco.cpmp.dao.BidProjOnWinDtlMapper;
import cn.cofco.cpmp.dto.BidProjOnAwdMatDtlDto;
import cn.cofco.cpmp.dto.BidProjOnInfViewIoDto;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.PagedResult;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.factory.MatFactory;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOnMngForPortalService;
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
 * Created by Xujy on 2017/5/17.
 * for [线上招标项目管理 - 门户网站 实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class BidProjOnMngForPortalServiceImpl implements IBidProjOnMngForPortalService {

    private static Logger logger = LoggerManager.getBidOnlineMngLog();


    @Resource
    private BidProjOnMapper bidProjOnMapper;

    @Resource
    private BidProjOnMatDtlMapper bidProjOnMatDtlMapper;

    @Resource
    private BidProjOnWinDtlMapper bidProjOnWinDtlMapper;

    @Resource
    private AtchMapper atchMapper;

    @Resource
    private MatFactory matFactory;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto queryProjs(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {
        logger.info("分页查询线上招标项目列表");

             if (pageNo == null || pageNo <= 0) {
                pageNo = 1;
             }

             if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
                 pageSize = Constants.PAGE_SIZE;
             }

             Integer start = (pageNo - 1) * pageSize + 1;
             Integer to = pageNo * pageSize;

             BidProjOn bidProjOn = new BidProjOn();
             bidProjOn.setBidRngTyp(Constants.BID_RNG_TYP_UNVERCTORING);
             bidProjOn.setBidNtcPubFlg(Constants.BID_NTC_PUB_FLG_ON);
             Map map = PageUtils.getQueryCondsMap(bidProjOn, start, to);
             map.put("desc", 1);
             map.put("IsMCompany",IsMCompany);
             List<BidProjOn> list = bidProjOnMapper.selectByMap(map);
             Integer count = bidProjOnMapper.countByMap(map);
             PagedResult result = new PagedResult(list, count);
             return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewProjDtl(Long projId) throws Exception {
        logger.info("根据项目ID查询线上招标信息详情");

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

        BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg()) || Constants.BID_NTC_PUB_FLG_OFF.equals(entity.getBidNtcPubFlg())) {
            String errMsg = "根据项目ID查询线上招标信息详情 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(matDtlMap);

        ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_JJBUD);
        if (comParm == null) {
            String errMsg = "根据项目ID查询线上招标信息详情 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_JJBUD + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        String atchPrefix = comParm.getParmVal();
        String refId = atchPrefix + projId;
        Atch atch = new Atch();
        atch.setRefId(refId);

        Map map = PageUtils.getQueryCondsMap(atch, 0, 0);

        List<Atch> atches = atchMapper.selectByMap(map);

        BidProjOnInfViewIoDto bidProjOnInfViewIoDto = new BidProjOnInfViewIoDto();
        bidProjOnInfViewIoDto.setBidProjOn(entity);
        bidProjOnInfViewIoDto.setMatDtls(matDtls);
        bidProjOnInfViewIoDto.setAtches(atches);
        bidProjOnInfViewIoDto.setMatTypDesc(matFactory.getMatTypDesc(entity.getMatTyp()));

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, bidProjOnInfViewIoDto);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto queryProjRsts(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {
        logger.info("分页查询线上招标结果列表");

            if (pageNo == null || pageNo <= 0) {
                pageNo = 1;
            }

            if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
                pageSize = Constants.PAGE_SIZE;
            }

            Integer start = (pageNo - 1) * pageSize + 1;
            Integer to = pageNo * pageSize;

            BidProjOn bidProjOn = new BidProjOn();
            bidProjOn.setBidRngTyp(Constants.BID_RNG_TYP_UNVERCTORING);
            bidProjOn.setBidRstPubFlg(Constants.BID_RST_PUB_FLG_ON);
            Map map = PageUtils.getQueryCondsMap(bidProjOn, start, to);
            map.put("desc", 1);
            map.put("IsMCompany",IsMCompany);
            List<BidProjOn> list = bidProjOnMapper.selectByMap(map);
            Integer count = bidProjOnMapper.countByMap(map);
            PagedResult result = new PagedResult(list, count);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewProjRstDtl(Long projId) throws Exception {
        logger.info("查看招标结果");

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (projId == null || projId == 0L) {
            String errMsg = "查看招标结果 - 参数校验 - 失败 - 项目ID不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || !Constants.EFF_FLG_ON.equals(bidProjOn.getEffFlg())) {
            String errMsg = "查看招标结果 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【-20:已废标】
        String projSts = bidProjOn.getProjSts();
        if (Constants.BID_PROJ_ON_STS_RPL.equals(projSts)) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, bidProjOn);
        }

        // 判断项目状态是否为 【40:决标审批中】或【50:决标审批通过】
        if (!Constants.BID_PROJ_ON_STS_AWD_ACCEPTED.equals(projSts)) {
            String errMsg = "查看招标结果 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");
        BidProjOnMatDtl bidProjOnMatDtl = new BidProjOnMatDtl();
        bidProjOnMatDtl.setProjId(bidProjOn.getProjId());
        bidProjOnMatDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matMap = PageUtils.getQueryCondsMap(bidProjOnMatDtl,0,0);
        List<BidProjOnMatDtl> matDtlList = bidProjOnMatDtlMapper.selectByMap(matMap);
        Map<String,BidProjOnMatDtl> matDtlMap = new HashMap<>();
        for(BidProjOnMatDtl matDtl:matDtlList){
            matDtlMap.put(String.valueOf(matDtl.getId()),matDtl);
        }
        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 查询数据库 - 开始");
        logger.info("3.1 查询该项目下所有物料明细");
        BidProjOnWinDtl bidProjOnWinDtl = new BidProjOnWinDtl();
        bidProjOnWinDtl.setProjId(projId);
        bidProjOnWinDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOnWinDtl, 0, 0);
        List<BidProjOnWinDtl> list = bidProjOnWinDtlMapper.selectByMap(map);
        List<BidProjOnAwdMatDtlDto> bidProjOnAwdMatDtlDtos = new ArrayList<>();

        if (list == null || list.isEmpty()) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        } else {
            for(BidProjOnWinDtl winDtl :list){
                BidProjOnMatDtl matInf = matDtlMap.get(String.valueOf(winDtl.getMatId()));
                BidProjOnAwdMatDtlDto bidProjOnAwdMatDtlDto = new BidProjOnAwdMatDtlDto();
                bidProjOnAwdMatDtlDto.setMatNam(matInf.getMatNam());
                bidProjOnAwdMatDtlDto.setMatId(matInf.getId());
                bidProjOnAwdMatDtlDto.setMatUnt(matInf.getMatUnt());
                bidProjOnAwdMatDtlDto.setMatCod(matInf.getMatCod());
                bidProjOnAwdMatDtlDto.setMatBnd(winDtl.getMatBnd());
                bidProjOnAwdMatDtlDto.setCurrTyp(winDtl.getCurrTyp());
                bidProjOnAwdMatDtlDto.setDlvAdr(matInf.getDlvAdr());
                bidProjOnAwdMatDtlDto.setExRat(winDtl.getExRat());
                bidProjOnAwdMatDtlDto.setPchsNum(winDtl.getPchsNum());
                bidProjOnAwdMatDtlDto.setSplrNam(winDtl.getSplrNam());
                bidProjOnAwdMatDtlDto.setTendDlvDte(DateUtils.date2SimpleString(winDtl.getTendDlvDte()));
                bidProjOnAwdMatDtlDto.setProjId(winDtl.getProjId());
                bidProjOnAwdMatDtlDto.setDlvDte(DateUtils.date2SimpleString(matInf.getDlvDte()));
                bidProjOnAwdMatDtlDtos.add(bidProjOnAwdMatDtlDto);
                // 判断是否需要展示价格
                if (!Constants.PUB_PRI_FLG_ON.equals(bidProjOn.getPubPriFlg())) {
                    bidProjOnAwdMatDtlDto.setUntPri(null);
                    bidProjOnAwdMatDtlDto.setTtlPri(null);

                }else{
                    bidProjOnAwdMatDtlDto.setTtlPri(winDtl.getTtlPri());
                    bidProjOnAwdMatDtlDto.setUntPri(winDtl.getUntPri());
                }
            }
            long durationC = DateUtils.currentTimeMillis() - timeC;
            logger.info("3. 查询数据库 - 结束，duration[" + durationC + "ms]");

            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, bidProjOnAwdMatDtlDtos);
        }
    }
}
