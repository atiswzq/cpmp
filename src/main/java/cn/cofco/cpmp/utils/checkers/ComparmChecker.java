package cn.cofco.cpmp.utils.checkers;

import cn.cofco.cpmp.entity.ComParm;
import org.springframework.util.StringUtils;

/**
 * Created by wzq on 2018/1/8.
 */
public class ComparmChecker {
    public static String checkCurrTypExRat(ComParm comparm) {
        StringBuilder sb = new StringBuilder("");

        String parmCod = comparm.getParmCod();
        if (StringUtils.isEmpty(parmCod)) {
            sb.append("币种代号不得为空;");
        }
        String parmMemo = comparm.getParmMemo();
        if(StringUtils.isEmpty(parmMemo)){
            sb.append("币种名称不得为空;");
        }
        String parmVal = comparm.getParmVal();
        if(StringUtils.isEmpty(parmVal)){
            sb.append("汇率不得为空");
        }
        return sb.toString();
    }
}
