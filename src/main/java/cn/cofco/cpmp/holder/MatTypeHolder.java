package cn.cofco.cpmp.holder;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.MatklSapMapper;
import cn.cofco.cpmp.entity.MatklSap;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.splr.vo.MatklSapTypVo;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.BeanUtils;
import org.slf4j.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xsmiler on 2017/11/5.
 */
public class MatTypeHolder {

    private static Logger logger = LoggerManager.getSysLog();

    private static Map<String, MatklSapTypVo> matTypeMap = new ConcurrentHashMap();

    public static void loadMatType(List<MatklSap> matklSaps) {

        if (null != matklSaps && matklSaps.size() > 0) {
            for (MatklSap matklSap : matklSaps) {
                MatklSapTypVo matklSapTypVo = new MatklSapTypVo();
                BeanUtils.copyProperties(matklSapTypVo, matklSap);
                matTypeMap.put(matklSapTypVo.getMatkl2(), matklSapTypVo);
            }
        }
    }

    public static MatklSapTypVo getByMatkl2(String matkl2) {
        return matTypeMap.get(matkl2);
    }
}
