package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.ArtclMapper;
import cn.cofco.cpmp.dao.AtchMapper;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.entity.Artcl;
import cn.cofco.cpmp.entity.Atch;
import cn.cofco.cpmp.entity.ComParm;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IArtclService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;
import cn.cofco.cpmp.utils.checkers.ArtclChecker;
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
public class ArtclServiceImpl implements IArtclService {

    private static Logger LOGGER = LoggerManager.getPortalMngLog();

    @Resource
    private ArtclMapper artclMapper;

    @Resource
    private AtchMapper atchMapper;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto queryByConds(IoArtclQureyDto dto, Integer pageNo, Integer pageSize) throws Exception {

        LOGGER.info("根据条件分页查询文章信息");

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
        map.put("effFlg", Constants.EFF_FLG_ON);
        map.put("desc", Constants.DESC_FLG_ON);

        List<Artcl> list = artclMapper.selectByMap(map);

        Integer count = artclMapper.countByMap(map);

        PagedResult result = new PagedResult(list, count);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, result);

    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public OutputDto view(Long artclId) throws Exception {

        LOGGER.info("根据文章ID查询文章信息");

        // 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        if (artclId == null || artclId == 0L) {
            String errMsg = "参数校验 - 不通过 - artclId不得为空";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        Artcl entity = artclMapper.selectByPrimaryKey(artclId);
        if (entity == null || Constants.EFF_FLG_OFF.equals(entity.getEffFlg())) {
            String errMsg = "根据文章ID查询文章信息 - 根据文章ID查询文章信息无数据 - artclId[" + artclId + "]";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }

        ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_ARTCL);
        if (comParm == null) {
            String errMsg = "根据文章ID查询文章信息 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_ARTCL + "]";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        String atchPrefix = comParm.getParmVal();
        String refId = atchPrefix + artclId;
        Atch atch = new Atch();
        atch.setRefId(refId);

        Map map = PageUtils.getQueryCondsMap(atch, 0, 0);

        List<Atch> atches = atchMapper.selectByMap(map);

        ArtclDtlIoDto artclDtlIoDto = new ArtclDtlIoDto();

        BeanUtils.copyProperties(artclDtlIoDto, entity);
        artclDtlIoDto.setAtches(atches);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC, artclDtlIoDto);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public OutputDto add(IoArtclDtlDto dto) throws Exception {

        LOGGER.info("新增文章");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        String chkRst = ArtclChecker.checkArgsForAdd(dto);
        if (!"".equals(chkRst)) {
            LOGGER.error("新增文章 - 参数校验不通过 - 参数信息：" + dto + " +++ 校验结果：" + chkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, chkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        // 2. 数据库操作
        long timeB = DateUtils.currentTimeMillis();
        LOGGER.info("2. 数据入库 - 开始");
        Artcl artcl = new Artcl();
        BeanUtils.copyProperties(artcl, dto);
        artcl.setArtclId(null);
        artcl.setClkCnt(0l);
        artcl.setCrtUsr(realNam);
        artcl.setCrtTim(DateUtils.getCurrentTimeStamp());
        artcl.setEffFlg(Constants.EFF_FLG_ON);

        int effRows = artclMapper.insert(artcl);
        if (effRows != 1) {
            String errMsg = "新增文章信息 - 失败 - 受影响行数不为1";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        List<IoAtchDto> atchDtos = dto.getAtchDtos();
        if (atchDtos != null && !atchDtos.isEmpty()) {
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_ARTCL);
            if (comParm == null) {
                String errMsg = "新增文章信息 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_ARTCL + "]";
                LOGGER.error(errMsg);
                throw new RuntimeException(errMsg);
            }

            String atchPrefix = comParm.getParmVal();
            String refId = atchPrefix + artcl.getArtclId();
            for (IoAtchDto atchDto : atchDtos) {
                Atch atch = new Atch();
                BeanUtils.copyProperties(atch, atchDto);
                atch.setRefId(refId);
                atch.setEffFlg(Constants.EFF_FLG_ON);
                atch.setCrtTim(DateUtils.getCurrentTimeStamp());
                atch.setCrtUsr(realNam);
                effRows = atchMapper.insert(atch);
                if (effRows != 1) {
                    String errMsg = "新增文章信息 - 新增附件 - 失败 - 受影响行数不为1";
                    LOGGER.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
            }
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        LOGGER.info("2. 数据入库 - 结束，duration[" + durationB + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto edit(IoArtclDtlDto dto) throws Exception {
        LOGGER.info("编辑文章");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        String chkRst = ArtclChecker.checkArgsForEdit(dto);
        if (!"".equals(chkRst)) {
            LOGGER.error("编辑文章 - 参数校验不通过 - 参数信息：" + dto + " +++ 校验结果：" + chkRst);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, chkRst);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        long timeB = DateUtils.currentTimeMillis();
        LOGGER.info("2. 业务校验 - 开始");
        Long artclId = dto.getArtclId();
        Artcl artcl = artclMapper.selectByPrimaryKey(artclId);
        if (artcl == null || Constants.EFF_FLG_OFF.equals(artcl.getEffFlg())) {
            String errMsg = "编辑文章 - 业务校验不通过 - 根据artclId查询文章信息无数据 - artclId: " + artclId;
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        LOGGER.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        // 2. 数据库操作
        long timeC = DateUtils.currentTimeMillis();
        LOGGER.info("3. 数据持久化 - 开始");
        BeanUtils.copyProperties(artcl, dto);
        artcl.setModUsr(realNam);
        artcl.setModTim(DateUtils.getCurrentTimeStamp());
        // 2.1 更新文章信息
        int effRows = artclMapper.updateByPrimaryKeySelective(artcl);
        if (effRows != 1) {
            String errMsg = "编辑文章 - 失败 - 受影响行数不为1";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 2.2 更新文章附件
        // 2.2.1 得到附件关联ID
        ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_ARTCL);
        if (comParm == null) {
            String errMsg = "编辑文章 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_ARTCL + "]";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        String atchPrefix = comParm.getParmVal();
        String refId = atchPrefix + artcl.getArtclId();

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
                    String errMsg = "编辑文章 - 新增附件 - 失败 - 受影响行数不为1";
                    LOGGER.error(errMsg);
                    throw new RuntimeException(errMsg);
                }
            }
        }
        long durationC = DateUtils.currentTimeMillis() - timeC;
        LOGGER.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto del(Long artclId) throws Exception {
        LOGGER.info("删除文章信息");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        if (artclId == null || artclId == 0L) {
            String errMsg = "删除文章信息 - 参数校验失败 - 文章ID为空";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");


        long timeB = DateUtils.currentTimeMillis();
        LOGGER.info("2. 业务校验 - 开始");
        Artcl artcl = artclMapper.selectByPrimaryKey(artclId);
        if (artcl == null || Constants.EFF_FLG_OFF.equals(artcl.getEffFlg())) {
            String errMsg = "删除文章信息 - 业务校验失败 - 根据artclId查询文章信息无数据 - artclId: " + artclId;
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        LOGGER.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        // 3. 数据库操作
        long timeC = DateUtils.currentTimeMillis();
        LOGGER.info("3. 数据持久化 - 开始");
        artcl.setEffFlg(Constants.EFF_FLG_OFF);
        artcl.setModUsr(realNam);
        artcl.setModTim(DateUtils.getCurrentTimeStamp());
        // 2.1 更新文章信息
        int effRows = artclMapper.updateByPrimaryKeySelective(artcl);
        if (effRows != 1) {
            String errMsg = "删除文章信息 - 失败 - 受影响行数不为1";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        // 2.2 更新文章附件
        // 2.2.1 得到附件关联ID
        ComParm comParm = ComParmHolder.getByParmTypAndParmCod(Constants.COM_PARM_TYP_ATCH_PFX, Constants.ATCH_PFX_ARTCL);
        if (comParm == null) {
            String errMsg = "删除文章信息 - 通用参数查询失败 - comParm[" + Constants.COM_PARM_TYP_ATCH_PFX + ", " + Constants.ATCH_PFX_ARTCL + "]";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        String atchPrefix = comParm.getParmVal();
        String refId = atchPrefix + artcl.getArtclId();
        // 2.2.2 删除附件信息
        atchMapper.deleteByRefId(refId);
        long durationC = DateUtils.currentTimeMillis() - timeC;
        LOGGER.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto pub(Long artclId) throws Exception {
        LOGGER.info("发布文章");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        if (artclId == null || artclId == 0L) {
            String errMsg = "发布文章 - 参数校验失败 - 文章ID为空";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        LOGGER.info("2. 业务校验 - 开始");
        Artcl artcl = artclMapper.selectByPrimaryKey(artclId);
        if (artcl == null || Constants.EFF_FLG_OFF.equals(artcl.getEffFlg())) {
            String errMsg = "发布文章 - 业务校验失败 - 根据artclId查询文章信息无数据 - artclId: " + artclId;
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (Constants.ARTCL_PUB_FLG_ON.equals(artcl.getPubFlg())) {
            String errMsg = "发布文章 - 业务校验失败 - 文章已发布";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        LOGGER.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        // 3. 数据库操作
        long timeC = DateUtils.currentTimeMillis();
        LOGGER.info("3. 数据持久化 - 开始");
        artcl.setPubFlg(Constants.ARTCL_PUB_FLG_ON);
        artcl.setModUsr(realNam);
        artcl.setModTim(DateUtils.getCurrentTimeStamp());
        // 2.1 更新文章信息
        int effRows = artclMapper.updateByPrimaryKeySelective(artcl);
        if (effRows != 1) {
            String errMsg = "发布文章 - 失败 - 受影响行数不为1";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        LOGGER.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public OutputDto cclPub(Long artclId) throws Exception {
        LOGGER.info("取消发布文章");

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        // 1. 参数校验
        long timeA = DateUtils.currentTimeMillis();
        LOGGER.info("1. 参数校验 - 开始");
        if (artclId == null || artclId == 0L) {
            String errMsg = "取消发布文章 - 参数校验失败 - 文章ID为空";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationA = DateUtils.currentTimeMillis() - timeA;
        LOGGER.info("1. 参数校验 - 结束，duration[" + durationA + "ms]");

        // 2. 业务校验
        long timeB = DateUtils.currentTimeMillis();
        LOGGER.info("2. 业务校验 - 开始");
        Artcl artcl = artclMapper.selectByPrimaryKey(artclId);
        if (artcl == null || Constants.EFF_FLG_OFF.equals(artcl.getEffFlg())) {
            String errMsg = "取消发布文章 - 业务校验失败 - 根据artclId查询文章信息无数据 - artclId: " + artclId;
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        if (Constants.ARTCL_PUB_FLG_OFF.equals(artcl.getPubFlg())) {
            String errMsg = "发布文章 - 业务校验失败 - 文章已取消发布";
            LOGGER.error(errMsg);
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, errMsg);
        }
        long durationB = DateUtils.currentTimeMillis() - timeB;
        LOGGER.info("2. 业务校验 - 结束，duration[" + durationB + "ms]");

        // 3. 数据库操作
        long timeC = DateUtils.currentTimeMillis();
        LOGGER.info("3. 数据持久化 - 开始");
        artcl.setPubFlg(Constants.ARTCL_PUB_FLG_OFF);
        artcl.setModUsr(realNam);
        artcl.setModTim(DateUtils.getCurrentTimeStamp());
        // 2.1 更新文章信息
        int effRows = artclMapper.updateByPrimaryKeySelective(artcl);
        if (effRows != 1) {
            String errMsg = "取消发布文章 - 失败 - 受影响行数不为1";
            LOGGER.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        long durationC = DateUtils.currentTimeMillis() - timeC;
        LOGGER.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }

}
