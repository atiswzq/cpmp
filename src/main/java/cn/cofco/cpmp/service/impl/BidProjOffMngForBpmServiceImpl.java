package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.bpm.entity.PiResponse;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.BidProjOffMapper;
import cn.cofco.cpmp.dao.BidProjOffSplrInfMapper;
import cn.cofco.cpmp.dao.BidProjOffSplrRstMapper;
import cn.cofco.cpmp.dao.BidProjOffStsLogMapper;
import cn.cofco.cpmp.entity.BidProjOff;
import cn.cofco.cpmp.entity.BidProjOffSplrInf;
import cn.cofco.cpmp.entity.BidProjOffSplrRst;
import cn.cofco.cpmp.entity.BidProjOffStsLog;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOffMngForBpmService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Xujy on 2017/5/17.
 * for [线下公开招标项目管理 - BPM 实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class BidProjOffMngForBpmServiceImpl implements IBidProjOffMngForBpmService {

    private static Logger logger = LoggerManager.getBidOfflineMngLog();


    @Resource
    private BidProjOffMapper bidProjOffMapper;

    @Resource
    private BidProjOffStsLogMapper bidProjOffStsLogMapper;

    @Resource
    private BidProjOffSplrInfMapper bidProjOffSplrInfMapper;

    @Resource
    private BidProjOffSplrRstMapper bidProjOffSplrRstMapper;

    @Resource
    private IMdmService mdmService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PiResponse bud(String bpmSeqNo, String sucFlg, String memo) throws Exception {

        logger.info("线下公开招标项目立项BPM回调");

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
            String errMsg = "线下公开招标项目立项BPM回调 - 参数校验 - 失败 - " + chkRst;
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOff entity = new BidProjOff();
        entity.setBpmBudSeq(bpmSeqNo);
        Map map = PageUtils.getQueryCondsMap(entity, 1, 1);
        List<BidProjOff> bidProjOffList = bidProjOffMapper.selectByEntity(map);
        if (bidProjOffList == null || bidProjOffList.isEmpty() || Constants.EFF_FLG_OFF.equals(bidProjOffList.get(0).getEffFlg())) {
            String errMsg = "线下公开招标项目立项BPM回调 - 业务校验 - 失败 - 根据BpmBudSeq查询项目信息无数据 - bpmSeqNo[" + bpmSeqNo + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOff bidProjOff = bidProjOffList.get(0);
        if (!Constants.BID_PROJ_OFF_STS_APP_ADTING.equals(bidProjOff.getProjSts())) {
            String errMsg = "线下公开招标项目立项BPM回调 - 业务校验 - 失败 - 项目状态异常 - projSts[" + bidProjOff.getProjSts() + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            logger.info("3.1 线下公开招标项目立项BPM回调 - BPM审批通过，更新项目状态至[20:招标中], 同时发布项目招标公告");
            bidProjOff.setNtcPubTim(DateUtils.getCurrentTimeStamp());
            bidProjOff.setProjSts(Constants.BID_PROJ_OFF_STS_APP_BIDDING);
            bidProjOff.setBidNtcPubFlg(Constants.BID_NTC_PUB_FLG_ON);
        } else {
            logger.info("3.1 线下公开招标项目立项BPM回调 - BPM审批否决，更新项目状态至[00:编辑中]");
            bidProjOff.setProjSts(Constants.BID_PROJ_OFF_STS_EDTING);
        }

        bidProjOff.setBpmBudAdtMemo(memo);
        bidProjOff.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOff.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOff.setModUsr(Constants.UID_BPM);

        int effRows = bidProjOffMapper.updateByPrimaryKey(bidProjOff);
        if (effRows != 1) {
            String errMsg = "更新线下招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 线下公开招标项目立项BPM回调 - 写入线下招标项目状态记录表");
        BidProjOffStsLog bidProjOffStsLog = new BidProjOffStsLog();
        bidProjOffStsLog.setProjId(bidProjOff.getProjId());
        bidProjOffStsLog.setProjNam(bidProjOff.getProjNam());
        bidProjOffStsLog.setProjSts(bidProjOff.getProjSts());
        bidProjOffStsLog.setStsUpdTim(now);
        bidProjOffStsLog.setModUsr(Constants.UID_BPM);

        effRows = bidProjOffStsLogMapper.insert(bidProjOffStsLog);
        if (effRows != 1) {
            String errMsg = "写入线下公开招标项目状态记录表失败 - 受影响行数不为1";
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

        logger.info("线下公开招标项目定标BPM回调");

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
            String errMsg = "线下公开招标项目定标BPM回调 - 参数校验 - 失败 - " + chkRst;
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOff entity = new BidProjOff();
        entity.setBpmFinSeq(bpmSeqNo);
        Map map = PageUtils.getQueryCondsMap(entity, 1, 1);
        List<BidProjOff> bidProjOffList = bidProjOffMapper.selectByEntity(map);
        if (bidProjOffList == null || bidProjOffList.isEmpty() || Constants.EFF_FLG_OFF.equals(bidProjOffList.get(0).getEffFlg())) {
            String errMsg = "线下公开招标项目定标BPM回调 - 业务校验 - 失败 - 根据BpmFinSeq查询项目信息无数据 - bpmSeqNo[" + bpmSeqNo + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOff bidProjOff = bidProjOffList.get(0);
        if (!Constants.BID_PROJ_OFF_STS_AWDING.equals(bidProjOff.getProjSts())) {
            String errMsg = "线下公开招标项目定标BPM回调 - 业务校验 - 失败 - 项目状态异常 - projSts[" + bidProjOff.getProjSts() + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");


        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");

        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            logger.info("3.1 线下公开招标项目定标 - BPM审批通过，更新项目状态至[50:决标审批通過]");
            bidProjOff.setProjSts(Constants.BID_PROJ_OFF_STS_AWD_ACCEPTED);
        } else {
            logger.info("3.1 线下公开招标项目定标 - BPM审批否决，按线下公开招标项目状态记录表回退状态至前一状态");
            BidProjOffStsLog bidProjOffStsLogEntity = new BidProjOffStsLog();
            bidProjOffStsLogEntity.setProjId(bidProjOff.getProjId());
            Map mapOfBidProjOffStsLog = PageUtils.getQueryCondsMap(bidProjOffStsLogEntity, 2, 2);
            List<BidProjOffStsLog> logList = bidProjOffStsLogMapper.selectByMap(mapOfBidProjOffStsLog);
            if (logList == null || logList.isEmpty()) {
                String errMsg = "线下公开招标项目定标BPM回调 - BPM审批否决 - 失败 - 线下公开招标项目状态记录表中无前一状态记录";
                logger.error(errMsg);
                return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.INNER_ERR, errMsg);
            }

            bidProjOff.setProjSts(logList.get(0).getProjSts());
        }

        bidProjOff.setBpmFinAdtMemo(memo);
        bidProjOff.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOff.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOff.setModUsr(Constants.UID_BPM);

        int effRows = bidProjOffMapper.updateByPrimaryKey(bidProjOff);
        if (effRows != 1) {
            String errMsg = "更新线下招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 线下公开招标项目定标BPM回调 - 写入线下招标项目状态记录表");
        BidProjOffStsLog bidProjOffStsLog = new BidProjOffStsLog();
        bidProjOffStsLog.setProjId(bidProjOff.getProjId());
        bidProjOffStsLog.setProjNam(bidProjOff.getProjNam());
        bidProjOffStsLog.setProjSts(bidProjOff.getProjSts());
        bidProjOffStsLog.setStsUpdTim(now);
        bidProjOffStsLog.setModUsr(Constants.UID_BPM);

        effRows = bidProjOffStsLogMapper.insert(bidProjOffStsLog);
        if (effRows != 1) {
            String errMsg = "写入线下招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.3 线下公开招标项目定标BPM回调 - 更新供应商投标表");
        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            BidProjOffSplrInf bidProjOffSplrInf = new BidProjOffSplrInf();
            bidProjOffSplrInf.setProjId(bidProjOff.getProjId());
            bidProjOffSplrInf.setBidDocSts(Constants.BID_DOC_STS_ACCEPTED);
            bidProjOffSplrInf.setEffFlg(Constants.EFF_FLG_ON);
            Map tendInfMap = PageUtils.getQueryCondsMap(bidProjOffSplrInf, 1, 0);
            List<BidProjOffSplrInf> tendInfs = bidProjOffSplrInfMapper.selectByMap(tendInfMap);

            BidProjOffSplrRst splrRst = new BidProjOffSplrRst();
            splrRst.setProjId(bidProjOff.getProjId());
            splrRst.setEffFlg(Constants.EFF_FLG_ON);
            Map splrRstMap = PageUtils.getQueryCondsMap(splrRst, 1, 0);
            List<BidProjOffSplrRst> splrRsts = bidProjOffSplrRstMapper.selectByMap(splrRstMap);
            for (BidProjOffSplrInf tendInf : tendInfs) {
                String bidDocSts = Constants.BID_DOC_STS_UNAWD;
                for (BidProjOffSplrRst tmpSplrRst : splrRsts) {
                    if (tendInf.getSplrId().equals(tmpSplrRst.getSplrId())) {
                        bidDocSts = Constants.BID_DOC_STS_AWD;
                        break;
                    }
                }
                tendInf.setBidDocSts(bidDocSts);
                tendInf.setModUsr(Constants.UID_BPM);
                tendInf.setModTim(DateUtils.getCurrentTimeStamp());

                effRows = bidProjOffSplrInfMapper.updateByPrimaryKeySelective(tendInf);
                if (effRows != 1) {
                    String errMsg = "更新供应商投标表失败 - 受影响行数不为1";
                    logger.error(errMsg);
                }
            }

            // 拋MDM
            List<Long> splrIds = splrRsts.stream().map(s -> s.getSplrId()).collect(Collectors.toList());
            logger.info("线下项目定标通过 - 定标供应商拋送MDM");
            try {
                mdmService.splrAply(splrIds);
            } catch (Exception e) {
                logger.error("线下项目定标通过 - 定标供应商拋送MDM异常 - for " + ExceptionUtils.getFullStackTrace(e));
            }
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_SUC, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PiResponse rpl(String bpmSeqNo, String sucFlg, String memo) {
        logger.info("线下公开招标项目废标BPM回调");

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
            String errMsg = "线下公开招标项目废标BPM回调 - 参数校验 - 失败 - " + chkRst;
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOff entity = new BidProjOff();
        entity.setBpmRplSeq(bpmSeqNo);
        Map map = PageUtils.getQueryCondsMap(entity, 1, 1);
        List<BidProjOff> bidProjOffList = bidProjOffMapper.selectByEntity(map);
        if (bidProjOffList == null || bidProjOffList.isEmpty() || Constants.EFF_FLG_OFF.equals(bidProjOffList.get(0).getEffFlg())) {
            String errMsg = "线下公开招标项目废标BPM回调 - 业务校验 - 失败 - 根据BpmRplSeq查询项目信息无数据 - bpmSeqNo[" + bpmSeqNo + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOff bidProjOff = bidProjOffList.get(0);
        if (!Constants.BID_PROJ_OFF_STS_RPL_ADTING.equals(bidProjOff.getProjSts())) {
            String errMsg = "线下公开招标项目废标BPM回调 - 业务校验 - 失败 - 项目状态异常 - projSts[" + bidProjOff.getProjSts() + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");


        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");

        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            logger.info("3.1 线下公开招标项目废标 - BPM审批通过，更新项目状态至[-20:已废标]");
            bidProjOff.setProjSts(Constants.BID_PROJ_OFF_STS_RPL);
        } else {
            logger.info("3.1 线下公开招标项目废标 - BPM审批否决，按线下招标项目状态记录表回退状态至前一状态");
            BidProjOffStsLog bidProjOffStsLogEntity = new BidProjOffStsLog();
            bidProjOffStsLogEntity.setProjId(bidProjOff.getProjId());
            Map mapOfBidProjOffStsLog = PageUtils.getQueryCondsMap(bidProjOffStsLogEntity, 2, 2);
            List<BidProjOffStsLog> logList = bidProjOffStsLogMapper.selectByMap(mapOfBidProjOffStsLog);
            if (logList == null || logList.isEmpty()) {
                String errMsg = "线下公开招标项目废标 - BPM审批否决 - 失败 - 线下招标项目状态记录表中无前一状态记录";
                logger.error(errMsg);
                return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.INNER_ERR, errMsg);
            }

            bidProjOff.setProjSts(logList.get(0).getProjSts());
        }

        bidProjOff.setBpmRplAdtMemo(memo);
        bidProjOff.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOff.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOff.setModUsr(Constants.UID_BPM);

        int effRows = bidProjOffMapper.updateByPrimaryKey(bidProjOff);
        if (effRows != 1) {
            String errMsg = "更新线下招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 线下公开招标项目废标BPM回调 - 写入线下招标项目状态记录表");
        BidProjOffStsLog bidProjOffStsLog = new BidProjOffStsLog();
        bidProjOffStsLog.setProjId(bidProjOff.getProjId());
        bidProjOffStsLog.setProjNam(bidProjOff.getProjNam());
        bidProjOffStsLog.setProjSts(bidProjOff.getProjSts());
        bidProjOffStsLog.setStsUpdTim(now);
        bidProjOffStsLog.setModUsr(Constants.UID_BPM);

        effRows = bidProjOffStsLogMapper.insert(bidProjOffStsLog);
        if (effRows != 1) {
            String errMsg = "写入线下招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_SUC, RtnEnum.SUC_OPR);
    }
}
