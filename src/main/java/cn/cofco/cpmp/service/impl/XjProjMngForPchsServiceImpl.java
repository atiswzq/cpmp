package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.factory.MatFactory;
import cn.cofco.cpmp.factory.XjProjBpmAppFactory;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ICodRulInfService;
import cn.cofco.cpmp.service.IXjProjMngForPchsService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;
import cn.cofco.cpmp.utils.StringUtils;
import cn.cofco.cpmp.utils.checkers.XjProjMngForPchsChecker;
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
 * Created by Wzq on 2018/01/13.
 * for [线上招标项目管理服务类 - 工厂采购员 实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class XjProjMngForPchsServiceImpl implements IXjProjMngForPchsService{
    private static Logger logger = LoggerManager.getBidOnlineMngLog();

    @Resource
    private XjProjMapper xjProjMapper;
    @Resource
    private ICodRulInfService codRulInfService;
    @Resource
    private XjProjMatDtlMapper xjProjMatDtlMapper;
    @Resource
    private RequisitionMapper requisitionMapper;
    @Resource
    private SplrMapper splrMapper;
    @Resource
    private XjProjSplrInvtMapper xjProjSplrInvtMapper;
    @Resource
    private AtchMapper atchMapper;
    @Resource
    private XjProjStsLogMapper xjProjStsLogMapper;
    @Resource
    private MatFactory matFactory;
    @Resource
    private XjProjBpmAppFactory xjProjBpmAppFactory;
    @Resource
    private XjProjSplrTendInfMapper xjProjSplrTendInfMapper;
    @Resource
    private XjProjSplrQotDtlMapper xjProjSplrQotDtlMapper;
    @Resource
    private XjProjSplrQotInfMapper xjProjSplrQotInfMapper;
    @Resource
    private  XjProjWinDtlMapper xjProjWinDtlMapper;


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getPagedResultByIoQueryXjProjForPchsDto(IoQueryXjProjForPchsDto dto, Integer pageNo, Integer pageSize) {
        logger.info("获取询价项目分页信息, dto - " + dto);

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
            String errMsg = "获取询价项目分页信息 - 失败 - 该采购员无所辖公司";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.NO_OPRT_AUTH, errMsg);
        }

        List<Long> orgIds = companies
                .stream()
                .map(currentUserInfoFactory -> Long.valueOf(currentUserInfoFactory.getId()))
                .collect(Collectors.toList());

        if (dto.getOrgId() != null && !dto.getOrgId().equals(0L) && !orgIds.stream().anyMatch(orgId -> orgId.equals(dto.getOrgId()))) {
            String errMsg = "获取询价项目分页信息 - 失败 - 所查询公司不在所辖范围内";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.NO_OPRT_AUTH, errMsg);
        }

        Map map = PageUtils.getQueryCondsMap(dto, start, to);
        map.put("orgIds", orgIds);
        map.put("effFlg", Constants.EFF_FLG_ON);
        map.put("desc", 1);

        List<XjProj> list = xjProjMapper.selectByMap(map);

        Integer count = xjProjMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto save(HttpServletRequest request, IoXjProjDto dto) throws Exception {
        logger.info("保存询价项目, request: " + dto);

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = XjProjMngForPchsChecker.checkArgsForSave(dto);
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
        XjProj xjProj = new XjProj();
        List<XjProjMatDtl> matDtls = new ArrayList<>();
        try {
            BeanUtils.copyProperties(xjProj, dto);
            List<IoXjProjMatInfDto> matInfInDtos = dto.getMatList();
            for (IoXjProjMatInfDto m : matInfInDtos) {
                XjProjMatDtl matDtl = new XjProjMatDtl();
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
        logger.info("3. 新增、更新、保存- 开始");

        // 新增项目
        if (xjProj.getProjId() == null || xjProj.getProjId() == 0L) {
            xjProj.setProjNbr(codRulInfService.getProjNbr(Constants.PROJ_TYP_XJ, deptCode));
            xjProj.setProjRsps(realNam);
            xjProj.setCrtUsr(realNam);
            xjProj.setCrtTim(DateUtils.getCurrentTimeStamp());
            xjProj.setEffFlg(Constants.EFF_FLG_ON);
            ComParm comParmXjProjStsEdting = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_XJ_PROJ_STS, Constants.XJ_PROJ_STS_EDTING);
            if (comParmXjProjStsEdting == null || !Constants.EFF_FLG_ON.equals(comParmXjProjStsEdting.getEffFlg())
                    || StringUtils.isEmpty(comParmXjProjStsEdting.getParmVal())) {
                String errMsg = "新增询价项目失败 - 通用参数为定义：parmTyp[" + Constants.COM_PARM_TYP_XJ_PROJ_STS + "], parmCod[" + Constants.XJ_PROJ_STS_EDTING + "]";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            xjProj.setProjSts(comParmXjProjStsEdting.getParmVal());
            int effRows = xjProjMapper.insert(xjProj);
            if (effRows != 1) {
                String errMsg = "新增询价项目失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            List<Long> reqIds = new ArrayList<>();
            for (XjProjMatDtl matDtl : matDtls) {
                Long reqId = matDtl.getReqId();
                if (reqId != null && !reqId.equals(0L)) {
                    reqIds.add(reqId);
                }
                // 设置项目ID
                matDtl.setProjId(xjProj.getProjId());
                matDtl.setCrtUsr(realNam);
                matDtl.setCrtTim(DateUtils.getCurrentTimeStamp());
                matDtl.setEffFlg(Constants.EFF_FLG_ON);
                xjProjMatDtlMapper.insert(matDtl);
            }
            if (!reqIds.isEmpty()) {
                requisitionMapper.updateToInzb(Constants.INZB_1, reqIds);
            }

            // 定向招标供应商信息新增
            if (Constants.BID_RNG_TYP_VECTORING.equals(xjProj.getBidRngTyp())) {
                String splrIdsStr = dto.getSplrIds();
                String[] splrIds = splrIdsStr.split(",");
                for (String splrIdStr : splrIds) {
                    Long splrId = Long.valueOf(splrIdStr);
                    XjProjSplrInvt xjProjSplrInvt = new XjProjSplrInvt();
                    xjProjSplrInvt.setProjId(xjProj.getProjId());
                    xjProjSplrInvt.setBidFlg(Constants.BID_FLG_UNDO);
                    xjProjSplrInvt.setSplrId(splrId);
                    Splr splr = splrMapper.selectByPrimaryKey(splrId);
                    if (splr != null) {
                        xjProjSplrInvt.setSplrNam(splr.getFullNam());
                    }
                    xjProjSplrInvt.setCrtUsr(realNam);
                    xjProjSplrInvt.setCrtTim(DateUtils.getCurrentTimeStamp());
                    xjProjSplrInvt.setEffFlg(Constants.EFF_FLG_ON);
                    xjProjSplrInvtMapper.insert(xjProjSplrInvt);
                }
            }

            // 附件新增
            List<IoAtchDto> atchDtos = dto.getAtchDtos();
            if (atchDtos != null && !atchDtos.isEmpty()) {
                ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_XJBUD);
                if (comParm == null) {
                    String errMsg = "新增询价项目失败 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_XJBUD + "]";
                    logger.error(errMsg);
                    throw new RuntimeException(errMsg);
                }

                String atchPrefix = comParm.getParmVal();
                String refId = atchPrefix + xjProj.getProjId();
                for (IoAtchDto atchDto : atchDtos) {
                    Atch atch = new Atch();
                    BeanUtils.copyProperties(atch, atchDto);
                    atch.setRefId(refId);
                    atch.setEffFlg(Constants.EFF_FLG_ON);
                    atch.setCrtTim(DateUtils.getCurrentTimeStamp());
                    atch.setCrtUsr(realNam);
                    effRows = atchMapper.insert(atch);
                    if (effRows != 1) {
                        String errMsg = "新增询价项目失败 - 新增附件 - 失败 - 受影响行数不为1";
                        logger.error(errMsg);
                        throw new RuntimeException(errMsg);
                    }
                }
            }

            // 写入线上招标项目状态记录表
            XjProjStsLog xjProjStsLog = new XjProjStsLog();
            xjProjStsLog.setProjId(xjProj.getProjId());
            xjProjStsLog.setProjNam(xjProj.getProjNam());
            xjProjStsLog.setProjSts(comParmXjProjStsEdting.getParmVal());
            xjProjStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
            xjProjStsLog.setUpdUsr(userinfo.getRealname());

            effRows = xjProjStsLogMapper.insert(xjProjStsLog);
            if (effRows != 1) {
                String errMsg = "写入线上招标项目状态记录表失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

        }
        // 更新项目
        else {
            XjProj entity = xjProjMapper.selectByPrimaryKey(xjProj.getProjId());
            if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
                String errMsg = "更新项目信息失败 - 根据项目ID查询项目信息无数据 - projId[" + xjProj.getProjId() + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }

            // 判断项目状态是否为 【编辑中】
            if (!Constants.XJ_PROJ_STS_EDTING.equals(entity.getProjSts())) {
                String errMsg = "更新项目信息失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
                logger.error(errMsg);
                return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
            }
            xjProj.setProjNbr(entity.getProjNbr());
            xjProj.setProjRsps(realNam);
            xjProj.setCrtUsr(entity.getCrtUsr());
            xjProj.setCrtTim(entity.getCrtTim());
            xjProj.setModUsr(realNam);
            xjProj.setModTim(DateUtils.getCurrentTimeStamp());
            xjProj.setEffFlg(Constants.EFF_FLG_ON);
            ComParm comParmXjProjStsEdting = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_XJ_PROJ_STS, Constants.XJ_PROJ_STS_EDTING);
            if (comParmXjProjStsEdting == null || !Constants.EFF_FLG_ON.equals(comParmXjProjStsEdting.getEffFlg())
                    || StringUtils.isEmpty(comParmXjProjStsEdting.getParmVal())) {
                String errMsg = "更新线上招标项目失败 - 通用参数为定义：parmTyp[" + Constants.COM_PARM_TYP_XJ_PROJ_STS + "], parmCod[" + Constants.XJ_PROJ_STS_EDTING + "]";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
            xjProj.setProjSts(comParmXjProjStsEdting.getParmVal());
            int effRows = xjProjMapper.updateByPrimaryKeySelective(xjProj);
            if (effRows != 1) {
                String errMsg = "更新线上招标项目失败 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            // 更新物料信息
            XjProjMatDtl xjProjMatDtl = new XjProjMatDtl();
            xjProjMatDtl.setProjId(xjProj.getProjId());
            xjProjMatDtl.setEffFlg(Constants.EFF_FLG_ON);
            Map matDtlEntityMap = PageUtils.getQueryCondsMap(xjProjMatDtl, 0, 0);
            List<XjProjMatDtl> matDtlList = xjProjMatDtlMapper.selectByMap(matDtlEntityMap);
            List<Long> delReqIds = new ArrayList<>();
            for (XjProjMatDtl m : matDtlList) {
                Long reqId = m.getReqId();
                if (reqId != null && !reqId.equals(0L)) {
                    delReqIds.add(reqId);
                }
                m.setModUsr(realNam);
                m.setModTim(DateUtils.getCurrentTimeStamp());
                m.setEffFlg(Constants.EFF_FLG_OFF);
                xjProjMatDtlMapper.updateByPrimaryKey(m);
            }
            if (!delReqIds.isEmpty()) {
                requisitionMapper.updateToInzb(Constants.INZB_0, delReqIds);
            }

            Iterator<XjProjMatDtl> itSub = matDtls.iterator();
            List<Long> reqIds = new ArrayList<>();
            while (itSub.hasNext()) {
                XjProjMatDtl dtlSub = itSub.next();
                Long reqId = dtlSub.getReqId();
                if (reqId != null && !reqId.equals(0L)) {
                    reqIds.add(reqId);
                }
                dtlSub.setProjId(xjProj.getProjId());
                dtlSub.setCrtUsr(realNam);
                dtlSub.setCrtTim(DateUtils.getCurrentTimeStamp());
                dtlSub.setEffFlg(Constants.EFF_FLG_ON);
                xjProjMatDtlMapper.insert(dtlSub);
            }
            if (!reqIds.isEmpty()) {
                requisitionMapper.updateToInzb(Constants.INZB_1, reqIds);
            }

            xjProjSplrInvtMapper.deleteByProjId(xjProj.getProjId());
            // 定向招标供应商信息新增
            if (Constants.BID_RNG_TYP_VECTORING.equals(xjProj.getBidRngTyp())) {
                String splrIdsStr = dto.getSplrIds();
                String[] splrIds = splrIdsStr.split(",");
                for (String splrIdStr : splrIds) {
                    Long splrId = Long.valueOf(splrIdStr);
                    XjProjSplrInvt xjProjSplrInvt = new XjProjSplrInvt();
                    xjProjSplrInvt.setProjId(xjProj.getProjId());
                    xjProjSplrInvt.setSplrId(splrId);
                    Splr splr = splrMapper.selectByPrimaryKey(splrId);
                    if (splr != null) {
                        xjProjSplrInvt.setSplrNam(splr.getFullNam());
                    }
                    xjProjSplrInvt.setBidFlg(Constants.BID_FLG_UNDO);
                    xjProjSplrInvt.setCrtUsr(realNam);
                    xjProjSplrInvt.setCrtTim(DateUtils.getCurrentTimeStamp());
                    xjProjSplrInvt.setEffFlg(Constants.EFF_FLG_ON);
                    xjProjSplrInvtMapper.insert(xjProjSplrInvt);
                }
            }

            // 2.2 更新文章附件
            // 2.2.1 得到附件关联ID
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_XJBUD);
            if (comParm == null) {
                String errMsg = "更新线上询价项目信息 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_XJBUD + "]";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            String atchPrefix = comParm.getParmVal();
            String refId = atchPrefix + xjProj.getProjId();

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
                        String errMsg = "更新询价项目信息 - 新增附件 - 失败 - 受影响行数不为1";
                        logger.error(errMsg);
                        throw new RuntimeException(errMsg);
                    }
                }
            }
        }

        /*// 提交BPM审批
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
*/
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

        XjProj entity = xjProjMapper.selectByPrimaryKey(projId);
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "删除项目信息失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【编辑中】
        if (!Constants.XJ_PROJ_STS_EDTING.equals(entity.getProjSts())) {
            String errMsg = "删除项目信息失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setEffFlg(Constants.EFF_FLG_OFF);

        int effRows = xjProjMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "删除询价项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 更新物料信息
        XjProjMatDtl matDtlEntity = new XjProjMatDtl();
        matDtlEntity.setProjId(projId);
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
            dtlQry.setModUsr(realNam);
            dtlQry.setModTim(DateUtils.getCurrentTimeStamp());
            dtlQry.setEffFlg(Constants.EFF_FLG_OFF);
            xjProjMatDtlMapper.updateByPrimaryKey(dtlQry);
        }

        if (!reqIds.isEmpty()) {
            requisitionMapper.updateToInzb(Constants.INZB_0, reqIds);
        }

        // 写入线上招标项目状态记录表
        XjProjStsLog xjProjStsLog = new XjProjStsLog();
        xjProjStsLog.setProjId(entity.getProjId());
        xjProjStsLog.setProjNam(entity.getProjNam());
        xjProjStsLog.setProjSts(Constants.XJ_PROJ_STS_EDTING);
        xjProjStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        xjProjStsLog.setUpdUsr("TST USR");

        effRows = xjProjStsLogMapper.insert(xjProjStsLog);
        if (effRows != 1) {
            String errMsg = "写入询价项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto view(Long projId) throws Exception {

        logger.info("查看询价项目详情");

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

        XjProj entity = xjProjMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "查看询价项目详情 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        XjProjMatDtl matDtl = new XjProjMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<XjProjMatDtl> matDtls = xjProjMatDtlMapper.selectByMap(matDtlMap);

       /* List<IoBidProjOnMatInfDto> ioBidProjOnMatInfDtos=new ArrayList<>();
        BeanUtils.copyProperties(ioBidProjOnMatInfDtos,matDtls);
        for(IoBidProjOnMatInfDto ioBidProjOnMatInfDto:ioBidProjOnMatInfDtos){
            ioBidProjOnMatInfDto.setDlvDteStr(DateUtils.date2SimpleString(ioBidProjOnMatInfDto.getDlvDte()));
        }*/
        List<XjProjSplrInvt> xjProjSplrInvts = null;
        if (Constants.BID_RNG_TYP_VECTORING.equals(entity.getBidRngTyp())) {
            XjProjSplrInvt xjProjSplrInvt = new XjProjSplrInvt();
            xjProjSplrInvt.setProjId(projId);
            Map splrInvtMap = PageUtils.getQueryCondsMap(xjProjSplrInvt, 0, 0);
            xjProjSplrInvts = xjProjSplrInvtMapper.selectByEntity(splrInvtMap);
        }

        ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_XJBUD);
        if (comParm == null) {
            String errMsg = "查看询价项目详情 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_XJBUD + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        String atchPrefix = comParm.getParmVal();
        String refId = atchPrefix + projId;
        Atch atch = new Atch();
        atch.setRefId(refId);

        Map map = PageUtils.getQueryCondsMap(atch, 0, 0);

        List<Atch> atches = atchMapper.selectByMap(map);

        XjProjInfViewIoDto xjProjInfViewIoDto = new XjProjInfViewIoDto();
        xjProjInfViewIoDto.setXjProj(entity);
        xjProjInfViewIoDto.setMatDtls(matDtls);
        xjProjInfViewIoDto.setSplrs(xjProjSplrInvts);
        xjProjInfViewIoDto.setAtches(atches);
        xjProjInfViewIoDto.setMatTypDesc(matFactory.getMatTypDesc(entity.getMatTyp()));

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, xjProjInfViewIoDto);
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

        XjProj entity = xjProjMapper.selectByPrimaryKey(projId);
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "发布询价项目失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【编辑中】
        if (!Constants.XJ_PROJ_STS_EDTING.equals(entity.getProjSts())) {
            String errMsg = "发布线上招标项目失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 更新项目状态为 - 招标中；发布状态为 - 已发布
        entity.setNtcPubTim(DateUtils.getCurrentTimeStamp());
        entity.setProjSts(Constants.XJ_PROJ_STS_BIDDING);
        entity.setBidNtcPubFlg(Constants.BID_NTC_PUB_FLG_ON);
        entity.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setOpenKey(CryptUtils.getRandNum(6));
        int effRows = xjProjMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新项目状态为失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        ComParm comParmXjProjStsBidding = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_XJ_PROJ_STS, Constants.XJ_PROJ_STS_BIDDING);
        if (comParmXjProjStsBidding == null || !Constants.EFF_FLG_ON.equals(comParmXjProjStsBidding.getEffFlg())
                || StringUtils.isEmpty(comParmXjProjStsBidding.getParmVal())) {
            String errMsg = "发布询价项目失败 - 通用参数为定义：parmTyp[" + Constants.COM_PARM_TYP_XJ_PROJ_STS + "], parmCod[" + Constants.XJ_PROJ_STS_BIDDING + "]";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        XjProjStsLog xjProjStsLog = new XjProjStsLog();
        xjProjStsLog.setProjId(entity.getProjId());
        xjProjStsLog.setProjNam(entity.getProjNam());
        xjProjStsLog.setProjSts(comParmXjProjStsBidding.getParmVal());
        xjProjStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        xjProjStsLog.setUpdUsr("TST USR");

        effRows = xjProjStsLogMapper.insert(xjProjStsLog);
        if (effRows != 1) {
            String errMsg = "写入询价项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto cut(IoXjProjCutDto dto) throws Exception {
        logger.info("询价项目截标");

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

        XjProj xjProj = xjProjMapper.selectByPrimaryKey(projId);
        if (xjProj == null || Constants.EFF_FLG_OFF.equals(xjProj.getEffFlg())) {
            String errMsg = "询价项目截标 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【招标中】 或 【二次报价中】，若不符合，则返回异常
        if (!Constants.XJ_PROJ_STS_BIDDING.equals(xjProj.getProjSts())) {
            String errMsg = "询价项目截标 - 失败 - 项目状态不符合 - projSts[" + xjProj.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 更新项目状态为 - 【招标结束】, 截标备注
        String sts = ""; // 后置状态
        if (Constants.XJ_PROJ_STS_BIDDING.equals(xjProj.getProjSts())) {
            // 若为【招标中】，则更新到【招标结束】状态
            dto.setBidEndMemo(StringUtils.getByLength(dto.getBidEndMemo(), 255));
            xjProj.setBidEndMemo(dto.getBidEndMemo());
            sts = Constants.XJ_PROJ_STS_BID_END;
        } /*else if (Constants.BID_PROJ_ON_STS_QOTING2.equals(entity.getProjSts())) {
            // 若为【二次报价中】，则更新到【二次报价结束】状态
            dto.setBidEndMemo2(StringUtils.getByLength(dto.getBidEndMemo2(), 255));
            entity.setBidEndMemo2(dto.getBidEndMemo2());
            sts = Constants.BID_PROJ_ON_STS_QOT2_END;
        }*/

        xjProj.setProjSts(sts);
        xjProj.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        xjProj.setModUsr(realNam);
        xjProj.setModTim(DateUtils.getCurrentTimeStamp());

        int effRows = xjProjMapper.updateByPrimaryKeySelective(xjProj);
        if (effRows != 1) {
            String errMsg = "更新项目状态 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        XjProjStsLog xjProjStsLog = new XjProjStsLog();
        xjProjStsLog.setProjId(xjProj.getProjId());
        xjProjStsLog.setProjNam(xjProj.getProjNam());
        xjProjStsLog.setProjSts(sts);
        xjProjStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        xjProjStsLog.setUpdUsr(realNam);

        effRows = xjProjStsLogMapper.insert(xjProjStsLog);
        if (effRows != 1) {
            String errMsg = "写入询价项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto repeal(IoXjProjRepealDto dto) throws Exception {
        logger.info("询价项目申请废标");

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

        XjProj xjProj = xjProjMapper.selectByPrimaryKey(projId);
        if (xjProj == null || Constants.EFF_FLG_OFF.equals(xjProj.getEffFlg())) {
            String errMsg = "询价项目申请废标 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【编辑中:00】 或 【已废标:-20】 或 【申请废标审批中:-21】，若符合，则返回异常
        if (Constants.XJ_PROJ_STS_EDTING.equals(xjProj.getProjSts()) ||
                Constants.XJ_PROJ_STS_RPL.equals(xjProj.getProjSts()) ||
                Constants.XJ_PROJ_STS_RPL_ADTING.equals(xjProj.getProjSts())) {
            String errMsg = "询价项目申请废标 - 失败 - 项目状态不符合 - projSts[" + xjProj.getProjSts() + "]";
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
            String errMsg = "询价项目申请废标 - 失败 - 该采购员所属公司为空，无法提交BPM审批";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        String bpmDeptCode = userinfo.getCompany().getDept_code();

        String bpmSeqNo = BpmUtils.getBpmSeqNo(Constants.PROJ_TYP_XJ);
        String bpmBody = xjProjBpmAppFactory.getRplBpmBody(bpmSeqNo, xjProj, dto.getAppRplMemo());
        String templateCode = bpmDeptCode + Constants.BPM_APP_TYP_XJ_RPL_SUFFIX;
        boolean subSucFlg = BpmUtils.subApp(bpmBody, templateCode, "询价项目申请废标", "", null);
        if (!subSucFlg) {
            String errMsg = "询价项目申请废标 - 提交BPM审批失败";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 更新项目状态为 - 【申请废标审批中:-21】
        xjProj.setAppRplMemo(StringUtils.getByLength(dto.getAppRplMemo(), 255));
        xjProj.setBpmRplSeq(bpmSeqNo);
        xjProj.setProjSts(Constants.XJ_PROJ_STS_RPL_ADTING);
        xjProj.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        xjProj.setModUsr(realNam);
        xjProj.setModTim(DateUtils.getCurrentTimeStamp());

        int effRows = xjProjMapper.updateByPrimaryKeySelective(xjProj);
        if (effRows != 1) {
            String errMsg = "更新询价项目状态 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        XjProjStsLog xjProjStsLog = new XjProjStsLog();
        xjProjStsLog.setProjId(xjProj.getProjId());
        xjProjStsLog.setProjNam(xjProj.getProjNam());
        xjProjStsLog.setProjSts(Constants.XJ_PROJ_STS_RPL_ADTING);
        xjProjStsLog.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        xjProjStsLog.setUpdUsr(realNam);

        effRows = xjProjStsLogMapper.insert(xjProjStsLog);
        if (effRows != 1) {
            String errMsg = "写入询价项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto getBidInfByIoQueryXjProjSplrTendInfDto(IoQueryXjProjSplrTendInfDto dto, Integer pageNo, Integer pageSize) throws Exception {
        logger.info("根据条件分页查询询价项目投标信息");

        // 参数处理
        Long projId = dto.getProjId();
        if (projId == null) {
            String errMsg = "根据条件分页查询询价项目投标信息 - 失败：projId不得为空";
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

        List<XjProjSplrTendInf> list = xjProjSplrTendInfMapper.selectByMap(map);

        Integer count = xjProjSplrTendInfMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto viewBidDtl(Long id) throws Exception {
        logger.info("查看询价项目供应商投标信息详情");

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

        XjProjSplrTendInf entity = xjProjSplrTendInfMapper.selectByPrimaryKey(id);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "查看询价项目供应商投标信息详情 - 根据ID查询询价项目供应商投标信息详情无数据 - id[" + id + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto adtBidInf(IoXjProjBidAdtDto dto) throws Exception {
        logger.info("询价项目供应商投标审核");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = XjProjMngForPchsChecker.checkArgsForAdtBidInf(dto);
        if (!"".equals(checkRst)) {
            logger.error("参数校验 - 不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        XjProjSplrTendInf entity = xjProjSplrTendInfMapper.selectByPrimaryKey(dto.getId());
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "询价项目供应商投标审核 - 根据项目ID查询询价项目供应商投标信息无数据 - id[" + dto.getId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断标书状态是否为 【00-已申请】
        if (!Constants.BID_DOC_STS_APPLIED.equals(entity.getBidDocSts())) {
            String errMsg = "询价项目供应商投标审核 - 标书状态不符合 - projSts[" + entity.getBidDocSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 更新投标信息
        BeanUtils.copyProperties(entity, dto);
        entity.setModUsr(realNam);
        entity.setModTim(DateUtils.getCurrentTimeStamp());

        int effRows = xjProjSplrTendInfMapper.updateByPrimaryKeySelective(entity);
        if (effRows != 1) {
            String errMsg = "更新失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
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
        XjProj entity = xjProjMapper.selectByPrimaryKey(projId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "发送开标密钥 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        logger.info("2.2 业务校验 - 校验项目状态[已截标:21]或[二次报价结束:31]");
        if (!Constants.XJ_PROJ_STS_BID_END.equals(entity.getProjSts()
                )) {
            String errMsg = "发送开标密钥 - 校验项目状态[已截标:21]或[二次报价结束:31] - 失败 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        boolean sndFlg = SmsSndUtils.sendSmsForOpenXjProj(entity);

        if (!sndFlg) {
            String errMsg = "发送开标密钥 - 发送短信失败";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto openProj(IoXjProjOpenDto dto) throws Exception {

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        logger.info("询价项目开标");

        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String chkRst = XjProjMngForPchsChecker.checkArgsForOpenProj(dto);
        if (!"".equals(chkRst)) {
            String errMsg = "询价项目开标 - 参数校验 - 失败 - " + chkRst;
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        logger.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        long timeB = DateUtils.currentTimeMillis();
        logger.info("2. 业务校验 - 开始");
        logger.info("2.1 业务校验 - 根据项目ID查询项目信息");
        XjProj xjProj = xjProjMapper.selectByPrimaryKey(dto.getProjId());
        if (xjProj == null || Constants.EFF_FLG_OFF.equals(xjProj.getEffFlg())) {
            String errMsg = "询价项目开标 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + dto.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (!Constants.XJ_PROJ_STS_BID_END.equals(xjProj.getProjSts())) {
            String errMsg = "询价项目开标 - 业务校验 - 失败 - 项目状态异常 - projSts[" + xjProj.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (!dto.getOpenKey().equals(xjProj.getOpenKey())) {
            String errMsg = "询价项目开标 - 业务校验 - 失败 - 开标密钥不正确";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 数据持久化 - 开始");
        logger.info("3.1 更新项目状态至[22:已开标]或[32:已二次开标]");
        String projSts = null;
        if (Constants.XJ_PROJ_STS_BID_END.equals(xjProj.getProjSts())) {
            projSts = Constants.XJ_PROJ_STS_OPENED;
        } /*else {
            projSts = Constants.BID_PROJ_ON_STS_QOT2_OPENED;
        }*/
        xjProj.setProjSts(projSts);
        xjProj.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        Timestamp now = DateUtils.getCurrentTimeStamp();
        xjProj.setModTim(DateUtils.getCurrentTimeStamp());
        xjProj.setModUsr(realNam);

        int effRows = xjProjMapper.updateByPrimaryKeySelective(xjProj);
        if (effRows != 1) {
            String errMsg = "更新询价项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        XjProjStsLog xjProjStsLog = new XjProjStsLog();
        xjProjStsLog.setProjId(xjProj.getProjId());
        xjProjStsLog.setProjNam(xjProj.getProjNam());
        xjProjStsLog.setProjSts(xjProj.getProjSts());
        xjProjStsLog.setStsUpdTim(now);
        xjProjStsLog.setUpdUsr(realNam);

        effRows = xjProjStsLogMapper.insert(xjProjStsLog);
        if (effRows != 1) {
            String errMsg = "写入询价项目状态记录表失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.2 报价解密");
        Long projId = dto.getProjId();
        XjProjSplrTendInf xjProjSplrTendInf = new XjProjSplrTendInf();
        xjProjSplrTendInf.setProjId(projId);
        xjProjSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);

        Map map = PageUtils.getQueryCondsMap(xjProjSplrTendInf, 0, 0);
        List<XjProjSplrTendInf> tendInfs = xjProjSplrTendInfMapper.selectByMap(map);
        List<Long> qotIds = new ArrayList<>();
        for (XjProjSplrTendInf tendInf : tendInfs) {
            if (!Constants.BID_DOC_STS_ACCEPTED.equals(tendInf.getBidDocSts())) {
                continue;
            }

            if (Constants.XJ_PROJ_STS_OPENED.equals(projSts) && tendInf.getQotId() != null) {
                qotIds.add(tendInf.getQotId());
            }/* else if (Constants.BID_PROJ_ON_STS_QOT2_OPENED.equals(projSts) && tendInf.getQot2Id() != null) {
                qotIds.add(tendInf.getQot2Id());
            }*/
        }

        if (!qotIds.isEmpty()) {
            XjProjSplrQotDtl dtl = new XjProjSplrQotDtl();
            dtl.setEffFlg(Constants.EFF_FLG_ON);
            map = PageUtils.getQueryCondsMap(dtl, 0, 0);
            map.put("qotIds", qotIds);
            map.put("effFlg", Constants.EFF_FLG_ON);
            List<XjProjSplrQotDtl> xjProjSplrQotDtls = xjProjSplrQotDtlMapper.selectByMap(map);
            for (XjProjSplrQotDtl b : xjProjSplrQotDtls) {
                String ecry = b.getUntPriEcrp();
                if (!StringUtils.isEmpty(ecry)) {
                    BigDecimal untPri = new BigDecimal(CryptUtils.decrypt(ecry, xjProj.getOpenKey()));
                    b.setUntPri(untPri);
                    b.setModTim(DateUtils.getCurrentTimeStamp());
                    b.setModUsr(realNam);

                    effRows = xjProjSplrQotDtlMapper.updateByPrimaryKey(b);
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
    public OutputDto getQotInf(Long id) throws Exception {
        logger.info("根据投标ID查看报价信息: id[{}]", id);

        // 参数处理
        if (id == null) {
            String errMsg = "根据投标ID查看报价信息 - 参数校验 - 不通过 - ID为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        XjProjSplrTendInf xjProjSplrTendInf = xjProjSplrTendInfMapper.selectByPrimaryKey(id);
        if (xjProjSplrTendInf == null || Constants.EFF_FLG_OFF.equals(xjProjSplrTendInf.getEffFlg())) {
            String errMsg = "根据投标ID查看报价信息 - 参数校验 - 不通过 - 跟据ID查询投标信息表无数据";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        XjQotInfForPchsIoDto xjQotInfForPchsIoDto = new XjQotInfForPchsIoDto();

        Long qotId = xjProjSplrTendInf.getQotId();
        if (qotId != null) {
            XjProjSplrQotInf qotInf = xjProjSplrQotInfMapper.selectByPrimaryKey(qotId);
            XjProjSplrQotDtl entity = new XjProjSplrQotDtl();
            entity.setQotId(qotId);
            entity.setEffFlg(Constants.EFF_FLG_ON);
            Map map = PageUtils.getQueryCondsMap(entity, 0, 0);
            List<XjProjSplrQotDtl> qotDtls = xjProjSplrQotDtlMapper.selectByMap(map);
            xjQotInfForPchsIoDto.setQotInf(qotInf);
            List<IoXjProjSplrQotDto> qotDtos = new ArrayList<>();
            for(XjProjSplrQotDtl xjProjSplrQotDtl :qotDtls){
                XjProjMatDtl matDtl = xjProjMatDtlMapper.selectByPrimaryKey(xjProjSplrQotDtl.getMatId());
                IoXjProjSplrQotDto qotDto = new IoXjProjSplrQotDto();
                qotDto.setDlvAdr(matDtl.getDlvAdr());
                qotDto.setDlvDte(DateUtils.date2SimpleString(matDtl.getDlvDte()));
                qotDto.setMatId(matDtl.getId());
                qotDto.setMatNam(matDtl.getMatNam());
                qotDto.setMatUnt(matDtl.getMatUnt());
                qotDto.setMatBnd(xjProjSplrQotDtl.getMatBnd());
                qotDto.setCurrTyp(xjProjSplrQotDtl.getCurrTyp());
                qotDto.setSplNum(xjProjSplrQotDtl.getSplNum());
                qotDto.setUntPri(xjProjSplrQotDtl.getUntPri());
                qotDto.setUntPriEcrp(xjProjSplrQotDtl.getUntPriEcrp());
                qotDto.setSplrId(xjProjSplrQotDtl.getSplrId());
                qotDto.setProjId(xjProjSplrQotDtl.getProjId());
                qotDto.setQotId(xjProjSplrQotDtl.getQotId());
                qotDto.setEffFlg(xjProjSplrQotDtl.getEffFlg());
                qotDto.setExRat(xjProjSplrQotDtl.getExRat());
                qotDto.setTendDlvDte(DateUtils.date2SimpleString(xjProjSplrQotDtl.getTendDlvDte()));
                qotDto.setId(xjProjSplrQotDtl.getId());
                qotDtos.add(qotDto);
            }
            xjQotInfForPchsIoDto.setQotDtls(qotDtos);
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_XJQOT);
            if (comParm == null) {
                String errMsg = "根据投标ID查看报价信息 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_XJQOT + "]";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            String atchPrefix = comParm.getParmVal();
            String refId = atchPrefix + qotId;
            Atch atch = new Atch();
            atch.setRefId(refId);

            map = PageUtils.getQueryCondsMap(atch, 0, 0);

            List<Atch> atches = atchMapper.selectByMap(map);
            xjQotInfForPchsIoDto.setAtches(atches);
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

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, xjQotInfForPchsIoDto);
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

        XjProjSplrTendInf xjProjSplrTendInf = new XjProjSplrTendInf();
        xjProjSplrTendInf.setProjId(projId);
        xjProjSplrTendInf.setBidDocSts(bidDocSts);

        Map map = PageUtils.getQueryCondsMap(xjProjSplrTendInf, 0, 0);

        List<XjProjSplrTendInf> list = xjProjSplrTendInfMapper.selectByMap(map);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, list);
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

        XjProj xjProj = xjProjMapper.selectByPrimaryKey(projId);
        if (xjProj == null || !Constants.EFF_FLG_ON.equals(xjProj.getEffFlg())) {
            String errMsg = "获取开标记录表 - 失败：项目信息不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }

        // 获取物料信息
        XjProjMatDtl matDtl = new XjProjMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<XjProjMatDtl> matDtls = xjProjMatDtlMapper.selectByMap(matDtlMap);
        if (matDtls == null || matDtls.isEmpty()) {
            String errMsg = "获取开标记录表 - 失败：物料信息为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }

        // 获取投标供应商
        List<String> bidDocStses = Arrays.asList(new String[]{Constants.BID_DOC_STS_ACCEPTED, Constants.BID_DOC_STS_AWD, Constants.BID_DOC_STS_UNAWD});
        XjProjSplrTendInf xjProjSplrTendInf = new XjProjSplrTendInf();
        xjProjSplrTendInf.setProjId(projId);
        xjProjSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(xjProjSplrTendInf, 0, 0);
        map.put("bidDocStses", bidDocStses);
        map.put("qotIdNotNul", "true");
        List<XjProjSplrTendInf> tendInfs = xjProjSplrTendInfMapper.selectByMap(map);
        if (tendInfs == null || tendInfs.isEmpty()) {
            String errMsg = "获取开标记录表 - 失败：投标供应商为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }
        // 获取报价信息
        XjProjSplrQotDtl qotDtl = new XjProjSplrQotDtl();
        qotDtl.setProjId(projId);
        qotDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map qotDtlMap = PageUtils.getQueryCondsMap(qotDtl, 0, 0);
        List<XjProjSplrQotDtl> qotDtls = xjProjSplrQotDtlMapper.selectByMap(qotDtlMap);
        if (qotDtls == null || qotDtls.isEmpty()) {
            String errMsg = "获取开标记录表 - 失败：报价信息为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }
        // 为提高效率，将报价信息转化为map方便查询，键为 物料ID-供应商ID
        Map<String, XjProjSplrQotDtl> qdMap = new HashMap<>();
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
                XjProjSplrQotDtl qd = qdMap.get(key);
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
        XjProj xjProj = xjProjMapper.selectByPrimaryKey(projId);
        if (xjProj == null || Constants.EFF_FLG_OFF.equals(xjProj.getEffFlg())) {
            String errMsg = "获取申请决标相关信息 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        XjProjMatDtl matDtl = new XjProjMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<XjProjMatDtl> matDtls = xjProjMatDtlMapper.selectByMap(matDtlMap);

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
        XjProjSplrTendInf xjProjSplrTendInf = new XjProjSplrTendInf();
        xjProjSplrTendInf.setProjId(projId);
        xjProjSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(xjProjSplrTendInf, 0, 0);
        map.put("bidDocStses", bidDocStses);
        map.put("qotIdNotNul", "true");
        List<XjProjSplrTendInf> tendInfs = xjProjSplrTendInfMapper.selectByMap(map);
        if (tendInfs == null || tendInfs.isEmpty()) {
            String errMsg = "获取开标记录表 - 失败：投标供应商为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_WITH_NO_DATA);
        }

        Map<Long, Splr> splrMap = tendInfs.stream().collect(
                Collectors.toMap(XjProjSplrTendInf::getSplrId,
                        tendInf -> splrMapper.selectByPrimaryKey(tendInf.getSplrId())));

        XjProjSplrQotDtl qotDtlQuery = new XjProjSplrQotDtl();
        qotDtlQuery.setProjId(projId);
        qotDtlQuery.setEffFlg(Constants.EFF_FLG_ON);
        map = PageUtils.getQueryCondsMap(qotDtlQuery, 0, 0);
        List<XjProjSplrQotDtl> xjProjSplrQotDtls = xjProjSplrQotDtlMapper.selectByMap(map);
        List<XjProjSplrQotDtlIoDto> qotDtlIoDtos = xjProjSplrQotDtls.parallelStream()
                .filter(dtl -> dtl.getUntPri() != null && !dtl.getUntPri().equals(BigDecimal.ZERO))
                .filter(dtl -> dtl.getSplNum() != null && !dtl.getSplNum().equals("0"))
                .map(dtl -> {
                    XjProjSplrQotDtlIoDto xjProjSplrQotDtlIoDto = new XjProjSplrQotDtlIoDto();
                    BeanUtils.copyProperties(xjProjSplrQotDtlIoDto, dtl);
                    xjProjSplrQotDtlIoDto.setTrpPrice(dtl.getUntPri().multiply(new BigDecimal(dtl.getExRat())));
                    Splr splr = splrMap.get(dtl.getSplrId());
                    xjProjSplrQotDtlIoDto.setFullNam(splr.getFullNam());
                    return xjProjSplrQotDtlIoDto;
                })
                .collect(Collectors.toList());

        List<QueryXjAppAwdInfDtlIoDto> queryXjAppAwdInfDtlIoDtos = new ArrayList<>();
        if (matDtls != null || matDtls.size() > 0) {
            for (XjProjMatDtl m : matDtls) {
                QueryXjAppAwdInfDtlIoDto queryAppAwdInfDtlIoDto = new QueryXjAppAwdInfDtlIoDto();
                queryAppAwdInfDtlIoDto.setMatDtl(m);
                queryAppAwdInfDtlIoDto.setQotDtls(getQotDtls(m.getId(), qotDtlIoDtos));
                queryXjAppAwdInfDtlIoDtos.add(queryAppAwdInfDtlIoDto);
            }
        }

        QueryXjAppAwdInfIoDto queryXjAppAwdInfIoDto = new QueryXjAppAwdInfIoDto();
        queryXjAppAwdInfIoDto.setXjProj(xjProj);
        queryXjAppAwdInfIoDto.setXjAppAwdInfDtlIoDtos(queryXjAppAwdInfDtlIoDtos);
        queryXjAppAwdInfIoDto.setMatTypDesc(matFactory.getMatTypDesc(xjProj.getMatTyp()));

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, queryXjAppAwdInfIoDto);
    }


    private List<XjProjSplrQotDtlIoDto> getQotDtls(Long matId, List<XjProjSplrQotDtlIoDto> dtls) {
        if (StringUtils.isEmpty(String.valueOf(matId)) || dtls == null || dtls.isEmpty()) {
            return Collections.emptyList();
        }

        return dtls.parallelStream()
                .filter(dtl -> dtl.getMatId().equals(matId))
                .sorted(Comparator.comparing(XjProjSplrQotDtlIoDto::getTrpPrice))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto appAwd(HttpServletRequest request, IoXjAppAwdDto dto) throws Exception {
        logger.info("申请决标, dto: [{}]", dto);

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = XjProjMngForPchsChecker.checkArgsForAppAwd(dto);
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
        XjProj xjProj = xjProjMapper.selectByPrimaryKey(projId);
        if (xjProj == null || !Constants.EFF_FLG_ON.equals(xjProj.getEffFlg())) {
            String errMsg = "申请决标 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + dto.getProjId() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【开标结束】,
        String projSts = xjProj.getProjSts();
        if (!Constants.XJ_PROJ_STS_OPENED.equals(projSts)) {
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
        XjProjMatDtl matDtl = new XjProjMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<XjProjMatDtl> matDtls = xjProjMatDtlMapper.selectByMap(map);
        if (matDtls == null || matDtls.isEmpty()) {
            String errMsg = "申请决标 - 业务校验 - 异常 - 该项目无物料信息 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        Map<String, XjProjMatDtl> matMap = new HashMap<>();
        for (XjProjMatDtl dtl : matDtls) {
            matMap.put(String.valueOf(dtl.getId()), dtl);
        }

        logger.info("3.2 新增询价项目中标明细");
        // 删除已存在的中标明细
        xjProjWinDtlMapper.deleteByProjId(projId);
        // 新增中标明细
        List<IoXjAppAwdDtlDto> appAwdDtlDtos = dto.getAppAwdDtls();
        for (IoXjAppAwdDtlDto dtlDto : appAwdDtlDtos) {
            XjProjWinDtl winDtl = new XjProjWinDtl();
            winDtl.setCrtTim(DateUtils.getCurrentTimeStamp());
            winDtl.setCrtUsr(realNam);
            winDtl.setEffFlg(Constants.EFF_FLG_ON);
            XjProjMatDtl matInf = matMap.get(String.valueOf(dtlDto.getMatId()));
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
            int effRows = xjProjWinDtlMapper.insert(winDtl);
            if (effRows != 1) {
                String errMsg = "新增询价项目中标明细 - 受影响行数不为1";
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        }

        logger.info("3.3 提交BPM审批");

       /* BidProjOnExptGrdInf exptGrdInfEntity = new BidProjOnExptGrdInf();
        exptGrdInfEntity.setEffFlg(Constants.EFF_FLG_ON);
        exptGrdInfEntity.setProjId(projId);
        exptGrdInfEntity.setProjGrdSts(Constants.PROJ_GRD_STS_DONE);
        Map exptGrdInfMap = PageUtils.getQueryCondsMap(exptGrdInfEntity, 0, 0);
        List<BidProjOnExptGrdInf> exptGrdInfs = bidProjOnExptGrdInfMapper.selectByMap(exptGrdInfMap);
        StringBuilder grdExpts = new StringBuilder("");
        if (exptGrdInfs != null && !exptGrdInfs.isEmpty()) {
            exptGrdInfs.stream().forEach(exptGrdInf -> grdExpts.append(exptGrdInf.getExptNam()).append(";"));
        }
*/
        String token = BpmFileUtils.getToken(userinfo.getUsername());
        String rootPath = request.getServletContext().getRealPath("/");
        String basePath = new File(rootPath + "..").getCanonicalPath();

        //暂时不加pdf附件
       /* String fileName = getOpenRcdFile(request, bidProjOn);
        String filePath = basePath + File.separator + Constants.ATCH_FILE_PATH + File.separator + fileName;
        String fileNameBpm = bidProjOn.getProjNam() + "-开标记录表-" + DateUtils.getTimeStampLong(DateUtils.getCurrentTimeStamp()) + ".pdf";
        Long field = BpmFileUtils.uploadAttachment(userinfo.getUsername(), token, filePath, fileNameBpm);
        List<Long> fields = new ArrayList<>();
        fields.add(field);*/

        String bpmSeqNo = BpmUtils.getBpmSeqNo(Constants.PROJ_TYP_XJ);
        String bpmBody = xjProjBpmAppFactory.getAwdBpmBody(bpmSeqNo, xjProj, dto.getAppAwdDtls(), matMap, dto.getAppAwdMemo());
        String templateCode =Constants.BPM_APP_TYP_XJ_AWD_SUFFIX + bpmDeptCode  ;
        boolean subSucFlg = BpmUtils.subApp(bpmBody, templateCode, "询价项目决标申请","",null);
        if (!subSucFlg) {
            String errMsg = "询价项目决标申请 - 提交BPM审批失败";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        logger.info("3.4 将项目状态置为【决标申请中】");
        Timestamp now = DateUtils.getCurrentTimeStamp();
        xjProj.setBpmFinSeq(bpmSeqNo);
        xjProj.setModUsr(realNam);
        xjProj.setModTim(now);
        xjProj.setEffFlg(Constants.EFF_FLG_ON);
        xjProj.setAppAwdMemo(dto.getAppAwdMemo());
        xjProj.setProjSts(Constants.XJ_PROJ_STS_AWDING);
        xjProj.setStsUpdTim(DateUtils.getCurrentTimeStamp());
        int effRows = xjProjMapper.updateByPrimaryKeySelective(xjProj);
        if (effRows != 1) {
            String errMsg = "更新询价项目失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 写入线上招标项目状态记录表
        XjProjStsLog xjProjStsLog = new XjProjStsLog();
        xjProjStsLog.setProjId(xjProj.getProjId());
        xjProjStsLog.setProjNam(xjProj.getProjNam());
        xjProjStsLog.setProjSts(Constants.XJ_PROJ_STS_AWDING);
        xjProjStsLog.setStsUpdTim(now);
        xjProjStsLog.setUpdUsr(realNam);

        effRows = xjProjStsLogMapper.insert(xjProjStsLog);
        if (effRows != 1) {
            String errMsg = "写入询价项目状态记录表失败 - 受影响行数不为1";
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
        XjProj xjProj = xjProjMapper.selectByPrimaryKey(projId);
        if (xjProj == null || !Constants.EFF_FLG_ON.equals(xjProj.getEffFlg())) {
            String errMsg = "查看招标结果 - 业务校验 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【40:决标审批中】或【50:决标审批通过】
        String projSts = xjProj.getProjSts();
        if (!Constants.XJ_PROJ_STS_AWDING.equals(projSts) && !Constants.XJ_PROJ_STS_AWD_ACCEPTED.equals(projSts)) {
            String errMsg = "查看招标结果 - 业务校验 - 失败 - 项目状态不符合 - projSts[" + projSts + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        logger.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        XjProjMatDtl xjProjMatDtl = new XjProjMatDtl();
        xjProjMatDtl.setProjId(xjProj.getProjId());
        xjProjMatDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matMap = PageUtils.getQueryCondsMap(xjProjMatDtl,0,0);
        List<XjProjMatDtl> matDtlList = xjProjMatDtlMapper.selectByMap(matMap);
        Map<String,XjProjMatDtl> matDtlMap = new HashMap<>();
        for(XjProjMatDtl matDtl:matDtlList){
            matDtlMap.put(String.valueOf(matDtl.getId()),matDtl);
        }
        long timeC = DateUtils.currentTimeMillis();
        logger.info("3. 查询数据库 - 开始");
        logger.info("3.1 查询该项目下所有物料明细");
        XjProjWinDtl xjProjWinDtl = new XjProjWinDtl();
        xjProjWinDtl.setProjId(projId);
        xjProjWinDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(xjProjWinDtl, 0, 0);
        List<XjProjWinDtl> list = xjProjWinDtlMapper.selectByMap(map);
        List<XjProjAwdMatDtlDto> xjProjAwdMatDtlDtos = new ArrayList<>();
        for(XjProjWinDtl winDtl :list){
            XjProjMatDtl matInf = matDtlMap.get(String.valueOf(winDtl.getMatId()));
            XjProjAwdMatDtlDto xjProjAwdMatDtlDto = new XjProjAwdMatDtlDto();
            xjProjAwdMatDtlDto.setMatNam(matInf.getMatNam());
            xjProjAwdMatDtlDto.setMatId(matInf.getId());
            xjProjAwdMatDtlDto.setMatUnt(matInf.getMatUnt());
            xjProjAwdMatDtlDto.setMatCod(matInf.getMatCod());
            xjProjAwdMatDtlDto.setMatBnd(winDtl.getMatBnd());
            xjProjAwdMatDtlDto.setCurrTyp(winDtl.getCurrTyp());
            xjProjAwdMatDtlDto.setDlvAdr(matInf.getDlvAdr());
            xjProjAwdMatDtlDto.setExRat(winDtl.getExRat());
            xjProjAwdMatDtlDto.setPchsNum(winDtl.getPchsNum());
            xjProjAwdMatDtlDto.setSplrNam(winDtl.getSplrNam());
            xjProjAwdMatDtlDto.setTendDlvDte(DateUtils.date2SimpleString(winDtl.getTendDlvDte()));
            xjProjAwdMatDtlDto.setTtlPri(winDtl.getTtlPri());
            xjProjAwdMatDtlDto.setUntPri(winDtl.getUntPri());
            xjProjAwdMatDtlDto.setProjId(winDtl.getProjId());
            xjProjAwdMatDtlDto.setDlvDte(DateUtils.date2SimpleString(matInf.getDlvDte()));
            xjProjAwdMatDtlDtos.add(xjProjAwdMatDtlDto);
        }
        long durationC = DateUtils.currentTimeMillis() - timeC;
        logger.info("3. 查询数据库 - 结束，duration[" + durationC + "ms]");

        if (list == null || list.isEmpty()) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        } else {
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, xjProjAwdMatDtlDtos);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto pubRst(Long projId) throws Exception {

        logger.info("发布询价项目结果");

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

        XjProj entity = xjProjMapper.selectByPrimaryKey(projId);
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "发布询价项目结果 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【招标申请审批通过】或【废标审批通过】
        if (!Constants.XJ_PROJ_STS_AWD_ACCEPTED.equals(entity.getProjSts()) && !Constants.XJ_PROJ_STS_RPL.equals(entity.getProjSts())) {
            String errMsg = "发布询价项目结果 - 失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        entity.setBidRstPubFlg(Constants.BID_RST_PUB_FLG_ON);
        entity.setRstPubTim(DateUtils.getCurrentTimeStamp());
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        int effRows = xjProjMapper.updateByPrimaryKeySelective(entity);
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
        logger.info("发布询价项目废标结果");

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

        XjProj entity = xjProjMapper.selectByPrimaryKey(projId);
        if (entity == null || !Constants.EFF_FLG_ON.equals(entity.getEffFlg())) {
            String errMsg = "发布询价项目废标结果 - 失败 - 根据项目ID查询项目信息无数据 - projId[" + projId + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        // 判断项目状态是否为 【-20已废标】
        if (!Constants.XJ_PROJ_STS_RPL.equals(entity.getProjSts())) {
            String errMsg = "发布询价项目废标结果 - 失败 - 项目状态不符合 - projSts[" + entity.getProjSts() + "]";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        entity.setRplNtcInf(rplNtcInf);
        entity.setBidRstPubFlg(Constants.BID_RST_PUB_FLG_ON);
        entity.setRstPubTim(DateUtils.getCurrentTimeStamp());
        entity.setModTim(DateUtils.getCurrentTimeStamp());
        entity.setModUsr(realNam);
        int effRows = xjProjMapper.updateByPrimaryKeyWithBLOBs(entity);
        if (effRows != 1) {
            String errMsg = "更新项目信息为失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
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
    public ResponseEntity<byte[]> exportOpenInf(HttpServletRequest request, Long projId) throws Exception {
        logger.info("导出开标记录表, projId[{}]", projId);

        // 参数处理
        if (projId == null || projId.equals(0L)) {
            String errMsg = "导出开标记录表 - 失败：projId不得为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        XjProj projInf = xjProjMapper.selectByPrimaryKey(projId);
        if (projInf == null || !Constants.EFF_FLG_ON.equals(projInf.getEffFlg())) {
            String errMsg = "导出开标记录表 - 失败：项目信息不存在";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 获取物料信息
        XjProjMatDtl matDtl = new XjProjMatDtl();
        matDtl.setProjId(projId);
        matDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map matDtlMap = PageUtils.getQueryCondsMap(matDtl, 0, 0);
        List<XjProjMatDtl> matDtls = xjProjMatDtlMapper.selectByMap(matDtlMap);
        if (matDtls == null || matDtls.isEmpty()) {
            String errMsg = "导出开标记录表 - 失败：物料信息为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 获取投标供应商
        List<String> bidDocStses = Arrays.asList(new String[]{Constants.BID_DOC_STS_ACCEPTED, Constants.BID_DOC_STS_AWD, Constants.BID_DOC_STS_UNAWD});
        XjProjSplrTendInf xjProjSplrTendInf = new XjProjSplrTendInf();
        xjProjSplrTendInf.setProjId(projId);
        xjProjSplrTendInf.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(xjProjSplrTendInf, 0, 0);
        map.put("bidDocStses", bidDocStses);
        map.put("qotIdNotNul", "true");
        List<XjProjSplrTendInf> tendInfs = xjProjSplrTendInfMapper.selectByMap(map);
        if (tendInfs == null || tendInfs.isEmpty()) {
            String errMsg = "导出开标记录表 - 失败：投标供应商为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 获取报价信息
        XjProjSplrQotDtl qotDtl = new XjProjSplrQotDtl();
        qotDtl.setProjId(projId);
        qotDtl.setEffFlg(Constants.EFF_FLG_ON);
        Map qotDtlMap = PageUtils.getQueryCondsMap(qotDtl, 0, 0);
        List<XjProjSplrQotDtl> qotDtls = xjProjSplrQotDtlMapper.selectByMap(qotDtlMap);
        if (qotDtls == null || qotDtls.isEmpty()) {
            String errMsg = "导出开标记录表 - 失败：报价信息为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        // 为提高效率，将报价信息转化为map方便查询，键为 物料ID-供应商ID
        Map<String, XjProjSplrQotDtl> qdMap = new HashMap<>();
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
                XjProjSplrQotDtl qd = qdMap.get(key);
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
            XjProjSplrTendInf tendInf = tendInfs.get(i);
            BigDecimal ttlPri = new BigDecimal(0);
            for (XjProjSplrQotDtl qd : qotDtls) {
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

        for (XjProjSplrTendInf tendInf : tendInfs) {
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

}
