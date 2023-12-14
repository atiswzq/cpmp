package cn.cofco.cpmp.utils.checkers;

import cn.cofco.cpmp.bpm.entity.BpmInputDto;
import cn.cofco.cpmp.utils.StringUtils;

/**
 * Created by Xujy on 2017/7/11.
 * for [BPM回调接口参数校验] in cpmp
 */
public class BidBpmChecker {

    public static String checkArgsForBidCallBack(BpmInputDto inputDto) {
        StringBuilder sb = new StringBuilder("");

        String bpmSeqNo = inputDto.getBpmSeqNo();
        if (StringUtils.isEmpty(bpmSeqNo)) {
            sb.append("BPM审批流水号不得为空;");
        }

        String tempNum = inputDto.getTempNum();
        if (StringUtils.isEmpty(tempNum)) {
            sb.append("模板编号不得为空;");
        }

        String sucFlg = inputDto.getSucFlg();
        if (StringUtils.isEmpty(sucFlg)) {
            sb.append("成功标识不得为空;");
        }

        String memo = inputDto.getMemo();
        inputDto.setMemo(StringUtils.getByLength(memo, 255));

        return sb.toString();
    }

}
