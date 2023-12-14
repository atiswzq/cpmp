package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.ExptInfMapper;
import cn.cofco.cpmp.dto.IoExptLoginDto;
import cn.cofco.cpmp.dto.IoExptModPswDto;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.PagedResult;
import cn.cofco.cpmp.entity.ExptInf;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IExptService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;
import cn.cofco.cpmp.utils.checkers.ExptInfChecker;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Xujy on 2017/5/30.
 * for [专家服务实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class ExptServiceImpl implements IExptService {

    private static Logger logger = LoggerManager.getExptMngLog();

    @Resource
    private ExptInfMapper exptInfMapper;

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
    public OutputDto getExptInfsByConds(ExptInf entity, Integer pageNo, Integer pageSize) throws Exception {

        logger.info("根据条件查询专家信息");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize + 1;
        Integer to = pageNo * pageSize;

        entity.setEffFlg(Constants.EFF_FLG_ON);

        Map map = PageUtils.getQueryCondsMap(entity, start, to);

        List<ExptInf> list = exptInfMapper.selectByMap(map);

        Integer count = exptInfMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto add(ExptInf entity) throws Exception {

        logger.info("新增专家信息");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        // 1. 参数校验
        long time = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = ExptInfChecker.checkArgsForAdd(entity);
        if (!"".equals(checkRst)) {
            logger.error("参数校验 - 不通过 - 参数信息：" + entity + " +++ 校验结果：" + checkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        ExptInf exptInf  = new ExptInf();
        exptInf.setMobNbr(entity.getMobNbr());
        Map map = PageUtils.getQueryCondsMap(exptInf,0,0);
        Integer conut = exptInfMapper.countByMap(map);
         if(conut>0){
             logger.error("参数校验-不通过-参数信息："+entity.getMobNbr()+"该手机号已经存在，请用其他手机号设置");
             String errMsg = "该手机号已经存在，请用其他手机号设置";
             return  OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,RtnEnum.ARG_INVALID,errMsg);
         }
        long duration = DateUtils.currentTimeMillis() - time;
        logger.info("1. 参数校验 - 结束，duration[" + duration + "ms]");

        // 2. 新增至数据库
        time = DateUtils.currentTimeMillis();
        logger.info("2. 新增至数据库 - 开始");
        // 设置初始密码
        entity.setExptPsw(CryptUtils.md5(Constants.EXPT_PSW_ORI));
        entity.setCrtUsr(userinfo.getRealname());
        entity.setCrtTim(DateUtils.getCurrentTimeStamp());
        entity.setEffFlg(Constants.EFF_FLG_ON);
        entity.setExptId(null);
        entity.setModUsr(null);
        entity.setModTim(null);
        int effRows = exptInfMapper.insert(entity);
        if (effRows != 1) {
            String errMsg = "新增专家信息 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        duration = DateUtils.currentTimeMillis() - time;
        logger.info("2. 新增至数据库 - 结束，duration[" + duration + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto del(Long exptId) throws Exception {

        logger.info("删除专家信息");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        // 1. 参数校验
        long time = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (exptId == null || exptId == 0l) {
            String errMsg = "删除专家信息 - 失败 - 参数校验不通过 - 专家ID不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        ExptInf exptInf = exptInfMapper.selectByPrimaryKey(exptId);
        if (exptInf == null || Constants.EFF_FLG_OFF.equals(exptInf.getEffFlg())) {
            String errMsg = "删除专家信息 - 失败 - 专家信息不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long duration = DateUtils.currentTimeMillis() - time;
        logger.info("1. 参数校验 - 结束，duration[" + duration + "ms]");


        // 2. 新增至数据库
        time = DateUtils.currentTimeMillis();
        logger.info("2. 更新至数据库 - 开始");
        // TODO 从session获取当前用户编号
        exptInf.setEffFlg(Constants.EFF_FLG_OFF);
        exptInf.setModUsr(userinfo.getRealname());
        exptInf.setModTim(DateUtils.getCurrentTimeStamp());
        int effRows = exptInfMapper.updateByPrimaryKey(exptInf);
        if (effRows != 1) {
            String errMsg = "删除专家信息 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        duration = DateUtils.currentTimeMillis() - time;
        logger.info("2. 删除专家信息 - 结束，duration[" + duration + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto mod(ExptInf entity) throws Exception {
        logger.info("修改专家信息");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        // 1. 参数校验
        long time = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = ExptInfChecker.checkArgsForMod(entity);
        if (!"".equals(checkRst)) {
            String errMsg = "修改专家信息 - 失败 - 参数校验不通过 - 参数信息：" + entity + " +++ 校验结果：" + checkRst;
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        ExptInf exptInf = exptInfMapper.selectByPrimaryKey(entity.getExptId());
        if (exptInf == null || Constants.EFF_FLG_OFF.equals(exptInf.getEffFlg())) {
            String errMsg = "修改专家信息 - 失败 - 专家信息不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long duration = DateUtils.currentTimeMillis() - time;
        logger.info("1. 参数校验 - 结束，duration[" + duration + "ms]");


        // 2. 更新至数据库
        time = DateUtils.currentTimeMillis();
        logger.info("2. 更新至数据库 - 开始");
        // 设置初始密码
//        exptInf.setExptPsw(CryptUtils.md5(Constants.EXPT_PSW_ORI));
        // TODO 从session获取当前用户编号
        exptInf.setExptNam(entity.getExptNam());
        exptInf.setMobNbr(entity.getMobNbr());
        exptInf.setExptTtl(entity.getExptTtl());
        exptInf.setModUsr(userinfo.getRealname());
        exptInf.setModTim(DateUtils.getCurrentTimeStamp());
        int effRows = exptInfMapper.updateByPrimaryKey(exptInf);
        if (effRows != 1) {
            String errMsg = "更新至数据库 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        duration = DateUtils.currentTimeMillis() - time;
        logger.info("2. 更新至数据库 - 结束，duration[" + duration + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto modPsw(IoExptModPswDto dto) throws Exception {
        logger.info("修改密码");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        Integer userId = userinfo.getUserid();

        // 1. 参数校验
        long time = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");

        Long exptId = Long.valueOf(userId);
        String checkRst = ExptInfChecker.checkArgsForModPsw(dto);
        if (!"".equals(checkRst)) {
            String errMsg = "修改密码 - 失败 - 参数校验不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst;
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        ExptInf exptInf = exptInfMapper.selectByPrimaryKey(exptId);
        if (exptInf == null || Constants.EFF_FLG_OFF.equals(exptInf.getEffFlg())) {
            String errMsg = "修改密码 - 失败 - 专家信息不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        String exptPsw = CryptUtils.md5(dto.getPsw());
        if (!exptPsw.equals(exptInf.getExptPsw())) {
            String errMsg = "修改密码 - 失败 - 原密码错误";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long duration = DateUtils.currentTimeMillis() - time;
        logger.info("1. 参数校验 - 结束，duration[" + duration + "ms]");


        // 2. 更新至数据库
        time = DateUtils.currentTimeMillis();
        logger.info("2. 更新至数据库 - 开始");
        // 设置新密码
        exptInf.setExptPsw(CryptUtils.md5(dto.getPswNew()));
        exptInf.setModUsr(userinfo.getRealname());
        exptInf.setModTim(DateUtils.getCurrentTimeStamp());
        int effRows = exptInfMapper.updateByPrimaryKey(exptInf);
        if (effRows != 1) {
            String errMsg = "修改密码 - 更新至数据库 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        duration = DateUtils.currentTimeMillis() - time;
        logger.info("2. 更新至数据库 - 结束，duration[" + duration + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto resetPsw(Long exptId) throws Exception {
        logger.info("重置专家密码");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        // 1. 参数校验
        long time = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (exptId == null || exptId == 0l) {
            String errMsg = "重置专家密码 - 失败 - 参数校验不通过 - 专家ID不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        ExptInf exptInf = exptInfMapper.selectByPrimaryKey(exptId);
        if (exptInf == null || Constants.EFF_FLG_OFF.equals(exptInf.getEffFlg())) {
            String errMsg = "重置专家密码 - 失败 - 专家信息不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long duration = DateUtils.currentTimeMillis() - time;
        logger.info("1. 参数校验 - 结束，duration[" + duration + "ms]");


        // 2. 更新至数据库
        time = DateUtils.currentTimeMillis();
        logger.info("2. 重置专家密码 - 更新至数据库 - 开始");
        // 设置默认密码
        exptInf.setExptPsw(CryptUtils.md5(Constants.EXPT_PSW_ORI));
        exptInf.setModUsr(userinfo.getRealname());
        exptInf.setModTim(DateUtils.getCurrentTimeStamp());
        int effRows = exptInfMapper.updateByPrimaryKey(exptInf);
        if (effRows != 1) {
            String errMsg = "重置专家密码 - 失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        duration = DateUtils.currentTimeMillis() - time;
        logger.info("2. 重置专家密码 - 更新至数据库 - 结束，duration[" + duration + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto login(IoExptLoginDto dto) throws Exception {
        logger.info("专家登录");

        long time = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        String checkRst = ExptInfChecker.checkArgsForLogin(dto);
        if (!"".equals(checkRst)) {
            String errMsg = "专家登录 - 失败 - 参数校验不通过 - 参数信息：" + dto + " +++ 校验结果：" + checkRst;
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
        }
        Map map = PageUtils.getQueryCondsMap(dto, 0, 1);
        List<ExptInf> exptInfs = exptInfMapper.selectByMap(map);
        if (exptInfs == null || exptInfs.isEmpty() || exptInfs.size() != 1 || Constants.EFF_FLG_OFF.equals(exptInfs.get(0).getEffFlg())) {
            String errMsg = "专家登录 - 失败 - 专家不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        ExptInf exptInf = exptInfs.get(0);
        if (!exptInf.getExptPsw().equals(CryptUtils.md5(dto.getExptPsw()))) {
            String errMsg = "专家登录 - 失败 - 密码不正确";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long duration = DateUtils.currentTimeMillis() - time;
        logger.info("1. 参数校验 - 结束，duration[" + duration + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, exptInf);
    }


    @Override
    public OutputDto view(Long exptId) throws Exception {
        logger.info("根据专家ID查询专家信息");

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }

        // 1. 参数校验
        long time = DateUtils.currentTimeMillis();
        logger.info("1. 参数校验 - 开始");
        if (exptId == null || exptId == 0l) {
            String errMsg = "根据专家ID查询专家信息 - 失败 - 参数校验不通过 - 专家ID不得为空";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        ExptInf exptInf = exptInfMapper.selectByPrimaryKey(exptId);
        if (exptInf == null || Constants.EFF_FLG_OFF.equals(exptInf.getEffFlg())) {
            String errMsg = "删除专家信息 - 失败 - 专家信息不存在";
            logger.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long duration = DateUtils.currentTimeMillis() - time;
        logger.info("1. 参数校验 - 结束，duration[" + duration + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR, exptInf);
    }
}
