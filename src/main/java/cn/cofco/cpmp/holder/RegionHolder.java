package cn.cofco.cpmp.holder;

import cn.cofco.cpmp.log.LoggerManager;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xsmiler on 2017/10/21.
 */
public class RegionHolder {

    private static Logger logger = LoggerManager.getSysLog();

    private static Map<String, String> provinceCodes = new ConcurrentHashMap<>();

    public static void loadRegion(String region) {
        logger.info("加载省份code");
        Map<String, Map<String, Object>> regionStr = JSON.parseObject(region, Map.class);
        for (Map.Entry<String, Map<String, Object>> entry : regionStr.entrySet()) {
            Map<String, Object> province = entry.getValue();
            if (null != entry.getKey() && null != province.get("sap_code")) {
                provinceCodes.put((String) province.get("sap_code"), entry.getKey());
            }
        }
    }

    /**
     * 省份：根据sap_cod获取cod
     * @param sapCod
     * @return
     */
    public static String getProvinceCodeForSapCod(String sapCod) {

        return provinceCodes.get(sapCod);
    }
}
