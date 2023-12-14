package cn.cofco.cpmp.utils;

import cn.cofco.cpmp.log.LoggerManager;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static Logger logger = LoggerManager.getSysLog();

	public final static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {

            // 去除前后全角空格
            while (str.startsWith("　")) {
                str = str.substring(1, str.length()).trim();
            }
            while (str.endsWith("　")) {
                str = str.substring(0, str.length() - 1).trim();
            }

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String getByLength(String str, int len, String charset) {
	    if (StringUtils.isEmpty(str)) {
	        return "";
        }
        byte[] bytes = null;
        try {
            bytes = str.getBytes(charset);
            if (bytes.length > len) {
                return new String(bytes,0, len, charset);
            }
            return str;
        } catch (UnsupportedEncodingException e) {
            logger.error("字符串截取异常 - E：" + ExceptionUtils.getFullStackTrace(e));
            return  "";
        }
    }

    public static String getByLength(String str, int len) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        byte[] bytes = null;
        try {
            bytes = str.getBytes("UTF-8");
            if (bytes.length > len) {
                return new String(bytes,0, len, "UTF-8");
            }
            return str;
        } catch (UnsupportedEncodingException e) {
            logger.error("字符串截取异常 - E：" + ExceptionUtils.getFullStackTrace(e));
            return  "";
        }


    }
}
