package cn.cofco.cpmp.listeners;

import cn.cofco.cpmp.constants.Constants;
import cn.cofco.cpmp.entity.ComParm;
import cn.cofco.cpmp.entity.SysParm;
import cn.cofco.cpmp.holder.ComParmHolder;
import cn.cofco.cpmp.holder.MatTypeHolder;
import cn.cofco.cpmp.holder.SysParmHolder;
import cn.cofco.cpmp.log.LoggerManager;
import cn.cofco.cpmp.service.IComParmService;
import cn.cofco.cpmp.service.IMatService;
import cn.cofco.cpmp.service.ISysParmService;
import cn.cofco.cpmp.utils.DateUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Properties;

/**
 * Created by Xujy on 2017/5/1.
 *      for [系统监听器-web服务启动时基础信息加载] in cpmp
 */
public class SystemListener implements ServletContextListener {

    private static Logger logger = LoggerManager.getSysLog();

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("系統參數銷毀 ... ");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Long timeA = System.currentTimeMillis();
        logger.info("參數加載開始 ... ");

        Long timeB = System.currentTimeMillis();

        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        IComParmService comParmService = (IComParmService) context.getBean("comParmServiceImpl");
        ISysParmService sysParmService = (ISysParmService) context.getBean("sysParmServiceImpl");

        logger.info("參數加載 - 通用参数加載開始");
        List<ComParm> comParmList = comParmService.getComParmAll();
        ComParmHolder.loadComParms(comParmList);
        long durationA = DateUtils.currentTimeMillis() - timeB;
        logger.info("參數加載 - 通用参数加載結束, 耗時[" + durationA + "ms]");

        Long timeC = System.currentTimeMillis();
        logger.info("參數加載 -系统参数加載開始");
        List<SysParm> sysParmList = sysParmService.getSysParmAll();
        SysParmHolder.loadSysParms(sysParmList);

        Properties props = System.getProperties(); //获得系统属性集
        String osName = props.getProperty("os.name"); //操作系统名称
        String osArch = props.getProperty("os.arch"); //操作系统构架
        String osVersion = props.getProperty("os.version"); //操作系统版本

        logger.info("osName: " + osName + " | osArch: " + osArch + " | version: " + osVersion);
        if (osName.startsWith("Windows")) {
            SysParmHolder.FILE_DIR = SysParmHolder.getByParmTypAndParmCod(Constants.SYS_PARM_TYP_FILE_ADDR, Constants.SYS_PARM_KEY_FILE_ADDR_WINDOWS).getParmVal();
        } else {
            SysParmHolder.FILE_DIR = SysParmHolder.getByParmTypAndParmCod(Constants.SYS_PARM_TYP_FILE_ADDR, Constants.SYS_PARM_KEY_FILE_ADDR_UNIX).getParmVal();
        }

        long durationB = DateUtils.currentTimeMillis() - timeC;
        logger.info("參數加載 - 系统参数加載結束, 耗時[" + durationB + "ms]");

        long duration = DateUtils.currentTimeMillis() - timeA;
        logger.info("參數加載結束 ... 耗時[" + duration + "ms]");
        
        // 属性拷贝转化器注册
        ConvertUtils.register(new DateConverter(null), java.sql.Timestamp.class);

        // 加载物料类型
        IMatService matService = (IMatService) context.getBean("matServiceImpl");
        MatTypeHolder.loadMatType(matService.matSaps());

    }
}
