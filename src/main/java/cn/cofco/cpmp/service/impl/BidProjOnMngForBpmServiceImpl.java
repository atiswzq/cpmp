package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.bpm.entity.PiResponse;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOnMngForBpmService;
import cn.cofco.cpmp.service.IMdmService;
import cn.cofco.cpmp.support.BpmOutputDtoUtil;
import cn.cofco.cpmp.utils.DateUtils;
import cn.cofco.cpmp.utils.PageUtils;
import cn.cofco.cpmp.utils.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Xujy on 2017/5/17.
 * for [线上招标项目管理 - BPM 实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class BidProjOnMngForBpmServiceImpl implements IBidProjOnMngForBpmService {

    private static Logger logger = LoggerManager.getBidOnlineMngLog();

    @Resource
    private BidProjOnMapper bidProjOnMapper;

    @Resource
    private BidProjOnStsLogMapper bidProjOnStsLogMapper;

    @Resource
    private BidProjOnWinDtlMapper bidProjOnWinDtlMapper;

    @Resource
    private BidProjOnSplrTendInfMapper bidProjOnSplrTendInfMapper;

    @Resource
    private IMdmService mdmService;

    @Resource
    private RequisitionMapper requisitionMapper;

    @Resource
    private BidProjOnMatDtlMapper bidProjOnMatDtlMapper;

    @Resource
    private BidProjOnExptGrdInfMapper bidProjOnExptGrdInfMapper;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PiResponse bud(String bpmSeqNo, String sucFlg, String memo) throws Exception {

        logger.info("网上竞价招标项目立项BPM回调");

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(bpmSeqNo)) {
            sb.append("bpmSeqNo不得为空");
        }
        if (StringUtils.isEmpty(sucFlg)) {
            sb.append("sucFlg成功标识不得为空");
        }

        String chkRst = sb.toString();
        if (!"".equals(chkRst)) {
            String errMsg = "网上竞价招标项目立项BPM回调 - 参数校验 - 失败 - " + chkRst;
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOn entity = new BidProjOn();
        entity.setBpmBudSeq(bpmSeqNo);
        Map map = PageUtils.getQueryCondsMap(entity, 1, 1);
        List<BidProjOn> bidProjOnList = bidProjOnMapper.selectByMap(map);
        if (bidProjOnList == null || bidProjOnList.isEmpty() || Constants.EFF_FLG_OFF.equals(bidProjOnList.get(0).getEffFlg())) {
            String errMsg = "网上竞价招标项目立项BPM回调 - 业务校验 - 失败 - 根据BpmBudSeq查询项目信息无数据 - bpmSeqNo[" + bpmSeqNo + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOn bidProjOn = bidProjOnList.get(0);
        if (!Constants.BID_PROJ_ON_STS_APP_ADTING.equals(bidProjOn.getProjSts())) {
            String errMsg = "网上竞价招标项目立项BPM回调 - 业务校验 - 失败 - 项目状态异常 - projSts[" + bidProjOn.getProjSts() + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            logger.info("3.1 网上竞价招标项目立项 - BPM审批通过，更新项目状态至[20:招标中], 同时发布项目招标公告");
            bidProjOn.setNtcPubTim(DateUtils.getCurrentTimeStamp());
            bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_BIDDING);
            bidProjOn.setBidNtcPubFlg(Constants.BID_NTC_PUB_FLG_ON);
        } else {
            logger.info("3.1 网上竞价招标项目立项 - BPM审批否决，更新项目状态至[00:编辑中]");
            bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_EDTING);
        }

        bidProjOn.setBpmBudAdtMemo(memo);
        bidProjOn.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOn.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOn.setModUsr(Constants.UID_BPM);

        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
        if (effRows != 1) {
            String errMsg = "更新线上招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 网上竞价招标项目立项BPM回调 - 写入线上招标项目状态记录表");
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(bidProjOn.getProjId());
        bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
        bidProjOnStsLog.setProjSts(bidProjOn.getProjSts());
        bidProjOnStsLog.setStsUpdTim(now);
        bidProjOnStsLog.setUpdUsr(Constants.UID_BPM);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_SUC, RtnEnum.SUC_OPR);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PiResponse awd(String bpmSeqNo, String sucFlg, String memo) {

        logger.info("网上竞价招标项目定标BPM回调");

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(bpmSeqNo)) {
            sb.append("bpmSeqNo不得为空");
        }
        if (StringUtils.isEmpty(sucFlg)) {
            sb.append("sucFlg成功标识不得为空");
        }

        String chkRst = sb.toString();
        if (!"".equals(chkRst)) {
            String errMsg = "网上竞价招标项目定标BPM回调 - 参数校验 - 失败 - " + chkRst;
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOn entity = new BidProjOn();
        entity.setBpmFinSeq(bpmSeqNo);
        Map map = PageUtils.getQueryCondsMap(entity, 1, 1);
        List<BidProjOn> bidProjOnList = bidProjOnMapper.selectByMap(map);
        if (bidProjOnList == null || bidProjOnList.isEmpty() || Constants.EFF_FLG_OFF.equals(bidProjOnList.get(0).getEffFlg())) {
            String errMsg = "网上竞价招标项目定标BPM回调 - 业务校验 - 失败 - 根据BpmFinSeq查询项目信息无数据 - bpmSeqNo[" + bpmSeqNo + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOn bidProjOn = bidProjOnList.get(0);
        if (!Constants.BID_PROJ_ON_STS_AWDING.equals(bidProjOn.getProjSts())) {
            String errMsg = "网上竞价招标项目定标BPM回调 - 业务校验 - 失败 - 项目状态异常 - projSts[" + bidProjOn.getProjSts() + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");


        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");

        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            logger.info("3.1 网上竞价招标项目定标 - BPM审批通过，更新项目状态至[50:决标审批通過]");
            bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_AWD_ACCEPTED);
        } else {
            logger.info("3.1 网上竞价招标项目定标 - BPM审批否决，按线上招标项目状态记录表回退状态至前一状态");
            BidProjOnStsLog bidProjOnStsLogEntity = new BidProjOnStsLog();
            bidProjOnStsLogEntity.setProjId(bidProjOn.getProjId());
            Map mapOfBidProjOnStsLog = PageUtils.getQueryCondsMap(bidProjOnStsLogEntity, 2, 2);
            List<BidProjOnStsLog> logList = bidProjOnStsLogMapper.selectByMap(mapOfBidProjOnStsLog);
            if (logList == null || logList.isEmpty()) {
                String errMsg = "网上竞价招标项目定标BPM回调 - BPM审批否决 - 失败 - 线上招标项目状态记录表中无前一状态记录";
                logger.error(errMsg);
                return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.INNER_ERR, errMsg);
            }
            bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_GRADED);
        }

        bidProjOn.setBpmAwdAdtMemo(memo);
        bidProjOn.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOn.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOn.setModUsr(Constants.UID_BPM);

        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
        if (effRows != 1) {
            String errMsg = "更新线上招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 网上竞价招标项目定标BPM回调 - 写入线上招标项目状态记录表");
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(bidProjOn.getProjId());
        bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
        bidProjOnStsLog.setProjSts(bidProjOn.getProjSts());
        bidProjOnStsLog.setStsUpdTim(now);
        bidProjOnStsLog.setUpdUsr(Constants.UID_BPM);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.3 网上竞价招标项目定标BPM回调 - 更新供应商投标表");
        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
            bidProjOnSplrTendInf.setProjId(bidProjOn.getProjId());
            bidProjOnSplrTendInf.setBidDocSts(Constants.BID_DOC_STS_ACCEPTED);
            bidProjOnSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
            Map tendInfMap = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf, 1,0);
            List<BidProjOnSplrTendInf> tendInfs = bidProjOnSplrTendInfMapper.selectByMap(tendInfMap);

            List<BidProjOnWinDtl> winDtls = bidProjOnWinDtlMapper.selectAwdSplrsByProjId(bidProjOn.getProjId());

            for (BidProjOnSplrTendInf tendInf : tendInfs) {
                String bidDocSts = Constants.BID_DOC_STS_UNAWD;
                for (BidProjOnWinDtl winDtl : winDtls) {
                    if (tendInf.getSplrId().equals(winDtl.getSplrId())) {
                        bidDocSts = Constants.BID_DOC_STS_AWD;
                        break;
                    }
                }
                tendInf.setBidDocSts(bidDocSts);
                tendInf.setModUsr(Constants.UID_BPM);
                tendInf.setModTim(DateUtils.getCurrentTimeStamp());

                effRows = bidProjOnSplrTendInfMapper.updateByPrimaryKeySelective(tendInf);
                if (effRows != 1) {
                    String errMsg = "更新供应商投标表失败 - 受影响行数不为1";
                    logger.error(errMsg);
                }
            }

            // 拋MDM
            List<Long> splrIds = winDtls.stream().map(s -> s.getSplrId()).collect(Collectors.toList());
            logger.info("线上项目定标通过 - 定标供应商拋送MDM");
            try {
                mdmService.splrAply(splrIds);
            } catch (Exception e) {
                logger.error("线上项目定标通过 - 定标供应商拋送MDM异常 - for " + ExceptionUtils.getFullStackTrace(e));
            }
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_SUC, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PiResponse rpl(String bpmSeqNo, String sucFlg, String memo) {
        logger.info("网上竞价招标项目废标BPM回调");

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(bpmSeqNo)) {
            sb.append("bpmSeqNo不得为空");
        }
        if (StringUtils.isEmpty(sucFlg)) {
            sb.append("sucFlg成功标识不得为空");
        }

        String chkRst = sb.toString();
        if (!"".equals(chkRst)) {
            String errMsg = "网上竞价招标项目废标BPM回调 - 参数校验 - 失败 - " + chkRst;
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOn entity = new BidProjOn();
        entity.setBpmRplSeq(bpmSeqNo);
        Map map = PageUtils.getQueryCondsMap(entity, 1, 1);
        List<BidProjOn> bidProjOnList = bidProjOnMapper.selectByMap(map);
        if (bidProjOnList == null || bidProjOnList.isEmpty() || Constants.EFF_FLG_OFF.equals(bidProjOnList.get(0).getEffFlg())) {
            String errMsg = "网上竞价招标项目废标BPM回调 - 业务校验 - 失败 - 根据BpmRplSeq查询项目信息无数据 - bpmSeqNo[" + bpmSeqNo + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOn bidProjOn = bidProjOnList.get(0);
        if (!Constants.BID_PROJ_ON_STS_RPL_ADTING.equals(bidProjOn.getProjSts())) {
            String errMsg = "网上竞价招标项目废标BPM回调 - 业务校验 - 失败 - 项目状态异常 - projSts[" + bidProjOn.getProjSts() + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");

        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            logger.info("3.1 网上竞价招标项目废标 - BPM审批通过，更新项目状态至[-20:已废标]");
            bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_RPL);

            // 若为采购需求中的物料，则将需求物料置状态为【不在招标中】
            BidProjOnMatDtl matDtlEntity = new BidProjOnMatDtl();
            matDtlEntity.setProjId(bidProjOn.getProjId());
            matDtlEntity.setEffFlg(Constants.EFF_FLG_ON);
            Map matDtlEntityMap = PageUtils.getQueryCondsMap(matDtlEntity, 0, 0);
            List<BidProjOnMatDtl> matDtlList = bidProjOnMatDtlMapper.selectByMap(matDtlEntityMap);

            Iterator<BidProjOnMatDtl> itQry = matDtlList.iterator();
            List<Long> reqIds = new ArrayList<>();
            while (itQry.hasNext()) {
                BidProjOnMatDtl dtlQry = itQry.next();
                Long reqId = dtlQry.getReqId();
                if (reqId != null && !reqId.equals(0L)) {
                    reqIds.add(reqId);
                }
            }

            if (!reqIds.isEmpty()) {
                requisitionMapper.updateToInzb(Constants.INZB_0, reqIds);
            }
        } else {
            logger.info("3.1 网上竞价招标项目废标 - BPM审批否决，按线上招标项目状态记录表回退状态至前一状态");
            BidProjOnStsLog bidProjOnStsLogEntity = new BidProjOnStsLog();
            bidProjOnStsLogEntity.setProjId(bidProjOn.getProjId());
            Map mapOfBidProjOnStsLog = PageUtils.getQueryCondsMap(bidProjOnStsLogEntity, 2, 2);
            List<BidProjOnStsLog> logList = bidProjOnStsLogMapper.selectByMap(mapOfBidProjOnStsLog);
            if (logList == null || logList.isEmpty()) {
                String errMsg = "网上竞价招标项目废标 - BPM审批否决 - 失败 - 线上招标项目状态记录表中无前一状态记录";
                logger.error(errMsg);
                return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.INNER_ERR, errMsg);
            }

            bidProjOn.setProjSts(logList.get(0).getProjSts());
        }

        bidProjOn.setBpmRplAdtMemo(memo);
        bidProjOn.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOn.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOn.setModUsr(Constants.UID_BPM);

        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
        if (effRows != 1) {
            String errMsg = "更新线上招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 网上竞价招标项目废标BPM回调 - 写入线上招标项目状态记录表");
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(bidProjOn.getProjId());
        bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
        bidProjOnStsLog.setProjSts(bidProjOn.getProjSts());
        bidProjOnStsLog.setStsUpdTim(now);
        bidProjOnStsLog.setUpdUsr(Constants.UID_BPM);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_SUC, RtnEnum.SUC_OPR);
    }

}
