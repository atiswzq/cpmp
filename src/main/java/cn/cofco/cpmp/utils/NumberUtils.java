package cn.cofco.cpmp.utils;

import java.text.NumberFormat;

/**
 * Created by Xujy on 2017/7/6.
 * for [数字工具类] in cpmp
 */
public class NumberUtils {

    public static String getFmtNbr(Long nbr, int len) {
        //得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(len);
        //设置最小整数位数
        nf.setMinimumIntegerDigits(len);
        return nf.format(nbr);
    }

}
