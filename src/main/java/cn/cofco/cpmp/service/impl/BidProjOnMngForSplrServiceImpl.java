package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.factory.MatFactory;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOnMngForSplrService;
import cn.cofco.cpmp.service.ISplrService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;
import cn.cofco.cpmp.utils.checkers.BidProjOnMngForSplrChecker;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Xujy on 2017/5/17.
 * for [线上招标项目管理 - 供应商 实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class BidProjOnMngForSplrServiceImpl implements IBidProjOnMngForSplrService {

    private static Logger logger = LoggerManager.getBidOnlineMngLog();

    @Resource
    private BidProjOnSplrTendInfMapper bidProjOnSplrTendInfMapper;

    @Resource
    private BidProjOnMapper bidProjOnMapper;

    @Resource
    private BidProjOnMatDtlMapper bidProjOnMatDtlMapper;

    @Resource
    private BidProjOnSplrInvtMapper bidProjOnSplrInvtMapper;

    @Resource
    private BidProjOnSplrQotInfMapper bidProjOnSplrQotInfMapper;

    @Resource
    private BidProjOnSplrQotDtlMapper bidProjOnSplrQotDtlMapper;

    @Resource
    private ISplrService splrService;

    @Resource
    private BidProjOnWinDtlMapper bidProjOnWinDtlMapper;

    @Resource
    private AtchMapper atchMapper;

    @Resource
    private MatFactory matFactory;

    @Resource
    private SplrWdotMapper splrWdotMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto appBid(IoBidProjOnAppBidDto dto) throws Exception {

        logger.info("供应商投标申请, request dto -> " + dto);

        CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
        Long splrId = currentSplrUserInfo.getSplrId();

        // 1. 参数基础校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数基础校验 - 开始");
        String checkRst = BidProjOnMngForSplrChecker.checkArgsForAppBid(dto);
        if (!"".equals(checkRst)) {
            logger.error("参数基础校验 - 不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数基础校验 - 结束，duration[" + durationA + "ms]");


        // 2. 参数业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 参数业务校验 - 开始");
        logger.info("2.1 参数业务校验 - 根据projId查询线上招标项目表, 验证项目是否存在");
        Long projId = dto.getProjId();
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "参数业务校验 - 失败 - 该线上招标项目不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (!Constants.BID_PROJ_ON_STS_BIDDING.equals(bidProjOn.getProjSts())) {
            String errMsg = "参数业务校验 - 失败 - 该线上招标项目不在招标中";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        logger.info("2.2 参数业务校验 - 验证是否已经过了项目投标时间");
        Timestamp bidEndTim = bidProjOn.getBidEndTim();
        if (bidEndTim != null) {
            Timestamp now = DateUtils.getCurrentTimeStamp();
            if (now.after(bidEndTim)) {
                String errMsg = "参数业务校验 - 失败 - 该线上招标项目招标截止";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }

        logger.info("2.3 参数业务校验 - 保证金验证");
        if (Constants.DPST_FLG_ON.equals(bidProjOn.getDpstFlg())) {
            String dpstPic = dto.getDpstPic();
            if (StringUtils.isEmpty(dpstPic)) {
                String errMsg = "参数业务校验 - 失败 - 该线上招标项目投标保证金不得为空";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }

        logger.info("2.4 参数业务校验 - 根据splrId, projId, qotCnt, effFlg信息查询供应商线上项目投标信息表, 进行排重校验 - 开始");
        BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
        bidProjOnSplrTendInf.setSplrId(splrId);
        bidProjOnSplrTendInf.setProjId(dto.getProjId());
        bidProjOnSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf, 1, 1);
        List<BidProjOnSplrTendInf> splrTendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);
        if (splrTendInfs != null && splrTendInfs.size() > 0) {
            BidProjOnSplrTendInf tendInf = splrTendInfs.get(0);
            if (Constants.BID_DOC_STS_REJECTED.equals(tendInf.getBidDocSts())) {
                String errMsg = "参数业务校验 - 查询供应商线上项目投标信息表, 进行排重校验 - 失败 - 该标书已被拒绝";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            } else {
                String errMsg = "参数业务校验 - 查询供应商线上项目投标信息表, 进行排重校验 - 失败 - 该标书已存在";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }

        BidProjOnSplrInvt splrInvt = null;
        if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOn.getBidRngTyp())) {
            logger.info("2.5 参数业务校验 - 若为定向邀标，验证该供应商是否在供应商邀请范围内");
            BidProjOnSplrInvt bidProjOnSplrInvt = new BidProjOnSplrInvt();
            bidProjOnSplrInvt.setProjId(projId);
            bidProjOnSplrInvt.setSplrId(splrId);
            bidProjOnSplrInvt.setEffFlg(Constants.EFF_FLG_ON);
//            bidProjOnSplrInvt.setBidCntTyp(Constants.BID_CNT_TYP_FST);
            bidProjOnSplrInvt.setBidFlg(Constants.BID_FLG_UNDO);
            Map bidProjOnSplrInvtMap = PageUtils.getQueryCondsMap(bidProjOnSplrInvt, 0, 1);
            List<BidProjOnSplrInvt> bidProjOnSplrInvts = bidProjOnSplrInvtMapper.selectByEntity(bidProjOnSplrInvtMap);
            if (bidProjOnSplrInvts == null || bidProjOnSplrInvts.isEmpty()) {
                String errMsg = "参数业务校验 - 若为定向邀标，验证该供应商是否在供应商邀请范围内 - 失败 - 供应商不在邀请范围内";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
            splrInvt = bidProjOnSplrInvts.get(0);
        }

        // 2.2 供应商资质验证
        if (!splrService.checkSplrForQualified(splrId)) {
            String errMsg = "参数业务校验 - 供应商资质校验不通过";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        //2.3 判断该公司下供应商是否已经淘汰
        String aplyOrg = bidProjOn.getOrgId().toString();
        Map sprlWdotMap  = new HashMap();
        sprlWdotMap.put("wdotOrg",aplyOrg);
        sprlWdotMap.put("splrId",splrId);
        sprlWdotMap.put("delFlg",0);
        List<SplrWdot> splrWdots = splrWdotMapper.selectByCondition(sprlWdotMap);
        if(!splrWdots.isEmpty()){
            String errMsg = "参数业务校验 - 供应商已经被该公司淘汰，不能参与此公司项目投标";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 参数业务校验 - 结束，duration[" + durationB + "ms]");

        // 3. entity根据有效值赋值
        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. entity根据有效值赋值 - 开始");
        BeanUtils.copyProperties(bidProjOnSplrTendInf, dto);
        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. entity根据有效值赋值 - 结束，duration[" + durationC + "ms]");

        // 4. 新增供应商线上项目投标信息
        long timeD = DateUtils.currentTimeMillis();
        logger.info("4. 新增供应商线上项目投标信息 - 开始");

        bidProjOnSplrTendInf.setSplrId(splrId);
        bidProjOnSplrTendInf.setQotCnt(0);
        bidProjOnSplrTendInf.setProjNam(bidProjOn.getProjNam());
        bidProjOnSplrTendInf.setCrtUsr(currentSplrUserInfo.getUsrNam());
        bidProjOnSplrTendInf.setCrtTim(DateUtils.getCurrentTimeStamp());
        bidProjOnSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
        bidProjOnSplrTendInf.setBidDocSts(Constants.BID_DOC_STS_APPLIED);

        int effRows = bidProjOnSplrTendInfMapper.insert(bidProjOnSplrTendInf);
        if (effRows != 1) {
            String errMsg = "新增供应商线上项目投标信息 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOn.getBidRngTyp())) {
            splrInvt.setBidFlg(Constants.BID_FLG_DONE);
            splrInvt.setModTim(DateUtils.getCurrentTimeStamp());
            splrInvt.setModUsr(currentSplrUserInfo.getUsrNam());
            bidProjOnSplrInvtMapper.updateByPrimaryKey(splrInvt);
        }

        // 若为定向招标, 更新邀请表
        if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOn.getBidRngTyp())) {
            logger.info("若为定向招标, 更新邀请表");

            splrInvt.setBidFlg(Constants.BID_FLG_DONE);
            splrInvt.setModTim(DateUtils.getCurrentTimeStamp());
            splrInvt.setModUsr(currentSplrUserInfo.getUsrNam());

            effRows = bidProjOnSplrInvtMapper.updateByPrimaryKey(splrInvt);
            if (effRows != 1) {
                String errMsg = "定向招标, 更新邀请 - 失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        }


        long durationD = DateUtils.currentTimeMillis() - timeD;
        logger.info("4. 新增供应商线上项目投标信息 - 结束，duration[" + durationD + "ms]");

        return OutputDtoUtil.setOutputDto(true, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getBidInfByIoQueryBidProjOnSplrTendInfDto(IoQueryBidProjOnSplrTendInfDto dto, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {

        logger.info("根据条件分页查询已投标的线上招标项目");

        CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
        Long splrId = currentSplrUserInfo.getSplrId();
            // 参数处理
            String bidTimBgn = dto.getBidTimBgn();
            if (!StringUtils.isEmpty(bidTimBgn)) {
                dto.setBidTimBgn(bidTimBgn + " 00:00:00");
            } else {
                dto.setBidTimBgn(null);
            }

            String bidTimEnd = dto.getBidTimEnd();
            if (!StringUtils.isEmpty(bidTimEnd)) {
                dto.setBidTimEnd(bidTimEnd + " 00:00:00");
            } else {
                dto.setBidTimEnd(null);
            }
            if (pageNo == null || pageNo <= 0) {
                pageNo = 1;
            }

            if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
                pageSize = Constants.PAGE_SIZE;
            }

            Integer start = (pageNo - 1) * pageSize + 1;

            Integer to = pageNo * pageSize;

            dto.setSplrId(splrId);

            Map map = PageUtils.getQueryCondsMap(dto, start, to);
            map.put("IsMCompany",IsMCompany);
            List<BidProjOnSplrTendInfIoDto> list = new ArrayList<>();
            List<BidProjOnSplrTendInf> bidProjOnSplrTendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);

            for (BidProjOnSplrTendInf b : bidProjOnSplrTendInfs) {
                BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(b.getProjId());
                BidProjOnSplrTendInfIoDto bidProjOnSplrTendInfIoDto = new BidProjOnSplrTendInfIoDto();
                BeanUtils.copyProperties(bidProjOnSplrTendInfIoDto, b);
                BeanUtils.copyProperties(bidProjOnSplrTendInfIoDto, bidProjOn);
                list.add(bidProjOnSplrTendInfIoDto);
            }

            Integer count = bidProjOnSplrTendInfMapper.countByMap(map);

            PagedResult result = new PagedResult(list, count);

            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getPagedResultByIoQueryBidProjOnProjInfForSplrDto(IoQueryBidProjOnProjInfForSplrDto dto, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {
        logger.info("根据条件分页查询线上招标项目列表");

            // 参数处理
            String projSts = dto.getProjSts();
            if (StringUtils.isEmpty(projSts)) {
                dto.setProjSts(null);
                dto.setProjStses(Constants.BID_PROJ_ON_STS_LIST_FOR_SPLR);
            } else {
                dto.setProjStses(null);
            }

            String bidEndTimFrom = dto.getBidEndTimFrom();
            if (!StringUtils.isEmpty(bidEndTimFrom)) {
                dto.setBidEndTimFrom(bidEndTimFrom + " 00:00:00");
            } else {
                dto.setBidEndTimFrom(null);
            }

            String bidEndTimTo = dto.getBidEndTimTo();
            if (!StringUtils.isEmpty(bidEndTimTo)) {
                dto.setBidEndTimTo(bidEndTimTo + " 00:00:00");
            } else {
                dto.setBidEndTimTo(null);
            }
            if (pageNo == null || pageNo <= 0) {
                pageNo = 1;
            }

            if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
                pageSize = Constants.PAGE_SIZE;
            }

            Integer start = (pageNo - 1) * pageSize + 1;
            Integer to = pageNo * pageSize;

            // 必须为已发布状态
            dto.setBidNtcPubFlg(Constants.BID_NTC_PUB_FLG_ON);

            // 若为定向招标
            if (Constants.BID_RNG_TYP_VECTORING.equals(dto.getBidRngTyp())) {
                Map map = PageUtils.getQueryCondsMap(dto, start, to);
                CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
                Long splrId = currentSplrUserInfo.getSplrId();
                map.put("splrId", splrId);
                map.put("desc", 1);
                map.put("IsMCompany",IsMCompany);
                List<BidProjOn> list = bidProjOnMapper.selectByMapOfVctInvt(map);
                Integer count = bidProjOnMapper.countByMapOfVctInvt(map);
                PagedResult result = new PagedResult(list, count);
                return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
            }
            // 非定向招标
            else {
                Map map = PageUtils.getQueryCondsMap(dto, start, to);
                map.put("desc", 1);
                map.put("IsMCompany",IsMCompany);
                List<BidProjOn> list = bidProjOnMapper.selectByMap(map);
                Integer count = bidProjOnMapper.countByMap(map);
                PagedResult result = new PagedResult(list, count);
                return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
            }
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto qot(IoBidProjOnQotDto dto) throws Exception {

        logger.info("线上招标项目供应商报价, request: " + dto);

        // 1. 参数基础校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数基础校验 - 开始");
        String checkRst = BidProjOnMngForSplrChecker.checkArgsForQot(dto);
        if (!"".equals(checkRst)) {
            logger.error("参数基础校验 - 不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数基础校验 - 结束，duration[" + durationA + "ms]");


        // 2. 参数业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2 参数业务校验 - 开始");

        // 2.1 TODO 黑名单校验
        logger.info("2.1 参数业务校验 - 验证供应商是否在黑名单内");
        // 2.2 TODO 淘汰校验
        logger.info("2.2 参数业务校验 - 验证供应商是否被该工厂淘汰");
        // 2.3 TODO 资质文件必填栏位有效性校验（a. 是否有值; b. 是否过期）
        logger.info("2.3 参数业务校验 - 验证供应商资质是否有问题");

        logger.info("2.4 参数业务校验 - 根据bidId查询线上招标项目供应商投标信息表");

        CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
        Long splrId = currentSplrUserInfo.getSplrId();
        Long bidId = dto.getBidId();
        BidProjOnSplrTendInf bidProjOnSplrTendInf = bidProjOnSplrTendInfMapper.selectByPrimaryKey(bidId);
        if (bidProjOnSplrTendInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnSplrTendInf.getEffFlg())) {
            String errMsg = "参数业务校验 - 根据bidId查询线上招标项目供应商投标信息表 - 失败 - 无投标信息";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        logger.info("2.5 参数业务校验 - 校验供应商投标信息状态");
        if (!Constants.BID_DOC_STS_ACCEPTED.equals(bidProjOnSplrTendInf.getBidDocSts())) {
            String errMsg = "参数业务校验 - 校验供应商投标信息状态 - 失败 - 投标信息待审核或审核拒绝";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        logger.info("2.6 参数业务校验 - 根据projId查询线上招标项目信息");
        Long projId = bidProjOnSplrTendInf.getProjId();
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "参数业务校验 - 根据projId查询线上招标项目信息 - 失败 - 无线上招标项目信息";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        logger.info("2.7 参数业务校验 - 校验项目状态");
        String projSts = bidProjOn.getProjSts();
        if (!Constants.BID_PROJ_ON_STS_BIDDING.equals(projSts) && !Constants.BID_PROJ_ON_STS_QOTING2.equals(projSts)) {
            String errMsg = "参数业务校验 - 校验项目状态 - 失败 - 项目状态不为投标中且不为二次报价中";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        logger.info("2.8 参数业务校验 - 校验报价时间是否超过报价截止时间");
        // 招标中
        Timestamp now = DateUtils.getCurrentTimeStamp();
        if (Constants.BID_PROJ_ON_STS_BIDDING.equals(projSts)) {
            Timestamp qotEndTim = bidProjOn.getBidEndTim();
            if (now.after(qotEndTim)) {
                String errMsg = "参数业务校验 - 失败 - 招标报价已截止";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }
        // 二次竞价报价中
        else if (Constants.BID_PROJ_ON_STS_QOTING2.equals(projSts)) {
            Timestamp qotEndTim = bidProjOn.getQot2EndTim();
            if (now.after(qotEndTim)) {
                String errMsg = "参数业务校验 - 失败 - 二次竞价报价已截止";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }

        // 2.9 若为2次报价，验证该供应商是否在二次竞价供应商邀请范围内
        if (Constants.BID_PROJ_ON_STS_QOTING2.equals(projSts)) {
            logger.info("2.9 参数业务校验 - 若为二次竞价，验证该供应商是否在二次报价供应商邀请范围内");
            BidProjOnSplrInvt bidProjOnSplrInvt = new BidProjOnSplrInvt();
            bidProjOnSplrInvt.setProjId(projId);
            bidProjOnSplrInvt.setSplrId(splrId);
            bidProjOnSplrInvt.setEffFlg(Constants.EFF_FLG_ON);
            bidProjOnSplrInvt.setBidCntTyp(Constants.BID_CNT_TYP_SCD);
//            bidProjOnSplrInvt.setBidFlg(Constants.BID_FLG_DONE);
            Map bidProjOnSplrInvtMap = PageUtils.getQueryCondsMap(bidProjOnSplrInvt, 0, 0);
            List<BidProjOnSplrInvt> bidProjOnSplrInvts = bidProjOnSplrInvtMapper.selectByEntity(bidProjOnSplrInvtMap);
            if (bidProjOnSplrInvts == null || bidProjOnSplrInvts.isEmpty()) {
                String errMsg = "参数业务校验 - 若为二次竞价，验证该供应商是否在二次报价供应商邀请范围内 - 失败 - 供应商不在二次竞价邀请范围内";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2 参数业务校验 - 结束，duration[" + durationB + "ms]");


        // 3. 数据持久化
        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");

        logger.info("3.1 数据持久化 - 新增线上招标项目供应商报价信息");
        // 查询当前报价信息
        BidProjOnSplrQotInf splrQotInfEntity = new BidProjOnSplrQotInf();
        splrQotInfEntity.setProjId(projId);
        splrQotInfEntity.setBidId(bidId);
        if (Constants.BID_PROJ_ON_STS_BIDDING.equals(projSts)) {
            splrQotInfEntity.setQotTyp(Constants.BID_CNT_TYP_FST);
        } else {
            splrQotInfEntity.setQotTyp(Constants.BID_CNT_TYP_SCD);
        }
        Map map = PageUtils.getQueryCondsMap(splrQotInfEntity, 1, 1);
        List<BidProjOnSplrQotInf> bidProjOnSplrQotInfs = bidProjOnSplrQotInfMapper.selectByMap(map);
        Long splrQotInfId = 0L;
        if (bidProjOnSplrQotInfs == null || bidProjOnSplrQotInfs.isEmpty()) {
            BidProjOnSplrQotInf bidProjOnSplrQotInf = new BidProjOnSplrQotInf();
            BeanUtils.copyProperties(bidProjOnSplrQotInf, dto);
            bidProjOnSplrQotInf.setSplrId(bidProjOnSplrTendInf.getSplrId());
            bidProjOnSplrQotInf.setCrtTim(DateUtils.getCurrentTimeStamp());
            bidProjOnSplrQotInf.setCrtUsr(currentSplrUserInfo.getUsrNam());
            bidProjOnSplrQotInf.setEffFlg(Constants.EFF_FLG_ON);
            bidProjOnSplrQotInf.setProjId(projId);
            if (Constants.BID_PROJ_ON_STS_BIDDING.equals(projSts)) {
                bidProjOnSplrQotInf.setQotTyp(Constants.BID_CNT_TYP_FST);
            } else {
                bidProjOnSplrQotInf.setQotTyp(Constants.BID_CNT_TYP_SCD);
            }
            int effRows = bidProjOnSplrQotInfMapper.insert(bidProjOnSplrQotInf);
            if (effRows != 1) {
                String errMsg = "新增线上招标项目供应商报价信息失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            splrQotInfId = bidProjOnSplrQotInf.getId();
        } else {
            BidProjOnSplrQotInf bidProjOnSplrQotInf = bidProjOnSplrQotInfs.get(0);
            bidProjOnSplrQotInf.setModTim(DateUtils.getCurrentTimeStamp());
            bidProjOnSplrQotInf.setModUsr(currentSplrUserInfo.getUsrNam());
            int effRows = bidProjOnSplrQotInfMapper.updateByPrimaryKeySelective(bidProjOnSplrQotInf);
            if (effRows != 1) {
                String errMsg = "更新线上招标项目供应商报价信息失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            splrQotInfId = bidProjOnSplrQotInf.getId();
        }

        // 2.2 更新报价附件
        // 2.2.1 得到附件关联ID
        ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_QOT);
        if (comParm == null) {
            String errMsg = "更新线上招标项目供应商报价信息失败 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_QOT + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        String atchPrefix = comParm.getParmVal();
        String refId = atchPrefix + splrQotInfId;

        atchMapper.deleteByRefId(refId);

        List<IoAtchDto> atchDtos = dto.getAtchDtos();
        if (atchDtos != null && !atchDtos.isEmpty()) {
            for (IoAtchDto atchDto : atchDtos) {
                Atch atch = new Atch();
                BeanUtils.copyProperties(atch, atchDto);
                atch.setRefId(refId);
                atch.setEffFlg(Constants.EFF_FLG_ON);
                atch.setCrtTim(DateUtils.getCurrentTimeStamp());
                atch.setCrtUsr(currentSplrUserInfo.getUsrNam());
                int effRows = atchMapper.insert(atch);
                if (effRows != 1) {
                    String errMsg = "更新线上招标项目供应商报价信息 - 新增附件 - 失败 - 受影响行数不为1";
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
            }
        }

        logger.info("3.2 数据持久化 - 将报价ID赋值");
        if (Constants.BID_PROJ_ON_STS_BIDDING.equals(projSts)) {
            bidProjOnSplrTendInf.setQotId(splrQotInfId);
        } else {
            bidProjOnSplrTendInf.setQot2Id(splrQotInfId);
        }
        bidProjOnSplrTendInf.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOnSplrTendInf.setModUsr(currentSplrUserInfo.getUsrNam());
        int effRows = bidProjOnSplrTendInfMapper.updateByPrimaryKey(bidProjOnSplrTendInf);
        if (effRows != 1) {
            String errMsg = "更新线上招标项目供应商投标信息失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 3.2 新增线上招标项目供应商报价明细
        logger.info("3.3 数据持久化 - 更新线上招标项目供应商报价明细");
        // 3.2.1 删除原有报价明细
        BidProjOnSplrQotDtl qotDtlEntity = new BidProjOnSplrQotDtl();
        qotDtlEntity.setQotId(splrQotInfId);
        map = PageUtils.getQueryCondsMap(qotDtlEntity, 0, 0);
        List<BidProjOnSplrQotDtl> bidProjOnSplrQotDtls = bidProjOnSplrQotDtlMapper.selectByMap(map);
        if (bidProjOnSplrQotDtls != null && bidProjOnSplrQotDtls.size() > 0) {
            for (BidProjOnSplrQotDtl qotDtl : bidProjOnSplrQotDtls) {
                qotDtl.setEffFlg(Constants.EFF_FLG_OFF);
                qotDtl.setModTim(DateUtils.getCurrentTimeStamp());
                qotDtl.setModUsr(currentSplrUserInfo.getUsrNam());
                effRows = bidProjOnSplrQotDtlMapper.updateByPrimaryKeySelective(qotDtl);
                if (effRows != 1) {
                    String errMsg = "删除原有报价明细失败 - 受影响行数不为1";
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
            }
        }

        // 3.2.2 新增报价明细
        List<IoBidProjOnQotDtlDto> dtls = dto.getDtls();
        for (IoBidProjOnQotDtlDto dtlDto : dtls) {
            BidProjOnSplrQotDtl bidProjOnSplrQotDtl = new BidProjOnSplrQotDtl();
            BeanUtils.copyProperties(bidProjOnSplrQotDtl, dtlDto);
            bidProjOnSplrQotDtl.setProjId(bidProjOnSplrTendInf.getProjId());
            bidProjOnSplrQotDtl.setSplrId(bidProjOnSplrTendInf.getSplrId());
            bidProjOnSplrQotDtl.setCrtTim(DateUtils.getCurrentTimeStamp());
            bidProjOnSplrQotDtl.setCrtUsr(currentSplrUserInfo.getUsrNam());
            bidProjOnSplrQotDtl.setEffFlg(Constants.EFF_FLG_ON);
            bidProjOnSplrQotDtl.setQotId(splrQotInfId);
            bidProjOnSplrQotDtl.setUntPriEcrp(CryptUtils.encrypt(dtlDto.getPrice().toString(), bidProjOn.getOpenKey()));
            effRows = bidProjOnSplrQotDtlMapper.insert(bidProjOnSplrQotDtl);
            if (effRows != 1) {
                String errMsg = "新增线上招标项目供应商报价明细失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getQotInf(Long id) throws Exception {
        logger.info("根据报价ID查询报价信息, id[{}]", id);

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (id == null) {
            String errMsg = "参数校验 - 不通过 - 报价ID不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        BidProjOnSplrQotInf entity = bidProjOnSplrQotInfMapper.selectByPrimaryKey(id);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "根据报价ID查询报价信息 - 失败 - 无数据 - id[" + id + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 获取物料报价且解密
        Long projId = entity.getProjId();
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        BidProjOnSplrQotDtl bidProjOnSplrQotDtl = new BidProjOnSplrQotDtl();
        bidProjOnSplrQotDtl.setQotId(id);
        bidProjOnSplrQotDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOnSplrQotDtl, 0, 0);
        List<BidProjOnSplrQotDtl> bidProjOnSplrQotDtls = bidProjOnSplrQotDtlMapper.selectByMap(map);
        for (BidProjOnSplrQotDtl qotDtl : bidProjOnSplrQotDtls) {
            String priceDecrypt = CryptUtils.decrypt(qotDtl.getUntPriEcrp(), bidProjOn.getOpenKey());
            qotDtl.setUntPri(new BigDecimal(priceDecrypt));
        }

        BidProjOnSplrQotIoInfDto dto = new BidProjOnSplrQotIoInfDto();
        BeanUtils.copyProperties(dto, entity);
        dto.setQotDtls(bidProjOnSplrQotDtls);

        ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_QOT);
        if (comParm == null) {
            String errMsg = "根据报价ID查询报价信息 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_QOT + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        String atchPrefix = comParm.getParmVal();
        String refId = atchPrefix + entity.getId();
        Atch atch = new Atch();
        atch.setRefId(refId);

        map = PageUtils.getQueryCondsMap(atch, 0, 0);

        List<Atch> atches = atchMapper.selectByMap(map);

        dto.setAtches(atches);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, dto);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getQotHis(IoGetQotHisDto dto, Integer pageNo, Integer pageSize) throws Exception {
        logger.info("获取线上招标项目供应商报价历史");

        // 参数处理
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize + 1;
        Integer to = pageNo * pageSize;

        Map map = PageUtils.getQueryCondsMap(dto, start, to);
        CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
        Long splrId = currentSplrUserInfo.getSplrId();
        map.put("splrId", splrId);
        map.put("effFlg", Constants.EFF_FLG_ON);

        List<BidProjOnSplrQotInf> list = bidProjOnSplrQotInfMapper.selectByMap(map);

        Integer count = bidProjOnSplrQotInfMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getQotOrder(Long projId) throws Exception {
        logger.info("查询实时竞价供应商报价排名及最低最高价");

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if(projId==null||projId==0L){
            String errMsg = "参数校验 - projId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        logger.info("参数校验 -结束");
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("业务校验 -开始");
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || !Constants.EFF_FLG_ON.equals(bidProjOn.getEffFlg())) {
            String errMsg = "查询实时竞价供应商报价排名及最低最高价 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        String projSts = bidProjOn.getProjSts();
        if (!Constants.BID_PROJ_OFF_STS_APP_BIDDING.equals(projSts)) {
            String errMsg = "查询实时竞价供应商报价排名及最低最高价 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.INNER_ERR, errMsg);
        }
      /*  BidProjOnSplrQotInf bidProjOnSplrQotInf = bidProjOnSplrQotInfMapper.selectByPrimaryKey(projId);
        if (bidProjOnSplrQotInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnSplrQotInf.getEffFlg())) {
            String errMsg = "查询实时竞价供应商报价排名及最低最高价 - 失败 - 根据projId查询报价信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }*/
        String openKey = bidProjOn.getOpenKey();
        if (StringUtils.isEmpty(openKey)) {
            String errMsg = "查询实时竞价供应商报价排名及最低最高价 - 失败 - 开标密钥为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOnMatDtl bidProjOnMatDtl = new BidProjOnMatDtl();
        bidProjOnMatDtl.setProjId(projId);
        bidProjOnMatDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOnMatDtl,0,0);
        List<BidProjOnMatDtl> bidProjOnMatDtls = bidProjOnMatDtlMapper.selectByMap(map);
        if(bidProjOnMatDtls==null||bidProjOnMatDtls.isEmpty()){
            String errMsg = "查询实时竞价供应商报价排名及最低最高价 - 失败 - 根据项目ID查询项目物料信息无数据";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");
        logger.info("判断是否有报价供应商");
        List<String> bidDocStses = Arrays.asList(new String[]{Constants.BID_DOC_STS_ACCEPTED, Constants.BID_DOC_STS_AWD, Constants.BID_DOC_STS_UNAWD});
        BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
        bidProjOnSplrTendInf.setProjId(projId);
        bidProjOnSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
        Map map1 = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf, 0, 0);
        map1.put("bidDocStses", bidDocStses);
        map1.put("qotIdNotNul", "true");
        List<BidProjOnSplrTendInf> tendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);

        logger.info("开始判断最高价最低价");
        BigDecimal price = new BigDecimal("0.00");
        List<QotOrderIoDto> qotOrderIoDtos = new ArrayList<>();
        //如果没有供应商报价，最高价最低价都显示0
        if (tendInfs == null || tendInfs.isEmpty()) {
            QotOrderIoDto qotOrderIoDto = new QotOrderIoDto();
            qotOrderIoDto.setMinPrice(price.toString());
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, qotOrderIoDto);
        }else {
            for(BidProjOnMatDtl matDtl :bidProjOnMatDtls){
                QotOrderIoDto qotOrderIoDto = new QotOrderIoDto();
                BigDecimal minPrice = price;
                BigDecimal maxPrice = price;
                String minPriceStr = "";
                String maxPriceStr = "";
                BidProjOnSplrQotDtl bidProjOnSplrQotDtl = new BidProjOnSplrQotDtl();
                bidProjOnSplrQotDtl.setEffFlg(Constants.EFF_FLG_ON);
                bidProjOnSplrQotDtl.setMatId(matDtl.getId());
                Map qotDtlMap = PageUtils.getQueryCondsMap(bidProjOnSplrQotDtl,0,0);
                List<BidProjOnSplrQotDtl> bidProjOnSplrQotDtls = bidProjOnSplrQotDtlMapper.selectByMap(qotDtlMap);
                for(BidProjOnSplrQotDtl qotDtl :bidProjOnSplrQotDtls){
                    String priceEncrypted = qotDtl.getUntPriEcrp();
                    String exRat = qotDtl.getExRat();
                    if (StringUtils.isEmpty(priceEncrypted)) {
                        continue;
                    }
                    String priceStrDecrypted = CryptUtils.decrypt(priceEncrypted, openKey);
                    if (StringUtils.isEmpty(priceStrDecrypted)) {
                        continue;
                    }
                    BigDecimal priceDecrypted = new BigDecimal(priceStrDecrypted).multiply(new BigDecimal(exRat));
                    if ((priceDecrypted.compareTo(new BigDecimal(0))>0)) {
                        if(minPrice.compareTo(new BigDecimal(0))==0){
                            minPrice = priceDecrypted;
                            minPriceStr = priceStrDecrypted+'('+qotDtl.getCurrTyp()+')';
                        }
                        if(priceDecrypted.compareTo(maxPrice)>0) {
                            maxPrice = priceDecrypted;
                            maxPriceStr = priceStrDecrypted+'('+qotDtl.getCurrTyp()+')';

                        }
                        if(priceDecrypted.compareTo(minPrice)<0){
                            minPriceStr = priceStrDecrypted+'('+qotDtl.getCurrTyp()+')';
                            minPrice = priceDecrypted;
                        }
                    }
                }
                qotOrderIoDto.setMatId(matDtl.getId());
                qotOrderIoDto.setMinPrice(minPriceStr);
                qotOrderIoDto.setMaxPrice(maxPriceStr);
                qotOrderIoDtos.add(qotOrderIoDto);
            }
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, qotOrderIoDtos);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewProjRstDtl(Long projId) throws Exception {

        CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
        Long splrId = currentSplrUserInfo.getSplrId();

        logger.info("供应商查看网上竞价项目招标结果, projId[{}], splrId[{}]", projId, splrId);

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (projId == null || projId == 0L) {
            String errMsg = "供应商查看网上竞价项目招标结果 - 参数校验 - 失败 - 项目ID不得为空";
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
            String errMsg = "供应商查看网上竞价项目招标结果 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
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
            String errMsg = "供应商查看网上竞价项目招标结果 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.INNER_ERR, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 查询数据库 - 开始");
        logger.info("3.1 查询该项目下所有物料明细");
        BidProjOnWinDtl bidProjOnWinDtl = new BidProjOnWinDtl();
        bidProjOnWinDtl.setProjId(projId);
        bidProjOnWinDtl.setSplrId(splrId);
        bidProjOnWinDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOnWinDtl, 0, 0);
        List<BidProjOnWinDtl> list = bidProjOnWinDtlMapper.selectByMap(map);
        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 查询数据库 - 结束，duration[" + durationC + "ms]");

        if (list == null || list.isEmpty()) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        } else {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, list);
        }
    }
}
