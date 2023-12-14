package cn.cofco.cpmp.utils.checkers;

import cn.cofco.cpmp.dto.IoArtclDtlDto;
import cn.cofco.cpmp.dto.IoAtchDto;
import cn.cofco.cpmp.utils.StringUtils;

import java.util.List;

/**
 * Created by Xujy on 2017/5/18.
 * for [文章管理 - 校验器] in cpmp
 */
public class ArtclChecker {

    public static String checkArgsForAdd(IoArtclDtlDto dto) {

        StringBuilder sb = new StringBuilder("");

        String artclTypCod = dto.getArtclTypCod();
        if (StringUtils.isEmpty(artclTypCod)) {
            sb.append("文章类型不得为空;");
        }

        String artclTtl = dto.getArtclTtl();
        if (StringUtils.isEmpty(artclTtl)) {
            sb.append("文章标题不得为空;");
        }

        String artclCtt = dto.getArtclCtt();
        if (StringUtils.isEmpty(artclCtt)) {
            sb.append("文章内容不得为空;");
        }

        List<IoAtchDto> atchDtos = dto.getAtchDtos();
        if (atchDtos != null && !atchDtos.isEmpty()) {
            for (IoAtchDto atchDto : atchDtos) {
                String atchNam = atchDto.getAtchNam();
                if (StringUtils.isEmpty(atchNam)) {
                    sb.append("附件名称不得为空;");
                }
                String atchUrl = atchDto.getAtchUrl();
                if (StringUtils.isEmpty(atchUrl)) {
                    sb.append("附件URL不得为空;");
                }
            }
        }

        return sb.toString();
    }

    public static String checkArgsForEdit(IoArtclDtlDto dto) {
        StringBuilder sb = new StringBuilder("");

        Long artclId = dto.getArtclId();
        if (artclId == null || artclId == 0L) {
            sb.append("文章ID不得为空;");
        }

        String artclTypCod = dto.getArtclTypCod();
        if (StringUtils.isEmpty(artclTypCod)) {
            sb.append("文章类型不得为空;");
        }

        String artclTtl = dto.getArtclTtl();
        if (StringUtils.isEmpty(artclTtl)) {
            sb.append("文章标题不得为空;");
        }

        String artclCtt = dto.getArtclCtt();
        if (StringUtils.isEmpty(artclCtt)) {
            sb.append("文章内容不得为空;");
        }

        List<IoAtchDto> atchDtos = dto.getAtchDtos();
        if (atchDtos != null && !atchDtos.isEmpty()) {
            for (IoAtchDto atchDto : atchDtos) {
                String atchNam = atchDto.getAtchNam();
                if (StringUtils.isEmpty(atchNam)) {
                    sb.append("附件名称不得为空;");
                }
                String atchUrl = atchDto.getAtchUrl();
                if (StringUtils.isEmpty(atchUrl)) {
                    sb.append("附件URL不得为空;");
                }
            }
        }

        return sb.toString();
    }
}
