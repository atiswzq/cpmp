package cn.cofco.cpmp.dto;

import java.util.List;

/**
 * Created by Xujy on 2017/6/11.
 * for [上送文章详情DTO] in cpmp
 */
public class IoArtclDtlDto {

    private Long artclId;

    private String artclTypCod;

    private String artclTtl;

    private String artclCtt;

    private String pubFlg;

    private List<IoAtchDto> atchDtos;

    public Long getArtclId() {
        return artclId;
    }

    public void setArtclId(Long artclId) {
        this.artclId = artclId;
    }

    public String getArtclTypCod() {
        return artclTypCod;
    }

    public void setArtclTypCod(String artclTypCod) {
        this.artclTypCod = artclTypCod;
    }

    public String getArtclTtl() {
        return artclTtl;
    }

    public void setArtclTtl(String artclTtl) {
        this.artclTtl = artclTtl;
    }

    public String getArtclCtt() {
        return artclCtt;
    }

    public void setArtclCtt(String artclCtt) {
        this.artclCtt = artclCtt;
    }

    public String getPubFlg() {
        return pubFlg;
    }

    public void setPubFlg(String pubFlg) {
        this.pubFlg = pubFlg;
    }

    public List<IoAtchDto> getAtchDtos() {
        return atchDtos;
    }

    public void setAtchDtos(List<IoAtchDto> atchDtos) {
        this.atchDtos = atchDtos;
    }

    @Override
    public String toString() {
        return "IoArtclDtlDto{" +
                "artclId=" + artclId +
                ", artclTypCod='" + artclTypCod + '\'' +
                ", artclTtl='" + artclTtl + '\'' +
                ", artclCtt='" + artclCtt + '\'' +
                ", pubFlg='" + pubFlg + '\'' +
                ", atchDtos=" + atchDtos +
                '}';
    }
}
