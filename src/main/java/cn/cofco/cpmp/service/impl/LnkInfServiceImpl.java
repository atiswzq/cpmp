package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.LnkInfMapper;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.PagedResult;
import cn.cofco.cpmp.entity.LnkInf;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ILnkInfService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.ContextTools;
import cn.cofco.cpmp.utils.CurrentUserInfo;
import cn.cofco.cpmp.utils.DateUtils;
import cn.cofco.cpmp.utils.PageUtils;
import cn.cofco.cpmp.utils.checkers.LnkInfChecker;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Xujy on 2017/4/29.
 */
@Service
@Transactional("transactionManager")
public class LnkInfServiceImpl implements ILnkInfService {

    private static Logger LOGGER = LoggerManager.getPortalMngLog();

    @Resource
    private LnkInfMapper lnkInfMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto queryByConds(LnkInf entity, Integer pageNo, Integer pageSize) throws Exception {

        LOGGER.info("根据条件分页查询链接信息");

        // 参数处理
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize + 1;
        Integer to = pageNo * pageSize;

        LnkInf lnkInf = new LnkInf();
        lnkInf.setLnkNam(entity.getLnkNam());
        lnkInf.setEffFlg(Constants.EFF_FLG_ON);
        lnkInf.setPubFlg(entity.getPubFlg());
        Map map = PageUtils.getQueryCondsMap(lnkInf, start, to);

        List<LnkInf> list = lnkInfMapper.selectByMap(map);

        Integer count = lnkInfMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);

    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto view(Long lnkId) throws Exception {

        LOGGER.info("根据链接ID查询链接信息");

        // 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        if (lnkId == null || lnkId == 0L) {
            String errMsg = "参数校验 - 不通过 - lnkId不得为空";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        LnkInf entity = lnkInfMapper.selectByPrimaryKey(lnkId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "根据链接ID查询链接信息 - 根据链接ID查询链接信息无数据 - lnkId[" + lnkId + "]";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.SUC_OPR_NIL, errMsg);
        }

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, entity);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto add(LnkInf entity) throws Exception {

        LOGGER.info("新增链接");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        String chkRst = LnkInfChecker.checkArgsForAdd(entity);
        if (!"".equals(chkRst)) {
            LOGGER.error("新增链接 - 参数校验不通过 - 参数信息：" + entity + " +++ 校验结果：" + chkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, chkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        // 2. 数据库操作
        long timeB = DateUtils.currentTimeMillis();
        LOGGER.info("2. 数据入库 - 开始");
        LnkInf lnkInf = new LnkInf();
        lnkInf.setEffFlg(Constants.EFF_FLG_ON);
        lnkInf.setLnkNam(entity.getLnkNam());
        lnkInf.setLnkUrl(entity.getLnkUrl());
        lnkInf.setLnkPic(entity.getLnkPic());
        lnkInf.setPubFlg(entity.getPubFlg());
        lnkInf.setCrtUsr(realNam);
        lnkInf.setCrtTim(DateUtils.getCurrentTimeStamp());

        int effRows = lnkInfMapper.insert(lnkInf);
        if (effRows != 1) {
            String errMsg = "新增链接信息 - 失败 - 受影响行数不为1";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        LOGGER.info("2. 数据入库 - 结束，duration[" + durationB + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto edit(LnkInf entity) throws Exception {
        LOGGER.info("编辑链接");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        String chkRst = LnkInfChecker.checkArgsForEdit(entity);
        if (!"".equals(chkRst)) {
            LOGGER.error("编辑链接 - 参数校验不通过 - 参数信息：" + entity + " +++ 校验结果：" + chkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, chkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        long timeB = DateUtils.currentTimeMillis();
        LOGGER.info("2. 业务校验 - 开始");
        Long lnkId = entity.getLnkId();
        LnkInf lnkInf = lnkInfMapper.selectByPrimaryKey(lnkId);
        if (lnkInf == null || Constants.EFF_FLG_OFF.equals(lnkInf.getEffFlg())) {
            String errMsg = "编辑链接 - 业务校验不通过 - 根据lnkId查询链接信息无数据 - lnkId: " + lnkId;
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        LOGGER.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        // 2. 数据库操作
        long timeC = DateUtils.currentTimeMillis();
        LOGGER.info("3. 数据持久化 - 开始");
        lnkInf.setLnkNam(entity.getLnkNam());
        lnkInf.setLnkUrl(entity.getLnkUrl());
        lnkInf.setLnkPic(entity.getLnkPic());
        lnkInf.setPubFlg(entity.getPubFlg());
        lnkInf.setModUsr(realNam);
        lnkInf.setModTim(DateUtils.getCurrentTimeStamp());
        int effRows = lnkInfMapper.updateByPrimaryKeySelective(lnkInf);
        if (effRows != 1) {
            String errMsg = "编辑链接 - 失败 - 受影响行数不为1";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        long durationC = DateUtils.currentTimeMillis() - timeC;
        LOGGER.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto del(Long lnkId) throws Exception {
        LOGGER.info("删除链接信息");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        if (lnkId == null || lnkId == 0L) {
            String errMsg = "删除链接信息 - 参数校验失败 - 链接ID为空";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        long timeB = DateUtils.currentTimeMillis();
        LOGGER.info("2. 业务校验 - 开始");
        LnkInf lnkInf = lnkInfMapper.selectByPrimaryKey(lnkId);
        if (lnkInf == null || Constants.EFF_FLG_OFF.equals(lnkInf.getEffFlg())) {
            String errMsg = "删除链接信息 - 业务校验失败 - 根据lnkId查询链接信息无数据 - lnkId: " + lnkId;
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        LOGGER.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        // 3. 数据库操作
        long timeC = DateUtils.currentTimeMillis();
        LOGGER.info("3. 数据持久化 - 开始");
        lnkInf.setEffFlg(Constants.EFF_FLG_OFF);
        lnkInf.setModUsr(realNam);
        lnkInf.setModTim(DateUtils.getCurrentTimeStamp());
        // 2.1 更新链接信息
        int effRows = lnkInfMapper.updateByPrimaryKeySelective(lnkInf);
        if (effRows != 1) {
            String errMsg = "删除链接信息 - 失败 - 受影响行数不为1";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        long durationC = DateUtils.currentTimeMillis() - timeC;
        LOGGER.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto pub(Long lnkId) throws Exception {
        LOGGER.info("发布链接");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        if (lnkId == null || lnkId == 0L) {
            String errMsg = "发布链接 - 参数校验失败 - 链接ID为空";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        LOGGER.info("2. 业务校验 - 开始");
        LnkInf lnkInf = lnkInfMapper.selectByPrimaryKey(lnkId);
        if (lnkInf == null || Constants.EFF_FLG_OFF.equals(lnkInf.getEffFlg())) {
            String errMsg = "发布链接 - 业务校验失败 - 根据lnkId查询链接信息无数据 - lnkId: " + lnkId;
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (Constants.ARTCL_PUB_FLG_ON.equals(lnkInf.getPubFlg())) {
            String errMsg = "发布链接 - 业务校验失败 - 链接已发布";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        LOGGER.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        // 3. 数据库操作
        long timeC = DateUtils.currentTimeMillis();
        LOGGER.info("3. 数据持久化 - 开始");
        lnkInf.setPubFlg(Constants.ARTCL_PUB_FLG_ON);
        lnkInf.setModUsr(realNam);
        lnkInf.setModTim(DateUtils.getCurrentTimeStamp());
        // 2.1 更新链接信息
        int effRows = lnkInfMapper.updateByPrimaryKeySelective(lnkInf);
        if (effRows != 1) {
            String errMsg = "发布链接 - 失败 - 受影响行数不为1";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        LOGGER.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto cclPub(Long lnkId) throws Exception {
        LOGGER.info("取消发布链接");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        if (lnkId == null || lnkId == 0L) {
            String errMsg = "取消发布链接 - 参数校验失败 - 链接ID为空";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        LOGGER.info("2. 业务校验 - 开始");
        LnkInf lnkInf = lnkInfMapper.selectByPrimaryKey(lnkId);
        if (lnkInf == null || Constants.EFF_FLG_OFF.equals(lnkInf.getEffFlg())) {
            String errMsg = "取消发布链接 - 业务校验失败 - 根据lnkId查询链接信息无数据 - lnkId: " + lnkId;
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (Constants.ARTCL_PUB_FLG_OFF.equals(lnkInf.getPubFlg())) {
            String errMsg = "发布链接 - 业务校验失败 - 链接已取消发布";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        LOGGER.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        // 3. 数据库操作
        long timeC = DateUtils.currentTimeMillis();
        LOGGER.info("3. 数据持久化 - 开始");
        lnkInf.setPubFlg(Constants.ARTCL_PUB_FLG_OFF);
        lnkInf.setModUsr(realNam);
        lnkInf.setModTim(DateUtils.getCurrentTimeStamp());
        // 2.1 更新链接信息
        int effRows = lnkInfMapper.updateByPrimaryKeySelective(lnkInf);
        if (effRows != 1) {
            String errMsg = "取消发布链接 - 失败 - 受影响行数不为1";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        LOGGER.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

}
