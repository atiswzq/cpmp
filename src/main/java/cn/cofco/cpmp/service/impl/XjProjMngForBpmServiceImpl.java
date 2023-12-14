package cn.cofco.cpmp.service.impl;


import cn.cofco.cpmp.bpm.entity.PiResponse;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IMdmService;
import cn.cofco.cpmp.service.IXjProjMngForBpmService;
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
 * Created by Wzq on 2018/01/15.
 * for [询价项目管理 - BPM 实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class XjProjMngForBpmServiceImpl implements IXjProjMngForBpmService {

    private static Logger logger = LoggerManager.getBidOnlineMngLog();
    @Resource
    private XjProjMapper xjProjMapper;
    @Resource
    private XjProjStsLogMapper xjProjStsLogMapper;
    @Resource
    private XjProjSplrTendInfMapper xjProjSplrTendInfMapper;
    @Resource
    private XjProjWinDtlMapper xjProjWinDtlMapper;
    @Resource
    private IMdmService mdmService;
    @Resource
    private XjProjMatDtlMapper xjProjMatDtlMapper;
    @Resource
    private RequisitionMapper requisitionMapper;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PiResponse awd(String bpmSeqNo, String sucFlg, String memo) {


        logger.info("询价项目定标BPM回调");

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
            String errMsg = "询价项目定标BPM回调 - 参数校验 - 失败 - " + chkRst;
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        XjProj entity = new XjProj();
        entity.setBpmFinSeq(bpmSeqNo);
        Map map = PageUtils.getQueryCondsMap(entity, 1, 1);
        List<XjProj> xjProjs = xjProjMapper.selectByMap(map);
        if (xjProjs == null || xjProjs.isEmpty() || Constants.EFF_FLG_OFF.equals(xjProjs.get(0).getEffFlg())) {
            String errMsg = "询价项目定标BPM回调 - 业务校验 - 失败 - 根据BpmFinSeq查询项目信息无数据 - bpmSeqNo[" + bpmSeqNo + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        XjProj xjProj = xjProjs.get(0);
        if (!Constants.XJ_PROJ_STS_AWDING.equals(xjProj.getProjSts())) {
            String errMsg = "询价项目定标BPM回调 - 业务校验 - 失败 - 项目状态异常 - projSts[" + xjProj.getProjSts() + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");


        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");

        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            logger.info("3.1 询价项目定标BPM回调 - BPM审批通过，更新项目状态至[50:决标审批通過]");
            xjProj.setProjSts(Constants.XJ_PROJ_STS_AWD_ACCEPTED);
        } else {
            logger.info("3.1 询价项目定标BPM回调 - BPM审批否决，按询价项目状态记录表回退状态至前一状态");
            XjProjStsLog xjProjStsLog = new XjProjStsLog();
            xjProjStsLog.setProjId(xjProj.getProjId());
            Map mapXjProjStsLog = PageUtils.getQueryCondsMap(xjProjStsLog, 2, 2);
            List<XjProjStsLog> logList = xjProjStsLogMapper.selectByMap(mapXjProjStsLog);
            if (logList == null || logList.isEmpty()) {
                String errMsg = "询价项目定标BPM回调 - BPM审批否决 - 失败 - 询价项目状态记录表中无前一状态记录";
                logger.error(errMsg);
                return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.INNER_ERR, errMsg);
            }
            xjProj.setProjSts(Constants.XJ_PROJ_STS_OPENED);
        }

        xjProj.setBpmAwdAdtMemo(memo);
        xjProj.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        xjProj.setModTim(DateUtils.getCurrentTimeStamp());
        xjProj.setModUsr(Constants.UID_BPM);

        int effRows = xjProjMapper.updateByPrimaryKeySelective(xjProj);
        if (effRows != 1) {
            String errMsg = "更新询价项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 询价项目定标BPM回调 - 写入询价项目状态记录表");
        XjProjStsLog xjProjStsLog = new XjProjStsLog();
        xjProjStsLog.setProjId(xjProj.getProjId());
        xjProjStsLog.setProjNam(xjProj.getProjNam());
        xjProjStsLog.setProjSts(xjProj.getProjSts());
        xjProjStsLog.setStsUpdTim(now);
        xjProjStsLog.setUpdUsr(Constants.UID_BPM);

        effRows = xjProjStsLogMapper.insert(xjProjStsLog);
        if (effRows != 1) {
            String errMsg = "写入询价项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.3 询价项目定标BPM回调 - 更新供应商投标表");
        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            XjProjSplrTendInf xjProjSplrTendInf = new XjProjSplrTendInf();
            xjProjSplrTendInf.setProjId(xjProj.getProjId());
            xjProjSplrTendInf.setBidDocSts(Constants.BID_DOC_STS_ACCEPTED);
            xjProjSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
            Map tendInfMap = PageUtils.getQueryCondsMap(xjProjSplrTendInf, 1,0);
            List<XjProjSplrTendInf> tendInfs = xjProjSplrTendInfMapper.selectByMap(tendInfMap);

            List<XjProjWinDtl> winDtls = xjProjWinDtlMapper.selectAwdSplrsByProjId(xjProj.getProjId());

            for (XjProjSplrTendInf tendInf : tendInfs) {
                String bidDocSts = Constants.BID_DOC_STS_UNAWD;
                for (XjProjWinDtl winDtl : winDtls) {
                    if (tendInf.getSplrId().equals(winDtl.getSplrId())) {
                        bidDocSts = Constants.BID_DOC_STS_AWD;
                        break;
                    }
                }
                tendInf.setBidDocSts(bidDocSts);
                tendInf.setModUsr(Constants.UID_BPM);
                tendInf.setModTim(DateUtils.getCurrentTimeStamp());

                effRows = xjProjSplrTendInfMapper.updateByPrimaryKeySelective(tendInf);
                if (effRows != 1) {
                    String errMsg = "更新供应商投标表失败 - 受影响行数不为1";
                    logger.error(errMsg);
                }
            }

            // 拋MDM
            List<Long> splrIds = winDtls.stream().map(s -> s.getSplrId()).collect(Collectors.toList());
            logger.info("询价项目定标通过 - 定标供应商拋送MDM");
            try {
                mdmService.splrAply(splrIds);
            } catch (Exception e) {
                logger.error("询价项目定标通过 - 定标供应商拋送MDM异常 - for " + ExceptionUtils.getFullStackTrace(e));
            }
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_SUC, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public PiResponse rpl(String bpmSeqNo, String sucFlg, String memo) {
        logger.info("询价项目废标BPM回调");

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
            String errMsg = "询价项目废标BPM回调 - 参数校验 - 失败 - " + chkRst;
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        XjProj entity = new XjProj();
        entity.setBpmRplSeq(bpmSeqNo);
        Map map = PageUtils.getQueryCondsMap(entity, 1, 1);
        List<XjProj> xjProjs = xjProjMapper.selectByMap(map);
        if (xjProjs == null || xjProjs.isEmpty() || Constants.EFF_FLG_OFF.equals(xjProjs.get(0).getEffFlg())) {
            String errMsg = "询价项目废标BPM回调 - 业务校验 - 失败 - 根据BpmRplSeq查询项目信息无数据 - bpmSeqNo[" + bpmSeqNo + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        XjProj xjProj = xjProjs.get(0);
        if (!Constants.XJ_PROJ_STS_RPL_ADTING.equals(xjProj.getProjSts())) {
            String errMsg = "询价项目废标BPM回调 - 业务校验 - 失败 - 项目状态异常 - projSts[" + xjProj.getProjSts() + "]";
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");

        if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
            logger.info("3.1 询价项目废标BPM回调 - BPM审批通过，更新项目状态至[-20:已废标]");
            xjProj.setProjSts(Constants.XJ_PROJ_STS_RPL);

            // 若为采购需求中的物料，则将需求物料置状态为【不在招标中】
            XjProjMatDtl matDtlEntity = new XjProjMatDtl();
            matDtlEntity.setProjId(xjProj.getProjId());
            matDtlEntity.setEffFlg(Constants.EFF_FLG_ON);
            Map matDtlEntityMap = PageUtils.getQueryCondsMap(matDtlEntity, 0, 0);
            List<XjProjMatDtl> matDtlList = xjProjMatDtlMapper.selectByMap(matDtlEntityMap);

            Iterator<XjProjMatDtl> itQry = matDtlList.iterator();
            List<Long> reqIds = new ArrayList<>();
            while (itQry.hasNext()) {
                XjProjMatDtl dtlQry = itQry.next();
                Long reqId = dtlQry.getReqId();
                if (reqId != null && !reqId.equals(0L)) {
                    reqIds.add(reqId);
                }
            }

            if (!reqIds.isEmpty()) {
                requisitionMapper.updateToInzb(Constants.INZB_0, reqIds);
            }
        } else {
            logger.info("3.1 询价项目废标 - BPM审批否决，按询价项目状态记录表回退状态至前一状态");
            XjProjStsLog xjProjStsLog = new XjProjStsLog();
            xjProjStsLog.setProjId(xjProj.getProjId());
            Map mapOfXjProjStsLog = PageUtils.getQueryCondsMap(xjProjStsLog, 2, 2);
            List<XjProjStsLog> logList = xjProjStsLogMapper.selectByMap(mapOfXjProjStsLog);
            if (logList == null || logList.isEmpty()) {
                String errMsg = "询价项目废标 - BPM审批否决 - 失败 - 询价项目状态记录表中无前一状态记录";
                logger.error(errMsg);
                return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.INNER_ERR, errMsg);
            }

            xjProj.setProjSts(logList.get(0).getProjSts());
        }

        xjProj.setBpmRplAdtMemo(memo);
        xjProj.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        xjProj.setModTim(DateUtils.getCurrentTimeStamp());
        xjProj.setModUsr(Constants.UID_BPM);

        int effRows = xjProjMapper.updateByPrimaryKeySelective(xjProj);
        if (effRows != 1) {
            String errMsg = "更新询价项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 询价项目废标BPM回调 - 写入询价项目状态记录表");
        XjProjStsLog xjProjStsLog = new XjProjStsLog();
        xjProjStsLog.setProjId(xjProj.getProjId());
        xjProjStsLog.setProjNam(xjProj.getProjNam());
        xjProjStsLog.setProjSts(xjProj.getProjSts());
        xjProjStsLog.setStsUpdTim(now);
        xjProjStsLog.setUpdUsr(Constants.UID_BPM);

        effRows = xjProjStsLogMapper.insert(xjProjStsLog);
        if (effRows != 1) {
            String errMsg = "写入询价项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_SUC, RtnEnum.SUC_OPR);
    }

}
