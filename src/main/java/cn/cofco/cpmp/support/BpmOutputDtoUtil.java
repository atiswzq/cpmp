package cn.cofco.cpmp.support;

import cn.cofco.cpmp.bpm.entity.BpmOutputDto;
import cn.cofco.cpmp.bpm.entity.PiResponse;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.utils.JaxbUtil;

/**
 * Created by Xujy on 2017/4/29.
 */
public class BpmOutputDtoUtil {


    public static PiResponse setOutputDto(String type, RtnEnum rtnEnum) {
        PiResponse piResponse = new PiResponse();
        piResponse.setType(type);
        BpmOutputDto bpmOutputDto = new BpmOutputDto();
        bpmOutputDto.setMsgCod(rtnEnum.getCod());
        bpmOutputDto.setMsgInf(rtnEnum.getDesc());
        String result = JaxbUtil.convertToXml(bpmOutputDto);
        piResponse.setMessage(result);
        return piResponse;
    }


    public static PiResponse setOutputDto(String type, RtnEnum rtnEnum, String msg) {
        PiResponse piResponse = new PiResponse();
        piResponse.setType(type);
        BpmOutputDto bpmOutputDto = new BpmOutputDto();
        bpmOutputDto.setMsgCod(rtnEnum.getCod());
        bpmOutputDto.setMsgInf(msg);
        String result = JaxbUtil.convertToXml(bpmOutputDto);
        piResponse.setMessage(result);
        return piResponse;
    }

}
