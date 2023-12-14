package cn.cofco.cpmp.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xujy on 2017/5/4.
 * for [分页工具类] in cpmp
 */
public class PageUtils {

    public static Map getQueryCondsMap(Object bean, int start, int limit) {
//        Map map = ReflcUtils.bean2Map(bean);
        Map map = ReflcUtils.obj2Map(bean);

        if (map == null) {
            map = new HashMap();
        }
        map.put("start", start);
        map.put("limit", limit);
        return map;
    }
}
