package cn.cofco.cpmp.dto.exptgrd;

import cn.cofco.cpmp.entity.BidProjOn;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Xujy on 2017/12/23.
 * for [文件用途] in cpmp
 */
public class GrdClctInfUniLowDtlIoDto {

    private Long matId;

    private String matCod;

    private String matNam;

    private String matUnt;

    private String pchsNum;

    private String dlvAdr;

    private Timestamp dlvDte;

    private String memo;

    private List<GrdClctInfUniLowDtlGrdInfIoDto> grdInfs;

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public String getMatCod() {
        return matCod;
    }

    public void setMatCod(String matCod) {
        this.matCod = matCod;
    }

    public String getMatNam() {
        return matNam;
    }

    public void setMatNam(String matNam) {
        this.matNam = matNam;
    }

    public String getMatUnt() {
        return matUnt;
    }

    public void setMatUnt(String matUnt) {
        this.matUnt = matUnt;
    }

    public String getPchsNum() {
        return pchsNum;
    }

    public void setPchsNum(String pchsNum) {
        this.pchsNum = pchsNum;
    }

    public String getDlvAdr() {
        return dlvAdr;
    }

    public void setDlvAdr(String dlvAdr) {
        this.dlvAdr = dlvAdr;
    }

    public Timestamp getDlvDte() {
        return dlvDte;
    }

    public void setDlvDte(Timestamp dlvDte) {
        this.dlvDte = dlvDte;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<GrdClctInfUniLowDtlGrdInfIoDto> getGrdInfs() {
        return grdInfs;
    }

    public void setGrdInfs(List<GrdClctInfUniLowDtlGrdInfIoDto> grdInfs) {
        this.grdInfs = grdInfs;
    }

    @Override
    public String toString() {
        return "GrdClctInfUniLowDtlIoDto{" +
                "matId=" + matId +
                ", matCod='" + matCod + '\'' +
                ", matNam='" + matNam + '\'' +
                ", matUnt='" + matUnt + '\'' +
                ", pchsNum='" + pchsNum + '\'' +
                ", dlvAdr='" + dlvAdr + '\'' +
                ", dlvDte=" + dlvDte +
                ", memo='" + memo + '\'' +
                ", grdInfs=" + grdInfs +
                '}';
    }
}
