package cn.cofco.cpmp.utils.checkers;

import cn.cofco.cpmp.dto.IoExptLoginDto;
import cn.cofco.cpmp.dto.IoExptModPswDto;
import cn.cofco.cpmp.entity.ExptInf;
import cn.cofco.cpmp.utils.StringUtils;

/**
 * Created by Xujy on 2017/5/30.
 * for [专家信息校验类] in cpmp
 */
public class ExptInfChecker {

    public static String checkArgsForAdd(ExptInf entity) {

        StringBuilder sb = new StringBuilder("");

        String exptNam = entity.getExptNam();
        if (StringUtils.isEmpty(exptNam)) {
            sb.append("专家姓名[exptNam]不得为空;");
        }

        String mobNbr = entity.getMobNbr();
        if (StringUtils.isEmpty(mobNbr)) {
            sb.append("专家手机号[mobNbr]不得为空;");
        }

        String exptTTl = entity.getExptTtl();
        if (StringUtils.isEmpty(exptTTl)) {
            sb.append("专家职称[exptTTl]不得为空;");
        }

        return sb.toString();
    }

    public static String checkArgsForMod(ExptInf entity) {

        StringBuilder sb = new StringBuilder("");

        Long exptId = entity.getExptId();
        if (exptId == null || exptId == 0l) {
            sb.append("专家ID[exptId]不得为空;");
        }

        String exptNam = entity.getExptNam();
        if (StringUtils.isEmpty(exptNam)) {
            sb.append("专家姓名[exptNam]不得为空;");
        }

        String mobNbr = entity.getMobNbr();
        if (StringUtils.isEmpty(mobNbr)) {
            sb.append("专家手机号[mobNbr]不得为空;");
        }

        String exptTTl = entity.getExptTtl();
        if (StringUtils.isEmpty(exptTTl)) {
            sb.append("专家职称[exptTTl]不得为空;");
        }

        return sb.toString();
    }

    public static String checkArgsForModPsw(IoExptModPswDto dto) {

        StringBuilder sb = new StringBuilder("");

        String pwd = dto.getPsw();
        if (StringUtils.isEmpty(pwd)) {
            sb.append("原密码[pwd]不得为空;");
        }

        String pwdNew = dto.getPswNew();
        if (StringUtils.isEmpty(pwdNew)) {
            sb.append("新密码[pwdNew]不得为空;");
        }

        return sb.toString();
    }

    public static String checkArgsForLogin(IoExptLoginDto dto) {
        StringBuilder sb = new StringBuilder("");

        String mobNbr = dto.getMobNbr();
        if (StringUtils.isEmpty(mobNbr)) {
            sb.append("手机号[mobNbr]不得为空;");
        }

        String exptPsw = dto.getExptPsw();
        if (StringUtils.isEmpty(exptPsw)) {
            sb.append("密码[exptPsw]不得为空;");
        }

        return sb.toString();
    }
}
