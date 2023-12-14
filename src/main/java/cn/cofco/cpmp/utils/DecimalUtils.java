package cn.cofco.cpmp.utils;

import cn.cofco.cpmp.log.LoggerManager;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalUtils {
    private static Logger logger = LoggerManager.getSysLog();

    public static String decimal2String(BigDecimal decimal) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(decimal);
    }
}