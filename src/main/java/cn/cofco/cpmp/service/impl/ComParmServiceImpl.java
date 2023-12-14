package cn.cofco.cpmp.service.impl;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dao.BnkCodParmMapper;
import cn.cofco.cpmp.dao.ComParmMapper;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.dto.PagedResult;
import cn.cofco.cpmp.entity.BnkCodParm;
import cn.cofco.cpmp.entity.ComParm;
import cn.cofco.cpmp.entity.ComParmKey;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IComParmService;
import cn.cofco.cpmp.support.OutputDtoUtil;


import cn.cofco.cpmp.utils.DateUtils;
import cn.cofco.cpmp.utils.StringUtils;
import cn.cofco.cpmp.utils.checkers.BidProjOnMngForPchsChecker;
import cn.cofco.cpmp.utils.checkers.ComparmChecker;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;


/**
 * Created by Xujy on 2017/5/1.
 * for [通用参数服务实现类] in cpmp
 */
@Service
@Transactional("transactionManager")
public class ComParmServiceImpl implements IComParmService {
	private static Logger logger = LoggerManager.getBidOnlineMngLog();

    @Resource
    private ComParmMapper comParmMapper;
    
    @Resource
    private BnkCodParmMapper bnkCodParmMapper;

	@Resource
	private IComParmService comParmService;

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
    public List<ComParm> getComParmAll() {

        List<ComParm> comParmList = comParmMapper.selectAll();

        return comParmList;
    }

	@Override
	public OutputDto getByBnkNam(String bnkNam) {
		
		List<BnkCodParm> listBnkCodParms = bnkCodParmMapper.selectByBnkNam(bnkNam);
		
		if (listBnkCodParms != null) {
			
			PagedResult result = new PagedResult(listBnkCodParms, listBnkCodParms.size());
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, result);
		}
		
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_WITH_NO_DATA, RtnEnum.SUC_WITH_NO_DATA.getDesc());
	}

	@Override
	public OutputDto addCurrTyp(ComParm currTyp) {
		logger.info("更新币种通用参数");

		// 1. 参数校验
		long timeA = DateUtils.currentTimeMillis();
		logger.info("1. 参数校验 - 开始");
		String checkRst = ComparmChecker.checkCurrTypExRat(currTyp);
		if (!"".equals(checkRst)) {
			logger.error("更新币种通用参数 - 参数校验不通过 - 参数信息：" + currTyp + " +++ 校验结果：" + checkRst);
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE, RtnEnum.ARG_INVALID, checkRst);
		}
		long durationA = DateUtils.currentTimeMillis() - timeA;
		logger.info("参数校验 - 结束 duration[" + durationA + "ms]");

		// 2. 业务校验
		long timeB = DateUtils.currentTimeMillis();
		logger.info("1. 数据持久化 - 开始");
		ComParmKey comParmKey = new ComParmKey();
		comParmKey.setParmTyp("CURR_TYP");
		comParmKey.setParmCod(currTyp.getParmCod());
		ComParm comParm = comParmMapper.selectByPrimaryKey(comParmKey);
		if (comParm != null) {
			comParm.setParmMemo(currTyp.getParmMemo());
			comParm.setParmVal(currTyp.getParmVal());
			comParm.setMntTim(DateUtils.getCurrentTimeStamp());
			int effRow = comParmMapper.updateByPrimaryKeySelective(comParm);
			if (effRow != 1) {
				String errMsg = "更新币种通用参数 - 受影响行数不为1";
				logger.error(errMsg);
				throw new RuntimeException(errMsg);
			}
			List<ComParm> comParmList = comParmMapper.selectByParmTyp("CURR_TYP");
			ComParmHolder.updateComParms("CURR_TYP",comParmList);
			OutputDto outputDto = OutputDtoUtil.setOutputDto(true,
					RtnEnum.SUC_OPR, comParmList);
			return outputDto;
		}
			long timeC = DateUtils.currentTimeMillis();
			ComParm comParm2 = new ComParm();
			comParm2.setParmMemo(currTyp.getParmMemo());
			comParm2.setParmVal(currTyp.getParmVal());
			comParm2.setParmTyp("CURR_TYP");
			comParm2.setEdtFlg(Constants.EFF_FLG_OFF);
			comParm2.setEffFlg(Constants.EFF_FLG_ON);
			comParm2.setMntTim(DateUtils.getCurrentTimeStamp());
			comParm2.setValTyp("00");
			comParm2.setVisiFlg(Constants.EFF_FLG_ON);
			comParm2.setParmCod(currTyp.getParmCod());
			int effRow1 = comParmMapper.insert(comParm2);
			if (effRow1 != 1) {
				String errMsg = "更新币种通用参数 - 受影响行数不为1";
				logger.error(errMsg);
				throw new RuntimeException(errMsg);
			}
			long durationC = DateUtils.currentTimeMillis() - timeC;
			logger.info("3. 数据持久化 - 结束，duration[" + durationC + "ms]");

			List<ComParm> comParmList = comParmMapper.selectByParmTyp("CURR_TYP");
			ComParmHolder.updateComParms("CURR_TYP",comParmList);
			OutputDto outputDto = OutputDtoUtil.setOutputDto(true,
				RtnEnum.SUC_OPR, comParmList);
			return outputDto;
		}

	@Override
	public OutputDto deleteCurrTyp(String parmCod) {
    	logger.info("删除币种");

    	//参数校验
		long timeA = DateUtils.currentTimeMillis();
		logger.info("参数校验 - 开始");
		if(StringUtils.isEmpty(parmCod)){
			String errMsg = "删除币种 - 失败 - 参数为空";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		long durationA = DateUtils.currentTimeMillis() - timeA;
		logger.info("参数校验 - 结束 duration[" + durationA + "ms]");

		long timeB = DateUtils.currentTimeMillis();
		logger.info("数据持久化 - 开始");
		ComParmKey comParmKey = new ComParmKey();
		comParmKey.setParmCod(parmCod);
		comParmKey.setParmTyp("CURR_TYP");
		int effRow = comParmMapper.deleteByPrimaryKey(comParmKey);
		if(effRow != 1){
			String errMsg = "删除币种 - 受影响行数不为1";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
			long durationB = DateUtils.currentTimeMillis() - timeB;
		logger.info("3. 数据持久化 - 结束，duration[" + durationB + "ms]");

		List<ComParm> comParmList = comParmMapper.selectByParmTyp("CURR_TYP");
		ComParmHolder.updateComParms("CURR_TYP",comParmList);
		OutputDto outputDto = OutputDtoUtil.setOutputDto(true,
				RtnEnum.SUC_OPR, comParmList);
		return outputDto;
	}

}
