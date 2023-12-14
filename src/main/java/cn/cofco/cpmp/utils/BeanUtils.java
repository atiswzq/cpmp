package cn.cofco.cpmp.utils;

import cn.cofco.cpmp.log.LoggerManager;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xujy on 2017/5/11.
 * for [封装org.apache.commons.beanutils.BeanUtils] in cpmp
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    private static Logger logger = LoggerManager.getSysLog();

    static {
        ConvertUtils.register(new DateConverter(), java.util.Date.class);
        ConvertUtils.register(new DateConverter(), java.sql.Date.class);
        ConvertUtils.register(new DateConverter(null), java.sql.Timestamp.class);
    }

    public static void copyProperties(Object target, Object source) {
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
        } catch (IllegalAccessException|InvocationTargetException e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
        }
    }

    public static Object cloneBean(Object object) {
        try {
            return org.apache.commons.beanutils.BeanUtils.cloneBean(object);
        } catch (IllegalAccessException|InstantiationException|InvocationTargetException|NoSuchMethodException e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
            return null;
        }
    }


    public static Map describe(Object obj) {
        try {
            return org.apache.commons.beanutils.BeanUtils.describe(obj);
        } catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
            return null;
        }
    }
}