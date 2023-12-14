package cn.cofco.cpmp.dto;

/**
 * Created by Xujy on 2017/5/28.
 * for [采购员查询评标专家列表DTO] in cpmp
 */
public class IoGetExptInfForPchsDto {

    /**
     * 物料类型 - 必填
     */
    private String matTyp;

    public String getMatTyp() {
        return matTyp;
    }

    public void setMatTyp(String matTyp) {
        this.matTyp = matTyp;
    }

    @Override
    public String toString() {
        return "IoGetExptInfForPchsDto{" +
                "matTyp='" + matTyp + '\'' +
                '}';
    }
}
