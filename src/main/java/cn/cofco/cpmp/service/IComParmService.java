package cn.cofco.cpmp.service;

import java.util.List;

import cn.cofco.cpmp.dto.OutputDto;
import cn.cofco.cpmp.entity.ComParm;

/**
 * Created by Xujy on 2017/5/1.
 * for [通用参数服务接口] in cpmp
 */
public interface IComParmService {

    /**
     * 获取所有通用参数信息
     * @return
     */
    List<ComParm> getComParmAll();

    /**
     * 根据银行名查询银行代码列表
     * @param bnkNam
     * @return
     */
    OutputDto getByBnkNam(String bnkNam);

    /**
     * 更新币种通用参数
     * @param currTyp
     * @return
     */
    OutputDto addCurrTyp(ComParm currTyp);

    /**
     * 根据币种代号删除币种
     * @param parmCod
     * @return
     */
    OutputDto deleteCurrTyp(String parmCod);

}
