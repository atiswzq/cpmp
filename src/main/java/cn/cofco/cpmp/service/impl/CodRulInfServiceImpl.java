package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.CodRulInfMapper;
import cn.cofco.cpmp.entity.CodRulInf;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.ICodRulInfService;
import cn.cofco.cpmp.utils.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Xujy on 2017/7/2.
 * for [编码规则信息服务实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class CodRulInfServiceImpl implements ICodRulInfService {

    private static Logger logger = LoggerManager.getBusiLog();

    @Resource
    private CodRulInfMapper codRulInfMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
    public String getProjNbr(String rulTyp, String orgCod) throws Exception {

        CurrentUserInfo userInfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userInfo.getRealname();

        if (StringUtils.isEmpty(orgCod) || StringUtils.isEmpty(rulTyp)) {
            String errMsg = "获取项目编号时组织短码、规则类型不得为空";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }

        CodRulInf entity = new CodRulInf();
        entity.setOrgCod(orgCod);
        entity.setRulTyp(rulTyp);
        Integer year = DateUtils.getCurrentYear();
        Integer month = DateUtils.getCurrentMonth();
        entity.setYear("" + year);
        entity.setMon("" + month);
        entity.setEffFlg(Constants.EFF_FLG_ON);
        Map map = PageUtils.getQueryCondsMap(entity, 1, 1);
        map.put("desc", 1);

        List<CodRulInf> codRulInfs = codRulInfMapper.selectByMap(map);
        Long seq = 1L;
        if (codRulInfs != null && codRulInfs.size() > 0) {
            CodRulInf codRulInf = codRulInfs.get(0);
            seq = codRulInf.getSeq() + 1;
        }

        entity.setSeq(seq);
        entity.setCrtTim(DateUtils.getCurrentTimeStamp());
        entity.setCrtUsr(realNam);
        int effRows = codRulInfMapper.insert(entity);
        if (effRows != 1) {
            String errMsg = "新增编码规则信息失败 - 受影响行数不为1";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }


        String seqStr =  NumberUtils.getFmtNbr(seq, 4);
        Date date = DateUtils.getDate();
        return orgCod + "-" + rulTyp + "-" + DateUtils.date2String(date, "yyMM") + seqStr;

    }
}
