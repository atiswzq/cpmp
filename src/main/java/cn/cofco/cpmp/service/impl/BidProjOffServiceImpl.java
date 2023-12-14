package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.factory.BidProjOffBpmAppFactory;
import cn.cofco.cpmp.factory.MatFactory;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOffService;
import cn.cofco.cpmp.service.ICodRulInfService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;
import cn.cofco.cpmp.utils.checkers.BidProjOffMngForPchsChecker;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Tao on 2017/5/16.
 */

@Service
@Transactional("transactionManager")
public class BidProjOffServiceImpl implements IBidProjOffService {


    private static Logger logger = LoggerManager.getBidOfflineMngLog();

    @Resource
    private SplrMapper splrMapper;

    @Resource
    private BidProjOffMapper bidProjOffMapper;

    @Resource
    private BidProjOffStsLogMapper bidProjOffStsLogMapper;

    @Resource
    private BidProjOffSplrRltMapper bidProjOffSplrRltMapper;

    @Resource
    private BidProjOffSplrInfMapper bidProjOffSplrInfMapper;

    @Resource
    private BidProjOffBpmAppFactory bidProjOffBpmAppFactory;

    @Resource
    private ICodRulInfService codRulInfService;

    @Resource
    private BidProjOffSplrRstMapper bidProjOffSplrRstMapper;

    @Resource
    private AtchMapper atchMapper;

    @Resource
    private MatFactory matFactory;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getPagedResultByEntity(IoQueryBidProjOffForPchsDto dto, Integer pageNo, Integer pageSize) throws Exception {

        logger.info("获取线下招标项目分页信息");
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer to = pageSize * pageNo;

        Integer start = (pageNo - 1) * pageSize + 1;

        List<CurrentUserInfoFactory> companies = userinfo.getManageCompanies();

        if (companies == null || companies.isEmpty()) {
            String errMsg = "获取线下招标项目分页信息 - 失败 - 该采购员无所辖公司";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.NO_OPRT_AUTH, errMsg);
        }

        List<Long> orgIds = companies
                .stream()
                .map(currentUserInfoFactory -> Long.valueOf(currentUserInfoFactory.getId()))
                .collect(Collectors.toList());

        if (dto.getOrgId() != null && !dto.getOrgId().equals(0L) && !orgIds.stream().anyMatch(orgId -> orgId.equals(dto.getOrgId()))) {
            String errMsg = "获取线下招标项目分页信息 - 失败 - 所查询公司不在所辖范围内";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.NO_OPRT_AUTH, errMsg);
        }

        Map map = PageUtils.getQueryCondsMap(dto, start, to);
        map.put("orgIds", orgIds);
        map.put("effFlg", Constants.EFF_FLG_ON);

        List<BidProjOff> list = bidProjOffMapper.selectByEntity(map);

        Integer count = bidProjOffMapper.countByEntity(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto saveOrSub(HttpServletRequest request, IoBidProjOffDto dto) throws Exception {

        logger.info("保存线下招标项目, IoBidProjOffDto: " + dto);

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = BidProjOffMngForPchsChecker.checkArgsForSaveOrSub(dto);
        if (!"".equals(checkRst)) {
            logger.error("参数校验 - 不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }

        List<CurrentUserInfoFactory> companies = userinfo.getManageCompanies();
        if (companies == null || companies.isEmpty()) {
            logger.error("保存线下招标项目 - 失败 - 该采购员无所辖公司");
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        }
        String deptCode = companies.stream()
                .filter(company -> company.getId().equals(dto.getOrgId().intValue()))
                .findFirst().get().getDept_code();

        if (userinfo.getCompany() == null || StringUtils.isEmpty(userinfo.getCompany().getDept_code())) {
            String errMsg = "保存线下招标项目 - 失败 - 该采购员所属公司为空，无法提交BPM审批";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        String bpmDeptCode = userinfo.getCompany().getDept_code();
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        // 2. entity根据有效值赋值
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. entity根据有效值赋值 - 开始");
        BidProjOffWithBLOBs bidProjOff = new BidProjOffWithBLOBs();
        try {
            BeanUtils.copyProperties(bidProjOff, dto);

        } catch (Exception e) {
            logger.error("反射赋值失败 - e : " + ExceptionUtils.getFullStackTrace(e));
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SYS_ERR);
        }

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. entity根据有效值赋值 - 结束，duration[" + durationB + "ms]");

        // 3. 新增、更新、提交BPM审批
        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 新增、更新、提交BPM审批 - 开始");

        // 新增项目
        if (bidProjOff.getProjId() == null || bidProjOff.getProjId().equals(0l)) {

            //设置项目负责人
            bidProjOff.setProjRsps(realNam);
            //设置项目创建人
            bidProjOff.setCrtUsr(realNam);
            //设置项目创建时间
            bidProjOff.setCrtTim(DateUtils.getCurrentTimeStamp());
            //设置数据生效标识
            bidProjOff.setEffFlg(Constants.EFF_FLG_ON);

            bidProjOff.setProjSts(Constants.BID_PROJ_OFF_STS_EDTING);
            int effRows = bidProjOffMapper.insert(bidProjOff);

            if (effRows != 1) {
                String errMsg = "新增线下招标项目失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            //定向招标供应商信息新增
            if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOff.getBidRngTyp())) {
                String aptSplrIdsStr = dto.getAptSplrIds();
                String[] aptSplrIds = aptSplrIdsStr.split(",");
                for (String aptSplrId : aptSplrIds) {
                    Long splrId = Long.valueOf(aptSplrId);
                    Splr SplrInfo = splrMapper.selectByPrimaryKey(splrId);
                    BidProjOffSplrRlt bidProjOffSplrRlt = new BidProjOffSplrRlt();
                    bidProjOffSplrRlt.setProjId(bidProjOff.getProjId());
                    bidProjOffSplrRlt.setSplrId(splrId);
                    bidProjOffSplrRlt.setSplrNam(SplrInfo.getFullNam());
                    bidProjOffSplrRlt.setSplrTyp(Constants.SPLR_TYP_FLG_ADT);
                    bidProjOffSplrRlt.setBidFlg(Constants.BID_FLG_UNDO);
                    bidProjOffSplrRlt.setCrtUsr(realNam);
                    bidProjOffSplrRlt.setCrtTim(DateUtils.getCurrentTimeStamp());
                    bidProjOffSplrRlt.setEffFlg(Constants.EFF_FLG_ON);
                    bidProjOffSplrRltMapper.insert(bidProjOffSplrRlt);
                }
            }

            // 附件新增
            List<IoAtchDto> atchDtos = dto.getAtchDtos();
            if (atchDtos != null && !atchDtos.isEmpty()) {
                ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_GKBUD);
                if (comParm == null) {
                    String errMsg = "新增线下公开招标项目 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_GKBUD + "]";
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }

                String atchPrefix = comParm.getParmVal();
                String refId = atchPrefix + bidProjOff.getProjId();
                for (IoAtchDto atchDto : atchDtos) {
                    Atch atch = new Atch();
                    BeanUtils.copyProperties(atch, atchDto);
                    atch.setRefId(refId);
                    atch.setEffFlg(Constants.EFF_FLG_ON);
                    atch.setCrtTim(DateUtils.getCurrentTimeStamp());
                    atch.setCrtUsr(realNam);
                    effRows = atchMapper.insert(atch);
                    if (effRows != 1) {
                        String errMsg = "新增线下公开招标项目 - 新增附件 - 失败 - 受影响行数不为1";
                        logger.error(errMsg);
                        throw new RuntimeException(errMsg);
                    }
                }
            }

            // 写入线下招标项目状态记录表
            BidProjOffStsLog bidProjOffStsLog = new BidProjOffStsLog();
            bidProjOffStsLog.setProjId(bidProjOff.getProjId());
            bidProjOffStsLog.setProjNam(bidProjOff.getProjNam());
            bidProjOffStsLog.setProjSts(Constants.BID_PROJ_OFF_STS_EDTING);
            bidProjOffStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
            bidProjOffStsLog.setModUsr(realNam);
            effRows = bidProjOffStsLogMapper.insert(bidProjOffStsLog);

            if (effRows != 1) {
                String errMsg = "写入线下招标项目状态记录表失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

        }
        // 更新项目
        else {

            BidProjOff entity = bidProjOffMapper.selectByPrimaryKey(bidProjOff.getProjId());
            if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
                String errMsg = "更新项目信息失败 - 根据项目ID查询项目信息无数据 - projId[" + bidProjOff.getProjId() + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }

            // 判断项目状态是否为 【编辑中】
            if (!Constants.BID_PROJ_OFF_STS_EDTING.equals(entity.getProjSts())) {
                String errMsg = "更新项目信息失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }

            bidProjOff.setProjRsps(realNam);
            bidProjOff.setModUsr(realNam);
            bidProjOff.setCrtUsr(entity.getCrtUsr());
            bidProjOff.setCrtTim(entity.getCrtTim());
            bidProjOff.setModTim(DateUtils.getCurrentTimeStamp());
            bidProjOff.setEffFlg(Constants.EFF_FLG_ON);
            bidProjOff.setProjSts(Constants.BID_PROJ_OFF_STS_EDTING);
            int effRows = bidProjOffMapper.updateByPrimaryKeyWithBLOBs(bidProjOff);
            if (effRows != 1) {
                String errMsg = "更新线下招标项目失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            bidProjOffSplrRltMapper.deleteByProjId(bidProjOff.getProjId());
            // 定向招标供应商信息更新
            if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOff.getBidRngTyp())) {
                String aptSplrIdsStr = dto.getAptSplrIds();
                if (StringUtils.isEmpty(aptSplrIdsStr)) {
                    String errMsg = "定向邀标时邀请供应商信息不得为空";
                    logger.error(errMsg);
                    return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
                }
                String[] aptSplrIds = aptSplrIdsStr.split(",");
                for (String aptSplrId : aptSplrIds) {
                    Long splrId = Long.valueOf(aptSplrId);
                    Splr splrInfo = splrMapper.selectByPrimaryKey(splrId);
                    BidProjOffSplrRlt bidProjOffSplrRlt = new BidProjOffSplrRlt();
                    bidProjOffSplrRlt.setProjId(bidProjOff.getProjId());
                    bidProjOffSplrRlt.setSplrId(splrId);
                    bidProjOffSplrRlt.setSplrNam(splrInfo.getFullNam());
                    bidProjOffSplrRlt.setSplrTyp(Constants.SPLR_TYP_FLG_ADT);
                    bidProjOffSplrRlt.setModUsr(realNam);
                    bidProjOffSplrRlt.setModTime(DateUtils.getCurrentTimeStamp());
                    bidProjOffSplrRlt.setBidFlg(Constants.BID_FLG_UNDO);
                    bidProjOffSplrRlt.setEffFlg(Constants.EFF_FLG_ON);
                    bidProjOffSplrRltMapper.insert(bidProjOffSplrRlt);
                }
            }

            // 2.2 更新文章附件
            // 2.2.1 得到附件关联ID
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_GKBUD);
            if (comParm == null) {
                String errMsg = "更新线下招标项目信息 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_GKBUD + "]";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            String atchPrefix = comParm.getParmVal();
            String refId = atchPrefix + bidProjOff.getProjId();

            atchMapper.deleteByRefId(refId);

            List<IoAtchDto> atchDtos = dto.getAtchDtos();
            if (atchDtos != null && !atchDtos.isEmpty()) {
                for (IoAtchDto atchDto : atchDtos) {
                    Atch atch = new Atch();
                    BeanUtils.copyProperties(atch, atchDto);
                    atch.setRefId(refId);
                    atch.setEffFlg(Constants.EFF_FLG_ON);
                    atch.setCrtTim(DateUtils.getCurrentTimeStamp());
                    atch.setCrtUsr(realNam);
                    effRows = atchMapper.insert(atch);
                    if (effRows != 1) {
                        String errMsg = "更新线下招标项目信息 - 新增附件 - 失败 - 受影响行数不为1";
                        logger.error(errMsg);
                        throw new RuntimeException(errMsg);
                    }
                }
            }
        }

        // 提交BPM审批
        String subFlg = dto.getSubFlg();
        if (Constants.SUB_FLG_ON.equals(subFlg)) {

            bidProjOff.setProjNbr(codRulInfService.getProjNbr(Constants.PROJ_TYP_GK, deptCode));

            String token = BpmFileUtils.getToken(userinfo.getUsername());
            String rootPath = request.getServletContext().getRealPath("/");
            String basePath = new File(rootPath + "..").getCanonicalPath();

            // 附件上传至BPM
            List<IoAtchDto> atchs = dto.getAtchDtos();
            List<Long> fileIds = new ArrayList<>();
            if (atchs != null && atchs.size() > 0) {
                fileIds = atchs.stream().map(atch -> {
                    String atchPath = atch.getAtchUrl();
                    String atchNam = atch.getAtchNam();
                    String filePath = basePath + File.separator + atchPath;
                    return BpmFileUtils.uploadAttachment(userinfo.getUsername(), token, filePath, atchNam);
                }).collect(Collectors.toList());
            }

            // BPM seqNo 生成
            String bpmSeqNo = BpmUtils.getBpmSeqNo(Constants.PROJ_TYP_GK);
            String bpmBody = bidProjOffBpmAppFactory.getBudBpmBody(bpmSeqNo, bidProjOff, dto.getAptSplrIds(), dto.getAtchDtos());
            String templateCode = bpmDeptCode + Constants.BPM_APP_TYP_GK_BUD_SUFFIX;
            boolean subSucFlg = BpmUtils.subApp(bpmBody, templateCode, "线下公开招标项目立项申请", "", fileIds);
            if (!subSucFlg) {
                String errMsg = "线下公开招标项目立项申请 - 提交BPM审批失败";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            Timestamp now = DateUtils.getCurrentTimeStamp();

            bidProjOff.setBpmBudSeq(bpmSeqNo);
            bidProjOff.setModUsr(realNam);
            bidProjOff.setModTim(now);
            bidProjOff.setEffFlg(Constants.EFF_FLG_ON);
            bidProjOff.setProjSts(Constants.BID_PROJ_OFF_STS_APP_ADTING);
            bidProjOff.setStsUpdTim(DateUtils.getCurrentTimeStamp());
            int effRows = bidProjOffMapper.updateByPrimaryKeySelective(bidProjOff);
            if (effRows != 1) {
                String errMsg = "更新线下招标项目失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            // 写入线下招标项目状态记录表
            BidProjOffStsLog bidProjOffStsLog = new BidProjOffStsLog();
            bidProjOffStsLog.setProjId(bidProjOff.getProjId());
            bidProjOffStsLog.setProjNam(bidProjOff.getProjNam());
            bidProjOffStsLog.setProjSts(Constants.BID_PROJ_OFF_STS_APP_ADTING);
            bidProjOffStsLog.setStsUpdTim(now);
            bidProjOffStsLog.setModUsr(realNam);

            effRows = bidProjOffStsLogMapper.insert(bidProjOffStsLog);
            if (effRows != 1) {
                String errMsg = "写入线下招标项目状态记录表失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 新增、更新、提交BPM审批 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto del(Long projId) throws Exception {

        logger.info("删除线下招标项目");
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

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
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "删除项目信息失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【编辑中】
        if (!Constants.BID_PROJ_OFF_STS_EDTING.equals(entity.getProjSts())) {
            String errMsg = "删除项目信息失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setEffFlg(Constants.EFF_FLG_OFF);

        int effRows = bidProjOffMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "删除线下招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        //线下招标项目关联供应商信息删除
        BidProjOffSplrRlt bidProjOffSplrRlt = new BidProjOffSplrRlt();
        bidProjOffSplrRlt.setProjId(projId);
        bidProjOffSplrRlt.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOffSplrRlt, 0, 0);
        List<BidProjOffSplrRlt> rlts = bidProjOffSplrRltMapper.selectByEntity(map);
        for (BidProjOffSplrRlt rlt : rlts) {
            rlt.setEffFlg(Constants.EFF_FLG_OFF);
            rlt.setModUsr(realNam);
            rlt.setModTime(DateUtils.getCurrentTimeStamp());
            bidProjOffSplrRltMapper.updateByPrimaryKey(rlt);
        }
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto view(Long projId) throws Exception {

        logger.info("查看线下招标项目详情, projId: " + projId);
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

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
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "查看线下招标项目详情 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
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


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto publish(Long projId) throws Exception {
        logger.info("发布线下招标项目");
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

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
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "发布线下招标项目失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【招标申请审批通过】
        if (!Constants.BID_PROJ_OFF_STS_APP_ADT_PASS.equals(entity.getProjSts())) {
            String errMsg = "发布线下招标项目失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 更新项目状态为 - 招标中
        entity.setBidNtcPubFlg(Constants.BID_NTC_PUB_FLG_ON);
        entity.setProjSts(Constants.BID_PROJ_OFF_STS_APP_BIDDING);
        entity.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setNtcPubTim(DateUtils.getCurrentTimeStamp());

        int effRows = bidProjOffMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新项目状态为失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线下招标项目状态记录表
        ComParm comParmBidProjOffStsBidding = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_BID_PROJ_OFF_STS, Constants.BID_PROJ_OFF_STS_APP_BIDDING);
        if (comParmBidProjOffStsBidding == null || !Constants.EFF_FLG_ON.equals(comParmBidProjOffStsBidding.getEffFlg())
                || StringUtils.isEmpty(comParmBidProjOffStsBidding.getParmVal())) {
            String errMsg = "发布线下招标项目失败 - 通用参数为定义：parmTyp[" + Constants.COM_PARM_TYP_BID_PROJ_OFF_STS + "], parmCod[" + Constants.BID_PROJ_OFF_STS_APP_BIDDING + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        BidProjOffStsLog bidProjOffStsLog = new BidProjOffStsLog();
        bidProjOffStsLog.setProjId(entity.getProjId());
        bidProjOffStsLog.setProjNam(entity.getProjNam());
        bidProjOffStsLog.setProjSts(comParmBidProjOffStsBidding.getParmVal());
        bidProjOffStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        bidProjOffStsLog.setModUsr(realNam);

        effRows = bidProjOffStsLogMapper.insert(bidProjOffStsLog);
        if (effRows != 1) {
            String errMsg = "写入线下招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto cut(IoBidProjOffCutDto dto) throws Exception {
        logger.info("线下招标项目截标");
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        Long projId = dto.getProjId();
        if (projId == null) {
            String errMsg = "参数校验 - 不通过 - projId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        BidProjOffWithBLOBs entity = bidProjOffMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "线下招标项目截标 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【招标中】 或 【二次报价中】，若不符合，则返回异常
        if (!Constants.BID_PROJ_OFF_STS_APP_BIDDING.equals(entity.getProjSts())) {
            String errMsg = "线下招标项目截标 - 失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 更新项目状态为 - 【招标结束】或【二次报价结束】
        String sts = ""; // 后置状态
        if (Constants.BID_PROJ_OFF_STS_APP_BIDDING.equals(entity.getProjSts())) {
            // 若为【招标中】，则更新到【招标结束】状态
            sts = Constants.BID_PROJ_OFF_STS_APP_BID_END;
        }

        entity.setProjSts(sts);
        entity.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());

        int effRows = bidProjOffMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新项目状态 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线下招标项目状态记录表
        BidProjOffStsLog bidProjOffStsLog = new BidProjOffStsLog();
        bidProjOffStsLog.setProjId(entity.getProjId());
        bidProjOffStsLog.setProjNam(entity.getProjNam());
        bidProjOffStsLog.setProjSts(sts);
        bidProjOffStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        bidProjOffStsLog.setModUsr(realNam);

        effRows = bidProjOffStsLogMapper.insert(bidProjOffStsLog);
        if (effRows != 1) {
            String errMsg = "写入线下招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto repeal(IoBidProjOffRepealDto dto) throws Exception {
        logger.info("线下招标项目申请废标, dto: " + dto);
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        Long projId = dto.getProjId();
        if (projId == null) {
            String errMsg = "参数校验 - 不通过 - projId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        BidProjOffWithBLOBs entity = bidProjOffMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "线下招标项目申请废标 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【编辑中:00】 或 【已废标:-20】 或 【申请废标审批中:-21】，若符合，则返回异常
        if (Constants.BID_PROJ_OFF_STS_EDTING.equals(entity.getProjSts()) ||
                Constants.BID_PROJ_OFF_STS_RPL.equals(entity.getProjSts()) ||
                Constants.BID_PROJ_OFF_STS_RPL_ADTING.equals(entity.getProjSts())) {
            String errMsg = "线下招标项目申请废标 - 失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (userinfo.getCompany() == null || StringUtils.isEmpty(userinfo.getCompany().getDept_code())) {
            String errMsg = "线下招标项目申请废标 - 失败 - 该采购员所属公司为空，无法提交BPM审批";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        String bpmDeptCode = userinfo.getCompany().getDept_code();
        logger.info("2. 提交BPM审批");
        // BPM seqNo 生成
        String bpmSeqNo = BpmUtils.getBpmSeqNo(Constants.PROJ_TYP_GK);
        String bpmBody = bidProjOffBpmAppFactory.getRplBpmBody(bpmSeqNo, entity, dto.getAppRplMemo());
        String templateCode = bpmDeptCode + Constants.BPM_APP_TYP_GK_RPL_SUFFIX;
        boolean subSucFlg = BpmUtils.subApp(bpmBody, templateCode, "线下公开招标项目废标申请",
                "", null);
        if (!subSucFlg) {
            String errMsg = "线下公开招标项目废标申请 - 提交BPM审批失败";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3. 更新项目状态为 - 【申请废标审批中:-21】");
        entity.setProjSts(Constants.BID_PROJ_OFF_STS_RPL_ADTING);
        entity.setRplMemo(dto.getAppRplMemo());
        entity.setBpmRplSeq(bpmSeqNo);
        entity.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());

        int effRows = bidProjOffMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新项目状态 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("4. 写入线下招标项目状态记录表");
        BidProjOffStsLog bidProjOffStsLog = new BidProjOffStsLog();
        bidProjOffStsLog.setProjId(entity.getProjId());
        bidProjOffStsLog.setProjNam(entity.getProjNam());
        bidProjOffStsLog.setProjSts(Constants.BID_PROJ_OFF_STS_RPL_ADTING);
        bidProjOffStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        bidProjOffStsLog.setModUsr(realNam);

        effRows = bidProjOffStsLogMapper.insert(bidProjOffStsLog);
        if (effRows != 1) {
            String errMsg = "写入线下招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getBidInfByIoQueryBidProjOffSplrInfDto(IoQueryBidProjOffSplrInfDto dto, Integer pageNo, Integer pageSize) throws Exception {
        logger.info("根据条件分页查询线下招标项目投标信息");

        // 参数处理
        Long projId = dto.getProjId();
        if (projId == null) {
            String errMsg = "根据条件分页查询线下招标项目投标信息 - 失败：projId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

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

        Integer to = pageSize * pageNo;

        Map map = PageUtils.getQueryCondsMap(dto, start, to);

        List<BidProjOffSplrInf> list = bidProjOffSplrInfMapper.selectByMap(map);

        Integer count = bidProjOffSplrInfMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto adtBidInf(IoBidProjOffBidAdtDto dto) throws Exception {
        logger.info("线下招标项目供应商投标审核");
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = BidProjOffMngForPchsChecker.checkArgsForAdtBidInf(dto);
        if (!"".equals(checkRst)) {
            logger.error("参数校验 - 不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        BidProjOffSplrInf entity = bidProjOffSplrInfMapper.selectByPrimaryKey(dto.getId());
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "线下招标项目供应商投标审核 - 根据项目ID查询线下招标项目供应商投标信息无数据 - id[" + dto.getId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断标书状态是否为 【00-已申请】
        if (!Constants.BID_DOC_STS_APPLIED.equals(entity.getBidDocSts())) {
            String errMsg = "线下招标项目供应商投标审核失败 - 标书状态不符合 - projSts[" + entity.getBidDocSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 更新投标信息
        BeanUtils.copyProperties(entity, dto);
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());

        int effRows = bidProjOffSplrInfMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto appAwd(HttpServletRequest request, IoBidProjOffAppAwdListDto dto) throws Exception {
        logger.info("线下招标项目申请决标, dto: " + dto);

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = BidProjOffMngForPchsChecker.checkArgsForAppAwd(dto);
        if (!"".equals(checkRst)) {
            logger.error("申请决标 - 参数校验不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        Long projId = dto.getProjId();
        BidProjOffWithBLOBs bidProjOff = bidProjOffMapper.selectByPrimaryKey(projId);
        if (bidProjOff == null || !Constants.EFF_FLG_ON.equals(bidProjOff.getEffFlg())) {
            String errMsg = "申请决标 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + dto.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        String projSts = bidProjOff.getProjSts();
        if (!Constants.BID_PROJ_OFF_STS_APP_BID_END.equals(projSts)) {
            String errMsg = "申请决标 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 根据所属公司获取公司代号提交BPM审批
        if (userinfo.getCompany() == null || StringUtils.isEmpty(userinfo.getCompany().getDept_code())) {
            String errMsg = "申请决标 - 失败 - 该采购员所属公司为空，无法提交BPM审批";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        String bpmDeptCode = userinfo.getCompany().getDept_code();
        long durationB = DateUtils.currentTimeMillis() - timeB;


        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        logger.info("3. 提交BPM审批");

        String token = BpmFileUtils.getToken(userinfo.getUsername());
        String rootPath = request.getServletContext().getRealPath("/");
        String basePath = new File(rootPath + "..").getCanonicalPath();

        // 附件上传至BPM
        List<IoAtchDto> atchs = dto.getAtchDtos();
        List<Long> fileIds = new ArrayList<>();
        if (atchs != null && atchs.size() > 0) {
            fileIds = atchs.stream().map(atch -> {
                String atchPath = atch.getAtchUrl();
                String atchNam = atch.getAtchNam();
                String filePath = basePath + File.separator + atchPath;
                return BpmFileUtils.uploadAttachment(userinfo.getUsername(), token, filePath, atchNam);
            }).collect(Collectors.toList());
        }

        // BPM seqNo 生成
        String bpmSeqNo = BpmUtils.getBpmSeqNo(Constants.PROJ_TYP_GK);
        String bpmBody = bidProjOffBpmAppFactory.getAwdBpmBody(bpmSeqNo, bidProjOff, dto);
        String templateCode = bpmDeptCode + Constants.BPM_APP_TYP_GK_AWD_SUFFIX;
        boolean subSucFlg = BpmUtils.subApp(bpmBody, templateCode,
                "线下公开招标项目定标申请", "", fileIds);
        if (!subSucFlg) {
            String errMsg = "线下公开招标项目定标申请 - 提交BPM审批失败";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }


        long timeC = DateUtils.currentTimeMillis();
        logger.info("4.数据持久化 - 开始");
        logger.info("4.1将项目状态置为【决标申请中】");
        Timestamp now = DateUtils.getCurrentTimeStamp();

        BidProjOffSplrRst splrRstQuery = new BidProjOffSplrRst();
        splrRstQuery.setEffFlg(Constants.EFF_FLG_ON);
        splrRstQuery.setProjId(dto.getProjId());
        Map map = PageUtils.getQueryCondsMap(splrRstQuery, 0, 0);
        List<BidProjOffSplrRst> splrRsts = bidProjOffSplrRstMapper.selectByMap(map);
        if (splrRsts != null && splrRsts.size() > 0) {
            splrRsts.stream().forEach(bidProjOffSplrRst -> {
                bidProjOffSplrRst.setEffFlg(Constants.EFF_FLG_OFF);
                bidProjOffSplrRst.setModUsr(realNam);
                bidProjOffSplrRst.setModTim(DateUtils.getCurrentTimeStamp());
                int effRows = bidProjOffSplrRstMapper.updateByPrimaryKey(bidProjOffSplrRst);
                if (effRows != 1) {
                    String errMsg = "更新定标详情失败 - 受影响行数不为1";
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
            });
        }


     /*   ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_GKAWD);
        if (comParm == null) {
            String errMsg = "更新定标详情失败 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_GKAWD + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }*/
  /* String atchPrefix = comParm.getParmVal();
        String refId = atchPrefix + bidProjOff.getProjId();

        atchMapper.deleteByRefId(refId);

        List<IoAtchDto> atchDtos = dto.getAtchDtos();
        if (atchDtos != null && !atchDtos.isEmpty()) {
            for (IoAtchDto atchDto : atchDtos) {
                Atch atch = new Atch();
                BeanUtils.copyProperties(atch, atchDto);
                atch.setRefId(refId);
                atch.setEffFlg(Constants.EFF_FLG_ON);
                atch.setCrtTim(DateUtils.getCurrentTimeStamp());
                atch.setCrtUsr(realNam);
                int effRows = atchMapper.insert(atch);
                if (effRows != 1) {
                    String errMsg = "更新定标详情失败 - 新增附件 - 失败 - 受影响行数不为1";
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
            }
        }*/
        // 附件新增
        List<IoAtchDto> atchDtos = dto.getAtchDtos();
        if (atchDtos != null && !atchDtos.isEmpty()) {
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_GKAWD);
            if (comParm == null) {
                String errMsg = "新增线下公开招标项目 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_GKAWD + "]";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            String atchPrefix = comParm.getParmVal();
            String refId = atchPrefix + bidProjOff.getProjId();
            atchMapper.deleteByRefId(refId);
            for (IoAtchDto atchDto : atchDtos) {
                Atch atch = new Atch();
                BeanUtils.copyProperties(atch, atchDto);
                atch.setRefId(refId);
                atch.setEffFlg(Constants.EFF_FLG_ON);
                atch.setCrtTim(DateUtils.getCurrentTimeStamp());
                atch.setCrtUsr(realNam);
                int effRows = atchMapper.insert(atch);
                if (effRows != 1) {
                    String errMsg = "新增线下公开招标项目 - 新增附件 - 失败 - 受影响行数不为1";
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
            }
        }





        List<IoBidProjOffAppAwdDto> ioBidProjOffAppAwdDtos = dto.getIoBidProjOffAppAwdDtos();
        for (IoBidProjOffAppAwdDto ioBidProjOffAppAwdDto : ioBidProjOffAppAwdDtos) {
            BidProjOffSplrRst bidProjOffSplrRst = new BidProjOffSplrRst();
            Splr splr = splrMapper.selectByPrimaryKey(ioBidProjOffAppAwdDto.getSplrId());
            bidProjOffSplrRst.setProjId(dto.getProjId());
            bidProjOffSplrRst.setProjNam(bidProjOff.getProjNam());
            bidProjOffSplrRst.setSplrId(ioBidProjOffAppAwdDto.getSplrId());
            bidProjOffSplrRst.setPchsNum(ioBidProjOffAppAwdDto.getPchsNum());
            bidProjOffSplrRst.setMatCod(ioBidProjOffAppAwdDto.getMatCod());
            bidProjOffSplrRst.setMatNam(ioBidProjOffAppAwdDto.getMatNam());
            bidProjOffSplrRst.setMatUnt(ioBidProjOffAppAwdDto.getMatUnt());
            bidProjOffSplrRst.setAwdAmt(ioBidProjOffAppAwdDto.getAwdAmt());
            bidProjOffSplrRst.setMemo(ioBidProjOffAppAwdDto.getMemo());
            bidProjOffSplrRst.setSplrNam(splr.getFullNam());
            bidProjOffSplrRst.setCrtUsr(realNam);
            bidProjOffSplrRst.setCrtTim(now);
            bidProjOffSplrRst.setEffFlg(Constants.EFF_FLG_ON);
            int effRows = bidProjOffSplrRstMapper.insert(bidProjOffSplrRst);
            if (effRows != 1) {
                String errMsg = "新增定标详情失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

        }

        bidProjOff.setBpmFinSeq(bpmSeqNo);
        bidProjOff.setAwdMemo(dto.getAwdMemo());
        bidProjOff.setProjSts(Constants.BID_PROJ_OFF_STS_AWDING);
        bidProjOff.setMatReqBpmAppSeq(dto.getMatReqBpmAppSeq());
        bidProjOff.setCaseRept(dto.getCaseRept());
        bidProjOff.setDept(dto.getDept());
        bidProjOff.setMatPchsBgt(dto.getMatPchsBgt());
        bidProjOff.setBidTolAmt(dto.getBidTolAmt());
        bidProjOff.setBrfDesc(dto.getBrfDesc());
        bidProjOff.setModUsr(userinfo.getRealname());
        bidProjOff.setModTim(DateUtils.getCurrentTimeStamp());
        int effRows = bidProjOffMapper.updateByPrimaryKeySelective(bidProjOff);
        if (effRows != 1) {
            String errMsg = "定标失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        //写入线下招标项目状态记录表
        BidProjOffStsLog bidProjOffStsLog = new BidProjOffStsLog();
        bidProjOffStsLog.setProjId(bidProjOff.getProjId());
        bidProjOffStsLog.setProjNam(bidProjOff.getProjNam());
        bidProjOffStsLog.setProjSts(Constants.BID_PROJ_OFF_STS_AWDING);
        bidProjOffStsLog.setStsUpdTim(now);
        bidProjOffStsLog.setModUsr(realNam);

        effRows = bidProjOffStsLogMapper.insert(bidProjOffStsLog);
        if (effRows != 1) {
            String errMsg = "写入线下招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("4. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);

    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewAwdInf(Long projId) throws Exception {

        logger.info("查看招标结果");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

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

        // 判断项目状态是否为 【40:决标审批中】或【50:决标审批通过】
        String projSts = bidProjOff.getProjSts();
        if (!Constants.BID_PROJ_OFF_STS_AWDING.equals(projSts) && !Constants.BID_PROJ_OFF_STS_AWD_ACCEPTED.equals(projSts)) {
            String errMsg = "查看招标结果 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        return OutputDtoUtil.setOutputDto(true, RtnEnum.SUC, bidProjOff);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto pubRst(Long projId) throws Exception {

        logger.info("发布线下招标项目结果");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

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
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "发布线下招标项目结果 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【招标申请审批通过】 或 【已废标】
        if (!(Constants.BID_PROJ_OFF_STS_AWD_ACCEPTED.equals(entity.getProjSts()) || Constants.BID_PROJ_OFF_STS_RPL.equals(entity.getProjSts()))) {
            String errMsg = "发布线下招标项目结果 - 失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        entity.setBidRstPubFlg(Constants.BID_RST_PUB_FLG_ON);
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        entity.setRstPubTim(DateUtils.getCurrentTimeStamp());
        int effRows = bidProjOffMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新项目信息为失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getTendSplrs(IoBidOffGetTendSplrsDto dto) throws Exception {

        logger.info("获取投标供应商");

        // 参数处理
        Long projId = dto.getProjId();
        if (projId == null) {
            String errMsg = "获取投标供应商 - 失败：projId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        String bidDocSts = dto.getBidDocSts();
        if (StringUtils.isEmpty(bidDocSts)) {
            bidDocSts = null;
        }
        //
        BidProjOffSplrInf bidProjOffSplrInf = new BidProjOffSplrInf();
        bidProjOffSplrInf.setProjId(projId);
        bidProjOffSplrInf.setBidDocSts(bidDocSts);

        Map map = PageUtils.getQueryCondsMap(bidProjOffSplrInf, 0, 0);

        List<BidProjOffSplrInf> list = bidProjOffSplrInfMapper.selectByMap(map);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getPagedBidResultByEntity(IoQueryBidProjOffForPchsDto dto, Integer pageNo, Integer pageSize) throws Exception {

        logger.info("分页获取线下招标项目招标结果信息");
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // TODO 鉴权模块
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer to = pageSize * pageNo;

        Integer start = (pageNo - 1) * pageSize + 1;

        // TODO 获取组织ID
        dto.setOrgId(Long.valueOf(userinfo.getOrgId()));
        dto.setBidRstPubFlg(Constants.BID_RST_PUB_FLG_ON);
        dto.setProjSts(Constants.BID_PROJ_OFF_STS_AWD_ACCEPTED);

        Map map = PageUtils.getQueryCondsMap(dto, start, to);

        List<BidProjOff> list = bidProjOffMapper.selectByEntity(map);

        Integer count = bidProjOffMapper.countByEntity(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }


    @Override
    public OutputDto viewProjRstDtl(Long projId) throws Exception {
        logger.info("查看招标结果");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

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

        // 判断项目状态是否为 【40:决标审批中】或【50:决标审批通过】
        String projSts = bidProjOff.getProjSts();
        if (!Constants.BID_PROJ_OFF_STS_AWDING.equals(projSts) && !Constants.BID_PROJ_ON_STS_AWD_ACCEPTED.equals(projSts)) {
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
        logger.info("3. 结束查询数据 - 开始");

        IoBidProjOffAppAwdDtlDto dto = new IoBidProjOffAppAwdDtlDto();
        dto.setBidProjOff(bidProjOff);
        dto.setBidProjOffSplrRstLists(bidProjOffSplrRsts);
        dto.setMatTypDesc(matFactory.getMatTypDesc(bidProjOff.getMatTyp()));
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, dto);
    }
}
