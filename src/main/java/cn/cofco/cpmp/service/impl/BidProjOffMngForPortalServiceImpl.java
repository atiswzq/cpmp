package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.BidProjOffInfViewIoDto;
import cn.cofco.cpmp.dto.IoBidProjOffAppAwdDtlDto;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.PagedResult;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.factory.MatFactory;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOffMngForPortalService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Tao on 2017/6/19.
 */
@Service
@Transactional("transactionManager")
public class BidProjOffMngForPortalServiceImpl implements IBidProjOffMngForPortalService{
    private static Logger logger = LoggerManager.getBidOfflineMngLog();

    @Resource
    private BidProjOffMapper bidProjOffMapper;

    @Resource
    private BidProjOffSplrRltMapper bidProjOffSplrRltMapper;

    @Resource
    private BidProjOffSplrRstMapper bidProjOffSplrRstMapper;

    @Resource
    private AtchMapper atchMapper;

    @Resource
    private MatFactory matFactory;

    public OutputDto getPagedResultByEntity(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {

        logger.info("获取线下招标项目分页信息");

        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer to = pageSize * pageNo;

        Integer start = (pageNo - 1) * pageSize+1;

        BidProjOff bidProjOff = new BidProjOff();
        bidProjOff.setBidNtcPubFlg(Constants.BID_NTC_PUB_FLG_ON);

        Map map = PageUtils.getQueryCondsMap(bidProjOff, start, to);
        map.put("IsMCompany",IsMCompany);
        List<BidProjOff> list = bidProjOffMapper.selectByEntity(map);

        Integer count = bidProjOffMapper.countByEntity(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewProjDtl(Long projId) throws Exception {
        logger.info("根据项目ID查询线下招标信息详情");

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

        BidProjOffWithBLOBs entity = bidProjOffMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg()) || Constants.BID_NTC_PUB_FLG_OFF.equals(entity.getBidNtcPubFlg())) {
            String errMsg = "根据项目ID查询线下招标信息详情 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        List<BidProjOffSplrRlt> bidProjOffSplrRlts = null;
        if (Constants.BID_RNG_TYP_VECTORING.equals(entity.getBidRngTyp())) {
            BidProjOffSplrRlt bidProjOffSplrRlt = new BidProjOffSplrRlt();
            bidProjOffSplrRlt.setProjId(projId);
            Map splrRltMap = PageUtils.getQueryCondsMap(bidProjOffSplrRlt, 0, 0);
            bidProjOffSplrRlts = bidProjOffSplrRltMapper.selectByEntity(splrRltMap);
        }

        ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_GKBUD);
        if (comParm == null) {
            String errMsg = "查看线下招标项目详情 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_GKBUD + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        String atchPrefix = comParm.getParmVal();
        String refId = atchPrefix + projId;
        Atch atch = new Atch();
        atch.setRefId(refId);

        Map map = PageUtils.getQueryCondsMap(atch, 0, 0);

        List<Atch> atches = atchMapper.selectByMap(map);

        BidProjOffInfViewIoDto bidProjOffInfViewIoDto = new BidProjOffInfViewIoDto();
        bidProjOffInfViewIoDto.setBidProjOff(entity);
        bidProjOffInfViewIoDto.setSplrs(bidProjOffSplrRlts);
        bidProjOffInfViewIoDto.setMatTypDesc(matFactory.getMatTypDesc(entity.getMatTyp()));
        bidProjOffInfViewIoDto.setAtches(atches);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, bidProjOffInfViewIoDto);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getPagedBidResultByEntity(Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {

        logger.info("分页获取线下招标项目招标结果信息");

        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer to = pageSize * pageNo;

        Integer start = (pageNo - 1) * pageSize+1;

        BidProjOff bidProjOff = new BidProjOff();
        bidProjOff.setBidRstPubFlg(Constants.BID_RST_PUB_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOff, start, to);
        map.put("IsMCompany",IsMCompany);
        List<BidProjOff> list = bidProjOffMapper.selectByEntity(map);

        Integer count = bidProjOffMapper.countByEntity(map);

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
        BidProjOffWithBLOBs bidProjOff = bidProjOffMapper.selectByPrimaryKey(projId);
        if (bidProjOff == null || !Constants.EFF_FLG_ON.equals(bidProjOff.getEffFlg())) {
            String errMsg = "查看招标结果 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【-20:已废标】
        String projSts = bidProjOff.getProjSts();
        if (Constants.BID_PROJ_OFF_STS_RPL.equals(projSts)) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, bidProjOff);
        }

        // 判断项目状态是否为【50:决标审批通过】
        if (!Constants.BID_PROJ_ON_STS_AWD_ACCEPTED.equals(projSts)) {
            String errMsg = "查看招标结果 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        logger.info("3. 开始查询数据 - 开始");
        BidProjOffSplrRst bidProjOffSplrRst = new BidProjOffSplrRst();
        bidProjOffSplrRst.setEffFlg(Constants.EFF_FLG_ON);
        bidProjOffSplrRst.setProjId(projId);
        Map map = PageUtils.getQueryCondsMap(bidProjOffSplrRst, 0, 0);
        List<BidProjOffSplrRst> bidProjOffSplrRsts = bidProjOffSplrRstMapper.selectByMap(map);
        // 判断是否需要展示价格
        if (bidProjOffSplrRsts != null && !bidProjOffSplrRsts.isEmpty()) {
            if (!Constants.PUB_PRI_FLG_ON.equals(bidProjOff.getPubPriFlg())) {
                for (BidProjOffSplrRst splrRst : bidProjOffSplrRsts) {
                    splrRst.setAwdAmt(null);
                }
            }
        }
        logger.info("3. 结束查询数据 - 开始");

        IoBidProjOffAppAwdDtlDto dto = new IoBidProjOffAppAwdDtlDto();
        dto.setBidProjOff(bidProjOff);
        dto.setBidProjOffSplrRstLists(bidProjOffSplrRsts);
        dto.setMatTypDesc(matFactory.getMatTypDesc(bidProjOff.getMatTyp()));

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, dto);
    }

}


