package cn.cofco.cpmp.dto;

import java.util.List;

/**
 * Created by Xujy on 2017/9/23.
 * for [根据评标ID查看评分详情DTO] in cpmp
 */
public class ScrDtlsIoDto {

    private List list;
    private Integer count;
    private Boolean flg;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getFlg() {
        return flg;
    }

    public void setFlg(Boolean flg) {
        this.flg = flg;
    }

    @Override
    public String toString() {
        return "ScrDtlsIoDto{" +
                "list=" + list +
                ", count=" + count +
                ", flg=" + flg +
                '}';
    }
}
