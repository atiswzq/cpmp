package cn.cofco.cpmp.dto;

import java.util.List;

/**
 * Created by Xujy on 2017/4/29.
 */
public class PagedResult {

    public PagedResult() {

    }

    public PagedResult(List list, Integer count) {
        this.list = list;
        this.count = count;
    }

    List list;

    Integer count;

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
}
