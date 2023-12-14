package cn.cofco.cpmp.entity;

import java.sql.Timestamp;

public class SplrChrmInfo {
    private Long id;

    private Long splrId;

    private String chrmIcon;

    private String itdc;

    private Long upldUsr;

    private Timestamp upldTim;

    private Long modUsr;

    private Timestamp modTim;

    private String delFlg;
    
    private String iconTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSplrId() {
        return splrId;
    }

    public void setSplrId(Long splrId) {
        this.splrId = splrId;
    }

    public String getChrmIcon() {
        return chrmIcon;
    }

    public void setChrmIcon(String chrmIcon) {
        this.chrmIcon = chrmIcon;
    }

    public String getItdc() {
        return itdc;
    }

    public void setItdc(String itdc) {
        this.itdc = itdc;
    }

    public Long getUpldUsr() {
        return upldUsr;
    }

    public void setUpldUsr(Long upldUsr) {
        this.upldUsr = upldUsr;
    }

    public Timestamp getUpldTim() {
        return upldTim;
    }

    public void setUpldTim(Timestamp upldTim) {
        this.upldTim = upldTim;
    }

    public Long getModUsr() {
        return modUsr;
    }

    public void setModUsr(Long modUsr) {
        this.modUsr = modUsr;
    }

    public Timestamp getModTim() {
        return modTim;
    }

    public void setModTim(Timestamp modTim) {
        this.modTim = modTim;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

	public String getIconTitle() {
		return iconTitle;
	}

	public void setIconTitle(String iconTitle) {
		this.iconTitle = iconTitle;
	}
    
    
}