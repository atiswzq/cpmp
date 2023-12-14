package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.Atch;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Xujy on 2017/6/11.
 * for [文件用途] in cpmp
 */
public class ArtclDtlIoDto {

    private Long artclId;

    private String artclTypCod;

    private String artclTtl;

    private String pubFlg;

    private Long clkCnt;

    private String effFlg;

    private String crtUsr;

    private Timestamp crtTim;

    private String modUsr;

    private Timestamp modTim;

    private String pubUsr;

    private Timestamp pubTim;

    private String artclCtt;

    private List<Atch> atches;

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

    public String getEffFlg() {
        return effFlg;
    }

    public void setEffFlg(String effFlg) {
        this.effFlg = effFlg;
    }

    public String getCrtUsr() {
        return crtUsr;
    }

    public void setCrtUsr(String crtUsr) {
        this.crtUsr = crtUsr;
    }

    public Timestamp getCrtTim() {
        return crtTim;
    }

    public void setCrtTim(Timestamp crtTim) {
        this.crtTim = crtTim;
    }

    public String getModUsr() {
        return modUsr;
    }

    public void setModUsr(String modUsr) {
        this.modUsr = modUsr;
    }

    public Timestamp getModTim() {
        return modTim;
    }

    public void setModTim(Timestamp modTim) {
        this.modTim = modTim;
    }

    public String getPubUsr() {
        return pubUsr;
    }

    public void setPubUsr(String pubUsr) {
        this.pubUsr = pubUsr;
    }

    public Timestamp getPubTim() {
        return pubTim;
    }

    public void setPubTim(Timestamp pubTim) {
        this.pubTim = pubTim;
    }

    public String getArtclCtt() {
        return artclCtt;
    }

    public void setArtclCtt(String artclCtt) {
        this.artclCtt = artclCtt;
    }

    public List<Atch> getAtches() {
        return atches;
    }

    public void setAtches(List<Atch> atches) {
        this.atches = atches;
    }

    @Override
    public String toString() {
        return "ArtclDtlIoDto{" +
                "artclId=" + artclId +
                ", artclTypCod='" + artclTypCod + '\'' +
                ", artclTtl='" + artclTtl + '\'' +
                ", pubFlg='" + pubFlg + '\'' +
                ", clkCnt=" + clkCnt +
                ", effFlg='" + effFlg + '\'' +
                ", crtUsr='" + crtUsr + '\'' +
                ", crtTim=" + crtTim +
                ", modUsr='" + modUsr + '\'' +
                ", modTim=" + modTim +
                ", pubUsr='" + pubUsr + '\'' +
                ", pubTim=" + pubTim +
                ", artclCtt='" + artclCtt + '\'' +
                ", atches=" + atches +
                '}';
    }
}
