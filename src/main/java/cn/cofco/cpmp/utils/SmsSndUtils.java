package cn.cofco.cpmp.utils;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.GrdKeySmsDto;
import cn.cofco.cpmp.dto.OpenKeySmsDto;
import cn.cofco.cpmp.entity.BidProjOn;
import cn.cofco.cpmp.entity.ExptInf;
import cn.cofco.cpmp.entity.XjProj;
import cn.cofco.cpmp.holder.SysParmHolder;
import org.json.JSONObject;



/**
 * Created by Xujy on 2017/5/28.
 * for [短信工具类] in cpmp
 */
public class SmsSndUtils {


    /**
     * 发送线上项目开标密钥
     * @param bidProjOn
     * @return
     */
    public static final boolean sendSmsForOpenBidProjOn(BidProjOn bidProjOn) {

        SysParmHolder.getByParmTypAndParmCod(Constants.SYS_PARM_TYP_SMS_TMPL, Constants.SYS_PARM_KEY_OPEN_BID);

        OpenKeySmsDto openKeySmsDto = new OpenKeySmsDto();
        openKeySmsDto.setPcode(bidProjOn.getProjNbr());
        openKeySmsDto.setVcode(bidProjOn.getOpenKey());
        JSONObject param = new JSONObject(openKeySmsDto);
        LastError lastError = new LastError();
        SendSmsTool.sendSms(SysParmHolder.getByParmTypAndParmCod(Constants.SYS_PARM_TYP_SMS_TMPL, Constants.SYS_PARM_KEY_OPEN_BID).getParmVal(),
                bidProjOn.getProjSupvTel(), param, lastError);
        return (!lastError.isError());
    }

    /**
     * 发送询价开标密钥
     * @param xjProj
     * @return
     */
    public static final boolean sendSmsForOpenXjProj(XjProj xjProj) {

        SysParmHolder.getByParmTypAndParmCod(Constants.SYS_PARM_TYP_SMS_TMPL, Constants.SYS_PARM_KEY_OPEN_BID);

        OpenKeySmsDto openKeySmsDto = new OpenKeySmsDto();
        openKeySmsDto.setPcode(xjProj.getProjNbr());
        openKeySmsDto.setVcode(xjProj.getOpenKey());
        JSONObject param = new JSONObject(openKeySmsDto);
        LastError lastError = new LastError();
        SendSmsTool.sendSms(SysParmHolder.getByParmTypAndParmCod(Constants.SYS_PARM_TYP_SMS_TMPL, Constants.SYS_PARM_KEY_OPEN_BID).getParmVal(),
                xjProj.getCtctTel(), param, lastError);
        return (!lastError.isError());
    }


    /**
     * 发送评标密钥
     * @param cod
     * @param exptInf
     * @param bidProjOn
     * @return
     */
    public static final boolean sendSmsForGrdBidProjOn(String cod, ExptInf exptInf, BidProjOn bidProjOn) {
        SysParmHolder.getByParmTypAndParmCod(Constants.SYS_PARM_TYP_SMS_TMPL, Constants.SYS_PARM_KEY_GRD_BID);

        GrdKeySmsDto grdKeySmsDto = new GrdKeySmsDto();
        grdKeySmsDto.setName(exptInf.getExptNam());
        grdKeySmsDto.setPcode(bidProjOn.getProjNbr());
        grdKeySmsDto.setVcode(cod);
        JSONObject param = new JSONObject(grdKeySmsDto);
        LastError lastError = new LastError();
        SendSmsTool.sendSms(SysParmHolder.getByParmTypAndParmCod(Constants.SYS_PARM_TYP_SMS_TMPL, Constants.SYS_PARM_KEY_GRD_BID).getParmVal(),
                exptInf.getMobNbr(), param, lastError);
        return lastError.isError();
    }

//    public static void main(String[] args) {
//
////        GrdKeySmsDto grdKeySmsDto = new GrdKeySmsDto();
////        grdKeySmsDto.setName("徐家园");
////        grdKeySmsDto.setPcode("1291-JJ-17090004");
////        grdKeySmsDto.setVcode("123456");
////        JSONObject param = new JSONObject(grdKeySmsDto);
////        LastError lastError = new LastError();
////        SendSmsTool.sendSms("SMS_93235017",
////                "15068822726", param, lastError);
////        System.out.println(lastError);
//
//
//
//        OpenKeySmsDto openKeySmsDto = new OpenKeySmsDto();
//        openKeySmsDto.setPcode("1291-JJ-17090004");
//        openKeySmsDto.setVcode("123456");
//        JSONObject param = new JSONObject(openKeySmsDto);
//        LastError lastError = new LastError();
//        SendSmsTool.sendSms("SMS_93235017",
//                "15068822726", param, lastError);
//        System.out.println(lastError);
//    }

}
