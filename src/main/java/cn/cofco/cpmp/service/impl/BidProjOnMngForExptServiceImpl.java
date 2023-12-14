package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.dto.exptgrd.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOnMngForExptService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;
import cn.cofco.cpmp.utils.checkers.BidProjOnMngForExptChecker;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xujy on 2017/5/17.
 * for [线上招标项目管理服务实现类 - 专家] in cpmp
 */
@Service
@Transactional("transactionManager")
public class BidProjOnMngForExptServiceImpl implements IBidProjOnMngForExptService {

    private static Logger logger = LoggerManager.getBidOnlineMngLog();

    @Resource
    private BidProjOnMapper bidProjOnMapper;

    @Resource
    private BidProjOnMatDtlMapper bidProjOnMatDtlMapper;

    @Resource
    private BidProjOnSplrQotInfMapper bidProjOnSplrQotInfMapper;

    @Resource
    private BidProjOnSplrQotDtlMapper bidProjOnSplrQotDtlMapper;

    @Resource
    private BidProjOnExptGrdInfMapper bidProjOnExptGrdInfMapper;

    @Resource
    private BidProjOnSplrTendInfMapper bidProjOnSplrTendInfMapper;

    @Resource
    private BidProjOnExptGrdDtlMapper bidProjOnExptGrdDtlMapper;

    @Resource
    private ExptInfMapper exptInfMapper;

    @Resource
    private BidProjOnWinDtlMapper winDtlMapper;

    @Resource
    private BidProjOnExptGrdDtlLowMapper bidProjOnExptGrdDtlLowMapper;

    @Resource
    private SplrMapper splrMapper;

    private String checkAuth() {
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        Integer userId = userinfo.getUserid();

        if (userId == null || userId == 0) {
            return "鉴权失败 - userId为空";
        }

        return "";
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto queryGrdInfs(BidProjOnExptGrdInf entity, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {

        logger.info("根据条件分页查询评标项目");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        Integer userId = userinfo.getUserid();
        entity.setExptId(Long.valueOf(userId));



            if (pageNo == null || pageNo <= 0) {
                pageNo = 1;
            }

            if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
                pageSize = Constants.PAGE_SIZE;
            }

            Integer start = (pageNo - 1) * pageSize + 1;

            Integer to = pageNo * pageSize;

            Map map = PageUtils.getQueryCondsMap(entity, start, to);
            map.put("IsMCompany",IsMCompany);
            List<BidProjOnExptGrdInfDto> bidProjOnExptGrdInfDtos = new ArrayList<>();
            List<BidProjOnExptGrdInf> bidProjOnExptGrdInfs = bidProjOnExptGrdInfMapper.selectByMap(map);
            bidProjOnExptGrdInfs.forEach(exptGrdInf -> {
                BidProjOnExptGrdInfDto exptGrdInfDto = new BidProjOnExptGrdInfDto();
                exptGrdInfDto.setBidProjOnExptGrdInf(exptGrdInf);
                BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(exptGrdInf.getProjId());
                exptGrdInfDto.setProjSts(bidProjOn.getProjSts());
                bidProjOnExptGrdInfDtos.add(exptGrdInfDto);
            });
            Integer count = bidProjOnExptGrdInfMapper.countByMap(map);

            PagedResult result = new PagedResult(bidProjOnExptGrdInfDtos, count);

            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);

    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewProjDtl(Long projId) throws Exception {
        logger.info("根据项目ID查询线上招标信息详情");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

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
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(matDtlMap);
//        for (BidProjOnMatDtl dtl : matDtls) {
//            if (!StringUtils.isEmpty(dtl.getMatUnt())) {
//                ComParm matUntComParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_MAT_UNT, dtl.getMatUnt());
//                if (matUntComParm != null) {
//                    dtl.setMatUnt(matUntComParm.getParmVal());
//                } else {
//                    dtl.setMatUnt("");
//                }
//            }
//        }

        BidProjOnInfViewIoDto bidProjOnInfViewIoDto = new BidProjOnInfViewIoDto();
        bidProjOnInfViewIoDto.setBidProjOn(entity);
        bidProjOnInfViewIoDto.setMatDtls(matDtls);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, bidProjOnInfViewIoDto);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto sndGrdKey(Long grdId) throws Exception {
        logger.info("发送评标密钥");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (grdId == null) {
            String errMsg = "参数校验 - 不通过 - grdId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOnExptGrdInf bidProjOnExptGrdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(grdId);
        if (bidProjOnExptGrdInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnExptGrdInf.getEffFlg())) {
            String errMsg = "发送评标密钥 - 根据评标ID查询评标信息无数据 - grdId[" + grdId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        String projGrdSts = bidProjOnExptGrdInf.getProjGrdSts();
        if (!Constants.GRD_STS_WAITING.equals(projGrdSts)) {
            String errMsg = "发送评标密钥 - 评标信息状态不为待评标 - projGrdSts[" + projGrdSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(bidProjOnExptGrdInf.getProjId());
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "发送评标密钥 - 根据项目ID查询项目信息无数据 - projId[" + bidProjOnExptGrdInf.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Long exptId = bidProjOnExptGrdInf.getExptId();
        ExptInf exptInf = exptInfMapper.selectByPrimaryKey(exptId);
        if (exptInf == null || Constants.EFF_FLG_OFF.equals(exptInf.getEffFlg())) {
            String errMsg = "发送评标密钥 - 根据专家ID查询专家信息无数据 - exptId[" + exptId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        logger.info("2.2 业务校验 - 校验项目状态[评标中:23]或[二次竞价评标中:33]");
        if (!(Constants.BID_PROJ_ON_STS_GRADING.equals(bidProjOn.getProjSts())
                || Constants.BID_PROJ_ON_STS_QOT2_GRADING.equals(bidProjOn.getProjSts()))) {
            String errMsg = "发送开标密钥 - 校验项目状态[评标中:23]或[二次竞价评标中:33] - 失败 - projSts[" + bidProjOn.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        boolean sndFlg = SmsSndUtils.sendSmsForGrdBidProjOn(bidProjOnExptGrdInf.getGrdKey(), exptInf, bidProjOn);

        if (!sndFlg) {
            String errMsg = "发送评标密钥 - 发送短信 - 失败";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewBidDtl(Long id) throws Exception {
        logger.info("查看线上招标项目供应商投标信息详情");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (id == null) {
            String errMsg = "参数校验 - 不通过 - id不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        BidProjOnExptGrdDtl bidProjOnSplrExptScr = bidProjOnExptGrdDtlMapper.selectByPrimaryKey(id);
        if (bidProjOnSplrExptScr == null || Constants.EFF_FLG_OFF.equals(bidProjOnSplrExptScr.getEffFlg())) {
            String errMsg = "查看线上招标项目供应商投标信息详情 - 根据ID查询线上招标项目标书专家评分表无数据 - id[" + id + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Long bidId = bidProjOnSplrExptScr.getBidAppId();

        BidProjOnSplrTendInf bidProjOnSplrTendInf = bidProjOnSplrTendInfMapper.selectByPrimaryKey(bidId);
        if (bidProjOnSplrTendInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnSplrTendInf.getEffFlg())) {
            String errMsg = "查看线上招标项目供应商投标信息详情 - 根据ID查询线上招标项目供应商投标信息详情无数据 - bidId[" + bidId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        QotInfForExptIoDto dto = new QotInfForExptIoDto();

        dto.setBidProjOnExptGrdDtl(bidProjOnSplrExptScr);

        dto.setBidProjOnSplrTendInf(bidProjOnSplrTendInf);

        Long qotId = bidProjOnSplrTendInf.getQotId();
        if (qotId != null) {
            BidProjOnSplrQotInf qotInf = bidProjOnSplrQotInfMapper.selectByPrimaryKey(qotId);
            BidProjOnSplrQotDtl entity = new BidProjOnSplrQotDtl();
            entity.setQotId(qotId);
            entity.setEffFlg(Constants.EFF_FLG_ON);
            Map map = PageUtils.getQueryCondsMap(entity, 0, 0);
            List<BidProjOnSplrQotDtl> qotDtls = bidProjOnSplrQotDtlMapper.selectByMap(map);
            dto.setQotInf(qotInf);
            dto.setQotDtls(qotDtls);
        }


        Long qot2Id = bidProjOnSplrTendInf.getQot2Id();
        if (qot2Id != null) {
            BidProjOnSplrQotInf qotInf2 = bidProjOnSplrQotInfMapper.selectByPrimaryKey(qot2Id);
            BidProjOnSplrQotDtl entity = new BidProjOnSplrQotDtl();
            entity.setQotId(qot2Id);
            entity.setEffFlg(Constants.EFF_FLG_ON);
            Map map = PageUtils.getQueryCondsMap(entity, 0, 0);
            List<BidProjOnSplrQotDtl> qotDtls2 = bidProjOnSplrQotDtlMapper.selectByMap(map);
            dto.setQotInf2(qotInf2);
            dto.setQotDtls2(qotDtls2);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, dto);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto bgnGrd(BidProjOnExptGrdInf entity) throws Exception {

        logger.info("开始评标");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String chkRst = BidProjOnMngForExptChecker.checkArgsForBgnGrd(entity);
        if (!"".equals(chkRst)) {
            String errMsg = "开始评标 - 失败 - 参数校验不通过" + chkRst;
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");

        BidProjOnExptGrdInf bidProjOnExptGrdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(entity.getGrdId());
        if (bidProjOnExptGrdInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnExptGrdInf.getEffFlg())) {
            String errMsg = "开始评标 - 业务校验 - 失败 - 根据评标ID查询评标信息无数据 - grdId[" + entity.getGrdId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (!entity.getGrdKey().equals(bidProjOnExptGrdInf.getGrdKey())) {
            String errMsg = "开始评标 - 业务校验 - 失败 - 评标密钥不正确";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(bidProjOnExptGrdInf.getProjId());
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "开始评标 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + bidProjOnExptGrdInf.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (!Constants.BID_PROJ_ON_STS_GRADING.equals(bidProjOn.getProjSts()) && !Constants.BID_PROJ_ON_STS_QOT2_GRADING.equals(bidProjOn.getProjSts())) {
            String errMsg = "开始评标 - 业务校验 - 失败 - 项目状态异常 - projSts[" + bidProjOn.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (!Constants.GRD_STS_WAITING.equals(bidProjOnExptGrdInf.getProjGrdSts())) {
            String errMsg = "开始评标 - 业务校验 - 失败 - 评标状态异常 - projGrdSts[" + bidProjOnExptGrdInf.getProjGrdSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");

        // 查询投标信息
        logger.info("3.1 查询评标信息");
        Long projId = bidProjOnExptGrdInf.getProjId();
        bidProjOnExptGrdInf.setProjGrdSts(Constants.GRD_STS_GRADDING);
        bidProjOnExptGrdInf.setModUsr(userinfo.getRealname());
        bidProjOnExptGrdInf.setModTim(DateUtils.getCurrentTimeStamp());
        int effRows = bidProjOnExptGrdInfMapper.updateByPrimaryKey(bidProjOnExptGrdInf);
        if (effRows != 1) {
            String errMsg = "开始评标 - 更新评标信息 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
        bidProjOnSplrTendInf.setProjId(projId);
        bidProjOnSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);

        Map map = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf, 0, 0);
        List<BidProjOnSplrTendInf> bidProjOnSplrTendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);
        Map<Long, BidProjOnSplrTendInf> tendMap = new HashMap<>();
        for (BidProjOnSplrTendInf tendInf : bidProjOnSplrTendInfs) {
            tendMap.put(tendInf.getSplrId(), tendInf);
        }

        // 查询物料信息
        BidProjOnMatDtl matDtlEntity = new BidProjOnMatDtl();
        matDtlEntity.setProjId(projId);
        matDtlEntity.setEffFlg(Constants.EFF_FLG_ON);
        map = PageUtils.getQueryCondsMap(matDtlEntity, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(map);
        Map matMap = new HashMap();
        for (BidProjOnMatDtl m : matDtls) {
            matMap.put(m.getId(), m);
        }

        // 查询线上招标项目供应商报价明细表
        BidProjOnSplrQotDtl splrQotDtlEntity = new BidProjOnSplrQotDtl();
        splrQotDtlEntity.setProjId(projId);
        splrQotDtlEntity.setEffFlg(Constants.EFF_FLG_ON);
        map = PageUtils.getQueryCondsMap(splrQotDtlEntity, 0, 0);
        List<BidProjOnSplrQotDtl> splrQotDtls = bidProjOnSplrQotDtlMapper.selectByMap(map);

        logger.info("3.2 增至线上招标项目标书专家评分表");
        String grdRul = bidProjOn.getGrdRul();
        if (Constants.GRD_RUL_0.equals(grdRul) || Constants.GRD_RUL_2.equals(grdRul)) {
            logger.info("3.2.1 最低价中标");

            Map<Long, Splr> splrMap = bidProjOnSplrTendInfs.stream().collect(
                    Collectors.toMap(BidProjOnSplrTendInf::getSplrId,
                            tendInf -> splrMapper.selectByPrimaryKey(tendInf.getSplrId())));

            List<BidProjOnSplrQotDtlIoDto> qotDtlIoDtos = splrQotDtls.parallelStream()
                    .filter(dtl -> dtl.getUntPri() != null && !dtl.getUntPri().equals(BigDecimal.ZERO))
                    .filter(dtl -> dtl.getSplNum() != null && !dtl.getSplNum().equals("0"))
                    .map(dtl -> {
                        BidProjOnSplrQotDtlIoDto splrQotDtlIoDto = new BidProjOnSplrQotDtlIoDto();
                        BeanUtils.copyProperties(splrQotDtlIoDto, dtl);
                        Splr splr = splrMap.get(dtl.getSplrId());
                        splrQotDtlIoDto.setFullNam(splr.getFullNam());
                        return splrQotDtlIoDto;
                    })
                    .collect(Collectors.toList());

            for(BidProjOnSplrQotDtlIoDto splrQotIoDtl:qotDtlIoDtos){
                BidProjOnExptGrdDtlLow exptGrdDtlLow = new BidProjOnExptGrdDtlLow();
                exptGrdDtlLow.setGrdId(bidProjOnExptGrdInf.getGrdId());
                exptGrdDtlLow.setMatId(splrQotIoDtl.getMatId());
                exptGrdDtlLow.setSplrId(splrQotIoDtl.getSplrId());
                exptGrdDtlLow.setProjId(splrQotIoDtl.getProjId());
                BidProjOnMatDtl matDtl = (BidProjOnMatDtl) matMap.get(splrQotIoDtl.getMatId());
                if (matDtl != null) {
                    exptGrdDtlLow.setMatCod(matDtl.getMatCod());
                    exptGrdDtlLow.setMatNam(matDtl.getMatNam());
                    exptGrdDtlLow.setTechServ(matDtl.getTechServ());
                    exptGrdDtlLow.setMemo(matDtl.getMemo());
                    exptGrdDtlLow.setReqId(matDtl.getReqId());
                    exptGrdDtlLow.setDlvDte(matDtl.getDlvDte());
                    exptGrdDtlLow.setDlvAdr(matDtl.getDlvAdr());
                    exptGrdDtlLow.setPchsNum(matDtl.getPchsNum());
                }
                BidProjOnSplrTendInf tendInf = tendMap.get(splrQotIoDtl.getSplrId());
                if (tendInf != null) {
                    exptGrdDtlLow.setBidAppId(tendInf.getId());
                    exptGrdDtlLow.setSplrNam(tendInf.getSplrNam());
                }
                exptGrdDtlLow.setCurrTyp(splrQotIoDtl.getCurrTyp());
                exptGrdDtlLow.setExRat(splrQotIoDtl.getExRat());
                exptGrdDtlLow.setUntPri(splrQotIoDtl.getUntPri());
                exptGrdDtlLow.setSplNum(splrQotIoDtl.getSplNum());
                BigDecimal uniPri = splrQotIoDtl.getUntPri();
                BigDecimal splNum = new BigDecimal(splrQotIoDtl.getSplNum());
                BigDecimal exRat = new BigDecimal(splrQotIoDtl.getExRat());
                BigDecimal ttlPri = uniPri.multiply(exRat).multiply(splNum).setScale(2, BigDecimal.ROUND_HALF_UP);
                exptGrdDtlLow.setTtlPri(ttlPri);
                exptGrdDtlLow.setTendDlvDte(splrQotIoDtl.getTendDlvDte());
                exptGrdDtlLow.setMatBnd(splrQotIoDtl.getMatBnd());
                exptGrdDtlLow.setEffFlg(Constants.EFF_FLG_ON);
                exptGrdDtlLow.setCrtUsr(userinfo.getRealname());
                exptGrdDtlLow.setCrtTim(DateUtils.getCurrentTimeStamp());
                effRows = bidProjOnExptGrdDtlLowMapper.insertSelective(exptGrdDtlLow);

                if (effRows != 1) {
                    String errMsg = "开始评标 - 增至线上招标项目专家评标最低价中标明细表 - 受影响行数不为1";
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
            }

        } else {
            // TODO 打分逻辑
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getScrDtls(Long grdId, Integer pageNo, Integer pageSize) throws Exception {

        logger.info("根据评标ID查看评分详情");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        // 参数处理
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (grdId == null || grdId == 0L) {
            String errMsg = "根据评标ID查看评分详情 - 失败 - 参数校验不通过 - 评标ID为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");

        BidProjOnExptGrdInf bidProjOnExptGrdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(grdId);
        if (bidProjOnExptGrdInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnExptGrdInf.getEffFlg())) {
            String errMsg = "根据评标ID查看评分详情 - 业务校验 - 失败 - 根据评标ID查询评标信息无数据 - grdId[" + grdId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Long projId = bidProjOnExptGrdInf.getProjId();
        if (projId == null || projId.equals(0L)) {
            String errMsg = "根据评标ID查看评分详情 - 业务校验 - 失败 - 项目ID为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "根据评标ID查看评分详情 - 业务校验 - 失败 - 项目不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        BidProjOnExptGrdDtl entity = new BidProjOnExptGrdDtl();
        entity.setEffFlg(Constants.EFF_FLG_ON);
        entity.setGrdId(grdId);

        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize + 1;

        Integer to = pageSize * pageNo;

        Map map = PageUtils.getQueryCondsMap(entity, start, to);

        List<BidProjOnExptGrdDtl> list = bidProjOnExptGrdDtlMapper.selectByMap(map);

        Integer count = bidProjOnExptGrdDtlMapper.countByMap(map);

        ScrDtlsIoDto scrDtlsIoDto = new ScrDtlsIoDto();
        scrDtlsIoDto.setCount(count);
        scrDtlsIoDto.setList(list);
        if ((Constants.BID_PROJ_ON_STS_GRADING.equals(bidProjOn.getProjSts())
                || Constants.BID_PROJ_ON_STS_QOT2_GRADING.equals(bidProjOn.getProjSts()))
                && Constants.GRD_STS_GRADDING.equals(bidProjOnExptGrdInf.getProjGrdSts())) {
            scrDtlsIoDto.setFlg(true);
        } else {
            scrDtlsIoDto.setFlg(false);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, scrDtlsIoDto);

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto score(BidProjOnExptGrdDtl entity) throws Exception {

        logger.info("线上招标项目打分");


        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        Long id = entity.getId();
        if (id == null) {
            String errMsg = "参数校验 - 不通过 - id不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BigDecimal scr = entity.getScr();
        if (scr == null) {
            String errMsg = "参数校验 - 不通过 - 评分不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        String scrMemo = entity.getScrMemo();

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        BidProjOnExptGrdDtl bidProjOnSplrExptScr = bidProjOnExptGrdDtlMapper.selectByPrimaryKey(id);
        if (bidProjOnSplrExptScr == null || Constants.EFF_FLG_OFF.equals(bidProjOnSplrExptScr.getEffFlg())) {
            String errMsg = "线上招标项目打分 - 根据ID查询线上招标项目标书专家评分表无数据 - id[" + id + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Long grdId = bidProjOnSplrExptScr.getGrdId();
        BidProjOnExptGrdInf bidProjOnExptGrdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(grdId);
        if (bidProjOnExptGrdInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnExptGrdInf.getEffFlg())) {
            String errMsg = "线上招标项目打分 - 根据评标ID查询评标信息无数据 - grdId[" + grdId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (!Constants.GRD_STS_GRADDING.equals(bidProjOnExptGrdInf.getProjGrdSts())) {
            String errMsg = "线上招标项目打分 - 项目评标状态不为评分中 - gradSts[" + bidProjOnExptGrdInf.getProjGrdSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Long projId = bidProjOnSplrExptScr.getProjId();
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        String projSts = bidProjOn.getProjSts();
        String grdTyp = bidProjOnExptGrdInf.getGrdTyp();
        // 一次评标
        if (Constants.BID_CNT_TYP_FST.equals(grdTyp)) {
            // TODO 评标截止时间判断
            if (!Constants.BID_PROJ_ON_STS_GRADING.equals(projSts)) {
                String errMsg = "线上招标项目打分 - 项目评标状态不为评标中 - projSts[" + projSts + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        } else {
            // TODO 评标截止时间判断
            if (!Constants.BID_PROJ_ON_STS_QOT2_GRADING.equals(projSts)) {
                String errMsg = "线上招标项目打分 - 项目评标状态不为二次竞价评标中 - projSts[" + projSts + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }

        bidProjOnSplrExptScr.setScr(scr);
        bidProjOnSplrExptScr.setScrMemo(scrMemo);
        bidProjOnSplrExptScr.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOnSplrExptScr.setModUsr(userinfo.getRealname());
        int effRows = bidProjOnExptGrdDtlMapper.updateByPrimaryKey(bidProjOnSplrExptScr);
        if (effRows != 1) {
            String errMsg = "线上招标项目打分 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto subScrRst(Long grdId) throws Exception {

        logger.info("提交打分结果");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        // 参数处理
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (grdId == null || grdId == 0L) {
            String errMsg = "提交打分结果 - 失败 - 参数校验不通过 - 评标ID为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");

        BidProjOnExptGrdInf bidProjOnExptGrdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(grdId);
        if (bidProjOnExptGrdInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnExptGrdInf.getEffFlg())) {
            String errMsg = "提交打分结果 - 业务校验 - 失败 - 根据评标ID查询评标信息无数据 - grdId[" + grdId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (!Constants.GRD_STS_GRADDING.equals(bidProjOnExptGrdInf.getProjGrdSts())) {
            String errMsg = "提交打分结果 - 业务校验 - 失败 - 评标状态异常 - grdId[" + grdId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Long projId = bidProjOnExptGrdInf.getProjId();
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        String projSts = bidProjOn.getProjSts();
        String gradTyp = bidProjOnExptGrdInf.getGrdTyp();
        // 一次评标
        if (Constants.BID_CNT_TYP_FST.equals(gradTyp)) {
            // TODO 评标截止时间判断
            if (!Constants.BID_PROJ_ON_STS_GRADING.equals(projSts)) {
                String errMsg = "提交打分结果 - 失败 - 项目评标状态不为评标中 - projSts[" + projSts + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        } else {
            // TODO 评标截止时间判断
            if (!Constants.BID_PROJ_ON_STS_QOT2_GRADING.equals(projSts)) {
                String errMsg = "提交打分结果 - 失败 - 项目评标状态不为二次竞价评标中 - projSts[" + projSts + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }
        //如果是最低价评标根据排名判断是否已经评标
        if(Constants.GRD_RUL_2.equals(bidProjOn.getGrdRul())||Constants.GRD_RUL_0.equals(bidProjOn.getGrdRul())){
            BidProjOnExptGrdDtlLow entity = new BidProjOnExptGrdDtlLow();
            entity.setEffFlg(Constants.EFF_FLG_ON);
            entity.setGrdId(grdId);
            Map map = PageUtils.getQueryCondsMap(entity, 0, 0);
            List<BidProjOnExptGrdDtlLow> list = bidProjOnExptGrdDtlLowMapper.selectByMap(map);
            for (BidProjOnExptGrdDtlLow exptGrdDtlLow : list) {
                if (exptGrdDtlLow.getGrdOrd() == null) {
                    String errMsg = "提交打分结果 - 失败 - 专家还未评标 - id[" + exptGrdDtlLow.getGrdOrd() + "]";
                    logger.error(errMsg);
                    return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
                }
            }
        }else {
            String errMsg = "展示评标信息 - 暂不支持该评标类型 - grdRul[" + bidProjOn.getGrdRul() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        bidProjOnExptGrdInf.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOnExptGrdInf.setModUsr(userinfo.getRealname());
        bidProjOnExptGrdInf.setProjGrdSts(Constants.GRD_STS_DONE);

        int effRows = bidProjOnExptGrdInfMapper.updateByPrimaryKey(bidProjOnExptGrdInf);
        if (effRows != 1) {
            String errMsg = "线上招标项目打分 - 更新评标状态 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("2. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    public OutputDto showExptInf() throws Exception {
        logger.info("根据专家ID查询专家信息");

        // 0. 权限校验
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        Long exptId = Long.valueOf(userinfo.getUserid());

        // 1. 参数校验
        long time = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (exptId == null || exptId.equals(0L)) {
            String errMsg = "根据专家ID查询专家信息 - 失败 - 参数校验不通过 - 专家ID不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        ExptInf exptInf = exptInfMapper.selectByPrimaryKey(exptId);
        if (exptInf == null || Constants.EFF_FLG_OFF.equals(exptInf.getEffFlg())) {
            String errMsg = "根据专家ID查询专家信息 - 失败 - 专家信息不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long duration = DateUtils.currentTimeMillis() - time;
        logger.info("1. 参数校验 - 结束，duration[" + duration + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, exptInf);
    }

    /*@Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto showAwdInf(Long grdId) throws Exception {
        logger.info("专家查看定标结果信息");

        // 0. 权限校验
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        Long exptId = Long.valueOf(userinfo.getUserid());

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (grdId == null || grdId == 0L) {
            String errMsg = "专家查看定标结果信息 - 失败 - 参数校验不通过 - 评标ID为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 专家查看定标结果信息");

        BidProjOnExptGrdInf bidProjOnExptGrdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(grdId);
        if (bidProjOnExptGrdInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnExptGrdInf.getEffFlg())) {
            String errMsg = "专家查看定标结果信息 - 业务校验 - 失败 - 根据评标ID查询评标信息无数据 - grdId[" + grdId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        Long projId = bidProjOnExptGrdInf.getProjId();
        if (projId == null || projId.equals(0L)) {
            String errMsg = "专家查看定标结果信息 - 业务校验 - 失败 - 项目ID为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "专家查看定标结果信息 - 业务校验 - 失败 - 项目不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
    *//*    if(!Constants.BID_PROJ_ON_STS_EXPT_ADITING.equals(bidProjOn.getProjSts())){
            String errMsg = "专家查看定标结果信息 - 业务校验 - 失败 - 项目状态不为专家审批中";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }*//*
        logger.info(" 查询该项目下所有物料明细");
        BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(matDtlMap);
        if (matDtls == null || matDtls.isEmpty()) {
            String errMsg = "专家查看定标结果信息 - 业务校验 - 异常 - 该项目无物料信息 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("业务校验 -结束 duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 查询定标结果信息 - 开始");

        BidProjOnWinDtl bidProjOnWinDtl = new BidProjOnWinDtl();
        bidProjOnWinDtl.setProjId(projId);
        bidProjOnWinDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOnWinDtl, 0, 0);
        BidProjOnExptAwdResIoDto exptAwdResIoDto = new BidProjOnExptAwdResIoDto();
        List<BidProjOnWinDtl> winDtls = winDtlMapper.selectByMap(map);
        BidProjOnMatDtl bidProjOnMatDtl = new BidProjOnMatDtl();
        bidProjOnMatDtl.setProjId(bidProjOn.getProjId());
        bidProjOnMatDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matMap = PageUtils.getQueryCondsMap(bidProjOnMatDtl,0,0);
        List<BidProjOnMatDtl> bidProjOnMatDtls = bidProjOnMatDtlMapper.selectByMap(matMap);
        exptAwdResIoDto.setProjOnMatDtls(bidProjOnMatDtls);
        exptAwdResIoDto.setBidProjOnWinDtls(winDtls);
        exptAwdResIoDto.setBidProjOnExptGrdInf(bidProjOnExptGrdInf);
        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 查询数据库 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC,exptAwdResIoDto);

    }
*/
    /*@Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto subAppRes(BidProjOnExptAppResDto exptAppResDto) throws Exception {

        logger.info("专家开始审批");

        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String chkRst = BidProjOnMngForExptChecker.checkArgsForSubAppRes(exptAppResDto);
        if (!"".equals(chkRst)) {
            String errMsg = "专家开始审批 - 失败 - 参数校验不通过" + chkRst;
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据评标ID查询评标信息");

        BidProjOnExptGrdInf bidProjOnExptGrdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(exptAppResDto.getGrdId());
        if (bidProjOnExptGrdInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnExptGrdInf.getEffFlg())) {
            String errMsg = "专家开始审批 - 业务校验 - 失败 - 根据评标ID查询评标信息无数据 - grdId[" + exptAppResDto.getGrdId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if(!Constants.IS_AGREED_0.equals(bidProjOnExptGrdInf.getIsAgreed())){
            String errMsg = "专家开始审批 - 业务校验 - 失败 - 专家审批结果不为待审批";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        Long projId = bidProjOnExptGrdInf.getProjId();
        if (projId == null || projId.equals(0L)) {
            String errMsg = "专家开始审批 - 业务校验 - 失败 - 项目ID为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "专家开始审批 - 业务校验 - 失败 - 项目不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if(!Constants.BID_PROJ_ON_STS_EXPT_ADITING.equals(bidProjOn.getProjSts())){
            String errMsg = "专家开始审批 - 业务校验 - 失败 - 项目状态不为专家审批中";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("业务校验 -结束 duration[" + durationB+ "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");

        bidProjOnExptGrdInf.setIsAgreed(exptAppResDto.getIsAgreed());
        bidProjOnExptGrdInf.setProjAwdRsn(exptAppResDto.getProjAwdRsn());
        bidProjOnExptGrdInf.setModUsr(userinfo.getRealname());
        bidProjOnExptGrdInf.setModTim(DateUtils.getCurrentTimeStamp());
        int effRows = bidProjOnExptGrdInfMapper.updateByPrimaryKey(bidProjOnExptGrdInf);
        if (effRows != 1) {
            String errMsg = "专家开始审批 - 更新审批结果 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("数据持久化 -结束 duration[" + durationC+ "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }*/


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewGrdInf(Long grdId) throws Exception {

        logger.info("展示评标信息");

        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.NO_OPRT_AUTH);
        }

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (grdId == null) {
            String errMsg = "参数校验 - 不通过 - grdId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据评标ID查询评标信息");
        BidProjOnExptGrdInf bidProjOnExptGrdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(grdId);
        if (bidProjOnExptGrdInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnExptGrdInf.getEffFlg())) {
            String errMsg = "展示评标信息 - 根据评标ID查询评标信息无数据 - grdId[" + grdId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(bidProjOnExptGrdInf.getProjId());
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "展示评标信息 - 根据项目ID查询项目信息无数据 - projId[" + bidProjOnExptGrdInf.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOnMatDtl matDtlEntity = new BidProjOnMatDtl();
        matDtlEntity.setEffFlg(Constants.EFF_FLG_ON);
        matDtlEntity.setProjId(bidProjOn.getProjId());
        Map map = PageUtils.getQueryCondsMap(matDtlEntity, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(map);

        BidProjOnExptGrdDtlLow entityQry = new BidProjOnExptGrdDtlLow();
        entityQry.setGrdId(grdId);
        entityQry.setEffFlg(Constants.EFF_FLG_ON);
        map = PageUtils.getQueryCondsMap(entityQry, 0, 0);
        List<BidProjOnExptGrdDtlLow> exptGrdDtlLows = bidProjOnExptGrdDtlLowMapper.selectByMap(map);
        if (exptGrdDtlLows == null || exptGrdDtlLows.isEmpty()) {
            String errMsg = "展示评标信息 - 根据评标ID查询评标明细信息无数据 - grdId[" + grdId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Map<String, BidProjOnExptGrdDtlLow> exptGrdDrlMap = new HashMap();
        exptGrdDtlLows.stream().forEach(l->{
            String key = l.getSplrId() + "-" + l.getMatId();
            exptGrdDrlMap.put(key, l);
        });

        List<Long> splrIds = exptGrdDtlLows.stream().map(l -> l.getSplrId()).distinct().collect(Collectors.toList());
        if (splrIds == null || splrIds.isEmpty()) {
            String errMsg = "展示评标信息 - 无供应商信息";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        String grdRul = bidProjOn.getGrdRul();
        if (Constants.GRD_RUL_2.equals(grdRul)) {
            logger.info("展示评标信息 - 最低总价评标");

            GrdInfTtlLowIoDto grdInfTtlLowIoDto = new GrdInfTtlLowIoDto();
            grdInfTtlLowIoDto.setProjInf(bidProjOn);
            grdInfTtlLowIoDto.setProjGrdSts(bidProjOnExptGrdInf.getProjGrdSts());
            List<GrdInfTtlLowDtlIoDto> list = splrIds.stream().map(splrId -> {
                GrdInfTtlLowDtlIoDto grdInfTtlLowDtl = new GrdInfTtlLowDtlIoDto();
                grdInfTtlLowDtl.setQotInfs(matDtls.stream().map(mat -> {
                    GrdInfTtlLowDtlQotInfIoDto qotInf = new GrdInfTtlLowDtlQotInfIoDto();
                    String key = splrId + "-" + mat.getId();
                    BidProjOnExptGrdDtlLow exptGrdDtlLow = exptGrdDrlMap.get(key);
                    qotInf.setId(exptGrdDtlLow.getId());
                    qotInf.setCurrTyp(exptGrdDtlLow.getCurrTyp());
                    qotInf.setMatId(exptGrdDtlLow.getMatId());
                    qotInf.setMatNam(exptGrdDtlLow.getMatNam());
                    qotInf.setSplNum(exptGrdDtlLow.getSplNum());
                    qotInf.setUniPri(exptGrdDtlLow.getUntPri());
                    qotInf.setExRat(exptGrdDtlLow.getExRat());
                    return qotInf;
                }).collect(Collectors.toList()));

                // 任取一供应商ID和招标物料ID，作为key值查询
                String key = splrId + "-" + matDtls.get(0).getId();
                BidProjOnExptGrdDtlLow exptGrdDtlLow = exptGrdDrlMap.get(key);
                grdInfTtlLowDtl.setSplrId(exptGrdDtlLow.getSplrId());
                grdInfTtlLowDtl.setGrdOrd(exptGrdDtlLow.getGrdOrd());
                grdInfTtlLowDtl.setGrdRsn(exptGrdDtlLow.getGrdRsn());
                grdInfTtlLowDtl.setIsRcmd(exptGrdDtlLow.getIsRcmd());
                grdInfTtlLowDtl.setSplrNam(exptGrdDtlLow.getSplrNam());
                grdInfTtlLowDtl.setTtlPri(new BigDecimal(
                        grdInfTtlLowDtl.getQotInfs().parallelStream()
                                .mapToDouble(qotInf -> {
                            double uniPri = qotInf.getUniPri().doubleValue();
                            double exRat = Double.valueOf(qotInf.getExRat());
                            double splNum = Double.valueOf(qotInf.getSplNum());
                            return (uniPri * exRat * splNum);
                        }).sum())
                        .setScale(2, BigDecimal.ROUND_HALF_UP));
                return grdInfTtlLowDtl;
            }).collect(Collectors.toList());
            list.sort((dtl1, dtl2) -> {
                Integer ord1 = dtl1.getGrdOrd();
                Integer ord2 = dtl2.getGrdOrd();
                if (ord1 != null && ord2 != null && !ord1.equals(0) && !ord2.equals(0)) {
                    return ord1.compareTo(ord2);
                }
                return dtl1.getTtlPri().compareTo(dtl2.getTtlPri());
            });
            grdInfTtlLowIoDto.setList(list);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, grdInfTtlLowIoDto);
        } else if (Constants.GRD_RUL_0.equals(grdRul)) {
            logger.info("展示评标信息 - 最低单价评标");

            GrdInfUniLowIoDto grdInfUniLowIoDto = new GrdInfUniLowIoDto();
            grdInfUniLowIoDto.setProjInf(bidProjOn);
            grdInfUniLowIoDto.setProjGrdSts(bidProjOnExptGrdInf.getProjGrdSts());
            List<GrdInfUniLowDtlIoDto> list = matDtls.stream().map(mat -> {
                GrdInfUniLowDtlIoDto dtl = new GrdInfUniLowDtlIoDto();
                dtl.setMatId(mat.getId());
                dtl.setMatCod(mat.getMatCod());
                dtl.setMatNam(mat.getMatNam());
                dtl.setMatUnt(mat.getMatUnt());
                dtl.setPchsNum(mat.getPchsNum());
                dtl.setDlvAdr(mat.getDlvAdr());
                dtl.setDlvDte(mat.getDlvDte());
                dtl.setMemo(mat.getMemo());
                List<GrdInfUniLowDtlQotInfIoDto> qotInfs = splrIds.stream().map(splrId -> {
                    GrdInfUniLowDtlQotInfIoDto qotInf = new GrdInfUniLowDtlQotInfIoDto();
                    String key = splrId + "-" + mat.getId();
                    BidProjOnExptGrdDtlLow exptGrdDtlLow = exptGrdDrlMap.get(key);
                    if (exptGrdDtlLow == null) {
                        return null;
                    }
                    qotInf.setId(exptGrdDtlLow.getId());
                    qotInf.setSplrId(splrId);
                    qotInf.setSplrNam(exptGrdDtlLow.getSplrNam());
                    qotInf.setIsRcmd(exptGrdDtlLow.getIsRcmd());
                    qotInf.setCurrTyp(exptGrdDtlLow.getCurrTyp());
                    qotInf.setExRat(exptGrdDtlLow.getExRat());
                    qotInf.setUniPri(exptGrdDtlLow.getUntPri().toString());
                    qotInf.setSplNum(exptGrdDtlLow.getSplNum());

                    BigDecimal uniPri = exptGrdDtlLow.getUntPri();
                    BigDecimal exRat = new BigDecimal(exptGrdDtlLow.getExRat());
                    BigDecimal splNum = new BigDecimal(exptGrdDtlLow.getSplNum());
                    BigDecimal ttlPri = uniPri.multiply(exRat).multiply(splNum).setScale(2, BigDecimal.ROUND_HALF_UP);
                    qotInf.setTtlPri(ttlPri);

                    qotInf.setTendDlvDte(exptGrdDtlLow.getTendDlvDte());
                    qotInf.setMatBnd(exptGrdDtlLow.getMatBnd());
                    qotInf.setGrdOrd("" + exptGrdDtlLow.getGrdOrd());
                    qotInf.setGrdRsn(exptGrdDtlLow.getGrdRsn());

                    return qotInf;
                }).filter(q -> q != null).collect(Collectors.toList());
                qotInfs.sort((dtl1, dtl2) -> {
                    String ord1 = dtl1.getGrdOrd();
                    String ord2 = dtl2.getGrdOrd();
                    if (StringUtils.isEmpty(ord1) && StringUtils.isEmpty(ord2)
                            && !Integer.valueOf(ord1).equals(0) && !Integer.valueOf(ord2).equals(0)) {
                        return Integer.valueOf(ord1).compareTo(Integer.valueOf(ord2));
                    }
                    BigDecimal uniPri1 = new BigDecimal(dtl1.getUniPri());
                    BigDecimal exRat1 = new BigDecimal(dtl1.getExRat());
                    BigDecimal price1 = uniPri1.multiply(exRat1);
                    BigDecimal uniPri2 = new BigDecimal(dtl2.getUniPri());
                    BigDecimal exRat2 = new BigDecimal(dtl2.getExRat());
                    BigDecimal price2 = uniPri2.multiply(exRat2);

                    return price1.compareTo(price2);
                });
                dtl.setQotInfs(qotInfs);
                return dtl;
            }).collect(Collectors.toList());
            grdInfUniLowIoDto.setList(list);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, grdInfUniLowIoDto);
        } else {
            String errMsg = "展示评标信息 - 暂不支持该评标类型 - grdRul[" + grdRul + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto postGrdInf(IoGrdInfLowPostDto dto) throws Exception {

        logger.info("提交最低价评标信息");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        Integer userId = userinfo.getUserid();

        if (userId == null || userId.equals(0)) {
            String errMsg = "鉴权失败 - userId为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        logger.info("1. 参数校验");
        if (dto == null) {
            String errMsg = "参数校验 - 不通过 - dto不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Long grdId = dto.getGrdId();
        if (grdId == null || grdId.equals(0l)) {
            String errMsg = "参数校验 - 不通过 - grdId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (dto.getList() == null || dto.getList().isEmpty()) {
            String errMsg = "参数校验 - 不通过 - list不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOnExptGrdInf grdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(grdId);
        if (grdInf == null || !Constants.EFF_FLG_ON.equals(grdInf.getEffFlg())) {
            String errMsg = "提交最低价评标信息 - 失败 - 该评标记录不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (!Constants.GRD_STS_GRADDING.equals(grdInf.getProjGrdSts())) {
            String errMsg = "提交最低价评标信息 - 专家评标状态不为评标中 - gradSts[" + grdInf.getProjGrdSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Long projId = grdInf.getProjId();
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        String projSts = bidProjOn.getProjSts();
        if (!Constants.BID_PROJ_ON_STS_GRADING.equals(projSts)) {
            String errMsg = "提交最低价评标信息 - 项目状态不为评标中 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        dto.getList().stream().forEach(grdInfDtl -> {
            BidProjOnExptGrdDtlLow bidProjOnExptGrdDtlLow = new BidProjOnExptGrdDtlLow();
            bidProjOnExptGrdDtlLow.setId(grdInfDtl.getId());
            bidProjOnExptGrdDtlLow.setModUsr(userinfo.getRealname());
            bidProjOnExptGrdDtlLow.setModTim(DateUtils.getCurrentTimeStamp());
            bidProjOnExptGrdDtlLow.setGrdOrd(grdInfDtl.getGrdOrd());
            bidProjOnExptGrdDtlLow.setGrdRsn(grdInfDtl.getGrdRsn());
            bidProjOnExptGrdDtlLow.setIsRcmd(grdInfDtl.getIsRcmd());

            int effRows = bidProjOnExptGrdDtlLowMapper.updateByPrimaryKeySelective(bidProjOnExptGrdDtlLow);
            if (effRows != 1) {
                String errMsg = "提交最低价评标信息 - 失败 - 受影响行数不为1, " + bidProjOnExptGrdDtlLow;
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        });
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }
}
