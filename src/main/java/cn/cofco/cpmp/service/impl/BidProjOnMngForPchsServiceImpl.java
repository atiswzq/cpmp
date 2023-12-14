package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.dto.exptgrd.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.factory.BidProjOnBpmAppFactory;
import cn.cofco.cpmp.factory.MatFactory;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOnMngForPchsService;
import cn.cofco.cpmp.service.ICodRulInfService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;
import cn.cofco.cpmp.utils.StringUtils;
import cn.cofco.cpmp.utils.checkers.BasicChecker;
import cn.cofco.cpmp.utils.checkers.BidProjOnMngForPchsChecker;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

import java.util.stream.Collectors;

/**
 * Created by Xujy on 2017/5/17.
 * for [线上招标项目管理服务类 - 工厂采购员 实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class BidProjOnMngForPchsServiceImpl implements IBidProjOnMngForPchsService {

    private static Logger logger = LoggerManager.getBidOnlineMngLog();

    @Resource
    private BidProjOnMapper bidProjOnMapper;

    @Resource
    private BidProjOnStsLogMapper bidProjOnStsLogMapper;

    @Resource
    private BidProjOnMatDtlMapper bidProjOnMatDtlMapper;

    @Resource
    private BidProjOnSplrInvtMapper bidProjOnSplrInvtMapper;

    @Resource
    private BidProjOnSplrTendInfMapper bidProjOnSplrTendInfMapper;

    @Resource
    private ExptInfMapper exptInfMapper;

    @Resource
    private BidProjOnExptGrdInfMapper bidProjOnExptGrdInfMapper;

    @Resource
    private BidProjOnSplrQotDtlMapper bidProjOnSplrQotDtlMapper;

    @Resource
    private BidProjOnSplrQotInfMapper bidProjOnSplrQotInfMapper;

    @Resource
    private BidProjOnExptGrdDtlMapper bidProjOnExptGrdDtlMapper;

    @Resource
    private BidProjOnWinDtlMapper bidProjOnWinDtlMapper;

    @Resource
    private SplrMapper splrMapper;

    @Resource
    private BidProjOnBpmAppFactory bidProjOnBpmAppFactory;

    @Resource
    private ICodRulInfService codRulInfService;

    @Resource
    private RequisitionMapper requisitionMapper;

    @Resource
    private AtchMapper atchMapper;

    @Resource
    private MatFactory matFactory;

    @Resource
    private BidProjOnExptGrdDtlLowMapper bidProjOnExptGrdDtlLowMapper;

    @Resource
    private MaterielMapper materielMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getPagedResultByIoQueryBidProjOnForPchsDto(IoQueryBidProjOnForPchsDto dto, Integer pageNo, Integer pageSize) {

        logger.info("获取线上招标项目分页信息, dto - " + dto);

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
            String errMsg = "获取线上招标项目分页信息 - 失败 - 该采购员无所辖公司";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.NO_OPRT_AUTH, errMsg);
        }

        List<Long> orgIds = companies
                .stream()
                .map(currentUserInfoFactory -> Long.valueOf(currentUserInfoFactory.getId()))
                .collect(Collectors.toList());

        if (dto.getOrgId() != null && !dto.getOrgId().equals(0L) && !orgIds.stream().anyMatch(orgId -> orgId.equals(dto.getOrgId()))) {
            String errMsg = "获取线上招标项目分页信息 - 失败 - 所查询公司不在所辖范围内";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.NO_OPRT_AUTH, errMsg);
        }
        /*采购员只负责自己项目信息*/
        String reseIndex = userinfo.getReseIndex();
        if(Arrays.asList(Constants.pchs).contains(reseIndex)){
            Map map = PageUtils.getQueryCondsMap(dto, start, to);
            map.put("orgIds", orgIds);
            map.put("effFlg", Constants.EFF_FLG_ON);
            map.put("desc", 1);
            map.put("crtUsr",userinfo.getUserid());
            List<BidProjOn> list = bidProjOnMapper.selectByMap(map);

            Integer count = bidProjOnMapper.countByMap(map);

            PagedResult result = new PagedResult(list, count);

            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
        }
        Map map = PageUtils.getQueryCondsMap(dto, start, to);
        map.put("orgIds", orgIds);
        map.put("effFlg", Constants.EFF_FLG_ON);
        map.put("desc", 1);

        List<BidProjOn> list = bidProjOnMapper.selectByMap(map);

        Integer count = bidProjOnMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto saveOrSub(HttpServletRequest request, IoBidProjOnDto dto) throws Exception {

        logger.info("保存线上招标项目, request: " + dto);

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = BidProjOnMngForPchsChecker.checkArgsForSaveOrSub(dto);
        if (!"".equals(checkRst)) {
            logger.error("参数校验 - 不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }

        List<CurrentUserInfoFactory> companies = userinfo.getManageCompanies();
        if (companies == null || companies.isEmpty()) {
            logger.error("保存线上招标项目 - 失败 - 该采购员无所辖公司");
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        }
        String deptCode = companies.stream()
                .filter(company -> company.getId().equals(dto.getOrgId().intValue()))
                .findFirst().get().getDept_code();

        if (userinfo.getCompany() == null || StringUtils.isEmpty(userinfo.getCompany().getDept_code())) {
            String errMsg = "保存线上招标项目 - 失败 - 该采购员所属公司为空，无法提交BPM审批";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        String bpmDeptCode = userinfo.getCompany().getDept_code();
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        // 2. entity根据有效值赋值
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. entity根据有效值赋值 - 开始");
        BidProjOn bidProjOn = new BidProjOn();
        List<BidProjOnMatDtl> matDtls = new ArrayList<>();
        try {
            BeanUtils.copyProperties(bidProjOn, dto);
            List<IoBidProjOnMatInfDto> matInfInDtos = dto.getMatList();
            for (IoBidProjOnMatInfDto m : matInfInDtos) {
                BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
                BeanUtils.copyProperties(matDtl, m);
                if (m.getReqId() == null || m.getReqId().equals(0L)) {
                    matDtl.setReqId(null);
                }
                matDtls.add(matDtl);
            }
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
        if (bidProjOn.getProjId() == null || bidProjOn.getProjId() == 0L) {
            bidProjOn.setProjNbr(codRulInfService.getProjNbr(Constants.PROJ_TYP_JJ, deptCode));
            bidProjOn.setProjRsps(realNam);
            bidProjOn.setCrtUsr(userinfo.getUserid().toString());
            bidProjOn.setBidTwcFlg(Constants.BID_TWC_FLG_N);
            bidProjOn.setCrtTim(DateUtils.getCurrentTimeStamp());
            bidProjOn.setEffFlg(Constants.EFF_FLG_ON);
            ComParm comParmBidProjOnStsEdting = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_BID_PROJ_ON_STS, Constants.BID_PROJ_ON_STS_EDTING);
            if (comParmBidProjOnStsEdting == null || !Constants.EFF_FLG_ON.equals(comParmBidProjOnStsEdting.getEffFlg())
                    || StringUtils.isEmpty(comParmBidProjOnStsEdting.getParmVal())) {
                String errMsg = "新增线上招标项目失败 - 通用参数为定义：parmTyp[" + Constants.COM_PARM_TYP_BID_PROJ_ON_STS + "], parmCod[" + Constants.BID_PROJ_ON_STS_EDTING + "]";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            bidProjOn.setProjSts(comParmBidProjOnStsEdting.getParmVal());
            int effRows = bidProjOnMapper.insert(bidProjOn);
            if (effRows != 1) {
                String errMsg = "新增线上招标项目失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            List<Long> reqIds = new ArrayList<>();
            for (BidProjOnMatDtl matDtl : matDtls) {
                Long reqId = matDtl.getReqId();
                if (reqId != null && !reqId.equals(0L)) {
                    reqIds.add(reqId);
                }
                // 设置项目ID
                matDtl.setProjId(bidProjOn.getProjId());
                matDtl.setCrtUsr(realNam);
                matDtl.setCrtTim(DateUtils.getCurrentTimeStamp());
                matDtl.setEffFlg(Constants.EFF_FLG_ON);
                bidProjOnMatDtlMapper.insert(matDtl);
            }
            if (!reqIds.isEmpty()) {
                requisitionMapper.updateToInzb(Constants.INZB_1, reqIds);
            }

            // 定向招标供应商信息新增
            if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOn.getBidRngTyp())) {
                String splrIdsStr = dto.getSplrIds();
                String[] splrIds = splrIdsStr.split(",");
                for (String splrIdStr : splrIds) {
                    Long splrId = Long.valueOf(splrIdStr);
                    BidProjOnSplrInvt bidProjOnSplrInvt = new BidProjOnSplrInvt();
                    bidProjOnSplrInvt.setProjId(bidProjOn.getProjId());
                    bidProjOnSplrInvt.setBidFlg(Constants.BID_FLG_UNDO);
                    bidProjOnSplrInvt.setBidCntTyp(Constants.BID_CNT_TYP_FST);
                    bidProjOnSplrInvt.setSplrId(splrId);
                    Splr splr = splrMapper.selectByPrimaryKey(splrId);
                    if (splr != null) {
                        bidProjOnSplrInvt.setSplrNam(splr.getFullNam());
                    }
                    bidProjOnSplrInvt.setCrtUsr(realNam);
                    bidProjOnSplrInvt.setCrtTim(DateUtils.getCurrentTimeStamp());
                    bidProjOnSplrInvt.setEffFlg(Constants.EFF_FLG_ON);
                    bidProjOnSplrInvtMapper.insert(bidProjOnSplrInvt);
                }
            }

            // 附件新增
            List<IoAtchDto> atchDtos = dto.getAtchDtos();
            if (atchDtos != null && !atchDtos.isEmpty()) {
                ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_JJBUD);
                if (comParm == null) {
                    String errMsg = "新增网上竞价招标项目 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_JJBUD + "]";
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }

                String atchPrefix = comParm.getParmVal();
                String refId = atchPrefix + bidProjOn.getProjId();
                for (IoAtchDto atchDto : atchDtos) {
                    Atch atch = new Atch();
                    BeanUtils.copyProperties(atch, atchDto);
                    atch.setRefId(refId);
                    atch.setEffFlg(Constants.EFF_FLG_ON);
                    atch.setCrtTim(DateUtils.getCurrentTimeStamp());
                    atch.setCrtUsr(realNam);
                    effRows = atchMapper.insert(atch);
                    if (effRows != 1) {
                        String errMsg = "新增网上竞价招标项目 - 新增附件 - 失败 - 受影响行数不为1";
                        logger.error(errMsg);
                        throw new RuntimeException(errMsg);
                    }
                }
            }

            // 写入线上招标项目状态记录表
            BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
            bidProjOnStsLog.setProjId(bidProjOn.getProjId());
            bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
            bidProjOnStsLog.setProjSts(comParmBidProjOnStsEdting.getParmVal());
            bidProjOnStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
            bidProjOnStsLog.setUpdUsr(userinfo.getRealname());

            effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
            if (effRows != 1) {
                String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

        }
        // 更新项目
        else {
            BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(bidProjOn.getProjId());
            if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
                String errMsg = "更新项目信息失败 - 根据项目ID查询项目信息无数据 - projId[" + bidProjOn.getProjId() + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }

            // 判断项目状态是否为 【编辑中】
            if (!Constants.BID_PROJ_ON_STS_EDTING.equals(entity.getProjSts())) {
                String errMsg = "更新项目信息失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
            bidProjOn.setProjNbr(entity.getProjNbr());
            bidProjOn.setProjRsps(realNam);
            bidProjOn.setCrtUsr(entity.getCrtUsr());
            bidProjOn.setCrtTim(entity.getCrtTim());
            bidProjOn.setModUsr(realNam);
            bidProjOn.setModTim(DateUtils.getCurrentTimeStamp());
            bidProjOn.setBidTwcFlg(Constants.BID_TWC_FLG_N);
            bidProjOn.setEffFlg(Constants.EFF_FLG_ON);
            ComParm comParmBidProjOnStsEdting = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_BID_PROJ_ON_STS, Constants.BID_PROJ_ON_STS_EDTING);
            if (comParmBidProjOnStsEdting == null || !Constants.EFF_FLG_ON.equals(comParmBidProjOnStsEdting.getEffFlg())
                    || StringUtils.isEmpty(comParmBidProjOnStsEdting.getParmVal())) {
                String errMsg = "更新线上招标项目失败 - 通用参数为定义：parmTyp[" + Constants.COM_PARM_TYP_BID_PROJ_ON_STS + "], parmCod[" + Constants.BID_PROJ_ON_STS_EDTING + "]";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            bidProjOn.setProjSts(comParmBidProjOnStsEdting.getParmVal());
            int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
            if (effRows != 1) {
                String errMsg = "更新线上招标项目失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            // 更新物料信息
            BidProjOnMatDtl matDtlEntity = new BidProjOnMatDtl();
            matDtlEntity.setProjId(bidProjOn.getProjId());
            matDtlEntity.setEffFlg(Constants.EFF_FLG_ON);
            Map matDtlEntityMap = PageUtils.getQueryCondsMap(matDtlEntity, 0, 0);
            List<BidProjOnMatDtl> matDtlList = bidProjOnMatDtlMapper.selectByMap(matDtlEntityMap);
            List<Long> delReqIds = new ArrayList<>();
            for (BidProjOnMatDtl m : matDtlList) {
                Long reqId = m.getReqId();
                if (reqId != null && !reqId.equals(0L)) {
                    delReqIds.add(reqId);
                }
                m.setModUsr(realNam);
                m.setModTim(DateUtils.getCurrentTimeStamp());
                m.setEffFlg(Constants.EFF_FLG_OFF);
                bidProjOnMatDtlMapper.updateByPrimaryKey(m);
            }
            if (!delReqIds.isEmpty()) {
                requisitionMapper.updateToInzb(Constants.INZB_0, delReqIds);
            }

            Iterator<BidProjOnMatDtl> itSub = matDtls.iterator();
            List<Long> reqIds = new ArrayList<>();
            while (itSub.hasNext()) {
                BidProjOnMatDtl dtlSub = itSub.next();
                Long reqId = dtlSub.getReqId();
                if (reqId != null && !reqId.equals(0L)) {
                    reqIds.add(reqId);
                }
                dtlSub.setProjId(bidProjOn.getProjId());
                dtlSub.setCrtUsr(realNam);
                dtlSub.setCrtTim(DateUtils.getCurrentTimeStamp());
                dtlSub.setEffFlg(Constants.EFF_FLG_ON);
                bidProjOnMatDtlMapper.insert(dtlSub);
            }
            if (!reqIds.isEmpty()) {
                requisitionMapper.updateToInzb(Constants.INZB_1, reqIds);
            }

            bidProjOnSplrInvtMapper.deleteByProjId(bidProjOn.getProjId());
            // 定向招标供应商信息新增
            if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOn.getBidRngTyp())) {
                String splrIdsStr = dto.getSplrIds();
                String[] splrIds = splrIdsStr.split(",");
                for (String splrIdStr : splrIds) {
                    Long splrId = Long.valueOf(splrIdStr);
                    BidProjOnSplrInvt bidProjOnSplrInvt = new BidProjOnSplrInvt();
                    bidProjOnSplrInvt.setProjId(bidProjOn.getProjId());
                    bidProjOnSplrInvt.setSplrId(splrId);
                    Splr splr = splrMapper.selectByPrimaryKey(splrId);
                    if (splr != null) {
                        bidProjOnSplrInvt.setSplrNam(splr.getFullNam());
                    }
                    bidProjOnSplrInvt.setBidFlg(Constants.BID_FLG_UNDO);
                    bidProjOnSplrInvt.setCrtUsr(realNam);
                    bidProjOnSplrInvt.setCrtTim(DateUtils.getCurrentTimeStamp());
                    bidProjOnSplrInvt.setEffFlg(Constants.EFF_FLG_ON);
                    bidProjOnSplrInvtMapper.insert(bidProjOnSplrInvt);
                }
            }

            // 2.2 更新文章附件
            // 2.2.1 得到附件关联ID
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_JJBUD);
            if (comParm == null) {
                String errMsg = "更新线上竞价招标项目信息 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_JJBUD + "]";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            String atchPrefix = comParm.getParmVal();
            String refId = atchPrefix + bidProjOn.getProjId();

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
                        String errMsg = "更新线上竞价招标项目信息 - 新增附件 - 失败 - 受影响行数不为1";
                        logger.error(errMsg);
                        throw new RuntimeException(errMsg);
                    }
                }
            }
        }

        // 提交BPM审批
        String subFlg = dto.getSubFlg();
        if (Constants.SUB_FLG_ON.equals(subFlg)) {
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
            String bpmSeqNo = BpmUtils.getBpmSeqNo(Constants.PROJ_TYP_JJ);
            String bpmBody = bidProjOnBpmAppFactory.getBudBpmBody(bpmSeqNo, bidProjOn, matDtls, dto.getSplrIds());
            String templateCode = bpmDeptCode + Constants.BPM_APP_TYP_JJ_BUD_SUFFIX;
            boolean subSucFlg = BpmUtils.subApp(bpmBody, templateCode, "网上竞价项目立项申请", "", fileIds);
            if (!subSucFlg) {
                String errMsg = "网上竞价项目立项申请 - 提交BPM审批失败";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            Timestamp now = DateUtils.getCurrentTimeStamp();

            bidProjOn.setBpmBudSeq(bpmSeqNo);
            bidProjOn.setOpenKey(CryptUtils.getRandNum(6));
            bidProjOn.setModUsr(realNam);
            bidProjOn.setModTim(now);
            bidProjOn.setEffFlg(Constants.EFF_FLG_ON);

            bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_APP_ADTING);
            bidProjOn.setStsUpdTim(DateUtils.getCurrentTimeStamp());
            int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
            if (effRows != 1) {
                String errMsg = "更新线上招标项目失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            // 写入线上招标项目状态记录表
            BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
            bidProjOnStsLog.setProjId(bidProjOn.getProjId());
            bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
            bidProjOnStsLog.setProjSts(Constants.BID_PROJ_ON_STS_APP_ADTING);
            bidProjOnStsLog.setStsUpdTim(now);
            bidProjOnStsLog.setUpdUsr(realNam);

            effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
            if (effRows != 1) {
                String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 新增、更新、提交BPM审批 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto del(Long projId) throws Exception {

        logger.info("删除线上招标项目");

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

        BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(projId);
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "删除项目信息失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【编辑中】
        if (!Constants.BID_PROJ_ON_STS_EDTING.equals(entity.getProjSts())) {
            String errMsg = "删除项目信息失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setEffFlg(Constants.EFF_FLG_OFF);

        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "删除线上招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 更新物料信息
        BidProjOnMatDtl matDtlEntity = new BidProjOnMatDtl();
        matDtlEntity.setProjId(projId);
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
            dtlQry.setModUsr(realNam);
            dtlQry.setModTim(DateUtils.getCurrentTimeStamp());
            dtlQry.setEffFlg(Constants.EFF_FLG_OFF);
            bidProjOnMatDtlMapper.updateByPrimaryKey(dtlQry);
        }

        if (!reqIds.isEmpty()) {
            requisitionMapper.updateToInzb(Constants.INZB_0, reqIds);
        }

        // 写入线上招标项目状态记录表
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(entity.getProjId());
        bidProjOnStsLog.setProjNam(entity.getProjNam());
        bidProjOnStsLog.setProjSts(Constants.BID_PROJ_OFF_STS_EDTING);
        bidProjOnStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        bidProjOnStsLog.setUpdUsr(realNam);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto view(Long projId) throws Exception {

        logger.info("查看线上招标项目详情");

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
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "查看线上招标项目详情 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(matDtlMap);

       /* List<IoBidProjOnMatInfDto> ioBidProjOnMatInfDtos=new ArrayList<>();
        BeanUtils.copyProperties(ioBidProjOnMatInfDtos,matDtls);
        for(IoBidProjOnMatInfDto ioBidProjOnMatInfDto:ioBidProjOnMatInfDtos){
            ioBidProjOnMatInfDto.setDlvDteStr(DateUtils.date2SimpleString(ioBidProjOnMatInfDto.getDlvDte()));
        }*/
        List<BidProjOnSplrInvt> bidProjOnSplrInvts = null;
        if (Constants.BID_RNG_TYP_VECTORING.equals(entity.getBidRngTyp())) {
            BidProjOnSplrInvt bidProjOnSplrInvt = new BidProjOnSplrInvt();
            bidProjOnSplrInvt.setProjId(projId);
            Map splrInvtMap = PageUtils.getQueryCondsMap(bidProjOnSplrInvt, 0, 0);
            bidProjOnSplrInvts = bidProjOnSplrInvtMapper.selectByEntity(splrInvtMap);
        }

        ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_JJBUD);
        if (comParm == null) {
            String errMsg = "查看线上招标项目详情 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_JJBUD + "]";
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
        bidProjOnInfViewIoDto.setSplrs(bidProjOnSplrInvts);
        bidProjOnInfViewIoDto.setAtches(atches);
        bidProjOnInfViewIoDto.setMatTypDesc(matFactory.getMatTypDesc(entity.getMatTyp()));

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, bidProjOnInfViewIoDto);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto publish(Long projId) throws Exception {
        logger.info("发布线上招标项目");

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

        BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(projId);
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "发布线上招标项目失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【招标申请审批通过】
        if (!Constants.BID_PROJ_ON_STS_APP_ADT_PASS.equals(entity.getProjSts())) {
            String errMsg = "发布线上招标项目失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 更新项目状态为 - 招标中；发布状态为 - 已发布
        entity.setNtcPubTim(DateUtils.getCurrentTimeStamp());
        entity.setProjSts(Constants.BID_PROJ_ON_STS_BIDDING);
        entity.setBidNtcPubFlg(Constants.BID_NTC_PUB_FLG_ON);
        entity.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());

        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新项目状态为失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        ComParm comParmBidProjOnStsBidding = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_BID_PROJ_ON_STS, Constants.BID_PROJ_ON_STS_BIDDING);
        if (comParmBidProjOnStsBidding == null || !Constants.EFF_FLG_ON.equals(comParmBidProjOnStsBidding.getEffFlg())
                || StringUtils.isEmpty(comParmBidProjOnStsBidding.getParmVal())) {
            String errMsg = "发布线上招标项目失败 - 通用参数为定义：parmTyp[" + Constants.COM_PARM_TYP_BID_PROJ_ON_STS + "], parmCod[" + Constants.BID_PROJ_ON_STS_BIDDING + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(entity.getProjId());
        bidProjOnStsLog.setProjNam(entity.getProjNam());
        bidProjOnStsLog.setProjSts(comParmBidProjOnStsBidding.getParmVal());
        bidProjOnStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        bidProjOnStsLog.setUpdUsr("TST USR");

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto cut(IoBidProjOnCutDto dto) throws Exception {
        logger.info("线上招标项目截标");

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

        BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "线上招标项目截标 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【招标中】 或 【二次报价中】，若不符合，则返回异常
        if (!Constants.BID_PROJ_ON_STS_BIDDING.equals(entity.getProjSts()) && !Constants.BID_PROJ_ON_STS_QOTING2.equals(entity.getProjSts())) {
            String errMsg = "线上招标项目截标 - 失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 更新项目状态为 - 【招标结束】或【二次报价结束】, 截标备注
        String sts = ""; // 后置状态
        if (Constants.BID_PROJ_ON_STS_BIDDING.equals(entity.getProjSts())) {
            // 若为【招标中】，则更新到【招标结束】状态
            dto.setBidEndMemo(StringUtils.getByLength(dto.getBidEndMemo(), 255));
            entity.setBidEndMemo(dto.getBidEndMemo());
            sts = Constants.BID_PROJ_ON_STS_BID_END;
        } else if (Constants.BID_PROJ_ON_STS_QOTING2.equals(entity.getProjSts())) {
            // 若为【二次报价中】，则更新到【二次报价结束】状态
            dto.setBidEndMemo2(StringUtils.getByLength(dto.getBidEndMemo2(), 255));
            entity.setBidEndMemo2(dto.getBidEndMemo2());
            sts = Constants.BID_PROJ_ON_STS_QOT2_END;
        }

        entity.setProjSts(sts);
        entity.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());

        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新项目状态 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(entity.getProjId());
        bidProjOnStsLog.setProjNam(entity.getProjNam());
        bidProjOnStsLog.setProjSts(sts);
        bidProjOnStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        bidProjOnStsLog.setUpdUsr("TST USR");

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto repeal(IoBidProjOnRepealDto dto) throws Exception {
        logger.info("线上招标项目申请废标");

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

        BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "线上招标项目申请废标 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【编辑中:00】 或 【已废标:-20】 或 【申请废标审批中:-21】，若符合，则返回异常
        if (Constants.BID_PROJ_ON_STS_EDTING.equals(entity.getProjSts()) ||
                Constants.BID_PROJ_ON_STS_RPL.equals(entity.getProjSts()) ||
                Constants.BID_PROJ_ON_STS_RPL_ADTING.equals(entity.getProjSts())) {
            String errMsg = "线上招标项目申请废标 - 失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

//        List<CurrentUserInfoFactory> companies = userinfo.getManageCompanies();
//        if (companies == null || companies.isEmpty()) {
//            logger.error("线上招标项目申请废标 - 失败 - 该采购员无所辖公司");
//            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
//        }
//        String deptCode = companies.stream()
//                .filter(company -> company.getId().equals(entity.getOrgId().intValue()))
//                .findFirst().get().getDept_code();

        if (userinfo.getCompany() == null || StringUtils.isEmpty(userinfo.getCompany().getDept_code())) {
            String errMsg = "线上招标项目申请废标 - 失败 - 该采购员所属公司为空，无法提交BPM审批";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        String bpmDeptCode = userinfo.getCompany().getDept_code();

        String bpmSeqNo = BpmUtils.getBpmSeqNo(Constants.PROJ_TYP_JJ);
        String bpmBody = bidProjOnBpmAppFactory.getRplBpmBody(bpmSeqNo, entity, dto.getAppRplMemo());
        String templateCode = bpmDeptCode + Constants.BPM_APP_TYP_JJ_RPL_SUFFIX;
        boolean subSucFlg = BpmUtils.subApp(bpmBody, templateCode, "网上竞价项目废标申请", "", null);
        if (!subSucFlg) {
            String errMsg = "网上竞价项目废标申请 - 提交BPM审批失败";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 更新项目状态为 - 【申请废标审批中:-21】
        entity.setAppRplMemo(StringUtils.getByLength(dto.getAppRplMemo(), 255));
        entity.setBpmRplSeq(bpmSeqNo);
        entity.setProjSts(Constants.BID_PROJ_ON_STS_RPL_ADTING);
        entity.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());

        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新项目状态 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(entity.getProjId());
        bidProjOnStsLog.setProjNam(entity.getProjNam());
        bidProjOnStsLog.setProjSts(Constants.BID_PROJ_ON_STS_RPL_ADTING);
        bidProjOnStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        bidProjOnStsLog.setUpdUsr(realNam);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewBidDtl(Long id) throws Exception {
        logger.info("查看线上招标项目供应商投标信息详情");

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

        BidProjOnSplrTendInf entity = bidProjOnSplrTendInfMapper.selectByPrimaryKey(id);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "查看线上招标项目供应商投标信息详情 - 根据ID查询线上招标项目供应商投标信息详情无数据 - id[" + id + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto adtBidInf(IoBidProjOnBidAdtDto dto) throws Exception {
        logger.info("线上招标项目供应商投标审核");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = BidProjOnMngForPchsChecker.checkArgsForAdtBidInf(dto);
        if (!"".equals(checkRst)) {
            logger.error("参数校验 - 不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        BidProjOnSplrTendInf entity = bidProjOnSplrTendInfMapper.selectByPrimaryKey(dto.getId());
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "线上招标项目供应商投标审核 - 根据项目ID查询线上招标项目供应商投标信息无数据 - id[" + dto.getId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断标书状态是否为 【00-已申请】
        if (!Constants.BID_DOC_STS_APPLIED.equals(entity.getBidDocSts())) {
            String errMsg = "线上招标项目供应商投标审核失败 - 标书状态不符合 - projSts[" + entity.getBidDocSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 更新投标信息
        BeanUtils.copyProperties(entity, dto);
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());

        int effRows = bidProjOnSplrTendInfMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getBidInfByIoQueryBidProjOnSplrTendInfDto(IoQueryBidProjOnSplrTendInfDto dto, Integer pageNo, Integer pageSize) throws Exception {
        logger.info("根据条件分页查询线上招标项目投标信息");

        // 参数处理
        Long projId = dto.getProjId();
        if (projId == null) {
            String errMsg = "根据条件分页查询线上招标项目投标信息 - 失败：projId不得为空";
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

        List<BidProjOnSplrTendInf> list = bidProjOnSplrTendInfMapper.selectByMap(map);

        Integer count = bidProjOnSplrTendInfMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto sndOpenKey(Long projId) throws Exception {
        logger.info("发送开标密钥");

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (projId == null) {
            String errMsg = "参数校验 - 不通过 - projId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "发送开标密钥 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        logger.info("2.2 业务校验 - 校验项目状态[已截标:21]或[二次报价结束:31]");
        if (!(Constants.BID_PROJ_ON_STS_BID_END.equals(entity.getProjSts())
                || Constants.BID_PROJ_ON_STS_QOT2_END.equals(entity.getProjSts()))) {
            String errMsg = "发送开标密钥 - 校验项目状态[已截标:21]或[二次报价结束:31] - 失败 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        boolean sndFlg = SmsSndUtils.sendSmsForOpenBidProjOn(entity);

        if (!sndFlg) {
            String errMsg = "发送开标密钥 - 发送短信失败";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto openProj(IoBidProjOnOpenDto dto) throws Exception {

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        logger.info("线上招标项目开标");

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String chkRst = BidProjOnMngForPchsChecker.checkArgsForOpenProj(dto);
        if (!"".equals(chkRst)) {
            String errMsg = "线上招标项目开标 - 参数校验 - 失败 - " + chkRst;
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(dto.getProjId());
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "线上招标项目开标 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + dto.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (!Constants.BID_PROJ_ON_STS_BID_END.equals(bidProjOn.getProjSts()) && !Constants.BID_PROJ_ON_STS_QOT2_END.equals(bidProjOn.getProjSts())) {
            String errMsg = "线上招标项目开标 - 业务校验 - 失败 - 项目状态异常 - projSts[" + bidProjOn.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (!dto.getOpenKey().equals(bidProjOn.getOpenKey())) {
            String errMsg = "线上招标项目开标 - 业务校验 - 失败 - 开标密钥不正确";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        logger.info("3.1 更新项目状态至[22:已开标]或[32:已二次开标]");
        String projSts = null;
        if (Constants.BID_PROJ_ON_STS_BID_END.equals(bidProjOn.getProjSts())) {
            projSts = Constants.BID_PROJ_ON_STS_OPENED;
        } else {
            projSts = Constants.BID_PROJ_ON_STS_QOT2_OPENED;
        }
        bidProjOn.setProjSts(projSts);
        bidProjOn.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOn.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOn.setModUsr(realNam);

        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
        if (effRows != 1) {
            String errMsg = "更新线上招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(bidProjOn.getProjId());
        bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
        bidProjOnStsLog.setProjSts(bidProjOn.getProjSts());
        bidProjOnStsLog.setStsUpdTim(now);
        bidProjOnStsLog.setUpdUsr(realNam);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 报价解密");
        Long projId = dto.getProjId();
        BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
        bidProjOnSplrTendInf.setProjId(projId);
        bidProjOnSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);

        Map map = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf, 0, 0);
        List<BidProjOnSplrTendInf> tendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);
        List<Long> qotIds = new ArrayList<>();
        for (BidProjOnSplrTendInf tendInf : tendInfs) {
            if (!Constants.BID_DOC_STS_ACCEPTED.equals(tendInf.getBidDocSts())) {
                continue;
            }

            if (Constants.BID_PROJ_ON_STS_OPENED.equals(projSts) && tendInf.getQotId() != null) {
                qotIds.add(tendInf.getQotId());
            } else if (Constants.BID_PROJ_ON_STS_QOT2_OPENED.equals(projSts) && tendInf.getQot2Id() != null) {
                qotIds.add(tendInf.getQot2Id());
            }
        }

        if (!qotIds.isEmpty()) {
            BidProjOnSplrQotDtl dtl = new BidProjOnSplrQotDtl();
            dtl.setEffFlg(Constants.EFF_FLG_ON);
            map = PageUtils.getQueryCondsMap(dtl, 0, 0);
            map.put("qotIds", qotIds);
            map.put("effFlg", Constants.EFF_FLG_ON);
            List<BidProjOnSplrQotDtl> bidProjOnSplrQotDtls = bidProjOnSplrQotDtlMapper.selectByMap(map);
            for (BidProjOnSplrQotDtl b : bidProjOnSplrQotDtls) {
                String ecry = b.getUntPriEcrp();
                if (!StringUtils.isEmpty(ecry)) {
                    BigDecimal untPri = new BigDecimal(CryptUtils.decrypt(ecry, bidProjOn.getOpenKey()));
                    b.setUntPri(untPri);
                    b.setModTim(DateUtils.getCurrentTimeStamp());
                    b.setModUsr(realNam);

                    effRows = bidProjOnSplrQotDtlMapper.updateByPrimaryKey(b);
                    if (effRows != 1) {
                        String errMsg = "解密后更新报价明细表 - 失败 - 受影响行数不为1";
                        logger.error(errMsg);
                        throw new RuntimeException(errMsg);
                    }
                }
            }
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getExptInfs(IoGetExptInfForPchsDto dto) throws Exception {
        logger.info("获取专家信息列表");

        // 参数处理
        String matTyp = dto.getMatTyp();
        if (StringUtils.isEmpty(matTyp)) {
            dto.setMatTyp(null);
        }

        Map map = PageUtils.getQueryCondsMap(dto, 0, 0);
        map.put("effFlg", Constants.EFF_FLG_ON);

        List<ExptInf> exptInfs = exptInfMapper.selectByMap(map);
        if (exptInfs == null || exptInfs.isEmpty()) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA, exptInfs);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, exptInfs);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto chsExpts(IoBidProjOnChsExptsDto dto) throws Exception {
        logger.info("线上招标项目选择评标专家, dto: " + dto);

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String chkRst = BidProjOnMngForPchsChecker.checkArgsForChsExpts(dto);
        if (!"".equals(chkRst)) {
            String errMsg = "线上招标项目开标 - 参数校验 - 不通过 - " + chkRst;
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        String[] exptIds = null;
        try {
            exptIds = dto.getExptIds().split(",");
        } catch (Exception e) {
            String errMsg = "线上招标项目选择评标专家 - 参数校验 - 不通过 - 专家IDs转列表异常 - exptIds[" + dto.getExptIds() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (exptIds == null || exptIds.length == 0) {
            String errMsg = "线上招标项目选择评标专家 - 参数校验 - 不通过 - 专家IDs转列表后为空 - exptIds[" + dto.getExptIds() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(dto.getProjId());
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "线上招标项目选择评标专家 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + dto.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (!Constants.BID_PROJ_ON_STS_OPENED.equals(bidProjOn.getProjSts()) && !Constants.BID_PROJ_ON_STS_QOT2_OPENED.equals(bidProjOn.getProjSts())) {
            String errMsg = "线上招标项目选择评标专家 - 业务校验 - 失败 - 项目状态异常 - projSts[" + bidProjOn.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        logger.info("3.1 更新项目状态至[23:评标中]或[33:二次评标中]");

//        String projSts = bidProjOn.getProjSts();

        if (Constants.BID_PROJ_ON_STS_OPENED.equals(bidProjOn.getProjSts())) {
            bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_GRADING);
        } else {
            bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_QOT2_GRADING);
        }
        bidProjOn.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOn.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOn.setModUsr(realNam);

        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
        if (effRows != 1) {
            String errMsg = "线上招标项目选择评标专家 - 更新线上招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(bidProjOn.getProjId());
        bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
        bidProjOnStsLog.setProjSts(bidProjOn.getProjSts());
        bidProjOnStsLog.setStsUpdTim(now);
        bidProjOnStsLog.setUpdUsr(realNam);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 新增数据至线上招标项目专家评标信息表");
        for (String exptIdStr : exptIds) {
            Long exptId = Long.valueOf(exptIdStr);
            BidProjOnExptGrdInf bidProjOnExptGrdInf = new BidProjOnExptGrdInf();
            bidProjOnExptGrdInf.setProjId(bidProjOn.getProjId());
            bidProjOnExptGrdInf.setProjNam(bidProjOn.getProjNam());
            bidProjOnExptGrdInf.setProjGrdSts(Constants.GRD_STS_WAITING);
            if (Constants.BID_PROJ_ON_STS_GRADING.equals(bidProjOn.getProjSts())) {
                bidProjOnExptGrdInf.setGrdTyp(Constants.GRAD_TYP_ONCE);
            } else {
                bidProjOnExptGrdInf.setGrdTyp(Constants.GRAD_TYP_TWICE);
            }
            bidProjOnExptGrdInf.setExptId(exptId);

            ExptInf exptInf = exptInfMapper.selectByPrimaryKey(exptId);
            if (exptInf == null || Constants.EFF_FLG_OFF.equals(exptInf.getEffFlg())) {
                String errMsg = "线上招标项目选择评标专家 - 根据专家ID查询专家信息无数据";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            bidProjOnExptGrdInf.setExptNam(exptInf.getExptNam());
            bidProjOnExptGrdInf.setExptTtl(exptInf.getExptTtl());
            bidProjOnExptGrdInf.setMobNbr(exptInf.getMobNbr());
            String grdCod = CryptUtils.getRandNum(4);
            bidProjOnExptGrdInf.setGrdKey(grdCod);
            bidProjOnExptGrdInf.setEffFlg(Constants.EFF_FLG_ON);
            bidProjOnExptGrdInf.setCrtUsr(realNam);
            bidProjOnExptGrdInf.setCrtTim(DateUtils.getCurrentTimeStamp());
            bidProjOnExptGrdInfMapper.insert(bidProjOnExptGrdInf);

            if (StringUtils.isEmpty(exptInf.getMobNbr())) {
                continue;
            }

            // TODO 发送短信邀请
//            SmsSndUtils.sendSmsForGrdBidProjOn(grdCod, exptInf, bidProjOn);
        }
        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getQotInf(Long id) throws Exception {
        logger.info("根据投标ID查看报价信息: id[{}]", id);

        // 参数处理
        if (id == null) {
            String errMsg = "根据投标ID查看报价信息 - 参数校验 - 不通过 - ID为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOnSplrTendInf bidProjOnSplrTendInf = bidProjOnSplrTendInfMapper.selectByPrimaryKey(id);
        if (bidProjOnSplrTendInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnSplrTendInf.getEffFlg())) {
            String errMsg = "根据投标ID查看报价信息 - 参数校验 - 不通过 - 跟据ID查询投标信息表无数据";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        QotInfForPchsIoDto qotInfForPchsIoDto = new QotInfForPchsIoDto();

        Long qotId = bidProjOnSplrTendInf.getQotId();
        if (qotId != null) {
            BidProjOnSplrQotInf qotInf = bidProjOnSplrQotInfMapper.selectByPrimaryKey(qotId);
            BidProjOnSplrQotDtl entity = new BidProjOnSplrQotDtl();
            entity.setQotId(qotId);
            entity.setEffFlg(Constants.EFF_FLG_ON);
            Map map = PageUtils.getQueryCondsMap(entity, 0, 0);
            List<BidProjOnSplrQotDtl> qotDtls = bidProjOnSplrQotDtlMapper.selectByMap(map);
            qotInfForPchsIoDto.setQotInf(qotInf);
            List<IoBidProjOnSplrQotDto> qotDtos = new ArrayList<>();
            for(BidProjOnSplrQotDtl bidProjOnSplrQotDtl :qotDtls){
                BidProjOnMatDtl matDtl = bidProjOnMatDtlMapper.selectByPrimaryKey(bidProjOnSplrQotDtl.getMatId());
                IoBidProjOnSplrQotDto qotDto = new IoBidProjOnSplrQotDto();
                qotDto.setDlvAdr(matDtl.getDlvAdr());
                qotDto.setDlvDte(DateUtils.date2SimpleString(matDtl.getDlvDte()));
                qotDto.setMatId(matDtl.getId());
                qotDto.setMatNam(matDtl.getMatNam());
                qotDto.setMatUnt(matDtl.getMatUnt());
                qotDto.setMatBnd(bidProjOnSplrQotDtl.getMatBnd());
                qotDto.setCurrTyp(bidProjOnSplrQotDtl.getCurrTyp());
                qotDto.setSplNum(bidProjOnSplrQotDtl.getSplNum());
                qotDto.setUntPri(bidProjOnSplrQotDtl.getUntPri());
                qotDto.setUntPriEcrp(bidProjOnSplrQotDtl.getUntPriEcrp());
                qotDto.setSplrId(bidProjOnSplrQotDtl.getSplrId());
                qotDto.setProjId(bidProjOnSplrQotDtl.getProjId());
                qotDto.setQotId(bidProjOnSplrQotDtl.getQotId());
                qotDto.setEffFlg(bidProjOnSplrQotDtl.getEffFlg());
                qotDto.setExRat(bidProjOnSplrQotDtl.getExRat());
                qotDto.setTendDlvDte(DateUtils.date2SimpleString(bidProjOnSplrQotDtl.getTendDlvDte()));
                qotDto.setId(bidProjOnSplrQotDtl.getId());
                qotDtos.add(qotDto);
            }
            qotInfForPchsIoDto.setQotDtls(qotDtos);
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_QOT);
            if (comParm == null) {
                String errMsg = "根据投标ID查看报价信息 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_QOT + "]";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            String atchPrefix = comParm.getParmVal();
            String refId = atchPrefix + qotId;
            Atch atch = new Atch();
            atch.setRefId(refId);

            map = PageUtils.getQueryCondsMap(atch, 0, 0);

            List<Atch> atches = atchMapper.selectByMap(map);
            qotInfForPchsIoDto.setAtches(atches);
        }

//        Long qot2Id = bidProjOnSplrTendInf.getQot2Id();
//        if (qot2Id != null) {
//            BidProjOnSplrQotInf qotInf2 = bidProjOnSplrQotInfMapper.selectByPrimaryKey(qot2Id);
//            BidProjOnSplrQotDtl entity = new BidProjOnSplrQotDtl();
//            entity.setQotId(qot2Id);
//            entity.setEffFlg(Constants.EFF_FLG_ON);
//            Map map = PageUtils.getQueryCondsMap(entity, 0, 0);
//            List<BidProjOnSplrQotDtl> qotDtls2 = bidProjOnSplrQotDtlMapper.selectByMap(map);
//            qotInfForPchsIoDto.setQotInf2(qotInf2);
//            qotInfForPchsIoDto.setQotDtls2(qotDtls2);
//        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, qotInfForPchsIoDto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto endGrd(Long projId) throws Exception {

        logger.info("结束评标, projId[{}]", projId);

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (projId == null || projId == 0L) {
            String errMsg = "结束评标 - 参数校验不通过 - 项目ID为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "结束评标 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (!Constants.BID_PROJ_ON_STS_GRADING.equals(bidProjOn.getProjSts()) && !Constants.BID_PROJ_ON_STS_QOT2_GRADING.equals(bidProjOn.getProjSts())) {
            String errMsg = "结束评标 - 业务校验 - 失败 - 项目状态异常 - projSts[" + bidProjOn.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOnExptGrdInf grdInfQuery = new BidProjOnExptGrdInf();
        grdInfQuery.setProjId(projId);
        grdInfQuery.setEffFlg(Constants.EFF_FLG_ON);
        if (Constants.BID_PROJ_ON_STS_GRADING.equals(bidProjOn.getProjSts())) {
            grdInfQuery.setGrdTyp(Constants.GRAD_TYP_ONCE);
        } else {
            grdInfQuery.setGrdTyp(Constants.GRAD_TYP_TWICE);
        }
        Map map = PageUtils.getQueryCondsMap(grdInfQuery, 0, 0);
        List<BidProjOnExptGrdInf> bidProjOnExptGrdInfs = bidProjOnExptGrdInfMapper.selectByMap(map);
        if (bidProjOnExptGrdInfs == null || bidProjOnExptGrdInfs.isEmpty()) {
            String errMsg = "结束评标 - 业务校验 - 失败 - 无相关专家评标信息";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (bidProjOnExptGrdInfs.stream()
                .anyMatch(grdInf -> !Constants.GRD_STS_DONE.equals(grdInf.getProjGrdSts()))) {
            String errMsg = "结束评标 - 业务校验 - 失败 - 尚有专家评标未完成";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        logger.info("3.1 更新项目状态至[24:评标结束]或[34:二次评标结束]");
        if (Constants.BID_PROJ_ON_STS_GRADING.equals(bidProjOn.getProjSts())) {
            bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_GRADED);
        } else {
            bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_QOT2_GRADED);
        }
        bidProjOn.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOn.setModTim(DateUtils.getCurrentTimeStamp());
        bidProjOn.setModUsr(realNam);

        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
        if (effRows != 1) {
            String errMsg = "更新线上招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(bidProjOn.getProjId());
        bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
        bidProjOnStsLog.setProjSts(bidProjOn.getProjSts());
        bidProjOnStsLog.setStsUpdTim(now);
        bidProjOnStsLog.setUpdUsr(realNam);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("2. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getTendSplrs(IoGetTendSplrsDto dto) throws Exception {

        logger.info("获取投标供应商, dto:" + dto);

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

        BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
        bidProjOnSplrTendInf.setProjId(projId);
        bidProjOnSplrTendInf.setBidDocSts(bidDocSts);

        Map map = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf, 0, 0);

        List<BidProjOnSplrTendInf> list = bidProjOnSplrTendInfMapper.selectByMap(map);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, list);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto startQot2(IoStartQot2Dto dto) throws Exception {

        logger.info("发起二次竞价");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = BidProjOnMngForPchsChecker.checkArgsForStartQot2(dto);
        if (!"".equals(checkRst)) {
            logger.error("发起二次竞价 - 参数校验不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(dto.getProjId());

        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "发起二次竞价 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + dto.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【评标结束】
        if (!Constants.BID_PROJ_ON_STS_GRADED.equals(entity.getProjSts())) {
            String errMsg = "发起二次竞价 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        // 3. 数据持久化
        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        // 赋值
        BeanUtils.copyProperties(entity, dto);
        // 新生成开标密钥
        entity.setOpenKey(CryptUtils.getRandNum(6));
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setEffFlg(Constants.EFF_FLG_ON);
        entity.setBidTwcFlg(Constants.BID_TWC_FLG_Y);
        entity.setProjSts(Constants.BID_PROJ_ON_STS_QOTING2);
        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "发起二次竞价 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(entity.getProjId());
        bidProjOnStsLog.setProjNam(entity.getProjNam());
        bidProjOnStsLog.setProjSts(entity.getProjSts());
        bidProjOnStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        bidProjOnStsLog.setUpdUsr(realNam);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "发起二次竞价 - 失败 - 写入线上招标项目状态记录表受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        String splrIdsStr = dto.getSplrIds();
        String[] splrIds = splrIdsStr.split(",");
        for (String splrIdStr : splrIds) {
            Long splrId = Long.valueOf(splrIdStr);
            BidProjOnSplrInvt bidProjOnSplrInvt = new BidProjOnSplrInvt();
            bidProjOnSplrInvt.setProjId(entity.getProjId());
            bidProjOnSplrInvt.setBidCntTyp(Constants.BID_CNT_TYP_SCD);
            bidProjOnSplrInvt.setBidFlg(Constants.BID_FLG_UNDO);
            bidProjOnSplrInvt.setSplrId(splrId);
            bidProjOnSplrInvt.setCrtUsr(realNam);
            bidProjOnSplrInvt.setCrtTim(DateUtils.getCurrentTimeStamp());
            bidProjOnSplrInvt.setEffFlg(Constants.EFF_FLG_ON);
            bidProjOnSplrInvtMapper.insert(bidProjOnSplrInvt);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(true, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getGrdInf(IoShowGrdInfDto dto, Integer pageNo, Integer pageSize) throws Exception {

        logger.info("获取評標信息 - 开始");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = BidProjOnMngForPchsChecker.checkArgsForGetGrdInf(dto);
        if (!"".equals(checkRst)) {
            logger.error("获取評標信息 - 参数校验不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(dto.getProjId());

        if (bidProjOn == null || !Constants.EFF_FLG_ON.equals(bidProjOn.getEffFlg())) {
            String errMsg = "获取評標信息 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + dto.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【可以查看一次评标信息的项目状态】
        String grdTyp = dto.getGrdTyp();
        if (Constants.GRAD_TYP_ONCE.equals(grdTyp)) {
            if (!BasicChecker.inArray(bidProjOn.getProjSts(), Constants.PROJ_STSES_OF_GET_GRD1_INF)) {
                String errMsg = "获取評標信息 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + bidProjOn.getProjSts() + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        } else {
            if (!BasicChecker.inArray(bidProjOn.getProjSts(), Constants.PROJ_STSES_OF_GET_GRD2_INF)) {
                String errMsg = "获取評標信息 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + bidProjOn.getProjSts() + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        // 3. 查询数据库
        BidProjOnExptGrdInf bidProjOnExptGrdInf = new BidProjOnExptGrdInf();
        bidProjOnExptGrdInf.setProjId(dto.getProjId());
        bidProjOnExptGrdInf.setEffFlg(Constants.EFF_FLG_ON);
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }
        Integer start = (pageNo - 1) * pageSize + 1;
        Integer to = pageSize * pageNo;
        Map map = PageUtils.getQueryCondsMap(bidProjOnExptGrdInf, start, to);
        List<BidProjOnExptGrdInf> list = bidProjOnExptGrdInfMapper.selectByMap(map);

        Integer count = bidProjOnExptGrdInfMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);

    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getGrdDtls(Long grdId, Integer pageNo, Integer pageSize) throws Exception {
        logger.info("根据评标ID查看评分详情");

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

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");


        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据库查询 - 开始");
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

        PagedResult result = new PagedResult(list, count);

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据库查询 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

   /* @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto appAwd(HttpServletRequest request,IoAppAwdDto dto) throws Exception {
        logger.info("定标提交BPM审批");

        //参数校验
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        Long projId = dto.getProjId();
        if (projId == null || projId == 0L) {
            String errMsg = "定标提交BPM审批 - 失败 - 参数校验不通过 - 项目ID为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        userinfo.getManageCompanies();

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询评标信息");
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "定标提交BPM审批 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        String projSts = bidProjOn.getProjSts();
        if (!Constants.BID_PROJ_ON_STS_EXPT_ADITING.equals(projSts)) {
            String errMsg = "定标提交BPM审批 - 项目信息状态不为专家审批中 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOnExptGrdInf bidProjOnExptGrdInf = new BidProjOnExptGrdInf();
        bidProjOnExptGrdInf.setProjId(projId);
        bidProjOnExptGrdInf.setEffFlg(Constants.EFF_FLG_ON);
        Map grdInfMap = PageUtils.getQueryCondsMap(bidProjOnExptGrdInf, 0, 0);
        List<BidProjOnExptGrdInf> bidProjOnExptGrdInfs = bidProjOnExptGrdInfMapper.selectByMap(grdInfMap);
        if (bidProjOnExptGrdInfs.stream()
                .anyMatch(grdInf -> Constants.IS_AGREED_0.equals(grdInf.getIsAgreed()))) {
            String errMsg = "定标提交BPM审批 - 业务校验 - 失败 - 尚有专家未审批";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (userinfo.getCompany() == null || StringUtils.isEmpty(userinfo.getCompany().getDept_code())) {
            String errMsg = "定标提交BPM审批 - 失败 - 该采购员所属公司为空，无法提交BPM审批";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        String bpmDeptCode = userinfo.getCompany().getDept_code();
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        logger.info("3.1 查询该项目下所有物料明细");
        BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(map);
        if (matDtls == null || matDtls.isEmpty()) {
            String errMsg = "定标提交BPM审批 - 业务校验 - 异常 - 该项目无物料信息 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        Map<String, BidProjOnMatDtl> matMap = new HashMap<>();
        for (BidProjOnMatDtl dtl : matDtls) {
            matMap.put(String.valueOf(dtl.getId()), dtl);
        }
        BidProjOnWinDtl bidProjOnWinDtl = new BidProjOnWinDtl();
        bidProjOnWinDtl.setEffFlg(Constants.EFF_FLG_ON);
        bidProjOnWinDtl.setProjId(projId);
        Map bidProjOnWinDtlMap = PageUtils.getQueryCondsMap(bidProjOnWinDtl, 0, 0);
        List<BidProjOnWinDtl> bidProjOnWinDtls = bidProjOnWinDtlMapper.selectByMap(bidProjOnWinDtlMap);

        logger.info("3.3 提交BPM审批");

        BidProjOnExptGrdInf exptGrdInfEntity = new BidProjOnExptGrdInf();
        exptGrdInfEntity.setEffFlg(Constants.EFF_FLG_ON);
        exptGrdInfEntity.setProjId(projId);
        exptGrdInfEntity.setProjGrdSts(Constants.PROJ_GRD_STS_DONE);
        Map exptGrdInfMap = PageUtils.getQueryCondsMap(exptGrdInfEntity, 0, 0);
        List<BidProjOnExptGrdInf> exptGrdInfs = bidProjOnExptGrdInfMapper.selectByMap(exptGrdInfMap);
        StringBuilder grdExpts = new StringBuilder("");
        if (exptGrdInfs != null && !exptGrdInfs.isEmpty()) {
            exptGrdInfs.stream().forEach(exptGrdInf -> grdExpts.append(exptGrdInf.getExptNam()).append(";"));
        }

        String token = BpmFileUtils.getToken(userinfo.getUsername());
        String rootPath = request.getServletContext().getRealPath("/");
        String basePath = new File(rootPath + "..").getCanonicalPath();
        String appAwdMemo="申请定标";
        String fileName = getOpenRcdFile(request, bidProjOn);
        String filePath = basePath + File.separator + Constants.ATCH_FILE_PATH + File.separator + fileName;
        String fileNameBpm = bidProjOn.getProjNam() + "-开标记录表-" + DateUtils.getTimeStampLong(DateUtils.getCurrentTimeStamp()) + ".pdf";
        Long field = BpmFileUtils.uploadAttachment(userinfo.getUsername(), token, filePath, fileNameBpm);
        List<Long> fields = new ArrayList<>();
        fields.add(field);

        String bpmSeqNo = BpmUtils.getBpmSeqNo(Constants.PROJ_TYP_JJ);
        String bpmBody = bidProjOnBpmAppFactory.getAwdBpmBody(bpmSeqNo, bidProjOn, bidProjOnWinDtls, matMap,appAwdMemo , grdExpts.toString());
        String templateCode = bpmDeptCode + Constants.BPM_APP_TYP_JJ_AWD_SUFFIX;
        boolean subSucFlg = BpmUtils.subApp(bpmBody, templateCode, "网上竞价项目决标申请", "", fields);
        if (!subSucFlg) {
            String errMsg = "网上竞价项目决标申请 - 提交BPM审批失败";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        logger.info("3.4 将项目状态置为【决标申请中】");
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOn.setBpmFinSeq(bpmSeqNo);
        bidProjOn.setModUsr(realNam);
        bidProjOn.setModTim(now);
        bidProjOn.setEffFlg(Constants.EFF_FLG_ON);
        bidProjOn.setAppAwdMemo(appAwdMemo);
        bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_AWDING);
        bidProjOn.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
        if (effRows != 1) {
            String errMsg = "更新线上招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(bidProjOn.getProjId());
        bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
        bidProjOnStsLog.setProjSts(Constants.BID_PROJ_ON_STS_AWDING);
        bidProjOnStsLog.setStsUpdTim(now);
        bidProjOnStsLog.setUpdUsr(realNam);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }
*/

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto appAwd(HttpServletRequest request, IoAppAwdDto dto) throws Exception {
        logger.info("申请决标, dto: [{}]", dto);

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = BidProjOnMngForPchsChecker.checkArgsForAppAwd(dto);
        if (!"".equals(checkRst)) {
            logger.error("申请决标 - 参数校验不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }

        userinfo.getManageCompanies();

        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        Long projId = dto.getProjId();
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || !Constants.EFF_FLG_ON.equals(bidProjOn.getEffFlg())) {
            String errMsg = "申请决标 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + dto.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【评标结束】或【二次评标结束】,
        String projSts = bidProjOn.getProjSts();
        if (!Constants.BID_PROJ_ON_STS_GRADED.equals(projSts) && !Constants.BID_PROJ_ON_STS_QOT2_GRADED.equals(projSts)
                && !(Constants.GRD_RUL_0.equals(bidProjOn.getGrdRul()) && Constants.BID_PROJ_ON_STS_OPENED.equals(projSts))) {
            String errMsg = "申请决标 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        if (userinfo.getCompany() == null || StringUtils.isEmpty(userinfo.getCompany().getDept_code())) {
            String errMsg = "申请决标 - 失败 - 该采购员所属公司为空，无法提交BPM审批";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        String bpmDeptCode = userinfo.getCompany().getDept_code();
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        logger.info("3.1 查询该项目下所有物料明细");
        BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(map);
        if (matDtls == null || matDtls.isEmpty()) {
            String errMsg = "申请决标 - 业务校验 - 异常 - 该项目无物料信息 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Map<String, BidProjOnMatDtl> matMap = new HashMap<>();
        for (BidProjOnMatDtl dtl : matDtls) {
            matMap.put(String.valueOf(dtl.getId()), dtl);
        }

        logger.info("3.2 新增线上招标项目中标明细");
        // 删除已存在的中标明细
        bidProjOnWinDtlMapper.deleteByProjId(projId);
        // 新增中标明细
        List<IoAppAwdDtlDto> appAwdDtlDtos = dto.getAppAwdDtls();
        for (IoAppAwdDtlDto dtlDto : appAwdDtlDtos) {
            BidProjOnWinDtl winDtl = new BidProjOnWinDtl();
            winDtl.setCrtTim(DateUtils.getCurrentTimeStamp());
            winDtl.setCrtUsr(realNam);
            winDtl.setEffFlg(Constants.EFF_FLG_ON);
            BidProjOnMatDtl matInf = matMap.get(String.valueOf(dtlDto.getMatId()));
            winDtl.setMatId(matInf.getId());
            winDtl.setMatCod(matInf.getMatCod());
            winDtl.setMatNam(matInf.getMatNam());
            winDtl.setMatUnt(matInf.getMatUnt());
            winDtl.setPchsNum(dtlDto.getPchsNum());
            winDtl.setProjId(projId);
            winDtl.setSplrId(dtlDto.getSplrId());
            winDtl.setSplrNam(dtlDto.getSplrNam());
            winDtl.setUntPri(dtlDto.getUntPri());
            winDtl.setTtlPri(dtlDto.getTtlPri());
            winDtl.setTendDlvDte(dtlDto.getTendDlvDte());
            winDtl.setCurrTyp(dtlDto.getCurrTyp());
            winDtl.setExRat(dtlDto.getExRat());
            winDtl.setMatBnd(dtlDto.getMatBnd());
            winDtl.setMemo(dtlDto.getMemo());
            int effRows = bidProjOnWinDtlMapper.insert(winDtl);
            if (effRows != 1) {
                String errMsg = "新增线上招标项目中标明细 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        }

        logger.info("3.3 提交BPM审批");

        BidProjOnExptGrdInf exptGrdInfEntity = new BidProjOnExptGrdInf();
        exptGrdInfEntity.setEffFlg(Constants.EFF_FLG_ON);
        exptGrdInfEntity.setProjId(projId);
        exptGrdInfEntity.setProjGrdSts(Constants.PROJ_GRD_STS_DONE);
        Map exptGrdInfMap = PageUtils.getQueryCondsMap(exptGrdInfEntity, 0, 0);
        List<BidProjOnExptGrdInf> exptGrdInfs = bidProjOnExptGrdInfMapper.selectByMap(exptGrdInfMap);
        StringBuilder grdExpts = new StringBuilder("");
        if (exptGrdInfs != null && !exptGrdInfs.isEmpty()) {
            exptGrdInfs.stream().forEach(exptGrdInf -> grdExpts.append(exptGrdInf.getExptNam()).append(";"));
        }

        String token = BpmFileUtils.getToken(userinfo.getUsername());
        String rootPath = request.getServletContext().getRealPath("/");
        String basePath = new File(rootPath + "..").getCanonicalPath();

        String fileName = getOpenRcdFile(request, bidProjOn);
        String filePath = basePath + File.separator + Constants.ATCH_FILE_PATH + File.separator + fileName;
        String fileNameBpm = bidProjOn.getProjNam() + "-开标记录表-" + DateUtils.getTimeStampLong(DateUtils.getCurrentTimeStamp()) + ".pdf";
        Long field = BpmFileUtils.uploadAttachment(userinfo.getUsername(), token, filePath, fileNameBpm);
        List<Long> fields = new ArrayList<>();
        fields.add(field);

        String bpmSeqNo = BpmUtils.getBpmSeqNo(Constants.PROJ_TYP_JJ);
        String bpmBody = bidProjOnBpmAppFactory.getAwdBpmBody(bpmSeqNo, bidProjOn, dto.getAppAwdDtls(), matMap, dto.getAppAwdMemo(), grdExpts.toString());
        String templateCode = bpmDeptCode + Constants.BPM_APP_TYP_JJ_AWD_SUFFIX;
        boolean subSucFlg = BpmUtils.subApp(bpmBody, templateCode, "网上竞价项目决标申请","", fields);
        if (!subSucFlg) {
            String errMsg = "网上竞价项目决标申请 - 提交BPM审批失败";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.4 将项目状态置为【决标申请中】");
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOn.setBpmFinSeq(bpmSeqNo);
        bidProjOn.setModUsr(realNam);
        bidProjOn.setModTim(now);
        bidProjOn.setEffFlg(Constants.EFF_FLG_ON);
        bidProjOn.setAppAwdMemo(dto.getAppAwdMemo());
        bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_AWDING);
        bidProjOn.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
        if (effRows != 1) {
            String errMsg = "更新线上招标项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(bidProjOn.getProjId());
        bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
        bidProjOnStsLog.setProjSts(Constants.BID_PROJ_ON_STS_AWDING);
        bidProjOnStsLog.setStsUpdTim(now);
        bidProjOnStsLog.setUpdUsr(realNam);

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

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
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || !Constants.EFF_FLG_ON.equals(bidProjOn.getEffFlg())) {
            String errMsg = "查看招标结果 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【40:决标审批中】或【50:决标审批通过】
        String projSts = bidProjOn.getProjSts();
        if (!Constants.BID_PROJ_ON_STS_AWDING.equals(projSts) && !Constants.BID_PROJ_ON_STS_AWD_ACCEPTED.equals(projSts)) {
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
            bidProjOnAwdMatDtlDto.setTtlPri(winDtl.getTtlPri());
            bidProjOnAwdMatDtlDto.setUntPri(winDtl.getUntPri());
            bidProjOnAwdMatDtlDto.setProjId(winDtl.getProjId());
            bidProjOnAwdMatDtlDto.setDlvDte(DateUtils.date2SimpleString(matInf.getDlvDte()));
            bidProjOnAwdMatDtlDtos.add(bidProjOnAwdMatDtlDto);
        }
        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 查询数据库 - 结束，duration[" + durationC + "ms]");

        if (list == null || list.isEmpty()) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        } else {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, bidProjOnAwdMatDtlDtos);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto pubRst(Long projId) throws Exception {

        logger.info("发布线上招标项目结果");

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

        BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(projId);
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "发布线上招标项目结果 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【招标申请审批通过】或【废标审批通过】
        if (!Constants.BID_PROJ_ON_STS_AWD_ACCEPTED.equals(entity.getProjSts()) && !Constants.BID_PROJ_ON_STS_RPL.equals(entity.getProjSts())) {
            String errMsg = "发布线上招标项目结果 - 失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        entity.setBidRstPubFlg(Constants.BID_RST_PUB_FLG_ON);
        entity.setRstPubTim(DateUtils.getCurrentTimeStamp());
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新项目信息为失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto pubRplRst(IoPubRplRstDto dto) throws Exception {
        logger.info("发布线上招标项目废标结果");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        Long projId = dto.getProjId();
        if (projId == null || projId.equals(0L)) {
            String errMsg = "参数校验 - 不通过 - projId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        String rplNtcInf = dto.getRplRstInf();
        if (StringUtils.isEmpty(rplNtcInf)) {
            String errMsg = "参数校验 - 不通过 - rplNtcInf不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(projId);
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "发布线上招标项目废标结果 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【-20已废标】
        if (!Constants.BID_PROJ_ON_STS_RPL.equals(entity.getProjSts())) {
            String errMsg = "发布线上招标项目废标结果 - 失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        entity.setRplNtcInf(rplNtcInf);
        entity.setBidRstPubFlg(Constants.BID_RST_PUB_FLG_ON);
        entity.setRstPubTim(DateUtils.getCurrentTimeStamp());
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        int effRows = bidProjOnMapper.updateByPrimaryKeyWithBLOBs(entity);
        if (effRows != 1) {
            String errMsg = "更新项目信息为失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getAppAwdInf(Long projId) throws Exception {

        logger.info("获取申请决标相关信息: projId:[" + projId + "]");

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

        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        BidProjOn entity = bidProjOnMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "获取申请决标相关信息 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(matDtlMap);

       /* BidProjOnSplrTendInf splrTendInfQuery = new BidProjOnSplrTendInf();
        splrTendInfQuery.setProjId(projId);
        splrTendInfQuery.setEffFlg(Constants.EFF_FLG_ON);
        splrTendInfQuery.setBidDocSts(Constants.BID_DOC_STS_ACCEPTED);
        Map map = PageUtils.getQueryCondsMap(splrTendInfQuery, 0, 0);
        List<BidProjOnSplrTendInf> tendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);
        if (tendInfs == null || tendInfs.isEmpty()) {
            String errMsg = "获取申请决标相关信息 - 根据项目ID查询供应商投标信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");*/

        // 获取投标供应商
        List<String> bidDocStses = Arrays.asList(new String[]{Constants.BID_DOC_STS_ACCEPTED, Constants.BID_DOC_STS_AWD, Constants.BID_DOC_STS_UNAWD});
        BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
        bidProjOnSplrTendInf.setProjId(projId);
        bidProjOnSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf, 0, 0);
        map.put("bidDocStses", bidDocStses);
        map.put("qotIdNotNul", "true");
        List<BidProjOnSplrTendInf> tendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);
        if (tendInfs == null || tendInfs.isEmpty()) {
            String errMsg = "获取开标记录表 - 失败：投标供应商为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }

        Map<Long, Splr> splrMap = tendInfs.stream().collect(
                Collectors.toMap(BidProjOnSplrTendInf::getSplrId,
                        tendInf -> splrMapper.selectByPrimaryKey(tendInf.getSplrId())));

        BidProjOnSplrQotDtl qotDtlQuery = new BidProjOnSplrQotDtl();
        qotDtlQuery.setProjId(projId);
        qotDtlQuery.setEffFlg(Constants.EFF_FLG_ON);
        map = PageUtils.getQueryCondsMap(qotDtlQuery, 0, 0);
        List<BidProjOnSplrQotDtl> bidProjOnSplrQotDtls = bidProjOnSplrQotDtlMapper.selectByMap(map);
        List<BidProjOnSplrQotDtlIoDto> qotDtlIoDtos = bidProjOnSplrQotDtls.parallelStream()
                .filter(dtl -> dtl.getUntPri() != null && !dtl.getUntPri().equals(BigDecimal.ZERO))
                .filter(dtl -> dtl.getSplNum() != null && !dtl.getSplNum().equals("0"))
                .map(dtl -> {
                    BidProjOnSplrQotDtlIoDto splrQotDtlIoDto = new BidProjOnSplrQotDtlIoDto();
                    BeanUtils.copyProperties(splrQotDtlIoDto, dtl);
                    splrQotDtlIoDto.setTrpPrice(dtl.getUntPri().multiply(new BigDecimal(dtl.getExRat())));
                    Splr splr = splrMap.get(dtl.getSplrId());
                    splrQotDtlIoDto.setFullNam(splr.getFullNam());
                    return splrQotDtlIoDto;
                })
                .collect(Collectors.toList());

        List<QueryAppAwdInfDtlIoDto> queryAppAwdInfDtlIoDtos = new ArrayList<>();
        if (matDtls != null || matDtls.size() > 0) {
            for (BidProjOnMatDtl m : matDtls) {
                QueryAppAwdInfDtlIoDto queryAppAwdInfDtlIoDto = new QueryAppAwdInfDtlIoDto();
                queryAppAwdInfDtlIoDto.setMatDtl(m);
                queryAppAwdInfDtlIoDto.setQotDtls(getQotDtls(m.getId(), qotDtlIoDtos));
                queryAppAwdInfDtlIoDtos.add(queryAppAwdInfDtlIoDto);
            }
        }

        QueryAppAwdInfIoDto queryAppAwdInfIoDto = new QueryAppAwdInfIoDto();
        queryAppAwdInfIoDto.setBidProjOn(entity);
        queryAppAwdInfIoDto.setAppAwdInfDtls(queryAppAwdInfDtlIoDtos);
        queryAppAwdInfIoDto.setMatTypDesc(matFactory.getMatTypDesc(entity.getMatTyp()));

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, queryAppAwdInfIoDto);
    }


    private List<BidProjOnSplrQotDtlIoDto> getQotDtls(Long matId, List<BidProjOnSplrQotDtlIoDto> dtls) {
        if (StringUtils.isEmpty(String.valueOf(matId)) || dtls == null || dtls.isEmpty()) {
            return Collections.emptyList();
        }

        return dtls.parallelStream()
                .filter(dtl -> dtl.getMatId().equals(matId))
                .sorted(Comparator.comparing(BidProjOnSplrQotDtlIoDto::getTrpPrice))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getReqMatInfs(String orgIdsStr, Integer pageNo, Integer pageSize) throws Exception {

        CurrentUserInfo userInfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        logger.info("获取需求中的物料信息, orgIdsStr[{}]", orgIdsStr);

        List<CurrentUserInfoFactory> companies = userInfo.getManageCompanies();

        if (companies == null || companies.isEmpty()) {
            logger.error("获取需求中的物料信息 - 失败 - 该采购员无所辖公司");
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        }

        if (StringUtils.isEmpty(orgIdsStr)) {
            logger.error("获取需求中的物料信息 - 失败 - 栏位检核失败，orgIdsStr不得为空");
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        }

        final List<Long> orgIds = companies.stream()
                .map(currentUserInfoFactory -> Long.valueOf(currentUserInfoFactory.getId()))
                .filter(orgId ->
                        Arrays.asList(orgIdsStr.split(","))
                                .stream()
                                .anyMatch(oid -> oid.equals(orgId.toString())))
                .collect(Collectors.toList());

        if (orgIds == null || orgIds.isEmpty()) {
            String errMsg = "获取需求中的物料信息 - 失败 - 栏位检核失败，所选公司不在所辖公司内";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        }

        List<String> bukrses = companies.stream()
                .filter(c ->
                        orgIds.stream().anyMatch(oid -> oid.equals(Long.valueOf(c.getId()))))
                .map(c -> c.getDept_code()).collect(Collectors.toList());


        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize + 1;

        Integer to = pageSize * pageNo;

        Map map = new HashMap();
        map.put("bukrses", bukrses);
        map.put("userName", userInfo.getUsername());
        map.put("start", start);
        map.put("limit", to);

        List<Map> requisitions = requisitionMapper.selectByMap(map);
        Integer count = requisitionMapper.countByMap(map);
        if (requisitions == null || requisitions.isEmpty()) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        }

        List<MatInfIoDto> matInfIoDtos = requisitions.parallelStream().map(req -> {
            MatInfIoDto matInf = new MatInfIoDto();
            matInf.setReqId(Long.valueOf(((BigDecimal) req.get("REQ_ID")).toString()));
            matInf.setMatCod((String) req.get("MATNR"));
            matInf.setMatNam((String) req.get("TXZ01"));
            matInf.setMatSpft((String) req.get("UNITNAME"));
            matInf.setMatTyp((String) req.get("MATKL"));
            matInf.setMatTypDsc((String) req.get("WGBEZ"));
            matInf.setMatUnt((String) req.get("UNITNAME"));
            matInf.setPchsNum(req.get("MENGE").toString());
            matInf.setDlvAdr((String) req.get("BUTXT"));
            String lfDat = (String) req.get("LFDAT");
            Timestamp dlvDte = null;
            if (!StringUtils.isEmpty(lfDat)) {
                try {
                    dlvDte = DateUtils.string2timestamp(lfDat);
                } catch (Exception e) {
                    dlvDte = null;
                }
            }
            matInf.setDlvDte(dlvDte);
            return matInf;
        }).collect(Collectors.toList());

        PagedResult result = new PagedResult(matInfIoDtos, count);
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getOpenRcds(Long projId) throws Exception {

        logger.info("获取开标记录表, projId[{}]", projId);

        // 参数处理
        if (projId == null || projId.equals(0L)) {
            String errMsg = "获取开标记录表 - 失败：projId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOn projInf = bidProjOnMapper.selectByPrimaryKey(projId);
        if (projInf == null || !Constants.EFF_FLG_ON.equals(projInf.getEffFlg())) {
            String errMsg = "获取开标记录表 - 失败：项目信息不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }

        // 获取物料信息
        BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(matDtlMap);
        if (matDtls == null || matDtls.isEmpty()) {
            String errMsg = "获取开标记录表 - 失败：物料信息为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }

        // 获取投标供应商
        List<String> bidDocStses = Arrays.asList(new String[]{Constants.BID_DOC_STS_ACCEPTED, Constants.BID_DOC_STS_AWD, Constants.BID_DOC_STS_UNAWD});
        BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
        bidProjOnSplrTendInf.setProjId(projId);
        bidProjOnSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf, 0, 0);
        map.put("bidDocStses", bidDocStses);
        map.put("qotIdNotNul", "true");
        List<BidProjOnSplrTendInf> tendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);
        if (tendInfs == null || tendInfs.isEmpty()) {
            String errMsg = "获取开标记录表 - 失败：投标供应商为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }
        // 获取报价信息
        BidProjOnSplrQotDtl qotDtl = new BidProjOnSplrQotDtl();
        qotDtl.setProjId(projId);
        qotDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map qotDtlMap = PageUtils.getQueryCondsMap(qotDtl, 0, 0);
        List<BidProjOnSplrQotDtl> qotDtls = bidProjOnSplrQotDtlMapper.selectByMap(qotDtlMap);
        if (qotDtls == null || qotDtls.isEmpty()) {
            String errMsg = "获取开标记录表 - 失败：报价信息为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }
        // 为提高效率，将报价信息转化为map方便查询，键为 物料ID-供应商ID
        Map<String, BidProjOnSplrQotDtl> qdMap = new HashMap<>();
        qotDtls.stream().forEach(q -> {
            String key = q.getMatId() + "-" + q.getSplrId();
            qdMap.put(key, q);
        });

        List<OpenRcdIoDto> openRcdIoDtos = new ArrayList<>();
        matDtls.stream().forEach(m -> {
            OpenRcdIoDto openRcdIoDto = new OpenRcdIoDto();
            Long matId = m.getId();
            openRcdIoDto.setMatId(matId);
            openRcdIoDto.setMatCod(m.getMatCod());
            openRcdIoDto.setMatNam(m.getMatNam());
            openRcdIoDto.setMatUnt(m.getMatUnt());
            openRcdIoDto.setMatMemo(m.getMemo());
            openRcdIoDto.setDlvAdr(m.getDlvAdr());
            openRcdIoDto.setDlvDte(m.getDlvDte().toString());
            List<OpenRcdSplrQotIoDto> splrQotIoDtos = new ArrayList<>();
            tendInfs.stream().forEach(t -> {
                String key = matId + "-" + t.getSplrId();
                BidProjOnSplrQotDtl qd = qdMap.get(key);
                OpenRcdSplrQotIoDto openRcdSplrQotIoDto = new OpenRcdSplrQotIoDto();
                openRcdSplrQotIoDto.setSplrId(t.getSplrId());
                openRcdSplrQotIoDto.setPrice(qd.getUntPri());
                openRcdSplrQotIoDto.setSplNum(qd.getSplNum());
                openRcdSplrQotIoDto.setSplrNam(t.getSplrNam());
                openRcdSplrQotIoDto.setCurrTyp(qd.getCurrTyp());
                openRcdSplrQotIoDto.setExRat(qd.getExRat());
                openRcdSplrQotIoDto.setTendDlvDte(qd.getTendDlvDte());
                openRcdSplrQotIoDto.setMatBnd(qd.getMatBnd());
                splrQotIoDtos.add(openRcdSplrQotIoDto);
            });
            openRcdIoDto.setSplrQotIoDtos(splrQotIoDtos);
            openRcdIoDtos.add(openRcdIoDto);
        });

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, openRcdIoDtos);

    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public ResponseEntity<byte[]> exportOpenInf(HttpServletRequest request, Long projId) throws Exception {
        logger.info("导出开标记录表, projId[{}]", projId);

        // 参数处理
        if (projId == null || projId.equals(0L)) {
            String errMsg = "导出开标记录表 - 失败：projId不得为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        BidProjOn projInf = bidProjOnMapper.selectByPrimaryKey(projId);
        if (projInf == null || !Constants.EFF_FLG_ON.equals(projInf.getEffFlg())) {
            String errMsg = "导出开标记录表 - 失败：项目信息不存在";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 获取物料信息
        BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(matDtlMap);
        if (matDtls == null || matDtls.isEmpty()) {
            String errMsg = "导出开标记录表 - 失败：物料信息为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 获取投标供应商
        List<String> bidDocStses = Arrays.asList(new String[]{Constants.BID_DOC_STS_ACCEPTED, Constants.BID_DOC_STS_AWD, Constants.BID_DOC_STS_UNAWD});
        BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
        bidProjOnSplrTendInf.setProjId(projId);
        bidProjOnSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf, 0, 0);
        map.put("bidDocStses", bidDocStses);
        map.put("qotIdNotNul", "true");
        List<BidProjOnSplrTendInf> tendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);
        if (tendInfs == null || tendInfs.isEmpty()) {
            String errMsg = "导出开标记录表 - 失败：投标供应商为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 获取报价信息
        BidProjOnSplrQotDtl qotDtl = new BidProjOnSplrQotDtl();
        qotDtl.setProjId(projId);
        qotDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map qotDtlMap = PageUtils.getQueryCondsMap(qotDtl, 0, 0);
        List<BidProjOnSplrQotDtl> qotDtls = bidProjOnSplrQotDtlMapper.selectByMap(qotDtlMap);
        if (qotDtls == null || qotDtls.isEmpty()) {
            String errMsg = "导出开标记录表 - 失败：报价信息为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        // 为提高效率，将报价信息转化为map方便查询，键为 物料ID-供应商ID
        Map<String, BidProjOnSplrQotDtl> qdMap = new HashMap<>();
        qotDtls.stream().forEach(q -> {
            String key = q.getMatId() + "-" + q.getSplrId();
            qdMap.put(key, q);
        });

        List<OpenRcdIoDto> openRcdIoDtos = new ArrayList<>();
        matDtls.stream().forEach(m -> {
            OpenRcdIoDto openRcdIoDto = new OpenRcdIoDto();
            String matCod = m.getMatCod();
            openRcdIoDto.setMatCod(matCod);
            openRcdIoDto.setMatNam(m.getMatNam());
            openRcdIoDto.setMatUnt(m.getMatUnt());
            openRcdIoDto.setMatMemo(m.getMemo());
            List<OpenRcdSplrQotIoDto> splrQotIoDtos = new ArrayList<>();
            tendInfs.stream().forEach(t -> {
                String key = m.getId() + "-" + t.getSplrId();
                BidProjOnSplrQotDtl qd = qdMap.get(key);
                OpenRcdSplrQotIoDto openRcdSplrQotIoDto = new OpenRcdSplrQotIoDto();
                openRcdSplrQotIoDto.setPrice(qd.getUntPri());
                openRcdSplrQotIoDto.setSplNum(qd.getSplNum());
                openRcdSplrQotIoDto.setSplrNam(t.getSplrNam());
                openRcdSplrQotIoDto.setCurrTyp(qd.getCurrTyp());
                openRcdSplrQotIoDto.setExRat(qd.getExRat());
                splrQotIoDtos.add(openRcdSplrQotIoDto);
            });
            openRcdIoDto.setSplrQotIoDtos(splrQotIoDtos);
            openRcdIoDtos.add(openRcdIoDto);
        });


        // 统计供应商总价
        List<BigDecimal> ttlPrices = new ArrayList<>();
        for (int i = 0; i < tendInfs.size(); i++) {
            BidProjOnSplrTendInf tendInf = tendInfs.get(i);
            BigDecimal ttlPri = new BigDecimal(0);
            for (BidProjOnSplrQotDtl qd : qotDtls) {
                if (tendInf.getSplrId().equals(qd.getSplrId()) && Constants.EFF_FLG_ON.equals(qd.getEffFlg())) {
                    BigDecimal price = qd.getUntPri();
                    if (price != null) {
                        BigDecimal exRat = new BigDecimal(1);
                        String exRatStr = qd.getExRat();
                        if (!StringUtils.isEmpty(exRatStr)) {
                            exRat = new BigDecimal(exRatStr);
                        }
                        ttlPri = price.multiply(exRat).multiply(new BigDecimal(qd.getSplNum())).add(ttlPri).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    }
                }
            }
            ttlPrices.add(ttlPri);
        }


        String rootPath = request.getServletContext().getRealPath("/");
        String basePath = new File(rootPath + "..").getCanonicalPath();
        String filename = projInf.getProjNam() + "-开标记录表-" + DateUtils.getTimeStampLong(DateUtils.getCurrentTimeStamp()) + ".pdf";
//        String filename = "xxxxxxxxxxx" + "-开标记录表-" + DateUtils.getTimeStampLong(DateUtils.getCurrentTimeStamp()) + ".pdf";
        String filePath = basePath + File.separator + filename;
        // 1.新建document对象
        Document document = new Document();
        Rectangle pageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        pageSize.rotate();
        document.setPageSize(pageSize);

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        File file = new File(filePath);
        FileOutputStream outputStream = new FileOutputStream(file);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);

        // 3.打开文档
        document.open();
        //中文字体,解决中文不能显示问题
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font baseFont = new Font(bfChinese);
        baseFont.setSize(11f);
        // 4.添加一个内容段落

        Font headFont = new Font(bfChinese);
        headFont.setSize(24f);
        Paragraph title = new Paragraph("开标记录表", headFont);
        title.setAlignment(Element.ALIGN_MIDDLE);
        title.setSpacingBefore(10f); // 前间距
        document.add(title);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(60); // 宽度100%填充
        table.setSpacingBefore(10f); // 前间距
        table.setSpacingAfter(10f); // 后间距

        List<PdfPRow> listRow = table.getRows();
        //设置列宽
        float[] columnWidths = {2f, 5f, 2f, 4f};
        table.setWidths(columnWidths);

        // 基本信息 行1
        float tableHeight = 30f;
        PdfPCell basicCells1[] = new PdfPCell[4];
        PdfPRow basicRow1 = new PdfPRow(basicCells1);
        basicCells1[0] = new PdfPCell(new Paragraph("项目名称: ", baseFont));
        basicCells1[0].setBorderColor(BaseColor.WHITE);
        basicCells1[0].setFixedHeight(tableHeight);
        basicCells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells1[0].setHorizontalAlignment(Element.ALIGN_RIGHT);//水平居右

        basicCells1[1] = new PdfPCell(new Paragraph(projInf.getProjNam(), baseFont));
        basicCells1[1].setBorderColor(BaseColor.WHITE);
        basicCells1[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells1[1].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居左
        basicCells1[1].setFixedHeight(tableHeight);

        basicCells1[2] = new PdfPCell(new Paragraph("项目编号: ", baseFont));
        basicCells1[2].setBorderColor(BaseColor.WHITE);
        basicCells1[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells1[2].setHorizontalAlignment(Element.ALIGN_RIGHT);//水平居右
        basicCells1[2].setFixedHeight(tableHeight);

        basicCells1[3] = new PdfPCell(new Paragraph(projInf.getProjNbr(), baseFont));
        basicCells1[3].setBorderColor(BaseColor.WHITE);
        basicCells1[3].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells1[3].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居左
        basicCells1[3].setFixedHeight(tableHeight);

        PdfPCell basicCells2[] = new PdfPCell[4];
        PdfPRow basicRow2 = new PdfPRow(basicCells2);
        basicCells2[0] = new PdfPCell(new Paragraph("开标地点: ", baseFont));
        basicCells2[0].setBorderColor(BaseColor.WHITE);
        basicCells2[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells2[0].setHorizontalAlignment(Element.ALIGN_RIGHT);//水平居右
        basicCells2[0].setFixedHeight(tableHeight);

        basicCells2[1] = new PdfPCell(new Paragraph(projInf.getBidDptAddr(), baseFont));
        basicCells2[1].setBorderColor(BaseColor.WHITE);
        basicCells2[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells2[1].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居左
        basicCells2[1].setFixedHeight(tableHeight);

        basicCells2[2] = new PdfPCell(new Paragraph("开标时间: ", baseFont));
        basicCells2[2].setBorderColor(BaseColor.WHITE);
        basicCells2[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells2[2].setHorizontalAlignment(Element.ALIGN_RIGHT);//水平居右
        basicCells2[2].setFixedHeight(tableHeight);

        basicCells2[3] = new PdfPCell(new Paragraph(DateUtils.timestamp2String(projInf.getBidOpenTim()), baseFont));
        basicCells2[3].setBorderColor(BaseColor.WHITE);
        basicCells2[3].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells2[3].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居左
        basicCells2[3].setFixedHeight(tableHeight);

        listRow.add(basicRow1);
        listRow.add(basicRow2);

        document.add(table);


        PdfPCell cell;
        PdfPCell iCell;
        PdfPTable iTable;
        float lineHeight1 = 40.0f;
        float lineHeight2 = 20.0f;

        PdfPTable headerTable = new PdfPTable(4 + tendInfs.size());
        headerTable.setWidthPercentage(100);

        cell = new PdfPCell(new Paragraph("物料编号", baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(0);
        cell.setFixedHeight(lineHeight1 + lineHeight2);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("物料名称", baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(0);
        cell.setFixedHeight(lineHeight1 + lineHeight2);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("物料单位", baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(0);
        cell.setFixedHeight(lineHeight1 + lineHeight2);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("备注", baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(0);
        cell.setFixedHeight(lineHeight1 + lineHeight2);
        headerTable.addCell(cell);

        BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font tableFont = new Font(font);
        tableFont.setSize(9f);

        for (BidProjOnSplrTendInf tendInf : tendInfs) {
            iTable = new PdfPTable(3);

            iCell = new PdfPCell(new Paragraph(tendInf.getSplrNam(), tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setFixedHeight(lineHeight1);
            iCell.setColspan(3);
            iTable.addCell(iCell);

            iCell = new PdfPCell(new Paragraph("币种", tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setFixedHeight(lineHeight2);
            iTable.addCell(iCell);

            iCell = new PdfPCell(new Paragraph("单价", tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setFixedHeight(lineHeight2);
            iTable.addCell(iCell);

            iCell = new PdfPCell(new Paragraph("数量", tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setFixedHeight(lineHeight2);
            iTable.addCell(iCell);

            cell = new PdfPCell(iTable);
            cell.setPadding(0);
            headerTable.addCell(cell);
        }

        document.add(headerTable);

        float bodyHeight = 60f;
        for (OpenRcdIoDto openRcd : openRcdIoDtos) {
            PdfPTable bodyTable = new PdfPTable(4 + tendInfs.size());
            bodyTable.setWidthPercentage(100);

            cell = new PdfPCell(new Paragraph(openRcd.getMatCod(), baseFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(0);
            cell.setFixedHeight(bodyHeight);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Paragraph(openRcd.getMatNam(), baseFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(0);
            cell.setFixedHeight(bodyHeight);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Paragraph(openRcd.getMatUnt(), baseFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(0);
            cell.setFixedHeight(bodyHeight);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Paragraph(openRcd.getMatMemo(), baseFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(0);
            cell.setFixedHeight(bodyHeight);
            bodyTable.addCell(cell);

            for (OpenRcdSplrQotIoDto splrQot : openRcd.getSplrQotIoDtos()) {
                iTable = new PdfPTable(3);
                iCell = new PdfPCell(new Paragraph(splrQot.getCurrTyp(), tableFont));
                iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                iCell.setFixedHeight(bodyHeight);
                iTable.addCell(iCell);

                String priceStr = "";
                BigDecimal price = splrQot.getPrice();
                if (price != null) {
                    priceStr = price.toString();
                }

                iCell = new PdfPCell(new Paragraph(priceStr, tableFont));
                iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                iCell.setFixedHeight(bodyHeight);
                iTable.addCell(iCell);

                String splNum = splrQot.getSplNum();
                iCell = new PdfPCell(new Paragraph(splNum, tableFont));
                iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                iCell.setFixedHeight(bodyHeight);
                iTable.addCell(iCell);

                cell = new PdfPCell(iTable);
                cell.setPadding(0);
                bodyTable.addCell(cell);
            }
            document.add(bodyTable);
        }

        // 末行
        PdfPTable footTable = new PdfPTable(4 + tendInfs.size());
        footTable.setWidthPercentage(100);

        cell = new PdfPCell(new Paragraph("合      计", baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(0);
        cell.setFixedHeight(bodyHeight);
        cell.setColspan(4);
        footTable.addCell(cell);

        for (BigDecimal ttlPri : ttlPrices) {
            iTable = new PdfPTable(3);
            iCell = new PdfPCell(new Paragraph("RMB", tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setFixedHeight(bodyHeight);
            iTable.addCell(iCell);

            iCell = new PdfPCell(new Paragraph(ttlPri.toString(), tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setColspan(2);
            iCell.setFixedHeight(bodyHeight);
            iTable.addCell(iCell);

            cell = new PdfPCell(iTable);
            cell.setPadding(0);
            footTable.addCell(cell);
        }
        document.add(footTable);


        // 5. 關閉文檔
        document.close();
        pdfWriter.close();

        String downFileName = null;
        downFileName = new String((filename).getBytes("UTF-8"), "iso-8859-1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", downFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(filePath),
                headers, HttpStatus.CREATED);
    }


    private String getOpenRcdFile(HttpServletRequest request, BidProjOn projInf) throws Exception {

        Long projId = projInf.getProjId();

        // 获取物料信息
        BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(matDtlMap);
        if (matDtls == null || matDtls.isEmpty()) {
            String errMsg = "导出开标记录表 - 失败：物料信息为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 获取投标供应商
        List<String> bidDocStses = Arrays.asList(new String[]{Constants.BID_DOC_STS_ACCEPTED, Constants.BID_DOC_STS_AWD, Constants.BID_DOC_STS_UNAWD});
        BidProjOnSplrTendInf bidProjOnSplrTendInf = new BidProjOnSplrTendInf();
        bidProjOnSplrTendInf.setProjId(projId);
        bidProjOnSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(bidProjOnSplrTendInf, 0, 0);
        map.put("bidDocStses", bidDocStses);
        map.put("qotIdNotNul", "true");
        List<BidProjOnSplrTendInf> tendInfs = bidProjOnSplrTendInfMapper.selectByMap(map);
        if (tendInfs == null || tendInfs.isEmpty()) {
            String errMsg = "导出开标记录表 - 失败：投标供应商为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 获取报价信息
        BidProjOnSplrQotDtl qotDtl = new BidProjOnSplrQotDtl();
        qotDtl.setProjId(projId);
        qotDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map qotDtlMap = PageUtils.getQueryCondsMap(qotDtl, 0, 0);
        List<BidProjOnSplrQotDtl> qotDtls = bidProjOnSplrQotDtlMapper.selectByMap(qotDtlMap);
        if (qotDtls == null || qotDtls.isEmpty()) {
            String errMsg = "导出开标记录表 - 失败：报价信息为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        // 为提高效率，将报价信息转化为map方便查询，键为 物料ID-供应商ID
        Map<String, BidProjOnSplrQotDtl> qdMap = new HashMap<>();
        qotDtls.stream().forEach(q -> {
            String key = q.getMatId() + "-" + q.getSplrId();
            qdMap.put(key, q);
        });

        List<OpenRcdIoDto> openRcdIoDtos = new ArrayList<>();
        matDtls.stream().forEach(m -> {
            OpenRcdIoDto openRcdIoDto = new OpenRcdIoDto();
            String matCod = m.getMatCod();
            openRcdIoDto.setMatCod(matCod);
            openRcdIoDto.setMatNam(m.getMatNam());
            openRcdIoDto.setMatUnt(m.getMatUnt());
            openRcdIoDto.setMatMemo(m.getMemo());
            List<OpenRcdSplrQotIoDto> splrQotIoDtos = new ArrayList<>();
            tendInfs.stream().forEach(t -> {
                String key = m.getId() + "-" + t.getSplrId();
                BidProjOnSplrQotDtl qd = qdMap.get(key);
                OpenRcdSplrQotIoDto openRcdSplrQotIoDto = new OpenRcdSplrQotIoDto();
                openRcdSplrQotIoDto.setPrice(qd.getUntPri());
                openRcdSplrQotIoDto.setSplNum(qd.getSplNum());
                openRcdSplrQotIoDto.setSplrNam(t.getSplrNam());
                openRcdSplrQotIoDto.setCurrTyp(qd.getCurrTyp());
                openRcdSplrQotIoDto.setExRat(qd.getExRat());
                splrQotIoDtos.add(openRcdSplrQotIoDto);
            });
            openRcdIoDto.setSplrQotIoDtos(splrQotIoDtos);
            openRcdIoDtos.add(openRcdIoDto);
        });


        // 统计供应商总价
        List<BigDecimal> ttlPrices = new ArrayList<>();
        for (int i = 0; i < tendInfs.size(); i++) {
            BidProjOnSplrTendInf tendInf = tendInfs.get(i);
            BigDecimal ttlPri = new BigDecimal(0);
            for (BidProjOnSplrQotDtl qd : qotDtls) {
                if (tendInf.getSplrId().equals(qd.getSplrId()) && Constants.EFF_FLG_ON.equals(qd.getEffFlg())) {
                    BigDecimal price = qd.getUntPri();
                    if (price != null) {
                        BigDecimal exRat = new BigDecimal(1);
                        String exRatStr = qd.getExRat();
                        if (!StringUtils.isEmpty(exRatStr)) {
                            exRat = new BigDecimal(exRatStr);
                        }
                        ttlPri = price.multiply(exRat).multiply(new BigDecimal(qd.getSplNum())).add(ttlPri).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    }
                }
            }
            ttlPrices.add(ttlPri);
        }

        String rootPath = request.getServletContext().getRealPath("/");
        String basePath = new File(rootPath + "..").getCanonicalPath();

        String filename = "BidOpening-" + projId + "-" + DateUtils.getTimeStampLong(DateUtils.getCurrentTimeStamp()) + ".pdf";
//        String filename = "xxxxxxxxxxx" + "-开标记录表-" + DateUtils.getTimeStampLong(DateUtils.getCurrentTimeStamp()) + ".pdf";
        String filePath = basePath + File.separator + Constants.ATCH_FILE_PATH + File.separator + filename;
        // 1.新建document对象
        Document document = new Document();
        Rectangle pageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        pageSize.rotate();
        document.setPageSize(pageSize);

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        File file = new File(filePath);
        FileOutputStream outputStream = new FileOutputStream(file);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);

        // 3.打开文档
        document.open();
        //中文字体,解决中文不能显示问题
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font baseFont = new Font(bfChinese);
        baseFont.setSize(11f);
        // 4.添加一个内容段落

        Font headFont = new Font(bfChinese);
        headFont.setSize(24f);
        Paragraph title = new Paragraph("开标记录表", headFont);
        title.setAlignment(Element.ALIGN_MIDDLE);
        title.setSpacingBefore(10f); // 前间距
        document.add(title);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(60); // 宽度100%填充
        table.setSpacingBefore(10f); // 前间距
        table.setSpacingAfter(10f); // 后间距

        List<PdfPRow> listRow = table.getRows();
        //设置列宽
        float[] columnWidths = {2f, 5f, 2f, 4f};
        table.setWidths(columnWidths);

        // 基本信息 行1
        float tableHeight = 30f;
        PdfPCell basicCells1[] = new PdfPCell[4];
        PdfPRow basicRow1 = new PdfPRow(basicCells1);
        basicCells1[0] = new PdfPCell(new Paragraph("项目名称: ", baseFont));
        basicCells1[0].setBorderColor(BaseColor.WHITE);
        basicCells1[0].setFixedHeight(tableHeight);
        basicCells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells1[0].setHorizontalAlignment(Element.ALIGN_RIGHT);//水平居右

        basicCells1[1] = new PdfPCell(new Paragraph(projInf.getProjNam(), baseFont));
        basicCells1[1].setBorderColor(BaseColor.WHITE);
        basicCells1[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells1[1].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居左
        basicCells1[1].setFixedHeight(tableHeight);

        basicCells1[2] = new PdfPCell(new Paragraph("项目编号: ", baseFont));
        basicCells1[2].setBorderColor(BaseColor.WHITE);
        basicCells1[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells1[2].setHorizontalAlignment(Element.ALIGN_RIGHT);//水平居右
        basicCells1[2].setFixedHeight(tableHeight);

        basicCells1[3] = new PdfPCell(new Paragraph(projInf.getProjNbr(), baseFont));
        basicCells1[3].setBorderColor(BaseColor.WHITE);
        basicCells1[3].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells1[3].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居左
        basicCells1[3].setFixedHeight(tableHeight);

        PdfPCell basicCells2[] = new PdfPCell[4];
        PdfPRow basicRow2 = new PdfPRow(basicCells2);
        basicCells2[0] = new PdfPCell(new Paragraph("开标地点: ", baseFont));
        basicCells2[0].setBorderColor(BaseColor.WHITE);
        basicCells2[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells2[0].setHorizontalAlignment(Element.ALIGN_RIGHT);//水平居右
        basicCells2[0].setFixedHeight(tableHeight);

        basicCells2[1] = new PdfPCell(new Paragraph(projInf.getBidDptAddr(), baseFont));
        basicCells2[1].setBorderColor(BaseColor.WHITE);
        basicCells2[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells2[1].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居左
        basicCells2[1].setFixedHeight(tableHeight);

        basicCells2[2] = new PdfPCell(new Paragraph("开标时间: ", baseFont));
        basicCells2[2].setBorderColor(BaseColor.WHITE);
        basicCells2[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells2[2].setHorizontalAlignment(Element.ALIGN_RIGHT);//水平居右
        basicCells2[2].setFixedHeight(tableHeight);

        basicCells2[3] = new PdfPCell(new Paragraph(DateUtils.timestamp2String(projInf.getBidOpenTim()), baseFont));
        basicCells2[3].setBorderColor(BaseColor.WHITE);
        basicCells2[3].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        basicCells2[3].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居左
        basicCells2[3].setFixedHeight(tableHeight);

        listRow.add(basicRow1);
        listRow.add(basicRow2);

        document.add(table);


        PdfPCell cell;
        PdfPCell iCell;
        PdfPTable iTable;
        float lineHeight1 = 40.0f;
        float lineHeight2 = 20.0f;

        PdfPTable headerTable = new PdfPTable(4 + tendInfs.size());
        headerTable.setWidthPercentage(100);

        cell = new PdfPCell(new Paragraph("物料编号", baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(0);
        cell.setFixedHeight(lineHeight1 + lineHeight2);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("物料名称", baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(0);
        cell.setFixedHeight(lineHeight1 + lineHeight2);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("物料单位", baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(0);
        cell.setFixedHeight(lineHeight1 + lineHeight2);
        headerTable.addCell(cell);

        cell = new PdfPCell(new Paragraph("备注", baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(0);
        cell.setFixedHeight(lineHeight1 + lineHeight2);
        headerTable.addCell(cell);

        BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font tableFont = new Font(font);
        tableFont.setSize(9f);

        for (BidProjOnSplrTendInf tendInf : tendInfs) {
            iTable = new PdfPTable(3);
            iCell = new PdfPCell(new Paragraph(tendInf.getSplrNam(), tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setFixedHeight(lineHeight1);
            iCell.setColspan(3);
            iTable.addCell(iCell);

            iCell = new PdfPCell(new Paragraph("币种", tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setFixedHeight(lineHeight2);
            iTable.addCell(iCell);

            iCell = new PdfPCell(new Paragraph("单价", tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setFixedHeight(lineHeight2);
            iTable.addCell(iCell);

            iCell = new PdfPCell(new Paragraph("数量", tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setFixedHeight(lineHeight2);
            iTable.addCell(iCell);

            cell = new PdfPCell(iTable);
            cell.setPadding(0);
            headerTable.addCell(cell);
        }

        document.add(headerTable);

        float bodyHeight = 60f;
        for (OpenRcdIoDto openRcd : openRcdIoDtos) {
            PdfPTable bodyTable = new PdfPTable(4 + tendInfs.size());
            bodyTable.setWidthPercentage(100);

            cell = new PdfPCell(new Paragraph(openRcd.getMatCod(), baseFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(0);
            cell.setFixedHeight(bodyHeight);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Paragraph(openRcd.getMatNam(), baseFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(0);
            cell.setFixedHeight(bodyHeight);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Paragraph(openRcd.getMatUnt(), baseFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(0);
            cell.setFixedHeight(bodyHeight);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Paragraph(openRcd.getMatMemo(), baseFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(0);
            cell.setFixedHeight(bodyHeight);
            bodyTable.addCell(cell);

            for (OpenRcdSplrQotIoDto splrQot : openRcd.getSplrQotIoDtos()) {
                iTable = new PdfPTable(3);
                iCell = new PdfPCell(new Paragraph(splrQot.getCurrTyp(), tableFont));
                iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                iCell.setFixedHeight(bodyHeight);
                iTable.addCell(iCell);

                String priceStr = "";
                BigDecimal price = splrQot.getPrice();
                if (price != null) {
                    priceStr = price.toString();
                }

                iCell = new PdfPCell(new Paragraph(priceStr, tableFont));
                iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                iCell.setFixedHeight(bodyHeight);
                iTable.addCell(iCell);

                String splNum = splrQot.getSplNum();
                iCell = new PdfPCell(new Paragraph(splNum, tableFont));
                iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                iCell.setFixedHeight(bodyHeight);
                iTable.addCell(iCell);

                cell = new PdfPCell(iTable);
                cell.setPadding(0);
                bodyTable.addCell(cell);
            }
            document.add(bodyTable);
        }

        // 末行
        PdfPTable footTable = new PdfPTable(4 + tendInfs.size());
        footTable.setWidthPercentage(100);

        cell = new PdfPCell(new Paragraph("合      计", baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(0);
        cell.setFixedHeight(bodyHeight);
        cell.setColspan(4);
        footTable.addCell(cell);

        for (BigDecimal ttlPri : ttlPrices) {
            iTable = new PdfPTable(3);
            iCell = new PdfPCell(new Paragraph("RMB", tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setFixedHeight(bodyHeight);
            iTable.addCell(iCell);

            iCell = new PdfPCell(new Paragraph(ttlPri.toString(), tableFont));
            iCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            iCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            iCell.setColspan(2);
            iCell.setFixedHeight(bodyHeight);
            iTable.addCell(iCell);

            cell = new PdfPCell(iTable);
            cell.setPadding(0);
            footTable.addCell(cell);
        }
        document.add(footTable);


        // 5. 關閉文檔
        document.close();
        pdfWriter.close();

        return new String((filename).getBytes("UTF-8"), "iso-8859-1");
    }

   /* @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto updateGrdRes(Long gradId) throws Exception {

        logger.info("根据评标ID修改专家评标状态");

        CurrentUserInfo currentUserInfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = currentUserInfo.getRealname();

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (gradId == null) {
            String errMsg = "参数校验 - 不通过 - gradId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据评标ID查询评标信息");
        BidProjOnExptGrdInf bidProjOnExptGrdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(gradId);
        if (bidProjOnExptGrdInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnExptGrdInf.getEffFlg())) {
            String errMsg = "修改专家状态 - 根据评标ID查询评标信息无数据 - gradId[" + gradId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        String projGrdSts = bidProjOnExptGrdInf.getProjGrdSts();
        if (!Constants.GRD_STS_DONE.equals(projGrdSts)) {
            String errMsg = "修改专家状态 - 评标信息状态不为评标完成 - projGrdSts[" + projGrdSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(bidProjOnExptGrdInf.getProjId());
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "修改专家状态 - 根据项目ID查询项目信息无数据 - projId[" + bidProjOnExptGrdInf.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        String projSts = bidProjOn.getProjSts();
        if (!Constants.BID_PROJ_ON_STS_GRADING.equals(projSts)) {
            String errMsg = "修改专家状态 - 项目信息状态不为评标中 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        Long exptId = bidProjOnExptGrdInf.getExptId();
        ExptInf exptInf = exptInfMapper.selectByPrimaryKey(exptId);
        if (exptInf == null || Constants.EFF_FLG_OFF.equals(exptInf.getEffFlg())) {
            String errMsg = "修改专家状态 - 根据专家ID查询专家信息无数据 - exptId[" + exptId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束,duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        BidProjOnExptGrdInf exptGrdInf = new BidProjOnExptGrdInf();
        exptGrdInf.setEffFlg(Constants.EFF_FLG_ON);
        exptGrdInf.setGrdId(gradId);
        exptGrdInf.setProjGrdSts(Constants.GRD_STS_WAITING);
        exptGrdInf.setModUsr(realNam);
        exptGrdInf.setModTim(DateUtils.getCurrentTimeStamp());
        int effRows = bidProjOnExptGrdInfMapper.updateByPrimaryKeySelective(exptGrdInf);
        if (effRows != 1) {
            String errMsg = "更新项目状态为失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        if(Constants.GRD_RUL_0.equals(bidProjOn.getGrdRul())||Constants.GRD_RUL_2.equals(bidProjOn.getGrdRul())){
            logger.info(" 最低价中标");
            BidProjOnExptGrdDtlLow bidProjOnExptGrdDtlLow = new BidProjOnExptGrdDtlLow();
            bidProjOnExptGrdDtlLow.setGrdId(gradId);
            Map map = PageUtils.getQueryCondsMap(bidProjOnExptGrdDtlLow, 0, 0);
            List<BidProjOnExptGrdDtlLow> exptGrdDtlLows = bidProjOnExptGrdDtlLowMapper.selectByMap(map);
            for(BidProjOnExptGrdDtlLow exptGrdDtlLow :exptGrdDtlLows){
                exptGrdDtlLow.setModUsr(realNam);
                exptGrdDtlLow.setModTim(DateUtils.getCurrentTimeStamp());
                exptGrdDtlLow.setEffFlg(Constants.EFF_FLG_OFF);
                int effRows1 = bidProjOnExptGrdDtlLowMapper.updateByPrimaryKeySelective(exptGrdDtlLow);
                if (effRows1 != 1) {
                    String errMsg = "更新项目状态为失败 - 受影响行数不为1";
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
            }
        }else {
            String errMsg = "修改专家状态 - 失败 - 评标规则无效 - grdRul[" + bidProjOn.getGrdRul() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 修改专家评标状态 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }
*/
   /* @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto sendAwdToExpt(IoAppAwdDto appAwdDto) throws Exception {

        logger.info("专家审批定标结果: " + appAwdDto);

        CurrentUserInfo currentUserInfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = currentUserInfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = BidProjOnMngForPchsChecker.checkArgsForAppAwd(appAwdDto);
        if (!"".equals(checkRst)) {
            logger.error("申请决标 - 参数校验不通过 - 参数信息：" + appAwdDto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        Long projId = appAwdDto.getProjId();
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || !Constants.EFF_FLG_ON.equals(bidProjOn.getEffFlg())) {
            String errMsg = "申请决标 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + appAwdDto.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【评标结束】或【二次评标结束】,
        String projSts = bidProjOn.getProjSts();
        if (!Constants.BID_PROJ_ON_STS_GRADED.equals(projSts) && !Constants.BID_PROJ_ON_STS_QOT2_GRADED.equals(projSts)) {
            String errMsg = "申请决标 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        logger.info("3.1 查询该项目下所有物料明细");
        BidProjOnMatDtl matDtl = new BidProjOnMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(map);
        if (matDtls == null || matDtls.isEmpty()) {
            String errMsg = "申请决标 - 业务校验 - 异常 - 该项目无物料信息 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        Map<String, BidProjOnMatDtl> matMap = new HashMap<>();
        for (BidProjOnMatDtl dtl : matDtls) {
            matMap.put(String.valueOf(dtl.getId()), dtl);
        }

        logger.info("3.2 新增线上招标项目中标明细");
        // 删除已存在的中标明细
        bidProjOnWinDtlMapper.deleteByProjId(projId);
        // 新增中标明细
        List<IoAppAwdDtlDto> appAwdDtlDtos = appAwdDto.getAppAwdDtls();
        for (IoAppAwdDtlDto dtlDto : appAwdDtlDtos) {
            BidProjOnWinDtl winDtl = new BidProjOnWinDtl();
            winDtl.setCrtTim(DateUtils.getCurrentTimeStamp());
            winDtl.setCrtUsr(realNam);
            winDtl.setEffFlg(Constants.EFF_FLG_ON);
            BidProjOnMatDtl matInf = matMap.get(String.valueOf(dtlDto.getMatId()));
            winDtl.setMatId(matInf.getId());
            winDtl.setMatCod(matInf.getMatCod());
            winDtl.setMatNam(matInf.getMatNam());
            winDtl.setMatUnt(matInf.getMatUnt());
            winDtl.setPchsNum(dtlDto.getPchsNum());
            winDtl.setProjId(projId);
            winDtl.setSplrId(dtlDto.getSplrId());
            winDtl.setSplrNam(dtlDto.getSplrNam());
            winDtl.setUntPri(dtlDto.getUntPri());
            winDtl.setTtlPri(dtlDto.getTtlPri());
            winDtl.setTendDlvDte(dtlDto.getTendDlvDte());
            winDtl.setCurrTyp(dtlDto.getCurrTyp());
            winDtl.setExRat(dtlDto.getExRat());
            winDtl.setMatBnd(dtlDto.getMatBnd());
            winDtl.setMemo(dtlDto.getMemo());
            int effRows = bidProjOnWinDtlMapper.insert(winDtl);
            if (effRows != 1) {
                String errMsg = "新增线上招标项目中标明细 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        }

        logger.info("3.3 提交专家审批");
        logger.info("3.4 项目状态改成专家审批状态");

        bidProjOn.setProjSts(Constants.BID_PROJ_ON_STS_EXPT_ADITING);
        Timestamp now = DateUtils.getCurrentTimeStamp();
        bidProjOn.setModUsr(realNam);
        bidProjOn.setModTim(now);
        bidProjOn.setEffFlg(Constants.EFF_FLG_ON);
        bidProjOn.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        int effRows = bidProjOnMapper.updateByPrimaryKeySelective(bidProjOn);
        if (effRows != 1) {
            String errMsg = "更新项目状态 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        // 写入线上招标项目状态记录表
        ComParm comParmBidProjOnStsBidding = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_BID_PROJ_ON_STS, Constants.BID_PROJ_ON_STS_EXPT_ADITING);
        if (comParmBidProjOnStsBidding == null || !Constants.EFF_FLG_ON.equals(comParmBidProjOnStsBidding.getEffFlg())
                || StringUtils.isEmpty(comParmBidProjOnStsBidding.getParmVal())) {
            String errMsg = "发布线上招标项目失败 - 通用参数为定义：parmTyp[" + Constants.COM_PARM_TYP_BID_PROJ_ON_STS + "], parmCod[" + Constants.BID_PROJ_ON_STS_EXPT_ADITING + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        // 写入线上招标项目状态记录表
        BidProjOnStsLog bidProjOnStsLog = new BidProjOnStsLog();
        bidProjOnStsLog.setProjId(bidProjOn.getProjId());
        bidProjOnStsLog.setProjNam(bidProjOn.getProjNam());
        bidProjOnStsLog.setProjSts(bidProjOn.getProjSts());
        bidProjOnStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        bidProjOnStsLog.setUpdUsr("TST USR");

        effRows = bidProjOnStsLogMapper.insert(bidProjOnStsLog);
        if (effRows != 1) {
            String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }*/

   /* @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getExptAwdRes(Long projId) throws Exception {

        logger.info("查看专家审批结果信息");

        CurrentUserInfo currentUserInfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (projId == null) {
            String errMsg = "参数校验 - 不通过 - projId不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "查看专家审批结果信息 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        String projSts = bidProjOn.getProjSts();
        if (!Constants.BID_PROJ_ON_STS_EXPT_ADITING.equals(projSts)) {
            String errMsg = "查看专家审批结果信息 - 项目信息状态不为专家审批中 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束,duration[" + durationB + "ms]");

        BidProjOnExptGrdInf exptGrdInfEntity = new BidProjOnExptGrdInf();
        exptGrdInfEntity.setEffFlg(Constants.EFF_FLG_ON);
        exptGrdInfEntity.setProjId(projId);
        exptGrdInfEntity.setProjGrdSts(Constants.PROJ_GRD_STS_DONE);
        Map exptGrdInfMap = PageUtils.getQueryCondsMap(exptGrdInfEntity, 0, 0);
        List<BidProjOnExptGrdInf> exptGrdInfs = bidProjOnExptGrdInfMapper.selectByMap(exptGrdInfMap);
        if (exptGrdInfs == null && exptGrdInfs.isEmpty()) {
            String errMsg = "查看专家审批结果信息 - 失败：专家评标信息为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }
        List<BidProjOnExptAppResDto> exptAppResDtos = new ArrayList<>();
        exptGrdInfs.forEach(exptGrdInf -> {
            BidProjOnExptAppResDto exptAppResDto = new BidProjOnExptAppResDto();
            exptAppResDto.setGrdId(exptGrdInf.getGrdId());
            exptAppResDto.setExptNam(exptGrdInf.getExptNam());
            exptAppResDto.setIsAgreed(exptGrdInf.getIsAgreed());
            exptAppResDto.setProjAwdRsn(exptGrdInf.getProjAwdRsn());

            exptAppResDtos.add(exptAppResDto);
        });
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, exptAppResDtos);
    }*/

  /*  @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public OutputDto updateExptAppRes(Long grdId) throws Exception {

        logger.info("根据评标ID修改专家审批状态");

        CurrentUserInfo currentUserInfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = currentUserInfo.getRealname();

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
        logger.info("2.1 业务校验 - 根据评标ID查询评标信息");
        BidProjOnExptGrdInf bidProjOnExptGrdInf = bidProjOnExptGrdInfMapper.selectByPrimaryKey(grdId);
        if (bidProjOnExptGrdInf == null || Constants.EFF_FLG_OFF.equals(bidProjOnExptGrdInf.getEffFlg())) {
            String errMsg = "根据评标ID修改专家审批状态 - 根据评标ID查询评标信息无数据 - grdId[" + grdId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        Long projId = bidProjOnExptGrdInf.getProjId();
        BidProjOn bidProjOn = bidProjOnMapper.selectByPrimaryKey(projId);
        if (bidProjOn == null || Constants.EFF_FLG_OFF.equals(bidProjOn.getEffFlg())) {
            String errMsg = "根据评标ID修改专家审批状态 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        String projSts = bidProjOn.getProjSts();
        if (!Constants.BID_PROJ_ON_STS_EXPT_ADITING.equals(projSts)) {
            String errMsg = "根据评标ID修改专家审批状态 - 项目信息状态不为专家审批中 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        String isAgreed = bidProjOnExptGrdInf.getIsAgreed();
        if (Constants.IS_AGREED_0.equals(isAgreed)) {
            String errMsg = "根据评标ID修改专家审批状态 - 专家审批状态为未审批 - isAgreed [" + isAgreed + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束,duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        BidProjOnExptGrdInf exptGrdInf = new BidProjOnExptGrdInf();
        exptGrdInf.setEffFlg(Constants.EFF_FLG_ON);
        exptGrdInf.setGrdId(grdId);
        exptGrdInf.setIsAgreed(Constants.IS_AGREED_0);
        exptGrdInf.setProjAwdRsn("");
        exptGrdInf.setModUsr(realNam);
        exptGrdInf.setModTim(DateUtils.getCurrentTimeStamp());
        int effRows = bidProjOnExptGrdInfMapper.updateByPrimaryKeySelective(exptGrdInf);
        if (effRows != 1) {
            String errMsg = "更新项目状态为失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 根据评标ID修改专家审批状态 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);

    }*/


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto collectGrdInf(Long projId) throws Exception {

        if (projId == null || projId.equals(0l)) {
            String errMsg = "查看评标汇总 - 失败，projId为空 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        BidProjOn projInf = bidProjOnMapper.selectByPrimaryKey(projId);
        if (projInf == null || Constants.EFF_FLG_OFF.equals(projInf.getEffFlg())) {
            String errMsg = "查看评标汇总 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

//        String projSts = bidProjOn.getProjSts();
//        if(!Constants.BID_PROJ_ON_STS_EXPT_ADITING.equals(projSts)){
//            String errMsg = "查看专家审批结果信息 - 项目信息状态不为专家审批中 - projSts[" + projSts + "]";
//            logger.error(errMsg);
//            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,RtnEnum.ARG_INVALID,errMsg);
//        }

        BidProjOnExptGrdInf exptGrdInfQry = new BidProjOnExptGrdInf();
        exptGrdInfQry.setProjId(projId);
        exptGrdInfQry.setEffFlg(Constants.EFF_FLG_ON);
        exptGrdInfQry.setProjGrdSts(Constants.GRD_STS_DONE);
        Map map = PageUtils.getQueryCondsMap(exptGrdInfQry, 0, 0);
        List<BidProjOnExptGrdInf> exptGrdInfs = bidProjOnExptGrdInfMapper.selectByMap(map);
        if (exptGrdInfs == null || exptGrdInfs.isEmpty()) {
            String errMsg = "查看评标汇总 - 失败 - 根据项目ID查询评标记录无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        List<Long> grdIds = exptGrdInfs.stream().map(g -> g.getGrdId()).collect(Collectors.toList());
        List<String> exptNams = exptGrdInfs.stream().map(g -> g.getExptNam()).collect(Collectors.toList());

        String grdRul = projInf.getGrdRul();
        if (Constants.GRD_RUL_0.equals(grdRul) || Constants.GRD_RUL_2.equals(grdRul)) {

            BidProjOnExptGrdDtlLow entityQry = new BidProjOnExptGrdDtlLow();
            entityQry.setProjId(projId);
            entityQry.setEffFlg(Constants.EFF_FLG_ON);
            map = PageUtils.getQueryCondsMap(entityQry, 0, 0);
            List<BidProjOnExptGrdDtlLow> exptGrdDtlLows = bidProjOnExptGrdDtlLowMapper.selectByMap(map);
            if (exptGrdDtlLows == null || exptGrdDtlLows.isEmpty()) {
                String errMsg = "查看评标汇总 - 根据projId查询评标明细信息无数据 - projId[" + projId + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }

            Map<String, BidProjOnExptGrdDtlLow> exptGrdDrlMap = new HashMap();
            exptGrdDtlLows.stream().forEach(l -> {
                String key = l.getGrdId() + "-" + l.getSplrId() + "-" + l.getMatId();
                exptGrdDrlMap.put(key, l);
            });

            List<Long> splrIds = exptGrdDtlLows.stream().map(l -> l.getSplrId()).distinct().collect(Collectors.toList());
            if (splrIds == null || splrIds.isEmpty()) {
                String errMsg = "查看评标汇总 - 无供应商信息";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }

            BidProjOnMatDtl matDtlEntity = new BidProjOnMatDtl();
            matDtlEntity.setEffFlg(Constants.EFF_FLG_ON);
            matDtlEntity.setProjId(projId);
            map = PageUtils.getQueryCondsMap(matDtlEntity, 0, 0);
            List<BidProjOnMatDtl> matDtls = bidProjOnMatDtlMapper.selectByMap(map);

            if (Constants.GRD_RUL_0.equals(grdRul)) {
                logger.info("最低单价评标");
                GrdClctInfUniLowIoDto dto = new GrdClctInfUniLowIoDto();
                dto.setProjInf(projInf);
                dto.setExptNams(exptNams);
                dto.setMatDtls(matDtls);
                dto.setList(matDtls.stream().map(mat -> {
                    GrdClctInfUniLowDtlIoDto dtl = new GrdClctInfUniLowDtlIoDto();
                    dtl.setMatId(mat.getId());
                    dtl.setMatCod(mat.getMatCod());
                    dtl.setMatNam(mat.getMatNam());
                    dtl.setMatUnt(mat.getMatUnt());
                    dtl.setPchsNum(mat.getPchsNum());
                    dtl.setDlvAdr(mat.getDlvAdr());
                    dtl.setDlvDte(mat.getDlvDte());
                    dtl.setMemo(mat.getMemo());
                    BidProjOnExptGrdDtlLow bidProjOnExptGrdDtlLow = new BidProjOnExptGrdDtlLow();
                    bidProjOnExptGrdDtlLow.setMatId(mat.getId());
                    bidProjOnExptGrdDtlLow.setEffFlg(Constants.EFF_FLG_ON);
                    bidProjOnExptGrdDtlLow.setGrdId(grdIds.get(0));
                    Map map1 = PageUtils.getQueryCondsMap(bidProjOnExptGrdDtlLow, 0, 0);
                    List<BidProjOnExptGrdDtlLow> bidProjOnExptGrdDtlLows = bidProjOnExptGrdDtlLowMapper.selectByMap(map1);
                    List<GrdClctInfUniLowDtlGrdInfIoDto> grdInfs = new ArrayList<>();
                    bidProjOnExptGrdDtlLows.stream().map(exptGrdDtlLow -> {
                        GrdClctInfUniLowDtlGrdInfIoDto grdInf = new GrdClctInfUniLowDtlGrdInfIoDto();
                        grdInf.setSplrId(exptGrdDtlLow.getSplrId());
                        grdInf.setSplrNam(exptGrdDtlLow.getSplrNam());
                        grdInf.setGrdList(exptGrdInfs.stream().map(exptGrdInf -> {
                            GrdClctInfUniLowDtlGrdInfDtlIoDto grdInfDtl = new GrdClctInfUniLowDtlGrdInfDtlIoDto();
                            String key = exptGrdInf.getGrdId() + "-" + exptGrdDtlLow.getSplrId() + "-" + mat.getId();
                            BidProjOnExptGrdDtlLow exptGrdDtlLow1 = exptGrdDrlMap.get(key);
                            if(exptGrdDtlLow1==null){
                                exptGrdDtlLow1 = new BidProjOnExptGrdDtlLow();
                            }
                            grdInfDtl.setGrdOrd(exptGrdDtlLow1.getGrdOrd());
                            grdInfDtl.setGrdRsn(exptGrdDtlLow1.getGrdRsn());
                            return grdInfDtl;
                        }).collect(Collectors.toList()));
                        grdInfs.add(grdInf);
                        return grdInf;
                    }).collect(Collectors.toList());
                    dtl.setGrdInfs(grdInfs);
                    return  dtl;
                }).collect(Collectors.toList()));
                return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,RtnEnum.SUC,dto);
            } else {
                logger.info("最低总价评标");
                GrdClctInfTtlLowIoDto dto = new GrdClctInfTtlLowIoDto();
                dto.setProjInf(projInf);
                dto.setExptNams(exptNams);
                dto.setList(splrIds.stream().map(splrId -> {
                    GrdClctInfTtlLowDtlIoDto dtl = new GrdClctInfTtlLowDtlIoDto();
                    dtl.setSplrId(splrId);
                    List<GrdClctInfTtlLowDtlGrdInfIoDto> grdInfs = exptGrdInfs.stream().map(g -> {
                        GrdClctInfTtlLowDtlGrdInfIoDto grdInf = new GrdClctInfTtlLowDtlGrdInfIoDto();

                        String key = g.getGrdId() + "-" + splrId + "-" + matDtls.get(0).getId();
                        BidProjOnExptGrdDtlLow low = exptGrdDrlMap.get(key);
                        if (low == null) {
                            low = new BidProjOnExptGrdDtlLow();
                        }
                        grdInf.setGrdOrd(low.getGrdOrd());
                        grdInf.setGrdRsn(low.getGrdRsn());
                        grdInf.setExptId(g.getExptId());
                        grdInf.setExptNam(g.getExptNam());
                        dtl.setSplrNam(low.getSplrNam());
                        return grdInf;
                    }).collect(Collectors.toList());
                    dtl.setGrdInfs(grdInfs);
                    return dtl;
                }).collect(Collectors.toList()));
                return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, dto);
            }
        } else {
            String errMsg = "查看评标汇总 - 失败 - 评标规则无效 - grdRul[" + grdRul + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
    }

    @Override
    public OutputDto getReqMatInfsByName(Integer pageNo, Integer pageSize, String matName) throws Exception {

        logger.info("根据物料名称搜索需求中的物料信息, matName]", matName);

        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize + 1;

        Integer to = pageSize * pageNo;

        Map map = new HashMap();
        map.put("matName", matName);
        map.put("start", start);
        map.put("limit", to);

        List<Map> requisitions = requisitionMapper.selectByName(map);
        Integer count = requisitionMapper.countByName(map);
        if (requisitions == null || requisitions.isEmpty()) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        }
        List<MatInfIoDto> matInfIoDtos = requisitions.parallelStream().map(req -> {
            MatInfIoDto matInf = new MatInfIoDto();
            matInf.setReqId(Long.valueOf(((BigDecimal) req.get("REQ_ID")).toString()));
            matInf.setMatCod((String) req.get("MATNR"));
            matInf.setMatNam((String) req.get("TXZ01"));
            matInf.setMatSpft((String) req.get("UNITNAME"));
            matInf.setMatTyp((String) req.get("MATKL"));
            matInf.setMatTypDsc((String) req.get("WGBEZ"));
            matInf.setMatUnt((String) req.get("UNITNAME"));
            matInf.setPchsNum(req.get("MENGE").toString());
            matInf.setDlvAdr((String) req.get("BUTXT"));
            String lfDat = (String) req.get("LFDAT");
            Timestamp dlvDte = null;
            if (!StringUtils.isEmpty(lfDat)) {
                try {
                    dlvDte = DateUtils.string2timestamp(lfDat);
                } catch (Exception e) {
                    dlvDte = null;
                }
            }
            matInf.setDlvDte(dlvDte);
            return matInf;
        }).collect(Collectors.toList());

        PagedResult result = new PagedResult(matInfIoDtos, count);
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    public OutputDto getMatInfsByMatCod(String matCod) throws Exception {

        logger.info("根据物料编码绑定物料信息, matCod]", matCod);
        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (matCod == null||matCod.isEmpty()) {
            String errMsg = "参数校验 - 不通过 - matCod不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        Materiel materiel = materielMapper.selectByMatcod(matCod);
        if(materiel==null){
            String errMsg = "该物料编码不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, materiel);
    }


}
