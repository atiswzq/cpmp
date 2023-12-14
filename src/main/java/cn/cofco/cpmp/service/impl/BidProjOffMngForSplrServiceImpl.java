package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.factory.MatFactory;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOffMngForSplrService;
import cn.cofco.cpmp.service.ISplrService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;
import cn.cofco.cpmp.utils.checkers.BidProjOffMngForSplrChecker;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangt on 2017/5/17.
 * for [线下招标项目管理 - 供应商 实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class BidProjOffMngForSplrServiceImpl implements IBidProjOffMngForSplrService {

    private static Logger logger = LoggerManager.getBidOfflineMngLog();

    @Resource
    private BidProjOffSplrInfMapper bidProjOffSplrInfMapper;

    @Resource
    private BidProjOffMapper bidProjOffMapper;

    @Resource
    private BidProjOffSplrRltMapper bidProjOffSplrRltMapper;

    @Resource
    private ISplrService splrService;

    @Resource
    private AtchMapper atchMapper;

    @Resource
    private MatFactory matFactory;

    @Resource
    private SplrWdotMapper splrWdotMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto appBid(IoBidProjOffAppBidDto dto) throws Exception {

        logger.info("供应商投标申请, dto: " + dto);

        CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
        Long splrId = currentSplrUserInfo.getSplrId();

        // 1. 参数基础校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数基础校验 - 开始");
        String checkRst = BidProjOffMngForSplrChecker.checkArgsForAppBid(dto);
        if (!"".equals(checkRst)) {
            logger.error("参数基础校验 - 不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数基础校验 - 结束，duration[" + durationA + "ms]");


        // 2. 参数业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 参数业务校验 - 开始");
        logger.info("2.1 参数业务校验 - 根据projId查询线下招标项目表, 验证项目是否存在");
        Long projId = dto.getProjId();
        BidProjOff bidProjOff = bidProjOffMapper.selectByPrimaryKey(projId);
        if (bidProjOff == null || Constants.EFF_FLG_OFF.equals(bidProjOff.getEffFlg())) {
            String errMsg = "参数业务校验 - 失败 - 该线下招标项目不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (!Constants.BID_PROJ_OFF_STS_APP_BIDDING.equals(bidProjOff.getProjSts())) {
            String errMsg = "参数业务校验 - 失败 - 该线下招标项目不在招标中";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        logger.info("2.2 参数业务校验 - 验证是否已经过了项目投标时间");
        Timestamp bidEndTim = bidProjOff.getBidEndTim();
        if (bidEndTim != null) {
            Timestamp now = DateUtils.getCurrentTimeStamp();
            if (now.after(bidEndTim)) {
                String errMsg = "参数业务校验 - 失败 - 该线下招标项目招标截止";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }

        logger.info("2.3 参数业务校验 - 保证金验证");
        if (Constants.DPST_FLG_ON.equals(bidProjOff.getDpstFlg())) {
            String dpstPic = dto.getDpstPic();
            if (StringUtils.isEmpty(dpstPic)) {
                String errMsg = "参数业务校验 - 失败 - 该线下招标项目投标保证金不得为空";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }

        logger.info("2.4 参数业务校验 - 根据splrId, projId, qotCnt,  ,进行排重校验 - 开始");
        BidProjOffSplrInf bidProjOffSplrInf = new BidProjOffSplrInf();
        bidProjOffSplrInf.setSplrId(splrId);
        bidProjOffSplrInf.setProjId(dto.getProjId());
        bidProjOffSplrInf.setEffFlg(Constants.EFF_FLG_ON);
        Map mapOfCnt = ReflcUtils.bean2Map(bidProjOffSplrInf);
        int cnt = bidProjOffSplrInfMapper.countByMap(mapOfCnt);
        if (cnt > 0) {
            String errMsg = "参数业务校验 - 查询供应商线下项目投标信息表, 进行排重校验 - 失败 - 该标书已存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOffSplrRlt splrRlt = null;
        if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOff.getBidRngTyp())) {
            logger.info("2.5 参数业务校验 - 若为定向邀标，验证该供应商是否在供应商邀请范围内");
            BidProjOffSplrRlt bidProjOffSplrRlt = new BidProjOffSplrRlt();
            bidProjOffSplrRlt.setProjId(projId);
            bidProjOffSplrRlt.setSplrId(splrId);
            bidProjOffSplrRlt.setEffFlg(Constants.EFF_FLG_ON);
            bidProjOffSplrRlt.setBidFlg(Constants.BID_FLG_UNDO);
            Map bidProjOffSplrRltMap = PageUtils.getQueryCondsMap(bidProjOffSplrRlt, 0, 1);
            List<BidProjOffSplrRlt> bidProjOffSplrRlts = bidProjOffSplrRltMapper.selectByEntity(bidProjOffSplrRltMap);
            if (bidProjOffSplrRlts == null || bidProjOffSplrRlts.isEmpty()) {
                String errMsg = "参数业务校验 - 若为定向邀标，验证该供应商是否在供应商邀请范围内 - 失败 - 供应商不在邀请范围内";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
            splrRlt = bidProjOffSplrRlts.get(0);
            splrRlt.setBidFlg(Constants.BID_FLG_DONE);
            splrRlt.setModTime(DateUtils.getCurrentTimeStamp());
            splrRlt.setModUsr(currentSplrUserInfo.getUsrNam());
            bidProjOffSplrRltMapper.updateByPrimaryKey(splrRlt);
        }

        // 2.2 供应商资质验证
        if (!splrService.checkSplrForQualified(splrId)) {
            String errMsg = "参数业务校验 - 供应商资质校验不通过";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        //2.3 判断该公司下供应商是否已经淘汰
        String aplyOrg = bidProjOff.getOrgId().toString();
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
        BeanUtils.copyProperties(bidProjOffSplrInf, dto);
        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. entity根据有效值赋值 - 结束，duration[" + durationC + "ms]");

        // 4. 新增供应商线下项目投标信息
        long timeD = DateUtils.currentTimeMillis();
        logger.info("4. 新增供应商线下项目投标信息 - 开始");

        // TODO 从session获取当前用户编号
        bidProjOffSplrInf.setSplrId(splrId);
        bidProjOffSplrInf.setProjNam(bidProjOff.getProjNam());
        bidProjOffSplrInf.setCrtUsr(currentSplrUserInfo.getUsrNam());
        bidProjOffSplrInf.setCrtTim(DateUtils.getCurrentTimeStamp());
        bidProjOffSplrInf.setEffFlg(Constants.EFF_FLG_ON);
        bidProjOffSplrInf.setBidDocSts(Constants.BID_DOC_STS_APPLIED);

        int effRows = bidProjOffSplrInfMapper.insert(bidProjOffSplrInf);
        if (effRows != 1) {
            String errMsg = "新增供应商线下项目投标信息 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOff.getBidRngTyp())) {
            splrRlt.setBidFlg(Constants.BID_FLG_DONE);
            splrRlt.setModTime(DateUtils.getCurrentTimeStamp());
            splrRlt.setModUsr(currentSplrUserInfo.getUsrNam());
            bidProjOffSplrRltMapper.updateByPrimaryKey(splrRlt);
        }

        long durationD = DateUtils.currentTimeMillis() - timeD;
        logger.info("4. 新增供应商线下项目投标信息 - 结束，duration[" + durationD + "ms]");

        return OutputDtoUtil.setOutputDto(true, RtnEnum.SUC_OPR);
    }




    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getBidInfByIoQueryBidProjOffSplrInfDto(IoQueryBidProjOffSplrInfDto dto, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {

        logger.info("根据条件分页查询已投标的线下招标项目");
        CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
        Long splrId = currentSplrUserInfo.getSplrId();

        // TODO 鉴权模块

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

        Integer start = (pageNo - 1) * pageSize;

        Integer to = pageNo * pageSize;

        dto.setSplrId(splrId);

        Map map = PageUtils.getQueryCondsMap(dto, start, to);
        map.put("IsMCompany",IsMCompany);

        List<BidProjOffSplrInfIoDto> list = new ArrayList<>();

        List<BidProjOffSplrInf> bidProjOffSplrInfs = bidProjOffSplrInfMapper.selectByMap(map);

        for (BidProjOffSplrInf b : bidProjOffSplrInfs) {
            BidProjOff bidProjOff = bidProjOffMapper.selectByPrimaryKey(b.getProjId());
            BidProjOffSplrInfIoDto bidProjOffSplrInfIoDto = new BidProjOffSplrInfIoDto();
            BeanUtils.copyProperties(bidProjOffSplrInfIoDto, b);
            BeanUtils.copyProperties(bidProjOffSplrInfIoDto, bidProjOff);
            list.add(bidProjOffSplrInfIoDto);
        }

        Integer count = bidProjOffSplrInfMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getPagedResultByIoQueryBidProjOffProjInfForSplrDto(IoQueryBidProjOffProjInfForSplrDto dto, Integer pageNo, Integer pageSize,String IsMCompany) throws Exception {
        logger.info("根据条件分页查询线下招标项目列表");
        CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
        Long splrId = currentSplrUserInfo.getSplrId();

        // 参数处理
        String projSts = dto.getProjSts();
        if (StringUtils.isEmpty(projSts)) {
            dto.setProjSts(null);
            dto.setProjStses(Constants.BID_PROJ_OFF_STS_LIST_FOR_SPLR);
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

        Integer start = (pageNo - 1) * pageSize;
        Integer to = pageNo * pageSize;

        // 必须为已发布状态
//        dto.setBidNtcPubFlg(Constants.BID_NTC_PUB_FLG_ON);

        // 若为定向招标
        if (Constants.BID_RNG_TYP_VECTORING.equals(dto.getBidRngTyp())) {
            Map map = PageUtils.getQueryCondsMap(dto, start, to);
            // TODO 获取SPLR_ID
            map.put("splrId", splrId);
            map.put("IsMCompany",IsMCompany);
            List<BidProjOff> list = bidProjOffMapper.selectByMapOfVctInvt(map);
            Integer count = bidProjOffMapper.countByMapOfVctInvt(map);
            PagedResult result = new PagedResult(list, count);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
        }
        // 非定向招标
        else {
            Map map = PageUtils.getQueryCondsMap(dto, start, pageSize);
            map.put("IsMCompany",IsMCompany);
            List<BidProjOff> list = bidProjOffMapper.selectByEntity(map);
            Integer count = bidProjOffMapper.countByEntity(map);
            PagedResult result = new PagedResult(list, count);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
        }


    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewProjDtl(Long projId) throws Exception {
        logger.info("根据项目ID查询线下招标信息详情");
        CurrentSplrUserInfo currentSplrUserInfo = SplrContextTools.appContext.getBean(CurrentSplrUserInfo.class);
        Long splrId = currentSplrUserInfo.getSplrId();

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
        bidProjOffInfViewIoDto.setAtches(atches);
        bidProjOffInfViewIoDto.setMatTypDesc(matFactory.getMatTypDesc(entity.getMatTyp()));

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, bidProjOffInfViewIoDto);
    }


}
