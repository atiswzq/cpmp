package cn.cofco.cpmp.dto;

import cn.cofco.cpmp.entity.BidProjOnExptGrdDtl;
import cn.cofco.cpmp.entity.BidProjOnSplrQotDtl;
import cn.cofco.cpmp.entity.BidProjOnSplrQotInf;
import cn.cofco.cpmp.entity.BidProjOnSplrTendInf;

import java.util.List;

/**
 * Created by Xujy on 2017/5/30.
 * for [开标记录表DTO] in cpmp
 */
public class OpenRcdIoDto {

    private Long matId;

    private String matCod;

    private String matNam;

    private String matUnt;

    private String matMemo;

    private String dlvAdr;

    private String dlvDte;

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public String getDlvDte() {
        return dlvDte;
    }

    public void setDlvDte(String dlvDte) {
        this.dlvDte = dlvDte;
    }

    public String getDlvAdr() {
        return dlvAdr;
    }

    public void setDlvAdr(String dlvAdr) {
        this.dlvAdr = dlvAdr;
    }

    List<OpenRcdSplrQotIoDto> splrQotIoDtos;

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

    public String getMatMemo() {
        return matMemo;
    }

    public void setMatMemo(String matMemo) {
        this.matMemo = matMemo;
    }

    public List<OpenRcdSplrQotIoDto> getSplrQotIoDtos() {
        return splrQotIoDtos;
    }

    public void setSplrQotIoDtos(List<OpenRcdSplrQotIoDto> splrQotIoDtos) {
        this.splrQotIoDtos = splrQotIoDtos;
    }

    @Override
    public String toString() {
        return "OpenRcdIoDto{" +
                "matId='" + matId + '\'' +
                "matCod='" + matCod + '\'' +
                ", matNam='" + matNam + '\'' +
                ", matUnt='" + matUnt + '\'' +
                ", matMemo='" + matMemo + '\'' +
                ", dlvAdr='" + dlvAdr + '\'' +
                ", splrQotIoDtos=" + splrQotIoDtos +'\''+
                ", dlvDte=" + dlvDte +
                '}';
    }
}
