package cn.cofco.cpmp.factory;

import cn.cofco.cpmp.bpm.XmlConvertUtils;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.SplrMapper;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.utils.ContextTools;
import cn.cofco.cpmp.utils.CurrentUserInfo;
import cn.cofco.cpmp.utils.DateUtils;
import cn.cofco.cpmp.utils.DecimalUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Wzq on 2018/01/13.
 * for [询价项目BPM审批表单生成工厂] in cpmp
 */
@Component
public class XjProjBpmAppFactory {

    @Resource
    private SplrMapper splrMapper;

    /*
    * 询价项目BPM申请废标
    * */
    public String getRplBpmBody(String bpmSeqNo, XjProj xjProj, String appRplMemo) throws Exception {

        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        XjProjRplMainIoDto rplMainIoDto = new XjProjRplMainIoDto();
        rplMainIoDto.setBpmSeqNo(bpmSeqNo);
        rplMainIoDto.setProjNbr(xjProj.getProjNbr());
        rplMainIoDto.setProjNam(xjProj.getProjNam());
        rplMainIoDto.setBidDptNam(xjProj.getBidDptNam());
        rplMainIoDto.setBidDptAddr(xjProj.getBidDptAddr());
        rplMainIoDto.setCtct(xjProj.getCtct());
        rplMainIoDto.setCtctTel(xjProj.getCtctTel());
        rplMainIoDto.setAppRplMemo(appRplMemo);
        rplMainIoDto.setCreateusername(realNam);
        rplMainIoDto.setCreatedate(DateUtils.date2String(DateUtils.getCurrentTimeStamp()));

        // 生成bpm报文
        Map<String, List<Object>> objs = new ConcurrentHashMap<>();
        return XmlConvertUtils.beanConvertXml(rplMainIoDto, objs);
    }

    /*
    * 询价定标申请BPM
    * */
    public String getAwdBpmBody(String bpmSeqNo, XjProj xjProj, List<IoXjAppAwdDtlDto> appAwdDtlDtos, Map<String, XjProjMatDtl> matDtlMap, String appAwdMemo) throws Exception {
        CurrentUserInfo userinfo = ContextTools.appContext.getBean(CurrentUserInfo.class);
        String realNam = userinfo.getRealname();

        XjProjAwdMainIoDto awdMainIoDto = new XjProjAwdMainIoDto();
        awdMainIoDto.setBpmSeqNo(bpmSeqNo);
        awdMainIoDto.setProjNbr(xjProj.getProjNbr());
        awdMainIoDto.setProjNam(xjProj.getProjNam());
        awdMainIoDto.setAppAwdMemo(appAwdMemo);
        awdMainIoDto.setCreateusername(realNam);
       // awdMainIoDto.setGrdRul(Constants.getGrdRulDesc(bidProjOn.getGrdRul()));
        awdMainIoDto.setProjMemo(xjProj.getProjMemo());
        awdMainIoDto.setCreatedate(DateUtils.date2String(DateUtils.getCurrentTimeStamp()));
       // awdMainIoDto.setTempNum(Constants.BPM_APP_TYP_XJ_AWD);
        List<Object> awdDtlIoDtos = new ArrayList<>();
        Set<String> currTypSet = new HashSet<>();
        for (IoXjAppAwdDtlDto appAwdDtlDto : appAwdDtlDtos) {
            if (appAwdDtlDto.getCurrTyp() != null) {
                currTypSet.add(appAwdDtlDto.getCurrTyp());
            }
        }
        StringBuilder ttlPriDesc = new StringBuilder("");
        for (String currTyp : currTypSet) {
            BigDecimal ttlPriByCurrTyp = new BigDecimal("0");
            for (IoXjAppAwdDtlDto IoAppAwdDtlDto : appAwdDtlDtos) {
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
       // awdMainIoDto.setGrdExpt(grdExpt);
        for (IoXjAppAwdDtlDto appAwdDto : appAwdDtlDtos) {
            XjProjAwdDtlIoDto awdDtlIoDto = new XjProjAwdDtlIoDto();
            awdDtlIoDto.setMatCod(appAwdDto.getMatCod());
            XjProjMatDtl matInf = matDtlMap.get(String.valueOf(appAwdDto.getMatId()));
            awdDtlIoDto.setMatNam(matInf.getMatNam());
            awdDtlIoDto.setMatUnt(matInf.getMatUnt());
            awdDtlIoDto.setDlvAdr(matInf.getDlvAdr());

            Long splrId = appAwdDto.getSplrId();
            Splr splr = splrMapper.selectByPrimaryKey(splrId);
            if (splr != null) {
                awdDtlIoDto.setFullNam(splr.getFullNam());
                //awdDtlIoDto.setPtnrCod(splr.getPtnrCod());
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
}
