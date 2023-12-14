package cn.cofco.cpmp.dto;

import java.math.BigDecimal;

/**
 * Created by Xujy on 2017/5/28.
 * for [供应商实时报价查询当前报价排名以及最低报价下行报文] in cpmp
 */
public class QotOrderIoDto {

    /**
     * 当前排名
     */
    private int order;
    /*
    * 当前物料id
    * */
    private  Long matId;

    /*
    * 带币种价格最小值
    * */
    private String minPrice;
      /*
    * 带币种价格最小值
    * */
    private String maxPrice;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        return "QotOrderIoDto{" +
                "order=" + order +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", matId=" + matId +
                '}';
    }
}