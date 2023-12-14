package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.bpm.entity.PiResponse;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.SplrDvlpAplyMapper;
import cn.cofco.cpmp.dao.SplrMapper;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.entity.SplrDvlpAply;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IMdmService;
import cn.cofco.cpmp.service.ISplrAplyForBpmService;
import cn.cofco.cpmp.support.BpmOutputDtoUtil;
import cn.cofco.cpmp.utils.checkers.SplrBpmChecker;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xsmiler on 2017/8/20.
 */
@Service
@Transactional("transactionManager")
public class SplrAplyForBpmServiceImpl implements ISplrAplyForBpmService{

    private Logger logger = LoggerManager.getSplrLog();

    @Resource
    private SplrDvlpAplyMapper splrDvlpAplyMapper;

    @Resource
    private SplrMapper splrMapper;

    @Resource
    private IMdmService iMdmService;

    @Override
    public PiResponse rpl(String bpmSeqNo, String sucFlg, String memo) {
		logger.info("BPM 供应商开发申请审核回调 bpmSeqNo:[{}] sucFlg[{}] memo[{}]", bpmSeqNo, sucFlg, memo);

		String chkStr = SplrBpmChecker.checkArgsForSplrCallBack(bpmSeqNo, sucFlg, memo);
        if (!"".equals(chkStr)) {
            String errMsg = "BPM 供应商开发申请审核回调 - 参数校验 - 失败 - " + chkStr;
            logger.error(errMsg);
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
        }

        try {
            Map<String, String> map = new ConcurrentHashMap<>();
            map.put("bpmSeqNo", bpmSeqNo);
            List<SplrDvlpAply> splrDvlpAplies = splrDvlpAplyMapper.selectByCondition(map);
            if (splrDvlpAplies.size() == 0) {
                return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, "bpmSeqNo不存在");
            }
            SplrDvlpAply splrDvlpAply = splrDvlpAplies.get(0);

            if (Constants.BPM_CAL_BAK_SUC.equals(sucFlg)) {
                logger.info("供应商开发申请成功，审核结果：[{}]", memo);
                Splr splr = new Splr();
                splr.setSplrId(splrDvlpAply.getSplrId());
                splr.setSplrSts(Constants.SPLR_STS_APLY_SUCCESS);
                splrMapper.updateByPrimaryKeySelective(splr);

                SplrDvlpAply splrDvlpAply1 = new SplrDvlpAply();
                splrDvlpAply1.setAplyId(splrDvlpAply.getAplyId());
                splrDvlpAply1.setAplySts(Constants.SPLR_ADT_STS_SUCCESS);
                splrDvlpAply1.setAdtMsg(memo.trim());
                splrDvlpAplyMapper.updateByPrimaryKeySelective(splrDvlpAply1);

//                boolean isExist = iMdmService.splrQuery(splrDvlpAply.getSplrId());
//                logger.info("准入申请成功");
//
//                if (!isExist) {
//                    try {
//                        iMdmService.splrAply(splrDvlpAply.getAplyId(), splrDvlpAply.getSplrId());
//                        logger.info("发起mdm申请...");
//                    } catch (Exception e) {
//                        logger.error("发起mdm申请失败");
//                    }
//                }
            } else {
                logger.info("供应商开发申请失败，失败原因：[{}]", memo);
                Splr splr = new Splr();
                splr.setSplrId(splrDvlpAply.getSplrId());
                splr.setSplrSts(Constants.SPLR_STS_APLY_FAIL);
                splrMapper.updateByPrimaryKeySelective(splr);

                SplrDvlpAply splrDvlpAply1 = new SplrDvlpAply();
                splrDvlpAply1.setAplyId(splrDvlpAply.getAplyId());
                splrDvlpAply1.setAplySts(Constants.SPLR_ADT_STS_FAIL);
                splrDvlpAply1.setAdtMsg(memo.trim());
                splrDvlpAplyMapper.updateByPrimaryKeySelective(splrDvlpAply1);
            }

            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_SUC, RtnEnum.SUC_OPR);
        } catch (Exception e) {
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.INNER_ERR, e.getMessage());
        }
    }
}
