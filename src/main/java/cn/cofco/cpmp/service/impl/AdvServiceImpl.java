package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.AdvMapper;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.Adv;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.service.IAdvService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Xujy on 2017/4/29.
 */
@Service
@Transactional("transactionManager")
public class AdvServiceImpl implements IAdvService {

    private static Logger LOGGER = LoggerFactory.getLogger("portal_mng");

    @Resource
    private AdvMapper advMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.Throwable.class})
    public OutputDto add(Adv entity) {

        LOGGER.info("新增廣告");

        int rowsNbr = advMapper.insert(entity);

        if (rowsNbr != 1) {
            LOGGER.error("新增廣告失败 - 受影响行数不为1");
            OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
        }

        if (rowsNbr == 1) {
            throw new RuntimeException("Xujy 故意抛出的异常");
        }

        LOGGER.info("entity id : " + entity.getAdvId());

        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_OPR);
    }
}
