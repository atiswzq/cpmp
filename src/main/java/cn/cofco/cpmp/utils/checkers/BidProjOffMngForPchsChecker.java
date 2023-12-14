package cn.cofco.cpmp.utils.checkers;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.utils.DateUtils;
import cn.cofco.cpmp.utils.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
/**
 * Created by Tao on 2017/6/1.
 */
public class BidProjOffMngForPchsChecker {
    public static String checkArgsForSaveOrSub(IoBidProjOffDto dto) {
        StringBuilder sb = new StringBuilder("");

        String subFlg = dto.getSubFlg();
        if (StringUtils.isEmpty(subFlg)) {
            sb.append("提交标识不得为空;");
        }

        Long orgId = dto.getOrgId();
        if (orgId == null || orgId.equals(0L)) {
            sb.append("招标发起单位不得为空;");
        }

        String sutOrgIds = dto.getSutOrgIds();
        if (StringUtils.isEmpty(sutOrgIds)) {
            sb.append("招标适用单位不得为空;");
        }

        String projNam = dto.getProjNam();
        if (StringUtils.isEmpty(projNam)) {
            sb.append("项目名称不得为空;");
        }

        String matTyp = dto.getMatTyp();
        if (StringUtils.isEmpty(matTyp)) {
            sb.append("物料品类不得为空;");
        }

        String bidDpt = dto.getBidDpt();
        if (StringUtils.isEmpty(bidDpt)) {
            sb.append("招标单位不得为空;");
        }

        String bidDptAddr = dto.getBidDptAddr();
        if (StringUtils.isEmpty(bidDptAddr)) {
            sb.append("招标单位地址不得为空;");
        }

        BigDecimal bidDpst = dto.getBidDpst();
        if (bidDpst == null) {
            sb.append("投标保证金不得为空;");
        }

        String ctct = dto.getCtct();
        if (StringUtils.isEmpty(ctct)) {
            sb.append("联系人不得为空;");
        }

        String ctctTel = dto.getCtctTel();
        if (StringUtils.isEmpty(ctctTel)) {
            sb.append("联系人电话不得为空;");
        }

        String bidEndTimStr = dto.getBidEndTimStr();
        if (StringUtils.isEmpty(bidEndTimStr)) {
            sb.append("投标截止时间不得为空;");
        } else {
            try {
                Timestamp bidEndTim = DateUtils.getTimeStamp(bidEndTimStr);
                dto.setBidEndTim(bidEndTim);
            } catch (Exception e) {
                sb.append("投标截止时间格式不正确;");
            }
        }

        String bidOpenTimStr = dto.getBidOpenTimStr();
        if (StringUtils.isEmpty(bidOpenTimStr)) {
            sb.append("投标截止时间不得为空;");
        } else {
            try {
                Timestamp bidOpenTim = DateUtils.getTimeStamp(bidOpenTimStr);
                dto.setBidOpenTim(bidOpenTim);
            } catch (Exception e) {
                sb.append("投标截止时间格式不正确;");
            }
        }


        String bidRngTyp = dto.getBidRngTyp();
        if (StringUtils.isEmpty(bidRngTyp)) {
            sb.append("招标范围类型不得为空;");
        }

        if (Constants.BID_RNG_TYP_VECTORING.equals(bidRngTyp)) {
            String aptSplrIds = dto.getAptSplrIds();
            if (StringUtils.isEmpty(aptSplrIds)) {
                sb.append("定向邀标供应商IDs不得为空;");
            }
        }
        String projMemo = dto.getProjMemo();
        dto.setProjMemo(StringUtils.getByLength(projMemo, 255));

        return sb.toString();
    }




    public static String checkArgsForAdtBidInf(IoBidProjOffBidAdtDto dto) {
        StringBuilder sb = new StringBuilder("");

        Long id = dto.getId();
        if (id == null) {
            sb.append("ID不得为空;");
        }

        String bidDocSts = dto.getBidDocSts();
        if (StringUtils.isEmpty(bidDocSts)) {
            sb.append("标书状态不得为空;");
        }

        if (!Constants.BID_DOC_STS_ACCEPTED.equals(bidDocSts) && !Constants.BID_DOC_STS_REJECTED.equals(bidDocSts)) {
            sb.append("标书状态不为已接受[01]或已拒绝[02];");
        }

        String adtMemo = dto.getAdtMemo();
        dto.setAdtMemo(StringUtils.getByLength(adtMemo, 255));

        return sb.toString();
    }

    public static String checkArgsForAppAwd(IoBidProjOffAppAwdListDto dto) {

        StringBuilder sb = new StringBuilder("");

        Long projId = dto.getProjId();
        if (projId == null || projId == 0L) {
            sb.append("项目ID不得为空;");
        }

        List<IoBidProjOffAppAwdDto> ioBidProjOffAppAwdDtos = dto.getIoBidProjOffAppAwdDtos();
        if(ioBidProjOffAppAwdDtos == null || ioBidProjOffAppAwdDtos.isEmpty()) {
            sb.append("中标详情不得为空;");
        }

        for (IoBidProjOffAppAwdDto ioBidProjOffAppAwdDto : ioBidProjOffAppAwdDtos) {
            Long splrId = ioBidProjOffAppAwdDto.getSplrId();
            if (splrId == null || splrId == 0L) {
                sb.append("供应商ID不得为空;");
            }
            String matCod = ioBidProjOffAppAwdDto.getMatCod();
            if (StringUtils.isEmpty(matCod)){
                sb.append("物料编码不得为空;");
            }
            String matNam = ioBidProjOffAppAwdDto.getMatNam();
            if (StringUtils.isEmpty(matNam)){
                sb.append("物料名称不得为空;");
            }
            String pchsNum = ioBidProjOffAppAwdDto.getPchsNum();
            if (StringUtils.isEmpty(pchsNum)) {
                sb.append("采购数量不得为空;");
            }
            try {
                BigDecimal pchsNumDec = new BigDecimal(pchsNum);
            } catch (Exception e) {
                sb.append("采购数量异常[" + pchsNum + "];");
            }

            String matUnt = ioBidProjOffAppAwdDto.getMatUnt();
            if (StringUtils.isEmpty(matUnt)){
                sb.append("计量单位不得为空;");
            }
            BigDecimal awdAmt = ioBidProjOffAppAwdDto.getAwdAmt();
            if (awdAmt == null) {
                sb.append("中标金额不得为空;");
            }
        }

        return sb.toString();
    }

}

