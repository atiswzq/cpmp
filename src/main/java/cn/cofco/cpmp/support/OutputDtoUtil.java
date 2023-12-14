package cn.cofco.cpmp.support;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;

/**
 * Created by Xujy on 2017/4/29.
 */
public class OutputDtoUtil {


    public static OutputDto setOutputDto(boolean success, RtnEnum rtnEnum) {
        OutputDto output = new OutputDto();
        output.setSuccess(success);
        output.setMsgCod(rtnEnum.getCod());
        output.setMsgInf(rtnEnum.getDesc());
        return output;
    }

    public static OutputDto setOutputDto(boolean success, RtnEnum rtnEnum, Object data) {
        OutputDto output = new OutputDto();
        output.setSuccess(success);
        output.setMsgCod(rtnEnum.getCod());
        output.setMsgInf(rtnEnum.getDesc());
        output.setData(data);
        return output;
    }

    public static OutputDto setOutputDto(boolean success, RtnEnum rtnEnum, String msg) {
        OutputDto output = new OutputDto();
        output.setSuccess(success);
        output.setMsgCod(rtnEnum.getCod());
        output.setMsgInf(msg);
        return output;
    }

    public static OutputDto setOutputDto(boolean success, RtnEnum rtnEnum, String msg, Object data) {
        OutputDto output = new OutputDto();
        output.setSuccess(success);
        output.setMsgCod(rtnEnum.getCod());
        output.setMsgInf(msg);
        output.setData(data);
        return output;
    }
}
