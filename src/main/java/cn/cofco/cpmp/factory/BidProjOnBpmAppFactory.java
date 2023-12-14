package cn.cofco.cpmp.factory;

import cn.cofco.cpmp.bpm.XmlConvertUtils;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.BidProjOnWinDtlMapper;
import cn.cofco.cpmp.dao.SplrMapper;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.entity.BidProjOn;
import cn.cofco.cpmp.entity.BidProjOnMatDtl;
import cn.cofco.cpmp.entity.BidProjOnWinDtl;
import cn.cofco.cpmp.entity.Splr;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.utils.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Xujy on 2017/7/16.
 * for [网上竞价项目BPM审批表单生成工厂] in cpmp
 */
@Component
public class BidProjOnBpmAppFactory {

    @Resource
    private SplrMapper splrMapper;

    @Resource
    private MatFactory matFactory;

    private static Logger logger = LoggerManager.getBidOnlineMngLog();
    @Resource
    private BidProjOnWinDtlMapper bidProjOnWinDtlMapper;

    public String getBudBpmBody(String bpmSeqNo, BidProjOn bidProjOn, List<BidProjOnMatDtl> matDtls, String splrIdsStr) throws Exception {

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        BidProjOnBudMainIoDto mainDto = new BidProjOnBudMainIoDto();
        mainDto.setBpmSeqNo(bpmSeqNo);
        mainDto.setProjNbr(bidProjOn.getProjNbr());
        mainDto.setProjNam(bidProjOn.getProjNam());
        mainDto.setMatTyp(matFactory.getMatTypDesc(bidProjOn.getMatTyp()));
        mainDto.setBidDptNam(bidProjOn.getBidDptNam());
        mainDto.setBidDptAddr(bidProjOn.getBidDptAddr());
        mainDto.setBidDpst(bidProjOn.getBidDpst().toString());
        mainDto.setCtct(bidProjOn.getCtct());
        mainDto.setCtctTel(bidProjOn.getCtctTel());
        mainDto.setProjSupv(bidProjOn.getProjSupv());
        mainDto.setProjSupvTel(bidProjOn.getProjSupvTel());
        mainDto.setBidEndTim(DateUtils.date2String(bidProjOn.getBidEndTim()));
        mainDto.setBidOpenTim(DateUtils.date2String(bidProjOn.getBidOpenTim()));
        mainDto.setBidRngTyp(bidProjOn.getBidRngTyp());
        mainDto.setQotCntTyp(bidProjOn.getQotCntTyp());
        mainDto.setProjMemo(bidProjOn.getProjMemo());
        mainDto.setCurrTyp(bidProjOn.getCurrTyp());
        mainDto.setDpstFlg(bidProjOn.getDpstFlg());
        mainDto.setPubPriFlg(bidProjOn.getPubPriFlg());
        mainDto.setGrdRul(Constants.getGrdRulDesc(bidProjOn.getGrdRul()));
        mainDto.setCreateusername(realNam);
        mainDto.setTempNum(Constants.BPM_APP_TYP_JJ_BUD);
        mainDto.setStatus("");
        mainDto.setCreatedate(DateUtils.date2String(DateUtils.getCurrentTimeStamp()));
        List<Object> buildMatDtos = new ArrayList<>();
        for (BidProjOnMatDtl matDtl : matDtls) {
            BidProjOnBudMatIoDto buildMatDto = new BidProjOnBudMatIoDto();
            buildMatDto.setMatCod(matDtl.getMatCod());
            buildMatDto.setMatNam(matDtl.getMatNam());
            buildMatDto.setMatUnt(matDtl.getMatUnt());
            buildMatDto.setMemo(matDtl.getMemo());
            buildMatDto.setPchsNum(matDtl.getPchsNum());
            buildMatDto.setTechServ(matDtl.getTechServ());
            buildMatDto.setDlvAdr(matDtl.getDlvAdr());
            buildMatDto.setDlvDte(DateUtils.date2SimpleString(matDtl.getDlvDte()));
            buildMatDtos.add(buildMatDto);
        }

        List<Object> buildSplrDtos = new ArrayList<>();
        if (Constants.BID_RNG_TYP_VECTORING.equals(bidProjOn.getBidRngTyp())) {
            String[] splrIds = splrIdsStr.split(",");
            for (String splrIdStr : splrIds) {
                BidProjOnBudSplrIoDto buildSplrDto = new BidProjOnBudSplrIoDto();
                Long splrId = Long.valueOf(splrIdStr);
                Splr splr = splrMapper.selectByPrimaryKey(splrId);
                if (splr != null) {
                    buildSplrDto.setPtnrId(splr.getPtnrId());
                    buildSplrDto.setPtnrCod(splr.getPtnrCod());
                    buildSplrDto.setAcntGrup(splr.getAcntGrup());
                    buildSplrDto.setShortNam(splr.getShortNam());
                    buildSplrDto.setFullNam(splr.getFullNam());
                    buildSplrDtos.add(buildSplrDto);
                }
            }
        }else {
            BidProjOnBudSplrIoDto buildSplrDto = new BidProjOnBudSplrIoDto();
            buildSplrDto.setPtnrCod(Constants.UNVERCTORING_MSG);
            buildMatDtos.add(buildSplrDto);
        }

        // 生成bpm报文
        Map<String, List<Object>> objs = new ConcurrentHashMap<>();
        objs.put("formson_2972", buildSplrDtos);
        objs.put("formson_2973", buildMatDtos);

        return XmlConvertUtils.beanConvertXml(mainDto, objs);
    }


    public String getAwdBpmBody(String bpmSeqNo, BidProjOn bidProjOn, List<IoAppAwdDtlDto> appAwdDtlDtos, Map<String, BidProjOnMatDtl> matDtlMap, String appAwdMemo, String grdExpt) throws Exception {
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        BidProjOnAwdMainIoDto awdMainIoDto = new BidProjOnAwdMainIoDto();
        awdMainIoDto.setBpmSeqNo(bpmSeqNo);
        awdMainIoDto.setProjNbr(bidProjOn.getProjNbr());
        awdMainIoDto.setProjNam(bidProjOn.getProjNam());
        awdMainIoDto.setAppAwdMemo(appAwdMemo);
        awdMainIoDto.setCreateusername(realNam);
        awdMainIoDto.setGrdRul(Constants.getGrdRulDesc(bidProjOn.getGrdRul()));
        awdMainIoDto.setProjMemo(bidProjOn.getProjMemo());
        awdMainIoDto.setCreatedate(DateUtils.date2String(DateUtils.getCurrentTimeStamp()));
        List<Object> awdDtlIoDtos = new ArrayList<>();
        Set<String> currTypSet = new HashSet<>();
        for (IoAppAwdDtlDto appAwdDtlDto : appAwdDtlDtos) {
            if (appAwdDtlDto.getCurrTyp() != null) {
                currTypSet.add(appAwdDtlDto.getCurrTyp());
            }
        }
        StringBuilder ttlPriDesc = new StringBuilder("");
        for (String currTyp : currTypSet) {
            BigDecimal ttlPriByCurrTyp = new BigDecimal("0");
            for (IoAppAwdDtlDto IoAppAwdDtlDto : appAwdDtlDtos) {
                if (currTyp.equals(IoAppAwdDtlDto.getCurrTyp())) {
                    BigDecimal price = IoAppAwdDtlDto.getUntPri();
                    if (price == null) {
                        price = new BigDecimal("0");
                    }
                /*    BigDecimal exRat = new BigDecimal("1.0000");
                    if (appAwdDtlDto.getExRat() != null) {
                        try {
                            exRat = new BigDecimal(appAwdDtlDto.getExRat());
                        } catch (Exception e) {
                            logger.error("exRat转化异常 - ex: [{}]", e.getMessage());
                        }
                    }
                    ttlPriByCurrTyp = ttlPriByCurrTyp.add(price.multiply(exRat).multiply(new BigDecimal(appAwdDtlDto.getPchsNum())));*/
                    ttlPriByCurrTyp =ttlPriByCurrTyp.add(price.multiply(new BigDecimal(IoAppAwdDtlDto.getPchsNum())));
                }
            }
            ttlPriDesc.append(currTyp).append(":").append(DecimalUtils.decimal2String(ttlPriByCurrTyp.setScale(2, BigDecimal.ROUND_HALF_EVEN))).append("; ");
        }
        awdMainIoDto.setTtlPriceMsg(ttlPriDesc.toString());
        awdMainIoDto.setGrdExpt(grdExpt);
        for (IoAppAwdDtlDto appAwdDto : appAwdDtlDtos) {
            BidProjOnAwdDtlIoDto awdDtlIoDto = new BidProjOnAwdDtlIoDto();
            awdDtlIoDto.setMatCod(appAwdDto.getMatCod());
            BidProjOnMatDtl matInf = matDtlMap.get(String.valueOf(appAwdDto.getMatId()));
            awdDtlIoDto.setMatNam(matInf.getMatNam());
            awdDtlIoDto.setMatUnt(matInf.getMatUnt());
            awdDtlIoDto.setDlvAdr(matInf.getDlvAdr());

            Long splrId = appAwdDto.getSplrId();
            Splr splr = splrMapper.selectByPrimaryKey(splrId);
            if (splr != null) {
                awdDtlIoDto.setFullNam(splr.getFullNam());
                awdDtlIoDto.setPtnrCod(splr.getPtnrCod());
            }
            awdDtlIoDto.setPchsNum(appAwdDto.getPchsNum());
            awdDtlIoDto.setUntPri(appAwdDto.getUntPri().toString());
            awdDtlIoDto.setTtlPri(appAwdDto.getTtlPri().toString());
            awdDtlIoDto.setTendDlvDte(DateUtils.date2SimpleString(appAwdDto.getTendDlvDte()));
            awdDtlIoDto.setCurrTyp(appAwdDto.getCurrTyp());
            awdDtlIoDto.setMatBnd(appAwdDto.getMatBnd());
            awdDtlIoDto.setAwdMemo(appAwdDto.getMemo());
            awdDtlIoDtos.add(awdDtlIoDto);
        }
        // 生成bpm报文
        Map<String, List<Object>> objs = new ConcurrentHashMap<>();
        objs.put("formson_2975", awdDtlIoDtos);
        return XmlConvertUtils.beanConvertXml(awdMainIoDto, objs);
    }

    public String getRplBpmBody(String bpmSeqNo, BidProjOn bidProjOn, String appRplMemo) throws Exception {

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        BidProjOnRplMainIoDto rplMainIoDto = new BidProjOnRplMainIoDto();
        rplMainIoDto.setBpmSeqNo(bpmSeqNo);
        rplMainIoDto.setProjNbr(bidProjOn.getProjNbr());
        rplMainIoDto.setProjNam(bidProjOn.getProjNam());
        rplMainIoDto.setBidDptNam(bidProjOn.getBidDptNam());
        rplMainIoDto.setBidDptAddr(bidProjOn.getBidDptAddr());
        rplMainIoDto.setCtct(bidProjOn.getCtct());
        rplMainIoDto.setCtctTel(bidProjOn.getCtctTel());
        rplMainIoDto.setAppRplMemo(appRplMemo);
        rplMainIoDto.setCreateusername(realNam);
        rplMainIoDto.setCreatedate(DateUtils.date2String(DateUtils.getCurrentTimeStamp()));

        // 生成bpm报文
        Map<String, List<Object>> objs = new ConcurrentHashMap<>();
        return XmlConvertUtils.beanConvertXml(rplMainIoDto, objs);
    }
}
