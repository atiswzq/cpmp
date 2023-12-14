package cn.cofco.cpmp.dto;

/**
 * Created by Tao on 2017/5/16.
 */
public class IoBidProjOffMatInfDto {
    // 自增ID
    private Long id;

    // 项目ID
    private Long projId;

    // 物料编码
    private String matCod;

    // 物料名称
    private String matNam;

    // 采购数量
    private String pchsNum;

    // 物料计量单位
    private String matUnit;

    // 技术指标与服务要求
    private String techServ;

    // 备注
    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
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

    public String getPchsNum() {
        return pchsNum;
    }

    public void setPchsNum(String pchsNum) {
        this.pchsNum = pchsNum;
    }

    public String getMatUnit() {
        return matUnit;
    }

    public void setMatUnit(String matUnit) {
        this.matUnit = matUnit;
    }

    public String getTechServ() {
        return techServ;
    }

    public void setTechServ(String techServ) {
        this.techServ = techServ;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "IoBidProjOffMatInfDto{" +
                "id=" + id +
                ", projId=" + projId +
                ", matCod='" + matCod + '\'' +
                ", matNam='" + matNam + '\'' +
                ", pchsNum=" + pchsNum +
                ", matUnit='" + matUnit + '\'' +
                ", techServ='" + techServ + '\'' +
                ", memo='" + memo + '\'' +
                '}';
    }
}
