package cn.cofco.cpmp.utils.checkers;

import cn.cofco.cpmp.dto.BidProjOnExptAppResDto;
import cn.cofco.cpmp.entity.BidProjOnExptGrdInf;
import cn.cofco.cpmp.utils.StringUtils;

/**
 * Created by Xujy on 2017/5/18.
 * for [线上招标项目管理 - 专家 - 校验器] in cpmp
 */
public class BidProjOnMngForExptChecker {


    public static String checkArgsForBgnGrd(BidProjOnExptGrdInf entity) {
        StringBuilder sb = new StringBuilder("");

        Long gradId = entity.getGrdId();
        if (gradId == null) {
            sb.append("评标ID不得为空;");
        }

        String grdKey = entity.getGrdKey();
        if (StringUtils.isEmpty(grdKey)) {
            sb.append("评标密钥不得为空;");
        }

        return sb.toString();
    }

    public static String checkArgsForSubAppRes(BidProjOnExptAppResDto entity) {
        StringBuilder sb = new StringBuilder("");

        Long gradId = entity.getGrdId();
        if (gradId == null) {
            sb.append("评标ID不得为空;");
        }

        String isAgreed = entity.getIsAgreed();
        if (StringUtils.isEmpty(isAgreed)) {
            sb.append("专家审批结果不得为空;");
        }
        String projAwdRsn = entity.getProjAwdRsn();
        if(StringUtils.isEmpty(projAwdRsn)){
            sb.append("专家审批理由不得为空");
        }

        return sb.toString();
    }

}
