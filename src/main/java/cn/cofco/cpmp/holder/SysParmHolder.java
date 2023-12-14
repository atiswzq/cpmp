package cn.cofco.cpmp.holder;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.entity.SysParm;
import cn.cofco.cpmp.log.LoggerManager;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Xujy on 2017/5/1.
 * for [系统参数缓存信息类] in cpmp
 */
public class SysParmHolder {

    private static Logger logger = LoggerManager.getSysLog();

    private static List<SysParm> sysParms;

    public static String FILE_DIR = "";

    /**
     * 加载通用参数
     * @param tmpSysParms
     */
    public static synchronized void loadSysParms(List<SysParm> tmpSysParms) {
        if (sysParms == null) {
            sysParms = new ArrayList<>();
        } else {
            sysParms.clear();
        }

        for (SysParm s : tmpSysParms) {
            sysParms.add(s);
        }
    }

    /**
     * 根据参数类型、参数编码查询系统参数信息
     * @param parmTyp
     * @param parmCod
     * @return
     */
    public static SysParm getByParmTypAndParmCod(String parmTyp, String parmCod) {
        if (sysParms == null || sysParms.isEmpty()) {
            return null;
        }

        for (SysParm s : sysParms) {

            if (s.getParmTyp().equals(parmTyp)
                    && s.getParmCod().equals(parmCod)
                    && Constants.EFF_FLG_ON.equals(s.getEffFlg())) {
                SysParm tmpSysParm = null;
                try {
                    tmpSysParm = (SysParm) BeanUtils.cloneBean(s);
                } catch (Exception e) {
                    logger.error("clone系统参数时异常 - e: " + ExceptionUtils.getFullStackTrace(e));
                    tmpSysParm = null;
                }
                return tmpSysParm;
            }
        }

        return null;
    }

    /**
     * 根据系统参数类型获取通用参数列表信息
     * @param parmTyp
     * @return
     */
    public static List<SysParm> getByParmTyp(String parmTyp) {
        if (sysParms == null || sysParms.isEmpty()) {
            return Collections.emptyList();
        }

        List<SysParm> sysParmList = new ArrayList<>();

        for (SysParm s : sysParms) {

            if (s.getParmTyp().equals(parmTyp)
                    && Constants.EFF_FLG_ON.equals(s.getEffFlg())) {
                SysParm tmpSysParm = null;
                try {
                    tmpSysParm = (SysParm) BeanUtils.cloneBean(s);
                    sysParmList.add(tmpSysParm);
                } catch (Exception e) {
                    logger.error("clone系统参数时异常 - e: " + ExceptionUtils.getFullStackTrace(e));
                }
            }
        }

        return sysParmList;
    }

}
