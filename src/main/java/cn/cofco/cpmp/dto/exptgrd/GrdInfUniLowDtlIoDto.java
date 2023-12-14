package cn.cofco.cpmp.dto.exptgrd;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Xujy on 2017/12/23.
 * for [最低单价评标明细 - 物料] in cpmp
 */
public class GrdInfUniLowDtlIoDto {

    private Long matId;

    private String matCod;

    private String matNam;

    private String matUnt;

    private String pchsNum;

    private String dlvAdr;

    private Timestamp dlvDte;

    private String memo;

    private List<GrdInfUniLowDtlQotInfIoDto> qotInfs;

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

    public List<GrdInfUniLowDtlQotInfIoDto> getQotInfs() {
        return qotInfs;
    }

    public void setQotInfs(List<GrdInfUniLowDtlQotInfIoDto> qotInfs) {
        this.qotInfs = qotInfs;
    }

    @Override
    public String toString() {
        return "GrdInfUniLowDtlIoDto{" +
                "matId=" + matId +
                ", matCod='" + matCod + '\'' +
                ", matNam='" + matNam + '\'' +
                ", matUnt='" + matUnt + '\'' +
                ", pchsNum='" + pchsNum + '\'' +
                ", dlvAdr='" + dlvAdr + '\'' +
                ", dlvDte=" + dlvDte +
                ", memo='" + memo + '\'' +
                ", qotInfs=" + qotInfs +
                '}';
    }
}
