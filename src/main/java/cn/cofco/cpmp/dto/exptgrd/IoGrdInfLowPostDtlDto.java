package cn.cofco.cpmp.dto.exptgrd;

import java.util.List;

/**
 * Created by Xujy on 2017/12/23.
 * for [提交最低价评标结果 - 明细] in cpmp
 */
public class IoGrdInfLowPostDtlDto {

    private Long id;

    private Integer grdOrd;

    private String isRcmd;

    private String grdRsn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrdOrd() {
        return grdOrd;
    }

    public void setGrdOrd(Integer grdOrd) {
        this.grdOrd = grdOrd;
    }

    public String getIsRcmd() {
        return isRcmd;
    }

    public void setIsRcmd(String isRcmd) {
        this.isRcmd = isRcmd;
    }

    public String getGrdRsn() {
        return grdRsn;
    }

    public void setGrdRsn(String grdRsn) {
        this.grdRsn = grdRsn;
    }

    @Override
    public String toString() {
        return "IoGrdInfLowPostDtlDto{" +
                "id=" + id +
                ", grdOrd=" + grdOrd +
                ", isRcmd='" + isRcmd + '\'' +
                ", grdRsn='" + grdRsn + '\'' +
                '}';
    }
}
