package cn.cofco.cpmp.dto.exptgrd;

import java.util.List;

/**
 * Created by Xujy on 2017/12/23.
 * for [提交最低价评标结果] in cpmp
 */
public class IoGrdInfLowPostDto {

    private Long grdId;

    List<IoGrdInfLowPostDtlDto> list;

    public Long getGrdId() {
        return grdId;
    }

    public void setGrdId(Long grdId) {
        this.grdId = grdId;
    }

    public List<IoGrdInfLowPostDtlDto> getList() {
        return list;
    }

    public void setList(List<IoGrdInfLowPostDtlDto> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "IoGrdInfLowPostDto{" +
                "grdId=" + grdId +
                ", list=" + list +
                '}';
    }


}
