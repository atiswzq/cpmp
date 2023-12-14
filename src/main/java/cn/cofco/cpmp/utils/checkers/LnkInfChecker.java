package cn.cofco.cpmp.utils.checkers;

import cn.cofco.cpmp.entity.LnkInf;
import cn.cofco.cpmp.utils.StringUtils;

/**
 * Created by Xujy on 2017/6/11.
 * for [文件用途] in cpmp
 */
public class LnkInfChecker {
    public static String checkArgsForAdd(LnkInf entity) {
        StringBuilder sb = new StringBuilder("");

        String lnkNam = entity.getLnkNam();
        if (StringUtils.isEmpty(lnkNam)) {
            sb.append("链接名称不得为空;");
        }

        String lnkUrl = entity.getLnkUrl();
        if (StringUtils.isEmpty(lnkUrl)) {
            sb.append("链接URL不得为空;");
        }

        String lnkPic = entity.getLnkPic();
        if (StringUtils.isEmpty(lnkPic)) {
            sb.append("链接图片地址不得为空;");
        }

        return sb.toString();

    }

    public static String checkArgsForEdit(LnkInf entity) {

        StringBuilder sb = new StringBuilder("");

        Long lnkId = entity.getLnkId();
        if (lnkId == null || lnkId == 0L) {
            sb.append("链接ID不得为空;");
        }

        String lnkNam = entity.getLnkNam();
        if (StringUtils.isEmpty(lnkNam)) {
            sb.append("链接名称不得为空;");
        }

        String lnkUrl = entity.getLnkUrl();
        if (StringUtils.isEmpty(lnkUrl)) {
            sb.append("链接URL不得为空;");
        }

        String lnkPic = entity.getLnkPic();
        if (StringUtils.isEmpty(lnkPic)) {
            sb.append("链接图片地址不得为空;");
        }

        return sb.toString();
    }
}
