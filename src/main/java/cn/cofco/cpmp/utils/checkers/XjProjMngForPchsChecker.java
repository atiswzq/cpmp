package cn.cofco.cpmp.utils.checkers;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.*;
import cn.cofco.cpmp.utils.DateUtils;
import cn.cofco.cpmp.utils.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Wzq on 2018/01/13.
 * for [询价项目管理 - 工厂采购员 - 校验器] in cpmp
 */
public class XjProjMngForPchsChecker {
    public static String checkArgsForSave(IoXjProjDto dto) {
        StringBuilder sb = new StringBuilder("");

     /*   String subFlg = dto.getSubFlg();
        if (StringUtils.isEmpty(subFlg)) {
            sb.append("提交标识不得为空;");
        }*/

        Long orgId = dto.getOrgId();
        if (orgId == null || orgId.equals(0L)) {
            sb.append("招标发起单位不得为空;");
        }

        /*String sutOrgIds = dto.getSutOrgIds();
        if (StringUtils.isEmpty(sutOrgIds)) {
            sb.append("招标适用单位不得为空;");
        }*/

        String projNam = dto.getProjNam();
        if (StringUtils.isEmpty(projNam)) {
            sb.append("项目名称不得为空;");
        }

        String matTyp = dto.getMatTyp();
        if (StringUtils.isEmpty(matTyp)) {
            sb.append("物料品类不得为空;");
        }

        String bidDptNam = dto.getBidDptNam();
        if (StringUtils.isEmpty(bidDptNam)) {
            sb.append("招标单位不得为空;");
        }

        String bidDptAddr = dto.getBidDptAddr();
        if (StringUtils.isEmpty(bidDptAddr)) {
            sb.append("招标单位地址不得为空;");
        }

     /*   BigDecimal bidDpst = dto.getBidDpst();
        if (bidDpst == null) {
            sb.append("投标保证金不得为空;");
        }*/

        String ctct = dto.getCtct();
        if (StringUtils.isEmpty(ctct)) {
            sb.append("项目联系人不得为空;");
        }
        String ctctTel = dto.getCtctTel();
        if (StringUtils.isEmpty(ctctTel)) {
            sb.append("联系人电话不得为空;");
        }
       /* String projSupv = dto.getProjSupv();
        if (StringUtils.isEmpty(projSupv)) {
            sb.append("监标人不得为空;");
        }
        String projSupvTel = dto.getProjSupvTel();
        if (StringUtils.isEmpty(projSupvTel)) {
            sb.append("监标人电话不得为空;");
        }*/

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
            sb.append("开标时间不得为空;");
        } else {
            try {
                Timestamp bidOpenTim = DateUtils.getTimeStamp(bidOpenTimStr);
                dto.setBidOpenTim(bidOpenTim);
            } catch (Exception e) {
                sb.append("开标时间格式不正确;");
            }
        }

        String bidRngTyp = dto.getBidRngTyp();
        if (StringUtils.isEmpty(bidRngTyp)) {
            sb.append("招标范围类型不得为空;");
        }

        if (Constants.BID_RNG_TYP_VECTORING.equals(bidRngTyp)) {
            String splrIds = dto.getSplrIds();
            if (StringUtils.isEmpty(splrIds)) {
                sb.append("定向邀标供应商IDs不得为空;");
            }
        }

        String qotCntTyp = dto.getQotCntTyp();
        if (StringUtils.isEmpty(qotCntTyp)) {
            sb.append("报价次数类型不得为空;");
        }

        List<IoXjProjMatInfDto> matList = dto.getMatList();
        if (matList == null || matList.isEmpty()) {
            sb.append("物料信息不得为空;");
        } else {
            int i = 1;
            for (IoXjProjMatInfDto matInfDto : matList) {
                String matCod = matInfDto.getMatCod();
                if (StringUtils.isEmpty(matCod)) {
                    sb.append("[").append(i).append("]物料编码不得为空;");
                }

                String matNam = matInfDto.getMatNam();
                if (StringUtils.isEmpty(matNam)) {
                    sb.append("[").append(i).append("]物料名称不得为空;");
                }

                String pchsNum = matInfDto.getPchsNum();
                if (StringUtils.isEmpty(pchsNum)) {
                    sb.append("[").append(i).append("]采购数量不得为空不合法;");
                }

                try {
                    BigDecimal pchsNumDec = new BigDecimal(pchsNum);
                } catch (Exception e) {
                    sb.append("[").append(i).append("]采购数量不合法;");
                }

                String matUnt = matInfDto.getMatUnt();
                if (StringUtils.isEmpty(matUnt)) {
                    sb.append("[").append(i).append("]物料计量单位不得为空;");
                }

                String dlvAdr = matInfDto.getDlvAdr();
                if (StringUtils.isEmpty(dlvAdr)) {
                    sb.append("[").append(i).append("]交货地址不得为空;");
                }

                String techServ = matInfDto.getTechServ();
                matInfDto.setTechServ(StringUtils.getByLength(techServ, 255));

                String memo = matInfDto.getMemo();
                matInfDto.setMemo(StringUtils.getByLength(memo, 255));

                String dlvDteStr = matInfDto.getDlvDteStr();
                if (!StringUtils.isEmpty(dlvDteStr)) {
                    try {
                        Timestamp dlvDte = DateUtils.getTimeStamp(dlvDteStr);
                        matInfDto.setDlvDte(dlvDte);
                    } catch (Exception e) {
                        sb.append("[").append(i).append("]交货日期格式不正确;");
                    }
                }

                i++;
            }
        }

        String projMemo = dto.getProjMemo();
        dto.setProjMemo(StringUtils.getByLength(projMemo, 1000));

        return sb.toString();
    }

    public static String checkArgsForAdtBidInf(IoXjProjBidAdtDto dto) {
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

    public static String checkArgsForOpenProj(IoXjProjOpenDto dto) {

        StringBuilder sb = new StringBuilder("");

        Long projId = dto.getProjId();
        if (projId == null || projId == 0L) {
            sb.append("项目ID不得为空;");
        }

        String openKsy = dto.getOpenKey();
        if (StringUtils.isEmpty(openKsy)) {
            sb.append("开标密钥不得为空;");
        }


        return sb.toString();
    }

    public static String checkArgsForAppAwd(IoXjAppAwdDto dto) {

        StringBuilder sb = new StringBuilder("");

        Long projId = dto.getProjId();
        if (projId == null || projId == 0L) {
            sb.append("项目ID不得为空;");
        }

        List<IoXjAppAwdDtlDto> appAwdDtls = dto.getAppAwdDtls();

        if (appAwdDtls == null || appAwdDtls.isEmpty()) {
            sb.append("决标明细不得为空;");
        } else {
            int i = 0;
            for (IoXjAppAwdDtlDto dtl : appAwdDtls) {
                String matCod = dtl.getMatCod();
                if (StringUtils.isEmpty(matCod)) {
                    sb.append("明细[" + i + "]物料编码不得为空;");
                }
                Long splrId = dtl.getSplrId();
                if (splrId == null || splrId == 0L) {
                    sb.append("明细[" + i + "]供应商ID不得为空;");
                }
                String pchsNum = dtl.getPchsNum();
                if (StringUtils.isEmpty(pchsNum)) {
                    sb.append("明细[" + i + "]采购数量不得为空;");
                }

                try {
                    BigDecimal pchsNumDec = new BigDecimal(pchsNum);
                } catch (Exception e) {
                    sb.append("明细[" + i + "]采购数量异常[" + pchsNum + "];");
                }

                String tendDlvDteStr = dtl.getTendDlvDteStr();
                if (!StringUtils.isEmpty(tendDlvDteStr)) {
                    try {
                        Timestamp tendDlvDte = DateUtils.getTimeStamp(tendDlvDteStr);
                        dtl.setTendDlvDte(tendDlvDte);
                    } catch (Exception e) {
                        sb.append("明细[" + i + "]交货日期格式不正确;");
                    }
                }
                i++;
            }
        }

        String appAwdMemo = dto.getAppAwdMemo();
        dto.setAppAwdMemo(StringUtils.getByLength(appAwdMemo, 255));

        return sb.toString();

    }
}
