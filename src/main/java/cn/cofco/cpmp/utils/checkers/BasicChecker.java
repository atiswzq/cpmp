package cn.cofco.cpmp.utils.checkers;

import cn.cofco.cpmp.utils.StringUtils;

/**
 * Created by Xujy on 2017/6/4.
 * for [基本校验工具类] in cpmp
 */
public class BasicChecker {

    public static boolean inArray(String arg, String[] args) {
        if (StringUtils.isEmpty(arg)) {
            return false;
        }
        if (args == null || args.length <= 0) {
            return false;
        }
        for (String a : args) {
            if (arg.equals(a)) {
                return true;
            }
        }
        return false;
    }
}
