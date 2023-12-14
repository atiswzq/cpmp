package cn.cofco.cpmp.service;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.splr.vo.SplrChrmSetVo;

public interface ISplrChrmService {

	OutputDto getSplrChrms() throws Exception;

	OutputDto getSplrChrmById(Long splrId) throws Exception;

	OutputDto setSplrChrms(SplrChrmSetVo splrIds) throws Exception;
}
