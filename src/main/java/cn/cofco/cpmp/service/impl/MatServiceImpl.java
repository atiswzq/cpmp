package cn.cofco.cpmp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.cofco.cpmp.dao.*;
import cn.cofco.cpmp.dto.PagedResult;
import cn.cofco.cpmp.entity.*;
import cn.cofco.cpmp.splr.vo.MatklSapTypVo;
import cn.cofco.cpmp.utils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.enums.RtnEnum;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IMatService;
import cn.cofco.cpmp.support.OutputDtoUtil;
import cn.cofco.cpmp.utils.PageUtils;

@Service
@Transactional("transactionManager")
public class MatServiceImpl implements IMatService {
	private Logger logger = LoggerManager.getSplrSelfMngLog();

	@Resource
	private SplrMatMapper splrMatMapper;
	
	@Resource
	private MatMapper matMapper;
	
	@Resource
    private ComParmMapper comParmMapper;

	@Resource
	private MaterielMapper materielMapper;

	@Resource
	private MatklSapMapper matklSapMapper;

	/**
	 * 批量插入物料
	 */
	@Override
	public OutputDto addMat(String access_token, List<Mat> mats)
			throws Exception {
		
		// 更新物料类型到公共参数表
		List<ComParm> comParms = new ArrayList<ComParm>();
		for (Mat mat : mats) {
			if (ComParmHolder.getByParmTypAndParmCod("MAT_TYP", mat.getMatTyp()) == null) {
				boolean isExist = false;
				for (ComParm comParm : comParms) {
					if (comParm.getParmCod().equalsIgnoreCase(mat.getMatTyp())) {
						isExist = true;
						break;
					}
				}
				if (isExist) {
					break;
				}
				ComParm comParm = new ComParm();
				comParm.setParmTyp("MAT_TYP");
				comParm.setParmCod(mat.getMatTyp());
				comParm.setValTyp("00");
				comParm.setParmVal(mat.getMatTyp());
				comParm.setParmMemo(mat.getMatTyp());
				comParm.setVisiFlg("1");
				comParm.setEdtFlg("0");
				comParm.setEffFlg("1");
				comParms.add(comParm);
				comParmMapper.insertSelective(comParm);
			}
		}
		// TODO
		
		// 批量插入物料
		int rowNum = matMapper.inserts(mats);
		if (rowNum == mats.size()) {
			logger.info("插入物料成功，物料数据：" + mats.toString());
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, RtnEnum.SUC_OPR.getDesc());
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_FALSE, RtnEnum.FAIL_OPR);
	}

	/**
	 * 根据条件获取物料
	 * @param access_token
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto getMat(String access_token, Mat dto, Integer pageNo,
			Integer pageSize) throws Exception {
		
		if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }

        if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
            pageSize = Constants.PAGE_SIZE;
        }

        Integer start = (pageNo - 1) * pageSize;

        Map map = PageUtils.getQueryCondsMap(dto, start, pageSize);
        
        List<Mat> mats = matMapper.selectCondition(map);
        
        if (mats == null || mats.size() == 0) {
        	return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_WITH_NO_DATA, RtnEnum.SUC_WITH_NO_DATA.getDesc());
        }
        
        return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR, mats);
	}

	/**
	 * 根据物料类型获取供应商列表
	 *
	 * @param accessToken
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutputDto splrByMatType(String matTyp, String splrSts)
			throws Exception {
		// TODO
		
		if (matTyp == null) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_FALSE,
					RtnEnum.ARG_INVALID, RtnEnum.ARG_INVALID.getDesc());
		}
		
		List<Splr> splrs = splrMatMapper.getSplrByMatTyp(matTyp, splrSts);
		if (splrs.size() == 0) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_WITH_NO_DATA, RtnEnum.SUC_WITH_NO_DATA.getDesc());
		}
		
		return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
				RtnEnum.SUC_OPR, splrs);
	}

	@Override
	public OutputDto matList(Materiel materiel, Integer pageNo, Integer pageSize) throws Exception {

		// 分页查询页码处理
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		if (pageSize == null || pageSize <= 0 || pageSize > Constants.PAGE_SIZE_MAX) {
			pageSize = Constants.PAGE_SIZE;
		}

		Integer start = (pageNo - 1) * pageSize + 1;

		Integer to = pageNo * pageSize;

		Map map = PageUtils.getQueryCondsMap(materiel, start, to);

		List<Materiel> materiels = materielMapper.selectByConditions(map);
		Integer count = materielMapper.countOfMap(map);

		PagedResult result = new PagedResult(materiels, count);

		if (materiels != null && materiels.size() > 0) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC_OPR, result);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA, RtnEnum.SUC_WITH_NO_DATA.getDesc());
	}

	@Override
	public OutputDto matType() throws Exception {
		// 查询物料类型
		List<String> matTypes = splrMatMapper.matType();
		if (matTypes != null && matTypes.size() > 0) {
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC, matTypes);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA, RtnEnum.SUC_WITH_NO_DATA.getDesc());
	}

	@Override
	public OutputDto matSapType() {

		List<MatklSap> matklSaps = matklSapMapper.selectAll();
		List<MatklSapTypVo> matklSapTypVos = new ArrayList<>();

		if (null != matklSaps && matklSaps.size() > 0) {
			for (MatklSap matklSap : matklSaps) {
				MatklSapTypVo matklSapTypVo = new MatklSapTypVo();
				BeanUtils.copyProperties(matklSapTypVo, matklSap);
				matklSapTypVos.add(matklSapTypVo);
			}
			return OutputDtoUtil.setOutputDto(Constants.SUC_TRUE,
					RtnEnum.SUC, matklSapTypVos);
		}
		return OutputDtoUtil
				.setOutputDto(Constants.SUC_TRUE, RtnEnum.SUC_WITH_NO_DATA, RtnEnum.SUC_WITH_NO_DATA.getDesc());
	}

	@Override
	public List<MatklSap> matSaps() {

		List<MatklSap> matklSaps = matklSapMapper.selectAll();

		return matklSaps;
	}

}
