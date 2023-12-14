package cn.cofco.cpmp.bpm;

import cn.cofco.cpmp.bpm.entity.BpmInputDto;
import cn.cofco.cpmp.bpm.entity.PiRequest;
import cn.cofco.cpmp.bpm.entity.PiResponse;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IBidProjOffMngForBpmService;
import cn.cofco.cpmp.service.IBidProjOnMngForBpmService;
import cn.cofco.cpmp.service.ISplrAplyForBpmService;
import cn.cofco.cpmp.service.IXjProjMngForBpmService;
import cn.cofco.cpmp.support.BpmOutputDtoUtil;
import cn.cofco.cpmp.utils.JaxbUtil;
import cn.cofco.cpmp.utils.checkers.BidBpmChecker;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

import javax.annotation.Resource;

public class BpmInterfaceImpl implements BpmInterface {

    private Logger logger = LoggerManager.getSplrSelfMngLog();

    @Resource
    private IBidProjOnMngForBpmService bidProjOnMngForBpmService;

    @Resource
    private IBidProjOffMngForBpmService bidProjOffMngForBpmService;

    @Resource
    private ISplrAplyForBpmService splrAplyForBpmService;

    @Resource
    private IXjProjMngForBpmService xjProjMngForBpmService;

    @Override
    public PiResponse BPM2GOS_FEEDBACK(PiRequest piRequest) {

        if (piRequest == null) {
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, "I_REQUEST为空");
        }
        try {
            String message = piRequest.getMessage();
            logger.info("message: " + message);
            BpmInputDto input = JaxbUtil.converyToJavaBean(message, BpmInputDto.class);

            // 0. 鉴权
            if (!Constants.UID_BPM.equals(input.getUid()) || !Constants.SIG_BPM.equals(input.getSignature())) {
                logger.error("BPM审核回调 - 鉴权失败");
                return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.UID_SGN_ERR);
            }

            // 1. 参数基础校验
            String chkRst = BidBpmChecker.checkArgsForBidCallBack(input);
            if (!"".equals(chkRst)) {
                String errMsg = "参数校验 - 不通过 - 参数信息：" + input + " +++ 校验结果：" + chkRst;
                logger.error(errMsg);
                return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, errMsg);
            }

            // 2. 根据模板号分流处理
            String tempNum = input.getTempNum();

            if (tempNum.contains(Constants.BPM_APP_TYP_GK_BUD)) {
                return bidProjOffMngForBpmService.bud(input.getBpmSeqNo(), input.getSucFlg(), input.getMemo());
            } else if (tempNum.contains(Constants.BPM_APP_TYP_GK_AWD)) {
                return bidProjOffMngForBpmService.awd(input.getBpmSeqNo(), input.getSucFlg(), input.getMemo());
            } else if (tempNum.contains(Constants.BPM_APP_TYP_GK_RPL)) {
                return bidProjOffMngForBpmService.rpl(input.getBpmSeqNo(), input.getSucFlg(), input.getMemo());
            } else if (tempNum.contains(Constants.BPM_APP_TYP_JJ_BUD)) {
                return bidProjOnMngForBpmService.bud(input.getBpmSeqNo(), input.getSucFlg(), input.getMemo());
            } else if (tempNum.contains(Constants.BPM_APP_TYP_JJ_AWD)) {
                return bidProjOnMngForBpmService.awd(input.getBpmSeqNo(), input.getSucFlg(), input.getMemo());
            } else if (tempNum.contains(Constants.BPM_APP_TYP_JJ_RPL)) {
                return bidProjOnMngForBpmService.rpl(input.getBpmSeqNo(), input.getSucFlg(), input.getMemo());
            } else if (tempNum.contains(Constants.BPM_APP_TYP_GK_APY)) {
                return splrAplyForBpmService.rpl(input.getBpmSeqNo(), input.getSucFlg(), input.getMemo());
            } else if (tempNum.contains(Constants.BPM_APP_TYP_XJ_AWD)) {
                return xjProjMngForBpmService.awd(input.getBpmSeqNo(), input.getSucFlg(), input.getMemo());
            } else if (tempNum.contains(Constants.BPM_APP_TYP_XJ_RPL)) {
                return xjProjMngForBpmService.rpl(input.getBpmSeqNo(), input.getSucFlg(), input.getMemo());
            } else {
                logger.error("BPM审核回调 - 失败 - 模板号[" + tempNum + "]不存在");
                return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.ARG_INVALID, "模板号[" + tempNum + "]不存在");
            }
        } catch (Exception e) {
            logger.error("BPM审核回调 - 异常 - Exception: " + ExceptionUtils.getStackTrace(e));
            return BpmOutputDtoUtil.setOutputDto(Constants.BPM_RTN_TYP_FAIL, RtnEnum.SYS_ERR, e.getMessage());
        }

    }
}
