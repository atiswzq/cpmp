package cn.cofco.cpmp.utils.checkers;
import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.IoBidProjOffAppBidDto;
import cn.cofco.cpmp.utils.StringUtils;

import java.util.List;
/**
 * Created by Tao on 2017/5/31.
 *  for [线下招标项目管理 - 工厂采购员 - 校验器] in cpm
 */
public class BidProjOffMngForSplrChecker {
    public static String checkArgsForAppBid(IoBidProjOffAppBidDto dto) {
        StringBuilder sb = new StringBuilder("");

        Long projId = dto.getProjId();
        if (projId == null) {
            sb.append("项目ID[projId]不得为空;");
        }

        Long splrId = dto.getSplrId();
        if (splrId == null) {
            sb.append("供应商ID[splrId]不得为空;");
        }

        String ctct = dto.getCntct();
        if (StringUtils.isEmpty(ctct)) {
            sb.append("供应商联系人[ctct]不得为空;");
        }

        String ctctTel = dto.getCntctTel();
        if (StringUtils.isEmpty(ctctTel)) {
            sb.append("供应商联系人电话[ctctTel]不得为空;");
        }

        String splrNam = dto.getSplrNam();
        if (StringUtils.isEmpty(splrNam)) {
            sb.append("供应商名称[splrNam]不得为空;");
        }

        String dpstSts = dto.getDpstSts();
        if (StringUtils.isEmpty(dpstSts)) {
            sb.append("保证金状态[dpstSts]不得为空;");
        }

        if (Constants.DPST_STS_PAID.equals(dpstSts)) {
            String dpstPic = dto.getDpstPic();
            if (StringUtils.isEmpty(dpstPic)) {
                sb.append("保证金图片[dpstPic]不得为空;");
            }
        }
        String memo = dto.getMemo();
        dto.setMemo(StringUtils.getByLength(memo, 255));

        return sb.toString();
    }
}
