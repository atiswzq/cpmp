package cn.cofco.cpmp.factory;

import cn.cofco.cpmp.bpm.XmlConvertUtils;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.SplrAcntMapper;
import cn.cofco.cpmp.dao.SplrMapper;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.entity.BidProjOff;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.utils.ContextTools;
import cn.cofco.cpmp.utils.CurrentUserInfo;
import cn.cofco.cpmp.utils.DateUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Xujy on 2017/7/16.
 * for [线下公开招标项目BPM审批表单生成工厂] in cpmp
 */
@Component
public class BidProjOffBpmAppFactory {

    @Resource
    private SplrMapper splrMapper;

    @Resource
    private MatFactory matFactory;

    @Resource
    private SplrAcntMapper splrAcntMapper;

    private static Logger logger = LoggerManager.getBidOfflineMngLog();

    public String getBudBpmBody(String bpmSeqNo, BidProjOff bidProjOff, String splrIdsStr, List<IoAtchDto> atchDtos) throws Exception {

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        BidProjOffBudMainIoDto mainDto = new BidProjOffBudMainIoDto();
        mainDto.setBpmSeqNo(bpmSeqNo);
        mainDto.setProjNbr(bidProjOff.getProjNbr());
        mainDto.setProjNam(bidProjOff.getProjNam());
        mainDto.setMatTyp(matFactory.getMatTypDesc(bidProjOff.getMatTyp()));
        mainDto.setBidDpt(bidProjOff.getBidDpt());
        mainDto.setBidDptAddr(bidProjOff.getBidDptAddr());
        mainDto.setBidDpst(bidProjOff.getBidDpst().toString());
        mainDto.setCtct(bidProjOff.getCtct());
        mainDto.setCtctTel(bidProjOff.getCtctTel());
        mainDto.setBidEndTim(DateUtils.date2String(bidProjOff.getBidEndTim()));
        mainDto.setBidOpenTim(DateUtils.date2String(bidProjOff.getBidOpenTim()));
        mainDto.setBidRngTyp(bidProjOff.getBidRngTyp());
        mainDto.setProjMemo(bidProjOff.getProjMemo());
        mainDto.setCreateusername(realNam);
        mainDto.setCreatedate(DateUtils.date2String(DateUtils.getCurrentTimeStamp()));
        mainDto.setCurrTyp(bidProjOff.getCurrTyp());
        mainDto.setDpstFlg(bidProjOff.getDpstFlg());
        mainDto.setPubPriFlg(bidProjOff.getPubPriFlg());
        mainDto.setMatReqDept(bidProjOff.getMatReqDept());
        mainDto.setBidTeamMmb(bidProjOff.getBidTeamMmb());

        List<Object> buildSplrDtos = new ArrayList<>();
        if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOff.getBidRngTyp()) && splrIdsStr != null) {
            String[] splrIds = splrIdsStr.split(",");
            for (String splrIdStr : splrIds) {
                BidProjOffBudSplrIoDto buildSplrDto = new BidProjOffBudSplrIoDto();
                Long splrId = Long.valueOf(splrIdStr);
                Splr splr = splrMapper.selectByPrimaryKey(splrId);
                if (splr != null) {
                    buildSplrDto.setPtnrId(splr.getPtnrId());
                    buildSplrDto.setPtnrCod(splr.getPtnrCod());
                    buildSplrDto.setAcntGrup(splr.getAcntGrup());
                    buildSplrDto.setShortNam(splr.getShortNam());
                    buildSplrDto.setFullNam(splr.getFullNam());
                    buildSplrDto.setSplrCtct(splr.getContact());
                    buildSplrDto.setSplrCtctTel(splr.getMob());
                    buildSplrDtos.add(buildSplrDto);
                }
            }
        }

        // 生成bpm报文
        Map<String, List<Object>> objs = new ConcurrentHashMap<>();
        objs.put("formson_2967", buildSplrDtos);

        return XmlConvertUtils.beanConvertXml(mainDto, objs);
    }


    public String getAwdBpmBody(String bpmSeqNo, BidProjOff bidProjOff, IoBidProjOffAppAwdListDto appAwdDto) throws Exception {
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        BidProjOffAwdMainIoDto awdMainIoDto = new BidProjOffAwdMainIoDto();
        awdMainIoDto.setBpmSeqNo(bpmSeqNo);
        awdMainIoDto.setProjNbr(bidProjOff.getProjNbr());
        awdMainIoDto.setProjNam(bidProjOff.getProjNam());
        awdMainIoDto.setMatTyp(matFactory.getMatTypDesc(bidProjOff.getMatTyp()));
        awdMainIoDto.setBidOpenTim(DateUtils.date2String(bidProjOff.getBidOpenTim()));
        awdMainIoDto.setAwdMemo(appAwdDto.getAwdMemo());
        awdMainIoDto.setCreateusername(realNam);
        awdMainIoDto.setCreatedate(DateUtils.date2String(DateUtils.getCurrentTimeStamp()));
        awdMainIoDto.setMatReqBpmAppSeq(appAwdDto.getMatReqBpmAppSeq());
        awdMainIoDto.setCaseRept(appAwdDto.getCaseRept());
        awdMainIoDto.setDept(appAwdDto.getDept());
        awdMainIoDto.setMatReqDept(bidProjOff.getMatReqDept());
        awdMainIoDto.setMatPchsbgt(appAwdDto.getMatPchsBgt());
        awdMainIoDto.setBidTolAmt(appAwdDto.getBidTolAmt());
        awdMainIoDto.setBrfDesc(appAwdDto.getBrfDesc());
        List<Object> awdDtlDtos = new ArrayList<>();
        List<IoBidProjOffAppAwdDto> dtos = appAwdDto.getIoBidProjOffAppAwdDtos();
        for (IoBidProjOffAppAwdDto ioDtl : dtos) {
            BidProjOffAwdDtlIoDto awdDtlIoDto = new BidProjOffAwdDtlIoDto();
            Long splrId = ioDtl.getSplrId();
            Splr splr = splrMapper.selectByPrimaryKey(splrId);
            if (splr != null) {
                awdDtlIoDto.setPtnrCod(splr.getPtnrCod());
                awdDtlIoDto.setFullNam(splr.getFullNam());
                awdDtlIoDto.setMatNam(ioDtl.getMatNam());
                awdDtlIoDto.setAwdAmt("" + ioDtl.getAwdAmt());
                awdDtlIoDto.setMatUnt(ioDtl.getMatUnt());
                awdDtlIoDto.setPchsNum(ioDtl.getPchsNum());
                awdDtlDtos.add(awdDtlIoDto);
            }
        }

        // 生成bpm报文
        Map<String, List<Object>> objs = new ConcurrentHashMap<>();
        objs.put("formson_2969", awdDtlDtos);
        return XmlConvertUtils.beanConvertXml(awdMainIoDto, objs);
    }

    public String getRplBpmBody(String bpmSeqNo, BidProjOff bidProjOff, String rplMemo) throws Exception {
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        BidProjOffRplMainIoDto rplMainIoDto = new BidProjOffRplMainIoDto();
        rplMainIoDto.setBpmSeqNo(bpmSeqNo);
        rplMainIoDto.setProjNbr(bidProjOff.getProjNbr());
        rplMainIoDto.setProjNam(bidProjOff.getProjNam());
        rplMainIoDto.setBidOpenTim(DateUtils.date2String(bidProjOff.getBidOpenTim()));
        rplMainIoDto.setBidDpt(bidProjOff.getBidDpt());
        rplMainIoDto.setBidDptAddr(bidProjOff.getBidDptAddr());
        rplMainIoDto.setCtct(bidProjOff.getCtct());
        rplMainIoDto.setCtctTel(bidProjOff.getCtctTel());
        rplMainIoDto.setRplMemo(rplMemo);
        rplMainIoDto.setCreateusername(realNam);
        rplMainIoDto.setCreatedate(DateUtils.date2String(DateUtils.getCurrentTimeStamp()));

        // 生成bpm报文
        Map<String, List<Object>> objs = new ConcurrentHashMap<>();
        return XmlConvertUtils.beanConvertXml(rplMainIoDto, objs);
    }
}
