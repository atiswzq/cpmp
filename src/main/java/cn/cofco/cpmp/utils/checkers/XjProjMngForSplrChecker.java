package cn.cofco.cpmp.utils.checkers;

import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.utils.DateUtils;
import cn.cofco.cpmp.utils.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Wzq on 2018/01/13.
 * for [询价项目管理 - 工厂采购员 - 校验器] in cpmp
 */
public class XjProjMngForSplrChecker {

    public static String checkArgsForAppBid(IoXjProjAppBidDto dto) {
        StringBuilder sb = new StringBuilder("");

        Long projId = dto.getProjId();
        if (projId == null) {
            sb.append("项目ID[projId]不得为空;");
        }

        Long splrId = dto.getSplrId();
        if (splrId == null) {
            sb.append("供应商ID[splrId]不得为空;");
        }

        String splrCtct = dto.getSplrCtct();
        if (StringUtils.isEmpty(splrCtct)) {
            sb.append("供应商联系人[splrCtct]不得为空;");
        }

        String splrCtctTel = dto.getSplrCtctTel();
        if (StringUtils.isEmpty(splrCtctTel)) {
            sb.append("供应商联系人电话[splrCtctTel]不得为空;");
        }

        String splrNam = dto.getSplrNam();
        if (StringUtils.isEmpty(splrNam)) {
            sb.append("供应商名称[splrNam]不得为空;");
        }

       /* String dpstSts = dto.getDpstSts();
        if (StringUtils.isEmpty(dpstSts)) {
            sb.append("保证金状态[dpstSts]不得为空;");
        }*/

       /* if (Constants.DPST_STS_PAID.equals(dpstSts)) {
            String dpstPic = dto.getDpstPic();
            if (StringUtils.isEmpty(dpstPic)) {
                sb.append("保证金图片[dpstPic]不得为空;");
            }
        }*/

        String memo = dto.getMemo();
        dto.setMemo(StringUtils.getByLength(memo, 255));

        return sb.toString();
    }

    public static String checkArgsForQot(IoXjProjQotDto dto) {
        StringBuilder sb = new StringBuilder("");

        Long bidId = dto.getBidId();
        if (bidId == null) {
            sb.append("供应商投标ID[bidId]不得为空;");
        }

        List<IoXjProjQotDtlDto> dtls = dto.getDtls();
        if (dtls == null || dtls.isEmpty()) {
            sb.append("报价明细[dtls]不得为空;");
        } else {
            for (IoXjProjQotDtlDto d : dtls) {
                if (StringUtils.isEmpty(d.getMatCod())) {
                    sb.append("物料编码[matCod]不得为空;");
                }
                if (StringUtils.isEmpty(d.getMatNam())) {
                    sb.append("物料名称[matNam]不得为空;");
                }
                if (StringUtils.isEmpty(d.getMatUnt())) {
                    sb.append("物料计量单位[matUnt]不得为空;");
                }
                if (d.getSplNum() == null) {
                    sb.append("供应数量[splNum]不得为空;");
                }
                if (d.getPrice() == null) {
                    sb.append("单价[price]不得为空;");
                }

                if (!StringUtils.isEmpty(d.getTendDlvDteStr())) {
                    String tendDlvDteStr = d.getTendDlvDteStr();
                    try {
                        Timestamp tendDlvDte = DateUtils.getTimeStamp(tendDlvDteStr);
                        d.setTendDlvDte(tendDlvDte);
                    } catch (Exception e) {
                        sb.append("交货日期格式不正确;");
                    }
                }
            }
        }

        String servPrms = dto.getServPrms();
        dto.setServPrms(StringUtils.getByLength(servPrms, 255));

        String qotMemo = dto.getQotMemo();
        dto.setQotMemo(StringUtils.getByLength(qotMemo, 255));

        return sb.toString();
    }

}
