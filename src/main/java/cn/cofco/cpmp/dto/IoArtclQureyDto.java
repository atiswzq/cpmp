package cn.cofco.cpmp.dto;


/**
 * Created by Xujy on 2017/6/10.
 * for [查询文章信息DTO] in cpmp
 */
public class IoArtclQureyDto {

    private Long artclId;

    private String artclTypCod;

    private String artclTtl;

    private String artclCtt;

    private String pubFlg;

    private Long clkCnt;

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

    public Long getClkCnt() {
        return clkCnt;
    }

    public void setClkCnt(Long clkCnt) {
        this.clkCnt = clkCnt;
    }

    @Override
    public String toString() {
        return "IoArtclQureyDto{" +
                "artclId=" + artclId +
                ", artclTypCod='" + artclTypCod + '\'' +
                ", artclTtl='" + artclTtl + '\'' +
                ", artclCtt='" + artclCtt + '\'' +
                ", pubFlg='" + pubFlg + '\'' +
                ", clkCnt=" + clkCnt +
                '}';
    }
}
