package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.bpm.XmlConvertUtils;
import cn.cofco.cpmp.bpm.entity.*;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.SplrChkIoDto;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.holder.MatTypeHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ISplrAdtService;
import cn.cofco.cpmp.splr.dto.SplrAptInfo;
import cn.cofco.cpmp.splr.vo.SplrAdmtVo;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xsmiler on 2017/7/9.
 */
@Service
@Transactional("transactionManager")
public class SplrAdtServiceImpl implements ISplrAdtService{

    private Logger logger = LoggerManager.getSplrLog();

    @Resource
    private SplrMapper splrMapper;

    @Resource
    private SplrBnkAcntMapper splrBnkAcntMapper;

    @Resource
    private SplrAptMapper splrAptMapper;

    @Resource
    private SplrAdmtAplyMapper splrAdmtAplyMapper;

    @Resource
    private SplrDvlpAplyMapper splrDvlpAplyMapper;

    @Resource
    private SplrAdmtChkMapper splrAdmtChkMapper;

    @Resource
    private MaterielMapper materielMapper;

    @Resource
    private SplrMatMapper splrMatMapper;

    private String checkAuth() {
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        Integer userId = userinfo.getUserid();

        if (userId == null || userId == 0) {
            return "鉴权失败 - userId为空";
        }

        return "";
    }

    @Override
    public OutputDto splrAdmt(SplrAdmtVo splrAdmtVo) throws Exception {

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();
        // 校验参数
        if (null == splrAdmtVo || null == splrAdmtVo.getSplrId()) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
                    RtnEnum.ARG_INVALID, "未传参数或供应商id为空");
        }

        // 查询供应商信息
        Splr splr = splrMapper.selectByPrimaryKey(splrAdmtVo.getSplrId());

        Map map = PageUtils.getQueryCondsMap(null, 1, 1000);
        map.put("splrId", splrAdmtVo.getSplrId());

        // 查询供应商银行账号信息
        List<SplrBnkAcnt> splrBnkAcnts = splrBnkAcntMapper.selectByBnkConditions(map);
        List<Object> bpmSplrBnks = new ArrayList<>();
        for (SplrBnkAcnt splrBnkAcnt : splrBnkAcnts) {
            BpmSplrBnk bpmSplrBnk = copy2BpmSplrBnk(splrBnkAcnt);
            bpmSplrBnks.add(bpmSplrBnk);
        }

        // 查询供应商资质文件信息
        List<SplrAptInfo> splrAptInfos = splrAptMapper.splrAptList(splr
                .getSplrId(), splr.getPtnrTyp());
        List<Object> bpmSplrApts = new ArrayList<>();
        for (SplrAptInfo splrAptInfo : splrAptInfos) {
            BpmSplrApt bpmSplrApt = copy2BpmSplrApt(splrAptInfo);
            bpmSplrApts.add(bpmSplrApt);
        }

        // 删除以前供应商信息
        Map map1 = new HashMap();
        map1.put("splrId", splrAdmtVo.getSplrId());
        map1.put("delFlg", Constants.DEL_FLG_NODEL);
        map1.put("start", 1);
        map1.put("limit", 1000);
        List<SplrMat> splrMatList = splrMatMapper.selectByCondition(map1);
        for (SplrMat splrMat : splrMatList) {
            splrMat.setDelFlg(Constants.DEL_FLG_DEL);
            splrMatMapper.updateByPrimaryKeySelective(splrMat);
        }

        // 更新供应商供货物料
        String matIds = null;
        List<SplrMat> splrMats = new ArrayList<>();
        if (null != splrAdmtVo.getMatIds() && splrAdmtVo.getMatIds().size() > 0) {
            matIds = String.join(",", splrAdmtVo.getMatIds());
            for(String matId : splrAdmtVo.getMatIds()) {
                SplrMat splrMat = new SplrMat();
                splrMat.setSplrId(splr.getSplrId());
                Materiel materiel = materielMapper.selectByMatcod(matId);
                if (null == materiel) {
                    continue;
                }
                splrMat.setMatId(Long.valueOf(materiel.getMatId()));
                splrMat.setDelFlg(Constants.DEL_FLG_NODEL);

                splrMats.add(splrMat);
            }
        }
        if (splrMats.size() > 0) {
            splrMatMapper.inserts(splrMats);
        }

       /* String chkIds = null;
        if (null != splrAdmtVo.getChkIds() && splrAdmtVo.getChkIds().size() > 0) {
            chkIds = String.join(",", splrAdmtVo.getChkIds());
        }*/
       /*上传考察表附件*/
        splrAdmtChkMapper.deleteBySplrId(splrAdmtVo.getSplrId());
        List<SplrChkIoDto> splrChkIoDtos = splrAdmtVo.getSplrChkIoDtos();
        if (splrChkIoDtos != null && !splrChkIoDtos.isEmpty()) {
            for (SplrChkIoDto splrChkIoDto : splrChkIoDtos) {
                SplrAdmtChk splrAdmtChk = new SplrAdmtChk();
                BeanUtils.copyProperties(splrAdmtChk, splrChkIoDto);
                splrAdmtChk.setSplrId(splrAdmtVo.getSplrId());
                splrAdmtChk.setEffFlg(Constants.EFF_FLG_ON);
                splrAdmtChk.setCrtTim(DateUtils.getCurrentTimeStamp());
                splrAdmtChk.setCrtUsr(realNam);
                splrAdmtChkMapper.insert(splrAdmtChk);
            }
        }

        String seqNo = UUID.randomUUID().toString();

        // 写入注册申请信息
        SplrAdmtAply splrAdmtAply = new SplrAdmtAply();
        BeanUtils.copyProperties(splrAdmtAply, splr);
        splrAdmtAply.setAplyUsr(userinfo.getUsername());
        splrAdmtAply.setAplySts("01");
        splrAdmtAply.setAplyTim(DateUtils.date2String(splr.getRegTim()));
        splrAdmtAply.setAplyTim(DateUtils.date2String(new Date()));
//        splrAdmtAply.setBpmSeqNo(UUID.randomUUID().toString());

        splrAdmtAply.setProdList(splrAdmtVo.getProdList());
        splrAdmtAply.setMatIds(matIds);
        splrAdmtAply.setSeqNo(seqNo);
        // 设置状态为申请
        splrAdmtAply.setAplySts(Constants.SPLR_ADT_STS_APLY);
        splrAdmtAplyMapper.insertSelective(splrAdmtAply);

        // 修改供应商状态
        Splr splr1 = new Splr();
        splr1.setSplrId(splrAdmtVo.getSplrId());
        splr1.setSplrSts(Constants.SPLR_STS_ADMITTANCE);
        splr1.setSeqNo(seqNo);
        splr1.setProdList(splrAdmtVo.getProdList());
        splrMapper.updateByPrimaryKeySelective(splr1);

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
                RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
    }

    /**
     * 供应商开发申请
     * @param splrId
     * @return
     * @throws Exception
     */
    @Override
    public OutputDto splrAply(HttpServletRequest request, Long splrId) throws Exception {

        // 0. 权限校验
        if (!"".equals(checkAuth())) {
            return OutputDtoUtil.setOutputDto(false, RtnEnum.NO_OPRT_AUTH);
        }
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);

        if (userinfo.getCompany() == null) {
            logger.error("对应公司信息不存在");
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA);
        }
        String deptCode = userinfo.getCompany().getDept_code();

        if (userinfo.getCompany() == null || StringUtils.isEmpty(userinfo.getCompany().getDept_code())) {
            String errMsg = "供应商开发申请 - 失败 - 所属公司为空，无法提交BPM审批";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
        String bpmDeptCode = userinfo.getCompany().getDept_code();

        // 查询供应商信息
        Splr splr = splrMapper.selectByPrimaryKey(splrId);

        if (null == splr) {
            return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
                    RtnEnum.SYS_ERR, "供应商不存在！");
        }

        Map map = PageUtils.getQueryCondsMap(null, 1, 1000);
        map.put("splrId", splrId);

        // 查询供应商银行账号信息
        List<SplrBnkAcnt> splrBnkAcnts = splrBnkAcntMapper.selectByBnkConditions(map);
        List<Object> bpmSplrBnks = new ArrayList<>();
        for (SplrBnkAcnt splrBnkAcnt : splrBnkAcnts) {
            BpmSplrBnk bpmSplrBnk = copy2BpmSplrBnk(splrBnkAcnt);
            bpmSplrBnks.add(bpmSplrBnk);
        }

        String token = BpmFileUtils.getToken(userinfo.getUsername());
        String rootPath = request.getServletContext().getRealPath("/");
        String basePath = new File(rootPath + "..").getCanonicalPath();

        List<Long> fileIds = new ArrayList<>();

        // 查询供应商资质文件信息
        List<SplrAptInfo> splrAptInfos = splrAptMapper.splrAptList(splr
                .getSplrId(), splr.getClassification());
        List<Object> bpmSplrApts = new ArrayList<>();
        for (SplrAptInfo splrAptInfo : splrAptInfos) {
            BpmSplrApt bpmSplrApt = copy2BpmSplrApt(splrAptInfo);
            bpmSplrApts.add(bpmSplrApt);

            // 去掉文件类型路径，只保留文件名
            String aptName = null;
            if (null != splrAptInfo.getAptVal()) {
                String[] aptVal = splrAptInfo.getAptVal().split("/");
                if (aptVal.length == 2) {
                    aptName = aptVal[1];
                } else if(aptVal.length == 1) {
                    aptName = aptVal[0];
                }
            }

            if (null != bpmSplrApt.getAptUrl()) {
                Long fileId = BpmFileUtils.uploadAttachment(userinfo.getUsername(), token, basePath + File.separator + bpmSplrApt.getAptUrl(), aptName);
                fileIds.add(fileId);
            }
        }

        Map map1 = new HashMap();
        map1.put("seqNo", splr.getSeqNo());
        map1.put("start", 1);
        List<SplrAdmtAply> splrAdmtAplyList = splrAdmtAplyMapper.selectByCondition(map1);

        List<Object> bpmSplrMats = new ArrayList<>();
        List<Object> bpmSplrIspts = new ArrayList<>();
        if (null != splrAdmtAplyList && splrAdmtAplyList.size() > 0) {
            SplrAdmtAply splrAdmtAply = splrAdmtAplyList.get(0);
            // 生成物料信息

            if (null != splrAdmtAply && null != splrAdmtAply.getMatIds()) {
                String[] matIdList = splrAdmtAply.getMatIds().split(",");
                for(String matId : matIdList) {
                    Materiel materiel = materielMapper.selectByMatcod(matId);
                    BpmSplrMat bpmSplrMat = new BpmSplrMat();
                    bpmSplrMat.setMatCod(matId);
                    if (null != materiel) {
                        bpmSplrMat.setMatNam(materiel.getMatDesc());

                        if (null != materiel.getCoMatgroup() && materiel.getCoMatgroup().length() >= 4) {
                            String matTypeName = MatTypeHolder.getByMatkl2(materiel.getCoMatgroup().substring(0, 4)).getMatkl2name();
                            bpmSplrMat.setMatTyp(matTypeName);
                        }
                        bpmSplrMat.setMatUnt(materiel.getUnt());
                    }
                    bpmSplrMats.add(bpmSplrMat);
                }
            }

            /*// 生成考察表信息
            if (null != splrAdmtAply && null != splrAdmtAply.getChkIds()) {
                String[] chkIdList = splrAdmtAply.getChkIds().split(",");
                for(String chkId : chkIdList) {
                    BpmSplrIspt bpmSplrIspt = new BpmSplrIspt();
                    SplrAdmtChk splrAdmtChk = splrAdmtChkMapper.selectByPrimaryKey(Long.valueOf(chkId));
                    bpmSplrIspt.setIsptCod(String.valueOf(splrAdmtChk.getAdmtChkId()));
                    bpmSplrIspt.setIsptExpn(splrAdmtChk.getChkExpn());
                    bpmSplrIspt.setIsptUrl(splrAdmtChk.getChkFileNam());
                    // 去掉文件类型路径，只保留文件名
                    String fileName = null;
                    if (null != splrAdmtChk.getChkFileNam()) {
                        String[] isptUrl = splrAdmtChk.getChkFileNam().split("/");
                        if (isptUrl.length == 2) {
                            fileName = isptUrl[1];
                        } else if(isptUrl.length == 1) {
                            fileName = isptUrl[0];
                        }
                    }
                    bpmSplrIspt.setIsptNam(splrAdmtChk.getChkNam());
                    bpmSplrIspts.add(bpmSplrIspt);

                    if (null != bpmSplrIspt.getIsptUrl()) {
                        Long fileId = BpmFileUtils.uploadAttachment(userinfo.getUsername(), token, basePath + File.separator + bpmSplrIspt.getIsptUrl(), fileName);
                        fileIds.add(fileId);
                    }
                }
            }*/
        }

        // 生成bpm报文
        Map<String, List<Object>> objs = new ConcurrentHashMap<>();
        objs.put("formson_3183", bpmSplrApts);
        objs.put("formson_3184", bpmSplrBnks);
        // 添加物料信息
        objs.put("formson_3185", bpmSplrMats);
        objs.put("formson_3186", bpmSplrIspts);

        // 设置开发申请
        SplrDvlpAply splrDvlpAply = new SplrDvlpAply();
        BeanUtils.copyProperties(splrDvlpAply, splr);
        splrDvlpAply.setAplyUsr(userinfo.getUsername());
        splrDvlpAply.setAplySts("01");
        splrDvlpAply.setRegTim(splr.getRegTim());
        splrDvlpAply.setAplyTim(new Timestamp(System.currentTimeMillis()));
        splrDvlpAply.setBpmSeqNo(BpmUtils.getBpmSeqNo(Constants.PROJ_TYP_ZR));
        // 设置状态为申请
        splrDvlpAply.setAplySts(Constants.SPLR_ADT_STS_APLY);
        splrDvlpAply.setSeqNo(splr.getSeqNo());


        splrDvlpAplyMapper.insertSelective(splrDvlpAply);


        BpmSplr bpmSplr = new BpmSplr();
        BeanUtils.copyProperties(bpmSplr, splrDvlpAply);

        bpmSplr.setRegTim(DateUtils.timestamp2String(splr.getRegTim()));

        String fax = "";
        if (null != splr.getFaxEx()) {
            fax = fax.concat(splr.getFaxEx());
        }
        if (null != splr.getFaxEx() && null != splr.getFax()) {
            fax = fax.concat("-");
        }
        if (null != splr.getFax()) {
            fax = fax.concat(splr.getFax());
        }
        bpmSplr.setFax(fax);

        String telephone = "";
        if (null != splr.getTelEx()) {
            telephone = telephone.concat(splr.getTelEx());
        }
        if (null != splr.getTelEx() && null != splr.getTelephone()) {
            telephone = telephone.concat("-");
        }
        if (null != splr.getTelephone()) {
            telephone = telephone.concat(splr.getTelephone());
        }
        bpmSplr.setTelephone(telephone);

        bpmSplr.setTacCod(splrDvlpAply.getTaxCod());
        bpmSplr.setDbustLice(splrDvlpAply.getDbusiLice());
        // 设置申请人
        bpmSplr.setPrpr(userinfo.getUsername());
        // 设置客商类型
        if (null != bpmSplr.getPtnrTyp()) {
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod("PTNR_TYP" ,bpmSplr.getPtnrTyp());
            if (null != comParm) {
                bpmSplr.setPtnrTyp(comParm.getParmVal());
            }
        }
        // 设置三证合一
        if (null != bpmSplr.getHasCreditCode()) {
            if ("1".equals(bpmSplr.getHasCreditCode())) {
                bpmSplr.setHasCreditCode("是");
            } else {
                bpmSplr.setHasCreditCode("否");
            }
        }

        // 设置主要行业1
        if (null != bpmSplr.getIdst1()) {
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod("IDST_TYP" ,bpmSplr.getIdst1());
            if (null != comParm) {
                bpmSplr.setIdst1(comParm.getParmVal());
            }
        }

        // 设置主要行业2
        if (null != bpmSplr.getIdst2()) {
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod("IDST_TYP" ,bpmSplr.getIdst2());
            if (null != comParm) {
                bpmSplr.setIdst2(comParm.getParmVal());
            }
        }

        // 设置企业性质
        if (null != bpmSplr.getCpnNtr()) {
            ComParm comParm = ComParmHolder.getByParmTypAndParmCod("CPN_NTR" ,bpmSplr.getCpnNtr());
            if (null != comParm) {
                bpmSplr.setCpnNtr(comParm.getParmVal());
            }
        }

        String bpmBody = XmlConvertUtils.beanConvertXml(bpmSplr, objs);
        String templateCode = bpmDeptCode + Constants.BPM_APP_TYP_GK_APY_SUFFIX;
        boolean success = BpmUtils.subApp(bpmBody, templateCode, "供应商准入申请","", fileIds);

        if (success) {
            // 修改供应商状态
            Splr splr1 = new Splr();
            splr1.setSplrId(splrId);
            splr1.setSplrSts(Constants.SPLR_STS_APLY);
            splrMapper.updateByPrimaryKeySelective(splr1);

            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
                    RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
        }
        return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
                RtnEnum.SYS_ERR, "供应商BPM准入申请失败");
    }

    @Override
    public OutputDto adtTempForType(String type) throws Exception {

        Map<String, String> tempId = new ConcurrentHashMap<>();
        if ("aply".equalsIgnoreCase(type)) {
            tempId.put("tempId","1");
            return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
                    RtnEnum.SUC_OPR, tempId);
        }
        return OutputDtoUtil
                .setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, "不支持的考核申请类型");
    }

    private BpmSplrAdmt copy2BpmSplrAdmt(SplrAdmtAply splrAdmtAply) {
        BpmSplrAdmt bpmSplrAdmt = new BpmSplrAdmt();

        BeanUtils.copyProperties(bpmSplrAdmt, splrAdmtAply);
        bpmSplrAdmt.setPrpr(splrAdmtAply.getAplyUsr());

        return  bpmSplrAdmt;
    }

    private BpmSplrApt copy2BpmSplrApt(SplrAptInfo splrAptInfo) {
        BpmSplrApt bpmSplrApt = new BpmSplrApt();

        BeanUtils.copyProperties(bpmSplrApt, splrAptInfo);
        bpmSplrApt.setAptUrl(splrAptInfo.getAptVal());

        return bpmSplrApt;
    }

    private BpmSplrBnk copy2BpmSplrBnk(SplrBnkAcnt splrBnkAcnt) {
        BpmSplrBnk bpmSplrBnk = new BpmSplrBnk();

        BeanUtils.copyProperties(bpmSplrBnk, splrBnkAcnt);

        return  bpmSplrBnk;
    }
}
