package cn.cofco.cpmp.utils;

import cn.cofco.cpmp.log.LoggerManager;
import io.swagger.models.auth.In;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class ReflcUtils {

    private static Logger logger = LoggerManager.getBusiLog();

    /**
     * 根据属性名称获取属性值，该属性必须具备公共的getter方法
     *
     * @param fieldName 属性值
     * @param object    对象
     * @return
     */
    public static Object getFieldValueByName(String fieldName, Object object) {
        if (StringUtils.isEmpty(fieldName) || object == null) {
            String msg = "根据属性名称获取属性值时属性名称为空或对象为null";
            logger.error(msg);
            throw new RuntimeException(msg);
        }
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            boolean accessFlag = f.isAccessible();
            f.setAccessible(true);
            Object o = f.get(object);
            f.setAccessible(accessFlag);
            return o;
        } catch (Exception e) {
            logger.error("根据属性名称获取属性值失败 - fieldName：" + fieldName + ", object: " + object.getClass());
        }
        return null;
    }

    /**
     * 根据属性名称设置对象中的相应值
     *
     * @param fieldName
     * @param value
     * @param object
     */
    public static void setFieldValueByName(String fieldName, Object value, Object object) {
        if (StringUtils.isEmpty(fieldName) || object == null) {
            String msg = "根据属性名称设置对象中的相应值时属性名称为空或对象为null";
            logger.error(msg);
            throw new RuntimeException(msg);
        }
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            boolean accessFlag = f.isAccessible();// 获得原始权限
            f.setAccessible(true); // 权限设置为可访问
            f.set(object, f.getType().getConstructor(value.getClass()).newInstance(value));
            f.setAccessible(accessFlag);// 还原权限
        } catch (Exception e) {
            String msg = "根据属性名称设置对象中的相应值失败 - fieldName[" + fieldName + "], obj[" + object.getClass() + "], Exception: {" + e.getMessage() + "}";
            logger.error("根据属性名称设置对象中的相应值失败 - fieldName[{}], obj[{}], Exception: {}", fieldName, object.getClass(), ExceptionUtils.getStackTrace(e));
            throw new RuntimeException(msg);
        }
    }


    public static Map bean2Map(Object bean) {
        Map<String, Object> map = null;
        try {
            map = BeanUtils.describe(bean);
        } catch (Exception e) {
            logger.error("bean转换为map异常 - E：" + ExceptionUtils.getFullStackTrace(e));
            e.printStackTrace();
        }
        return map;
    }


    public static Map<String, Object> obj2Map(Object obj) {

        Map<String, Object> map = new HashMap<>();

        if (obj == null) {
            return null;
        }

        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null) {
                    map.put(varName, o);
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                logger.error("bean转换为map异常 - E：" + ExceptionUtils.getFullStackTrace(ex));
            } catch (IllegalAccessException ex) {
                logger.error("bean转换为map异常 - E：" + ExceptionUtils.getFullStackTrace(ex));
            }
        }
        return map;
    }

}
