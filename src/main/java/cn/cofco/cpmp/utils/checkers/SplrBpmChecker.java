package cn.cofco.cpmp.utils.checkers;

import cn.cofco.cpmp.utils.StringUtils;

/**
 * Created by xsmiler on 2017/7/24.
 */
public class SplrBpmChecker {

    public static String checkArgsForSplrCallBack(String bpmSeqNo, String sucFlg, String memo) {
        StringBuilder sb = new StringBuilder("");


        if (StringUtils.isEmpty(bpmSeqNo)) {
            sb.append("BPM审批流水号不得为空;");
        }

        if (StringUtils.isEmpty(sucFlg)) {
            sb.append("审核结果状态不得为空;");
        }

        return sb.toString();
    }
}
